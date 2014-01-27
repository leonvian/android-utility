package br.com.lvc.utility.screen.smartreminder;

import android.view.View;
import android.view.ViewGroup;

public abstract class SmartReminderClickListener  {
	
	public void onClickSmartReminderButton(ViewGroup smartReminderView) {
		onClick();
		smartReminderView.setVisibility(View.GONE);
	}

	
	public abstract void onClick();
 

}
