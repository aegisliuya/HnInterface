package com.engine.model.beans.persist.opinion;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "case_detail")
public class case_detail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer  id;

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
    String caseId;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }


    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
