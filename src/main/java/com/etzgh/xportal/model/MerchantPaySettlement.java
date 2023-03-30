package com.etzgh.xportal.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class MerchantPaySettlement implements Serializable {

    private static final long serialVersionUID = 7694017992266580732L;

    @Id
    private String unique_transid;
    private String merchant_name;
    private String account_number;
    private BigDecimal settle_amount;
    private String settlement_ref;
    private String status;
    private String status_msg;
    private String attempts;
    private String date;

    public String getUnique_transid() {
        return unique_transid;
    }

    public void setUnique_transid(String unique_transid) {
        this.unique_transid = unique_transid;
    }

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public BigDecimal getSettle_amount() {
        return settle_amount;
    }

    public void setSettle_amount(BigDecimal settle_amount) {
        this.settle_amount = settle_amount;
    }

    public String getSettlement_ref() {
        return settlement_ref;
    }

    public void setSettlement_ref(String settlement_ref) {
        this.settlement_ref = settlement_ref;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_msg() {
        return status_msg;
    }

    public void setStatus_msg(String status_msg) {
        this.status_msg = status_msg;
    }

    public String getAttempts() {
        return attempts;
    }

    public void setAttempts(String attempts) {
        this.attempts = attempts;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "MerchantPaySettlement{" + "unique_transid=" + unique_transid + ", merchant_name=" + merchant_name + ", account_number=" + account_number + ", settle_amount=" + settle_amount + ", settlement_ref=" + settlement_ref + ", status=" + status + ", status_msg=" + status_msg + ", attempts=" + attempts + ", date=" + date + '}';
    }

}
