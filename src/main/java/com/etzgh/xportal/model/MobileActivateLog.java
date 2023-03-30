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
public class MobileActivateLog {

    private String mobile_number;
    private String app_id;
    private String app_name;
    private String account;
    private String account_id;
    private String initiated_by;
    private String initiated_date;
    private String action;
    private String authorized_by;
    private String authorized_date;
    private String account_change;
    private String reason;
    private String status;
    private String branch;

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getAccount_change() {
        return account_change;
    }

    public void setAccount_change(String account_change) {
        this.account_change = account_change;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "MobileActivateLog{" + "mobile_number=" + mobile_number + ", app_id=" + app_id + ", app_name=" + app_name + ", account=" + account + ", account_id=" + account_id + ", initiated_by=" + initiated_by + ", initiated_date=" + initiated_date + ", action=" + action + ", authorized_by=" + authorized_by + ", authorized_date=" + authorized_date + ", account_change=" + account_change + ", reason=" + reason + ", status=" + status + ", branch=" + branch + '}';
    }

}
