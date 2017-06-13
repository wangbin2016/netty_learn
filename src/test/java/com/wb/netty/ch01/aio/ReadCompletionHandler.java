package com.wb.netty.ch01.aio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class ReadCompletionHandler implements CompletionHandler<Integer,ByteBuffer> {
	private AsynchronousSocketChannel channel;
	public ReadCompletionHandler(AsynchronousSocketChannel channel){
		if(this.channel == null){
			this.channel = channel;
		}
	}

	public void completed(Integer result, ByteBuffer attachment) {
		attachment.flip();
		byte[] body = new byte[attachment.remaining()];
		attachment.get(body);
		try{
			String req = new String(body,"UTF-8");
			System.out.println("The time server receive order :"+req);
			String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(req)?System.currentTimeMillis()+"":"bad order";
			doWrite(currentTime);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private void doWrite(String currentTime) {
		if(currentTime != null && currentTime.length()>0){
			byte[] bytes = currentTime.getBytes();
			final ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
			writeBuffer.put(bytes);
			writeBuffer.flip();
			channel.write(writeBuffer,writeBuffer,new CompletionHandler<Integer, ByteBuffer>() {

				public void completed(Integer result, ByteBuffer byteBuffer) {
					if(byteBuffer.hasRemaining()){
						channel.write(writeBuffer,writeBuffer,this);
					}
					
				}

				public void failed(Throwable exc, ByteBuffer attachment) {					
					try {
						channel.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
		
	}

	public void failed(Throwable exc, ByteBuffer attachment) {
		try {
			this.channel.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
