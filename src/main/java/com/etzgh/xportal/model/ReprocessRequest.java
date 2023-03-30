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
public class ReprocessRequest implements Serializable {

    private static final long serialVersionUID = -2638637689954227247L;

    @Id
    private String reference;
    private String product;
    private String account;
    private BigDecimal amount;
    private String bank_code;
    private String trans_type;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getBank_code() {
        return bank_code;
    }

    public void setBank_code(String bank_code) {
        this.bank_code = bank_code;
    }

    public String getTrans_type() {
        return trans_type;
    }

    public void setTrans_type(String trans_type) {
        this.trans_type = trans_type;
    }

    @Override
    public String toString() {
        return "ReprocessRequest{" + "reference=" + reference + ", product=" + product + ", account=" + account + ", amount=" + amount + ", bank_code=" + bank_code + ", trans_type=" + trans_type + '}';
    }
}
