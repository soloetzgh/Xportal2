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
public class NlaLiquidationReport implements Serializable {

    @Id
    @Column(name = "response_time", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date response_time;
    @Column(name = "trans_date", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date trans_date;

    private String ticket_number;
    private String code;
    private String response;
    private String reference;
    private String amount_won;
    private String amount;
    private String fee;
    private String fg_error;
    private String fg_referrence;
    private String fg_message;
    private String created;
    private String bank_code;
    private String phone_number;
    private String status;
    private String transid;
    private String subscriber_id;
    private String unique_transid;
    private String response_date;
    private String newCustomerId;
    private String initiated_by;
    private String initiated_date;
    private String authorised_by;
    private String authorised_date;
    private String transactionReference;
    private String reversal_reference;

    public Date getTrans_date() {
        return trans_date;
    }

    public void setTrans_date(Date trans_date) {
        this.trans_date = trans_date;
    }

    public Date getResponse_time() {
        return response_time;
    }

    public void setResponse_time(Date response_time) {
        this.response_time = response_time;
    }

    public String getTicket_number() {
        return ticket_number;
    }

    public void setTicket_number(String ticket_number) {
        this.ticket_number = ticket_number;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getAmount_won() {
        return amount_won;
    }

    public void setAmount_won(String amount_won) {
        this.amount_won = amount_won;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getFg_error() {
        return fg_error;
    }

    public void setFg_error(String fg_error) {
        this.fg_error = fg_error;
    }

    public String getFg_referrence() {
        return fg_referrence;
    }

    public void setFg_referrence(String fg_referrence) {
        this.fg_referrence = fg_referrence;
    }

    public String getFg_message() {
        return fg_message;
    }

    public void setFg_message(String fg_message) {
        this.fg_message = fg_message;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getBank_code() {
        return bank_code;
    }

    public void setBank_code(String bank_code) {
        this.bank_code = bank_code;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransid() {
        return transid;
    }

    public void setTransid(String transid) {
        this.transid = transid;
    }

    public String getSubscriber_id() {
        return subscriber_id;
    }

    public void setSubscriber_id(String subscriber_id) {
        this.subscriber_id = subscriber_id;
    }

    public String getUnique_transid() {
        return unique_transid;
    }

    public void setUnique_transid(String unique_transid) {
        this.unique_transid = unique_transid;
    }

    public String getResponse_date() {
        return response_date;
    }

    public void setResponse_date(String response_date) {
        this.response_date = response_date;
    }

    public String getNewCustomerId() {
        return newCustomerId;
    }

    public void setNewCustomerId(String newCustomerId) {
        this.newCustomerId = newCustomerId;
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

    public String getAuthorised_by() {
        return authorised_by;
    }

    public void setAuthorised_by(String authorised_by) {
        this.authorised_by = authorised_by;
    }

    public String getAuthorised_date() {
        return authorised_date;
    }

    public void setAuthorised_date(String authorised_date) {
        this.authorised_date = authorised_date;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public String getReversal_reference() {
        return reversal_reference;
    }

    public void setReversal_reference(String reversal_reference) {
        this.reversal_reference = reversal_reference;
    }

    @Override
    public String toString() {
        return "NlaLiquidationReport{" + "response_time=" + response_time + ", trans_date=" + trans_date + ", ticket_number=" + ticket_number + ", code=" + code + ", response=" + response + ", reference=" + reference + ", amount_won=" + amount_won + ", amount=" + amount + ", fee=" + fee + ", fg_error=" + fg_error + ", fg_referrence=" + fg_referrence + ", fg_message=" + fg_message + ", created=" + created + ", bank_code=" + bank_code + ", phone_number=" + phone_number + ", status=" + status + ", transid=" + transid + ", subscriber_id=" + subscriber_id + ", unique_transid=" + unique_transid + ", response_date=" + response_date + ", newCustomerId=" + newCustomerId + ", initiated_by=" + initiated_by + ", initiated_date=" + initiated_date + ", authorised_by=" + authorised_by + ", authorised_date=" + authorised_date + ", transactionReference=" + transactionReference + ", reversal_reference=" + reversal_reference + '}';
    }

}
