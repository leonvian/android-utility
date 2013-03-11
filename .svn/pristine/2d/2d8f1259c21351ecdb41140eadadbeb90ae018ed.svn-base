package br.com.lvc.utility.taskcontrol;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import br.com.lvc.utility.R;
import br.com.lvc.utility.exceptions.AndroidAppException;
import br.com.lvc.utility.screen.component.MyProgressDialog;

public class TaskManager extends AsyncTask<Void, String, TaskResult>{


	private Context context;
	private ProgressDialog progressDialog;
	private int messageProgress = R.string.processing;
	private int titleProgress = R.string.attention;
	private SimpleTask task;

	public TaskManager(Context context,SimpleTask task,int messageProgress,int titleProgress) {
		this(context, task);
		this.messageProgress = messageProgress;
		this.titleProgress = titleProgress;
	}

	public TaskManager(Context context,SimpleTask task) {
		this.context = context;
		this.task = task;
	}


	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		showProgressDialog();
	}
	
	@Override
	protected void onProgressUpdate(String... values) {
		super.onProgressUpdate(values);
	}
	
	@Override
	protected TaskResult doInBackground(Void... params) {
		TaskResult taskResult = new TaskResult();
		try {
			taskResult = task.executeTask();
		} catch (AndroidAppException e) {
			e.printStackTrace();
			taskResult.setAndroidException(e);
		}

		return taskResult;
	}

	@Override
	protected void onPostExecute(TaskResult result) {
		closeProgressDialog();
		if(result.isTaskSucess()){
			task.processAfterTask(result);
		} else {
			task.processAfterFailTask(result.getAndroidException());
		}

	}

	private void closeProgressDialog() {
		if(progressDialog != null)
			progressDialog.dismiss();
	}

	private void showProgressDialog() {
		progressDialog = new MyProgressDialog(context);
		progressDialog.setTitle(titleProgress);
		progressDialog.setMessage(context.getString(messageProgress));
		progressDialog.setIndeterminate(true);
		progressDialog.show();	  
	}

}
