package datastructure.deque;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.Objects;

public abstract class AbstractDeque< E> implements Deque< E> {

	@Override
	public Iterator< E> iterator() {
		throw new UnsupportedOperationException( "iterator() is a bonus method, you must override it.");
	}

	@Override
	public Iterator< E> descendingIterator() {
		throw new UnsupportedOperationException( "descendingIterator() is a bonus method, you must override it.");
	}

	@Override
	public boolean remove( Object o) {
		throw new UnsupportedOperationException( "Error - remove(Object) is not implemented");
	}

	@Override
	public Object[] toArray() {
		Object[] array = new Object[ size()];
		int i = 0;
		for ( E r : this) {
			array[i++] = r;
		}
		return array;
	}

	@SuppressWarnings( "unchecked")
	@Override
	public < T> T[] toArray( T[] a) {
		Objects.requireNonNull( a);
		if ( a.length < size())
			a = (T[]) new Object[ size()];
		int i = 0;
		for ( E r : this) {
			a[i++] = (T) r;
		}
		return a;
	}

	@Override
	public boolean addAll( Collection< ? extends E> c) {
		Objects.requireNonNull( c);
		for ( E r : c) {
			add( r);
		}
		return true;
	}

	@Override
	public boolean containsAll( Collection< ?> c) {
		for ( Object o : c) {
			if ( !contains( o))
				return false;
		}
		return true;
	}

	@Override
	public boolean removeAll( Collection< ?> c) {
		for ( Object o : c) {
			remove( o);
		}
		return true;
	}

	@Override
	public boolean retainAll( Collection< ?> c) {
		Iterator< E> it = iterator();
		while( it.hasNext()) {
			if(!c.contains( it.next()))
				it.remove();
		}
		return true;
	}

	@Override
	public void addFirst( E e) {
		offerFirst( e);
	}

	@Override
	public void addLast( E e) {
		offerLast( e);
	}

	@Override
	public E removeFirst() {
		return pollFirst();
	}

	@Override
	public E removeLast() {
		return pollLast();
	}

	@Override
	public E getFirst() {
		return peekFirst();
	}

	@Override
	public E getLast() {
		return peekLast();
	}

	@Override
	public boolean add( E e) {
		addLast( e);
		return true;
	}

	@Override
	public boolean offer( E e) {
		return offerLast( e);
	}

	@Override
	public E remove() {
		return removeFirst();
	}

	@Override
	public E poll() {
		return pollFirst();
	}

	@Override
	public E element() {
		return getFirst();
	}

	@Override
	public E peek() {
		return getFirst();
	}

	@Override
	public void push( E e) {
		addFirst( e);
	}

	@Override
	public E pop() {
		return removeFirst();
	}
}