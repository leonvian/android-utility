package br.com.lvc.utility.screen.component;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

//br.com.lvc.utility.screen.component.TextViewDroidSansBold
public class TextViewDroidSansBold  extends TextView {

	private static final String FONT_PATH = "fonts/DroidSans-Bold.ttf";//"fonts/Face Your Fears.ttf";
	
	public TextViewDroidSansBold(Context context) {
		super(context);
		configureFont();
	}
	
	public TextViewDroidSansBold(Context context, AttributeSet attrs) {
		super(context, attrs);
		configureFont();
	}
	public TextViewDroidSansBold(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		configureFont();
	}
	 
    private void configureFont() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), FONT_PATH);
        setTypeface(tf);
    }
	
}