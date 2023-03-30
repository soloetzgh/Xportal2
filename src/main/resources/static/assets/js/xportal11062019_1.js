//$(document).ready(function () {
////    var toastr; 
////    var ws;
////
////    toastr.options = {
////        "closeButton": false,
////        "debug": false,
////        "newestOnTop": false,
////        "progressBar": false,
////        "positionClass": "toast-top-right",
////        "preventDuplicates": false,
////        "onclick": null,
////        "showDuration": "300",
////        "hideDuration": "1000",
////        "timeOut": "1000",
////        "extendedTimeOut": "1000",
////        "showEasing": "swing",
////        "hideEasing": "linear",
////        "showMethod": "fadeIn",
////        "hideMethod": "fadeOut"
////    };
////
////    
////    function connect () {
////
////        var host = document.location.host;
////        var pathname = document.location.pathname;
////
////        ws = new WebSocket("ws://" + host + pathname + "chat/9000000000000490" );
////        alert("herr");
////
////        ws.onmessage = function (event) {
//////        var log = document.getElementById("log");
////            console.log(event.data);
////            var message = JSON.parse(event.data);
////            var toastr;
////            toastr["success"](message.from, message.content);
//////        log.innerHTML += message.from + " : " + message.content + "\n";
////        };
////    };
////
////   function send () {
////        var content = "hello";
////        var json = JSON.stringify({
////            "content": content
////        });
////
////        ws.send(json);
////    };
////    function me () {
////        alert("year");
////    };
//
//
//
////if (window.WebSocket) {
////    var ws = new WebSocket("ws://localhost:8084/XPortal2/push");
////    ws.onmessage = function(event) {
////        var text = event.data;
////        console.log(text);
////    };
////}
////else {
////    // Bad luck. Browser doesn't support it. Consider falling back to long polling.
////    // See http://caniuse.com/websockets for an overview of supported browsers.
////    // There exist jQuery WebSocket plugins with transparent fallback.
////}
//});
//
//
//
//var app = angular.module('FundGateApp', ['ngRoute', 'ngFileUpload', 'dropdown-multiselect']);
////app.filter('offset', function () {
////    return function (input, start) {
////        start = parseInt(start, 10);
////        return input.slice(start);
////    };
////});
//app.config(['$httpProvider', '$routeProvider', function ($httpProvider, $routeProvider, $location) {
//        $httpProvider.interceptors.push('ajaxNonceInterceptor');
//        $routeProvider
//                .when('/', {
//                    templateUrl: 'views/transactionReport.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/transactions', {
//                    templateUrl: 'views/transactionReport.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/activity', {
//                    templateUrl: 'views/activityReport.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/justpay', {
//                    templateUrl: 'views/justpayReport.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/vasgatebills', {
//                    templateUrl: 'views/vasGateBillTransLog.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/barclaysUBP', {
//                    templateUrl: 'views/barclaysUBP.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/mobileMoney', {
//                    templateUrl: 'views/mobileMoneyReport.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/VTU', {
//                    templateUrl: 'views/vtuInvestigation.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/webconnect', {
//                    templateUrl: 'views/webConnectPlus.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/SASReport', {
//                    templateUrl: 'views/saspayment.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/changePassword', {
//                    templateUrl: 'views/changePassword.html',
//                    controller: 'supportCtrl'
//                })
//                .when('/firstChangePassword', {
//                    templateUrl: 'views/changePasswordNew.html',
//                    controller: 'supportCtrl'
//                })
//                .when('/USSDReport', {
//                    templateUrl: 'views/USSDReport.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/TMCtransactions', {
//                    templateUrl: 'views/tmctransactions.html',
//                    controller: 'fgTransactionsCtrl'
//                })
////              .when('/BillPaymentReport',{
////                 templateUrl: 'views/billpayment.html',
////		 controller: 'fgTransactionsCtrl'
////              })
////              .when('/MobileInvestigation',{
////                 templateUrl: 'views/mobileinvestigation.html',
////		 controller: 'fgTransactionsCtrl'
////              })
//                .when('/KweseTvBillPaymentTransactions', {
//                    templateUrl: 'views/kwesetvbillpayments.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//
//                .when('/BOGswitchReport', {
//                    templateUrl: 'views/BOGswitchReport.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/FundgateBalanceReport', {
//                    templateUrl: 'views/FundgateBalanceReport.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/KYCReport', {
//                    templateUrl: 'views/KYCReport.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//
//                .when('/BusyTransactionsReport', {
//                    templateUrl: 'views/busyTransactionReport.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//
//                .when('/GwclTransactionsReport', {
//                    templateUrl: 'views/gwclTransactionReport.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/FGMomoTransactions', {
//                    templateUrl: 'views/fundgatemomo.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/ElacTransactionsReport', {
//                    templateUrl: 'views/elacTransactionReport.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/MobileInvestigation', {
//                    templateUrl: 'views/mobileinvestigation.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/fgInvestigation', {
//                    templateUrl: 'views/fgInvestigation.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/gcbAgentTransactions', {
//                    templateUrl: 'views/gcbagentTrans.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/MomoStatusUpdater', {
//                    templateUrl: 'views/MomoStatusUpdater.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/CardTransactionsReport', {
//                    templateUrl: 'views/cardTransactionReport.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/RevenueReport', {
//                    templateUrl: 'views/revenueReport.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/CardDetailsUpdate', {
//                    templateUrl: 'views/CardDetailsUpdate.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/CardDetailView', {
//                    templateUrl: 'views/CardDetailsView.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/CardHolderEnquiry', {
//                    templateUrl: 'views/cardholderEnquiry.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/OfflineDetailsView', {
//                    templateUrl: 'views/OfflineDetailsView.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/Reprocess', {
//                    templateUrl: 'views/reprocess.html',
//                    controller: 'reprocessCtrl'
//                })
//                .when('/Investigation', {
//                    templateUrl: 'views/investigation.html',
//                    controller: 'investigationCtrl'
//                })
//                .when('/InitiateReversal', {
//                    templateUrl: 'views/initiate_reversal.html',
//                    controller: 'reversalCtrl'
//                })
//                .when('/InitiateReprocessing', {
//                    templateUrl: 'views/initiate_reprocessing.html',
//                    controller: 'reprocessCtrl'
//                })
//                .when('/AuthoriseReversal', {
//                    templateUrl: 'views/authorize_reversal.html',
//                    controller: 'reversalCtrl'
//                })
//                .when('/AuthoriseReprocessing', {
//                    templateUrl: 'views/authorize_reprocessing.html',
//                    controller: 'reprocessCtrl'
//                })
//                .when('/AuthorisedReprocessing', {
//                    templateUrl: 'views/authorized_reprocessing.html',
//                    controller: 'reprocessCtrl'
//                })
//                .when('/AdminAuthoriseReversal', {
//                    templateUrl: 'views/admin_authorize_reversal.html',
//                    controller: 'reversalCtrl'
//                })
//                .when('/AdminAuthoriseReprocessing', {
//                    templateUrl: 'views/admin_authorize_processing.html',
//                    controller: 'reversalCtrl'
//                })
//                .when('/ReversalTransactions', {
//                    templateUrl: 'views/reversals.html',
//                    controller: 'reversalCtrl'
//                })
//
////            .when('/TMC', {
////                templateUrl: 'views/tmc.html',
////                controller: 'reversalCtrl'
////            })
//                .when('/Administrator', {
//                    templateUrl: 'views/administrator.html',
//                    controller: 'adminCtrl'
//                })
//                .when('/AppUserLogin', {
//                    templateUrl: 'views/appLogin.html',
//                    controller: 'adminCtrl'
//                })
//                .when('/AppUserLoginError', {
//                    templateUrl: 'views/appLoginError.html',
//                    controller: 'adminCtrl'
//                })
//                .when('/TopupSalesReport', {
//                    templateUrl: 'views/topupsalesReport.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/MomoDebitStatus', {
//                    templateUrl: 'views/MobileMoneyDebitChecker.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/UserManagement', {
//                    templateUrl: 'views/UserManagement.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/MomoStatusChecker', {
//                    templateUrl: 'views/MTNMomoStatusChecker.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/ICGCTransactions', {
//                    templateUrl: 'views/icgcpayments.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/GCBMerchantPay', {
//                    templateUrl: 'views/gcbmerchantpay.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/CardHotlist', {
//                    templateUrl: 'views/hotlistcard.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/CardDehostlist', {
//                    templateUrl: 'views/dehotlistcard.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/AuthorizeCMS', {
//                    templateUrl: 'views/authorizecms.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/AuthorizedCMS', {
//                    templateUrl: 'views/authorizedcms.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/ETRANZACT_MTN_Report', {
//                    templateUrl: 'views/mtnReport.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//
//                .when('/VerificationPortal', {
//                    templateUrl: 'views/verification.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/VodafoneReport', {
//                    templateUrl: 'views/vodafoneReport.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/FeePayments', {
//                    templateUrl: 'views/feepayment.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/testReport', {
//                    templateUrl: 'views/testReport.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/ScbReport', {
//                    templateUrl: 'views/justpayReport.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/EtzReport', {
//                    templateUrl: 'views/justpayReport.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/CorporatePayReport', {
//                    templateUrl: 'views/CorporatePay.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/GcbOnBoarding', {
//                    templateUrl: 'views/gcbOnBoarding.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/MerchantPay', {
//                    templateUrl: 'views/merchantPay.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/GipInvestigation', {
//                    templateUrl: 'views/gipInvestigation.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/GcbMtnMomo', {
//                    templateUrl: 'views/gcbmtnmomoAcct.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/MtnOvaUpload', {
//                    templateUrl: 'views/mtnOvaUpload.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/MobileAppManagement', {
//                    templateUrl: 'views/mobileAppActivate.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/FeeCollection', {
//                    templateUrl: 'views/feeCollection.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/FeeCollectionReversals', {
//                    templateUrl: 'views/feeCollectionRev.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/TeraRefund', {
//                    templateUrl: 'views/teraRefund.html',
//                    controller: 'fgTransactionsCtrl'
//                })
//                .when('/MomoCardRequests', {
//                    templateUrl: 'views/momocardrequests.html',
//                    controller: 'fgTransactionsCtrl'
//                })
////            .when('/KU_Leuven', {
////                templateUrl: 'views/cardTransactionReport.html',
////                controller: 'fgTransactionsCtrl'
////            })
//
//
//                .otherwise({
//                    redirectTo: '/'
//                });
//    }]).factory('ajaxNonceInterceptor', function () {
//    // This interceptor is equivalent to the behavior induced by $.ajaxSetup({cache:false});
//
//    var param_start = /\?/;
//
//    return {
//        request: function (config) {
//            if (config.method === 'GET' || config.method === "POST") {
//                // Add a query parameter named '_' to the URL, with a value equal to the current timestamp
//                config.url += (param_start.test(config.url) ? "&" : "?") + '_=' + new Date().getTime();
//            }
//            return config;
//        }
//    };
//});
//app.controller('userProfileCtrl', function ($scope, $http) {
//
//});
//app.controller('supportCtrl', function ($scope, $http, $httpParamSerializerJQLike) {
//
//    $scope.getProfile = function () {
//        $http.get('./UserProfile').then(
//                function (response) {
//                    if (response.data) {
//                        $scope.profile = response.data;
//                    } else {
//                        window.location = './login';
//                    }
//                }
//        );
//    };
//
//    $(".load-icon").removeClass("fa-check");
//    $(".load-icon").addClass("fa-cog fa-spin");
//    $scope.pwdLoader = false;
//    $scope.pwdUrl = "./chgpwd";
//    $scope.pwdUrl2 = "./chgpwdnew";
//    $scope.changePassword = function () {
//        $scope.getProfile();
//        $scope.pwdLoader = true;
//        $http({
//            url: $scope.pwdUrl,
//            method: 'POST',
//            data: $httpParamSerializerJQLike({
//                "oldPwd": $scope.oldPwd,
//                "newPwd": $scope.newPwd,
//                "confPwd": $scope.confPwd,
//                "email": $scope.email
//            }),
//            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//        }).then(function success(response) {
//            var rsp = response.data;
//
//            if (rsp.trim() === "success") {
//                $(".load-icon").addClass("fa-check");
//                $(".load-icon").removeClass("fa-cog fa-spin");
//                $(".rsp").html("<span class='text-success'>Password change successful<span> <br><a href='./login' class='text-primary font-weight-bold'>Continue</a> ");
//            } else {
//                $(".load-icon").removeClass("fa-check");
//                $(".load-icon").addClass("fa-cog fa-spin");
//                $(".rsp").html("<span class='text-danger'>" + rsp + "</span>");
//            }
//        }, function error(response) {
//            $(".load-icon").removeClass("fa-check");
//            $(".load-icon").addClass("fa-cog fa-spin");
//            $(".rsp").html("<span class='text-danger'>An error occured. Please try again</span>");
//        });
//    };
//
//    $scope.changePasswordNew = function () {
//        $scope.getProfile();
//        $scope.pwdLoader = true;
//        $http({
//            url: $scope.pwdUrl2,
//            method: 'POST',
//            data: $httpParamSerializerJQLike({
//                "oldPwd": $scope.oldPwd,
//                "newPwd": $scope.newPwd,
//                "confPwd": $scope.confPwd,
//                "email": $scope.email
//
//            }),
//            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//        }).then(function success(response) {
//            var rsp = response.data;
//
//            if (rsp.trim() === "success") {
//                $(".load-icon").addClass("fa-check");
//                $(".load-icon").removeClass("fa-cog fa-spin");
//                $(".rsp").html("<span class='text-success'>Password change successful<span> <br><a href='./login' class='text-primary font-weight-bold'>Continue</a> ");
//            } else {
//                $(".load-icon").removeClass("fa-check");
//                $(".load-icon").addClass("fa-cog fa-spin");
//                $(".rsp").html("<span class='text-danger'>" + rsp + "</span>");
//            }
//        }, function error(response) {
//            $(".load-icon").removeClass("fa-check");
//            $(".load-icon").addClass("fa-cog fa-spin");
//            $(".rsp").html("<span class='text-danger'>An error occured. Please try again</span>");
//        });
//    };
//
//    $scope.fetchUserEmail = function () {
//        $scope.email = $scope.profile.email;
//    };
//
//    $scope.hideFilterBox = function () {
//        $scope.pwdLoader = false;
//    };
//
//
//});
//
//app.controller('reprocessCtrl', function ($scope, $http, $httpParamSerializerJQLike) {
//
//    $scope.getProfile = function () {
//        $http.get('./UserProfile').then(
//                function (response) {
//                    if (response.data) {
//                        $scope.profile = response.data;
//                    } else {
//                        window.location = './login';
//                    }
//                }
//        );
//    };
//
//    $scope.initDates = function () {
//        var startOfDay = moment().format('YYYY-MM-DD 00:00');
//        var currentDateTime = moment().format('YYYY-MM-DD 23:59');
//        $('#start-date').datetimepicker({
//            format: 'Y-m-d H:i',
//            value: startOfDay
//        });
//        $scope.startDate = startOfDay;
//        $scope.endDate = currentDateTime;
//
//        $('#start-date-btn').on('click', function () {
//            $('#start-date').datetimepicker('show');
//        });
//
//        $('#end-date').datetimepicker({
//            format: 'Y-m-d H:i',
//            value: moment().format('Y-m-d H:i')
//        });
//        $('#end-date-btn').on('click', function () {
//            $('#end-date').datetimepicker('show');
//        });
//
//    };
//
//    $scope.getProfile();
//    $scope.initDates();
//
//    $scope.updateApp = function () {
//        $scope.getProfile();
//    };
//
//
//    $(".load-icon").removeClass("fa-check");
//    $(".load-icon").addClass("fa-cog fa-spin");
//    $scope.erequestlogProxy = "./etz/proxy/2018/erequestProxy";
//    $scope.reprocessProxy = "./etz/proxy/2018/reprocessProxy";
////    $scope.rvslProxy = "./etz/proxy/2018/rvsl";
//    $scope.tmcProxy = "./etz/proxy/2018/tmcProxy";
//    $scope.showReProcessBox = false;
//    $scope.showTMCView = false;
//    $scope.destination = '000';
//    $scope.origin = '25';
//    $scope.appname = 'ALL';
//    $scope.transType = 'AIRTIME';
//    $scope.rowsPerPage = '100';
//    $scope.srcAccount = '';
//    $scope.destAccount = '';
//    $scope.showDetailsBox = false;
//    $scope.reprocessResp = 'initiate reprocess';
//
//
//    $scope.reloadReprocessTrxns = function () {
//        $scope.endDate = moment().format('YYYY-MM-DD 23:59');
//        $scope.getReprocessTrxns(1);
//    };
//
//    $scope.getReprocessTrxns = function (page, paging) {
//
//        $scope.updateApp();
//        if ($('#start-date').val() && $('#end-date').val()) {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.reprocessProxy;
//            if (paging) {
//                url = './FailedReprocessFunc?action=paging';
//            }
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.erequestRecordsLastPage)
//                $scope.pageNumber = $scope.erequestRecordsLastPage;
//
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "startDate": $scope.startDate,
//                    "endDate": $scope.endDate,
//                    "trans_data": $scope.trans_data,
//                    "transtype": $scope.transType,
//                    "source": $scope.srcAccount,
//                    "destination": $scope.destAccount,
//                    "searchKey": $scope.searchTrxnKey,
//                    "service": "transactions",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.erequestTrxResp = response.data;
//                if ($scope.erequestTrxResp.code === "00") {
//                    if (paging) {
//                        $scope.erequestTrxs = JSON.parse($scope.erequestTrxResp.data);
//                        $scope.getReprocessTrxnsCount();
//                    } else {
//                        $scope.erequestTrxs = JSON.parse($scope.erequestTrxResp.data);
//                        $scope.getReprocessTrxnsCount();
////                        $scope.getE_RequestLogTransactionsTotalAmount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Sever Error. Try again');
//            });
//        } else {
//            alert('Select values for start and end date');
//        }
//    };
//
//    $scope.getReprocessTrxnsCount = function () {
//        $http.get('./FailedReprocessFunc?action=count').then(
//                function (response) {
//                    $scope.erequestRecordsTotalCount = JSON.parse(response.data);
//                    $scope.erequestRecordsLastPage = $scope.erequestRecordsTotalCount / $scope.rowsPerPage;
//                    if ($scope.erequestRecordsLastPage > parseInt($scope.erequestRecordsTotalCount / $scope.rowsPerPage)) {
//                        $scope.erequestRecordsLastPage = parseInt($scope.erequestRecordsTotalCount / $scope.rowsPerPage) + 1;
//                    }
//                }
//        );
//    };
//
////    $scope.checkInitiateNum = function () {
////        var selectedCount = 0;
////        for (var k = 0; k < $scope.erequestTrxs.length; k++)
////        {
////            if ($scope.erequestTrxs[k].selected === true)
////            {
////                selectedCount += 1;
////            }
////
////        }
////        if (selectedCount > 0) {
////            $('#InitiateteBtn').prop("disabled", false);
////        } else {
////            $('#InitiateteBtn').prop("disabled", true);
////        }
////
////    };
//
//    $scope.checkAuthorizeNum = function () {
//        var selectedCount = 0;
//        for (var k = 0; k < $scope.reproTrxs.length; k++)
//        {
//            if ($scope.reproTrxs[k].selected === true)
//            {
//                selectedCount += 1;
//            }
//
//        }
//        if (selectedCount > 0) {
//
//            $('#AuthorizeBtn').prop("disabled", false);
//        } else {
//            $('#AuthorizeBtn').prop("disabled", true);
//        }
//
//    };
//
//    $scope.confirmationDialogConfig = {};
//
//    $scope.confirmationDialog = function () {
//
//        switch ($scope.profile.role_id) {
//
//            case "3":
//                $scope.confirmationDialogConfig = {
//                    title: "CONFIRM INITIATION",
//                    message: "Are you sure you want initiate?",
//                    buttons: [{
//                            label: "Initiate",
//                            action: "initiateReprocess"
//                        }]
//                };
//                break;
//            case "1":
//            case "5":
//                $scope.confirmationDialogConfig = {
//                    title: "CONFIRM AUTHORIZATION",
//                    message: "Are you sure you want authorize?",
//                    buttons: [{
//                            label: "Authorize",
//                            action: "authorizeReprocess"
//                        }]
//                };
//                break;
//            default:
//                break;
//        }
//        $scope.showDialog(true);
//    };
//
//    $scope.executeDialogAction = function (action) {
//        if (typeof $scope[action] === "function") {
//            $scope[action]();
//        }
//    };
//
//
//    $scope.showDialog = function (flag) {
//        jQuery("#confirmation-dialog .modal").modal(flag ? 'show' : 'hide');
//    };
//
////    $scope.initiateReprocess = function () {
////        $scope.updateApp();
////        $scope.showDialog();
////        $scope.referencesArray = "";
////        $scope.transactions = "";
////        for (var k = 0; k < $scope.erequestTrxs.length; k++)
////        {
////            if ($scope.erequestTrxs[k].selected === true)
////            {
////                $scope.referencesArray += $scope.erequestTrxs[k].unique_transid + "_"
////                        + $scope.erequestTrxs[k].provider + "_" + $scope.erequestTrxs[k].amount
////                        + "_" + $scope.erequestTrxs[k].dest_account + "_" + $scope.erequestTrxs[k].trans_type + ",";
////            }
////        }
////        $scope.transactions = $scope.referencesArray.slice(0, -1);
////        console.log($scope.transactions);
////
////        if ($scope.transactions.length > 1) {
////            $scope.showLoader = true;
////            $scope.showFilterBox = false;
////            $('.reload').addClass('fa-spin text-info');
////            var url = $scope.reprocessProxy;
////
////            $http({
////                url: url,
////                method: 'POST',
////                data: $httpParamSerializerJQLike({
////
////                    "references": $scope.transactions,
////                    "service": "reprocess"
////                }),
////                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
////            }).then(function success(response) {
////                $scope.showLoader = false;
////                $('.reload').removeClass('fa-spin text-info');
////                $scope.erequestTrxResp = response.data;
////                if ($scope.erequestTrxResp.code === "00") {
////
////                    $scope.erequestTrxs = JSON.parse($scope.erequestTrxResp.data);
////                    $scope.getE_RequestLogTransactionsCount();
////
////                } else {
////
////                }
////            }, function error(response) {
////                $scope.showLoader = false;
////                $('.reload').removeClass('fa-spin text-info');
////                alert('Connection or Sever Error. Try again');
////            });
////        } else {
////            alert('No Transaaction Selected');
////        }
////
////    };
//
//    $scope.cancelInitiate = function () {
//        $scope.ref = "";
//        $scope.transId = "";
//        $scope.verifyDetails = "";
//        $scope.verifyDest = "";
//        $scope.showDetailsBox = false;
//    };
//    $scope.trimShort = function (text) {
//        if (text) {
//            if (text.length > 40) {
//                return (text.substring(0, 38)) + " ...";
//            }
//        }
//        return text;
//    };
//    $scope.initiateReprocess = function (k) {
//        $scope.updateApp();
////        $scope.referencesArray = "";
//
//        transaction = {};
//        $scope.showDetailsBox = false;
//        $('#authorize').addClass("bg-success");
//        $('#authorize').prop("disabled", false);
//        $('#authorize').removeClass("bg-danger");
//        $('#authorize').html("INITIATE");
//        $('#vst' + k).html("<i class='fa fa-spinner fa-spin btn-loader'></i> PROCESSING");
//        $('#vst' + k).prop('title', '');
//        $scope.recordId = k;
//        $scope.ref = $scope.erequestTrxs[k].unique_transid;
//        $scope.verifyDest = $scope.erequestTrxs[k].dest_account;
//        transaction["unique_transid"] = $scope.erequestTrxs[k].unique_transid;
//        transaction["provider"] = $scope.erequestTrxs[k].provider;
//        transaction["amount"] = $scope.erequestTrxs[k].amount.toString();
//        transaction["dest_account"] = $scope.erequestTrxs[k].dest_account;
//        transaction["transtype"] = $scope.erequestTrxs[k].transtype;
//        transaction["otherinfo"] = $scope.erequestTrxs[k].otherinfo;
//
//
//        console.log(transaction);
//
//        if (Object.keys(transaction).length > 1) {
////            $scope.showLoader = true;
////            $scope.showFilterBox = false;
////            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.reprocessProxy;
//
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//
//                    "references": JSON.stringify(transaction),
//                    "service": "reprocess"
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $scope.erequestTrxResp = response.data;
////                alert($scope.erequestTrxResp.data);
//                switch ($scope.erequestTrxResp.data.split(":")[0]) {
//                    case "INITIATED":
//                        $('#vst' + k).addClass("bg-success");
//                        $('#vst' + k).removeClass("bg-danger");
//                        $('#vst' + k).html("INITIATED");
//                        $('#vst' + k).prop("disabled", true);
//                        break;
//                    case "SUCCESS":
//                        $('#vst' + k).addClass("bg-success");
//                        $('#vst' + k).removeClass("bg-danger");
//                        $('#vst' + k).html("SUCCESS");
//                        $('#vst' + k).prop("disabled", true);
//                        break;
//                    case "DUPLICATE":
//                        $('#vst' + k).addClass("bg-danger");
//                        $('#vst' + k).removeClass("bg-success");
//                        $('#vst' + k).html("DUPLICATE");
//                        $('#vst' + k).prop("disabled", true);
//                        break;
//                    case "ERROR":
//                        $('#vst' + k).addClass("bg-danger");
//                        $('#vst' + k).removeClass("bg-success");
//                        $('#vst' + k).html("ERROR");
//                        if ($scope.erequestTrxResp.data.split(":").length > 1) {
//                            $('#vst' + k).prop('title', $scope.erequestTrxResp.data.substring($scope.erequestTrxResp.data.indexOf(":") + 1));
//                        }
//                        break;
//                    case "VERIFY":
//                        $('#authorize').prop("disabled", false);
//                        $('#vst' + k).html("INITIATE");
//                        $scope.verifyDetails = $scope.erequestTrxResp.data.substring($scope.erequestTrxResp.data.indexOf(":") + 1);
//                        $scope.showDetailsBox = true;
////                        $('#vst' + k).addClass("bg-warning");
//                        $('#vst' + k).removeClass("bg-success");
//                        $('#vst' + k).removeClass("bg-danger");
////                        $('#vst' + k).html("CHECKING");
//                        break;
//                    default:
//                        $('#vst' + k).addClass("bg-danger");
//                        $('#vst' + k).removeClass("bg-success");
//                        $('#vst' + k).html("ERROR");
//                        break;
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
////                alert(response);
//                $('#vst' + k).addClass("bg-danger");
//                $('#vst' + k).removeClass("bg-success");
//                $('#vst' + k).html("ERROR");
//            });
//        } else {
//            alert('No Transaaction Selected');
//        }
//
//    };
//    $scope.continueInitiateReprocess = function () {
//        $scope.updateApp();
////        $scope.referencesArray = "";
//
//        console.log(transaction);
//
//        if (Object.keys(transaction).length > 1) {
////            $scope.showLoader = true;
////            $scope.showFilterBox = false;
////            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.reprocessProxy;
//
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//
//                    "references": JSON.stringify(transaction),
//                    "service": "continuereprocess"
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $scope.erequestTrxResp = response.data;
////                alert($scope.erequestTrxResp.data);
//                switch ($scope.erequestTrxResp.data.split(":")[0]) {
//                    case "INITIATED":
//                        $('#vst' + $scope.recordId).addClass("bg-success");
//                        $('#vst' + $scope.recordId).removeClass("bg-danger");
//                        $('#vst' + $scope.recordId).html("INITIATED");
//                        $('#vst' + $scope.recordId).prop("disabled", true);
//                        $('#authorize').addClass("bg-success");
//                        $('#authorize').prop("disabled", true);
//                        $('#authorize').removeClass("bg-danger");
//                        $('#authorize').html("INITIATED");
//                        break;
//                    case "DUPLICATE":
//                        $('#vst' + $scope.recordId).addClass("bg-danger");
//                        $('#vst' + $scope.recordId).removeClass("bg-success");
//                        $('#vst' + $scope.recordId).html("DUPLICATE");
//                        $('#vst' + $scope.recordId).prop("disabled", true);
//                        $('#authorize').addClass("bg-danger");
//                        $('#authorize').prop("disabled", true);
//                        $('#authorize').removeClass("bg-success");
//                        $('#authorize').html("DUPLICATE");
//                        break;
//                    case "ERROR":
//                        $('#vst' + $scope.recordId).addClass("bg-danger");
//                        $('#vst' + $scope.recordId).removeClass("bg-success");
//                        $('#vst' + $scope.recordId).html("ERROR");
//                        $('#authorize').addClass("bg-danger");
//                        $('#authorize').removeClass("bg-success");
//                        $('#authorize').html("ERROR");
//                        $('#authorize').prop("disabled", true);
//                        break;
//                    default:
//                        $('#vst' + $scope.recordId).addClass("bg-danger");
//                        $('#vst' + $scope.recordId).removeClass("bg-success");
//                        $('#vst' + $scope.recordId).html("ERROR");
//                        break;
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
////                alert(response);
//                $('#vst' + $scope.recordId).addClass("bg-danger");
//                $('#vst' + $scope.recordId).removeClass("bg-success");
//                $('#vst' + $scope.recordId).html("ERROR");
//            });
////            setTimeout(() => {
////                $scope.$apply($scope.cancelInitiate());
////            }, 2000);
//            setTimeout(function () {
//                $scope.$apply($scope.cancelInitiate());
//            }, 2000);
//        } else {
//            alert('No Transaaction Selected');
//        }
//
//    };
//    $scope.authorizeReprocess = function (k, action) {
//        $scope.updateApp();
////        $scope.referencesArray = "";
//
//        transaction = {};
//        $scope.showDetailsBox = false;
////        $('#authorize').addClass("bg-success");
////        $('#authorize').prop("disabled", false);
////        $('#authorize').removeClass("bg-danger");
////        $('#authorize').html("AUTHORIZE");
//        $('#' + action + k).html("<i class='fa fa-spinner fa-spin btn-loader'></i>");
//        $('#' + action + k).prop('title', '');
//        $scope.recordId = k;
//        $scope.ref = $scope.reproTrxs[k].reference;
//        $scope.verifyDest = $scope.reproTrxs[k].account;
//        transaction["unique_transid"] = $scope.reproTrxs[k].reference;
//        transaction["provider"] = $scope.reproTrxs[k].product;
//        transaction["amount"] = $scope.reproTrxs[k].amount.toString();
//        transaction["dest_account"] = $scope.reproTrxs[k].account;
//        transaction["transtype"] = $scope.reproTrxs[k].trans_type;
//        transaction["otherinfo"] = $scope.reproTrxs[k].otherinfo;
//
//
//        console.log(transaction);
//
//        if (Object.keys(transaction).length > 1) {
////            $scope.showLoader = true;
////            $scope.showFilterBox = false;
////            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.reprocessProxy;
//
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "action": action,
//                    "references": JSON.stringify(transaction),
//                    "service": "reprocess"
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $scope.erequestTrxResp = response.data;
////                alert($scope.erequestTrxResp.data);
//                switch ($scope.erequestTrxResp.data.split(":")[0]) {
//                    case "SUCCESS":
//                        $('#' + action + k).addClass("bg-success");
//                        $('#' + action + k).removeClass("bg-danger");
//                        $('#' + action + k).html("<i class='fa fa-thumbs-up'></i>");
//                        $('#' + action + k).prop("disabled", true);
//                        break;
//                    case "DENIED":
//                        $('#' + action + k).addClass("bg-success");
//                        $('#' + action + k).removeClass("bg-danger");
//                        $('#' + action + k).html("<i class='fa fa-thumbs-up'></i>");
//                        $('#' + action + k).prop("disabled", true);
//                        break;
//                    case "ERROR":
//                        $('#' + action + k).addClass("bg-danger");
//                        $('#' + action + k).removeClass("bg-success");
//                        $('#' + action + k).html("<i class='fa fa-thumbs-down'></i>");
//                        if ($scope.erequestTrxResp.data.split(":").length > 1) {
//                            $('#' + action + k).prop('title', $scope.erequestTrxResp.data.substring($scope.erequestTrxResp.data.indexOf(":") + 1));
//                        }
//                        break;
//                    default:
//                        $('#' + action + k).addClass("bg-danger");
//                        $('#' + action + k).removeClass("bg-success");
//                        $('#' + action + k).html("ERROR");
//                        $('#' + action + k).prop('title', 'AN ERROR OCCURED');
//                        break;
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
////                alert(response);
//                $('#' + action + k).addClass("bg-danger");
//                $('#' + action + k).removeClass("bg-success");
//                $('#' + action + k).html("ERROR");
//                $('#' + action + k).prop('title', 'SERVER ERROR');
//            });
//        } else {
//            alert('No Transaaction Selected');
//        }
//
//    };
//    $scope.getReproTrxns = function (page, paging) {
//        $scope.updateApp();
//        if ($('#start-date').val() && $('#end-date').val()) {
//
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.reprocessProxy;
//            if (paging) {
//                url = './reprocessTransFunction?action=paging';
//            }
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.erequestRecordsLastPage)
//                $scope.pageNumber = $scope.erequestRecordsLastPage;
//
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "startDate": $scope.startDate,
//                    "endDate": $scope.endDate,
//                    "trans_data": $scope.trans_data,
//                    "account": $scope.account,
//                    "transtype": $scope.transType,
//                    "appname": $scope.appname,
//                    "service": "transactions",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.reproTrxnResp = response.data;
//                if ($scope.reproTrxnResp.code === "00") {
//                    if (paging) {
//                        $scope.reproTrxs = JSON.parse($scope.reproTrxnResp.data);
//                        $scope.getReproTrxnsCount();
//                    } else {
//                        $scope.reproTrxs = JSON.parse($scope.reproTrxnResp.data);
//                        $scope.getReproTrxnsCount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Sever Error. Try again');
//            });
//        } else {
//            alert('Select values for start and end date');
//        }
//    };
//    $scope.getReproTrxnsCount = function () {
//        $http.get('./LoggedReprocessFunc?action=count').then(
//                function (response) {
//                    $scope.erequestRecordsTotalCount = JSON.parse(response.data);
//                    $scope.erequestRecordsLastPage = $scope.erequestRecordsTotalCount / $scope.rowsPerPage;
//                    if ($scope.erequestRecordsLastPage > parseInt($scope.erequestRecordsTotalCount / $scope.rowsPerPage)) {
//                        $scope.erequestRecordsLastPage = parseInt($scope.erequestRecordsTotalCount / $scope.rowsPerPage) + 1;
//                    }
//                }
//        );
//    };
//
//    $scope.getReprocessedTrxns = function (page, paging) {
//        $scope.updateApp();
//        if ($('#start-date').val() && $('#end-date').val()) {
//
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.reprocessProxy;
//            if (paging) {
//                url = './reprocessTransFunction?action=paging';
//            }
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.erequestRecordsLastPage)
//                $scope.pageNumber = $scope.erequestRecordsLastPage;
//
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "startDate": $scope.startDate,
//                    "endDate": $scope.endDate,
//                    "trans_data": $scope.trans_data,
//                    "account": $scope.account,
//                    "transtype": $scope.transType,
//                    "appname": $scope.appname,
//                    "service": "reprocessedtrans",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.reproTrxnResp = response.data;
//                if ($scope.reproTrxnResp.code === "00") {
//                    if (paging) {
//                        $scope.reproTrxs = JSON.parse($scope.reproTrxnResp.data);
//                        $scope.getReproTrxnsCount();
//                    } else {
//                        $scope.reproTrxs = JSON.parse($scope.reproTrxnResp.data);
//                        $scope.getReproTrxnsCount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Sever Error. Try again');
//            });
//        } else {
//            alert('Select values for start and end date');
//        }
//    };
//
//    $scope.searchTMCTrxn = function (id, reference) {
//
//        buttonId = '#' + id;
//        $(buttonId).prop("disabled", true);
//        $(buttonId).html("<i class='fa fa-spinner fa-spin btn-loader'></i> ...");
//        $scope.pageNumber = 0;
//        $('.reload').addClass('fa-spin text-info');
//        if ($scope.pageNumber < 1)
//            $scope.pageNumber = 1;
//        if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.TmcRecordsLastPage)
//            $scope.pageNumber = $scope.TmcRecordsLastPage;
//        $http({
//            url: $scope.tmcProxy,
//            method: 'POST',
//            data: $httpParamSerializerJQLike({
//                "searchKey": reference,
//                "service": "searchReversal",
//                "pageNumber": $scope.pageNumber,
//                "rowsPerPage": "100"
//            }),
//            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//        }).then(function success(response) {
//            $scope.showLoader = false;
//            $(buttonId).prop('disabled', false);
//            $(buttonId).html('VERIFY');
//            $scope.showTMCView = true;
//            $scope.tmcTrxResp = response.data;
//            $('.reload').removeClass('fa-spin text-info');
//            if ($scope.tmcTrxResp.code === "00") {
//
//                $scope.tmcTrxs = JSON.parse($scope.tmcTrxResp.data);
//            } else if ($scope.tmcTrxResp.code === "01") {
//                $scope.tmcTrxs = "";
//            } else {
//                $scope.tmcTrxs = "";
//            }
//        }, function error(response) {
//            $scope.showTMCView = true;
//            $('.reload').removeClass('fa-spin text-info');
//            alert('Connection or Sever Error. Try again');
//        });
//
//    };
//    $scope.reverse = function (id, transid, type) {
//        buttonId = '#' + id;
//        $(buttonId).prop("disabled", true);
//        if (type === "ini") {
//            $(buttonId).html("<i class='fa fa-spinner fa-spin btn-loader'></i> Saving...");
//        } else if (type === "auth") {
//            $(buttonId).html("<i class='fa fa-spinner fa-spin btn-loader'></i> Reprocessing...");
//        }
//        $scope.updateApp();
//
//        var url = $scope.rvslProxy;
//
//        $http({
//            url: url,
//            method: 'POST',
//            data: $httpParamSerializerJQLike({
//                "id": id,
//                "transid": transid
//            }),
//            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//        }).then(function success(response) {
//            $scope.showLoader = false;
//            if (type === "ini") {
//                if (response.data === "success") {
//                    $(buttonId).removeClass("btn-primary");
//                    $(buttonId).addClass('btn-success');
//                    $(buttonId).html('SUCCESS, AWAITING AUTHORIZATION');
//                    $(buttonId).prop('disabled', true);
//                } else {
//                    alert(response.data);
//                    $(buttonId).prop('disabled', false);
//                    $(buttonId).html('TRY AGAIN');
//                }
//            } else if (type === "auth") {
//                $(buttonId).removeClass("btn-primary");
//                $(buttonId).addClass('btn-success');
//                $(buttonId).html(response.data);
//                $(buttonId + "_1").removeClass("hidden");
//            }
//        }, function error(response) {
//            $scope.showLoader = false;
//            alert('Connection or Sever Error. Try again');
//        });
//    };
//    $scope.hideFilterBox = function () {
//        $scope.showTrxFilterBox = false;
//        $scope.showFilterBox = false;
//    };
//    $scope.showFilterBoxView = function () {
//        $scope.showTrxFilterBox = true;
//        $scope.showFilterBox = true;
//    };
//    $scope.toggleFilterBoxView = function () {
//        $scope.showFilterBox = !$scope.showFilterBox;
//    };
//    $scope.toggleTMCView = function () {
//        $scope.showTMCView = !$scope.showTMCView;
//    };
//
//});
//app.controller('reversalCtrl', function ($scope, $http, $httpParamSerializerJQLike) {
//
//    $scope.getProfile = function () {
//        $http.get('./UserProfile').then(
//                function (response) {
//                    if (response.data) {
//                        $scope.profile = response.data;
//                    } else {
//                        window.location = './login';
//                    }
//                }
//        );
//    };
//
//    $scope.merchant = "";
//    $scope.bank = "";
//
//    $scope.initInfo = function () {
//        $http.get('./MerchantsInfo?action=fundgate').then(
//                function (response) {
//                    $scope.merchantList = response.data;
//                }
//        );
//
//        $http.get('./MerchantsInfo?action=vasgate').then(
//                function (response) {
//                    $scope.vasgateMerchantList = response.data;
//                }
//        );
//
//        $http.get('./MerchantsInfo?action=banks').then(
//                function (response) {
//                    $scope.banksList = response.data;
//                }
//        );
//
//        $http.get('./MerchantsInfo?action=channels').then(
//                function (response) {
//                    $scope.channelsList = response.data;
//                }
//        );
//
//        $http.get('./MerchantsInfo?action=wcMerchants').then(
//                function (response) {
//                    $scope.wcMerchantList = response.data;
//                }
//        );
//    };
//
//    $scope.initDates = function () {
//        var startOfDay = moment().format('2018-04-01 00:00');
//        var currentDateTime = moment().format('YYYY-MM-DD 23:59');
//        $('#start-date').datetimepicker({
//            format: 'Y-m-d H:i',
//            value: startOfDay
//        });
//        $scope.startDate = startOfDay;
//        $scope.endDate = currentDateTime;
//
//        $('#start-date-btn').on('click', function () {
//            $('#start-date').datetimepicker('show');
//        });
//
//        $('#end-date').datetimepicker({
//            format: 'Y-m-d H:i',
//            value: moment().format('Y-m-d H:i')
//        });
//        $('#end-date-btn').on('click', function () {
//            $('#end-date').datetimepicker('show');
//        });
//
//    };
//
//    $scope.getProfile();
//    $scope.initDates();
//
//    $scope.updateApp = function () {
//        $scope.getProfile();
//    };
//
//    $scope.testE_RequestLogtrans = function () {
//        var i = $scope.profile.type_id;
//        if (i === "1" || i === "7" || i === "8" || i === "22" || i === "44") {
//            return true;
//        }
//        return false;
//    };
//
//    $(".load-icon").removeClass("fa-check");
//    $(".load-icon").addClass("fa-cog fa-spin");
//    $scope.erequestlogProxy = "./etz/proxy/2018/erequestProxy";
//    $scope.rvslProxy = "./etz/proxy/2018/rvsl";
//    $scope.tmcProxy = "./etz/proxy/2018/tmcProxy";
//    $scope.showReProcessBox = false;
//    $scope.showTMCView = false;
//    $scope.destination = '000';
//    $scope.origin = '25';
//    $scope.appname = 'ALL';
//
//    $scope.reloadReversalTrxns = function () {
//        $scope.endDate = moment().format('YYYY-MM-DD 23:59');
//        $scope.getReversalTrxns(1);
//    };
//
//    $scope.reloadAdminReversalTrxns = function () {
//        $scope.endDate = moment().format('YYYY-MM-DD 23:59');
//        $scope.getAdminReversalTrxns(1);
//    };
//
//
//    $scope.getReversalTrxns = function (page, paging) {
//        $scope.updateApp();
//        if ($('#start-date').val() && $('#end-date').val()) {
//
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.erequestlogProxy;
//            if (paging) {
//                url = './e_RequestLogTransFunction?action=paging';
//            }
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.erequestRecordsLastPage)
//                $scope.pageNumber = $scope.erequestRecordsLastPage;
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "startDate": $scope.startDate,
//                    "endDate": $scope.endDate,
//                    "trans_data": $scope.trans_data,
//                    "appname": $scope.appname,
//                    "service": "transactions",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.erequestTrxResp = response.data;
//                if ($scope.erequestTrxResp.code === "00") {
//                    if (paging) {
//                        $scope.erequestTrxs = JSON.parse($scope.erequestTrxResp.data);
//                        $scope.getE_RequestLogTransactionsCount();
//                    } else {
//                        $scope.erequestTrxs = JSON.parse($scope.erequestTrxResp.data);
//                        $scope.getE_RequestLogTransactionsCount();
//                        $scope.getE_RequestLogTransactionsTotalAmount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Sever Error. Try again');
//            });
//        } else {
//            alert('Select values for start and end date');
//        }
//    };
//    $scope.getAdminReversalTrxns = function (page, paging) {
////        alert("hi");
//        $scope.updateApp();
//        if ($('#start-date').val() && $('#end-date').val()) {
////            alert("hi");
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.erequestlogProxy;
//            if (paging) {
//                url = './e_RequestLogTransFunction?action=paging';
//            }
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.erequestRecordsLastPage)
//                $scope.pageNumber = $scope.erequestRecordsLastPage;
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "startDate": $scope.startDate,
//                    "endDate": $scope.endDate,
//                    "trans_data": $scope.trans_data,
//                    "service": "authtransactions",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.erequestTrxResp = response.data;
//                if ($scope.erequestTrxResp.code === "00") {
//                    if (paging) {
//                        $scope.erequestTrxs = JSON.parse($scope.erequestTrxResp.data);
//                        $scope.getE_RequestLogTransactionsCount();
//                    } else {
//                        $scope.erequestTrxs = JSON.parse($scope.erequestTrxResp.data);
//                        $scope.getE_RequestLogTransactionsCount();
//                        $scope.getE_RequestLogTransactionsTotalAmount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Sever Error. Try again');
//            });
//        } else {
//            alert('Select values for start and end date');
//        }
//    };
//
//
//    $scope.searchE_RequestLogTrxn = function () {
//        if ($scope.searchTrxnKey.length > 12) {
//            $scope.showLoader = true;
//            $('.reload').addClass('fa-spin text-info');
//            $http({
//                url: $scope.erequestProxy,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "searchKey": $scope.searchTrxnKey,
//                    "service": "search"
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.erequestTrxResp = response.data;
//                if ($scope.erequestTrxResp.code === "00") {
//                    $scope.erequestTrxs = JSON.parse($scope.erequestTrxResp.data);
//                    $scope.getE_RequestLogTransactionsCount();
//                } else if ($scope.erequestTrxResp.code === "01") {
//                    $scope.erequestTrxs = "";
//                } else {
//                    $scope.erequestTrxs = "";
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Sever Error. Try again');
//            });
//        } else {
//            alert("Length of Transaction ID must be greater than 12");
//        }
//    };
//    $scope.getE_RequestLogTransactionsCount = function () {
//        $http.get('./e_RequestLogTransFunction?action=count').then(
//                function (response) {
//                    $scope.erequestRecordsTotalCount = JSON.parse(response.data);
//                    $scope.erequestRecordsLastPage = $scope.erequestRecordsTotalCount / $scope.rowsPerPage;
//                    if ($scope.erequestRecordsLastPage > parseInt($scope.erequestRecordsTotalCount / $scope.rowsPerPage)) {
//                        $scope.erequestRecordsLastPage = parseInt($scope.erequestRecordsTotalCount / $scope.rowsPerPage) + 1;
//                    }
//                }
//        );
//    };
//    $scope.getE_RequestLogTransactionsTotalAmount = function () {
//        $http.get('./e_RequestLogTransFunction?action=totalAmount').then(
//                function (response) {
//                    $scope.erequestRecordsTotalAmount = response.data;
//                });
//    };
//
//    $scope.searchTMCTrxn = function (id, reference) {
//
//        buttonId = '#' + id;
//        $(buttonId).prop("disabled", true);
//        $(buttonId).html("<i class='fa fa-spinner fa-spin btn-loader'></i> ...");
//        $scope.pageNumber = 0;
//        $('.reload').addClass('fa-spin text-info');
//        if ($scope.pageNumber < 1)
//            $scope.pageNumber = 1;
//        if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.TmcRecordsLastPage)
//            $scope.pageNumber = $scope.TmcRecordsLastPage;
//        $http({
//            url: $scope.tmcProxy,
//            method: 'POST',
//            data: $httpParamSerializerJQLike({
//                "searchKey": reference,
//                "service": "searchReversal",
//                "pageNumber": $scope.pageNumber,
//                "rowsPerPage": "100"
//            }),
//            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//        }).then(function success(response) {
//            $scope.showLoader = false;
//            $(buttonId).prop('disabled', false);
//            $(buttonId).html('VERIFY');
//            $scope.showTMCView = true;
//            $scope.tmcTrxResp = response.data;
//            $('.reload').removeClass('fa-spin text-info');
//            if ($scope.tmcTrxResp.code === "00") {
//
//                $scope.tmcTrxs = JSON.parse($scope.tmcTrxResp.data);
//            } else if ($scope.tmcTrxResp.code === "01") {
//                $scope.tmcTrxs = "";
//            } else {
//                $scope.tmcTrxs = "";
//            }
//        }, function error(response) {
//            $scope.showTMCView = true;
//            $('.reload').removeClass('fa-spin text-info');
//            alert('Connection or Sever Error. Try again');
//        });
//
//    };
//
//    $scope.reverse = function (id, transid, type) {
//        buttonId = '#' + id;
//        $(buttonId).prop("disabled", true);
//        if (type === "ini") {
//            $(buttonId).html("<i class='fa fa-spinner fa-spin btn-loader'></i> Saving...");
//        } else if (type === "auth") {
//            $(buttonId).html("<i class='fa fa-spinner fa-spin btn-loader'></i> Reversing...");
//        }
//        $scope.updateApp();
//
//        var url = $scope.rvslProxy;
//
//        $http({
//            url: url,
//            method: 'POST',
//            data: $httpParamSerializerJQLike({
//                "id": id,
//                "transid": transid
//            }),
//            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//        }).then(function success(response) {
//            $scope.showLoader = false;
//            if (type === "ini") {
//                if (response.data === "success") {
//                    $(buttonId).removeClass("btn-primary");
//                    $(buttonId).addClass('btn-success');
//                    $(buttonId).html('SUCCESS, AWAITING AUTHORIZATION');
//                    $(buttonId).prop('disabled', true);
//                } else {
//                    alert(response.data);
//                    $(buttonId).prop('disabled', false);
//                    $(buttonId).html('TRY AGAIN');
//                }
//            } else if (type === "auth") {
//                $(buttonId).removeClass("btn-primary");
//                $(buttonId).addClass('btn-success');
//                $(buttonId).html(response.data);
//                $(buttonId + "_1").removeClass("hidden");
//            }
//        }, function error(response) {
//            $scope.showLoader = false;
//            alert('Connection or Sever Error. Try again');
//        });
//    };
//
//    $scope.hideFilterBox = function () {
//        $scope.showTrxFilterBox = false;
//        $scope.showFilterBox = false;
//    };
//
//    $scope.showFilterBoxView = function () {
//        $scope.showTrxFilterBox = true;
//        $scope.showFilterBox = true;
//    };
//    $scope.toggleFilterBoxView = function () {
//        $scope.showFilterBox = !$scope.showFilterBox;
//    };
//
//
//    $scope.toggleTMCView = function () {
//        $scope.showTMCView = !$scope.showTMCView;
//    };
//
//    $scope.initInfo();
//});
//app.controller('investigationCtrl', function ($scope, $http) {
//    $scope.getProfile = function () {
//        $http.get('./UserProfile').then(
//                function (response) {
//                    if (response.data) {
//                        $scope.profile = response.data;
//                    } else {
//                        window.location = './login';
//                    }
//                }
//        );
//    };
//
//    $scope.showFilterBox = true;
//    $scope.transType = '';
//
//    $scope.initInfo = function () {
//
//    };
//
//    $scope.initDates = function () {
//        var startOfDay = moment().format('YYYY-MM-DD 00:00');
//        var currentDateTime = moment().format('YYYY-MM-DD 23:59');
//        $('#start-date').datetimepicker({
//            format: 'Y-m-d H:i',
//            value: startOfDay
//        });
//        $scope.startDate = startOfDay;
//        $scope.endDate = currentDateTime;
//
//        $('#start-date-btn').on('click', function () {
//            $('#start-date').datetimepicker('show');
//        });
//
//        $('#end-date').datetimepicker({
//            format: 'Y-m-d H:i',
//            value: moment().format('Y-m-d H:i')
//        });
//        $('#end-date-btn').on('click', function () {
//            $('#end-date').datetimepicker('show');
//        });
//
//    };
//
//    $scope.getProfile();
//    $scope.initDates();
//
//    $scope.updateApp = function () {
//        $scope.getProfile();
//    };
//
//    var Request = new Object();
//    $scope.centralInvestigation = function () {
//        if ($('#reference').val() || $('#account').val()) {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            Request.startDate = $scope.startDate;
//            Request.endDate = $scope.endDate;
//            Request.reference = $scope.reference;
//            Request.account = $scope.account;
////        Request.startDate = '2018-05-01 00:00:00';
////        Request.endDate = '2018-05-07 23:59:00';
////        Request.reference = '02JTSV44524B57793';
////        Request.account = '';
//            var json = JSON.stringify(Request);
//            console.log(json);
//            var url = 'http://localhost:8084/TRANSACTION_INVESTIGATION/api/etz/global/service/';
//            $http.post(url, json).then(
//                    function (response) {
//                        $scope.showLoader = false;
//                        $scope.response = response.data;
//                    }
//            );
//        } else {
//            alert("Enter value for either Reference or Mobile/Account Number");
//        }
//    };
//
//    $scope.hideFilterBox = function () {
//        $scope.showFilterBox = false;
//    };
//
//    $scope.toggleFilterBoxView = function () {
//        $scope.showFilterBox = !$scope.showFilterBox;
//    };
//
//    $scope.initInfo();
//});
//app.controller('adminCtrl', function ($scope, $http, $httpParamSerializerJQLike) {
//
//    $scope.role = "0";
//
//    $scope.initInfo = function () {
//        $http.get('./Administrator?action=roles').then(
//                function (response) {
//                    $scope.roles = response.data;
//                }
//        );
//    };
//
//    $scope.getProfile = function () {
//        $http.get('./UserProfile').then(
//                function (response) {
//                    if (response.data) {
//                        $scope.profile = response.data;
//                    } else {
//                        window.location = './login';
//                    }
//                }
//        );
//    };
//
//    $scope.updateApp = function () {
//        $scope.getProfile();
//    };
//
//    $(".load-icon").removeClass("fa-check");
//    $(".load-icon").addClass("fa-cog fa-spin");
//    $scope.pwdLoader = false;
//    $scope.admnUrl = "./Administrator";
//    $scope.showFilterBox = true;
//
//    $scope.createUser = function () {
//        $scope.updateApp();
//        $scope.pwdLoader = true;
//        $http({
//            url: $scope.admnUrl,
//            method: 'POST',
//            data: $httpParamSerializerJQLike({
//                "username": $scope.username,
//                "role": $scope.role,
//                "service": "create"
//            }),
//            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//        }).then(function success(response) {
//            var rsp = response.data;
//
//            if (rsp.trim() === "success") {
//                $(".load-icon").addClass("fa-check");
//                $(".load-icon").removeClass("fa-cog fa-spin");
//                $scope.clearForm();
//                $(".rsp").html("<span class='text-success'>Update successful<span> <br> ");
//            } else {
//                $(".load-icon").removeClass("fa-check");
//                $(".load-icon").addClass("fa-cog fa-spin");
//                $(".rsp").html("<span class='text-danger'>" + rsp + "</span>");
//            }
//        }, function error(response) {
//            $(".load-icon").removeClass("fa-check");
//            $(".load-icon").addClass("fa-cog fa-spin");
//            $(".rsp").html("<span class='text-danger'>An error occured. Please try again</span>");
//        });
//    };
//
//    $scope.appLogin = function () {
//        $scope.updateApp();
//        $scope.pwdLoader = true;
//        $http({
//            url: $scope.admnUrl,
//            method: 'POST',
//            data: $httpParamSerializerJQLike({
//                "username": $scope.username,
//                "password": $scope.password,
//                "service": "login"
//            }),
//            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//        }).then(function success(response) {
//            var rsp = response.data;
//
//            if (rsp.trim() === "success") {
//                $(".load-icon").addClass("fa-check");
//                $(".load-icon").removeClass("fa-cog fa-spin");
//                $scope.clearForm();
//                $(".rsp").html("<span class='text-success'>Account created successfully<span> <br><a href='#!/Administrator' class='text-primary font-weight-bold'>Continue</a> ");
//            } else {
//                $(".load-icon").removeClass("fa-check");
//                $(".load-icon").addClass("fa-cog fa-spin");
//                $(".rsp").html("<span class='text-danger'>" + rsp + "</span>");
//            }
//        }, function error(response) {
//            $(".load-icon").removeClass("fa-check");
//            $(".load-icon").addClass("fa-cog fa-spin");
//            $(".rsp").html("<span class='text-danger'>An error occured. Please try again</span>");
//        });
//    };
//
//    $scope.clearForm = function () {
//        $scope.name = "";
//        $scope.username = "";
//        $scope.password = "";
//    };
//
//    $scope.hidePwdLoader = function () {
//        $scope.pwdLoader = false;
//    };
//
//    $scope.hideFilterBox = function () {
//        $scope.showFilterBox = false;
//    };
//
//    $scope.showFilterBoxView = function () {
//        $scope.showFilterBox = true;
//    };
//
//    $scope.initInfo();
//});
//
//app.directive('ngFiles', ['$parse', function ($parse) {
//
//        function fn_link(scope, element, attrs) {
//            var onChange = $parse(attrs.ngFiles);
//            element.on('change', function (event) {
//                onChange(scope, {$files: event.target.files});
//            });
//        }
//
//        return {
//            link: fn_link
//        };
//    }]).controller('fgTransactionsCtrl', function ($scope, $parse, $http, $httpParamSerializerJQLike, $location, $timeout, $compile, Upload) {
////    $scope.profile = "";
////    $scope.TMCBankNodeList ="";
//    $(document).ready(function () {
//        function generatePDF() {
//            var documentDefinition = {
//                content:
//                        [
//                            {
//                                text: 'Etranzact Ghana', style: 'mainheader'
//                            },
//                            {
//                                table:
//                                        {
//                                            headerRows: 1,
//                                            widths: ['*', '*', '*', '*'],
//                                            body: [
//                                                [
//                                                    {text: 'Header 1', style: 'tableHeader'},
//                                                    {text: 'Header 2', style: 'tableHeader'},
//                                                    {text: 'Header 3', style: 'tableHeader'}
//                                                ],
//                                                [
//                                                    {text: 'Hello'},
//                                                    {text: 'I'},
//                                                    {text: 'am'}
//                                                ],
//                                                [
//                                                    {text: 'a'},
//                                                    {text: 'table'},
//                                                    {text: '.'}
//                                                ]
//                                            ]
//                                        }
//                            }
//                        ],
//                styles:
//                        {mainheader: {
//                                fontSize: 20,
//                                bold: true,
//                                margin: [0, 10, 0, 10],
//                                alignment: 'left'
//                            },
//                            header:
//                                    {
//                                        fontSize: 18,
//                                        bold: true,
//                                        margin: [0, 10, 0, 10],
//                                        alignment: 'center'
//                                    },
//                            tableHeader:
//                                    {
//                                        fillColor: '#4CAF50',
//                                        color: 'white'
//                                    }
//                        }
//            };
//
//            pdfMake.createPdf(documentDefinition).download('testdoc.pdf');
//        }
//
//        function KeyPress(e) {
//            var evtobj = window.event ? event : e;
////            if (evtobj.keyCode === 90 && evtobj.ctrlKey && evtobj.shiftKey) {
//////                $scope.goBack();
////                alert("HEY");
////            }
//            if (evtobj.keyCode === 76 && evtobj.ctrlKey && evtobj.shiftKey) {
//                $scope.logout();
//            }
//            if (evtobj.keyCode === 90 && evtobj.ctrlKey && evtobj.shiftKey) {
////                alert("hello");
//                $http.get('https://api.freebinchecker.com/bin/464898').then(
//                        function (response) {
//                            console.log(response.data);
//                        });
//            }
//
//        }
//
////        var userHost = document.location.host;
////        var hostname = '';
////        if (userHost === "localhost:8084" || userHost === "172.16.30.9:8092") {
////            hostname = '172.16.30.9';
////            var options = {
////                hostname: hostname,
////                path: '/etzRTC',
////                port: 9876
////            };
////
////// Initiate the connection to the server
////            var socket = socketCluster.create(options);
////
////            socket.on('connect', function () {
////                console.log('CONNECTED');
////            });
////
////// Listen to an event called 'rand' from the server
////            socket.on('rand', function (num) {
////                console.log('RANDOM: ' + num);
////                var curHTML = document.body.innerHTML;
////                curHTML += 'RANDOM: ' + num + '<br />';
////                document.body.innerHTML = curHTML;
////            });
////            socket.on('rand', function (num) {
////                console.log('RANDOM: ' + num);
////                var curHTML = document.body.innerHTML;
////                curHTML += 'RANDOM: ' + num + '<br />';
////                document.body.innerHTML = curHTML;
////            });
////            console.log(event.data);
////            var message = JSON.parse(event.data);
////            var toastr;
////            toastr["success"](message.from, message.content);
////        } else {
////            hostname = '197.159.133.152';
////        }
//
//
//
//        document.onkeydown = KeyPress;
//    });
//
//    $scope.usingFlash = FileAPI && FileAPI.upload !== null;
//    //Upload.setDefaults({ngfKeep: true, ngfPattern:'image/*'});
//    $scope.changeAngularVersion = function () {
//        window.location.hash = $scope.angularVersion;
//        window.location.reload(true);
//    };
//    $scope.angularVersion = window.location.hash.length > 1 ? (window.location.hash.indexOf('/') === 1 ?
//            window.location.hash.substring(2) : window.location.hash.substring(1)) : '1.2.24';
//
//    $scope.invalidFiles = [];
//
//    // make invalidFiles array for not multiple to be able to be used in ng-repeat in the ui
//    $scope.$watch('invalidFiles', function (invalidFiles) {
//        if (invalidFiles !== null && !angular.isArray(invalidFiles)) {
//            $timeout(function () {
//                $scope.invalidFiles = [invalidFiles];
//            });
//        }
//    });
//
////    $scope.$watch('files', function (files) {
////        $scope.formUpload = false;
////        if (files !== null) {
////            // make files array for not multiple to be able to be used in ng-repeat in the ui
////            if (!angular.isArray(files)) {
////                $timeout(function () {
////                    $scope.files = files = [files];
////                });
////                return;
////            }
////            for (var i = 0; i < files.length; i++) {
////                $scope.errorMsg = null;
////                (function (f) {
////                    $scope.upload(f, true);
////                })(files[i]);
////            }
////        }
////    });
//
//    function uploadUsing$http(file, type, url) {
//        $scope.UploadOvaData(file, type, url);
//    }
//
//    $scope.uploadPic = function (file) {
//        $scope.formUpload = true;
////        console.log("Is file null? " + file === null);
//        $scope.validateExcelFile(file, 'upload', 1);
////        if (file !== null) {
////            $scope.uploadFile(file);
////        }
//    };
//
//    $scope.checkKycFile = function (file) {
//        $scope.validateKYCFile(file, 'upload', 1);
//    };
//
//    $scope.verifyOvaData = function (file, type) {
////        alert('verifyova');
//        $scope.hideFilterBox();
//        $scope.validateExcelFile(file, 'verify', type);
//    };
//
//    $scope.recordsExists = function () {
//        $scope.hideFilterBox();
//        $scope.showLoader = true;
////        $scope.uploadResult = JSON.parse('{ "message": "0 out of 5 inserted; one or more records exists", "status": "failed", "ovaDataRecordInserted": [], "ovaDataRecordExist": [{ "id": null, "bankId": "4826163925",   "externalTransactionId": "02RU24476A63507", "txnDate": "2018-12-01",   "status": "Successful",   "type": "Transfer", "providerCategory": null,  "information": null,  "noteMessage": null, "from_": "FRI:gcbpupull.sp/USER",  "fromName": "GCB Bank ova",  "fromHandlerName": null, "to_": "FRI:233242718488/MSISDN",  "toName": "Frank Quainoo", "toHandlerName": null,   "initiatedBy": "ID:gcbpupull.sp/USER",  "onBehalfOf": "ID:gcbpupull.sp/USER",  "amount": -250, "currency": "GHS" }]}');
////        console.log('message: ' + $scope.uploadResult.message);
//        if ($scope.pageNumber < 1)
//            $scope.pageNumber = 1;
//        if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//            $scope.pageNumber = $scope.fgRecordsLastPage;
//
//
//        $scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage);
//        $scope.end = parseInt($scope.begin + $scope.rowsPerPage);
//
//        $scope.filteredOvaRecords = ($scope.uploadResult.ovaDataRecordExist).slice($scope.begin, $scope.end);
//
//        $scope.getRecordsDetailsTotalCount();
//        console.log('DATA: ' + $scope.filteredOvaRecords);
//        $scope.showLoader = false;
//
//    };
//
//    $scope.recordsInserted = function () {
////        if ($scope.uploadResult.ovaDataRecordInserted.length > 0) {
//        $scope.hideFilterBox();
//        $scope.showLoader = true;
//        if ($scope.pageNumber < 1)
//            $scope.pageNumber = 1;
//        if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//            $scope.pageNumber = $scope.fgRecordsLastPage;
//
//        $scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage);
//        $scope.end = parseInt($scope.begin + $scope.rowsPerPage);
////        $scope.uploadResult = JSON.parse('{ "message": "0 out of 5 inserted; one or more records exists", "status": "failed", "ovaDataRecordExist": [], "ovaDataRecordInserted": [{ "id": null, "bankId": "4826163925",   "externalTransactionId": "02RU24476A63507", "txnDate": "2018-12-01",   "status": "Successful",   "type": "Transfer", "providerCategory": null,  "information": null,  "noteMessage": null, "from_": "FRI:gcbpupull.sp/USER",  "fromName": "GCB Bank ova",  "fromHandlerName": null, "to_": "FRI:233242718488/MSISDN",  "toName": "Frank Quainoo", "toHandlerName": null,   "initiatedBy": "ID:gcbpupull.sp/USER",  "onBehalfOf": "ID:gcbpupull.sp/USER",  "amount": -250, "currency": "GHS" }]}');
////        console.log('message: ' + $scope.uploadResult.message);
////            console.log('length: ' + $scope.uploadResult.ovaDataRecordInserted.length);
//
//        $scope.filteredOvaRecords = ($scope.uploadResult.ovaDataRecordInserted).slice($scope.begin, $scope.end);
//        $scope.getRecordsDetailsTotalCount();
//        console.log('DATA: ' + $scope.filteredOvaRecords);
//        $scope.showLoader = false;
////        }
//    };
//
//    $scope.UploadOvaData = function (file, type, urlType) {
////        console.log('Got');
//        var formdata = new FormData();
//        $scope.uploadResult = "";
//
//        $scope.showProgressBar = false;
//        $scope.showProgressBarError = false;
//        formdata.append("file", file);
//
//        $http.get($scope.ovaUploadProxy).then(
//                function (response) {
//                    $scope.ovaUploadSettings = response.data;
//                    if ($scope.ovaUploadSettings.response === 'success') {
//                        if (file.size <= $scope.ovaUploadSettings.ovaUploadMaxSize * 1024000) {
//                            if (type === 'verify') {
////                                alert('VERIFY');
//                                if ($scope.pageNumber < 1)
//                                    $scope.pageNumber = 1;
//                                if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                                    $scope.pageNumber = $scope.fgRecordsLastPage;
////                                console.log('DATA: ' + $scope.pageNumber + ' : ' + $scope.fgRecordsLastPage);
//                                $scope.showLoader = true;
//                                file.upload = Upload.http({
//                                    url: $scope.ovaUploadSettings['ovaVerifyUrl' + urlType],
//                                    method: 'POST',
//                                    headers: {
//                                        'Content-Type': undefined
//                                    },
//                                    data: formdata
//                                });
//                                file.upload.then(function (response) {
//                                    $scope.showLoader = false;
//                                    if (urlType === 1) {
//                                        $('#verify-btn').prop("disabled", false);
//                                        $('#verify-btn').html("VERIFY ER");
//                                    } else {
//                                        $('#verify-btn2').prop("disabled", false);
//                                        $('#verify-btn2').html("VERIFY NER");
//                                    }
//                                    file.result = response.data;
////                                    console.log('RESPONSE: ' + JSON.stringify(response.data));
//                                    $scope.verifyResult = response.data;
////                                    console.log("RESP: " + $scope.verifyResult.status);
//                                    $scope.showLoader = false;
////                                    $('#upload-btn').prop("disabled", false);
////                                    $('#upload-btn').html("Upload");
////                                    if ($scope.uploadResult.status) {
////                                        $scope.showProgressBar = true;
////                                    } else {
////                                        $scope.showProgressBarError = true;
////                                    }
//
//                                    $scope.getOvaTotalCount();
//                                    $scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage);
//                                    $scope.end = parseInt($scope.begin + $scope.rowsPerPage);
//
//                                    $scope.filteredOvaRecords = $scope.verifyResult.slice($scope.begin, $scope.end);
//
//                                    console.log('DATA: ' + $scope.filteredOvaRecords);
//
//                                }, function (response) {
////                                    $('#upload-btn').prop("disabled", false);
////                                    $('#upload-btn').html("Upload");
//                                    if (urlType === 1) {
//                                        $('#verify-btn').prop("disabled", false);
//                                        $('#verify-btn').html("VERIFY ER");
//                                    } else {
//                                        $('#verify-btn2').prop("disabled", false);
//                                        $('#verify-btn2').html("VERIFY NER");
//                                    }
//                                    $scope.showLoader = false;
//                                    alert('An Error Occured: ' + response.status);
//                                });
//                                file.upload.progress(function (evt) {
//                                    file.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
//                                });
//                            } else {
////                                alert('Uploading');
//                                $('#upload-btn').html("<i class='fa fa-spinner fa-spin btn-loader'></i> Uploading...");
////                                alert('Sending: ' + $scope.ovaUploadSettings['ovaUploadUrl' + urlType]);
//                                file.upload = Upload.http({
//                                    url: $scope.ovaUploadSettings['ovaUploadUrl' + urlType],
//                                    method: 'POST',
//                                    headers: {
//                                        'Content-Type': undefined
//                                    },
//                                    data: formdata
//                                });
//                                file.upload.then(function (response) {
//                                    file.result = response.data;
//                                    console.log('RESPONSE: ' + JSON.stringify(response.data));
//                                    $scope.uploadResult = response.data;
//                                    console.log("RESP: " + $scope.uploadResult.status);
//                                    $scope.showLoader = false;
//                                    $('#upload-btn').prop("disabled", false);
//                                    $('#upload-btn').html("Upload");
//                                    if ($scope.uploadResult.status) {
//                                        $scope.showProgressBar = true;
//                                    } else {
//                                        $scope.showProgressBarError = true;
//                                    }
//
//                                }, function (response) {
//                                    $('#upload-btn').prop("disabled", false);
//                                    $('#upload-btn').html("Upload");
//                                    alert('An Error Occured: ' + response.status);
//                                });
//                                file.upload.progress(function (evt) {
//                                    file.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
//                                });
//                            }
//                        } else {
//                            alert("File is too large. Limit is " + $scope.ovaUploadSettings.ovaUploadMaxSize + "MB");
//                        }
//                    } else {
//                        alert('Could not Load OvaUpload Settings');
//                    }
//                    return true;
//                }
//        );
//    };
//
//    $scope.UploadKycData = function (file, type, urlType) {
////        console.log('Got');
//        var formdata = new FormData();
//        $scope.uploadResult = "";
//
//        $scope.showProgressBar = false;
//        $scope.showProgressBarError = false;
//        formdata.append("file", file);
//
//        $http.get($scope.kycUploadProxy).then(
//                function (response) {
//                    $scope.kycUploadSettings = response.data;
//                    if ($scope.kycUploadSettings.response === 'success') {
//                        if (file.size <= $scope.kycUploadSettings.UploadMaxSize * 1024000) {
//
////                                alert('Uploading');
//                            $('#upload-btn').html("<i class='fa fa-spinner fa-spin btn-loader'></i> Uploading...");
////                                alert('Sending: ' + $scope.ovaUploadSettings['ovaUploadUrl' + urlType]);
//                            file.upload = Upload.http({
//                                url: $scope.kycUploadSettings['ovaUploadUrl' + urlType],
//                                method: 'POST',
//                                headers: {
//                                    'Content-Type': undefined
//                                },
//                                data: formdata
//                            });
//                            file.upload.then(function (response) {
//                                file.result = response.data;
//                                console.log('RESPONSE: ' + JSON.stringify(response.data));
//                                $scope.uploadResult = response.data;
//                                console.log("RESP: " + $scope.uploadResult.status);
//                                $scope.showLoader = false;
//                                $('#upload-btn').prop("disabled", false);
//                                $('#upload-btn').html("Upload");
//                                if ($scope.uploadResult.status) {
//                                    $scope.showProgressBar = true;
//                                } else {
//                                    $scope.showProgressBarError = true;
//                                }
//
//                            }, function (response) {
//                                $('#upload-btn').prop("disabled", false);
//                                $('#upload-btn').html("Upload");
//                                alert('An Error Occured: ' + response.status);
//                            });
//                            file.upload.progress(function (evt) {
//                                file.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
//                            });
//
//                        } else {
//                            alert("File is too large. Limit is " + $scope.kycUploadSettings.UploadMaxSize + "MB");
//                        }
//                    } else {
//                        alert('Could not Load KycUpload Settings');
//                    }
//                    return true;
//                }
//        );
//    };
//
//    $scope.validateExcelFile = function (file, type, recordType) {
////        $('#binupdateResp').addClass("bg-success py-2");
////        $('#binupdateResp').removeClass("bg-danger");
////        $('#binCheck-btn').prop("disabled", false);
////        $('#binCheck-btn').html("Check");
////        alert('TErY: ' + type);
//        if (type === 'verify') {
//            if (recordType === 1) {
//                $('#verify-btn').prop("disabled", true);
//                $('#verify-btn').html("<i class='fa fa-spinner fa-spin btn-loader'></i> Validating");
//            } else {
//                $('#verify-btn2').prop("disabled", true);
//                $('#verify-btn2').html("<i class='fa fa-spinner fa-spin btn-loader'></i> Validating");
//            }
//        } else {
//            $('#upload-btn').prop("disabled", true);
//            $('#upload-btn').html("<i class='fa fa-spinner fa-spin btn-loader'></i> Validating");
//        }
////        var regex = /^([a-zA-Z0-9\s_\\.\-:])+(.xlsx)$/;
////        /*Checks whether the file is a valid excel file*/
////        if (regex.test($("#excelfile").val().toLowerCase())) {
////            console.log('1');
//        var xlsxflag = false; /*Flag for checking whether excel is .xls format or .xlsx format*/
//        if ($("#excelfile").val().toLowerCase().indexOf(".xlsx") > 0) {
////                console.log('2');
//            xlsxflag = true;
//        }
//        /*Checks whether the browser supports HTML5*/
//        if (typeof (FileReader) !== "undefined") {
//
//            var reader = new FileReader();
//            reader.onload = function (e) {
//                var data = e.target.result;
//                /*Converts the excel data in to object*/
//                if (xlsxflag) {
//                    var binary = "";
//                    var bytes = new Uint8Array(data);
//                    var length = bytes.byteLength;
//                    for (var i = 0; i < length; i++) {
//                        binary += String.fromCharCode(bytes[i]);
//                    }
//                    var workbook = XLSX.read(binary, {type: 'binary'});
//                }
//                /*Gets all the sheetnames of excel in to a variable*/
//                var sheet_name_list = workbook.SheetNames;
//                var cnt = 0; /*This is used for restricting the script to consider only first sheet of excel*/
//                sheet_name_list.forEach(function (y) { /*Iterate through all sheets*/
//                    /*Convert the cell value to Json*/
//                    if (xlsxflag) {
//                        var exceljson = XLSX.utils.sheet_to_json(workbook.Sheets[y]);
////                        var jsonObj = JSON.stringify(exceljson);
////
////                        console.log(jsonObj);
//
//                    }
//
//                    var columnSet = [];
//                    if (exceljson.length > 0 && cnt === 0) {
////                            console.log('Data: t');
//                        var rowHash = exceljson[0];
//
//                        for (var key in rowHash) {
//
//                            if (rowHash.hasOwnProperty(key)) {
//                                if ($.inArray(key, columnSet) === -1) {/*Adding each unique column names to a variable array*/
//                                    columnSet.push(key);
//                                }
//                            }
//                        }
//                        console.log('2: ' + columnSet);
//
//                        var header = 'Id,External Transaction Id,Date,Status,Type';
//
//
//                        if ($.trim(columnSet).includes(header)) {
//
//                            console.log('Valid excel file');
//                            if (file !== null) {
//                                $scope.uploadFile(file, type, recordType);
//                            } else {
//                                alert('could not check File');
//                            }
//                        } else {
//
//                            alert("invalid excel file");
//
//                        }
//                        cnt++;
//                    }
//                });
//            };
//            if (xlsxflag) {/*If excel file is .xlsx extension than creates a Array Buffer from excel*/
//                reader.readAsArrayBuffer($("#excelfile")[0].files[0]);
//            }
//
//        } else {
//            $('#upload-btn').prop("disabled", false);
//            $('#upload-btn').html("Upload");
//            alert("Sorry! Your browser does not support HTML5!");
//        }
////        } else {
////            $('#upload-btn').prop("disabled", false);
////            $('#upload-btn').html("Upload");
////            alert("Please upload a valid Excel file!!!");
////        }
//    };
//
//    $scope.validateKYCFile = function (file, type, recordType) {
//        if (type === 'verify') {
//            if (recordType === 1) {
//                $('#verify-btn').prop("disabled", true);
//                $('#verify-btn').html("<i class='fa fa-spinner fa-spin btn-loader'></i> Validating");
//            } else {
//                $('#verify-btn2').prop("disabled", true);
//                $('#verify-btn2').html("<i class='fa fa-spinner fa-spin btn-loader'></i> Validating");
//            }
//        } else {
//            $('#upload-btn').prop("disabled", true);
//            $('#upload-btn').html("<i class='fa fa-spinner fa-spin btn-loader'></i> Validating");
//        }
//        var xlsxflag = false; /*Flag for checking whether excel is .xls format or .xlsx format*/
//        if ($("#excelfile").val().toLowerCase().indexOf(".xlsx") !== 1) {
////                console.log('2');
//            xlsxflag = true;
//        }
//        /*Checks whether the browser supports HTML5*/
//        if (typeof (FileReader) !== "undefined") {
//
//            var reader = new FileReader();
//            reader.onload = function (e) {
//                var data = e.target.result;
//                /*Converts the excel data in to object*/
//                if (xlsxflag) {
//                    var binary = "";
//                    var bytes = new Uint8Array(data);
//                    var length = bytes.byteLength;
//                    for (var i = 0; i < length; i++) {
//                        binary += String.fromCharCode(bytes[i]);
//                    }
//                    var workbook = XLSX.read(binary, {type: 'binary'});
//                }
//                /*Gets all the sheetnames of excel in to a variable*/
//                var sheet_name_list = workbook.SheetNames;
//                var cnt = 0; /*This is used for restricting the script to consider only first sheet of excel*/
//                sheet_name_list.forEach(function (y) { /*Iterate through all sheets*/
//                    /*Convert the cell value to Json*/
//                    if (xlsxflag) {
//                        var exceljson = XLSX.utils.sheet_to_json(workbook.Sheets[y]);
//                        var jsonObj = JSON.stringify(exceljson);
//
//                        console.log(jsonObj);
//
//                    }
//
//                    var columnSet = [];
//                    if (exceljson.length > 0 && cnt === 0) {
////                            console.log('Data: t');
//                        var rowHash = exceljson[0];
//
//                        for (var key in rowHash) {
//
//                            if (rowHash.hasOwnProperty(key)) {
//                                if ($.inArray(key, columnSet) === -1) {/*Adding each unique column names to a variable array*/
//                                    columnSet.push(key);
//                                }
//                            }
//                        }
//                        console.log('2: ' + columnSet);
//
//                        var header = 'CardHolderName,CardNumber,IdNumber,IdType,MobileNumber';
//                        $scope.validKycFile = false;
//                        header.split(/\s*,\s*/).forEach(function (myString) {
//                            $scope.validKycFile = exceljson[0].hasOwnProperty(myString);
//                        });
//
//                        if ($scope.validKycFile) {
//
//                            console.log('Valid excel file');
//                            if (file !== null) {
//                                $scope.uploadKycFile(file, type, recordType);
//                            } else {
//                                alert('could not check File');
//                            }
//                        } else {
//
//                            alert("invalid excel file");
//
//                        }
//                        cnt++;
//                    }
//                });
//            };
//            if (xlsxflag) {/*If excel file is .xlsx extension than creates a Array Buffer from excel*/
//                reader.readAsArrayBuffer($("#excelfile")[0].files[0]);
//            }
//
//        } else {
//            $('#upload-btn').prop("disabled", false);
//            $('#upload-btn').html("Upload");
//            alert("Sorry! Your browser does not support HTML5!");
//        }
////        } else {
////            $('#upload-btn').prop("disabled", false);
////            $('#upload-btn').html("Upload");
////            alert("Please upload a valid Excel file!!!");
////        }
//    };
//
//    $scope.toggleKycUploadBox = function () {
//        $scope.showKycUploadBox = !$scope.showKycUploadBox;
//    };
//
//    $scope.getOvatrxs = function (page, paging) {
//
//        $scope.showLoader = true;
//        $scope.showFilterBox = false;
//        $scope.pageNumber = page;
//        $('.reload').addClass('fa-spin text-info');
//
//        if ($scope.pageNumber < 1)
//            $scope.pageNumber = 1;
//        if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//            $scope.pageNumber = $scope.fgRecordsLastPage;
//        console.log('DATA: ' + $scope.pageNumber + ' : ' + $scope.fgRecordsLastPage);
//        if (paging) {
//
////                console.log('DATA: ' + page + ' : ' + $scope.fgRecordsLastPage);
//            $scope.begin = (page - 1) * parseInt($scope.rowsPerPage);
//            $scope.end = parseInt($scope.begin) + parseInt($scope.rowsPerPage);
////                console.log('begin: ' + $scope.begin + ' : ' + $scope.end);
//            $scope.filteredOvaRecords = $scope.verifyResult.slice($scope.begin, $scope.end);
//            $scope.getOvaTotalCount();
//            $scope.showLoader = false;
//            $('.reload').removeClass('fa-spin text-info');
//
//        }
//    };
//
//    $scope.getOvaTotalCount = function () {
//        $scope.fgRecordsTotalCount = $scope.verifyResult.length;
//        $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//        if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//            $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//        }
//    };
//
//    $scope.getRecordsDetailsTotalCount = function () {
//        $scope.fgRecordsTotalCount = $scope.uploadResult.ovaDataRecordInserted.length > 0 ? $scope.uploadResult.ovaDataRecordInserted.length : $scope.uploadResult.ovaDataRecordExist.length;
//        $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//        if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//            $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//        }
//    };
//
////    $scope.getOvaTotalAmount = function () {
////        $scope.fgRecordsTotalAmount = $scope.sumAmount($scope.ovaTrxs, 'response', 'SUCCESSFUL', 'trans_amount');
////    };
//
//    function _objectWithoutProperties(source, excluded) {
//        if (source === null)
//            return {};
//        var target = _objectWithoutPropertiesLoose(source, excluded);
//        var key, i;
//        if (Object.getOwnPropertySymbols) {
//            var sourceSymbolKeys = Object.getOwnPropertySymbols(source);
//            for (i = 0; i < sourceSymbolKeys.length; i++) {
//                key = sourceSymbolKeys[i];
//                if (excluded.indexOf(key) >= 0)
//                    continue;
//                if (!Object.prototype.propertyIsEnumerable.call(source, key))
//                    continue;
//                target[key] = source[key];
//            }
//        }
//        return target;
//    }
//
//    function _objectWithoutPropertiesLoose(source, excluded) {
//        if (source === null)
//            return {};
//        var target = {};
//        var sourceKeys = Object.keys(source);
//        var key, i;
//        for (i = 0; i < sourceKeys.length; i++) {
//            key = sourceKeys[i];
//            if (excluded.indexOf(key) >= 0)
//                continue;
//            target[key] = source[key];
//        }
//        return target;
//    }
//
//
//    $scope.exportToExcel = function (dataToExport) {
//
////        $scope.data = dataToExport.map(({$$hashKey, ...item }) => item);
//        $scope.data = dataToExport.map(function (_ref) {
//            var $$hashKey = _ref.$$hashKey,
//                    item = _objectWithoutProperties(_ref, ["$$hashKey"]);
//
//            return item;
//        });
//        /* generate a worksheet */
//        var ws = XLSX.utils.json_to_sheet($scope.data);
//
//        /* add to workbook */
//        var wb = XLSX.utils.book_new();
//        XLSX.utils.book_append_sheet(wb, ws, $scope.currentPageLocation);
//
//        /* write workbook and force a download */
//        XLSX.writeFile(wb, $scope.currentPageLocation + ".xlsx");
//    };
//
//    $scope.sumAmount = function (data, filter, filterValue, amount) {
//        var total = 0;
//        $scope.tempData = data;
//        var amountType = amount;
//        var filterType = filter;
////        console.log('sim: ' + $scope.tempData[1][amountType]);
////        console.log('Type: ' + typeof $scope.tempData[0].[amount]);
//        for (var i = 0; i < $scope.tempData.length; i++) {  //loop through the array
//            if (filterType === '') {
//                total += $scope.tempData[i][amountType];  //Do the math!
//            } else {
//                if ($scope.tempData[i][filterType] === filterValue)
//                    total += $scope.tempData[i][amountType];
//            }
//        }
//        return total;
//    };
//
//    $scope.resultsPageCount = function (data) {
//        return Math.ceil(data.length / $scope.rowsPerPage);
//    };
//
//    $scope.uploadFile = function (file, type, url) {
//        console.log('got here' + type + ':' + url);
//        $scope.errorMsg = null;
//        uploadUsing$http(file, type, url);
//
//    };
//
//    $scope.uploadKycFile = function (file, type, url) {
//        console.log('got here' + type + ':' + url);
//        $scope.errorMsg = null;
//        $scope.UploadKycData(file, type, url);
//
//    };
//
//    $scope.chunkSize = 100000;
////    $scope.getTheFiles = function ($file) {
////
////
////        formdata.append("file", $file[0]);
//////        console.log("data : " + $file[0]);
////
////    };
//
//
////    $scope.uploadFiles = function () {
////
////        var request = {
////            method: 'POST',
////            url: 'http://e958bece.ngrok.io/api/sendfile',
////            data: formdata,
////            headers: {
////                'Content-Type': undefined
////            }
////        };
////
////
////        $http(request)
////                .success(function (d) {
////                    alert(d);
////                })
////                .error(function () {
////                });
////    };
//
//
//    $scope.confirm = function () {
//        return confirm('Are you sure? Your local changes will be lost.');
//    };
//
//    $scope.rowsPerPage = 100;
//    $scope.currentPage = 0;
//    $scope.items = [];
//
//    for (var i = 0; i < 50; i++) {
//        $scope.items.push({id: i, name: "name " + i, description: "description " + i});
//    }
//
//    $scope.range = function () {
//        var rangeSize = 5;
//        var ret = [];
//        var start;
//
//        start = $scope.currentPage;
//        if (start > $scope.pageCount() - rangeSize) {
//            start = $scope.pageCount() - rangeSize + 1;
//        }
//
//        for (var i = start; i < start + rangeSize; i++) {
//            ret.push(i);
//        }
//        return ret;
//    };
//
//    $scope.prevPage = function () {
//        if ($scope.currentPage > 0) {
//            $scope.currentPage--;
//        }
//    };
//
//    $scope.prevPageDisabled = function () {
//        return $scope.currentPage === 0 ? "disabled" : "";
//    };
//
//    $scope.pageCount = function (trans) {
//        return Math.ceil($scope.fgTrxs.length / $scope.rowsPerPage) - 1;
//    };
//
//    $scope.nextPage = function () {
//        if ($scope.currentPage < $scope.pageCount()) {
//            $scope.currentPage++;
//        }
//    };
//
//    $scope.nextPageDisabled = function () {
//        return $scope.currentPage === $scope.pageCount() ? "disabled" : "";
//    };
//
//    $scope.setPage = function (n) {
//        $scope.currentPage = n;
//    };
//
//
//    $('[data-toggle="tooltip"]').tooltip();
//    $scope.getProfile = function () {
//        $http.get('./UserProfile').then(
//                function (response) {
//                    if (response.data) {
//                        $scope.profile = response.data;
//                        if ($scope.profile.first_logon !== '1') {
//                            window.location = '#!firstChangePassword';
//                        }
//                    } else {
//                        window.location = './login';
//                    }
//                }
//        );
//        //.slice($scope.currentLocation.lastIndexOf("#!"), $scope.currentLocation.length));
//    };
//    $scope.$on("$routeChangeSuccess", function (event, next, current) {
//        $http.get('./Cleaner').then(function (response) {});
//    });
//    $scope.initDates = function () {
//        var startOfDay = moment().format('YYYY-MM-DD 00:00');
//        var currentDateTime = moment().format('YYYY-MM-DD 23:59');
//        $('#start-date').datetimepicker({
//            format: 'Y-m-d H:i',
//            value: startOfDay
//        });
//        $scope.startDate = startOfDay;
//        $scope.endDate = currentDateTime;
//        $('#start-date-btn').on('click', function () {
//            $('#start-date').datetimepicker('show');
//        });
//        $('#end-date').datetimepicker({
//            format: 'Y-m-d H:i',
//            value: moment().format('Y-m-d H:i')
//        });
//        $('#end-date-btn').on('click', function () {
//            $('#end-date').datetimepicker('show');
//        });
//    };
//    $scope.getProfile();
//    $scope.initDates();
//
//    $scope.userReady2 = function () {
//        if ($scope.profile.first_logon !== '1' || $scope.profile.temp_password === 'true') {
//            window.location = '#!firstChangePassword';
//        }
//    };
//    $scope.downloadPDF = function () {
////        alert(Object.keys($scope.fgTrxs).length);
//        $scope.details = [];
//        var details2 = [];
//        angular.forEach($scope.fgTrxs, function (value, index) {
//            details2.push({text: value.etzRef});
//        });
//        var documentDefinition = {
//            content:
//                    [
//                        {
//                            text: 'Etranzact Ghana', style: 'mainheader'
//                        },
//                        {
//                            table:
//                                    {
//                                        headerRows: 1,
//                                        widths: ['*', '*', '*', '*'],
//                                        body: [
//                                            [
//                                                {text: 'Header 1', style: 'tableHeader'}
////                                                {text: 'Header 2', style: 'tableHeader'},
////                                                {text: 'Header 3', style: 'tableHeader'}
//                                            ]
////                                            [
////                                                {text: 'Hello'},
////                                                {text: 'I'},
////                                                {text: 'am'}
////                                            ],
////                                            [
////                                                {text: 'a'},
////                                                {text: 'table'},
////                                                {text: '.'}
////                                            ]
//                                        ]
//                                    }
//                        },
//                        {
//                            table:
//                                    {
//                                        headerRows: 1,
//                                        widths: ['*', '*'],
//                                        body: [
////                                            [
////                                                {text: 'Header 1', style: 'tableHeader'},
////                                                {text: 'Header 2', style: 'tableHeader'},
////                                                {text: 'Header 3', style: 'tableHeader'}
////                                            ],
////                                            [
////                                                {text: 'Hello'},
////                                                {text: 'I'},
////                                                {text: 'am'}
////                                            ],
////                                            [
////                                                {text: 'a'},
////                                                {text: 'table'},
////                                                {text: '.'}
////                                            ]
//                                            details2
//                                        ]
//                                    }
//                        }
//                    ],
//            styles:
//                    {mainheader: {
//                            fontSize: 20,
//                            bold: true,
//                            margin: [0, 10, 0, 10],
//                            alignment: 'left'
//                        },
//                        header:
//                                {
//                                    fontSize: 18,
//                                    bold: true,
//                                    margin: [0, 10, 0, 10],
//                                    alignment: 'center'
//                                },
//                        tableHeader:
//                                {
//                                    fillColor: '#4CAF50',
//                                    color: 'white'
//                                }
//                    }
//        };
//        pdfMake.createPdf(documentDefinition).download('testdoc.pdf');
//    };
//
//    $scope.testFeeCollection = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        if (i === '65' || i === '68') {
//            return true;
//        }
//        return false;
//    };
//
//    $scope.testRvslFeeCollection = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        if ($scope.testUCC()) {
//            return true;
//        }
//        return false;
//    };
//
//    $scope.testFGTrx = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        if (i === "1" || i === "7" || i === "8" || i === "17" || i === "22" || i === "32" || i === "44" || i === "79" || i === "80") {
////            $scope.pageReady = true;
////            console.log("ready: "+ $scope.pageReady);
//            return true;
//        }
//        return false;
//    };
//    $scope.testVasBill = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        if (i === "1" || i === "7" || i === "8" || i === "22" || i === "44" || i === "79") {
//            return true;
//        }
//        return false;
//    };
//    $scope.testBillPayment = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        if (i === "26") {
//            return false;
//        }
//        if ($scope.testBank() || $scope.testVasMerchant()) {
//            return true;
//        }
//        if (i === "1" || i === "7" || i === "8" || i === "22" || i === "44" || i === "79" || i === "32") {
//            return true;
//        }
//        return false;
//    };
//    $scope.testFAB = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        if (i === "32") {
//            return true;
//        }
//        return false;
//    };
//    $scope.testUssd = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//
//        if (i === "2000000000000041" || i === "26" || i === "16") {
//            return false;
//        }
//        if ($scope.testBank()) {
//            return true;
//        }
//        if (i === "1" || i === "7" || i === "8" || i === "22" || i === "44" || i === "79" || i === "32") {
//            return true;
//        }
//        return false;
//    };
//    $scope.testMobileInvestigation = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        var j = $scope.profile.user_code;
//
//        if (j === "917") {
//            return true;
//        }
//
//        if (i === "26") {
//            return false;
//        }
//
//        if (i === "1" || i === "7" || i === "8" || i === "22" || i === "44") {
//            return true;
//        }
//        if ($scope.testBank()) {
//            return true;
//        }
//        return false;
//    };
//    $scope.testMomoStatus = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        var j = $scope.profile.user_id;
//        if (i === "1" || i === "22" || i === "8" || j === "9000000000000510") {
//            return true;
//        }
//        return false;
//    };
//    $scope.testGIPInvestigation = function () {
//        $scope.userReady2();
//        if ($scope.testEtz()) {
//            return true;
//        }
//        return false;
//    };
//
//    $scope.testVerificationPortal = function () {
//        $scope.userReady2();
//        if ($scope.testEtz()) {
//            return true;
//        }
//        return false;
//    };
//
//    $scope.testTools = function () {
//        if ($scope.testMomoStatus() || $scope.testMobileInvestigation() || $scope.testFGTrx() || $scope.testMobileAppActivation()) { // || $scope.testBarclays()) {
//            return true;
//        }
//        return false;
//    };
//    $scope.testFGReport = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        if (i === "17" || i === "80") {
//            return true;
//        }
//        return false;
//    };
//    $scope.testJP = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        if (i === "1" || i === "7" || i === "8" || i === "22" || i === "44" || i === "79" || i === "2000000000000054") {
//            return true;
//        }
//        return false;
////       if (/1|7|8|22|44|79/.test($scope.profile.type_id)) {
////          return true;
////       }
////       return false;
//    };
//    $scope.testBarUBP = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        if (i === "1" || i === "7" || i === "29" || i === "8" || i === "22" || i === "44") {
//            return true;
//        }
//        return false;
//    };
//    $scope.testBank = function () {
//        var i = $scope.profile.user_code;
//        if (i === "025" || i === "003" || i === "905" || i === "021" || i === "029" || i === "004" || i === "006" || i === "012" || i === "917" || i === "029") {
//            return true;
//        }
//        return false;
//    };
//    $scope.testBanks = function () {
//        if ($scope.testBank()) {
//            return true;
//        }
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        if (i === "32") {
//            return true;
//        }
//        return false;
//    };
//    $scope.testMomo = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        if (i === "26") {
//            return false;
//        }
//        if ($scope.testBank()) {
//            return true;
//        }
//        if (i === "1" || i === "7" || i === "8" || i === "22" || i === "44") {
//            return true;
//        }
//        return false;
//    };
//    $scope.testEtz = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        if (i === "1" || i === "2" || i === "7" || i === "8" || i === "22" || i === "44" || i === "69") {
//            return true;
//        }
//        return false;
//    };
//    $scope.testSas = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        if (i === "1" || i === "7" || i === "8" || i === "22" || i === "44" || i === "98") {
//            return true;
//        }
//        return false;
//    };
//    $scope.testSas2 = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        if ($scope.testEtz()) {
//            return true;
//        }
//        if (i === "98") {
//            return true;
//        }
//        return false;
//    };
//    $scope.testTMCNodeList = function () {
//        if ($scope.TMCBankNodeList.length > 0) {
//            return true;
//        }
//    };
//    $scope.testTMCtrans = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        if (i === "26") {
//            return false;
//        }
//        if (i === "1" || i === "7" || i === "8" || i === "22" || i === "44") {
//            return true;
//        }
//        if (i === "2000000000000041" || i === "2000000000000044") {
//            return false;
//        }
//        if ($scope.testTMCNodeList()) {
//            return true;
//        }
//        return false;
//    };
//    $scope.testTMCBanks = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        if (i === "1" || i === "7" || i === "8" || i === "22" || i === "44" || i === "90" || i === "96") {
//            return true;
//        }
//        return false;
//    };
//    $scope.testKwesetvTrans = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        if (i === "1" || i === "7" || i === "8" || i === "22" || i === "44" || i === "95") {
//            return true;
//        }
//        return false;
//    };
//    $scope.testMobileAppActivation = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        if ($scope.testEtz()) {
//            return true;
//        }
//        if (i === "26") {
//            return true;
//        }
//        return false;
//    };
//
//    $scope.testBOGswitchTrans = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        var j = $scope.profile.user_id;
//        if ($scope.testProject()) {
//            return true;
//        }
//        if ($scope.testReport()) {
//            return true;
//        }
//        if (i === '8') {
//            return true;
//        }
//        return false;
//    };
//    $scope.testFgBalanceReport = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        if (i === '8' || i === '22') {
//            return true;
//        }
//        return false;
//    };
//    $scope.testKYCReport = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        if (i === '8' || i === '22') {
//            return true;
//        }
//        return false;
//    };
//    $scope.testBusyTrx = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        if (i === '1500000000000035') {
//            return true;
//        }
//        return false;
//    };
//    $scope.testGWCLTrx = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        if (i === '1500000000000036') {
//            return true;
//        }
//        return false;
//    };
//    $scope.testElacTrx = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        if (i === '1' || i === '2' || i === '22' || i === '1500000000000037') {
//            return true;
//        }
//        return false;
//    };
//    $scope.testGCBAgent = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        if (i === '1' || i === '16' || i === '22' || i === '23' || i === '55' || i === '56') {
//            return true;
//        }
//        return false;
//    };
//    $scope.testRevenueRpt = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        if (i === '1' || i === '22') {
//            return true;
//        }
//        return false;
//    };
//    $scope.testAdmin = function () {
//        $scope.userReady2();
//        if ($scope.testAdm()) { //|| testUserMgt()) {
//            return true;
//        }
//
//        return false;
//    };
//    $scope.userReady = function () {
//        if ($scope.profile.first_logon === '1' && $scope.profile.temp_password === 'false') {
////            console.log('user is ready');
//            return true;
//        }
////        console.log('user is not ready');
//        return false;
//    };
//    $scope.testTech = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        if (i === "1" || i === "2" || i === "8" || i === "22" || i === "2000000000000050") {
//            return true;
//        }
//        return false;
//    };
//    $scope.testCardTrans = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        if (i === "1" || i === "2" || i === "8" || i === "22" || i === "2000000000000043" || i === "2000000000000045" || i === "2000000000000048" || i === "2000000000000050") {
//            return true;
//        }
//        return false;
//    };
//    $scope.testSingleCardTrans = function () {
//
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        if (i === "2000000000000043" || i === "2000000000000045" || i === "2000000000000048") {
//            return true;
//        }
//        return false;
//    };
//    $scope.checkSingleCard = function () {
//        if ($scope.testEtz()) {
//            return $scope.profile.type_desc + " Transactions";
//        } else {
//            return "Card Transactions";
//        }
//    };
//    $scope.testAdm = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        var j = $scope.profile.role_id;
//        if (i === "1" || i === "2" || j === "1") {
//            return true;
//        }
//        return false;
//    };
//    $scope.testReprocessingInitiator = function () {
//        $scope.userReady2();
//        var i = $scope.profile.role_id;
//        if (i === "3") {
//            return true;
//        }
//        return false;
//    };
//    $scope.testReprocessingAuthorizer = function () {
//        $scope.userReady2();
//        var i = $scope.profile.role_id;
//        if (i === "5" || i === "1") {
//            return true;
//        }
//        return false;
//    };
//    $scope.testReversalInitiator = function () {
//        $scope.userReady2();
//        var i = $scope.profile.role_id;
//        if (i === "2") {
//            return true;
//        }
//        return false;
//    };
//    $scope.testReversalAuthorizer = function () {
//        $scope.userReady2();
//        var i = $scope.profile.role_id;
//        if (i === "4") {
//            return true;
//        }
//        return false;
//    };
//    $scope.testTopupSales = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        if (i === "22" || i === "1" || i === "2") {
//            return true;
//        }
//        return false;
//    };
//    $scope.testBarclays = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        if (i === "1" || i === "22" || i === "8" || i === "29" || i === "49") {
//            return true;
//        }
//        return false;
//    };
//    $scope.testUserMgt = function () {
//        var i = $scope.profile.Admin;
//        if (i === "1" || i === "2") {
//            return true;
//        }
//        return false;
//    };
//    $scope.testICGCTrx = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        var j = $scope.profile.user_id;
//        if (i === "1" || i === "2" || i === "1500000000000039" || j === "1000000000000038") {
//            return true;
//        }
//        return false;
//    };
//    $scope.testGCBMerchantTrx = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        var j = $scope.profile.user_id;
//        if (i === "1" || i === "2" || i === '16' || i === '2000000000000040' || j === "1000000000000038" || j === "9000000000000490" || j === "10000000000000557") {
//            return true;
//        }
//        return false;
//    };
//
//    $scope.testMerchantPayTrx = function () {
//        var i = $scope.profile.type_id;
//        if (i === "2000000000000049" || $scope.testEtz()) {
//            return true;
//        }
//        return false;
//    };
//
//    $scope.testMTNReport = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        var j = $scope.profile.user_id;
//        if (i === "1" || i === "2" || i === '2000000000000042' || j === "1000000000000038" || j === "9000000000000490") {
//            return true;
//        }
//        return false;
//    };
//    $scope.testMTNCMS = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        var j = $scope.profile.user_id;
//        if (i === "2000000000000050" || i === "26") {
//            return true;
//        }
//        return false;
//    };
//    $scope.testBOA = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        var j = $scope.profile.user_id;
//        if (i === "26") {
//            return true;
//        }
//        return false;
//    };
//
//    $scope.testFGbanks = function () {
//        var i = $scope.profile.user_code;
//        if (i === "025" || i === "003" || i === "905" || i === "021" || i === "029" || i === "012" || i === "190" || i === "033") {
//            return true;
//        }
//        return false;
//    };
//
//    $scope.testAuthorizeCMS = function () {
//        $scope.userReady2();
//        var i = $scope.profile.cms_role;
//        if (i === "1" || i === "3") {
//            return true;
//        }
//        return false;
//    };
//
//    $scope.testInitiateCMS = function () {
//        $scope.userReady2();
//        var i = $scope.profile.cms_role;
//        if (i === "2") {
//            return true;
//        }
//        return false;
//    };
//    $scope.testVodafone = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        if (i === '2000000000000044') {
//            return true;
//        }
//        return false;
//    };
//    $scope.testFeePayment = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        var j = $scope.profile.user_id;
//        if (i === '2000000000000046' || j === '9000000000000490' || j === '1000000000000038') {
//            return true;
//        }
//        return false;
//    };
//    $scope.testMomoDebitStatus = function () {
//        return $scope.testEtz();
//    };
//
//    $scope.testEtzMerchant = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        if (i === '2000000000000051') {
//            return true;
//        }
//        return false;
//    };
//    $scope.testHideFee = function () {
//
//        var i = $scope.profile.type_id;
//        var j = $scope.profile.user_code;
//        if (j !== 'ETZ:6WCI') {
//            return true;
//        }
//        return false;
//    };
//    $scope.testEtzMerchantAll = function () {
//        var j = $scope.profile.user_code;
//
//        if (j !== 'ETZ:ALL') {
//            return true;
//        }
//        return false;
//    };
//    $scope.testMerchantPayAll = function () {
//        var j = $scope.profile.user_code;
//
//        if (j === 'ETZ:ALL' || j === 'GCB:ALL' || j === 'SCB:ALL') {
//            return true;
//        }
//        return false;
//    };
//
//    $scope.testSCB = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        if (i === '2000000000000047') {
//            return true;
//        }
//        return false;
//    };
//    $scope.testSCB2 = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        var j = $scope.profile.user_code;
//        if (i === '2000000000000047' && j !== 'SCB:ALL') {
//            return true;
//        }
//        return false;
//    };
//    $scope.testScbAll = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        var j = $scope.profile.user_code;
//        if (i === '2000000000000047' && j === 'SCB:ALL') {
//            return true;
//        }
//        return false;
//    };
//    $scope.testProject = function () {
//        var i = $scope.profile.user_id;
//        if (i === '10000000000000557' || i === '1000000000000038') {
//            return true;
//        }
//        return false;
//    };
//    $scope.testReport = function () {
//        var i = $scope.profile.user_id;
//        if (i === '1000000000000037' || i === '3' || i === '9000000000000490') {
//            return true;
//        }
//        return false;
//    };
//    $scope.testCorporatePay = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        var j = $scope.profile.cop_company_id;
//        if (i === '2000000000000048' || j !== '0') {
//            return true;
//        }
//        return false;
//    };
//    $scope.testCPay = function () {
//        $scope.userReady2();
//        var i = $scope.profile.type_id;
//        var j = $scope.profile.cop_company_id;
//        if (i === '2000000000000048') {
//            return true;
//        }
//        return false;
//    };
//
//    $scope.testUCC = function () {
//        var i = $scope.profile.type_id;
//        if (i === '68') {
//            return true;
//        }
//        return false;
//    };
//    $scope.testKNUST = function () {
//        var i = $scope.profile.type_id;
//        if (i === '65') {
//            return true;
//        }
//        return false;
//    };
//    $scope.testFeeCollection = function () {
//        if ($scope.testKNUST() || $scope.testUCC()) {
//            return true;
//        }
//        return false;
//    };
//
//    $scope.testWebconnect = function () {
//        var i = $scope.profile.type_id;
//        if ($scope.testEtz()) {
//            return true;
//        }
//        if (i === "67" || i === "71") {
//            return true;
//        }
//        return false;
//    };
//    $scope.testVasMerchant = function () {
//        var i = $scope.profile.type_id;
//        if (i === "2000000000000053") {
//            return true;
//        }
//        return false;
//    };
//
//    $scope.testMomoCardRequests = function () {
//        var i = $scope.profile.type_id;
//        var j = $scope.profile.user_id;
//        if ($scope.testEtz()) {
//            return true;
//        }
//        return false;
//
//    };
//    $scope.testMomoCardRequestUpdate = function () {
//
//        var i = $scope.profile.type_id;
//        var j = $scope.profile.user_id;
//        if (j === "9000000000000490" || j === "10000000000000590") {
//            return true;
//        }
//        return false;
//    };
//
//    $scope.merchant = "ALL";
//    $scope.product = "ALL";
//    $scope.status = "ALL";
//    $scope.bank = "ALL";
//    $scope.channel = "ALL";
//    $scope.transType = "ALL";
//    $scope.refundStatus = "true";
//    $scope.refundType = "true";
//    $scope.subscriberId = "ALL";
//    $scope.subscriberId2 = "MTN";
//    $scope.card_num = "ALL";
//    $scope.optionType = "ALL";
//    $scope.vtuOptionType = "A";
//    $scope.lineType = "ALL";
//    $scope.mobile_type = "VERSION II";
//    $scope.msg_type = "ALL";
//    $scope.trans_code = 'ALL';
//    $scope.trans_status = 'ALL';
//    $scope.payType = "ALL";
//    $scope.wcMerchant = "ALL";
//    $scope.trans_channel = "ALL";
//    $scope.destination = '000';
//    $scope.destination2 = $scope.BankNodes;
//    $scope.AgentsTransactionsAmt = 0.0;
//    $scope.origin = '000';
//    $scope.url1 = "./etz/proxy/2017";
//    $scope.url2 = "./etz/proxy/2018";
//    $scope.url3 = "./etz/investproxy/2017";
//    $scope.vasbillProxy = "./etz/proxy/2018/vasbill";
//    $scope.barUBPProxy = "./etz/proxy/2018/barUBPProxy";
//    $scope.momoProxy = "./etz/proxy/2018/momoProxy";
//    $scope.vtuProxy = "./etz/proxy/2018/vtuProxy";
//    $scope.jpProxy = "./etz/proxy/2019/JustPay";
//    $scope.webconProxy = "./etz/proxy/2018/webconProxy";
//    $scope.sasProxy = "./etz/proxy/2018/sasProxy";
//    $scope.ussdProxy = "./etz/proxy/2018/ussdProxy";
//    $scope.tmcProxy = "./etz/proxy/2018/tmcProxy";
//    $scope.billpaymentProxy = "./etz/proxy/2018/billpaymentProxy";
//    $scope.kwesetvpaymentProxy = "./etz/proxy/2018/kwesetvpaymentProxy";
//    $scope.bogswitchProxy = "./etz/proxy/2018/bogswitchProxy";
//    $scope.busyProxy = "./etz/proxy/2018/busyProxy";
//    $scope.gwclProxy = "./etz/proxy/2018/gwclProxy";
//    $scope.elacProxy = "./etz/proxy/2018/elacProxy";
//    $scope.fgmomoProxy = "./etz/proxy/2018/fgmomoProxy";
//    $scope.mobileinvestigationProxy = "./etz/proxy/2018/mobileinvestProxy";
//    $scope.mobileinvestigationdetailsProxy = "./etz/proxy/2018/mobileinvestdetailsProxy";
//    $scope.gcbagentProxy = "./etz/proxy/2018/gcbagentProxy";
//    $scope.statusProxy = "./etz/proxy/2018/statusProxy";
//    $scope.statusupdateProxy = "./etz/proxy/2018/statusupdateProxy";
//    $scope.cardtransreportProxy = "./etz/proxy/2018/cardtransreportProxy";
//    $scope.revenuerptProxy = "./etz/proxy/2018/revenuerptProxy";
//    $scope.crdDetProxy = "./etz/proxy/2018/cardDetailsView";
//    $scope.crdUpdateProxy = "./etz/proxy/2018/cardUpdate";
//    $scope.topupsalesProxy = "./etz/proxy/2018/topupsalesProxy";
//    $scope.debitstatusProxy = "./etz/proxy/2018/debitstatusProxy";
//    $scope.usersProxy = "./etz/proxy/2018/usersProxy";
//    $scope.icgcProxy = "./etz/proxy/2018/icgcProxy";
//    $scope.gcbMerchantProxy = "./etz/proxy/2018/gcbMerchantProxy";
//    $scope.gcbMerchantPayReportsProxy = "./etz/proxy/2018/gcbMerchantPayReportsProxy";
//    $scope.MerchantPayProxy = "./etz/proxy/2018/MerchantPayProxy";
//    $scope.cmsRequestProxy = "./etz/proxy/2018/cmsRequestProxy";
//    $scope.verifyProxy = "./etz/proxy/2018/verifyProxy";
//    $scope.feepaymentProxy = "./etz/proxy/2018/feepaymentProxy";
//    $scope.corporatepayProxy = "./etz/proxy/2018/corporatepayProxy";
//    $scope.gcbOnboardingProxy = "./etz/proxy/2018/gcbOnboardingProxy";
//    $scope.gipProxy = "./etz/proxy/2018/gipProxy";
//    $scope.GcbAltProxy = "./etz/proxy/2018/GcbAltProxy";
//    $scope.ovaUploadProxy = "./etz/proxy/2018/ovaUploadProxy";
//    $scope.kycUploadProxy = "./etz/proxy/2019/kycUploadProxy";
//    $scope.mobileAppActivateProxy = "./etz/proxy/2018/mobileAppActivateProxy";
//    $scope.momocardRequestProxy = "./etz/proxy/2019/momocardRequestProxy";
//    $scope.vasUrl = "./vasgate";
//    $scope.mtnOvaUploadUrl = "http://localhost:8084/ovaUpload/api/sendfile";
//    $scope.feeCollectionProxy = "./etz/proxy/2018/feeCollectionProxy";
//    $scope.teraRefundProxy = "./etz/proxy/2019/refundProxy";
//    $scope.showProgressBar = false;
//    $scope.showProgressBarError = false;
//    $scope.showFilterBox = true;
//    $scope.showCardUpdateBox = false;
//    $scope.cardUpdateResultView = false;
//    $scope.binUpdateResultView = false;
//    $scope.showagents = false;
//    $scope.rowsPerPage = "100";
//    $scope.pageNumber = 1;
//    $scope.fgRecordsLastPage = "";
//    $scope.fgRecordsTotalCount = 0;
//    $scope.fgRecordsTotalAmount = 0;
//    $scope.fgRecordsTotalFailedAmount = 0;
//    $scope.OverallTotalTrans = 0;
//    $scope.OverallTotalVat = 0;
//    $scope.OverallTotalAmount = 0;
//    $scope.OverallEtzCom = 0;
//    $scope.OverallGCBCom = 0;
//    $scope.OverallTotalDisVat = 0;
//    $scope.OverallVatAddEtz = 0;
//    $scope.OverallDiscount = 0;
//    $scope.TotalDebitAmount = 0;
//    $scope.TotalCreditAmount = 0;
//    $scope.pageNumber2 = 1;
//    $scope.fgRecordsLastPage2 = "";
//    $scope.fgRecordsTotalCount2 = 0;
//    $scope.fgRecordsTotalAmount1 = 0;
//    $scope.fgRecordsTotalFee = 0;
//    $scope.GCBTotalTrans = "";
//    $scope.balance = "n/a";
//    $scope.lastBalanceDate = "n/a";
//    $scope.TmcRecordsLastPage = "";
//    $scope.TmcRecordsTotalCount = 0;
//    $scope.TmcRecordsTotalAmount = 0;
//    $scope.TmcRecordsTotalFee = 0;
//    $scope.showCardUpdateBox = false;
//    $scope.cardUpdateResultView = false;
//    $scope.cardnum = "";
//    $scope.mobile_no = "";
//    $scope.reason = "";
//    $scope.showgcbmerchantpayreport = "false";
//    $scope.specificReason = false;
//    $scope.specificEnhancement = false;
//    $scope.specificOption = 'New';
//    $scope.hotlistreason = "";
//    $scope.cmstype = "ALL";
//    $scope.card_number = "";
//    $scope.updateCms = false;
//    $scope.IdType = "";
//    $scope.binNumber = "";
//    $scope.newBinNumber = "";
//    $scope.custAccount = "";
//    $scope.kycTranstype = "Minimum";
//    $scope.schoolType = "ALL";
//    $scope.reference = "";
//    $scope.paymentId = "";
//    $scope.paymentDesc = "";
//    $scope.batchId = "";
//    $scope.cpName = "";
//    $scope.cpStatus = "A";
//    $scope.searchPaymentId = 'false';
//    $scope.showCPayTransView = false;
//    $scope.OBStatus = "NM";
//    $scope.OBBank = "68";
//    $scope.rowsPerPage = "100";
//    $scope.pageNumber = 1;
//    $scope.fgRecordsLastPage = "";
//    $scope.fgRecordsTotalCount = 0;
//    $scope.rowsPerPage1 = "50";
//    $scope.pageNumber1 = 1;
//    $scope.fgRecordsLastPage1 = "";
//    $scope.fgRecordsTotalCount1 = 0;
//    $scope.rowsPerPage2 = "50";
//    $scope.pageNumber2 = 1;
//    $scope.fgRecordsLastPage2 = "";
//    $scope.fgRecordsTotalCount2 = 0;
//    $scope.rowsPerPage3 = "50";
//    $scope.pageNumber3 = 1;
//    $scope.fgRecordsLastPage3 = "";
//    $scope.fgRecordsTotalCount3 = 0;
//    $scope.rowsPerPage4 = "50";
//    $scope.pageNumber4 = 1;
//    $scope.fgRecordsLastPage4 = "";
//    $scope.fgRecordsTotalCount4 = 0;
//    $scope.updateRequest = false;
//    $scope.showPinSelect = false;
//    $scope.pinRequest = false;
//    $scope.rectifyRectifyRequest = false;
//    $scope.updateRectifyRequest = false;
//    $scope.showOBRequests = false;
//    $scope.showOBOkRequests = false;
//    $scope.showOBNotOkRequests = false;
//    $scope.showOBNotFoundRequests = false;
//    $scope.scbType = "ALL";
//    $scope.gcbmomotype = "B2W";
//    $scope.enhancementType = "Medium";
//    $scope.description = "";
//    $scope.IdName = "";
//    $scope.pageReady = false;
//    $scope.showUserTypes = false;
//    $scope.showKycUploadBox = false;
//    $scope.usernameAvailable = false;
////    var ws;
////
////    $scope.connect = function () {
////
////        var host = document.location.host;
////        var pathname = document.location.pathname;
////
////        ws = new WebSocket("ws://" + host + pathname + "chat/" +  $scope.profile.user_id);
////
////        ws.onmessage = function (event) {
//////        var log = document.getElementById("log");
////            console.log(event.data);
////            var message = JSON.parse(event.data);
////            var toastr;
////            toastr["success"](message.from, message.content);
//////        log.innerHTML += message.from + " : " + message.content + "\n";
////        };
////    };
////
////    $scope.send = function () {
////        var content = "hello";
////        var json = JSON.stringify({
////            "content": content
////        });
////
////        ws.send(json);
////    };
////    $scope.mee = function(){
////        alert("year");
////    };
//
//    $scope.initInfo = function () {
//        $http.get('./MerchantsInfo?action=fundgate').then(
//                function (response) {
//                    $scope.merchantList = response.data;
//                }
//        );
//        $http.get('./MerchantsInfo?action=vasgate').then(
//                function (response) {
//                    $scope.vasgateMerchantList = response.data;
//                }
//        );
//        $http.get('./MerchantsInfo?action=banks').then(
//                function (response) {
//                    $scope.banksList = response.data;
//                }
//        );
//        $http.get('./MerchantsInfo?action=channels').then(
//                function (response) {
//                    $scope.channelsList = response.data;
//                }
//        );
//        $http.get('./MerchantsInfo?action=wcMerchants').then(
//                function (response) {
//                    $scope.wcMerchantList = response.data;
//                }
//        );
////        $http.get('./MerchantsInfo?action=tmc').then(
////                function (response) {
////                    $scope.TMCNodeList = response.data;
////                }
////        );
//        $http.get('./MerchantsInfo?action=bankNodes').then(
//                function (response) {
//                    $scope.TMCBankNodeList = response.data;
//                    $scope.BankNodesSet();
//                }
//        );
////        $http.get('./MerchantsInfo?action=GCBTotalTrans').then(
////                function (response) {
////                    $scope.GCBTotalTrans = response.data;
////                    $scope.GCBTotalTransDate = moment().format('DD-MM-YY H:mm');
////                }
////        );
//        $http.get('./MerchantsInfo?action=BMerchantList').then(
//                function (response) {
//                    $scope.BMerchantList = response.data;
//                    $scope.BankNodesSet();
//                }
//        );
////        $scope.getBankNodes();
//
//    };
//    $scope.getCurrentLocation = function () {
//        $scope.currentPageLocation = $location.path().substring(1);
//    };
//
//    $scope.getFGBalance = function () {
//        $http.get('./MerchantsInfo?action=balance&terminalId=' + $scope.merchant).then(
//                function (response) {
//                    $scope.balance = response.data;
//                    $scope.lastBalanceDate = moment().format('DD-MM-YY H:mm');
//                }
//        );
//    };
//    $scope.getSurfBalance = function () {
//        $http.get('./surfBalance').then(
//                function (response) {
//                    $scope.surfBalance = response.data;
//                    $scope.surflastBalanceDate = moment().format('DD-MM-YY H:mm');
//                }
//        );
//    };
//    $scope.getTigoBalance = function () {
//        $http.get('./tigoBalance').then(
//                function (response) {
//                    $scope.tigoBalance = response.data;
//                    $scope.tigolastBalanceDate = moment().format('DD-MM-YY H:mm');
//                }
//        );
//        $http.get('./terapayTigoBalance').then(
//                function (response) {
//                    $scope.terapaytigoBalance = response.data;
//                }
//        );
//    };
//    $scope.getVodaTopupBalance = function () {
//        $http.get('./vodafoneTopup').then(
//                function (response) {
//                    $scope.vodaTopupBalance = response.data;
//                    $scope.vodaTopupBalancelastBalanceDate = moment().format('DD-MM-YY H:mm');
//                }
//        );
//    };
//    $scope.getGCBTotal = function () {
//        $http.get('./MerchantsInfo?action=GCBTotalTrans').then(
//                function (response) {
//                    $scope.GCBTotalTrans = response.data;
//                    $scope.GCBTotalTransDate = moment().format('DD-MM-YY H:mm');
//                }
//        );
//    };
//    $scope.updateApp = function () {
//        $scope.getProfile();
//        $scope.getFGBalance();
////        $scope.getGCBTotal();
//
//    };
//
//
//    $scope.logout = function () {
//        location.href = "./Logout";
//    };
//
//    $scope.fundgateBank = function () {
//        return false;
//    };
//    $scope.reloadFGTrxns = function () {
//        $scope.endDate = moment().format('YYYY-MM-DD 23:59');
//        $scope.getFGTransactions(1);
//    };
//    $scope.reloadFGInvestigation = function () {
//        $scope.endDate = moment().format('YYYY-MM-DD 23:59');
//        $scope.getFGInvestigation(1);
//    };
//    $scope.reloadFGReports = function () {
//        $scope.endDate = moment().format('YYYY-MM-DD 23:59');
//        $scope.getFGReport(1);
//    };
//    $scope.reloadVasBillTrxns = function () {
//        $scope.endDate = moment().format('YYYY-MM-DD 23:59');
//        $scope.getVasBillTrxn(1);
//    };
//    $scope.reloadBarUBPTrxns = function () {
//        $scope.endDate = moment().format('YYYY-MM-DD 23:59');
//        $scope.getBarUBPTrxn(1);
//    };
//    $scope.reloadVtuTrxns = function () {
//        $scope.endDate = moment().format('YYYY-MM-DD 23:59');
//        $scope.getVtuTrxns(1);
//    };
//    $scope.reloadWebconTrxns = function () {
//        $scope.endDate = moment().format('YYYY-MM-DD 23:59');
//        $scope.getWebconTrnxs(1);
//    };
//    $scope.reloadMomoTrxns = function () {
//        $scope.endDate = moment().format('YYYY-MM-DD 23:59');
//        $scope.getMomoTrxns(1);
//    };
//    $scope.reloadMomoStatus = function () {
//        $scope.endDate = moment().format('YYYY-MM-DD 23:59');
//        $scope.getMomoStatus(1);
//    };
//    $scope.reloadDebitStatus = function () {
//        $scope.endDate = moment().format('YYYY-MM-DD 23:59');
//        $scope.getDebitStatus(1);
//    };
//    $scope.reloadSasTrxns = function () {
//        $scope.endDate = moment().format('YYYY-MM-DD 23:59');
//        $scope.getSasTrxns(1);
//    };
//    $scope.reloadUssdTrxns = function () {
//        $scope.endDate = moment().format('YYYY-MM-DD 23:59');
//        $scope.getUssdTrxns(1);
//    };
//    $scope.reloadJPTrxns = function () {
//        $scope.endDate = moment().format('YYYY-MM-DD 23:59');
//        $scope.getJustPayTrxns(1);
//    };
//    $scope.reloadTMCTrxns = function () {
//        $scope.endDate = moment().format('YYYY-MM-DD 23:59');
//        $scope.getTMCTrxns(1);
//    };
//    $scope.reloadBillPaymtTrxns = function () {
//        $scope.endDate = moment().format('YYYY-MM-DD 23:59');
//        $scope.getBillPaymentTrxns(1);
//    };
//    $scope.reloadKweseTrxns = function () {
//        $scope.endDate = moment().format('YYYY-MM-DD 23:59');
//        $scope.getKweseTvTrxns(1);
//    };
//    $scope.reloadBOGswitchTrxns = function () {
//        $scope.endDate = moment().format('YYYY-MM-DD 23:59');
//        $scope.getBOGswitchTrxns(1);
//    };
//    $scope.reloadfgMerchantBalance = function () {
//        $scope.getFundgateBalanceReport(1);
//    };
//    $scope.reloadkycReport = function () {
//        $scope.getKYCReport(1);
//    };
//    $scope.reloadBusyTrxns = function () {
//        $scope.endDate = moment().format('YYYY-MM-DD 23:59');
//        $scope.getBusyBillTrxn(1);
//    };
//    $scope.reloadGWCLTrxns = function () {
//        $scope.endDate = moment().format('YYYY-MM-DD 23:59');
//        $scope.getGWCLBillTrxn(1);
//    };
//    $scope.reloadFGMomoTrxns = function () {
//        $scope.endDate = moment().format('YYYY-MM-DD 23:59');
//        $scope.getFGMomoTransactions(1);
//    };
//    $scope.reloadElacTrxns = function () {
//        $scope.endDate = moment().format('YYYY-MM-DD 23:59');
//        $scope.getElacBillTrxn(1);
//    };
//    $scope.reloadMobileInvestigations = function () {
//        $scope.endDate = moment().format('YYYY-MM-DD 23:59');
//        $scope.getMobileInvestigation(1);
//    };
//    $scope.reloadGCBAgents = function () {
//        $scope.endDate = moment().format('YYYY-MM-DD 23:59');
//        $scope.getGCBAgents(1);
//    };
//    $scope.reloadAgentTransaction = function () {
//        $scope.endDate = moment().format('YYYY-MM-DD 23:59');
//        $scope.getAgentTransaction(1);
//    };
//    $scope.reloadTopupSales = function () {
//        $scope.endDate = moment().format('YYYY-MM-DD 23:59');
//        $scope.getTopupSales(1);
//    };
//    $scope.reloadICGCTrxns = function () {
//        $scope.endDate = moment().format('YYYY-MM-DD 23:59');
//        $scope.getICGCTrxn(1);
//    };
//    $scope.reloadFeesCollection = function () {
//        $scope.endDate = moment().format('YYYY-MM-DD 23:59');
//        $scope.getFeesCollection(1);
//    };
//    $scope.reloadGCBMerchantTrxns = function () {
//        $scope.endDate = moment().format('YYYY-MM-DD 23:59');
//        $scope.getGCBMerchantTrxn(1);
//    };
//    $scope.reloadGCBMerchantPayReports = function () {
//        $scope.endDate = moment().format('YYYY-MM-DD 23:59');
//        $scope.getGCBMerchantPayReports(1);
//    };
//    $scope.reloadMerchantTrxns = function () {
//        $scope.endDate = moment().format('YYYY-MM-DD 23:59');
//        $scope.getMerchantTrxn(1);
//    };
//    $scope.reloadFeePayments = function () {
//        $scope.endDate = moment().format('YYYY-MM-DD 23:59');
//        $scope.getFeePaymentTrxn(1);
//    };
//    $scope.reloadCorporatePayTrxs = function () {
//        $scope.endDate = moment().format('YYYY-MM-DD 23:59');
//        $scope.getCorporatePayTrxns(1);
//    };
//    $scope.reloadGcbOnboardRequests = function () {
//        $scope.endDate = moment().format('YYYY-MM-DD 23:59');
//        $scope.getGcbOnboardRequests(1);
//    };
//
//    $scope.reloadGIPInvestigation = function () {
//        $scope.endDate = moment().format('YYYY-MM-DD 23:59');
//        $scope.getGIPInvestigation(1);
//    };
//    $scope.reloadOvatrxs = function () {
//        $scope.getOvatrxs(1);
//    };
//
//    $scope.reloadMomoCardRequests = function () {
//        $scope.endDate = moment().format('YYYY-MM-DD 23:59');
//        $scope.getMomoCardRequests(1);
//    };
//
//    $scope.getFGTransactions = function (page, paging) {
//        $scope.updateApp();
//        if ($('#start-date').val() && $('#end-date').val()) {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.url1;
//            if (paging) {
//                url = './FundGateTransactions?action=paging';
//            }
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                $scope.pageNumber = $scope.fgRecordsLastPage;
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "startDate": $scope.startDate,
//                    "endDate": $scope.endDate,
//                    "merchant": $scope.merchant,
//                    "product": $scope.product,
//                    "status": $scope.status,
//                    "record": $scope.record,
//                    "account_no": $('#account_no').val(),
//                    "service": "transactions",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.fgTrxResp = response.data;
//                if ($scope.fgTrxResp.code === "00") {
//                    if (paging) {
//                        $scope.fgTrxs = JSON.parse($scope.fgTrxResp.data);
//                        $scope.getFGTransactionsCount();
//                    } else {
//                        $scope.fgTrxs = JSON.parse($scope.fgTrxResp.data);
//                        $scope.getFGTransactionsCount();
//                        $scope.getFGTransactionsTotalAmount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert('Select values for start and end date');
//        }
//    };
//
//    $scope.getFGCardTransactions = function (page, paging) {
//        $scope.updateApp();
//        if ($('#start-date').val() && $('#end-date').val()) {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.url1;
//            if (paging) {
//                url = './FundGateCardTransactions?action=paging';
//            }
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                $scope.pageNumber = $scope.fgRecordsLastPage;
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "startDate": $scope.startDate,
//                    "endDate": $scope.endDate,
//                    "merchant": $scope.merchant,
//                    "product": $scope.product,
//                    "status": $scope.status,
//                    "record": $scope.record,
//                    "account_no": $('#account_no').val(),
//                    "service": "cardtransactions",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.fgTrxResp = response.data;
//                if ($scope.fgTrxResp.code === "00") {
//                    if (paging) {
//                        $scope.fgTrxs = JSON.parse($scope.fgTrxResp.data);
//                        $scope.getFGTransactionsCount();
//                    } else {
//                        $scope.fgTrxs = JSON.parse($scope.fgTrxResp.data);
//                        $scope.getFGTransactionsCount();
//                        $scope.getFGTransactionsTotalAmount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert('Select values for start and end date');
//        }
//    };
//
//
//    $scope.searchFGTransaction = function () {
//        if ($scope.searchTrxnKey.length > 12) {
//            $scope.showLoader = true;
//            $('.reload').addClass('fa-spin text-info');
//            $http({
//                url: $scope.url1,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "searchKey": $scope.searchTrxnKey,
//                    "service": "search"
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.fgTrxResp = response.data;
//                if ($scope.fgTrxResp.code === "00") {
//                    $scope.fgTrxs = JSON.parse($scope.fgTrxResp.data);
//                    $scope.getFGTransactionsCount();
//                } else if ($scope.fgTrxResp.code === "01") {
//                    $scope.fgTrxs = "";
//                } else {
//                    $scope.fgTrxs = "";
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert("Length of Transaction ID must be greater than 12");
//        }
//    };
//    $scope.getFGTransactionsCount = function () {
//        $http.get('./FundGateTransactions?action=count').then(
//                function (response) {
//                    $scope.fgRecordsTotalCount = JSON.parse(response.data);
//                    $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//                    if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//                        $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//                    }
//                }
//        );
//    };
//    $scope.getFGTransactionsTotalAmount = function () {
//        $http.get('./FundGateTransactions?action=totalAmount').then(
//                function (response) {
//                    $scope.fgRecordsTotalAmount = response.data;
//                });
//    };
//    $scope.getFGReport = function (page, paging) {
//        $scope.updateApp();
//        if ($('#start-date').val() && $('#end-date').val()) {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.url1;
//            if (paging) {
//                url = './FundGateReports?action=paging';
//            }
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                $scope.pageNumber = $scope.fgRecordsLastPage;
//            $http({
//                url: $scope.url1,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "startDate": $scope.startDate,
//                    "endDate": $scope.endDate,
//                    "product": $scope.product,
//                    "account_no": $('#account_no').val(),
//                    "service": "report",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.fgReportResp = response.data;
//                if ($scope.fgReportResp.code === "00") {
//                    if (paging) {
//                        $scope.fgReports = JSON.parse($scope.fgReportResp.data);
//                        $scope.getFGReportsCount();
//                    } else {
//                        $scope.fgReports = JSON.parse($scope.fgReportResp.data);
//                        $scope.getFGReportsCount();
//                        $scope.getFGReportsTotalAmount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert('Select values for start and end date');
//        }
//    };
//    $scope.searchFGReport = function () {
//        if ($scope.searchTrxnKey.length > 12) {
//            $scope.showLoader = true;
//            $('.reload').addClass('fa-spin text-info');
//            $http({
//                url: $scope.url1,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "searchKey": $scope.searchTrxnKey,
//                    "service": "searchReport"
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $scope.fgReportResp = response.data;
//                if ($scope.fgReportResp.code === "00") {
//                    $scope.fgReports = JSON.parse($scope.fgReportResp.data);
//                } else if ($scope.fgReportResp.code === "01") {
//                    $scope.fgReports = "";
//                } else {
//                    $scope.fgReports = "";
//                }
//
//
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert("Length of Transaction ID must be greater than 12");
//        }
//    };
//    $scope.getFGReportsCount = function () {
//        $http.get('./FundGateReports?action=count').then(
//                function (response) {
//                    $scope.fgRecordsTotalCount = JSON.parse(response.data);
//                    $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//                    if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//                        $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//                    }
//                }
//        );
//    };
//    $scope.getFGReportsTotalAmount = function () {
//        $http.get('./FundGateReports?action=totalAmount').then(
//                function (response) {
//                    $scope.fgRecordsTotalAmount = response.data;
//                });
//    };
//    $scope.getFGInvestigation = function (page, paging) {
//        $scope.updateApp();
//        if ($('#start-date').val() && $('#end-date').val()) {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.url3;
//            if (paging) {
//                url = './FundGateInvestigations?action=ipaging';
//            }
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                $scope.pageNumber = $scope.fgRecordsLastPage;
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "startDate": $scope.startDate,
//                    "endDate": $scope.endDate,
////                    "merchant": $scope.merchant,
////                    "product": $scope.product,
////                    "status": $scope.status,
////                    "account_no": $('#account_no').val(),
//                    "service": "investigation",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.fgTrxResp = response.data;
//                if ($scope.fgTrxResp.code === "00") {
//                    if (paging) {
//                        $scope.fgTrxs = JSON.parse($scope.fgTrxResp.data);
//                        $scope.getFGInvestigationCount();
//                    } else {
//                        $scope.fgTrxs = JSON.parse($scope.fgTrxResp.data);
//                        $scope.getFGInvestigationCount();
//                        $scope.getFGInvestigationTotalAmount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert('Select values for start and end date');
//        }
//    };
//    $scope.checkHotlist = function (status, card) {
////        console.log("status: " + status + " " + "cardnum: " + card);
////        if ($scope.formType === "HOTLIST") {
//        if (status === '0') {
//            var element = document.getElementById(card);
//            element.classList.add("btn-success");
//            return "HOTLIST";
//        } else {
//            var element = document.getElementById(card);
//            element.classList.add("btn-warning");
//            return "DEHOTLIST";
//        }
////        }
////        return "UPDATE";
//
//    };
//
//    $scope.hideSpecificOption = function () {
//        $scope.specificReason = false;
//        $scope.reason = '';
//        $scope.hideSpecificEnhancement();
//    };
//
//    $scope.showSpecificOption = function () {
//        $scope.hideSpecificEnhancement();
//        $scope.specificReason = true;
//        $scope.reason = '';
//    };
//
//
//    $scope.hideSpecificEnhancement = function () {
//        $scope.specificEnhancement = false;
//    };
//
//    $scope.showSpecificEnhancement = function () {
//        $scope.showSpecificOption();
//        $scope.specificEnhancement = true;
//        $scope.specificReason = false;
//        $scope.reason = '';
//    };
//
//    $scope.changeCMSOption = function () {
//        $scope.specificOption = $('#specificOption').val();
//    };
//
//    $scope.showOtherReason = function () {
//
//    };
//
//    $scope.checkHotlist1 = function (status, card) {
//        console.log("status: " + status + " " + "cardnum: " + card);
//        if ($scope.formType === "HOTLIST") {
//            if (status === '0') {
//                var element = document.getElementById(card);
//                element.classList.add("btn-success");
//                return "HOTLIST";
//            } else {
//                var element = document.getElementById(card);
//                element.classList.add("btn-warning");
//                return "DEHOTLIST";
//            }
//        }
//        return "UPDATE";
//
//    };
//
//
//    $scope.hotlistCard = function () {
//        $scope.updateApp();
//        if ($('#reason').val()) {
//            $http({
//                url: $scope.crdUpdateProxy,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "card_num": $scope.card_num_to_update,
//                    "reason": $scope.reason,
////                "firstName": $('#firstName').val(),
////                "lastName": $('#lastName').val(),
////                "from_source": $('#from_source2').val(),
////                "street": $('#street').val(),
////                "email": $('#email').val(),
////                "city": $('#city').val(),
////                "boundwork": $('#bound_work').val(),
////                "missed": $('#pin_missed').val(),
////                "hotlist": $('#user_hotlist').val(),
////                "locked": $('#user_locked').val(),
//                    "service": $scope.service
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                var rsp = response.data;
//                $scope.cardUpdateResultView = true;
//                $('.scrollbar').animate({
//                    scrollTop: 0}, 500, function () {
//                });
//                if (rsp.trim() === "Success") {
//                    $('#updateResp').addClass("bg-success py-2");
//                    $('#updateResp').removeClass("bg-danger");
//                    $('#updateResp').html(rsp);
//                } else {
//                    $('#updateResp').addClass("bg-danger py-2");
//                    $('#updateResp').removeClass("bg-success");
//                    $('#updateResp').html(rsp);
//                }
//            }, function error(response) {
//            });
//        } else {
//            alert('Please provide your reason for this!!!');
//        }
//
//    };
////    $scope.verifyTrans = function (reference, bankcode, paymenttype, clientCode) {
////        //Todo
//////        alert(reference + bankcode + paymenttype);
////        $('#status-update-btn').html("Update Status");
////        $('#status-update-btn').prop('disabled', false);
////        $scope.ref = reference;
////        $scope.clientCodex = clientCode;
////        $scope.showLoader = true;
////        $('.reload').addClass('fa-spin text-info');
//////        var Request = new Object();
////        $scope.showLoader = true;
////        $scope.showFilterBox = false;
////        $http({
////            url: $scope.statusProxy,
////            method: 'POST',
////            data: $httpParamSerializerJQLike({
////                "reference": reference,
////                "bankCode": bankcode,
////                "paymentType": paymenttype,
////                "service": "check"
////            }),
////            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
////        }).then(function success(response) {
////
////            $scope.showLoader = false;
////            $('.reload').removeClass('fa-spin text-info');
////            $scope.verifyTrxs = response.data;
////            $scope.showDetailsBox = true;
////        },
////                function error(response) {
////                    $scope.showLoader = false;
////                    $('.reload').removeClass('fa-spin text-info');
////                    alert('Connection or Server Error. Try again');
////                });
////    };
////    
//    $scope.toggleMobileAppStatus = function (client_id, phone, state) {
//        $scope.showLoader = true;
//        $('.reload').addClass('fa-spin text-info');
////        var Request = new Object();
//        $scope.showLoader = true;
//        $scope.showFilterBox = false;
//
//        $http({
//            url: $scope.mobileAppActivateProxy,
//            method: 'POST',
//            data: $httpParamSerializerJQLike({
//                "clientId": client_id,
//                "mobile_no": phone,
//                "state": state,
//                "service": "toggle"
//            }),
//            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//        }).then(function success(response) {
//            $scope.showLoader = false;
//            $('.reload').removeClass('fa-spin text-info');
//            $scope.toggleMobileAppActivationResp = response.data;
//            console.log('DAta: ' + $scope.toggleMobileAppActivationResp);
//            if ($scope.toggleMobileAppActivationResp.code === "00") {
//                $scope.responseOnActivation = JSON.parse($scope.toggleMobileAppActivationResp.data);
//                console.log('GIPresp: ' + $scope.responseOnActivation.response);
//
//                $('#toggle' + client_id).html($scope.responseOnActivation.status);
//
//            } else {
//
//            }
////            $scope.showDetailsBox = true;
//        },
//                function error(response) {
//                    $scope.showLoader = false;
//                    $('.reload').removeClass('fa-spin text-info');
//                    alert('Connection or Server Error. Try again');
//                });
//    };
//
//
//    $scope.updateMomoCardRequestStatus = function (id, phone) {
//        $scope.showLoader = true;
//        $('.reload').addClass('fa-spin text-info');
////        var Request = new Object();
//        $scope.showLoader = true;
//        $scope.showFilterBox = false;
//
//        $http({
//            url: $scope.momocardRequestProxy,
//            method: 'POST',
//            data: $httpParamSerializerJQLike({
//                "subscriberId": phone,
//                "service": "update"
//            }),
//            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//        }).then(function success(response) {
//            $scope.showLoader = false;
//            $('.reload').removeClass('fa-spin text-info');
//            $scope.updateMomoCardRequestResp = response.data;
//            console.log('DAta: ' + $scope.updateMomoCardRequestResp);
//            if ($scope.updateMomoCardRequestResp.code === "00") {
//                if ($scope.updateMomoCardRequestResp.data === 'TREATED') {
//                    $('#toggle' + id).html($scope.updateMomoCardRequestResp.data);
//                    $('#vst' + id).css('display', 'none');
//                } else {
//                    $('#toggle' + id).html($scope.updateMomoCardRequestResp.data);
//                }
//            } else {
//
//            }
////            $scope.showDetailsBox = true;
//        },
//                function error(response) {
//                    $scope.showLoader = false;
//                    $('.reload').removeClass('fa-spin text-info');
//                    alert('Connection or Server Error. Try again');
//                });
//    };
//
//    $scope.getMomoCardRequests = function (page, paging) {
//        $scope.getProfile();
//        if ($('#start-date').val() && $('#end-date').val()) {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.momocardRequestProxy;
//
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                $scope.pageNumber = $scope.fgRecordsLastPage;
////            console.log('DATA: ' + $scope.pageNumber + ' : ' + $scope.fgRecordsLastPage);
//            if (paging) {
//
////                console.log('DATA: ' + page + ' : ' + $scope.fgRecordsLastPage);
//                $scope.begin = (page - 1) * parseInt($scope.rowsPerPage);
//                $scope.end = parseInt($scope.begin) + parseInt($scope.rowsPerPage);
////                console.log('begin: ' + $scope.begin + ' : ' + $scope.end);
//                $scope.filteredMomoCardReqs = $scope.webconTrxs.slice($scope.begin, $scope.end);
//                $scope.getMomoCardReqsTotalCount();
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//
//            } else {
//                $http({
//                    url: url,
//                    method: 'POST',
//                    data: $httpParamSerializerJQLike({
//                        "startDate": $scope.startDate,
//                        "endDate": $scope.endDate,
//                        "subscriberId": $scope.mobile_no || "",
//                        "transType": $scope.status,
//                        "location": $scope.description,
//                        "service": "requests",
//                        "pageNumber": $scope.pageNumber,
//                        "rowsPerPage": $scope.rowsPerPage
//                    }),
//                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                }).then(function success(response) {
//                    $scope.showLoader = false;
//                    $('.reload').removeClass('fa-spin text-info');
//                    $scope.momoCardReqResp = response.data;
//                    if ($scope.momoCardReqResp.code === "00") {
//                        $scope.momoCardReqs = JSON.parse($scope.momoCardReqResp.data);
//                        $scope.getMomoCardReqsTotalCount();
//                        $scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage);
//                        $scope.end = parseInt($scope.begin + $scope.rowsPerPage);
//                        $scope.filteredMomoCardReqs = $scope.momoCardReqs.slice($scope.begin, $scope.end);
//                    }
//                }, function error(response) {
//                    $scope.showLoader = false;
//                    $('.reload').removeClass('fa-spin text-info');
//                    alert('Connection or Server Error. Try again');
//                });
//            }
//        } else {
//            alert('Select values for start and end date');
//        }
//
//    };
//
//
//    $scope.searchMomoCardRequest = function () {
//        if ($scope.searchTrxnKey.length > 6) {
//            $scope.showLoader = true;
//            $('.reload').addClass('fa-spin text-info');
//            $http({
//                url: $scope.momocardRequestProxy,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "searchKey": $scope.searchTrxnKey,
//                    "service": "search"
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.momoCardReqResp = response.data;
//                if ($scope.momoCardReqResp.code === "00") {
//                    $scope.momoCardReqs = JSON.parse($scope.momoCardReqResp.data);
//                    $scope.getMomoCardReqsTotalCount();
//                    $scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage);
//                    $scope.end = parseInt($scope.begin + $scope.rowsPerPage);
//                    $scope.filteredMomoCardReqs = $scope.momoCardReqs.slice($scope.begin, $scope.end);
//                } else if ($scope.momoCardReqResp.code === "01") {
//                    $scope.momoCardReqs = "";
//                } else {
//                    $scope.momoCardReqs = "";
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert("Length of Transaction ID must be greater than 6");
//        }
//    };
//
//    $scope.getMomoCardReqsTotalCount = function () {
//        $scope.fgRecordsTotalCount = $scope.momoCardReqs.length;
//        $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//        if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//            $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//        }
//    };
//
//    $scope.verifyGipTrans = function (reference) {
//        $scope.ref = reference;
//        $scope.showLoader = true;
//        $('.reload').addClass('fa-spin text-info');
////        var Request = new Object();
//        $scope.showLoader = true;
//        $scope.showFilterBox = false;
//        $http({
//            url: $scope.gipProxy,
//            method: 'POST',
//            data: $httpParamSerializerJQLike({
//                "uniqueTransId": reference,
//                "service": "gipcheck"
//            }),
//            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//        }).then(function success(response) {
//            $scope.showLoader = false;
//            $('.reload').removeClass('fa-spin text-info');
//            $scope.verifyTrxsResp = response.data;
//            if ($scope.verifyTrxsResp.code === "00") {
//                $scope.verifyTrxs = JSON.parse($scope.verifyTrxsResp.data);
//                console.log('GIPresp: ' + $scope.verifyTrxs.ResponseStatus);
//            }
//            $scope.showDetailsBox = true;
//        },
//                function error(response) {
//                    $scope.showLoader = false;
//                    $('.reload').removeClass('fa-spin text-info');
//                    alert('Connection or Server Error. Try again');
//                });
//    };
//
//
//    $scope.showRefund = function (index) {
//        $scope.reference = $scope.teraTrxs[index].terra_id;
//        $scope.etzId = $scope.teraTrxs[index].etz_id;
//        $scope.originalAmount = $scope.teraTrxs[index].amount;
//        $scope.reason = $scope.teraTrxs[index].reversal_reason;
//        $scope.refundBtn = "REFUND";
//        $('#refundBtn').prop("disabled", false);
//        $scope.showDetailsBox = true;
//    };
//
//    $scope.refundTera = function () {
////        console.log($scope.refundStatus + ':' + $('#reverseAmount').val());
//        if ($scope.reference !== '' && $scope.originalAmount !== '' && $scope.refundType !== '' &&
//                (($('#reverseAmount').val() !== '' && $scope.refundType === "true") || ($('#reverseAmount').val() === '' && $scope.refundType !== "true"))) {
//            $scope.showLoader = true;
//            $('.reload').addClass('fa-spin text-info');
//            $scope.showFilterBox = false;
//            $http({
//                url: $scope.teraRefundProxy,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "terraTrxnID": $scope.reference,
//                    "etzID": $scope.etzId,
//                    "reversalStatus": $scope.refundStatus,
//                    "reverseAmount": $('#reverseAmount').val() || $scope.originalAmount,
//                    "partialReversal": $scope.refundType,
//                    "service": "refund"
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.teraRefundResp = response.data;
//                if ($scope.teraRefundResp.code === "00") {
//                    $scope.teraRefundTrx = JSON.parse($scope.teraRefundResp.data);
//                    console.log('GIPresp: ' + JSON.parse($scope.teraRefundTrx.responseData).amountReversed);
//                    $scope.refundBtn = $scope.teraRefundTrx.responseStatus;
////                    console.log('f: ' + $scope.refundBtn);
//                    if ($scope.refundBtn.trim() === "Success") {
//                        $('#refundBtn').addClass("bg-success py-2");
//                        $('#refundBtn').removeClass("bg-danger");
////                        $('#refundBtn').html(rsp);
////                        console.log('re: ' + JSON.parse($scope.teraRefundTrx.responseData).etzID + JSON.parse($scope.teraRefundTrx.responseData).refundStatus);
//                        $('#ar' + JSON.parse($scope.teraRefundTrx.responseData).etzID).html(JSON.parse($scope.teraRefundTrx.responseData).amountReversed);
//                        $('#rf' + JSON.parse($scope.teraRefundTrx.responseData).etzID).html(JSON.parse($scope.teraRefundTrx.responseData).refundStatus);
//                    } else {
//                        $('#refundBtn').addClass("bg-danger py-2");
//                        $('#refundBtn').removeClass("bg-success");
////                        $('#refundBtn').html(rsp);
//                    }
//                    $('#refundBtn').prop("disabled", true);
//                }
//            },
//                    function error(response) {
//                        $scope.showLoader = false;
//                        $('.reload').removeClass('fa-spin text-info');
//                        alert('Connection or Server Error. Try again');
//                    });
//        } else {
//            alert('Fill in requires fields');
//        }
//    };
//
//    $scope.checkTrans = function (status) {
//        if (status !== "SUCCESSFUL") {
//            return false;
//        }
//        return true;
//    };
////    $scope.updateMomoStatus = function (clientId, ref, status, clientcode) {
////        $('.reload').addClass('fa-spin text-info');
////        $('#status-update-btn').prop("disabled", true);
////        $('#status-update-btn').html("<i class='fa fa-spinner fa-spin btn-loader'></i> Updating...");
//////        twoToneButton.innerHTML = "Updating";
//////        twoToneButton.classList.add('spinning');
////
//////        var Request = new Object();
//////        $scope.showLoader = true;
////        $scope.showFilterBox = false;
////        $http({
////            url: $scope.statusProxy,
////            method: 'POST',
////            data: $httpParamSerializerJQLike({
////                "clientId": clientId,
////                "reference": ref,
////                "status": status,
////                "clientcode": clientcode,
////                "service": "update"
////            }),
////            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
////        }).then(function success(response) {
//////            $scope.showLoader = false;
////            $scope.updateResponse = response.data;
////            $('#vst' + ref).removeClass('btn-info');
////            $('#vst' + ref).addClass('btn-success');
////            $('#vst' + ref).html('UPDATED');
////            $('#vst' + ref).prop("disabled", true);
////            $('.reload').removeClass('fa-spin text-info');
////            $('#status-update-btn').html($scope.updateResponse.response);
////            $scope.showDetailsBox = true;
//////            twoToneButton.classList.remove('spinning');
//////            twoToneButton.innerHTML = "Update";
////        }, function error(response) {
//////                $scope.showLoader = false;                     
////            $('#status-update-btn').html("Update Status");
////            $('#status-update-btn').prop('disabled', false);
////            $('.reload').removeClass('fa-spin text-info');
////            alert('Connection or Server Error. Try again');
////        });
////    };
//    $scope.searchFGTransaction = function () {
//        if ($scope.searchTrxnKey.length > 12) {
//            $scope.showLoader = true;
//            $('.reload').addClass('fa-spin text-info');
//            $http({
//                url: $scope.url1,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "searchKey": $scope.searchTrxnKey,
//                    "service": "search"
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.fgTrxResp = response.data;
//                if ($scope.fgTrxResp.code === "00") {
//                    $scope.fgTrxs = JSON.parse($scope.fgTrxResp.data);
//                    $scope.getFGTransactionsCount();
//                } else if ($scope.fgTrxResp.code === "01") {
//                    $scope.fgTrxs = "";
//                } else {
//                    $scope.fgTrxs = "";
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert("Length of Transaction ID must be greater than 12");
//        }
//    };
//    $scope.getFGInvestigationCount = function () {
//        $http.get('./FundGateInvestigations?action=icount').then(
//                function (response) {
//                    $scope.fgRecordsTotalCount = JSON.parse(response.data);
//                    $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//                    if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//                        $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//                    }
//                }
//        );
//    };
//    $scope.getFGInvestigationTotalAmount = function () {
//        $http.get('./FundGateInvestigations?action=itotalAmount').then(
//                function (response) {
//                    $scope.fgRecordsTotalAmount = response.data;
//                });
//    };
//    $scope.getVasBillTrxn = function (page, paging) {
//        $scope.getProfile();
//        if ($('#start-date').val() && $('#end-date').val()) {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.vasbillProxy;
//            if (paging) {
//                url = './VasBillTransactions?action=paging';
//            }
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                $scope.pageNumber = $scope.fgRecordsLastPage;
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "startDate": $scope.startDate,
//                    "endDate": $scope.endDate,
//                    "uniqueTransId": $scope.uniqueTransId,
//                    "merchant": $scope.merchant,
//                    "bank": $scope.bank,
//                    "status": $scope.status,
//                    "channel": $scope.channel,
//                    "custAccount": $scope.custAccount,
//                    "service": "transactions",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.vasTrxResp = response.data;
//                if ($scope.vasTrxResp.code === "00") {
//                    if (paging) {
//                        $scope.vasTrxs = JSON.parse($scope.vasTrxResp.data);
//                        $scope.getVasBillCount();
//                    } else {
//                        $scope.vasTrxs = JSON.parse($scope.vasTrxResp.data);
//                        $scope.getVasBillCount();
//                        $scope.getVasBillTotalAmount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert('Select values for start and end date');
//        }
//    };
//    $scope.getTerapay = function (page, paging) {
//        $scope.getProfile();
//        if ($('#start-date').val() && $('#end-date').val()) {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.vasbillProxy;
//            if (paging) {
//                url = './VasBillTransactions?action=paging';
//            }
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                $scope.pageNumber = $scope.fgRecordsLastPage;
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "startDate": $scope.startDate,
//                    "endDate": $scope.endDate,
//                    "uniqueTransId": $scope.uniqueTransId,
//                    "merchant": $scope.merchant,
//                    "bank": $scope.bank,
//                    "status": $scope.status,
//                    "channel": $scope.channel,
//                    "custAccount": $scope.custAccount,
//                    "service": "transactions",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.vasTrxResp = response.data;
//                if ($scope.vasTrxResp.code === "00") {
//                    if (paging) {
//                        $scope.vasTrxs = JSON.parse($scope.vasTrxResp.data);
//                        $scope.getVasBillCount();
//                    } else {
//                        $scope.vasTrxs = JSON.parse($scope.vasTrxResp.data);
//                        $scope.getVasBillCount();
//                        $scope.getVasBillTotalAmount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert('Select values for start and end date');
//        }
//    };
//
//    $scope.searchVasBillTrxn = function () {
//        if ($scope.searchTrxnKey.length > 12) {
//            $scope.showLoader = true;
//            $('.reload').addClass('fa-spin text-info');
//            $http({
//                url: $scope.vasbillProxy,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "searchKey": $scope.searchTrxnKey,
//                    "service": "search"
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.vasTrxResp = response.data;
//                if ($scope.vasTrxResp.code === "00") {
//                    $scope.vasTrxs = JSON.parse($scope.vasTrxResp.data);
//                } else if ($scope.vasTrxResp.code === "01") {
//                    $scope.vasTrxs = "";
//                } else {
//                    $scope.vasTrxs = "";
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert("Length of Transaction ID must be greater than 12");
//        }
//    };
//    $scope.getVasBillCount = function () {
//        $http.get('./VasBillTransactions?action=count').then(
//                function (response) {
//                    $scope.fgRecordsTotalCount = JSON.parse(response.data);
//                    $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//                    if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//                        $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//                    }
//                }
//        );
//    };
//    $scope.getVasBillTotalAmount = function () {
//        $http.get('./VasBillTransactions?action=totalAmount').then(
//                function (response) {
//                    $scope.fgRecordsTotalAmount = response.data;
//                });
//    };
//
//
//    $scope.getFeePaymentTrxn = function (page, paging) {
//        $scope.getProfile();
//        if ($('#start-date').val() && $('#end-date').val()) {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.feepaymentProxy;
//            if (paging) {
//                url = './FeePaymentFunc?action=paging';
//            }
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                $scope.pageNumber = $scope.fgRecordsLastPage;
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "startDate": $scope.startDate,
//                    "endDate": $scope.endDate,
//                    "uniqueTransId": $scope.uniqueTransId,
//                    "merchant": $scope.schoolType,
//                    "custAccount": $scope.custAccount,
//                    "service": "transactions",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.vasTrxResp = response.data;
//                if ($scope.vasTrxResp.code === "00") {
//                    if (paging) {
//                        $scope.vasTrxs = JSON.parse($scope.vasTrxResp.data);
//                        $scope.getFeePaymentCount();
//                    } else {
//                        $scope.vasTrxs = JSON.parse($scope.vasTrxResp.data);
//                        $scope.getFeePaymentCount();
//                        $scope.getFeePaymentTotalAmount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert('Select values for start and end date');
//        }
//    };
//
//    $scope.getFeePaymentCount = function () {
//        $http.get('./FeePaymentFunc?action=count').then(
//                function (response) {
//                    $scope.fgRecordsTotalCount = JSON.parse(response.data);
//                    $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//                    if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//                        $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//                    }
//                }
//        );
//    };
//
//    $scope.getFeePaymentTotalAmount = function () {
//        $http.get('./FeePaymentFunc?action=totalAmount').then(
//                function (response) {
//                    $scope.fgRecordsTotalAmount = response.data;
//                });
//    };
//
//    $scope.searchFeePayment = function () {
//        if ($scope.searchTrxnKey.length > 8) {
//            $scope.showLoader = true;
//            $('.reload').addClass('fa-spin text-info');
//            $http({
//                url: $scope.feepaymentProxy,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "searchKey": $scope.searchTrxnKey,
//                    "service": "search"
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.vasTrxResp = response.data;
//                if ($scope.vasTrxResp.code === "00") {
//                    $scope.vasTrxs = JSON.parse($scope.vasTrxResp.data);
//                } else if ($scope.vasTrxResp.code === "01") {
//                    $scope.vasTrxs = "";
//                } else {
//                    $scope.vasTrxs = "";
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert("Length of Transaction ID must be greater than 8");
//        }
//    };
//
//    $scope.getBarUBPTrxn = function (page, paging) {
//        $scope.getProfile();
//        if ($('#start-date').val() && $('#end-date').val()) {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.barUBPProxy;
//            if (paging) {
//                url = './BarUBPTransactions?action=paging';
//            }
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                $scope.pageNumber = $scope.fgRecordsLastPage;
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "startDate": $scope.startDate,
//                    "endDate": $scope.endDate,
//                    "uniqueTransId": $scope.uniqueTransId,
//                    "transId": $scope.transId,
//                    "transType": $scope.transType,
//                    "status": $scope.status,
//                    "service": "transactions",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.ubpTrxResp = response.data;
//                if ($scope.ubpTrxResp.code === "00") {
//                    if (paging) {
//                        $scope.ubpTrxs = JSON.parse($scope.ubpTrxResp.data);
//                        $scope.getBarUBPTotalCount();
//                    } else {
//                        $scope.ubpTrxs = JSON.parse($scope.ubpTrxResp.data);
//                        $scope.getBarUBPTotalCount();
//                        $scope.getBarUBPTotalAmount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert('Select values for start and end date');
//        }
//    };
//    $scope.searchBarUBPTrxn = function () {
//        if ($scope.searchTrxnKey.length > 12) {
//            $scope.showLoader = true;
//            $('.reload').addClass('fa-spin text-info');
//            $http({
//                url: $scope.barUBPProxy,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "searchKey": $scope.searchTrxnKey,
//                    "service": "search"
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.ubpTrxResp = response.data;
//                if ($scope.ubpTrxResp.code === "00") {
//                    $scope.ubpTrxs = JSON.parse($scope.ubpTrxResp.data);
//                } else if ($scope.ubpTrxResp.code === "01") {
//                    $scope.ubpTrxs = "";
//                } else {
//                    $scope.ubpTrxs = "";
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert("Length of Transaction ID must be greater than 12");
//        }
//    };
//    $scope.mapBarUBPCode = function (code) {
//        if (code === "000") {
//            return "Transaction Successful";
//        } else if (code === "001") {
//            return "Transaction Unsuccessful";
//        } else if (code === "010") {
//            return "Invalid Payment Method";
//        } else if (code === "011") {
//            return "Invalid Utility code";
//        } else if (code === "012") {
//            return "Duplicate transid detected";
//        } else if (code === "013") {
//            return "Internal Error Occurred, please contact the service provider";
//        } else if (code === "014") {
//            return "Request Timeout";
//        } else if (code === "015") {
//            return "No transaction found";
//        } else if (code === "016") {
//            return "invalid details passed - school fees";
//        } else if (code === "017") {
//            return "invalid IP Address";
//        } else if (code === "099") {
//            return "Server error occurred/invalid entry";
//        } else if (!code) {
//            return "No response";
//        } else if (code === "null") {
//            return "No response";
//        }
//
//        return code;
//    };
//    $scope.getBarUBPTotalCount = function () {
//        $http.get('./BarUBPTransactions?action=count').then(
//                function (response) {
//                    $scope.fgRecordsTotalCount = JSON.parse(response.data);
//                    $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//                    if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//                        $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//                    }
//                }
//        );
//    };
//    $scope.getBarUBPTotalAmount = function () {
//        $http.get('./BarUBPTransactions?action=totalAmount').then(
//                function (response) {
//                    $scope.fgRecordsTotalAmount = response.data;
//                });
//    };
//    $scope.getMomoTrxns = function (page, paging) {
//        $scope.getProfile();
//        if ($('#start-date').val() && $('#end-date').val()) {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.momoProxy;
//            if (paging) {
//                url = './MomoTransactions?action=paging';
//            }
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                $scope.pageNumber = $scope.fgRecordsLastPage;
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "startDate": $scope.startDate,
//                    "endDate": $scope.endDate,
//                    "bank": $scope.bank,
//                    "bank_code": $scope.bank_code,
//                    "uniqueTransId": $scope.uniqueTransId,
//                    "from_source": $scope.from_source,
//                    "subscriberId": $scope.subscriberId,
//                    "transType": $scope.transType,
//                    "card_num": $scope.card_num,
//                    "status": $scope.status,
//                    "merchantName": $scope.merchant,
//                    "optionType": $scope.optionType,
//                    "service": "transactions",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.momoTrxResp = response.data;
//                if ($scope.momoTrxResp.code === "00") {
//                    if (paging) {
//                        $scope.momoTrxs = JSON.parse($scope.momoTrxResp.data);
//                        $scope.getMomoTotalCount();
//                    } else {
//                        $scope.momoTrxs = JSON.parse($scope.momoTrxResp.data);
//                        $scope.getMomoTotalCount();
//                        $scope.getMomoTotalAmount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert('Select values for start and end date');
//        }
//    };
//    $scope.searchMomoTrxn = function () {
//        if ($scope.searchTrxnKey.length > 8) {
//            $scope.showLoader = true;
//            $('.reload').addClass('fa-spin text-info');
//            $http({
//                url: $scope.momoProxy,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "searchKey": $scope.searchTrxnKey,
//                    "service": "search"
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.momoTrxResp = response.data;
//                if ($scope.momoTrxResp.code === "00") {
//                    $scope.momoTrxs = JSON.parse($scope.momoTrxResp.data);
//                } else if ($scope.momoTrxResp.code === "01") {
//                    $scope.momoTrxs = "";
//                } else {
//                    $scope.momoTrxs = "";
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert("Length of Transaction ID must be greater than 8");
//        }
//    };
//    $scope.searchMomoStatusTrxn = function () {
////        alert("he");
//        if ($scope.searchTrxnKey.length > 8) {
//            $scope.showLoader = true;
//            $('.reload').addClass('fa-spin text-info');
//            $http({
//                url: $scope.momoProxy,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "searchKey": $scope.searchTrxnKey,
//                    "service": "searchSearch"
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.momoTrxResp = response.data;
//                if ($scope.momoTrxResp.code === "00") {
//                    $scope.momoTrxs = JSON.parse($scope.momoTrxResp.data);
//                } else if ($scope.momoTrxResp.code === "01") {
//                    $scope.momoTrxs = "";
//                } else {
//                    $scope.momoTrxs = "";
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert("Length of Transaction ID must be greater than 8");
//        }
//    };
//    $scope.getMomoTotalCount = function () {
//        $http.get('./MomoTransactions?action=count').then(
//                function (response) {
//                    $scope.fgRecordsTotalCount = JSON.parse(response.data);
//                    $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//                    if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//                        $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//                    }
//                }
//        );
//    };
//    $scope.getMomoTotalAmount = function () {
//        $http.get('./MomoTransactions?action=totalAmount').then(
//                function (response) {
//                    $scope.fgRecordsTotalAmount = response.data;
//                });
//    };
//    $scope.getMomoStatus = function (page, paging) {
//
//        $scope.getProfile();
//        if ($('#start-date').val() && $('#end-date').val()) {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.momoProxy;
//            if (paging) {
//                url = './MomoTransactions?action=paging';
//            }
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                $scope.pageNumber = $scope.fgRecordsLastPage;
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "startDate": $scope.startDate,
//                    "endDate": $scope.endDate,
//                    "bank": $scope.bank,
//                    "bank_code": $scope.bank_code,
//                    "uniqueTransId": $scope.uniqueTransId,
//                    "from_source": $scope.from_source,
//                    "subscriberId": $scope.subscriberId2,
//                    "transType": $scope.transType,
//                    "card_num": $scope.card_num,
//                    "status": $scope.status,
//                    "optionType": $scope.optionType,
//                    "service": "momostatusupdater",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.momoTrxResp = response.data;
//                if ($scope.momoTrxResp.code === "00") {
//                    if (paging) {
//                        $scope.momoTrxs = JSON.parse($scope.momoTrxResp.data);
//                        $scope.getMomoTotalCount();
//                    } else {
//                        $scope.momoTrxs = JSON.parse($scope.momoTrxResp.data);
//                        $scope.getMomoTotalCount();
//                        $scope.getMomoTotalAmount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert('Select values for start and end date');
//        }
//    };
//    $scope.getMomoStatusCheck = function (page, paging) {
//        $scope.getProfile();
//        if ($('#start-date').val() && $('#end-date').val()) {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.momoProxy;
//            if (paging) {
//                url = './MomoTransactions?action=paging';
//            }
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                $scope.pageNumber = $scope.fgRecordsLastPage;
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "startDate": $scope.startDate,
//                    "endDate": $scope.endDate,
//                    "bank": $scope.bank,
//                    "bank_code": $scope.bank_code,
//                    "uniqueTransId": $scope.uniqueTransId,
//                    "from_source": $scope.from_source,
//                    "subscriberId": $scope.subscriberId2,
//                    "transType": $scope.transType,
//                    "card_num": $scope.card_num,
//                    "status": $scope.status,
//                    "optionType": $scope.optionType,
//                    "service": "momostatuschecker",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.momoTrxResp = response.data;
//                if ($scope.momoTrxResp.code === "00") {
//                    if (paging) {
//                        $scope.momoTrxs = JSON.parse($scope.momoTrxResp.data);
//                        $scope.getMomoTotalCount();
//                    } else {
//                        $scope.momoTrxs = JSON.parse($scope.momoTrxResp.data);
//                        $scope.getMomoTotalCount();
//                        $scope.getMomoTotalAmount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert('Select values for start and end date');
//        }
//    };
//
//    $scope.getGIPInvestigation = function (page, paging) {
//        $scope.getProfile();
//        if ($('#start-date').val() && $('#end-date').val()) {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.gipProxy;
//            if (paging) {
//                url = './GipTransactions?action=paging';
//            }
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                $scope.pageNumber = $scope.fgRecordsLastPage;
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "startDate": $scope.startDate,
//                    "endDate": $scope.endDate,
//                    "uniqueTransId": $scope.uniqueTransId,
//                    "status": $scope.status,
//                    "service": "transactions",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.gipTrxResp = response.data;
//                if ($scope.gipTrxResp.code === "00") {
//                    if (paging) {
//                        $scope.gipTrxs = JSON.parse($scope.gipTrxResp.data);
//                        $scope.getGipTotalCount();
//                    } else {
//                        $scope.gipTrxs = JSON.parse($scope.gipTrxResp.data);
//                        $scope.getGipTotalCount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert('Select values for start and end date');
//        }
//    };
//
//    $scope.getGipTotalCount = function () {
//        $http.get('./GipTransactions?action=count').then(
//                function (response) {
//                    $scope.fgRecordsTotalCount = JSON.parse(response.data);
//                    $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//                    if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//                        $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//                    }
//                }
//        );
//    };
//
//    $scope.searchGipTransactions = function () {
//        $scope.getProfile();
//        $scope.showLoader = true;
//        $scope.showFilterBox = false;
//        $scope.pageNumber = 1;
//        $('.reload').addClass('fa-spin text-info');
//        var url = $scope.gipProxy;
//        if ($scope.pageNumber < 1)
//            $scope.pageNumber = 1;
//        if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//            $scope.pageNumber = $scope.fgRecordsLastPage;
////        alert('E: ' + $scope.searchTrxnKey);
//        $http({
//            url: url,
//            method: 'POST',
//            data: $httpParamSerializerJQLike({
//                "searchKey": $scope.searchTrxnKey,
//                "service": "search",
//                "pageNumber": $scope.pageNumber,
//                "rowsPerPage": $scope.rowsPerPage
//            }),
//            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//        }).then(function success(response) {
//            $scope.showLoader = false;
//            $('.reload').removeClass('fa-spin text-info');
//            $scope.gipTrxResp = response.data;
//            if ($scope.gipTrxResp.code === "00") {
//                $scope.gipTrxs = JSON.parse($scope.gipTrxResp.data);
//                $scope.getGipTotalCount();
//            } else {
//
//            }
//        }, function error(response) {
//            $scope.showLoader = false;
//            $('.reload').removeClass('fa-spin text-info');
//            alert('Connection or Server Error. Try again');
//        });
//    };
//
//    $scope.getTeraRefund = function (page, paging) {
//        $scope.getProfile();
//        $scope.showLoader = true;
//        $scope.showFilterBox = false;
//        $scope.pageNumber = page;
//        $('.reload').addClass('fa-spin text-info');
//        var url = $scope.teraRefundProxy;
//        if (paging) {
//            url = './GipTransactions?action=paging';
//        }
//        if ($scope.pageNumber < 1)
//            $scope.pageNumber = 1;
//        if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//            $scope.pageNumber = $scope.fgRecordsLastPage;
//        $http({
//            url: url,
//            method: 'POST',
//            data: $httpParamSerializerJQLike({
//                "uniqueTransId": $scope.uniqueTransId,
//                "service": "transactions",
//                "pageNumber": $scope.pageNumber,
//                "rowsPerPage": $scope.rowsPerPage
//            }),
//            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//        }).then(function success(response) {
//            $scope.showLoader = false;
//            $('.reload').removeClass('fa-spin text-info');
//            $scope.teraTrxResp = response.data;
//            if ($scope.teraTrxResp.code === "00") {
//                if (paging) {
//                    $scope.teraTrxs = JSON.parse($scope.teraTrxResp.data);
////                    $scope.getGipTotalCount();
//                } else {
//                    $scope.teraTrxs = JSON.parse($scope.teraTrxResp.data);
////                    $scope.getGipTotalCount();
//                }
//            } else {
//
//            }
//        }, function error(response) {
//            $scope.showLoader = false;
//            $('.reload').removeClass('fa-spin text-info');
//            alert('Connection or Server Error. Try again');
//        });
//    };
//
//    $scope.getGipTotalCount = function () {
//        $http.get('./GipTransactions?action=count').then(
//                function (response) {
//                    $scope.fgRecordsTotalCount = JSON.parse(response.data);
//                    $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//                    if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//                        $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//                    }
//                }
//        );
//    };
//
//    $scope.searchGipTransactions = function () {
//        $scope.getProfile();
//        $scope.showLoader = true;
//        $scope.showFilterBox = false;
//        $scope.pageNumber = 1;
//        $('.reload').addClass('fa-spin text-info');
//        var url = $scope.gipProxy;
//        if ($scope.pageNumber < 1)
//            $scope.pageNumber = 1;
//        if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//            $scope.pageNumber = $scope.fgRecordsLastPage;
////        alert('E: ' + $scope.searchTrxnKey);
//        $http({
//            url: url,
//            method: 'POST',
//            data: $httpParamSerializerJQLike({
//                "searchKey": $scope.searchTrxnKey,
//                "service": "search",
//                "pageNumber": $scope.pageNumber,
//                "rowsPerPage": $scope.rowsPerPage
//            }),
//            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//        }).then(function success(response) {
//            $scope.showLoader = false;
//            $('.reload').removeClass('fa-spin text-info');
//            $scope.gipTrxResp = response.data;
//            if ($scope.gipTrxResp.code === "00") {
//                $scope.gipTrxs = JSON.parse($scope.gipTrxResp.data);
//                $scope.getGipTotalCount();
//            } else {
//
//            }
//        }, function error(response) {
//            $scope.showLoader = false;
//            $('.reload').removeClass('fa-spin text-info');
//            alert('Connection or Server Error. Try again');
//        });
//    };
//
//
//    $scope.getVtuTrxns = function (page, paging) {
//        $scope.getProfile();
//        if ($('#start-date').val() && $('#end-date').val()) {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.vtuProxy;
//            if (paging) {
//                url = './VtuTransactions?action=paging';
//            }
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                $scope.pageNumber = $scope.fgRecordsLastPage;
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "startDate": $scope.startDate,
//                    "endDate": $scope.endDate,
//                    "bank": $scope.bank,
//                    "from_source": $scope.from_source,
//                    "destination": $scope.vtudest,
//                    "status": $scope.status,
//                    "vtuOptionType": $scope.vtuOptionType,
//                    "transType": $scope.transType,
//                    "lineType": $scope.lineType,
////                    "bankName" :  $scope.bank,
//                    "service": "transactions",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.vtuTrxResp = response.data;
//                if ($scope.vtuTrxResp.code === "00") {
//                    if (paging) {
//                        $scope.vtuTrxs = JSON.parse($scope.vtuTrxResp.data);
//                        $scope.getVtuTotalCount();
//                    } else {
//                        $scope.vtuTrxs = JSON.parse($scope.vtuTrxResp.data);
//                        $scope.getVtuTotalCount();
//                        $scope.getVtuTotalAmount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert('Select values for start and end date');
//        }
//    };
//    $scope.searchVtuTrxn = function () {
//        if ($scope.searchTrxnKey.length > 6) {
//            $scope.showLoader = true;
//            $('.reload').addClass('fa-spin text-info');
//            $http({
//                url: $scope.vtuProxy,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "searchKey": $scope.searchTrxnKey,
//                    "service": "search"
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.vtuTrxResp = response.data;
//                if ($scope.vtuTrxResp.code === "00") {
//                    $scope.vtuTrxs = JSON.parse($scope.vtuTrxResp.data);
//                } else if ($scope.vtuTrxResp.code === "01") {
//                    $scope.vtuTrxs = "";
//                } else {
//                    $scope.vtuTrxs = "";
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert("Length of Transaction ID must be greater than 6");
//        }
//    };
//    $scope.getVtuTotalCount = function () {
//        $http.get('./VtuTransactions?action=count').then(
//                function (response) {
//                    $scope.fgRecordsTotalCount = JSON.parse(response.data);
//                    $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//                    if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//                        $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//                    }
//                }
//        );
//    };
//    $scope.getVtuTotalAmount = function () {
//        $http.get('./VtuTransactions?action=totalAmount').then(
//                function (response) {
//                    $scope.fgRecordsTotalAmount = response.data;
//                });
//    };
//    $scope.getJustPayTrxns = function (page, paging) {
//        $scope.getProfile();
////        console.log('chech');
////        alert($('#scbType').val());
//        if ($('#start-date').val() && $('#end-date').val()) {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.jpProxy;
//            if (paging) {
//                url = './JustPayTransactions?action=paging';
//            }
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                $scope.pageNumber = $scope.fgRecordsLastPage;
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "startDate": $scope.startDate,
//                    "endDate": $scope.endDate,
//                    "uniqueTransId": $scope.uniqueTransId,
//                    "trans_code": $scope.trans_code,
//                    "transType": $scope.transType,
//                    "bank_code": $scope.bank_code,
//                    "jpSubscriberId": $scope.jpSubscriberId,
//                    "trans_status": $scope.trans_status,
////                    "scbType": $('#scbType').val(),
//                    "product": $('#product').val(),
//                    "merchant": $scope.merchant || 'ALL',
//                    "service": "transactions",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.JPResp = response.data;
//                if ($scope.JPResp.code === "00") {
//                    if (paging) {
//                        $scope.JPTrxs = JSON.parse($scope.JPResp.data);
//                        $scope.getJPTransactionsCount();
//                    } else {
//                        $scope.JPTrxs = JSON.parse($scope.JPResp.data);
//                        $scope.getJPTransactionsCount();
//                        $scope.getJPTransactionsTotalAmount();
//                        $scope.getJPTransactionsTotalFee();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert('Select values for start and end date');
//        }
//    };
//    $scope.searchJPTrxn = function () {
//        if ($scope.searchTrxnKey.length > 15) {
//            $scope.showLoader = true;
//            $('.reload').addClass('fa-spin text-info');
//            $http({
//                url: $scope.jpProxy,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "searchKey": $scope.searchTrxnKey,
//                    "service": "search"
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.JPResp = response.data;
//                if ($scope.JPResp.code === "00") {
//                    $scope.JPTrxs = JSON.parse($scope.JPResp.data);
//                    $scope.getJPTransactionsCount();
//                } else if ($scope.JPResp.code === "01") {
//                    $scope.JPTrxs = "";
//                } else {
//                    $scope.JPTrxs = "";
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert("Length of Transaction ID must be greater than 15");
//        }
//    };
//    $scope.getJPTransactionsCount = function () {
//        $http.get('./JustPayTransactions?action=count').then(
//                function (response) {
//                    $scope.fgRecordsTotalCount = JSON.parse(response.data);
//                    $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//                    if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//                        $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//                    }
//                }
//        );
//    };
//    $scope.getJPTransactionsTotalAmount = function () {
//        $http.get('./JustPayTransactions?action=totalAmount').then(
//                function (response) {
//                    $scope.fgRecordsTotalAmount = response.data;
//                });
//    };
//    $scope.getJPTransactionsTotalFee = function () {
//        $http.get('./JustPayTransactions?action=totalFee').then(
//                function (response) {
//                    $scope.fgRecordsTotalFee = response.data;
//                });
//    };
////    $scope.getWebconTrnxs = function (page, paging) {
////        $scope.getProfile();
////        if (paging) {
////            $scope.pageNumber = page;
////            if ($scope.pageNumber < 1)
////                $scope.pageNumber = 1;
////            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage) {
////                $scope.pageNumber = $scope.fgRecordsLastPage;
////            }
////
////            console.log('DATA: ' + page + ' : ' + $scope.fgRecordsLastPage);
////            $scope.begin = (page - 1) * parseInt($scope.rowsPerPage);
////            $scope.end = parseInt($scope.begin) + parseInt($scope.rowsPerPage);
////            console.log('begin: ' + $scope.begin + ' : ' + $scope.end);
////            $scope.filteredwebconTrxs = $scope.webconTrxs.slice($scope.begin, $scope.end);
////            $scope.getWebconTotalCount($scope.webconTrxs);
////
////        } else {
////            if ($('#start-date').val() && $('#end-date').val()) {
////                $scope.showLoader = true;
////                $scope.showFilterBox = false;
////                $scope.pageNumber = page;
////                $('.reload').addClass('fa-spin text-info');
////                var url = $scope.webconProxy;
////                if (paging) {
////                    url = './WebconTransactions?action=paging';
////                }
////                if ($scope.pageNumber < 1)
////                    $scope.pageNumber = 1;
////                if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
////                    $scope.pageNumber = $scope.fgRecordsLastPage;
////                console.log('DATA: ' + $scope.pageNumber + ' : ' + $scope.fgRecordsLastPage);
////                $http({
////                    url: url,
////                    method: 'POST',
////                    data: $httpParamSerializerJQLike({
////                        "startDate": $scope.startDate,
////                        "endDate": $scope.endDate,
////                        "payType": $scope.payType,
////                        "wcMerchant": $('#wcMerchant').val().replace("string:", "") || 'ALL',
////                        "status": $scope.status,
////                        "service": "transactions",
////                        "pageNumber": $scope.pageNumber,
////                        "rowsPerPage": $scope.rowsPerPage
////                    }),
////                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
////                }).then(function success(response) {
////                    $scope.showLoader = false;
////                    $('.reload').removeClass('fa-spin text-info');
////                    $scope.webconTrxResp = response.data;
////                    if ($scope.webconTrxResp.code === "00") {
////                        if (paging) {
////                            $scope.webconTrxs = JSON.parse($scope.webconTrxResp.data);
////                            $scope.getWebconTotalCount($scope.webconTrxs);
////                            $scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage);
////                            $scope.end = parseInt($scope.begin + $scope.rowsPerPage);
////                            console.log('begin: ' + $scope.begin + ' : ' + $scope.end);
////                            $scope.filteredwebconTrxs = $scope.webconTrxs.slice($scope.begin, $scope.end);
////                            $scope.getWebconTotalAmount();
////                        } else {
////                            $scope.webconTrxs = JSON.parse($scope.webconTrxResp.data);
////                            $scope.getWebconTotalCount($scope.webconTrxs);
////                            $scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage);
////                            $scope.end = parseInt($scope.begin + $scope.rowsPerPage);
////                            console.log('begin: ' + $scope.begin + ' : ' + $scope.end);
////                            $scope.filteredwebconTrxs = $scope.webconTrxs.slice($scope.begin, $scope.end);
////                            $scope.getWebconTotalAmount();
////                        }
////                    } else {
////
////                    }
////                }, function error(response) {
////                    $scope.showLoader = false;
////                    $('.reload').removeClass('fa-spin text-info');
////                    alert('Connection or Server Error. Try again');
////                });
////            } else {
////                alert('Select values for start and end date');
////            }
////        }
////
////    };
//
//    $scope.getWebconTrnxs = function (page, paging) {
//        $scope.getProfile();
//        if ($('#start-date').val() && $('#end-date').val()) {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.webconProxy;
//
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                $scope.pageNumber = $scope.fgRecordsLastPage;
////            console.log('DATA: ' + $scope.pageNumber + ' : ' + $scope.fgRecordsLastPage);
//            if (paging) {
//
////                console.log('DATA: ' + page + ' : ' + $scope.fgRecordsLastPage);
//                $scope.begin = (page - 1) * parseInt($scope.rowsPerPage);
//                $scope.end = parseInt($scope.begin) + parseInt($scope.rowsPerPage);
////                console.log('begin: ' + $scope.begin + ' : ' + $scope.end);
//                $scope.filteredwebconTrxs = $scope.webconTrxs.slice($scope.begin, $scope.end);
//                $scope.getWebconTotalCount();
//                $scope.getWebconTotalAmount();
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//
//            } else {
//                $http({
//                    url: url,
//                    method: 'POST',
//                    data: $httpParamSerializerJQLike({
//                        "startDate": $scope.startDate,
//                        "endDate": $scope.endDate,
//                        "payType": $scope.payType,
//                        "wcMerchant": $('#wcMerchant').val().replace("string:", "") || 'ALL',
//                        "status": $scope.status,
//                        "service": "transactions",
//                        "pageNumber": $scope.pageNumber,
//                        "rowsPerPage": $scope.rowsPerPage
//                    }),
//                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                }).then(function success(response) {
//                    $scope.showLoader = false;
//                    $('.reload').removeClass('fa-spin text-info');
//                    $scope.webconTrxResp = response.data;
//                    if ($scope.webconTrxResp.code === "00") {
//                        $scope.webconTrxs = JSON.parse($scope.webconTrxResp.data);
//                        $scope.getWebconTotalCount();
//                        $scope.getWebconTotalAmount();
//                        $scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage);
//                        $scope.end = parseInt($scope.begin + $scope.rowsPerPage);
//                        $scope.filteredwebconTrxs = $scope.webconTrxs.slice($scope.begin, $scope.end);
//                    }
//                }, function error(response) {
//                    $scope.showLoader = false;
//                    $('.reload').removeClass('fa-spin text-info');
//                    alert('Connection or Server Error. Try again');
//                });
//            }
//        } else {
//            alert('Select values for start and end date');
//        }
//
//
//    };
//
//
//    $scope.searchWebconTrxn = function () {
//        if ($scope.searchTrxnKey.length > 6) {
//            $scope.showLoader = true;
//            $('.reload').addClass('fa-spin text-info');
//            $http({
//                url: $scope.webconProxy,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "searchKey": $scope.searchTrxnKey,
//                    "service": "search"
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.webconTrxResp = response.data;
//                if ($scope.webconTrxResp.code === "00") {
//                    $scope.webconTrxs = JSON.parse($scope.webconTrxResp.data);
//                    $scope.getWebconTotalCount();
//                    $scope.getWebconTotalAmount();
//                    $scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage);
//                    $scope.end = parseInt($scope.begin + $scope.rowsPerPage);
//                    $scope.filteredwebconTrxs = $scope.webconTrxs.slice($scope.begin, $scope.end);
//                } else if ($scope.webconTrxResp.code === "01") {
//                    $scope.webconTrxs = "";
//                } else {
//                    $scope.webconTrxs = "";
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert("Length of Transaction ID must be greater than 6");
//        }
//    };
//
//    $scope.getWebconTotalCount = function () {
//        $scope.fgRecordsTotalCount = $scope.webconTrxs.length;
//        $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//        if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//            $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//        }
//    };
//
//    $scope.getWebconTotalAmount = function () {
//        $scope.fgRecordsTotalAmount = $scope.sumAmount($scope.webconTrxs, 'response', 'SUCCESSFUL', 'trans_amount');
//    };
//
//    $scope.getSasTrxns = function (page, paging) {
//        $scope.getProfile();
//        if ($('#start-date').val() && $('#end-date').val()) {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.sasProxy;
//            if (paging) {
//                url = './SASTransactions?action=paging';
//            }
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                $scope.pageNumber = $scope.fgRecordsLastPage;
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "startDate": $scope.startDate,
//                    "endDate": $scope.endDate,
//                    "uniqueTransId": $scope.uniqueTransId,
//                    "from_source": $scope.from_source,
//                    "trans_channel": $scope.trans_channel,
//                    "status": $scope.status,
//                    "service": "transactions",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.sasTrxResp = response.data;
//                if ($scope.sasTrxResp.code === "00") {
//                    if (paging) {
//                        $scope.sasTrxs = JSON.parse($scope.sasTrxResp.data);
//                        $scope.getSasTotalCount();
//                    } else {
//                        $scope.sasTrxs = JSON.parse($scope.sasTrxResp.data);
//                        $scope.getSasTotalCount();
//                        $scope.getSasTotalAmount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert('Select values for start and end date');
//        }
//    };
//    $scope.searchSasTrxn = function () {
//        if ($scope.searchTrxnKey.length > 8) {
//            $scope.showLoader = true;
//            $('.reload').addClass('fa-spin text-info');
//            $http({
//                url: $scope.sasProxy,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "searchKey": $scope.searchTrxnKey,
//                    "service": "search"
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.sasTrxResp = response.data;
//                if ($scope.sasTrxResp.code === "00") {
//                    $scope.sasTrxs = JSON.parse($scope.sasTrxResp.data);
//                } else if ($scope.sasTrxResp.code === "01") {
//                    $scope.sasTrxs = "";
//                } else {
//                    $scope.sasTrxs = "";
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert("Length of Transaction ID must be greater than 8");
//        }
//    };
//
//    $scope.getSasTotalCount = function () {
//        $http.get('./SASTransactions?action=count').then(
//                function (response) {
//                    $scope.fgRecordsTotalCount = JSON.parse(response.data);
//                    $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//                    if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//                        $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//                    }
//                }
//        );
//    };
//    $scope.getSasTotalAmount = function () {
//        $http.get('./SASTransactions?action=totalAmount').then(
//                function (response) {
//                    $scope.fgRecordsTotalAmount = response.data;
//                });
//    };
//    $scope.getUssdTrxns = function (page, paging) {
//        $scope.getProfile();
//        if ($('#start-date').val() && $('#end-date').val()) {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.ussdProxy;
//            if (paging) {
//                url = './UssdTransactions?action=paging';
//            }
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                $scope.pageNumber = $scope.fgRecordsLastPage;
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "startDate": $scope.startDate,
//                    "endDate": $scope.endDate,
//                    "from_source": $scope.from_source,
//                    "transType": $scope.transType,
//                    "card_num": $scope.card_num,
//                    "status": $scope.status,
//                    "service": "transactions",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.ussdTrxResp = response.data;
//                if ($scope.ussdTrxResp.code === "00") {
//                    if (paging) {
//                        $scope.ussdTrxs = JSON.parse($scope.ussdTrxResp.data);
//                        $scope.getUssdTotalCount();
//                    } else {
//                        $scope.ussdTrxs = JSON.parse($scope.ussdTrxResp.data);
//                        $scope.getUssdTotalCount();
//                        $scope.getUssdTotalAmount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert('Select values for start and end date');
//        }
//    };
//    $scope.searchUssdTrxn = function () {
//        if ($scope.searchTrxnKey.length > 5) {
//            $scope.showLoader = true;
//            $('.reload').addClass('fa-spin text-info');
//            $http({
//                url: $scope.ussdProxy,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "searchKey": $scope.searchTrxnKey,
//                    "service": "search"
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.ussdTrxResp = response.data;
//                if ($scope.ussdTrxResp.code === "00") {
//                    $scope.ussdTrxs = JSON.parse($scope.ussdTrxResp.data);
//                } else if ($scope.ussdTrxResp.code === "01") {
//                    $scope.ussdTrxs = "";
//                } else {
//                    $scope.ussdTrxs = "";
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert("Length of Transaction ID or Mobile Nos must be greater than 5");
//        }
//    };
//    $scope.getUssdTotalCount = function () {
//        $http.get('./UssdTransactions?action=count').then(
//                function (response) {
//                    $scope.fgRecordsTotalCount = JSON.parse(response.data);
//                    $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//                    if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//                        $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//                    }
//                }
//        );
//    };
//    $scope.getUssdTotalAmount = function () {
//        $http.get('./UssdTransactions?action=totalAmount').then(
//                function (response) {
//                    $scope.fgRecordsTotalAmount = response.data;
//                });
//    };
//    $scope.getTMCTrxns = function (page, paging) {
//        $scope.updateApp();
//        if ($('#start-date').val() && $('#end-date').val()) {
//
////            var start = Date.parse($('#start-date').val());
////            var end = Date.parse($('#end-date').val());
////            var diff = end - start;
////            if (diff > 43200000) {
////                alert('Date Range cannot exceed 12 hours');
////            } else {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.tmcProxy;
//            if (paging) {
//                url = './TmcTransFunction?action=paging';
//            }
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.TmcRecordsLastPage)
//                $scope.pageNumber = $scope.TmcRecordsLastPage;
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "startDate": $scope.startDate,
//                    "endDate": $scope.endDate,
//                    "from_source": $scope.origin,
//                    "pan": $scope.pan,
//                    "trans_data": $scope.trans_data,
//                    "transtype": $scope.transType,
//                    "channel": $scope.channel,
//                    "destination": $scope.destination,
//                    "destination2": $scope.destination2,
//                    "optionType": $scope.record,
//                    "status": $scope.status,
//                    "service": "transactions",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.tmcTrxResp = response.data;
//                if ($scope.tmcTrxResp.code === "00") {
//                    if (paging) {
//                        $scope.tmcTrxs = JSON.parse($scope.tmcTrxResp.data);
//                        $scope.getTMCTransactionsCount();
////                        $scope.getTMCTransactionsTotalAmount();
//                        $scope.getTMCTransactionsTotalFee();
//                    } else {
//                        $scope.tmcTrxs = JSON.parse($scope.tmcTrxResp.data);
//                        $scope.getTMCTransactionsCount();
//                        $scope.getTMCTransactionsTotalAmount();
//                        $scope.getTMCTransactionsTotalFee();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert('Select values for start and end date');
//        }
//    };
////    $scope.tmcRespTime = function(resptime){
////        if(!resptime.equals("") || resptime !== null ){
////            return resptime;
////        }
////        return "Not Available";
////    };
//    $scope.tmcTAT = function (end, start) {
//        end = new Date(end);
//        start = new Date(start);
//        var diff = (end.getTime() - start.getTime()) / 1000.0;
//        return Math.abs(Math.round(diff));
//    };
//    $scope.searchTMCTrxn = function () {
//        if ($scope.searchTrxnKey.length > 12) {
//            $scope.showLoader = true;
//            $scope.pageNumber = 0;
//            $('.reload').addClass('fa-spin text-info');
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.TmcRecordsLastPage)
//                $scope.pageNumber = $scope.TmcRecordsLastPage;
//            $http({
//                url: $scope.tmcProxy,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "searchKey": $scope.searchTrxnKey,
//                    "destination": $scope.destination,
//                    "destination2": $scope.destination2,
//                    "service": "search",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.tmcTrxResp = response.data;
//                if ($scope.tmcTrxResp.code === "00") {
//                    $scope.tmcTrxs = JSON.parse($scope.tmcTrxResp.data);
//                    $scope.getTMCTransactionsCount();
//                } else if ($scope.tmcTrxResp.code === "01") {
//                    $scope.tmcTrxs = "";
//                } else {
//                    $scope.tmcTrxs = "";
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert("Length of Transaction ID must be greater than 12");
//        }
//    };
//    $scope.getTMCTransactionsCount = function () {
//        $http.get('./TmcTransFunction?action=count').then(
//                function (response) {
//                    $scope.TmcRecordsTotalCount = JSON.parse(response.data);
//                    $scope.TmcRecordsLastPage = $scope.TmcRecordsTotalCount / $scope.rowsPerPage;
//                    if ($scope.TmcRecordsLastPage > parseInt($scope.TmcRecordsTotalCount / $scope.rowsPerPage)) {
//                        $scope.TmcRecordsLastPage = parseInt($scope.TmcRecordsTotalCount / $scope.rowsPerPage) + 1;
//                    }
//                }
//        );
//    };
//    $scope.getTMCTransactionsTotalAmount = function () {
//        $http.get('./TmcTransFunction?action=totalAmount').then(
//                function (response) {
//                    $scope.TmcRecordsTotalAmount = response.data;
//                });
//    };
//    $scope.getTMCTransactionsTotalFee = function () {
//        $http.get('./TmcTransFunction?action=totalFee').then(
//                function (response) {
//                    $scope.TmcRecordsTotalFee = response.data;
//                });
//    };
//    $scope.getBankNodes = function () {
//        $scope.getProfile();
//        url = './MerchantsInfo?action=tmc';
//        $http({
//            url: url,
//            method: 'POST',
//            data: $httpParamSerializerJQLike({
//                "service": "banknodes"
//            }),
//            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//        }).then(function success(response) {
//            $scope.TMCNodeList = response.data;
//            if ($scope.TMCNodeListResp.code === "00") {
//                $scope.TMCNodeList = JSON.parse($scope.TMCNodeListResp.data);
//                $scope.getVasBillCount();
//            } else {
//
//            }
//        }, function error(response) {
//            alert('Connection or Server Error. Try again');
//        });
//    };
//    $scope.BankNodesSet = function () {
//        var bankNode = "";
//        angular.forEach($scope.TMCBankNodeList, function (value, index) {
//            bankNode += value.id + ":";
//        });
//        $scope.BankNodes = bankNode.slice(0, -1);
//    };
//    $scope.getBillPaymentTrxns = function (page, paging) {
//        $scope.updateApp();
//        if ($('#start-date').val() && $('#end-date').val()) {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.billpaymentproxy;
//            if (paging) {
//                url = './BillPaymentTransactions?action=paging';
//            }
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                $scope.pageNumber = $scope.fgRecordsLastPage;
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "startDate": $scope.startDate,
//                    "endDate": $scope.endDate,
//                    "source": $scope.from_source,
//                    "pan": $scope.pan,
//                    "terminal_id": $scope.terminal_id,
//                    "destination": $scope.target,
//                    "status": $scope.status,
//                    "service": "transactions",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.tmcTrxResp = response.data;
//                if ($scope.tmcTrxResp.code === "00") {
//                    if (paging) {
//                        $scope.tmcTrxs = JSON.parse($scope.tmcTrxResp.data);
//                        $scope.getTMCTransactionsCount();
//                    } else {
//                        $scope.tmcTrxs = JSON.parse($scope.tmcTrxResp.data);
//                        $scope.getTMCTransactionsCount();
//                        $scope.getTMCTransactionsTotalAmount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert('Select values for start and end date');
//        }
//    };
//    $scope.getKweseTvTrxns = function (page, paging) {
//        $scope.updateApp();
//        if ($('#start-date').val() && $('#end-date').val()) {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.kwesetvpaymentProxy;
//            if (paging) {
//                url = './KweseTvTransFunction?action=paging';
//            }
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.KweseTvRecordsLastPage)
//                $scope.pageNumber = $scope.KweseTvRecordsLastPage;
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "startDate": $scope.startDate,
//                    "endDate": $scope.endDate,
//                    "trans_status": $scope.trans_status,
//                    "transType": $scope.transType,
//                    "uniqueTransId": $scope.uniqueTransId,
//                    "bank_code": $scope.bank_code,
//                    "trans_channel": $scope.trans_channel,
//                    "service": "transactions",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.kweseTrxResp = response.data;
//                if ($scope.kweseTrxResp.code === "00") {
//                    if (paging) {
//                        $scope.kwesetvTrxs = JSON.parse($scope.kweseTrxResp.data);
//                        $scope.getKweseTvTransactionsCount();
//                    } else {
//                        $scope.kwesetvTrxs = JSON.parse($scope.kweseTrxResp.data);
//                        $scope.getKweseTvTransactionsCount();
//                        $scope.getKweseTvTransactionsTotalAmount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert('Select values for start and end date');
//        }
//    };
//    $scope.searchKweseTvTrxn = function () {
//        if ($scope.searchTrxnKey.length > 12) {
//            $scope.showLoader = true;
//            $('.reload').addClass('fa-spin text-info');
//            $http({
//                url: $scope.kwesetvpaymentProxy,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "searchKey": $scope.searchTrxnKey,
//                    "service": "search"
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.kweseTrxResp = response.data;
//                if ($scope.kweseTrxResp.code === "00") {
//                    $scope.kwesetvTrxs = JSON.parse($scope.kweseTrxResp.data);
//                    $scope.getKweseTvTransactionsCount();
//                } else if ($scope.kweseTrxResp.code === "01") {
//                    $scope.kwesetvTrxs = "";
//                } else {
//                    $scope.kwesetvTrxs = "";
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert("Length of Transaction ID must be greater than 12");
//        }
//    };
//    $scope.getKweseTvTransactionsCount = function () {
//        $http.get('./KweseTvTransFunction?action=count').then(
//                function (response) {
//                    $scope.KweseTvRecordsTotalCount = JSON.parse(response.data);
//                    $scope.KweseTvRecordsLastPage = $scope.KweseTvRecordsTotalCount / $scope.rowsPerPage;
//                    if ($scope.KweseTvRecordsLastPage > parseInt($scope.KweseTvRecordsTotalCount / $scope.rowsPerPage)) {
//                        $scope.KweseTvRecordsLastPage = parseInt($scope.KweseTvRecordsTotalCount / $scope.rowsPerPage) + 1;
//                    }
//                }
//        );
//    };
//    $scope.getKweseTvTransactionsTotalAmount = function () {
//        $http.get('./KweseTvTransFunction?action=totalAmount').then(
//                function (response) {
//                    $scope.KweseTvRecordsTotalAmount = response.data;
//                });
//    };
//
//    $scope.getBOGswitchTrxns = function (page, paging) {
//        $scope.updateApp();
//        if ($('#start-date').val() && $('#end-date').val()) {
//            $scope.showLoader = true;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.bogswitchProxy;
//            if (paging) {
//                url = './BOGswitchTransFunction?action=paging';
//            }
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.BOGswitchRecordsLastPage)
//                $scope.pageNumber = $scope.BOGswitchRecordsLastPage;
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "startDate": $scope.startDate,
//                    "endDate": $scope.endDate,
//                    "service": "transactions",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.bogswitchTrxResp = response.data;
//                if ($scope.bogswitchTrxResp.code === "00") {
//                    if (paging) {
//                        $scope.bogswitchTrxs = JSON.parse($scope.bogswitchTrxResp.data);
//                        $scope.getBOGswitchTransactionsCount();
//                    } else {
//                        $scope.bogswitchTrxs = JSON.parse($scope.bogswitchTrxResp.data);
//                        $scope.getBOGswitchTransactionsCount();
//                        $scope.getBOGswitchTransactionsTotalAmount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert('Select values for start and end date');
//        }
//    };
//
//    $scope.getFundgateBalanceReport = function (page, paging) {
//        $scope.updateApp();
//        $scope.showLoader = true;
//        $scope.pageNumber = page;
//        $('.reload').addClass('fa-spin text-info');
//        var url = $scope.bogswitchProxy;
//        if (paging) {
//            url = './BOGswitchTransFunction?action=paging';
//        }
//        if ($scope.pageNumber < 1)
//            $scope.pageNumber = 1;
//        if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.BOGswitchRecordsLastPage)
//            $scope.pageNumber = $scope.BOGswitchRecordsLastPage;
//        $http({
//            url: url,
//            method: 'POST',
//            data: $httpParamSerializerJQLike({
//                "service": "fgBalance",
//                "pageNumber": $scope.pageNumber,
//                "rowsPerPage": $scope.rowsPerPage
//            }),
//            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//        }).then(function success(response) {
//            $scope.showLoader = false;
//            $('.reload').removeClass('fa-spin text-info');
//            $scope.fgmerchantBalanceResp = response.data;
//            if ($scope.fgmerchantBalanceResp.code === "00") {
//                if (paging) {
//                    $scope.fgMerchantbal = JSON.parse($scope.fgmerchantBalanceResp.data);
////                    $scope.getBOGswitchTransactionsCount();
//                } else {
//                    $scope.fgMerchantbal = JSON.parse($scope.fgmerchantBalanceResp.data);
////                    $scope.getBOGswitchTransactionsCount();
////                    $scope.getBOGswitchTransactionsTotalAmount();
//                }
//            } else {
//
//            }
//        }, function error(response) {
//            $scope.showLoader = false;
//            $('.reload').removeClass('fa-spin text-info');
//            alert('Connection or Server Error. Try again');
//        });
//
//    };
//    $scope.getKYCReport = function (page, paging) {
//        $scope.updateApp();
//        $scope.showLoader = true;
//        $scope.pageNumber = page;
//        $('.reload').addClass('fa-spin text-info');
//        var url = $scope.bogswitchProxy;
//        if (paging) {
//            url = './KYCFunc?action=paging';
//        }
//        if ($scope.pageNumber < 1)
//            $scope.pageNumber = 1;
//        if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//            $scope.pageNumber = $scope.fgRecordsLastPage;
//        $http({
//            url: url,
//            method: 'POST',
//            data: $httpParamSerializerJQLike({
//                "service": "kyc",
//                "transType": $scope.kycTranstype,
//                "pageNumber": $scope.pageNumber,
//                "rowsPerPage": $scope.rowsPerPage
//            }),
//            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//        }).then(function success(response) {
//            $scope.showLoader = false;
//            $('.reload').removeClass('fa-spin text-info');
//            $scope.kycResp = response.data;
//            if ($scope.kycResp.code === "00") {
//                if (paging) {
//                    $scope.kycRecords = JSON.parse($scope.kycResp.data);
//                    $scope.getKYCReportCount();
//                } else {
//                    $scope.kycRecords = JSON.parse($scope.kycResp.data);
//                    $scope.getKYCReportCount();
//                }
//            } else {
//
//            }
//        }, function error(response) {
//            $scope.showLoader = false;
//            $('.reload').removeClass('fa-spin text-info');
//            alert('Connection or Server Error. Try again');
//        });
//
//    };
//    $scope.getKYCReportCount = function () {
//        $http.get('./KYCFunc?action=count').then(
//                function (response) {
//                    $scope.fgRecordsTotalCount = JSON.parse(response.data);
//                    $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//                    if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//                        $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//                    }
//                }
//        );
//    };
//
//    $scope.getBusyBillTrxn = function (page, paging) {
//        $scope.getProfile();
//        if ($('#start-date').val() && $('#end-date').val()) {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.busyProxy;
//            if (paging) {
//                url = './BusyTransFunction?action=paging';
//            }
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                $scope.pageNumber = $scope.fgRecordsLastPage;
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "startDate": $scope.startDate,
//                    "endDate": $scope.endDate,
//                    "uniqueTransId": $scope.uniqueTransId,
//                    "merchant": $scope.merchant,
//                    "status": $scope.status,
//                    "service": "transactions",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.busyTrxResp = response.data;
//                if ($scope.busyTrxResp.code === "00") {
//                    if (paging) {
//                        $scope.busyTrxs = JSON.parse($scope.busyTrxResp.data);
//                        $scope.getVasBillCount();
//                    } else {
//                        $scope.busyTrxs = JSON.parse($scope.busyTrxResp.data);
//                        $scope.getBusyCount();
//                        $scope.getBusyTotalAmount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert('Select values for start and end date');
//        }
//    };
//    $scope.searchBusyTrxn = function () {
//        if ($scope.searchTrxnKey.length > 12) {
//            $scope.showLoader = true;
//            $('.reload').addClass('fa-spin text-info');
//            $http({
//                url: $scope.busyProxy,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "searchKey": $scope.searchTrxnKey,
//                    "service": "search"
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.busyTrxResp = response.data;
//                if ($scope.busyTrxResp.code === "00") {
//                    $scope.busyTrxs = JSON.parse($scope.busyTrxResp.data);
//                } else if ($scope.busyTrxResp.code === "01") {
//                    $scope.busyTrxs = "";
//                } else {
//                    $scope.busyTrxs = "";
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert("Length of Transaction ID must be greater than 12");
//        }
//    };
//    $scope.getBusyCount = function () {
//        $http.get('./BusyTransFunction?action=count').then(
//                function (response) {
//                    $scope.fgRecordsTotalCount = JSON.parse(response.data);
//                    $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//                    if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//                        $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//                    }
//                }
//        );
//    };
//    $scope.getBusyTotalAmount = function () {
//        $http.get('./BusyTransFunction?action=totalAmount').then(
//                function (response) {
//                    $scope.fgRecordsTotalAmount = response.data;
//                });
//    };
//    $scope.getGWCLBillTrxn = function (page, paging) {
//        $scope.getProfile();
//        if ($('#start-date').val() && $('#end-date').val()) {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.gwclProxy;
//            if (paging) {
//                url = './GwclTransFunction?action=paging';
//            }
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                $scope.pageNumber = $scope.fgRecordsLastPage;
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "startDate": $scope.startDate,
//                    "endDate": $scope.endDate,
//                    "uniqueTransId": $scope.uniqueTransId,
//                    "merchant": "GWCL",
//                    "status": $scope.status,
//                    "service": "transactions",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.gwclTrxResp = response.data;
//                if ($scope.gwclTrxResp.code === "00") {
//                    if (paging) {
//                        $scope.gwclTrxs = JSON.parse($scope.gwclTrxResp.data);
//                        $scope.getGWCLBillCount();
//                    } else {
//                        $scope.gwclTrxs = JSON.parse($scope.gwclTrxResp.data);
//                        $scope.getGWCLBillCount();
//                        $scope.getGWCLBillTotalAmount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert('Select values for start and end date');
//        }
//    };
//    $scope.searchGWCLBillTrxn = function () {
//        if ($scope.searchTrxnKey.length > 12) {
//            $scope.showLoader = true;
//            $('.reload').addClass('fa-spin text-info');
//            $http({
//                url: $scope.gwclProxy,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "searchKey": $scope.searchTrxnKey,
//                    "service": "search"
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.gwclTrxResp = response.data;
//                if ($scope.gwclTrxResp.code === "00") {
//                    $scope.gwclTrxs = JSON.parse($scope.gwclTrxResp.data);
//                } else if ($scope.gwclTrxResp.code === "01") {
//                    $scope.gwclTrxs = "";
//                } else {
//                    $scope.gwclTrxs = "";
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert("Length of Transaction ID must be greater than 12");
//        }
//    };
//    $scope.getGWCLBillCount = function () {
//        $http.get('./GwclTransFunction?action=count').then(
//                function (response) {
//                    $scope.fgRecordsTotalCount = JSON.parse(response.data);
//                    $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//                    if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//                        $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//                    }
//                }
//        );
//    };
//    $scope.getGWCLBillTotalAmount = function () {
//        $http.get('./GwclTransFunction?action=totalAmount').then(
//                function (response) {
//                    $scope.fgRecordsTotalAmount = response.data;
//                });
//    };
//    $scope.getFGMomoTransactions = function (page, paging) {
//        $scope.updateApp();
//        if ($('#start-date').val() && $('#end-date').val()) {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.fgmomoProxy;
//            if (paging) {
//                url = './FundGateMomoTransactions?action=paging';
//            }
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                $scope.pageNumber = $scope.fgRecordsLastPage;
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "startDate": $scope.startDate,
//                    "endDate": $scope.endDate,
//                    "merchant": $scope.merchant,
//                    "product": $scope.product,
//                    "status": $scope.status,
//                    "account_no": $('#account_no').val(),
//                    "service": "transactions",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.fgMomoTrxResp = response.data;
//                if ($scope.fgMomoTrxResp.code === "00") {
//                    if (paging) {
//                        $scope.fgMomoTrxs = JSON.parse($scope.fgMomoTrxResp.data);
//                        $scope.getFGTransactionsCount();
//                    } else {
//                        $scope.fgMomoTrxs = JSON.parse($scope.fgMomoTrxResp.data);
//                        $scope.getFGMomoTransactionsCount();
//                        $scope.getFGMomoTransactionsTotalAmount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert('Select values for start and end date');
//        }
//    };
//    $scope.searchFGMomoTransaction = function () {
//        if ($scope.searchTrxnKey.length > 12) {
//            $scope.showLoader = true;
//            $('.reload').addClass('fa-spin text-info');
//            $http({
//                url: $scope.fgmomoProxy,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "searchKey": $scope.searchTrxnKey,
//                    "service": "search"
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.fgMomoTrxResp = response.data;
//                if ($scope.fgMomoTrxResp.code === "00") {
//                    $scope.fgMomoTrxs = JSON.parse($scope.fgMomoTrxResp.data);
//                    $scope.getFGMomoTransactionsCount();
//                } else if ($scope.fgMomoTrxResp.code === "01") {
//                    $scope.fgMomoTrxs = "";
//                } else {
//                    $scope.fgMomoTrxs = "";
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert("Length of Transaction ID must be greater than 12");
//        }
//    };
//    $scope.getFGMomoTransactionsCount = function () {
//        $http.get('./FundGateMomoTransactions?action=count').then(
//                function (response) {
//                    $scope.fgRecordsTotalCount = JSON.parse(response.data);
//                    $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//                    if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//                        $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//                    }
//                }
//        );
//    };
//    $scope.getFGMomoTransactionsTotalAmount = function () {
//        $http.get('./FundGateMomoTransactions?action=totalAmount').then(
//                function (response) {
//                    $scope.fgRecordsTotalAmount = response.data;
//                });
//    };
//    $scope.getElacBillTrxn = function (page, paging) {
//        $scope.getProfile();
//        if ($('#start-date').val() && $('#end-date').val()) {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.elacProxy;
//            if (paging) {
//                url = './ElacTransFunction?action=paging';
//            }
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                $scope.pageNumber = $scope.fgRecordsLastPage;
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "startDate": $scope.startDate,
//                    "endDate": $scope.endDate,
//                    "uniqueTransId": $scope.uniqueTransId,
//                    "merchant": $scope.merchant,
//                    "status": $scope.status,
//                    "service": "transactions",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.elacTrxResp = response.data;
//                if ($scope.elacTrxResp.code === "00") {
//                    if (paging) {
//                        $scope.elacTrxs = JSON.parse($scope.elacTrxResp.data);
//                        $scope.getElacCount();
//                    } else {
//                        $scope.elacTrxs = JSON.parse($scope.elacTrxResp.data);
//                        $scope.getElacCount();
//                        $scope.getElacTotalAmount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert('Select values for start and end date');
//        }
//    };
//    $scope.searchElacTrxn = function () {
//        if ($scope.searchTrxnKey.length > 12) {
//            $scope.showLoader = true;
//            $('.reload').addClass('fa-spin text-info');
//            $http({
//                url: $scope.elacProxy,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "searchKey": $scope.searchTrxnKey,
//                    "service": "search"
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.elacTrxResp = response.data;
//                if ($scope.elacTrxResp.code === "00") {
//                    $scope.elacTrxs = JSON.parse($scope.elacTrxResp.data);
//                } else if ($scope.elacTrxResp.code === "01") {
//                    $scope.elacTrxs = "";
//                } else {
//                    $scope.elacTrxs = "";
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert("Length of Transaction ID must be greater than 12");
//        }
//    };
//    $scope.getElacCount = function () {
//        $http.get('./ElacTransFunction?action=count').then(
//                function (response) {
//                    $scope.fgRecordsTotalCount = JSON.parse(response.data);
//                    $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//                    if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//                        $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//                    }
//                }
//        );
//    };
//    $scope.getElacTotalAmount = function () {
//        $http.get('./ElacTransFunction?action=totalAmount').then(
//                function (response) {
//                    $scope.fgRecordsTotalAmount = response.data;
//                });
//    };
//    $scope.getMobileInvestigation = function (page, paging) {
////        alert($('#banks').val());
//
//        $scope.getProfile();
//        if ($('#start-date').val() && $('#end-date').val()) {
//            if (!$('#mobile_no').val()) {
//                if (!($('#banks').val() === 'ALL')) {
//                    var start = Date.parse($('#start-date').val());
//                    var end = Date.parse($('#end-date').val());
//                    var diff = end - start;
//                    if (diff > 86400000) {
//                        alert('Date Range cannot exceed 24 hours');
//                    } else {
//
//                        $scope.showLoader = true;
//                        $scope.showFilterBox = false;
//                        $scope.pageNumber = page;
//                        $('.reload').addClass('fa-spin text-info');
//                        var url = $scope.mobileinvestigationProxy;
//                        if (paging) {
//                            url = './MobileInvestigationFunction?action=paging';
//                        }
//                        if ($scope.pageNumber < 1)
//                            $scope.pageNumber = 1;
//                        if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                            $scope.pageNumber = $scope.fgRecordsLastPage;
//                        $http({
//                            url: url,
//                            method: 'POST',
//                            data: $httpParamSerializerJQLike({
//                                "startDate": $scope.startDate,
//                                "endDate": $scope.endDate,
//                                "cardnum": "ALL",
//                                "uniqueTransId": $scope.uniqueTransId,
//                                "type": $scope.msg_type,
//                                "bank": $scope.bank,
//                                "service": "mobileinvest",
//                                "pageNumber": $scope.pageNumber,
//                                "rowsPerPage": $scope.rowsPerPage
//                            }),
//                            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                        }).then(function success(response) {
//                            $scope.showLoader = false;
//                            $('.reload').removeClass('fa-spin text-info');
//                            $scope.mobileinvestResp = response.data;
//                            if ($scope.mobileinvestResp.code === "00") {
//                                if (paging) {
//                                    $scope.mobileinvestigations = JSON.parse($scope.mobileinvestResp.data);
//                                    $scope.getMobileInvestigationCount();
//                                } else {
//                                    $scope.mobileinvestigations = JSON.parse($scope.mobileinvestResp.data);
//                                    $scope.getMobileInvestigationCount();
////                        $scope.getElacTotalAmount();
//                                }
//                            } else {
//
//                            }
//                        }, function error(response) {
//                            $scope.showLoader = false;
//                            $('.reload').removeClass('fa-spin text-info');
//                            alert('Connection or Server Error. Try again');
//                        });
//                    }
//                } else {
//                    if ($scope.profile.user_code === '000') {
//                        alert("Please Select Bank");
//                    } else {
//                        alert("Please provide mobile number");
//                    }
//                }
//
//            } else {
//                $scope.showLoader = true;
//                $scope.showFilterBox = false;
//                $scope.pageNumber = page;
//                $('.reload').addClass('fa-spin text-info');
//                var url = $scope.mobileinvestigationProxy;
//                if (paging) {
//                    url = './MobileInvestigationFunction?action=paging';
//                }
//                if ($scope.pageNumber < 1)
//                    $scope.pageNumber = 1;
//                if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                    $scope.pageNumber = $scope.fgRecordsLastPage;
//                $http({
//                    url: url,
//                    method: 'POST',
//                    data: $httpParamSerializerJQLike({
//                        "startDate": $scope.startDate,
//                        "endDate": $scope.endDate,
//                        "cardnum": $scope.mobile_no,
//                        "uniqueTransId": $scope.uniqueTransId,
//                        "type": $scope.msg_type,
//                        "bank": $scope.bank,
//                        "service": "mobileinvest",
//                        "pageNumber": $scope.pageNumber,
//                        "rowsPerPage": $scope.rowsPerPage
//                    }),
//                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                }).then(function success(response) {
//                    $scope.showLoader = false;
//                    $('.reload').removeClass('fa-spin text-info');
//                    $scope.mobileinvestResp = response.data;
//                    if ($scope.mobileinvestResp.code === "00") {
//                        if (paging) {
//                            $scope.mobileinvestigations = JSON.parse($scope.mobileinvestResp.data);
//                            $scope.getMobileInvestigationCount();
//                        } else {
//                            $scope.mobileinvestigations = JSON.parse($scope.mobileinvestResp.data);
//                            $scope.getMobileInvestigationCount();
////                        $scope.getElacTotalAmount();
//                        }
//                    } else {
//
//                    }
//                }, function error(response) {
//                    $scope.showLoader = false;
//                    $('.reload').removeClass('fa-spin text-info');
//                    alert('Connection or Server Error. Try again');
//                });
//            }
//
//        } else {
//            alert('Select values for start and end date');
//        }
//    };
//    $scope.searchMobileInvestigation = function () {
//        if ($scope.searchTrxnKey.length > 12) {
//            $scope.showLoader = true;
//            $('.reload').addClass('fa-spin text-info');
//            $http({
//                url: $scope.mobileinvestigationProxy,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "searchKey": $scope.searchTrxnKey,
//                    "service": "search"
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.mobileinvestResp = response.data;
//                if ($scope.mobileinvestResp.code === "00") {
//                    $scope.mobileinvestigations = JSON.parse($scope.mobileinvestResp.data);
//                } else if ($scope.mobileinvestResp.code === "01") {
//                    $scope.mobileinvestigations = "";
//                } else {
//                    $scope.mobileinvestigations = "";
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert("Length of Transaction ID must be greater than 12");
//        }
//    };
//    $scope.getMobileInvestigationCount = function () {
//        $http.get('./MobileInvestigationFunction?action=count').then(
//                function (response) {
//                    $scope.fgRecordsTotalCount = JSON.parse(response.data);
//                    $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//                    if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//                        $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//                    }
//                }
//        );
//    };
//    $scope.getMobileInvestigationDetails = function (message_id, unique_transid, type, app_id) {
//        $scope.getProfile();
//        $scope.showLoader = true;
//        $scope.showDetailsBox = true;
//        $('.reload').addClass('fa-spin text-info');
//        var url = $scope.mobileinvestigationdetailsProxy;
//        $http({
//            url: url,
//            method: 'POST',
//            data: $httpParamSerializerJQLike({
//                "trans_id": message_id,
//                "unique_transid": unique_transid,
//                "appId": app_id,
//                "type": type,
//                "service": "mobileinvestdetails"
//            }),
//            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//        }).then(function success(response) {
////                alert(response);
////                console.log(response);
//            $scope.showLoader = false;
//            $('.reload').removeClass('fa-spin text-info');
//            $scope.mobileinvestdetailsResp = response.data;
//            if ($scope.mobileinvestdetailsResp.code === "00") {
//                $scope.mobileinvestigationsdetails = JSON.parse($scope.mobileinvestdetailsResp.data);
////                    alert($scope.mobileinvestigationsdetails);
////                console.log($scope.mobileinvestigationsdetails);
//                $scope.detailsid = $scope.mobileinvestigationsdetails.id;
//                $scope.detailsappid = $scope.mobileinvestigationsdetails.appid;
//                $scope.detailsmessage_id = $scope.mobileinvestigationsdetails.message_id;
////                    alert($scope.mobileinvestigationsdetails);
//            } else {
//
//            }
//        }, function error(response) {
//            $scope.showLoader = false;
//            $('.reload').removeClass('fa-spin text-info');
//            alert('Connection or Server Error. Try again');
//        });
//    };
//    $scope.getGCBAgents = function (page, paging) {
//        $scope.getProfile();
//        $scope.showagents = true;
//        $scope.pageNumber = page;
//        var url = $scope.gcbagentProxy;
//        if (paging) {
//            url = './MobileInvestigationFunction?action=paging';
//        }
//        if ($scope.pageNumber < 1)
//            $scope.pageNumber = 1;
//        if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//            $scope.pageNumber = $scope.fgRecordsLastPage;
//        $http({
//            url: url,
//            method: 'POST',
//            data: $httpParamSerializerJQLike({
//                "service": "agents",
//                "pageNumber": $scope.pageNumber,
//                "rowsPerPage": $scope.rowsPerPage
//            }),
//            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//        }).then(function success(response) {
//            $scope.showLoader = false;
//            $('.reload').removeClass('fa-spin text-info');
//            $scope.agentsResp = response.data;
//            if ($scope.agentsResp.code === "00") {
//                if (paging) {
//                    $scope.gcbagents = JSON.parse($scope.agentsResp.data);
//                    $scope.getGCBAgentsCount();
//                } else {
//                    $scope.gcbagents = JSON.parse($scope.agentsResp.data);
//                    $scope.getGCBAgentsCount();
////                        $scope.getElacTotalAmount();
//                }
//            } else {
//
//            }
//        }, function error(response) {
//            $scope.showLoader = false;
//            $('.reload').removeClass('fa-spin text-info');
//            alert('Connection or Server Error. Try again');
//        });
//    };
//    $scope.getGCBAgentsCount = function () {
//        $http.get('./GCBAgentFunction?action=count').then(
//                function (response) {
//                    $scope.fgRecordsTotalCount = JSON.parse(response.data);
//                    $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//                    if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//                        $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//                    }
//                }
//        );
//    };
//    $scope.getRes = function (respcode) {
//        if (respcode === "0") {
//            return "0:SUCCESSFUL";
//        } else
//            return respcode + ":FAILED";
//    };
//    $scope.getRef = function (createRef) {
//        var arr = createRef.split(/ 02 {0,1}/);
//        return ("02" + arr[arr.length - 1].substr(0, arr[arr.length - 1].indexOf('.')));
//    };
//    $scope.getDest = function (createDest) {
//        var arr = createDest.split(/ account: {0,1}/);
//        createDest = arr[arr.length - 1].substr(0, arr[arr.length - 1].indexOf('.'));
//        if (!isNaN(createDest))
//        {
//            return createDest;
//        } else
//            return "Not Available";
//    };
//    $scope.getAmt = function (createAmt) {
////        var arr = createAmt.split(/ GHS {0,1}/);
////        createAmt = arr[arr.length - 1].substr(0, arr[arr.length - 1].indexOf(' '));
////        if (!isNaN(createAmt) && createAmt.toString().indexOf('.') !== -1)
////        {
////            return createAmt;
////        } else
////            return "Not Available";
//        if (createAmt === "N/A") {
//            return "N/A";
//        }
//        if (!isNaN(createAmt))
//        {
//            return accounting.formatMoney(parseFloat(createAmt), "");
//        }
//    };
//    $scope.getTotalAmt = function (createAmt) {
//        var arr = createAmt.split(/ GHS {0,1}/);
//        createAmt = arr[arr.length - 1].substr(0, arr[arr.length - 1].indexOf(' '));
//        if (!isNaN(createAmt) && createAmt.toString().indexOf('.') !== -1)
//        {
//            return parseFloat(createAmt);
//        } else
//            return 0.0;
//    };
////    $scope.getAgentsTransactionsAmt = function () {
////        var TotalAmount = 0.0;
////        angular.forEach($scope.agentTransactions, function (value, index) {
////            TotalAmount += $scope.getTotalAmt(value.response_message);
////        });
////        $scope.AgentsTransactionsAmt = TotalAmount;
////    };
//    $scope.showGCBAgentTrx = function (mobile_no) {
//        $scope.mobile_no = mobile_no;
//        $scope.showAgent = true;
//    };
//    $scope.setOrderProperty = function (propertyName) {
//        if ($scope.orderProperty === propertyName) {
//            $scope.orderProperty = '-' + propertyName;
//        } else if ($scope.orderProperty === '-' + propertyName) {
//            $scope.orderProperty = propertyName;
//        } else {
//            $scope.orderProperty = propertyName;
//        }
//    };
//    $scope.initializeGcbAgentTrans = function () {
//        $scope.getGCBTotal();
//        $scope.getAgentTransaction(1);
//    };
//    $scope.initializeFundgateMerchantBalance = function () {
//        $scope.currentLocation = $location.path().substring(1);
//        if ($scope.currentLocation === "FundgateBalanceReport") {
//            $scope.getFundgateBalanceReport(1);
//        }
//    };
//
//    $scope.initializeCPayMerchants = function () {
//        var i = $scope.profile.user_code;
//        if (i === '252:ALL') {
//            $scope.getCPayMerchants();
//        }
//    };
////
////    $scope.initializeScbmerchants = function () {
////        $scope.getScbMerchants();
////    };
//
//    $scope.initializeMerchantSettings = function () {
//        $scope.getScbMerchants();
//        $scope.getEtzMerchantProducts();
//
//        if ($scope.testEtz() || $scope.testMerchantPayAll()) {
//            $scope.getMPayMerchants();
//        } else {
//            $scope.getMPayProducts();
//        }
//    };
//    $scope.initializeMerchantPaySettings = function () {
//        if ($scope.testEtz() || $scope.testMerchantPayAll()) {
//            $scope.getMPayMerchants();
//        } else {
//            $scope.getMPayProducts();
//        }
//    };
//
//    $scope.initializeUserTypes = function () {
//        $http.get('./MerchantsInfo?action=typeIdList').then(
//                function (response) {
//                    $scope.typeIdList = response.data;
////                    $scope.showUserTypes = true;
//                }
//        );
//    };
//
//    $scope.getUserTypeOptions = function (typeId, scopeVariable) {
////        console.log(typeId + ' ' + scopeVariable);
//        $http.get('./MerchantsInfo?action=typeIdListOptions&option=' + typeId).then(
//                function (response) {
//                    var model = $parse(scopeVariable);
//                    model.assign($scope, response.data);
////                    console.log('datafield10_ ' + JSON.stringify($scope["datafield10_"]));
//                }
//        );
//    };
//
//    $scope.getCPayMerchants = function () {
//        $http.get('./MerchantsInfo?action=cpayMerchants').then(
//                function (response) {
//                    $scope.cpayMerchantList = response.data;
//                }
//        );
//    };
//
//    $scope.getScbMerchants = function () {
//        $http.get('./MerchantsInfo?action=scbMerchants').then(
//                function (response) {
//                    $scope.scbMerchantList = response.data;
//                }
//        );
//    };
//    $scope.getEtzMerchants = function () {
//        $http.get('./MerchantsInfo?action=etzMerchants').then(
//                function (response) {
//                    $scope.etzMerchantList = response.data;
//                }
//        );
//    };
//    $scope.getEtzMerchantProducts = function () {
//        $http.get('./MerchantsInfo?action=etzMerchantProducts').then(
//                function (response) {
//                    $scope.etzMerchantProductList = response.data;
//                });
//    };
//
//    $scope.getMPayProducts = function () {
//        $http.get('./MerchantsInfo?action=mpayProducts&merchant=' + $scope.merchant).then(
//                function (response) {
//                    $scope.merchantPayProductList = response.data;
//                });
//    };
//
//    $scope.getMerchantPayProductList = function () {
//        if ($scope.merchant !== 'ALL') {
//            $scope.getMPayProducts();
//            $scope.product = "ALL";
//        } else {
//            $scope.merchantPayProductList = "";
//            $scope.product = "ALL";
//        }
//    };
//    $scope.getMerchantPayMerchantList = function () {
//        if ($scope.bank !== 'ALL') {
//            $scope.getMPayMerchants();
//            $scope.merchant = "ALL";
//            $scope.product = "ALL";
//        } else {
//            $scope.merchantPayMerchantList = "";
//            $scope.merchantPayProductList = "";
//            $scope.merchant = "ALL";
//            $scope.product = "ALL";
//        }
//    };
//
//    $scope.getMPayMerchants = function () {
//        var j = $scope.profile.user_code;
//
//        $http.get('./MerchantsInfo?action=' + ($scope.testEtz() ? $scope.bank : j.split(":")[0]).toLowerCase() + 'Merchants').then(
//                function (response) {
//                    $scope.merchantPayMerchantList = response.data;
//                });
//    };
//
////    $scope.initializeKYCReport = function () {
////        $scope.currentLocation = $location.path().substring(1);
////        if ($scope.currentLocation === "KYCReport") {
////            $scope.getKYCReport(1);
////        }
////    };
//
//    $scope.getAgentTransaction = function (page, paging) {
//        $scope.fgRecordsTotalFailedAmount = 0;
//        $scope.fgRecordsTotalAmount = 0;
//        $scope.getGCBTotal();
//        $scope.getProfile();
//        $scope.hideAgentBox();
//        if ($('#start-date').val() && $('#end-date').val()) {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $scope.pageNumber2 = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.gcbagentProxy;
//            if (paging) {
//                url = './GCBAgentTransFunc?action=paging';
//            }
//            if ($scope.pageNumber2 < 1)
//                $scope.pageNumber2 = 1;
//            if ($scope.pageNumber2 !== 1 && $scope.pageNumber2 > $scope.fgRecordsLastPage2)
//                $scope.pageNumber2 = $scope.fgRecordsLastPage2;
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "startDate": $scope.startDate,
//                    "endDate": $scope.endDate,
//                    "mobile_no": $scope.mobile_no,
//                    "account_no": $scope.account_no,
//                    "status": $scope.status,
//                    "service": "agentTrans",
//                    "pageNumber": $scope.pageNumber2,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.gcbagenttransResp = response.data;
//                if ($scope.gcbagenttransResp.code === "00") {
//                    $scope.showagents = false;
//                    if (paging) {
//                        $scope.agentTransactions = JSON.parse($scope.gcbagenttransResp.data);
//                        $scope.getAgentTransactionsCount();
//                        $scope.getAgentTransactionsAmount();
//                        $scope.getAgentTransactionsFailedAmount();
//                    } else {
//                        $scope.agentTransactions = JSON.parse($scope.gcbagenttransResp.data);
//                        $scope.getAgentTransactionsCount();
//                        $scope.getAgentTransactionsAmount();
//
//                        $scope.getAgentTransactionsFailedAmount();
////                        $scope.getElacTotalAmount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert('Select values for start and end date');
//        }
//    };
//    $scope.getAgentTransactionsCount = function () {
//        $http.get('./GCBAgentTransFunc?action=count').then(
//                function (response) {
//                    $scope.fgRecordsTotalCount2 = JSON.parse(response.data);
//                    $scope.fgRecordsLastPage2 = $scope.fgRecordsTotalCount2 / $scope.rowsPerPage;
//                    if ($scope.fgRecordsLastPage2 > parseInt($scope.fgRecordsTotalCount2 / $scope.rowsPerPage)) {
//                        $scope.fgRecordsLastPage2 = parseInt($scope.fgRecordsTotalCount2 / $scope.rowsPerPage) + 1;
//                    }
//                }
//        );
//    };
//    $scope.getAgentTransactionsAmount = function () {
//        $http.get('./GCBAgentTransFunc?action=totalAmount').then(
//                function (response) {
//
//                    $scope.fgRecordsTotalAmount = response.data;
//                }
//        );
//    };
//    $scope.getAgentTransactionsFailedAmount = function () {
//        $http.get('./GCBAgentTransFunc?action=totalFailedAmount').then(
//                function (response) {
//
//                    $scope.fgRecordsTotalFailedAmount = response.data;
//                }
//        );
//    };
//
//    $scope.ViewAgents = function () {
//        $scope.showagents = true;
//        $scope.getGCBAgents(1);
//    };
//
//    $scope.ViewAgentsTrans = function () {
//        $scope.showagents = false;
//        $scope.getAgentTransaction(1);
//    };
//
//    $scope.ViewGCBMerchantPayReports = function () {
//        $scope.showgcbmerchantpayreport = false;
//    };
//    $scope.ViewGCBMerchantPayTrxs = function () {
//        $scope.showgcbmerchantpayreport = true;
//    };
//    $scope.downloadFile = function (batch_id) {
//        location.href = "./Report/EtzReport?action=GCBMerchantPay&filename=" + batch_id;
//    };
//    $scope.searchGCBAgent = function () {
//        if ($scope.searchTrxnKey.length > 11) {
//            $scope.showLoader = true;
//            $('.reload').addClass('fa-spin text-info');
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                $scope.pageNumber = $scope.fgRecordsLastPage;
//            $http({
//                url: $scope.gcbagentProxy,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//
//                    "searchKey": $scope.searchTrxnKey,
//                    "service": "searchAgent",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.agentsResp = response.data;
//                if ($scope.agentsResp.code === "00") {
//                    $scope.gcbagents = JSON.parse($scope.agentsResp.data);
//                } else if ($scope.agentsResp.code === "01") {
//                    $scope.gcbagents = "";
//                } else {
//                    $scope.gcbagents = "";
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert("Number must be greater than 11");
//        }
//    };
//    $scope.searchGCBAgentTrans = function () {
//        if ($scope.searchTrxnKey.length > 11) {
//            $scope.showLoader = true;
//            $('.reload').addClass('fa-spin text-info');
//            if ($scope.pageNumber2 < 1)
//                $scope.pageNumber2 = 1;
//            if ($scope.pageNumber2 !== 1 && $scope.pageNumber2 > $scope.fgRecordsLastPage2)
//                $scope.pageNumber2 = $scope.fgRecordsLastPage2;
//            $http({
//                url: $scope.gcbagentProxy,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//
//                    "searchKey": $scope.searchTrxnKey,
//                    "service": "searchAgentTrans",
//                    "pageNumber": $scope.pageNumber2,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.gcbagenttransResp = response.data;
//                if ($scope.gcbagenttransResp.code === "00") {
//                    $scope.agentTransactions = JSON.parse($scope.gcbagenttransResp.data);
//                    $scope.getAgentTransactionsCount();
//                    $scope.getAgentTransactionsAmount();
//                } else if ($scope.gcbagenttransResp.code === "01") {
//                    $scope.agentTransactions = "";
//                    $scope.getAgentTransactionsCount();
//                    $scope.getAgentTransactionsAmount();
//                } else {
//                    $scope.agentTransactions = "";
//                    $scope.getAgentTransactionsCount();
//                    $scope.getAgentTransactionsAmount();
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert("Reference must be greater than 11");
//        }
//    };
//    $scope.goBack = function () {
//        $scope.showagents = false;
//    };
//    $scope.getCardTrxnReport = function (page, paging) {
//        $scope.getProfile();
////        alert($scope.description);
//        if ($('#start-date').val() && $('#end-date').val()) {
//
//            if ($('#cardnum').val() || $('#mobile_no').val() || $scope.testSingleCardTrans()) {
//                $scope.showLoader = true;
//                $scope.showFilterBox = false;
//                $scope.pageNumber = page;
//                $('.reload').addClass('fa-spin text-info');
//                var url = $scope.cardtransreportProxy;
//                if (paging) {
//                    url = './CardTransFunction?action=paging';
//                }
//                if ($scope.pageNumber < 1)
//                    $scope.pageNumber = 1;
//                if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                    $scope.pageNumber = $scope.fgRecordsLastPage;
//                $http({
//                    url: url,
//                    method: 'POST',
//                    data: $httpParamSerializerJQLike({
//                        "startDate": $scope.startDate,
//                        "endDate": $scope.endDate,
//                        "cardnum": $scope.cardnum,
//                        "mobile_no": $scope.mobile_no,
//                        "uniqueTransId": $scope.uniqueTransId,
//                        "service": "cardTransRpt",
//                        "pageNumber": $scope.pageNumber,
//                        "rowsPerPage": $scope.rowsPerPage,
//                        "CardType": "SingleCard",
//                        "searchKey": $('#description').val() || ""
//                    }),
//                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                }).then(function success(response) {
//                    $scope.showLoader = false;
//                    $('.reload').removeClass('fa-spin text-info');
//                    $scope.cardtransrptResp = response.data;
//                    if ($scope.cardtransrptResp.code === "00") {
//                        if (paging) {
//                            $scope.cardtransactions = JSON.parse($scope.cardtransrptResp.data);
//                            $scope.getCardBalance();
//                            $scope.getCardTransactionsCount();
//                            $scope.getCardTransCreditAmount();
//                            $scope.getCardTransDebitAmount();
//                        } else {
//                            $scope.cardtransactions = JSON.parse($scope.cardtransrptResp.data);
//                            $scope.getCardBalance();
//                            $scope.getCardTransactionsCount();
//                            $scope.getCardTransCreditAmount();
//                            $scope.getCardTransDebitAmount();
//                        }
//                    } else {
//
//                    }
//                }, function error(response) {
//                    $scope.showLoader = false;
//                    $('.reload').removeClass('fa-spin text-info');
//                    alert('Connection or Server Error. Try again');
//                });
//            } else {
//                alert('Card number and Phone number cannot be null');
//            }
//
//        } else {
//            alert('Select values for start and end date');
//        }
//
//    };
//    $scope.getCardBalance = function () {
//        if ($scope.testSingleCardTrans()) {
//            $scope.getSingleCardBalance();
//        } else {
//            $http.get('./MerchantsInfo?action=cardbalance&cardNum=' + $scope.cardnum + '&phoneNum=' + $scope.mobile_no).then(
//                    function (response) {
//                        $scope.balance = response.data;
//                        $scope.lastBalanceDate = moment().format('DD-MM-YY H:mm');
//                    }
//            );
//        }
//    };
//    $scope.getSingleCardBalance = function () {
//        if ($scope.testSingleCardTrans()) {
//            $http.get('./MerchantsInfo?action=singlecardbalance').then(
//                    function (response) {
//                        $scope.balance = response.data;
//                        $scope.lastBalanceDate = moment().format('DD-MM-YY H:mm');
//                    }
//            );
//        }
//    };
//    $scope.getCardTransactionsCount = function () {
//        $http.get('./CardTransFunction?action=count').then(
//                function (response) {
//                    $scope.fgRecordsTotalCount = JSON.parse(response.data);
//                    $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//                    if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//                        $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//                    }
//                }
//        );
//    };
//    $scope.getCardTransCreditAmount = function () {
//        $http.get('./CardTransFunction?action=totalCreditAmount').then(
//                function (response) {
//                    $scope.TotalCreditAmount = response.data;
//                }
//        );
//    };
//    $scope.getCardTransDebitAmount = function () {
//        $http.get('./CardTransFunction?action=totalDebitAmount').then(
//                function (response) {
//                    $scope.TotalDebitAmount = response.data;
//                }
//        );
//    };
//    $scope.getRevenueReport = function (page, paging) {
////        alert("hi");
//        $scope.getProfile();
//        $scope.pageNumber = page;
//        var url = $scope.revenuerptProxy;
//        if (paging) {
//            url = './MobileInvestigationFunction?action=paging';
//        }
//        if ($scope.pageNumber < 1)
//            $scope.pageNumber = 1;
//        if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//            $scope.pageNumber = $scope.fgRecordsLastPage;
//        $http({
//            url: url,
//            method: 'POST',
//            data: $httpParamSerializerJQLike({
//                "startDate": $scope.startDate,
//                "endDate": $scope.endDate,
//                "service": "report",
//                "pageNumber": $scope.pageNumber,
//                "rowsPerPage": $scope.rowsPerPage
//            }),
//            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//        }).then(function success(response) {
//            $scope.showLoader = false;
//            $('.reload').removeClass('fa-spin text-info');
//            $scope.agentsResp = response.data;
//            if ($scope.agentsResp.code === "00") {
//                if (paging) {
//                    $scope.gcbagents = JSON.parse($scope.agentsResp.data);
//                    $scope.getGCBAgentsCount();
//                } else {
//                    $scope.gcbagents = JSON.parse($scope.agentsResp.data);
//                    $scope.getGCBAgentsCount();
////                        $scope.getElacTotalAmount();
//                }
//            } else {
//
//            }
//        }, function error(response) {
//            $scope.showLoader = false;
//            $('.reload').removeClass('fa-spin text-info');
//            alert('Connection or Server Error. Try again');
//        });
//    };
//
//    $scope.getCardDetails = function (type, page, paging) {
//        $scope.getProfile();
//        if ($('#card_num2').val().length > 0 || $('#from_source').val().length > 0) {
//            $scope.showLoader = true;
////      $scope.showFilterBox = false;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.crdDetProxy;
//            if (paging) {
//                url = './cardDetails?action=paging';
//            }
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                $scope.pageNumber = $scope.fgRecordsLastPage;
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "card_num": $scope.card_num2,
//                    "from_source": $scope.from_source,
//                    "service": "transactions",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage,
//                    "type": type
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $scope.showCardUpdateBox = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.crdTrxResp = response.data;
//                if ($scope.crdTrxResp.code === "00") {
//                    $scope.crdTrxs = JSON.parse($scope.crdTrxResp.data);
////                    console.log("Data Received: " + JSON.stringify($scope.crdTrxs));.
//                    $scope.getCardDetailsCount();
//                } else {
//                    $scope.crdTrxs = "";
//                    if ($scope.crdTrxResp.message) {
//                        $scope.errorData = "Error Message: " + $scope.crdTrxResp.message;
//                    }
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert('Card number or Mobile number is required');
//        }
//    };
//
//    $scope.getCardDetailsCount = function () {
//        $http.get('./cardDetails?action=count').then(
//                function (response) {
//                    $scope.fgRecordsTotalCount = JSON.parse(response.data);
//                    $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//                    if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//                        $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//                    }
//                }
//        );
//    };
//
//    $scope.checkActionType = function (action, reason) {
//        var actionType = "";
//        switch (action) {
//            case "hotlist":
//            case "dehotlist":
//                actionType = action;
//                break;
//            case "update":
//            switch (reason) {
//                case "PinReset":
//                case "New User":
//                    actionType = reason;
//                    break;
//                case "Enhanced CardEnhancement":
//                case "Medium CardEnhancement":
//                    actionType = reason.split(' ')[1];
//                    break;
//                default:
//                    break;
//            }
//            default:
//                break;
//
//        }
//        return actionType;
//    };
//    $scope.getCmsRequests = function (page, paging) {
//        $scope.getProfile();
//
//        if ($('#start-date').val() && $('#end-date').val()) {
//            $scope.showLoader = true;
////      $scope.showFilterBox = false;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.cmsRequestProxy;
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                $scope.pageNumber = $scope.fgRecordsLastPage;
//            if (paging) {
//                $scope.begin = (page - 1) * parseInt($scope.rowsPerPage);
//                $scope.end = parseInt($scope.begin) + parseInt($scope.rowsPerPage);
////                console.log('begin: ' + $scope.begin + ' : ' + $scope.end);
//                $scope.filteredcmsReq = $scope.cmsReq.slice($scope.begin, $scope.end);
//                $scope.getCmsReqTotalCount();
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//            }
//
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "card_num": $scope.card_number,
//                    "startDate": $scope.startDate,
//                    "endDate": $scope.endDate,
//                    "cmstype": $scope.cmstype,
//                    "status": $scope.status,
//                    "service": "cmsrequests"
////                    "pageNumber": $scope.pageNumber,
////                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $scope.showCardUpdateBox = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.cmsreqResp = response.data;
//                if ($scope.cmsreqResp.code === "00") {
//
//                    $scope.cmsReq = JSON.parse($scope.cmsreqResp.data);
//                    $scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage);
//                    $scope.end = parseInt($scope.begin + $scope.rowsPerPage);
//                    $scope.filteredcmsReq = $scope.cmsReq.slice($scope.begin, $scope.end);
//                    $scope.getCmsReqTotalCount();
////                    console.log("Data Received: " + JSON.stringify($scope.crdTrxs));
//                } else {
//                    $scope.cmsReq = "";
//                    $scope.getCmsReqTotalCount();
//                    if ($scope.cmsreqResp.message) {
//                        $scope.errorData = "Error Message: " + $scope.cmsreqResp.message;
//                    }
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert('Select values for start and end date');
//        }
//    };
//
//    $scope.getCmsReqTotalCount = function () {
//        $scope.fgRecordsTotalCount = $scope.cmsReq.length;
//        $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//        if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//            $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//        }
//    };
////    $scope.BinCheck = function () {
////        $scope.getProfile();
//////        alert('ne :' + $('#binNumber').val());
////        var url = $scope.verifyProxy;
////
////
////        $http({
////            url: url,
////            method: 'POST',
////            data: $httpParamSerializerJQLike({
////                "binNumber": $('#binNumber').val(),
////                "Number" : "hisd",
////                "service": "bincheck"
////            }),
////            headers: {'Content-Type': 'application/json'}
////        }).then(function success(response) {
////            $scope.verifyResp = response.data;
////            if ($scope.verifyResp.code === "00") {
////                $scope.binCheck = JSON.parse($scope.verifyResp.data);
////                console.log("Data Received: " + JSON.stringify($scope.binCheck));
////            } else {
////                $scope.binCheck = "";
////                if ($scope.binCheck.message) {
////                    $scope.errorData = "Error Message: " + $scope.verifyResp.message;
////                }
////            }
////        }, function error(response) {
////            $scope.showLoader = false;
////            $('.reload').removeClass('fa-spin text-info');
////            alert('Connection or Server Error. Try again');
////        });
////    };
//
//    $scope.BinCheck = function () {
//        $scope.binUpdateResultView = false;
//        $('#binCheck-btn').prop("disabled", true);
//        $('#binCheck-btn').html("<i class='fa fa-spinner fa-spin btn-loader'></i> please wait");
//        $scope.updateApp();
//        $scope.newBinNumber = $scope.binNumber;
//        $http({
//            url: $scope.verifyProxy,
//            method: 'POST',
//            data: $httpParamSerializerJQLike({
//                "binNumber": $scope.binNumber,
//                "service": 'checkbin'
//            }),
//            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//        }).then(function success(response) {
//            $('#binCheck-btn').prop("disabled", false);
//            $('#binCheck-btn').html("Check");
//            $scope.binRsp = response.data;
//
//            if ($scope.binRsp.code === "00") {
//                $('#binUpdate-btn').prop("disabled", false);
//
//                $scope.binCheck = $scope.binRsp.data;
////                console.log("Data Received: " + $scope.binCheck);
//            } else {
//                $scope.binCheck = $scope.binRsp.data;
//                $('#binUpdate-btn').prop("disabled", true);
//            }
//        }, function error(response) {
//            $('#binCheck-btn').prop("disabled", false);
//            $('#binCheck-btn').html("Check");
//        });
//    };
//
//    $scope.BinUpdate = function () {
//        $scope.binUpdateResultView = false;
//        $scope.getProfile();
//        $scope.addBinResp = "";
//
//        var url = $scope.verifyProxy;
//
//        $http({
//            url: url,
//            method: 'POST',
//            data: $httpParamSerializerJQLike({
//                "binNumber": $scope.newBinNumber,
//                "service": "addbin"
//            }),
//            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//        }).then(function success(response) {
//            $scope.binUpdateResultView = true;
//            $scope.addBinResp = response.data;
//            console.log($scope.addBinResp.data);
//            if ($scope.addBinResp.code === "00") {
//
//                if ($scope.addBinResp.data === "Success") {
//                    $('#binupdateResp').addClass("bg-success py-2");
//                    $('#binupdateResp').removeClass("bg-danger");
//                    $scope.binCheck = $scope.addBinResp.data;
//                    $('#binupdateResp').html("Bin Added to Database");
//                } else {
//                    $('#binupdateResp').addClass("bg-danger py-2");
//                    $('#binupdateResp').removeClass("bg-success");
//                    $('#binupdateResp').html($scope.addBinResp.data);
//                    $scope.binCheck = $scope.addBinResp.data;
//                }
//            } else {
//                $('#binupdateResp').addClass("bg-danger py-2");
//                $('#binupdateResp').removeClass("bg-success");
//                $('#binupdateResp').html("Failed");
//            }
//        }, function error(response) {
//            $scope.showLoader = false;
//            $scope.binUpdateResultView = false;
//            $('.reload').removeClass('fa-spin text-info');
//            alert('Connection or Server Error. Try again');
//        });
//    };
//
//    $scope.verifyMobileNumber = function () {
//        $scope.portedNetwork = "";
//        $scope.ported = "";
//        $scope.originalNetwork = "";
//        $('#verifyPhone-btn').prop("disabled", true);
//        $('#verifyPhone-btn').html("<i class='fa fa-spinner fa-spin btn-loader'></i> please wait");
//        $scope.getProfile();
//        var url = $scope.verifyProxy;
//        $http({
//            url: url,
//            method: 'POST',
//            data: $httpParamSerializerJQLike({
//                "mobile_no": $scope.mobile_no,
//                "service": "numberLookup"
//            }),
//            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//        }).then(function success(response) {
////            $scope.binUpdateResultView = true;
//            $('#verifyPhone-btn').prop("disabled", false);
//            $('#verifyPhone-btn').html("Check");
//            $scope.verifyNumberResp = response.data;
//            if ($scope.verifyNumberResp.code === "00") {
//                $scope.verifyNumberResp = response.data;
////                console.log($scope.verifyNumberResp.data);
//                $scope.numberDetails = $scope.verifyNumberResp.data;
//                $scope.portedNetwork = $scope.numberDetails.split(':')[0];
//                $scope.ported = $scope.numberDetails.split(':')[1];
//                $scope.originalNetwork = $scope.numberDetails.split(':')[2];
//            } else {
//                $scope.portedNetwork = "N/A";
//                $scope.ported = "N/A";
//                $scope.originalNetwork = "N/A";
//            }
//        }, function error(response) {
//            $scope.showLoader = false;
////            $scope.binUpdateResultView = false;
//            $('#verifyPhone-btn').prop("disabled", false);
//            $('#verifyPhone-btn').html("Check");
//            $('.reload').removeClass('fa-spin text-info');
//            alert('Connection or Server Error. Try again');
//        });
//    };
//
//    $scope.getUsers = function (page, paging) {
//        $scope.getProfile();
//        $scope.showLoader = true;
////      $scope.showFilterBox = false;
//        $scope.pageNumber = page;
//        $('.reload').addClass('fa-spin text-info');
//        var url = $scope.usersProxy;
//        if (paging) {
//            url = './cardDetails?action=paging';
//        }
//        if ($scope.pageNumber < 1)
//            $scope.pageNumber = 1;
//        if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//            $scope.pageNumber = $scope.fgRecordsLastPage;
//        $http({
//            url: url,
//            method: 'POST',
//            data: $httpParamSerializerJQLike({
//                "username": $scope.username,
//                "service": "users",
//                "pageNumber": $scope.pageNumber,
//                "rowsPerPage": $scope.rowsPerPage
//            }),
//            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//        }).then(function success(response) {
//            $scope.showLoader = false;
//            $scope.showCardUpdateBox = false;
//            $('.reload').removeClass('fa-spin text-info');
//            $scope.usersResp = response.data;
//            if ($scope.usersResp.code === "00") {
//                $scope.userProfiles = JSON.parse($scope.usersResp.data);
//                console.log("Data Received: " + JSON.stringify($scope.userProfiles));
//            } else {
//                $scope.userProfiles = "";
//                if ($scope.usersResp.message) {
//                    $scope.errorData = "Error Message: " + $scope.usersResp.message;
//                }
//            }
//        }, function error(response) {
//            $scope.showLoader = false;
//            $('.reload').removeClass('fa-spin text-info');
//            alert('Connection or Server Error. Try again');
//        });
//    };
//
//    $scope.hotlistCardView = function (index, hotlist) {
//        $scope.formName = "Card Information";
//        $scope.formType = "HOTLIST";
//        $scope.formButton = hotlist;
//
//        $scope.showCardUpdateBoxView();
//
//        $scope.card_num_to_update = $scope.crdTrxs[index].card2;
//        $scope.card_num3 = $scope.crdTrxs[index].card_num;
//        $scope.email = $scope.crdTrxs[index].Email;
//        $scope.firstName = $scope.crdTrxs[index].Firstname;
//        $scope.lastName = $scope.crdTrxs[index].Lastname;
//        $scope.from_source2 = $scope.crdTrxs[index].Phone;
//        $scope.fax = $scope.crdTrxs[index].fax;
////        alert($scope.crdTrxs[index].fax.split(' ')[0]);
//        $scope.IdType = $scope.crdTrxs[index].fax.split(' ')[0];
////        $scope.user_hotlist = $scope.crdTrxs[index].User_Hotlist;
//        if ($scope.fax !== "null" || $scope.fax !== "") {
//            $scope.CheckId();
//        }
//    };
//
//    $scope.checkformType = function (formtype, hotlist) {
//        if (formtype === "HOTLIST") {
//            return hotlist;
//        } else
//            return "UPDATE";
//    };
//
//    $scope.checkForm = function () {
//        if ($scope.formButton === "UPDATE") {
//            return true;
//        }
//        return false;
//    };
//
//    $scope.setDecision = function (decision) {
//        $scope.decision = decision;
//    };
//
//    $scope.authCMS = function (status) {
//        if (status === 'PENDING') {
//            return true;
//        }
//        return false;
//    };
//    $scope.checkAction = function (action) {
////        alert(action);
//        if (action === 'update') {
////            alert(action);
//            return true;
//
//        }
//        return false;
//    };
//
//    $scope.updateCard = function () {
//        $scope.updateApp();
//        if ($scope.testAuthorizeCMS()) {
////            alert("2"+$scope.IdType);
//            $('#deny').prop("disabled", false);
//            $('#authorize').prop("disabled", false);
//            $http({
//                url: $scope.crdUpdateProxy,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "card_num": $scope.card_num3,
//                    "firstName": $('#firstName').val(),
//                    "lastName": $('#lastName').val(),
//                    "from_source": $('#from_source2').val(),
//                    "street": $('#street').val(),
//                    "email": $('#email').val(),
//                    "city": $('#city').val(),
//                    "idType": $scope.IdType,
//                    "idNumber": $scope.IdNumber,
////                "boundwork": $('#bound_work').val(),
////                "missed": $('#pin_missed').val(),
////                "hotlist": $('#user_hotlist').val(),
////                "locked": $('#user_locked').val(),
//                    "id": $scope.id,
//                    "decision": $scope.decision,
//                    "service": $scope.action
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                var rsp = response.data;
//                $scope.cardUpdateResultView = true;
//                $('.scrollbar').animate({
//                    scrollTop: 0}, 500, function () {
//                });
//                if (rsp.trim() === "Success") {
//                    switch ($scope.profile.cms_role) {
//                        case "2":
//                            $('#updateResp').addClass("bg-success py-2");
//                            $('#updateResp').removeClass("bg-danger");
//                            $('#updateResp').html($scope.formButton + " Request Successful");
//                            break;
//                        case "1":
//                        case "3":
//                            $('#updateResp').addClass("bg-success py-2");
//                            $('#updateResp').removeClass("bg-danger");
//
//
////                            alert($scope.decision + " " + $scope.id);
//                            if ($scope.decision === 'AUTHORIZE') {
////                                alert($scope.decision + " " + $scope.id);
//                                $('#vst' + $scope.id).html('AUTHORIZED');
//                                $('#vstb' + $scope.id).prop("disabled", true);
//                                $('#authorize').html('AUTHORIZED');
//                                $('#updateResp').html("AUTHORIZED SUCCESSFULLY");
//                                $('#deny').prop("disabled", true);
//                                $('#authorize').prop("disabled", true);
//                            } else if ($scope.decision === 'DENY') {
//                                $('#vst' + $scope.id).html('DENIED');
//                                $('#vstb' + $scope.id).prop("disabled", true);
//                                $('#deny').html('DENIED');
//                                $('#updateResp').html("DENIED SUCCESSFULLY");
//                                $('#deny').prop("disabled", true);
//                                $('#authorize').prop("disabled", true);
//                            }
//                            break;
//                        default:
//                            break;
//                    }
//                } else {
//                    switch ($scope.profile.cms_role) {
//                        case "2":
//                            $('#updateResp').addClass("bg-danger py-2");
//                            $('#updateResp').removeClass("bg-success");
//                            $('#updateResp').html(rsp);
//                            break;
//                        case "1":
//                        case "3":
//                            $('#updateResp').addClass("bg-danger py-2");
//                            $('#updateResp').removeClass("bg-success");
//                            $('#updateResp').html(rsp);
//
//
//                            break;
//                        default:
//                            break;
//                    }
//
//                }
//            }, function error(response) {
//                alert('Connection or Server Error. Try again');
//            });
//
//        } else {
//            if ($('#fax').val() !== '' || $('#fax').val() !== 'null') {
//
//                if (($scope.IdType !== '' || $scope.IdNumber !== '') && ($scope.IdType !== 'null' || $scope.IdNumber !== 'null'))
//                    if ($('#specificOption').val() === 'New' && $scope.formButton === 'UPDATE') {
//
//                        $scope.reason = 'New User';
//                    } else if ($('#specificOption').val() === 'PinReset' && $scope.formButton === 'UPDATE') {
//                        $scope.reason = 'PinReset';
////                        alert('Pin');
//                    } else if ($('#specificOption').val() === 'Enhancement' && $scope.formButton === 'UPDATE') {
//                        $scope.reason = $('#enhancementType').val() + ' CardEnhancement';
////                        alert('card');
//                    } else if ($scope.formButton !== 'UPDATE') {
//                        $scope.reason = $('#hotlistreason').val();
//                    }
////                alert($scope.card_num_to_update);
////                alert($scope.reason);
//
//                $http({
//                    url: $scope.crdUpdateProxy,
//                    method: 'POST',
//                    data: $httpParamSerializerJQLike({
//                        "card_num": $scope.card_num_to_update,
//                        "firstName": $('#firstName').val(),
//                        "lastName": $('#lastName').val(),
//                        "from_source": $('#from_source2').val(),
//                        "street": $('#street').val(),
//                        "email": $('#email').val(),
//                        "city": $('#city').val(),
//                        "idType": $('#IdType').val(),
//                        "idNumber": $('#IdNumber').val(),
////                "boundwork": $('#bound_work').val(),
////                "missed": $('#pin_missed').val(),
////                "hotlist": $('#user_hotlist').val(),
////                "locked": $('#user_locked').val(),
//                        "reason": $scope.reason,
//                        "service": $scope.formButton
//                    }),
//                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                }).then(function success(response) {
//                    var rsp = response.data;
//                    $scope.cardUpdateResultView = true;
//                    $('.scrollbar').animate({
//                        scrollTop: 0}, 500, function () {
//                    });
//                    if (rsp.trim() === "Success") {
//                        switch ($scope.profile.cms_role) {
//                            case "2":
//                                $('#updateResp').addClass("bg-success py-2");
//                                $('#updateResp').removeClass("bg-danger");
//                                $('#updateResp').html($scope.formButton + " Request Successful");
//                                break;
//                            case "1":
//                            case "3":
//                                $('#updateResp').addClass("bg-success py-2");
//                                $('#updateResp').removeClass("bg-danger");
//                                $('#updateResp').html("Record Update Successful");
//                                break;
//                            default:
//                                break;
//                        }
//                    } else {
//                        switch ($scope.profile.cms_role) {
//                            case "2":
//                                $('#updateResp').addClass("bg-danger py-2");
//                                $('#updateResp').removeClass("bg-success");
//                                $('#updateResp').html(rsp);
//                                break;
//                            case "1":
//                            case "3":
//                                $('#updateResp').addClass("bg-danger py-2");
//                                $('#updateResp').removeClass("bg-success");
//                                $('#updateResp').html(rsp);
//                                break;
//                            default:
//                                break;
//                        }
//
//                    }
//                }, function error(response) {
//                    alert('Connection or Server Error. Try again');
//                });
//            } else {
//                $('#updateResp').addClass("bg-danger py-2");
//                $('#updateResp').removeClass("bg-success");
//                $('#updateResp').html("Specify Identification");
//            }
//        }
//
//    };
//
//    $scope.updateCardOffline = function () {
////        console.log('sdfsdf');
//        $scope.updateApp();
//        if ($scope.testAuthorizeCMS()) {
////            alert("2"+$scope.IdType);
////            $('#deny').prop("disabled", false);
////            $('#authorize').prop("disabled", false);
//            if ($('#fax').val() !== '' || $('#fax').val() !== 'null') {
//                console.log('fa: ' + $scope.decision);
//                console.log('fa1: ' + $scope.specificOption);
//                if (($scope.IdType !== '' || $scope.IdNumber !== '') && ($scope.IdType !== 'null' || $scope.IdNumber !== 'null'))
//                    if ($('#specificOption').val() === 'New') {
//
//                        $scope.reason = 'New User';
//                    } else if ($('#specificOption').val() === 'PinReset') {
//                        $scope.reason = 'PinReset';
////                        alert('Pin');
//                    } else if ($('#specificOption').val() === 'Enhancement') {
//                        $scope.reason = $('#enhancementType').val() + ' CardEnhancement';
////                        alert('card');
//                    } else if ($scope.formButton !== 'UPDATE') {
//                        $scope.reason = $('#hotlistreason').val();
//                    }
//                console.log('fat: ' + $scope.reason);
//                $http({
//                    url: $scope.crdUpdateProxy,
//                    method: 'POST',
//                    data: $httpParamSerializerJQLike({
//                        "card_num": $scope.card_num_to_update,
//                        "firstName": $('#firstName').val(),
//                        "lastName": $('#lastName').val(),
//                        "from_source": $('#from_source2').val(),
//                        "street": $('#street').val(),
//                        "email": $('#email').val(),
//                        "city": $('#city').val(),
//                        "idType": ($scope.IdType !== 'Others' ? $scope.IdType : $('#IdName').val() || $scope.IdName),
//                        "idNumber": $scope.IdNumber,
//                        "decision": $scope.reason,
//                        "id": "",
//                        "service": 'offlineKYC'
//                    }),
//                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                }).then(function success(response) {
//                    var rsp = response.data;
//                    $scope.cardUpdateResultView = true;
//                    $('.scrollbar').animate({
//                        scrollTop: 0}, 500, function () {
//                    });
//                    if (rsp.trim() === "Success") {
//
//                        $('#updateResp').addClass("bg-success py-2");
//                        $('#updateResp').removeClass("bg-danger");
//                        $('#vst' + $scope.id).html('AUTHORIZED');
//                        $('#vstb' + $scope.id).prop("disabled", true);
//                        $('#authorize').html('AUTHORIZED');
//                        $('#updateResp').html("CARD UPDATE SUCCESSFUL");
//                        $('#deny').prop("disabled", true);
//                        $('#authorize').prop("disabled", true);
//                    } else {
//                        $('#updateResp').addClass("bg-danger py-2");
//                        $('#updateResp').removeClass("bg-success");
//                        $('#updateResp').html(rsp);
//                    }
//                }, function error(response) {
//                    alert('Connection or Server Error. Try again');
//                });
//            } else {
//                $('#updateResp').addClass("bg-danger py-2");
//                $('#updateResp').removeClass("bg-success");
//                $('#updateResp').html("Specify Identification");
//            }
//
//        } else {
//            alert('You are not Authorized to do this !!!');
//        }
//
//    };
//
//
//    $scope.searchCard = function () {
//        if ($scope.searchTrxnKey.length > 5) {
//            $scope.showLoader = true;
//            $('.reload').addClass('fa-spin text-info');
//            $http({
//                url: $scope.crdDetProxy,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "searchKey": $scope.searchTrxnKey,
//                    "service": "search"
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $scope.showCardUpdateBox = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.crdTrxResp = response.data;
//                if ($scope.crdTrxResp.code === "00") {
//                    $scope.crdTrxs = JSON.parse($scope.crdTrxResp.data);
//                    $scope.firstName = $scope.crdTrxs[0].Firstname;
//                } else {
//                    $scope.crdTrxs = "";
//                    if ($scope.crdTrxResp.message) {
//                        $scope.errorData = "Error Message: " + $scope.crdTrxResp.message;
//                    }
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert("Length of Transaction ID or Mobile Nos must be greater than 5");
//        }
//    };
//
//    $scope.updateForm = function (index) {
//        $scope.formName = "Card Information";
//        $scope.formType = "UPDATE";
//        $scope.formButton = "UPDATE";
//        $scope.showCardUpdateBoxView();
//        $scope.card_num_to_update = $scope.crdTrxs[index].card2;
//        $scope.card_num3 = $scope.crdTrxs[index].card_num;
//        $scope.email = $scope.crdTrxs[index].Email;
//        $scope.firstName = $scope.crdTrxs[index].Firstname;
//        $scope.lastName = $scope.crdTrxs[index].Lastname;
//        $scope.street = $scope.crdTrxs[index].Street;
//        $scope.city = $scope.crdTrxs[index].city;
//        $scope.bound_work = $scope.crdTrxs[index].bound_work;
//        $scope.from_source2 = $scope.crdTrxs[index].Phone;
//        $scope.fax = $scope.crdTrxs[index].fax;
////        alert($scope.fax);
//        if ($scope.fax.length > 10) {
//            $scope.IdType = $scope.crdTrxs[index].fax.split(' ')[0];
//            $scope.IdNumber = $scope.crdTrxs[index].fax.split(' ')[1];
//            $scope.CheckId();
//        }
//
//        $scope.checkAction($scope.crdTrxs[index].action);
//
////        $scope.id = $scope.crdTrxs[index].id;
////        $scope.user_locked = $scope.crdTrxs[index].User_Locked;
////        $scope.pin_missed = $scope.crdTrxs[index].Pin_Missed;
////        $scope.user_hotlist = $scope.crdTrxs[index].User_Hotlist;
//    };
//
//    $scope.userUpdateForm = function (index) {
//        $scope.formName = "Update User Info";
//        $scope.formType = "UPDATE";
//        $scope.formButton = "Update User";
//        $scope.showCardUpdateBoxView();
//
//        $scope.email = $scope.userProfiles[index].email;
//        $scope.firstName = $scope.userProfiles[index].firstname;
//        $scope.lastName = $scope.userProfiles[index].lastname;
//        $scope.type_id = $scope.userProfiles[index].type_id;
//    };
//
//    $scope.getTypeIdOptions = function (option) {
////        if ($scope.type_id !== '') {
//////            
////            $http.get('./MerchantsInfo?action=' + ($scope.testEtz() ? $scope.bank : j.split(":")[0]).toLowerCase() + 'Merchants').then(
////                    function (response) {
////                        $scope.merchantPayMerchantList = response.data;
////                    });
////            $scope.merchant = "ALL";
////            $scope.product = "ALL";
////        } else {
////            $scope.merchantPayMerchantList = "";
////            $scope.merchantPayProductList = "";
////            $scope.merchant = "ALL";
////            $scope.product = "ALL";
////        }
////        console.log(option);
////        console.log('defined1: ' + $scope["datafield10_"]);
////        console.log('value: ' + $scope.values[option]);
//        $scope.getUserTypeOptions($scope.values[option], 'data' + option);
////        if ($('#' + option).length) {
////        console.log('option ' + '#' + option + ' ' + $('#' + option).length);
////        }
////        console.log('defined2: ' + $scope["datafield10_"]);
//        if ($('#data' + option).length < 1) {
//            $scope.addTypeIdOptions(option);
//        }
//    };
//
//
//    $scope.getMPayMerchants = function () {
//        var j = $scope.profile.user_code;
//
//        $http.get('./MerchantsInfo?action=' + ($scope.testEtz() ? $scope.bank : j.split(":")[0]).toLowerCase() + 'Merchants').then(
//                function (response) {
//                    $scope.merchantPayMerchantList = response.data;
//                });
//    };
//
//    $scope.showCreateUserForm = function () {
//        $scope.formName = "Create New User";
//        $scope.formType = "CreateUser";
//        $scope.formButton = "Create User";
//        $scope.showCardUpdateBoxView();
//
//        $scope.email = "";
//        $scope.firstName = "";
//        $scope.lastName = "";
//        $scope.type_id = "";
//    };
//
//
//
//    $scope.authorizeForm = function (index) {
//        $scope.formName = "Card Information";
//        $scope.formType = "AUTHORIZE";
//        $scope.formButton = "AUTHORIZE";
//        $('#deny').prop("disabled", false);
//        $('#authorize').prop("disabled", false);
//        $scope.showCardUpdateBoxView();
//        $scope.card_num_to_update = $scope.cmsReq[index].card2;
//        $scope.card_num3 = $scope.cmsReq[index].card_num;
//        $scope.email = $scope.cmsReq[index].Email;
//        $scope.firstName = $scope.cmsReq[index].Firstname;
//        $scope.lastName = $scope.cmsReq[index].Lastname;
//        $scope.street = $scope.cmsReq[index].Street;
//        $scope.city = $scope.cmsReq[index].city;
////        $scope.bound_work = $scope.cmsReq[index].bound_work;
//        $scope.from_source2 = $scope.cmsReq[index].Phone;
//        $scope.fax = $scope.cmsReq[index].fax;
//        if ($scope.fax) {
////             console.log('use me');
//            $scope.IdType = $scope.cmsReq[index].fax.split(' ')[0];
//            $scope.IdNumber = $scope.cmsReq[index].fax.split(' ')[1];
//            $scope.CheckId();
//        }
//
//        $scope.reason = $scope.cmsReq[index].reason;
//        $scope.action = $scope.cmsReq[index].action;
//
//        $scope.intiator = $scope.cmsReq[index].initiated_by;
//        $scope.initiated_date = $scope.cmsReq[index].date_initiated;
//        $scope.id = $scope.cmsReq[index].id;
//        $scope.checkAction($scope.cmsReq[index].action);
//
////        alert($scope.decision + " " + $scope.id);
////        if ($scope.decision === 'authorize') {
////            alert($scope.decision + " " + $scope.id);
////            $('#vst' + $scope.id).html('AUTHORIZED()');
////            $('#vst' + $scope.id).prop("disabled", true);
////        } else if ($scope.decision === 'deny') {
////            $('#vst' + $scope.id).html('DENIED');
////            $('#vst' + $scope.id).prop("disabled", true);
////        }
////        $scope.user_locked = $scope.crdTrxs[index].User_Locked;
////        $scope.pin_missed = $scope.crdTrxs[index].Pin_Missed;
////        $scope.user_hotlist = $scope.crdTrxs[index].User_Hotlist;
//    };
//
//    $scope.authoriseForm = function (index) {
//
////        $('.example2').on('click', function () {
//
//        $scope.formName = "Card Information";
//        $scope.formType = "AUTHORIZE";
//        $scope.formButton = "AUTHORIZE";
//        $('#deny').prop("disabled", false);
//        $('#authorize').prop("disabled", false);
//        $scope.showCardUpdateBoxView();
//        $scope.card_num_to_update = $scope.cmsReq[index].card2;
//        $scope.card_num3 = $scope.cmsReq[index].card_num;
//        $scope.email = $scope.cmsReq[index].Email;
//        $scope.firstName = $scope.cmsReq[index].Firstname;
//        $scope.lastName = $scope.cmsReq[index].Lastname;
//        $scope.street = $scope.cmsReq[index].Street;
//        $scope.city = $scope.cmsReq[index].city;
////        $scope.bound_work = $scope.cmsReq[index].bound_work;
//        $scope.from_source2 = $scope.cmsReq[index].Phone;
//        $scope.fax = $scope.cmsReq[index].fax;
//        if ($scope.fax) {
////             console.log('use me');
//            $scope.IdType = $scope.cmsReq[index].fax.split(' ')[0];
//            $scope.IdNumber = $scope.cmsReq[index].fax.split(' ')[1];
//            $scope.CheckId();
//        }
//
//        $scope.reason = $scope.cmsReq[index].reason;
//        $scope.action = $scope.cmsReq[index].action;
//
//        $scope.intiator = $scope.cmsReq[index].initiated_by;
//        $scope.initiated_date = $scope.cmsReq[index].date_initiated;
//        $scope.id = $scope.cmsReq[index].id;
//        $scope.checkAction($scope.cmsReq[index].action);
//        $.confirm({
//            title: 'Confirm!',
//            closeIcon: true,
//            closeIconClass: 'fa fa-close',
//            columnClass: 'medium',
//            draggable: true,
////            content: '' +
////                    '<form ng-submit="updateCard();">' +
////                    '<div class="row" data-ng-init="CheckId()">' +
////                    '   <div class="col-9">' +
////                    '      <table class="table borderless">    ' +
////                    '         <tr>' +
////                    '            <td><label>Card Number:</label></td>' +
////                    '          <td><label class="">&nbsp;&nbsp;&nbsp;' + $scope.card_num3 + '</label></td>' +
////                    '     </tr>' +
////                    '    <tr>' +
////                    '       <td><label for="firstName">First Name:</label></td>' +
////                    '      <td><label class="">&nbsp;&nbsp;&nbsp;' + $scope.firstName + '</label></td>' +
////                    ' </tr>' +
////                    '<tr>' +
////                    '   <td><label for="lastName">Last Name:</label></td>' +
////                    '      <td><label class="">&nbsp;&nbsp;&nbsp;' + $scope.lastName + '</label></td>' +
////                    '</tr>' +
////                    '<tr>' +
////                    '   <td><label for="from_source">Mobile No:</label></td>' +
////                    '      <td><label class="">&nbsp;&nbsp;&nbsp;' + $scope.from_source2 + '</label></td>' +
////                    '</tr> ' +
////                    '<tr>' +
////                    '  <td><label for="reason">Reason:</label></td>' +
////                    '  <td><textarea class="form-control form-control-sm text-primary" name="reason" ng-model="reason" id="reason" max-rows="3" style="resize:none;" disabled> ' + $scope.reason + '</textarea></td>' +
////                    '</tr>' +
////                    '<tr>' +
////                    '<td><label for="fax">ID:</label></td>' +
////                    '<td>' +
////                    '<div class="input-group">' +
////                    '<input type="text" class="form-control form-control-sm text-primary" value="' + $scope.fax + '" disabled>' +
////                    '<span class="input-group-btn">' +
////                    ' <button class="btn btn-success btn-sm">' +
////                    '   Verify' +
////                    '</button>' +
////                    '</span>' +
////                    '</div>' +
////                    '</td>' +
////                    '</tr>' +
////                    '</table>' +
////                    ' </div>' +
////                    ' <div class="col-3">' +
////                    '<img id="verifyIDimage" src="http://ssl.gstatic.com/accounts/ui/avatar_2x.png" class="avatar img-circle img-thumbnail" alt="avatar">' +
////                    ' </div>' +
////                    '</div>' +
//////                    '<div class="row">' +
//////                    '<div class="col col-6">' +
//////                    '<button id="authorize" type="submit" class="btn btn-success btn-block filter-btn" ng-click="setDecision(\'AUTHORIZE\')">AUTHORIZE</button>' +
//////                    '</div>' +
//////                    '<div class="col col-6">' +
//////                    '<button id="deny" type="submit" class="btn btn-danger btn-block filter-btn" ng-click="setDecision(\'DENY\')">DENY</button>' +
//////                    '</div>' +
////                    '</div>' +
////                    '</form>',
//
//            content: function () {
//                var self = this;
//                return $.ajax({
//                    url: 'bower.json',
//                    dataType: 'json',
//                    method: 'post',
//                    data: data,
//                }).done(function (response) {
//                    self.setContent('' +
//                            '<form ng-submit="updateCard();">' +
//                            '<div class="row" data-ng-init="CheckId()">' +
//                            '   <div class="col-9">' +
//                            '      <table class="table borderless">    ' +
//                            '         <tr>' +
//                            '            <td><label>Card Number:</label></td>' +
//                            '          <td><label class="">&nbsp;&nbsp;&nbsp;' + $scope.card_num3 + '</label></td>' +
//                            '     </tr>' +
//                            '    <tr>' +
//                            '       <td><label for="firstName">First Name:</label></td>' +
//                            '      <td><label class="">&nbsp;&nbsp;&nbsp;' + $scope.firstName + '</label></td>' +
//                            ' </tr>' +
//                            '<tr>' +
//                            '   <td><label for="lastName">Last Name:</label></td>' +
//                            '      <td><label class="">&nbsp;&nbsp;&nbsp;' + $scope.lastName + '</label></td>' +
//                            '</tr>' +
//                            '<tr>' +
//                            '   <td><label for="from_source">Mobile No:</label></td>' +
//                            '      <td><label class="">&nbsp;&nbsp;&nbsp;' + $scope.from_source2 + '</label></td>' +
//                            '</tr> ' +
//                            '<tr>' +
//                            '  <td><label for="reason">Reason:</label></td>' +
//                            '  <td><textarea class="form-control form-control-sm text-primary" name="reason" ng-model="reason" id="reason" max-rows="3" style="resize:none;" disabled> ' + $scope.reason + '</textarea></td>' +
//                            '</tr>' +
//                            '<tr>' +
//                            '<td><label for="fax">ID:</label></td>' +
//                            '<td>' +
//                            '<div class="input-group">' +
//                            '<input type="text" class="form-control form-control-sm text-primary" value="' + $scope.fax + '" disabled>' +
//                            '<span class="input-group-btn">' +
//                            ' <button class="btn btn-success btn-sm">' +
//                            '   Verify' +
//                            '</button>' +
//                            '</span>' +
//                            '</div>' +
//                            '</td>' +
//                            '</tr>' +
//                            '</table>' +
//                            ' </div>' +
//                            ' <div class="col-3">' +
//                            '<img id="verifyIDimage" src="http://ssl.gstatic.com/accounts/ui/avatar_2x.png" class="avatar img-circle img-thumbnail" alt="avatar">' +
//                            ' </div>' +
//                            '</div>' +
////                    '<div class="row">' +
////                    '<div class="col col-6">' +
////                    '<button id="authorize" type="submit" class="btn btn-success btn-block filter-btn" ng-click="setDecision(\'AUTHORIZE\')">AUTHORIZE</button>' +
////                    '</div>' +
////                    '<div class="col col-6">' +
////                    '<button id="deny" type="submit" class="btn btn-danger btn-block filter-btn" ng-click="setDecision(\'DENY\')">DENY</button>' +
////                    '</div>' +
//                            '</div>' +
//                            '</form>');
////                    self.setContentAppend('<br>Version: ' + response.version);
////                    self.setTitle(response.name);
//                }).fail(function () {
//                    self.setContent('' +
//                            '<form ng-submit="updateCard();">' +
//                            '<div class="row" data-ng-init="CheckId()">' +
//                            '   <div class="col-9">' +
//                            '      <table class="table borderless">    ' +
//                            '         <tr>' +
//                            '            <td><label>Card Number:</label></td>' +
//                            '          <td><label class="">&nbsp;&nbsp;&nbsp;' + $scope.card_num3 + '</label></td>' +
//                            '     </tr>' +
//                            '    <tr>' +
//                            '       <td><label for="firstName">First Name:</label></td>' +
//                            '      <td><label class="">&nbsp;&nbsp;&nbsp;' + $scope.firstName + '</label></td>' +
//                            ' </tr>' +
//                            '<tr>' +
//                            '   <td><label for="lastName">Last Name:</label></td>' +
//                            '      <td><label class="">&nbsp;&nbsp;&nbsp;' + $scope.lastName + '</label></td>' +
//                            '</tr>' +
//                            '<tr>' +
//                            '   <td><label for="from_source">Mobile No:</label></td>' +
//                            '      <td><label class="">&nbsp;&nbsp;&nbsp;' + $scope.from_source2 + '</label></td>' +
//                            '</tr> ' +
//                            '<tr>' +
//                            '  <td><label for="reason">Reason:</label></td>' +
//                            '  <td><textarea class="form-control form-control-sm text-primary" name="reason" ng-model="reason" id="reason" max-rows="3" style="resize:none;" disabled> ' + $scope.reason + '</textarea></td>' +
//                            '</tr>' +
//                            '<tr>' +
//                            '<td><label for="fax">ID:</label></td>' +
//                            '<td>' +
//                            '<div class="input-group">' +
//                            '<input type="text" class="form-control form-control-sm text-primary" value="' + $scope.fax + '" disabled>' +
//                            '<span class="input-group-btn">' +
//                            ' <button class="btn btn-success btn-sm">' +
//                            '   Verify' +
//                            '</button>' +
//                            '</span>' +
//                            '</div>' +
//                            '</td>' +
//                            '</tr>' +
//                            '</table>' +
//                            ' </div>' +
//                            ' <div class="col-3">' +
//                            '<img id="verifyIDimage" src="http://ssl.gstatic.com/accounts/ui/avatar_2x.png" class="avatar img-circle img-thumbnail" alt="avatar">' +
//                            ' </div>' +
//                            '</div>' +
////                    '<div class="row">' +
////                    '<div class="col col-6">' +
////                    '<button id="authorize" type="submit" class="btn btn-success btn-block filter-btn" ng-click="setDecision(\'AUTHORIZE\')">AUTHORIZE</button>' +
////                    '</div>' +
////                    '<div class="col col-6">' +
////                    '<button id="deny" type="submit" class="btn btn-danger btn-block filter-btn" ng-click="setDecision(\'DENY\')">DENY</button>' +
////                    '</div>' +
//                            '</div>' +
//                            '</form>');
//                });
//            },
//            buttons: {
//                confirm: function () {
//                    $.alert('Confirmed!');
//                },
//                cancel: function () {
//                    $.alert('Canceled!');
//                },
//                somethingElse: {
//                    text: 'Something else',
//                    btnClass: 'btn-blue',
//                    keys: [
//                        'enter',
//                        'shift'
//                    ],
//                    action: function () {
//                        this.$content; // reference to the content
//                        $.alert('Something else?');
//                    }
//                }
//            }
//        });
////        });
//
//    };
//
//    $scope.CheckId = function () {
////        $('.reload').addClass('fa-spin text-info');
////        alert($('#fax').val());
//        $('#checkId').prop("disabled", true);
//        $('#checkId').html("<i class='fa fa-spinner fa-spin btn-loader'></i> Checking...");
//
////        $scope.IdType = $scope.fax.substring($scope.fax.lastIndexOf(" "), 0);
////        alert(IdType);
////        $scope.IdNumber = $scope.fax.substring($scope.fax.lastIndexOf(" ") + 1);
////        alert(IdNumber);
//        $http({
//            url: $scope.crdDetProxy,
//            method: 'POST',
//            data: $httpParamSerializerJQLike({
//                "idType": $('#IdType').val() || $scope.IdType,
//                "idNumber": $('#IdNumber').val() || $scope.IdNumber,
////              "idType": $('#IdType').val(),
//                "service": "verifyID"
//            }),
//            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//        }).then(function success(response) {
//
//            $('#checkId').html("Check");
//            $('.reload').removeClass('fa-spin text-info');
//            $scope.verifyIDResp = response.data;
//            if ($scope.verifyIDResp.code === "00") {
//                $scope.verified = JSON.parse($scope.verifyIDResp.data);
////                console.log($scope.verified.message);
////                console.log($scope.verified.message.substring(0, $scope.verified.message.indexOf("_")));
//                $scope.fullName = $scope.verified.message.split('_')[0];
////                console.log('fullname: ' + $scope.fullName + $scope.fullName.indexOf("No Person Found"));
//                if ($scope.fullName.indexOf("No Person Found") === -1 && $scope.fullName.indexOf("Incorrect Id Type") === -1 && $scope.fullName.indexOf("HTTP error code") === -1) {
//                    $scope.firstName = $scope.fullName.substring($scope.fullName.lastIndexOf(" "), 0);
//                    $scope.lastName = $scope.fullName.substring($scope.fullName.lastIndexOf(" ") + 1);
//                    $("#verifyIDimage").attr("src", "data:image/gif;base64," + $scope.verified.message.split('_')[3]);
////                    console.log('image: ' + $scope.verified.message.split('_')[3]);
//                    $('#updateData').prop("disabled", false);
//                } else {
//                    $('#updateResp').addClass("bg-danger py-2");
//                    $('#updateResp').removeClass("bg-success");
//                    $('#updateResp').html('Verification Failed: ' + $scope.fullName);
//                    if ($scope.fullName.indexOf("Incorrect Id Type") !== -1) {
//                        $scope.IdName = $scope.IdType || '';
//                        $scope.IdType = "Others";
//                    }
//
//                    $scope.cardUpdateResultView = true;
////                    console.log('view: ' + $scope.cardUpdateResultView);
//                    setTimeout(function () {
////                        $('#updateResp').css('display', 'none');
//                        $scope.cardUpdateResultView = false;
//                        $scope.$apply($scope.cardUpdateResultView = false);
//                    }, 1500);
//                }
//            }
//
//        }, function error(response) {
////            $scope.showLoader = false;
////            $('.reload').removeClass('fa-spin text-info');
//            $('#checkId').html("Check");
//            $('#checkId').prop('disabled', false);
//            alert('Connection or Server Error. Try again');
//        });
//    };
//
//    $scope.getTopupSales = function (page, paging) {
//        $scope.updateApp();
//        if ($('#start-date').val() && $('#end-date').val()) {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.topupsalesProxy;
//            if (paging) {
//                url = './TopupSalesFunction?action=paging';
//            }
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                $scope.pageNumber = $scope.fgRecordsLastPage;
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "startDate": $scope.startDate,
//                    "endDate": $scope.endDate,
//                    "channel": $scope.channel,
//                    "service": "topupsales",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.topupSalesResp = response.data;
//                if ($scope.topupSalesResp.code === "00") {
//                    if (paging) {
//                        $scope.topupSales = JSON.parse($scope.topupSalesResp.data);
//                        $scope.getOverAllVat();
//                        $scope.getOverAllEtzCom();
//                        $scope.getOverAllGCBCom();
//                        $scope.getOverAllTrans();
//                        $scope.getOverAllDiscount();
//                        $scope.getOverAllAmount();
//                        $scope.getOverAllDiscount();
//                        $scope.getOverallTotalDisVat();
//                        $scope.getOverallVatAddEtz();
//                    } else {
//                        $scope.topupSales = JSON.parse($scope.topupSalesResp.data);
//                        $scope.getOverAllVat();
//                        $scope.getOverAllEtzCom();
//                        $scope.getOverAllGCBCom();
//                        $scope.getOverAllTrans();
//                        $scope.getOverAllDiscount();
//                        $scope.getOverAllAmount();
//                        $scope.getOverAllDiscount();
//                        $scope.getOverallTotalDisVat();
//                        $scope.getOverallVatAddEtz();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert('Select values for start and end date');
//        }
//    };
//
//    $scope.verifyVasgate = function () {
//        $scope.vasRsp = '';
//        $('#verify-btn').prop("disabled", true);
//        $('#verify-btn').html("<i class='fa fa-spinner fa-spin btn-loader'></i> please wait");
//        $scope.updateApp();
//        $http({
//            url: $scope.verifyProxy,
//            method: 'POST',
//            data: $httpParamSerializerJQLike({
//                "merchant": $scope.merchant,
//                "account": $scope.account,
////                "action": 'query',
//                "service": 'vasgate'
//            }),
//            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//        }).then(function success(response) {
//            $('#verify-btn').prop("disabled", false);
//            $('#verify-btn').html(" Verify");
//            $scope.vasRsp = response.data;
//        }, function error(response) {
//            $('#verify-btn').prop("disabled", false);
//            $('#verify-btn').html(" Verify");
//        });
//    };
//
//
//    $scope.getOverAllVat = function () {
//        $http.get('./TopupSalesFunction?action=OverAllVat').then(
//                function (response) {
////                     alert("hello" + JSON.parse(response.data));
//                    $scope.OverallTotalVat = response.data;
//                }
//        );
//    };
//
//    $scope.getOverAllAmount = function () {
//        $http.get('./TopupSalesFunction?action=OverAllTotalAmount').then(
//                function (response) {
//                    $scope.OverallTotalAmount = response.data;
////                    alert($scope.OverallTotalAmount);
//                }
//        );
//    };
//
//    $scope.getOverAllTrans = function () {
//        $http.get('./TopupSalesFunction?action=OverAllTotalTrans').then(
//                function (response) {
//                    $scope.OverallTotalTrans = response.data;
//                }
//        );
//    };
//
//    $scope.getOverAllDiscount = function () {
//        $http.get('./TopupSalesFunction?action=OverAllTotalDiscount').then(
//                function (response) {
//                    $scope.OverallDiscount = response.data;
//                }
//        );
//    };
//
//    $scope.getOverallTotalDisVat = function () {
//        $http.get('./TopupSalesFunction?action=OverallTotalDisVat').then(
//                function (response) {
//                    $scope.OverallTotalDisVat = response.data;
//                }
//        );
//    };
//    $scope.getOverallVatAddEtz = function () {
//        $http.get('./TopupSalesFunction?action=OverallVatAddEtz').then(
//                function (response) {
//                    $scope.OverallVatAddEtz = response.data;
//                }
//        );
//    };
//    $scope.getOverAllEtzCom = function () {
//        $http.get('./TopupSalesFunction?action=OverAllTotalEtzCom').then(
//                function (response) {
//                    $scope.OverallEtzCom = response.data;
//                }
//        );
//    };
//    $scope.getOverAllGCBCom = function () {
//        $http.get('./TopupSalesFunction?action=OverAllTotalGCBCom').then(
//                function (response) {
//                    $scope.OverallGCBCom = response.data;
//                }
//        );
//    };
//    $scope.getDebitStatus = function (page, paging) {
//        $scope.getProfile();
//        if ($('#start-date').val() && $('#end-date').val()) {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.momoProxy;
//            if (paging) {
//                url = './MomoTransactions?action=paging';
//            }
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                $scope.pageNumber = $scope.fgRecordsLastPage;
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "startDate": $scope.startDate,
//                    "endDate": $scope.endDate,
//                    "bank": $scope.bank,
//                    "bank_code": $scope.bank_code,
//                    "uniqueTransId": $scope.uniqueTransId,
//                    "from_source": $scope.from_source,
//                    "subscriberId": $scope.subscriberId2,
//                    "transType": $scope.transType,
//                    "card_num": $scope.card_num,
//                    "status": $scope.status,
//                    "optionType": $scope.optionType,
//                    "service": "debitstatus",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.momoTrxResp = response.data;
//                if ($scope.momoTrxResp.code === "00") {
//                    if (paging) {
//                        $scope.momoTrxs = JSON.parse($scope.momoTrxResp.data);
//                        $scope.getMomoTotalCount();
//                    } else {
//                        $scope.momoTrxs = JSON.parse($scope.momoTrxResp.data);
//                        $scope.getMomoTotalCount();
//                        $scope.getMomoTotalAmount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert('Select values for start and end date');
//        }
//    };
//
//
//
//    $scope.getMobileMoneyStatus = function (page, paging) {
//        $scope.getProfile();
//        if ($('#start-date').val() && $('#end-date').val()) {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.momoProxy;
//            if (paging) {
//                url = './MomoTransactions?action=paging';
//            }
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                $scope.pageNumber = $scope.fgRecordsLastPage;
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "startDate": $scope.startDate,
//                    "endDate": $scope.endDate,
//                    "bank": $scope.bank,
//                    "bank_code": $scope.bank_code,
//                    "uniqueTransId": $scope.uniqueTransId,
//                    "from_source": $scope.from_source,
//                    "subscriberId": $scope.subscriberId2,
//                    "transType": $scope.transType,
//                    "card_num": $scope.card_num,
//                    "status": $scope.status,
//                    "optionType": $scope.optionType,
//                    "service": "checkmomostatus",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.momoTrxResp = response.data;
//                if ($scope.momoTrxResp.code === "00") {
//                    if (paging) {
//                        $scope.momoTrxs = JSON.parse($scope.momoTrxResp.data);
//                        $scope.getMomoTotalCount();
//                    } else {
//                        $scope.momoTrxs = JSON.parse($scope.momoTrxResp.data);
//                        $scope.getMomoTotalCount();
//                        $scope.getMomoTotalAmount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert('Select values for start and end date');
//        }
//    };
//
//    $scope.getICGCTrxn = function (page, paging) {
//        $scope.getProfile();
//        if ($('#start-date').val() && $('#end-date').val()) {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.icgcProxy;
//            if (paging) {
//                url = './ICGCTransactions?action=paging';
//            }
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                $scope.pageNumber = $scope.fgRecordsLastPage;
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "startDate": $scope.startDate,
//                    "endDate": $scope.endDate,
//                    "uniqueTransId": $scope.uniqueTransId,
//                    "from_source": $scope.from_source,
//                    "trans_channel": $scope.trans_channel,
////                    "status": $scope.status,
//                    "service": "transactions",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.icgcTrxResp = response.data;
//                if ($scope.icgcTrxResp.code === "00") {
//                    if (paging) {
//                        $scope.icgcTrxs = JSON.parse($scope.icgcTrxResp.data);
//                        $scope.getICGCTotalCount();
//                        $scope.getICGCTotalAmount();
//                    } else {
//                        $scope.icgcTrxs = JSON.parse($scope.icgcTrxResp.data);
//                        $scope.getICGCTotalCount();
//                        $scope.getICGCTotalAmount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert('Select values for start and end date');
//        }
//    };
//    $scope.searchICGCTrxn = function () {
//        if ($scope.searchTrxnKey.length > 8) {
//            $scope.showLoader = true;
//            $('.reload').addClass('fa-spin text-info');
//            $http({
//                url: $scope.icgcProxy,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "searchKey": $scope.searchTrxnKey,
//                    "service": "search",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.icgcTrxResp = response.data;
//                if ($scope.icgcTrxResp.code === "00") {
////                    console.log(JSON.parse($scope.icgcTrxResp.data));
//                    $scope.icgcTrxs = JSON.parse($scope.icgcTrxResp.data);
//                    $scope.getICGCTotalCount();
//                    $scope.getICGCTotalAmount();
//                } else if ($scope.icgcTrxResp.code === "01") {
//                    $scope.icgcTrxs = "";
//                } else {
//                    $scope.icgcTrxs = "";
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert("Length of Transaction ID must be greater than 8");
//        }
//    };
//    $scope.getICGCTotalCount = function () {
//        $http.get('./ICGCTransactions?action=count').then(
//                function (response) {
//                    $scope.fgRecordsTotalCount = JSON.parse(response.data);
//                    $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//                    if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//                        $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//                    }
//                }
//        );
//    };
//    $scope.getICGCTotalAmount = function () {
//        $http.get('./ICGCTransactions?action=totalAmount').then(
//                function (response) {
//                    $scope.fgRecordsTotalAmount = response.data;
//                });
//    };
//    $scope.getGCBMerchantPayReports = function (page, paging) {
//
//        $scope.getProfile();
//        if ($('#start-date').val() && $('#end-date').val()) {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.gcbMerchantPayReportsProxy;
//            if (paging) {
//                url = './GCBMPayReports?action=paging';
//            }
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                $scope.pageNumber = $scope.fgRecordsLastPage;
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "startDate": $scope.startDate,
//                    "endDate": $scope.endDate,
//                    "service": "reports",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.gcbMerchantPayResp = response.data;
//                if ($scope.gcbMerchantPayResp.code === "00") {
//                    if (paging) {
//                        $scope.gcbMerchantPayReports = JSON.parse($scope.gcbMerchantPayResp.data);
//                        $scope.getGCBMerchantPayReportCount();
//                    } else {
//                        $scope.gcbMerchantPayReports = JSON.parse($scope.gcbMerchantPayResp.data);
//                        $scope.getGCBMerchantPayReportCount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert('Select values for start and end date');
//        }
//    };
//    $scope.getGCBMerchantPayReportCount = function () {
//        $http.get('./GCBMPayReports?action=count').then(
//                function (response) {
//                    $scope.fgRecordsTotalCount = JSON.parse(response.data);
//                    $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//                    if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//                        $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//                    }
//                }
//        );
//    };
//    $scope.getGCBMerchantTrxn = function (page, paging) {
//        $scope.getProfile();
//        if ($('#start-date').val() && $('#end-date').val()) {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.gcbMerchantProxy;
//            if (paging) {
//                url = './GCBMerchantTransactions?action=paging';
//            }
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                $scope.pageNumber = $scope.fgRecordsLastPage;
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "startDate": $scope.startDate,
//                    "endDate": $scope.endDate,
//                    "uniqueTransId": $scope.uniqueTransId,
//                    "from_source": $scope.from_source,
//                    "merchant": $scope.merchant,
//                    "status": $scope.status,
//                    "service": "transactions",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.gcbMerchantTrxResp = response.data;
//                if ($scope.gcbMerchantTrxResp.code === "00") {
//                    if (paging) {
//                        $scope.gcbMerchantTrxs = JSON.parse($scope.gcbMerchantTrxResp.data);
//                        $scope.getGCBMerchantTotalCount();
//                        $scope.getGCBMerchantTotalAmount();
//                    } else {
//                        $scope.gcbMerchantTrxs = JSON.parse($scope.gcbMerchantTrxResp.data);
//                        $scope.getGCBMerchantTotalCount();
//                        $scope.getGCBMerchantTotalAmount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert('Select values for start and end date');
//        }
//    };
//    $scope.searchGCBMerchantTrxn = function () {
//        if ($scope.searchTrxnKey.length > 8) {
//            $scope.showLoader = true;
//            $('.reload').addClass('fa-spin text-info');
//            $http({
//                url: $scope.gcbMerchantProxy,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "searchKey": $scope.searchTrxnKey,
//                    "service": "search",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.gcbMerchantTrxResp = response.data;
//                if ($scope.gcbMerchantTrxResp.code === "00") {
////                    console.log(JSON.parse($scope.gcbMerchantTrxResp.data));
//                    $scope.gcbMerchantTrxs = JSON.parse($scope.gcbMerchantTrxResp.data);
//                    $scope.getGCBMerchantTotalCount();
//                    $scope.getGCBMerchantTotalAmount();
//                } else if ($scope.gcbMerchantTrxResp.code === "01") {
//                    $scope.gcbMerchantTrxs = "";
//                } else {
//                    $scope.gcbMerchantTrxs = "";
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert("Length of Transaction ID must be greater than 8");
//        }
//    };
//    $scope.getGCBMerchantTotalCount = function () {
//        $http.get('./GCBMerchantTransactions?action=count').then(
//                function (response) {
//                    $scope.fgRecordsTotalCount = JSON.parse(response.data);
//                    $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//                    if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//                        $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//                    }
//                }
//        );
//    };
//    $scope.getGCBMerchantTotalAmount = function () {
//        $http.get('./GCBMerchantTransactions?action=totalAmount').then(
//                function (response) {
//                    $scope.fgRecordsTotalAmount = response.data;
//                });
//    };
//
//    $scope.getMerchantTrxn = function (page, paging) {
//        $scope.getProfile();
//        if ($('#start-date').val() && $('#end-date').val()) {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.MerchantPayProxy;
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                $scope.pageNumber = $scope.fgRecordsLastPage;
//            if (paging) {
////                url = './MerchantPayTrans?action=paging';
//                console.log('DATA: ' + page + ' : ' + $scope.fgRecordsLastPage);
//                $scope.begin = (page - 1) * parseInt($scope.rowsPerPage);
//                $scope.end = parseInt($scope.begin) + parseInt($scope.rowsPerPage);
////                console.log('begin: ' + $scope.begin + ' : ' + $scope.end);
//                $scope.filteredMerchantPayTrxs = $scope.MerchantTrxs.slice($scope.begin, $scope.end);
//                $scope.getMerchantTotalCount();
//                $scope.getMerchantTotalAmount();
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//            }
//
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "startDate": $scope.startDate,
//                    "endDate": $scope.endDate,
//                    "uniqueTransId": $scope.uniqueTransId,
//                    "from_source": $scope.from_source,
//                    "merchant": $scope.merchant,
//                    "status": $scope.status,
//                    "product": $('#product').val() || 'ALL',
//                    "service": "transactions",
//                    "bank": $scope.bank,
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.MerchantTrxResp = response.data;
//                if ($scope.MerchantTrxResp.code === "00") {
//
//                    $scope.MerchantTrxs = JSON.parse($scope.MerchantTrxResp.data);
//                    $scope.getMerchantTotalCount();
//                    $scope.getMerchantTotalAmount();
//                    $scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage);
//                    $scope.end = parseInt($scope.begin + $scope.rowsPerPage);
//                    $scope.filteredMerchantPayTrxs = $scope.MerchantTrxs.slice($scope.begin, $scope.end);
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert('Select values for start and end date');
//        }
//    };
//
//    $scope.searchMerchantPayTrxn = function () {
//        if ($scope.searchTrxnKey.length > 8) {
//            $scope.showLoader = true;
//            $('.reload').addClass('fa-spin text-info');
//            $http({
//                url: $scope.MerchantPayProxy,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "searchKey": $scope.searchTrxnKey,
//                    "service": "search"
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.MerchantTrxResp = response.data;
//                if ($scope.MerchantTrxResp.code === "00") {
////                    console.log(JSON.parse($scope.gcbMerchantTrxResp.data));
//                    $scope.MerchantTrxs = JSON.parse($scope.MerchantTrxResp.data);
//                    $scope.getMerchantTotalCount();
//                    $scope.getMerchantTotalAmount();
//                    $scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage);
//                    $scope.end = parseInt($scope.begin + $scope.rowsPerPage);
//                    $scope.filteredMerchantPayTrxs = $scope.MerchantTrxs.slice($scope.begin, $scope.end);
//                } else if ($scope.MerchantTrxResp.code === "01") {
//                    $scope.MerchantTrxs = "";
//                } else {
//                    $scope.MerchantTrxs = "";
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert("Length of Transaction ID must be greater than 8");
//        }
//    };
//    $scope.getMerchantTotalCount = function () {
//        $scope.fgRecordsTotalCount = $scope.MerchantTrxs.length;
//        $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//        if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//            $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//        }
//    };
//    $scope.getMerchantTotalAmount = function () {
//        $scope.fgRecordsTotalAmount = $scope.sumAmount($scope.MerchantTrxs, 'status', 'SUCCESSFUL', 'trans_amount').toFixed(2);
//    };
//
//    $scope.getCorporatePayTrxns = function (page, paging) {
//        $scope.getProfile();
////        alert($scope.uniqueTransid);
////        alert('hello');
//        $scope.showCPayTransView = false;
////        alert($scope.searchPaymentId);
//        if (($('#start-date').val() && $('#end-date').val()) || $scope.searchPaymentId === 'true') {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.corporatepayProxy;
//            if (paging) {
//                url = './CorporatePayTransactions?action=paging';
//            }
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                $scope.pageNumber = $scope.fgRecordsLastPage;
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "startDate": $scope.startDate,
//                    "endDate": $scope.endDate,
//                    "uniqueTransId": $scope.uniqueTransid,
//                    "companyId": $("#cpName").val(),
//                    "paymentId": $scope.paymentId,
//                    "batchId": $scope.batchId,
//                    "service": "transactions",
//                    "payId": $scope.searchPaymentId,
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.cpayTrxResp = response.data;
//                if ($scope.cpayTrxResp.code === "00") {
//                    if ($scope.searchPaymentId === 'true') {
//                        $scope.showCPayTransView = true;
//                    }
//                    if (paging) {
//                        $scope.cpayTrxs = JSON.parse($scope.cpayTrxResp.data);
//                        $scope.getCorporatePayTotalCount();
//                        $scope.getCorporatePayTotalAmount();
//                    } else {
//                        $scope.cpayTrxs = JSON.parse($scope.cpayTrxResp.data);
//                        $scope.getCorporatePayTotalCount();
//                        $scope.getCorporatePayTotalAmount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert('Select values for start and end date');
//        }
//    };
//    $scope.searchCorporatePayTrxn = function () {
//        if ($scope.searchTrxnKey.length > 8) {
//            $scope.showLoader = true;
//            $('.reload').addClass('fa-spin text-info');
//            $http({
//                url: $scope.gcbMerchantProxy,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "searchKey": $scope.searchTrxnKey,
//                    "service": "search",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.corporatepayTrxResp = response.data;
//                if ($scope.corporatepayTrxResp.code === "00") {
////                    console.log(JSON.parse($scope.gcbMerchantTrxResp.data));
//                    $scope.corporatepayTrxs = JSON.parse($scope.corporatepayTrxResp.data);
//                    $scope.getGCBMerchantTotalCount();
//                    $scope.getGCBMerchantTotalAmount();
//                } else if ($scope.corporatepayTrxResp.code === "01") {
//                    $scope.corporatepayTrxs = "";
//                } else {
//                    $scope.corporatepayTrxs = "";
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert("Length of Transaction ID must be greater than 8");
//        }
//    };
//    $scope.getCorporatePayTotalCount = function () {
//        $http.get('./CorporatePayTransactions?action=count').then(
//                function (response) {
//                    $scope.fgRecordsTotalCount = JSON.parse(response.data);
//                    $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//                    if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//                        $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//                    }
//                }
//        );
//    };
//    $scope.getCorporatePayTotalAmount = function () {
//        $http.get('./CorporatePayTransactions?action=totalAmount').then(
//                function (response) {
//                    $scope.fgRecordsTotalAmount = response.data;
//                });
//    };
//
//    $scope.checkCOPTransfer = function (descr) {
//        if (descr.startsWith("TRANSFER:;")) {
//            if (descr.startsWith("TRANSFER:;COP")) {
////                console.log("TRANSFER:;COP" + descr);
//                return false;
//            } else {
////                console.log("TRANSFER:;" + descr);
//                return true;
//            }
//        }
//        return false;
//    };
//
//    $scope.getCOPTransDetails = function (descr) {
//        $scope.paymentId = descr.split('TRANSFER:;').pop().split(':')[0];
//        $scope.paymentDesc = descr.split('TRANSFER:;').pop().split(':')[1].substr(0, descr.split('TRANSFER:;').pop().split(':')[1].length - 1);
////        alert($scope.paymentDesc);
//        $scope.searchPaymentId = 'true';
//        $scope.getCorporatePayTrxns(1);
//    };
//
//    $scope.checkGcbOnboardingCount = function () {
//        var selectedCount = 0;
//        for (var k = 0; k < $scope.GcbOnboardRequests.length; k++)
//        {
//            if ($scope.GcbOnboardRequests[k].selected === true)
//            {
//                selectedCount += 1;
//            }
//
//        }
//        if (selectedCount > 0) {
//
//            $('#AuthorizeBtn').prop("disabled", false);
//        } else {
//            $('#AuthorizeBtn').prop("disabled", true);
//        }
//
//    };
//    $scope.confirmationDialogConfig = {};
//
//    $scope.confirmationDialog = function (action) {
//
////        switch ($scope.profile.role_id) {
////
////            case "3":
//        $scope.confirmationDialogConfig = {
//            title: "CONFIRM ACTION",
//            message: "Are you sure you want proceed?",
//            buttons: [{
//                    label: "PROCEED",
//                    action: action
//                }]
//        };
////                break;
////            case "1":
////            case "5":
////                $scope.confirmationDialogConfig = {
////                    title: "CONFIRM AUTHORIZATION",
////                    message: "Are you sure you want authorize?",
////                    buttons: [{
////                            label: "Authorize",
////                            action: "authorizeReprocess"
////                        }]
////                };
////                break;
////            default:
////                break;
////        }
//        $scope.showDialog(true);
//    };
//
//    $scope.executeDialogAction = function (action) {
//        if (typeof $scope[action] === "function") {
//            $scope[action]();
//        }
//    };
//    $scope.dismissModal = function () {
//        $scope.showDialog(false);
//    };
//
//    $scope.showDialog = function (flag) {
//        jQuery("#confirmation-dialog .modal").modal(flag ? 'show' : 'hide');
//    };
//    $scope.verifyGcbOnboardingRequests = function (page, paging) {
//        $scope.referencesArray = "";
//        $scope.transactions = "";
//        $scope.gcbOBRequests = [];
//        $scope.showDetailsBox = false;
//        $scope.rectifyRectifyRequest = false;
//        $scope.updateRectifyRequest = false;
//        $scope.pinRequestOK = false;
//        $scope.updateRequest = false;
//        $scope.pinRequestNotOk = false;
//        $scope.updateRectifyRequest = false;
////        $scope.showLoader = true;
//        $scope.showDialog(false);
//        for (var k = 0; k < $scope.GcbOnboardRequests.length; k++)
//        {
//            if ($scope.GcbOnboardRequests[k].selected === true)
//            {
//                $scope.gcbOBRequests.push({
//                    "ID": $scope.GcbOnboardRequests[k].ID,
//                    "CARD_ACCT": $scope.GcbOnboardRequests[k].CARD_ACCT,
//                    "BRANCH": $scope.GcbOnboardRequests[k].BRANCH,
//                    "LNAME": $scope.GcbOnboardRequests[k].LNAME,
//                    "FNAME": $scope.GcbOnboardRequests[k].FNAME,
//                    "BANK": $scope.GcbOnboardRequests[k].BANK,
//                    "BATCHID": $scope.GcbOnboardRequests[k].BATCHID,
//                    "SECONDARY": $scope.GcbOnboardRequests[k].SECONDARY,
//                    "STATUS2": $scope.GcbOnboardRequests[k].STATUS2,
//                    "CREATED": $scope.GcbOnboardRequests[k].CREATED,
//                    "COY": $scope.GcbOnboardRequests[k].COY,
//                    "STATUS": $scope.GcbOnboardRequests[k].STATUS,
//                    "EMAIL": $scope.GcbOnboardRequests[k].EMAIL,
//                    "CITY": $scope.GcbOnboardRequests[k].CITY,
//                    "ACCT_ID": $scope.GcbOnboardRequests[k].ACCT_ID,
//                    "COUNTRY": $scope.GcbOnboardRequests[k].COUNTRY,
//                    "BANKOK": $scope.GcbOnboardRequests[k].BANKOK,
//                    "CENTRALOK": $scope.GcbOnboardRequests[k].CENTRALOK,
//                    "MOBILE": $scope.GcbOnboardRequests[k].MOBILE,
//                    "STATE": $scope.GcbOnboardRequests[k].STATE,
//                    "MODIFIED": $scope.GcbOnboardRequests[k].MODIFIED
//                });
//            }
//        }
//
////        console.log($scope.gcbOBRequests);
//
//
//        if ($scope.gcbOBRequests.length > 0) {
//            $scope.showLoader = true;
////            console.log('sending request' + JSON.stringify($scope.gcbOBRequests));
//            $http({
//                url: $scope.gcbOnboardingProxy,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "requests": JSON.stringify($scope.gcbOBRequests),
//                    "service": "verifyRequests",
//                    "status": $scope.OBStatus,
//                    "pageNumber": $scope.pageNumber1,
//                    "rowsPerPage": $scope.rowsPerPage1
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.GcbOnboardVerifyResp = response.data;
//                if ($scope.GcbOnboardRequestsResp.code === "00") {
////                    $('.ok').css('display', 'block');
//                    $('.reptab').siblings().removeClass('active');
//                    $('#ok').addClass('active');
//                    $(".toggle").css('display', 'none');
////                    var c = $(this).attr("data-tab");
////                    c = "." + c;
//                    $('.ok').css('display', 'block');
//                    if (paging) {
//                        $scope.GcbOnboardVerifiedOk = JSON.parse(JSON.parse($scope.GcbOnboardVerifyResp.data).Ok);
//                        $scope.GcbOnboardVerifiedNotOk = JSON.parse(JSON.parse($scope.GcbOnboardVerifyResp.data).NotOk);
//                        $scope.GcbOnboardVerifiedNotFound = JSON.parse(JSON.parse($scope.GcbOnboardVerifyResp.data).NotFound);
//                        $scope.getGcbOnboardVerifiedOkTotalCount();
//                        $scope.getGcbOnboardVerifiedNotOkTotalCount();
//                        $scope.getGcbOnboardVerifiedNotFoundTotalCount();
//                    } else {
////                        console.log('DATA ' + JSON.parse($scope.GcbOnboardVerifyResp.data).NotFound);
//                        $scope.GcbOnboardVerifiedOk = JSON.parse(JSON.parse($scope.GcbOnboardVerifyResp.data).Ok);
//                        $scope.GcbOnboardVerifiedNotOk = JSON.parse(JSON.parse($scope.GcbOnboardVerifyResp.data).NotOk);
//                        $scope.GcbOnboardVerifiedNotFound = JSON.parse(JSON.parse($scope.GcbOnboardVerifyResp.data).NotFound);
////                        $scope.GcbOnboardVerified = JSON.parse(JSON.parse($scope.GcbOnboardVerifyResp.data).NotFound);
//                        $scope.getGcbOnboardVerifiedOkTotalCount();
//                        $scope.getGcbOnboardVerifiedNotOkTotalCount();
//                        $scope.getGcbOnboardVerifiedNotFoundTotalCount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        }
//    };
//
//    $scope.updateGcbOnboardingRequests = function (page, paging) {
//        $scope.gcbOBUpdateRequests = [];
//        $scope.showDetailsBox = false;
//        $scope.updateRequest = false;
//        $scope.showPinSelect = false;
////        $scope.showLoader = true;
//        $scope.showDialog(false);
//        for (var k = 0; k < $scope.GcbOnboardVerifiedOk.length; k++)
//        {
//            if ($scope.GcbOnboardVerifiedOk[k].selected === true)
//            {
//                $scope.gcbOBUpdateRequests.push({
//                    "ID": $scope.GcbOnboardVerifiedOk[k].ID,
//                    "CARD_ACCT": $scope.GcbOnboardVerifiedOk[k].CARD_ACCT,
//                    "BRANCH": $scope.GcbOnboardVerifiedOk[k].BRANCH,
//                    "LNAME": $scope.GcbOnboardVerifiedOk[k].LNAME,
//                    "FNAME": $scope.GcbOnboardVerifiedOk[k].FNAME,
//                    "BATCHID": $scope.GcbOnboardVerifiedOk[k].BATCHID,
//                    "SECONDARY": $scope.GcbOnboardVerifiedOk[k].SECONDARY,
//                    "STATUS2": $scope.GcbOnboardVerifiedOk[k].STATUS2,
//                    "BANK": $scope.GcbOnboardVerifiedOk[k].BANK,
//                    "CREATED": $scope.GcbOnboardVerifiedOk[k].CREATED,
//                    "COY": $scope.GcbOnboardVerifiedOk[k].COY,
//                    "STATUS": $scope.GcbOnboardVerifiedOk[k].STATUS,
//                    "EMAIL": $scope.GcbOnboardVerifiedOk[k].EMAIL,
//                    "CITY": $scope.GcbOnboardVerifiedOk[k].CITY,
//                    "ACCT_ID": $scope.GcbOnboardVerifiedOk[k].ACCT_ID,
//                    "COUNTRY": $scope.GcbOnboardVerifiedOk[k].COUNTRY,
//                    "BANKOK": $scope.GcbOnboardVerifiedOk[k].BANKOK,
//                    "CENTRALOK": $scope.GcbOnboardVerifiedOk[k].CENTRALOK,
//                    "MOBILE": $scope.GcbOnboardVerifiedOk[k].MOBILE,
//                    "STATE": $scope.GcbOnboardVerifiedOk[k].STATE,
//                    "MODIFIED": $scope.GcbOnboardVerifiedOk[k].MODIFIED,
//                    "MOBILEOK": $scope.GcbOnboardVerifiedOk[k].MOBILEOK,
//                    "ECARDHOLDEROK": $scope.GcbOnboardVerifiedOk[k].ECARDHOLDEROK,
//                    "OLDMOBILE": $scope.GcbOnboardVerifiedOk[k].OLDMOBILE,
//                    "subscriberId": $scope.GcbOnboardVerifiedOk[k].subscriberId,
//                    "deviceId": $scope.GcbOnboardVerifiedOk[k].deviceId
//                });
//            }
//        }
//
////        console.log('Update Request: ' + $scope.gcbOBUpdateRequests);
//
//
//        if ($scope.gcbOBUpdateRequests.length > 0) {
//            $scope.showLoader = true;
////            console.log('sending request' + JSON.stringify($scope.gcbOBRequests));
//            $http({
//                url: $scope.gcbOnboardingProxy,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "requests": JSON.stringify($scope.gcbOBUpdateRequests),
//                    "service": "updateRequests",
//                    "pageNumber": $scope.pageNumber2,
//                    "rowsPerPage": $scope.rowsPerPage2
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.GcbOnboardUpdateResp = response.data;
//                if ($scope.GcbOnboardUpdateResp.code === "00") {
//
//                    if (paging) {
////                        $scope.updateRequest = true;
//                        $scope.showPinSelect = true;
//
//                        $scope.GcbOnboardVerifiedOk = JSON.parse($scope.GcbOnboardUpdateResp.data);
//                        if ($scope.GcbOnboardVerifiedOk.length > 0) {
//                            $scope.updateRequest = $scope.GcbOnboardVerifiedOk[0].hasOwnProperty("UPDATEOK");
//                            $scope.pinRequestOk = $scope.GcbOnboardVerifiedOk[0].hasOwnProperty("PINOK");
//                        }
//
//                    } else {
////                        $scope.updateRequest = true;
//                        $scope.showPinSelect = true;
////                        console.log('DATA ' + JSON.parse($scope.GcbOnboardVerifyResp.data).NotFound);
//                        $scope.GcbOnboardVerifiedOk = JSON.parse($scope.GcbOnboardUpdateResp.data);
////                        console.log('DATA: ' + $scope.GcbOnboardVerifiedOk);
//                        if ($scope.GcbOnboardVerifiedOk.length > 0) {
//                            $scope.updateRequest = $scope.GcbOnboardVerifiedOk[0].hasOwnProperty("UPDATEOK");
//                            $scope.pinRequestOk = $scope.GcbOnboardVerifiedOk[0].hasOwnProperty("PINOK");
//                        }
//                        $scope.getGcbOnboardVerifiedOkTotalCount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        }
//    };
//
//    $scope.sendpinGcbOnboardingRequestsOK = function (page, paging) {
//        $scope.gcbOBPinRequestsOK = [];
//        $scope.showDetailsBox = false;
//        $scope.pinRequestOK = false;
////        $scope.showLoader = true;
//        $scope.showDialog(false);
//        for (var k = 0; k < $scope.GcbOnboardVerifiedOk.length; k++)
//        {
//            if ($scope.GcbOnboardVerifiedOk[k].selected === true)
//            {
//                $scope.gcbOBPinRequestsOK.push({
//                    "ID": $scope.GcbOnboardVerifiedOk[k].ID,
//                    "CARD_ACCT": $scope.GcbOnboardVerifiedOk[k].CARD_ACCT,
//                    "BRANCH": $scope.GcbOnboardVerifiedOk[k].BRANCH,
//                    "LNAME": $scope.GcbOnboardVerifiedOk[k].LNAME,
//                    "FNAME": $scope.GcbOnboardVerifiedOk[k].FNAME,
//                    "BATCHID": $scope.GcbOnboardVerifiedOk[k].BATCHID,
//                    "SECONDARY": $scope.GcbOnboardVerifiedOk[k].SECONDARY,
//                    "STATUS2": $scope.GcbOnboardVerifiedOk[k].STATUS2,
//                    "BANK": $scope.GcbOnboardVerifiedOk[k].BANK,
//                    "CREATED": $scope.GcbOnboardVerifiedOk[k].CREATED,
//                    "COY": $scope.GcbOnboardVerifiedOk[k].COY,
//                    "STATUS": $scope.GcbOnboardVerifiedOk[k].STATUS,
//                    "EMAIL": $scope.GcbOnboardVerifiedOk[k].EMAIL,
//                    "CITY": $scope.GcbOnboardVerifiedOk[k].CITY,
//                    "ACCT_ID": $scope.GcbOnboardVerifiedOk[k].ACCT_ID,
//                    "COUNTRY": $scope.GcbOnboardVerifiedOk[k].COUNTRY,
//                    "BANKOK": $scope.GcbOnboardVerifiedOk[k].BANKOK,
//                    "CENTRALOK": $scope.GcbOnboardVerifiedOk[k].CENTRALOK,
//                    "MOBILE": $scope.GcbOnboardVerifiedOk[k].MOBILE,
//                    "STATE": $scope.GcbOnboardVerifiedOk[k].STATE,
//                    "MODIFIED": $scope.GcbOnboardVerifiedOk[k].MODIFIED,
//                    "MOBILEOK": $scope.GcbOnboardVerifiedOk[k].MOBILEOK,
//                    "ECARDHOLDEROK": $scope.GcbOnboardVerifiedOk[k].ECARDHOLDEROK,
//                    "OLDMOBILE": $scope.GcbOnboardVerifiedOk[k].OLDMOBILE,
//                    "subscriberId": $scope.GcbOnboardVerifiedOk[k].subscriberId,
//                    "deviceId": $scope.GcbOnboardVerifiedOk[k].deviceId,
//                    "UPDATEOK": $scope.GcbOnboardVerifiedOk[k].UPDATEOK
//                });
//            }
//        }
//
////        console.log('Pin Request: ' + JSON.stringify($scope.gcbOBPinRequestsOK));
//
//
//        if ($scope.gcbOBPinRequestsOK.length > 0) {
//            $scope.showLoader = true;
////            console.log('sending request' + JSON.stringify($scope.gcbOBRequests));
//            $http({
//                url: $scope.gcbOnboardingProxy,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "requests": JSON.stringify($scope.gcbOBPinRequestsOK),
//                    "service": "pinRequests",
//                    "pageNumber": $scope.pageNumber2,
//                    "rowsPerPage": $scope.rowsPerPage2
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.GcbOnboardOkPinResp = response.data;
//                if ($scope.GcbOnboardOkPinResp.code === "00") {
//
//                    if (paging) {
////                        $scope.pinRequestOK = true;
//                        $scope.showPinSelect = false;
//                        $scope.GcbOnboardVerifiedOk = JSON.parse($scope.GcbOnboardOkPinResp.data);
//                        if ($scope.GcbOnboardVerifiedOk.length > 0) {
////                            $scope.rectifyRectifyRequest = $scope.GcbOnboardVerifiedOk[0].hasOwnProperty("RECTIFYOK");
//                            $scope.updateRequest = $scope.GcbOnboardVerifiedOk[0].hasOwnProperty("UPDATEOK");
//                            $scope.pinRequestOk = $scope.GcbOnboardVerifiedOk[0].hasOwnProperty("PINOK");
//                        }
//
//                    } else {
////                        $scope.pinRequestOK = true;
//                        $scope.showPinSelect = false;
////                        console.log('DATA ' + JSON.parse($scope.GcbOnboardVerifyResp.data).NotFound);
//                        $scope.GcbOnboardVerifiedOk = JSON.parse($scope.GcbOnboardOkPinResp.data);
////                        console.log('DATA: ' + $scope.GcbOnboardVerifiedOk);
//                        if ($scope.GcbOnboardVerifiedOk.length > 0) {
////                            $scope.rectifyRectifyRequest = $scope.GcbOnboardVerifiedOk[0].hasOwnProperty("RECTIFYOK");
//                            $scope.updateRequest = $scope.GcbOnboardVerifiedOk[0].hasOwnProperty("UPDATEOK");
//                            $scope.pinRequestOk = $scope.GcbOnboardVerifiedOk[0].hasOwnProperty("PINOK");
//                        }
//                        $scope.getGcbOnboardVerifiedOkTotalCount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        }
//    };
//
//    $scope.sendpinGcbOnboardingRequestsNotOK = function (page, paging) {
//        $scope.gcbOBPinRequestsNotOK = [];
//        $scope.showDetailsBox = false;
//        $scope.pinRequestNotOk = false;
////        $scope.showLoader = true;
//        $scope.showDialog(false);
//        for (var k = 0; k < $scope.GcbOnboardVerifiedNotOk.length; k++)
//        {
//            if ($scope.GcbOnboardVerifiedNotOk[k].selected === true)
//            {
//                $scope.gcbOBPinRequestsNotOK.push({
//                    "ID": $scope.GcbOnboardVerifiedNotOk[k].ID,
//                    "CARD_ACCT": $scope.GcbOnboardVerifiedNotOk[k].CARD_ACCT,
//                    "BRANCH": $scope.GcbOnboardVerifiedNotOk[k].BRANCH,
//                    "LNAME": $scope.GcbOnboardVerifiedNotOk[k].LNAME,
//                    "FNAME": $scope.GcbOnboardVerifiedNotOk[k].FNAME,
//                    "BATCHID": $scope.GcbOnboardVerifiedNotOk[k].BATCHID,
//                    "SECONDARY": $scope.GcbOnboardVerifiedNotOk[k].SECONDARY,
//                    "STATUS2": $scope.GcbOnboardVerifiedNotOk[k].STATUS2,
//                    "BANK": $scope.GcbOnboardVerifiedNotOk[k].BANK,
//                    "CREATED": $scope.GcbOnboardVerifiedNotOk[k].CREATED,
//                    "COY": $scope.GcbOnboardVerifiedNotOk[k].COY,
//                    "STATUS": $scope.GcbOnboardVerifiedNotOk[k].STATUS,
//                    "EMAIL": $scope.GcbOnboardVerifiedNotOk[k].EMAIL,
//                    "CITY": $scope.GcbOnboardVerifiedNotOk[k].CITY,
//                    "ACCT_ID": $scope.GcbOnboardVerifiedNotOk[k].ACCT_ID,
//                    "COUNTRY": $scope.GcbOnboardVerifiedNotOk[k].COUNTRY,
//                    "BANKOK": $scope.GcbOnboardVerifiedNotOk[k].BANKOK,
//                    "CENTRALOK": $scope.GcbOnboardVerifiedNotOk[k].CENTRALOK,
//                    "MOBILE": $scope.GcbOnboardVerifiedNotOk[k].MOBILE,
//                    "STATE": $scope.GcbOnboardVerifiedNotOk[k].STATE,
//                    "MODIFIED": $scope.GcbOnboardVerifiedNotOk[k].MODIFIED,
//                    "MOBILEOK": $scope.GcbOnboardVerifiedNotOk[k].MOBILEOK,
//                    "ECARDHOLDEROK": $scope.GcbOnboardVerifiedNotOk[k].ECARDHOLDEROK,
//                    "OLDMOBILE": $scope.GcbOnboardVerifiedNotOk[k].OLDMOBILE,
//                    "subscriberId": $scope.GcbOnboardVerifiedNotOk[k].subscriberId,
//                    "deviceId": $scope.GcbOnboardVerifiedNotOk[k].deviceId,
//                    "UPDATEOK": $scope.GcbOnboardVerifiedNotOk[k].UPDATEOK
//                });
//            }
//        }
//
//        console.log('Pin Request: ' + $scope.gcbOBPinRequestsNotOK);
//
//
//        if ($scope.gcbOBPinRequestsNotOK.length > 0) {
//            $scope.showLoader = true;
////            console.log('sending request' + JSON.stringify($scope.gcbOBRequests));
//            $http({
//                url: $scope.gcbOnboardingProxy,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "requests": JSON.stringify($scope.gcbOBPinRequestsNotOK),
//                    "service": "pinRequests",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.GcbOnboardPinNotOkResp = response.data;
//                if ($scope.GcbOnboardPinNotOkResp.code === "00") {
//
//                    if (paging) {
////                        $scope.pinRequestNotOk = true;
//                        $scope.GcbOnboardVerifiedNotOk = JSON.parse($scope.GcbOnboardPinNotOkResp.data);
//                        if ($scope.GcbOnboardVerifiedNotOk.length > 0) {
//                            $scope.rectifyRectifyRequest = $scope.GcbOnboardVerifiedNotOk[0].hasOwnProperty("RECTIFYOK");
//                            $scope.updateRectifyRequest = $scope.GcbOnboardVerifiedNotOk[0].hasOwnProperty("UPDATEOK");
//                            $scope.pinRequestNotOk = $scope.GcbOnboardVerifiedNotOk[0].hasOwnProperty("PINOK");
//                        }
//
//                    } else {
////                        $scope.pinRequestNotOk = true;
////                        console.log('DATA ' + JSON.parse($scope.GcbOnboardVerifyResp.data).NotFound);
//                        $scope.GcbOnboardVerifiedNotOk = JSON.parse($scope.GcbOnboardPinNotOkResp.data);
////                        console.log('DATA: ' + $scope.GcbOnboardVerifiedNotOk);
//                        if ($scope.GcbOnboardVerifiedNotOk.length > 0) {
//                            $scope.rectifyRectifyRequest = $scope.GcbOnboardVerifiedNotOk[0].hasOwnProperty("RECTIFYOK");
//                            $scope.updateRectifyRequest = $scope.GcbOnboardVerifiedNotOk[0].hasOwnProperty("UPDATEOK");
//                            $scope.pinRequestNotOk = $scope.GcbOnboardVerifiedNotOk[0].hasOwnProperty("PINOK");
//                        }
//                        $scope.getGcbOnboardVerifiedNotOkTotalCount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        }
//    };
//
//    $scope.checkGcbOnboardNum = function () {
//        var selectedCount = 0;
//        for (var k = 0; k < $scope.GcbOnboardRequests.length; k++)
//        {
//            if ($scope.GcbOnboardRequests[k].selected === true)
//            {
//                selectedCount += 1;
//            }
//
//        }
//        if (selectedCount > 0) {
//            $('#VerifyBtn').prop("disabled", false);
//        } else {
//            $('#VerifyBtn').prop("disabled", true);
//        }
//
//    };
//    $scope.checkGcbOnboardVerifyNum = function () {
//        var selectedCount = 0;
//        for (var k = 0; k < $scope.GcbOnboardVerified.length; k++)
//        {
//            if ($scope.GcbOnboardVerified[k].selected === true)
//            {
//                selectedCount += 1;
//            }
//
//        }
//        if (selectedCount > 0) {
//            $('#VerifyBtn').prop("disabled", false);
//        } else {
//            $('#VerifyBtn').prop("disabled", true);
//        }
//
//    };
//    $scope.checkGcbOnboardVerifyNotOkNum = function () {
//        var selectedCount = 0;
//        for (var k = 0; k < $scope.GcbOnboardVerifiedNotOk.length; k++)
//        {
//            if ($scope.GcbOnboardVerifiedNotOk[k].selected === true)
//            {
//                selectedCount += 1;
//            }
//
//        }
//        if (selectedCount > 0) {
//            $('#VerifyBtn').prop("disabled", false);
//        } else {
//            $('#VerifyBtn').prop("disabled", true);
//        }
//
//    };
//
//    $scope.getGcbOnboardRequests = function (page, paging) {
//        $scope.getProfile();
//        if ($('#start-date').val() && $('#end-date').val()) {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $scope.pageNumber1 = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.gcbOnboardingProxy;
//            if (paging) {
//                url = './GCBOnboardingRequests?action=paging';
//            }
//            if ($scope.pageNumber1 < 1)
//                $scope.pageNumber1 = 1;
//            if ($scope.pageNumber1 !== 1 && $scope.pageNumber1 > $scope.fgRecordsLastPage1)
//                $scope.pageNumber1 = $scope.fgRecordsLastPage1;
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "startDate": $scope.startDate,
//                    "endDate": $scope.endDate,
//                    "accountNumber": $scope.cardnum,
//                    "mobile": $scope.mobile_no,
//                    "status": $scope.OBStatus,
//                    "bank": $scope.OBBank,
//                    "service": "getrequests",
//                    "pageNumber": $scope.pageNumber1,
//                    "rowsPerPage": $scope.rowsPerPage1
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.GcbOnboardRequestsResp = response.data;
//                if ($scope.GcbOnboardRequestsResp.code === "00") {
//                    if (paging) {
//                        $scope.GcbOnboardRequests = JSON.parse($scope.GcbOnboardRequestsResp.data);
//                        $scope.getGcbOnboardRequestsTotalCount();
//                    } else {
//                        $scope.GcbOnboardRequests = JSON.parse($scope.GcbOnboardRequestsResp.data);
//                        $scope.getGcbOnboardRequestsTotalCount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert('Select values for start and end date');
//        }
//    };
//
//    $scope.rectifyGcbOnboardingRequests = function (page, paging) {
//        $scope.gcbOBRectifyRequests = [];
//        $scope.showDetailsBox = false;
//        $scope.rectifyRectifyRequest = false;
//        $scope.updateRectifyRequest = false;
////        $scope.showLoader = true;
//        $scope.showDialog(false);
//
////        console.log("RESULT: " + $scope.GcbOnboardVerifiedNotOk[0].hasOwnProperty("RECTIFYOK"));
//
//        for (var k = 0; k < $scope.GcbOnboardVerifiedNotOk.length; k++)
//        {
//            if ($scope.GcbOnboardVerifiedNotOk[k].selected === true)
//            {
//                $scope.gcbOBRectifyRequests.push({
//                    "ID": $scope.GcbOnboardVerifiedNotOk[k].ID,
//                    "CARD_ACCT": $scope.GcbOnboardVerifiedNotOk[k].CARD_ACCT,
//                    "BRANCH": $scope.GcbOnboardVerifiedNotOk[k].BRANCH,
//                    "LNAME": $scope.GcbOnboardVerifiedNotOk[k].LNAME,
//                    "FNAME": $scope.GcbOnboardVerifiedNotOk[k].FNAME,
//                    "BATCHID": $scope.GcbOnboardVerifiedNotOk[k].BATCHID,
//                    "SECONDARY": $scope.GcbOnboardVerifiedNotOk[k].SECONDARY,
//                    "STATUS2": $scope.GcbOnboardVerifiedNotOk[k].STATUS2,
//                    "CREATED": $scope.GcbOnboardVerifiedNotOk[k].CREATED,
//                    "BANK": $scope.GcbOnboardVerifiedNotOk[k].BANK,
//                    "COY": $scope.GcbOnboardVerifiedNotOk[k].COY,
//                    "STATUS": $scope.GcbOnboardVerifiedNotOk[k].STATUS,
//                    "EMAIL": $scope.GcbOnboardVerifiedNotOk[k].EMAIL,
//                    "CITY": $scope.GcbOnboardVerifiedNotOk[k].CITY,
//                    "ACCT_ID": $scope.GcbOnboardVerifiedNotOk[k].ACCT_ID,
//                    "COUNTRY": $scope.GcbOnboardVerifiedNotOk[k].COUNTRY,
//                    "BANKOK": $scope.GcbOnboardVerifiedNotOk[k].BANKOK,
//                    "CENTRALOK": $scope.GcbOnboardVerifiedNotOk[k].CENTRALOK,
//                    "MOBILE": $scope.GcbOnboardVerifiedNotOk[k].MOBILE,
//                    "STATE": $scope.GcbOnboardVerifiedNotOk[k].STATE,
//                    "MODIFIED": $scope.GcbOnboardVerifiedNotOk[k].MODIFIED,
//                    "MOBILEOK": $scope.GcbOnboardVerifiedNotOk[k].MOBILEOK,
//                    "ECARDHOLDEROK": $scope.GcbOnboardVerifiedNotOk[k].ECARDHOLDEROK,
//                    "OLDMOBILE": $scope.GcbOnboardVerifiedNotOk[k].OLDMOBILE,
//                    "subscriberId": $scope.GcbOnboardVerifiedNotOk[k].subscriberId,
//                    "deviceId": $scope.GcbOnboardVerifiedNotOk[k].deviceId
//                });
//            }
//        }
//
//
//        console.log('Rectify Request: ' + JSON.stringify($scope.gcbOBRectifyRequests));
//
//
//        if ($scope.gcbOBRectifyRequests.length > 0) {
//            $scope.showLoader = true;
////            console.log('sending request' + JSON.stringify($scope.gcbOBRequests));
//            $http({
//                url: $scope.gcbOnboardingProxy,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "requests": JSON.stringify($scope.gcbOBRectifyRequests),
//                    "service": "rectifyRequests",
//                    "pageNumber": $scope.pageNumber3,
//                    "rowsPerPage": $scope.rowsPerPage3
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.GcbOnboardRectifyResp = response.data;
//
//                if ($scope.GcbOnboardRectifyResp.code === "00") {
////                    $scope.rectifyRectifyRequest = true;
//                    if (paging) {
//                        $scope.GcbOnboardVerifiedNotOk = JSON.parse($scope.GcbOnboardRectifyResp.data);
//                        if ($scope.GcbOnboardVerifiedNotOk.length > 0) {
//                            $scope.rectifyRectifyRequest = $scope.GcbOnboardVerifiedNotOk[0].hasOwnProperty("RECTIFYOK");
//                            $scope.updateRectifyRequest = $scope.GcbOnboardVerifiedNotOk[0].hasOwnProperty("UPDATEOK");
//                            $scope.pinRequestNotOk = $scope.GcbOnboardVerifiedNotOk[0].hasOwnProperty("PINOK");
//                        }
//
//                    } else {
////                        console.log('DATA ' + JSON.parse($scope.GcbOnboardVerifyResp.data).NotFound);
//                        $scope.GcbOnboardVerifiedNotOk = JSON.parse($scope.GcbOnboardRectifyResp.data);
////                        console.log('DATA: ' + $scope.GcbOnboardVerifiedNotOk);
//                        if ($scope.GcbOnboardVerifiedNotOk.length > 0) {
//                            $scope.rectifyRectifyRequest = $scope.GcbOnboardVerifiedNotOk[0].hasOwnProperty("RECTIFYOK");
//                            $scope.updateRectifyRequest = $scope.GcbOnboardVerifiedNotOk[0].hasOwnProperty("UPDATEOK");
//                            $scope.pinRequestNotOk = $scope.GcbOnboardVerifiedNotOk[0].hasOwnProperty("PINOK");
//                        }
//                        $scope.getGcbOnboardVerifiedNotOkTotalCount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        }
//    };
//
//    $scope.checkPhoneNumber = function (phone) {
////        var i = "";
//        return phone.length === 12;
//
//    };
//
//    $scope.updateRectifyGcbOnboardingRequests = function (page, paging) {
//        $scope.gcbOBUpdateRectifyRequests = [];
//        $scope.showDetailsBox = false;
////        $scope.rectifyRectifyRequest = false;
//
//        $scope.updateRectifyRequest = false;
////        $scope.showLoader = true;
//        $scope.showDialog(false);
//        for (var k = 0; k < $scope.GcbOnboardVerifiedNotOk.length; k++)
//        {
//            if ($scope.GcbOnboardVerifiedNotOk[k].selected === true)
//            {
//                $scope.gcbOBUpdateRectifyRequests.push({
//                    "ID": $scope.GcbOnboardVerifiedNotOk[k].ID,
//                    "CARD_ACCT": $scope.GcbOnboardVerifiedNotOk[k].CARD_ACCT,
//                    "BRANCH": $scope.GcbOnboardVerifiedNotOk[k].BRANCH,
//                    "LNAME": $scope.GcbOnboardVerifiedNotOk[k].LNAME,
//                    "FNAME": $scope.GcbOnboardVerifiedNotOk[k].FNAME,
//                    "BATCHID": $scope.GcbOnboardVerifiedNotOk[k].BATCHID,
//                    "SECONDARY": $scope.GcbOnboardVerifiedNotOk[k].SECONDARY,
//                    "STATUS2": $scope.GcbOnboardVerifiedNotOk[k].STATUS2,
//                    "CREATED": $scope.GcbOnboardVerifiedNotOk[k].CREATED,
//                    "BANK": $scope.GcbOnboardVerifiedNotOk[k].BANK,
//                    "COY": $scope.GcbOnboardVerifiedNotOk[k].COY,
//                    "STATUS": $scope.GcbOnboardVerifiedNotOk[k].STATUS,
//                    "EMAIL": $scope.GcbOnboardVerifiedNotOk[k].EMAIL,
//                    "CITY": $scope.GcbOnboardVerifiedNotOk[k].CITY,
//                    "ACCT_ID": $scope.GcbOnboardVerifiedNotOk[k].ACCT_ID,
//                    "COUNTRY": $scope.GcbOnboardVerifiedNotOk[k].COUNTRY,
//                    "BANKOK": $scope.GcbOnboardVerifiedNotOk[k].BANKOK,
//                    "CENTRALOK": $scope.GcbOnboardVerifiedNotOk[k].CENTRALOK,
//                    "MOBILE": $scope.GcbOnboardVerifiedNotOk[k].MOBILE,
//                    "STATE": $scope.GcbOnboardVerifiedNotOk[k].STATE,
//                    "MODIFIED": $scope.GcbOnboardVerifiedNotOk[k].MODIFIED,
//                    "MOBILEOK": $scope.GcbOnboardVerifiedNotOk[k].MOBILEOK,
//                    "ECARDHOLDEROK": $scope.GcbOnboardVerifiedNotOk[k].ECARDHOLDEROK,
//                    "OLDMOBILE": $scope.GcbOnboardVerifiedNotOk[k].OLDMOBILE,
//                    "subscriberId": $scope.GcbOnboardVerifiedNotOk[k].subscriberId,
//                    "deviceId": $scope.GcbOnboardVerifiedNotOk[k].deviceId,
//                    "RECTIFYOK": $scope.GcbOnboardVerifiedNotOk[k].RECTIFYOK
//                });
//            }
//        }
//
//        console.log('Update Rectify Request: ' + $scope.gcbOBUpdateRectifyRequests);
//
//
//        if ($scope.gcbOBUpdateRectifyRequests.length > 0) {
//            $scope.showLoader = true;
////            console.log('sending request' + JSON.stringify($scope.gcbOBRequests));
//            $http({
//                url: $scope.gcbOnboardingProxy,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "requests": JSON.stringify($scope.gcbOBUpdateRectifyRequests),
//                    "service": "updaterectifyRequests",
//                    "pageNumber": $scope.pageNumber3,
//                    "rowsPerPage": $scope.rowsPerPage3
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.GcbOnboardRectifyResp = response.data;
//                if ($scope.GcbOnboardRectifyResp.code === "00") {
////                    $scope.updateRectifyRequest = true;
//                    if (paging) {
//
//                        $scope.GcbOnboardVerifiedNotOk = JSON.parse($scope.GcbOnboardRectifyResp.data);
//                        if ($scope.GcbOnboardVerifiedNotOk.length > 0) {
//                            $scope.rectifyRectifyRequest = $scope.GcbOnboardVerifiedNotOk[0].hasOwnProperty("RECTIFYOK");
//                            $scope.updateRectifyRequest = $scope.GcbOnboardVerifiedNotOk[0].hasOwnProperty("UPDATEOK");
//                            $scope.pinRequestNotOk = $scope.GcbOnboardVerifiedNotOk[0].hasOwnProperty("PINOK");
//                        }
//
//                    } else {
////                        console.log('DATA ' + JSON.parse($scope.GcbOnboardVerifyResp.data).NotFound);
//                        $scope.GcbOnboardVerifiedNotOk = JSON.parse($scope.GcbOnboardRectifyResp.data);
////                        console.log('DATA: ' + $scope.GcbOnboardVerifiedNotOk);
//                        if ($scope.GcbOnboardVerifiedNotOk.length > 0) {
//                            $scope.rectifyRectifyRequest = $scope.GcbOnboardVerifiedNotOk[0].hasOwnProperty("RECTIFYOK");
//                            $scope.updateRectifyRequest = $scope.GcbOnboardVerifiedNotOk[0].hasOwnProperty("UPDATEOK");
//                            $scope.pinRequestNotOk = $scope.GcbOnboardVerifiedNotOk[0].hasOwnProperty("PINOK");
//                        }
//                        $scope.getGcbOnboardVerifiedNotOkTotalCount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        }
//    };
//
//    $scope.getGcbMomoAcctTrxn = function (page, paging) {
//        $scope.getProfile();
//        if ($('#start-date').val() && $('#end-date').val()) {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.GcbAltProxy;
//            if (paging) {
//                url = './GcbAltFunction?action=paging';
//            }
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                $scope.pageNumber = $scope.fgRecordsLastPage;
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "startDate": $scope.startDate,
//                    "endDate": $scope.endDate,
//                    "transtype": $scope.gcbmomotype,
//                    "service": "transactions",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.elacTrxResp = response.data;
//                if ($scope.elacTrxResp.code === "00") {
//                    if (paging) {
//                        $scope.gcbAltTrxs = JSON.parse($scope.elacTrxResp.data);
//                        $scope.getGcbAltCount();
//                    } else {
//                        $scope.gcbAltTrxs = JSON.parse($scope.elacTrxResp.data);
//                        $scope.getGcbAltCount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert('Select values for start and end date');
//        }
//    };
//
//    $scope.getMobileAppActivation = function (page, paging) {
//        $scope.getProfile();
//        if ($('#mobile_no').val()) {
//            $scope.showLoader = true;
//            $scope.showFilterBox = false;
//            $scope.pageNumber = page;
//            $('.reload').addClass('fa-spin text-info');
//            var url = $scope.mobileAppActivateProxy;
//            if (paging) {
//                url = './GcbAltFunction?action=paging';
//            }
//            if ($scope.pageNumber < 1)
//                $scope.pageNumber = 1;
//            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                $scope.pageNumber = $scope.fgRecordsLastPage;
//            $http({
//                url: url,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "mobile_no": $('#mobile_no').val(),
//                    "service": "search",
//                    "pageNumber": $scope.pageNumber,
//                    "rowsPerPage": $scope.rowsPerPage
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.mobileAppResp = response.data;
//                if ($scope.mobileAppResp.code === "00") {
//                    if (paging) {
//                        $scope.mobileAppRecs = JSON.parse($scope.mobileAppResp.data);
//                        $scope.mobileAppRecsCount();
//                    } else {
//                        $scope.mobileAppRecs = JSON.parse($scope.mobileAppResp.data);
//                        $scope.mobileAppRecsCount();
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//        } else {
//            alert('Please provide phone number');
//        }
//    };
//
//
//    $scope.addRoleElement = function () {
//        var html = '';
//
//        html += '<tr id="field' + $scope.currentIndex + '0_">';
//        html += '<td><button type="button" name="remove" class="btn btn-danger btn-sm remove" ng-click="removeRoleElement();"><i class="fa fa-trash"></i></button></td>';
//        html += '<td>';
//        html += '<select class="form-control form-control-sm item_name" name="item_name[]" ng-model="values[\'field' + $scope.currentIndex + '0_\']" id="type_id" ng-Change="getTypeIdOptions(\'field' + $scope.currentIndex + '0_\')" required >';
//        html += '<option value="" selected disabled>Select User Type</option>';
//        html += '<option ng-repeat="option in typeIdList" title="yert" value="{{option.id}}">{{option.name}}</option>';
//        html += '</select>';
//        html += '</td>';
////        html += '<td class="row">';
////        html += '<div class="col-6">';
////        html += '<select class="form-control form-control-sm item_quantity" name="item_quantity[]" ng-model="values[\'field' + $scope.currentIndex + '1_\'+$index]" id="type_id" ng-change="getTypeIdOptions()" required >';
////        html += '<option value="">Select User Type</option>';
////        html += '<option ng-repeat="option in typeIdList" value="{{option.id}}">{{option.name}}</option>';
////        html += '</select>';
////        html += '</div>';
////        html += '<div class="col-6">';
////        html += '<select class="form-control form-control-sm item_unit" name="item_unit[]" ng-model="values[\'field' + $scope.currentIndex + '2_\'+$index]" id="type_id" ng-change="getTypeIdOptions()" required >';
////        html += '<option value="">Select User Type</option>';
////        html += '<option ng-repeat="option in typeIdList" value="{{option.id}}">{{option.name}}</option>';
////        html += '</select>';
////        html += '</div>';
////        html += '</td>';
////        html += '<td><button type="button" name="remove" class="btn btn-danger btn-sm remove" ng-click="removeRoleElement()"><i class="fa fa-minus"></i></button></td>';
//        html += '</tr>';
//        $('#typeIdData').prepend($compile(angular.element(html))($scope));
//        $scope.currentIndex++;
//    };
//
//    $scope.addTypeIdOptions = function (id) {
////        if (id === '43') {
//        var html = '';
//        html += '<td class="row">';
//        html += '<div class="col-6" id="data' + id + '">';
//        html += '<select class="form-control form-control-sm item_quantity" name="item_quantity[]" ng-model="values[\'field' + $scope.currentIndex + '1_\'+$index]" id="type_id"  >';
//        html += '<option value="" selected disabled>Select User Type</option>';
//        html += '<option ng-repeat="option in data' + id + '" value="{{option.alias}}">{{option.name}}</option>';
//        html += '</select>';
//        html += '</div>';
//        html += '<div class="col-6">';
//        html += '<select class="form-control form-control-sm item_unit" name="item_unit[]" ng-model="values[\'field' + $scope.currentIndex + '2_\'+$index]" id="type_id" >';
//        html += '<option value="">Select User Type</option>';
//        html += '<option ng-repeat="option in typeIdList" value="{{option.id}}">{{option.name}}</option>';
//        html += '</select>';
//        html += '</div>';
//        html += '</td>';
////        html += '<td><button type="button" name="remove" class="btn btn-danger btn-sm remove" ng-click="removeRoleElement();"><i class="fa fa-minus"></i></button></td>';
//        $('#' + id).append($compile(angular.element(html))($scope));
////        }
//
//    };
//
//
//
//    $('#typeIdData').on('click', '.remove', function () {
//        $(this).closest('tr').remove();
//    });
//
//    $('#userUpdateForm').on('submit', function (event) {
//        event.preventDefault();
//        var form_data = $(this).serialize();
////        console.log('data: ' + form_data);
//        var loginForm = $(this).serializeJSON();
////        console.log('hg: ' + JSON.stringify(loginForm));
////        console.log((loginForm).item_name.length);
//        var userOptions = [];
//        for (var i = 0; i < (loginForm).item_name.length; i++) {
//            userOptions.push({
//                "userOption0": (loginForm).item_name[i],
//                "userOption1": (loginForm).item_quantity[i],
//                "userOption2": (loginForm).item_unit[i]
//            });
//        }
//
//        delete loginForm['item_name'];
//        delete loginForm['item_unit'];
//        delete loginForm['item_quantity'];
////        console.log('newjson: ' + JSON.stringify(userOptions));
//
//        loginForm['userOptions'] = userOptions;
//
//        console.log('JSON: ' + JSON.stringify(loginForm));
//
//        if ($scope.usernameAvailable) {
//            //create user
//
//            $http({
//                url: $scope.usersProxy,
//                method: 'POST',
//                data: $httpParamSerializerJQLike({
//                    "createUserData": JSON.stringify(loginForm),
//                    "service": "createUser"
//                }),
//                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//            }).then(function success(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                $scope.mobileAppResp = response.data;
//                if ($scope.mobileAppResp.code === "00") {
////                console.log('' + $scope.mobileAppResp.data);
////                console.log('data: ' + $scope.mobileAppResp.data.indexOf('Unavailable'));
//                    if ($scope.mobileAppResp.data.indexOf('Unavailable') !== -1) {
//                        $('.unavailable').css('display', 'block');
//                        $scope.usernameAvailable = false;
//                    }
//                    if ($scope.mobileAppResp.data.indexOf('Available') !== -1) {
//                        $('.available').css('display', 'block');
//                        $scope.usernameAvailable = true;
//                    }
//                } else {
//
//                }
//            }, function error(response) {
//                $scope.showLoader = false;
//                $('.reload').removeClass('fa-spin text-info');
//                alert('Connection or Server Error. Try again');
//            });
//
//        } else {
//
//            console.log('username is unavailable');
//        }
//    });
//    
//    var checkusernametimer = "";
//    $scope.cancelCheckUsernameTimeout = function () {
//        clearTimeout(checkusernametimer);
//    };
//
//    $scope.checkUsernameTimeout = function (username) {
//        $scope.usernameAvailable = false;
//        $scope.cancelCheckUsernameTimeout();
//        $('.unavailable').css('display', 'none');
//        $('.available').css('display', 'none');
//        if (username.trim() !== "") {
//            checkusernametimer = setTimeout(function () {
//                $scope.$apply($scope.checkUsername(username));
//            }, 1000);
//        }
//    };
//
//
//
//    $scope.checkUsername = function (username) {
//        $http({
//            url: $scope.usersProxy,
//            method: 'POST',
//            data: $httpParamSerializerJQLike({
//                "username": username,
//                "service": "checkUsername"
//            }),
//            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//        }).then(function success(response) {
//            $scope.showLoader = false;
//            $('.reload').removeClass('fa-spin text-info');
//            $scope.mobileAppResp = response.data;
//            if ($scope.mobileAppResp.code === "00") {
////                console.log('' + $scope.mobileAppResp.data);
////                console.log('data: ' + $scope.mobileAppResp.data.indexOf('Unavailable'));
//                if ($scope.mobileAppResp.data.indexOf('Unavailable') !== -1) {
//                    $('.unavailable').css('display', 'block');
//                    $scope.usernameAvailable = false;
//                }
//                if ($scope.mobileAppResp.data.indexOf('Available') !== -1) {
//                    $('.available').css('display', 'block');
//                    $scope.usernameAvailable = true;
//                }
//            } else {
//
//            }
//        }, function error(response) {
//            $scope.showLoader = false;
//            $('.reload').removeClass('fa-spin text-info');
//            alert('Connection or Server Error. Try again');
//        });
//    };
//    $scope.roleValues = {};
//
//    $scope.currentIndex = 1;
//
//    $scope.getGcbAltCount = function () {
//        $http.get('./GcbAltFunction?action=count').then(
//                function (response) {
//                    $scope.fgRecordsTotalCount = JSON.parse(response.data);
//                    $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//                    if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//                        $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//                    }
//                }
//        );
//    };
//
//    $scope.mobileAppRecsCount = function () {
//        $http.get('./MobileAppFunc?action=count').then(
//                function (response) {
//                    $scope.fgRecordsTotalCount = JSON.parse(response.data);
//                    $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//                    if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//                        $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//                    }
//                }
//        );
//    };
//
//    $scope.selectAllRequests = function (type) {
//        switch (type) {
//            case "OBRequests":
//                if (!$scope.showOBRequests) {
//                    for (var k = 0; k < $scope.GcbOnboardRequests.length; k++)
//                    {
//                        if ($scope.checkPhoneNumber($scope.GcbOnboardRequests[k].MOBILE)) {
//                            $scope.GcbOnboardRequests[k].selected = true;
//                            $scope.showOBRequests = true;
//                        }
//                    }
//                } else {
//                    for (var k = 0; k < $scope.GcbOnboardRequests.length; k++)
//                    {
//                        $scope.GcbOnboardRequests[k].selected = false;
//                        $scope.showOBRequests = false;
//                    }
//                }
//                break;
//            case "OBOkRequests":
//                if (!$scope.showOBOkRequests) {
//                    for (var k = 0; k < $scope.GcbOnboardVerifiedOk.length; k++)
//                    {
//                        $scope.GcbOnboardVerifiedOk[k].selected = true;
//                        $scope.showOBOkRequests = true;
//                    }
//                } else {
//                    for (var k = 0; k < $scope.GcbOnboardVerifiedOk.length; k++)
//                    {
//                        $scope.GcbOnboardVerifiedOk[k].selected = false;
//                        $scope.showOBOkRequests = false;
//                    }
//                }
//                break;
//            case "OBNotOkRequests":
//                if (!$scope.showOBNotOkRequests) {
//                    for (var k = 0; k < $scope.GcbOnboardVerifiedNotOk.length; k++)
//                    {
//                        $scope.GcbOnboardVerifiedNotOk[k].selected = true;
//                        $scope.showOBNotOkRequests = true;
//                    }
//                } else {
//                    for (var k = 0; k < $scope.GcbOnboardVerifiedNotOk.length; k++)
//                    {
//                        $scope.GcbOnboardVerifiedNotOk[k].selected = false;
//                        $scope.showOBNotOkRequests = false;
//                    }
//                }
//                break;
//            default:
//                break;
//        }
//    };
//
//    $scope.getGcbOnboardRequestsTotalCount = function () {
//        $http.get('./OnBoardingReqs?action=count').then(
//                function (response) {
//                    $scope.fgRecordsTotalCount = JSON.parse(response.data);
//                    $scope.fgRecordsLastPage1 = $scope.fgRecordsTotalCount / $scope.rowsPerPage1;
//                    if ($scope.fgRecordsLastPage1 > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage1)) {
//                        $scope.fgRecordsLastPage1 = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage1) + 1;
//                    }
//                }
//        );
//    };
//
//    $scope.getGcbOnboardVerifiedOkTotalCount = function () {
//        $http.get('./OnBoardingVerifiedOk?action=count').then(
//                function (response) {
//                    $scope.fgRecordsTotalCount2 = JSON.parse(response.data);
//                    $scope.fgRecordsLastPage2 = $scope.fgRecordsTotalCount2 / $scope.rowsPerPage2;
//                    if ($scope.fgRecordsLastPage2 > parseInt($scope.fgRecordsTotalCount2 / $scope.rowsPerPage2)) {
//                        $scope.fgRecordsLastPage2 = parseInt($scope.fgRecordsTotalCount2 / $scope.rowsPerPage2) + 1;
//                    }
//                }
//        );
//    };
//
//    $scope.getGcbOnboardVerifiedNotOkTotalCount = function () {
//        $http.get('./OnBoardingVerifiedNotOk?action=count').then(
//                function (response) {
//                    $scope.fgRecordsTotalCount3 = JSON.parse(response.data);
//                    $scope.fgRecordsLastPage3 = $scope.fgRecordsTotalCount3 / $scope.rowsPerPage3;
//                    if ($scope.fgRecordsLastPage3 > parseInt($scope.fgRecordsTotalCount3 / $scope.rowsPerPage3)) {
//                        $scope.fgRecordsLastPage3 = parseInt($scope.fgRecordsTotalCount3 / $scope.rowsPerPage3) + 1;
//                    }
//                }
//        );
//    };
//
//    $scope.getGcbOnboardVerifiedNotFoundTotalCount = function () {
//        $http.get('./OnBoardingVerifiedNotFound?action=count').then(
//                function (response) {
//                    $scope.fgRecordsTotalCount4 = JSON.parse(response.data);
//                    $scope.fgRecordsLastPage4 = $scope.fgRecordsTotalCount4 / $scope.rowsPerPage4;
//                    if ($scope.fgRecordsLastPage4 > parseInt($scope.fgRecordsTotalCount4 / $scope.rowsPerPage4)) {
//                        $scope.fgRecordsLastPage4 = parseInt($scope.fgRecordsTotalCount4 / $scope.rowsPerPage4) + 1;
//                    }
//                }
//        );
//    };
//
//    $scope.getGcbOkRequests = function (page, paging) {
//        $scope.getProfile();
//        $scope.showLoader = true;
//        $scope.showFilterBox = false;
////        $scope.pageNumber = page;
//        $('.reload').addClass('fa-spin text-info');
//        if (paging) {
//            url = './OnBoardingVerifiedOk?action=paging';
//        }
//        if (page < 1)
//            page = 1;
//        if (page !== 1 && page > $scope.fgRecordsLastPage2)
//            page = $scope.fgRecordsLastPage2;
//        $http({
//            url: url,
//            method: 'POST',
//            data: $httpParamSerializerJQLike({
//                "pageNumber": page,
//                "rowsPerPage": $scope.rowsPerPage2
//            }),
//            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//        }).then(function success(response) {
//            $scope.updateRequest = false;
//            $scope.pinRequestOK = false;
//            $scope.showLoader = false;
//            $('.reload').removeClass('fa-spin text-info');
//            $scope.page1Resp = response.data;
//            if ($scope.page1Resp.code === "00") {
//                $scope.GcbOnboardVerifiedOk = JSON.parse($scope.page1Resp.data);
//            } else {
//
//            }
//        }, function error(response) {
//            $scope.showLoader = false;
//            $('.reload').removeClass('fa-spin text-info');
//            alert('Connection or Server Error. Try again');
//        });
//    };
//    $scope.getGcbNotOkRequests = function (page, paging) {
//        $scope.getProfile();
//        $scope.showLoader = true;
//        $scope.showFilterBox = false;
////        $scope.pageNumber = page;
//        $('.reload').addClass('fa-spin text-info');
//        if (paging) {
//            url = './OnBoardingVerifiedNotOk?action=paging';
//        }
//        if (page < 1)
//            page = 1;
//        if (page !== 1 && page > $scope.fgRecordsLastPage3)
//            page = $scope.fgRecordsLastPage3;
//        $http({
//            url: url,
//            method: 'POST',
//            data: $httpParamSerializerJQLike({
//                "pageNumber": page,
//                "rowsPerPage": $scope.rowsPerPage3
//            }),
//            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//        }).then(function success(response) {
//            $scope.rectifyRectifyRequest = false;
//            $scope.updateRectifyRequest = false;
//            $scope.pinRequestNotOk = false;
//            $scope.showLoader = false;
//            $('.reload').removeClass('fa-spin text-info');
//            $scope.page2Resp = response.data;
//            if ($scope.page2Resp.code === "00") {
//                $scope.GcbOnboardVerifiedNotOk = JSON.parse($scope.page2Resp.data);
//            } else {
//
//            }
//        }, function error(response) {
//            $scope.showLoader = false;
//            $('.reload').removeClass('fa-spin text-info');
//            alert('Connection or Server Error. Try again');
//        });
//    };
//    $scope.getGcbNotFoundRequests = function (page, paging) {
//        $scope.getProfile();
//        $scope.showLoader = true;
//        $scope.showFilterBox = false;
////        $scope.pageNumber = page;
//        $('.reload').addClass('fa-spin text-info');
//        if (paging) {
//            url = './OnBoardingVerifiedNotFound?action=paging';
//        }
//        if (page < 1)
//            page = 1;
//        if (page !== 1 && page > $scope.fgRecordsLastPage4)
//            page = $scope.fgRecordsLastPage4;
//        $http({
//            url: url,
//            method: 'POST',
//            data: $httpParamSerializerJQLike({
//                "pageNumber": page,
//                "rowsPerPage": $scope.rowsPerPage4
//            }),
//            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//        }).then(function success(response) {
//            $scope.showLoader = false;
//            $('.reload').removeClass('fa-spin text-info');
//            $scope.page3Resp = response.data;
//            if ($scope.page3Resp.code === "00") {
//                $scope.GcbOnboardVerifiedNotFound = JSON.parse($scope.page3Resp.data);
//            } else {
//
//            }
//        }, function error(response) {
//            $scope.showLoader = false;
//            $('.reload').removeClass('fa-spin text-info');
//            alert('Connection or Server Error. Try again');
//        });
//    };
//
//    $scope.getFeesCollection = function (page, paging) {
//        $scope.getProfile();
//        $scope.showLoader = true;
//        $scope.showFilterBox = false;
////        $scope.pageNumber = page;
//        $('.reload').addClass('fa-spin text-info');
//        var url = $scope.feeCollectionProxy;
//        if (paging) {
//            url = './FeesCollectionFunc?action=paging';
//        }
//        if (page < 1)
//            page = 1;
//        if (page !== 1 && page > $scope.fgRecordsLastPage)
//            page = $scope.fgRecordsLastPage;
//        $http({
//            url: url,
//            method: 'POST',
//            data: $httpParamSerializerJQLike({
//                "startDate": $scope.startDate,
//                "endDate": $scope.endDate,
//                "uniqueTransId": $scope.uniqueTransId || '',
//                "from_source": $scope.from_source || '',
//                "student_name": $("#student_name").val() || '',
//                "optionType": $scope.trans_channel || 'ALL',
//                "service": "transactions",
//                "pageNumber": page,
//                "rowsPerPage": $scope.rowsPerPage
//            }),
//            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//        }).then(function success(response) {
//            $scope.showLoader = false;
//            $('.reload').removeClass('fa-spin text-info');
//            $scope.pageResp = response.data;
//            if ($scope.pageResp.code === "00") {
//                $scope.feeCollectionTrxs = JSON.parse($scope.pageResp.data);
//                $scope.getFeesCollectionTotalCount();
//                $scope.getFeesCollectionTotalAmount();
//            } else {
//
//            }
//        }, function error(response) {
//            $scope.showLoader = false;
//            $('.reload').removeClass('fa-spin text-info');
//            alert('Connection or Server Error. Try again');
//        });
//    };
//    $scope.getFeesRevCollection = function (page, paging) {
//        $scope.getProfile();
//        $scope.showLoader = true;
//        $scope.showFilterBox = false;
////        $scope.pageNumber = page;
//        $('.reload').addClass('fa-spin text-info');
//        var url = $scope.feeCollectionProxy;
//        if (paging) {
//            url = './FeesCollectionFunc?action=paging';
//        }
//        if (page < 1)
//            page = 1;
//        if (page !== 1 && page > $scope.fgRecordsLastPage)
//            page = $scope.fgRecordsLastPage;
//        $http({
//            url: url,
//            method: 'POST',
//            data: $httpParamSerializerJQLike({
//                "startDate": $scope.startDate,
//                "endDate": $scope.endDate,
//                "uniqueTransId": $scope.uniqueTransId || '',
//                "from_source": $scope.from_source || '',
//                "student_name": $("#student_name").val() || '',
//                "optionType": $scope.trans_channel || 'ALL',
//                "service": "rvsltransactions",
//                "pageNumber": page,
//                "rowsPerPage": $scope.rowsPerPage
//            }),
//            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//        }).then(function success(response) {
//            $scope.showLoader = false;
//            $('.reload').removeClass('fa-spin text-info');
//            $scope.pageResp = response.data;
//            if ($scope.pageResp.code === "00") {
//                $scope.feeCollectionTrxs = JSON.parse($scope.pageResp.data);
//                $scope.getFeesCollectionTotalCount();
//                $scope.getFeesCollectionTotalAmount();
//            } else {
//
//            }
//        }, function error(response) {
//            $scope.showLoader = false;
//            $('.reload').removeClass('fa-spin text-info');
//            alert('Connection or Server Error. Try again');
//        });
//    };
//    $scope.getFeesCollectionTotalCount = function () {
//        $http.get('./FeesCollectionFunc?action=count').then(
//                function (response) {
//                    $scope.fgRecordsTotalCount = JSON.parse(response.data);
//                    $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//                    if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount4 / $scope.rowsPerPage)) {
//                        $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//                    }
//                }
//        );
//    };
//    $scope.getFeesCollectionTotalAmount = function () {
//        $http.get('./FeesCollectionFunc?action=totalAmount').then(
//                function (response) {
////                    alert('Amount: ' + JSON.parse(response.data));
//                    $scope.fgRecordsTotalAmount = response.data;
//                }
//        );
//    };
//
//
//
//
//    $scope.getValueCodeMsgMapping = function (code) {
//        if (code === "0" || code === "00") {
//            return "SUCCESSFUL";
//        } else if (!code) {
//            return "NO RESPONSE";
//        } else if (code === "N/A" || code === "null") {
//            return code;
//        }
//        return "FAILED";
//    };
//
//    $scope.getFlagMessage = function (flag) {
//        if (!flag) {
//            return "";
//        }
//        if (flag === "0")
//            return "COMPLETED";
//        else if (flag === "null")
//            return " ";
//        else if (flag === "")
//            return " ";
//        else if (flag === "7")
//            return "REVERSING ";
//        else if (flag === "8")
//            return "REVERSED";
//        else
//            return "PENDING";
//    };
//
//    $scope.trimJP = function (text) {
//        if (text) {
//            if (text.length > 36) {
//                return (text.substring(0, 35)) + " ...";
//            }
//        }
//        return text;
//    };
//    $scope.trim = function (text) {
//        if (text.length > 60) {
//            return (text.substring(0, 62)) + " ...";
//        }
//        return text;
//    };
//    $scope.trimShort = function (text) {
//        if (text) {
//            if (text.length > 40) {
//                return (text.substring(0, 38)) + " ...";
//            }
//        }
//        return text;
//    };
//    $scope.trimVasBillId = function (text) {
//        if (text.length > 30) {
//            return (text.substring(0, 30)) + " ...";
//        }
//        return text;
//    };
//    $scope.getResponseCode = function (str) {
//        return str.split("::")[0];
//    };
//    $scope.getResponseMsg = function (str) {
//        return $scope.trim(str.split("::")[1]);
//    };
//    $scope.trim2 = function (text) {
//        if (text.length > 78) {
//            return (text.substring(0, 77)) + " ...";
//        }
//        return text;
//    };
//    $scope.getBankName = function (bankCode) {
////       angular.forEach($scope.banksList, function(value, key){          
////          if(bankCode === value.issuer_code){
////              return value.issuer_name;    
////          }
////          return bankCode;       
////       });       
//        return bankCode;
//    };
//    $scope.formatStr = function (descr) {
//        var newStr = descr.replace(/,/g, ' ');
//        return newStr;
//    };
//    $scope.hideFilterBox = function () {
//        $scope.showTrxFilterBox = false;
//        $scope.showFilterBox = false;
//    };
//    $scope.hideAgentBox = function () {
//        $scope.showAgent = false;
//    };
//    $scope.hideDetailsBox = function () {
//        $scope.showTrxFilterBox = false;
//        $scope.showDetailsBox = false;
//    };
//    $scope.showFilterBoxView = function () {
//        $scope.showTrxFilterBox = true;
//        $scope.showFilterBox = true;
//    };
//    $scope.showAgentBox = function () {
//        $scope.showAgent = true;
//    };
//    $scope.toggleFilterBoxView = function () {
//        $scope.showFilterBox = !$scope.showFilterBox;
//    };
//    $scope.toggleCPayTransView = function () {
//        $scope.showCPayTransView = !$scope.showCPayTransView;
//    };
//    $scope.showCardUpdateBoxView = function () {
//        $scope.showCardUpdateBox = true;
//        $scope.cardUpdateResultView = false;
//    };
//    $scope.hideCardUpdateBoxView = function () {
//        $scope.showCardUpdateBox = false;
//    };
//    $scope.showCardHotlistBoxView = function () {
//        $scope.showCardUpdateBox = true;
//        $scope.cardUpdateResultView = false;
//    };
//    $scope.hideCardHotlistBoxView = function () {
//        $scope.showCardUpdateBox = false;
//    };
//    $scope.toggleAgentBoxView = function () {
//        $scope.showAgent = !$scope.showAgent;
//    };
//
//    $scope.getChannel = function (channel) {
//        console.log(channel);
//        switch (channel) {
//            case "ju":
//                channel = "justpay";
//                break;
//            case "09":
//                channel = "fundgate";
//                break;
//            case  "02":
//                channel = "mobile";
//                break;
//            case "05":
//                channel = "payoutlet";
//                break;
//            case "01":
//                channel = "webconnect";
//                break;
//            default:
//                channel = "N/A";
//                break;
//        }
//        return channel;
//    };
//    $scope.fgRowColor = function (str) {
//        if ($scope.status === "ALL") {
//            var rowClass = $scope.getResponseCode(str);
//            if (rowClass !== "0") {
//                if (rowClass === '31') {
//                    return "blue";
//                }
//                return "red";
//            }
//        }
//    };
//    $scope.momoRowColor = function (str) {
//        if ($scope.status === "ALL") {
//            if (str.toUpperCase() !== "SUCCESSFUL") {
//                if (str.toUpperCase() === "PENDING") {
//                    return "blue";
//                }
//                return "red";
//            }
//
//        }
//    };
//    $scope.webconnectRowColor = function (str, str2) {
//        if ($scope.status === "ALL") {
//            if (str.toUpperCase() !== "SUCCESSFUL" || str2.toUpperCase() !== "SUCCESSFUL") {
//                if (str.toUpperCase() === "PENDING") {
//                    return "blue";
//                }
//                if (str2.toUpperCase() !== "PENDING") {
//                    return "blue";
//                }
//
//                return "red";
//            }
//
//        }
//    };
//    $scope.flagColor = function (str) {
//        if ($scope.status === "ALL") {
//            if (str.toUpperCase() === "PENDING") {
//                return "yellow";
//            }
//        }
//        if ($scope.status === "SUCCESSFUL") {
//            if (str.toUpperCase() === "PENDING") {
//                return "yellow";
//            }
//        }
//        if ($scope.status === "FAILED") {
//            if (str.toUpperCase() === "PENDING") {
//                return "yellow";
//            }
//        }
//    };
//    $scope.barRowColor = function (str) {
//        if ($scope.status === "ALL") {
//            if (str !== "000") {
//                return "red";
//            }
//        }
//    };
//    $scope.barRowColor = function (str) {
//        if ($scope.status === "ALL") {
//            if (str !== "000") {
//                return "red";
//            }
//        }
//    };
//    $scope.vtuRowColor = function (str) {
//        if ($scope.status === "ALL") {
//            if (str !== "0") {
//                return "red";
//            }
//        }
//    };
//    $scope.jpRowColor = function (str) {
//        if ($scope.status === "ALL") {
//            if (str === "0" || str === "00") {
//            } else {
//                return "red";
//            }
//        }
//    };
//    $scope.sasRowColor = function (str) {
//        if ($scope.status === "ALL") {
//            if (str === "0" || str === "00") {
//            } else {
//                return "red";
//            }
//        }
//    };
//    $scope.tmcRowColor = function (str) {
//        if (str === "00") {
//
//        } else {
//            switch ($scope.status) {
//                case "ALL":
//                    if (str === "51" || str === "55" || str === "57") {
//                        return "yellow";
//                    } else if (str === "58") {
//                        return "blue";
//                    } else
//                        return "red";
//                    break;
//                case "REVERSAL":
//                    if (str === "51" || str === "55" || str === "57") {
//                        return "yellow";
//                    } else if (str === "58") {
//                        return "blue";
//                    } else
//                        return "red";
//                    break;
//                case "FAIL":
//                    if (str === "51" || str === "55" || str === "57") {
//                        return "yellow";
//                    } else if (str === "58") {
//                        return "blue";
//                    } else
//                        return "red";
//                    break;
//                case "FAILED":
//                    if (str === "51" || str === "55" || str === "57") {
//                        return "yellow";
//                    } else if (str === "58") {
//                        return "blue";
//                    } else
//                        return "red";
//                    break;
//                case "AMBIGUOUS":
//                    if (str === "51" || str === "55" || str === "57") {
//                        return "yellow";
//                    } else if (str === "58") {
//                        return "blue";
//                    } else
//                        return "red";
//                    break;
//                case "SUCCESSFUL":
//                    break;
//                default:
//                    break;
//            }
//        }
//
////     if($scope.status === "ALL" ){   
////       if(str !== "00" ){     
////          return "red";
////        }
////       }
//    };
//    $scope.onme = function () {
//        return blue;
//    };
//
//
//    $scope.kwesetvRowColor = function (str) {
//        switch ($scope.trans_status) {
//            case "ALL":
//                if (str !== "00") {
//                    return "red";
//                }
//                break;
//            case "FAILED":
//                if (str !== "00") {
//                    return "red";
//                }
//                break;
//            case "SUCCESS":
//                break;
//            default:
//                break;
//        }
////     if($scope.status === "ALL" ){   
////       if(str !== "00" ){     
////          return "red";
////        }
////       }
//    };
////  document.addEventListener("keydown", function(event) {
////         $scope.test();
////   });
////   
////   $scope.test = function(){
////       $scope.showFilterBox = !$scope.showFilterBox;  
//////       alert('I can do all things through Christ who strenghtens me');
////   };
//
////    if ($scope.testFGTrx()) {
//    $scope.getFGBalance();
//    $scope.initInfo();
//    $scope.getCurrentLocation();
////        if ($scope.testEtz()) {
//    $scope.getSurfBalance();
//    $scope.getTigoBalance();
//    $scope.getVodaTopupBalance();
////        }
////
////    }
//
//});
//
////
////app.directive('ngFiles', ['$parse', function ($parse) {
////
////        function fn_link(scope, element, attrs) {
////            var onChange = $parse(attrs.ngFiles);
////            element.on('change', function (event) {
////                onChange(scope, {$files: event.target.files});
////            });
////        }
////        ;
////
////        return {
////            link: fn_link
////        };
////    }]);
