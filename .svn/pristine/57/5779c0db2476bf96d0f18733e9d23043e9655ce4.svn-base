package br.com.lvc.utility.connection;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

public class DataSerializer {
	
	ObjectMapper objectMapper = null;
	
	private static  DataSerializer instance = null;
	
	
	private DataSerializer() {
		objectMapper = new ObjectMapper();
	}
	
	
	public static DataSerializer getInstance() {
		if(instance == null) {
			instance = new DataSerializer();
		}
		
		return instance;
	}

	public String toJson(Object content) throws IOException {
		objectMapper.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.getSerializationConfig().setSerializationInclusion(Inclusion.NON_NULL);
		
		return objectMapper.writeValueAsString(content);
	}
	
	public  DataBundle toObject(String json) throws JsonParseException, JsonMappingException, IOException {
		return objectMapper.readValue(json, DataBundle.class);	
	}
	
	public<T>  T toObject(String json, Class targetClass) throws JsonParseException, JsonMappingException, IOException {
		return (T) objectMapper.readValue(json, targetClass);	
	}
	
}