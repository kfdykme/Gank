package xyz.kfdykme.gank.web;
import android.content.*;
import android.util.*;
import android.view.*;
import android.webkit.*;
import android.widget.*;

import io.realm.Realm;
import xyz.kfdykme.gank.*;
import xyz.kfdykme.gank.bean.*;
import android.support.design.widget.*;

public  class WebDow  extends LinearLayout 
{
	
	private ProgressBar mProgressBar;
	
	private TextView fTV;

	private TextView rTV;

	private TextView bTV;

	private TextView cTV;
	
	private TextView lTV;
	
	private WebView webview;
	
	private LinearLayout linearLayout;
	
	private DataEntity.result loadResult;
	
	
	
	private WebPresenter mPresenter;
	
	public static final String BLANK_URL= "file:///android_asset/index.html";
	
	public WebDow(Context context ){
		
		this(context, null);
	
	}
	
	
	public WebDow(Context context, AttributeSet attrs){
		super(context,attrs);
		
		mPresenter = new WebPresenter(WebDow.this);
		
		LayoutInflater.from(context).inflate(R.layout.webdow, this);

		webview = (WebView)findViewById(R.id.kwebviewlayoutWebView);
		webview.loadUrl(BLANK_URL);
		
		mProgressBar = (ProgressBar)findViewById(R.id.kwebviewlayoutProgressBar);
		
		fTV = (TextView)findViewById(R.id.kwebviewlayoutForwardTextView);
		rTV = (TextView)findViewById(R.id.kwebviewlayoutReflashTextView);
		bTV = (TextView)findViewById(R.id.kwebviewlayoutBackTextView);
		cTV = (TextView)findViewById(R.id.kwebviewlayoutCopyTextView);
		lTV = (TextView)findViewById(R.id.kwebviewlayoutLikeTextView);
		
		
		linearLayout  = (LinearLayout)findViewById(R.id.kwebviewlayoutLinearLayout);
		mPresenter.initWebView(webview,mProgressBar);
		
		
		fTV.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View view)
				{
					if(webview.canGoForward()){
						webview.goForward();
					}
					// TODO: Implement this method
				}
			});
			
		bTV.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View view)
				{
					mPresenter.onGoBackWebDow();
					// TODO: Implement this method
				}
			});
			
		cTV.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View view)
				{
					ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
					cm.setText(webview.getUrl());
					Toast.makeText(getContext(),"Copied : "+webview.getUrl(),Toast.LENGTH_SHORT).show();
					
					// TODO: Implement this method
				}
			});
		
		rTV.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View view)
				{
					webview.reload();
					// TODO: Implement this method
				}
			});
			
			
		lTV.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					if (loadResult != null){

						boolean shouldSave = true;
						for (Likes mLikes: Realm.getDefaultInstance().where(Likes.class).findAll()) {
							if (mLikes.desc.equals(loadResult.getDesc())
									&& mLikes.url.equals(loadResult.getUrl())) {
								Snackbar.make(WebDow.this, "Has one same. ", Snackbar.LENGTH_SHORT).show();
								shouldSave = false;
							}
						}
						
						if(shouldSave){
							Snackbar.make(WebDow.this, "Save successfully.", Snackbar.LENGTH_SHORT).show();


							Realm.getDefaultInstance()
									.executeTransaction(
											new Realm.Transaction() {
												@Override
												public void execute(Realm realm) {

													Likes newLikes = realm.createObject(Likes.class);
													newLikes.desc = loadResult.getDesc();
													newLikes.url = loadResult.getUrl();
													newLikes.who = loadResult.getWho();

												}
											}
									);

							
						}
					}
					
					
					
				}
			});
			
			
			
	}

	
	
	
	
	
	
	
	
	public void setPresenter(WebPresenter mPresenter)
	{
		this.mPresenter = mPresenter;
	}

	public WebPresenter getPresenter()
	{
		return mPresenter;
	}

	public void setLoadResult(DataEntity.result loadResult)
	{
		this.loadResult = loadResult;
	}

	public DataEntity.result getLoadResult()
	{
		return loadResult;
	}
	

	public void setWebView(WebView wv)
	{
		this.webview = wv;
	}

	public WebView getWebView()
	{
		return webview;
	}

	
}
