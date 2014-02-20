package br.com.lvc.utility.screen.component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import br.com.lvc.utility.util.FontUtil;



//br.com.lvc.utility.screen.component.ButtonDroidSans
public class ButtonDroidSans extends Button {

	public ButtonDroidSans(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		FontUtil.putFontOnView(context, this);
	}
	
	 
	public ButtonDroidSans(Context context) {
		super(context);
		FontUtil.putFontOnView(context, this);
	}

	public ButtonDroidSans(Context context, AttributeSet attrs) {
		super(context, attrs);
		FontUtil.putFontOnView(context, this);
	}
	
}