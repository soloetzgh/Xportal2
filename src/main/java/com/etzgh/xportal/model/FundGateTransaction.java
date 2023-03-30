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
public class FundGateTransaction implements Serializable {

    @Id
    private String etzRef;
    private String destination;
    private String clientRef;
    private BigDecimal amount;
    private String transId;
    private String created;
    private String respMessage;
    private String lineType;
    private String action;
    private String terminal;
    private String reversal;
    private String senderName;
    private String billerRespMessage;
    private String billerRespCode;
    private String billerTransId;
    private String respCode;
    private String description;
    private String beneficiaryName;

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

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

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getBillerTransId() {
        return billerTransId;
    }

    public void setBillerTransId(String billerTransId) {
        this.billerTransId = billerTransId;
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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getReversal() {
        return reversal;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public void setReversal(String reversal) {
        this.reversal = reversal;
    }

    public String getBillerRespMessage() {
        return billerRespMessage;
    }

    public void setBillerRespMessage(String billerRespMessage) {
        this.billerRespMessage = billerRespMessage;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBillerRespCode() {
        return billerRespCode;
    }

    public void setBillerRespCode(String billerRespCode) {
        this.billerRespCode = billerRespCode;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    @Override
    public String toString() {
        return "FundGateTransaction{" + "etzRef=" + etzRef + ", destination=" + destination + ", clientRef=" + clientRef + ", amount=" + amount + ", transId=" + transId + ", created=" + created + ", respMessage=" + respMessage + ", lineType=" + lineType + ", action=" + action + ", terminal=" + terminal + ", reversal=" + reversal + ", senderName=" + senderName + ", billerRespMessage=" + billerRespMessage + ", billerRespCode=" + billerRespCode + ", respCode=" + respCode + ", description=" + description + ", beneficiaryName=" + beneficiaryName + '}';
    }

}
