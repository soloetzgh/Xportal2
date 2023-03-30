package com.etz.gh.sw.dto;

public class QueryObj {

    private String trace_id;
    private String identifier;
    private String client_trace_no;
    private String dr_cr;
    private String status_code;
    private String start_txn_date;

    private String end_txn_date;

    public String getTrace_id() {
        return trace_id;
    }

    public void setTrace_id(String trace_id) {
        this.trace_id = trace_id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getClient_trace_no() {
        return client_trace_no;
    }

    public void setClient_trace_no(String client_trace_no) {
        this.client_trace_no = client_trace_no;
    }

    public String getDr_cr() {
        return dr_cr;
    }

    public void setDr_cr(String dr_cr) {
        this.dr_cr = dr_cr;
    }

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public String getStart_txn_date() {
        return start_txn_date;
    }

    public void setStart_txn_date(String start_txn_date) {
        this.start_txn_date = start_txn_date;
    }

    public String getEnd_txn_date() {
        return end_txn_date;
    }

    public void setEnd_txn_date(String end_txn_date) {
        this.end_txn_date = end_txn_date;
    }

    public QueryObj(String trace_id, String identifier, String client_trace_no, String dr_cr, String status_code,
            String start_txn_date, String end_txn_date) {
        super();
        this.trace_id = trace_id;
        this.identifier = identifier;
        this.client_trace_no = client_trace_no;
        this.dr_cr = dr_cr;
        this.status_code = status_code;
        this.start_txn_date = start_txn_date;
        this.end_txn_date = end_txn_date;
    }

}
