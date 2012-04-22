package com.parousia.idlebrain.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRoute;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

public class HTTPUtility {
	
	public static int NETWORK_TIMEOUT = 5000;
	private static final int MAX_CONNECTIONS = 15;
	private static final int THREAD_TIMEOUT = 120000;
	private static int HTTP_PORT = 80;
	private static final int SOCKET_TIMEOUT = 90000;
	protected static ThreadSafeClientConnManager connectionManager;
	protected static HttpClient httpClient;
	protected static HttpParams connParams;

	public static byte[] fetchImage(URI url) {
		
		setupNetwork();
		
		URLConnection conn;
		InputStream is = null;
		try {
			SchemeRegistry schReg = new SchemeRegistry();
			schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), HTTP_PORT));
			connParams = new BasicHttpParams();
			connectionManager = new ThreadSafeClientConnManager(connParams, schReg);
			httpClient =new DefaultHttpClient(connectionManager, connParams);
			HttpGet httpget = new HttpGet(url);
			HttpResponse response = httpClient.execute(httpget);
			if (response == null)
				throw new Exception("Null response");
			if (response.getStatusLine().getStatusCode() != 200)
				throw new Exception("Server return code " + response.getStatusLine().getStatusCode());
			ByteArrayOutputStream outstream = new ByteArrayOutputStream();
			response.getEntity().writeTo(outstream);
			return outstream.toByteArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	
	}

	private static void setupNetwork() {
		
		HttpParams connParams = new BasicHttpParams();
		ConnManagerParams.setMaxTotalConnections(connParams, MAX_CONNECTIONS);
		ConnManagerParams.setTimeout(connParams, THREAD_TIMEOUT);
		ConnManagerParams.setMaxConnectionsPerRoute(connParams,
				new ConnPerRoute() {
					@Override
					public int getMaxForRoute(HttpRoute route) {
						return MAX_CONNECTIONS;
					}
				});
		
		SchemeRegistry schReg = new SchemeRegistry();
		schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), HTTP_PORT));
		
		
		HttpProtocolParams.setVersion(connParams, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setUseExpectContinue(connParams, false);
		HttpConnectionParams.setTcpNoDelay(connParams, true);
		HttpConnectionParams.setStaleCheckingEnabled(connParams, false);	
		HttpProtocolParams.setContentCharset(connParams, HTTP.UTF_8);
		HttpConnectionParams.setSoTimeout(connParams, SOCKET_TIMEOUT);
		
		
	}
}