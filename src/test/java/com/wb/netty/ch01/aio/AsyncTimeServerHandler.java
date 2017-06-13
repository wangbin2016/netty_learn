package com.wb.netty.ch01.aio;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

public class AsyncTimeServerHandler implements Runnable {
	int port = 0;
	CountDownLatch latch;
	AsynchronousServerSocketChannel asynchronousServerSocketChannel;

	public AsyncTimeServerHandler(int port) {
		this.port = port;
		try{
			asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
			asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
			System.out.println("time service is start in port:"+port);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void run() {
		latch = new CountDownLatch(1);
		doAccept();
		try{
			latch.await();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private void doAccept() {
		System.out.println("doAccept。。。。");
		asynchronousServerSocketChannel.accept(this, new AcceptionCompletionHandler());
		
	}
}
