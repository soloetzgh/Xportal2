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
public class CorporatePay implements Serializable {

    @Id
    private String reference;
    private String companyName;
    private String beneficiaryName;
    private String beneficiaryBank;
    private String accountNumber;
    private BigDecimal amount;
    private String authorized_date;
    private String status;
    private String paymentId;
    private String initBank;
    private String description;
    private String batchId;
    private String remark;
    private String authorizer;
    private String converted_amount;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getBeneficiaryBank() {
        return beneficiaryBank;
    }

    public void setBeneficiaryBank(String beneficiaryBank) {
        this.beneficiaryBank = beneficiaryBank;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getAuthorized_date() {
        return authorized_date;
    }

    public void setAuthorized_date(String authorized_date) {
        this.authorized_date = authorized_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getInitBank() {
        return initBank;
    }

    public void setInitBank(String initBank) {
        this.initBank = initBank;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAuthorizer() {
        return authorizer;
    }

    public void setAuthorizer(String authorizer) {
        this.authorizer = authorizer;
    }

    public String getConverted_amount() {
        return converted_amount;
    }

    public void setConverted_amount(String converted_amount) {
        this.converted_amount = converted_amount;
    }

    @Override
    public String toString() {
        return "CorporatePay{" + "reference=" + reference + ", companyName=" + companyName + ", beneficiaryName=" + beneficiaryName + ", beneficiaryBank=" + beneficiaryBank + ", accountNumber=" + accountNumber + ", amount=" + amount + ", authorized_date=" + authorized_date + ", status=" + status + ", paymentId=" + paymentId + ", initBank=" + initBank + ", description=" + description + ", batchId=" + batchId + ", remark=" + remark + ", authorizer=" + authorizer + ", converted_amount=" + converted_amount + '}';
    }

}
