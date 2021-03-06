package adt.hashtable.closed;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import adt.hashtable.closed.AbstractHashtableClosedAddress;
import adt.hashtable.closed.HashtableClosedAddressImpl;
import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class StudentTestHashtableClosedAddressDivisionMethod {

	protected AbstractHashtableClosedAddress<Integer> table1;
	protected AbstractHashtableClosedAddress<Integer> table2;

	protected final int PROPOSED_SIZE = 100;

	@Before
	public void setUp() throws Exception {
		table1 = new HashtableClosedAddressImpl<Integer>(PROPOSED_SIZE,
				HashFunctionClosedAddressMethod.DIVISION);

		Integer initialValue = 200;
		int increment = 5;
		while (initialValue < 600) {
			table1.insert(initialValue);
			initialValue = initialValue + increment;
		}

		table2 = new HashtableClosedAddressImpl<Integer>(PROPOSED_SIZE,
				HashFunctionClosedAddressMethod.DIVISION);

	}

	@Test
	public void testInsert() {
		assertEquals(0, table1.getCOLLISIONS());
		table1.insert(105); // nao produz colisao
		assertEquals(0, table1.getCOLLISIONS());
		assertEquals(4, table1.indexOf(105));
		table1.insert(110); // nao produz colisao
		assertEquals(0, table1.getCOLLISIONS());
		assertEquals(9, table1.indexOf(110));
		table1.insert(101); // produz colisao no 0
		assertEquals(1, table1.getCOLLISIONS());
		assertEquals(0, table1.indexOf(101));
		table1.insert(1010);
		assertEquals(2, table1.getCOLLISIONS());
		table2.insert(103); // nao produz colisao inserindo 1 elemento na talbe
							// vazia
		assertEquals(0, table2.getCOLLISIONS());
		assertEquals(2, table2.indexOf(103));
	}

	@Test
	public void testRemove() {
		int currentSize = table1.size();
		table1.remove(200); // elemento existente
		assertEquals(currentSize - 1, table1.size());
		assertEquals(-1, table1.indexOf(200));
		assertEquals(3, table1.indexOf(205));
		table1.remove(205);
		assertEquals(-1, table1.indexOf(205));
		table1.insert(205);
		assertEquals(3, table1.indexOf(205));
	}

	@Test
	public void testSearch() {
		// busca um elemento inexistente. compara a posicao
		assertNull(table1.search(100));
		assertEquals(-1, table1.indexOf(100));

		// busca um elemento existente. compara a posicao
		assertEquals(new Integer(305), table1.search(305));
		assertEquals(2, table1.indexOf(305));

	}

	@Test
	public void testIsEmpty() {
		assertFalse(table1.isEmpty());
		assertTrue(table2.isEmpty());
	}

	@Test
	public void testIsFull() {
		assertFalse(table1.isFull());
		for (int i = 0; i < 21; i++) {
			table1.insert(i);
		}
		assertTrue(table1.isFull());
		table1.remove(0);
		assertFalse(table1.isFull());
	}

	@Test
	public void testeCapacity(){
		assertEquals(101, table1.capacity());
	}

	@Test
	public void testSize() {
		assertEquals(80, table1.size());
		for (int i = 0; i < 21; i++) {
			table1.insert(i);
		}
		assertEquals(101, table1.size());
		for (int i = 21; i < 42; i++) {
			table1.insert(i);
		}
		assertEquals(122, table1.size());
		Integer initialValue = 200;
		int increment = 5;
		while (initialValue < 600) {
			table1.remove(initialValue);
			initialValue = initialValue + increment;
		}
		assertEquals(42, table1.size());
	}

}
