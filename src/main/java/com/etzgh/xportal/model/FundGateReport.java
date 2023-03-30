package com.etzgh.xportal.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author sunkwa-arthur
 */
@Entity
@XmlRootElement
public class FundGateReport implements Serializable {

    @Id
    private String unique_transid;

    private BigDecimal trans_amount;
    private String trans_date;

    public String getUnique_transid() {
        return unique_transid;
    }

    public void setUnique_transid(String unique_transid) {
        this.unique_transid = unique_transid;
    }

    public BigDecimal getTrans_amount() {
        return trans_amount;
    }

    public void setTrans_amount(BigDecimal trans_amount) {
        this.trans_amount = trans_amount;
    }

    public String getTrans_date() {
        return trans_date;
    }

    public void setTrans_date(String trans_date) {
        this.trans_date = trans_date;
    }

    @Override
    public String toString() {
        return "FundGateReport{" + "unique_transid=" + unique_transid + ", trans_amount=" + trans_amount + ", trans_date=" + trans_date + '}';
    }

}
