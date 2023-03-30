$(document).ready(function () {

});

app.controller('reprocessCtrl', function ($scope, $http, $httpParamSerializerJQLike) {

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

    $scope.initDates = function () {
        var startOfDay = moment().format('YYYY-MM-DD 00:00');
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


    $(".load-icon").removeClass("fa-check");
    $(".load-icon").addClass("fa-cog fa-spin");
    $scope.erequestlogProxy = "./etz/proxy/2018/erequestProxy";
    $scope.reprocessProxy = "./etz/proxy/2018/reprocessProxy";
//    $scope.rvslProxy = "./etz/proxy/2018/rvsl";
    $scope.tmcProxy = "./etz/proxy/2018/tmcProxy";
    $scope.showReProcessBox = false;
    $scope.showTMCView = false;
    $scope.destination = '000';
    $scope.origin = '25';
    $scope.appname = 'ALL';
    $scope.transType = 'AIRTIME';
    $scope.rowsPerPage = '100';
    $scope.srcAccount = '';
    $scope.destAccount = '';
    $scope.showDetailsBox = false;
    $scope.reprocessResp = 'initiate reprocess';


    $scope.reloadReprocessTrxns = function () {
        $scope.endDate = moment().format('YYYY-MM-DD 23:59');
        $scope.getReprocessTrxns(1);
    };

    $scope.getReprocessTrxns = function (page, paging) {

        $scope.updateApp();
        if ($('#start-date').val() && $('#end-date').val()) {
            $scope.showLoader = true;
            $scope.showFilterBox = false;
            $scope.pageNumber = page;
            $('.reload').addClass('fa-spin text-info');
            var url = $scope.reprocessProxy;
            if (paging) {
                url = './FailedReprocessFunc?action=paging';
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
                    "transtype": $scope.transType,
                    "source": $scope.srcAccount,
                    "destination": $scope.destAccount,
                    "searchKey": $scope.searchTrxnKey,
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
                        $scope.getReprocessTrxnsCount();
                    } else {
                        $scope.erequestTrxs = JSON.parse($scope.erequestTrxResp.data);
                        $scope.getReprocessTrxnsCount();
//                        $scope.getE_RequestLogTransactionsTotalAmount();
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

    $scope.getReprocessTrxnsCount = function () {
        $http.get('./FailedReprocessFunc?action=count').then(
                function (response) {
                    $scope.erequestRecordsTotalCount = JSON.parse(response.data);
                    $scope.erequestRecordsLastPage = $scope.erequestRecordsTotalCount / $scope.rowsPerPage;
                    if ($scope.erequestRecordsLastPage > parseInt($scope.erequestRecordsTotalCount / $scope.rowsPerPage)) {
                        $scope.erequestRecordsLastPage = parseInt($scope.erequestRecordsTotalCount / $scope.rowsPerPage) + 1;
                    }
                }
        );
    };

//    $scope.checkInitiateNum = function () {
//        var selectedCount = 0;
//        for (var k = 0; k < $scope.erequestTrxs.length; k++)
//        {
//            if ($scope.erequestTrxs[k].selected === true)
//            {
//                selectedCount += 1;
//            }
//
//        }
//        if (selectedCount > 0) {
//            $('#InitiateteBtn').prop("disabled", false);
//        } else {
//            $('#InitiateteBtn').prop("disabled", true);
//        }
//
//    };

    $scope.checkAuthorizeNum = function () {
        var selectedCount = 0;
        for (var k = 0; k < $scope.reproTrxs.length; k++)
        {
            if ($scope.reproTrxs[k].selected === true)
            {
                selectedCount += 1;
            }

        }
        if (selectedCount > 0) {

            $('#AuthorizeBtn').prop("disabled", false);
        } else {
            $('#AuthorizeBtn').prop("disabled", true);
        }

    };

    $scope.confirmationDialogConfig = {};

    $scope.confirmationDialog = function () {

        switch ($scope.profile.role_id) {

            case "3":
                $scope.confirmationDialogConfig = {
                    title: "CONFIRM INITIATION",
                    message: "Are you sure you want initiate?",
                    buttons: [{
                            label: "Initiate",
                            action: "initiateReprocess"
                        }]
                };
                break;
            case "1":
            case "5":
                $scope.confirmationDialogConfig = {
                    title: "CONFIRM AUTHORIZATION",
                    message: "Are you sure you want authorize?",
                    buttons: [{
                            label: "Authorize",
                            action: "authorizeReprocess"
                        }]
                };
                break;
            default:
                break;
        }
        $scope.showDialog(true);
    };

    $scope.executeDialogAction = function (action) {
        if (typeof $scope[action] === "function") {
            $scope[action]();
        }
    };


    $scope.showDialog = function (flag) {
        jQuery("#confirmation-dialog .modal").modal(flag ? 'show' : 'hide');
    };

//    $scope.initiateReprocess = function () {
//        $scope.updateApp();
//        $scope.showDialog();
//        $scope.referencesArray = "";
//        $scope.transactions = "";
//        for (var k = 0; k < $scope.erequestTrxs.length; k++)
//        {
//            if ($scope.erequestTrxs[k].selected === true)
//            {
//                $scope.referencesArray += $scope.erequestTrxs[k].unique_transid + "_"
//                        + $scope.erequestTrxs[k].provider + "_" + $scope.erequestTrxs[k].amount
//                        + "_" + $scope.erequestTrxs[k].dest_account + "_" + $scope.erequestTrxs[k].trans_type + ",";
//            }
//        }
//        $scope.transactions = $scope.referencesArray.slice(0, -1);
//        console.log($scope.transactions);
//
//        if ($scope.transactions.length > 1) {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.reprocessProxy;
//
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//
//                    "references": $scope.transactions,
//                    "service": "reprocess"
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.erequestTrxResp = response.data;
//                if ($scope.erequestTrxResp.code === "00") {
//
//                    $scope.erequestTrxs = JSON.parse($scope.erequestTrxResp.data);
//                    $scope.getE_RequestLogTransactionsCount();
//
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Sever Error. Try again');
//            });
//        } else {
//            alert('No Transaaction Selected');
//        }
//
//    };

    $scope.cancelInitiate = function () {
        $scope.ref = "";
        $scope.transId = "";
        $scope.verifyDetails = "";
        $scope.verifyDest = "";
        $scope.showDetailsBox = false;
    };
    $scope.trimShort = function (text) {
        if (text) {
            if (text.length > 40) {
                return (text.substring(0, 38)) + " ...";
            }
        }
        return text;
    };
    $scope.initiateReprocess = function (k) {
        $scope.updateApp();
//        $scope.referencesArray = "";

        transaction = {};
        $scope.showDetailsBox = false;
        $('#authorize').addClass("bg-success");
        $('#authorize').prop("disabled", false);
        $('#authorize').removeClass("bg-danger");
        $('#authorize').html("INITIATE");
        $('#vst' + k).html("<i class='fa fa-spinner fa-spin btn-loader'></i> PROCESSING");
        $('#vst' + k).prop('title', '');
        $scope.recordId = k;
        $scope.ref = $scope.erequestTrxs[k].unique_transid;
        $scope.verifyDest = $scope.erequestTrxs[k].dest_account;
        transaction["unique_transid"] = $scope.erequestTrxs[k].unique_transid;
        transaction["provider"] = $scope.erequestTrxs[k].provider;
        transaction["amount"] = $scope.erequestTrxs[k].amount.toString();
        transaction["dest_account"] = $scope.erequestTrxs[k].dest_account;
        transaction["transtype"] = $scope.erequestTrxs[k].transtype;
        transaction["otherinfo"] = $scope.erequestTrxs[k].otherinfo;


        console.log(transaction);

        if (Object.keys(transaction).length > 1) {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $('.reload').addClass('fa-spin text-info');
            var url = $scope.reprocessProxy;

            $http({
                url: url,
                method: 'POST',
                data: $httpParamSerializerJQLike({

                    "references": JSON.stringify(transaction),
                    "service": "reprocess"
                }),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).then(function success(response) {
                $scope.showLoader = false;
                $scope.erequestTrxResp = response.data;
//                alert($scope.erequestTrxResp.data);
                switch ($scope.erequestTrxResp.data.split(":")[0]) {
                    case "INITIATED":
                        $('#vst' + k).addClass("bg-success");
                        $('#vst' + k).removeClass("bg-danger");
                        $('#vst' + k).html("INITIATED");
                        $('#vst' + k).prop("disabled", true);
                        break;
                    case "SUCCESS":
                        $('#vst' + k).addClass("bg-success");
                        $('#vst' + k).removeClass("bg-danger");
                        $('#vst' + k).html("SUCCESS");
                        $('#vst' + k).prop("disabled", true);
                        break;
                    case "DUPLICATE":
                        $('#vst' + k).addClass("bg-danger");
                        $('#vst' + k).removeClass("bg-success");
                        $('#vst' + k).html("DUPLICATE");
                        $('#vst' + k).prop("disabled", true);
                        break;
                    case "ERROR":
                        $('#vst' + k).addClass("bg-danger");
                        $('#vst' + k).removeClass("bg-success");
                        $('#vst' + k).html("ERROR");
                        if ($scope.erequestTrxResp.data.split(":").length > 1) {
                            $('#vst' + k).prop('title', $scope.erequestTrxResp.data.substring($scope.erequestTrxResp.data.indexOf(":") + 1));
                        }
                        break;
                    case "VERIFY":
                        $('#authorize').prop("disabled", false);
                        $('#vst' + k).html("INITIATE");
                        $scope.verifyDetails = $scope.erequestTrxResp.data.substring($scope.erequestTrxResp.data.indexOf(":") + 1);
                        $scope.showDetailsBox = true;
//                        $('#vst' + k).addClass("bg-warning");
                        $('#vst' + k).removeClass("bg-success");
                        $('#vst' + k).removeClass("bg-danger");
//                        $('#vst' + k).html("CHECKING");
                        break;
                    default:
                        $('#vst' + k).addClass("bg-danger");
                        $('#vst' + k).removeClass("bg-success");
                        $('#vst' + k).html("ERROR");
                        break;
                }
            }, function error(response) {
                $scope.showLoader = false;
//                alert(response);
                $('#vst' + k).addClass("bg-danger");
                $('#vst' + k).removeClass("bg-success");
                $('#vst' + k).html("ERROR");
            });
        } else {
            alert('No Transaaction Selected');
        }

    };
    $scope.continueInitiateReprocess = function () {
        $scope.updateApp();
//        $scope.referencesArray = "";

        console.log(transaction);

        if (Object.keys(transaction).length > 1) {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $('.reload').addClass('fa-spin text-info');
            var url = $scope.reprocessProxy;

            $http({
                url: url,
                method: 'POST',
                data: $httpParamSerializerJQLike({

                    "references": JSON.stringify(transaction),
                    "service": "continuereprocess"
                }),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).then(function success(response) {
                $scope.showLoader = false;
                $scope.erequestTrxResp = response.data;
//                alert($scope.erequestTrxResp.data);
                switch ($scope.erequestTrxResp.data.split(":")[0]) {
                    case "INITIATED":
                        $('#vst' + $scope.recordId).addClass("bg-success");
                        $('#vst' + $scope.recordId).removeClass("bg-danger");
                        $('#vst' + $scope.recordId).html("INITIATED");
                        $('#vst' + $scope.recordId).prop("disabled", true);
                        $('#authorize').addClass("bg-success");
                        $('#authorize').prop("disabled", true);
                        $('#authorize').removeClass("bg-danger");
                        $('#authorize').html("INITIATED");
                        break;
                    case "DUPLICATE":
                        $('#vst' + $scope.recordId).addClass("bg-danger");
                        $('#vst' + $scope.recordId).removeClass("bg-success");
                        $('#vst' + $scope.recordId).html("DUPLICATE");
                        $('#vst' + $scope.recordId).prop("disabled", true);
                        $('#authorize').addClass("bg-danger");
                        $('#authorize').prop("disabled", true);
                        $('#authorize').removeClass("bg-success");
                        $('#authorize').html("DUPLICATE");
                        break;
                    case "ERROR":
                        $('#vst' + $scope.recordId).addClass("bg-danger");
                        $('#vst' + $scope.recordId).removeClass("bg-success");
                        $('#vst' + $scope.recordId).html("ERROR");
                        $('#authorize').addClass("bg-danger");
                        $('#authorize').removeClass("bg-success");
                        $('#authorize').html("ERROR");
                        $('#authorize').prop("disabled", true);
                        break;
                    default:
                        $('#vst' + $scope.recordId).addClass("bg-danger");
                        $('#vst' + $scope.recordId).removeClass("bg-success");
                        $('#vst' + $scope.recordId).html("ERROR");
                        break;
                }
            }, function error(response) {
                $scope.showLoader = false;
//                alert(response);
                $('#vst' + $scope.recordId).addClass("bg-danger");
                $('#vst' + $scope.recordId).removeClass("bg-success");
                $('#vst' + $scope.recordId).html("ERROR");
            });
            setTimeout(() => {
                $scope.$apply($scope.cancelInitiate());
            }, 2000);
        } else {
            alert('No Transaaction Selected');
        }

    };
    $scope.authorizeReprocess = function (k, action) {
        $scope.updateApp();
//        $scope.referencesArray = "";

        transaction = {};
        $scope.showDetailsBox = false;
//        $('#authorize').addClass("bg-success");
//        $('#authorize').prop("disabled", false);
//        $('#authorize').removeClass("bg-danger");
//        $('#authorize').html("AUTHORIZE");
        $('#' + action + k).html("<i class='fa fa-spinner fa-spin btn-loader'></i>");
        $('#' + action + k).prop('title', '');
        $scope.recordId = k;
        $scope.ref = $scope.reproTrxs[k].reference;
        $scope.verifyDest = $scope.reproTrxs[k].account;
        transaction["unique_transid"] = $scope.reproTrxs[k].reference;
        transaction["provider"] = $scope.reproTrxs[k].product;
        transaction["amount"] = $scope.reproTrxs[k].amount.toString();
        transaction["dest_account"] = $scope.reproTrxs[k].account;
        transaction["transtype"] = $scope.reproTrxs[k].trans_type;
        transaction["otherinfo"] = $scope.reproTrxs[k].otherinfo;


        console.log(transaction);

        if (Object.keys(transaction).length > 1) {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $('.reload').addClass('fa-spin text-info');
            var url = $scope.reprocessProxy;

            $http({
                url: url,
                method: 'POST',
                data: $httpParamSerializerJQLike({
                    "action": action,
                    "references": JSON.stringify(transaction),
                    "service": "reprocess"
                }),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).then(function success(response) {
                $scope.showLoader = false;
                $scope.erequestTrxResp = response.data;
//                alert($scope.erequestTrxResp.data);
                switch ($scope.erequestTrxResp.data.split(":")[0]) {
                    case "SUCCESS":
                        $('#' + action + k).addClass("bg-success");
                        $('#' + action + k).removeClass("bg-danger");
                        $('#' + action + k).html("<i class='fa fa-thumbs-up'></i>");
                        $('#' + action + k).prop("disabled", true);
                        break;
                    case "DENIED":
                        $('#' + action + k).addClass("bg-success");
                        $('#' + action + k).removeClass("bg-danger");
                        $('#' + action + k).html("<i class='fa fa-thumbs-up'></i>");
                        $('#' + action + k).prop("disabled", true);
                        break;
                    case "ERROR":
                        $('#' + action + k).addClass("bg-danger");
                        $('#' + action + k).removeClass("bg-success");
                        $('#' + action + k).html("<i class='fa fa-thumbs-down'></i>");
                        if ($scope.erequestTrxResp.data.split(":").length > 1) {
                            $('#' + action + k).prop('title', $scope.erequestTrxResp.data.substring($scope.erequestTrxResp.data.indexOf(":") + 1));
                        }
                        break;
                    default:
                        $('#' + action + k).addClass("bg-danger");
                        $('#' + action + k).removeClass("bg-success");
                        $('#' + action + k).html("ERROR");
                        $('#' + action + k).prop('title', 'AN ERROR OCCURED');
                        break;
                }
            }, function error(response) {
                $scope.showLoader = false;
//                alert(response);
                $('#' + action + k).addClass("bg-danger");
                $('#' + action + k).removeClass("bg-success");
                $('#' + action + k).html("ERROR");
                $('#' + action + k).prop('title', 'SERVER ERROR');
            });
        } else {
            alert('No Transaaction Selected');
        }

    };
    $scope.getReproTrxns = function (page, paging) {
        $scope.updateApp();
        if ($('#start-date').val() && $('#end-date').val()) {

            $scope.showLoader = true;
            $scope.showFilterBox = false;
            $scope.pageNumber = page;
            $('.reload').addClass('fa-spin text-info');
            var url = $scope.reprocessProxy;
            if (paging) {
                url = './reprocessTransFunction?action=paging';
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
                    "account": $scope.account,
                    "transtype": $scope.transType,
                    "appname": $scope.appname,
                    "service": "transactions",
                    "pageNumber": $scope.pageNumber,
                    "rowsPerPage": $scope.rowsPerPage
                }),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).then(function success(response) {
                $scope.showLoader = false;
                $('.reload').removeClass('fa-spin text-info');
                $scope.reproTrxnResp = response.data;
                if ($scope.reproTrxnResp.code === "00") {
                    if (paging) {
                        $scope.reproTrxs = JSON.parse($scope.reproTrxnResp.data);
                        $scope.getReproTrxnsCount();
                    } else {
                        $scope.reproTrxs = JSON.parse($scope.reproTrxnResp.data);
                        $scope.getReproTrxnsCount();
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
    $scope.getReproTrxnsCount = function () {
        $http.get('./LoggedReprocessFunc?action=count').then(
                function (response) {
                    $scope.erequestRecordsTotalCount = JSON.parse(response.data);
                    $scope.erequestRecordsLastPage = $scope.erequestRecordsTotalCount / $scope.rowsPerPage;
                    if ($scope.erequestRecordsLastPage > parseInt($scope.erequestRecordsTotalCount / $scope.rowsPerPage)) {
                        $scope.erequestRecordsLastPage = parseInt($scope.erequestRecordsTotalCount / $scope.rowsPerPage) + 1;
                    }
                }
        );
    };

    $scope.getReprocessedTrxns = function (page, paging) {
        $scope.updateApp();
        if ($('#start-date').val() && $('#end-date').val()) {

            $scope.showLoader = true;
            $scope.showFilterBox = false;
            $scope.pageNumber = page;
            $('.reload').addClass('fa-spin text-info');
            var url = $scope.reprocessProxy;
            if (paging) {
                url = './reprocessTransFunction?action=paging';
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
                    "account": $scope.account,
                    "transtype": $scope.transType,
                    "appname": $scope.appname,
                    "service": "reprocessedtrans",
                    "pageNumber": $scope.pageNumber,
                    "rowsPerPage": $scope.rowsPerPage
                }),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).then(function success(response) {
                $scope.showLoader = false;
                $('.reload').removeClass('fa-spin text-info');
                $scope.reproTrxnResp = response.data;
                if ($scope.reproTrxnResp.code === "00") {
                    if (paging) {
                        $scope.reproTrxs = JSON.parse($scope.reproTrxnResp.data);
                        $scope.getReproTrxnsCount();
                    } else {
                        $scope.reproTrxs = JSON.parse($scope.reproTrxnResp.data);
                        $scope.getReproTrxnsCount();
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
            $(buttonId).html("<i class='fa fa-spinner fa-spin btn-loader'></i> Reprocessing...");
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

});