package leetcode.leetcode_1;


/**
 * You are given a map in form of a two-dimensional integer grid where 1 represents land and 0 represents water. 
 * Grid cells are connected horizontally/vertically (not diagonally). The grid is completely surrounded by water,
 *  and there is exactly one island (i.e., one or more connected land cells). 
 *  The island doesn't have "lakes" (water inside that isn't connected to the water around the island). 
 *  One cell is a square with side length 1. The grid is rectangular, width and height don't exceed 100. 
 *  Determine the perimeter of the island.
 * @author 李嘉明
 * @2017-3-3
 */
public class IslandPerimeter_463 {
	public static void main(String[] args) {
		int[][] grid = { { 0, 1, 0, 0 }, { 1, 1, 1, 0 }, { 0, 1, 0, 0 }, { 1, 1, 0, 0 } };
		System.out.println(islandPerimeter(grid));
	}
	
	/**
	 * 解题思路：
	 * 1、凡是岛在数组边缘的（接壤上边缘，接壤下边缘，接壤左边缘，接壤右边缘），都做加一处理（因为边缘都是水） 
	 * 2、凡是这个岛右边或者下面是水，都做加一处理
	 * 3、凡是这个湖右边或者下面是岛，都做加一处理
	 * @param grid
	 * @return
	 */
	public static int islandPerimeter(int[][] grid) {
		int hang = grid.length - 1;// 行
		int lie = grid[0].length - 1;// 列
		int perimeter = 0;
		for (int i = 0; i < hang + 1; i++) {
			for (int j = 0; j < lie + 1; j++) {
				if(j == 0 && grid[i][j] == 1){
					perimeter++;//接壤左边缘
				}
				if(j == lie && grid[i][j] == 1){
					perimeter++;//接壤右边缘
				}
				if(i == 0 && grid[i][j] == 1){
					perimeter++;//接壤上边缘
				}
				if(i == hang && grid[i][j] == 1){
					perimeter++;//接壤下边缘
				}
				if (j <= lie - 1 && (grid[i][j] != grid[i][j + 1])) {
					perimeter++;
				}
				if (i <= hang - 1 && (grid[i][j] != grid[i + 1][j])) {
					perimeter++;
				}
			}
		}
		return perimeter;
	}
}
