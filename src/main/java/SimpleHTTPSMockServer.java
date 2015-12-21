package main.java;

public class SimpleHTTPSMockServer extends SimpleHTTPSServer{

	public SimpleHTTPSMockServer(int port) {
		super(port, SSLHandlerMock.class);
	}
	
}
