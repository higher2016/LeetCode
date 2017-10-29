package NIO;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * NIO通道之间的数据传输：transferFrom()和transferTo()两个方法
 * @author 李嘉明
 * @2017-3-23
 */
public class ChannelToChannel {
	public static void main(String[] args) throws Exception {
		FileInputStream s = new FileInputStream("F:/jia.txt");
		FileChannel fromChannel = s.getChannel();
		
		FileInputStream ss = new FileInputStream("F:/to.txt");
		FileChannel toChannel = ss.getChannel();
		
		try{
			fromChannel.transferTo(0, fromChannel.size(),toChannel);
		}finally{
			fromChannel.close();
			toChannel.close();
		}
		
		ByteBuffer byteBuffer = ByteBuffer.allocate(48);
		ByteBuffer byteBuffer1 = ByteBuffer.allocate(18);
		ByteBuffer[] byteBuffers = {byteBuffer,byteBuffer1};
		long bytesRead = toChannel.read(byteBuffers);
		while (bytesRead != -1L) {
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
			byteBuffer.clear();
			byteBuffer1.clear();
			bytesRead = toChannel.read(byteBuffers);
		}
	}
}
