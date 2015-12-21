package main.java;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;
import java.security.KeyStore;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public class SimpleHTTPSServer {
	private int port = 9996;
	private SSLServerSocket serverSocket = null;
	private Class<? extends SSLHandler> sslHandler;
	private SimpleHTTPSServer() {

	}

	public SimpleHTTPSServer(int port,Class<? extends SSLHandler> sslHandler) {
		this.port = port;
		this.sslHandler = sslHandler;
	}

	// Create the and initialize the SSLContext
	private SSLContext createSSLContext() {
		try {
			KeyStore keyStore = KeyStore.getInstance("JKS");
			keyStore.load(
					new FileInputStream("/Users/yanbooracle/lig.keystore"),
					"simulator".toCharArray());
												

			// Create key manager
			KeyManagerFactory keyManagerFactory = KeyManagerFactory
					.getInstance("SunX509");
			keyManagerFactory.init(keyStore, "simulator".toCharArray());
																		
			KeyManager[] km = keyManagerFactory.getKeyManagers();

			// Create trust manager
			TrustManagerFactory trustManagerFactory = TrustManagerFactory
					.getInstance("SunX509");
			trustManagerFactory.init(keyStore);
			TrustManager[] tm = trustManagerFactory.getTrustManagers();

			// Initialize SSLContext
			SSLContext sslContext = SSLContext.getInstance("TLSv1");
			sslContext.init(km, tm, null);

			return sslContext;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	// Start to run the server
	public void start() {
		SSLContext sslContext = this.createSSLContext();

		try {
			// Create server socket factory
			SSLServerSocketFactory sslServerSocketFactory = sslContext
					.getServerSocketFactory();

			// Create server socket
			SSLServerSocket sslServerSocket = (SSLServerSocket) sslServerSocketFactory
					.createServerSocket(this.port);
			
			serverSocket = sslServerSocket;
			while (true) {
				SSLSocket sslSocket = null;
				try {
					sslSocket = (SSLSocket) sslServerSocket.accept();
					
				} catch(SocketException s) {
					break;
				}
				
				// Start the server thread
				SSLHandler h = sslHandler.newInstance();
				
				h.handle(sslSocket);
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void stop() throws IOException{
		if(serverSocket != null){
			serverSocket.close();
			serverSocket = null;
		}
	}
}
