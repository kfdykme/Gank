package xyz.kfdykme.gank.web;
import android.webkit.*;
import android.widget.*;
import xyz.kfdykme.gank.base.*;

import xyz.kfdykme.gank.bean.*;
import javax.xml.transform.*;

public class WebContract
{
	public interface Presenter extends BasePresenter{
		void onGoBackWebDow( );
		void initWebView(WebView webview, final ProgressBar progressBar);
		void openWebDow( DataEntity.result loadResult);
		void openWebDow(Likes loadLike);
	}
}
