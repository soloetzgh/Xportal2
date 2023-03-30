$(document).ready(function () {

//    setTimeout(connect(), 5000);
//    setTimeout(met(), 3000);

});

var ws;
var toastr;
var userN;
var host;
var pathname;
toastr.options = {
    "closeButton": false,
    "debug": false,
    "newestOnTop": false,
    "progressBar": false,
    "positionClass": "toast-top-right",
    "preventDuplicates": false,
    "onclick": null,
    "showDuration": "300",
    "hideDuration": "1000",
    "timeOut": "1000",
    "extendedTimeOut": "1000",
    "showEasing": "swing",
    "hideEasing": "linear",
    "showMethod": "fadeIn",
    "hideMethod": "fadeOut"
};

function getScope(ctrlName) {
    var sel = 'body[ng-controller="' + ctrlName + '"]';
    return angular.element(sel).scope();
}
function changeText() {
    var $scope = getScope('fgTransactionsCtrl');
    var userN = $scope.profile.user_id;
    alert(userN);
//    $scope.message = 'goodbye';
//    $scope.$apply();
}


function connect() {
//    var username = document.getElementById("username").value;

    var host = document.location.host;
    var pathname = document.location.pathname;
    var $scope = getScope('fgTransactionsCtrl');
    userN = $scope.profile.user_id;

    ws = new WebSocket("ws://" + host + pathname + "chat/" + userN);

    ws.onmessage = function (event) {
        console.log(event.data);
        var message = JSON.parse(event.data);

        toastr["success"](message.content, message.from);
    };
}

function met() {
    alert("ws://" + host + pathname + "chat/" + userN);
    toastr["success"]("message.", "message.from");
}
;

function send(message, user) {
    var json = JSON.stringify({
        "content": message,
        "to": user
    });

    ws.send(json);
}