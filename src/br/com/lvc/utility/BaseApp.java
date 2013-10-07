package br.com.lvc.utility;

import android.app.Application;
import android.os.Bundle;

public class BaseApp extends Application implements BaseApplicationUI {

	public static final String NEXT_SCREEN = "NEXT_SCREEN";

	Bundle bundle = new Bundle();

	@Override
	public Bundle getBundle() {
		return bundle;
	}

	@Override
	public void setBundle(Bundle bundle) {
		this.bundle = bundle;		
	} 

}
