package tick3star;

public class Pair<A, B> {
	private A first;
	private B second;

	public Pair(A f, B s) {
		first = f;
		second = s;
	}

	public A first() {
		return first;
	}

	public B second() {
		return second;
	}

	public void setFirst(A f) {
		first = f;
	}

	public void setSecond(B s) {
		second = s;
	}

}
