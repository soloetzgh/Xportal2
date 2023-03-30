/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yaw.owusu-koranteng
 */
@Entity
@XmlRootElement
public class FundgateResponse implements Serializable {
    
      @Id
    private String respId;
    private String action;
    private String terminal;
    private String etzRef;
    private String respMessage;
    private String clientRef;
    private String created;
    private String respCode;

    public String getRespId() {
        return respId;
    }

    public void setRespId(String respId) {
        this.respId = respId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

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

    public String getRespMessage() {
        return respMessage;
    }

    public void setRespMessage(String respMessage) {
        this.respMessage = respMessage;
    }

    public String getClientRef() {
        return clientRef;
    }

    public void setClientRef(String clientRef) {
        this.clientRef = clientRef;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    @Override
    public String toString() {
        return "FundgateResponse{" + "respId=" + respId + ", action=" + action + ", terminal=" + terminal + ", etzRef=" + etzRef + ", respMessage=" + respMessage + ", clientRef=" + clientRef + ", created=" + created + ", respCode=" + respCode + '}';
    }
 
    
    
    
}
