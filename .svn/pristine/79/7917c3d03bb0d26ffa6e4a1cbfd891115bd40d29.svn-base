package br.com.lvc.utility.screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public abstract class SplashScreen extends Activity implements Runnable {
	
	private Handler handle = new Handler();
	
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(getLayout());
		setTitle("");
		handle.postDelayed(this, getDelay());				
	}

	@Override
	public void run() {
		Intent intent = new Intent(this,getNextScreen());
		startActivity(intent);
		finish();
	}
	
	public abstract Class getNextScreen();
	
	public abstract int getLayout();
	
	public abstract int getDelay();
	
	
     
}