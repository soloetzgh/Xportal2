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
public class Activity {

    private String activityReference;
    private double amount;
    private String transactionDate;
    private String targetIdNo;
    private String targetName;
    private String extraInfo;
    private String activity;
    private String status;
    private String statusMsg;
    private String initIdNo;
    private String initName;
    private String initDistrict;
    private String commentNote;

    public String getCommentNote() {
        return commentNote;
    }

    public void setCommentNote(String commentNote) {
        this.commentNote = commentNote;
    }

    public String getActivityReference() {
        return activityReference;
    }

    public void setActivityReference(String activityReference) {
        this.activityReference = activityReference;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTargetIdNo() {
        return targetIdNo;
    }

    public void setTargetIdNo(String targetIdNo) {
        this.targetIdNo = targetIdNo;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getInitIdNo() {
        return initIdNo;
    }

    public void setInitIdNo(String initIdNo) {
        this.initIdNo = initIdNo;
    }

    public String getInitName() {
        return initName;
    }

    public void setInitName(String initName) {
        this.initName = initName;
    }

    public String getInitDistrict() {
        return initDistrict;
    }

    public void setInitDistrict(String initDistrict) {
        this.initDistrict = initDistrict;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    @Override
    public String toString() {
        return "Activity{" + "activityReference=" + activityReference + ", amount=" + amount + ", transactionDate=" + transactionDate + ", targetIdNo=" + targetIdNo + ", targetName=" + targetName + ", extraInfo=" + extraInfo + ", activity=" + activity + ", status=" + status + ", statusMsg=" + statusMsg + ", initIdNo=" + initIdNo + ", initName=" + initName + ", initDistrict=" + initDistrict + ", commentNote=" + commentNote + '}';
    }

}
