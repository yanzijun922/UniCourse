package tick1;

public class MaxCharHeap implements MaxCharHeapInterface {

	char[] cheap;
	int size = 0;

	public MaxCharHeap(String s) {
		cheap = new char[s.length()];
		cheap = s.toLowerCase().toCharArray();
		size = s.length();
		for (int i = size / 2; i > -1; --i) {
			heapifyTop(i);
		}
	}

	private void swap(char[] carr, int a, int b) {
		char t = carr[a];
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
				if (cheap[left] < cheap[right]) {
					largerChild = right;
				}
				if (cheap[largerChild] > cheap[p]) {
					swap(cheap, p, largerChild);
				} else {
					return;
				}
			} else {
				if (cheap[left] > cheap[p]) {
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
			if (cheap[parent] < cheap[p]) {
				swap(cheap, p, parent);
			} else {
				return;
			}
			p = parent;
			parent = (p - 1) / 2;
		}
	}

	@Override
	public char getMax() throws EmptyHeapException {
		if (size == 0)
			throw new EmptyHeapException();

		char c = cheap[0];
		cheap[0] = cheap[size - 1];
		--size;
		heapifyTop(0);
		return c;

	}

	@Override
	public void insert(char i) {
		if (size <= cheap.length) {
			char[] newcheap = new char[size * 2 + 1];
			for (int in = 0; in < size; ++in) {
				newcheap[in] = cheap[in];
			}
			cheap = newcheap;
		}
		cheap[size++] = Character.toLowerCase(i);
		heapifyBottom(size - 1);

	}

	@Override
	public int getLength() {
		return size;
	}
}
