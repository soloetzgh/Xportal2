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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChartData {

    private ChartJsData chartJsData;
    private List<ChartJsDataset> chartJsDataset;

    public ChartJsData getChartJsData() {
        return chartJsData;
    }

    public void setChartJsData(ChartJsData chartJsData) {
        this.chartJsData = chartJsData;
    }

    public List<ChartJsDataset> getChartJsDataset() {
        return chartJsDataset;
    }

    public void setChartJsDataset(List<ChartJsDataset> chartJsDataset) {
        this.chartJsDataset = chartJsDataset;
    }

    @Override
    public String toString() {
        return "ChartData{" + "chartJsData=" + chartJsData + ", chartJsDataset=" + chartJsDataset + '}';
    }

}
