/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.model;

/**
 *
 * @author sunkwa-arthur
 */
public class MobileAppUserLog {

    private String customerId;
    private String mobileNo;
    private String status;
    private String action;
    private String bank;
    private String appId;
    private String bankCode;
    private String initiatedDate;
    private String initiatedBy;
    private String authorizedDate;
    private String authorizedBy;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getInitiatedDate() {
        return initiatedDate;
    }

    public void setInitiatedDate(String initiatedDate) {
        this.initiatedDate = initiatedDate;
    }

    public String getInitiatedBy() {
        return initiatedBy;
    }

    public void setInitiatedBy(String initiatedBy) {
        this.initiatedBy = initiatedBy;
    }

    public String getAuthorizedDate() {
        return authorizedDate;
    }

    public void setAuthorizedDate(String authorizedDate) {
        this.authorizedDate = authorizedDate;
    }

    public String getAuthorizedBy() {
        return authorizedBy;
    }

    public void setAuthorizedBy(String authorizedBy) {
        this.authorizedBy = authorizedBy;
    }

    @Override
    public String toString() {
        return "MobileAppUserLog{" + "customerId=" + customerId + ", mobileNo=" + mobileNo + ", status=" + status + ", action=" + action + ", bank=" + bank + ", bankCode=" + bankCode + ", initiatedDate=" + initiatedDate + ", initiatedBy=" + initiatedBy + ", authorizedDate=" + authorizedDate + ", authorizedBy=" + authorizedBy + '}';
    }

}
