package br.com.lvc.utility.screen.component;

import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import br.com.lvc.utility.R;
import br.com.lvc.utility.util.ParseUtil;

public class DatePickerListener implements OnClickListener {

	private EditText editTextDate = null;
	private Context context = null;
	
	public DatePickerListener(Context context) {
		this.context = context;	
	}	

	@Override
	public void onClick(View v) {	
		this.editTextDate = (EditText)v;
		Calendar c = Calendar.getInstance();
		if (!editTextDate.getText().toString().equals("")) {
			String data = editTextDate.getText().toString();					
			Date date = ParseUtil.toDate(data);
			c.setTime(date);
	
		}	
		
		DatePickerDialog datePickerDialog = new DatePickerCustomize(context, mDateSetListener, c);
		String limpar = context.getString(R.string.clean);
		datePickerDialog.setButton(DatePickerDialog.BUTTON_NEUTRAL,limpar, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				editTextDate.setText("");			
			}
		});
		datePickerDialog.show();
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener =  new DatePickerDialog.OnDateSetListener() {      	
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {    
			Calendar calendar = Calendar.getInstance();
			calendar.set(year, monthOfYear, dayOfMonth);
			
			Date date = calendar.getTime();			               		    
			editTextDate.setText(ParseUtil.toString(date));
		}     
	};
}