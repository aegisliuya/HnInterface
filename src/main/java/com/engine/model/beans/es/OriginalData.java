package com.engine.model.beans.es;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "original_data")
@Document(indexName = "imonitor_new_v1",type = "originalData")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
		getterVisibility = JsonAutoDetect.Visibility.NONE,
		isGetterVisibility = JsonAutoDetect.Visibility.NONE
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OriginalData implements Serializable {

	private static final long serialVersionUID = 5573870263714708302L;
	@Id
	@Column(length = 32)
	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
	private String id;
	@Column(nullable = false, length = 100)
//	@Field(type= FieldType.String)
	private String title;
	@Column(nullable = false, length = 65535)
//	@Field(type= FieldType.String)
	private String summary;
	@Column(nullable = false, length = 450)
	@Field(type= FieldType.String,index = FieldIndex.not_analyzed)
	private String url;

	@Column(length = 45)
//	@Field(type= FieldType.String)
	private String webName;
	@Field(type = FieldType.Integer)
	private Integer homepageState;
	@Field(type = FieldType.Integer)
	private Integer mediaType = 0;
	/**
	 * 作者链接
	 */
//	@Column(name = "url_author")
//	@Field(type = FieldType.String,index = FieldIndex.not_analyzed)
//	private String          authorUrl;
	@Field(type = FieldType.Integer)
	private Integer userIdSensitivity = 0;
	//微博的首发id
//	@Field(type = FieldType.String)
	private String  firstId;
	/**
	 * 是否含有视频
	 */
	@Field(type = FieldType.Boolean)
	private Boolean hasVideo = false;
	/**
	 * 智能摘要
	 */
	@Column(name = "abstract")
//	@Field(type = FieldType.String)
	private String abs;
	//微博用户大小v标识，0表示不是v，1为小v，2为大V
	@Column(columnDefinition = "TINYINT DEFAULT 0")
	@Field(type = FieldType.Integer)
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

	public Boolean getHasVideo() {
		return hasVideo;
	}

	public void setHasVideo(Boolean hasVideo) {
		this.hasVideo = hasVideo;
	}

	public String getAbs() {
		return abs;
	}

	public void setAbs(String abs) {
		this.abs = abs;
	}

	public Integer getvValue() {
		return vValue;
	}

	public void setvValue(Integer vValue) {
		this.vValue = vValue;
	}

}
