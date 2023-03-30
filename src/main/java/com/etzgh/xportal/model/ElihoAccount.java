package com.etzgh.xportal.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class ElihoAccount implements Serializable {

    private static final long serialVersionUID = -6629748083141412912L;

    @Id
    private String idNo;
    private String category;
    private String msisdn;
    private String prySuperId;
    private String distrCode;
    private String distrName;
    private String societyName;
    private String region;
    private String names;
    private String activeStatus;
    private String created;
    private BigDecimal onlineBalance;

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getPrySuperId() {
        return prySuperId;
    }

    public void setPrySuperId(String prySuperId) {
        this.prySuperId = prySuperId;
    }

    public String getDistrCode() {
        return distrCode;
    }

    public void setDistrCode(String distrCode) {
        this.distrCode = distrCode;
    }

    public String getDistrName() {
        return distrName;
    }

    public void setDistrName(String distrName) {
        this.distrName = distrName;
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

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
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

    public BigDecimal getOnlineBalance() {
        return onlineBalance;
    }

    public void setOnlineBalance(BigDecimal onlineBalance) {
        this.onlineBalance = onlineBalance;
    }

    @Override
    public String toString() {
        return "ElihoAccount{" + "idNo=" + idNo + ", category=" + category + ", msisdn=" + msisdn + ", prySuperId=" + prySuperId + ", distrCode=" + distrCode + ", distrName=" + distrName + ", societyName=" + societyName + ", region=" + region + ", names=" + names + ", activeStatus=" + activeStatus + ", created=" + created + ", onlineBalance=" + onlineBalance + '}';
    }

}
