package com.wb.netty.ch01.aio;

import java.io.IOException;

public class TimeService {
	
	public static void main(String[] arg) throws IOException{
		int port = 8088;
		if(arg != null && arg.length > 0){
			try{
				port = Integer.valueOf(arg[0]);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		AsyncTimeServerHandler timeServer = new AsyncTimeServerHandler(port);
		new Thread(timeServer ,"AsyncTimeServerHandler").start();;
	}
}
