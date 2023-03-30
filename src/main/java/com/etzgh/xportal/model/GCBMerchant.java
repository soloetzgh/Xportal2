package com.etzgh.xportal.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class GCBMerchant implements Serializable {

    @Id
    private String unique_transid;
    private String transid;
    private String merchant;
    private BigDecimal trans_amount;
    private String trans_status;
    private String mobile_no;
    private String status;
    @Column(name = "trans_date", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date trans_date;

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

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public BigDecimal getTrans_amount() {
        return trans_amount;
    }

    public void setTrans_amount(BigDecimal trans_amount) {
        this.trans_amount = trans_amount;
    }

    public String getTrans_status() {
        return trans_status;
    }

    public void setTrans_status(String trans_status) {
        this.trans_status = trans_status;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public Date getTrans_date() {
        return trans_date;
    }

    public void setTrnxdate(Date trans_date) {
        this.trans_date = trans_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "GCBMerchant{" + "unique_transid=" + unique_transid + ", transid=" + transid + ", merchant=" + merchant + ", trans_amount=" + trans_amount + ", trans_status=" + trans_status + ", mobile_no=" + mobile_no + ", status=" + status + ", trans_date=" + trans_date + '}';
    }

}
