package br.com.lvc.utility.screen;

import java.io.Serializable;
import java.util.List;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextWatcher;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import br.com.lvc.utility.R;
import br.com.lvc.utility.exceptions.AndroidAppException;
import br.com.lvc.utility.screen.component.MyTextWatcher;
import br.com.lvc.utility.taskcontrol.SimpleTask;
import br.com.lvc.utility.taskcontrol.TaskResult;


public abstract class ListGeneric<T extends Serializable> extends BaseListActivity implements OnItemClickListener{


	protected LinearLayout linearLayoutPrincipal;
	protected ListView listView = null;
	protected EditText editTextfilterText = null;
	private TextWatcher filterTextWatcher = null;

	protected T currentElement = null;
	protected List<T> elements = null;	  
	protected  ArrayAdapter<T> adapter = null;



	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(idLayout());				
		linearLayoutPrincipal = (LinearLayout)findViewById(R.id.linear_layout_main);

		SimpleTask simpleTask = getTaskLoadList();
		executeTask(simpleTask);
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
		adapter = new ArrayAdapter<T>(ListGeneric.this, R.layout.list_item, elements);		
		filterTextWatcher = new MyTextWatcher(adapter);	   
		editTextfilterText = (EditText) findViewById(R.id.search_box);   
		
		if(editTextfilterText != null) {
			editTextfilterText.requestFocus();
			editTextfilterText.addTextChangedListener(filterTextWatcher);
			editTextfilterText.setFocusable(true);	
		}
				
		setListAdapter(adapter);

		listView  = getListView();		
		listView.setOnItemClickListener(ListGeneric.this);
	}
	
	public void afterFailTask(AndroidAppException e) {
		DialogInterface.OnClickListener event = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				dialog.dismiss();
				finish();
			}
		};
		showMessageError(e.getMessageFromResource(), event);
	}

	@Override 
	protected void onDestroy() {   
		if(editTextfilterText != null && filterTextWatcher != null)
			editTextfilterText.removeTextChangedListener(filterTextWatcher);
		super.onDestroy();  
	}


	protected abstract List<T> getListElements() throws AndroidAppException;

}