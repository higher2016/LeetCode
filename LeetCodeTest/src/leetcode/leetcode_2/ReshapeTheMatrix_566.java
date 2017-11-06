package leetcode.leetcode_2;

/**
 * In MATLAB, there is a very useful function called 'reshape', which can
 * reshape a matrix into a new one with different size but keep its original
 * data.
 * 
 * You're given a matrix represented by a two-dimensional array, and two
 * positive integers r and c representing the row number and column number of
 * the wanted reshaped matrix, respectively.
 * 
 * The reshaped matrix need to be filled with all the elements of the original
 * matrix in the same row-traversing order as they were.
 * 
 * If the 'reshape' operation with given parameters is possible and legal,
 * output the new reshaped matrix; Otherwise, output the original matrix.
 * 
 * Example 1: Input: nums = [[1,2], [3,4]] r = 1, c = 4 Output: [[1,2,3,4]]
 * Explanation: The row-traversing of nums is [1,2,3,4]. The new reshaped matrix
 * is a 1 * 4 matrix, fill it row by row by using the previous list. <br>
 * 
 * Example 2: Input: nums = [[1,2], [3,4]] r = 2, c = 4 Output: [[1,2], [3,4]] <br>
 * Explanation: There is no way to reshape a 2 * 2 matrix to a 2 * 4 matrix. So
 * output the original matrix. Note: The height and width of the given matrix is
 * in range [1, 100]. The given r and c are all positive.
 */
public class ReshapeTheMatrix_566 {
	public static void main(String[] args) {
		int[][] nums = { { 1, 2 }, { 3, 4 } };
		int[][] res = matrixReshape1(nums, 4, 1);
		System.out.println(res.length);
		System.out.println(res[0].length);
	}

	/**
	 * my way
	 */
	public static int[][] matrixReshape1(int[][] nums, int r, int c) {
		int numsRow = nums.length, numsColumn = nums[0].length;
		if (numsRow * numsColumn != r * c)
			return nums;

		int[][] res = new int[r][c];
		int fillNumInLine = 0;
		int nowRow = 0;

		for (int i = 0; i < numsRow; i++) {
			for (int j = 0; j < numsColumn; j++) {
				if (c - 1 >= fillNumInLine) {
					res[nowRow][fillNumInLine] = nums[i][j];
				} else {
					fillNumInLine = 0;
					res[++nowRow][fillNumInLine] = nums[i][j];
				}
				fillNumInLine++;
			}
		}
		return res;
	}

	/**
	 * Java Concise O(nm) time
	 */
	public static int[][] matrixReshape2(int[][] nums, int r, int c) {
		int n = nums.length, m = nums[0].length;
		if (r * c != n * m)
			return nums;
		int[][] res = new int[r][c];
		for (int i = 0; i < r * c; i++)
			res[i / c][i % c] = nums[i / m][i % m];
		return res;
	}
}
