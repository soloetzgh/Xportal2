/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yaw.owusu-koranteng
 */

@Entity
@XmlRootElement
public class FundsTransfer implements Serializable{
    
        @Id
    @Column(name = "response_time", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date response_time;
    @Column(name = "trans_date", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date trans_date;

    private String source_card;
    private String amount;
    private String destination_bank;
    private String destination_account;
    private String narration;
    private String transaction_reference;
    private String status;
    private String initiated_by;
    private String initiated_date;
    private String authorized_by;
    private String authorized_date;
    private String response_code;
    private String response_message;
    private String network;
    private String bank;

    public Date getResponse_time() {
        return response_time;
    }

    public void setResponse_time(Date response_time) {
        this.response_time = response_time;
    }

    public Date getTrans_date() {
        return trans_date;
    }

    public void setTrans_date(Date trans_date) {
        this.trans_date = trans_date;
    }

    public String getSource_card() {
        return source_card;
    }

    public void setSource_card(String source_card) {
        this.source_card = source_card;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDestination_bank() {
        return destination_bank;
    }

    public void setDestination_bank(String destination_bank) {
        this.destination_bank = destination_bank;
    }

    public String getDestination_account() {
        return destination_account;
    }

    public void setDestination_account(String destination_account) {
        this.destination_account = destination_account;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getTransaction_reference() {
        return transaction_reference;
    }

    public void setTransaction_reference(String transaction_reference) {
        this.transaction_reference = transaction_reference;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInitiated_by() {
        return initiated_by;
    }

    public void setInitiated_by(String initiated_by) {
        this.initiated_by = initiated_by;
    }

    public String getInitiated_date() {
        return initiated_date;
    }

    public void setInitiated_date(String initiated_date) {
        this.initiated_date = initiated_date;
    }

    public String getAuthorized_by() {
        return authorized_by;
    }

    public void setAuthorized_by(String authorized_by) {
        this.authorized_by = authorized_by;
    }

    public String getAuthorized_date() {
        return authorized_date;
    }

    public void setAuthorized_date(String authorized_date) {
        this.authorized_date = authorized_date;
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

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    @Override
    public String toString() {
        return "FundsTransfer{" + "response_time=" + response_time + ", trans_date=" + trans_date + ", source_card=" + source_card + ", amount=" + amount + ", destination_bank=" + destination_bank + ", destination_account=" + destination_account + ", narration=" + narration + ", transaction_reference=" + transaction_reference + ", status=" + status + ", initiated_by=" + initiated_by + ", initiated_date=" + initiated_date + ", authorized_by=" + authorized_by + ", authorized_date=" + authorized_date + ", response_code=" + response_code + ", response_message=" + response_message + ", network=" + network + ", bank=" + bank + '}';
    }
    
        
}
