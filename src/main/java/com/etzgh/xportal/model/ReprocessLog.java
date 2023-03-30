package com.etzgh.xportal.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sunkwa-arthur
 */
@Entity
@XmlRootElement

public class ReprocessLog implements Serializable {

    private static final long serialVersionUID = 6531404133896734836L;

    @Id
    private String reference;
    private String product;
    private String account;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date_authorized;
    private String authorized_by;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date trans_date;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date_initiated;
    private String initiated_by;
    private BigDecimal amount;
    private String status;
    private String bank_code;
    private String rsp_message;
    private String rsp_code;
    private String trans_type;
    private String otherinfo;
    private String settled;
    private String mac;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public Date getDate_initiated() {
        return date_initiated;
    }

    public void setDate_initiated(Date date_initiated) {
        this.date_initiated = date_initiated;
    }

    public String getInitiated_by() {
        return initiated_by;
    }

    public void setInitiated_by(String initiated_by) {
        this.initiated_by = initiated_by;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBank_code() {
        return bank_code;
    }

    public void setBank_code(String bank_code) {
        this.bank_code = bank_code;
    }

    public String getRsp_message() {
        return rsp_message;
    }

    public void setRsp_message(String rsp_message) {
        this.rsp_message = rsp_message;
    }

    public String getRsp_code() {
        return rsp_code;
    }

    public void setRsp_code(String rsp_code) {
        this.rsp_code = rsp_code;
    }

    public Date getTrans_date() {
        return trans_date;
    }

    public void setTrans_date(Date trans_date) {
        this.trans_date = trans_date;
    }

    public String getTrans_type() {
        return trans_type;
    }

    public void setTrans_type(String trans_type) {
        this.trans_type = trans_type;
    }

    public String getOtherinfo() {
        return otherinfo;
    }

    public void setOtherinfo(String otherinfo) {
        this.otherinfo = otherinfo;
    }

    public String getSettled() {
        return settled;
    }

    public void setSettled(String settled) {
        this.settled = settled;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    @Override
    public String toString() {
        return "ReprocessLog{" + "reference=" + reference + ", product=" + product + ", account=" + account + ", date_authorized=" + date_authorized + ", authorized_by=" + authorized_by + ", trans_date=" + trans_date + ", date_initiated=" + date_initiated + ", initiated_by=" + initiated_by + ", amount=" + amount + ", status=" + status + ", bank_code=" + bank_code + ", rsp_message=" + rsp_message + ", rsp_code=" + rsp_code + ", trans_type=" + trans_type + ", otherinfo=" + otherinfo + ", settled=" + settled + ", mac=" + mac + '}';
    }
}
