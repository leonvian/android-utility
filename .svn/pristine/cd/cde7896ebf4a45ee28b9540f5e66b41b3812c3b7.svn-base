package br.com.lvc.utility.util;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;

public class ButtonsEffects {


	public static void setImageClickEffect(View v) {
		v.setOnTouchListener(lstTouch);
		v.setSoundEffectsEnabled(true);
		v.playSoundEffect(android.view.SoundEffectConstants.CLICK);
	}

	private static OnTouchListener lstTouch = new OnTouchListener() {


		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if(v instanceof ImageView) {
				treatImageButton(event, (ImageView) v);	
			} else if(v instanceof Button) {
				treatButton(event, (Button)v);
			}

			return false;
		}


		private void treatImageButton(MotionEvent event, ImageView button) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				button.setColorFilter(Color.argb(150, 0, 0, 0));
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				button.setColorFilter(Color.argb(0, 155, 155, 155)); 									
			} else if(event.getAction() == MotionEvent.ACTION_CANCEL) {
				button.setColorFilter(Color.argb(0, 155, 155, 155)); 		
			}
		}

		private void treatButton(MotionEvent event, Button button) {

			if(event.getAction() == MotionEvent.ACTION_DOWN) {
				putColorFilterOnDrawables(button.getCompoundDrawables(), Color.argb(150, 0, 0, 0));
			} else if(event.getAction() == MotionEvent.ACTION_UP) {
				putColorFilterOnDrawables(button.getCompoundDrawables(),Color.argb(0, 155, 155, 155));
			} else if(event.getAction() == MotionEvent.ACTION_CANCEL) {
				putColorFilterOnDrawables(button.getCompoundDrawables(),Color.argb(0, 155, 155, 155));
			}
		}
		
	   
		private void putColorFilterOnDrawables(Drawable[] drawables, int argb) {
			if(drawables != null && drawables.length > 0) {
				for(Drawable d : drawables) {
					if(d != null) {
						d.setColorFilter(argb,PorterDuff.Mode.SRC_ATOP);	
					}
				}	
			}
		}

	};		

}