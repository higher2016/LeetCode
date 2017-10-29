package leetcode.leetcode_1;

/**
 * Given a word, you need to judge whether the usage of capitals in it is right or not.
 * We define the usage of capitals in a word to be right when one of the following cases holds:
 * 		1.All letters in this word are capitals, like "USA".
 * 		2.All letters in this word are not capitals, like "leetcode".
 * 		3.Only the first letter in this word is capital if it has more than one letter, like "Google".
 * Otherwise, we define that this word doesn't use capitals in a right way.
 * 
 * Example 1:
 * Input: "USA"
 * Output: True
 * 
 * Example 2:
 * Input: "FlaG"
 * Output: False
 * @author 李嘉明
 * @2017-3-8
 */
public class DetectCapital_520 {
	public static void main(String[] args) {
		System.out.println(detectCapitalUse("UsA"));
	}

	/**
	 * 效率比较高（LeetCode执行为32ms）
	 * @param word
	 * @return
	 */
	public static boolean detectCapitalUse(String word) {
		char[] wordChats = word.toCharArray();
		if(word.equals(word.toUpperCase())){
			return true;//全大写
		}
		
		boolean result = true;
		for (int i = 1; i < wordChats.length; i++) {
			if(!Character.isLowerCase(wordChats[i])){
				result = false;
				break;
			}
		}
		return result;
	}
	
	/**
	 * 使用通配符效率比较低(LeetCode执行为64ms)
	 * @param word
	 * @return
	 */
	public static boolean detectCapitalUse1(String word) {
		return word.matches("[A-Z]*|[A-Z]?[a-z]*");
	}
}
