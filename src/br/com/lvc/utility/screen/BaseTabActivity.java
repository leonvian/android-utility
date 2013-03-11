package br.com.lvc.utility.screen;

import br.com.lvc.utility.BaseApplicationUI;
import br.com.lvc.utility.R;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.TabHost;

public class BaseTabActivity extends TabActivity {
	
	@SuppressWarnings("rawtypes")
	protected void addTabHost(String tagName, int indicatorName, Class  tabActivity){
		Intent	intent = new Intent(this, tabActivity);
		TabHost.TabSpec tabSpec = getTabHost().newTabSpec(tagName);		
		tabSpec.setIndicator(getString(indicatorName));
		tabSpec.setContent(intent);
		getTabHost().addTab(tabSpec);
	}
	
	protected BaseApplicationUI getBaseApplication() {
		return ScreenManager.getInstance().getBaseApplication(this);
	}
	
	protected void showMessageError(int message, DialogInterface.OnClickListener event) {
		ScreenManager.getInstance().showDialog(R.string.error, message, this, event,ScreenManager.MSG_ERROR);
	}

	protected void showMessageSucess(int message, DialogInterface.OnClickListener event) {
		ScreenManager.getInstance().showDialog(R.string.sucess, message, this, event, ScreenManager.MSG_POSITIVE);
	}	
	
	protected void showMessageSucess(int message) {
		ScreenManager.getInstance().showDialogWithEventFinishActivity(R.string.sucess, message, this, ScreenManager.MSG_POSITIVE);
	}

	protected void showMessageAttention(int message) {
		ScreenManager.getInstance().showDialog(R.string.attention, message, this, ScreenManager.MSG_ATTENTION);
	}
	
	protected void showMessageAttention(int message, DialogInterface.OnClickListener event) {
		ScreenManager.getInstance().showDialog(R.string.attention, message, this,event, ScreenManager.MSG_ATTENTION);
	}

	protected void showMessageWarningToExit() {
		ScreenManager.getInstance().showMessageToExit(this);
	}

	protected void showMessageWithOptionsYesAndNo(int message, DialogInterface.OnClickListener eventYes, DialogInterface.OnClickListener eventNo) {
		ScreenManager.getInstance().showDialogYesNo(R.string.attention, message, this, eventYes, eventNo, ScreenManager.MSG_ATTENTION);
	}

	protected void showMessageWithOptionsYesAndNo(int message, DialogInterface.OnClickListener eventYes) {
		showMessageWithOptionsYesAndNo(message, eventYes, null);
	}	

}
