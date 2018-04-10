package xyz.kfdykme.gank.web;
import android.content.*;
import android.webkit.*;
import android.widget.*;
import android.view.*;

import xyz.kfdykme.gank.bean.DataEntity.*;
import xyz.kfdykme.gank.bean.*;
public class WebPresenter implements WebContract.Presenter
{

	private WebDow view;
	
	@Override
	public void openWebDow(Likes loadLike)
	{

		view.setVisibility(View.VISIBLE);
		view.getWebView().loadUrl(loadLike.url);
		
	}
	

	
	
	@Override
	public void openWebDow(DataEntity.result loadResult)
	{
	
		view.setVisibility(View.VISIBLE);
		view.getWebView().loadUrl(loadResult.getUrl());
		view.setLoadResult(loadResult);

		
	}
	
	
	
	

	@Override
	public void onGoBackWebDow( )
	{

		if ((!view.getWebView().canGoBack())||view.getWebView().getUrl().equals(WebDow.BLANK_URL))
		{
			view.setVisibility(View.GONE);
			view.getWebView().loadUrl(WebDow.BLANK_URL);
		}
		else
		{
			view.getWebView().goBack();
		}
	}

	@Override
	public void initWebView(WebView webview,final ProgressBar progressBar)
	{
		WebSettings wb = webview.getSettings();
		wb.setJavaScriptEnabled(true);
		wb.setJavaScriptCanOpenWindowsAutomatically(true);
		wb.setBuiltInZoomControls(true);

		WebViewClient wvc = new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url)
			{
				view.loadUrl(url);
				return true;
			}
		};

		WebChromeClient wcc = new WebChromeClient(){

			@Override
			public void onProgressChanged(WebView view, int newProgress)
			{
				if(newProgress <= 20)
					progressBar.setVisibility(View.VISIBLE);
				else if(newProgress >= 100){
					progressBar.setVisibility(View.GONE);
				}

				progressBar.setProgress(newProgress);
			}
		};

		webview.setWebChromeClient(wcc);
		webview.setWebViewClient(wvc);

	}
	
	

	public WebPresenter(WebDow view){
		this.view = view;
    }

	@Override
	public void attach() {

	}

	@Override
	public void detach() {

	}
}
