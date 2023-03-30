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
public class FundGateMomoTransaction implements Serializable {

    @Id
    private String etz_ref;
    private String client_ref;
    private String mno;
    private String beneficiary;
    private BigDecimal amount;
    private String response;
    private String response_narration;
    private String date;
    private String terminal;
    private String clientid;

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getEtz_ref() {
        return etz_ref;
    }

    public void setEtz_ref(String etz_ref) {
        this.etz_ref = etz_ref;
    }

    public String getClient_ref() {
        return client_ref;
    }

    public void setClient_ref(String client_ref) {
        this.client_ref = client_ref;
    }

    public String getMno() {
        return mno;
    }

    public void setMno(String mno) {
        this.mno = mno;
    }

    public String getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(String beneficiary) {
        this.beneficiary = beneficiary;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getResponse_narration() {
        return response_narration;
    }

    public void setResponse_narration(String response_narration) {
        this.response_narration = response_narration;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
