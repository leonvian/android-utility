package br.com.lvc.utility.connection;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import br.com.lvc.utility.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


public class SynchronousHttpConnection {
	
	
	private static final int TIME_OUT_DEFAULT = 30000;
	private static final int TIME_OUT_LONGER = 60000;

	public static final int DID_START = 0;
	public static final int DID_ERROR = 1;
	public static final int DID_SUCCEED = 2;

	private static final int GET = 0;
	private static final int POST = 1;
	private static final int PUT = 2;
	private static final int DELETE = 3;

	public SynchronousHttpConnection() {				
	}

	public String get(String url) throws HttpConnectionException  {
		return executeHTTPConnection(GET, url, null);
	}
	
	public String exexuteGetHTTPLonger(String url) throws HttpConnectionException  {
		return executeHTTPConnection(GET, url, null,TIME_OUT_LONGER);
	}


	public String post(String url, String data) throws HttpConnectionException  {
		return executeHTTPConnection(POST, url, data);
	}
	
	public String postLongTimeOut(String url, String data) throws HttpConnectionException  {
		return executeHTTPConnection(POST, url, data, TIME_OUT_LONGER);
	}

	public String put(String url, String data) throws HttpConnectionException  {
		return executeHTTPConnection(PUT, url, data);
	}

	public String delete(String url) throws HttpConnectionException  {
		return executeHTTPConnection(DELETE, url, null);
	}

	public Bitmap bitmap(String url) throws IllegalStateException, IOException {
		return executeHTTPConnectionBitmap(url);
	}
	
	


	private String executeHTTPConnection(int method, String url, String data) throws  HttpConnectionException {
	    String result = executeHTTPConnection(method, url, data, TIME_OUT_DEFAULT);
	    return result;
	}
	
	
	private String executeHTTPConnection(int method, String url, String data, int timeOut) throws  HttpConnectionException {
		try {

			HttpClient httpClient = new DefaultHttpClient();
			HttpConnectionParams.setSoTimeout(httpClient.getParams(), timeOut);
			HttpResponse response = null;
			switch (method) {
			case GET:
				
			//  url =	URLEncoder.encode(url, "UTF-8");
			//	url = convertURL(url);
				HttpGet httpGet = new HttpGet(url);
			//	httpGet.addHeader(new BasicHeader("Content-Type", "text/plain; charset=utf-8"));
				response = httpClient.execute(httpGet);
				break;
			case POST:
				HttpPost httpPost = new HttpPost(url);
				//httpPost.addHeader(new BasicHeader("Content-type", "application/json; charset=utf-8"));
				httpPost.addHeader(new BasicHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8"));
				//httpPost.addHeader(new BasicHeader("Content-type", "application/json"));
				//httpPost.addHeader(new BasicHeader("Content-Type", "text/plain; charset=utf-8"));
				httpPost.setEntity(new StringEntity(data,HTTP.UTF_8));
				response = httpClient.execute(httpPost);
				break;
			case PUT:
				HttpPut httpPut = new HttpPut(url);
				httpPut.addHeader(new BasicHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8"));
				httpPut.setEntity(new StringEntity(data));
				response = httpClient.execute(httpPut);
				break;
			case DELETE:
				response = httpClient.execute(new HttpDelete(url));
				break;
			default:
				throw new HttpConnectionException("Unknown Request.");
			}  

			return processResponse(response.getEntity());

		} catch (Exception e) {
			e.printStackTrace();
			throw new HttpConnectionException(R.string.falha_conectar_servidor , e);
		}
	}


	private Bitmap executeHTTPConnectionBitmap(String url) throws IllegalStateException, IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpConnectionParams.setSoTimeout(httpClient.getParams(), TIME_OUT_LONGER);
		HttpResponse response = httpClient.execute(new HttpGet(url));		
		return processBitmapEntity(response.getEntity());
	}

	private String processResponse(HttpEntity entity) throws IllegalStateException,IOException {
		String jsonText = EntityUtils.toString(entity, HTTP.UTF_8);
		return jsonText;
	}

	private Bitmap processBitmapEntity(HttpEntity entity) throws IOException {
		BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity);
		Bitmap bm = BitmapFactory.decodeStream(bufHttpEntity.getContent());
		return bm;
	}
}