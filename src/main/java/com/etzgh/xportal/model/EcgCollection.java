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
 * @author Eugene
 */
@Entity
@XmlRootElement
public class EcgCollection implements Serializable {

    private static final long serialVersionUID = 8487448382375208862L;

    @Id
    private String trans_id;
    private String dest_account;
    private String dest_name;
    private String trans_channel;
    private BigDecimal trans_amount;
    private String currency;
    private String trans_status;
    private String trans_descr;
    private String trans_date;

    public String getTrans_id() {
        return trans_id;
    }

    public void setTrans_id(String trans_id) {
        this.trans_id = trans_id;
    }

    public String getDest_account() {
        return dest_account;
    }

    public void setDest_account(String dest_account) {
        this.dest_account = dest_account;
    }

    public String getDest_name() {
        return dest_name;
    }

    public void setDest_name(String dest_name) {
        this.dest_name = dest_name;
    }

    public String getTrans_channel() {
        return trans_channel;
    }

    public void setTrans_channel(String trans_channel) {
        this.trans_channel = trans_channel;
    }

    public BigDecimal getTrans_amount() {
        return trans_amount;
    }

    public void setTrans_amount(BigDecimal trans_amount) {
        this.trans_amount = trans_amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTrans_status() {
        return trans_status;
    }

    public void setTrans_status(String trans_status) {
        this.trans_status = trans_status;
    }

    public String getTrans_descr() {
        return trans_descr;
    }

    public void setTrans_descr(String trans_descr) {
        this.trans_descr = trans_descr;
    }

    public String getTrans_date() {
        return trans_date;
    }

    public void setTrans_date(String trans_date) {
        this.trans_date = trans_date;
    }

    @Override
    public String toString() {
        return "EcgCollection{" + "trans_id=" + trans_id + ", dest_account=" + dest_account + ", dest_name=" + dest_name + ", trans_channel=" + trans_channel + ", trans_amount=" + trans_amount + ", currency=" + currency + ", trans_status=" + trans_status + ", trans_descr=" + trans_descr + ", trans_date=" + trans_date + '}';
    }
}
