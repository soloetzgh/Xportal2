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
public class SugarWallet {

    private String session;
    private String customer_ref;
    private String account_id;
    private String amount;
    private String fee;
    private String currency;
    private String transaction_type;
    private String impact_type;
    private String transaction_status;
    private String narration;
    private String target_account_id;
    private String service_note;
    private String echo_data;
    private String reward_points;
    private String reward_value;
    private String reward_apply_status;
    private String transaction_date;

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getCustomer_ref() {
        return customer_ref;
    }

    public void setCustomer_ref(String customer_ref) {
        this.customer_ref = customer_ref;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public String getImpact_type() {
        return impact_type;
    }

    public void setImpact_type(String impact_type) {
        this.impact_type = impact_type;
    }

    public String getTransaction_status() {
        return transaction_status;
    }

    public void setTransaction_status(String transaction_status) {
        this.transaction_status = transaction_status;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getTarget_account_id() {
        return target_account_id;
    }

    public void setTarget_account_id(String target_account_id) {
        this.target_account_id = target_account_id;
    }

    public String getService_note() {
        return service_note;
    }

    public void setService_note(String service_note) {
        this.service_note = service_note;
    }

    public String getEcho_data() {
        return echo_data;
    }

    public void setEcho_data(String echo_data) {
        this.echo_data = echo_data;
    }

    public String getReward_points() {
        return reward_points;
    }

    public void setReward_points(String reward_points) {
        this.reward_points = reward_points;
    }

    public String getReward_value() {
        return reward_value;
    }

    public void setReward_value(String reward_value) {
        this.reward_value = reward_value;
    }

    public String getReward_apply_status() {
        return reward_apply_status;
    }

    public void setReward_apply_status(String reward_apply_status) {
        this.reward_apply_status = reward_apply_status;
    }

    public String getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(String transaction_date) {
        this.transaction_date = transaction_date;
    }

    @Override
    public String toString() {
        return "SugarWallet{" + "session=" + session + ", customer_ref=" + customer_ref + ", account_id=" + account_id + ", amount=" + amount + ", fee=" + fee + ", currency=" + currency + ", transaction_type=" + transaction_type + ", impact_type=" + impact_type + ", transaction_status=" + transaction_status + ", narration=" + narration + ", target_account_id=" + target_account_id + ", service_note=" + service_note + ", echo_data=" + echo_data + ", reward_points=" + reward_points + ", reward_value=" + reward_value + ", reward_apply_status=" + reward_apply_status + ", transaction_date=" + transaction_date + '}';
    }

}
