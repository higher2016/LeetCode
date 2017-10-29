package IO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 通过输入流（FileReader）读取文件中的数据
 * @author 李嘉明
 * @2017-3-15
 */
public class FileReaderTest {
	public static void main(String[] args) throws FileNotFoundException {
		FileReader in = new FileReader("F:/BufferedReaderTest.java");
		BufferedReader bin = new BufferedReader(in);
		try {
			while (bin.readLine() != null) {
				System.out.println(bin.readLine());
			}
			System.out.println(System.getProperty("user.dir"));
			bin.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
