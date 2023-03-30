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
public class VasBillTransaction implements Serializable {

    @Id
    private String unique_transid;
    private String merchant_id;
    private BigDecimal amount;
    private String trans_date;
    private String trans_channel;
    private String trans_status;
    private String trans_note;
    private String subscriber_id;
    private String issuer_code;
    private String payment_code;
    private String mobile_no;
    private String trans_no;
    private String attempts;
    private String processed_date;

    private String merchant_code;
    private String trans_period;
    private String trans_type;
    private String trans_amount;
    private String card_subname;
    private String card_fullname;
    private String card_account;
    private String card_num;
    private String card_issuersubcodes;
    private String spnf_batchno;
    private String user_name;
    private String sp_status;
    private String sub_code;
    private String payment_type;
    private String t_fullname;
    private String t_address;
    private String t_quantity;
    private String cheque_no;
    private String cheque_bank;
    private String aut_username;
    private String int_status;
    private String process_status;
    private String status_description;
    private String response_date;
    private String transid;

    private String sequence;
    private String reference;
    private String source_account;
    private String dest_account;
    private String dest_balance;
    private String response_code;
    private String response_message;
    private String source;
    private String original_amount;
    private String provider;
    private String voucher_type;
    private String original_transid;
    private String alias;
    private String check_digit;
    private String initial_response_code;
    private String process_mode;
    private String ip_address;
    private String total_attempts;
    private String linetype;
    private String source_balance;
    
    private String vas_id;
    private String acct_no;
    private String client_ref;
    private String tran_dt;
    private String message;
    private String narration;
    private String node_ref;
    private String node_response;
    private String prod_id;
    private String vas_ref;
    private String subscriber;
    private String third_party_ref;
    private String channel_id;
    private String terminal_id;
    private String bank_code;
    private String line_type;
    private String line_mode;
    private String client;
    private String other_info;




    public String getProcessed_date() {
        return processed_date;
    }

    public void setProcessed_date(String processed_date) {
        this.processed_date = processed_date;
    }

    public String getUnique_transid() {
        return unique_transid;
    }

    public void setUnique_transid(String unique_transid) {
        this.unique_transid = unique_transid;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTrans_date() {
        return trans_date;
    }

    public void setTrans_date(String trans_date) {
        this.trans_date = trans_date;
    }

    public String getTrans_channel() {
        return trans_channel;
    }

    public void setTrans_channel(String trans_channel) {
        this.trans_channel = trans_channel;
    }

    public String getTrans_status() {
        return trans_status;
    }

    public void setTrans_status(String trans_status) {
        this.trans_status = trans_status;
    }

    public String getTrans_note() {
        return trans_note;
    }

    public void setTrans_note(String trans_note) {
        this.trans_note = trans_note;
    }

    public String getSubscriber_id() {
        return subscriber_id;
    }

    public void setSubscriber_id(String subscriber_id) {
        this.subscriber_id = subscriber_id;
    }

    public String getIssuer_code() {
        return issuer_code;
    }

    public void setIssuer_code(String issuer_code) {
        this.issuer_code = issuer_code;
    }

    public String getPayment_code() {
        return payment_code;
    }

    public void setPayment_code(String payment_code) {
        this.payment_code = payment_code;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getTrans_no() {
        return trans_no;
    }

    public void setTrans_no(String trans_no) {
        this.trans_no = trans_no;
    }

    public String getAttempts() {
        return attempts;
    }

    public void setAttempts(String attempts) {
        this.attempts = attempts;
    }

    public String getMerchant_code() {
        return merchant_code;
    }

    public void setMerchant_code(String merchant_code) {
        this.merchant_code = merchant_code;
    }

    public String getTrans_period() {
        return trans_period;
    }

    public void setTrans_period(String trans_period) {
        this.trans_period = trans_period;
    }

    public String getTrans_type() {
        return trans_type;
    }

    public void setTrans_type(String trans_type) {
        this.trans_type = trans_type;
    }

    public String getTrans_amount() {
        return trans_amount;
    }

    public void setTrans_amount(String trans_amount) {
        this.trans_amount = trans_amount;
    }

    public String getCard_subname() {
        return card_subname;
    }

    public void setCard_subname(String card_subname) {
        this.card_subname = card_subname;
    }

    public String getCard_fullname() {
        return card_fullname;
    }

    public void setCard_fullname(String card_fullname) {
        this.card_fullname = card_fullname;
    }

    public String getCard_account() {
        return card_account;
    }

    public void setCard_account(String card_account) {
        this.card_account = card_account;
    }

    public String getCard_num() {
        return card_num;
    }

    public void setCard_num(String card_num) {
        this.card_num = card_num;
    }

    public String getCard_issuersubcodes() {
        return card_issuersubcodes;
    }

    public void setCard_issuersubcodes(String card_issuersubcodes) {
        this.card_issuersubcodes = card_issuersubcodes;
    }

    public String getSpnf_batchno() {
        return spnf_batchno;
    }

    public void setSpnf_batchno(String spnf_batchno) {
        this.spnf_batchno = spnf_batchno;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getSp_status() {
        return sp_status;
    }

    public void setSp_status(String sp_status) {
        this.sp_status = sp_status;
    }

    public String getSub_code() {
        return sub_code;
    }

    public void setSub_code(String sub_code) {
        this.sub_code = sub_code;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getT_fullname() {
        return t_fullname;
    }

    public void setT_fullname(String t_fullname) {
        this.t_fullname = t_fullname;
    }

    public String getT_address() {
        return t_address;
    }

    public void setT_address(String t_address) {
        this.t_address = t_address;
    }

    public String getT_quantity() {
        return t_quantity;
    }

    public void setT_quantity(String t_quantity) {
        this.t_quantity = t_quantity;
    }

    public String getCheque_no() {
        return cheque_no;
    }

    public void setCheque_no(String cheque_no) {
        this.cheque_no = cheque_no;
    }

    public String getCheque_bank() {
        return cheque_bank;
    }

    public void setCheque_bank(String cheque_bank) {
        this.cheque_bank = cheque_bank;
    }

    public String getAut_username() {
        return aut_username;
    }

    public void setAut_username(String aut_username) {
        this.aut_username = aut_username;
    }

    public String getInt_status() {
        return int_status;
    }

    public void setInt_status(String int_status) {
        this.int_status = int_status;
    }

    public String getProcess_status() {
        return process_status;
    }

    public void setProcess_status(String process_status) {
        this.process_status = process_status;
    }

    public String getStatus_description() {
        return status_description;
    }

    public void setStatus_description(String status_description) {
        this.status_description = status_description;
    }

    public String getResponse_date() {
        return response_date;
    }

    public void setResponse_date(String response_date) {
        this.response_date = response_date;
    }

    public String getTransid() {
        return transid;
    }

    public void setTransid(String transid) {
        this.transid = transid;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getSource_account() {
        return source_account;
    }

    public void setSource_account(String source_account) {
        this.source_account = source_account;
    }

    public String getDest_balance() {
        return dest_balance;
    }

    public void setDest_balance(String dest_balance) {
        this.dest_balance = dest_balance;
    }

    public String getResponse_code() {
        return response_code;
    }

    public void setResponse_code(String response_code) {
        this.response_code = response_code;
    }

    public String getResponse_message() {
        return response_message;
    }

    public void setResponse_message(String response_message) {
        this.response_message = response_message;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getOriginal_amount() {
        return original_amount;
    }

    public void setOriginal_amount(String original_amount) {
        this.original_amount = original_amount;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getVoucher_type() {
        return voucher_type;
    }

    public void setVoucher_type(String voucher_type) {
        this.voucher_type = voucher_type;
    }

    public String getOriginal_transid() {
        return original_transid;
    }

    public void setOriginal_transid(String original_transid) {
        this.original_transid = original_transid;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCheck_digit() {
        return check_digit;
    }

    public void setCheck_digit(String check_digit) {
        this.check_digit = check_digit;
    }

    public String getInitial_response_code() {
        return initial_response_code;
    }

    public void setInitial_response_code(String initial_response_code) {
        this.initial_response_code = initial_response_code;
    }

    public String getProcess_mode() {
        return process_mode;
    }

    public void setProcess_mode(String process_mode) {
        this.process_mode = process_mode;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public String getTotal_attempts() {
        return total_attempts;
    }

    public void setTotal_attempts(String total_attempts) {
        this.total_attempts = total_attempts;
    }

    public String getDest_account() {
        return dest_account;
    }

    public void setDest_account(String dest_account) {
        this.dest_account = dest_account;
    }

    public String getLinetype() {
        return linetype;
    }

    public void setLinetype(String linetype) {
        this.linetype = linetype;
    }

    public String getSource_balance() {
        return source_balance;
    }

    public void setSource_balance(String source_balance) {
        this.source_balance = source_balance;
    }

    public String getVas_id() {
        return vas_id;
    }

    public void setVas_id(String vas_id) {
        this.vas_id = vas_id;
    }

    public String getAcct_no() {
        return acct_no;
    }

    public void setAcct_no(String acct_no) {
        this.acct_no = acct_no;
    }

    public String getClient_ref() {
        return client_ref;
    }

    public void setClient_ref(String client_ref) {
        this.client_ref = client_ref;
    }

    public String getTran_dt() {
        return tran_dt;
    }

    public void setTran_dt(String tran_dt) {
        this.tran_dt = tran_dt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getNode_ref() {
        return node_ref;
    }

    public void setNode_ref(String node_ref) {
        this.node_ref = node_ref;
    }

    public String getNode_response() {
        return node_response;
    }

    public void setNode_response(String node_response) {
        this.node_response = node_response;
    }

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public String getVas_ref() {
        return vas_ref;
    }

    public void setVas_ref(String vas_ref) {
        this.vas_ref = vas_ref;
    }

    public String getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(String subscriber) {
        this.subscriber = subscriber;
    }

    public String getThird_party_ref() {
        return third_party_ref;
    }

    public void setThird_party_ref(String third_party_ref) {
        this.third_party_ref = third_party_ref;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getTerminal_id() {
        return terminal_id;
    }

    public void setTerminal_id(String terminal_id) {
        this.terminal_id = terminal_id;
    }

    public String getBank_code() {
        return bank_code;
    }

    public void setBank_code(String bank_code) {
        this.bank_code = bank_code;
    }

    public String getLine_type() {
        return line_type;
    }

    public void setLine_type(String line_type) {
        this.line_type = line_type;
    }

    public String getLine_mode() {
        return line_mode;
    }

    public void setLine_mode(String line_mode) {
        this.line_mode = line_mode;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getOther_info() {
        return other_info;
    }

    public void setOther_info(String other_info) {
        this.other_info = other_info;
    }


    @Override
    public String toString() {
        return "VasBillTransaction{" + "unique_transid=" + unique_transid + ", merchant_id=" + merchant_id + ", amount=" + amount + ", trans_date=" + trans_date + ", trans_channel=" + trans_channel + ", trans_status=" + trans_status + ", trans_note=" + trans_note + ", subscriber_id=" + subscriber_id + ", issuer_code=" + issuer_code + ", payment_code=" + payment_code + ", mobile_no=" + mobile_no + ", trans_no=" + trans_no + ", attempts=" + attempts + ", processed_date=" + processed_date + ", merchant_code=" + merchant_code + ", trans_period=" + trans_period + ", trans_type=" + trans_type + ", trans_amount=" + trans_amount + ", card_subname=" + card_subname + ", card_fullname=" + card_fullname + ", card_account=" + card_account + ", card_num=" + card_num + ", card_issuersubcodes=" + card_issuersubcodes + ", spnf_batchno=" + spnf_batchno + ", user_name=" + user_name + ", sp_status=" + sp_status + ", sub_code=" + sub_code + ", payment_type=" + payment_type + ", t_fullname=" + t_fullname + ", t_address=" + t_address + ", t_quantity=" + t_quantity + ", cheque_no=" + cheque_no + ", cheque_bank=" + cheque_bank + ", aut_username=" + aut_username + ", int_status=" + int_status + ", process_status=" + process_status + ", status_description=" + status_description + ", response_date=" + response_date + ", transid=" + transid + ", sequence=" + sequence + ", reference=" + reference + ", source_account=" + source_account + ", dest_account=" + dest_account + ", dest_balance=" + dest_balance + ", response_code=" + response_code + ", response_message=" + response_message + ", source=" + source + ", original_amount=" + original_amount + ", provider=" + provider + ", voucher_type=" + voucher_type + ", original_transid=" + original_transid + ", alias=" + alias + ", check_digit=" + check_digit + ", initial_response_code=" + initial_response_code + ", process_mode=" + process_mode + ", ip_address=" + ip_address + ", total_attempts=" + total_attempts + ", linetype=" + linetype + ", source_balance=" + source_balance + ", vas_id=" + vas_id + ", acct_no=" + acct_no + ", client_ref=" + client_ref + ", tran_dt=" + tran_dt + ", message=" + message + ", narration=" + narration + ", node_ref=" + node_ref + ", node_response=" + node_response + ", prod_id=" + prod_id + ", vas_ref=" + vas_ref + ", subscriber=" + subscriber + ", third_party_ref=" + third_party_ref + ", channel_id=" + channel_id + ", terminal_id=" + terminal_id + ", bank_code=" + bank_code + ", line_type=" + line_type + ", line_mode=" + line_mode + ", client=" + client + ", other_info=" + other_info + '}';
    }

    
    
    
    
}
