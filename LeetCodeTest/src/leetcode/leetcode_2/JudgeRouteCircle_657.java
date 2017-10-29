package leetcode.leetcode_2;

/**
 * Initially, there is a Robot at position (0, 0). Given a sequence of its
 * moves, judge if this robot makes a circle, which means it moves back to the
 * original place. The move sequence is represented by a string. And each move
 * is represent by a character. The valid robot moves are R (Right), L (Left), U
 * (Up) and D (down). The output should be true or false representing whether
 * the robot makes a circle. <br>
 * Example 1: Input: "UD" Output: true<br>
 * Example 2: Input: "LL" Output: false<br>
 */
public class JudgeRouteCircle_657 {
	public static void main(String[] args) {
		System.out.println(judgeRouteCircle1("UDLR"));
	}

	// my method
	private static boolean judgeRouteCircle1(String moves) {
		String str = moves.toUpperCase();
		int upOrDownTotal = 0;
		int leftOrRigrtTotal = 0;
		for (char c : str.toCharArray()) {
			if (c == 'U') {
				upOrDownTotal++;
			}
			if (c == 'D') {
				upOrDownTotal--;
			}
			if (c == 'L') {
				leftOrRigrtTotal++;
			}
			if (c == 'R') {
				leftOrRigrtTotal--;
			}
		}
		return upOrDownTotal == 0 && leftOrRigrtTotal == 0;
	}
}
