package main.java;

import java.io.IOException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class SimpleHTTPSClient {
	public static HttpsURLConnection getConnection(String httpsURL) throws KeyManagementException, NoSuchAlgorithmException, IOException{
		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier(){

			public boolean verify(String arg0, SSLSession arg1) {
				return true;
			}
        });
		TrustManager[] trustAllCerts = new TrustManager[] {
       		 new X509TrustManager(){

					public void checkClientTrusted(X509Certificate[] arg0,
							String arg1) throws CertificateException {
						
					}

					public void checkServerTrusted(X509Certificate[] arg0,
							String arg1) throws CertificateException {
						
					}

					public X509Certificate[] getAcceptedIssuers() {
						return null;
					}
       			 
       		 }
        };
		SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		URL myurl = new URL(httpsURL);
		return (HttpsURLConnection)myurl.openConnection();

	}
}
