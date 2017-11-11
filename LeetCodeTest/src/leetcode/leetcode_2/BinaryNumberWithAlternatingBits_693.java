package leetcode.leetcode_2;

/**
 * Given a positive integer, check whether it has alternating bits: namely, if
 * two adjacent bits will always have different values.
 * 
 * Example 1: Input: 5 <br>
 * Output: True Explanation: The binary representation of 5 is: 101 <br>
 * 
 * Example 2: Input: 7 Output: False <br>
 * Explanation: The binary representation of 7 is: 111. <br>
 * 
 * Example 3: Input: 11 Output: False <br>
 * Explanation: The binary representation of 11 is: 1011. <br>
 * 
 * Example 4: Input: 10 Output: True<br>
 * Explanation: The binary representation of 10 is: 1010.
 *
 */
public class BinaryNumberWithAlternatingBits_693 {
	public static void main(String[] args) {
		System.out.println(hasAlternatingBits(0));
		System.out.println(hasAlternatingBits(1));
		System.out.println(hasAlternatingBits(2));
		System.out.println(hasAlternatingBits(5));
		System.out.println(hasAlternatingBits(7));
		System.out.println(hasAlternatingBits(10));
	}

	/**
	 * My way
	 */
	public static boolean hasAlternatingBits(int n) {
		String bin = Integer.toBinaryString(n);
		char lastChar = 'x';
		for (char c : bin.toCharArray()) {
			if (lastChar == c) {
				return false;
			}
			lastChar = c;
		}
		return true;
	}

	public static boolean hasAlternatingBits1(int n) {
		return (((long) n + (n >> 1) + 1) & ((long) n + (n >> 1))) == 0;
	}
}
