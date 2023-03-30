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

/**
 *
 * @author sunkwa-arthur
 */
@Entity
@XmlRootElement
public class ReproVTU implements Serializable {

    @Id
    private String id;
    private String unique_transid;
    private BigDecimal amount;
    private String dest_account;
    private String response_code;
    private String response_message;
    private String provider;

    @Column(name = "trans_date", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date trans_date;
    @Column(name = "response_date", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date response_date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnique_transid() {
        return unique_transid;
    }

    public void setUnique_transid(String unique_transid) {
        this.unique_transid = unique_transid;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Date getTrans_date() {

        return trans_date;
    }

    public void setTrans_date(Date trans_date) {
        this.trans_date = trans_date;
    }

    public Date getResponse_date() {
        return response_date;
    }

    public void setResponse_date(Date response_date) {
        this.response_date = response_date;
    }

    public String getDest_account() {
        return dest_account;
    }

    public void setDest_account(String dest_account) {
        this.dest_account = dest_account;
    }

    @Override
    public String toString() {
        return "ReproVTU{" + "id=" + id + ", unique_transid=" + unique_transid + ", amount=" + amount + ", dest_account=" + dest_account + ", response_code=" + response_code + ", response_message=" + response_message + ", provider=" + provider + ", trans_date=" + trans_date + ", response_date=" + response_date + '}';
    }

}
