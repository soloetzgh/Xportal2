package com.etzgh.xportal.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sunkwa-arthur
 */
@XmlRootElement
@Entity
public class GCBOBVerified implements Serializable {

    private static final long serialVersionUID = 6075838680390178568L;

    @Id
    private int id;
    private String card_ACCT;
    private String branch;
    private String lname;
    private String fname;
    private String mobile;
    private String coy;
    private String acct_ID;
    private String email;
    private String status;
    private String city;
    private String state;
    private String country;
    private String secondary;
    private String status2;
    private String created;
    private String modified;
    private String bankok;
    private String centralok;
    private String batchid;
    private Boolean fault;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCard_ACCT() {
        return card_ACCT;
    }

    public void setCard_ACCT(String card_ACCT) {
        this.card_ACCT = card_ACCT;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCoy() {
        return coy;
    }

    public void setCoy(String coy) {
        this.coy = coy;
    }

    public String getAcct_ID() {
        return acct_ID;
    }

    public void setAcct_ID(String acct_ID) {
        this.acct_ID = acct_ID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSecondary() {
        return secondary;
    }

    public void setSecondary(String secondary) {
        this.secondary = secondary;
    }

    public String getStatus2() {
        return status2;
    }

    public void setStatus2(String status2) {
        this.status2 = status2;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getBankok() {
        return bankok;
    }

    public void setBankok(String bankok) {
        this.bankok = bankok;
    }

    public String getCentralok() {
        return centralok;
    }

    public void setCentralok(String centralok) {
        this.centralok = centralok;
    }

    public String getBatchid() {
        return batchid;
    }

    public void setBatchid(String batchid) {
        this.batchid = batchid;
    }

    public Boolean getFault() {
        return fault;
    }

    public void setFault(Boolean fault) {
        this.fault = fault;
    }

    @Override
    public String toString() {
        return "GCBOBVerified{" + "id=" + id + ", card_ACCT=" + card_ACCT + ", branch=" + branch + ", lname=" + lname + ", fname=" + fname + ", mobile=" + mobile + ", coy=" + coy + ", acct_ID=" + acct_ID + ", email=" + email + ", status=" + status + ", city=" + city + ", state=" + state + ", country=" + country + ", secondary=" + secondary + ", status2=" + status2 + ", created=" + created + ", modified=" + modified + ", bankok=" + bankok + ", centralok=" + centralok + ", batchid=" + batchid + ", fault=" + fault + '}';
    }

}
