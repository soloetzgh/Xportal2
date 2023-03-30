package com.etzgh.xportal.model;

import java.io.Serializable;

public class MobileMoneyUpdate implements Serializable {

    private String reference;
    private String original_clientid;
    private String updated_clientid;
    private String original_respcode;
    private String updated_respcode;
    private String original_narration;
    private String updated_narration;
    private String update_by;
    private String update_date;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getOriginal_clientid() {
        return original_clientid;
    }

    public void setOriginal_clientid(String original_clientid) {
        this.original_clientid = original_clientid;
    }

    public String getUpdated_clientid() {
        return updated_clientid;
    }

    public void setUpdated_clientid(String updated_clientid) {
        this.updated_clientid = updated_clientid;
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

    public String getOriginal_narration() {
        return original_narration;
    }

    public void setOriginal_narration(String original_narration) {
        this.original_narration = original_narration;
    }

    public String getUpdated_narration() {
        return updated_narration;
    }

    public void setUpdated_narration(String updated_narration) {
        this.updated_narration = updated_narration;
    }

    public String getUpdate_by() {
        return update_by;
    }

    public void setUpdate_by(String update_by) {
        this.update_by = update_by;
    }

    public String getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }

    @Override
    public String toString() {
        return "MobileMoneyUpdate{" + "reference=" + reference + ", original_clientid=" + original_clientid + ", updated_clientid=" + updated_clientid + ", original_respcode=" + original_respcode + ", updated_respcode=" + updated_respcode + ", original_narration=" + original_narration + ", updated_narration=" + updated_narration + ", update_by=" + update_by + ", update_date=" + update_date + '}';
    }

}
