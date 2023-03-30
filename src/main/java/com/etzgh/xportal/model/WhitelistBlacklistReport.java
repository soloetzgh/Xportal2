/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yaw.owusu-koranteng
 */
@Entity
@XmlRootElement
public class WhitelistBlacklistReport implements Serializable {

    @Id
    @Column(name = "response_time", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date response_time;
    @Column(name = "trans_date", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date trans_date;

//    private String ticket_number;
//    private String code;
//    private String response;
//    private String reference;
//    private String amount_won;
//    private String amount;
//    private String fee;
//    private String fg_error;
//    private String fg_referrence;
//    private String fg_message;
//    private String created;
//    private String bank_code;
//    private String phone_number;
//    private String status;
//    private String transid;
//    private String subscriber_id;
//    private String unique_transid;
//    private String response_date;
//    private String newCustomerId;
//    private String initiated_by;
//    private String initiated_date;
//    private String authorised_by;
//    private String authorised_date;
//    private String transactionReference;
//    private String reversal_reference;
    private String account;
    private String reason;
    private String action;
    private String authorizer;
    private String date_whitelisted;
    private String date_blacklisted;

    public Date getTrans_date() {
        return trans_date;
    }

    public void setTrans_date(Date trans_date) {
        this.trans_date = trans_date;
    }

    public Date getResponse_time() {
        return response_time;
    }

    public void setResponse_time(Date response_time) {
        this.response_time = response_time;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAuthorizer() {
        return authorizer;
    }

    public void setAuthorizer(String authorizer) {
        this.authorizer = authorizer;
    }

    public String getDate_whitelisted() {
        return date_whitelisted;
    }

    public void setDate_whitelisted(String date_whitelisted) {
        this.date_whitelisted = date_whitelisted;
    }

    public String getDate_blacklisted() {
        return date_blacklisted;
    }

    public void setDate_blacklisted(String date_blacklisted) {
        this.date_blacklisted = date_blacklisted;
    }

    @Override
    public String toString() {
        return "WhitelistBlacklistReport{" + "response_time=" + response_time + ", trans_date=" + trans_date + ", account=" + account + ", reason=" + reason + ", action=" + action + ", authorizer=" + authorizer + ", date_whitelisted=" + date_whitelisted + ", date_blacklisted=" + date_blacklisted + '}';
    }

}
