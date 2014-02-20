package br.com.lvc.utility.util;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FontUtil {

	private static final String DROID_SANS_FONT = "fonts/DroidSans.ttf";//"fonts/Face Your Fears.ttf";
	
	public static void putFontOnView(Context context, View view) {
		putFontOnView(context, view, DROID_SANS_FONT);
	}

	public static void putFontOnView(Context context, View view, String fontName) { 
		Typeface font = Typeface.createFromAsset(context.getAssets(), fontName);
		
		if(view instanceof Button) {
			Button button = (Button)view;		
			button.setTypeface(font);
		} else if(view instanceof TextView) {
			TextView editText = (TextView)view;		
			editText.setTypeface(font);
		}

	}

}
