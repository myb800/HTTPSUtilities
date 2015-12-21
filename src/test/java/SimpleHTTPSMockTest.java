package test.java;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HttpsURLConnection;

import main.java.ProxySocketHandler;
import main.java.SimpleHTTPSClient;
import main.java.SimpleHTTPServer;

import org.junit.Test;

public class SimpleHTTPSMockTest {

	@Test
	public void mock() throws KeyManagementException, NoSuchAlgorithmException, IOException{
		int port = 10000;
		System.setProperty("https.proxyHost", "127.0.0.1");
		System.setProperty("https.proxyPort", Integer.toString(port));
		
		SimpleHTTPServer mserver = new SimpleHTTPServer(port,ProxySocketHandler.class);
		mserver.start();
		//new Thread(new ProxyServer()).start();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		HttpsURLConnection con = SimpleHTTPSClient.getConnection("https://www.google.com");
		con.setRequestMethod("GET");
		
		InputStream in = con.getInputStream();
		int read;
		StringBuilder sb = new StringBuilder();
		while((read = in.read()) != -1){
			sb.append((char)read);
		}
		in.close();
		System.out.println(sb.toString());
	}
}
