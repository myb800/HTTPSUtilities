package main.java;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HTTPRequest {
	
	private String body;
	private Map<String, String> header;
	private String rawRequest;
	private int port;
	public int getPort(){
		return port;
	}
	public String getMethod() {
		return method;
	}
	private void setMethod(String method) {
		this.method = method;
	}
	public String getPath() {
		return path;
	}
	private void setPath(String path) {
		this.path = path;
	}
	public String getProtocol() {
		return protocol;
	}
	private void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	private void setRawRequest(String rawRequest) {
		this.rawRequest = rawRequest;
	}
	@Override
	public String toString(){
		return rawRequest;
	}
	private String method;
	private String path;
	private String protocol;
	private HTTPRequest(String body,Map<String, String> header){
		this.body = body;
		this.header = header;
	}
	public static HTTPRequest parseRequest(InputStream in) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line;
		Map<String, String> header = new HashMap<>();
		String method = "";
		String path = "";
		String protocol = "";
		boolean firstLine = true;
		StringBuilder rawRequestBuilder = new StringBuilder();
		while((line = br.readLine()).trim().length() > 0){
			rawRequestBuilder.append(line);
			rawRequestBuilder.append("\r\n");
			if(firstLine){
				String[] tokens = line.trim().split(" ");
				method = tokens[0];
				path = tokens[1];
				protocol = tokens[2];
				firstLine = false;
				continue;
			}
			int i = line.indexOf(':');
			if(i == -1){
				throw new Exception("Malformated Http request");
			}
			String key = line.substring(0, i).trim();
			String value = line.substring(i + 1).trim();
			header.put(key,value);
		}
		rawRequestBuilder.append("\r\n");
		
		String body = "";
		HTTPRequest r = new HTTPRequest(body, header);
		r.setMethod(method);
		r.setPath(path);
		r.setProtocol(protocol);
		r.setRawRequest(rawRequestBuilder.toString());
		return r;
	}
	public String getBody() {
		return body;
	}
	public Map<String, String> getHeader() {
		return header;
	}
		
}
