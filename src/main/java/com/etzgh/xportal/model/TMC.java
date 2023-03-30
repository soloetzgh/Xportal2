/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author Eugene
 */
@Entity
@XmlRootElement
public class TMC implements Serializable {

    @Id
    private String reference;
    private String mti;
    private String pro_code;
    private String pan;
    private BigDecimal amount;
    private String track2;
    private String response_code;
    private String terminal_id;
    private String stan;
    private String card_acc_name;
    private BigDecimal fee;
    private String trans_data;
    private String src_node;
    private String target_node;
    private String transtype;
    private String response;
    private String source_account;
    private String destination_account;
    private double elevy;
    @Column(name = "response_time", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date response_time;
    @Column(name = "trans_date", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date trans_date;

    private String original_respcode;
    private String updated_respcode;
    private String initiated_by;
    private String initiated_date;
    private String authorised_by;
    private String authorised_date;

    public String getMti() {
        return mti;
    }

    public void setMti(String mti) {
        this.mti = mti;
    }

    public Date getTrans_date() {
        return trans_date;
    }

    public void setTrans_date(Date trans_date) {
        this.trans_date = trans_date;
    }

    public String getPro_code() {
        return pro_code;
    }

    public void setPro_code(String pro_code) {
        this.pro_code = pro_code;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTrack2() {
        return track2;
    }

    public void setTrack2(String track2) {
        this.track2 = track2;
    }

    public String getResponse_code() {
        return response_code;
    }

    public void setResponse_code(String response_code) {
        this.response_code = response_code;
    }

    public String getTerminal_id() {
        return terminal_id;
    }

    public void setTerminal_id(String terminal_id) {
        this.terminal_id = terminal_id;
    }

    public String getStan() {
        return stan;
    }

    public void setStan(String stan) {
        this.stan = stan;
    }

    public String getCard_acc_name() {
        return card_acc_name;
    }

    public void setCard_acc_name(String card_acc_name) {
        this.card_acc_name = card_acc_name;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getSrc_node() {
        return src_node;
    }

    public void setSrc_node(String src_node) {
        this.src_node = src_node;
    }

    public String getTarget_node() {
        return target_node;
    }

    public void setTarget_node(String target_node) {
        this.target_node = target_node;
    }

    public double getElevy() {
        return elevy;
    }

    public void setElevy(double elevy) {
        this.elevy = elevy;
    }

    public Date getResponse_time() {
        return response_time;
    }

    public void setResponse_time(Date response_time) {
        this.response_time = response_time;
    }

    public String getTranstype() {
        return transtype;
    }

    public void setTranstype(String transtype) {
        this.transtype = transtype;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getTrans_data() {
        return trans_data;
    }

    public void setTrans_data(String trans_data) {
        this.trans_data = trans_data;
    }

    public String getSource_account() {
        return source_account;
    }

    public void setSource_account(String source_account) {
        this.source_account = source_account;
    }

    public String getDestination_account() {
        return destination_account;
    }

    public void setDestination_account(String destination_account) {
        this.destination_account = destination_account;
    }

    public String getOriginal_respcode() {
        return original_respcode;
    }

    public void setOriginal_respcode(String original_respcode) {
        this.original_respcode = original_respcode;
    }

    public String getUpdated_respcode() {
        return updated_respcode;
    }

    public void setUpdated_respcode(String updated_respcode) {
        this.updated_respcode = updated_respcode;
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

    public void setAuthorised_date(String authoresed_date) {
        this.authorised_date = authoresed_date;
    }

    @Override
    public String toString() {
        return "TMC{" + "reference=" + reference + ", mti=" + mti + ", pro_code=" + pro_code + ", pan=" + pan + ", amount=" + amount + ", track2=" + track2 + ", response_code=" + response_code + ", terminal_id=" + terminal_id + ", stan=" + stan + ", card_acc_name=" + card_acc_name + ", fee=" + fee + ", trans_data=" + trans_data + ", src_node=" + src_node + ", target_node=" + target_node + ", transtype=" + transtype + ", response=" + response + ", source_account=" + source_account + ", destination_account=" + destination_account + ", elevy=" + elevy + ", response_time=" + response_time + ", trans_date=" + trans_date + ", original_respcode=" + original_respcode + ", updated_respcode=" + updated_respcode + ", initiated_by=" + initiated_by + ", initiated_date=" + initiated_date + ", authorised_by=" + authorised_by + ", authorised_date=" + authorised_date + '}';
    }

}
