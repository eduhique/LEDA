package adt.heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import util.Util;

/**
 * O comportamento de qualquer heap é definido pelo heapify. Neste caso o
 * heapify dessa heap deve comparar os elementos e colocar o maior sempre no
 * topo. Ou seja, admitindo um comparador normal (responde corretamente 3 > 2),
 * essa heap deixa os elementos maiores no topo. Essa comparação não é feita
 * diretamente com os elementos armazenados, mas sim usando um comparator.
 * Dessa forma, dependendo do comparator, a heap pode funcionar como uma max-heap
 * ou min-heap.
 */
public class HeapImpl<T extends Comparable<T>> implements Heap<T> {

    private static final int INITIAL_SIZE = 20;
    private static final int INCREASING_FACTOR = 10;
    protected T[] heap;
    protected int index = -1;
    /**
     * O comparador é utilizado para fazer as comparações da heap. O ideal é
     * mudar apenas o comparator e mandar reordenar a heap usando esse joseTheDev
     * comparator. Assim os metodos da heap não precisam saber se vai funcionar
     * como max-heap ou min-heap.
     */
    protected Comparator<T> comparator;

    /**
     * Construtor da classe. Note que de inicio a heap funciona como uma
     * min-heap.
     */
    @SuppressWarnings("unchecked")
    public HeapImpl(Comparator<T> comparator) {
        this.heap = (T[]) (new Comparable[INITIAL_SIZE]);
        this.comparator = comparator;
    }

    // /////////////////// METODOS IMPLEMENTADOS
    private int parent(int i) {
        return (i - 1) / 2;
    }

    /**
     * Deve retornar o indice que representa o filho a esquerda do elemento
     * indexado pela posicao i no vetor
     */
    private int left(int i) {
        return (i * 2 + 1);
    }

    /**
     * Deve retornar o indice que representa o filho a direita do elemento
     * indexado pela posicao i no vetor
     */
    private int right(int i) {
        return (i * 2 + 1) + 1;
    }

    @Override
    public boolean isEmpty() {
        return (index == -1);
    }

    @Override
    public T[] toArray() {
        ArrayList<T> resp = new ArrayList<T>();
        for (T elem : this.heap) {
            if (elem != null) {
                resp.add(elem);
            }
        }
        return (T[]) resp.toArray(new Comparable[0]);
    }

    // ///////////// METODOS A IMPLEMENTAR

    /**
     * Valida o invariante de uma heap a partir de determinada posicao, que pode
     * ser a raiz da heap ou de uma sub-heap. O heapify deve colocar os maiores
     * (comparados usando o comparator) elementos na parte de cima da heap.
     */
    private void heapify(int position) {
        int left = this.left(position);
        int right = this.right(position);
        int largest = position;
        if (left < this.size() && getComparator().compare(this.getHeap()[left], this.getHeap()[largest]) > 0) {
            largest = left;
        }
        if (right < this.size() && getComparator().compare(this.getHeap()[right], this.getHeap()[largest]) > 0) {
            largest = right;
        }
        if (largest != position) {
            Util.swap(getHeap(), position, largest);
            heapify(largest);
        }
    }

    @Override
    public void insert(T element) {
        // ESSE CODIGO E PARA A HEAP CRESCER SE FOR PRECISO. NAO MODIFIQUE
        if (index == heap.length - 1) {
            heap = Arrays.copyOf(heap, heap.length + INCREASING_FACTOR);
        }
        // /////////////////////////////////////////////////////////////////
        if (element != null) {
            int index = ++this.index;
            while (index > 0 && getComparator().compare(getHeap()[this.parent(index)], element) < 0) {
                getHeap()[index] = getHeap()[this.parent(index)];
                index = this.parent(index);
            }
            getHeap()[index] = element;
        }
    }

    @Override
    public void buildHeap(T[] array) {
        this.heap = array;
        this.index = array.length - 1;
        for (int i = index / 2; i >= 0; i--) {
            this.heapify(i);
        }
    }

    @Override
    public T extractRootElement() {
        T result = null;
        if (!this.isEmpty()) {
            result = getHeap()[0];
            getHeap()[0] = null;
            Util.swap(getHeap(), 0, this.size() - 1);
            index--;
            heapify(0);
        }
        return result;
    }

    @Override
    public T rootElement() {
        T result = null;
        if (!isEmpty()) {
            result = getHeap()[0];
        }
        return result;
    }

    @Override
    public T[] heapsort(T[] array) {
        Comparator<T> originalComparator = this.comparator;

        this.comparator = (o1, o2) -> o1.compareTo(o2); //Garantindo que sempre irá ordenar do menor para o maior.

        buildHeap(array);

        for (int i = array.length - 1; i >= 1; i--) {
            Util.swap(heap, 0, i);
            this.index--;
            this.heapify(0);
        }

        array = Arrays.copyOf(heap, array.length);

        //Reinicializa a heap
        index = -1;
        this.heap = (T[]) (new Comparable[INITIAL_SIZE]);
        this.comparator = originalComparator;

        return array;
    }

    @Override
    public int size() {
        return this.index + 1;
    }

    public Comparator<T> getComparator() {
        return comparator;
    }

    public void setComparator(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public T[] getHeap() {
        return heap;
    }

}
