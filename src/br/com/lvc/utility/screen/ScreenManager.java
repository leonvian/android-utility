package br.com.lvc.utility.screen;

import java.io.Serializable;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import br.com.lvc.utility.BaseApplicationUI;
import br.com.lvc.utility.R;
import br.com.lvc.utility.exceptions.IncorrectFieldsException;
import br.com.lvc.utility.exceptions.InsufficientDataException;


public class ScreenManager {


	private static ScreenManager instance = null;

	//Intent CODES
	public static final int TAKE_PHOTO = 10;

	public static final int MSG_POSITIVE = 1;
	public static final int MSG_ATTENTION = 2;
	public static final int MSG_ERROR = 3;
	public static final int BACK = 0;
	public static final int ADD = 1;
	public static final int SCRIPT = 2;
	public static final int SUSPENSE = 3;
	public static final int FINISH_GENERAL = 4;
	public static final int CANCEL = 5;
	public static final int SEARCH = 6;
	public static final int SAVE = 7;
	public static final int DELETE = 8;
	public static final int EDIT = 9;
	public static final int NEW = 10;
	public static final int NEXT = 11;
	public static final int VIEW = 12;
	public static final int PRINT = 13;
	private static final int LIMIT_MINIMUM = 0;


	private ScreenManager() {
	}

	public static ScreenManager getInstance() {
		if(instance == null) {
			instance = new ScreenManager();
		}
		return instance;
	}

	public  boolean isTablet(Context context) {
		return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;		 

	}

	public  boolean isTabletAndAndroid3(Context context) {
		return isAndroid3_OrNewer(context) && isTablet(context);
	}

	public  boolean isAndroid3_OrNewer(Context context) {
		/*int apiLevel = Build.VERSION_SDK_INT;
		if(apiLevel >= 11)
			return true;
		else
			return false; */ return true;
	}
	
	public static void dismissKeyboard(Activity activity,EditText editText) {
		InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
	}

	public static boolean isIntentAvailable(Context context, String action) {
		final PackageManager packageManager = context.getPackageManager();
		final Intent intent = new Intent(action);
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}
	
	public void recycleImageView(ImageView imageView) {
		if(imageView == null)
			return;
		
		Drawable drawable = imageView.getDrawable();
		if (drawable instanceof BitmapDrawable) {
			BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
			Bitmap bitmap = bitmapDrawable.getBitmap();
			bitmap.recycle();
		}	
	}
	

	public void shareIntent(Activity currentActivity) {		
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, "Subject of email");
		intent.putExtra(Intent.EXTRA_TEXT, "Body of email");
		currentActivity.startActivity(Intent.createChooser(intent, "Share"));

	}

	public void dispatchTakePictureIntent(Activity currentActivity) {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		
		if(isPackageExists(currentActivity,"com.google.android.camera")) {
			takePictureIntent = new Intent();
			takePictureIntent.setPackage("com.google.android.camera");
			takePictureIntent.setAction(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		} 
		
		currentActivity.startActivityForResult(takePictureIntent, TAKE_PHOTO);
	}
		
		public boolean isPackageExists(Activity currentActivity,String targetPackage){
		    List<ApplicationInfo> packages;
		    PackageManager pm;
		        pm = currentActivity.getPackageManager();        
		        packages = pm.getInstalledApplications(0);
		        for (ApplicationInfo packageInfo : packages) {
		    if(packageInfo.packageName.equals(targetPackage)) return true;
		    }        
		    return false;
		}


	public void loadNextScreenByApplication(Activity currentActivity, Class<?> nextScreen, Bundle bundle, int requestCode) {
		Intent intent = new Intent(currentActivity, nextScreen);
		BaseApplicationUI utilityApplication = (BaseApplicationUI)currentActivity.getApplicationContext();
		utilityApplication.setBundle(bundle);		
		currentActivity.startActivityForResult(intent,requestCode);
	}

	public void loadNextScreenByApplication(Activity currentActivity, Class<?> nextScreen, Bundle bundle) {
		Intent intent = new Intent(currentActivity, nextScreen);
		BaseApplicationUI utilityApplication = (BaseApplicationUI)currentActivity.getApplicationContext();
		utilityApplication.setBundle(bundle);		
		currentActivity.startActivity(intent);
	}

	public void loadNextScreen(Activity currentActivity, Class nextScreen, int requestCode) {
		Intent intent = new Intent(currentActivity, nextScreen);				
		currentActivity.startActivityForResult(intent, requestCode);	    
	}

	public void loadNextScreenWithBundle(Activity currentActivity, Class nextScreen,  Bundle bundle, int requestCode) {		 			
		Intent intent = new Intent(currentActivity, nextScreen);	
		intent.putExtras(bundle);
		currentActivity.startActivityForResult(intent, requestCode);	    		
	}

	public void loadNextScreenWithBundle(Activity currentActivity, Class nextScreen,  Bundle bundle) {	 
		Intent intent = new Intent(currentActivity, nextScreen);	
		intent.putExtras(bundle);
		currentActivity.startActivity(intent);	    		
	}
	
	public void loadNextScreenFlag(Activity currentActivity, Class nextScreen, int flag) {		 						
		Intent intent = new Intent(currentActivity, nextScreen);
		intent.setFlags(flag);
		currentActivity.startActivity(intent);			
	}

	public void loadNextScreen(Activity currentActivity, Class nextScreen) {		 						
		Intent intent = new Intent(currentActivity, nextScreen);           			
		currentActivity.startActivity(intent);			
	}

	public void showMenuOptions(BaseAdapter options, DialogInterface.OnClickListener listener, Activity currentActivity, int title ) {
		AlertDialog.Builder builder = new AlertDialog.Builder(currentActivity);
		builder.setTitle(title);		
		builder.setSingleChoiceItems(options, -1, listener);
		builder.setIcon(R.drawable.tools);
		builder.create();		
		builder.show();
	}

	public void showMenuOptions(String[] options, DialogInterface.OnClickListener listener, Activity currentActivity) {	
		AlertDialog.Builder builder = new AlertDialog.Builder(currentActivity);
		builder.setTitle(currentActivity.getString(R.string.options));		
		builder.setSingleChoiceItems(options, -1, listener);
		builder.setIcon(R.drawable.tools);
		builder.create();		
		builder.show(); 
	}

	public void showMenuOptions(int options, DialogInterface.OnClickListener listener, Activity currentActivity) {
		AlertDialog.Builder builder = new AlertDialog.Builder(currentActivity);
		builder.setTitle(currentActivity.getString(R.string.options));
		builder.setItems(options, listener);
		builder.setIcon(R.drawable.tools);
		builder.create();		
		builder.show();
	}

	public void showDialog(String title, String message, Context context, DialogInterface.OnClickListener eventAction, int type) {	
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message);		
		builder.setPositiveButton("OK", eventAction);
		AlertDialog dialog = builder.create();		
		dialog.setTitle(Html.fromHtml(title));
		int icon = getIconToDialog(type);
		dialog.setIcon(icon);
		dialog.show();
	}
	
	public void showDialogWithEventCloseDialog(int title, int message, final Context context, int type) {
		OnClickListener event = new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();			}
		};

		showDialog(title, message, context, event, type);
	}



	public void showDialogWithEventFinishActivity(int title, int message, final Context context, int type) {
		OnClickListener event = new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				dialog.dismiss();
				Activity activity = (Activity) context;
				activity.setResult(Activity.RESULT_OK);
				activity.finish();

			}
		};

		showDialog(title, message, context, event, type);
	}

	public void showDialog(int title, int message, Context context, DialogInterface.OnClickListener eventAction, int type) {	
		showDialog(context.getString(title), context.getString(message), context, eventAction, type);		
	}
	
	//ScreenManager.getInstance().showDialog(R.string.attention, message, this, ScreenManager.MSG_ATTENTION);
	
	public void showDialogAttention(int message, Context context) {	
		showDialog(R.string.attention, message, context, null,ScreenManager.MSG_ATTENTION);
	}


	public void showDialog(int title, int message, Context context, int type) {	
		showDialog(title, message, context, null,type);
	}

	public void showDialog(int title, String message, Context context, DialogInterface.OnClickListener eventAction, int type) {	
		showDialog(context.getString(title),message, context, eventAction, type);		
	}


	public void showDialog(int title, String message, Context context, int type) {	
		showDialog(title, message, context, null,type);
	}
	
	public void showMessageToastShort(Context context, String message) {
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		//toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	
	public void showMessageToastLong(Context context, String message) {
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
		//toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	
	public void showMessageToastShort(Context context, int message) {
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		//toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	
	public void showMessageToastLong(Context context, int message) {
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
		//toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	public void showDialogTwoButton(int title, int message,int labelButtonUm, int labelButtonDois, Context context, DialogInterface.OnClickListener positiveEventAction, DialogInterface.OnClickListener negativeEventAction, int type) {	
		showDialogTwoButton( title,  context.getString(message), labelButtonUm,  labelButtonDois,  context,  positiveEventAction,  negativeEventAction,  type);
	}

	public void showDialogTwoButton(int title, String message,int labelButtonUm, int labelButtonDois, Context context, DialogInterface.OnClickListener positiveEventAction, DialogInterface.OnClickListener negativeEventAction, int type) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);		
		builder.setMessage(message);		
		builder.setPositiveButton(context.getString(labelButtonUm), positiveEventAction);
		builder.setNegativeButton(context.getString(labelButtonDois), negativeEventAction);
		AlertDialog dialog = builder.create();
		dialog.getWindow().clearFlags(LayoutParams.FLAG_DIM_BEHIND);
		String titleDialog = getTitleToDialog(context,type, title);
		dialog.setTitle(Html.fromHtml(titleDialog));
		int icon = getIconToDialog(type);
		dialog.setIcon(icon);
		dialog.show();
	}

	public void showDialogYesNo(int title, int message, Context context, DialogInterface.OnClickListener positiveEventAction, DialogInterface.OnClickListener negativeEventAction, int type) {	
		showDialogTwoButton(title, message, R.string.yes, R.string.no, context, positiveEventAction, negativeEventAction, type);
	}

	public void showDialogYesNo(int title, String message, Context context, DialogInterface.OnClickListener positiveEventAction, DialogInterface.OnClickListener negativeEventAction, int type) {	
		showDialogTwoButton(title, message, R.string.yes, R.string.no, context, positiveEventAction, negativeEventAction, type);
	}


	public void showMessageToExit(Activity context) {	      
		class PositiveAnswerListener implements DialogInterface.OnClickListener {

			Activity context = null;
			public PositiveAnswerListener(Activity context) {
				this.context = context;
			}

			@Override
			public void onClick(DialogInterface dialog, int which) {
				context.finish();
			}
		}

		showDialogYesNo(R.string.attention, R.string.do_you_really_want_out, context, new PositiveAnswerListener(context), null, ScreenManager.MSG_ATTENTION);

	}

	private String getTitleToDialog(Context context, int type, int title) {
		String msg = null;
		String titleString = context.getString(title);
		if(type == MSG_ERROR) {
			msg = "<b><font color=#ff0000>"+ titleString +"</font></b>";
		} else if(type == MSG_ATTENTION) {
			msg = "<b><font color=#FFFFFF>"+ titleString +"</font></b>";
		} else if(type == MSG_POSITIVE) {
			msg = "<b><font color=#00FF00>"+ titleString +"</font></b>";
		}

		return msg;
	}

	private int getIconToDialog(int type) {
		int icon = 0;

		if(type == MSG_ATTENTION) {
			icon = R.drawable.exclamation;
		} else if(type == MSG_POSITIVE) {
			icon = R.drawable.ok;
		} else if(type == MSG_ERROR) {
			icon = R.drawable.erro;
		}

		return icon;
	}


	public void setResult(Activity currentActivity, int returnCode, String nameBundle, Serializable serializable) {
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putSerializable(nameBundle, serializable);	
		intent.putExtras(bundle);
		currentActivity.setResult(returnCode, intent);		
	}

	public void setResult(Activity currentActivity, int returnCode) {
		Intent intent = new Intent();		
		currentActivity.setResult(returnCode,intent);		
	}

	public Bundle getBundleFromApplication(Context context) {
		BaseApplicationUI baseApplication = (BaseApplicationUI)context.getApplicationContext();
		return baseApplication.getBundle();
	}

	public void putBundleOnApplication(Bundle bundle, Context context) {
		BaseApplicationUI baseApplication = (BaseApplicationUI)context.getApplicationContext();
		baseApplication.setBundle(bundle);
	}

	public BaseApplicationUI getBaseApplication(Context context) {
		BaseApplicationUI baseApplication = (BaseApplicationUI)context.getApplicationContext();
		return baseApplication;
	}

	public void disableFields(View... views) {
		for(View view : views) {
			if(view != null) {
				view.setFocusable(false);
				view.setEnabled(false);
				view.setClickable(false);	
			}			
		}
	}

	public void enableFields(View... views) {
		for(View view : views) {
			if(view != null) {
				view.setFocusable(true);
				view.setEnabled(true);
				view.setClickable(true);
				view.setFocusableInTouchMode(true);
			}			
		}
	}

	public void putFieldsVisible(View... views) {
		for(View view : views) {
			if(view != null) {
				view.setVisibility(View.VISIBLE);		
			}			
		}
	}

	public void putFieldsInvisible(View... views) {
		for(View view : views) {
			if(view != null) {
				view.setVisibility(View.INVISIBLE);	
			}			
		}
	}

	public ArrayAdapter<CharSequence> getAdapterToSpinner(Context context, int idArray) {      
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource (context, idArray, android.R.layout.simple_spinner_item);  
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  

		return adapter;
	}

	public <T>ArrayAdapter<T> getAdapterToSpinner(Context context, List<T> array) {      
		ArrayAdapter<T> adapter = new ArrayAdapter<T>(context, android.R.layout.simple_spinner_item, array);		  
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  

		return adapter;
	}

	
	public boolean verifiedObrigatoryFields(Context context, int messageErro, EditText... editTexts) {		
		boolean ok = true;
		for(EditText editText : editTexts) {
			if(editText.getText().toString().length()  == 0) {
				String error = context.getString(messageErro);
				editText.setError(error);
				ok = false;
			}
		}		
		return ok;
	}
	
	public boolean verifiedObrigatoryFields(Context context, EditText... editTexts)  {
		return verifiedObrigatoryFields(context, R.string.este_campo_e_obrigatorio, editTexts);
	}

/*
	public void verifiedEssentialFields(int message,EditText... editTexts) throws EssentialFieldException {		
		for(EditText editText : editTexts) {
			if(editText.getText().toString().length()  <= LIMIT_MINIMUM) {
				throw new EssentialFieldException(message);
			}
		}		
	} */

	public void verifiedSuficientFields(int limitMinimum, int message, EditText... editTexts) throws InsufficientDataException {		
		for(EditText editText : editTexts) {
			if(editText.getText().toString().length()  >= limitMinimum) {
				return;
			}
		}		
		throw new InsufficientDataException(message);
	}

	public void verifiedInvalidCharacter(int message, EditText... editTexts) throws IncorrectFieldsException {		
		for(EditText editText : editTexts) {
			if(editText.getText().toString().contains("%")) {
				throw new IncorrectFieldsException(message);				
			}
		}		
	}	

	public InputFilter[] retrieveCaracterLimit(int size){
		InputFilter[] filterArray = new InputFilter[1];
		filterArray[0] = new InputFilter.LengthFilter(size);
		return filterArray;
	}

}