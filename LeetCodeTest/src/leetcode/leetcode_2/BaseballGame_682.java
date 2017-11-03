package leetcode.leetcode_2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * You're now a baseball game point recorder.
 * 
 * Given a list of strings, each string can be one of the 4 following types:
 * 
 * Integer (one round's score): Directly represents the number of points you get
 * in this round. <br>
 * 
 * "+" (one round's score): Represents that the points you get in this round are
 * the sum of the last two valid round's points.<br>
 * 
 * "D" (one round's score): Represents that the points you get in this round are
 * the doubled data of the last valid round's points. <br>
 * 
 * "C" (an operation, which isn't a round's score): Represents the last valid
 * round's points you get were invalid and should be removed. Each round's
 * operation is permanent and could have an impact on the round before and the
 * round after.
 * 
 * You need to return the sum of the points you could get in all the rounds.
 * 
 * Example 1: Input: ["5","2","C","D","+"] Output: 30 <br>
 * 
 * Explanation: Round 1: You could get 5 points. The sum is: 5. Round 2: You
 * could get 2 points. The sum is: 7. Operation 1: The round 2's data was
 * invalid. The sum is: 5. Round 3: You could get 10 points (the round 2's data
 * has been removed). The sum is: 15. Round 4: You could get 5 + 10 = 15 points.
 * The sum is: 30. <br>
 * 
 * Example 2: Input: ["5","-2","4","C","D","9","+","+"] Output: 27 <br>
 * 
 * Explanation: Round 1: You could get 5 points. The sum is: 5. Round 2: You
 * could get -2 points. The sum is: 3. Round 3: You could get 4 points. The sum
 * is: 7. Operation 1: The round 3's data is invalid. The sum is: 3. Round 4:
 * You could get -4 points (the round 3's data has been removed). The sum is:
 * -1. Round 5: You could get 9 points. The sum is: 8. Round 6: You could get -4
 * + 9 = 5 points. The sum is 13. Round 7: You could get 9 + 5 = 14 points. The
 * sum is 27. Note: The size of the input list will be between 1 and 1000. Every
 * integer represented in the list will be between -30000 and 30000.
 *
 */
public class BaseballGame_682 {
	public static void main(String[] args) {
		String[] ops = { "36", "28", "70", "65", "C", "+", "33", "-46", "84", "C" };
		System.out.println(calPoints(ops));
	}

	/**
	 * my way
	 */
	public static int calPoints(String[] ops) {
		List<Integer> res = new ArrayList<Integer>();
		for (String op : ops) {
			if (op.equals("+")) {
				res.add(res.get(res.size() - 1) + res.get(res.size() - 2));
			} else if (op.equals("D")) {
				res.add(res.get(res.size() - 1) * 2);
			} else if (op.equals("C")) {
				res.remove(res.size() - 1);
			} else {
				res.add(Integer.parseInt(op));
			}
		}
		int ress = 0;
		for (int e : res) {
			ress += e;
		}
		return ress;
	}

	public static int calPoints1(String[] ops) {
		int sum = 0;
		LinkedList<Integer> list = new LinkedList<>();
		for (String op : ops) {
			if (op.equals("C")) {
				sum -= list.removeLast();
			} else if (op.equals("D")) {
				list.add(list.peekLast() * 2);
				sum += list.peekLast();
			} else if (op.equals("+")) {
				list.add(list.peekLast() + list.get(list.size() - 2));
				sum += list.peekLast();
			} else {
				list.add(Integer.parseInt(op));
				sum += list.peekLast();
			}
		}
		return sum;
	}

	/**
	 * <strong>How to use stack</strong> <br>
	 * Let's maintain the value of each valid round on a stack as we process the
	 * data. A stack is ideal since we only deal with operations involving the
	 * last or second-last valid round.
	 */
	public static int calPoints2(String[] ops) {
		Stack<Integer> stack = new Stack<>();
		int ans = 0;

		for (String op : ops) {
			if (op.equals("C")) {
				ans -= stack.pop();
			} else if (op.equals("D")) {
				stack.push(2 * stack.peek());
				ans += stack.peek();
			} else if (op.equals("+")) {
				int top = stack.pop();
				int newtop = top + stack.peek();
				stack.push(top);
				stack.push(newtop);
				ans += stack.peek();
			} else {
				stack.push(Integer.valueOf(op));
				ans += stack.peek();
			}
		}
		return ans;
	}

}
