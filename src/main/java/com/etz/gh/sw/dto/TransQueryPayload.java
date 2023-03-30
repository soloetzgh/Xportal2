package com.etz.gh.sw.dto;

public class TransQueryPayload {

    private String session_id;
    private String echo_data;
    private String extra_parameters;
    private QueryObj queries;

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getEcho_data() {
        return echo_data;
    }

    public void setEcho_data(String echo_data) {
        this.echo_data = echo_data;
    }

    public String getExtra_parameters() {
        return extra_parameters;
    }

    public void setExtra_parameters(String extra_parameters) {
        this.extra_parameters = extra_parameters;
    }

    public QueryObj getQueries() {
        return queries;
    }

    public void setQueries(QueryObj queries) {
        this.queries = queries;
    }

    public TransQueryPayload(String session_id, String echo_data, String extra_parameters, QueryObj queries) {
        super();
        this.session_id = session_id;
        this.echo_data = echo_data;
        this.extra_parameters = extra_parameters;
        this.queries = queries;
    }

    @Override
    public String toString() {
        String response = "{"
                + "\"session_id\": \"" + session_id + "\","
                + "\"echo_data\": \"" + echo_data + "\","
                + "\"extra_parameters\": \"" + extra_parameters + "\","
                + "\"queries\" : " + queries + ""
                + "}";

        return response;
    }

}
