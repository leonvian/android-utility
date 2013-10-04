package br.com.lvc.utility.screen.component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import br.com.lvc.utility.util.FontUtil;


public class EditTextDroidSans  extends EditText{

	public EditTextDroidSans(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		FontUtil.colocarFonteBotao(context, this);
	}
	
	public EditTextDroidSans(Context context, AttributeSet attrs) {
		super(context, attrs);
		FontUtil.colocarFonteBotao(context, this);
	}
	
	public EditTextDroidSans(Context context) {
		super(context);
		FontUtil.colocarFonteBotao(context, this);
	}
}
