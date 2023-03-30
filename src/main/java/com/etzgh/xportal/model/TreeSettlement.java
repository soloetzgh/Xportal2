package com.etzgh.xportal.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class TreeSettlement implements Serializable {

    private static final long serialVersionUID = 3593910264901075687L;

    @Id
    private String unique_transid;
    private String phone_number;
    private String mmda;
    private BigDecimal settle_amount;
    private String trans_date;
    private String account_number;
    private String settlement_ref;
    private String status;
    private int attempts;
    private String settlement_date;

    public String getUnique_transid() {
        return unique_transid;
    }

    public void setUnique_transid(String unique_transid) {
        this.unique_transid = unique_transid;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getMmda() {
        return mmda;
    }

    public void setMmda(String mmda) {
        this.mmda = mmda;
    }

    public BigDecimal getSettle_amount() {
        return settle_amount;
    }

    public void setSettle_amount(BigDecimal settle_amount) {
        this.settle_amount = settle_amount;
    }

    public String getTrans_date() {
        return trans_date;
    }

    public void setTrans_date(String trans_date) {
        this.trans_date = trans_date;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
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

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public String getSettlment_date() {
        return settlement_date;
    }

    public void setSettlment_date(String settlement_date) {
        this.settlement_date = settlement_date;
    }

    @Override
    public String toString() {
        return "TreeSettlement{" + "unique_transid=" + unique_transid + ", phone_number=" + phone_number + ", mmda=" + mmda + ", settle_amount=" + settle_amount + ", trans_date=" + trans_date + ", account_number=" + account_number + ", settlement_ref=" + settlement_ref + ", status=" + status + ", attempts=" + attempts + ", settlement_date=" + settlement_date + '}';
    }
}
