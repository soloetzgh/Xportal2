//author: seth.sebeh.kusi October.2019
class DynamicGraph {
    graphObject = null;
    divContainer = null;
    url = null;
    chart = [];
    constructor() {}

    static generateID(length) {
        let result = '';
        let characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
        let charactersLength = characters.length;
        for (let i = 0; i < length; i++) {
            result += characters.charAt(Math.floor(Math.random() * charactersLength));
        }
        return result;
    }

    static float2Cedis(value) {
        return "GHS " + (value).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,');
    }

    static convert(value, unit) {
        switch (unit) {
            case 'currency':
                return "GHS " + (value).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,');
                break;
            case 's':
                return value + ' ' + unit;
            default:
                return value;
        }
    }

    get getGraphObject() {
        return this.graphObject;
    }
    set setGraphObject(x) {
        this.graphObject = x;
    }

    get getChart() {
        return this.chart;
    }
    set setChart(x) {
        this.chart = x;
    }

    fetchGraphData(url) {
        this.url = url;
        let result = null;
        $.ajax({
            type: 'GET',
            url: url,
            async: true,
            success: function (data) {
                result = data;
            },
            error: function () {
//                alert("Sorry no data available or Amard server unable to retrieve data for display.");
            }
        });
        this.graphObject = result;
        //console.log(result);
        return this.graphObject;
    }

    fetchGraphData2(url) {
        let xhr = new XMLHttpRequest();
        xhr.open("GET", url);
        xhr.send();
        xhr.onload = function () {
//            alert('his grace');
            if (xhr.status === 200) {
            }
        };
        xhr.onerror = function () {
            console.log('An error occured sending this request');
        };
    }

    drawGraph(div, graphObject) {
        let legend_display = true;
        for (let chartJsDataset of graphObject.chartJsDataset) {
            chartJsDataset.data = JSON.parse(chartJsDataset.data);
            // if (graphObject.chartJsData.chartType !== 'line' && graphObject.chartJsData.chartType !== 'bar') {
            if (chartJsDataset.backgroundColor.startsWith("[")) {
                chartJsDataset.backgroundColor = JSON.parse(chartJsDataset.backgroundColor);
//                chartJsDataset.borderColor = JSON.parse(chartJsDataset.borderColor);
            }
//            else {
////            chartJsDataset.backgroundColor = ["#3e95cd", "#ff9f40", "#8e5ea2", "#ff6384", "#005005", "#3cba9f", "#e8c3b9", "#c45850", "#883997", "#810095", "#928000", "#607d8b", "#000000", "#003300", "#ba000d", "#6a0080", "#002984", "#00675b", "#5a9216", "#c8b900", "#c41c00,#707070", "#870000", ];
////            chartJsDataset.borderColor = ["#3e95cd", "#ff9f40", "#8e5ea2", "#ff6384", "#005005", "#3cba9f", "#e8c3b9", "#c45850", "#883997", "#810095", "#928000", "#607d8b", "#000000", "#003300", "#ba000d", "#6a0080", "#002984", "#00675b", "#5a9216", "#c8b900", "#c41c00,#707070", "#870000", ];
//            }
        }
        let canvas_id = DynamicGraph.generateID(10);
        let graph_div_item = "<div class='chart-cnt'><div style='width:100%'><canvas id='" + canvas_id + "'></canvas></div></div>";
        $("." + div).append(graph_div_item);
        let ctx = document.getElementById(canvas_id).getContext('2d');
        let chart = new Chart(ctx, {
            type: graphObject.chartJsData.chartType,
            data: {
                labels: JSON.parse(graphObject.chartJsData.labels),
                datasets: graphObject.chartJsDataset
            },
            options: {
                responsive: true,
//                maintainAspectRatio: false,
                legend: {display: legend_display},
                title: {
                    display: true,
                    text: graphObject.chartJsData.title
                },
                scales: {
                    yAxes: [{
                            ticks: {
                                beginAtZero: true
//                                callback: function (value, index, values) {
//                                    return DynamicGraph.convert(value, 's');
//                                }
                            },
                            scaleLabel: {
                                display: true,
                                labelString: graphObject.chartJsData.y1Label
                            }
                        }],
                    xAxes: [{
                            ticks: {
                                beginAtZero: true,
                                display: graphObject.chartJsData.showXdata
                            },
                            scaleLabel: {
                                //display: true,
                                //labelString: graphObject.chartJsData.x1Label
                            }
                        }]
                }
            }
        });
        this.chart.push(chart);
        return chart;
    }

    drawStackedGraph(div, data) {
        var Chart;
        Chart.defaults.groupableBar = Chart.helpers.clone(Chart.defaults.bar);

        var helpers = Chart.helpers;
        Chart.controllers.groupableBar = Chart.controllers.bar.extend({
            calculateBarX: function (index, datasetIndex) {
                // position the bars based on the stack index
                var stackIndex = this.getMeta().stackIndex;
                return Chart.controllers.bar.prototype.calculateBarX.apply(this, [index, stackIndex]);
            },

            hideOtherStacks: function (datasetIndex) {
                var meta = this.getMeta();
                var stackIndex = meta.stackIndex;

                this.hiddens = [];
                for (var i = 0; i < datasetIndex; i++) {
                    var dsMeta = this.chart.getDatasetMeta(i);
                    if (dsMeta.stackIndex !== stackIndex) {
                        this.hiddens.push(dsMeta.hidden);
                        dsMeta.hidden = true;
                    }
                }
            },

            unhideOtherStacks: function (datasetIndex) {
                var meta = this.getMeta();
                var stackIndex = meta.stackIndex;

                for (var i = 0; i < datasetIndex; i++) {
                    var dsMeta = this.chart.getDatasetMeta(i);
                    if (dsMeta.stackIndex !== stackIndex) {
                        dsMeta.hidden = this.hiddens.unshift();
                    }
                }
            },

            calculateBarY: function (index, datasetIndex) {
                this.hideOtherStacks(datasetIndex);
                var barY = Chart.controllers.bar.prototype.calculateBarY.apply(this, [index, datasetIndex]);
                this.unhideOtherStacks(datasetIndex);
                return barY;
            },

            calculateBarBase: function (datasetIndex, index) {
                this.hideOtherStacks(datasetIndex);
                var barBase = Chart.controllers.bar.prototype.calculateBarBase.apply(this, [datasetIndex, index]);
                this.unhideOtherStacks(datasetIndex);
                return barBase;
            },

            getBarCount: function () {
                var stacks = [];

                // put the stack index in the dataset meta
                Chart.helpers.each(this.chart.data.datasets, function (dataset, datasetIndex) {
                    var meta = this.chart.getDatasetMeta(datasetIndex);
                    if (meta.bar && this.chart.isDatasetVisible(datasetIndex)) {
                        var stackIndex = stacks.indexOf(dataset.stack);
                        if (stackIndex === -1) {
                            stackIndex = stacks.length;
                            stacks.push(dataset.stack);
                        }
                        meta.stackIndex = stackIndex;
                    }
                }, this);

                this.getMeta().stacks = stacks;
                return stacks.length;
            }
        });

        let canvas_id = DynamicGraph.generateID(10);
        let graph_div_item = "<div class='chart-cnt'><div style='width:100%'><canvas id='" + canvas_id + "'></canvas></div></div>";
        $("." + div).append(graph_div_item);

        var ctx = document.getElementById(canvas_id).getContext("2d");
        new Chart(ctx, {
            type: 'groupableBar',
            data: data,
            options: {
                legend: {
                    labels: {
                        generateLabels: function (chart) {
                            return Chart.defaults.global.legend.labels.generateLabels.apply(this, [chart]).filter(function (item, i) {
                                return i <= 2;
                            });
                        }
                    }
                },
                scales: {
                    yAxes: [{
                            ticks: {
                                /* max: 160, */
                            },
                            stacked: true
                        }]
                }
            }
        });

    }

    drawGraphPie(div, graphObject) {
        let legend_display = true;
        for (let chartJsDataset of graphObject.chartJsDataset) {
            chartJsDataset.data = JSON.parse(chartJsDataset.data);
            //if (graphObject.chartJsData.chartType !== 'line' && graphObject.chartJsData.chartType !== 'bar') {
            if (chartJsDataset.backgroundColor.startsWith("[")) {
//                legend_display = false;
                chartJsDataset.backgroundColor = JSON.parse(chartJsDataset.backgroundColor);
//                chartJsDataset.borderColor = JSON.parse(chartJsDataset.borderColor);
                //console.log(chartJsDataset.borderColor);
            }
//            else {
////            chartJsDataset.backgroundColor = ["#3e95cd", "#ff9f40", "#8e5ea2", "#ff6384", "#005005", "#3cba9f", "#e8c3b9", "#c45850", "#883997", "#810095", "#928000", "#607d8b", "#000000", "#003300", "#ba000d", "#6a0080", "#002984", "#00675b", "#5a9216", "#c8b900", "#c41c00,#707070", "#870000", ];
////            chartJsDataset.borderColor = ["#3e95cd", "#ff9f40", "#8e5ea2", "#ff6384", "#005005", "#3cba9f", "#e8c3b9", "#c45850", "#883997", "#810095", "#928000", "#607d8b", "#000000", "#003300", "#ba000d", "#6a0080", "#002984", "#00675b", "#5a9216", "#c8b900", "#c41c00,#707070", "#870000", ];
//            }
        }
        let canvas_id = DynamicGraph.generateID(10);
        let graph_div_item = "<div class='chart-cnt'><div style='width:100%'><canvas id='" + canvas_id + "'></canvas></div></div>";
        $("." + div).append(graph_div_item);
        let ctx = document.getElementById(canvas_id).getContext('2d');
        let chart = new Chart(ctx, {
            type: graphObject.chartJsData.chartType,
            data: {
                labels: JSON.parse(graphObject.chartJsData.labels),
                datasets: graphObject.chartJsDataset
            },
            options: {
                responsive: true,
                legend: {display: legend_display},
                title: {
                    display: false,
                    text: graphObject.chartJsData.title
                }
            }
        });
        this.chart.push(chart);
        return chart;
    }

    updateGraph(url) {
        let data = this.fetchGraphData(url);
        let chartArr = this.chart;
//        for (let i = 0; i < data.length; i++) {
//            chartArr[i].data.labels = JSON.parse(data[i].chartJsData.labels);
//            //chartArr[i].data.datasets[0].data = JSON.parse(data[i].chartJsData.y1Values);
//            chartArr[i].data.datasets[0].data = JSON.parse(data[i].chartJsDataset[0].data);
//            chartArr[i].update();
//        }
        for (let i = 0; i < data.length; i++) {
            chartArr[i].data.labels = JSON.parse(data[i].chartJsData.labels);
            //chartArr[i].data.datasets[0].data = JSON.parse(data[i].chartJsData.y1Values);
            for (let j = 0; j < chartArr[i].data.datasets.length; j++) {
                chartArr[i].data.datasets[j].data = JSON.parse(data[i].chartJsDataset[j].data);
                let legend_display = true;
                //if (data[i].chartJsData.chartType !== 'line' && data[i].chartJsData.chartType !== 'bar') {
                if (data[i].chartJsDataset[j].backgroundColor.startsWith("[")) {
                    chartArr[i].data.datasets[j].backgroundColor = JSON.parse(data[i].chartJsDataset[j].backgroundColor);
//                    chartArr[i].data.datasets[j].borderColor = JSON.parse(data[i].chartJsDataset[j].borderColor);
                }
                chartArr[i].update();
            }
//            chartArr[i].data.datasets[0].data = JSON.parse(data[i].chartJsDataset[0].data);
//            chartArr[i].update();
        }
    }
}

