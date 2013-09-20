package br.com.lvc.utility.connection;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import br.com.lvc.utility.R;

public class DataSerializer {

	ObjectMapper objectMapper = null;

	private static  DataSerializer instance = null;


	private DataSerializer() {
		objectMapper = new ObjectMapper();
		objectMapper.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.setSerializationInclusion(Inclusion.NON_NULL);
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
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