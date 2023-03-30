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
@XmlRootElement
@Entity
public class KYCswitch implements Serializable {

    private static final long serialVersionUID = 7771040595269727512L;
    @Id
    private String card_num;
    private String phone;
    private String firstname;
    private String lastname;
    private String online_balance;

    public String getCard_num() {
        return card_num;
    }

    public void setCard_num(String card_num) {
        this.card_num = card_num;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getOnline_balance() {
        return online_balance;
    }

    public void setOnline_balance(String online_balance) {
        this.online_balance = online_balance;
    }

    @Override
    public String toString() {
        return "KYCswitch{" + "card_num=" + card_num + ", phone=" + phone + ", firstname=" + firstname + ", lastname=" + lastname + ", online_balance=" + online_balance + '}';
    }

}
