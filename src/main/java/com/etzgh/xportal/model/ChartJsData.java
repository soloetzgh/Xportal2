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
public class ChartJsData {

    private String y1Label;
    private Boolean showXdata;
    private String chartType;
    private String title;
    private String x1Label;
    private String labels;

    public String getY1Label() {
        return y1Label;
    }

    public void setY1Label(String y1Label) {
        this.y1Label = y1Label;
    }

    public Boolean getShowXdata() {
        return showXdata;
    }

    public void setShowXdata(Boolean showXdata) {
        this.showXdata = showXdata;
    }

    public String getChartType() {
        return chartType;
    }

    public void setChartType(String chartType) {
        this.chartType = chartType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getX1Label() {
        return x1Label;
    }

    public void setX1Label(String x1Label) {
        this.x1Label = x1Label;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    @Override
    public String toString() {
        return "ChartJsData{" + "y1Label=" + y1Label + ", showXdata=" + showXdata + ", chartType=" + chartType + ", title=" + title + ", x1Label=" + x1Label + ", labels=" + labels + '}';
    }

}
