package br.com.lvc.utility.util.loadimage;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageLoaderAssetFile  extends ImageLoader {

	private Context context;

	private static ImageLoaderAssetFile instance;

	public static ImageLoaderAssetFile getInstance(Context context) {
		if(instance == null) 
			instance = new ImageLoaderAssetFile(context);
		return instance;
	}

	private ImageLoaderAssetFile(Context context) {
		super(context);
		this.context = context;
	}



	@Override
	protected Bitmap getBitmap(int tipo, String url) {
		Bitmap bitmap =  null;

		try {
			InputStream stream = context.getAssets().open(url);;
			try {
				bitmap = BitmapFactory.decodeStream(stream);
			} finally {
				if(stream != null)
					stream.close();
			}

		}catch (IOException e) {
			e.printStackTrace();
		}

		return bitmap;
	}


}
