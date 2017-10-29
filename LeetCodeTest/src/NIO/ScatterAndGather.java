package NIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Scatter 	:	将channel的数据分散写入到多个Buffer中（写入操作完毕之后，所有的buffer的数据加起来就是channel的数据）
 * Gather	:	是指在写操作时将多个buffer的数据写入同一个Channel，也就是从多个Buffer中的数据“聚集（gather）”后发送到Channel。
 * @author 李嘉明
 * @2017-3-22
 */
public class ScatterAndGather {
	public static void main(String[] args) throws IOException {
		File file = new File("F:/jia.txt");
		FileInputStream fileInput = new FileInputStream(file);
		FileChannel fileChannel = fileInput.getChannel();
		ByteBuffer byteBuffer = ByteBuffer.allocate(48);
		ByteBuffer byteBuffer1 = ByteBuffer.allocate(18);
		ByteBuffer[] byteBuffers = {byteBuffer,byteBuffer1};
		// 2、将Channel的数据写入到Buffer对象，这里返回的值是指写入到byteBuffer和byteBuffer1字节数的和（也就是写入到ByteBuffer数组的字节数）
		long bytesRead = fileChannel.read(byteBuffers);
		while (bytesRead != -1L) {
			// 3、从写模式切换到读模式,想还原channel所有的数据可以按顺序读取ByteBuffer数组的所有Buffer对象，然后拼接起来。
			byteBuffer.flip();
			byteBuffer1.flip();
			while (byteBuffer.hasRemaining()) {
				System.out.print((char) byteBuffer.get());
			}
			System.out.println(" ----read " + bytesRead);
			while (byteBuffer1.hasRemaining()) {
				System.out.print((char) byteBuffer1.get());
			}
			System.out.println(" ----read111111 " + bytesRead);
			// 4、清空Buffer对象缓冲区的所有数据
			byteBuffer.clear();
			byteBuffer1.clear();
			// 再次将通道的数据写入到buffer中，这里返回的值是本次从通道读取的数据的字节数
			bytesRead = fileChannel.read(byteBuffers);
		}
	}
}
