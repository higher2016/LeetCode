package leetcode.leetcode_2;

import java.util.StringTokenizer;

/**
 * Given a string, you need to reverse the order of characters in each word
 * within a sentence while still preserving whitespace and initial word order.
 * 
 * Example 1: Input: "Let's take LeetCode contest" <br>
 * Output: "s'teL ekat edoCteeL tsetnoc" <br>
 * Note: In the string, each word is separated by single space and there will
 * not be any extra space in the string.
 * 
 * @author JM
 */
public class ReverseWordsInAStringIII_557 {
	public static void main(String[] args) {
		System.out.println(reverseWords("Let's take LeetCode contest"));
	}

	/**
	 * My way. use StringTokenizer to split every word and use StringBuilder
	 * reverse() to reverse word
	 */
	private static String reverseWords(String s) {
		StringBuilder result = new StringBuilder();
		StringTokenizer tokenizer = new StringTokenizer(s, " ");
		while (tokenizer.hasMoreTokens()) {
			String word = tokenizer.nextToken();
			if (tokenizer.hasMoreTokens()) {
				result.append(new StringBuilder(word).reverse().toString()).append(' ');
			} else {
				result.append(new StringBuilder(word).reverse().toString());
			}
		}
		return result.toString();
	}

	@SuppressWarnings("unused")
	private static String reverseWords2(String s) {
		StringBuilder result = new StringBuilder();
		String[] words = s.split(" ");
		int wordsLength = words.length - 1;
		for (int i = 0; i < words.length; i++) {
			if (i != wordsLength) {
				result.append(new StringBuilder(words[i]).reverse().toString()).append(' ');
			} else {
				result.append(new StringBuilder(words[i]).reverse().toString());
			}
		}
		return result.toString();
	}
	
	/**
	 * 这算法效率最高，如果写基础组件可以考虑。但是写功能就不建议（不好看）。
	 */
    public static String reverseWords3(String s) {
        char[] ca = s.toCharArray();
        for (int i = 0; i < ca.length; i++) {
            if (ca[i] != ' ') {   // when i is a non-space
                int j = i;
                while (j + 1 < ca.length && ca[j + 1] != ' ') { j++; } // move j to the end of the word
                reverse(ca, i, j);
                i = j;
            }
        }
        return new String(ca);
    }

    private static void reverse(char[] ca, int i, int j) {
        for (; i < j; i++, j--) {
            char tmp = ca[i];
            ca[i] = ca[j];
            ca[j] = tmp;
        }
    }
}
