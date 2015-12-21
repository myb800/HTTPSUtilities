package main.java;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class SocketHandlerSimple implements SocketHandler{

	@Override
	public void handle(Socket socket) {
		try {
			DataInputStream in = new DataInputStream(socket.getInputStream());
			for(int c = in.read();c != -1;c = in.read()){
				System.out.print((char)c);
			}
			System.out.println();
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
