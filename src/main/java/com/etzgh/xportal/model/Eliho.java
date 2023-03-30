package com.etzgh.xportal.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Eliho implements Serializable {

    private static final long serialVersionUID = 2644659847230738781L;

    @Id
    private String activityref;
    private String initcatgry;
    private String initidno;
    private String initname;
    private String initmsisdn;
    private String activitycode;
    private String targetcatgry;
    private String targetidno;
    private String targetmsisdn;
    private Double amount;
    private String activitystatus;
    private String xtrainfo1;
    private String xtrainfo2;
    private String created;

    public String getActivityref() {
        return activityref;
    }

    public void setActivityref(String activityref) {
        this.activityref = activityref;
    }

    public String getInitcatgry() {
        return initcatgry;
    }

    public void setInitcatgry(String initcatgry) {
        this.initcatgry = initcatgry;
    }

    public String getInitidno() {
        return initidno;
    }

    public void setInitidno(String initidno) {
        this.initidno = initidno;
    }

    public String getInitname() {
        return initname;
    }

    public void setInitname(String initname) {
        this.initname = initname;
    }

    public String getInitmsisdn() {
        return initmsisdn;
    }

    public void setInitmsisdn(String initmsisdn) {
        this.initmsisdn = initmsisdn;
    }

    public String getActivitycode() {
        return activitycode;
    }

    public void setActivitycode(String activitycode) {
        this.activitycode = activitycode;
    }

    public String getTargetcatgry() {
        return targetcatgry;
    }

    public void setTargetcatgry(String targetcatgry) {
        this.targetcatgry = targetcatgry;
    }

    public String getTargetidno() {
        return targetidno;
    }

    public void setTargetidno(String targetidno) {
        this.targetidno = targetidno;
    }

    public String getTargetmsisdn() {
        return targetmsisdn;
    }

    public void setTargetmsisdn(String targetmsisdn) {
        this.targetmsisdn = targetmsisdn;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getActivitystatus() {
        return activitystatus;
    }

    public void setActivitystatus(String activitystatus) {
        this.activitystatus = activitystatus;
    }

    public String getXtrainfo1() {
        return xtrainfo1;
    }

    public void setXtrainfo1(String xtrainfo1) {
        this.xtrainfo1 = xtrainfo1;
    }

    public String getXtrainfo2() {
        return xtrainfo2;
    }

    public void setXtrainfo2(String xtrainfo2) {
        this.xtrainfo2 = xtrainfo2;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "Eliho{" + "activityref=" + activityref + ", initcatgry=" + initcatgry + ", initidno=" + initidno + ", initname=" + initname + ", initmsisdn=" + initmsisdn + ", activitycode=" + activitycode + ", targetcatgry=" + targetcatgry + ", targetidno=" + targetidno + ", targetmsisdn=" + targetmsisdn + ", amount=" + amount + ", activitystatus=" + activitystatus + ", xtrainfo1=" + xtrainfo1 + ", xtrainfo2=" + xtrainfo2 + ", created=" + created + '}';
    }
}
