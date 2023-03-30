package com.etzgh.xportal.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author seth.sebeh
 */
@Entity
@XmlRootElement
public class E_Reprocess implements Serializable {

    private static final long serialVersionUID = 7888650738041150250L;

    @Id
    private String reference;
    private String account;
    private BigDecimal amount;
    private String product;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
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

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "E_Reprocess{" + "reference=" + reference + ", account=" + account + ", amount=" + amount + ", product=" + product + '}';
    }
}
