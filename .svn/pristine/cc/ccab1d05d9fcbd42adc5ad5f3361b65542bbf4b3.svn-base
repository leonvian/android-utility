package br.com.lvc.utility.connection;

import br.com.lvc.utility.util.LogHandler;

public class ProcessResponseImpl implements ProcessResponse {

	@Override
	public void treat(String dataResponse) {
		try {
			DataSerializer dataSerializer =  DataSerializer.getInstance();
			DataBundle dataBundle = dataSerializer.toObject(dataResponse);
			LogHandler.log("RETORNO: " + dataResponse + " " + dataBundle);
			LogHandler.logInfo("RETORNO: " + dataResponse+ " " + dataBundle);
			LogHandler.logLogin("RETORNO: " + dataResponse+ " " + dataBundle);
		} catch (Exception e) {
			
		}		
	}

}
