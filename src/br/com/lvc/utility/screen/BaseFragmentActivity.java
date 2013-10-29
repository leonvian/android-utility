package br.com.lvc.utility.screen;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import br.com.lvc.utility.BaseApplicationUI;
import br.com.lvc.utility.R;
import br.com.lvc.utility.connection.ConnectionUtil;
import br.com.lvc.utility.exceptions.EssentialFieldException;
import br.com.lvc.utility.exceptions.IncorrectFieldsException;
import br.com.lvc.utility.exceptions.InsufficientDataException;
import br.com.lvc.utility.screen.annotation.FindViewByID;
import br.com.lvc.utility.screen.annotation.Obrigatory;
import br.com.lvc.utility.taskcontrol.SimpleTask;
import br.com.lvc.utility.taskcontrol.TaskManager;
import br.com.lvc.utility.util.ButtonsEffects;
import br.com.lvc.utility.util.PhoneUtil;
import br.com.lvc.utility.util.ProgressTask;
import br.com.lvc.utility.util.ProgressTaskRunnable;
import br.com.lvc.utility.util.VideoUTIL;
import br.com.lvc.utility.util.WebUTIL;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class BaseFragmentActivity extends FragmentActivity {


	public static final int PROGRESS_DIALOG = 0;
	private Handler handler = new Handler();
	public static final int PROGRESS_DIALOG_ID = 10;

	private List<ScreenView>  screenViews = new ArrayList<ScreenView>();

	public  boolean isTablet() {
		return ScreenManager.getInstance().isTablet(this);		 
	}

	public void showVideo(String url) {
		VideoUTIL.exibirVideo(this, url);
	}

	public boolean isDeviceConnected() {
		return ConnectionUtil.isDeviceConnected(this);
	}

	public  boolean isTabletAndAndroid3(Context context) {
		return ScreenManager.getInstance().isTabletAndAndroid3(this);
	}

	public  boolean isAndroid3_OrNewer() {
		return ScreenManager.getInstance().isAndroid3_OrNewer(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);			
	}


	protected void setLayoutAndinstanciateElementsByAnnotation(int resID) {
		setContentView(resID);
		try {
			Field[] fields = getClass().getDeclaredFields();
			for(Field field : fields) {
				if(field.isAnnotationPresent(FindViewByID.class)) {
					FindViewByID findViewByID = field.getAnnotation(FindViewByID.class);
					long id = findViewByID.id();
					View view = findViewById((int)id);
					if(view instanceof Button || view instanceof ImageButton)
						ButtonsEffects.setImageClickEffect(view);
					field.setAccessible(true);
					field.set(this, view);
					boolean obrigatory = field.isAnnotationPresent(Obrigatory.class);
					ScreenView screenView = new ScreenView(view, obrigatory);
					screenViews.add(screenView);
				}

			}
		} catch(Exception e) {
			throw new RuntimeException(e);
		}		
	} 


	protected EditText getEditText(int resource, int caracterLimit, boolean obrigatory) {
		return null;	
	}

	protected Button getButton(int idRes) {
		return (Button)findViewById(idRes);
	}

	protected InputFilter[] retrieveCaracterLimit(int limit) {
		return ScreenManager.getInstance().retrieveCaracterLimit(limit);
	}

	protected void verifyEssentialFields(int message) throws EssentialFieldException {
		List<View> viewsWrong = new ArrayList<View>();

		for(ScreenView screenView : screenViews) {
			View view =  screenView.getView();
			if(view instanceof EditText) {
				EditText editText = (EditText)view;
				if(screenView.isObrigatory() && editText.getText().toString().length() == 0) {
					viewsWrong.add(view);
				}
			}
		}

		if(!viewsWrong.isEmpty()) {
			throw new EssentialFieldException(message, viewsWrong);
		}

	}

	protected boolean verifyObrigatoryFields(int message, EditText... editTexts) {
		return ScreenManager.getInstance().verifiedObrigatoryFields(this,message, editTexts);
	}

	protected boolean verifyObrigatoryFields(EditText... editTexts) {
		return ScreenManager.getInstance().verifiedObrigatoryFields(this, editTexts);
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
		return ScreenManager.getInstance().getAdapterToSpinner(this, idArray);
	}

	protected <T>ArrayAdapter<T> getSpinnerCommomAdapter(List<T> array) {
		return ScreenManager.getInstance().getAdapterToSpinner(this, array);
	}	

	protected void showMessageError(int message, DialogInterface.OnClickListener event) {
		ScreenManager.getInstance().showDialog(R.string.error, message, this, event,ScreenManager.MSG_ERROR);
	}

	protected void showMessageSucess(int message, DialogInterface.OnClickListener event) {
		ScreenManager.getInstance().showDialog(R.string.sucess, message, this, event, ScreenManager.MSG_POSITIVE);
	}	

	protected void showMessageSucessAndFinishOnClickOk(int message) {
		ScreenManager.getInstance().showDialogWithEventFinishActivity(R.string.sucess, message, this, ScreenManager.MSG_POSITIVE);
	}	

	protected void showMessageSucessAndCloseDialog(int message) {
		ScreenManager.getInstance().showDialogWithEventCloseDialog(R.string.sucess, message, this, ScreenManager.MSG_POSITIVE);
	}	


	protected void showMessageAttention(int message) {
		ScreenManager.getInstance().showDialog(R.string.attention, message, this, ScreenManager.MSG_ATTENTION);
	}

	protected void showMessageAttention(String message) {
		ScreenManager.getInstance().showDialog(R.string.attention, message, this, ScreenManager.MSG_ATTENTION);
	}

	protected void showMessageAttention(String message,DialogInterface.OnClickListener event) {
		ScreenManager.getInstance().showDialog(R.string.attention, message, this, event, ScreenManager.MSG_ATTENTION);
	}

	protected void showMessageAttention(int message,DialogInterface.OnClickListener event) {
		showMessageAttention(getString(message), event);
	}

	protected void showMessageToastLong(int message) {
		ScreenManager.getInstance().showMessageToastLong(this, message);
	}

	protected void showMessageToastShort(int message) {
		ScreenManager.getInstance().showMessageToastShort(this, message);
	}
	
	protected void showMessageToastLong(String message) {
		ScreenManager.getInstance().showMessageToastLong(this, message);
	}

	protected void showMessageToastShort(String message) {
		ScreenManager.getInstance().showMessageToastShort(this, message);
	}


	protected void showMessageAttentionYesNo(int message, DialogInterface.OnClickListener yesEvent) {
		ScreenManager.getInstance().showDialogYesNo(R.string.attention, message, this,yesEvent, null,ScreenManager.MSG_ATTENTION);
	}

	protected void showMessageWarningToExit() {
		ScreenManager.getInstance().showMessageToExit(this);
	}
	
	protected void showMessageWithOptionsYesAndNo(int message, DialogInterface.OnClickListener eventYes) {
		showMessageWithOptionsYesAndNo(message, eventYes, null);
	}

	protected void showMessageWithOptionsYesAndNo(int message, DialogInterface.OnClickListener eventYes, DialogInterface.OnClickListener eventNo) {
		ScreenManager.getInstance().showDialogYesNo(R.string.attention, message, this, eventYes, eventNo, ScreenManager.MSG_ATTENTION);
	}

	protected void showMessageWithOptionsYesAndNo(String message, DialogInterface.OnClickListener eventYes, DialogInterface.OnClickListener eventNo) {
		ScreenManager.getInstance().showDialogYesNo(R.string.attention, message, this, eventYes, eventNo, ScreenManager.MSG_ATTENTION);
	}

		

	protected Bundle getBundleFromApplication() {
		return ScreenManager.getInstance().getBundleFromApplication(this);
	}

	public void putBundleOnApplication(Bundle bundle) {
		ScreenManager.getInstance().putBundleOnApplication(bundle, this);
	}

	public void goToNextScreenWithFlag(Class<? extends Activity> nextScreen, int flag) {
		ScreenManager.getInstance().loadNextScreenFlag(this,nextScreen,flag);
	}

	public void goToNextScreen(Class<? extends Activity> nextScreen) {
		ScreenManager.getInstance().loadNextScreen(this,nextScreen);
	}

	public void goToNextScreen(Class<? extends Activity> nextScreen,Bundle bundle) {
		ScreenManager.getInstance().loadNextScreenByApplication(this,nextScreen,bundle);
	}

	public void goToNextScreen(Class<? extends Activity> nextScreen,int requestCode) {
		ScreenManager.getInstance().loadNextScreen(this,nextScreen,requestCode);
	}

	public void goToNextScreen(Class<? extends Activity> nextScreen,Bundle bundle, int requestCode) {
		ScreenManager.getInstance().loadNextScreenByApplication(this,nextScreen,bundle,requestCode);
	}



	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean press = false;
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			press = true;
			backButtonEvent();
		}
		return press;
	}

	protected void backButtonEvent() {
		finish();
	}

	public void dismissKeyboard(EditText editText) {
		ScreenManager.getInstance().dismissKeyboard(this, editText);
	}


	public void makeAPhoneCall(String phone) {
		PhoneUtil.makeAPhoneCall(phone, this);
	}

	public void goToWebSite(String url) {
		WebUTIL.goToWebSite(url, this);
	}

	protected void doHandleActionDelayed(Runnable r,long delayMillis) {		
		handler.postDelayed(r, delayMillis);		
	}

	protected void doHandleAction(Runnable r) {		
		handler.post(r);		
	}

	protected BaseApplicationUI getBaseApplication() {
		return ScreenManager.getInstance().getBaseApplication(this);
	}


	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case PROGRESS_DIALOG_ID:			
			ProgressDialog progressDialog = new ProgressDialog(this);
			progressDialog.setMessage(getString(R.string.processing));
			return progressDialog;
		default:
			break;

		}

		return super.onCreateDialog(id);
	}


	protected void executeTask(ProgressTaskRunnable task) {
		ProgressTask progressTask = new ProgressTask(this, task);
		progressTask.execute();	
	}

	protected void executeTask(SimpleTask task) {
		TaskManager taskManager = new TaskManager(this, task);
		taskManager.execute();
	}

	protected void executeTask(SimpleTask task, int message) {
		TaskManager taskManager = new TaskManager(this, task, message, R.string.attention);
		taskManager.execute();
	}


	public void takePhoto() {
		ScreenManager.getInstance().dispatchTakePictureIntent(this);
	}

	public void share() {
		ScreenManager.getInstance().shareIntent(this);
	}

	public void recycleImageView(ImageView imageView ) {
		ScreenManager.getInstance().recycleImageView(imageView);
	}

	public void hideKeyBoard(View view) {
		try {
			InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			View viewInFocus = this.getCurrentFocus();
			if(viewInFocus != null)
				inputManager.hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);	
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
}
