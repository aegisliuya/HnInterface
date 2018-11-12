package com.engine.model.beans.persist.opinion;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "track_project")
public class TrackProject implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    //	@Column(nullable = false)
    private String[] keywordsGroups;            // 关键词数组
    //	@Column(nullable = false)
    private Integer grade = 1;
    private Integer provinceId;
    private Integer cityId;
    private Integer category1Id;


    private Integer category2Id;
    private Integer eventsNature;
    private Integer  enable;



    private String excludeKeywords;    // 排除关键词
    //	@Column(nullable = false)
    private Date createTime;
    //	@Column(nullable = false)
    private Date startTime;
    private Date endTime;
    private String sensitiveKeywords;    // 敏感关键词
    private String weiboIds;            // 微博关注人物ID（多个）
    private String name;
    private String institutionIds;    // 所属机构ID（多个）
    private Integer parentId;
    //	@Column(nullable = false)
    private Integer clientId;

    private Integer[] infoTypeIds;                // 采集范围 信息类型（多个）
    //	private String		infoTypeIds;				// 采集范围 信息类型（多个）
    private Integer orderType;                    // 微博搜索结构排序方式 1-实时 2-热门
    private Integer crawlerGradeId;            // 采集等级
    private Integer maxKeywordsDistance;        // 关键词最大语义距离
    private Integer maxExcludeKeywordsDistance; // 排除关键词最大语义距离
    //	@Column(nullable = false)
//    private Boolean enable = true;                // 是否启用
    private Date updateTime;
    private Integer noCrawl;                    //是否采集
    private Integer eventsId;

    //新加案件跟踪的字段
    private String caseId;                     //案件标识
    private Integer[] siteTypeIds;

    private boolean caseEnable;                 //案件跟踪项目是否开启（人工校正后才能设为true）
    private String keywords;
    private Integer type;


    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer[] getSiteTypeIds() {
        return siteTypeIds;
    }

    public void setSiteTypeIds(Integer[] siteTypeIds) {
        this.siteTypeIds = siteTypeIds;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Integer getEventsNature() {
        return eventsNature;
    }

    public void setEventsNature(Integer eventsNature) {
        this.eventsNature = eventsNature;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String[] getKeywordsGroups() {
        return keywordsGroups;
    }

    public void setKeywordsGroups(String[] keywordsGroups) {
        this.keywordsGroups = keywordsGroups;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
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

    public String getExcludeKeywords() {
        return excludeKeywords;
    }

    public void setExcludeKeywords(String excludeKeywords) {
        this.excludeKeywords = excludeKeywords;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getSensitiveKeywords() {
        return sensitiveKeywords;
    }

    public void setSensitiveKeywords(String sensitiveKeywords) {
        this.sensitiveKeywords = sensitiveKeywords;
    }

    public String getWeiboIds() {
        return weiboIds;
    }

    public void setWeiboIds(String weiboIds) {
        this.weiboIds = weiboIds;
    }

    public String getInstitutionIds() {
        return institutionIds;
    }

    public void setInstitutionIds(String institutionIds) {
        this.institutionIds = institutionIds;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer[] getInfoTypeIds() {
        return infoTypeIds;
    }

    public void setInfoTypeIds(Integer[] infoTypeIds) {
        this.infoTypeIds = infoTypeIds;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getCrawlerGradeId() {
        return crawlerGradeId;
    }

    public void setCrawlerGradeId(Integer crawlerGradeId) {
        this.crawlerGradeId = crawlerGradeId;
    }

    public Integer getMaxKeywordsDistance() {
        return maxKeywordsDistance;
    }

    public void setMaxKeywordsDistance(Integer maxKeywordsDistance) {
        this.maxKeywordsDistance = maxKeywordsDistance;
    }

    public Integer getMaxExcludeKeywordsDistance() {
        return maxExcludeKeywordsDistance;
    }

    public void setMaxExcludeKeywordsDistance(Integer maxExcludeKeywordsDistance) {
        this.maxExcludeKeywordsDistance = maxExcludeKeywordsDistance;
    }


    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getNoCrawl() {
        return noCrawl;
    }

    public void setNoCrawl(Integer noCrawl) {
        this.noCrawl = noCrawl;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public boolean isCaseEnable() {
        return caseEnable;
    }

    public void setCaseEnable(boolean caseEnable) {
        this.caseEnable = caseEnable;
    }

    public Integer getEventsId() {
        return eventsId;
    }

    public void setEventsId(Integer eventsId) {
        this.eventsId = eventsId;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


}
