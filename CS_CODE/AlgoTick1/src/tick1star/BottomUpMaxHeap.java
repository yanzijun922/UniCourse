package tick1star;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BottomUpMaxHeap<T extends Comparable<T>> extends MaxHeap<T> {

	public BottomUpMaxHeap(List<T> s) {
		super(s);
	}

	@Override
	public T getMax() throws EmptyHeapException {
		if (size == 0)
			throw new EmptyHeapException();
		if (size == 1) {
			size = 0;
			return cheap[0];
		}

		T max = cheap[0];
		T sto = cheap[size - 1];
		LinkedList<Integer> path = new LinkedList<>();
		int iLargerChild = 0;
		int left;
		int right;

		path.addFirst(0);

		while (iLargerChild < size / 2) {
			left = iLargerChild * 2 + 1;
			right = iLargerChild * 2 + 2;
			iLargerChild = left;
			if (right < size - 1) {
				if (cheap[left].compareTo(cheap[right]) < 0) {
					iLargerChild = right;
				}
			}
			path.addLast(iLargerChild);
		}
		if (path.getLast() == size - 1)
			path.removeLast();
		while (path.size() > 1) {
			if (cheap[path.getLast()].compareTo(sto) < 0) {
				path.removeLast();
			} else {
				break;
			}
		}

		while (path.size() > 1) {
			cheap[path.getFirst()] = cheap[path.get(1)];
			path.removeFirst();
		}
		cheap[path.getLast()] = sto;

		--size;
		return max;
	}

	public static void main(String args[]) throws EmptyHeapException {
		ArrayList<Character> arrayLi = new ArrayList<>();
		for (Character c : "abcdefg".toCharArray()) {
			arrayLi.add(c);
		}
		BottomUpMaxHeap<Character> bbb = new BottomUpMaxHeap<>(arrayLi);
		MaxHeap<Character> aaa = new MaxHeap<>(arrayLi);
		System.out.println(bbb.getMax());
		System.out.println(bbb.getMax());
		System.out.println(bbb.getMax());
		System.out.println(bbb.getMax());
		System.out.println(bbb.getMax());
		System.out.println(bbb.getMax());
		System.out.println(bbb.getMax());
		bbb.insert('F');
		bbb.insert('D');
		bbb.insert('G');
		System.out.println(bbb.getMax());
		System.out.println(bbb.getMax());
		System.out.println(bbb.getMax());
		System.out.println(bbb.getMax());
		bbb.getLength();
	}
}
