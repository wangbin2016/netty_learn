package com.wb.netty.ch01;

public class TimeService {
	public static void main(String[] args) {
		int port = 8778;
		if(args != null && args.length>0){
			try{
				port = Integer.valueOf(args[0]);
			}catch(Exception e){}
		}
		MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
		new Thread(timeServer).start();;
		
	}
}
