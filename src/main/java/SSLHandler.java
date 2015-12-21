package main.java;

import javax.net.ssl.SSLSocket;

public interface SSLHandler {
	public void handle(SSLSocket s);
}
