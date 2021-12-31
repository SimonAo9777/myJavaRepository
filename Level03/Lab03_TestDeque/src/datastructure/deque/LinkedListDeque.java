package datastructure.deque;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 
 * @author Simon Ao 040983402
 * @version 2021-07-22
 * @References: Professor's class tutorials
 * 
 *              This class create the concrete implementation of java.util.Deque
 *              using a DoublyLinkedList rules. The class inherit the attached
 *              AbstractDeque.
 */
public class LinkedListDeque<E> extends AbstractDeque<E> {
	private Node<E> head;
	private Node<E> tail;
	private int size;

	/**
	 * Override Iterator interface to DequeIterator()
	 */
	@Override
	public Iterator<E> iterator() {
		return new DequeIterator();
	}

	/**
	 * Override Iterator interface to DequeDescIterator()
	 */
	@Override
	public Iterator<E> descendingIterator() {
		return new DequeDescIterator();
	}

	private void removeToNode(Node<E> node) {

		Node<E> prev = node.prev;
		Node<E> next = node.next;

		if (node == head) {
			head = next;
		} else {
			prev.next = next;
		}
		if (node == tail) {
			tail = prev;
		} else {
			next.prev = prev;
		}
		node.prev = node.next = null;
		--size;
	}

	@Override
	public boolean offerFirst(E e) {
		// TODO Auto-generated method stub
		Node<E> temp = new Node<>(e);
		if (isEmpty()) {
			head = tail = temp;
		} else {
			temp.next = head;
			temp.prev = temp;
			head = temp;
		}

		size++;
		return true;
	}

	@Override
	public boolean offerLast(E e) {
		// TODO Auto-generated method stub
		Node<E> temp = new Node<>(e);
		if (isEmpty()) {
			head = tail = temp;
		} else {
			temp.prev = tail;
			tail.next = temp;
			tail = temp;
		}

		size++;
		return true;
	}

	@Override
	public E pollFirst() {
		// TODO Auto-generated method stub
		if (isEmpty())
			return null;
		E temp = head.value;
		removeToNode(head);
		return temp;
	}

	@Override
	public E pollLast() {
		// TODO Auto-generated method stub
		if (isEmpty())
			return null;
		E temp = tail.value;
		removeToNode(tail);
		return temp;
	}

	@Override
	public E peekFirst() {
		if (isEmpty())
			return null;
		return head.value;
	}

	@Override
	public E peekLast() {
		if (isEmpty())
			return null;
		return tail.value;
	}

	@Override
	public boolean removeFirstOccurrence(Object o) {

		if (o == null)
			return false;

		Node<E> temp = head;
		while (temp != null) {
			if (temp.value.equals(o)) {
				remove(temp);
				return true;
			} else {
				temp = temp.next;
			}
		}
		return false;
	}

	@Override
	public boolean removeLastOccurrence(Object o) {
		if (o == null) {
			for (Node<E> node = tail; node != null; node = node.prev) {
				if (node.value == null) {
					removeToNode(node);
					return true;
				}
			}
		} else {
			for (Node<E> node = tail; node != null; node = node.prev) {
				if (o.equals(node.value)) {
					removeToNode(node);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean contains(Object o) {
		Node<E> temp = head;
		while (temp != null) {
			if (temp.value.equals(o)) {
				return true;
			} else {
				temp = temp.next;
			}
		}
		return false;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return size == 0;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		head = tail = null;
		size = 0;

	}

	private static class Node<E> {
		private E value;
		private Node<E> next;
		private Node<E> prev;

		private Node(E value) {
			this(value, null, null);
		}

		private Node(Node<E> next, Node<E> prev) {
			this(null, next, prev);
		}

		private Node(E value, Node<E> next, Node<E> prev) {
			this.value = value;
			this.next = next;
			this.prev = prev;
		}

		public String toString() {
			return String.valueOf(value);
		}
	}

	private class DequeIterator implements Iterator<E> {
		Node<E> current;
		Node<E> next;

		public DequeIterator() {
			current = null;
			next = head;
		}

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return next != null;
		}

		@Override
		public E next() {
			// TODO Auto-generated method stub
			if (!hasNext())
				throw new NoSuchElementException();
			current = next;
			next = next.next;
			return current.value;
		}

		@Override
		public void remove() {
			if (current == null)
				throw new IllegalStateException();
			removeToNode(current);
			current = null;
		}

	}

	private class DequeDescIterator implements Iterator<E> {
		Node<E> current;
		Node<E> next;

		public DequeDescIterator() {
			current = null;
			next = tail;
		}

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return next != null;
		}

		@Override
		public E next() {
			// TODO Auto-generated method stub
			if (!hasNext())
				throw new NoSuchElementException();
			current = next;
			next = next.prev;
			return current.value;
		}

		@Override
		public void remove() {
			if (current == null)
				throw new IllegalStateException();
			removeToNode(current);
			current = null;
		}

	}

}
