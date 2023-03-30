/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yaw.owusu-koranteng
 */
@Entity
@XmlRootElement
public class MobileMoneyReprocess implements Serializable {

    @Id
    private String reference;
    private String response_time;
    private String trans_date;
    private String paymentType;
    private String flag;
    private String switchCode;
    private String otherInfo;
    private String amount;
    private String status;
    private String originalReference;
    private String updatedReference;
    private String initiatedBy;
    private String initiatedDate;
    private String authorisedBy;
    private String authorisedDate;
    private String client;
    private String cardno;
    private String channel;

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getTrans_date() {
        return trans_date;
    }

    public void setTrans_date(String trans_date) {
        this.trans_date = trans_date;
    }

    public String getResponse_time() {
        return response_time;
    }

    public void setResponse_time(String response_time) {
        this.response_time = response_time;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getSwitchCode() {
        return switchCode;
    }

    public void setSwitchCode(String switchCode) {
        this.switchCode = switchCode;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOriginalReference() {
        return originalReference;
    }

    public void setOriginalReference(String originalReference) {
        this.originalReference = originalReference;
    }

    public String getUpdatedReference() {
        return updatedReference;
    }

    public void setUpdatedReference(String updatedReference) {
        this.updatedReference = updatedReference;
    }

    public String getInitiatedBy() {
        return initiatedBy;
    }

    public void setInitiatedBy(String initiatedBy) {
        this.initiatedBy = initiatedBy;
    }

    public String getInitiatedDate() {
        return initiatedDate;
    }

    public void setInitiatedDate(String initiatedDate) {
        this.initiatedDate = initiatedDate;
    }

    public String getAuthorisedBy() {
        return authorisedBy;
    }

    public void setAuthorisedBy(String authorisedBy) {
        this.authorisedBy = authorisedBy;
    }

    public String getAuthorisedDate() {
        return authorisedDate;
    }

    public void setAuthorisedDate(String authorisedDate) {
        this.authorisedDate = authorisedDate;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "MobileMoneyReprocess{" + "response_time=" + response_time + ", trans_date=" + trans_date + ", reference=" + reference + ", paymentType=" + paymentType + ", flag=" + flag + ", switchCode=" + switchCode + ", otherInfo=" + otherInfo + ", amount=" + amount + ", status=" + status + ", originalReference=" + originalReference + ", updatedReference=" + updatedReference + ", initiatedBy=" + initiatedBy + ", initiatedDate=" + initiatedDate + ", authorisedBy=" + authorisedBy + ", authorisedDate=" + authorisedDate + ", client=" + client + ", cardno=" + cardno + ", channel=" + channel + '}';
    }

}
