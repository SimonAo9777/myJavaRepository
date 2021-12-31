package datastructure.deque;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Deque;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Lab3 Test
 * 
 * @author Simon Ao
 * @version 2021-07-22
 * @References: Professor's class tutorials
 */

@DisplayName("Basic LinkedList Test")

class TestDeque {
	private Deque<String> deque = null;
	private String[] testStringArray = null;

	@BeforeEach
	private void setup() {
		deque = new LinkedListDeque<>();
		testStringArray = new String[8];
		for (int i = 0; i < 8; ++i) {
			testStringArray[i] = "Test" + i;
		}
	}

	@Nested
	@DisplayName( "OfferLast empty,one element,normal case Test")
	class OfferLastTest {

		@Test
		public void testOfferLastOnEmpty() {
			assertTrue(deque.isEmpty());
			assertTrue(deque.offerLast(testStringArray[0]));
			assertFalse(deque.isEmpty());
			assertEquals(1, deque.size());
		}
		
		@Test
		public void testOfferLastSizeOne() {
			assertTrue(deque.isEmpty());
			assertTrue(deque.offerLast(testStringArray[0]));
			assertEquals(1, deque.size());
			assertEquals(deque.peekFirst(), deque.peekLast());

			assertTrue(deque.offerLast(testStringArray[1]));
			assertEquals(deque.peekLast(), testStringArray[1]);
			assertEquals(deque.peekFirst(), testStringArray[0]);
			assertEquals(2, deque.size());
		}

		@Test
		public void testOfferLastOnNormal() {			
			assertTrue(deque.isEmpty());
			for (int i = 0; i < testStringArray.length; ++i) {
				assertTrue(deque.offerLast(testStringArray[i]));
			}
			assertEquals(testStringArray.length, deque.size());		
			assertEquals(testStringArray[testStringArray.length - 1], deque.peekLast());
		}

	}

	
	@Nested
	@DisplayName( "PollFirst empty,one element,normal case Test")
	class PollFirstTest {	
	
	@Test
	public void testPollFirstOnEmpty() {		
		assertTrue(deque.isEmpty());
		assertEquals(null, deque.pollFirst());
		assertTrue(deque.isEmpty());
	}

	@Test
	public void testPollFirstSizeOne() {	
		assertTrue(deque.isEmpty());
		assertTrue(deque.add(testStringArray[0]));
		assertEquals(1, deque.size());
		assertEquals(deque.peekFirst(), deque.peekLast());
		
		assertEquals(testStringArray[0], deque.pollFirst());
		assertTrue(deque.isEmpty());
	}

	@Test
	public void testPollFirstOnNormal() {		
		assertTrue(deque.isEmpty());
		for (int i = 0; i < testStringArray.length; ++i) {
			assertTrue(deque.add(testStringArray[i]));
		}
		assertEquals(testStringArray.length, deque.size());		
		for (int i = 0; i < testStringArray.length; ++i) {
			int size = deque.size();
			assertEquals(deque.pollFirst(), testStringArray[i]);
			assertEquals(size - 1, deque.size());
		}
	}

	
	}	
	
	
	
	
	@Nested
	@DisplayName( "PollFirst empty,one element,normal case Test")
	class PeekFirstTest {		
	
	@Test
	public void testPeekFirstOnEmpty() {		
		assertTrue(deque.isEmpty());
		assertEquals(null, deque.peekFirst());
		assertTrue(deque.isEmpty());
	}

	@Test
	public void testPeekFirstSizeOne() {
		assertTrue(deque.isEmpty());
		assertTrue(deque.add(testStringArray[0]));
		assertEquals(1, deque.size());
		assertEquals(deque.peekFirst(), deque.peekLast());
		
		assertEquals(testStringArray[0], deque.peekFirst());
		assertEquals(1, deque.size());
	}

	@Test
	public void testPeekFirstOnNormal() {		
		assertTrue(deque.isEmpty());
		for (int i = 0; i < testStringArray.length; ++i) {
			assertTrue(deque.add(testStringArray[i]));
		}
		assertEquals(testStringArray.length, deque.size());		
		assertEquals(deque.peekFirst(), testStringArray[0]);
	}

	}	
	
	
	
	@Nested
	@DisplayName( "RemoveLastOccurence empty,one element,normal case Test")
	class RemoveLastOccurenceTest {		
	
	@Test
	public void testRemoveLastOccurenceOnEmpty() {		
		assertTrue(deque.isEmpty());
		assertFalse(deque.removeLastOccurrence(testStringArray[0]));
		assertTrue(deque.isEmpty());
	}

	@Test
	public void testRemoveLastOccurenceSizeOne() {
		assertTrue(deque.isEmpty());
		assertTrue(deque.add(testStringArray[0]));
		assertEquals(1, deque.size());
		assertEquals(deque.peekFirst(), deque.peekLast());
	
		assertTrue(deque.removeLastOccurrence(testStringArray[0]));
		assertEquals(0, deque.size());
	}

	@Test
	public void testRemoveLastOccurenceOnNormal() {		
		assertTrue(deque.isEmpty());
		for (int i = 0; i < testStringArray.length; ++i) {
			assertTrue(deque.add(testStringArray[i]));
		}
		assertEquals(testStringArray.length, deque.size());		
		for (int i = 0; i < testStringArray.length; ++i) {
			int size = deque.size();
			assertTrue(deque.removeLastOccurrence(testStringArray[i]));
			assertEquals(size - 1, deque.size());
		}
	}

	@Test
	public void testRemoveLastOccurenceOnError() {		
		assertTrue(deque.isEmpty());		
		assertThrows(ClassCastException.class, () -> deque.removeLastOccurrence((String) (Object) 567));
		assertEquals(0, deque.size());
	}
	
	
	}
	
	
	@Nested
	@DisplayName( "Size empty,one element,normal case Test")
	class SizeTest {		
	
	@Test
	public void testSizeOnEmpty() {		
		assertEquals(0, deque.size());
	}

	@Test
	public void testSizeSizeOne() {		
		assertTrue(deque.isEmpty());
		assertTrue(deque.add(testStringArray[0]));
		assertEquals(1, deque.size());
	}

	@Test
	public void testSizeOnNormal() {		
		assertTrue(deque.isEmpty());
		for (int i = 0; i < testStringArray.length; ++i) {
			assertTrue(deque.add(testStringArray[i]));
		}
		assertEquals(testStringArray.length, deque.size());
	}
	
	}
	
	
	@Nested
	@DisplayName( "IsEmpty empty,one element,normal case Test")
	class IsEmptyTest {	
	@Test
	public void testIsEmptyOnEmpty() {		
		assertTrue(deque.isEmpty());
	}

	@Test
	public void testIsEmptySizeOne() {		
		assertTrue(deque.isEmpty());
		assertTrue(deque.add(testStringArray[0]));
		assertFalse(deque.isEmpty());
	}
	@Test
	public void testIsEmptyOnNormal() {		
		assertTrue(deque.isEmpty());
		for (int i = 0; i < testStringArray.length; ++i) {
			assertTrue(deque.add(testStringArray[i]));
		}
		assertFalse(deque.isEmpty());
	}
	
	
	}	
	
	
	@Nested
	@DisplayName( "Clear empty,one element,normal case Test")
	class ClearTest {		

	@Test
	public void testClearOnEmpty() {		
		assertTrue(deque.isEmpty());
		deque.clear();
		assertTrue(deque.isEmpty());
	}

	@Test
	public void testClearSizeOne() {		
		assertTrue(deque.isEmpty());
		assertTrue(deque.add(testStringArray[0]));
		assertEquals(1, deque.size());
		assertEquals(deque.peekFirst(), deque.peekLast());		
		deque.clear();
		assertEquals(0, deque.size());
	}

	@Test
	public void testClearOnNormal() {		
		assertTrue(deque.isEmpty());
		for (int i = 0; i < testStringArray.length; ++i) {
			assertTrue(deque.add(testStringArray[i]));
		}
		assertEquals(testStringArray.length, deque.size());		
		deque.clear();
		assertEquals(0, deque.size());
	}

}
}