package com.etzgh.xportal.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Webconnect implements Serializable {

    @Id
    private String unique_transid;
    private String merchant_code;
    private String merchant_name;
    private String merchant_ref;
    private String switch_ref;
    private BigDecimal trans_amount;
    @Column(name = "trans_date", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date trans_date;
    private String trans_descr;
    private String response;
    private String debit_response;
    private String card_type;
    private String responsedescription;
    private double commission_amount;
    private String mobile_no;
    private String USERCARD_NO;
    private String EMAIL;
    private String CLIENT_ID;
    private String flag;
    private String client;

    public String getMerchant_code() {
        return merchant_code;
    }

    public void setMerchant_code(String merchant_code) {
        this.merchant_code = merchant_code;
    }

    public String getMerchant_ref() {
        return merchant_ref;
    }

    public void setMerchant_ref(String merchant_ref) {
        this.merchant_ref = merchant_ref;
    }

    public String getSwitch_ref() {
        return switch_ref;
    }

    public void setSwitch_ref(String switch_ref) {
        this.switch_ref = switch_ref;
    }

    public BigDecimal getTrans_amount() {
        return trans_amount;
    }

    public void setTrans_amount(BigDecimal trans_amount) {
        this.trans_amount = trans_amount;
    }

    public Date getTrans_date() {
        return trans_date;
    }

    public void setTrans_date(Date trans_date) {
        this.trans_date = trans_date;
    }

    public String getTrans_descr() {
        return trans_descr;
    }

    public void setTrans_descr(String trans_descr) {
        this.trans_descr = trans_descr;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getResponsedescription() {
        return responsedescription;
    }

    public void setResponsedescription(String responsedescription) {
        this.responsedescription = responsedescription;
    }

    public double getCommission_amount() {
        return commission_amount;
    }

    public void setCommission_amount(double commission_amount) {
        this.commission_amount = commission_amount;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getUnique_transid() {
        return unique_transid;
    }

    public void setUnique_transid(String unique_transid) {
        this.unique_transid = unique_transid;
    }

    public String getUSERCARD_NO() {
        return USERCARD_NO;
    }

    public void setUSERCARD_NO(String USERCARD_NO) {
        this.USERCARD_NO = USERCARD_NO;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public String getCLIENT_ID() {
        return CLIENT_ID;
    }

    public void setCLIENT_ID(String CLIENT_ID) {
        this.CLIENT_ID = CLIENT_ID;
    }

    public String getDebit_response() {
        return debit_response;
    }

    public void setDebit_response(String debit_response) {
        this.debit_response = debit_response;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

}
