package tick2star;

public class LCSTopDownRecursive extends LCSFinder {

	public LCSTopDownRecursive(String s1, String s2) {
		super(s1, s2);
		if (mString1 == "" || mString2 == "") {
			mTable = null;
			return;
		}
		mTable = new int[s1.length()][s2.length()];
		for (int[] is : mTable) {
			for (int i = 0; i < s2.length(); ++i) {
				is[i] = -1;
			}
		}

		LCS(mString1.length() - 1, mString2.length() - 1);
	}

	@Override
	public int getLCSLength() {
		if (mTable == null) {
			return 0;
		}
		return mTable[mString1.length() - 1][mString2.length() - 1];
	}

	@Override
	public String getLCSString() {
		if (mTable == null) {
			return "";
		}
		String rlcs = "";
		String lcs = "";
		int i = mString1.length() - 1;
		int j = mString2.length() - 1;

		while (i != 0 && j != 0) {
			if (i > 0 && mTable[i][j] == mTable[i - 1][j]) {
				--i;
			} else if (j > 0 && mTable[i][j] == mTable[i][j - 1]) {
				--j;
			} else {
				rlcs += mString1.charAt(i);
				--i;
				--j;
			}
		}
		if (i == 0) {
			while (j > 0 && mTable[i][j - 1] == 1) {
				--j;
			}
		} else {
			while (i > 0 && mTable[i - 1][j] == 1) {
				--i;
			}
		}

		if (mTable[i][j] == 1) {
			rlcs += mString1.charAt(i);
		}

		for (i = rlcs.length() - 1; i >= 0; --i) {
			lcs += rlcs.charAt(i);
		}
		return lcs;
	}

	private int LCS(int end1, int end2) {
		if (end1 == -1 || end2 == -1) {
			return 0;
		}
		if (mTable[end1][end2] != -1) {
			return mTable[end1][end2];
		}

		if (mString1.charAt(end1) == mString2.charAt(end2)) {
			mTable[end1][end2] = 1 + LCS(end1 - 1, end2 - 1);
			return mTable[end1][end2];
		} else {
			mTable[end1][end2] = Math.max(LCS(end1 - 1, end2), LCS(end1, end2 - 1));
			return mTable[end1][end2];
		}
	}

	public static void main(String args[]) {
		LCSTopDownRecursive ins = new LCSTopDownRecursive("axaxaxaxaxax", "aaaaab");
		System.out.println(ins.getLCSLength());
		System.out.println(ins.getLCSString());

	}
}
