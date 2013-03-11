package br.com.lvc.utility.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class BitmapUtil {

	public static int HORIZONTAL = 2;
	public static int VERTICAL = 1;

	public static Bitmap[] cut(Bitmap raw, int count, int direction) {

		int width = raw.getWidth();
		int height = raw.getHeight();
		int partHeight = (direction == VERTICAL ? height/count : height);
		int partWidth = (direction == HORIZONTAL ? width/count : height);
		Bitmap[] result = new Bitmap[count];

		if(direction == HORIZONTAL){
			for(int i=0; i<count; i++) {
				result[i] = Bitmap.createBitmap(raw, i * partWidth, 0, partWidth, partHeight);
			}
		} else {
			for(int i=0; i<count; i++) {
				result[i] = Bitmap.createBitmap(raw, 0, i * partHeight, partWidth, partHeight);
			}

		}

		return result;

	}



	public static Bitmap clearBlack(Bitmap mBitmap) {

		int picw = mBitmap.getWidth(), pich = mBitmap.getHeight();
		int[] pix = new int[picw * pich];
		mBitmap.getPixels(pix, 0, picw, 0, 0, picw, pich);
		for (int y = 0; y < pich; y++)
			for (int x = 0; x < picw; x++) {
				int index = y * picw + x;
				int r = (pix[index] >> 16) & 0xff;
				int g = (pix[index] >> 8) & 0xff;
				int b = pix[index] & 0xff;

				if(r < 50 && g < 50 && b < 50){
					pix[index] = 0x00ffffff;
				}
			}

		Bitmap bm = Bitmap.createBitmap(picw, pich, Config.ARGB_4444);
		bm.setPixels(pix, 0, picw, 0, 0, picw, pich);
		return bm;

	}

	public static String toString64(Bitmap bmp) {
		if(bmp == null)
			return null;
		
		byte[] byteArrayImage = toBytes(bmp);
		String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
		
		return encodedImage;
	}

	public static byte[] toBytes(Bitmap bmp) {
		if(bmp == null)
			return null;

		byte[] byteArray  = null;

		try {

			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			try {
				bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
				byteArray = stream.toByteArray();	
			} finally {
				stream.close();	
			}

		} catch(IOException e) {
			e.printStackTrace();
		}


		return byteArray;
	}

	public static Bitmap getBitmapFromURL(String src) {
		try {
			URL url = new URL(src);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			return myBitmap;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}