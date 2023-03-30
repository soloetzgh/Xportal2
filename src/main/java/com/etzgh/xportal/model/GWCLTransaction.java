/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sunkwa-arthur
 */
@Entity
@XmlRootElement
public class GWCLTransaction implements Serializable {

    @Id
    private String unique_transid;
    private String merchant_id;
    private BigDecimal amount;
    private String trans_date;
    private String trans_channel;
    private String trans_status;
    private String trans_note;
    private String subscriber_id;
    private String issuer_code;
    private String payment_code;
    private String payment_type;
    private String mobile_no;
    private String transid;

    public String getUnique_transid() {
        return unique_transid;
    }

    public void setUnique_transid(String unique_transid) {
        this.unique_transid = unique_transid;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTrans_date() {
        return trans_date;
    }

    public void setTrans_date(String trans_date) {
        this.trans_date = trans_date;
    }

    public String getTrans_channel() {
        return trans_channel;
    }

    public void setTrans_channel(String trans_channel) {
        this.trans_channel = trans_channel;
    }

    public String getTrans_status() {
        return trans_status;
    }

    public void setTrans_status(String trans_status) {
        this.trans_status = trans_status;
    }

    public String getTrans_note() {
        return trans_note;
    }

    public void setTrans_note(String trans_note) {
        this.trans_note = trans_note;
    }

    public String getSubscriber_id() {
        return subscriber_id;
    }

    public void setSubscriber_id(String subscriber_id) {
        this.subscriber_id = subscriber_id;
    }

    public String getIssuer_code() {
        return issuer_code;
    }

    public void setIssuer_code(String issuer_code) {
        this.issuer_code = issuer_code;
    }

    public String getPayment_code() {
        return payment_code;
    }

    public void setPayment_code(String payment_code) {
        this.payment_code = payment_code;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getTransid() {
        return transid;
    }

    public void setTransid(String transid) {
        this.transid = transid;
    }

    @Override
    public String toString() {
        return "GWCLTransaction{" + "unique_transid=" + unique_transid + ", merchant_id=" + merchant_id + ", amount=" + amount + ", trans_date=" + trans_date + ", trans_channel=" + trans_channel + ", trans_status=" + trans_status + ", trans_note=" + trans_note + ", subscriber_id=" + subscriber_id + ", issuer_code=" + issuer_code + ", payment_code=" + payment_code + ", payment_type=" + payment_type + ", mobile_no=" + mobile_no + ", transid=" + transid + '}';
    }

}
