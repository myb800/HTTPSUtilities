package main.java;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.net.ssl.SSLSocket;

public class SSLHandlerMock implements SSLHandler{

	public void handle(SSLSocket s) {
		try {
			s.startHandshake();
			InputStream inputStream = s.getInputStream();
	        OutputStream outputStream = s.getOutputStream();
	        for(int c = inputStream.read(); c != -1; c = inputStream.read()){
	        	System.out.print(c);
	        }
	        outputStream.write("HTTP/1.1 200 OK\n\n".getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Start handling application content
        
	}

}
