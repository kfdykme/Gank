package xyz.kfdykme.gank.adapter;

import android.content.*;
import android.support.v7.widget.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import xyz.kfdykme.gank.*;
import xyz.kfdykme.gank.adapter.*;
import xyz.kfdykme.gank.bean.*;
import java.util.*;

public class RecyclerViewAdapter2Like extends RecyclerView.Adapter<RecyclerViewAdapter2Like.ViewHolder> 
{

	public  List<Likes> mLikes;

	private Context context;	

	private LayoutInflater Inflater;

	private RecyclerViewAdapter2Like.OnItemClickListener mOnItemClickListener;

	private RecyclerViewAdapter2Like.OnItemLongClickListener mOnItemLongClickListener;




	// init my recyclerview
	public RecyclerViewAdapter2Like(Context context, List<Likes>  mResults)
	{
		this.mLikes = mResults;
		this.context = context;
		Inflater = LayoutInflater.from(context);
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View view = Inflater.inflate(R.layout.item_recyclerview, parent, false);

		ViewHolder holder = new ViewHolder(view);

		// TODO: Implement this method
		return holder;
	}

	@Override
	public void onBindViewHolder(RecyclerViewAdapter2Like.ViewHolder holder, final int position)
	{
		Likes l = mLikes.get(position); 

		holder.descTextView.setText(l.desc);
		holder.whoTextView.setText(l.who);
		holder.idTextView.setText(position +1 +"");
		holder.dateTextView.setText("");

		//bind Listeners
		if (mOnItemClickListener != null)
		{  
			holder. itemView.setOnClickListener(new OnClickListener() {  

					@Override  
					public void onClick(View v)
					{  
						mOnItemClickListener.onClick(position);  
					}  
				});  
		}

		if (mOnItemLongClickListener != null)
		{
			holder. itemView.setOnLongClickListener(new OnLongClickListener() {  
					@Override  
					public boolean onLongClick(View v)
					{  
						mOnItemClickListener.onClick(position);  
						return false;  
					}  
				});  

		}

	}

	@Override
	public int getItemCount()
	{
		// TODO: Implement this method
		return mLikes.size();
	}



	// create my viewholder
	public class ViewHolder extends RecyclerView.ViewHolder
	{

		TextView descTextView ;
		TextView idTextView ;
		TextView dateTextView ;
		TextView whoTextView ;

		public ViewHolder(View view)
		{
			super(view);
			descTextView = (TextView) view.findViewById(R.id.item_recyclerview_descText);
			idTextView = (TextView) view.findViewById(R.id.item_recyclerview_idText);
			whoTextView = (TextView) view.findViewById(R.id.item_recyclerview_whoText);
			dateTextView = (TextView) view.findViewById(R.id.item_recyclerview_dateText);

		}

	}

	//implements onclicklistener
	public interface OnItemClickListener
	{
		void onClick(int position);
	}
	public interface OnItemLongClickListener
	{
		void onLongClick(int position);
	}

	public void setOnItemClickListener(OnItemClickListener onItemClickListener)
	{
		this.mOnItemClickListener = onItemClickListener;
	}

	public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener)
	{
		this.mOnItemLongClickListener = onItemLongClickListener;
	}


}
