package br.com.lvc.utility.taskcontrol;

import android.os.Bundle;
import br.com.lvc.utility.exceptions.AndroidAppException;

public class TaskResult {
	

	private AndroidAppException androidException;
	private Bundle bundle;
	
	
	public TaskResult(AndroidAppException androidException, Bundle bundle) {
		super();
		this.androidException = androidException;
		this.bundle = bundle;
	}
	
	public TaskResult() {
	}

	
	public boolean isTaskSucess() {
		if(androidException == null) {
			return true;
		}			
		return false;
	}

	public AndroidAppException getAndroidException() {
		return androidException;
	}


	public void setAndroidException(AndroidAppException androidException) {
		this.androidException = androidException;
	}


	public Bundle getBundle() {
		return bundle;
	}


	public void setBundle(Bundle bundle) {
		this.bundle = bundle;
	}
	
	
	
	

}
