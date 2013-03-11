package br.com.lvc.utility.util;

import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.os.AsyncTask;
import br.com.lvc.utility.screen.BaseActivity;

public class ProgressTask extends AsyncTask<Void, Void, Void> implements Observer {

	private static final String PROCESSING =  "Processando...";
	private Activity activity;
	private String progressMessage = PROCESSING;
	private ProgressTaskRunnable runnable = null;		
	
	public ProgressTask(Activity activity, ProgressTaskRunnable task, String actionMessage) {
		this.activity = activity;
		this.progressMessage = actionMessage;
		this.runnable = task;
		runnable.addObserver(this);
	}

	public ProgressTask(Activity activity, ProgressTaskRunnable task) {		
		this(activity,task, PROCESSING);		
	}
			
	public ProgressTask(ProgressTaskRunnable task, Observer observer) {					
		this.progressMessage = PROCESSING;
		this.runnable = task;
		runnable.addObserver(observer);
	}
	
	
		
	public void addObserver() {
		runnable.addObserver(this);
	}	
	
	@Override
	protected Void doInBackground(Void... params) {		
        doTask();
		return null;
	}
	
	private void doTask() {
		 try {
  			Thread thread = new Thread(runnable);
  			thread.start();
  			thread.join();			
  		} catch (InterruptedException e) {			
  			e.printStackTrace();
  		}		
	}

	@Override
	protected void onPreExecute() {		
		activity.showDialog(BaseActivity.PROGRESS_DIALOG);
				
	} 
	
	@Override
	protected void onPostExecute(Void result) {		
		runnable.onPostAction();		
		activity.removeDialog(BaseActivity.PROGRESS_DIALOG);
	} 
	
	@Override
	protected void onProgressUpdate(Void... values) {		
		super.onProgressUpdate(values);	    
	}
	
	@Override
	public void update(Observable observable, Object data) {
		publishProgress();		
	    progressMessage = data.toString();		
	}
}