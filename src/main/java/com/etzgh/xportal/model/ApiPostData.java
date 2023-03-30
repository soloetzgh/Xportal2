package com.etzgh.xportal.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ApiPostData {

    String startDate;
    String endDate;
    String merchant;
    String product;
    String accountNumber;
    String status;
    String service;
    String action;
    String apiSecret;
    String apiCode;

    String userCode;
    String admin;
    String cardSchemeNumbers;
    String type_desc;
    String role_id;
    String type_id;
    String searchKey;
    String userName;
    String ClientId;
    String user_id;
    int pageNumber;
    int rowsPerPage;

    String uniqueTransId;
    String trans_code;
    String transType;
    String bank_code;
    String subscriberId;
    String trans_status;
    List<String> roleList;

    String channel;
    String bank;

    String biller;
    String transId;

    private String bankid;
    private String from_source;
    private String card_num;
    private String optionType;

    private String payType;
    private String wcMerchant;

    private String destination;
    private String lineType;

    private String trans_channel;

    private String pan;
    private String terminal_id;

    private String card_account;

    private String cop_issuercode;

    private String cop_role_id;

    private String cop_company_id;

    private String mobile_no;

    private String phone;

    private String type;

    private String amount;

    private String amount2;

    private String refundStatus;
    private String partialReversal;
    private String reversalReason;

    private String ipAddress;

    private String userData;

    private List<String> appId;

    private String company;

    private String origin;

    private String original_respcode;
    private String updated_respcode;
    private String initiated_by;
    private String initiated_date;
    private String authorised_by;
    private String authorised_date;

    private String originalReference;

    private String client;
    private String vastype;
    private String account;

    private String data;

    String otherInfo;

    private String branch;

    String ticketNumber;
    String nlaReference;
    String etzReference;
    String newCustomerId;

    String reason;
    String date_whitelisted;
    String date_blacklisted;
    String accountType;
    String clientRef;

    String destinationAccount;
    String destinationBank;
    String narration;
    String sourceCard;
    String transactionReference;
    String pin;
    String network;

    String reference;
    String extReference;
    String destacct;
    String transNumber;
    
    String email;
    String idNumber;
    String can;
    String transactionStatus;
    String transactions;
    

    private HashMap<String, Object> options;

    public ApiPostData() {
        this.company = "";
        this.startDate = "";
        this.endDate = "";
        this.merchant = "";
        this.product = "";
        this.accountNumber = "";
        this.status = "";
        this.service = "";
        this.action = "";
        this.apiSecret = "";
        this.apiCode = "";
        this.userCode = "";
        this.admin = "";
        this.cardSchemeNumbers = "";
        this.type_desc = "";
        this.role_id = "";
        this.type_id = "";
        this.searchKey = "";
        this.userName = "";
        this.ClientId = "";
        this.user_id = "";
        this.pageNumber = 1;
        this.rowsPerPage = 1;
        this.uniqueTransId = "";
        this.trans_code = "";
        this.transType = "";
        this.bank_code = "";
        this.subscriberId = "";
        this.trans_status = "";
        this.channel = "";
        this.bank = "";
        this.biller = "";
        this.transId = "";
        this.bankid = "";
        this.from_source = "";
        this.card_num = "";
        this.optionType = "";
        this.payType = "";
        this.wcMerchant = "";
        this.destination = "";
        this.lineType = "";
        this.trans_channel = "";
        this.pan = "";
        this.terminal_id = "";
        this.card_account = "";
        this.cop_issuercode = "";
        this.cop_role_id = "";
        this.cop_company_id = "";
        this.mobile_no = "";
        this.phone = "";
        this.type = "";
        this.amount = "";
        this.amount2 = "";
        this.refundStatus = "";
        this.partialReversal = "";
        this.reversalReason = "";
        this.userData = "";
        this.ipAddress = "";
        this.origin = "";
        this.appId = new ArrayList<>();
        this.roleList = new ArrayList<>();
        this.options = new HashMap<>();

        this.original_respcode = "";
        this.updated_respcode = "";
        this.initiated_by = "";
        this.initiated_date = "";
        this.authorised_by = "";
        this.authorised_date = "";
        this.client = "";
        this.vastype = "";
        this.account = "";
        this.data = "";
        this.otherInfo = "";
        this.branch = "";

        this.ticketNumber = "";
        this.nlaReference = "";
        this.etzReference = "";

        this.originalReference = "";

        this.newCustomerId = "";

        this.reason = "";
        this.date_blacklisted = "";
        this.date_whitelisted = "";
        this.accountType = "";
        this.clientRef = "";

        this.destinationAccount = "";
        this.destinationBank = "";
        this.narration = "";
        this.sourceCard = "";

        this.transactionReference = "";
        this.pin = "";
        this.network = "";

        this.reference = "";
        this.extReference = "";
        this.destacct = "";
        this.transNumber = "";

        this.email = "";
        this.idNumber = "";
        this.can = "";
        
        this.transactionStatus = "";
        this.transactions = "";
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    public String getApiCode() {
        return apiCode;
    }

    public void setApiCode(String apiCode) {
        this.apiCode = apiCode;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getCardSchemeNumbers() {
        return cardSchemeNumbers;
    }

    public void setCardSchemeNumbers(String cardSchemeNumbers) {
        this.cardSchemeNumbers = cardSchemeNumbers;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getRowsPerPage() {
        return rowsPerPage;
    }

    public void setRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }

    public String getUniqueTransId() {
        return uniqueTransId;
    }

    public void setUniqueTransId(String uniqueTransId) {
        this.uniqueTransId = uniqueTransId;
    }

    public String getTrans_code() {
        return trans_code;
    }

    public void setTrans_code(String trans_code) {
        this.trans_code = trans_code;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getBank_code() {
        return bank_code;
    }

    public void setBank_code(String bank_code) {
        this.bank_code = bank_code;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getTrans_status() {
        return trans_status;
    }

    public void setTrans_status(String trans_status) {
        this.trans_status = trans_status;
    }

    public String getType_desc() {
        return type_desc;
    }

    public void setType_desc(String type_desc) {
        this.type_desc = type_desc;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public List<String> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<String> roleList) {
        this.roleList = roleList;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBiller() {
        return biller;
    }

    public void setBiller(String biller) {
        this.biller = biller;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getBankid() {
        return bankid;
    }

    public void setBankid(String bankid) {
        this.bankid = bankid;
    }

    public String getFrom_source() {
        return from_source;
    }

    public void setFrom_source(String from_source) {
        this.from_source = from_source;
    }

    public String getCard_num() {
        return card_num;
    }

    public void setCard_num(String card_num) {
        this.card_num = card_num;
    }

    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getLineType() {
        return lineType;
    }

    public void setLineType(String lineType) {
        this.lineType = lineType;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getWcMerchant() {
        return wcMerchant;
    }

    public void setWcMerchant(String wcMerchant) {
        this.wcMerchant = wcMerchant;
    }

    public String getTrans_channel() {
        return trans_channel;
    }

    public void setTrans_channel(String trans_channel) {
        this.trans_channel = trans_channel;
    }

    /**
     * @return the pan
     */
    public String getPan() {
        return pan;
    }

    /**
     * @param pan the pan to set
     */
    public void setPan(String pan) {
        this.pan = pan;
    }

    /**
     * @return the terminal_id
     */
    public String getTerminal_id() {
        return terminal_id;
    }

    /**
     * @param terminal_id the terminal_id to set
     */
    public void setTerminal_id(String terminal_id) {
        this.terminal_id = terminal_id;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCard_account() {
        return card_account;
    }

    public void setCard_account(String card_account) {
        this.card_account = card_account;
    }

    public String getCop_issuercode() {
        return cop_issuercode;
    }

    public void setCop_issuercode(String cop_issuercode) {
        this.cop_issuercode = cop_issuercode;
    }

    public String getCop_role_id() {
        return cop_role_id;
    }

    public void setCop_role_id(String cop_role_id) {
        this.cop_role_id = cop_role_id;
    }

    public String getCop_company_id() {
        return cop_company_id;
    }

    public void setCop_company_id(String cop_company_id) {
        this.cop_company_id = cop_company_id;
    }

    public String getClientId() {
        return ClientId;
    }

    public void setClientId(String ClientId) {
        this.ClientId = ClientId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount2() {
        return amount2;
    }

    public void setAmount2(String amount2) {
        this.amount2 = amount2;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getPartialReversal() {
        return partialReversal;
    }

    public void setPartialReversal(String partialReversal) {
        this.partialReversal = partialReversal;
    }

    public String getReversalReason() {
        return reversalReason;
    }

    public void setReversalReason(String reversalReason) {
        this.reversalReason = reversalReason;
    }

    public String getUserData() {
        return userData;
    }

    public void setUserData(String userData) {
        this.userData = userData;
    }

    public List<String> getAppId() {
        return appId;
    }

    public void setAppId(List<String> appId) {
        this.appId = appId;
    }

    public HashMap<String, Object> getOptions() {
        return options;
    }

    public void setOptions(HashMap<String, Object> options) {
        this.options = options;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    public String getInitiated_by() {
        return initiated_by;
    }

    public void setInitiated_by(String initiated_by) {
        this.initiated_by = initiated_by;
    }

    public String getInitiated_date() {
        return initiated_date;
    }

    public void setInitiated_date(String initiated_date) {
        this.initiated_date = initiated_date;
    }

    public String getAuthorised_by() {
        return authorised_by;
    }

    public void setAuthorised_by(String authorised_by) {
        this.authorised_by = authorised_by;
    }

    public String getAuthorised_date() {
        return authorised_date;
    }

    public void setAuthorised_date(String authorised_date) {
        this.authorised_date = authorised_date;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getVastype() {
        return vastype;
    }

    public void setVastype(String vastype) {
        this.vastype = vastype;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getNlaReference() {
        return nlaReference;
    }

    public void setNlaReference(String nlaReference) {
        this.nlaReference = nlaReference;
    }

    public String getEtzReference() {
        return etzReference;
    }

    public void setEtzReference(String etzReference) {
        this.etzReference = etzReference;
    }

    public String getOriginalReference() {
        return originalReference;
    }

    public void setOriginalReference(String originalReference) {
        this.originalReference = originalReference;
    }

    public String getNewCustomerId() {
        return newCustomerId;
    }

    public void setNewCustomerId(String newCustomerId) {
        this.newCustomerId = newCustomerId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDate_whitelisted() {
        return date_whitelisted;
    }

    public void setDate_whitelisted(String date_whitelisted) {
        this.date_whitelisted = date_whitelisted;
    }

    public String getDate_blacklisted() {
        return date_blacklisted;
    }

    public void setDate_blacklisted(String date_blacklisted) {
        this.date_blacklisted = date_blacklisted;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getClientRef() {
        return clientRef;
    }

    public void setClientRef(String clientRef) {
        this.clientRef = clientRef;
    }

    public String getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(String destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public String getDestinationBank() {
        return destinationBank;
    }

    public void setDestinationBank(String destinationBank) {
        this.destinationBank = destinationBank;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getSourceCard() {
        return sourceCard;
    }

    public void setSourceCard(String sourceCard) {
        this.sourceCard = sourceCard;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getExtReference() {
        return extReference;
    }

    public void setExtReference(String extReference) {
        this.extReference = extReference;
    }

    public String getDestacct() {
        return destacct;
    }

    public void setDestacct(String destacct) {
        this.destacct = destacct;
    }

    public String getTransNumber() {
        return transNumber;
    }

    public void setTransNumber(String transNumber) {
        this.transNumber = transNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getCan() {
        return can;
    }

    public void setCan(String can) {
        this.can = can;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getTransactions() {
        return transactions;
    }

    public void setTransactions(String transactions) {
        this.transactions = transactions;
    }


//    @Override
//    public String toString() {
//        return "ApiPostData{" + "startDate=" + startDate + ", endDate=" + endDate + ", merchant=" + merchant + ", product=" + product + ", accountNumber=" + accountNumber + ", status=" + status + ", service=" + service + ", action=" + action + ", apiSecret=" + apiSecret + ", apiCode=" + apiCode + ", userCode=" + userCode + ", admin=" + admin + ", cardSchemeNumbers=" + cardSchemeNumbers + ", type_desc=" + type_desc + ", role_id=" + role_id + ", type_id=" + type_id + ", searchKey=" + searchKey + ", userName=" + userName + ", ClientId=" + ClientId + ", user_id=" + user_id + ", pageNumber=" + pageNumber + ", rowsPerPage=" + rowsPerPage + ", uniqueTransId=" + uniqueTransId + ", trans_code=" + trans_code + ", transType=" + transType + ", bank_code=" + bank_code + ", subscriberId=" + subscriberId + ", trans_status=" + trans_status + ", roleList=" + roleList + ", channel=" + channel + ", bank=" + bank + ", biller=" + biller + ", transId=" + transId + ", bankid=" + bankid + ", from_source=" + from_source + ", card_num=" + card_num + ", optionType=" + optionType + ", payType=" + payType + ", wcMerchant=" + wcMerchant + ", destination=" + destination + ", lineType=" + lineType + ", trans_channel=" + trans_channel + ", pan=" + pan + ", terminal_id=" + terminal_id + ", card_account=" + card_account + ", cop_issuercode=" + cop_issuercode + ", cop_role_id=" + cop_role_id + ", cop_company_id=" + cop_company_id + ", mobile_no=" + mobile_no + ", phone=" + phone + ", type=" + type + ", amount=" + amount + ", amount2=" + amount2 + ", refundStatus=" + refundStatus + ", partialReversal=" + partialReversal + ", reversalReason=" + reversalReason + ", ipAddress=" + ipAddress + ", userData=" + userData + ", appId=" + appId + ", company=" + company + ", origin=" + origin + ", original_respcode=" + original_respcode + ", updated_respcode=" + updated_respcode + ", initiated_by=" + initiated_by + ", initiated_date=" + initiated_date + ", authorised_by=" + authorised_by + ", authorised_date=" + authorised_date + ", originalReference=" + originalReference + ", client=" + client + ", vastype=" + vastype + ", account=" + account + ", data=" + data + ", otherInfo=" + otherInfo + ", branch=" + branch + ", ticketNumber=" + ticketNumber + ", nlaReference=" + nlaReference + ", etzReference=" + etzReference + ", newCustomerId=" + newCustomerId + ", reason=" + reason + ", date_whitelisted=" + date_whitelisted + ", date_blacklisted=" + date_blacklisted + ", accountType=" + accountType + ", clientRef=" + clientRef + ", destinationAccount=" + destinationAccount + ", destinationBank=" + destinationBank + ", narration=" + narration + ", sourceCard=" + sourceCard + ", transactionReference=" + transactionReference + ", pin=" + pin + ", network=" + network + ", reference=" + reference + ", extReference=" + extReference + ", destacct=" + destacct + ", transNumber=" + transNumber + ", email=" + email + ", idNumber=" + idNumber + ", can=" + can + ", transactionStatus=" + transactionStatus + ", options=" + options + '}';
//    }

    @Override
    public String toString() {
        return "ApiPostData{" + "startDate=" + startDate + ", endDate=" + endDate + ", merchant=" + merchant + ", product=" + product + ", accountNumber=" + accountNumber + ", status=" + status + ", service=" + service + ", action=" + action + ", apiSecret=" + apiSecret + ", apiCode=" + apiCode + ", userCode=" + userCode + ", admin=" + admin + ", cardSchemeNumbers=" + cardSchemeNumbers + ", type_desc=" + type_desc + ", role_id=" + role_id + ", type_id=" + type_id + ", searchKey=" + searchKey + ", userName=" + userName + ", ClientId=" + ClientId + ", user_id=" + user_id + ", pageNumber=" + pageNumber + ", rowsPerPage=" + rowsPerPage + ", uniqueTransId=" + uniqueTransId + ", trans_code=" + trans_code + ", transType=" + transType + ", bank_code=" + bank_code + ", subscriberId=" + subscriberId + ", trans_status=" + trans_status + ", roleList=" + roleList + ", channel=" + channel + ", bank=" + bank + ", biller=" + biller + ", transId=" + transId + ", bankid=" + bankid + ", from_source=" + from_source + ", card_num=" + card_num + ", optionType=" + optionType + ", payType=" + payType + ", wcMerchant=" + wcMerchant + ", destination=" + destination + ", lineType=" + lineType + ", trans_channel=" + trans_channel + ", pan=" + pan + ", terminal_id=" + terminal_id + ", card_account=" + card_account + ", cop_issuercode=" + cop_issuercode + ", cop_role_id=" + cop_role_id + ", cop_company_id=" + cop_company_id + ", mobile_no=" + mobile_no + ", phone=" + phone + ", type=" + type + ", amount=" + amount + ", amount2=" + amount2 + ", refundStatus=" + refundStatus + ", partialReversal=" + partialReversal + ", reversalReason=" + reversalReason + ", ipAddress=" + ipAddress + ", userData=" + userData + ", appId=" + appId + ", company=" + company + ", origin=" + origin + ", original_respcode=" + original_respcode + ", updated_respcode=" + updated_respcode + ", initiated_by=" + initiated_by + ", initiated_date=" + initiated_date + ", authorised_by=" + authorised_by + ", authorised_date=" + authorised_date + ", originalReference=" + originalReference + ", client=" + client + ", vastype=" + vastype + ", account=" + account + ", data=" + data + ", otherInfo=" + otherInfo + ", branch=" + branch + ", ticketNumber=" + ticketNumber + ", nlaReference=" + nlaReference + ", etzReference=" + etzReference + ", newCustomerId=" + newCustomerId + ", reason=" + reason + ", date_whitelisted=" + date_whitelisted + ", date_blacklisted=" + date_blacklisted + ", accountType=" + accountType + ", clientRef=" + clientRef + ", destinationAccount=" + destinationAccount + ", destinationBank=" + destinationBank + ", narration=" + narration + ", sourceCard=" + sourceCard + ", transactionReference=" + transactionReference + ", pin=" + pin + ", network=" + network + ", reference=" + reference + ", extReference=" + extReference + ", destacct=" + destacct + ", transNumber=" + transNumber + ", email=" + email + ", idNumber=" + idNumber + ", can=" + can + ", transactionStatus=" + transactionStatus + ", transactions=" + transactions + ", options=" + options + '}';
    }
    
    

}
