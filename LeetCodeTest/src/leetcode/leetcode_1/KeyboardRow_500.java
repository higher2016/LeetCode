package leetcode.leetcode_1;
import java.util.ArrayList;
import java.util.List;

/**
 * Given a List of words, return the words that can be typed using 
 * letters of alphabet on only one row's of American keyboard like the image below.
 * @author 李嘉明
 * @2017-2-28
 */
public class KeyboardRow_500 {
	public static void main(String[] args) {
		String[] words = { "Hello", "Alaska", "Dad", "Peace" };
		List<String> sd = new ArrayList<String>();
		List<Character> asa1 = new ArrayList<Character>();
		asa1.add('q');
		asa1.add('w');
		asa1.add('e');
		asa1.add('r');
		asa1.add('t');
		asa1.add('y');
		asa1.add('u');
		asa1.add('i');
		asa1.add('o');
		asa1.add('p');
		List<Character> asa2 = new ArrayList<Character>();
		asa2.add('a');
		asa2.add('s');
		asa2.add('d');
		asa2.add('f');
		asa2.add('g');
		asa2.add('h');
		asa2.add('j');
		asa2.add('k');
		asa2.add('l');
		List<Character> asa3 = new ArrayList<Character>();
		asa3.add('z');
		asa3.add('x');
		asa3.add('c');
		asa3.add('v');
		asa3.add('b');
		asa3.add('n');
		asa3.add('m');
		for (int i = 0; i < words.length; i++) {
			String thisWord = words[i];
			thisWord = thisWord.toLowerCase();
			char[] ss = thisWord.toCharArray();
			int a = 0;
			int b = 0;
			int c = 0;
			for (char s : ss) {
				if (asa3.contains(s)) {
					a = 1;
					continue;
				}
				if (asa2.contains(s)) {
					b = 1;
					continue;
				}
				if (asa1.contains(s)) {
					c = 1;
					continue;
				}
			}
			if ((a + b + c) == 1) {
				sd.add(words[i]);
			}
		}
		String[] out = new String[sd.size()];
		for (int i = 0; i < sd.size(); i++) {
			out[i] = sd.get(i);
		}
	}
}
