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
public class CardAccounts {

    private String card_number;
    private String pin;
    private String card_pin;
    private String x_offset;

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getCard_pin() {
        return card_pin;
    }

    public void setCard_pin(String card_pin) {
        this.card_pin = card_pin;
    }

    public String getX_offset() {
        return x_offset;
    }

    public void setX_offset(String x_offset) {
        this.x_offset = x_offset;
    }

    @Override
    public String toString() {
        return "CardAccounts{" + "card_number=" + card_number + ", pin=" + pin + ", card_pin=" + card_pin + ", x_offset=" + x_offset + '}';
    }

}
