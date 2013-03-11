package br.com.lvc.utility.util.loadimage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;


public class FileImageDecoder {
	
	public static final int LIST_VIEW = 1;
	public static final int IMAGE_VIEW = 2;
	
	private static final int WIDTH_DEFAULT = 460;
	private static final int HEIGHT_DEFAULT = 288;
	
	public static Bitmap decodeFileListView(File file ) {
		int width = WIDTH_DEFAULT / 2;
		int heigth = HEIGHT_DEFAULT / 2;
		return decodeFile(file, width, heigth);
	}
	
	public static Bitmap decodeFile(File file ) {
		return decodeFile(file, WIDTH_DEFAULT, HEIGHT_DEFAULT);
	}
	
	public static Bitmap decodeFile(File file, int dstWidth,int dstHeight ) {
		
		if(file == null || !file.exists()) {
			return null;
		}
		
		try {
		    int inWidth = 0;
		    int inHeight = 0;
	
		    InputStream in = new FileInputStream(file);

		    // decode image size (decode metadata only, not the whole image)
		    BitmapFactory.Options options = new BitmapFactory.Options();
		    options.inJustDecodeBounds = true;
		    BitmapFactory.decodeStream(in, null, options);
		    in.close();
		    in = null;

		    // save width and height
		    inWidth = options.outWidth;
		    inHeight = options.outHeight;
		    

		    // decode full image pre-resized
		    in = new FileInputStream(file);
		    options = new BitmapFactory.Options();
		    // calc rought re-size (this is no exact resize)
		    options.inSampleSize = Math.max(inWidth/dstWidth, inHeight/dstHeight);
		    // decode full image
		    Bitmap roughBitmap = BitmapFactory.decodeStream(in, null, options);

		    if(roughBitmap == null) {
		    	return null;
		    }
		    // calc exact destination size
		    Matrix m = new Matrix();
		    RectF inRect = new RectF(0, 0, roughBitmap.getWidth(), roughBitmap.getHeight());
		    RectF outRect = new RectF(0, 0, dstWidth, dstHeight);
		    m.setRectToRect(inRect, outRect, Matrix.ScaleToFit.CENTER);
		    float[] values = new float[9];
		    m.getValues(values);

		    // resize bitmap
		    Bitmap resizedBitmap = Bitmap.createScaledBitmap(roughBitmap, (int) (roughBitmap.getWidth() * values[0]), (int) (roughBitmap.getHeight() * values[4]), true);
		    
		    return resizedBitmap;
		} catch (IOException e) {
		    e.printStackTrace();
		    return null;
		}
		
	}

}
