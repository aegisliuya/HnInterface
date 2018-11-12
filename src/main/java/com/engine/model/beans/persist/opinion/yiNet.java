package com.engine.model.beans.persist.opinion;

import java.util.Date;

public class yiNet {

  private String commentId; // 原评论的id，采集；对于新浪新闻，是mid
  private String newsId; // 文章id，采集；对于腾讯新闻，是targetId； 对于网易新闻，是docId； 对于新浪新闻，是newsId
  private String content; // 内容，采集
  private String author;
  private Date releaseTime;
  private String url; // 文章的url
  private int sourceId; // 稿源id
  private Object params; // 其他的参数，包括网易需要的参数productKey，新浪需要的参数channel
  private Integer vote;

  public String getCommentId() {
    return commentId;
  }

  public void setCommentId(String commentId) {
    this.commentId = commentId;
  }

  public String getNewsId() {
    return newsId;
  }

  public void setNewsId(String newsId) {
    this.newsId = newsId;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public Date getReleaseTime() {
    return releaseTime;
  }

  public void setReleaseTime(Date releaseTime) {
    this.releaseTime = releaseTime;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public int getSourceId() {
    return sourceId;
  }

  public void setSourceId(int sourceId) {
    this.sourceId = sourceId;
  }

  public Object getParams() {
    return params;
  }

  public void setParams(Object params) {
    this.params = params;
  }

  public Integer getVote() {
    return vote;
  }

  public void setVote(Integer vote) {
    this.vote = vote;
  }

  @Override
  public String toString() {
    return "yiNet{" +
        "commentId='" + commentId + '\'' +
        ", newsId='" + newsId + '\'' +
        ", content='" + content + '\'' +
        ", author='" + author + '\'' +
        ", releaseTime=" + releaseTime +
        ", url='" + url + '\'' +
        ", sourceId=" + sourceId +
        ", params='" + params + '\'' +
        ", vote=" + vote +
        '}';
  }
}
