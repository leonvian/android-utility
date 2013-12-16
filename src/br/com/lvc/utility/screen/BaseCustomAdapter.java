package br.com.lvc.utility.screen;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
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