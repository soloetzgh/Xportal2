$(document).ready(function () {

});


app.controller('investigationCtrl', function ($scope, $http) {
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

    $scope.showFilterBox = true;
    $scope.transType = '';

    $scope.initInfo = function () {

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

    var Request = new Object();
    $scope.centralInvestigation = function () {
        if ($('#reference').val() || $('#account').val()) {
            $scope.showLoader = true;
            $scope.showFilterBox = false;
            Request.startDate = $scope.startDate;
            Request.endDate = $scope.endDate;
            Request.reference = $scope.reference;
            Request.account = $scope.account;
//        Request.startDate = '2018-05-01 00:00:00';
//        Request.endDate = '2018-05-07 23:59:00';
//        Request.reference = '02JTSV44524B57793';
//        Request.account = '';
            var json = JSON.stringify(Request);
            console.log(json);
            var url = 'http://localhost:8084/TRANSACTION_INVESTIGATION/api/etz/global/service/';
            $http.post(url, json).then(
                    function (response) {
                        $scope.showLoader = false;
                        $scope.response = response.data;
                    }
            );
        } else {
            alert("Enter value for either Reference or Mobile/Account Number");
        }
    };

    $scope.hideFilterBox = function () {
        $scope.showFilterBox = false;
    };

    $scope.toggleFilterBoxView = function () {
        $scope.showFilterBox = !$scope.showFilterBox;
    };

    $scope.initInfo();
});