package com.etzgh.xportal.model;

import java.io.Serializable;

/**
 *
 * @author Eugene
 */
public class AuditTrail implements Serializable {

    private String user;
    private String extra_info;
    private String action;
    private String user_id;
    private String comment;
    private String ip_address;
    private String date;

    public AuditTrail() {
        this.user = "";
        this.extra_info = "";
        this.action = "";
        this.user_id = null;
        this.comment = "";
        this.ip_address = null;
    }

    public AuditTrail(String user, String extra_info, String action, String user_id, String comment, String ip_address) {
        this.user = user;
        this.extra_info = extra_info;
        this.action = action;
        this.user_id = user_id;
        this.comment = comment;
        this.ip_address = ip_address;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getExtra_info() {
        return extra_info;
    }

    public void setExtra_info(String extra_info) {
        this.extra_info = extra_info;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "AuditTrail{" + "user=" + user + ", extra_info=" + extra_info + ", action=" + action + ", user_id=" + user_id + ", comment=" + comment + ", ip_address=" + ip_address + ", date=" + date + '}';
    }

}
