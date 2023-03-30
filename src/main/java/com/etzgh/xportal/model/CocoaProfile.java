/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sunkwa-arthur
 */
@XmlRootElement
@Entity
public class CocoaProfile implements Serializable {

    @Id
    private String idNo;
    private String names;
    private String msisdn;
    private String category;
    private String primarySuperior;
    private String secondarySuperior;
    private String districtName;
    private String societyName;
    private String region;
    private String sex;
    private double onlineBalance;
    private double maxCreditLimit;
    private double maxWithdrawalLimit;
    private String activeStatus;
    private String created;

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrimarySuperior() {
        return primarySuperior;
    }

    public void setPrimarySuperior(String primarySuperior) {
        this.primarySuperior = primarySuperior;
    }

    public String getSecondarySuperior() {
        return secondarySuperior;
    }

    public void setSecondarySuperior(String secondarySuperior) {
        this.secondarySuperior = secondarySuperior;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getSocietyName() {
        return societyName;
    }

    public void setSocietyName(String societyName) {
        this.societyName = societyName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public double getOnlineBalance() {
        return onlineBalance;
    }

    public void setOnlineBalance(double onlineBalance) {
        this.onlineBalance = onlineBalance;
    }

    public double getMaxCreditLimit() {
        return maxCreditLimit;
    }

    public void setMaxCreditLimit(double maxCreditLimit) {
        this.maxCreditLimit = maxCreditLimit;
    }

    public double getMaxWithdrawalLimit() {
        return maxWithdrawalLimit;
    }

    public void setMaxWithdrawalLimit(double maxWithdrawalLimit) {
        this.maxWithdrawalLimit = maxWithdrawalLimit;
    }

    public String getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

}
