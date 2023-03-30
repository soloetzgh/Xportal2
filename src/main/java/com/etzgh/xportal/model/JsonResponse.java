package com.etzgh.xportal.model;

public class JsonResponse {

    private String code;
    private String message;
    private String description;
    private String data;

    public JsonResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public JsonResponse(String code, String message, String data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public JsonResponse(String code, String message, String data, String description) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "JsonResponse{" + "code=" + code + ", message=" + message + ", description=" + description + ", data=" + data + '}';
    }
}
