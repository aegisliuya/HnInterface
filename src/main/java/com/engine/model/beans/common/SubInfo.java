package com.engine.model.beans.common;

import java.util.Date;

public class SubInfo implements java.io.Serializable, Cloneable {

	public static final String spit_opera = ",";
	private static final int EVENTS_NUM_LENGTH = 11;
	private static final long serialVersionUID = -8688556408801019930L;

	private String id;
	private String author; // 作者
	private OriginalData originalData; // 原始数据
	private Date crawlerTime; // 采集时间
	private Date lastReplyTime; // 最后回复时间
	private Date postTime; // 发布时间
	private String refId; // 首发信息ID
	private String similarUrl;
	private Integer industryId = 1; // 行业id
	private Integer infoTypeId; // 信息类型ID
//	private Integer lastClickCount; // 点击数
	private Integer lastCommentCount; // 暂时不用
//	private Integer lastReplyCount; // 回复数
	private Integer similarNumber; // 相似数
	private Integer siteTypeId; // 媒体类型ID
	private Integer webSimilarCount; // 百度相关数

	private Integer replyIncPct; // 回复数即时增长百分比(Inc是increase缩写,Pct是percentage缩写)
	private Integer replyIncVel; // 回复数即时增长速度，以1小时为间隔(vel是velocity缩写)
	private Integer clickIncPct; // 点击数即时增长百分比
	private Integer clickIncVel; // 回复数即时增长速度，以1小时为间隔
	private Integer exposureIncPct; // 曝光数即时增长百分比，可能为负值
	private Integer exposureIncVel; // 曝光数即时增长速度，以1小时为间隔，可能为负值
	private Integer       sourceId;
	private String   remoteImgLinks; // 图片链接集合
	private Integer boardId = 0; //

	private String weiboUserId; // 微博用户ID

	/**
	 * 最后更新时间
	 */
	private Date    lastUpdateTime;
	/**
	 * 是否首发
	 */
	private boolean isFirst = true;

	private Integer boardPriority = 0;
	private Integer bbsId = 0;

	private Integer detectiveFlag;

	private Date trackEndTime;
	private Integer trackFrequency;
	private Integer storeType = 0;
	private Integer hide = 0;
	private Integer forWards = 1;

	public SubInfo() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public OriginalData getOriginalData() {
		return originalData;
	}

	public void setOriginalData(OriginalData originalData) {
		this.originalData = originalData;
	}

	public Date getCrawlerTime() {
		return crawlerTime;
	}

	public void setCrawlerTime(Date crawlerTime) {
		this.crawlerTime = crawlerTime;
	}

	public Date getLastReplyTime() {
		return lastReplyTime;
	}

	public void setLastReplyTime(Date lastReplyTime) {
		this.lastReplyTime = lastReplyTime;
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

	public String getSimilarUrl() {
		return similarUrl;
	}

	public void setSimilarUrl(String similarUrl) {
		this.similarUrl = similarUrl;
	}

	public Integer getIndustryId() {
		return industryId;
	}

	public void setIndustryId(Integer industryId) {
		this.industryId = industryId;
	}

	public Integer getInfoTypeId() {
		return infoTypeId;
	}

	public void setInfoTypeId(Integer infoTypeId) {
		this.infoTypeId = infoTypeId;
	}

//	public Integer getLastClickCount() {
//		return lastClickCount;
//	}

//	public void setLastClickCount(Integer lastClickCount) {
//		this.lastClickCount = lastClickCount;
//	}

	public Integer getLastCommentCount() {
		return lastCommentCount;
	}

	public void setLastCommentCount(Integer lastCommentCount) {
		this.lastCommentCount = lastCommentCount;
	}

//	public Integer getLastReplyCount() {
//		return lastReplyCount;
//	}

//	public void setLastReplyCount(Integer lastReplyCount) {
//		this.lastReplyCount = lastReplyCount;
//	}

	public Integer getSimilarNumber() {
		return similarNumber;
	}

	public void setSimilarNumber(Integer similarNumber) {
		this.similarNumber = similarNumber;
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

	public Integer getSourceId() {
		return sourceId;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}

	public String getRemoteImgLinks() {
		return remoteImgLinks;
	}

	public void setRemoteImgLinks(String remoteImgLinks) {
		this.remoteImgLinks = remoteImgLinks;
	}

	public Integer getBoardId() {
		return boardId;
	}

	public void setBoardId(Integer boardId) {
		this.boardId = boardId;
	}

	public String getWeiboUserId() {
		return weiboUserId;
	}

	public void setWeiboUserId(String weiboUserId) {
		this.weiboUserId = weiboUserId;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public boolean getIsFirst() {
		return isFirst;
	}

	public void setIsFirst(boolean first) {
		isFirst = first;
	}

	public Integer getBoardPriority() {
		return boardPriority;
	}

	public void setBoardPriority(Integer boardPriority) {
		this.boardPriority = boardPriority;
	}

	public Integer getBbsId() {
		return bbsId;
	}

	public void setBbsId(Integer bbsId) {
		this.bbsId = bbsId;
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

	public Integer getStoreType() {
		return storeType;
	}


	public void setStoreType(Integer storeType) {
		this.storeType = storeType;
	}

	public Integer getHide() {
		return hide;
	}

	public void setHide(Integer hide) {
		this.hide = hide;
	}

	public Integer getForWards() {
		return forWards;
	}

	public void setForWards(Integer forWards) {
		this.forWards = forWards;
	}

	@Override
	public String toString() {
		return "SubInfo{" +
				"id='" + id + '\'' +
				", author='" + author + '\'' +
				", originalData=" + originalData +
				", crawlerTime=" + crawlerTime +
				", lastReplyTime=" + lastReplyTime +
				", postTime=" + postTime +
				", refId='" + refId + '\'' +
				", similarUrl='" + similarUrl + '\'' +
				", industryId=" + industryId +
				", infoTypeId=" + infoTypeId +
//				", lastClickCount=" + lastClickCount +
				", lastCommentCount=" + lastCommentCount +
//				", lastReplyCount=" + lastReplyCount +
				", similarNumber=" + similarNumber +
				", siteTypeId=" + siteTypeId +
				", webSimilarCount=" + webSimilarCount +
				", replyIncPct=" + replyIncPct +
				", replyIncVel=" + replyIncVel +
				", clickIncPct=" + clickIncPct +
				", clickIncVel=" + clickIncVel +
				", exposureIncPct=" + exposureIncPct +
				", exposureIncVel=" + exposureIncVel +
				", sourceId=" + sourceId +
				", remoteImgLinks='" + remoteImgLinks + '\'' +
				", boardId=" + boardId +
				", weiboUserId='" + weiboUserId + '\'' +
				", lastUpdateTime=" + lastUpdateTime +
				", isFirst=" + isFirst +
				", boardPriority=" + boardPriority +
				", bbsId=" + bbsId +
				", detectiveFlag=" + detectiveFlag +
				", trackEndTime=" + trackEndTime +
				", trackFrequency=" + trackFrequency +
				", storeType=" + storeType +
				", hide=" + hide +
				'}';
	}
}