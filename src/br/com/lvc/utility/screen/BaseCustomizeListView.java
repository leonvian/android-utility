package br.com.lvc.utility.screen;

import java.util.List;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.com.lvc.utility.R;
import br.com.lvc.utility.exceptions.AndroidAppException;
import br.com.lvc.utility.taskcontrol.SimpleTask;
import br.com.lvc.utility.taskcontrol.TaskResult;

import com.markupartist.android.widget.ActionBar;

public abstract class BaseCustomizeListView<T, Z extends ArrayAdapter<T>>  extends BaseListActivity {

	protected ListView listView;
	protected Z adapter;
	public  List<T> elements = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myOnCreate();
	}


	protected void myOnCreate() {
		setContentView(layoutID());
		loadOnCreate();
	}

	public void removeActionBar() {
		View view = findViewById(R.id.actionbar);
		if(view != null)
			view.setVisibility(View.GONE);
	}

	/**
	 * Primeiro método a ser acionado logo após setar o contentView.
	 */
	protected void loadOnCreate() {
		listView = getListView();
		configureActionBar();
		buildList();
	}

	private void configureActionBar() {
		View view = findViewById(R.id.actionbar);
		if(view != null) {
			ActionBar actionBar = (ActionBar) view; 
			configureActionBar(actionBar);	
		} 
	}


	public void buildList() {
		SimpleTask simpleTask = getSimpleTask();
		executeTask(simpleTask);
	}

	protected int layoutID() {
		return R.layout.customize_list;
	}


	private SimpleTask getSimpleTask() {

		SimpleTask simpleTask = new SimpleTask() {

			@Override
			public TaskResult executeTask() throws AndroidAppException {
				TaskResult taskResult = new TaskResult();
				elements = getListElements();				
				return taskResult;
			}

			@Override
			public void processAfterTask(TaskResult taskResult) {
				configureListViewProcessAfterTask();
			}


			@Override
			public void processAfterFailTask(AndroidAppException e) {
				if(elements != null)
					configureListViewProcessAfterTask();
				treatFailGeneral(e);
			}
		};

		return simpleTask;
	}


	public void configureListViewProcessAfterTask() {
		if(listView != null) {
			adapter = newAdapter(elements);         
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					T selectedObject = adapter.getItem(position);
					onClick(selectedObject); 
				}
			});

			listView.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> arg0,	View arg1, int position, long arg3) {
					T selectedObject = adapter.getItem(position);
					onItemLongClickEvent(selectedObject);
					return false;
				}
			});
		}
	}

	public void onItemLongClickEvent(T selectedObject) {

	}

	public void treatFailGeneral(AndroidAppException e) {
		showMessageErro(e);
	}

	public void showMessageErro(AndroidAppException e) {

		switch (e.getMode()) {
		case AndroidAppException.MODE_TOAST:
			showMessageInToastMode(e);

			break;
		default:
			showMessageInDialogMode(e);
			break;
		}
	}

	private void showMessageInToastMode(AndroidAppException e) {
		showMessageToastLong(e.getMessageFromResource());
	}

	private void showMessageInDialogMode(AndroidAppException e) {
		DialogInterface.OnClickListener event = new  OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				finish();

			}
		};
		showMessageError(e.getMessageFromResource(), event);	
	}


	protected TextWatcher getTextWatcher() {
		TextWatcher textWatcher = new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
				try {
					adapter.getFilter().filter(cs);	
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { 
			}

			@Override
			public void afterTextChanged(Editable arg0) {
			}
		};

		return textWatcher;
	}

	public void configureActionBar(ActionBar actionBar) { }

	public abstract void onClick(T clickedElement);

	public abstract Z newAdapter(List<T> elements);

	public abstract List<T> getListElements() throws AndroidAppException;
}