package NIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * NIO中FileChannle的创建
 * Channel的理解
 * @author 李嘉明
 * @2017-3-21
 */
public class FileChannleMake {
	public static void main(String[] args) throws IOException {
		// 获取文件对象
		File file = new File("F:/jia.txt");
		// 将文件对象转为流的形式
		FileInputStream fileInput = new FileInputStream(file);
		// 将输入流转为channel对象
		FileChannel fileChannel = fileInput.getChannel();
		// 用于保存从channel读取到的数据，最大容量：48bytes
		ByteBuffer byteBuffer = ByteBuffer.allocate(48);
		// 从文件中读写数据，下面这行代码表示从该通道读取一个字节序列到给定的缓冲区。通道是可以异步读写的！！！
		// 每次从通道读48个字节到byteBuffer中，读过的数据不能再读
		int bytesRead = fileChannel.read(byteBuffer);
		while (bytesRead != -1) {
			byteBuffer.flip();
			while (byteBuffer.hasRemaining()) {
				System.out.print((char) byteBuffer.get());
			}
			System.out.println(" ----read " + bytesRead);
			byteBuffer.clear();
			// 当byteBuffer全部读出后，又会继续从channel读数据到byteBuffer中，直到读完channel中的数据
			bytesRead = fileChannel.read(byteBuffer);
		}

		//通过下面的代码，可以清晰的发现。FileChannel中的数据只要读出之后就会被删掉
		ByteBuffer byteBuffer1 = ByteBuffer.allocate(48);
		int bytesRead1 = fileChannel.read(byteBuffer);
		System.out.println("ssssssssss" + bytesRead1);
		while (bytesRead1 != -1) {
			System.out.println("ssssssssssasasas----end" + bytesRead1);
			byteBuffer1.flip();
			while (byteBuffer.hasRemaining()) {
				System.out.print((char) byteBuffer1.get());
			}
			System.out.println(" ----read " + bytesRead1);
			byteBuffer.clear();
			bytesRead1 = fileChannel.read(byteBuffer);
		}
	}
}
