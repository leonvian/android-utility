package br.com.lvc.utility.screen;

import java.io.Serializable;

import android.view.WindowManager;
import br.com.lvc.utility.R;

import com.markupartist.android.widget.ActionBar;

public abstract class ListGenericWithActionBar<T extends Serializable> extends ListGeneric<T> {
	
	public void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
		configureActionBar(actionBar);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}
	
	
	public abstract void configureActionBar(ActionBar actionBar);
	


}
