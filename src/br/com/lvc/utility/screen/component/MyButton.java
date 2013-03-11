package br.com.lvc.utility.screen.component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import br.com.lvc.utility.util.ButtonsEffects;

public class MyButton extends Button {

	public MyButton(Context context) {
		super(context);
		ButtonsEffects.setImageClickEffect(this);
	}

	public MyButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		ButtonsEffects.setImageClickEffect(this);
	}

	public MyButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		ButtonsEffects.setImageClickEffect(this);
	}



}
