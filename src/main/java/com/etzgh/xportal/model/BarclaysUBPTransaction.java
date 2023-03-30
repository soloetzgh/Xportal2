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
public class BarclaysUBPTransaction implements Serializable {

    private static final long serialVersionUID = -6690856357140253763L;
    @Id
    private String extTransid;
    private String uniqueTransid;
    private BigDecimal amount;
    private String utilityCode;
    private String utilityRef;
    private String resultCode;
    private String result;
    private String message;
    private String transDate;
    private String isReversal;

    public String getIsReversal() {
        return isReversal;
    }

    public void setIsReversal(String isReversal) {
        this.isReversal = isReversal;
    }

    public String getExTransid() {
        return extTransid;
    }

    public void setExTransid(String extTransid) {
        this.extTransid = extTransid;
    }

    public String getUniqueTransid() {
        return uniqueTransid;
    }

    public void setUniqueTransid(String uniqueTransid) {
        this.uniqueTransid = uniqueTransid;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal transAmount) {
        this.amount = transAmount;
    }

    public String getUtilityCode() {
        return utilityCode;
    }

    public void setUtilityCode(String utilityCode) {
        this.utilityCode = utilityCode;
    }

    public String getUtilityRef() {
        return utilityRef;
    }

    public void setUtilityRef(String utilityRef) {
        this.utilityRef = utilityRef;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

}
