package br.com.lvc.utility.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;
import br.com.lvc.utility.R;

public class WebUTIL {
	
	
	public static void goToWebSite(String url, Context context) {
		if(url == null || url.length() == 0) {
			Toast.makeText(context, R.string.web_site_nao_informado, Toast.LENGTH_SHORT).show();
		} else {
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse(url));
			context.startActivity(i);	
		}
	}

}
