package telran.util;

import java.util.Arrays;

public class Array<T> implements IndexedList<T> {
	private T array[];
	private int size;
	private static int defaultCapacity = 16;

	private void realocate() {
		array = Arrays.copyOf(array, array.length * 2);
	}

	@SuppressWarnings("unchecked")
	public Array(int capacity) {
		array = (T[]) new Object[capacity];

	}

	public Array() {
		this(defaultCapacity);
	}

	@Override
	public void add(T obj) {

		if (size == array.length) {
			realocate();
		}
		array[size++] = obj;
	}

	@Override
	public boolean add(int index, T obj) {
		if (index < 0 || index > size - 1)
			return false;
		if (size == array.length) {
			realocate();
		}
		System.arraycopy(array, index, array, index + 1, size - index);
		array[index] = obj;
		size++;

		return true;
	}

	@Override
	public T remove(int index) {
		if (index < 0 || index > size)
			return null;
		T temp = array[index];
		System.arraycopy(array, index + 1, array, index, size - index - 1);
		size--;
		array[size] = null;
		return temp;
	}

	@Override
	public T get(int x) {
		if (x < 0 || x >= size)
			return null;
		return array[x];
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public int indexOf(Object pattern) {
		for (int i = 0; i < size; i++)
			if (array[i].equals(pattern))
				return i;
		return -1;
	}

	@Override
	public int lastIndexOf(Object pattern) {

		for (int i = size - 1; i > 0; i--)
			if (array[i].equals(pattern))
				return i;
		return -1;
	}

	@Override
	public void reverse() {
		for (int left = 0, right = size - 1; left < right; left++, right--) {
			T tmp = array[left];
			array[left] = array[right];
			array[right] = tmp;
		}
	}

	@Override
	public T remove(Object pattern) {
		for (int i = 0; i < size; i++) {
			if (array[i].equals(pattern))
				return this.remove(i);
		}
		return null;
	}

	@Override
	public boolean removeAll(IndexedList<T> patterns) {
		int currentSize = this.size;
		for (int i = 0; i < this.size; i++) {
			if (patterns.contains(this.get(i))) {
				this.remove(i--);
			}
		}
		return currentSize != this.size;
	}

	@Override
	public boolean contains(T pattern) {
		return this.indexOf(pattern) != -1;
	}

	@Override
	public boolean retainAll(IndexedList<T> patterns) {
		int currentSize = this.size;
		for (int i = 0; i < this.size; i++) {
			if (!patterns.contains(this.get(i))) {
				this.remove(i--);
			}
		}
		return currentSize != this.size;
	}
}

