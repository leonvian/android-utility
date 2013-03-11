package br.com.lvc.utility.util.loadimage;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import br.com.lvc.utility.R;

public class ImageLoader {

	

	MemoryCache memoryCache = new MemoryCache();
	FileCache fileCache;
	private Map<ImageView, String> imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
	ExecutorService executorService; 

	private static ImageLoader instance;
	int stub_id = R.drawable.no_image;
	private int tipo = FileImageDecoder.IMAGE_VIEW;

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}


	public static ImageLoader getInstance(Context context, int tipo) {
		instance = getInstance(context);
		instance.setTipo(tipo);

		return instance;
	}

	public static ImageLoader getInstance(Context context) {
		if(instance == null)
			instance = new ImageLoader(context);

		return instance;
	}

	public ImageLoader(Context context){
		fileCache = new FileCache(context);
		executorService = Executors.newFixedThreadPool(5);
	}

	public void displayImage(String url, ImageView imageView) {
		displayImage( url,  imageView, null);
	}

	public void displayImage(String url, ImageView imageView, ProgressBar progressBar) {
		displayImage(url, imageView, progressBar,0);
	}

	public void displayImage(String url, ImageView imageView, int noImage) {
		displayImage(url, imageView, null,noImage);
	}

	public void displayImage(String url, ImageView imageView, ProgressBar progressBar, int noImage) {
		if(noImage != 0)
			stub_id = noImage;
		
		imageViews.put(imageView, url);
		
		initializeUIInterface(imageView, progressBar);

		if(url == null) {
			imageView.setVisibility(View.VISIBLE);
			turnOffProgressBarIfNoNull(progressBar);
		} else {
			Bitmap bitmap = memoryCache.get(url);
			
			if(bitmap != null) 
				showImageViewCached(imageView, bitmap, progressBar);
			else  
				queuePhoto(url, imageView,progressBar);
		}
	}

	private void initializeUIInterface(ImageView imageView, ProgressBar progressBar) {
		imageView.setImageResource(stub_id);
		
		if(progressBar != null) {
			progressBar.setVisibility(View.VISIBLE);
			imageView.setVisibility(View.GONE);
		} else {
			imageView.setVisibility(View.VISIBLE);
		}
	}


	private void showImageViewCached(ImageView imageView, Bitmap bitmap, ProgressBar progressBar) {
		Log.i("IMAGE LOAD", "Carregando imagem pela memória RAM");
		imageView.setImageBitmap(bitmap);
		imageView.setVisibility(View.VISIBLE);
		turnOffProgressBarIfNoNull(progressBar);		
	}

	private void turnOffProgressBarIfNoNull(ProgressBar progressBar) {
		if(progressBar != null)
			progressBar.setVisibility(View.GONE);

	}

	private void queuePhoto(String url, ImageView imageView, ProgressBar progressBar) {
		PhotoToLoad p = new PhotoToLoad(url, imageView, progressBar);
		PhotosLoader photosLoader = new PhotosLoader(p);
		executorService.submit(photosLoader);
	}

	private class PhotosLoader implements Runnable {

		PhotoToLoad photoToLoad;

		PhotosLoader(PhotoToLoad photoToLoad){
			this.photoToLoad = photoToLoad;
		}

		@Override
		public void run() {
			if(imageViewReused(photoToLoad))
				return;

			Bitmap bmp = getBitmap(tipo,photoToLoad.url);
			memoryCache.put(photoToLoad.url, bmp);

			if(imageViewReused(photoToLoad)) 
				return;
			
				

			diplayImageByThread(bmp, photoToLoad);
		}
	}

	private void diplayImageByThread(Bitmap bmp, PhotoToLoad photoToLoad) {
		BitmapDisplayerUIThread bd = new BitmapDisplayerUIThread(bmp, photoToLoad);
		Activity a = (Activity)photoToLoad.imageView.getContext();
		a.runOnUiThread(bd);
	}

	protected Bitmap getBitmap(int tipo,String url)  {
		File f = fileCache.getFile(url);
		try {		
			
			Bitmap bitmap = decodeFile(tipo,f);
			if(bitmap != null)
				return bitmap;
			else {
				bitmap = getImageByWeb(url, f,tipo);
				return bitmap;
			}
			
		} catch (Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
	
	private  Bitmap decodeFile(int tipo, File file) {

		if(tipo == FileImageDecoder.LIST_VIEW) {
			return FileImageDecoder.decodeFileListView(file);
		} else {
			return FileImageDecoder.decodeFile(file);
		}
	}

	private Bitmap getImageByWeb(String url, File f, int tipo) throws IOException {
		Bitmap bitmap = ImageDownloader.getImageByWeb(url, f, tipo);		
		return bitmap;
	}

	boolean imageViewReused(PhotoToLoad photoToLoad){
		String tag = imageViews.get(photoToLoad.imageView);
		if(tag == null || !tag.equals(photoToLoad.url)) {
			return true;
		}
			

		return false;
	}

	//Used to display bitmap in the UI thread
	private class BitmapDisplayerUIThread implements Runnable {

		Bitmap bitmap;
		PhotoToLoad photoToLoad;

		public BitmapDisplayerUIThread(Bitmap b, PhotoToLoad p) {
			bitmap = b;
			photoToLoad = p;
		}

		public void run()	{
		
			if(imageViewReused(photoToLoad))
				return;

			if(bitmap != null) {
				photoToLoad.imageView.setImageBitmap(bitmap);
			} else {
				photoToLoad.imageView.setImageResource(stub_id);
		
			}

			dismissProgressBarAndShowImage();
		
		}


		private void dismissProgressBarAndShowImage() {
			if(photoToLoad.progressBar != null)
				photoToLoad.progressBar.setVisibility(View.GONE);

			photoToLoad.imageView.setVisibility(View.VISIBLE);
		}
	}

	private class PhotoToLoad {

		public String url;
		public ImageView imageView;
		public ProgressBar progressBar;

		public PhotoToLoad(String u, ImageView i,ProgressBar p){
			url = u; 
			imageView=i;
			progressBar = p;
		}
	}


	public void clearCache() {
		memoryCache.clear();
		fileCache.clear();
	}

}
