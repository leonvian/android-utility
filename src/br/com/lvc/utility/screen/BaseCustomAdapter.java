package br.com.lvc.utility.screen;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

public abstract class BaseCustomAdapter<T>  extends ArrayAdapter<T>  {

	protected Activity activity;
	protected List<T> data;
	protected static LayoutInflater inflater = null;



	public BaseCustomAdapter(Activity a, List<T> d) {
		super(a, 0, d);
		activity = a;
		data = d;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/*

	public int getCount() {
		return data.size();
	}

	public T getItem(int position) {
		return data.get(position);
	}

	public long getItemId(int position) {
		return position;
	} 

	
	@Override
	public Filter getFilter() {
		Filter filter = super.getFilter();
		return filter;
	}*/

}
