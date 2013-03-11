package br.com.lvc.utility.util;

import java.util.Observable;



public abstract class ProgressTaskRunnable extends Observable implements Runnable {

	public ProgressTaskRunnable() {
	}

	public void refreshView(String updateMessage) {		
		LogHandler.log(" " + updateMessage);
		setChanged(); 		
		notifyObservers(updateMessage);		
	}

	public abstract void onPostAction();
}