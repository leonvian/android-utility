package br.com.lvc.utility.screen;

import java.io.Serializable;
import java.util.List;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import br.com.lvc.utility.R;
import br.com.lvc.utility.exceptions.AndroidAppException;
import br.com.lvc.utility.screen.component.MyTextWatcher;
import br.com.lvc.utility.taskcontrol.SimpleTask;
import br.com.lvc.utility.taskcontrol.TaskManager;
import br.com.lvc.utility.taskcontrol.TaskResult;

public abstract class ListGenericFragments<T extends Serializable> extends ListFragment implements OnItemClickListener{

	
	protected LinearLayout linearLayoutPrincipal;
	protected ListView listView = null;
	protected EditText editTextfilterText = null;
	private TextWatcher filterTextWatcher = null;

	protected T currentElement = null;
	protected List<T> elements = null;	  
	protected  ArrayAdapter<T> adapter = null;



	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);


		//getActivity().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


	}

	protected void executeTask(SimpleTask task) {
		TaskManager taskManager = new TaskManager(getActivity(), task);
		taskManager.execute();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(idLayout(), null);
		linearLayoutPrincipal = (LinearLayout)view.findViewById(R.id.linear_layout_main);
		editTextfilterText = (EditText) view.findViewById(R.id.search_box);
		SimpleTask simpleTask = getTaskLoadList();

		executeTask(simpleTask);
		return view;
	}


	public int idLayout() {
		return R.layout.list_generic;
	}

	private SimpleTask getTaskLoadList() {

		SimpleTask simpleTask = new SimpleTask() {

			@Override
			public TaskResult executeTask() throws AndroidAppException {
				TaskResult taskResult = new  TaskResult();
				elements = getListElements();  
				return taskResult;
			}

			@Override
			public void processAfterTask(TaskResult taskResult) {
				afterTask(taskResult);
			}

			@Override
			public void processAfterFailTask(AndroidAppException e) {
				afterFailTask(e);
			}
		};

		return simpleTask;
	}

	public void afterTask(TaskResult taskResult) {
		adapter = new ArrayAdapter<T>(getActivity(), getListItemId(), elements);		
		filterTextWatcher = new MyTextWatcher(adapter);	   
		   

		if(editTextfilterText != null) {
			editTextfilterText.requestFocus();
			editTextfilterText.addTextChangedListener(filterTextWatcher);
			editTextfilterText.setFocusable(true);	
		}

		setListAdapter(adapter);

		listView  = getListView();		
		listView.setOnItemClickListener(this);
	}


	public int getListItemId() {
		return  R.layout.list_item;
	}

	public void afterFailTask(AndroidAppException e) {
		DialogInterface.OnClickListener event = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				dialog.dismiss();
			}
		};
		ScreenManager.getInstance().showDialog(R.string.error, e.getMessageFromResource(), getActivity(), event,ScreenManager.MSG_ERROR);
	}
	
	@Override
	public void onDestroyView() {
		if(editTextfilterText != null && filterTextWatcher != null)
			editTextfilterText.removeTextChangedListener(filterTextWatcher);
		super.onDestroyView();
	}

	


	protected abstract List<T> getListElements() throws AndroidAppException;
	protected abstract void onClickItem(T element);


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
		T element = elements.get(pos);
		onClickItem(element);

	}

}