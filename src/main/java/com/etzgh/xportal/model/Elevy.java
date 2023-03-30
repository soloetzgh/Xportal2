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
public class Elevy {

    private String charging_party_id;
    private String reference;
    private String sender_etz_bank_code;
    private String sender_phone_number;
    private String sender_etz_card_number;
    private String sender_account_number;
    private String receiver_etz_bank_code;
    private String receiver_account_number;
    private String service;
    private String service_provider;
    private String vas_account;
    private String channel;
    private String receiver_institution_id;
    private String sender_issuer_id;
    private String sender_tin;
    private String reserve_req_time;
    private String reserve_rsp_time;
    private String reserve_tat;
    private String reserve_status_code;
    private String reserve_status_msg;
    private String elevy_id;
    private double transfer_amount;
    private double transfer_fee;
    private double taxable_amount_error;
    private double taxable_amount;
    private double elevy;
    private double elevy_error;
    private double elevy_error_diff;
    private String state;
    private String state_history;
    private String flag;
    private String status;
    private String process_attempt;
    private String confirm_status_code;
    private String confirm_status_msg;
    private String cancel_status_code;
    private String cancel_status_msg;

    public String getCharging_party_id() {
        return charging_party_id;
    }

    public void setCharging_party_id(String charging_party_id) {
        this.charging_party_id = charging_party_id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getSender_etz_bank_code() {
        return sender_etz_bank_code;
    }

    public void setSender_etz_bank_code(String sender_etz_bank_code) {
        this.sender_etz_bank_code = sender_etz_bank_code;
    }

    public String getSender_phone_number() {
        return sender_phone_number;
    }

    public void setSender_phone_number(String sender_phone_number) {
        this.sender_phone_number = sender_phone_number;
    }

    public String getSender_etz_card_number() {
        return sender_etz_card_number;
    }

    public void setSender_etz_card_number(String sender_etz_card_number) {
        this.sender_etz_card_number = sender_etz_card_number;
    }

    public String getSender_account_number() {
        return sender_account_number;
    }

    public void setSender_account_number(String sender_account_number) {
        this.sender_account_number = sender_account_number;
    }

    public String getReceiver_etz_bank_code() {
        return receiver_etz_bank_code;
    }

    public void setReceiver_etz_bank_code(String receiver_etz_bank_code) {
        this.receiver_etz_bank_code = receiver_etz_bank_code;
    }

    public String getReceiver_account_number() {
        return receiver_account_number;
    }

    public void setReceiver_account_number(String receiver_account_number) {
        this.receiver_account_number = receiver_account_number;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getService_provider() {
        return service_provider;
    }

    public void setService_provider(String service_provider) {
        this.service_provider = service_provider;
    }

    public String getVas_account() {
        return vas_account;
    }

    public void setVas_account(String vas_account) {
        this.vas_account = vas_account;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getReceiver_institution_id() {
        return receiver_institution_id;
    }

    public void setReceiver_institution_id(String receiver_institution_id) {
        this.receiver_institution_id = receiver_institution_id;
    }

    public String getSender_issuer_id() {
        return sender_issuer_id;
    }

    public void setSender_issuer_id(String sender_issuer_id) {
        this.sender_issuer_id = sender_issuer_id;
    }

    public String getSender_tin() {
        return sender_tin;
    }

    public void setSender_tin(String sender_tin) {
        this.sender_tin = sender_tin;
    }

    public String getReserve_req_time() {
        return reserve_req_time;
    }

    public void setReserve_req_time(String reserve_req_time) {
        this.reserve_req_time = reserve_req_time;
    }

    public String getReserve_rsp_time() {
        return reserve_rsp_time;
    }

    public void setReserve_rsp_time(String reserve_rsp_time) {
        this.reserve_rsp_time = reserve_rsp_time;
    }

    public String getReserve_tat() {
        return reserve_tat;
    }

    public void setReserve_tat(String reserve_tat) {
        this.reserve_tat = reserve_tat;
    }

    public String getReserve_status_code() {
        return reserve_status_code;
    }

    public void setReserve_status_code(String reserve_status_code) {
        this.reserve_status_code = reserve_status_code;
    }

    public String getReserve_status_msg() {
        return reserve_status_msg;
    }

    public void setReserve_status_msg(String reserve_status_msg) {
        this.reserve_status_msg = reserve_status_msg;
    }

    public String getElevy_id() {
        return elevy_id;
    }

    public void setElevy_id(String elevy_id) {
        this.elevy_id = elevy_id;
    }

    public double getTransfer_amount() {
        return transfer_amount;
    }

    public void setTransfer_amount(double transfer_amount) {
        this.transfer_amount = transfer_amount;
    }

    public double getTransfer_fee() {
        return transfer_fee;
    }

    public void setTransfer_fee(double transfer_fee) {
        this.transfer_fee = transfer_fee;
    }

    public double getTaxable_amount_error() {
        return taxable_amount_error;
    }

    public void setTaxable_amount_error(double taxable_amount_error) {
        this.taxable_amount_error = taxable_amount_error;
    }

    public double getTaxable_amount() {
        return taxable_amount;
    }

    public void setTaxable_amount(double taxable_amount) {
        this.taxable_amount = taxable_amount;
    }

    public double getElevy() {
        return elevy;
    }

    public void setElevy(double elevy) {
        this.elevy = elevy;
    }

    public double getElevy_error() {
        return elevy_error;
    }

    public void setElevy_error(double elevy_error) {
        this.elevy_error = elevy_error;
    }

    public double getElevy_error_diff() {
        return elevy_error_diff;
    }

    public void setElevy_error_diff(double elevy_error_diff) {
        this.elevy_error_diff = elevy_error_diff;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState_history() {
        return state_history;
    }

    public void setState_history(String state_history) {
        this.state_history = state_history;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProcess_attempt() {
        return process_attempt;
    }

    public void setProcess_attempt(String process_attempt) {
        this.process_attempt = process_attempt;
    }

    public String getConfirm_status_code() {
        return confirm_status_code;
    }

    public void setConfirm_status_code(String confirm_status_code) {
        this.confirm_status_code = confirm_status_code;
    }

    public String getConfirm_status_msg() {
        return confirm_status_msg;
    }

    public void setConfirm_status_msg(String confirm_status_msg) {
        this.confirm_status_msg = confirm_status_msg;
    }

    public String getCancel_status_code() {
        return cancel_status_code;
    }

    public void setCancel_status_code(String cancel_status_code) {
        this.cancel_status_code = cancel_status_code;
    }

    public String getCancel_status_msg() {
        return cancel_status_msg;
    }

    public void setCancel_status_msg(String cancel_status_msg) {
        this.cancel_status_msg = cancel_status_msg;
    }
}
