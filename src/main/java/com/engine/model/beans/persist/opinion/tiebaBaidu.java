package com.engine.model.beans.persist.opinion;

public class tiebaBaidu {

  private String name;
  private String url;
  private String imgUrl;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }

  @Override
  public String toString() {
    return "tiebaBaidu{" +
        "name='" + name + '\'' +
        ", url='" + url + '\'' +
        ", imgUrl='" + imgUrl + '\'' +
        '}';
  }
}
