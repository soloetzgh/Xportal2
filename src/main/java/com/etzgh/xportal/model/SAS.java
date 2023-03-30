package com.etzgh.xportal.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class SAS implements Serializable {

    @Id
    private String unique_transid;
    private String transid;
    private String trans_channel;
    private BigDecimal amount;
    private String trans_note;
    private String trans_status;
    private String subscriber_id;
    private String mobile_no;
    private String product;
    private String acctNumber;
    private String name;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date response_date;

    public String getUnique_transid() {
        return unique_transid;
    }

    public void setUnique_transid(String unique_transid) {
        this.unique_transid = unique_transid;
    }

    public String getTransid() {
        return transid;
    }

    public void setTransid(String transid) {
        this.transid = transid;
    }

    public String getTrans_channel() {
        return trans_channel;
    }

    public void setTrans_channel(String trans_channel) {
        this.trans_channel = trans_channel;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTrans_note() {
        return trans_note;
    }

    public void setTrans_note(String trans_note) {
        this.trans_note = trans_note;
    }

    public String getTrans_status() {
        return trans_status;
    }

    public void setTrans_status(String trans_status) {
        this.trans_status = trans_status;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Date getResponse_date() {
        return response_date;
    }

    public void setResponse_date(Date response_date) {
        this.response_date = response_date;
    }

    public String getSubscriber_id() {
        return subscriber_id;
    }

    public void setSubscriber_id(String subscriber_id) {
        this.subscriber_id = subscriber_id;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getAcctNumber() {
        return acctNumber;
    }

    public void setAcctNumber(String acctNumber) {
        this.acctNumber = acctNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SAS{" + "unique_transid=" + unique_transid + ", transid=" + transid + ", trans_channel=" + trans_channel + ", amount=" + amount + ", trans_note=" + trans_note + ", trans_status=" + trans_status + ", ubscriber_id=" + subscriber_id + ", mobile_=" + mobile_no + ", product=" + product + ", response_date=" + response_date + '}';
    }

}
