package br.com.lvc.utility.screen;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import br.com.lvc.utility.BaseApplicationUI;
import br.com.lvc.utility.R;
import br.com.lvc.utility.connection.ConnectionUtil;
import br.com.lvc.utility.exceptions.EssentialFieldException;
import br.com.lvc.utility.exceptions.IncorrectFieldsException;
import br.com.lvc.utility.exceptions.InsufficientDataException;
import br.com.lvc.utility.taskcontrol.SimpleTask;
import br.com.lvc.utility.taskcontrol.TaskManager;
import br.com.lvc.utility.util.PhoneUtil;
import br.com.lvc.utility.util.ProgressTask;
import br.com.lvc.utility.util.ProgressTaskRunnable;
import br.com.lvc.utility.util.VideoUTIL;
import br.com.lvc.utility.util.WebUTIL;

public class FragmentBase extends Fragment {

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

	protected void showMessageToastLong(int message) {
		ScreenManager.getInstance().showMessageToastLong(getActivity(), message);
	}

	protected void showMessageToastShort(int message) {
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

	protected void executeTask(SimpleTask task) {
		TaskManager taskManager = new TaskManager(getActivity(), task);
		taskManager.execute();
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


}
