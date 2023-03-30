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
 * @author Eugene
 */
@Entity
@XmlRootElement
public class CardNumber implements Serializable {

    @Id
    private String card_num;

    public String getCard_num() {
        return card_num;
    }

    public void setCard_num(String card_num) {
        this.card_num = card_num;
    }

    @Override
    public String toString() {
        return "CardNumber{" + "card_num=" + card_num + '}';
    }

}
