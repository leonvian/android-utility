package br.com.lvc.utility.util.loadimage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;

public class ImageDownloader {
	
	
	public static void getImageByWebAndPutOnCache(String url, Context context) throws IOException {
		
		FileCache fileCache = new FileCache(context);
		File f = fileCache.getFile(url);
		getImageByWeb(url, f, FileImageDecoder.IMAGE_VIEW,false);
		
	}
	
	public static Bitmap getImageByWeb(String url, File f, int tipo) throws IOException {
		Bitmap bitmap = getImageByWeb(url, f, tipo, true);
		return bitmap;
	}
	
	private static Bitmap getImageByWeb(String url, File f, int tipo, boolean wantReturn) throws IOException {
		URL imageUrl = new URL(url);
		HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
		conn.setConnectTimeout(30000);
		conn.setReadTimeout(30000);
		conn.setInstanceFollowRedirects(true);
		InputStream is = conn.getInputStream();
		OutputStream os = new FileOutputStream(f);
		Utils.CopyStream(is, os);
		os.close();
		if(wantReturn) {
			Bitmap bitmap = decodeFile(tipo,f);
			return bitmap;		
		} else {
			return null;
		}

	}

	private static Bitmap decodeFile(int tipo, File file) {

		if(tipo == FileImageDecoder.LIST_VIEW) {
			return FileImageDecoder.decodeFileListView(file);
		} else {
			return FileImageDecoder.decodeFile(file);
		}
	}

}
