package test;

import java.util.LinkedList;

public class PlayGround {

	// public boolean equals(PlayGround p2) {
	// return this.x == p2.x;
	// }
	public static void main(String args[]) {
		UpdatableTreeSet treeSet = new UpdatableTreeSet();
		SubscribableStudentInfo info = new SubscribableStudentInfo("Joe");
		treeSet.add(info);
		info.setCompleted(100);
		LinkedList<Object> list = new LinkedList<>();
		Integer aInteger = 1;
		list.add(aInteger);
		aInteger = 2;
		int[] arr = new int[1];
		int i = arr.length;
	}

	public static Object test() {
		return new Integer(1);
	}
}
