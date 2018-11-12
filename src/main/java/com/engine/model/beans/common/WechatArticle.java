package com.engine.model.beans.common;

import java.util.Date;

public class WechatArticle {
	private String  id;				//文章id
	
	private String title;			//标题
	private String sourceId;			//所属公众号
	private String url;				//文章链接
	private String imgUrl;			//文章中图片链接
	private String summary;			//概括
	private String type;			//分类
	private String content;			//内容
	private int hot;			//是否热门
	private Date postTime;			//发布时间
	private int clickCount;			//阅读数
	private int likeCount;			//点赞数
	
	public WechatArticle(){
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgurl) {
		this.imgUrl = imgurl;
	}

	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getHot() {
		return hot;
	}
	public void setHot(int hot) {
		this.hot = hot;
	}
	public Date getPostTime() {
		return postTime;
	}
	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
	public int getClickCount() {
		return clickCount;
	}
	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}
	public int getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
	
	@Override
	public String toString(){
		return id+"--"+title+"--"+sourceId+"--"+url+"--"+summary+"--"+clickCount+"--"+likeCount+"--"+postTime;
	}
}
