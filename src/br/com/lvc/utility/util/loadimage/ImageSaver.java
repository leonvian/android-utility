package br.com.lvc.utility.util.loadimage;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;

public class ImageSaver {

	private ExecutorService executorService;
	private Context context;



	public ImageSaver(Context context) {
		executorService = Executors.newFixedThreadPool(5);
	}

	public  void saveAllImages(final List<String> urls) {
		for(String url : urls) {
			saveImage(url)	;
		}
	}

	private  void saveImage(final String url) {

		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				
				try {
					ImageDownloader.getImageByWebAndPutOnCache(url, context);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		
		executorService.submit(runnable);

	}

}
