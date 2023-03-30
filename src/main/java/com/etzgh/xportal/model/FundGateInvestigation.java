package com.etzgh.xportal.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Eugene
 */
@Entity
@XmlRootElement
public class FundGateInvestigation implements Serializable {

    @Id
    private String etzRef;
    private String destination;
    private String clientRef;
    private BigDecimal amount;

    private String created2;
    private String respMessage;
    private String lineType;
    private String action;

    public String getEtzRef() {
        return etzRef;
    }

    public void setEtzRef(String etzRef) {
        this.etzRef = etzRef;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getClientRef() {
        return clientRef;
    }

    public void setClientRef(String clientRef) {
        this.clientRef = clientRef;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getRespMessage() {
        return respMessage;
    }

    public void setRespMessage(String respMessage) {
        this.respMessage = respMessage;
    }

    public String getLineType() {
        return lineType;
    }

    public void setLineType(String lineType) {
        this.lineType = lineType;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getCreated2() {
        return created2;
    }

    public void setCreated2(String created2) {
        this.created2 = created2;
    }

    @Override
    public String toString() {
        return "FundGateInvestigation{" + "etzRef=" + etzRef + ", destination=" + destination + ", clientRef=" + clientRef + ", amount=" + amount + ", created2=" + created2 + ", respMessage=" + respMessage + ", lineType=" + lineType + ", action=" + action + '}';
    }

}
