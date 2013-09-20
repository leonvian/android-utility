package br.com.lvc.utility.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;
import br.com.lvc.utility.R;

public class WebUTIL {
	
	
	public static void goToWebSite(String url, Context context) {
		if(url == null || url.length() == 0) {
			Toast.makeText(context, R.string.web_site_nao_informado, Toast.LENGTH_SHORT).show();
		} else {
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse(url));
			context.startActivity(i);	
		}
	}
	
	
	public static void configureWebView(WebView webView, ProgressBar progressBar) {
		
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setBuiltInZoomControls(true);
		webSettings.setDomStorageEnabled(true);

		webView.setWebChromeClient(new WebChromeCallBack());
		webView.setWebViewClient(new WebViewCallback(progressBar));		
	}

	private static class WebViewCallback extends WebViewClient {
		
		private ProgressBar progressBar;
		
		public WebViewCallback(ProgressBar progressBar) {
			this.progressBar = progressBar;
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			progressBar.setVisibility(View.VISIBLE);
			Log.i("On PAGE INITING", "On PAGE INITING");
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,	String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
			Log.i("On onReceivedError", "On onReceivedError");
		}

		@Override
		public void onReceivedSslError(WebView view, SslErrorHandler handler,	SslError error) {
			super.onReceivedSslError(view, handler, error);
			Log.i("On onReceivedSslError", "On onReceivedSslError");
		}
		
		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			progressBar.setVisibility(View.INVISIBLE);
			Log.i("On PAGE Finish", "On PAGE Finish");
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return false;
		}

	}

	private static class WebChromeCallBack  extends WebChromeClient { }
	
}