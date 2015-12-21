package main.java;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class SimpleHTTPServer extends Thread{
	
	private int port;
	private Class<? extends SocketHandler> handler;
	private ServerSocket serversocket = null;
	public SimpleHTTPServer(int port,Class<? extends SocketHandler> handler){
		this.port = port;
		this.handler = handler;
	}
	
	public void run(){
		if(serversocket != null){
			return;
		}
		
		try {
			serversocket = new ServerSocket(port);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while(true){
			Socket s = null;
			try{
				s = serversocket.accept();
			} catch(SocketException e){
				break;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			SocketHandler h = null;
			try {
				h = handler.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			h.handle(s);
		}
	}
}
