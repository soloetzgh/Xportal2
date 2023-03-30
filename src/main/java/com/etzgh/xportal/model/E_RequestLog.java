package com.etzgh.xportal.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author seth.sebeh
 */
@Entity
@XmlRootElement
@Table(name = "e_requestlog")
public class E_RequestLog implements Serializable {

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
    @Column(name = "otherfee")
    private String elevy;

    private String srcname;

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

    public String getElevy() {
        return elevy;
    }

    public void setElevy(String elevy) {
        this.elevy = elevy;
    }

    public String getSrcname() {
        return srcname;
    }

    public void setSrcname(String srcname) {
        this.srcname = srcname;
    }

    @Override
    public String toString() {
        return "E_RequestLog{" + "id=" + id + ", transid=" + transid + ", card_num=" + card_num + ", trans_date=" + trans_date + ", trans_amount=" + trans_amount + ", trans_code=" + trans_code + ", merchant_code=" + merchant_code + ", response_code=" + response_code + ", response_time=" + response_time + ", trans_descr=" + trans_descr + ", client_id=" + client_id + ", request_id=" + request_id + ", fee=" + fee + ", currency=" + currency + ", channelid=" + channelid + ", stan=" + stan + ", reversed=" + reversed + ", elevy=" + elevy + ", srcname=" + srcname + '}';
    }

}
