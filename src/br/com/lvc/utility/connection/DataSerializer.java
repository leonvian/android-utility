package br.com.lvc.utility.connection;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import br.com.lvc.utility.R;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataSerializer {
	
	 
	ObjectMapper objectMapper = null;

	private static  DataSerializer instance = null;
	
	private static final String FORMAT_DATE = "yyyy-MM-dd HH:mm a z";


	private DataSerializer() {
		objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.setSerializationInclusion(Include.NON_NULL);  
		DateFormat df = new SimpleDateFormat(FORMAT_DATE);
		objectMapper.setDateFormat(df);
	}


	public static DataSerializer getInstance() {
		if(instance == null) {
			instance = new DataSerializer();
		}

		return instance;
	}

	public String toJson(Object content) throws HttpConnectionException {
		try {
			return objectMapper.writeValueAsString(content);	
		} catch(Exception e) {
			throw new HttpConnectionException(R.string.falha_ao_serializar, e);
		}

	}

	public  DataBundle toObject(String json) throws HttpConnectionException {
		try {
			return objectMapper.readValue(json, DataBundle.class);
		} catch(Exception e) {
			throw new HttpConnectionException(R.string.falha_ao_serializar, e);
		}
	}

	public<T>  T toObject(String json, Class targetClass) throws HttpConnectionException {
		try {
			return (T) objectMapper.readValue(json, targetClass);	
		} catch(Exception e) {
			throw new HttpConnectionException(R.string.falha_ao_serializar, e);
		}
	}

}