package leetcode.leetcode_1;
import java.util.ArrayList;
import java.util.List;

/**
 * Write a program that outputs the string representation of numbers from 1 to
 * n.
 * 
 * But for multiples of three it should output “Fizz” instead of the number and
 * for the multiples of five output “Buzz”. For numbers which are multiples of
 * both three and five output “FizzBuzz”.
 * 
 * @author 李嘉明
 * @2017-2-28
 */
public class FizzBuzz_412 {
	public static void main(String[] args) {
		int i = 15;
		System.out.println(fizzBuzz(i));
	}

	/**
	 * 高效算法1,这个算法相当精妙。思路非常值得学习
	 * @param n
	 * @return
	 */
	public static List<String> fizzBuzz(int n) {
		List<String> ret = new ArrayList<String>(n);
		for (int i = 1, fizz = 0, buzz = 0; i <= n; i++) {
			fizz++;
			buzz++;
			if (fizz == 3 && buzz == 5) {
				ret.add("FizzBuzz");
				fizz = 0;
				buzz = 0;
			} else if (fizz == 3) {
				ret.add("Fizz");
				fizz = 0;
			} else if (buzz == 5) {
				ret.add("Buzz");
				buzz = 0;
			} else {
				ret.add(String.valueOf(i));
			}
		}
		return ret;
	}

	/**
	 * 高效算法2
	 * @param n
	 * @return
	 */
	public static List<String> fizzBuzz1(int n) {
		List<String> outPut = new ArrayList<String>();
		for (int i = 1; i <= n; i++) {
			if ((i % 5) == 0 && (i % 3) == 0) {
				outPut.add("FizzBuzz");
			} else if ((i % 5) == 0) {
				outPut.add("Buzz");
			} else if ((i % 3) == 0) {
				outPut.add("Fizz");
			} else {
				outPut.add(String.valueOf(i));
			}
		}
		return outPut;
	}
	
	/**
	 * 低效率算法
	 * @param n
	 * @return
	 */
	public static List<String> fizzBuzz2(int n) {
		List<String> outPut = new ArrayList<String>();
		for (int i = 0; i < n; i++) {
			String thisNumber = i + 1 + "";
			if ((i + 1) % 3 == 0) {
				thisNumber = "Fizz";
				if ((i + 1) % 5 == 0) {
					thisNumber += "Buzz";
				}
			} else if (((i + 1) % 5) == 0 && ((i + 1) % 3) != 0) {
				thisNumber = "Buzz";
			}
			outPut.add(thisNumber);
		}
		return outPut;
	}
}
