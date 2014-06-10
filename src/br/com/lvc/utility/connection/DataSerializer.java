package br.com.lvc.utility.connection;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataSerializer {
	
	 
	ObjectMapper objectMapper = null;

	private static  DataSerializer instance = null;
	
	public static final String DEFAULT_FORMAT_DATE = "yyyy-MM-dd HH:mm a z";


	private DataSerializer(DataSerializerMapperConfiguration dataSerializerMapperConfiguration) {
		objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.setSerializationInclusion(Include.NON_NULL);  
		
		DateFormat df = new SimpleDateFormat(dataSerializerMapperConfiguration.getFormatDate());
		objectMapper.setDateFormat(df);
	}
	
	
	private DataSerializer() {
		this(new DataSerializerMapperConfiguration(DEFAULT_FORMAT_DATE));
	}
	
	public static DataSerializer getInstance(DataSerializerMapperConfiguration dataSerializerMapperConfiguration) {
		if(instance == null) {
			instance = new DataSerializer(dataSerializerMapperConfiguration);
		}

		return instance;
	}



	public static DataSerializer getInstance() {
		if(instance == null) {
			instance = new DataSerializer();
		}

		return instance;
	}

	public String toJson(Object content)   {
		try {
			return objectMapper.writeValueAsString(content);	
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
 
	public<T>  T toObject(String json, Class targetClass)  {
		try {
			return (T) objectMapper.readValue(json, targetClass);	
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

}