package tick2star;

public class LCSBottomUp extends LCSFinder {

	public LCSBottomUp(String s1, String s2) {
		super(s1, s2);
		if (mString1 == "" || mString2 == "") {
			mTable = null;
			return;
		}
		mTable = new int[s1.length()][s2.length()];
		getLCSLengthTable();
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

	public int[][] getLCSLengthTable() {
		if (mTable == null) {
			return null;
		}
		int len1 = mString1.length();
		int len2 = mString2.length();
		boolean flag = false;
		for (int i = 0; i < len1; ++i) {
			if (flag || mString1.charAt(i) == mString2.charAt(0)) {
				mTable[i][0] = 1;
				flag = true;
			}
		}
		flag = false;
		for (int j = 0; j < len2; ++j) {
			if (flag || mString1.charAt(0) == mString2.charAt(j)) {
				mTable[0][j] = 1;
				flag = true;
			}
		}

		for (int i = 1; i < len1; ++i) {
			for (int j = 1; j < len2; ++j) {
				if (mString1.charAt(i) == mString2.charAt(j)) {
					mTable[i][j] = mTable[i - 1][j - 1] + 1;
				} else {
					mTable[i][j] = Math.max(mTable[i][j - 1], mTable[i - 1][j]);
				}
			}
		}

		return mTable;
	}
}
