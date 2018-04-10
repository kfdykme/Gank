package xyz.kfdykme.gank;
import android.os.*;
import android.support.v4.app.*;
import android.view.*;

import android.widget.*;

import xyz.kfdykme.gank.bean.*;

import java.util.*;

import android.util.*;

import android.support.v7.widget.*;
import xyz.kfdykme.gank.adapter.*;
import android.support.v4.widget.*;

public class ArtcleListFragment extends Fragment implements ArtcleListContract.View
{


	private String mTitle;

	private String mType ;

	private int mPage =  0;

	private int mSearchPage = 0;



	private View view ;

	private TextView mLoadingTextView;


	private ProgressBar mLoadingProgressBar;

	private android.support.v7.widget.RecyclerView mRecyclerView;

	private RecyclerViewAdapter mRecyclerViewAdpter ;

	private SwipeRefreshLayout mSwipeRefreshLayout;


	private ArtcleListPresenter mPresenter;


	public static final String BUNDLE_TITLE = "title";


	@Override
	public void addViews(final List<DataEntity.result> results)
	{

		initLoadViews(isInited());

		//initRecyclerViewAdapter
		if(mRecyclerViewAdpter == null){
			mRecyclerViewAdpter = new RecyclerViewAdapter(getContext(),results);

			mRecyclerViewAdpter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener(){

				@Override
				public void onClick(int position)
				{
					DataEntity.result result = mRecyclerViewAdpter.mResults.get(position);


					GankActivity.Companion.getGankPresenter().openWebDow(result);

				}
			});


			//setAdapter
			mRecyclerView.setAdapter(mRecyclerViewAdpter);

		} else {
			mRecyclerViewAdpter.addData(results);
		}


	}





	@Override
	public void initLoadViews(Boolean isInited)
	{
		if(isInited){
			mLoadingTextView.setVisibility(View.GONE);
			mLoadingProgressBar.setVisibility(View.GONE);
			mRecyclerView.setVisibility(View.VISIBLE);
		}

	}

	@Override
	public Boolean isInited()
	{
		return (mLoadingProgressBar.getVisibility() == View.VISIBLE);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		Bundle bundle = getArguments();

		if (bundle != null)
		{
			mTitle = bundle.getString(BUNDLE_TITLE);
			mType = mTitle;
		}

		if (view == null)
		{
			view = inflater.inflate(R.layout.frag_gank, container, false);

			initView();

			//first load
			mPresenter.loadArticle(getType(), ++mPage);
		}

		return view;

	}


	private void initView()
	{
		//findViewById

		mRecyclerView = (RecyclerView)view.findViewById(R.id.fragment_kgank_recyclerview);
		mLoadingProgressBar = (ProgressBar)view.findViewById(R.id.fragment_kgank_load_progressbar);
		mLoadingTextView = (TextView)view.findViewById(R.id.fragment_kgank_load_text);
		mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.fragment_kgank_swiperefreshlayout);




		//initRecyclerView
		LinearLayoutManager manager = new LinearLayoutManager(getContext());
		manager.setOrientation(OrientationHelper.VERTICAL);
		mRecyclerView.setLayoutManager(manager);
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());

		mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
				int lastVisibleItem;
				@Override
				public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
					super.onScrollStateChanged(recyclerView, newState);
					mRecyclerViewAdpter.changeFooterStatus(RecyclerViewAdapter.LOADING_MORE);
					if (newState ==  RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 ==mRecyclerViewAdpter.getItemCount()) {
						new Handler().postDelayed(new Runnable() {
								@Override
								public void run() {
									mPresenter.loadArticle(getType(),++mPage);
									mRecyclerViewAdpter.changeFooterStatus(RecyclerViewAdapter.PULLUP_LOAD_MORE);
								}
							},1000);
					}
				}
				@Override
				public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
					super.onScrolled(recyclerView,dx, dy);
					lastVisibleItem =((LinearLayoutManager)mRecyclerView.getLayoutManager()).findLastVisibleItemPosition();
				}

			});

		//initSwipeRefreshLayout

		mSwipeRefreshLayout.setColorScheme(
				android.R.color.holo_blue_dark,
				android.R.color.holo_blue_light,
				android.R.color.holo_green_light,
				android.R.color.holo_green_light);


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
				@Override
				public void onRefresh() {
					mSwipeRefreshLayout.setRefreshing(true);

					//refresh data
					setPage(0);
					mRecyclerViewAdpter.mResults.clear();
					mPresenter.loadArticle(getType(),++mPage);
					Log.d("Swipe", "Refreshing Number");


					( new Handler()).postDelayed(new Runnable() {
							@Override
							public void run() {
								mSwipeRefreshLayout.setRefreshing(false);
							}
						}, 3000);
				}
			});

	}







	@Override
	public void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);


		if(mPresenter == null)
				mPresenter = new ArtcleListPresenter(this);


	}





	public static ArtcleListFragment newInstance(String title)
	{
		Bundle bundle = new Bundle();

		bundle.putString(BUNDLE_TITLE, title);


		ArtcleListFragment fragment = new ArtcleListFragment();

		fragment.setArguments(bundle);

		return fragment;
	}




	// get & set


	@Override
	public void setPresenter(ArtcleListPresenter presenter)
	{
		this.mPresenter = presenter;
	}

	public ArtcleListPresenter getPresenter(){
		return mPresenter;
	}

	public void setMType(String mType)
	{
		this.mType = mType;
	}

	public String getType()
	{
		return mType;
	}



	public void setPage(int mPage)
	{
		this.mPage = mPage;
	}

	public int getMPage()
	{
		return mPage;
	}

	public void setMSearchPage(int mSearchPage)
	{
		this.mSearchPage = mSearchPage;
	}

	public int getMSearchPage()
	{
		return mSearchPage;
	}




	public void setTitle(String mTitle)
	{
		this.mTitle = mTitle;
	}

	public String getTitle()
	{
		return mTitle;
	}




}
