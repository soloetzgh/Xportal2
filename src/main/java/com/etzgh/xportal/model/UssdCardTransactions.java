/*
 * To change this license header;private String  choose License Headers in Project Properties.
 * To change this template file;private String  choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sunkwa-arthur
 */
@Entity
@XmlRootElement
public class UssdCardTransactions implements Serializable {

    @Id
    private String reference;
    private String msisdn;
    private String trans_type;
    private String destination;
    private String vas_type;
    private double amount;
    private String status_code;
    private String status_msg;
    private String value_code;
    private String value_msg;
    private String sms_sent;
    private String created_date;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getTrans_type() {
        return trans_type;
    }

    public void setTrans_type(String trans_type) {
        this.trans_type = trans_type;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getVas_type() {
        return vas_type;
    }

    public void setVas_type(String vas_type) {
        this.vas_type = vas_type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public String getStatus_msg() {
        return status_msg;
    }

    public void setStatus_msg(String status_msg) {
        this.status_msg = status_msg;
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

    public String getSms_sent() {
        return sms_sent;
    }

    public void setSms_sent(String sms_sent) {
        this.sms_sent = sms_sent;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

}
