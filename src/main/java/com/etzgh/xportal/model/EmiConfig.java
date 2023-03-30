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
public class EmiConfig {

    private String ISSUER_ID;
    private String CHARGING_PARTY_ID;
    private String API_KEY;
    private String TRUSTSTORE_LOC;
    private String TRUSTSTORE_PASSWORD;
    private String KEYSTORE_LOC;
    private String KEYSTORE_PASSWORD;

    public String getISSUER_ID() {
        return ISSUER_ID;
    }

    public void setISSUER_ID(String ISSUER_ID) {
        this.ISSUER_ID = ISSUER_ID;
    }

    public String getCHARGING_PARTY_ID() {
        return CHARGING_PARTY_ID;
    }

    public void setCHARGING_PARTY_ID(String CHARGING_PARTY_ID) {
        this.CHARGING_PARTY_ID = CHARGING_PARTY_ID;
    }

    public String getAPI_KEY() {
        return API_KEY;
    }

    public void setAPI_KEY(String API_KEY) {
        this.API_KEY = API_KEY;
    }

    public String getTRUSTSTORE_LOC() {
        return TRUSTSTORE_LOC;
    }

    public void setTRUSTSTORE_LOC(String TRUSTSTORE_LOC) {
        this.TRUSTSTORE_LOC = TRUSTSTORE_LOC;
    }

    public String getTRUSTSTORE_PASSWORD() {
        return TRUSTSTORE_PASSWORD;
    }

    public void setTRUSTSTORE_PASSWORD(String TRUSTSTORE_PASSWORD) {
        this.TRUSTSTORE_PASSWORD = TRUSTSTORE_PASSWORD;
    }

    public String getKEYSTORE_LOC() {
        return KEYSTORE_LOC;
    }

    public void setKEYSTORE_LOC(String KEYSTORE_LOC) {
        this.KEYSTORE_LOC = KEYSTORE_LOC;
    }

    public String getKEYSTORE_PASSWORD() {
        return KEYSTORE_PASSWORD;
    }

    public void setKEYSTORE_PASSWORD(String KEYSTORE_PASSWORD) {
        this.KEYSTORE_PASSWORD = KEYSTORE_PASSWORD;
    }

    @Override
    public String toString() {
        return "EmiConfig{" + "CHARGING_PARTY_ID=" + CHARGING_PARTY_ID + ", API_KEY=" + API_KEY + ", TRUSTSTORE_LOC=" + TRUSTSTORE_LOC + ", TRUSTSTORE_PASSWORD=" + TRUSTSTORE_PASSWORD + ", KEYSTORE_LOC=" + KEYSTORE_LOC + ", KEYSTORE_PASSWORD=" + KEYSTORE_PASSWORD + '}';
    }

}
