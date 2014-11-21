package br.com.lvc.utility.screen.component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import br.com.lvc.utility.util.FontUtil;

//br.com.lvc.utility.screen.component.EditTextDroidSans
public class EditTextDroidSans  extends EditText{

	public EditTextDroidSans(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		FontUtil.putFontOnView(context, this);
	}
	
	public EditTextDroidSans(Context context, AttributeSet attrs) {
		super(context, attrs);
		FontUtil.putFontOnView(context, this);
	}
	
	public EditTextDroidSans(Context context) {
		super(context);
		FontUtil.putFontOnView(context, this);
	}
}
