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
@XmlRootElement
@Entity
public class Terra_Refund implements Serializable {

    private static final long serialVersionUID = 6539138147645871505L;

    @Id
    private int id;
    private String terra_id;
    private String etz_id;
    private String reversal_reason;
    private String sender_name;
    private BigDecimal amount;
    private BigDecimal amount_reversed;
    private String correct_phone;
    private int partial_reversal;
    private int refund_status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTerra_id() {
        return terra_id;
    }

    public void setTerra_id(String terra_id) {
        this.terra_id = terra_id;
    }

    public String getEtz_id() {
        return etz_id;
    }

    public void setEtz_id(String etz_id) {
        this.etz_id = etz_id;
    }

    public String getReversal_reason() {
        return reversal_reason;
    }

    public void setReversal_reason(String reversal_reason) {
        this.reversal_reason = reversal_reason;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount_reversed() {
        return amount_reversed;
    }

    public void setAmount_reversed(BigDecimal amount_reversed) {
        this.amount_reversed = amount_reversed;
    }

    public String getCorrect_phone() {
        return correct_phone;
    }

    public void setCorrect_phone(String correct_phone) {
        this.correct_phone = correct_phone;
    }

    public int getPartial_reversal() {
        return partial_reversal;
    }

    public void setPartial_reversal(int partial_reversal) {
        this.partial_reversal = partial_reversal;
    }

    public int getRefund_status() {
        return refund_status;
    }

    public void setRefund_status(int refund_status) {
        this.refund_status = refund_status;
    }

    @Override
    public String toString() {
        return "Terra_Refund{" + "id=" + id + ", terra_id=" + terra_id + ", etz_id=" + etz_id + ", reversal_reason=" + reversal_reason + ", sender_name=" + sender_name + ", amount=" + amount + ", amount_reversed=" + amount_reversed + ", correct_phone=" + correct_phone + ", partial_reversal=" + partial_reversal + ", refund_status=" + refund_status + '}';
    }

}
