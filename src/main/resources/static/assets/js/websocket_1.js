var ws;
var toastr;
toastr.options = {
    "closeButton": false,
    "debug": false,
    "newestOnTop": false,
    "progressBar": false,
    "positionClass": "toast-top-right",
    "preventDuplicates": true,
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
    var userN = $scope.profile.user_id;

    ws = new WebSocket("ws://" + host + pathname + "chat/" + userN);

    ws.onmessage = function (event) {
//        var log = document.getElementById("log");
        console.log(event.data);
        var message = JSON.parse(event.data);

        toastr["success"](message.content, message.from);
//        log.innerHTML += message.from + " : " + message.content + "\n";
    };
}

function met() {
    alert("helo");
}
;

function send(content, user) {
//    var content = document.getElementById("msg").value;
    var json = JSON.stringify({
        "content": content,
        "to": user
    });

    ws.send(json);
}