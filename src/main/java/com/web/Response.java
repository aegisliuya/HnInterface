package com.web;

public class Response {

    private    String   code;
    //200表示成功，500表示失败

    private   String    msg;
    private    Object  data;
    private Integer currentPage;
    private Integer commentNum;
    String    title;
    String   content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Response(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Response(String code, String msg,Integer currentPage,Integer commentNum, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.commentNum=commentNum;
        this.currentPage=currentPage;
    }

    public Response(String code, String msg,Integer currentPage,Integer commentNum, String title, String content,Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.commentNum=commentNum;
        this.title = title;
        this.content=content;
        this.currentPage=currentPage;

    }

    public Response(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }



}
