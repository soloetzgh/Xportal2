$(document).ready(function () {
});

app.controller('reversalCtrl', function ($scope, $http, $httpParamSerializerJQLike) {

    $scope.getProfile = function () {
        $http.get('./UserProfile').then(
                function (response) {
                    if (response.data) {
                        $scope.profile = response.data;
                    } else {
                        window.location = './login';
                    }
                }
        );
    };

    $scope.merchant = "";
    $scope.bank = "";

    $scope.initInfo = function () {
        $http.get('./MerchantsInfo?action=fundgate').then(
                function (response) {
                    $scope.merchantList = response.data;
                }
        );

        $http.get('./MerchantsInfo?action=vasgate').then(
                function (response) {
                    $scope.vasgateMerchantList = response.data;
                }
        );

        $http.get('./MerchantsInfo?action=banks').then(
                function (response) {
                    $scope.banksList = response.data;
                }
        );

        $http.get('./MerchantsInfo?action=channels').then(
                function (response) {
                    $scope.channelsList = response.data;
                }
        );

        $http.get('./MerchantsInfo?action=wcMerchants').then(
                function (response) {
                    $scope.wcMerchantList = response.data;
                }
        );
    };

    $scope.initDates = function () {
        var startOfDay = moment().format('2018-04-01 00:00');
        var currentDateTime = moment().format('YYYY-MM-DD 23:59');
        $('#start-date').datetimepicker({
            format: 'Y-m-d H:i',
            value: startOfDay
        });
        $scope.startDate = startOfDay;
        $scope.endDate = currentDateTime;

        $('#start-date-btn').on('click', function () {
            $('#start-date').datetimepicker('show');
        });

        $('#end-date').datetimepicker({
            format: 'Y-m-d H:i',
            value: moment().format('Y-m-d H:i')
        });
        $('#end-date-btn').on('click', function () {
            $('#end-date').datetimepicker('show');
        });

    };

    $scope.getProfile();
    $scope.initDates();

    $scope.updateApp = function () {
        $scope.getProfile();
    };

    $scope.testE_RequestLogtrans = function () {
        var i = $scope.profile.type_id;
        if (i === "1" || i === "7" || i === "8" || i === "22" || i === "44") {
            return true;
        }
        return false;
    };

    $(".load-icon").removeClass("fa-check");
    $(".load-icon").addClass("fa-cog fa-spin");
    $scope.erequestlogProxy = "./etz/proxy/2018/erequestProxy";
    $scope.rvslProxy = "./etz/proxy/2018/rvsl";
    $scope.tmcProxy = "./etz/proxy/2018/tmcProxy";
    $scope.showReProcessBox = false;
    $scope.showTMCView = false;
    $scope.destination = '000';
    $scope.origin = '25';
    $scope.appname = 'ALL';

    $scope.reloadReversalTrxns = function () {
        $scope.endDate = moment().format('YYYY-MM-DD 23:59');
        $scope.getReversalTrxns(1);
    };

    $scope.reloadAdminReversalTrxns = function () {
        $scope.endDate = moment().format('YYYY-MM-DD 23:59');
        $scope.getAdminReversalTrxns(1);
    };


    $scope.getReversalTrxns = function (page, paging) {
        $scope.updateApp();
        if ($('#start-date').val() && $('#end-date').val()) {

            $scope.showLoader = true;
            $scope.showFilterBox = false;
            $scope.pageNumber = page;
            $('.reload').addClass('fa-spin text-info');
            var url = $scope.erequestlogProxy;
            if (paging) {
                url = './e_RequestLogTransFunction?action=paging';
            }
            if ($scope.pageNumber < 1)
                $scope.pageNumber = 1;
            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.erequestRecordsLastPage)
                $scope.pageNumber = $scope.erequestRecordsLastPage;
            $http({
                url: url,
                method: 'POST',
                data: $httpParamSerializerJQLike({
                    "startDate": $scope.startDate,
                    "endDate": $scope.endDate,
                    "trans_data": $scope.trans_data,
                    "appname": $scope.appname,
                    "service": "transactions",
                    "pageNumber": $scope.pageNumber,
                    "rowsPerPage": $scope.rowsPerPage
                }),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).then(function success(response) {
                $scope.showLoader = false;
                $('.reload').removeClass('fa-spin text-info');
                $scope.erequestTrxResp = response.data;
                if ($scope.erequestTrxResp.code === "00") {
                    if (paging) {
                        $scope.erequestTrxs = JSON.parse($scope.erequestTrxResp.data);
                        $scope.getE_RequestLogTransactionsCount();
                    } else {
                        $scope.erequestTrxs = JSON.parse($scope.erequestTrxResp.data);
                        $scope.getE_RequestLogTransactionsCount();
                        $scope.getE_RequestLogTransactionsTotalAmount();
                    }
                } else {

                }
            }, function error(response) {
                $scope.showLoader = false;
                $('.reload').removeClass('fa-spin text-info');
                alert('Connection or Sever Error. Try again');
            });
        } else {
            alert('Select values for start and end date');
        }
    };
    $scope.getAdminReversalTrxns = function (page, paging) {
//        alert("hi");
        $scope.updateApp();
        if ($('#start-date').val() && $('#end-date').val()) {
//            alert("hi");
            $scope.showLoader = true;
            $scope.showFilterBox = false;
            $scope.pageNumber = page;
            $('.reload').addClass('fa-spin text-info');
            var url = $scope.erequestlogProxy;
            if (paging) {
                url = './e_RequestLogTransFunction?action=paging';
            }
            if ($scope.pageNumber < 1)
                $scope.pageNumber = 1;
            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.erequestRecordsLastPage)
                $scope.pageNumber = $scope.erequestRecordsLastPage;
            $http({
                url: url,
                method: 'POST',
                data: $httpParamSerializerJQLike({
                    "startDate": $scope.startDate,
                    "endDate": $scope.endDate,
                    "trans_data": $scope.trans_data,
                    "service": "authtransactions",
                    "pageNumber": $scope.pageNumber,
                    "rowsPerPage": $scope.rowsPerPage
                }),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).then(function success(response) {
                $scope.showLoader = false;
                $('.reload').removeClass('fa-spin text-info');
                $scope.erequestTrxResp = response.data;
                if ($scope.erequestTrxResp.code === "00") {
                    if (paging) {
                        $scope.erequestTrxs = JSON.parse($scope.erequestTrxResp.data);
                        $scope.getE_RequestLogTransactionsCount();
                    } else {
                        $scope.erequestTrxs = JSON.parse($scope.erequestTrxResp.data);
                        $scope.getE_RequestLogTransactionsCount();
                        $scope.getE_RequestLogTransactionsTotalAmount();
                    }
                } else {

                }
            }, function error(response) {
                $scope.showLoader = false;
                $('.reload').removeClass('fa-spin text-info');
                alert('Connection or Sever Error. Try again');
            });
        } else {
            alert('Select values for start and end date');
        }
    };


    $scope.searchE_RequestLogTrxn = function () {
        if ($scope.searchTrxnKey.length > 12) {
            $scope.showLoader = true;
            $('.reload').addClass('fa-spin text-info');
            $http({
                url: $scope.erequestProxy,
                method: 'POST',
                data: $httpParamSerializerJQLike({
                    "searchKey": $scope.searchTrxnKey,
                    "service": "search"
                }),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).then(function success(response) {
                $scope.showLoader = false;
                $('.reload').removeClass('fa-spin text-info');
                $scope.erequestTrxResp = response.data;
                if ($scope.erequestTrxResp.code === "00") {
                    $scope.erequestTrxs = JSON.parse($scope.erequestTrxResp.data);
                    $scope.getE_RequestLogTransactionsCount();
                } else if ($scope.erequestTrxResp.code === "01") {
                    $scope.erequestTrxs = "";
                } else {
                    $scope.erequestTrxs = "";
                }
            }, function error(response) {
                $scope.showLoader = false;
                $('.reload').removeClass('fa-spin text-info');
                alert('Connection or Sever Error. Try again');
            });
        } else {
            alert("Length of Transaction ID must be greater than 12");
        }
    };
    $scope.getE_RequestLogTransactionsCount = function () {
        $http.get('./e_RequestLogTransFunction?action=count').then(
                function (response) {
                    $scope.erequestRecordsTotalCount = JSON.parse(response.data);
                    $scope.erequestRecordsLastPage = $scope.erequestRecordsTotalCount / $scope.rowsPerPage;
                    if ($scope.erequestRecordsLastPage > parseInt($scope.erequestRecordsTotalCount / $scope.rowsPerPage)) {
                        $scope.erequestRecordsLastPage = parseInt($scope.erequestRecordsTotalCount / $scope.rowsPerPage) + 1;
                    }
                }
        );
    };
    $scope.getE_RequestLogTransactionsTotalAmount = function () {
        $http.get('./e_RequestLogTransFunction?action=totalAmount').then(
                function (response) {
                    $scope.erequestRecordsTotalAmount = response.data;
                });
    };

    $scope.searchTMCTrxn = function (id, reference) {

        buttonId = '#' + id;
        $(buttonId).prop("disabled", true);
        $(buttonId).html("<i class='fa fa-spinner fa-spin btn-loader'></i> ...");
        $scope.pageNumber = 0;
        $('.reload').addClass('fa-spin text-info');
        if ($scope.pageNumber < 1)
            $scope.pageNumber = 1;
        if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.TmcRecordsLastPage)
            $scope.pageNumber = $scope.TmcRecordsLastPage;
        $http({
            url: $scope.tmcProxy,
            method: 'POST',
            data: $httpParamSerializerJQLike({
                "searchKey": reference,
                "service": "searchReversal",
                "pageNumber": $scope.pageNumber,
                "rowsPerPage": "100"
            }),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(function success(response) {
            $scope.showLoader = false;
            $(buttonId).prop('disabled', false);
            $(buttonId).html('VERIFY');
            $scope.showTMCView = true;
            $scope.tmcTrxResp = response.data;
            $('.reload').removeClass('fa-spin text-info');
            if ($scope.tmcTrxResp.code === "00") {

                $scope.tmcTrxs = JSON.parse($scope.tmcTrxResp.data);
            } else if ($scope.tmcTrxResp.code === "01") {
                $scope.tmcTrxs = "";
            } else {
                $scope.tmcTrxs = "";
            }
        }, function error(response) {
            $scope.showTMCView = true;
            $('.reload').removeClass('fa-spin text-info');
            alert('Connection or Sever Error. Try again');
        });

    };

    $scope.reverse = function (id, transid, type) {
        buttonId = '#' + id;
        $(buttonId).prop("disabled", true);
        if (type === "ini") {
            $(buttonId).html("<i class='fa fa-spinner fa-spin btn-loader'></i> Saving...");
        } else if (type === "auth") {
            $(buttonId).html("<i class='fa fa-spinner fa-spin btn-loader'></i> Reversing...");
        }
        $scope.updateApp();

        var url = $scope.rvslProxy;

        $http({
            url: url,
            method: 'POST',
            data: $httpParamSerializerJQLike({
                "id": id,
                "transid": transid
            }),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(function success(response) {
            $scope.showLoader = false;
            if (type === "ini") {
                if (response.data === "success") {
                    $(buttonId).removeClass("btn-primary");
                    $(buttonId).addClass('btn-success');
                    $(buttonId).html('SUCCESS, AWAITING AUTHORIZATION');
                    $(buttonId).prop('disabled', true);
                } else {
                    alert(response.data);
                    $(buttonId).prop('disabled', false);
                    $(buttonId).html('TRY AGAIN');
                }
            } else if (type === "auth") {
                $(buttonId).removeClass("btn-primary");
                $(buttonId).addClass('btn-success');
                $(buttonId).html(response.data);
                $(buttonId + "_1").removeClass("hidden");
            }
        }, function error(response) {
            $scope.showLoader = false;
            alert('Connection or Sever Error. Try again');
        });
    };

    $scope.hideFilterBox = function () {
        $scope.showTrxFilterBox = false;
        $scope.showFilterBox = false;
    };

    $scope.showFilterBoxView = function () {
        $scope.showTrxFilterBox = true;
        $scope.showFilterBox = true;
    };
    $scope.toggleFilterBoxView = function () {
        $scope.showFilterBox = !$scope.showFilterBox;
    };


    $scope.toggleTMCView = function () {
        $scope.showTMCView = !$scope.showTMCView;
    };

    $scope.initInfo();
});