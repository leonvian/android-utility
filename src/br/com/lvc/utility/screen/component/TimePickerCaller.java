package br.com.lvc.utility.screen.component;

import java.util.Calendar;
import java.util.Date;

import android.app.TimePickerDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TimePicker;

public class TimePickerCaller implements OnClickListener,TimePickerDialog.OnTimeSetListener  {


	TimePickerDialog timePickerDialog = null;
	private EditText editText;
	private int hourSugested;
	private int minuteSugested;

	public TimePickerCaller() {

	}


	public TimePickerCaller(int hourSugested, int minuteSugested) {
		super();
		this.hourSugested = hourSugested;
		this.minuteSugested = minuteSugested;
	}


	@Override
	public void onClick(View view) {
		editText = (EditText)view;
		int hourOfDay = 0;
		int minute = 0;
		if(hourSugested == 0 || minuteSugested == 0) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
			minute = calendar.get(Calendar.MINUTE);
		} else {
			hourOfDay = hourSugested;
			minute = minuteSugested;
		}
		
		timePickerDialog = new TimePickerDialog(view.getContext(), this, hourOfDay, minute, true);
		timePickerDialog.show();
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		timePickerDialog.dismiss();
		editText.setText(hourOfDay + ":" + minute);
	}
}