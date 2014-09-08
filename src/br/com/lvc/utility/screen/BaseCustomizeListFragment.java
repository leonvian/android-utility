package br.com.lvc.utility.screen;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import br.com.lvc.utility.BaseApplicationUI;
import br.com.lvc.utility.R;
import br.com.lvc.utility.connection.ConnectionUtil;
import br.com.lvc.utility.exceptions.AndroidAppException;
import br.com.lvc.utility.exceptions.EssentialFieldException;
import br.com.lvc.utility.exceptions.IncorrectFieldsException;
import br.com.lvc.utility.exceptions.InsufficientDataException;
import br.com.lvc.utility.taskcontrol.SimpleTask;
import br.com.lvc.utility.taskcontrol.TaskManager;
import br.com.lvc.utility.taskcontrol.TaskResult;
import br.com.lvc.utility.util.PhoneUtil;
import br.com.lvc.utility.util.ProgressTask;
import br.com.lvc.utility.util.ProgressTaskRunnable;
import br.com.lvc.utility.util.VideoUTIL;
import br.com.lvc.utility.util.WebUTIL;

import com.markupartist.android.widget.ActionBar;

public abstract class BaseCustomizeListFragment<T, Z extends BaseCustomAdapter<T>> extends ListFragment {


	protected ListView listView;
	protected Z adapter;
	public  List<T> elements = null;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,	Bundle savedInstanceState) {
		View view = inflateView(inflater, container, savedInstanceState);
		configureActionBar(view);
		listView = (ListView) view.findViewById(android.R.id.list);
		buildList();
		removeActionBar(view);
		return  view;
	}
	
	public View inflateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(layoutID(), null);
		return view;
	}

	public void removeActionBar(View viewMain) {
		View view = viewMain.findViewById(R.id.actionbar);
		if(view != null)
			view.setVisibility(View.GONE);
	}


	private void configureActionBar(View viewMain) {
		View view = viewMain.findViewById(R.id.actionbar);
		if(view != null) {
			ActionBar actionBar = (ActionBar) view; 
			configureActionBar(actionBar);	
		} 
	}


	public void buildList() {
		SimpleTask simpleTask = getSimpleTask();
		executeTask(simpleTask);
	}


	protected void executeTask(SimpleTask task) {
		TaskManager taskManager = new TaskManager(getActivity(), task);
		taskManager.execute();
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
				treatFailGeneral(e);
			}
		};

		return simpleTask;
	}


	public void configureListViewProcessAfterTask() {
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
		case AndroidAppException.MODE_DIALOG:
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
			}
		};
		ScreenManager.getInstance().showDialog(R.string.error, e.getMessageFromResource(), getActivity(), event,ScreenManager.MSG_ERROR);	
	}

	private void showMessageToastLong(int message) {
		Toast.makeText(getActivity(), getString(message), Toast.LENGTH_SHORT).show();
	}

 
	public void configureActionBar(ActionBar actionBar) { }

	public abstract void onClick(T clickedElement);

	public abstract Z newAdapter(List<T> elements);

	public abstract List<T> getListElements() throws AndroidAppException;


	private Handler handler = new Handler(); 
 

	public  boolean isTablet() {
		return ScreenManager.getInstance().isTablet(getActivity());		 
	}

	public void showVideo(String url) {
		VideoUTIL.exibirVideo(getActivity(), url);
	}

	public boolean isDeviceConnected() {
		return ConnectionUtil.isDeviceConnected(getActivity());
	}

	public  boolean isTabletAndAndroid3(Context context) {
		return ScreenManager.getInstance().isTabletAndAndroid3(getActivity());
	}

	public  boolean isAndroid3_OrNewer() {
		return ScreenManager.getInstance().isAndroid3_OrNewer(getActivity());
	}


	protected InputFilter[] retrieveCaracterLimit(int limit) {
		return ScreenManager.getInstance().retrieveCaracterLimit(limit);
	}

	protected boolean verifyObrigatoryFields(int message, EditText... editTexts) {
		return ScreenManager.getInstance().verifiedObrigatoryFields(getActivity(),message, editTexts);
	}

	protected boolean verifyObrigatoryFields(EditText... editTexts) {
		return ScreenManager.getInstance().verifiedObrigatoryFields(getActivity(), editTexts);
	}	

	protected void verifySuficientFields(int limitMinimum,int message, EditText... editTexts) throws InsufficientDataException {
		ScreenManager.getInstance().verifiedSuficientFields(limitMinimum, message, editTexts);
	}	

	protected void verifyInvalidCaracterFields(int message,EditText... editTexts) throws IncorrectFieldsException  {
		ScreenManager.getInstance().verifiedInvalidCharacter(message,editTexts);
	}

	protected void invisibleFields(View... views) {
		ScreenManager.getInstance().putFieldsInvisible(views);
	}

	protected void visibleFields(View... views) {
		ScreenManager.getInstance().putFieldsVisible(views);
	}

	protected void disableFields(View... views) {
		ScreenManager.getInstance().disableFields(views);
	}

	protected void enableFields(View... views) {
		ScreenManager.getInstance().enableFields(views);
	}

	protected ArrayAdapter<CharSequence> getSpinnerCommomAdapter(int idArray) {
		return ScreenManager.getInstance().getAdapterToSpinner(getActivity(), idArray);
	}

	protected <T>ArrayAdapter<T> getSpinnerCommomAdapter(List<T> array) {
		return ScreenManager.getInstance().getAdapterToSpinner(getActivity(), array);
	}	

	protected void showMessageError(int message, DialogInterface.OnClickListener event) {
		ScreenManager.getInstance().showDialog(R.string.error, message, getActivity(), event,ScreenManager.MSG_ERROR);
	}

	protected void showMessageSucess(int message, DialogInterface.OnClickListener event) {
		ScreenManager.getInstance().showDialog(R.string.sucess, message, getActivity(), event, ScreenManager.MSG_POSITIVE);
	}	

	protected void showMessageSucessAndFinishOnClickOk(int message) {
		ScreenManager.getInstance().showDialogWithEventFinishActivity(R.string.sucess, message, getActivity(), ScreenManager.MSG_POSITIVE);
	}	

	protected void showMessageSucessAndCloseDialog(int message) {
		ScreenManager.getInstance().showDialogWithEventCloseDialog(R.string.sucess, message, getActivity(), ScreenManager.MSG_POSITIVE);
	}	


	protected void showMessageAttention(int message) {
		ScreenManager.getInstance().showDialog(R.string.attention, message, getActivity(), ScreenManager.MSG_ATTENTION);
	}

	protected void showMessageAttention(String message) {
		ScreenManager.getInstance().showDialog(R.string.attention, message, getActivity(), ScreenManager.MSG_ATTENTION);
	}

	protected void showMessageAttention(String message,DialogInterface.OnClickListener event) {
		ScreenManager.getInstance().showDialog(R.string.attention, message, getActivity(), event, ScreenManager.MSG_ATTENTION);
	}

	protected void showMessageAttention(int message,DialogInterface.OnClickListener event) {
		showMessageAttention(getString(message), event);
	}

	protected void showMessageToastShort(int message) {
		ScreenManager.getInstance().showMessageToastShort(getActivity(), message);
	}
	
	
	protected void showMessageToastLong(String message) {
		ScreenManager.getInstance().showMessageToastLong(getActivity(), message);
	}

	protected void showMessageToastShort(String message) {
		ScreenManager.getInstance().showMessageToastShort(getActivity(), message);
	}


	protected void showMessageAttentionYesNo(int message, DialogInterface.OnClickListener yesEvent) {
		ScreenManager.getInstance().showDialogYesNo(R.string.attention, message, getActivity(),yesEvent, null,ScreenManager.MSG_ATTENTION);
	}

	protected void showMessageWarningToExit() {
		ScreenManager.getInstance().showMessageToExit(getActivity());
	}

	protected void showMessageWithOptionsYesAndNo(int message, DialogInterface.OnClickListener eventYes) {
		showMessageWithOptionsYesAndNo(message, eventYes, null);
	}

	protected void showMessageWithOptionsYesAndNo(int message, DialogInterface.OnClickListener eventYes, DialogInterface.OnClickListener eventNo) {
		ScreenManager.getInstance().showDialogYesNo(R.string.attention, message, getActivity(), eventYes, eventNo, ScreenManager.MSG_ATTENTION);
	}

	protected void showMessageWithOptionsYesAndNo(String message, DialogInterface.OnClickListener eventYes, DialogInterface.OnClickListener eventNo) {
		ScreenManager.getInstance().showDialogYesNo(R.string.attention, message, getActivity(), eventYes, eventNo, ScreenManager.MSG_ATTENTION);
	}



	protected Bundle getBundleFromApplication() {
		return ScreenManager.getInstance().getBundleFromApplication(getActivity());
	}

	public void putBundleOnApplication(Bundle bundle) {
		ScreenManager.getInstance().putBundleOnApplication(bundle, getActivity());
	}

	public void goToNextScreenWithFlag(Class<? extends Activity> nextScreen, int flag) {
		ScreenManager.getInstance().loadNextScreenFlag(getActivity(),nextScreen,flag);
	}

	public void goToNextScreen(Class<? extends Activity> nextScreen) {
		ScreenManager.getInstance().loadNextScreen(getActivity(),nextScreen);
	}

	public void goToNextScreen(Class<? extends Activity> nextScreen,Bundle bundle) {
		ScreenManager.getInstance().loadNextScreenByApplication(getActivity(),nextScreen,bundle);
	}

	public void goToNextScreen(Class<? extends Activity> nextScreen,int requestCode) {
		ScreenManager.getInstance().loadNextScreen(getActivity(),nextScreen,requestCode);
	}

	public void goToNextScreen(Class<? extends Activity> nextScreen,Bundle bundle, int requestCode) {
		ScreenManager.getInstance().loadNextScreenByApplication(getActivity(),nextScreen,bundle,requestCode);
	}

	public void dismissKeyboard(EditText editText) {
		ScreenManager.getInstance().dismissKeyboard(getActivity(), editText);
	}


	public void makeAPhoneCall(String phone) {
		PhoneUtil.makeAPhoneCall(phone, getActivity());
	}

	public void goToWebSite(String url) {
		WebUTIL.goToWebSite(url, getActivity());
	}

	protected void doHandleActionDelayed(Runnable r,long delayMillis) {		
		handler.postDelayed(r, delayMillis);		
	}

	protected void doHandleAction(Runnable r) {		
		handler.post(r);		
	}

	protected BaseApplicationUI getBaseApplication() {
		return ScreenManager.getInstance().getBaseApplication(getActivity());
	}

	protected void executeTask(ProgressTaskRunnable task) {
		ProgressTask progressTask = new ProgressTask(getActivity(), task);
		progressTask.execute();	
	}


	protected void executeTask(SimpleTask task, int message) {
		TaskManager taskManager = new TaskManager(getActivity(), task, message, R.string.attention);
		taskManager.execute();
	}


	public void takePhoto() {
		ScreenManager.getInstance().dispatchTakePictureIntent(getActivity());
	}

	public void share() {
		ScreenManager.getInstance().shareIntent(getActivity());
	}

	public void recycleImageView(ImageView imageView ) {
		ScreenManager.getInstance().recycleImageView(imageView);
	}

	public void hideKeyBoard() {
		try {
			InputMethodManager inputManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
			View viewInFocus = getActivity().getCurrentFocus();
			if(viewInFocus != null)
				inputManager.hideSoftInputFromWindow(viewInFocus.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);	
		} catch (Exception e) {
			e.printStackTrace();
		}

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

}
