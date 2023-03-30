package com.etzgh.xportal.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author sunkwa-arthur
 */
@Entity
@XmlRootElement
public class FundGateMerchant implements Serializable {

    @Id
    private String terminal_id;
    private String merchant_name;
    private String card_num;

    public String getTerminal_id() {
        return terminal_id;
    }

    public void setTerminal_id(String terminal_id) {
        this.terminal_id = terminal_id;
    }

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public String getCard_num() {
        return card_num;
    }

    public void setCard_num(String card_num) {
        this.card_num = card_num;
    }

    @Override
    public String toString() {
        return "FundGateMerchant{" + "terminal_id=" + terminal_id + ", merchant_name=" + merchant_name + ", card_num=" + card_num + '}';
    }

}
