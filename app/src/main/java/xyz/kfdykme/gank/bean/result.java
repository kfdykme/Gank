package xyz.kfdykme.gank.bean;

import java.util.List;

public class result 
{
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

	public String getId()
	{
		return _id;
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
