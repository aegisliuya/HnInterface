package com.engine.model.beans.es;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.Type;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;



@Entity
@Table(name = "sub_info")
@Document(indexName = "imonitor_new_v1",type = "subinfo")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
		getterVisibility = JsonAutoDetect.Visibility.NONE,
		isGetterVisibility = JsonAutoDetect.Visibility.NONE
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubInfo implements Cloneable, Serializable {

	public static final String spit_opera = ",";
	// 机构总数在1024-2048之间，将clientID移位到与12位及以上的位做位运算，EventsID与1-11位做位运算，最大化的保证hashCode的单一性
	private static final int EVENTS_NUM_LENGTH = 11;
	private static final long serialVersionUID = -8688556408801019930L;

	@Id
	@Column(length = 35)
	@org.springframework.data.annotation.Id
	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String id;
//	@Field(type = FieldType.String)
	private String author; // 作者
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "originalDataId")
	@Field(type = FieldType.Object)
	private OriginalData originalData; // 原始数据
	@Field(type = FieldType.Date)
	private Date crawlerTime; // 采集时间
	@Field(type = FieldType.Date)
	private Date lastReplyTime; // 最后回复时间
	@Field(type = FieldType.Date)
	private Date postTime; // 发布时间
	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String refId; // 首发信息ID
	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String similarUrl;
	@Transient
	@Field(type = FieldType.Integer)
	private Set<Integer> eventsIds = null;
	// 二级分类ID
	@Transient
	@Field(type = FieldType.Integer)
	private Set<Integer> category2Ids;
	//分类id
	@Transient
	@Field(type = FieldType.Integer)
	private Set<Integer> categoryIds;
	// 客户ID
	@Transient
	@Field(type = FieldType.Integer)
	private Set<Integer> clientIds;

	@JsonIgnore
	@Column(name = "institutionId")
	private Integer eventsId;
	@Field(type = FieldType.Integer)
	private Integer industryId; // 行业id
	@JsonIgnore
	private Integer category2Id;
	@JsonIgnore
	private Integer categoryId;
	@JsonIgnore
	private Integer clientId;

	@Field(type = FieldType.Integer)
	private Integer infoTypeId; // 信息类型ID
	@Field(type = FieldType.Integer)
	private Integer lastClickCount; // 点击数
	@Field(type = FieldType.Integer)
	private Integer lastCommentCount; // 暂时不用
	@Field(type = FieldType.Integer)
	private Integer lastReplyCount; // 回复数
	@Transient
	@Field(type = FieldType.Integer)
	private Set<Integer> parentEventsIds;
	@Field(type = FieldType.Integer)
	private Integer similarNumber; // 相似数
	@Field(type = FieldType.Integer)
	private Integer siteTypeId; // 媒体类型ID
	@Transient
	@Field(type = FieldType.Integer)
	private Set<Integer> category1Ids;
	@JsonIgnore
	private Integer category1Id;
	@Field(type = FieldType.Integer)
	private Integer webSimilarCount; // 百度相关数

	@Field(type = FieldType.Integer)
	private Integer replyIncPct; // 回复数即时增长百分比(Inc是increase缩写,Pct是percentage缩写)
	@Field(type = FieldType.Integer)
	private Integer replyIncVel; // 回复数即时增长速度，以1小时为间隔(vel是velocity缩写)
	@Field(type = FieldType.Integer)
	private Integer clickIncPct; // 点击数即时增长百分比
	@Field(type = FieldType.Integer)
	private Integer clickIncVel; // 回复数即时增长速度，以1小时为间隔
	@Field(type = FieldType.Integer)
	private Integer exposureIncPct; // 曝光数即时增长百分比，可能为负值
	@Field(type = FieldType.Integer)
	private Integer exposureIncVel; // 曝光数即时增长速度，以1小时为间隔，可能为负值
	@Field(type = FieldType.Integer)
	@Column(name = "websiteId")
	private Integer       sourceId;
	@Transient
	@Field(type = FieldType.Integer)
	private Set<Integer>  provinceIds;
	@Transient
	@Field(type = FieldType.Integer)
	private Set<Integer>  cityIds; // 城市ID
	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String remoteImgLinks; // 图片链接集合
	@Field(type = FieldType.Float)
	@Column(name = "sensitivity")
	private Float         sensitivity; // 敏感值
	@Field(type = FieldType.Integer)
	private Integer boardId; //

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String weiboUserId; // 微博用户ID

	@Field(type = FieldType.Integer)
	private Integer weiboUserType = 0; //微博用户类型

	@Field(type = FieldType.Integer)
	private Integer forWards; //是否是转发

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private String lightKeywords;

	@Field(type = FieldType.Boolean)
	private Boolean checked;
	/**
	 * 最后更新时间
	 */
	@Field(type = FieldType.Date)
	private Date    lastUpdateTime = new Date();
	/**
	 * 所属跟踪项目id集合
	 */
	@Transient
	@Field(type = FieldType.Integer)
	private Set<Integer> trackProjectIds;
	/**
	 * 是否首发
	 */
	@Field(type = FieldType.Boolean)
	@Column(columnDefinition = "TINYINT(1) DEFAULT 1 COMMENT '是否首发'")
	private Boolean isFirst;
	@JsonIgnore
	@Transient
	private Double correlation;
	@JsonIgnore
	@Transient
	private String candidateEventId = "";
	@JsonIgnore
	@Transient
	private boolean isOriginalEs = false;
	// @Transient
	// private List<Category> candidateCategoryList = new
	// ArrayList<Category>(1);
	// @Transient
	// private List<City> candidateCityList = new ArrayList<City>(1);
	// @Transient
	// private List<Province> candidateProvinceList = new
	// ArrayList<Province>(1);
	// @Transient
	// private List<Category1> candidateCategory1List = new
	// ArrayList<Category1>(1);
	// @Transient
	// private List<Category2> candidateCategory2List = new
	// ArrayList<Category2>(1);

	/**
	 * 属性 信息正负面 负面-1 中性:0 正面:1 热点：2 无关：3 二级负面：4 三级负面：5 四级负面：6
	 */
	@Enumerated()
	@Type(type = "com.model.bean.common.NatureReflect")
	@Field(type = FieldType.String, index = FieldIndex.not_analyzed)
	private Nature nature = Nature.UNSIGNED;

	@Field(type = FieldType.Boolean, index = FieldIndex.not_analyzed)
	private Boolean hasNatureConflict;


	/**
	 * 预警等级，0-未分级，1-1级，2-2级，3-3级，3级最高.默认值为
	 */
	@Field(type = FieldType.Integer)
	@Column(name = "earlyWarningGrade")
	private Integer warningGrade;

	@JsonIgnore
	private Integer featureFlag;

	/**
	 * 是否锁定
	 */
	@Field(type = FieldType.Boolean)
	private Boolean locked;
	@Field(type = FieldType.Float)
	private Float tf;

	@JsonIgnore
	@Column(length = 3)
	private Integer boardPriority = 0;
	@JsonIgnore
	private Integer bbsId = 0;
	@JsonIgnore
	@Transient
	private Integer hotWordsId;

	@Field(type = FieldType.Integer)
	private Integer detectiveFlag;

	//	private String localImgLinks;
	@Field(type = FieldType.Date)
	private Date trackEndTime;
	@Field(type = FieldType.Integer)
	private Integer trackFrequency;
	@Field(type = FieldType.Float)
	@Transient
	private Integer storeType;
	@JsonIgnore
	private Integer hide;
	@JsonIgnore
	@Transient
	private boolean					isInDb				= false;


	@JsonIgnore
	private Integer provinceId;
	@JsonIgnore
	private Integer cityId;
	@JsonIgnore
	private Integer parentEventsId = 0;
	//必须Boolean，boolean不会为null，所以new的时候自动赋值false，因此在做单字段更新会覆盖原有状态
	private Boolean                 isOrganInferred;

	/**
	 * 首页栏目ID
	 */
	@Transient
	@Field(type = FieldType.Integer)
	private Set<Integer> homeForumIds;

	@JsonIgnore
	@Column(name = "home_forum_id")
	private Integer homeForumId;                                        // 首页栏目ID

	@Transient
	private Boolean inDb;

	@JsonIgnore
	@Transient
	private long                      deliveryTag;

	public Boolean getIsOrganInferred() {
		return isOrganInferred;
	}

	public void setIsOrganInferred(Boolean isOrganInferred) {
		this.isOrganInferred = isOrganInferred;
	}

	public long getDeliveryTag() {
		return deliveryTag;
	}

	public void setDeliveryTag(long deliveryTag) {
		this.deliveryTag = deliveryTag;
	}

	//	private boolean sent;
//
//	public boolean isSent() {
//		return sent;
//	}
//
//	public void setSent(boolean sent) {
//		this.sent = sent;
//	}

	public void addCandidateEventId(Integer id) {
		if (candidateEventId.length() < 35) {
			StringBuilder resultStr = new StringBuilder(candidateEventId + id
					+ spit_opera);
			candidateEventId = resultStr.toString();
		} else
			System.out.println("candidateEventId超长");
	}

	public SubInfo(SubInfo subInfo) {
		this.author = subInfo.author;
		this.boardId = subInfo.boardId;
		this.category1Ids = subInfo.category1Ids;
		this.category2Ids = subInfo.category2Ids;
	}

	public Double getCorrelation() {
		return correlation;
	}

	public void setCorrelation(Double correlation) {
		this.correlation = correlation;
	}

	public SubInfo() {
	}

	public Boolean getIsFirst() {
		return isFirst;
	}

	public void setIsFirst(Boolean isFirst) {
		this.isFirst = isFirst;
	}


	// public boolean getChecked() {
	// return checked;
	// }
	//
	// public void setChecked(boolean checked) {
	// this.checked = checked;
	// }

	public String getRemoteImgLinks() {
		return remoteImgLinks;
	}

	public void setRemoteImgLinks(String remoteImgLinks) {
		this.remoteImgLinks = remoteImgLinks;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getCrawlerTime() {
		return crawlerTime;
	}

	public void setCrawlerTime(Date crawlerTime) {
		this.crawlerTime = crawlerTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getInfoTypeId() {
		return infoTypeId;
	}

	public void setInfoTypeId(Integer infoTypeId) {
		this.infoTypeId = infoTypeId;
	}

	public Integer getLastClickCount() {
		return lastClickCount;
	}

	public void setLastClickCount(Integer lastClickCount) {
		this.lastClickCount = lastClickCount;
	}

	public Integer getLastCommentCount() {
		return lastCommentCount;
	}

	public void setLastCommentCount(Integer lastCommentCount) {
		this.lastCommentCount = lastCommentCount;
	}

	public Integer getLastReplyCount() {
		return lastReplyCount;
	}

	public void setLastReplyCount(Integer lastReplyCount) {
		this.lastReplyCount = lastReplyCount;
	}

	public Date getLastReplyTime() {
		return lastReplyTime;
	}

	public void setLastReplyTime(Date lastReplyTime) {
		this.lastReplyTime = lastReplyTime;
	}

	public OriginalData getOriginalData() {
		return originalData;
	}

	public void setOriginalData(OriginalData originalData) {
		this.originalData = originalData;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public Integer getSimilarNumber() {
		return similarNumber;
	}

	public void setSimilarNumber(Integer similarNumber) {
		this.similarNumber = similarNumber;
	}

	public String getSimilarUrl() {
		return similarUrl;
	}

	public void setSimilarUrl(String similarUrl) {
		this.similarUrl = similarUrl;
	}

	public Integer getSiteTypeId() {
		return siteTypeId;
	}

	public void setSiteTypeId(Integer siteTypeId) {
		this.siteTypeId = siteTypeId;
	}

	public Integer getWebSimilarCount() {
		return webSimilarCount;
	}

	public void setWebSimilarCount(Integer webSimilarCount) {
		this.webSimilarCount = webSimilarCount;
	}


	public Integer getReplyIncPct() {
		return replyIncPct;
	}

	public void setReplyIncPct(Integer replyIncPct) {
		this.replyIncPct = replyIncPct;
	}

	public Integer getReplyIncVel() {
		return replyIncVel;
	}

	public void setReplyIncVel(Integer replyIncVel) {
		this.replyIncVel = replyIncVel;
	}

	public Integer getClickIncPct() {
		return clickIncPct;
	}

	public void setClickIncPct(Integer clickIncPct) {
		this.clickIncPct = clickIncPct;
	}

	public Integer getClickIncVel() {
		return clickIncVel;
	}

	public void setClickIncVel(Integer clickIncVel) {
		this.clickIncVel = clickIncVel;
	}

	public Integer getExposureIncPct() {
		return exposureIncPct;
	}

	public void setExposureIncPct(Integer exposureIncPct) {
		this.exposureIncPct = exposureIncPct;
	}

	public Integer getExposureIncVel() {
		return exposureIncVel;
	}

	public void setExposureIncVel(Integer exposureIncVel) {
		this.exposureIncVel = exposureIncVel;
	}

	public Integer getIndustryId() {
		return industryId;
	}

	public void setIndustryId(Integer industryId) {
		this.industryId = industryId;
	}

	public Float getSensitivity() {
		return sensitivity;
	}

	public void setSensitivity(Float sensitivity) {
		this.sensitivity = sensitivity;
	}

	public String getWeiboUserId() {
		return weiboUserId;
	}

	public void setWeiboUserId(String weiboUserId) {
		this.weiboUserId = weiboUserId;
	}

	// public boolean getLocked() {
	// return locked;
	// }
	//
	// public void setLocked(boolean locked) {
	// this.locked = locked;
	// }

	public Integer getBoardId() {
		return boardId;
	}

	public void setBoardId(Integer boardId) {
		this.boardId = boardId;
	}


	@Override
	public String toString() {
		if (originalData != null) {
			return originalData.getTitle() + ":" + originalData.getUrl();
		} else {
			return null;
		}
	}


	@JsonIgnore
	public String getFullInfo() {
		StringBuilder strBuf = new StringBuilder();
		strBuf.append("\nsid = "
				+ id
				+ ", refId = "
				+ refId
				+ ", title = "
				+ originalData.getTitle()
				+ ", url = "
				+ originalData.getUrl()
				+ ",isFirst ="
				+ isFirst
//				+ ",similarNumber =" + similarNumber
				+
				// " summary = "+ originalData.getSummary() +
				", webName = " + originalData.getWebName() + ", author = "
				+ author + ", postTime = "
				+ ((postTime == null) ? null : postTime) + ", infoTypeId = "
				+ infoTypeId + ", siteTypeId = " + siteTypeId + ", eventsId = "
				+ eventsIds + ", industryId = " + industryId + ", clientId = "
				+ clientIds + ", weiboUserId = " + weiboUserId
				+ ", crawlerTime = "
				+ (crawlerTime == null ? null : crawlerTime)
				+ ", category1Id = " + category1Ids + ", category2id = "
				+ category2Ids + ", provinceId = " + provinceIds + ", cityId = "
				+ cityIds + ", webSiteId = " + sourceId + ", sensitivity = " + sensitivity
//				+ ", remoteImgLinks = " + remoteImgLinks
				+ ", boardId = " + boardId);

		return strBuf.toString();
	}

	@Override
	public SubInfo clone() {
		try {
			SubInfo clone = (SubInfo) super.clone();
			clone.setCandidateEventId("");
			return clone;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}





	@Override
	public boolean equals(Object o) {
		if (o != null && o instanceof SubInfo) {
			SubInfo tmp = (SubInfo) o;
			return tmp.getId().equals(this.getId());
		} else {
			return false;
		}
	}

	/**
	 * id值太长，转为int会超过int范围 使用采集时间的hashcode，eventsID和clientID做位运算
	 *
	 * @author furaul
	 */
	@Override
	public int hashCode() {
		int timeCode = this.crawlerTime.hashCode();
//		Integer clientIDCode = this.getClientId();
//		Integer eventsIDCode = this.getEventsId();
		Integer clientIDCode = null;
		if(!this.getClientIds().isEmpty()){
			clientIDCode = this.getClientIds().iterator().next();
		}
		Integer eventsIDCode = null;
		if(!this.getEventsIds().isEmpty()) {
			eventsIDCode = this.getEventsIds().iterator().next();
		}
		if (clientIDCode != null && clientIDCode != 0)
			if (eventsIDCode != null && eventsIDCode != 0)
				return (timeCode) ^ (clientIDCode << EVENTS_NUM_LENGTH)
						^ (eventsIDCode);
			else
				return (timeCode) ^ (clientIDCode << EVENTS_NUM_LENGTH);
		return timeCode;
	}

	public Integer getFeatureFlag() {
		return featureFlag;
	}

	public void setFeatureFlag(Integer featureFlag) {
		this.featureFlag = featureFlag;
	}

	public static class Comparator_subInfo_postTime implements
			Comparator<SubInfo> {

		public int compare(SubInfo o1, SubInfo o2) {
			if (o1.getPostTime().after(o2.postTime)) {
				return 1;
			} else if (o1.getPostTime().before(o2.postTime)) {
				return -1;
			} else {
				return 0;
			}

		}
	}

	public static class Comparator_subInfo_crawlerTime implements
			Comparator<SubInfo> {

		public int compare(SubInfo o1, SubInfo o2) {
			if (o1.getCrawlerTime().after(o2.crawlerTime)) {
				return 1;
			} else if (o1.getCrawlerTime().before(o2.crawlerTime)) {
				return -1;
			} else {
				return 0;
			}
		}
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}


	public Integer getBoardPriority() {
		return boardPriority;
	}

	public void setBoardPriority(Integer boardPriority) {
		if (boardPriority > 255)
			boardPriority = 255;
		else if (boardPriority < 0)
			boardPriority = 0;
		this.boardPriority = boardPriority;
	}

	public Integer getBbsId() {
		return bbsId;
	}

	public void setBbsId(Integer bbsId) {
		this.bbsId = bbsId;
	}

	public Integer getHotWordsId() {
		return hotWordsId;
	}

	public void setHotWordsId(Integer hotWordsId) {
		this.hotWordsId = hotWordsId;
	}

	public String getCandidateEventId() {
		return candidateEventId;
	}

	public void setCandidateEventId(String candidateEventId) {
		this.candidateEventId = candidateEventId;
	}


	public Integer getDetectiveFlag() {
		return detectiveFlag;
	}

	public void setDetectiveFlag(Integer detectiveFlag) {
		this.detectiveFlag = detectiveFlag;
	}

	public Date getTrackEndTime() {
		return trackEndTime;
	}

	public void setTrackEndTime(Date trackEndTime) {
		this.trackEndTime = trackEndTime;
	}

	public Integer getTrackFrequency() {
		return trackFrequency;
	}

	public void setTrackFrequency(Integer trackFrequency) {
		this.trackFrequency = trackFrequency;
	}


	public Float getTf() {
		return tf;
	}

	public void setTf(Float tf) {
		this.tf = tf;
	}

	public boolean getIsInDb() {
		return isInDb;
	}

	public void setIsInDb(boolean isInDb) {
		this.isInDb = isInDb;
	}

	public Set<Integer> getEventsIds() {
		return eventsIds;
	}

	public void setEventsIds(Set<Integer> eventsIds) {
		this.eventsIds = eventsIds;
	}

	public Set<Integer> getCategory2Ids() {
		return category2Ids;
	}

	public void setCategory2Ids(Set<Integer> category2Ids) {
		this.category2Ids = category2Ids;
	}

	public Set<Integer> getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(Set<Integer> categoryIds) {
		this.categoryIds = categoryIds;
	}

	public Set<Integer> getClientIds() {
		return clientIds;
	}

	public void setClientIds(Set<Integer> clientIds) {
		this.clientIds = clientIds;
	}

	public Set<Integer> getParentEventsIds() {
		return parentEventsIds;
	}

	public void setParentEventsIds(Set<Integer> parentEventsIds) {
		this.parentEventsIds = parentEventsIds;
	}

	public Set<Integer> getCategory1Ids() {
		return category1Ids;
	}

	public void setCategory1Ids(Set<Integer> category1Ids) {
		this.category1Ids = category1Ids;
	}

	public Integer getSourceId() {
		return sourceId;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}

	public Set<Integer> getProvinceIds() {
		return provinceIds;
	}

	public void setProvinceIds(Set<Integer> provinceIds) {
		this.provinceIds = provinceIds;
	}

	public Set<Integer> getCityIds() {
		return cityIds;
	}

	public void setCityIds(Set<Integer> cityIds) {
		this.cityIds = cityIds;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Set<Integer> getTrackProjectIds() {
		return trackProjectIds;
	}

	public void setTrackProjectIds(Set<Integer> trackProjectIds) {
		this.trackProjectIds = trackProjectIds;
	}

	public Nature getNature() {
		return nature;
	}

	public void setNature(Nature nature) {
		this.nature = nature;
	}

	public void setHasNatureConflict(Boolean hasNatureConflict){
		this.hasNatureConflict = hasNatureConflict;
	}

	public Boolean getHasNatureConflict(){
		return hasNatureConflict;
	}

	public Integer getWarningGrade() {
		return warningGrade;
	}

	public void setWarningGrade(Integer warningGrade) {
		this.warningGrade = warningGrade;
	}

	public Integer getStoreType() {
		return storeType;
	}

	public void setStoreType(Integer storeType) {
		this.storeType = storeType;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public Integer getEventsId() {
		return eventsId;
	}

	public void setEventsId(Integer eventsId) {
		this.eventsId = eventsId;
	}

	public Integer getCategory1Id() {
		return category1Id;
	}

	public void setCategory1Id(Integer category1Id) {
		this.category1Id = category1Id;
	}

	public Integer getCategory2Id() {
		return category2Id;
	}

	public void setCategory2Id(Integer category2Id) {
		this.category2Id = category2Id;
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getlightKeywords() { return lightKeywords; }

	public void setLightKeywords(String lightKeywordsList) { this.lightKeywords = lightKeywordsList; }

	public Integer getHide() {
		return hide;
	}

	public void setHide(Integer hide) {
		this.hide = hide;
	}
	public Integer getParentEventsId() {
		return parentEventsId;
	}

	public void setParentEventsId(Integer parentEventsId) {
		this.parentEventsId = parentEventsId;
	}

	public void setWeiboUserType(Integer weiboUserType) { this.weiboUserType = weiboUserType;}
	public Integer getWeiboUserType() { return weiboUserType;}

	public void setForWards(int forWards){ this.forWards = forWards;}
	public Integer getForWards() { return forWards;}


	public void addEventsIds(Integer eventsId){
		if(eventsIds == null){
			eventsIds = new HashSet<>();
		}
		eventsIds.add(eventsId);

	}
	public void addClientIds(Integer clientId){
		if(clientIds == null){
			clientIds = new HashSet<>();
		}
		clientIds.add(clientId);

	}

	public void addCategory1Ids(Integer category1Id){
		if(category1Ids == null){
			category1Ids = new HashSet<>();
		}
		category1Ids.add(category1Id);

	}
	public void addCategory2Ids(Integer category2Id){
		if(category2Ids == null){
			category2Ids = new HashSet<>();
		}
		category2Ids.add(category2Id);

	}
	public void addCategoryIds(Integer categoryId){
		if(categoryIds == null){
			categoryIds = new HashSet<>();
		}
		categoryIds.add(categoryId);

	}
	public void addProvinceIds(Integer provinceId){
		if(provinceIds == null){
			provinceIds = new HashSet<>();
		}
		provinceIds.add(provinceId);

	}

	public void addCityIds(Integer cityId){
		if(cityIds == null){
			cityIds = new HashSet<>();
		}
		cityIds.add(cityId);

	}

	public void addParentEventsIds(Integer parentEventsId){
		if(parentEventsIds == null){
			parentEventsIds = new HashSet<>();
		}
		parentEventsIds.add(parentEventsId);
	}

	public void addTrackProjectIds(Integer trackProjectId){
		if(trackProjectIds == null){
			trackProjectIds = new HashSet<>();
		}
		trackProjectIds.add(trackProjectId);
	}

	public void removeTrackProjectId(Integer trackProjectId){
		if(trackProjectIds == null){
			return;
		}
		trackProjectIds.remove(trackProjectId);
	}

	public void removeParentEventsId(Integer parentEventsId){
		if(parentEventsIds == null){
			return;
		}
		parentEventsIds.remove(parentEventsId);
	}

	public void removeClientId(Integer clientId){
		if(clientIds == null){
			return;
		}
		clientIds.remove(clientId);

	}

	public void removeEventsId(Integer eventsId){
		if(eventsIds == null){
			return;
		}else{
			eventsIds.remove(eventsId);
		}
	}

	public boolean getIsOriginalEs() {
		return isOriginalEs;
	}

	public void setIsOriginalEs(boolean isOriginalEs) {
		this.isOriginalEs = isOriginalEs;
	}


	public Integer getHomeForumId() {
		return homeForumId;
	}

	public void setHomeForumId(Integer homeForumId) {
		this.homeForumId = homeForumId;
	}

	public Boolean isInDb() {
		return inDb;
	}

	public void setInDb(Boolean inDb) {
		this.inDb = inDb;
	}
}