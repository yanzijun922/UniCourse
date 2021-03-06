package tick5;

public class ArrayWorld extends World implements Cloneable {
	private boolean[][] mWorld;
	private boolean[] mDeadRow;

	private static boolean onlyContainsFalse(boolean[] bs) {
		for (boolean b : bs) {
			if (b) {
				return false;
			}
		}
		return true;
	}

	public ArrayWorld(String serial) throws PatternFormatException {
		super(serial);
		mDeadRow = new boolean[this.getWidth()];
		mWorld = new boolean[getHeight()][getWidth()];

		getPattern().initialise(this);
		for (int i = 0; i < mWorld.length; ++i) {
			if (onlyContainsFalse(mWorld[i])) {
				mWorld[i] = mDeadRow;
			}
		}
	}

	public ArrayWorld(Pattern serial) throws PatternFormatException {
		super(serial);
		mDeadRow = new boolean[this.getWidth()];
		mWorld = new boolean[getHeight()][getWidth()];

		getPattern().initialise(this);
		for (int i = 0; i < mWorld.length; ++i) {
			if (onlyContainsFalse(mWorld[i])) {
				mWorld[i] = mDeadRow;
			}
		}
	}

	public ArrayWorld(ArrayWorld w) {
		super(w);
		mDeadRow = w.mDeadRow;
		mWorld = w.mWorld.clone();

		for (int i = 0; i < mWorld.length; ++i) {
			if (onlyContainsFalse(mWorld[i])) {
				mWorld[i] = mDeadRow;
			} else {
				mWorld[i] = mWorld[i].clone();
			}
		}
	}

	@Override
	public ArrayWorld clone() throws CloneNotSupportedException {
		ArrayWorld n = (ArrayWorld) super.clone();
		n.mWorld = this.mWorld.clone();

		n.mDeadRow = this.mDeadRow;
		for (int i = 0; i < n.mWorld.length; ++i) {
			if (onlyContainsFalse(mWorld[i])) {
				n.mWorld[i] = mDeadRow;
			} else {
				n.mWorld[i] = mWorld[i].clone();
			}
		}
		return n;
	}

	@Override
	protected void nextGenerationimpl() {
		// TODO Auto-generated method stub
		boolean[][] nextGeneration = new boolean[mWorld.length][];
		for (int y = 0; y < mWorld.length; ++y) {
			nextGeneration[y] = new boolean[mWorld[y].length];
			for (int x = 0; x < mWorld[y].length; ++x) {
				boolean nextCell = computeCell(x, y);
				nextGeneration[y][x] = nextCell;
			}
		}
		mWorld = nextGeneration;
		for (boolean[] b : mWorld) {
			if (onlyContainsFalse(b)) {
				b = mDeadRow;
			}
		}
	}

	@Override
	public boolean getCell(int x, int y) {
		if (y < 0 || y >= getHeight())
			return false;
		if (x < 0 || x >= getWidth())
			return false;

		return mWorld[y][x];
	}

	@Override
	public void setCell(int x, int y, boolean val) {
		mWorld[y][x] = val;
	}

}
