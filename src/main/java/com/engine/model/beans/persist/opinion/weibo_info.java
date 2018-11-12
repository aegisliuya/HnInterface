//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.engine.model.beans.persist.opinion;

import java.util.Date;

public class weibo_info {
    String title;
    String abs;
    String author;
    Date postTime;
    String summary;
    Integer forwardNum;
    Integer commentNum;
    String userType;
    String uid;

    public weibo_info() {
    }

    public String getUserType() {
        return this.userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbs() {
        return this.abs;
    }

    public void setAbs(String abs) {
        this.abs = abs;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPostTime() {
        return this.postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public String getSummary() {
        return this.summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getForwardNum() {
        return this.forwardNum;
    }

    public void setForwardNum(Integer forwardNum) {
        this.forwardNum = forwardNum;
    }

    public Integer getCommentNum() {
        return this.commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public String toString() {
        return "weibo_info{title='" + this.title + '\'' + ", abs='" + this.abs + '\'' + ", author='" + this.author + '\'' + ", postTime=" + this.postTime + ", summary='" + this.summary + '\'' + ", forwardNum=" + this.forwardNum + ", commentNum=" + this.commentNum + ", userType='" + this.userType + '\'' + ", uid='" + this.uid + '\'' + '}';
    }
}
