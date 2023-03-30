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
 * @author sunkwa-arthur
 */
@Entity
@XmlRootElement
public class TopupSale implements Serializable {

    @Id
    private String merchant;
    private String shortcode;
    private String provider;
    private BigDecimal totaltrans;
    private BigDecimal totalamount;
    private BigDecimal vat;
    private BigDecimal discount;
    private BigDecimal disvat;
    private BigDecimal etzcom;
    private BigDecimal gcbcom;
    private BigDecimal vataddetzcom;

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public BigDecimal getTotaltrans() {
        return totaltrans;
    }

    public void setTotaltrans(BigDecimal totaltrans) {
        this.totaltrans = totaltrans;
    }

    public BigDecimal getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(BigDecimal totalamount) {
        this.totalamount = totalamount;
    }

    public BigDecimal getVat() {
        return vat;
    }

    public void setVat(BigDecimal vat) {
        this.vat = vat;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getDisvat() {
        return disvat;
    }

    public void setDisvat(BigDecimal disvat) {
        this.disvat = disvat;
    }

    public BigDecimal getEtzcom() {
        return etzcom;
    }

    public void setEtzcom(BigDecimal etzcom) {
        this.etzcom = etzcom;
    }

    public BigDecimal getGcbcom() {
        return gcbcom;
    }

    public void setGcbcom(BigDecimal gcbcom) {
        this.gcbcom = gcbcom;
    }

    public BigDecimal getVataddetzcom() {
        return vataddetzcom;
    }

    public void setVataddetzcom(BigDecimal vataddetzcom) {
        this.vataddetzcom = vataddetzcom;
    }

    public String getShortcode() {
        return shortcode;
    }

    public void setShortcode(String shortcode) {
        this.shortcode = shortcode;
    }

    @Override
    public String toString() {
        return "TopupSale{" + "merchant=" + merchant + ", shortcode=" + shortcode + ", provider=" + provider + ", totaltrans=" + totaltrans + ", totalamount=" + totalamount + ", vat=" + vat + ", discount=" + discount + ", disvat=" + disvat + ", etzcom=" + etzcom + ", gcbcom=" + gcbcom + ", vataddetzcom=" + vataddetzcom + '}';
    }

}
