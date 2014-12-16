package br.com.lvc.utility.connection;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;

import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;

public abstract class WebServiceComun {
	 
	protected SynchronousHttpConnection synchronousHttpConnection = null;
	private DataSerializerMapperConfiguration jsonSerializerConfig = null;
	
	public WebServiceComun() {
		synchronousHttpConnection = new SynchronousHttpConnection(getTimeOut(),getHeaders());
		jsonSerializerConfig = getJSONSerializerConfiguration();
		
	}

	public String sendDataPOST(String url,List<NameValuePair> data) throws  HttpConnectionException {
		 
		Log.i("Dados enviados", "URL: " + url + " DADOS: " + data);

		String response = synchronousHttpConnection.post(url, data);
		Log.i("Resposta Servidor", response);
		
		return response;
	}
	
	public String sendDataPOST(String url,String passable) throws  HttpConnectionException {
		String json = passable;
		Log.i("Dados enviados", "URL: " + url + " DADOS: " + json);

		String response = synchronousHttpConnection.post(url, json);
		Log.i("Resposta Servidor", response);
		
		return response;
	}
	
	public  String sendDataPOST(String url,String passable,BasicHeader[] headers) throws  HttpConnectionException {
		String json = passable;
		Log.i("Dados enviados", "URL: " + url + " DADOS: " +json);

		String response = synchronousHttpConnection.post(url, json, headers);
		Log.i("Resposta Servidor", response);
		
		return response;
	}

	public  <T> T sendDataGet(String url, Class<T> targetClass) throws HttpConnectionException {
		String response = synchronousHttpConnection.get(url);
		Log.i("Resposta Servidor", response);
		T t = getSerializer().toObject(response, targetClass);

		return t;
	}
	
	public  <T> T sendDataGet(String url, TypeReference<T> target) throws HttpConnectionException {
		String response = synchronousHttpConnection.get(url);
		Log.i("Resposta Servidor", response);
		T t = getSerializer().toObject(response, target);

		return t;
	}
	 

	public  <T> T sendDataPOST(String url,Object passable, Class<T> targetClass) throws  HttpConnectionException {
		String json = getSerializer().toJson(passable);
		Log.i("Dados enviados","URL: " + url + " DADOS: " + json);

		String response = synchronousHttpConnection.post(url, json);
		Log.i("Resposta Servidor", response);

		T t = getSerializer().toObject(response, targetClass);

		return t;
	}
 

	public  <T> T sendDataPOST(String url,String passable, Class<T> targetClass) throws  HttpConnectionException {
		String json = passable;
		Log.i("Dados enviados", "URL: " + url + " DADOS: " +json);

		String response = synchronousHttpConnection.post(url, json);
		Log.i("Resposta Servidor", response);

		T t = getSerializer().toObject(response, targetClass);

		return t;
	} 

	public  <T> T sendDataPUT(String url,Object passable, Class<T> targetClass) throws HttpConnectionException {
		String response = null;

		String json = getSerializer().toJson(passable);
		Log.i("Dados enviados", "URL: " + url + " DADOS: " +json);

		response = synchronousHttpConnection.put(url, json);
		Log.i("Resposta Servidor", response); 

		T t = getSerializer().toObject(response, targetClass);

		return t;		
	}

	public  <T> T sendDataGetLonger(String url, Class<T> targetClass) throws  HttpConnectionException {
		String response = synchronousHttpConnection.executeGetHTTPLonger(url);
		T t = getSerializer().toObject(response, targetClass);

		return t;
	}
 
	public  HttpResponse sendDataPOSTHttpResponseAsReturn(String url,String passable) throws  HttpConnectionException { 
		HttpResponse response = synchronousHttpConnection.postHttpResponseAsReturn(url, passable);
	    return response;
	}

	public  HttpResponse sendDataGetHttpResponseAsReturn(String url) throws HttpConnectionException {
		HttpResponse response = synchronousHttpConnection.getHttpResponseAsReturn(url);
		return response;
	}

	public  HttpResponse sendDataPOSTHttpResponseAsReturn(String url,Object passable) throws  HttpConnectionException {
		String json = DataSerializer.getInstance().toJson(passable);
		Log.i("Dados enviados", json);

		HttpResponse response = synchronousHttpConnection.postHttpResponseAsReturn(url, json);
		return response;
	}
 
	public  HttpResponse sendDataPUTHttpResponseAsReturn(String url,Object passable) throws HttpConnectionException {
		String json = DataSerializer.getInstance().toJson(passable);
		HttpResponse response = synchronousHttpConnection.putHttpResponseAsReturn(url, json);
		return response;		
	}

	public  HttpResponse sendDataGetLongerHttpResponseAsReturn(String url) throws  HttpConnectionException {
		HttpResponse response = synchronousHttpConnection.executeGetHTTPLongerHttpResponseAsReturn(url);
		return response;
	}
	
	public int getTimeOut() {
		return SynchronousHttpConnection.TIME_OUT_DEFAULT;
	}
	
	public BasicHeader[] getHeaders() {
		BasicHeader[] headers = {
			//	new BasicHeader("Content-type", "application/json"),
				new BasicHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8")
		};
		
		return headers; 
	}
	
	protected DataSerializer getSerializer() {
		return DataSerializer.getInstance(jsonSerializerConfig);
	}
	
	public DataSerializerMapperConfiguration getJSONSerializerConfiguration() {
		DataSerializerMapperConfiguration configuration = new DataSerializerMapperConfiguration(DataSerializer.DEFAULT_FORMAT_DATE);
		return configuration;
	}
}