package leetcode.leetcode_2;


/**
 * 
 * Give a string s, count the number of non-empty (contiguous) substrings that
 * have the same number of 0's and 1's, and all the 0's and all the 1's in these
 * substrings are grouped consecutively.<br>
 * 
 * Substrings that occur multiple times are counted the number of times they
 * occur.
 * 
 * Example 1: Input: "00110011" Output: 6 <br>
 * Explanation: There are 6 substrings that have equal number of consecutive 1's
 * and 0's: "0011", "01", "1100", "10", "0011", and "01".
 * 
 * Notice that some of these substrings repeat and are counted the number of
 * times they occur.
 * 
 * Also, "00110011" is not a valid substring because all the 0's (and 1's) are
 * not grouped together. Example 2: Input: "10101" Output: 4 Explanation: There
 * are 4 substrings: "10", "01", "10", "01" that have equal number of
 * consecutive 1's and 0's. <br>
 * 
 * Note: s.length will be between 1 and 50,000. s will only consist of "0" or
 * "1" characters.
 *
 */
public class CountBinarySubstrings696 {
	public static void main(String[] args) {
		// int s = 5/2;
		// System.out.println(s);
		int res = countBinarySubstrings("00110011");
		System.out.println(res);
		// for(int j = 0 + 1; j < 6; j += 2){
		// System.out.println(j);
		// }
	}

	public static int countBinarySubstrings2(String s) {
		int res = 0;
		int prv = 0;
		int cur = 0;
		for (int i = 0; i < s.length(); i++) {
			cur++;
			if (i == s.length() - 1 || s.charAt(i) != s.charAt(i + 1)) {
				res += Math.min(prv, cur);
				prv = cur;
				cur = 0;
			}
		}
		return res;
	}

	/**
	 * 本题是给定一个01二进制字符串，看有多少个包含0和1数目相同的子字符串，而且要求是连续的0和1，也就是说像0101这样的不行，因为两个0不是连续的
	 * 建模是这道题的难点
	 */
	public static int countBinarySubstrings1(String s) {
		// 这道题建模的关键在于：其实可以把字符串s转化为0和1的堆。
		// 譬如，“1111000011010001011”转化为“4 4 2 1 1 3 1 1 2”，
		// 也就是统计一下每个连续子串元素的个数。上面例子的堆1和堆2，首先能确定的就是每个堆的值是不一样的（0堆或1堆），
		// 第二是前后堆子字串元素个数最小值就是这两个堆包含0和1数目相同且0和1连续的子字符串的最大值。所以只要把堆连续求解求和就可以得出答案
		int prvCount = 0, curCount = 0, res = 0;
		for (int i = 0; i < s.length(); i++) {
			curCount++;
			if (i == s.length() - 1 || s.charAt(i) != s.charAt(i + 1)) {
				res += Math.min(prvCount, curCount);
				prvCount = curCount;
				curCount = 0;
			}
		}
		return res;
	}

	public static int countBinarySubstrings(String s) {
		byte[] sChars = s.getBytes();
		int res = 0;
		for (int i = 0; i < sChars.length - 1; i++) {
			for (int j = i + 1; j < sChars.length; j += 2) {
				byte[] copy = new byte[j - i + 1];
				System.arraycopy(sChars, i, copy, 0, j - i + 1);
				if (isCount(copy)) {
					res++;
				}
			}
		}
		return res;
	}

	private static boolean isCount(byte[] chars) {
		int middle = chars.length / 2;
		if (chars[0] == chars[middle]) {
			return false;
		}
		byte lastByte = chars[0];
		for (int i = 0; i < middle; i++) {
			if (lastByte != chars[i]) {
				return false;
			}
		}
		lastByte = chars[middle];
		for (int i = middle; i < chars.length; i++) {
			if (lastByte != chars[i]) {
				return false;
			}
		}
		return true;
	}
}
