package test.java;

import static org.junit.Assert.*;

import java.io.InputStream;

import javax.net.ssl.HttpsURLConnection;

import main.java.SimpleHTTPSClient;
import main.java.SimpleHTTPSServer;

import org.junit.Test;

public class SimpleHTTPSServerClientTest {
	
	@Test
	public void jointTest(){
		int port = 10000;
		try{
			final SimpleHTTPSServer server = new SimpleHTTPSServer(10000, SimpleHandler.class);
			new Thread(new Runnable(){

				public void run() {
					server.start();
				}
				
			}).start();
			
			Thread.sleep(1000);
			
			HttpsURLConnection con = SimpleHTTPSClient.getConnection("https://127.0.0.1:" + port);

			con.setRequestMethod("POST");
			InputStream in = con.getInputStream();
			int read;
			StringBuilder sb = new StringBuilder();
			while((read = in.read()) != -1){
				sb.append((char)read);
			}
			server.stop();
			
			assertTrue(con.getResponseCode() == 200);
		} catch(Exception e){
			assertTrue(false);
		}
		
	}
}
