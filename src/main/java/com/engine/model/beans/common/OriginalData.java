package com.engine.model.beans.common;

import java.io.Serializable;

public class OriginalData implements Serializable {

	private static final long serialVersionUID = 5573870263714708302L;
	private String id;
	private String title;
	private String summary;
	private String url;
	private String webName;
	private Integer homepageState;
	private Integer mediaType = 0;
	/**
	 * 作者链接
	 */
	private Integer userIdSensitivity = 0;
	//微博的首发id
	private String  firstId;
	/**
	 * 是否含有视频
	 */
	private Boolean hasVideo = false;
	//微博用户大小v标识，0表示不是v，1为小v，2为大V
	private Integer vValue = 0;

	public Integer getHomepageState() {
		return homepageState;
	}

	public void setHomepageState(Integer homepageState) {
		this.homepageState = homepageState;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getWebName() {
		return webName;
	}

	public void setWebName(String webName) {
		this.webName = webName;
	}

	public Integer getMediaType() {
		return mediaType;
	}

	public void setMediaType(Integer mediaType) {
		this.mediaType = mediaType;
	}

	public Integer getUserIdSensitivity() {
		return userIdSensitivity;
	}

	public void setUserIdSensitivity(Integer userIdSensitivity) {
		this.userIdSensitivity = userIdSensitivity;
	}

	public String getFirstId() {
		return firstId;
	}

	public void setFirstId(String firstId) {
		this.firstId = firstId;
	}

	public boolean isHasVideo() {
		return hasVideo;
	}

	public void setHasVideo(boolean hasVideo) {
		this.hasVideo = hasVideo;
	}

	public Integer getvValue() {
		return vValue;
	}

	public void setvValue(Integer vValue) {
		this.vValue = vValue;
	}

	@Override
	public String toString() {
		return "OriginalData{" +
				"id='" + id + '\'' +
				", title='" + title + '\'' +
				", summary='" + summary + '\'' +
				", url='" + url + '\'' +
				", webName='" + webName + '\'' +
				", homepageState=" + homepageState +
				", mediaType=" + mediaType +
				", userIdSensitivity=" + userIdSensitivity +
				", firstId='" + firstId + '\'' +
				", hasVideo=" + hasVideo +
				", vValue=" + vValue +
				'}';
	}
}
