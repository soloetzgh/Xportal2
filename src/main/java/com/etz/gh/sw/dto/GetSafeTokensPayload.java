package com.etz.gh.sw.dto;

public class GetSafeTokensPayload {

    private String datetime;

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public GetSafeTokensPayload(String datetime) {
        super();
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        String response = "{"
                + "\"datetime\" : \"" + datetime + "\""
                + "}";

        return response;
    }

}
