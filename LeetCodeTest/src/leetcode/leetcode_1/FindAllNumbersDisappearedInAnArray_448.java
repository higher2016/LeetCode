package leetcode.leetcode_1;
import java.util.ArrayList;
import java.util.List;

/**
 * Given an array of integers where 1 ≤ a[i] ≤ n (n = size of array), some
 * elements appear twice and others appear once. Find all the elements of [1, n]
 * inclusive that do not appear in this array. Could you do it without extra
 * space and in O(n) runtime? You may assume the returned list does not count as
 * extra space.
 * 
 * Example: Input: [4,3,2,7,8,2,3,1] Output: [5,6]
 * 
 * @author 李嘉明
 * @2017-3-9
 */
public class FindAllNumbersDisappearedInAnArray_448 {
	public static void main(String[] args) {
		int[] s = {4,3,2,7,8,2,3,1};
		System.out.println(findDisappearedNumbers(s));
	}

	public static List<Integer> findDisappearedNumbers(int[] nums) {
		   List<Integer> res = new ArrayList<Integer>();
	        int n = nums.length;
	        for (int i = 0; i < nums.length; i ++) nums[(nums[i]-1) % n] += n;
	        for (int i = 0; i < nums.length; i ++) if (nums[i] <= n) res.add(i+1);
	        return res;
	}
}
