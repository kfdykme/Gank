package xyz.kfdykme.gank.adapter;
import android.support.v7.widget.*;
import android.view.*;
import java.util.*;
import android.content.*;
import java.util.zip.*;
import xyz.kfdykme.gank.*;
import android.widget.*;
import xyz.kfdykme.gank.bean.*;
import xyz.kfdykme.gank.adapter.RecyclerViewAdapter.*;
import android.widget.AdapterView.*;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> 
{
	public  final static int TYPE_ITEM = 0;
	
	public final static int TYPE_FOOTER =1;
	//上拉加载更多
    public static final int  PULLUP_LOAD_MORE=0;
    //正在加载中
    public static final int  LOADING_MORE=1;
    //上拉加载更多状态-默认为0
    private int footer_status=0;
	
	public static final String STRING_PULLUP_LOAD_MORE = "Pull up to load more.";
	
	public static final String STRING_LOADING_MORE ="Loading more.";
	
	
	public  List<DataEntity.result> mResults;

	private Context context;	
	
	private LayoutInflater Inflater;

	private RecyclerViewAdapter.OnItemClickListener mOnItemClickListener;

	private RecyclerViewAdapter.OnItemLongClickListener mOnItemLongClickListener;

	
	
	
	// init my recyclerview
	public RecyclerViewAdapter(Context context, List<DataEntity.result>  mResults)
	{
		this.mResults = mResults;
		this.context = context;
		Inflater = LayoutInflater.from(context);
	}
	
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		if (viewType == TYPE_ITEM){
			View view = Inflater.inflate(R.layout.item_recyclerview ,parent,false);

			ItemHolder holder = new ItemHolder(view);

			return holder;			
		} else if (viewType == TYPE_FOOTER){
			View view = Inflater.inflate(R.layout.item_recyclerview_footer,parent,false);
			
			FooterHolder holder = new FooterHolder (view);
			
			return holder;
		}
		
		return null;
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
	{
		
		if (holder instanceof ItemHolder){
			ItemHolder mHolder = (ItemHolder)holder;
			DataEntity.result r = mResults.get(position); 

			mHolder .descTextView.setText(r.getDesc());
			mHolder .idTextView.setText(position+1+"");
			mHolder .dateTextView.setText(r.getPublishedAt());
			mHolder .whoTextView.setText(r.getWho());

			//bind Listeners
			if( mOnItemClickListener!= null){  
				holder. itemView.setOnClickListener( new View.OnClickListener() {

						@Override  
						public void onClick(View v) {  
							mOnItemClickListener.onClick(position);  
						}  
					});  
			}

			if(mOnItemLongClickListener != null){
				holder. itemView.setOnLongClickListener( new View.OnLongClickListener() {
						@Override  
						public boolean onLongClick(View v) {  
							mOnItemClickListener.onClick(position);  
							return false;  
						}  
					});  

			}
		} else if (holder instanceof FooterHolder){
			FooterHolder mHolder = (FooterHolder)holder;
			
			switch (footer_status)
			{
				case PULLUP_LOAD_MORE:
					mHolder.mTextView.setText(STRING_PULLUP_LOAD_MORE);
					break;
				case LOADING_MORE:
					mHolder.mTextView.setText(STRING_LOADING_MORE);
					break;
				

			}
		}
			
	}
	
	@Override
	public int getItemCount()
	{
		return mResults.size() +1 ;
	}

	@Override
	public int getItemViewType(int position)
	{
		if(position +1 == getItemCount()){
			return TYPE_FOOTER;
		} else {
			return TYPE_ITEM;	
		}
		
	}
	
	
	
	public void addData(List<DataEntity.result> result){
		mResults.addAll(result);
		notifyDataSetChanged();
	}
	
	// create my viewholder
	public class ItemHolder extends RecyclerView.ViewHolder{

		TextView descTextView ;
		TextView idTextView ;
		TextView dateTextView ;
		TextView whoTextView ;
		
		public ItemHolder(View view){
			super(view);
			descTextView = (TextView) view.findViewById(R.id.item_recyclerview_descText);
			idTextView = (TextView) view.findViewById(R.id.item_recyclerview_idText);
			whoTextView = (TextView) view.findViewById(R.id.item_recyclerview_whoText);
			dateTextView = (TextView) view.findViewById(R.id.item_recyclerview_dateText);
			
		}
		
	}
	
	
	
	//create footer holder
	public class FooterHolder extends RecyclerView.ViewHolder{
		TextView mTextView;
		public FooterHolder(View view){
			super(view);
			mTextView = (TextView) view.findViewById(R.id.item_recyclerview_footer_textview);
		}
	}

    public void changeFooterStatus(int status){
        footer_status=status;
        notifyDataSetChanged();
    }
	
	//implements onclicklistener
	public interface OnItemClickListener{
		void onClick(int position);
	}
	public interface OnItemLongClickListener{
		void onLongClick( int position);
	}
	
	public void setOnItemClickListener(OnItemClickListener onItemClickListener){
		this.mOnItemClickListener = onItemClickListener;
	}
	
	public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
		this.mOnItemLongClickListener = onItemLongClickListener;
	}
	
	
}
