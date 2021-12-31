package datastructure.deque;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.function.IntFunction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * CST_8288_21F Lab2 JUnit 5 Test
 * 
 * @author Simon Ao 040983402
 * @version Jun 25,2021
 * 
 * @References: Professor's tutorials
 * 
 * In this lab you will test the edge, normal, and error cases of
 * some methods in theLinkedListDeque
 * 
 * Test the methods below from LinkedListDeque class: 1.offerLast
 * 2.pollFirst 3.peekFirst 4.removeLastOccurence 5.size 6.isEmpty
 * 7.clear
 * 
 *Create test method for error, normal, and edge cases
 */

public class TestDeque {

	private Deque<Integer> deque;
	private Random random = new Random(1);

	@BeforeEach
	void setup() {
		deque = new LinkedList<>();
	}

	@AfterEach
	void teardown() {
		deque = null;
	}

	<T> void newData(Collection<T> array, final int reSet, final int COUNT, IntFunction<T> newSup) {
		for (int i = reSet; i < reSet + COUNT; i++)
			array.add(newSup.apply(i));
	}

	<T> void newData(Collection<T> array, final int COUNT, IntFunction<T> reSup) {
		newData(array, 0, COUNT, reSup);
	}

	@Nested
	@DisplayName("Test offerLast")
	public class offerLastTest {

		@Test
		@DisplayName("Test one element, size check")
		final void testOfferToEmptySize() {
			assertTrue(deque.isEmpty());
			deque.offerLast(1);
			assertEquals(1, deque.size());
			assertFalse(deque.isEmpty());
		}

		@Test
		@DisplayName("Test one element, element check")
		final void testOfferToEmptyElement() {
			assertTrue(deque.isEmpty());
			deque.offerLast(1);
			assertEquals(1, deque.pollFirst());
		}

		@Test
		@DisplayName("Test add null to an empty list twice")
		void testOfferingNullToList() {
			Integer test = null;
			assertTrue(deque.isEmpty());
			assertTrue(deque.offerLast(test));
			assertNull(deque.peekLast());
			assertTrue(deque.offerLast(test));
			assertNull(deque.peekLast());
			assertNull(deque.peekLast());
			assertEquals(2, deque.size());
		}

		@Test
		@DisplayName("Test add to a none mepty list once")
		void testOfferingToNoneEmptyList() {
			int test = 1;
			assertTrue(deque.isEmpty());
			assertTrue(deque.offerLast(test));
			assertEquals(test, deque.peekLast());

			int test2 = 2;
			assertTrue(deque.offerLast(test2));
			assertEquals(test, deque.peekFirst());
			assertEquals(test2, deque.peekLast());

			assertEquals(2, deque.size());
		}

		@Test
		@DisplayName("Test many, size check")
		final void testOfferManySizeChange() {
			assertTrue(deque.isEmpty());
			for (int i = 0; i < 50; i++) {
				int number = random.nextInt(Integer.MAX_VALUE);
				deque.offerLast(number);
				assertEquals(i + 1, deque.size(), "Show index = " + i);
			}
		}

		@Test
		@DisplayName("Test many, element check")
		final void testOfferManyElementChange() {
			assertTrue(deque.isEmpty());
			for (int i = 0; i < 50; i++) {
				int num = random.nextInt(Integer.MAX_VALUE);
				deque.offerLast(num);
				assertEquals(num, deque.peekLast(), "Show index = " + i);
			}
		}

	}

	@Nested
	@DisplayName("Test poolFirst")
	class PollFirstTest {

		@Test
		@DisplayName("Test empty")
		final void testPollFirstEmpty() {
			assertTrue(deque.isEmpty());
			assertNull(deque.pollFirst());
		}

		@Test
		@DisplayName("Test one element, element and size check")
		final void testPollFirstSizeOne() {
			assertTrue(deque.isEmpty());
			deque.offerFirst(1);
			assertEquals(1, deque.pollFirst());
			assertEquals(0, deque.size());
		}

		@Test
		@DisplayName("Test two elements, element and size check")
		final void testPollFirstSizeTwo() {
			assertTrue(deque.isEmpty());
			deque.offerFirst(1);
			deque.offerLast(2);
			assertEquals(1, deque.pollFirst());
			assertEquals(1, deque.size());
			assertEquals(2, deque.pollFirst());
			assertEquals(0, deque.size());
		}

		@Test
		@DisplayName("Test three elements, element and size check")
		final void testPollFirstSizeThree() {
			assertTrue(deque.isEmpty());
			deque.offerFirst(1);
			deque.offerLast(2);
			deque.offerFirst(3);
			assertEquals(3, deque.pollFirst());
			assertEquals(2, deque.size());
		}

		@Test
		@DisplayName("Test many with offerFirst")
		final void testPollFirstManyWithOfferFirst() {
			for (int i = 0; i < 50; i++) {
				deque.offerFirst(i);
			}
		}

		@Test
		@DisplayName("Test many with offerLast")
		final void testPollFirstManyWithOfferLast() {
			for (int i = 0; i < 50; i++) {
				deque.offerLast(i);
			}
			for (int i = 0; i < 50; i++) {
				assertEquals(i, deque.pollFirst());
			}
		}
	}

	@Nested
	@DisplayName("Test peekFirst ")
	class PeekFirstTest {
		@Test
		@DisplayName("Test empty")
		final void testPeekFirstOnEmpty() {
			assertTrue(deque.isEmpty());
			assertNull(deque.peekFirst());
		}

		@Test
		@DisplayName("Test one element, element check")
		final void testGetFirstSizeOne() {
			assertTrue(deque.isEmpty());
			deque.offerFirst(1);
			assertEquals(1, deque.peekFirst());
		}

		@Test
		@DisplayName("Test two elements, element check")
		final void testPeekFirstSizeTwo() {
			assertTrue(deque.isEmpty());
			deque.offerFirst(1);
			deque.offerFirst(2);
			assertEquals(2, deque.peekFirst());
		}

		@Test
		@DisplayName("Test three elements, element check")
		final void testPeekFirstSizeThree() {
			assertTrue(deque.isEmpty());
			deque.offerFirst(1);
			deque.offerFirst(2);
			deque.offerFirst(3);
			assertEquals(3, deque.peekFirst());
		}

		@Test
		@DisplayName("Test many")
		final void testGetFirstMany() {
			assertTrue(deque.isEmpty());
			for (int i = 0; i < 50; i++) {
				deque.offerFirst(i);
				assertEquals(i, deque.peekFirst());
			}
		}
	}

	@Nested
	@DisplayName("Test removeLastOccurrence ")
	class RemoveLastOccurrenceTest {
		@Test
		@DisplayName("Test empty")
		final void testRemoveLastOccurrenceEmpty() {
			assertTrue(deque.isEmpty());
			assertFalse(deque.removeLastOccurrence(null));
			assertFalse(deque.removeLastOccurrence(1));
		}

		@Test
		@DisplayName("Test one remove, element and size check")
		final void testRemoveLastOccurrenceSizeOne() {
			assertTrue(deque.isEmpty());
			deque.offerLast(1);
			assertTrue(deque.removeLastOccurrence(1));
			assertEquals(0, deque.size());
		}

		@Test
		@DisplayName("Test two removes, element and size check")
		final void testRemoveLastOccurrenceSizeTwo() {
			assertTrue(deque.isEmpty());
			deque.offerLast(1);
			deque.offerLast(2);
			deque.offerLast(1);
			deque.offerLast(3);
			assertTrue(deque.removeLastOccurrence(1));
			assertEquals(3, deque.size());
			assertTrue(deque.removeLastOccurrence(1));
			assertEquals(2, deque.size());
			assertEquals(3, deque.pollLast());
			assertEquals(2, deque.pollLast());
		}

		@Test
		@DisplayName("Test three removes, element and size check")
		final void testRemoveLastOccurrenceSizeThree() {
			assertTrue(deque.isEmpty());
			assertTrue(deque.isEmpty());
			deque.offerLast(1);
			deque.offerLast(2);
			deque.offerLast(1);
			deque.offerLast(3);
			deque.offerLast(1);
			assertTrue(deque.removeLastOccurrence(1));
			assertEquals(4, deque.size());
			assertTrue(deque.removeLastOccurrence(1));
			assertEquals(3, deque.size());
			assertTrue(deque.removeLastOccurrence(1));
			assertEquals(2, deque.size());
			assertEquals(3, deque.pollLast());
			assertEquals(2, deque.pollLast());
		}

		@Test
		@DisplayName("Test all removes, element and size check")
		final void testRemoveLastOccurrenceAll() {
			assertTrue(deque.isEmpty());
			assertTrue(deque.isEmpty());
			deque.offerLast(1);
			deque.offerLast(1);
			deque.offerLast(1);
			deque.offerLast(1);
			deque.offerLast(1);
			assertTrue(deque.removeLastOccurrence(1));
			assertEquals(4, deque.size());
			assertTrue(deque.removeLastOccurrence(1));
			assertEquals(3, deque.size());
			assertTrue(deque.removeLastOccurrence(1));
			assertEquals(2, deque.size());
			assertTrue(deque.removeLastOccurrence(1));
			assertEquals(1, deque.size());
			assertTrue(deque.removeLastOccurrence(1));
			assertEquals(0, deque.size());
		}

		@Test
		@DisplayName("Test many")
		final void testRemoveLastOccurrenceMany() {
			for (int i = 0; i < 50; i++) {
				if (i % 2 == 0)
					deque.offerLast(-1);
				else
					deque.offerLast(i);
			}
			while (deque.removeLastOccurrence(-1))
				;

			assertEquals(25, deque.size());
		}
	}

	@Nested
	@DisplayName("Test size, isEmpty,and Clear")
	class SizeTest {

		@Test
		@DisplayName("Test size with offer")
		final void testSizeWithOffer() {
			assertEquals(0, deque.size());
			newData(deque, 6, i -> i);
			assertEquals(6, deque.size());

			deque.offerLast(6);
			assertEquals(7, deque.size());
			newData(deque, 12, 6, i -> i);
			assertEquals(13, deque.size());

			deque.offerFirst(8);
			assertEquals(14, deque.size());
			newData(deque, 16, 4, i -> i);
			assertEquals(18, deque.size());
		}

		@Test
		@DisplayName("Test size with Poll")
		final void testSizeWithPoll() {
			assertEquals(0, deque.size());
			newData(deque, 6, i -> i);
			assertEquals(6, deque.size());

			deque.pollFirst();
			assertEquals(5, deque.size());
			newData(deque, 8, 6, i -> i);
			assertEquals(11, deque.size());

			deque.pollLast();
			assertEquals(10, deque.size());
			newData(deque, 12, 4, i -> i);
			assertEquals(14, deque.size());
		}

		@Test
		@DisplayName("Test isEmpty  with offer")
		final void testIsEmptyWithOffer() {
			assertTrue(deque.isEmpty());
			newData(deque, 3, i -> i);
			assertFalse(deque.isEmpty());

			deque.offerLast(9);
			assertFalse(deque.isEmpty());

			deque.offerFirst(10);
			assertFalse(deque.isEmpty());
		}

		@Test
		@DisplayName("Test isEmpty  with Poll")
		final void testIsEmptyWithPoll() {
			assertTrue(deque.isEmpty());
			newData(deque, 3, i -> i);
			assertFalse(deque.isEmpty());

			deque.pollFirst();
			assertFalse(deque.isEmpty());

			deque.pollLast();
			assertFalse(deque.isEmpty());

			deque.pollLast();
			assertTrue(deque.isEmpty());
		}

		@Test
		@DisplayName("Test clear on populated test")
		final void testOnPopulatedList() {
			assertTrue(deque.isEmpty());
			assertEquals(0, deque.size());
			deque.clear();
			assertTrue(deque.isEmpty());
			assertEquals(0, deque.size());

			newData(deque, 6, i -> i);
			assertEquals(6, deque.size());
			assertFalse(deque.isEmpty());

			deque.clear();
			assertTrue(deque.isEmpty());
			assertEquals(0, deque.size());
		}

		@Test
		@DisplayName("Test clear on empty list")
		final void testOnEmptyList() {
			assertTrue(deque.isEmpty());
			assertEquals(0, deque.size());
			deque.clear();
			assertTrue(deque.isEmpty());
			assertEquals(0, deque.size());
		}

		@Test
		@DisplayName("Test contains on empty list")
		final void testContainsEmpty() {
			assertTrue(deque.isEmpty());
			assertFalse(deque.contains(null));
			assertFalse(deque.contains(1));
		}

		@Test
		@DisplayName("Test contains on populated list")
		final void testContainsPopulated() {
			assertTrue(deque.isEmpty());
			assertFalse(deque.contains(null));
			assertFalse(deque.contains(1));
			assertFalse(deque.contains(2));
			assertFalse(deque.contains(3));
			assertFalse(deque.contains(4));
			for (int i = 0; i < 50; i++) {
				deque.offerFirst(i);
			}
			for (int i = 0; i < 50; i++) {
				assertTrue(deque.contains(i));
			}
		}
	}

	@Nested
	@DisplayName("Test iterator for empty," 
	+ "one element,many elements,and error case")
	class iteratorTest {
		@Test
		void testIteratorInEmpty() {
			assertTrue(deque.isEmpty());
			Iterator<Integer> or = deque.iterator();
			assertFalse(or.hasNext());
			assertThrows(NoSuchElementException.class, () -> or.next());
			assertThrows(IllegalStateException.class, () -> or.remove());
		}

		@Test
		void testIteratorWithOneElements() {
			assertTrue(deque.isEmpty());
			Integer g = Integer.valueOf(0);
			assertTrue(deque.offerLast(g));
			assertEquals(1, deque.size());
			Iterator<Integer> or = deque.iterator();
			assertTrue(or.hasNext());
			assertEquals(Integer.valueOf(0), or.next());
			assertFalse(or.hasNext());

		}

		@Test
		void testIteratorWithManyElements() {

			assertTrue(deque.isEmpty());
			int maxSize = 50;
			for (int i = 0; i < maxSize; i++) {
				Integer value = Integer.valueOf(i);
				assertTrue(deque.offerLast(value));
			}
			assertEquals(50, deque.size());
			Iterator<Integer> or = deque.iterator();

			for (int i = 0; i < maxSize; i++) {
				assertTrue(or.hasNext());
				assertEquals(Integer.valueOf(i), or.next());
				if (i == 1)
					or.remove();
			}
			assertEquals(maxSize - 1, deque.size());
			assertFalse(or.hasNext());
			assertThrows(NoSuchElementException.class, () -> or.next());
		}

		@Test
		void testIteratorWithErrorCase() {

			assertTrue(deque.isEmpty());
			int maxSize = 50;
			for (int i = 0; i < maxSize; i++) {
				Integer value = Integer.valueOf(i);
				assertTrue(deque.offerLast(value));
			}
			assertEquals(50, deque.size());
			Iterator<Integer> or = deque.iterator();
			for (int i = 0; i < maxSize; i++) {
				assertTrue(or.hasNext());
				assertEquals(Integer.valueOf(i), or.next());
				or.remove();
			}
			assertThrows(NoSuchElementException.class, () -> or.next());
			assertThrows(IllegalStateException.class, () -> or.remove());
		}

	}

	@Nested
	@DisplayName("Test Descending iterator for empty," 
	+ "one element,many elements,and error case")

	class testDescendingIteratorWithElements {

		@Test
		void testDescendingIteratorForEmptyList() {
			assertTrue(deque.isEmpty());
			Iterator<Integer> or = deque.descendingIterator();
			assertFalse(or.hasNext());
		}

		@Test
		void testDescendingIteratorForListWithOneElement() {
			assertTrue(deque.isEmpty());
			Integer g = Integer.valueOf(0);
			assertTrue(deque.offerLast(g));
			assertEquals(1, deque.size());
			Iterator<Integer> or = deque.descendingIterator();
			assertTrue(or.hasNext());
			assertEquals(Integer.valueOf(0), or.next());
			assertFalse(or.hasNext());

		}

		@Test
		void testDescendingIteratorForListWithManyElements() {

			assertTrue(deque.isEmpty());
			int maxSize = 50;
			for (int i = 0; i < maxSize; i++) {
				Integer value = Integer.valueOf(i);
				assertTrue(deque.offerLast(value));
			}
			assertEquals(50, deque.size());
			Iterator<Integer> or = deque.descendingIterator();

			for (int i = maxSize - 1; i >= 0; i--) {
				assertTrue(or.hasNext());
				assertEquals(Integer.valueOf(i), or.next());
				if (i == 1)
					or.remove();
			}
			assertEquals(maxSize - 1, deque.size());

		}

		@Test
		void testDescendingIteratorWithErrorCase() {

			assertTrue(deque.isEmpty());
			int maxSize = 50;
			for (int i = 0; i < maxSize; i++) {
				Integer value = Integer.valueOf(i);
				assertTrue(deque.offerLast(value));
			}
			assertEquals(50, deque.size());
			Iterator<Integer> it = deque.descendingIterator();

			for (int i = maxSize - 1; i >= 0; i--) {
				assertTrue(it.hasNext());
				assertEquals(Integer.valueOf(i), it.next());
				it.remove();
			}
			assertThrows(NoSuchElementException.class, () -> it.next());
			assertThrows(IllegalStateException.class, () -> it.remove());

		}

	}

}