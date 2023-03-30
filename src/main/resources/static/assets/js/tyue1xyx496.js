app.controller('adminCtrl', function ($scope, $http, $httpParamSerializerJQLike) {

    $scope.role = "0";

    $scope.initInfo = function () {
        $http.get('./Administrator?action=roles').then(
                function (response) {
                    $scope.roles = response.data;
                }
        );
    };

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

    $scope.updateApp = function () {
        $scope.getProfile();
    };

    $(".load-icon").removeClass("fa-check");
    $(".load-icon").addClass("fa-cog fa-spin");
    $scope.pwdLoader = false;
    $scope.admnUrl = "./Administrator";
    $scope.showFilterBox = true;

    $scope.createUser = function () {
        $scope.updateApp();
        $scope.pwdLoader = true;
        $http({
            url: $scope.admnUrl,
            method: 'POST',
            data: $httpParamSerializerJQLike({
                "username": $scope.username,
                "role": $scope.role,
                "service": "create"
            }),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(function success(response) {
            var rsp = response.data;

            if (rsp.trim() === "success") {
                $(".load-icon").addClass("fa-check");
                $(".load-icon").removeClass("fa-cog fa-spin");
                $scope.clearForm();
                $(".rsp").html("<span class='text-success'>Update successful<span> <br> ");
            } else {
                $(".load-icon").removeClass("fa-check");
                $(".load-icon").addClass("fa-cog fa-spin");
                $(".rsp").html("<span class='text-danger'>" + rsp + "</span>");
            }
        }, function error(response) {
            $(".load-icon").removeClass("fa-check");
            $(".load-icon").addClass("fa-cog fa-spin");
            $(".rsp").html("<span class='text-danger'>An error occured. Please try again</span>");
        });
    };

    $scope.appLogin = function () {
        $scope.updateApp();
        $scope.pwdLoader = true;
        $http({
            url: $scope.admnUrl,
            method: 'POST',
            data: $httpParamSerializerJQLike({
                "username": $scope.username,
                "password": $scope.password,
                "service": "login"
            }),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(function success(response) {
            var rsp = response.data;

            if (rsp.trim() === "success") {
                $(".load-icon").addClass("fa-check");
                $(".load-icon").removeClass("fa-cog fa-spin");
                $scope.clearForm();
                $(".rsp").html("<span class='text-success'>Account created successfully<span> <br><a href='#!/Administrator' class='text-primary font-weight-bold'>Continue</a> ");
            } else {
                $(".load-icon").removeClass("fa-check");
                $(".load-icon").addClass("fa-cog fa-spin");
                $(".rsp").html("<span class='text-danger'>" + rsp + "</span>");
            }
        }, function error(response) {
            $(".load-icon").removeClass("fa-check");
            $(".load-icon").addClass("fa-cog fa-spin");
            $(".rsp").html("<span class='text-danger'>An error occured. Please try again</span>");
        });
    };

    $scope.clearForm = function () {
        $scope.name = "";
        $scope.username = "";
        $scope.password = "";
    };

    $scope.hidePwdLoader = function () {
        $scope.pwdLoader = false;
    };

    $scope.hideFilterBox = function () {
        $scope.showFilterBox = false;
    };

    $scope.showFilterBoxView = function () {
        $scope.showFilterBox = true;
    };

    $scope.initInfo();
});