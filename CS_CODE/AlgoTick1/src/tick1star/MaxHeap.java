package tick1star;

import java.util.List;

public class MaxHeap<T extends Comparable<T>> implements MaxHeapInterface<T> {

	T[] cheap;
	int size = 0;

	public MaxHeap(List<T> s) {
		cheap = (T[]) new Comparable[s.size()];
		cheap = s.toArray(cheap);
		size = s.size();
		for (int i = size / 2; i > -1; --i) {
			heapifyTop(i);
		}
	}

	private void swap(T[] carr, int a, int b) {
		T t = carr[a];
		carr[a] = carr[b];
		carr[b] = t;
	}

	private void heapifyTop(int p) {
		int largerChild;
		int left;
		int right;
		while (p < size / 2) {
			left = 2 * p + 1;
			right = 2 * p + 2;
			largerChild = left;
			if (right < size) {
				if (cheap[left].compareTo(cheap[right]) < 0) {
					largerChild = right;
				}
				if (cheap[largerChild].compareTo(cheap[p]) > 0) {
					swap(cheap, p, largerChild);
				} else {
					return;
				}
			} else {
				if (cheap[left].compareTo(cheap[p]) > 0) {
					swap(cheap, p, left);
				} else {
					return;
				}
			}
			p = largerChild;
		}
	}

	private void heapifyBottom(int p) {
		int parent = (p - 1) / 2;
		while (p > 0) {
			if (cheap[parent].compareTo(cheap[p]) < 0) {
				swap(cheap, p, parent);
			} else {
				return;
			}
			p = parent;
			parent = (p - 1) / 2;
		}
	}

	@Override
	public T getMax() throws EmptyHeapException {
		if (size == 0)
			throw new EmptyHeapException();

		T c = (T) cheap[0];
		cheap[0] = cheap[size - 1];
		--size;
		heapifyTop(0);
		return c;

	}

	@Override
	public void insert(T i) {
		if (size <= cheap.length) {
			T[] newcheap = (T[]) new Comparable[size * 2 + 1];
			for (int in = 0; in < size; ++in) {
				newcheap[in] = cheap[in];
			}
			cheap = newcheap;
		}
		cheap[size++] = i;
		heapifyBottom(size - 1);

	}

	@Override
	public int getLength() {
		return size;
	}
}
