package br.com.lvc.utility.util;

import android.util.Log;

public class LogHandler {

	private static boolean showLogError =  true;
	private static boolean showLogDebug = true;
	private static boolean showLogWarn = true;
	private static boolean showLogInfo = true;


	public static void log(String log) {
		if(showLogDebug) {
			Log.d("LOG", log);
		}
	}

	public static void logWarn(String log) {
		if(showLogWarn) {
			Log.w("LOG", log);
		}
	}	

	public static void logInfo(String str) {
		if(showLogInfo && str != null) {

			if(str.length() > 4000) {
				Log.i("",str.substring(0, 4000));
				logInfo(str.substring(4000));
			} else
				Log.i("",str);

		}
	}	

	public static void logLogin(String log) {
		if(showLogInfo) {
			Log.i("LOG", log);
		}
	}	

	public static void logSinc(String log) {
		if(showLogInfo) {
			Log.i("LOG", log);
		}
	}

	public static void logErro(String log) {
		if(showLogError) {
			Log.e("LOG", log);
		}
	}	

	public static void activate() {
		showLogError = true;
		showLogDebug = true;
		showLogInfo = true;
		showLogWarn = true;

	}

	public static void desactivate() {
		showLogError = false;
		showLogDebug = false;
		showLogInfo = false;
		showLogWarn = false;

	}
}