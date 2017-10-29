package leetcode.leetcode_1;
/**
 * Given an array of integers, every element appears twice except for one. Find
 * that single one.
 * 
 * Note: Your algorithm should have a linear runtime complexity. Could you
 * implement it without using extra memory?
 * 
 * @author 李嘉明
 * @2017-3-8
 */
public class SnippetNumber_136 {
	public static void main(String[] args) {
		int[] nums = {1,1,2,3,4,4,5,5,6,2,6};
		System.out.println(singleNumber(nums));
	}
	
	/**
	 * 使用位运算高效解决问题
	 * we use bitwise XOR to solve this problem :
	 * first , we have to know the bitwise XOR in java
	 * 0 ^ N = N
	 * N ^ N = 0
	 * So..... if N is the single number
	 * N1 ^ N1 ^ N2 ^ N2 ^..............^ Nx ^ Nx ^ N
	 * = (N1^N1) ^ (N2^N2) ^..............^ (Nx^Nx) ^ N
	 * = 0 ^ 0 ^ ..........^ 0 ^ N
	 * = N
	 * 
	 * @param nums
	 * @return
	 */
	public static int singleNumber(int[] nums) {
		int result = 0;
	    for(int i : nums) {
	        result ^= i;
	    }
	    return result;
	}
}