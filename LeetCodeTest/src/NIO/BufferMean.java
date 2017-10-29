package NIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Buffer的理解 还是使用FileChannel的代码案例<br/>
 * 首先介绍Buffer的基本用法：<br/>
 * <br/>
 * 1、创建Buffer实例（创建的时候可以初始化Buffer对象的容量通过ByteBuffer.allocate(int capacity)）<br/>
 * <br/>
 * 2、将Channel的数据写入到Buffer对象中（例如：channel.read(buffer)）<br/>
 * <br/>
 * 3、Buffer对象调用flip()方法（<strong>flip()方法的作用是：将buffer从写模式切换到读模式</strong>）<br/>
 * <br/>
 * 4、Buffer对象调用clear()方法清空buffer缓冲区中的所有数据（其实可以使用compact()方法：compact()
 * 方法只会清除已经读过的数据。任何未读的数据都被移到缓冲区的起始处，新写入的数据将放到缓冲区未读数据的后面。）
 * 
 * @author 李嘉明
 * @2017-3-21
 */
public class BufferMean {
	public static void main(String[] args) throws IOException {
		File file = new File("F:/jia.txt");
		FileInputStream fileInput = new FileInputStream(file);
		FileChannel fileChannel = fileInput.getChannel();
		// 1、创建Buffer实例并初始化Buffer对象的容量（48byte）
		ByteBuffer byteBuffer = ByteBuffer.allocate(48);
		// 2、将Channel的数据写入到Buffer对象
		int bytesRead = fileChannel.read(byteBuffer);
		while (bytesRead != -1) {
			// 3、从写模式切换到读模式
			byteBuffer.flip();
			while (byteBuffer.hasRemaining()) {
				System.out.print((char) byteBuffer.get());
			}
			System.out.println(" ----read " + bytesRead);
			// 4、清空Buffer对象缓冲区的所有数据
			byteBuffer.clear();
			// 再次将通道的数据写入到buffer中，这里返回的值是本次从通道读取的数据的字节数
			bytesRead = fileChannel.read(byteBuffer);
		}
	}
}
