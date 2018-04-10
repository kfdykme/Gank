package xyz.kfdykme.gank.search;
import android.os.*;
import android.support.v4.app.*;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.*;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import xyz.kfdykme.gank.*;
import xyz.kfdykme.gank.bean.*;

import java.util.*;

import android.view.View.OnClickListener;
import android.widget.TextView.*;
import android.support.v7.widget.*;
import xyz.kfdykme.gank.adapter.*;

public class SearchView extends DialogFragment
{

	private Spinner mSpinner;

	private Button mButton;
	
	private EditText mEditText;
	
	private RecyclerView mRecyclerView;
	
	private RecyclerViewAdapter mRecyclerViewAdpter;
	
	
	private List<DataEntity.result> mResults;
	
	
	private int page = 1;

	private String type = "all";
	
	private String lastKeyNType = "nullnull";
	
	private final String SEARCH = "search";
	
	//private final String UPDATE = "update";
	
	private final String LOADING = "loading";
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		
		View view = inflater.inflate(R.layout.view_search, null);

		getDialog().setTitle("       Search");
		
		initViews(view);
			
		//reload data at restart	
		if(mResults != null){
			addViews(mResults);
		}
		
		return view;
	}

	private void initViews(View view)
	{
		

		
		//initial RecyclerView
		mRecyclerView = (RecyclerView)view.findViewById(R.id.dialog_fargment_search_recyclerview);
		LinearLayoutManager manager = new LinearLayoutManager(getContext());
		manager.setOrientation(OrientationHelper.VERTICAL);
		mRecyclerView.setLayoutManager(manager);
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		

		//initial mEditText
		mEditText = (EditText)view.findViewById(R.id.searchEditText);
		
		//no action now ??????????????
		mEditText.setOnEditorActionListener(new OnEditorActionListener(){

				@Override
				public boolean onEditorAction(TextView p1, int p2, KeyEvent p3)
				{
					doSearch();
					return false;
				}
			});

		//initial searchButton
		mButton = (Button)view.findViewById(R.id.searchButton);	
		mButton.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View view)
				{
					doSearch();
				}

			});

		//initial spinner 
		mSpinner = (Spinner)view.findViewById(R.id.searchSpinner);
		String spinnerStrings[] ={"all","Android","iOS","前端"};
		ArrayAdapter<String> mSpinnerAdapter=new ArrayAdapter<String>(this.getActivity(),
																	  android.R.layout.simple_list_item_1,
																	  spinnerStrings);
		mSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
		mSpinner.setAdapter(mSpinnerAdapter);
		mSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){

				@Override
				public void onItemSelected(AdapterView<?> p1, View view, int p3, long p4)
				{
					TextView mTv =  (TextView)view;
					type = mTv.getText().toString();
				}

				@Override
				public void onNothingSelected(AdapterView<?> p1)
				{
					
				}
				
			
		});
		
			
	}

	
	//check is Search a new key or search more data
	private boolean isSearch(){
		return !lastKeyNType.equals(mEditText.getText().toString() + type);
	}
	

	private void doSearch()
	{
		//as a tip to tell it is loading data from net 
		mButton.setText(LOADING);
		String key = mEditText.getText().toString();

		new Retrofit.Builder()
				.baseUrl(GankService.Companion.getBASEURL())
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.build()
				.create(GankService.class)
				.doSerach(key,type,GankService.Companion.getDEFAULT_NUMBER_PER_PAGE(), String.valueOf(page))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Action1<DataEntity>() {
					@Override
					public void call(DataEntity dataEntity) {
						if(!isSearch()){
							mResults.addAll(dataEntity.getResults());
						} else {
							mResults = dataEntity.getResults();
						}

						addViews(mResults);

						lastKeyNType = mEditText.getText().toString() + type;

					}
				});

	}
	
	
	
	public void addViews( final List<DataEntity.result> results)
	{

		//set search TextView's text to loading
		mButton.setText(LOADING);

		if(mRecyclerViewAdpter == null){
			//initRecyclerViewAdapter
			mRecyclerViewAdpter = new RecyclerViewAdapter(getContext(),results);

			mRecyclerViewAdpter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener(){

				@Override
				public void onClick(int position)
				{
					DataEntity.result result = mRecyclerViewAdpter.mResults.get(position);
					GankActivity.Companion.getGankPresenter().openWebDow(result);
					getDialog().dismiss();

				}
			});

			//setAdapter
			mRecyclerView.setAdapter(mRecyclerViewAdpter);

		} else {
			mRecyclerViewAdpter.addData(results);
		}

		mButton.setText(SEARCH);


	}

	
	
}
