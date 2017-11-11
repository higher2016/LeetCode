package leetcode.leetcode_2;

/**
 * Given a non-empty 2D array grid of 0's and 1's, an island is a group of 1's
 * (representing land) connected 4-directionally (horizontal or vertical.) You
 * may assume all four edges of the grid are surrounded by water.
 * 
 * Find the maximum area of an island in the given 2D array. (If there is no
 * island, the maximum area is 0.)
 * 
 * Example 1: <br>
 * [[0,0,1,0,0,0,0,1,0,0,0,0,0], <br>
 * [0,0,0,0,0,0,0,1,1,1,0,0,0], <br>
 * [0,1,1,0,1,0,0,0,0,0,0,0,0], <br>
 * [0,1,0,0,1,1,0,0,1,0,1,0,0], <br>
 * [0,1,0,0,1,1,0,0,1,1,1,0,0], <br>
 * [0,0,0,0,0,0,0,0,0,0,1,0,0], <br>
 * [0,0,0,0,0,0,0,1,1,1,0,0,0], <br>
 * [0,0,0,0,0,0,0,1,1,0,0,0,0]] <br>
 * Given the above grid, return 6.<br>
 * Note the answer is not 11, because the island must be connected
 * 4-directionally.
 * 
 * Example 2: [[0,0,0,0,0,0,0,0]] Given the above grid, return 0.
 * 
 * Note: The length of each dimension in the given grid does not exceed 50.
 *
 */
public class MaxAreaOfIsland_695 {
	public static void main(String[] args) {
		int[][] grid = { { 1, 0 }, { 1, 1 } };
		// int[][] grid = { { 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 }, { 0, 0,
		// 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0 }, { 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0,
		// 0, 0 }, { 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0 },
		// { 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0,
		// 0, 0, 1, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0 }, { 0, 0,
		// 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0 } };
		System.out.println(maxAreaOfIsland(grid));
	}

	public static int maxAreaOfIsland(int[][] grid) {
		int lie = grid[0].length;
		int hang = grid.length;
		int res = 1;
		int realRes = 0;
		for (int i = 0; i < hang; i++) {
			for (int j = 0; j < lie; j++) {
				if (grid[i][j] == 1) {
					boolean isRight = j < lie - 1 ? (grid[i][j + 1] == 1) : false;
					boolean isLow = i < hang - 1 ? (grid[i + 1][j] == 1) : false;
					if (isRight) {
						res++;
					}
					if (isLow) {
						res++;
					}
					if (!isRight && !isLow) {
						if (realRes < res) {
							realRes = res;
							res = 0;
						}
					}
				}
			}
		}
		return Math.max(res, realRes);
	}
}
