package com.engine.model.beans.persist.opinion;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "case_detail")
public class CaseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    String   id;
    Integer   track_id;
    Integer    state;
    String    ah;
    String        ajlb;
    String  ajbt;
    String         keyword;
    String ay;
    String         cbr;
    String  cbfy;
    String         dsrlist;
    String  dsrinfo;
    Date    requestTime;
    Date   endTime;

    public Integer getTrack_id() {
        return track_id;
    }

    public void setTrack_id(Integer track_id) {
        this.track_id = track_id;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getAh() {
        return ah;
    }

    public void setAh(String ah) {
        this.ah = ah;
    }

    public String getAjlb() {
        return ajlb;
    }

    public void setAjlb(String ajlb) {
        this.ajlb = ajlb;
    }

    public String getAjbt() {
        return ajbt;
    }

    public void setAjbt(String ajbt) {
        this.ajbt = ajbt;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getAy() {
        return ay;
    }

    public void setAy(String ay) {
        this.ay = ay;
    }

    public String getCbr() {
        return cbr;
    }

    public void setCbr(String cbr) {
        this.cbr = cbr;
    }

    public String getCbfy() {
        return cbfy;
    }

    public void setCbfy(String cbfy) {
        this.cbfy = cbfy;
    }

    public String getDsrlist() {
        return dsrlist;
    }

    public void setDsrlist(String dsrlist) {
        this.dsrlist = dsrlist;
    }


    public String getDsrinfo() {
        return dsrinfo;
    }

    public void setDsrinfo(String dsrinfo) {
        this.dsrinfo = dsrinfo;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
