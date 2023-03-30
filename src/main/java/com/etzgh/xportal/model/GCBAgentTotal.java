/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sunkwa-arthur
 */
@Entity
@XmlRootElement
public class GCBAgentTotal implements Serializable {

    @Id
    private String totalTrans;
    private String totalAmount;

    public String getTotalTrans() {
        return totalTrans;
    }

    public void setTotalTrans(String totalTrans) {
        this.totalTrans = totalTrans;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "GCBAgentTotal{" + "totalTrans=" + totalTrans + ", totalAmount=" + totalAmount + '}';
    }
}
