package com.wb.netty.ch07.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wb.netty.ch02.demo.HelloClientIntHandler;
import com.wb.netty.ch07.entity.SubscribeReq;
import com.wb.netty.ch07.entity.SubscribeResp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SubReqClientHandle extends ChannelInboundHandlerAdapter {  
    private static Logger   logger  = LoggerFactory.getLogger(SubReqClientHandle.class);  
    @Override  
    // 读取服务端的信息  
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {  
    	SubscribeResp resp = (SubscribeResp)msg;
    	
        logger.info("HelloClientIntHandler.channelRead" + resp.toString());  
 
        //ctx.close();  
        System.out.println("Server said:" + msg);  
    }  
    @Override  
    // 当连接建立的时候向服务端发送消息 ，channelActive 事件当连接建立的时候会触发  
    public void channelActive(ChannelHandlerContext ctx) throws Exception {  
        logger.info("HelloClientIntHandler.channelActive");  
        //String msg = "Are you ok?"+System.getProperty("line.separator");
        for(int i= 0;i<2;i++){
        	ctx.write(req(i));
        	ctx.flush();
        }
    } 
    
    public SubscribeReq req(int subReqId){
    	SubscribeReq req = new SubscribeReq();
    	req.setAddress("address");
    	req.setPhoneNumber("123");
    	req.setProductName("hehe");
    	req.setSubReqId(subReqId);
    	req.setUserName("wb");
    	return req;
    }
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    	System.out.println("channelReadComplete");
    	super.channelReadComplete(ctx);
    }
    
    



}
