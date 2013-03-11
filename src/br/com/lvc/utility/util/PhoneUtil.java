package br.com.lvc.utility.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;
import br.com.lvc.utility.R;
import br.com.lvc.utility.screen.ScreenManager;

public class PhoneUtil {


	public static void makeAPhoneCall(String telefoneNumero, Context context) {
		if(telefoneNumero == null || telefoneNumero.length() == 0) {
			Toast.makeText(context, R.string.telefone_nao_informado, Toast.LENGTH_SHORT).show();
		} else {
			Intent intent = new Intent(Intent.ACTION_CALL);
			intent.setData(Uri.parse("tel:" +telefoneNumero));
			context.startActivity(intent);
		}
	}

	public static void askToMakeAPhoneCall(final Context context, final String telefoneNumero) {
		askToMakeAPhoneCall(R.string.gostaria_realizar_ligacao, context, telefoneNumero);
		
	}

	public static void askToMakeAPhoneCall(int message, final Context context, final String telefoneNumero) {
		OnClickListener positiveEventAction = getPositiveEvent(telefoneNumero, context);
		ScreenManager.getInstance().showDialogYesNo(R.string.attention, message, context, positiveEventAction, null, ScreenManager.MSG_POSITIVE);
	}


	private static OnClickListener getPositiveEvent(final String telefoneNumero, final Context context) {
		OnClickListener positiveEventAction = new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				dialog.dismiss();
				makeAPhoneCall(telefoneNumero, context);
			}
		};

		return positiveEventAction;
	}
}
