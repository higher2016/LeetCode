package IO;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 将控制台输入（标准输入），显示到控制台（标准输出)。中间的过程靠输入流衔接
 * 
 * @author 李嘉明
 * @2017-3-15
 */
public class BufferedReaderTest {
	public static void main(String[] args) {
		char ch;
		// 将控制台输入（System.in）转换为输入流（字符流：InputStreamReader（基于字符的输入操作））
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		// InputStreamReader in = new InputStreamReader(System.in);
		try {
			while ((ch = (char) in.read()) != -1) {
				System.out.print(ch);
			}
		} catch (Exception e) {
		}
	}
}
