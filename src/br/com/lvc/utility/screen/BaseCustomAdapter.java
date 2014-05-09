package br.com.lvc.utility.screen;

import java.util.ArrayList;
import java.util.List;

import br.com.lvc.utility.R;
import br.com.lvc.utility.taskcontrol.SimpleTask;
import br.com.lvc.utility.taskcontrol.TaskManager;
import br.com.lvc.utility.util.ProgressTask;
import br.com.lvc.utility.util.ProgressTaskRunnable;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Filter;

public abstract class BaseCustomAdapter<T>  extends ArrayAdapter<T>  {
	
	protected GenericFilter filter;
	protected Activity activity;
	protected List<T> data;
	protected List<T> dataComplete;
		
	public BaseCustomAdapter(Activity a, List<T> d) {
		super(a, 0, d);
		activity = a;
		data = d;
		dataComplete = new ArrayList<T>(data);
	}

	public BaseCustomAdapter(Context context, int resource, List<T> objects) {
		super(context, resource, objects);
		data = objects;
		dataComplete = new ArrayList<T>(data);
	}
	
	public void goToNextScreenWithFlag(Class<? extends Activity> nextScreen, int flag) {
		ScreenManager.getInstance().loadNextScreenFlag(activity,nextScreen,flag);
	}

	public void goToNextScreen(Class<? extends Activity> nextScreen) {
		ScreenManager.getInstance().loadNextScreen(activity,nextScreen);
	}

	public void goToNextScreen(Class<? extends Activity> nextScreen,Bundle bundle) {
		ScreenManager.getInstance().loadNextScreenByApplication(activity,nextScreen,bundle);
	}

	public void goToNextScreen(Class<? extends Activity> nextScreen,int requestCode) {
		ScreenManager.getInstance().loadNextScreen(activity,nextScreen,requestCode);
	}

	public void goToNextScreen(Class<? extends Activity> nextScreen,Bundle bundle, int requestCode) {
		ScreenManager.getInstance().loadNextScreenByApplication(activity,nextScreen,bundle,requestCode);
	}
	
	protected void executeTask(ProgressTaskRunnable task) {
		ProgressTask progressTask = new ProgressTask(activity, task);
		progressTask.execute();	
	}

	protected void executeTask(SimpleTask task) {
		TaskManager taskManager = new TaskManager(activity, task);
		taskManager.execute();
	}

	protected void executeTask(SimpleTask task, int message) {
		TaskManager taskManager = new TaskManager(activity, task, message, R.string.empty);
		taskManager.execute();
	}

	@Override
	public Filter getFilter() {
		if(filter == null) {
			filter = new GenericFilter();
		}
		
		return filter;
	}
	
	protected class GenericFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence constraint){
			FilterResults results = new FilterResults();
			String prefix = constraint.toString().toLowerCase();
			
			ArrayList<T> list = new ArrayList<T>(dataComplete);
			
			if (prefix == null || prefix.length() == 0) {
				results.values = list;
				results.count = list.size();
			} else {
				ArrayList<T> nlist = new ArrayList<T>();
				int count = list.size();

				for (int i = 0; i < count; i++){
					final T element = list.get(i);
					final String value = element.toString().toLowerCase();

					if(value.contains(prefix)){
						nlist.add(element);
					}
					results.values = nlist;
					results.count = nlist.size();
				}
			}
			
			return results;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			data = (List<T>)results.values;
			notifyDataSetChanged();
			clear();
			if(data != null) {
				int count = data.size();
				for(int i = 0; i <count; i++){
					add(data.get(i));
					notifyDataSetInvalidated();
				}	
			}

		}
	}
}