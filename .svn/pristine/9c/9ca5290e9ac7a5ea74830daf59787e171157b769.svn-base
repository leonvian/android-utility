package br.com.lvc.utility.util.loadimage;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.content.Context;

public class FileCache {
    
	private static final String UTF_8 = "UTF-8"; 
    private File cacheDir;
    
    public FileCache(Context context){
        //Find the dir to save cached images
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir = new File(android.os.Environment.getExternalStorageDirectory(),"LazyList");
        else
            cacheDir = context.getCacheDir();
      
        if(!cacheDir.exists())
            cacheDir.mkdirs();
    }
    
    public File getFile(String url){
        //I identify images by hashcode. Not a perfect solution, good for the demo.
        //String filename = String.valueOf(url.hashCode());
        //Another possible solution (thanks to grantland)
        //String filename = URLEncoder.encode(url);
        String filename = null;
    	try {
			filename = URLEncoder.encode(url,UTF_8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			filename = String.valueOf(url.hashCode());
		}
        
        File f = new File(cacheDir, filename);
        return f;
        
    }
    
    public void clear() {
        File[] files = cacheDir.listFiles();
        
        if(files == null)
            return;
        
        for(File f : files)
            f.delete();
    }

}