package leetcode.leetcode_1;
/**
 * Given a binary array, find the maximum number of consecutive 1s in this
 * array.
 * 
 * Input: [1,1,0,1,1,1] Output: 3 Explanation: The first two digits or the last
 * three digits are consecutive 1s. The maximum number of consecutive 1s is 3.
 * 
 * @author 李嘉明
 * @2017-3-6
 */
public class MaxConsecutiveOnes_485 {
	public static void main(String[] args) {
		int[] nums = {1,1,0,1,1,1};
		System.out.println(findMaxConsecutiveOnes(nums));
	}

	public static int findMaxConsecutiveOnes(int[] nums) {
		int maximum = 0;
		int thisMaximum = 0;
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] == 1) {
				thisMaximum++;
			} else {
				thisMaximum = 0;
			}
			if (thisMaximum > maximum) {
				maximum = thisMaximum;
			}
		}
		return maximum;
	}
}
