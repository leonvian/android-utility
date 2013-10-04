package br.com.lvc.utility.util;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FontUtil {

	private static final String FONT_PATH = "fonts/DroidSans.ttf";//"fonts/Face Your Fears.ttf";

	public static void colocarFonteBotao(Context context, View view) {

		Typeface font = Typeface.createFromAsset(context.getAssets(), FONT_PATH);
		
		if(view instanceof Button) {
			Button button = (Button)view;		
			button.setTypeface(font);
		} else if(view instanceof EditText) {
			EditText editText = (EditText)view;		
			editText.setTypeface(font);
		}

	}

}
