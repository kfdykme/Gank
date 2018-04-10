package xyz.kfdykme.gank.view;
import android.widget.*;
import android.graphics.*;
import java.util.*;
import android.content.*;
import android.util.*;
import android.content.res.*;
import xyz.kfdykme.gank.*;
import android.view.*;
import android.support.v4.view.*;

public class ViewPagerIndicator extends LinearLayout
{
	private Paint mPaint;
	
	private Path mPath;
	
	private int mTriangleWidth;
	
	private int mTriangleHeight;
	
	private static final float  RADIO_TRIANGLE_WIDTH = 1/3f;
	
	private int mInitTransletionX;
	
	private int mTranslationX;
	
	private int mTabVisibleCount;
	
	private static final int COUNT_DEFAULT_TAB = 4;
	
	private List<String> mTitles;
	
	private List<String> oriTitles = Arrays.asList("Android","iOS","前端","福利");
	
	private static final int COLOR_TEXT_NORMAL = 0x77ffffff;
	
	public static final int COLOR_TEXT_HIGHLIGHT = 0x771047a9;
	
	
	public void setVisibleTabCount(int count){
		mTabVisibleCount = count;
	}
	
	public ViewPagerIndicator(Context context){
		this(context,null);
	}
	
	public ViewPagerIndicator(Context context, AttributeSet attrs){
		super(context, attrs);
		
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);
		
		mTabVisibleCount = ta.getInt(R.styleable.ViewPagerIndicator_visible_tab_count,COUNT_DEFAULT_TAB);
		
		if (mTabVisibleCount < 0)
				mTabVisibleCount = COUNT_DEFAULT_TAB;
				
		ta.recycle();
		
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.parseColor("#771047a9"));
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setPathEffect(new CornerPathEffect(3));
		
		
	}
	
	public void scroll(int position, float positionOffset){
		int tabWidth = getWidth() / COUNT_DEFAULT_TAB;

		mTranslationX = (int) (tabWidth * (position + positionOffset));
		int sX = 0;
		
		if ( position >= (mTabVisibleCount - 2 ) && getChildCount() > mTabVisibleCount && positionOffset > 0){
			if ( mTabVisibleCount != 1){
				sX = (position - ( mTabVisibleCount - 2)) * tabWidth + (int)(tabWidth * positionOffset);
			} else {
				sX = position * tabWidth + (int)(tabWidth * positionOffset);
			}
			
			this.scrollTo(sX,0);
		} 
		
		invalidate();
		
	}
	
	
	public void setTabItems(List<String> titles ){
		if (titles != null && titles.size() > 0){
			this.removeAllViews();
			mTitles = titles;
			for (String title: mTitles){
				addView(generateTextView(title));
			}
		}
		
		
		setItemClickEvent();
	}


	private View generateTextView(String title)
	{
		TextView tv = new TextView(getContext());
		
		LinearLayout.LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		
		lp.width = getScreenWidth() / mTabVisibleCount;

		tv.setText(title);

		for (String text : oriTitles){
			if (text.equals(title))
				tv.setTextSize(24);
		}		
		
		tv.setGravity(Gravity.CENTER);
		
		tv.setTextColor(COLOR_TEXT_NORMAL);
		
		tv.setLayoutParams(lp);
		return tv;
	}
	
	private ViewPager mViewPager;
	
	public interface PageOnchangeListener{
		
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);
		
		public void onPageSelected(int position);
		
		public void onPageScrollStateChanged(int state);
		
	}
	
	public PageOnchangeListener mListener;
	
	public void setOnPageChangeListener(PageOnchangeListener listener){
		this.mListener = listener;
	}
	
	
	public void setViewPager(ViewPager viewPager, int position){
		mViewPager = viewPager;
		
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){

				@Override
				public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
				{
					if ( mListener != null){
						mListener.onPageScrolled(position,positionOffset,positionOffsetPixels);
					}
					
					scroll(position,positionOffset);
				}


				@Override
				public void onPageSelected(int position)
				{
					if ( mListener != null){
						mListener.onPageSelected(position);
					}
					
					setHighLightTextView(position);
				}

				@Override
				public void onPageScrollStateChanged(int state)
				{
					if ( mListener != null){
						mListener.onPageScrollStateChanged(state);
					}
				}	
		});
		for(int i = 0; i < mViewPager.getChildCount(); i++)
			mViewPager.setCurrentItem(i);
		
		
		mViewPager.setCurrentItem(position);
		setHighLightTextView(position);
	}

	@Override
	protected void dispatchDraw(Canvas canvas)
	{
		canvas.save();
		canvas.translate(mInitTransletionX+ mTranslationX, getHeight() + 2);
		canvas.drawPath(mPath,mPaint);		
		canvas.restore();
		
		super.dispatchDraw(canvas);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		super.onSizeChanged(w, h, oldw, oldh);
		
		mTriangleWidth = (int)(w / mTabVisibleCount  * RADIO_TRIANGLE_WIDTH);
		
		mInitTransletionX = w / mTabVisibleCount / 2 - mTriangleWidth / 2;
		
		initTriangle();
		
	}

	@Override
	protected void onFinishInflate()
	{
		// TODO: Implement this method
		super.onFinishInflate();
		
		int cCount = getChildCount();
		
		if (cCount == 0) return;
		
		for(int i = 0; i < cCount; i++){
			View view = getChildAt(i);
			LinearLayout.LayoutParams lp = (LayoutParams) view.getLayoutParams();
			lp.weight = 0;
			lp.width = getScreenWidth() / mTabVisibleCount;
			view.setLayoutParams(lp);
		}
		
		
		setItemClickEvent();
	}
	
	private int getScreenWidth(){
		WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics  outMetrics = new DisplayMetrics();
		
		wm.getDefaultDisplay().getMetrics(outMetrics);
		
		return outMetrics.widthPixels;
	}
	
	
	private void initTriangle(){
		mPath = new Path();
		mTriangleHeight = mTriangleWidth / 3;
		mPath.moveTo(0,0);
		mPath.lineTo(mTriangleWidth,0);
		mPath.lineTo(0, -mTriangleHeight);
		mPath.close();
	}
	
	private void setHighLightTextView(int position){
		for(int i = 0; i < getChildCount();i++){
			View allView = getChildAt(i);
			if (allView instanceof TextView){
				((TextView) allView).setTextColor(COLOR_TEXT_NORMAL);
			}
		}		
		View view = getChildAt(position);
		if (view instanceof TextView){
			((TextView) view).setTextColor(COLOR_TEXT_HIGHLIGHT);
		}
	}
	
	

	private void setItemClickEvent()
	{
		int cCount = getChildCount();
		
		for (int i = 0; i < cCount; i++){
			
			View view = getChildAt(i);
			
			final int j = i;
			
			view.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View view)
					{
						mViewPager.setCurrentItem(j);
						// TODO: Implement this method
					}
					
				
			});
		}
	}
	
	
	
	
	
	
	
	
	
	
}
