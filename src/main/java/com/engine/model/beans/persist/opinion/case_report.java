package com.engine.model.beans.persist.opinion;

import com.engine.model.beans.es.Nature;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "case_report")
public class case_report{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    String		caseId;
    String		title;
    String      url;
    Date      postTime;
    Integer     grade;
    String    label;
    String		 author;
    String		 webName;
    Integer     infotypeId;
    Integer     sitetypeId;
    String		summary;
    String  track_id;


    public String getTrack_id() {
        return track_id;
    }

    public void setTrack_id(String track_id) {
        this.track_id = track_id;
    }

    public String getLabel() {
        return label;
    }




    public String getUrl() {
        return url;
    }

    public String getLabel(String label) {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUrl(String url) {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }



    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getWebName() {
        return webName;
    }

    public void setWebName(String webName) {
        this.webName = webName;
    }

    public Integer getInfotypeId() {
        return infotypeId;
    }

    public void setInfotypeId(Integer infotypeId) {
        this.infotypeId = infotypeId;
    }

    public Integer getSitetypeId() {
        return sitetypeId;
    }

    public void setSitetypeId(Integer sitetypeId) {
        this.sitetypeId = sitetypeId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
