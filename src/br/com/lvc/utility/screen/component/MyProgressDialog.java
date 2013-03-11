package br.com.lvc.utility.screen.component;

import java.io.Serializable;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;

public class MyProgressDialog extends ProgressDialog implements Serializable, OnCancelListener{

	private static final long serialVersionUID = -172337044821495493L;
	
	public MyProgressDialog(Context context) {
		super(context);
		setOnCancelListener(this);
	}

	@Override
	public void onCancel(DialogInterface dialog) {	
		this.show();		
	}
		
	
}