package leetcode.leetcode_1;
/**
 * You are playing the following Nim Game with your friend: There is a heap of
 * stones on the table, each time one of you take turns to remove 1 to 3 stones.
 * The one who removes the last stone will be the winner. You will take the
 * first turn to remove the stones.
 * 
 * Both of you are very clever and have optimal strategies for the game. Write a
 * function to determine whether you can win the game given the number of stones
 * in the heap.
 * 
 * For example, if there are 4 stones in the heap, then you will never win the
 * game: no matter 1, 2, or 3 stones you remove, the last stone will always be
 * removed by your friend.
 * 
 * @author 李嘉明
 * @2017-3-7
 */
public class NimGame_292 {
	public static void main(String[] args) {

	}

	/**
	 * 高效算法 解释：只要自己面临4的倍数的情况就有可能会输（前提是对方也知道处理方式），否则其它情况下通过自己合理的处理一定会赢
	 * 
	 * @param n
	 * @return
	 */
	public static boolean canWinNim(int n) {
		return !(n % 4 == 0);
	}
}
