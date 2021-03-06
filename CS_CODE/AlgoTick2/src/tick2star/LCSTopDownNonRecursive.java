package tick2star;

import java.util.Stack;

public class LCSTopDownNonRecursive extends LCSFinder {

	public LCSTopDownNonRecursive(String s1, String s2) {
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
		getLCSLength();
	}

	private class recInfo {
		public final int i;
		public final int j;
		public final boolean m;

		public recInfo(int x, int y, boolean z) {
			i = x;
			j = y;
			m = z;
		}
	}

	@Override
	public int getLCSLength() {
		if (mTable == null) {
			return 0;
		}
		Stack<recInfo> stk = new Stack<>();
		int len1 = mString1.length();
		int len2 = mString2.length();
		if (mString1.charAt(len1 - 1) == mString2.charAt(len2 - 1)) {
			stk.push(new recInfo(len1 - 1, len2 - 1, true));
		} else {
			stk.push(new recInfo(len1 - 1, len2 - 1, false));
		}

		recInfo tempRecInfo;
		int i;
		int j;
		while (!stk.isEmpty()) {
			tempRecInfo = stk.peek();
			i = tempRecInfo.i;
			j = tempRecInfo.j;

			if (tempRecInfo.m) {
				if (i == 0 || j == 0) {
					mTable[i][j] = 1;
					stk.pop();
				} else if (mTable[i - 1][j - 1] == -1) {
					stk.push(new recInfo(i - 1, j - 1, mString1.charAt(i - 1) == mString2.charAt(j - 1)));
				} else {
					mTable[i][j] = mTable[i - 1][j - 1] + 1;
					stk.pop();
				}
			} else {
				if (i == 0) {
					if (j == 0) {
						mTable[i][j] = 0;
						stk.pop();
					} else {
						if (mTable[i][j - 1] == -1) {
							stk.push(new recInfo(i, j - 1, mString1.charAt(i) == mString2.charAt(j - 1)));
						} else {
							mTable[i][j] = mTable[i][j - 1];
							stk.pop();
						}
					}
				} else if (j == 0) {
					if (mTable[i - 1][j] == -1) {
						stk.push(new recInfo(i - 1, j, mString1.charAt(i - 1) == mString2.charAt(j)));
					} else {
						mTable[i][j] = mTable[i - 1][j];
						stk.pop();
					}
				} else {
					if (mTable[i - 1][j] == -1) {
						stk.push(new recInfo(i - 1, j, mString1.charAt(i - 1) == mString2.charAt(j)));
					} else if (mTable[i][j - 1] == -1) {
						stk.push(new recInfo(i, j - 1, mString1.charAt(i) == mString2.charAt(j - 1)));
					} else {
						mTable[i][j] = Math.max(mTable[i - 1][j], mTable[i][j - 1]);
						stk.pop();
					}
				}
			}
		}
		return mTable[len1 - 1][len2 - 1];
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

	public static void main(String args[]) {
		LCSTopDownNonRecursive ins = new LCSTopDownNonRecursive("", "");
		System.out.println(ins.getLCSLength());
		System.out.println(ins.getLCSString());
	}

}
