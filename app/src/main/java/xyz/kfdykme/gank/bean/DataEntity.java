package xyz.kfdykme.gank.bean;
import java.util.*;


public class DataEntity
{
	private String error;

	private List<result> results;



	public class result {
		private String _id;

		private String creatAt;

		private String desc;

		private List<String> images;

		private String publishedAt;

		private String source;

		private String url;

		private String type;

		private boolean used;

		private String who;
		
		

		public result(String _id, String creatAt, String desc, List<String> images, String publishedAt, String source, String url, String type, boolean used, String who)
		{
			this._id = _id;
			this.creatAt = creatAt;
			this.desc = desc;
			this.images = images;
			this.publishedAt = publishedAt;
			this.source = source;
			this.url = url;
			this.type = type;
			this.used = used;
			this.who = who;
		}


		
		
		@Override
		public String toString()
		{
			return "result : { _id : " + _id
				+ ", creatAt : " + creatAt
				+ ", desc :  "+ desc 
				+ ", images : " + images
				+ ", publishedAt : " + publishedAt
				+ ", source : " + source
				+ ", url : " + url
				+ ", type : " + type
				+ ", used : " + used 
				+ ", who : " + who
				+" }";
		}



		public void setId(String id)
		{
			_id = id;
		}



		public void setCreatAt(String creatAt)
		{
			this.creatAt = creatAt;
		}

		public String getCreatAt()
		{
			return creatAt;
		}

		public void setDesc(String desc)
		{
			this.desc = desc;
		}

		public String getDesc()
		{
			return desc;
		}

		public void setImages(List<String> images)
		{
			this.images = images;
		}

		public List<String> getImages()
		{
			return images;
		}

		public void setPublishedAt(String publishedAt)
		{
			this.publishedAt = publishedAt;
		}

		public String getPublishedAt()
		{
			return publishedAt;
		}

		public void setSource(String source)
		{
			this.source = source;
		}

		public String getSource()
		{
			return source;
		}

		public void setUrl(String url)
		{
			this.url = url;
		}

		public String getUrl()
		{
			return url;
		}

		public void setType(String type)
		{
			this.type = type;
		}

		public String getType()
		{
			return type;
		}

		public void setUsed(boolean used)
		{
			this.used = used;
		}

		public boolean isUsed()
		{
			return used;
		}

		public void setWho(String who)
		{
			this.who = who;
		}

		public String getWho()
		{
			return who;
		}





	}
	
	public void setError(String error)
	{
		this.error = error;
	}

	public String getError()
	{
		return error;
	}

	public void setResults(List<result> results)
	{
		this.results = results;
	}

	public List<result> getResults()
	{
		return results;
	}

	@Override
	public String toString()
	{
		return "dataEntity { error : "+ error +", results : " + results +"}";
	}



}
