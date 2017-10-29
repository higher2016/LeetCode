package NIO.Selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * ServerSocketChannelTest:服务端
 * ServerSocketChannelTest（服务端）和SocketChannelTest（客户端）是一个简单的服务器<br/>
 * 这个代码结合ServerSocketChannel来演示Selector的使用
 * 
 * @author 李嘉明
 * @2017-3-24
 */
public class ServerSocketChannelTest {
	private int size = 1024;
	private ServerSocketChannel socketChannel;
	private ByteBuffer byteBuffer;
	private Selector selector;
	/** 端口号 */
	private final int port = 8998;
	/** 客户端访问数量 */
	private int remoteClientNum = 0;

	public ServerSocketChannelTest() {
		try {
			initChannel();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public void initChannel() throws Exception {
		socketChannel = ServerSocketChannel.open();
		// 调整socketChannel通道的阻塞情况，传参为false表示将这个通道设置为非阻塞
		socketChannel.configureBlocking(false);
		// InetSocketAddress对象表示IP Socket Address（IP号+端口号）就是访问路径
		// bind(SocketAddress local)方法表示：Binds the channel's socket to a local
		// address and configures the socket to listen for connections.
		// 就是对这个IP Socket Address进行绑定监听
		socketChannel.bind(new InetSocketAddress(port));
		System.out.println("listener on port:" + port);
		selector = Selector.open();
		// 表示将通道注册到Selector中
		socketChannel.register(selector, SelectionKey.OP_ACCEPT);
		byteBuffer = ByteBuffer.allocate(size);
		byteBuffer.order(ByteOrder.BIG_ENDIAN);
	}

	private void listener() throws Exception {
		while (true) {
			System.out.println("------------------1");
			int n = selector.select();
			System.out.println("------------------4");
			if (n == 0) {
				continue;
			}
			Iterator<SelectionKey> ite = selector.selectedKeys().iterator();
			while (ite.hasNext()) {
				SelectionKey key = ite.next();
				if (key.isAcceptable()) {
					ServerSocketChannel server = (ServerSocketChannel) key.channel();
					SocketChannel channel = server.accept();
					remoteClientNum++;
					System.out.println("online client num = " + remoteClientNum);
					replyClient(channel);
				}
				if (key.isReadable()) {
					readDataFromSocket(key);
				}
				ite.remove();
			}
		}
	}

	protected void readDataFromSocket(SelectionKey key) throws Exception {
		SocketChannel socketChannel = (SocketChannel) key.channel();
		int count;
		byteBuffer.clear();
		while ((count = socketChannel.read(byteBuffer)) > 0) {
			byteBuffer.flip();
			while (byteBuffer.hasRemaining()) {
				socketChannel.write(byteBuffer);
			}
			byteBuffer.clear();
		}
		if (count < 0) {
			socketChannel.close();
		}
	}

	private void replyClient(SocketChannel channel) throws IOException {
		byteBuffer.clear();
		byteBuffer.put("hello client!\r\n".getBytes());
		byteBuffer.flip();
		channel.write(byteBuffer);
	}

	public static void main(String[] args) {
		try {
			new ServerSocketChannelTest().listener();
		} catch (Exception e) {
		}
	}
}
