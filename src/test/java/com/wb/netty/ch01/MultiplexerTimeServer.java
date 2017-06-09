package com.wb.netty.ch01;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class MultiplexerTimeServer implements Runnable {
	private Selector selector;
	private ServerSocketChannel serverChannel;
	private volatile boolean stop;

	public MultiplexerTimeServer(int port) {
		try {
			selector = Selector.open();
			serverChannel = ServerSocketChannel.open();
			serverChannel.configureBlocking(false);
			serverChannel.bind(new InetSocketAddress(port),1024);
			serverChannel.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("the time service is start in port:"+port);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

	}
	
	public void stop(){
		this.stop = true;
	}

	public void run() {
		while(!stop){
			try{
				selector.select(1000);
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> it = selectionKeys.iterator();
				SelectionKey key = null;
				System.out.println("key:"+key);
				while(it.hasNext()){
					key = it.next();
					it.remove();
					try{
						handleInput(key);
					}catch(Exception e){
						e.printStackTrace();
						if(key.channel() != null){
							key.channel().close();
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if(selector != null){
			try{
				selector.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	private void handleInput(SelectionKey key) throws IOException {
		if(key.isAcceptable()){
			ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
			SocketChannel sc = ssc.accept();
			sc.configureBlocking(false);
			sc.register(selector,SelectionKey.OP_READ);
		}
		if(key.isReadable()){
			SocketChannel sc = (SocketChannel)key.channel();
			ByteBuffer readBuffer = ByteBuffer.allocate(1024);
			int readBytes = sc.read(readBuffer);
			if(readBytes>0){
				readBuffer.flip();
				byte[] bytes = new byte[readBuffer.remaining()];
				readBuffer.get(bytes);
				String body = new String(bytes,"utf-8");
				System.out.println("the time server receive order :"+body);
				
				String currentTime = "QUERY TIME".equalsIgnoreCase(body)?System.currentTimeMillis()+"":"BAD";
				
				doWrite(sc,currentTime);
			}
			
		}
		
	}

	private void doWrite(SocketChannel sc, String currentTime) throws IOException {
		if(currentTime != null && currentTime.length()>0){
			byte[] bytes = currentTime.getBytes();
			ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
			writeBuffer.put(bytes);
			writeBuffer.flip();
			sc.write(writeBuffer);
		}
	}

}






























