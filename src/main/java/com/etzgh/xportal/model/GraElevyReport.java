/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
public class GraElevyReport implements Serializable{
    
    @Id
    @Column(name = "response_time", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date response_time;
    @Column(name = "trans_date", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date trans_date;
    
    private String statusCode;
    private String statusMessage;
    private String serverTimestamp;
    private String cursor;
    private List<String> transactions;

    private String transactionStatus;
    private String transactionStatusTimestamp;
    private String elevyID;
    private String clientTransactionID;
    private String serviceType;
    private String currency;
    private String transferAmount;
    private String taxableAmount;
    private String elevyAmount;
    private String elevyRefundAmount;

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

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getTransactionStatusTimestamp() {
        return transactionStatusTimestamp;
    }

    public void setTransactionStatusTimestamp(String transactionStatusTimestamp) {
        this.transactionStatusTimestamp = transactionStatusTimestamp;
    }

    public String getElevyID() {
        return elevyID;
    }

    public void setElevyID(String elevyID) {
        this.elevyID = elevyID;
    }

    public String getClientTransactionID() {
        return clientTransactionID;
    }

    public void setClientTransactionID(String clientTransactionID) {
        this.clientTransactionID = clientTransactionID;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(String transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getTaxableAmount() {
        return taxableAmount;
    }

    public void setTaxableAmount(String taxableAmount) {
        this.taxableAmount = taxableAmount;
    }

    public String getElevyAmount() {
        return elevyAmount;
    }

    public void setElevyAmount(String elevyAmount) {
        this.elevyAmount = elevyAmount;
    }

    public String getElevyRefundAmount() {
        return elevyRefundAmount;
    }

    public void setElevyRefundAmount(String elevyRefundAmount) {
        this.elevyRefundAmount = elevyRefundAmount;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getServerTimestamp() {
        return serverTimestamp;
    }

    public void setServerTimestamp(String serverTimestamp) {
        this.serverTimestamp = serverTimestamp;
    }

    public String getCursor() {
        return cursor;
    }

    public void setCursor(String cursor) {
        this.cursor = cursor;
    }

//    public String getTransactions() {
//        return transactions;
//    }
//
//    public void setTransactions(String transactions) {
//        this.transactions = transactions;
//    }

    public List<String> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<String> transactions) {
        this.transactions = transactions;
    }
    
//    @Override
//    public String toString() {
//        return "GraElevyReport{" + "response_time=" + response_time + ", trans_date=" + trans_date + ", statusCode=" + statusCode + ", statusMessage=" + statusMessage + ", serverTimestamp=" + serverTimestamp + ", cursor=" + cursor + ", transactions=" + transactions + ", transactionStatus=" + transactionStatus + ", transactionStatusTimestamp=" + transactionStatusTimestamp + ", elevyID=" + elevyID + ", clientTransactionID=" + clientTransactionID + ", serviceType=" + serviceType + ", currency=" + currency + ", transferAmount=" + transferAmount + ", taxableAmount=" + taxableAmount + ", elevyAmount=" + elevyAmount + ", elevyRefundAmount=" + elevyRefundAmount + '}';
//    }

    @Override
    public String toString() {
        return "GraElevyReport{" + "response_time=" + response_time + ", trans_date=" + trans_date + ", statusCode=" + statusCode + ", statusMessage=" + statusMessage + ", serverTimestamp=" + serverTimestamp + ", cursor=" + cursor + ", transactions=" + transactions + ", transactionStatus=" + transactionStatus + ", transactionStatusTimestamp=" + transactionStatusTimestamp + ", elevyID=" + elevyID + ", clientTransactionID=" + clientTransactionID + ", serviceType=" + serviceType + ", currency=" + currency + ", transferAmount=" + transferAmount + ", taxableAmount=" + taxableAmount + ", elevyAmount=" + elevyAmount + ", elevyRefundAmount=" + elevyRefundAmount + '}';
    }



    
}
