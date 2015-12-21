package test.java;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.net.ssl.SSLSocket;

import main.java.HTTPRequest;
import main.java.SSLHandler;

public class SimpleHandler implements SSLHandler{
	public SimpleHandler(){
		
	}
	
	public void handle(SSLSocket s) {
		try {
			s.startHandshake();
			// Start handling application content
            InputStream inputStream = s.getInputStream();
            System.out.println(HTTPRequest.parseRequest(inputStream));
            OutputStream outputStream = s.getOutputStream();
            
            //BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(outputStream));
            DataInputStream in = new DataInputStream(s.getInputStream());
         
            // Write data
            printWriter.print("HTTP/1.1 200 OK\r\n");
            //printWriter.print("Content-Type: text/html\r\n");
            printWriter.print("<html><body>Hello world!</body></html>\n");
            printWriter.flush();
             
            s.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}