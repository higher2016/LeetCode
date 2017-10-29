package leetcode.leetcode_1;
/**
 * Write a function that takes a string as input and returns the string reversed.
 * Example:
 * Given s = "hello", return "olleh".
 * @author 李嘉明
 * @2017-3-2
 */
public class ReverseString_344 {
	public static void main(String[] args) {
		String s = "aswadaefawefawefawefweaaafefwadswergebvsdsaeaaaaaaaaaawwwwwww" +
				"wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwaaaaaaaaaaaaaaaaaaa" +
				"aaaaaawwwwwwwwwwwwwwwwwwwwwefqfregqagrfuegruighbvdsaiuhiuerfbadv";
		long sta = System.currentTimeMillis();
		for(int i=0;i<1000;i++){
			System.out.println(reverseString(s));
		}
		long end = System.currentTimeMillis();
		System.out.println(end - sta);
	}

	public static String reverseString(String s) {
		char[] sChar = s.toCharArray();
		int le = sChar.length;
		char[] reSChar = new char[le];
		for (int i = 0, j = le - 1; i < le; i++, j--) {
			reSChar[j] = sChar[i];
		}
		return String.valueOf(reSChar);
	}
	
	/**
	 * 理论上这个算法会比上面的算法快，因为这个算法只会遍历数组的一半。
	 * 而上面却会将整个数组完全遍历（并且还会创建多一个新的数组对象）。
	 * 但在实际测试中，两个算法运算时间差不多
	 * @param s
	 * @return
	 */
	public static String reverseString1(String s) {
        char[] word = s.toCharArray();
        int i = 0;
        int j = s.length() - 1;
        while (i < j) {
            char temp = word[i];
            //按照对称原则将数组的元素进行调换（如果数组有7个元素那么调换顺序为 0--6；1--5；2--4）
            //上面的算法要这样（数组1（ c1[6]--c2[0];  c1[5]--c2[1];
            //						c1[4]--c2[2];  c1[3]--c2[3];
            //						c1[2]--c2[4];  c1[1]--c2[5];  
            //						c1[0]--c2[6]））在时间长度上是这个算法的两倍
            word[i] = word[j];
            word[j] = temp;
            i++;
            j--;
        }
        return String.valueOf(word);
    }
}
