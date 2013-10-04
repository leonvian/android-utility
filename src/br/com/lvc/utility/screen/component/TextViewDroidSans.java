package br.com.lvc.utility.screen.component;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

//com.lvc.bolaopedrao.TextViewBolao
public class TextViewDroidSans  extends TextView {

	private static final String FONT_PATH = "fonts/DroidSans.ttf";//"fonts/Face Your Fears.ttf";
	
	public TextViewDroidSans(Context context) {
		super(context);
		configureFont();
	}
	
	public TextViewDroidSans(Context context, AttributeSet attrs) {
		super(context, attrs);
		configureFont();
	}
	public TextViewDroidSans(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		configureFont();
	}
	 
    private void configureFont() {
        TextView txtGhost = this;
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), FONT_PATH);
        txtGhost.setTypeface(tf);
    }
	
}
