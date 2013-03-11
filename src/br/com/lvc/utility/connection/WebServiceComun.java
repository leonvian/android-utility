package br.com.lvc.utility.connection;

import java.io.IOException;

import android.util.Log;

public abstract class WebServiceComun {
	
	private SynchronousHttpConnection synchronousHttpConnection = new SynchronousHttpConnection();
	
	public  <T> T sendDataPUT(String url,Passable passable, Class<T> targetClass) throws IOException, HttpConnectionException {
		String response = null;
		try {
			String json = DataSerializer.getInstance().toJson(passable);
			Log.i("Dados enviados", json);

			response = synchronousHttpConnection.put(url, json);
			Log.i("Resposta Servidor", response); 

			T t = DataSerializer.getInstance().toObject(response, targetClass);

			return t;

		} catch (IOException e) {
			throw e;
		} catch (HttpConnectionException e) {
			throw new HttpConnectionException("Resposta do servidor: " + response, e);
		}
	}

	public  <T> T sendDataPOST(String url,Passable passable, Class<T> targetClass) throws IOException, HttpConnectionException {
		String json = DataSerializer.getInstance().toJson(passable);
		Log.i("Dados enviados", json);

		String response = synchronousHttpConnection.post(url, json);
		Log.i("Resposta Servidor", response);

		T t = DataSerializer.getInstance().toObject(response, targetClass);

		return t;
	}

	public  <T> T sendDataGet(String url, Class<T> targetClass) throws IOException, HttpConnectionException {
		String response = synchronousHttpConnection.get(url);
		T t = DataSerializer.getInstance().toObject(response, targetClass);

		return t;
	}
	
	public  <T> T sendDataGetLonger(String url, Class<T> targetClass) throws IOException, HttpConnectionException {
		String response = synchronousHttpConnection.exexuteGetHTTPLonger(url);
		T t = DataSerializer.getInstance().toObject(response, targetClass);

		return t;
	}

}
