package br.com.lvc.utility.util;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import br.com.lvc.utility.R;
import br.com.lvc.utility.screen.ScreenManager;

public class VideoUTIL {
	
	
	private static final String HTTP = "http:";
	private static final int INDEX_INVALID = -1;

	private static final String REGEX_TIPO_UM = "be/";
	private static final String REGEX_TIPO_DOIS = "v=";
	private static final String REGEX_TIPO_TRES = "videos/";
	private static final String REGEX_TIPO_QUATRO = "embed/";
	private static final String REGEX_FINAL = "&";

	private static final String[] REGEX_ARRAY = {
		REGEX_TIPO_UM, REGEX_TIPO_DOIS, REGEX_TIPO_TRES//, REGEX_TIPO_QUATRO
	};
	
	public static void exibirVideo(Context context, String movieURL) {
		if(movieURL == null || movieURL.length() == 0) {
			ScreenManager.getInstance().showMessageToastLong(context, R.string.no_video_found);
		} else {

			try {
				String idVideo = getIDYoutubeURL(movieURL);
				if(idVideo.contains(HTTP)) {
					showVideoIntentGeneric(context,movieURL);
				} else {
					showViewIntentYoutube(context,movieURL);
				}
		
			} catch(Exception e) {
				showVideoIntentGeneric(context, movieURL);	
			}

		}
	}
	
	private static void showViewIntentYoutube(Context context, String idVideo) {
	//	Intent intent = YouTubeIntents.createPlayVideoIntent(context, idVideo);
	//	context.startActivity(intent);	
	}
	
	public static void showVideoIntentGeneric(Context context, String movieURL) {
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(movieURL));
		context.startActivity(intent);
	}

	private static  String getIDYoutubeURL(String urlYoutube) {
		String resultado = null;

		int beginIndex = 0;

		for(String regex : REGEX_ARRAY) {
			int index = urlYoutube.indexOf(regex);
			if(index > INDEX_INVALID) {
				beginIndex = index + regex.length();
			}
		}

		int finalIndex = urlYoutube.indexOf(REGEX_FINAL);
		if(finalIndex > INDEX_INVALID)
			resultado = urlYoutube.substring(beginIndex,finalIndex);
		else
			resultado = urlYoutube.substring(beginIndex);

		return resultado;
	}

}
