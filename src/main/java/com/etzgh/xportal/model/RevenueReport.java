/*
 * To change this license header ; private String choose License Headers in Project Properties.
 * To change this template file ; private String choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.model;

/**
 *
 * @author eugene
 */
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Eugene
 */
@Entity
@XmlRootElement
public class RevenueReport implements Serializable {

    @Id
    private String created;
    private String terminal;
    private String etzref;
    private BigDecimal amount;
    private String dest_account;
    private String trans_date;
    private String provider;
    private String unique_transid;
    private String alias;
    private String response_message;

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getEtzref() {
        return etzref;
    }

    public void setEtzref(String etzref) {
        this.etzref = etzref;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDest_account() {
        return dest_account;
    }

    public void setDest_account(String dest_account) {
        this.dest_account = dest_account;
    }

    public String getTrans_date() {
        return trans_date;
    }

    public void setTrans_date(String trans_date) {
        this.trans_date = trans_date;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getUnique_transid() {
        return unique_transid;
    }

    public void setUnique_transid(String unique_transid) {
        this.unique_transid = unique_transid;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getResponse_message() {
        return response_message;
    }

    public void setResponse_message(String response_message) {
        this.response_message = response_message;
    }

    @Override
    public String toString() {
        return "RevenueReport{" + "created=" + created + ", terminal=" + terminal + ", etzref=" + etzref + ", amount=" + amount + ", dest_account=" + dest_account + ", trans_date=" + trans_date + ", provider=" + provider + ", unique_transid=" + unique_transid + ", alias=" + alias + ", response_message=" + response_message + '}';
    }
}
