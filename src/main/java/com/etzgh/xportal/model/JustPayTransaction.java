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
public class JustPayTransaction implements Serializable {

    @Id
    private String reference;
    private String clientid;
    private String flag;
    private String provider;
    private String mmno;
    private String destacct;
    private BigDecimal amount;
    private String value_code;
    private String value_msg;
    private String created_date;
    private String callback_msg;
    private String mainoptions;
    private String vastype;
    private BigDecimal fee;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getMmno() {
        return mmno;
    }

    public void setMmno(String mmno) {
        this.mmno = mmno;
    }

    public String getDestacct() {
        return destacct;
    }

    public void setDestacct(String destacct) {
        this.destacct = destacct;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getValue_code() {
        return value_code;
    }

    public void setValue_code(String value_code) {
        this.value_code = value_code;
    }

    public String getValue_msg() {
        return value_msg;
    }

    public void setValue_msg(String value_msg) {
        this.value_msg = value_msg;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getCallback_msg() {
        return callback_msg;
    }

    public void setCallback_msg(String callback_msg) {
        this.callback_msg = callback_msg;
    }

    public String getMainoptions() {
        return mainoptions;
    }

    public void setMainoptions(String mainoptions) {
        this.mainoptions = mainoptions;
    }

    @Override
    public String toString() {
        return "JustPayTransaction{" + "reference=" + reference + ", clientid=" + clientid + ", flag=" + flag + ", provider=" + provider + ", mmno=" + mmno + ", destacct=" + destacct + ", amount=" + amount + ", value_code=" + value_code + ", value_msg=" + value_msg + ", created_date=" + created_date + ", callback_msg=" + callback_msg + ", mainoptions=" + mainoptions + ", vastype=" + vastype + '}';
    }

    public String getVastype() {
        return vastype;
    }

    public void setVastype(String vastype) {
        this.vastype = vastype;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }
}
