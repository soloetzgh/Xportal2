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
public class CardMasterReport implements Serializable {

    @Id
    @Column(name = "response_time", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date response_time;
    @Column(name = "trans_date", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date trans_date;

    private String reference;
    private String extReference;
    private String amount;
    private String vastype;
    private String destacct;
    private String description;
    private String transType;
    private String transNumber;
    private String debitStatus;
    private String debitMessage;
    private String valueStatus;
    private String valueMessage;
    private String transactionStatus;
    private String isReversed;
    private String createdAt;
    private String updatedAt;

    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String idNumber;
    private String can;
    private String onboardStatus;
    private String response;
    private String created_at;
    private String updated_at;

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

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getExtReference() {
        return extReference;
    }

    public void setExtReference(String extReference) {
        this.extReference = extReference;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getVastype() {
        return vastype;
    }

    public void setVastype(String vastype) {
        this.vastype = vastype;
    }

    public String getDestacct() {
        return destacct;
    }

    public void setDestacct(String destacct) {
        this.destacct = destacct;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getTransNumber() {
        return transNumber;
    }

    public void setTransNumber(String transNumber) {
        this.transNumber = transNumber;
    }

    public String getDebitStatus() {
        return debitStatus;
    }

    public void setDebitStatus(String debitStatus) {
        this.debitStatus = debitStatus;
    }

    public String getDebitMessage() {
        return debitMessage;
    }

    public void setDebitMessage(String debitMessage) {
        this.debitMessage = debitMessage;
    }

    public String getValueStatus() {
        return valueStatus;
    }

    public void setValueStatus(String valueStatus) {
        this.valueStatus = valueStatus;
    }

    public String getValueMessage() {
        return valueMessage;
    }

    public void setValueMessage(String valueMessage) {
        this.valueMessage = valueMessage;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getIsReversed() {
        return isReversed;
    }

    public void setIsReversed(String isReversed) {
        this.isReversed = isReversed;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getCan() {
        return can;
    }

    public void setCan(String can) {
        this.can = can;
    }

    public String getOnboardStatus() {
        return onboardStatus;
    }

    public void setOnboardStatus(String onboardStatus) {
        this.onboardStatus = onboardStatus;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }


    @Override
    public String toString() {
        return "CardMasterReport{" + "response_time=" + response_time + ", trans_date=" + trans_date + ", reference=" + reference + ", extReference=" + extReference + ", amount=" + amount + ", vastype=" + vastype + ", destacct=" + destacct + ", description=" + description + ", transType=" + transType + ", transNumber=" + transNumber + ", debitStatus=" + debitStatus + ", debitMessage=" + debitMessage + ", valueStatus=" + valueStatus + ", valueMessage=" + valueMessage + ", transactionStatus=" + transactionStatus + ", isReversed=" + isReversed + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + ", phone=" + phone + ", idNumber=" + idNumber + ", can=" + can + ", onboardStatus=" + onboardStatus + ", response=" + response + ", created_at=" + created_at + ", updated_at=" + updated_at + '}';
    }
    
    

}
