package com.wb.netty.ch02.demo;

import io.netty.buffer.ByteBuf;  
import io.netty.channel.ChannelHandlerContext;  
import io.netty.channel.ChannelInboundHandlerAdapter;  
  
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  
  
public class HelloClientIntHandler extends ChannelInboundHandlerAdapter {  
    private static Logger   logger  = LoggerFactory.getLogger(HelloClientIntHandler.class);  
    @Override  
    // 读取服务端的信息  
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {  
        logger.info("HelloClientIntHandler.channelRead" + msg);  
 
        //ctx.close();  
        System.out.println("Server said:" + msg);  
    }  
    @Override  
    // 当连接建立的时候向服务端发送消息 ，channelActive 事件当连接建立的时候会触发  
    public void channelActive(ChannelHandlerContext ctx) throws Exception {  
        logger.info("HelloClientIntHandler.channelActive");  
        String msg = "Are you ok?"+System.getProperty("line.separator");
        for(int i= 0;i<100;i++){
        	byte[] msgs = msg.getBytes();
	        ByteBuf encoded = ctx.alloc().buffer(msgs.length);  
	        encoded.writeBytes(msgs);  
	        ctx.writeAndFlush(encoded);
	        System.out.println(i+ " msg " +msg);
        }
    } 
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    	System.out.println("channelReadComplete");
    	super.channelReadComplete(ctx);
    }
    
    

}  
