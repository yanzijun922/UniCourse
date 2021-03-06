package tick2;

public class ArrayWorld extends World {
	private boolean[][] mWorld;

	public ArrayWorld(String serial) throws PatternFormatException {
		super(serial);
		// TODO: initialise mWorld
		mWorld = new boolean[getHeight()][getWidth()];

		getPattern().initialise(this);
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

	}

	@Override
	public boolean getCell(int x, int y) {
		// TODO Auto-generated method stub
		if (y < 0 || y >= getHeight())
			return false;
		if (x < 0 || x >= getWidth())
			return false;

		return mWorld[y][x];
	}

	@Override
	public void setCell(int x, int y, boolean val) {
		// TODO Auto-generated method stub
		mWorld[y][x] = val;
	}

}
