package br.com.lvc.utility.exceptions;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

public class EssentialFieldException extends AndroidAppException {


	
	private static final long serialVersionUID = 4194732802341833342L;
	
	private List<View> fieldsNotLoaded;

	public EssentialFieldException(int message, List<View> fieldsNotLoaded) {
		super(message);
		this.fieldsNotLoaded = fieldsNotLoaded;
	}
	
	
	public List<View> getFieldsNotLoaded() {
		return fieldsNotLoaded;
	}
	
	
	public void putErrorsOnViews() {
		if(fieldsNotLoaded != null) {
			for(View viewWrong : fieldsNotLoaded) {
				EditText editText = (EditText)viewWrong;
				Context context = viewWrong.getContext();
				String mensagem = context.getString(getMessageFromResource());
				editText.setError(mensagem);
			}	
		}
	}
}
