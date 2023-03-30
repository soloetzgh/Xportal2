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
public class VasgateReprocess implements Serializable {

    @Id
    @Column(name = "response_time", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date response_time;
    @Column(name = "trans_date", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date trans_date;

    private String status;
    private String account;
    private String client;
    private String vastype;
    private String amount;
    private String mobile;
    private String reference;
    private String narration;
    private String transtype;
    private String mainoptions;
    private String provider;
    private String destaccount;
    private String mmno;
    private String mmnocode;
    private String response_code;
    private String response_message;

    private String original_reference;
    private String updated_reference;
    private String initiated_by;
    private String initiated_date;
    private String authorized_by;
    private String authorized_date;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getVastype() {
        return vastype;
    }

    public void setVastype(String vastype) {
        this.vastype = vastype;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getTranstype() {
        return transtype;
    }

    public void setTranstype(String transtype) {
        this.transtype = transtype;
    }

    public String getMainoptions() {
        return mainoptions;
    }

    public void setMainoptions(String mainoptions) {
        this.mainoptions = mainoptions;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getDestaccount() {
        return destaccount;
    }

    public void setDestaccount(String destaccount) {
        this.destaccount = destaccount;
    }

    public String getMmno() {
        return mmno;
    }

    public void setMmno(String mmno) {
        this.mmno = mmno;
    }

    public String getMmnocode() {
        return mmnocode;
    }

    public void setMmnocode(String mmnocode) {
        this.mmnocode = mmnocode;
    }

    public String getOriginal_reference() {
        return original_reference;
    }

    public void setOriginal_reference(String original_reference) {
        this.original_reference = original_reference;
    }

    public String getUpdated_reference() {
        return updated_reference;
    }

    public void setUpdated_reference(String updated_reference) {
        this.updated_reference = updated_reference;
    }

    public String getInitiated_by() {
        return initiated_by;
    }

    public void setInitiated_by(String initiated_by) {
        this.initiated_by = initiated_by;
    }

    public String getInitiated_date() {
        return initiated_date;
    }

    public void setInitiated_date(String initiated_date) {
        this.initiated_date = initiated_date;
    }

    public String getAuthorized_by() {
        return authorized_by;
    }

    public void setAuthorized_by(String authorized_by) {
        this.authorized_by = authorized_by;
    }

    public String getAuthorized_date() {
        return authorized_date;
    }

    public void setAuthorized_date(String authorized_date) {
        this.authorized_date = authorized_date;
    }

    public String getResponse_code() {
        return response_code;
    }

    public void setResponse_code(String response_code) {
        this.response_code = response_code;
    }

    public String getResponse_message() {
        return response_message;
    }

    public void setResponse_message(String response_message) {
        this.response_message = response_message;
    }

    @Override
    public String toString() {
        return "VasgateReprocess{" + "response_time=" + response_time + ", trans_date=" + trans_date + ", status=" + status + ", account=" + account + ", client=" + client + ", vastype=" + vastype + ", amount=" + amount + ", mobile=" + mobile + ", reference=" + reference + ", narration=" + narration + ", transtype=" + transtype + ", mainoptions=" + mainoptions + ", provider=" + provider + ", destaccount=" + destaccount + ", mmno=" + mmno + ", mmnocode=" + mmnocode + ", response_code=" + response_code + ", response_message=" + response_message + ", original_reference=" + original_reference + ", updated_reference=" + updated_reference + ", initiated_by=" + initiated_by + ", initiated_date=" + initiated_date + ", authorized_by=" + authorized_by + ", authorized_date=" + authorized_date + '}';
    }
    
    

}
