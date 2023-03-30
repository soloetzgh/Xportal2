app.controller('supportCtrl', function ($scope, $http, $httpParamSerializerJQLike) {

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


    $scope.pwdLoader = false;
    $scope.pwdUrl = "./chgpwd";
    $scope.pwdUrl2 = "./chgpwdnew";
    $scope.resetUrl = "../../PasswordReset";
    $scope.changePassword = function () {
        $scope.getProfile();
        $scope.pwdLoader = true;
        $http({
            url: $scope.pwdUrl,
            method: 'POST',
            data: $httpParamSerializerJQLike({
                "oldPwd": $scope.oldPwd,
                "newPwd": $scope.newPwd,
                "confPwd": $scope.confPwd,
                "email": $scope.email
            }),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(function success(response) {
            var rsp = response.data;
            $(".load-icon").removeClass("fa-cog fa-spin");
            if (rsp.trim() === "success") {
                $(".load-icon").addClass("fa-check");
                $(".load-icon").removeClass("fa-cog fa-spin");
                $(".rsp").html("<span class='text-success'>Password change successful<span> <br><a href='./login' class='text-primary font-weight-bold'>Continue</a> ");
            } else {
                $(".load-icon").removeClass("fa-check");
//                $(".load-icon").addClass("fa-cog fa-spin");
                $(".load-icon").removeClass("fa-cog fa-spin");
                $(".rsp").html("<span class='text-danger'>" + rsp + "</span>");
            }
        }, function error(response) {
            $(".load-icon").removeClass("fa-check");
//            $(".load-icon").addClass("fa-cog fa-spin");
            $(".load-icon").removeClass("fa-cog fa-spin");
            $(".rsp").html("<span class='text-danger'>An error occured. Please try again</span>");
        });
    };

    $scope.changePasswordNew = function () {
        $scope.getProfile();
        $scope.pwdLoader = true;
        $http({
            url: $scope.pwdUrl2,
            method: 'POST',
            data: $httpParamSerializerJQLike({
                "oldPwd": $scope.oldPwd,
                "newPwd": $scope.newPwd,
                "confPwd": $scope.confPwd,
                "email": $scope.email,
                "confemail": $scope.confemail
            }),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(function success(response) {
            var rsp = response.data;

            if (rsp.trim() === "success") {
                $(".load-icon").addClass("fa-check");
                $(".load-icon").removeClass("fa-cog fa-spin");
                $(".rsp").html("<span class='text-success'>Password change successful<span> <br><a href='./login' class='text-primary font-weight-bold'>Continue</a> ");
            } else {
                $(".load-icon").removeClass("fa-check");
//                $(".load-icon").addClass("fa-cog fa-spin");
                $(".load-icon").removeClass("fa-cog fa-spin");
                $(".rsp").html("<span class='text-danger'>" + rsp + "</span>");
            }
        }, function error(response) {
            $(".load-icon").removeClass("fa-check");
//            $(".load-icon").addClass("fa-cog fa-spin");
            $(".load-icon").removeClass("fa-cog fa-spin");
            $(".rsp").html("<span class='text-danger'>An error occured. Please try again</span>");
        });
    };

    $scope.resetPassword = function () {
        $scope.getProfile();
        console.log("ref");
        $(".bt-reset").html("Please Wait <i class='fa fa-3x fas fa-spinner fa-pulse'></i>");
        $scope.pwdLoader = true;
        $http({
            url: $scope.resetUrl,
            method: 'POST',
            data: $httpParamSerializerJQLike({
                "username": $('#username').val(),
                "email": $('#email').val()
            }),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(function success(response) {
            var rsp = response.data;

            console.log(rsp);

//            if (rsp.trim() === "success") {
//                $(".rsp").html("Email Sent");
//            } else {
//                
//                $(".rsp").html("" + rsp + "</span>");
//            }
        }, function error(response) {
            $(".load-icon").removeClass("fa-check");
            $(".load-icon").addClass("fa-cog fa-spin");
            $(".rsp").html("<span class='text-danger'>An error occured. Please try again</span>");
        });
    };

    $scope.fetchUserEmail = function () {
        $scope.email = $scope.profile.email;
    };

    $scope.hideFilterBox = function () {
        $scope.pwdLoader = false;
    };


});