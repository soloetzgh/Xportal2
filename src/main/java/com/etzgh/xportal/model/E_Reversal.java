package com.etzgh.xportal.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author seth.sebeh
 */
@Entity
@XmlRootElement
@Table(name = "e_reversal")
@NamedQueries({
    @NamedQuery(name = "E_Reversal.duplicateCheck", query = "SELECT p FROM E_Reversal p WHERE p.transid = :id")
})
public class E_Reversal implements Serializable {

    private static final long serialVersionUID = 5665322911360730282L;

    @Id
    private int id;
    private String transid;
    private String card_num;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date trans_date;
    private BigDecimal trans_amount;
    private String trans_code;
    private String merchant_code;
    private String response_code;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date response_time;
    private String trans_descr;
    private String client_id;
    private String request_id;
    private String fee;
    private String currency;
    private String channelid;
    private String stan;
    private String reversed;
    private String appname;
    @Column(updatable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date_initiated;
    private Date date_authorized;
    private String authorized_by;
    @Column(updatable = false)
    private String initiated_by;
    private String status;

    private String srcname;

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTransid() {
        return transid;
    }

    public void setTransid(String transid) {
        this.transid = transid;
    }

    public String getCard_num() {
        return card_num;
    }

    public void setCard_num(String card_num) {
        this.card_num = card_num;
    }

    public Date getTrans_date() {
        return trans_date;
    }

    public void setTrans_date(Date trans_date) {
        this.trans_date = trans_date;
    }

    public BigDecimal getTrans_amount() {
        return trans_amount;
    }

    public void setTrans_amount(BigDecimal trans_amount) {
        this.trans_amount = trans_amount;
    }

    public String getTrans_code() {
        return trans_code;
    }

    public void setTrans_code(String trans_code) {
        this.trans_code = trans_code;
    }

    public String getMerchant_code() {
        return merchant_code;
    }

    public void setMerchant_code(String merchant_code) {
        this.merchant_code = merchant_code;
    }

    public String getResponse_code() {
        return response_code;
    }

    public void setResponse_code(String response_code) {
        this.response_code = response_code;
    }

    public Date getResponse_time() {
        return response_time;
    }

    public void setResponse_time(Date response_time) {
        this.response_time = response_time;
    }

    public String getTrans_descr() {
        return trans_descr;
    }

    public void setTrans_descr(String trans_descr) {
        this.trans_descr = trans_descr;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getChannelid() {
        return channelid;
    }

    public void setChannelid(String channelid) {
        this.channelid = channelid;
    }

    public String getStan() {
        return stan;
    }

    public void setStan(String stan) {
        this.stan = stan;
    }

    public String getReversed() {
        return reversed;
    }

    public void setReversed(String reversed) {
        this.reversed = reversed;
    }

    public Date getDate_initiated() {
        return date_initiated;
    }

    public void setDate_initiated(Date date_initiated) {
        this.date_initiated = date_initiated;
    }

    public Date getDate_authorized() {
        return date_authorized;
    }

    public void setDate_authorized(Date date_authorized) {
        this.date_authorized = date_authorized;
    }

    public String getAuthorized_by() {
        return authorized_by;
    }

    public void setAuthorized_by(String authorized_by) {
        this.authorized_by = authorized_by;
    }

    public String getInitiated_by() {
        return initiated_by;
    }

    public void setInitiated_by(String initiated_by) {
        this.initiated_by = initiated_by;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSrcname() {
        return srcname;
    }

    public void setSrcname(String srcname) {
        this.srcname = srcname;
    }

    @Override
    public String toString() {
        return "E_Reversal{" + "id=" + id + ", transid=" + transid + ", card_num=" + card_num + ", trans_date=" + trans_date + ", trans_amount=" + trans_amount + ", trans_code=" + trans_code + ", merchant_code=" + merchant_code + ", response_code=" + response_code + ", response_time=" + response_time + ", trans_descr=" + trans_descr + ", client_id=" + client_id + ", request_id=" + request_id + ", fee=" + fee + ", currency=" + currency + ", channelid=" + channelid + ", stan=" + stan + ", reversed=" + reversed + ", appname=" + appname + ", date_initiated=" + date_initiated + ", date_authorized=" + date_authorized + ", authorized_by=" + authorized_by + ", initiated_by=" + initiated_by + ", status=" + status + ", srcname=" + srcname + '}';
    }

}
