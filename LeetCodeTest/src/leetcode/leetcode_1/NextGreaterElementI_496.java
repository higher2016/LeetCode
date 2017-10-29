package leetcode.leetcode_1;
import java.util.HashMap;
import java.util.Map;

/**
 * You are given two arrays (without duplicates) nums1 and nums2 where nums1’s
 * elements are subset of nums2. Find all the next greater numbers for nums1's
 * elements in the corresponding places of nums2.
 * 
 * The Next Greater Number of a number x in nums1 is the first greater number to
 * its right in nums2. If it does not exist, output -1 for this number.
 * Example:Input: nums1 = [4,1,2], nums2 = [1,3,4,2].Output: [-1,3,-1]
 * 
 * 
 * 这道题给了我们一个数组，又给了该数组的一个子集合，让我们求集合中每个数字在原数组中右边第一个较大的数字(如果右边所有的数都不比这个数大，那么就为-1，
 * 否则就是这个这个比较大的数)。
 * 
 * @author 李嘉明
 * @2017-2-28
 */
public class NextGreaterElementI_496 {
	public static void main(String[] args) {
		int[] findNums = { 1, 3, 5, 2, 4 };
		int[] nums = { 6, 5, 4, 3, 2, 1, 7 };
		int[] r = nextGreaterElement(findNums, nums);
		for (int i = 0; i < r.length; i++) {
			System.out.println(r[i]);
		}
	}

	/**
	 * 高效算法1
	 * @param findNums
	 * @param nums
	 * @return
	 *///一开始就可以确定数组的长度，就直接建立数组。不要用List，这样就可少一步List转int[]
	public static int[] nextGreaterElement(int[] findNums, int[] nums) {
		int[] outPut = new int[findNums.length];
		Map<Integer, Integer> numsMap = new HashMap<Integer, Integer>();
		for (int i = 0; i < nums.length; i++) {
			numsMap.put(nums[i], i);
		}
		int i = 0;
		for (int findNum : findNums) {
			int parentSetNextNumO = numsMap.get(findNum) + 1;
			int nextMax = -1;
			for (int j = parentSetNextNumO; j < nums.length; j++) {
				if (nums[j] > findNum) {
					nextMax = nums[j];
					break;
				}
			}
			outPut[i] = nextMax;
			i++;
		}
		return outPut;
	}
	
	

	public static int[] nextGreaterElement1(int[] findNums, int[] nums) {
		Map<Integer, Integer> valToIndex = new HashMap<Integer, Integer>();
		for (int i = 0; i < nums.length; i++) {
			valToIndex.put(nums[i], i);
		}

		int[] result = new int[findNums.length];
		for (int i = 0; i < findNums.length; i++) {
			result[i] = -1;
			int start = valToIndex.get(findNums[i]);

			for (int j = start + 1; j < nums.length; j++) {
				if (nums[j] > findNums[i]) {
					result[i] = nums[j];
					break;
				}
			}
		}
		return result;
	}
}
