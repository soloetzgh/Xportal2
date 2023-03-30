/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.model;

/**
 *
 * @author sunkwa-arthur
 */
public class MomoUpdate {

    private String reference;
    private String clientid;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    @Override
    public String toString() {
        return "MomoUpdate{" + "reference=" + reference + ", clientid=" + clientid + '}';
    }

}
