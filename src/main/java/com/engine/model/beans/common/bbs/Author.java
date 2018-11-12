package com.engine.model.beans.common.bbs;

public class Author {
	private Long id;
	private Long bbsId;
	private String authorName;
	private String authorPageUrl;
	
	public Author(){}
	
	public Author(Long id,Long bbsId,String name){
		this.id=id;
		this.bbsId=bbsId;
		this.authorName=name;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getBbsId() {
		return bbsId;
	}
	public void setBbsId(Long bbsId) {
		this.bbsId = bbsId;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getAuthorPageUrl() {
		return authorPageUrl;
	}

	public void setAuthorPageUrl(String authorPageUrl) {
		this.authorPageUrl = authorPageUrl;
	}
	
}
