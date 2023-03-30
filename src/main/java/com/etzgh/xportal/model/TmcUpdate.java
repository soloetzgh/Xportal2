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
 * @author yaw.owusu-koranteng
 */
@Entity
@XmlRootElement
public class TmcUpdate implements Serializable {

    @Id
    private String reference;
    private String original_respcode;
    private String updated_respcode;
    private String initiated_by;
    private String initiated_date;
    private String authorised_by;
    private String authorised_date;
    private String clientid;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getOriginal_respcode() {
        return original_respcode;
    }

    public void setOriginal_respcode(String original_respcode) {
        this.original_respcode = original_respcode;
    }

    public String getUpdated_respcode() {
        return updated_respcode;
    }

    public void setUpdated_respcode(String updated_respcode) {
        this.updated_respcode = updated_respcode;
    }

    public String getInitiated_by() {
        return initiated_by;
    }

    public void setInitiated_by(String initiated_by) {
        this.initiated_by = initiated_by;
    }

    public String getInitiated_date() {
        return initiated_date;
    }

    public void setInitiated_date(String initiated_date) {
        this.initiated_date = initiated_date;
    }

    public String getAuthorised_by() {
        return authorised_by;
    }

    public void setAuthorised_by(String authorised_by) {
        this.authorised_by = authorised_by;
    }

    public String getAuthorised_date() {
        return authorised_date;
    }

    public void setAuthorised_date(String authorised_date) {
        this.authorised_date = authorised_date;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    @Override
    public String toString() {
        return "TmcUpdate{" + "reference=" + reference + ", original_respcode=" + original_respcode + ", updated_respcode=" + updated_respcode + ", initiated_by=" + initiated_by + ", initiated_date=" + initiated_date + ", authorised_by=" + authorised_by + ", authorised_date=" + authorised_date + ", clientid=" + clientid + '}';
    }

}
