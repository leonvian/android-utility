package br.com.lvc.utility.connection;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import br.com.lvc.utility.R;


public class SynchronousHttpConnection {

	public static final int TIME_OUT_DEFAULT = 60000;


	private int timeOut = TIME_OUT_DEFAULT;

	public static final int DID_START = 0;
	public static final int DID_ERROR = 1;
	public static final int DID_SUCCEED = 2;

	private static final int GET = 0;
	private static final int POST = 1;
	private static final int PUT = 2;
	private static final int DELETE = 3;

	BasicHeader[] commonsHeaders = {
			new BasicHeader("Content-type", "application/json")
	};


	private BasicHeader[] headers;

	public SynchronousHttpConnection() {
		this.headers = commonsHeaders;
	}

	public SynchronousHttpConnection(BasicHeader[] headers) {
		this.headers = headers;
	}

	public SynchronousHttpConnection(int timeOut,BasicHeader[] headers) {
		this.headers = headers;
		this.timeOut = timeOut;
	}

	public String get(String url) throws HttpConnectionException  {
		return executeHTTPConnection(GET, url, null);
	}

	public String executeGetHTTPLonger(String url) throws HttpConnectionException  {
		return executeHTTPConnection(GET, url, null,timeOut);
	}

	public String post(String url, String data) throws HttpConnectionException  {
		return executeHTTPConnection(POST, url, data);
	}

	public String post(String url, List<NameValuePair> data) throws HttpConnectionException  {
		HttpResponse response = executeHTTPPostGetResponse(url, data, timeOut, null);
		try {
			String serverResponse = processResponse(response.getEntity());
			return serverResponse;	
		} catch (Exception e) {
			throw new HttpConnectionException(e);
		}
	}

	public String post(String url, String data, BasicHeader[] headers) throws HttpConnectionException  {
		return executeHTTPConnection(POST, url, data, timeOut, headers);
	}

	public String put(String url, String data) throws HttpConnectionException  {
		return executeHTTPConnection(PUT, url, data);
	}

	public String delete(String url) throws HttpConnectionException  {
		return executeHTTPConnection(DELETE, url, null);
	}

	public HttpResponse getHttpResponseAsReturn(String url) throws HttpConnectionException  {
		return executeHTTPConnectionGetResponse(GET, url, null,timeOut, null);
	}

	public HttpResponse executeGetHTTPLongerHttpResponseAsReturn(String url) throws HttpConnectionException  {
		return executeHTTPConnectionGetResponse(GET, url, null,timeOut, null);
	}

	public HttpResponse postHttpResponseAsReturn(String url, String data) throws HttpConnectionException  {
		return executeHTTPConnectionGetResponse(POST, url, data,timeOut, null);
	}

	public HttpResponse putHttpResponseAsReturn(String url, String data) throws HttpConnectionException  {
		return executeHTTPConnectionGetResponse(PUT, url, data,timeOut, null);
	}

	public HttpResponse deleteHttpResponseAsReturn(String url) throws HttpConnectionException  {
		return executeHTTPConnectionGetResponse(DELETE, url, null,timeOut, null);
	}

	public Bitmap bitmap(String url) throws IllegalStateException, IOException {
		return executeHTTPConnectionBitmap(url);
	}

	public String postLongTimeOut(String url, String data) throws HttpConnectionException  {
		return executeHTTPConnection(POST, url, data, timeOut);
	}

	private String executeHTTPConnection(int method, String url, String data) throws  HttpConnectionException {
		String result = executeHTTPConnection(method, url, data, timeOut);
		return result;
	}

	private String executeHTTPConnection(int method, String url, String data, int timeOut) throws  HttpConnectionException {
		return executeHTTPConnection(method,url,data,timeOut,null);
	}

	private String executeHTTPConnection(int method, String url, String data, int timeOut,BasicHeader[] customHeaders) throws  HttpConnectionException {
		try {
			HttpResponse response =	executeHTTPConnectionGetResponse(method, url, data, timeOut, customHeaders);

			return processResponse(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
			throw new HttpConnectionException(R.string.falha_conectar_servidor , e);
		}
	}


	private HttpResponse executeHTTPPostGetResponse(String url, List<NameValuePair> data, int timeOut, BasicHeader[] customHeaders) throws  HttpConnectionException {
		HttpResponse response = null; 

		try { 
			HttpClient httpClient = new DefaultHttpClient();
			HttpConnectionParams.setSoTimeout(httpClient.getParams(), timeOut);
			HttpPost httpRequestBase = new HttpPost(url);				
			httpRequestBase.setEntity(new UrlEncodedFormEntity(data));
			addHeaders(httpRequestBase, customHeaders);
			response = 	httpClient.execute(httpRequestBase);

		} catch (Exception e) {
			e.printStackTrace();
			throw new HttpConnectionException(R.string.falha_conectar_servidor , e);
		}

		return response;
	}

	private HttpResponse executeHTTPConnectionGetResponse(int method, String url, String data, int timeOut, BasicHeader[] customHeaders) throws  HttpConnectionException {

		HttpResponse response = null; 

		try { 
			HttpClient httpClient = new DefaultHttpClient();
			HttpConnectionParams.setSoTimeout(httpClient.getParams(), timeOut);

			HttpRequestBase httpRequestBase = null;

			switch (method) {
			case GET: 
				httpRequestBase = new HttpGet(url);
				break;

			case POST:
				httpRequestBase = new HttpPost(url);
				((HttpPost)httpRequestBase).setEntity(new StringEntity(data,HTTP.UTF_8));
				break;

			case PUT:
				httpRequestBase = new HttpPut(url);
				((HttpPut)httpRequestBase).setEntity(new StringEntity(data));
				break;

			case DELETE:
				httpRequestBase = new HttpDelete(url);
				break;

			default:
				throw new HttpConnectionException("Unknown Request.");
			}  


			addHeaders(httpRequestBase, customHeaders);
			response = 	httpClient.execute(httpRequestBase);

		} catch (Exception e) {
			e.printStackTrace();
			throw new HttpConnectionException(R.string.falha_conectar_servidor , e);
		}

		return response;
	} 

	private void addHeaders(HttpRequestBase httpRequestBase, BasicHeader[] customHeaders) {

		BasicHeader[] headersToBeUsed = null;

		if(customHeaders != null)
			headersToBeUsed = customHeaders;
		else
			headersToBeUsed = headers;

		for(BasicHeader header : headersToBeUsed) {
			httpRequestBase.addHeader(header);
		} 
	}

	private Bitmap executeHTTPConnectionBitmap(String url) throws IllegalStateException, IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpConnectionParams.setSoTimeout(httpClient.getParams(), timeOut);
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