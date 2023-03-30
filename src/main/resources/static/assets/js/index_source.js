///* global Swal, moment, pdfMake, XLSX, accounting, Symbol */
//
//
//var toastr, angular, zxcvbn;
//var ticking = false;
//var lastScrollLeft = 0;
//var $routeProviderReference;
//var IN_DEVELOPMENT = true;
//var environment = window.location.hostname;
//var routes = "";
//var userProfileData = "";
//var userProfiles = "";
//var portalSettings = "";
//var accountUser = "";
//var session_timeout = 600;
//var pub_key = "";
//var roleElementBound = false;
//var autoRefreshRate = 5000;
//var settings = "";
//var startedSession = false;
//var timeoutAlert = false;
//var isIdle = false;
//var allowedMenuItems = [];
//var allowedMenu = [];
//
//var app = angular.module('XPortalApp', ['ngRoute', 'ngAnimate', 'oc.lazyLoad', 'angular.filter', 'userInfo', 'menuList', 'menus', 'ngIdle']);
//
//$(document).ready(function () {
//    $('.loader').css('display', 'none');
//    $.LoadingOverlaySetup({
//        background: "rgba(0, 0, 0, 1.0)",
//        image: "loader.png",
//        imageAnimation: "1.5s fadein"
////                imageColor: "#ffcc00"
//    });
//    $.LoadingOverlay("show");
//    toastr.options = {
//        "closeButton": true,
//        "debug": false,
//        "newestOnTop": false,
//        "progressBar": false,
//        "positionClass": "toast-bottom-right",
//        "preventDuplicates": true,
//        "onclick": null,
//        "showDuration": "300",
//        "hideDuration": "1000",
//        "timeOut": "3500",
//        "extendedTimeOut": "1000",
//        "showEasing": "swing",
//        "hideEasing": "linear",
//        "showMethod": "slideDown",
//        "hideMethod": "slideUp",
//        "closeMethod": "slideUp"
//    };
//    ["input", "keydown", "keyup", "mousedown", "mouseup", "select", "contextmenu", "drop", "paste"].forEach(function (event) {
//        $(document).on(event, '.numberOnly', function (e) {
//            $(this).val($(this).val().replace(/[^0-9]+/g, ''));
//        });
//    });
//    $(document).on('focus', '.newpassword input', function (e) {
////        resetPass();
//        $('#pswd_info').show();
//        var pass = $('.newpassword input').val();
////        console.log('pass: ' + pass);
//        validatePassword(pass);
////        $('#pswd_info').show();
////        validatePassword($(this).val());
//    }).on('blur', '.newpassword input', function (e) {
//        $('#pswd_info').hide();
//    }).on('keyup', '.newpassword input', function (e) {
//        validatePassword($(this).val());
//    });
//    $(document).on('click', '#start-date-btn', function (e) {
//        var startOfDay = moment().format('YYYY-MM-DD 00:00');
//        $('#start-date').datetimepicker({
//            format: 'Y-m-d H:i',
//            value: startOfDay
//        });
//        $('#start-date').datetimepicker('show');
//    });
//    $(document).on('click', '#end-date-btn', function (e) {
//        var currentDateTime = moment().format('YYYY-MM-DD 23:59');
//        $('#end-date').datetimepicker({
//            format: 'Y-m-d H:i',
//            value: currentDateTime
//        });
//        $('#end-date').datetimepicker('show');
//    });
//    $(document).on('mouseenter', '.draggable_item_header', function (e) {
//        $('.draggable_item').dragon({
//            'handle': $('.draggable_item_header')
//        });
//    });
//    function debounce(method, delay) {
//        clearTimeout(method._tId);
//        method._tId = setTimeout(function () {
//            method();
//        }, delay);
//    }
//
//    function updateTableView() {
////        console.log('update view');
//        var node = document.querySelector(".table-responsive");
//        if (node) {
//            $('.table-responsive').responsiveTable('update');
//        }
//    }
//
//    $(window).scroll(function () {
//        debounce(updateTableView, 150);
//    });
//});
//
//function checkInput(input, words) {
//    return !words.some(function (word) {
//        return new RegExp(word, "i").test(input);
//    });
//}
//
//function resetPass() {
//    $('#password-details').removeClass('valid').addClass('invalid');
//    $('#password-length').removeClass('valid').addClass('invalid');
//    $('#password-lowercase').removeClass('valid').addClass('invalid');
//    $('#password-uppercase').removeClass('valid').addClass('invalid');
//    $('#password-special').removeClass('valid').addClass('invalid');
//    $('#password-number').removeClass('valid').addClass('invalid');
//}
//
//function validatePassword(pswd) {
//    var username = userProfileData.username.trim().toLowerCase().split(/[^A-Za-z]/);
//    var fname = userProfileData.firstname.trim().toLowerCase().split(/[^A-Za-z]/);
//    var lname = userProfileData.lastname.trim().toLowerCase().split(/[^A-Za-z]/);
//    var containsDetails = checkInput(pswd, username) && checkInput(pswd, fname) && checkInput(pswd, lname);
//    var special = pswd.match(/\W|_/g);
//    var pswdL = pswd.length < 12;
//    var lower = pswd.match(/[a-z]/);
//    var upper = pswd.match(/[A-Z]/);
//    var number = pswd.match(/\d/);
//    if (containsDetails) {
//        $('#password-details').removeClass('invalid').addClass('valid');
//    } else {
//        $('#password-details').removeClass('valid').addClass('invalid');
//    }
//
//    if (pswdL) {
//        $('#password-length').removeClass('valid').addClass('invalid');
//    } else {
//        $('#password-length').removeClass('invalid').addClass('valid');
//    }
////validate lowercase
//    if (lower) {
//        $('#password-lowercase').removeClass('invalid').addClass('valid');
//    } else {
//        $('#password-lowercase').removeClass('valid').addClass('invalid');
//    }
//
////validate capital letter
//    if (upper) {
//        $('#password-uppercase').removeClass('invalid').addClass('valid');
//    } else {
//        $('#password-uppercase').removeClass('valid').addClass('invalid');
//    }
//
////validate number
//    if (number) {
//        $('#password-number').removeClass('invalid').addClass('valid');
//    } else {
//        $('#password-number').removeClass('valid').addClass('invalid');
//    }
//
//    if (special) {
//        $('#password-special').removeClass('invalid').addClass('valid');
//    } else {
//        $('#password-special').removeClass('valid').addClass('invalid');
//    }
//    var valid = !(number && containsDetails && upper && lower && special && pswdL);
//    if (!valid) {
//        $('#pswd_info').show();
//    }
//    return valid;
//}
//
//function animateCSS(element, animation, prefix) {
//    if (prefix === void 0) {
//        prefix = 'animate__';
//    }
//
//    return (new Promise(function (resolve, reject) {
//        var animationName = "" + prefix + animation;
//        var node = document.querySelector(element);
//        if (node) {
//            node.classList.add(prefix + "animated", animationName); // When the animation ends, we clean the classes and resolve the Promise
//
//            function handleAnimationEnd() {
//                node.classList.remove(prefix + "animated", animationName);
//                resolve('Animation ended');
//            }
//
//            node.addEventListener('animationend', handleAnimationEnd, {
//                once: true
//            });
//        }
//    }));
//}
//
//(function () {
//
//    // Get Angular's $http module.
//    var initInjector = angular.injector(['ng']);
//    var $http = initInjector.get('$http');
//    var $rootScope = initInjector.get('$rootScope');
//    $rootScope.authenticated = false;
//    $rootScope.defaultState = false;
////    $rootScope.timerate = 5000;
//    // Get user info.
//    $rootScope.fetchMenuList = function () {
//
//        return $http.get('api/PortalSettings?action=menuList').then(
//                function (resp) {
////                    console.log('dat: ' + resp.data.messag);
////                    console.log('dat2: ' + JSON.stringify(resp.data));
//                    var respmesg = (resp.data.message === undefined || resp.data.message !== 'Session Expired') ? resp.data : "";
////                    console.log('fre: ' + respmesg);
//                    return respmesg;
//                });
//    };
//    $rootScope.fetchPortalSettings = function () {
//
//        return $http.get('api/PortalSettings?action=config').then(
//                function (resp) {
////                    console.log('dat: ' + resp.data.messag);
////                    console.log('dat2: ' + JSON.stringify(resp.data));
//                    var respmesg = (resp.data.message === undefined || resp.data.message !== 'Session Expired') ? resp.data : "";
////                    console.log('fre: ' + respmesg);
//                    return respmesg;
//                });
//    };
//    $rootScope.fetchMenu = function (menu) {
////        console.log('menulist' + JSON.stringify(menu));
//        var data = new Promise(function (resolve) {
//            var unique = "";
//            if (menu !== "") {
//                function _toConsumableArray(arr) {
//                    return _arrayWithoutHoles(arr) || _iterableToArray(arr) || _nonIterableSpread();
//                }
//
//                function _nonIterableSpread() {
//                    throw new TypeError("Invalid attempt to spread non-iterable instance");
//                }
//
//                function _iterableToArray(iter) {
//                    if (Symbol.iterator in Object(iter) || Object.prototype.toString.call(iter) === "[object Arguments]")
//                        return Array.from(iter);
//                }
//
//                function _arrayWithoutHoles(arr) {
//                    if (Array.isArray(arr)) {
//                        for (var i = 0, arr2 = new Array(arr.length); i < arr.length; i++) {
//                            arr2[i] = arr[i];
//                        }
//                        return arr2;
//                    }
//                }
//
//                unique = _toConsumableArray(new Set(menu.map(function (item) {
//                    var jsonMenu = {};
//                    jsonMenu['name'] = item['menu'];
//                    jsonMenu['icon'] = item['icon'];
//                    return jsonMenu; //item['menu']  + ' '+ item['icon'];
//                }))).filter(function (el) {
//                    return  el['name'] !== undefined;
//                });
////                console.log('unique: ' + JSON.stringify(unique));
//            }
//            resolve(unique);
//        });
//        return data;
//    };
////    console.log('window: ' + window.location);
//
//    if (window.location.hash.split('?')[0].replace("#!/", "") !== 'resetAccount') {
//        $http.get('api/UserProfile').then(
//                function (success) {
//
//                    // Define a 'userInfo' module.
//                    angular.module('userInfo', []).constant('userInfo', success.data);
//                    userProfileData = success.data;
////                Fetch MenuItems
//                    $rootScope.fetchMenuList().then(function (menuList) {
////                    console.log(JSON.stringify(menuList));
//                        routes = menuList;
//                        angular.module('menuList', []).constant('menuList', menuList);
//                        $rootScope.fetchMenu(menuList).then(function (menus) {
//                            $rootScope.fetchPortalSettings().then(function (settingsData) {
////                        console.log(JSON.stringify(menus));
//                                settings = settingsData;
////                            console.log('settingos: ' + settings);
//                                angular.module('menus', []).constant('menus', menus);
//                                angular.element(document).ready(function () {
//                                    angular.bootstrap(document, ['XPortalApp']);
//                                });
//                            });
//                        });
//                    });
//                });
//    } else {
//        angular.module('userInfo', []).constant('userInfo', {});
//        userProfileData = {};
////                Fetch MenuItems
//        $rootScope.fetchMenuList().then(function (menuList) {
////                    console.log(JSON.stringify(menuList));
//            routes = menuList;
//            angular.module('menuList', []).constant('menuList', menuList);
//            $rootScope.fetchMenu(menuList).then(function (menus) {
//                $rootScope.fetchPortalSettings().then(function (settingsData) {
////                        console.log(JSON.stringify(menus));
//                    settings = settingsData;
////                            console.log('settingos: ' + settings);
//                    angular.module('menus', []).constant('menus', menus);
//                    angular.element(document).ready(function () {
//                        angular.bootstrap(document, ['XPortalApp']);
//                    });
//                });
//            });
//        });
//    }
//
//})();
//
//app.config(['$httpProvider', '$routeProvider', 'IdleProvider', 'KeepaliveProvider', function ($httpProvider, $routeProvider, IdleProvider, KeepaliveProvider) {
//
//        //calculate time
//        session_timeout = settings.session_timeout;
//        pub_key = settings.pub_key;
////        console.log('timeout: ' + session_timeout);
////        IdleProvider.idle(session_timeout - 9);
////        IdleProvider.timeout(9);
//        //KeepaliveProvider.interval(10);
//
//
//        $httpProvider.interceptors.push('ajaxNonceInterceptor');
//        $httpProvider.interceptors.push('responseInterceptor');
//        $httpProvider.interceptors.push('csrfInterceptor');
//        $routeProvider.caseInsensitiveMatch = true;
//        angular.forEach(routes, function (route) {
//            if (route.controller !== null && route.controller !== undefined && route.templateUrl !== null) {
////                console.log('route: ' + route.name + '\n access: ' + route.controller);
//                $routeProvider.when('/' + route.routePath, {
//                    title: route.name,
//                    templateUrl: 'views/' + route.templateUrl,
//                    controller: route.controller,
//                    resolve: {
//                        //This function is injected with the AuthService where you'll put your authentication logic
////                        'loadMyCtrl': ['$ocLazyLoad', function ($ocLazyLoad) {
////                                // you can lazy load files for an existing module
////                                return $ocLazyLoad.load('lazy_assets');
////                            }],
//                        'initialData': ["$ocLazyLoad", "profileService", "$q", function ($ocLazyLoad, profileService, $q) {
//                                return $q.all({
//                                    lazy: (route.controller === 'authCtrl' ? '' : $ocLazyLoad.load('lazy_assets')),
//                                    auth: profileService.authenticate(route.accessLevel, route.openTo, route.open)
//                                });
//                            }]
////                        'auth': function (profileService) {
////                            return profileService.authenticate(route.accessLevel, route.openTo);
////                        }
//                    }
//                });
//            }
//        });
//        $routeProvider.otherwise({redirectTo: '/auth'});
//    }])
//        .config(['$provide', function ($provide) {
////                console.log('env: ' + environment + ' dev: ' + IN_DEVELOPMENT);
//                if (window && !(environment === "localhost" && IN_DEVELOPMENT)) {
//                    window.console.log = function () {};
//                    $provide.decorator('$log', ['$delegate', function ($delegate)
//                        {
//                            var originals = {};
//                            var methods = ['info', 'debug', 'warn', 'error', 'log'];
//                            angular.forEach(methods, function (method)
//                            {
//                                originals[method] = $delegate[method];
//                                $delegate[method] = function () {};
//                            });
//                            return $delegate;
//                        }]);
//                }
//
//            }])
//        .config(['$ocLazyLoadProvider', function ($ocLazyLoadProvider) {
//                $ocLazyLoadProvider.config({
//                    debug: false,
//                    events: false,
//                    modules: [{
//                            name: 'lazy_assets',
//                            files: [
//                                'assets/css/rwd-table.min.css',
////                                'assets/css/all.min.css',
//                                'assets/css/bootstrap-toggle.min.css',
////                                'assets/css/uikit.almost-flat.min.css',
//                                'assets/css/jquery.datetimepicker.min.css',
//                                'assets/css/jquery.contextMenu.min.css',
//                                'assets/css/daterangepicker.min.css',
//                                'assets/js/rwd-table.js',
//                                'assets/js/app.js',
//                                'assets/js/chart.min.js',
////                                'assets/js/dynamicgraph.js',
//                                'assets/js/xlsx.full.min.js',
//                                'assets/js/ng-file-upload-shim.js',
//                                'assets/js/ng-file-upload.js',
//                                'assets/js/jquery.datetimepicker.full.min.js',
////                                'https://cdnjs.cloudflare.com/ajax/libs/angular-js-bootstrap-datetimepicker/2.5.1/datetimepicker.min.css',
////                                'https://cdnjs.cloudflare.com/ajax/libs/angular-js-bootstrap-datetimepicker/2.5.1/datetimepicker.min.js',
//                                'assets/js/daterangepicker.min.js',
////                                'assets/js/moment.min.js',
//                                'assets/js/jquery.stickyheader.js',
//                                'assets/js/jquery.ba-throttle-debounce.min.js',
//                                'assets/js/money.min.js',
//                                'assets/js/angular-long-press.min.js',
//                                'assets/js/jquery.contextMenu.min.js',
//                                'assets/js/jquery.ui.position.js',
//                                'assets/js/bootstrap-toggle.min.js',
//                                'assets/js/filesaver.min.js',
//                                'assets/js/jquery.dragon.js'
////                                'assets/css/loading.min.css',
////                                'assets/css/ldbtn.min.css'
//                            ],
//                            cache: true,
//                            serie: true
//                        }]
//                }
//                );
//            }])
//        .run(['$route', '$rootScope', 'profileService', '$location', 'menuList', 'menus', '$http', '$httpParamSerializerJQLike', function ($route, $rootScope, profileService, $location, menuList, menus, $http, $httpParamSerializerJQLike) {
//                $rootScope.menus = menus;
////            const data = menus;
////            const set = new Set(data.map(item => JSON.stringify(item)));
////            const dedup = [...set].map(item => JSON.parse(item));
////            console.log(`Removed ${data.length - dedup.length} elements`);
////            console.log(dedup);
////            $rootScope.menus = dedup;
////            let myMap = new Map(Object.entries(dedup));
////            console.log([...myMap]);
//                $rootScope.menuList = menuList;
//                $rootScope.timerate = autoRefreshRate;
//                var session = {
//                    //Logout Settings
////                inactiveTimeout: (session_timeout - 10) * 1000, //(ms) The time until we display a warning message
//                    inactiveTimeout: 10000,
//                    warningTimeout: (session_timeout - 10) * 1000, //(ms) The time until we log them out
//                    minWarning: 2000, //(ms) If they come back to page (on mobile), The minumum amount, before we just log them out
//                    warningStart: null, //Date time the warning was started
//                    warningTimer: null, //Timer running every second to countdown to logout
//                    logout: function () {       //Logout function once warningTimeout has expired
//                        //window.location = settings.autologout.logouturl;
//                        $rootScope.logout();
//                    },
//                    //Keepalive Settings
//                    keepaliveTimer: null,
//                    keepaliveUrl: "",
//                    keepaliveInterval: 30000, //(ms) the interval to call said url
//                    keepAlive: function () {
//                        if (!isIdle) {
//                            profileService.reloadSessionProfile();
//                        }
//                    }
//                };
//                session.keepaliveTimer = setInterval(function () {
//                    session.keepAlive();
//                }, session.keepaliveInterval);
//                var showAlert = function () {
//
//                    if (!timeoutAlert) {
//                        timeoutAlert = true;
//                        Swal.fire({
//                            title: 'Inactivity Detected!',
//                            html: '<br/>You have been inactive for a while. For your security, you will be automatically logged out in <strong></strong> seconds.<br/><br/>',
//                            timer: 10000,
//                            type: 'warning',
//                            allowEscapeKey: true,
//                            showConfirmButton: false,
//                            onOpen: function () {
//                                const content = Swal.getContent();
//                                const $ = content.querySelector.bind(content);
//                                timerInterval = setInterval(() => {
//                                    Swal.getContent().querySelector('strong')
//                                            .textContent = (Swal.getTimerLeft() / 1000)
//                                            .toFixed(0);
//                                }, 100);
//                            },
//                            onClose: function () {
//                                clearInterval(timerInterval);
//                                timeoutAlert = false;
//                            }
//                        });
//                    }
//                };
//                $(document).on("idle.idleTimer", function (event, elem, obj) {
//                    //Get time when user was last active
////                console.log('tertert');
//                    event.stopPropagation();
//                    var diff = (+new Date()) - obj.lastActive - obj.timeout,
//                            warning = (+new Date()) - diff;
////                console.log('diff: ' + diff);
//
//                    //On mobile js is paused, so see if this was triggered while we were sleeping
//                    if (diff >= session.warningTimeout || warning <= session.minWarning) {
//                        $rootScope.logout();
//                    } else {
//
//                        session.warningStart = (+new Date()) - diff;
//                        session.warningTimer = setInterval(function () {
//                            var remaining = Math.round((session.warningTimeout / 1000) - (((+new Date()) - session.warningStart) / 1000));
////                        console.log('remainig:' + remaining);
//                            if (remaining <= 30) {
//                                isIdle = true;
//                            } else {
//                                isIdle = false;
//                            }
//                            if (remaining >= 0 && remaining <= 10) {
//                                showAlert();
//                            } else if (remaining >= 0) {
//                            } else {
//                                session.logout();
//                            }
//                        }, 1000);
//                    }
//                });
//                $(document).on("active.idleTimer", function (event, elem, obj) {
//                    event.stopPropagation();
//                    profileService.reloadSessionProfile();
//                    if (isIdle) {
//                        Swal.close();
//                    }
//                    clearTimeout(session.warningTimer);
//                });
//                var letters = {
//                    1: ["q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "a", "s", "d", "f", "g", "h", "j", "k", "l", "z", "x", "c", "v", "b", "n", "m"],
//                    2: ["Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "A", "S", "D", "F", "G", "H", "J", "K", "L", "Z", "X", "C", "V", "B", "N", "M"]
//                }, i, letter, final = "";
//                random = (max, min) => {
//                    return Math.floor(Math.random() * (max - min + 1) + min);
//                };
//                function generateCode(length) {
//                    final = "", letter = "";
//                    for (i = 1; i <= length; i++) {
//                        letter = letters[random(0, 3)][random(0, 25)];
//                        final += letter;
//                    }
//                    return final;
//                }
//
//                $rootScope.encryptPayload = function (data) {
//                    if (pub_key) {
//                        var encrypt = new JSEncrypt();
//                        encrypt.setPublicKey(pub_key);
//                        var encrypted = encrypt.encrypt(JSON.stringify(data));
//                        return encrypted;
//                    }
//                };
//                $rootScope.showLoading = function () {
//                    $('.loader').css('display', 'block');
////                $('.loader').show();
////                $rootScope.showLoader = true;
//                };
//                $rootScope.hideLoading = function () {
//                    $('.loader').css('display', 'none');
////                $('.loader').hide();
//                    $rootScope.showLoader = false;
////                $rootScope.updateRecordsTable();
//                };
//                $rootScope.toggleFab = function () {
////                $('.floating-menus').toggle();
//                    $('.float-btn-group').toggleClass('open');
//                };
//                $rootScope.startSession = function () {
////                $('.floating-menus').toggle();
//
//                    if (profileService.isAuthenticated() && !startedSession) {
//                        startedSession = true;
//                        console.log('started');
//                        $(document).idleTimer(session.inactiveTimeout);
//                    }
//                };
//                $rootScope.startSession();
////            $.LoadingOverlaySetup({
////                background: "rgba(0, 0, 0, 1.0)",
////                image: "loader.png",
////                imageAnimation: "1.5s fadein"
//////                imageColor: "#ffcc00"
////            });
//                $rootScope.goHome = function () {
////                window.location = '/XPortal/#!' + profileService.getUserProfile().firstRoute;
//                    window.location = './#!' + profileService.getUserProfile().firstRoute;
////                window.location = "/XPortal/#!/dashboard";
//                };
//                $rootScope.changepass = function () {
////                window.location = "/XPortal/#!/firstChangePassword";
//                    window.location = "./#!/firstChangePassword";
//                };
//                $rootScope.menuFilter = function (item) {
//                    if (item.showOnMenu === '1') {
//                        return item;
//                    }
//                };
//                $rootScope.checkMenuList = function (type) {
//                    var i = (userProfileData.type_id !== undefined && userProfileData.type_id !== null) ? userProfileData.type_id : "";
//                    var count = $rootScope.menuList.reduce(function (acc, cur) {
//                        return cur.menu === type && (i.indexOf(cur.accessLevel) > -1 || ((i.indexOf('[1]') > -1 || i.indexOf('[0]') > -1) && cur.openTo.length === 0)) ? ++acc : acc;
//                    }, 0);
//                    return count;
//                };
//                $rootScope.defaultState = false;
//                $rootScope.authenticated = false;
//                var profile = profileService.getUserProfile();
//                $rootScope.populateMenuList = function () {
//                    var menuOptions = [];
//                    angular.forEach($rootScope.menuList, function (menuItem) {
//                        var accessLevel = menuItem.accessLevel;
////                    console.log('accessLevel: ' + accessLevel);
//                        var openTo = menuItem.openTo;
//                        var open = menuItem.open;
//                        var menuName = menuItem.menu;
//                        var menuIcon = menuItem.icon;
//                        var allowed = false;
//                        var profile = $rootScope.profile;
//                        if (profile !== undefined && openTo !== undefined && open !== undefined) {
//
////                    var types_ = openTo.split("~");
//                            angular.forEach(openTo.split(","), function (stats) {
//
//                                var type = "";
//                                var access = "";
//                                var roles = "";
//                                var allowUser = false;
////                        console.log('stats: ' + stats);
//                                if (stats.indexOf("|") > -1 && !stats.split("|")[1].split("~").includes("0")) {
//                                    type = stats.split("|")[0];
//                                    roles = profile.user_code.split('[' + type + ']|').pop().split(',')[0].split("~");
//                                    access = stats.split("|")[1].split("~");
//                                    var b = access.some(function (val) {
//                                        return (roles.indexOf(val) !== -1) || (roles.indexOf('[' + val + ']') !== -1);
//                                    });
//                                    allowUser = b;
//                                }
//
//                                if (profile.type_id && profile.type_id.indexOf(accessLevel) > -1 && allowUser) {
//                                    allowed = true;
//                                }
//
//                                if (profile.type_id && ((profile.type_id.indexOf('[1]') > -1 || profile.type_id.indexOf('[0]') > -1) && open === '1')) {
//                                    allowed = true;
//                                }
//                            });
//                        }
//                        if (allowed && !allowedMenuItems.includes(accessLevel)) {
//                            allowedMenuItems.push(accessLevel);
//                            if (!menuOptions.includes(menuName) && menuName) {
//                                allowedMenu.push({name: menuName, icon: menuIcon});
//                            }
//
//                        }
//
//                    });
//                    $rootScope.menus = allowedMenu;
//                };
//                if (profile !== "") {
//                    if (!($('#navbarmenu').hasClass('animated bounceIn'))) {
//                        $('#navbarmenu').addClass('animated bounceIn');
//                        $rootScope.authenticated = true;
//                    }
//                }
//
//                $rootScope.$on("$routeChangeStart", function (event, next, current) {
////                console.log('2-' + next.originalPath);
//                    var route = next.originalPath !== undefined ? next.originalPath.replace("/", "") : "";
//                    Swal.close();
////                var route = next.toString().split("#!")[1].replace("/", "");
//                    console.log('router: ' + route);
////                console.log('route2: ' + JSON.stringify(next));
//
//                    if (!profileService.isAuthenticated() && route === "resetAccount") {
////                    console.log('ehere');
//
//                    } else {
//
//                        if (profileService.isAuthenticated()) {
//                            if (profileService.isFirstLogon()) {
//                                console.log('df');
//                                $rootScope.changepass();
//                                setTimeout(function () {
//                                    $rootScope.toggleErrorNotification("Password Change Required");
//                                }, 1000);
//                            } else if (route === "") {
//                                $rootScope.goHome();
//                                console.log('a');
//                                setTimeout(function () {
//                                    $rootScope.toggleErrorNotification("You are already Logged In");
//                                }, 1000);
//                            } else if (route === "auth") {
//                                console.log('b');
//                                $rootScope.goHome();
//                                setTimeout(function () {
//                                    $rootScope.toggleErrorNotification("You are already Logged In");
//                                }, 1000);
//                            }
////                    else if (route !== "") {
////
////                    }
//                            else {
//
//                                if (!$rootScope.profile) {
//                                    profileService.setUserProfile(JSON.stringify(profile));
//                                }
//
//                                $rootScope.authenticated = true;
//                                $('#navbarmenu').css('display', 'block');
//                                setTimeout(function () {
//                                    $.LoadingOverlay("hide");
//                                }, 1300);
//                            }
//
//                        } else {
////                        console.log('erww : ' + route);
//
//                            if ($('#navbarmenu').hasClass('animated bounceIn')) {
//                                $('#navbarmenu').removeClass('animated bounceIn');
//                            }
//                            $rootScope.authenticated = false;
//                            if (route !== 'auth') {
//                                window.location = './#!/auth';
//                            }
//                        }
//                    }
//
//                });
//                $rootScope.$on('$routeChangeError', function (event, current, previous, rejection) {
//                    console.log('reject: ' + rejection);
//                    event.preventDefault();
//                    if (rejection === 'Unauthorized') {
//                        $location.path('/unauthorized');
//                    }
////                if (rejection === 'AuthenticationError') {
////                    $location.path('/auth');
////                }
//                    if (rejection === 'Authenticated') {
////                    $location.path('/auth');
//                        console.log('authenticated already');
//                    }
//                });
//                $rootScope.$on('$routeChangeSuccess', function (event, current, previous) {
//                    $rootScope.title = current.$$route ? current.$$route.title : "";
//                    window.document.title = ($rootScope.title.length > 0 ? 'XPORTAL | ' + $rootScope.title : '');
//                    profileService.reloadSessionProfile();
////                if (current.$$route.initMethod) {
////                    if (typeof $rootScope[current.$$route.initMethod] === "function") {
////                        $rootScope[current.$$route.initMethod]();
////                    }
////                }
//                });
//                $rootScope.$on('$viewContentLoaded', function () {
////                console.log('msg: loader');
//                    setTimeout(function () {
//                        $.LoadingOverlay("hide");
//                    }, 250);
//                });
//                $rootScope.checkOpenTo = function (accessLevel, profile, openTo, open) {
//                    return allowedMenuItems.includes(accessLevel);
//                };
//                $rootScope.userReady2 = function () {
//                    if (profileService.isAuthenticated()) {
//                        if (profileService.isFirstLogon()) {
//                            $location.path('/firstChangePassword');
//                            return false;
//                        } else {
//                            return true;
//                        }
//                    }
//                };
//                $rootScope.trnxTAT = function (end, start) {
//                    end = new Date(end);
//                    start = new Date(start);
//                    var diff = (end.getTime() - start.getTime()) / 1000.0;
//                    return Math.abs(Math.round(diff));
//                };
//                $rootScope.updateRecordsTable = function () {
//                    setTimeout(function () {
//                        $('.table-responsive').responsiveTable('update');
//                    }, 800);
//                };
//                $rootScope.toggleErrorNotification = function (message) {
//                    toastr["error"](message);
//                };
//                $rootScope.toggleSuccessNotification = function (message) {
////                console.log('login');
//                    toastr["success"](message);
//                };
//                $rootScope.toggleInfoNotification = function (message) {
//                    toastr["info"](message);
//                };
//                $rootScope.logout = function () {
////                location.href = "./Logout";
////                console.log('logging out');
//                    $http.get('api/Logout').then(function () {});
//                    window.localStorage.clear();
//                    profileService.logout();
//                };
//                $rootScope.getRoleParams = function (rolesList, role) {
//                    var roleParam = "";
//                    if (rolesList) {
//                        var firstParam = rolesList.indexOf(role);
//                        var secondParam = rolesList.indexOf(",", firstParam);
//                        roleParam = rolesList.substring(firstParam, secondParam === -1 ? rolesList.length : secondParam).split("|")[1];
//                    }
//                    return roleParam;
//                };
//                $rootScope.testEtz = function () {
////                $rootScope.userReady2();
//                    var i = ($rootScope.profile.type_id !== undefined && $rootScope.profile.type_id !== null) ? $rootScope.profile.type_id : "";
////                console.log('i: ' + i);
//                    if (i.indexOf("[0]") > -1) {
//                        return true;
//                    }
//                    return false;
//                };
//                $rootScope.testMomoUpdate = function () {
////                $rootScope.userReady2();
//                    var i = ($rootScope.profile.type_id !== undefined && $rootScope.profile.type_id !== null) ? $rootScope.profile.type_id : "";
////                console.log('i: ' + i);
//                    if (i.indexOf("[44]") > -1) {
//                        return true;
//                    }
//                    return false;
//                };
//                $rootScope.testConsoleUpload = function () {
////                $rootScope.userReady2();
//                    var i = ($rootScope.profile.user_code !== undefined && $rootScope.profile.user_code !== null) ? $rootScope.profile.user_code : "";
////                console.log('i: ' + i);
//                    if (i.indexOf("[124]") > -1) {
//                        return true;
//                    }
//                    return false;
//                };
//                $rootScope.testTmcUpdate = function () {
////                $rootScope.userReady2();
//                    var i = ($rootScope.profile.type_id !== undefined && $rootScope.profile.type_id !== null) ? $rootScope.profile.type_id : "";
////                console.log('i: ' + i);
//                    if (i.indexOf("[64]") > -1) {
//                        return true;
//                    }
//                    return false;
//                };
//                $rootScope.testRoleFunc = function (role_id, role) {
////                $rootScope.userReady2();
//                    var i = ($rootScope.profile.user_code !== undefined && $rootScope.profile.user_code !== null) ? $rootScope.profile.user_code : "";
////                console.log('i: ' + i);
//
//                    return i.split(role_id + '|').pop().split(',')[0].split("~").includes(role);
////                return roles.includes(role);
//
//                };
//                $rootScope.testAdmin = function () {
//                    $rootScope.userReady2();
//                    if ($rootScope.testAdm()) { //|| testUserMgt()) {
//                        return true;
//                    }
//
//                    return false;
//                };
//                $rootScope.testTech = function () {
//                    $rootScope.userReady2();
//                    var i = ($rootScope.profile.type_id !== undefined && $rootScope.profile.type_id !== null) ? $rootScope.profile.type_id : "";
//                    if (i.indexOf("[1]") !== -1 || i.indexOf("[2]") !== -1 || i.indexOf("[8]") !== -1 || i.indexOf("[22]") !== -1) {
//                        return true;
//                    }
//                    return false;
//                };
//                $rootScope.testCardTrans = function () {
//                    $rootScope.userReady2();
//                    var i = ($rootScope.profile.type_id !== undefined && $rootScope.profile.type_id !== null) ? $rootScope.profile.type_id : "";
//                    if ($rootScope.testEtz() || i.indexOf("[6]") !== -1) {
//                        return true;
//                    }
//                    return false;
//                };
//                $rootScope.testAdm = function () {
//                    $rootScope.userReady2();
//                    var i = ($rootScope.profile.user_code !== undefined && $rootScope.profile.user_code !== null) ? $rootScope.profile.user_code : "";
//                    var j = $rootScope.profile.role_id;
//                    if (i.indexOf("[1]") !== -1 || i.indexOf("[2]") !== -1 || j === "1") {
//                        return true;
//                    }
//                    return false;
//                };
//                $rootScope.testUserMgt = function () {
//                    var i = $rootScope.profile.Admin;
//                    if (i.indexOf("[1]") !== -1 || i.indexOf("[2]") !== -1) {
//                        return true;
//                    }
//                    return false;
//                };
//                $rootScope.testHideFee = function () {
//
//                    var i = ($rootScope.profile.type_id !== undefined && $rootScope.profile.type_id !== null) ? $rootScope.profile.type_id : "";
//                    var j = $rootScope.profile.user_code;
//                    if (j !== 'ETZ:6WCI') {
//                        return true;
//                    }
//                    return false;
//                };
//                $rootScope.testEtzMerchantAll = function () {
//                    var j = $rootScope.profile.user_code;
//                    if (j !== 'ETZ:ALL') {
//                        return true;
//                    }
//                    return false;
//                };
//                $rootScope.testMerchantPayAll = function () {
//                    var j = $rootScope.profile.user_code;
////                console.log('rec: ' + j);
////                console.log('rec2: ' + profileService.getUserProfile());
//                    if ((j.indexOf('ETZ:ALL') > -1) || (j.indexOf('GCB:ALL') > -1) || (j.indexOf('SCB:ALL') > -1)) {
//                        return true;
//                    }
//                    return false;
//                };
//            }])
//        .directive('ngFiles', ['$parse', function ($parse) {
//
//                function fn_link(scope, element, attrs) {
//                    var onChange = $parse(attrs.ngFiles);
//                    element.on('change', function (event) {
//                        onChange(scope, {$files: event.target.files});
//                    });
//                }
//
//                return {
//                    link: fn_link
//                };
//            }])
//        .directive('onLastRepeat', function () {
//            return function (scope, element, attrs) {
//                if (scope.$last)
//                    setTimeout(function () {
//                        scope.$emit('onRepeatLast', element, attrs);
//                    }, 1);
//            };
//        })
//        .filter('subMenuFilter', function () {
//            return function (items, type) {
//                return items.filter(function (item) {
//                    return item.showOnMenu === '1' && item.menu === type;
//                });
//            };
//        })
//        .factory('profileService', function (userInfo, $rootScope, $http, $location, $q) {
//            var userProfile = userInfo;
//            var addUserProfile = function (profile, type) {
//                userProfile = profile !== "" ? (typeof profile === 'string' ? JSON.parse(profile) : profile) : "";
//                userProfileData = userProfile;
//                $rootScope.profile = userProfileData;
////                console.log('rootScope: ' + $rootScope.profile);
//
//                if ($rootScope.profile !== "") {
////                    console.log('saving: ' + type);
//                    window.localStorage.setItem("userData", $rootScope.profile);
//                    if (type === 1) {
//                        $rootScope.authenticated = true;
//                    }
//                    $rootScope.populateMenuList();
//                } else {
//                    if ($('#navbarmenu').hasClass('animated bounceIn')) {
//                        $('#navbarmenu').removeClass('animated bounceIn');
//                    }
//                    $rootScope.authenticated = false;
//                }
//
//            };
//            var getFirstLogon = function () {
//                return userProfile.first_logon === "0";
//            };
//            var isAuthenticated = function () {
//                return userProfile !== "";
//            };
//            return {
//                updateUserProfile: function () {
//                    var profileExists = (typeof userProfileData === "string" ? userProfileData.length > 0 : Object.keys(userProfileData).length > 0);
//                    console.log('profileExists: ' + profileExists);
////                    $http.get('api/UserProfile').then(
////                            function (success) {
//////                                console.log('updating profile');
////                                if (success.data) {
////                                    addUserProfile(success.data);
//////                                    console.log('checking firstlogon: ' + $rootScope.profile.first_logon);
////                                    if (getFirstLogon()) {
////                                        $location.path('/firstChangePassword');
////                                    }
////                                } else {
////                                    if ($('#navbarmenu').hasClass('animated bounceIn')) {
////                                        $('#navbarmenu').removeClass('animated bounceIn');
////                                    }
////                                    if ($rootScope.authenticated) {
////                                        $rootScope.authenticated = false;
////                                        setTimeout(function () {
////                                            $rootScope.toggleErrorNotification("Your Session Timed out");
////                                        }, 500);
////                                    }
////                                    location.href = "/auth";
////                                }
////                            });
//                },
//                reloadSessionProfile: function () {
//                    var profileExists = (typeof userProfileData === "string" ? userProfileData.length > 0 : Object.keys(userProfileData).length > 0);
//                    //console.log('profileExists: ' + profileExists + " - " + JSON.stringify(userProfileData));
//                    if ($rootScope.authenticated && window.location.hash.split('?')[0].replace("#!/", "") !== 'resetAccount') {
//                        $http.get('api/RefreshToken').then(function (resp) {
////                                console.log('updating profile');
//                            if (resp.data.code !== '00') {
////                            $rootScope.startSession();
//                                isIdle = true;
//                                if ($('#navbarmenu').hasClass('animated bounceIn')) {
//                                    $('#navbarmenu').removeClass('animated bounceIn');
//                                }
//                                if ($rootScope.authenticated) {
//                                    $rootScope.authenticated = false;
//                                    setTimeout(function () {
//                                        $rootScope.toggleErrorNotification("Your Session Timed out");
//                                    }, 500);
//                                }
//                                $location.path("/auth");
//                            } else {
//                                $rootScope.startSession();
//                            }
//                        });
//                    }
//                },
//                setUserProfile: function (profile, type) {
//                    addUserProfile(profile, type);
//                },
//                logout: function () {
//                    userProfile = "";
//                    if ($('#navbarmenu').hasClass('animated bounceIn')) {
//                        $('#navbarmenu').removeClass('animated bounceIn');
//                    }
//                    $('#navbarmenu').css('display', 'none');
//                    $rootScope.authenticated = false;
//                    window.location = './#!/auth';
////                    $location.path("/auth");
//                    window.location.reload();
//                },
//                getUserProfile: function () {
////                    console.log('fetching profile: ' + userProfile.cms_role);
//                    return userProfile;
//                },
//                isAuthenticated: function () {
////                    console.log('userProfile: ' + JSON.stringify(userProfile));
////                    isLoggedIn
//                    return (userProfile !== "" && userProfile !== "{}" && userProfile.message !== "Session Expired" && userProfile.isLoggedIn === true);
//                },
//                isFirstLogon: function () {
////                    console.log('userProfile: ');
//                    return userProfile.first_logon === "0" || userProfile.temp_password === "true";
//                },
//                authenticate: function (role, openTo, open) {
//                    console.log('role: ' + role);
////                    console.log('1');
////                    if (role !== "[2]" && role !== "[3]" && role !== "[4]" && role !== "[5]" && role !== "[6]") {
////                        console.log('data: '+ JSON.stringify(userProfile));
////                        console.log('type: '+ typeof userProfile);
//                    var userState = typeof userProfile === 'string' ? JSON.parse(userProfile) : userProfile;
////                    console.log('checking auth: ' + userState.type_id);
//                    if (userState.type_id) {
//                        console.log('checking role: ' + role);
//                        if (role === '[23]' || role === '[12]' || role === '[13]' || role === '[14]' || role === '[22]') {
//                            return true;
//                        }
//
////                            if (userState.type_id.indexOf(role) > -1 || (userState.type_id.indexOf('[1]') > -1 && openTo.indexOf('[1]') > -1)) {
////                                return true;
////                            }
//
//
//                        if ($rootScope.checkOpenTo(role, userState, openTo, open)) {
//                            return true;
//                        } else {
//                            return $q.reject("Unauthorized");
//                        }
//                    } else {
////                        return $q.reject("AuthenticationError");
////                            console.log('rest: ' + $location.path());
//                        if (!$location.path() === "/resetAccount") {
//                            $location.path("/auth");
//                        } else {
//                            return true;
//                        }
//                    }
////                    } else {
////                        return true;
////                    }
//                }
//            };
//        })
//        .factory('ajaxNonceInterceptor', function () {
//            // This interceptor is equivalent to the behavior induced by $.ajaxSetup({cache:false});
//
//            var param_start = /\?/;
//            return {
//                request: function (config) {
//                    if (config.method === 'GET' || config.method === "POST") {
//                        // Add a query parameter named '_' to the URL, with a value equal to the current timestamp
//                        config.url += (param_start.test(config.url) ? "&" : "?") + '_=' + new Date().getTime();
//                    }
//                    return config;
//                }
//            };
//        })
//        .factory('responseInterceptor', function ($q, $rootScope) {
//            return {
//                response: function (response) {
//                    // do something on success
////                    console.log('resp code: ' + JSON.stringify(response));
//
////                    if (response.headers()['content-type'].indexOf("application/json") > -1) {
//                    // Validate response, if not ok reject
////                        console.log('response msg');
//                    var header = response.headers()['content-type'];
//                    if (header && header.indexOf('application/json') > -1) {
//                        if (typeof response === 'object') {
////                            console.log('object response');
////                            console.log('r -> ' + response.data.message);
//                            if (response.data.message === 'Session Expired') {
//                                $rootScope.toggleErrorNotification(response.data.message);
//                                $rootScope.logout();
//                                return $q.reject(response);
//                            } else if (response.data.code === '01') {
//
//                                $rootScope.toggleErrorNotification(response.data.message);
//                            }
//                        }
//                    } else if (header && header.indexOf('application/pdf') > -1) {
//                        var filename = response.headers('Content-Disposition').split("filename=")[1];
//                        // console.log('filename: ' + filename);
//                        saveAs(new Blob([response.data], {type: "application/pdf"}), filename);
//                    } else if (header && header.indexOf('application/octet-stream') > -1) {
//                        var filename = response.headers('Content-Disposition').split("filename=")[1];
////                        console.log('filename: ' + filename.replace(/\"/g, ""));
//                        saveAs(new Blob([response.data], {type: "application/octet-stream"}), filename.replace(/\"/g, ""));
//                    }
////                      
//                    return response;
//                },
//                responseError: function (response) {
//                    // do something on error
////                    console.log('resp: ' + response.config.url);
//                    if (!(response.config.url.indexOf('RefreshToken') > -1)) {
//                        $rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                    }
//                    return $q.reject(response);
//                }
//            };
//        })
//        .factory('csrfInterceptor', function ($rootScope) {
//            // This interceptor is equivalent to the behavior induced by $.ajaxSetup({cache:false});
//            return {
//                request: function (config) {
////                    if (config.method === 'GET' || config.method === "POST") {
////                        // Add a query parameter named '_' to the URL, with a value equal to the current timestamp
////                        config.url += (param_start.test(config.url) ? "&" : "?") + '_=' + new Date().getTime();
////                    }
//                    if (config.method === "POST") {
//                        // Add a query parameter named '_' to the URL, with a value equal to the current timestamp
//
//                        config.headers['X-XSRF-TOKEN'] = $rootScope.csrf;
//                        config.headers['app-token'] = $rootScope.mac;
//                    }
//                    return config;
//                },
//                response: function (config) {
////                    console.log('re + ' + config.headers()['xsrf-token']);
//                    if (config.headers()['xsrf-token']) {
//                        $rootScope.csrf = config.headers()['xsrf-token'];
//                        if ($('.csrf').length > 0) {
//                            $('.csrf').val($rootScope.csrf);
//                        }
//                    }
//                    if (config.headers()['app-token']) {
//                        $rootScope.mac = config.headers()['app-token'];
//                    }
//
//                    return config;
//                }
//            };
//        })
//        .factory('zxcvbn', [function () {
//                return {
//                    score: function () {
//                        var compute = zxcvbn.apply(null, arguments);
//                        return compute && compute.score;
//                    }
//                };
//            }])
//        .directive('okPassword', ['zxcvbn', function (zxcvbn) {
//                return {
//                    // restrict to only attribute and class
//                    restrict: 'AC',
//                    // use the NgModelController
//                    require: 'ngModel',
//                    // add the NgModelController as a dependency to your link function
//                    link: function ($scope, $element, $attrs, ngModelCtrl) {
//                        $element.on('blur change keydown', function (evt) {
//                            $scope.$evalAsync(function ($scope) {
//                                // update the $scope.password with the element's value
//                                var pwd = $scope.password = $element.val();
//                                // resolve password strength score using zxcvbn service
//                                $scope.passwordStrength = pwd ? (pwd.length >= 12 && zxcvbn.score(pwd) || 0) : null;
//                                // define the validity criterion for okPassword constraint
//                                ngModelCtrl.$setValidity('okPassword', $scope.passwordStrength >= 2);
//                            });
//                        });
//                    }
//                };
//            }])
//        .controller('supportCtrl', ['$scope', 'userInfo', '$rootScope', 'profileService', '$http', '$httpParamSerializerJQLikefunction', function ($scope, userInfo, $rootScope, profileService, $http, $httpParamSerializerJQLike) {
//
//                $scope.profile = userInfo;
//                $scope.auth = {};
//                $scope.showPassword1 = "password";
//                $scope.showPassword2 = "password";
//                $scope.showPassword3 = "password";
//                $(".load-icon").removeClass("fa-check");
//                $(".load-icon").addClass("fa-cog fa-spin");
//                $scope.pwdLoader = false;
//                $scope.pwdUrl = "api/ChangePassword";
//                $scope.pwdUrl2 = "api/ChangePasswordNew";
//                $scope.executeDialogAction = function (action) {
//                    if (typeof $scope[action] === "function") {
//                        $scope[action]();
//                    }
//                };
//                $scope.fetchUserEmail = function () {
//                    setTimeout(function () {
//                        $scope.$evalAsync(function () {
////                        console.log('profile: '+ JSON.stringify(userProfileData));
//                            $scope.auth.email = userProfileData.email;
//                        });
//                    }, 2000);
//                };
//                $scope.toggleShowPassword = function (type) {
//                    if ($('.' + type).hasClass('fa-eye')) {
//                        $('.' + type).removeClass('fa-eye');
//                        $('.' + type).addClass('fa-eye-slash');
//                    } else {
//                        $('.' + type).addClass('fa-eye');
//                        $('.' + type).removeClass('fa-eye-slash');
//                    }
//                    $scope[type] = $scope[type] === 'text' ? 'password' : 'text';
//                };
//                $scope.toggleSuccess = function () {
//                    $('.circle-loader').removeClass('load-error').toggleClass('load-complete');
//                    $('.checkmark').removeClass('error').addClass('draw').toggle();
//                    setTimeout(function () {
//                        $('.circle-loader').removeClass('load-error').toggleClass('load-complete');
//                        $('.checkmark').removeClass('error').addClass('draw').toggle();
//                    }, 3500);
//                };
//                $scope.toggleError = function () {
//                    $('.circle-loader').removeClass('load-complete').toggleClass('load-error');
//                    $('.checkmark').removeClass('draw').addClass('error').toggle();
//                    setTimeout(function () {
//                        $('.circle-loader').removeClass('load-complete').toggleClass('load-error');
//                        $('.checkmark').removeClass('draw').addClass('error').toggle();
//                    }, 3500);
//                };
//                $scope.hideOverlay = function (type) {
//                    if (type === 'error') {
//                        $scope.toggleError();
//                    }
//                    if (type === 'success') {
//                        $scope.toggleSuccess();
//                    }
//                    $('.authLoading').css('display', 'none');
//                    $('.authLogin').css('display', 'block');
//                };
//                $scope.showOverlay = function () {
//                    $('.overlay').addClass('blur');
//                    $('.authLoading').css('display', 'block');
//                    $('.authLogin').css('display', 'none');
//                };
////            $scope.initChangePassword = function () {
////                console.log('email: '+ $scope.profile.email);
////                $scope.auth.email = $scope.profile.email;
////            };
//                $scope.changePassword = function () {
//                    $scope.passwordChangeMessage = "";
//                    $scope.validForm = true;
//                    if (!$scope.auth.oldpassword) {
//                        $('.oldpassword').addClass('alert-validate');
//                        setTimeout(function () {
//                            $('.oldpassword').removeClass('alert-validate');
//                        }, 2000);
//                        $scope.validForm = false;
//                    }
//                    if (!$scope.auth.newpassword) {
//                        $('.newpassword').addClass('alert-validate');
//                        setTimeout(function () {
//                            $('.newpassword').removeClass('alert-validate');
//                        }, 2000);
//                        $scope.validForm = false;
//                    }
//                    if (!$scope.auth.confpassword) {
//                        $('.confpassword').addClass('alert-validate');
//                        setTimeout(function () {
//                            $('.confpassword').removeClass('alert-validate');
//                        }, 2000);
//                        $scope.validForm = false;
//                    }
//                    var regex = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/;
//                    if (!regex.test($scope.auth.email)) {
//                        $('.email').addClass('alert-validate');
//                        setTimeout(function () {
//                            $('.email').removeClass('alert-validate');
//                        }, 2000);
//                        $scope.validForm = false;
//                    }
//                    if (!validatePassword($scope.auth.newpassword)) {
//                        $scope.validForm = false;
//                    }
//                    if ($scope.validForm) {
//                        $('#pswd_info').blur();
//                        $scope.showOverlay();
//                        $http({
//                            url: $scope.pwdUrl,
//                            method: 'POST',
//                            data: $httpParamSerializerJQLike({
//                                "oldPwd": $scope.auth.oldpassword,
//                                "newPwd": $scope.auth.newpassword,
//                                "confPwd": $scope.auth.confpassword,
//                                "email": $scope.auth.email
//                            }),
//                            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                        }).then(function success(response) {
//                            $scope.authResp = response.data;
//                            $scope.type = "";
//                            $scope.auth.oldpassword = "";
//                            $scope.auth.newpassword = "";
//                            $scope.auth.confpassword = "";
//                            if ($scope.authResp.code !== "00") {
//                                $scope.toggleError();
//                                $scope.type = "error";
//                                $scope.passwordChangeMessage = $scope.authResp.message;
//                            } else {
//                                //save user
//                                $scope.passwordChangeMessage = "Password Changed Successfully";
//                                $scope.toggleSuccess();
//                                $scope.type = "success";
//                            }
//                            setTimeout(function () {
//
//                                $scope.$apply($scope.hideOverlay($scope.type));
//                                setTimeout(function () {
//                                    $('.okPassword').blur();
//                                }, 200);
//                            }, 1500);
//                        }, function error(response) {
////                        $scope.showLoader = false; $rootScope.hideLoading();
////                        $('.reload').removeClass('fa-spin text-info');
//                            $scope.toggleError();
//                            $scope.type = "error";
//                            $scope.hideOverlay($scope.type);
//                            //$rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                        });
//                    }
//                };
//                $scope.changePasswordNew = function () {
//                    profileService.updateUserProfile();
//                    $scope.pwdLoader = true;
//                    $http({
//                        url: $scope.pwdUrl,
//                        method: 'POST',
//                        data: $httpParamSerializerJQLike({
//                            "oldPwd": $scope.oldPwd,
//                            "newPwd": $scope.newPwd,
//                            "confPwd": $scope.confPwd,
//                            "email": $scope.email
//
//                        }),
//                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                    }).then(function success(response) {
//                        var rsp = response.data;
//                        if (rsp.code === "00") {
//                            $(".load-icon").addClass("fa-check");
//                            $(".load-icon").removeClass("fa-cog fa-spin");
//                            $(".rsp").html("<span class='text-success'>Password change successful<span> <br><a href='#!auth' class='text-primary font-weight-bold'>Continue</a> ");
//                        } else {
//                            $(".load-icon").removeClass("fa-check");
//                            $(".load-icon").addClass("fa-cog fa-spin");
//                            $(".rsp").html("<span class='text-danger'>" + rsp.message + "</span>");
//                        }
//                        setTimeout(function () {
//                            $('.okPassword').blur();
//                        }, 200);
//                    }, function error(response) {
//                        $(".load-icon").removeClass("fa-check");
//                        $(".load-icon").addClass("fa-cog fa-spin");
//                        $(".rsp").html("<span class='text-danger'>An error occured. Please try again</span>");
//                    });
//                };
//                $scope.fetchUserEmail = function () {
//                    $scope.auth.email = $scope.profile.email;
//                };
//                $scope.hideFilterBox = function () {
//                    $scope.pwdLoader = false;
//                };
//            }])
//        .controller('noCtrl', function () {})
//        .controller('authCtrl', ['$scope', 'userInfo', '$rootScope', 'profileService', '$http', '$httpParamSerializerJQLikefunction', '$location', '$compile', '$routeParams', function ($scope, userInfo, $rootScope, profileService, $http, $httpParamSerializerJQLike, $location, $compile, $routeParams) {
//                $scope.showPassword = "password";
//                $scope.showPassword1 = "password";
//                $scope.showPassword2 = "password";
//                $scope.showPassword3 = "password";
//                $scope.pwdUrl = "api/ChangePassword";
//                $scope.pwdUrl2 = "api/ChangePasswordNew";
//                $scope.resetUrl = "api/ResetAccount";
//                $scope.login_state = 'LOGIN';
//                $scope.auth = {};
//                Object.keys($routeParams).forEach(function (key) {
////                console.log("key: " + key);
//                    $scope[key] = $routeParams[key];
////                console.log("value: " + $scope[key]);
//                });
//                $scope.executeDialogAction = function (action) {
//                    if (typeof $scope[action] === "function") {
//                        $scope[action]();
//                    }
//                };
//                $scope.$evalAsync(function () {
//
//                });
//                $scope.fetchUserEmail = function () {
//                    setTimeout(function () {
//                        $scope.$evalAsync(function () {
////                        console.log('profile: '+ JSON.stringify(userProfileData));
//                            $scope.auth.email = userProfileData.email;
//                        });
//                    }, 2000);
//                };
//                $scope.toggleShowPassword = function (type) {
//                    if ($('.' + type).hasClass('fa-eye')) {
//                        $('.' + type).removeClass('fa-eye');
//                        $('.' + type).addClass('fa-eye-slash');
//                    } else {
//                        $('.' + type).addClass('fa-eye');
//                        $('.' + type).removeClass('fa-eye-slash');
//                    }
//                    $scope[type] = $scope[type] === 'text' ? 'password' : 'text';
//                };
//                $scope.toggleAuthView = function (formType) {
//                    $scope.showResetForm = !$scope.showResetForm;
//                    if (formType === 'login') {
//                        $('.loginForm').css('display', 'block');
//                        $('.resetForm').css('display', 'none');
//                    } else {
//                        $('.loginForm').css('display', 'none');
//                        $('.resetForm').css('display', 'block');
//                    }
//                };
//                $scope.toggleSuccess = function () {
//                    $('.circle-loader').removeClass('load-error').toggleClass('load-complete');
//                    $('.checkmark').removeClass('error').addClass('draw').toggle();
////                if ($('.checkmark').hasClass('error')) {
////                    $('.checkmark').removeClass('error');
////                }
////                if ($('.checkmark').hasClass('draw')) {
////                    $('.checkmark').removeClass('draw');
////                }
////                $('.checkmark').addClass('draw');
//                    setTimeout(function () {
//                        $('.circle-loader').removeClass('load-error').toggleClass('load-complete');
//                        $('.checkmark').removeClass('error').addClass('draw').toggle();
////                    $('.checkmark').removeClass('error').addClass('draw').toggle();
//                    }, 3500);
//                };
//                $scope.toggleError = function () {
//                    $('.circle-loader').removeClass('load-complete').toggleClass('load-error');
//                    $('.checkmark').removeClass('draw').addClass('error').toggle();
////                if ($('.checkmark').hasClass('draw')) {
////                    $('.checkmark').removeClass('draw');
////                }
////                if ($('.checkmark').hasClass('error')) {
////                    $('.checkmark').removeClass('error');
////                }
////                $('.checkmark').addClass('error');
//
//                    setTimeout(function () {
//                        $('.circle-loader').removeClass('load-complete').toggleClass('load-error');
//                        $('.checkmark').removeClass('draw').addClass('error').toggle();
//                    }, 3500);
//                };
//                $scope.hideOverlay = function (type) {
//                    if (type === 'error') {
//                        $scope.toggleError();
//                        $('.authLoading').css('display', 'none');
//                        $('.authLogin').css('display', 'block');
//                    } else if (type === 'success') {
////                    $scope.togglerEror();
//                        $('.authLoading').css('display', 'none');
//                        $('.authLogin').css('display', 'block');
//                    } else {
////                    $scope.toggleSuccess();
////                    $('.authLoading').css('display', 'none');
////                    $('.authLogin').css('display', 'block');
//                    }
//
//                };
//                $scope.showOverlay = function () {
//                    $('.overlay').addClass('blur');
//                    $('.authLoading').css('display', 'block');
//                    $('.authLogin').css('display', 'none');
//                };
//                $scope.showOtp = function () {
////                location.href = "./Logout";
//                    console.log('showing OTP');
////                var otpHtml = '<h4 style="text-align: center">Enter One Time Password</h4>\n\
////                                <div otp-input-directive options="otpInput"></div>\n\
////                                <div style="margin-top:20px;text-align: center"> Output: {{otpInput.value}}</div>';
////                var el = angular.element(otpHtml);
////                var result = $compile(el)($scope);
////                $scope.$apply();
//                    Swal.fire({
//                        customClass: {input: 'otpBox numberOnly'},
//                        title: "Enter OTP",
////                    text: "This will " + action + " " + $scope.username,
////                    html: "This will <b>" + action + " " + $scope.username + "</b>",
//                        type: 'info',
////                    onOpen() {
////                        $('.otpBox').append(result);
////                    },
//                        text: "Check your Email for OTP",
//                        input: 'text',
////                    html: '<div class="otpBox"></div>',
//                        showCancelButton: true,
//                        confirmButtonColor: '#3085d6',
//                        cancelButtonColor: '#d33',
//                        confirmButtonText: 'Login',
//                        showLoaderOnConfirm: true,
//                        preConfirm: function preConfirm(login) {
//                            console.log('action: ' + login);
//                            console.log('calling');
//                            return $http({
//                                url: "api/ValidateOtp",
//                                method: 'POST',
//                                data: $httpParamSerializerJQLike({
//                                    "otp": login
//                                }),
//                                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                            }).then(function success(response) {
//                                console.log(response.data);
//                                if (response.data.code !== '00') {
//                                    throw new Error(response.data.message);
//                                }
//                                return response;
//                            }).catch(function (error) {
//                                Swal.showValidationMessage(error || 'Request Failed');
//                            });
//                        },
//                        allowOutsideClick: function allowOutsideClick() {
//                            return !Swal.isLoading();
//                        }
//                    }).then(function (result) {
////                    console.log('code: ' + result.value.data.code);
//                        if (result.dismiss) {
//                            $scope.$apply($scope.hideOverlay($scope.type));
//                        }
//                        if (result.value) {
//                            var resp = result.value.data.code;
//                            if (resp === '00') {
//                                $scope.loginMessage = '';
//                                $scope.extraInfo = result.value.data.message;
//                                $scope.toggleMsg();
//                            } else {
//                                console.log('herrr');
//                                Swal.showValidationMessage(result.value.data.message);
//                            }
//                            //Swal.fire({title: respTitle, text: respText, type: respType, timer: 1500});
//                        } else {
//
//                            Swal.showValidationMessage("An Error Occured");
//                        }
//                    });
//                };
//                $scope.toggleMsg = function () {
//                    $scope.toggleSuccess();
//                    if ($scope.type === "success") {
////                    profileService.updateUserProfile();
//                        $http.get('api/UserProfile').then(function (success) {
////                                console.log('updating profile');
//                            if (success.data) {
//                                console.log('data: ' + success.data.first_logon);
//                                profileService.setUserProfile(success.data, 0);
//                                if (success.data.first_logon === '0') {
//                                    setTimeout(function () {
//                                        $scope.$apply($location.path("/firstChangePassword"));
//                                        console.log('auth');
//                                    }, 1300);
//                                } else {
//                                    setTimeout(function () {
//                                        $.LoadingOverlay("show");
//                                    }, 1300);
//                                    setTimeout(function () {
//                                        $scope.$evalAsync(function () {
//                                            $scope.hideOverlay($scope.type);
//                                            if ($scope.type === "success") {
//                                                $rootScope.authenticated = true;
//                                                $('#navbarmenu').css('display', 'block');
////                                $rootScope.profile = $scope.authResp.userData;
//                                                console.log('setting profile: ' + success.data.first_logon);
//                                                profileService.setUserProfile(success.data);
////                                        console.log('router : ' + '/XPortal/#!' + success.data.firstRoute);
////                                        window.location = '/XPortal/#!' + success.data.firstRoute;
//                                                console.log("first: " + success.data.firstRoute);
//                                                $location.path(success.data.firstRoute);
////                                        $location.path($scope.authResp.nextRoute);
////                                        if()
////                                        window.location = './#!/requestLog';
//                                                setTimeout(function () {
//                                                    $scope.$apply($rootScope.toggleSuccessNotification('Welcome ' + $rootScope.profile.username));
//                                                }, 1200);
//                                            }
//                                        });
//                                    }, 1500);
//                                }
//                            }
//                        });
//                    } else {
//                        setTimeout(function () {
//                            $scope.$apply($scope.hideOverlay($scope.type));
//                        }, 1700);
//                    }
//                };
//                $scope.Login = function () {
//                    $scope.loginMessage = "";
//                    $scope.extraInfo = "";
//                    if (!$scope.auth.username) {
//                        $('.username').addClass('alert-validate');
//                        setTimeout(function () {
//                            $('.username').removeClass('alert-validate');
//                        }, 1500);
//                    }
//                    if (!$scope.auth.password) {
//                        $('.password').addClass('alert-validate');
//                        setTimeout(function () {
//                            $('.password').removeClass('alert-validate');
//                        }, 1500);
//                    }
//                    if ($scope.auth.password && $scope.auth.username) {
//                        $scope.showOverlay();
////                    $('.login_state').prop('disabled', true);
////                    $scope.login_state = "AUTHENTICATING...";
////                    $('.login_state').toggleClass("running");
//                        var payload = {
//                            username: $scope.auth.username,
//                            password: $scope.auth.password
//                        };
//                        var encPayload = $rootScope.encryptPayload(payload);
//                        $http({
//                            url: "api/Authenticator",
//                            method: 'POST',
//                            data: $httpParamSerializerJQLike({
////                            "username": $scope.auth.username,
////                            "password": $scope.auth.password,
//                                "payload": encPayload
//                            }),
//                            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                        }).then(function success(response) {
//                            console.log('resp: ' + response.data.message);
////                        $('.login_state').prop('disabled', false);
////                        $scope.$apply($scope.login_state = "LOGIN");
////                        $('.login_state').toggleClass("running");
//                            $scope.authResp = response.data;
//                            $scope.type = "";
//                            $scope.auth.username = "";
//                            $scope.auth.password = "";
//                            $scope.loginMessage = $scope.authResp.message;
//                            $scope.extraInfo = $scope.authResp.extra_info;
//                            if ($scope.authResp.message !== "Successful Login" && $scope.authResp.message !== "OTP Required") {
//                                $scope.toggleError();
//                                $scope.type = "error";
//                                //hideOverlay
//                                setTimeout(function () {
//
//                                    $scope.$apply($scope.hideOverlay($scope.type));
//                                }, 1500);
//                            } else {
//                                //save user
//
//                                $scope.type = "success";
//                                if ($scope.authResp.userData && JSON.parse($scope.authResp.userData).requires2FA) {
//                                    console.log('USER REQUIRES 2FA');
//                                    $scope.showOtp();
//                                } else {
//                                    $scope.toggleMsg();
//                                }
//                            }
//
//                        }, function error(response) {
////                        $scope.showLoader = false; $rootScope.hideLoading();
////                        $('.reload').removeClass('fa-spin text-info');
//                            $scope.toggleError();
//                            $scope.type = "error";
//                            $scope.hideOverlay($scope.type);
//                            //$rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                        });
//                    }
//                };
//                $scope.Reset = function () {
//                    if (!$scope.auth.username) {
//                        $('.username').addClass('alert-validate');
//                        setTimeout(function () {
//                            $('.username').removeClass('alert-validate');
//                        }, 1500);
//                    } else {
//                        $scope.showOverlay();
//                        $http({
//                            url: "api/PasswordReset",
//                            method: 'POST',
//                            data: $httpParamSerializerJQLike({
//                                "username": $scope.auth.username,
//                                "password": $scope.auth.password
//                            }),
//                            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                        }).then(function success(response) {
////                        console.log('resp: ' + response.data.message);
//                            $scope.authResp = response.data;
//                            $scope.type = "";
//                            $scope.auth.username = "";
//                            $scope.auth.password = "";
//                            if (!($scope.authResp.code === "00")) {
//                                $scope.toggleError();
//                                $scope.type = "error";
//                                setTimeout(function () {
//                                    $scope.$apply($scope.hideOverlay($scope.type));
//                                }, 2500);
//                            } else {
//                                $scope.type = "success";
//                                $scope.toggleSuccess();
//                                setTimeout(function () {
//                                    $('.authLoading').css('display', 'none');
//                                    $('.authLogin').css('display', 'block');
//                                }, 2500);
//                            }
//                            $scope.loginMessage = $scope.authResp.message;
//                            //hideOverlay
//
//
//                        }, function error(response) {
////                       $scope.toggleError();
//                            $scope.type = "error";
//                            $scope.hideOverlay($scope.type);
//                            //$rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                        });
//                    }
//                };
//                $scope.changePassword = function () {
//                    $scope.passwordChangeMessage = "";
//                    $scope.validForm = true;
//                    if (!$scope.auth.oldpassword) {
//                        $('.oldpassword').addClass('alert-validate');
//                        setTimeout(function () {
//                            $('.oldpassword').removeClass('alert-validate');
//                        }, 2000);
//                        $scope.validForm = false;
//                    }
//                    if (!$scope.auth.newpassword) {
//                        $('.newpassword').addClass('alert-validate');
//                        setTimeout(function () {
//                            $('.newpassword').removeClass('alert-validate');
//                        }, 2000);
//                        $scope.validForm = false;
//                    }
//                    if (!$scope.auth.confpassword) {
//                        $('.confpassword').addClass('alert-validate');
//                        setTimeout(function () {
//                            $('.confpassword').removeClass('alert-validate');
//                        }, 2000);
//                        $scope.validForm = false;
//                    }
//                    if ($scope.auth.confpassword !== $scope.auth.newpassword) {
//                        $('#password-match').show();
//                        setTimeout(function () {
//                            $('#password-match').hide();
//                        }, 3500);
//                        $scope.validForm = false;
//                    }
//                    var regex = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/;
//                    if (!regex.test($scope.auth.email)) {
//                        $('.email').addClass('alert-validate');
//                        setTimeout(function () {
//                            $('.email').removeClass('alert-validate');
//                        }, 2000);
//                        $scope.validForm = false;
//                    }
//                    if (!validatePassword($scope.auth.newpassword)) {
//                        $scope.validForm = false;
//                    }
//                    if ($scope.validForm) {
//                        $scope.showOverlay();
//                        $http({
//                            url: $scope.pwdUrl,
//                            method: 'POST',
//                            data: $httpParamSerializerJQLike({
//                                "oldPwd": $scope.auth.oldpassword,
//                                "newPwd": $scope.auth.newpassword,
//                                "confPwd": $scope.auth.confpassword,
//                                "email": $scope.auth.email
//                            }),
//                            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                        }).then(function success(response) {
//                            $scope.authResp = response.data;
//                            $scope.type = "";
//                            $scope.auth.oldpassword = "";
//                            $scope.auth.newpassword = "";
//                            $scope.auth.confpassword = "";
//                            if ($scope.authResp.code !== "00") {
//                                $scope.toggleError();
//                                $scope.type = "error";
//                                $scope.passwordChangeMessage = $scope.authResp.message;
//                            } else {
//                                //save user
//                                $scope.passwordChangeMessage = "Password Changed Successfully";
//                                $scope.toggleSuccess();
//                                $scope.type = "success";
//                                setTimeout(function () {
////                            location.href = "./Logout";
//                                    $rootScope.$evalAsync(function () {
//                                        $rootScope.logout();
//                                    });
//                                    window.localStorage.clear();
//                                }, 1300);
//                            }
//                            setTimeout(function () {
//                                $scope.$apply($scope.hideOverlay($scope.type));
//                                setTimeout(function () {
//                                    $('.okPassword').blur();
//                                }, 200);
//                            }, 1000);
////                        if ($scope.authResp.code === "00") {
////                            setTimeout(function () {
//////                            location.href = "./Logout";
////                                $rootScope.$evalAsync(function () {
////                                    $rootScope.logout();
////                                });
////                                window.localStorage.clear();
////                            }, 1300);
////                        }
//                        }, function error(response) {
////                        $scope.showLoader = false; $rootScope.hideLoading();
////                        $('.reload').removeClass('fa-spin text-info');
//                            $scope.toggleError();
//                            $scope.type = "error";
//                            $scope.hideOverlay($scope.type);
//                            //$rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                        });
//                    }
//                };
//                $scope.decodeToken = function (token) {
//
//                    return atob(token);
//                };
//                $scope.checkTokenValidity = function () {
//                    $scope.showOverlay();
//                    $scope.username = "";
//                    $scope.passwordChangeMessage = "Validating Reset Link";
//                    setTimeout(function () {
//                        $http({
//                            url: $scope.resetUrl,
//                            method: 'POST',
//                            data: $httpParamSerializerJQLike({
//                                "token": $scope.token,
//                                "action": "checkTokenValidity"
//                            }),
//                            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                        }).then(function success(response) {
//                            $scope.passwordChangeMessage = "Validating Reset Link";
//                            $scope.tokenResp = response.data;
//                            if ($scope.tokenResp.code !== "00") {
//                                $scope.toggleError();
//                                $scope.type = "error";
//                                $scope.passwordChangeMessage = $scope.tokenResp.message;
//                                $scope.resetUser = "";
//                                setTimeout(function () {
////                            location.href = "./Logout";
//                                    $rootScope.logout();
//                                    window.localStorage.clear();
//                                }, 1000);
//                            } else {
//                                //save user
////                        $scope.passwordChangeMessage = $scope.authResp.message;
////                        $scope.toggleSuccess();
////                        $scope.type = "success";
//                                userProfileData.username = $scope.tokenResp.username;
//                                userProfileData.firstname = $scope.tokenResp.firstname;
//                                userProfileData.lastname = $scope.tokenResp.lastname;
////                        setTimeout(function () {
//                                $('.authLoading').css('display', 'none');
////                        $scope.toggleMsg();
//                                $('.authLogin').css('display', 'block');
//                            }
//
//                        }, function error(response) {
//                            $scope.toggleError();
//                            $scope.type = "error";
//                            $scope.hideOverlay($scope.type);
////                    $('.authLoading').css('display', 'none');
//
//                        });
//                    }, 1000);
//                };
//                $scope.ResetAccount = function () {
//                    $scope.passwordChangeMessage = "";
//                    $scope.validForm = true;
//                    if (!$scope.auth.newpassword) {
//                        $('.newpassword').addClass('alert-validate');
//                        setTimeout(function () {
//                            $('.newpassword').removeClass('alert-validate');
//                        }, 2000);
//                        $scope.validForm = false;
//                    }
//                    if (!$scope.auth.confpassword) {
//                        $('.confpassword').addClass('alert-validate');
//                        setTimeout(function () {
//                            $('.confpassword').removeClass('alert-validate');
//                        }, 2000);
//                        $scope.validForm = false;
//                    }
//
//                    if (!validatePassword($scope.auth.newpassword)) {
//                        $scope.validForm = false;
//                    }
//                    if ($scope.validForm) {
//                        $('#pswd_info').blur();
//                        $scope.showOverlay();
//                        $http({
//                            url: $scope.resetUrl,
//                            method: 'POST',
//                            data: $httpParamSerializerJQLike({
//                                "newPwd": $scope.auth.newpassword,
//                                "confPwd": $scope.auth.confpassword,
//                                "token": $scope.token,
//                                "action": "resetAccount"
//                            }),
//                            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                        }).then(function success(response) {
//                            $scope.authResp = response.data;
//                            $scope.type = "";
//                            $scope.auth.oldpassword = "";
//                            $scope.auth.newpassword = "";
//                            $scope.auth.confpassword = "";
//                            if ($scope.authResp.code !== "00") {
//                                $scope.toggleError();
//                                $scope.type = "error";
//                                $scope.passwordChangeMessage = $scope.authResp.message;
//                            } else {
//                                //save user
//                                $scope.passwordChangeMessage = $scope.authResp.message;
//                                $scope.toggleSuccess();
//                                $scope.type = "success";
//                            }
//                            setTimeout(function () {
//                                $scope.$apply($scope.hideOverlay($scope.type));
//                                setTimeout(function () {
//                                    $('.okPassword').blur();
//                                }, 200);
//                            }, 1000);
//                            if ($scope.authResp.code === "00") {
//                                setTimeout(function () {
////                            location.href = "./Logout";
//                                    $rootScope.logout();
//                                    window.localStorage.clear();
//                                }, 1300);
//                            }
//                        }, function error(response) {
////                        $scope.showLoader = false; $rootScope.hideLoading();
////                        $('.reload').removeClass('fa-spin text-info');
//                            $scope.toggleError();
//                            $scope.type = "error";
//                            $scope.hideOverlay($scope.type);
//                            //$rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                        });
//                    }
//                };
//            }])
//        .controller('fgTransactionsCtrl', ['$scope', 'Idle', 'Keepalive', 'userInfo', 'profileService', '$rootScope', '$parse', '$http', '$httpParamSerializerJQLike', '$location', '$timeout', '$compile', 'Upload', '$q', '$route', '$routeParams', function ($scope, Idle, Keepalive, userInfo, profileService, $rootScope, $parse, $http, $httpParamSerializerJQLike, $location, $timeout, $compile, Upload, $q, $route, $routeParams) {
////    $scope.profile = "";
////    $scope.TMCBankNodeList ="";
//                Object.keys($routeParams).forEach(function (key) {
////                console.log("key: " + key);
//                    $scope[key] = $routeParams[key];
////                console.log("value: " + $scope[key]);
//                });
//                $(function () {
//                    var observer = new MutationObserver(function (mutations) {
//                        $rootScope.$evalAsync(function () {
//                            //console.log('act: ');
//                            $rootScope.updateRecordsTable();
//                        });
//                    });
//                    var target = document.querySelector('.sticky-table-header');
//                    if (target) {
//                        observer.observe(target, {
//                            attributes: true
//                        });
//                    }
//                });
//                $scope.OpenInNewTabWinBrowser = function (url) {
//                    url = window.location.origin + window.location.pathname + "#!/" + url;
//                    var win = window.open(url, '_blank');
//                    win.focus();
//                };
////            Idle.watch();
//                if ($('.csrf').length < 1) {
//                    $("<input>").attr({
//                        name: "csrf",
//                        class: "csrf",
//                        type: "hidden",
//                        value: $rootScope.csrf
//                    }).appendTo("form");
////                console.log('adding');
//                }
//
////            var showAlert = function (title, timer, isReload) {
//////                Swal.fire({
//////                    title: title,
//////                    html: '<br/>You will be logged out automatically in <strong class="swal-timer-count">' + timer / 1000 + '</strong> seconds...',
//////                    type: 'warning',
//////                    timer: 10000,
//////                    allowEscapeKey: true,
//////                    showConfirmButton: false
//////                }, function () {
//////                    Swal.close();
//////                    if (false)
//////                        location.reload(true);
//////                });
//////                var e = $(".swal2-content").find(".swal-timer-count");
//////                var n = +e.text();
//////                setInterval(function () {
//////                    n > 1 && e.text(--n);
//////                }, 1000);
////
////
////                Swal.fire({
////                    title: 'Inactivity Detected!',
////                    html: '<br/>You will be automatically logged out in <strong></strong> seconds.<br/><br/>',
////                    timer: 10000,
////                    type: 'warning',
////                    allowEscapeKey: true,
////                    showConfirmButton: false,
////                    onOpen: function () {
////                        const content = Swal.getContent();
////                        const $ = content.querySelector.bind(content);
////                        timerInterval = setInterval(() => {
////                            Swal.getContent().querySelector('strong')
////                                    .textContent = (Swal.getTimerLeft() / 1000)
////                                    .toFixed(0);
////                        }, 100);
////                    },
////                    onClose: function () {
////                        clearInterval(timerInterval);
//////                        location.reload(true);
////                    }
////                });
////            };
//
//                function closeModals() {
//                    if ($scope.warning) {
//                        $scope.warning.close();
//                        $scope.warning = null;
//                    }
//
//                    if ($scope.timedout) {
//                        $scope.timedout.close();
//                        $scope.timedout = null;
//                    }
//                }
//
////            $scope.$on('IdleStart', function () {
////                closeModals();
////                console.log('idle detected');
////                showAlert("Inactivity Detected");
////            });
////            $scope.$on('IdleEnd', function () {
////                closeModals();
////                console.log('welcome back');
////                profileService.reloadSessionProfile();
////                Swal.close();
////            });
////            $scope.$on('IdleTimeout', function () {
////                closeModals();
//////                Swal.fire({title: "", text: respText, type: respType});
////                console.log('logging out');
////                $rootScope.logout();
////            });
////            $scope.$on('IdleWarn', function () {
////                closeModals();
//////                Swal.fire({title: "", text: respText, type: respType});
//////                console.log('warnings');
////            });
////            var dynamicGraph = new DynamicGraph();
//                $scope.menuList = "";
//                $scope.onInit = function () {
//                    $scope.profile = profileService.getUserProfile();
//                };
//                $scope.onInit();
////            $scope.profile = $rootScope.profile;
//
//                $(document).ready(function () {
//
//
//                    $(document).click(function (event) {
//                        $(event.target).closest(".navbar").length || $(".navbar-collapse.show").length && $(".navbar-collapse.show").collapse("hide");
//                        if (!event.target.closest(".float-btn-group"))
//                            $('.float-btn-group').removeClass("open");
//                    });
////                $(".dropdown-item").click(function (event) {
////                    console.log('tes');
////                    $("#navbarDropdown").dropdown("toggle");
////                });
////                $('[data-toggle="tooltip"]').tooltip();
////                $('[data-toggle="popover"]').popover();
//                    $('#inpt_search').mouseover(function () {
//                        //gets the current placeholder
//                        this.holder = $(this).attr('placeholder');
//                        $(this).attr('placeholder', 'Enter Reference');
//                    });
//                    $('#inpt_search').mouseout(function () {
//                        $(this).attr('placeholder', ''); //sets it back to the initial value
//                    });
//                    $("#inpt_search").on('focus', function () {
//                        $(this).parent('label').addClass('active');
//                    });
//                    $("#inpt_search").on('blur', function () {
//                        if ($(this).val().length === 0)
//                            $(this).parent('label').removeClass('active');
//                    });
//                    function generatePDF() {
//                        var documentDefinition = {
//                            content:
//                                    [
//                                        {
//                                            text: 'Etranzact Ghana', style: 'mainheader'
//                                        },
//                                        {
//                                            table:
//                                                    {
//                                                        headerRows: 1,
//                                                        widths: ['*', '*', '*', '*'],
//                                                        body: [
//                                                            [
//                                                                {text: 'Header 1', style: 'tableHeader'},
//                                                                {text: 'Header 2', style: 'tableHeader'},
//                                                                {text: 'Header 3', style: 'tableHeader'}
//                                                            ],
//                                                            [
//                                                                {text: 'Hello'},
//                                                                {text: 'I'},
//                                                                {text: 'am'}
//                                                            ],
//                                                            [
//                                                                {text: 'a'},
//                                                                {text: 'table'},
//                                                                {text: '.'}
//                                                            ]
//                                                        ]
//                                                    }
//                                        }
//                                    ],
//                            styles:
//                                    {mainheader: {
//                                            fontSize: 20,
//                                            bold: true,
//                                            margin: [0, 10, 0, 10],
//                                            alignment: 'left'
//                                        },
//                                        header:
//                                                {
//                                                    fontSize: 18,
//                                                    bold: true,
//                                                    margin: [0, 10, 0, 10],
//                                                    alignment: 'center'
//                                                },
//                                        tableHeader:
//                                                {
//                                                    fillColor: '#4CAF50',
//                                                    color: 'white'
//                                                }
//                                    }
//                        };
//                        pdfMake.createPdf(documentDefinition).download('testdoc.pdf');
//                    }
//
//                    function KeyPress(e) {
//                        var evtobj = window.event ? event : e;
////            if (evtobj.keyCode === 90 && evtobj.ctrlKey && evtobj.shiftKey) {
//////                $scope.goBack();
////                alert("HEY");
////            }
//                        if (evtobj.keyCode === 76 && evtobj.ctrlKey && evtobj.shiftKey) {
//                            $rootScope.logout();
//                        }
//                        if (evtobj.keyCode === 90 && evtobj.ctrlKey && evtobj.shiftKey) {
////                alert("hello");
//                            $http.get('https://api.freebinchecker.com/bin/464898').then(
//                                    function (response) {
//                                        console.log(response.data);
//                                    });
//                        }
//
//                    }
//
//
//
//                    document.onkeydown = KeyPress;
//                });
//                $scope.trimText = function (text) {
//
//                    if (text) {
//                        if (text.length > 36) {
//                            return (text.substring(0, 35)) + " ...";
//                        }
//                    }
//                    return text;
//                };
//                $scope.changeUserStatus = function (index, action) {
//                    $scope.hideFilterBox();
//                    $scope.username = $scope.searchUserProfile[0].username;
//                    $scope.action = action;
//                    $scope.userId = $scope.searchUserProfile[0].user_id;
//                    Swal.fire({
//                        title: "Are you sure ?",
////                    text: "This will " + action + " " + $scope.username,
//                        html: "This will <b>" + action + " " + $scope.username + "</b>",
//                        type: 'warning',
//                        showCancelButton: true,
//                        confirmButtonColor: '#3085d6',
//                        cancelButtonColor: '#d33',
//                        confirmButtonText: 'Yes, ' + action + ' user!',
//                        showLoaderOnConfirm: true,
//                        preConfirm: function preConfirm(login) {
//                            console.log('action: ' + action);
//                            return $http({
//                                url: $scope.adminProxy,
//                                method: 'POST',
//                                data: $httpParamSerializerJQLike({
//                                    "userId": $scope.userId,
//                                    "status": $scope.action,
//                                    "service": "toggleStatus"
//                                }),
//                                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                            }).then(function success(response) {
////                            console.log(response.data);
//                                return response;
//                            }).catch(function (error) {
//                                Swal.showValidationMessage("Request failed ");
//                            });
//                        },
//                        allowOutsideClick: function allowOutsideClick() {
//                            return !Swal.isLoading();
//                        }
//                    }).then(function (result) {
////                    console.log('USer: ' + JSON.stringify(result));
//                        //do asyc
//                        if (result.value) {
//                            var resp = result.value.data.data;
////                        console.log('before: ' + JSON.stringify($scope.searchUserProfile[0]));
////                        console.log('action: ' + $scope.action);
//                            $scope.searchUserProfile[0].status_id = (resp === 'SUCCESS' ? ($scope.action === 'activate' ? 1 : 2) : $scope.searchUserProfile[0].status_id);
//                            var respText = (resp === 'SUCCESS' ? $scope.searchUserProfile[0].username + ' has been ' + action + 'd.' : (resp === 'FAILED' ? 'Failed to ' + action + ' ' + $scope.searchUserProfile[0].username + '.' : resp));
//                            var respType = (resp === 'SUCCESS' ? 'success' : 'error');
//                            var respTitle = (resp === 'SUCCESS' ? action + 'd!' : 'Failed');
////                        console.log('after: ' + JSON.stringify($scope.searchUserProfile[0]));
//                            if (resp === 'SUCCESS') {
//                                $('#user' + index).html(($scope.action === 'activate' ? 'active' : 'inactive'));
//                                $scope.filtereduserProfiles[$scope.userIndex].status_id = ($scope.action === 'activate') ? '1' : '2';
//                            }
//                            Swal.fire({title: respTitle, text: respText, type: respType, timer: 1500});
//                        }
//                    });
//                };
//                $scope.resetUserPassword = function (index) {
//                    $scope.hideFilterBox();
//                    $scope.username = $scope.searchUserProfile[0].username;
////                $scope.action = action;
//                    $scope.userId = $scope.searchUserProfile[0].user_id;
//                    Swal.fire({
//                        title: "Are you sure ?",
////                    text: "This will " + action + " " + $scope.username,
//                        html: "This will <b>reset " + $scope.username + "'s password</b>",
//                        type: 'warning',
//                        showCancelButton: true,
//                        confirmButtonColor: '#3085d6',
//                        cancelButtonColor: '#d33',
//                        confirmButtonText: 'Yes, Reset Password!',
//                        showLoaderOnConfirm: true,
//                        preConfirm: function preConfirm(login) {
////                        console.log('action: ' + action);
//                            return $http({
//                                url: $scope.adminProxy,
//                                method: 'POST',
//                                data: $httpParamSerializerJQLike({
//                                    "userId": $scope.userId,
//                                    "service": "resetPassword"
//                                }),
//                                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                            }).then(function success(response) {
//                                console.log(response.data);
//                                return response;
//                            }).catch(function (error) {
//                                Swal.showValidationMessage("Request failed ");
//                            });
//                        },
//                        allowOutsideClick: function allowOutsideClick() {
//                            return !Swal.isLoading();
//                        }
//                    }).then(function (result) {
//                        console.log('USer: ' + JSON.stringify(result));
//                        //do asyc
//                        if (result.value) {
//                            var resp = result.value.data;
//                            console.log('before: ' + JSON.stringify(resp));
////                        console.log('action: ' + $scope.action);
////                        $scope.searchUserProfile[0].status_id = (resp === 'SUCCESS' ? ($scope.action === 'activate' ? 1 : 2) : $scope.searchUserProfile[0].status_id);
////                    $scope.searchUserProfile[0].username    
//                            var respText = (resp.code === '00' ? resp.data : (resp === 'FAILED' ? 'Failed to reset ' + $scope.searchUserProfile[0].username + '\'s password.' : resp));
//                            var respType = (resp.code === '00' ? 'success' : 'error');
//                            var respTitle = (resp.code === '00' ? 'Password Reset Success' : 'Password Reset Failed');
//                            Swal.fire({title: respTitle, text: respText, type: respType, timer: 1500});
//                        }
//                    });
//                };
////            $scope.initMenu();
//                $scope.usingFlash = FileAPI && FileAPI.upload !== null;
//                //Upload.setDefaults({ngfKeep: true, ngfPattern:'image/*'});
//                $scope.changeAngularVersion = function () {
//                    window.location.hash = $scope.angularVersion;
//                    window.location.reload(true);
//                };
//                $scope.angularVersion = window.location.hash.length > 1 ? (window.location.hash.indexOf('/') === 1 ?
//                        window.location.hash.substring(2) : window.location.hash.substring(1)) : '1.2.24';
//                $scope.invalidFiles = [];
//                // make invalidFiles array for not multiple to be able to be used in ng-repeat in the ui
//                $scope.$watch('invalidFiles', function (invalidFiles) {
//                    if (invalidFiles !== null && !angular.isArray(invalidFiles)) {
//                        $timeout(function () {
//                            $scope.invalidFiles = [invalidFiles];
//                        });
//                    }
//                });
//                function uploadUsing$http(file, type, url) {
//                    $scope.UploadOvaData(file, type, url);
//                }
//
//                $scope.uploadPic = function (file) {
//                    $scope.formUpload = true;
//                    $scope.validateExcelFile(file, 'upload', 1);
//                };
//                $scope.checkKycFile = function (file) {
//                    $scope.validateKYCFile(file, 'upload', 1);
//                };
//                $scope.verifyOvaData = function (file, type) {
////        alert('verifyova');
//                    $scope.hideFilterBox();
//                    $scope.validateExcelFile(file, 'verify', type);
//                };
//                $scope.recordsExists = function () {
//                    $scope.hideFilterBox();
//                    $scope.showLoader = true;
//                    $rootScope.showLoading();
//                    if ($scope.pageNumber < 1)
//                        $scope.pageNumber = 1;
//                    if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                        $scope.pageNumber = $scope.fgRecordsLastPage;
//                    $scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage);
//                    $scope.end = parseInt($scope.begin + $scope.rowsPerPage);
//                    $scope.filteredOvaRecords = ($scope.uploadResult.ovaDataRecordExist).slice($scope.begin, $scope.end);
//                    $scope.getRecordsDetailsTotalCount();
//                    console.log('DATA: ' + $scope.filteredOvaRecords);
//                    $scope.showLoader = false;
//                    $rootScope.hideLoading();
//                };
//                $scope.recordsInserted = function () {
////        if ($scope.uploadResult.ovaDataRecordInserted.length > 0) {
//                    $scope.hideFilterBox();
//                    $scope.showLoader = true;
//                    $rootScope.showLoading();
//                    if ($scope.pageNumber < 1)
//                        $scope.pageNumber = 1;
//                    if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                        $scope.pageNumber = $scope.fgRecordsLastPage;
//                    $scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage);
//                    $scope.end = parseInt($scope.begin + $scope.rowsPerPage);
//                    $scope.filteredOvaRecords = ($scope.uploadResult.ovaDataRecordInserted).slice($scope.begin, $scope.end);
//                    $scope.getRecordsDetailsTotalCount();
//                    $scope.showLoader = false;
//                    $rootScope.hideLoading();
////        }
//                };
//                $scope.UploadOvaData = function (file, type, urlType) {
////        console.log('Got');
//                    var formdata = new FormData();
//                    $scope.uploadResult = "";
//                    $scope.showProgressBar = false;
//                    $scope.showProgressBarError = false;
//                    formdata.append("file", file);
//                    $http.get($scope.ovaUploadProxy).then(
//                            function (response) {
//                                $scope.ovaUploadSettings = response.data;
//                                if ($scope.ovaUploadSettings.response === 'success') {
//                                    if (file.size <= $scope.ovaUploadSettings.ovaUploadMaxSize * 1024000) {
//                                        if (type === 'verify') {
////                                alert('VERIFY');
//                                            if ($scope.pageNumber < 1)
//                                                $scope.pageNumber = 1;
//                                            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                                                $scope.pageNumber = $scope.fgRecordsLastPage;
////                                console.log('DATA: ' + $scope.pageNumber + ' : ' + $scope.fgRecordsLastPage);
//                                            $scope.showLoader = true;
//                                            $rootScope.showLoading();
//                                            file.upload = Upload.http({
//                                                url: $scope.ovaUploadSettings['ovaVerifyUrl' + urlType],
//                                                method: 'POST',
//                                                headers: {
//                                                    'Content-Type': undefined
//                                                },
//                                                data: formdata
//                                            });
//                                            file.upload.then(function (response) {
//                                                $scope.showLoader = false;
//                                                $rootScope.hideLoading();
//                                                if (urlType === 1) {
//                                                    $('#verify-btn').prop("disabled", false);
//                                                    $('#verify-btn').html("VERIFY ER");
//                                                } else {
//                                                    $('#verify-btn2').prop("disabled", false);
//                                                    $('#verify-btn2').html("VERIFY NER");
//                                                }
//                                                file.result = response.data;
//                                                $scope.verifyResult = response.data;
//                                                $scope.showLoader = false;
//                                                $rootScope.hideLoading();
//                                                $scope.getOvaTotalCount();
//                                                $scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage);
//                                                $scope.end = parseInt($scope.begin + $scope.rowsPerPage);
//                                                $scope.filteredOvaRecords = $scope.verifyResult.slice($scope.begin, $scope.end);
//                                                console.log('DATA: ' + $scope.filteredOvaRecords);
//                                            }, function (response) {
//                                                if (urlType === 1) {
//                                                    $('#verify-btn').prop("disabled", false);
//                                                    $('#verify-btn').html("VERIFY ER");
//                                                } else {
//                                                    $('#verify-btn2').prop("disabled", false);
//                                                    $('#verify-btn2').html("VERIFY NER");
//                                                }
//                                                $scope.showLoader = false;
//                                                $rootScope.hideLoading();
//                                                alert('An Error Occured: ' + response.status);
//                                            });
//                                            file.upload.progress(function (evt) {
//                                                file.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
//                                            });
//                                        } else {
////                                alert('Uploading');
//                                            $('#upload-btn').html("<i class='fa fa-spinner fa-spin btn-loader'></i> Uploading...");
////                                alert('Sending: ' + $scope.ovaUploadSettings['ovaUploadUrl' + urlType]);
//                                            file.upload = Upload.http({
//                                                url: $scope.ovaUploadSettings['ovaUploadUrl' + urlType],
//                                                method: 'POST',
//                                                headers: {
//                                                    'Content-Type': undefined
//                                                },
//                                                data: formdata
//                                            });
//                                            file.upload.then(function (response) {
//                                                file.result = response.data;
//                                                console.log('RESPONSE: ' + JSON.stringify(response.data));
//                                                $scope.uploadResult = response.data;
//                                                console.log("RESP: " + $scope.uploadResult.status);
//                                                $scope.showLoader = false;
//                                                $rootScope.hideLoading();
//                                                $('#upload-btn').prop("disabled", false);
//                                                $('#upload-btn').html("Upload");
//                                                if ($scope.uploadResult.status) {
//                                                    $scope.showProgressBar = true;
//                                                } else {
//                                                    $scope.showProgressBarError = true;
//                                                }
//
//                                            }, function (response) {
//                                                $('#upload-btn').prop("disabled", false);
//                                                $('#upload-btn').html("Upload");
//                                                alert('An Error Occured: ' + response.status);
//                                            });
//                                            file.upload.progress(function (evt) {
//                                                file.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
//                                            });
//                                        }
//                                    } else {
//                                        alert("File is too large. Limit is " + $scope.ovaUploadSettings.ovaUploadMaxSize + "MB");
//                                    }
//                                } else {
//                                    alert('Could not Load OvaUpload Settings');
//                                }
//                                return true;
//                            }
//                    );
//                };
//                $scope.UploadKycData = function (file, type, urlType) {
////        console.log('Got');
//                    var formdata = new FormData();
//                    $scope.uploadResult = "";
//                    $scope.showProgressBar = false;
//                    $scope.showProgressBarError = false;
//                    formdata.append("file", file);
//                    $http.get($scope.kycUploadProxy).then(
//                            function (response) {
//                                $scope.kycUploadSettings = response.data;
//                                if ($scope.kycUploadSettings.response === 'success') {
//                                    if (file.size <= $scope.kycUploadSettings.UploadMaxSize * 1024000) {
//
////                                alert('Uploading');
//                                        $('#upload-btn').html("<i class='fa fa-spinner fa-spin btn-loader'></i> Uploading...");
////                                alert('Sending: ' + $scope.ovaUploadSettings['ovaUploadUrl' + urlType]);
//                                        file.upload = Upload.http({
//                                            url: $scope.kycUploadSettings['ovaUploadUrl' + urlType],
//                                            method: 'POST',
//                                            headers: {
//                                                'Content-Type': undefined
//                                            },
//                                            data: formdata
//                                        });
//                                        file.upload.then(function (response) {
//                                            file.result = response.data;
//                                            console.log('RESPONSE: ' + JSON.stringify(response.data));
//                                            $scope.uploadResult = response.data;
//                                            console.log("RESP: " + $scope.uploadResult.status);
//                                            $scope.showLoader = false;
//                                            $rootScope.hideLoading();
//                                            $('#upload-btn').prop("disabled", false);
//                                            $('#upload-btn').html("Upload");
//                                            if ($scope.uploadResult.status) {
//                                                $scope.showProgressBar = true;
//                                            } else {
//                                                $scope.showProgressBarError = true;
//                                            }
//
//                                        }, function (response) {
//                                            $('#upload-btn').prop("disabled", false);
//                                            $('#upload-btn').html("Upload");
//                                            alert('An Error Occured: ' + response.status);
//                                        });
//                                        file.upload.progress(function (evt) {
//                                            file.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
//                                        });
//                                    } else {
//                                        alert("File is too large. Limit is " + $scope.kycUploadSettings.UploadMaxSize + "MB");
//                                    }
//                                } else {
//                                    alert('Could not Load KycUpload Settings');
//                                }
//                                return true;
//                            }
//                    );
//                };
//                $scope.validateExcelFile = function (file, type, recordType) {
//                    if (type === 'verify') {
//                        if (recordType === 1) {
//                            $('#verify-btn').prop("disabled", true);
//                            $('#verify-btn').html("<i class='fa fa-spinner fa-spin btn-loader'></i> Validating");
//                        } else {
//                            $('#verify-btn2').prop("disabled", true);
//                            $('#verify-btn2').html("<i class='fa fa-spinner fa-spin btn-loader'></i> Validating");
//                        }
//                    } else {
//                        $('#upload-btn').prop("disabled", true);
//                        $('#upload-btn').html("<i class='fa fa-spinner fa-spin btn-loader'></i> Validating");
//                    }
//
//                    var xlsxflag = false; /*Flag for checking whether excel is .xls format or .xlsx format*/
//                    if ($("#excelfile").val().toLowerCase().indexOf(".xlsx") > 0) {
////                console.log('2');
//                        xlsxflag = true;
//                    }
//                    /*Checks whether the browser supports HTML5*/
//                    if (typeof (FileReader) !== "undefined") {
//
//                        var reader = new FileReader();
//                        reader.onload = function (e) {
//                            var data = e.target.result;
//                            /*Converts the excel data in to object*/
//                            if (xlsxflag) {
//                                var binary = "";
//                                var bytes = new Uint8Array(data);
//                                var length = bytes.byteLength;
//                                for (var i = 0; i < length; i++) {
//                                    binary += String.fromCharCode(bytes[i]);
//                                }
//                                var workbook = XLSX.read(binary, {type: 'binary'});
//                            }
//                            /*Gets all the sheetnames of excel in to a variable*/
//                            var sheet_name_list = workbook.SheetNames;
//                            var cnt = 0; /*This is used for restricting the script to consider only first sheet of excel*/
//                            sheet_name_list.forEach(function (y) { /*Iterate through all sheets*/
//                                /*Convert the cell value to Json*/
//                                if (xlsxflag) {
//                                    var exceljson = XLSX.utils.sheet_to_json(workbook.Sheets[y]);
//                                }
//
//                                var columnSet = [];
//                                if (exceljson.length > 0 && cnt === 0) {
////                            console.log('Data: t');
//                                    var rowHash = exceljson[1];
//                                    for (var key in rowHash) {
//
//                                        if (rowHash.hasOwnProperty(key)) {
//                                            if ($.inArray(key, columnSet) === -1) {/*Adding each unique column names to a variable array*/
//                                                columnSet.push(key);
//                                            }
//                                        }
//                                    }
////                                console.log('2: ' + columnSet);
//                                    var header = 'reference,clientid';
//                                    if ($.trim(columnSet).includes(header)) {
//
//                                        console.log('Valid excel file');
//                                        if (file !== null) {
//                                            $scope.uploadFile(file, type, recordType);
//                                        } else {
//                                            alert('could not check File');
//                                        }
//                                    } else {
//
//                                        alert("invalid excel file");
//                                    }
//                                    cnt++;
//                                }
//                            });
//                        };
//                        if (xlsxflag) {/*If excel file is .xlsx extension than creates a Array Buffer from excel*/
//                            reader.readAsArrayBuffer($("#excelfile")[1].files[1]);
//                        }
//
//                    } else {
//                        $('#upload-btn').prop("disabled", false);
//                        $('#upload-btn').html("Upload");
//                        alert("Sorry! Your browser does not support HTML5!");
//                    }
//                };
//                $scope.validateKYCFile = function (file, type, recordType) {
//                    if (type === 'verify') {
//                        if (recordType === 1) {
//                            $('#verify-btn').prop("disabled", true);
//                            $('#verify-btn').html("<i class='fa fa-spinner fa-spin btn-loader'></i> Validating");
//                        } else {
//                            $('#verify-btn2').prop("disabled", true);
//                            $('#verify-btn2').html("<i class='fa fa-spinner fa-spin btn-loader'></i> Validating");
//                        }
//                    } else {
//                        $('#upload-btn').prop("disabled", true);
//                        $('#upload-btn').html("<i class='fa fa-spinner fa-spin btn-loader'></i> Validating");
//                    }
//                    var xlsxflag = false; /*Flag for checking whether excel is .xls format or .xlsx format*/
//                    if ($("#excelfile").val().toLowerCase().indexOf(".xlsx") !== 1) {
////                console.log('2');
//                        xlsxflag = true;
//                    }
//                    /*Checks whether the browser supports HTML5*/
//                    if (typeof (FileReader) !== "undefined") {
//
//                        var reader = new FileReader();
//                        reader.onload = function (e) {
//                            var data = e.target.result;
//                            /*Converts the excel data in to object*/
//                            if (xlsxflag) {
//                                var binary = "";
//                                var bytes = new Uint8Array(data);
//                                var length = bytes.byteLength;
//                                for (var i = 0; i < length; i++) {
//                                    binary += String.fromCharCode(bytes[i]);
//                                }
//                                var workbook = XLSX.read(binary, {type: 'binary'});
//                            }
//                            /*Gets all the sheetnames of excel in to a variable*/
//                            var sheet_name_list = workbook.SheetNames;
//                            var cnt = 0; /*This is used for restricting the script to consider only first sheet of excel*/
//                            sheet_name_list.forEach(function (y) { /*Iterate through all sheets*/
//                                /*Convert the cell value to Json*/
//                                if (xlsxflag) {
//                                    var exceljson = XLSX.utils.sheet_to_json(workbook.Sheets[y]);
//                                    var jsonObj = JSON.stringify(exceljson);
//                                    console.log(jsonObj);
//                                }
//
//                                var columnSet = [];
//                                if (exceljson.length > 0 && cnt === 0) {
////                            console.log('Data: t');
//                                    var rowHash = exceljson[1];
//                                    for (var key in rowHash) {
//
//                                        if (rowHash.hasOwnProperty(key)) {
//                                            if ($.inArray(key, columnSet) === -1) {/*Adding each unique column names to a variable array*/
//                                                columnSet.push(key);
//                                            }
//                                        }
//                                    }
//                                    console.log('2: ' + columnSet);
//                                    var header = 'CardHolderName,CardNumber,IdNumber,IdType,MobileNumber';
//                                    $scope.validKycFile = false;
//                                    header.split(/\s*,\s*/).forEach(function (myString) {
//                                        $scope.validKycFile = exceljson[1].hasOwnProperty(myString);
//                                    });
//                                    if ($scope.validKycFile) {
//
//                                        console.log('Valid excel file');
//                                        if (file !== null) {
//                                            $scope.uploadKycFile(file, type, recordType);
//                                        } else {
//                                            alert('could not check File');
//                                        }
//                                    } else {
//
//                                        alert("invalid excel file");
//                                    }
//                                    cnt++;
//                                }
//                            });
//                        };
//                        if (xlsxflag) {/*If excel file is .xlsx extension than creates a Array Buffer from excel*/
//                            reader.readAsArrayBuffer($("#excelfile")[1].files[1]);
//                        }
//
//                    } else {
//                        $('#upload-btn').prop("disabled", false);
//                        $('#upload-btn').html("Upload");
//                        alert("Sorry! Your browser does not support HTML5!");
//                    }
//
//                };
//                function uploadFileX(inputFile, headers) {
//                    var reader = new FileReader();
//                    return new Promise(function (resolve, reject) {
//                        if (typeof (FileReader) === "undefined") {
//                            reject("Problem parsing input file.");
//                        } else {
//
//                            reader.onerror = function (e) {
//                                reader.abort();
//                                reject("Problem parsing input file.");
//                            };
//                            reader.onload = function (e) {
////                            console.log('loading');
//                                var xlsxflag = true;
////                            resolve(reader.result);
//                                var data = e.target.result;
////                            console.log('data: ' + data);
//                                if (xlsxflag) {
//                                    var binary = "";
//                                    var bytes = new Uint8Array(data);
//                                    var length = bytes.byteLength;
//                                    for (var i = 0; i < length; i++) {
//                                        binary += String.fromCharCode(bytes[i]);
//                                    }
//                                    var workbook = XLSX.read(binary, {type: 'binary'});
//                                }
//                                var sheet_name_list = workbook.SheetNames;
////                            console.log('sheet::: ' + sheet_name_list);
//                                var cnt = 0;
//                                var exceljson = "";
//                                sheet_name_list.forEach(function (y) {
//                                    if (xlsxflag) {
//                                        exceljson = XLSX.utils.sheet_to_json(workbook.Sheets[y], {defval: ""});
////                                    var jsonObj = JSON.stringify(exceljson);
////                                    console.log(jsonObj);
//                                    }
//
//                                    var columnSet = [];
//                                    if (exceljson.length > 0 && cnt === 0) {
////                                    var rowHash = exceljson[1];
////                                    for (var key in rowHash) {
////
////                                        if (rowHash.hasOwnProperty(key)) {
////                                            if ($.inArray(key, columnSet) === -1) {
////                                                columnSet.push(key);
////                                            }
////                                        }
////                                    }
//                                        var header = headers;
//                                        var validExcelFile = false;
////                                    console.log('ter::: '+ Object.keys(exceljson[0]));
//                                        var fileHeaders = Object.keys(exceljson.reduce(function (result, obj) {
//                                            return Object.assign(result, obj);
//                                        }, {}));
//                                        header.split(/\s*,\s*/).forEach(function (myString) {
////                                        console.log('er: ' + JSON.stringify(exceljson[1]) + " -- " + myString + " -- " + JSON.stringify(exceljson[0]));
////                                        validExcelFile = exceljson[1].hasOwnProperty(myString);
//                                            validExcelFile = fileHeaders.includes(myString);
//                                        });
//                                        if (validExcelFile) {
////                                        console.log("size:: " + exceljson.length);
////                                        console.log("size2:: " + Object.keys(exceljson[0]).length);
//
//                                            resolve(exceljson);
//                                        } else {
//                                            reject("Invalid File Header");
//                                        }
//                                    }
//                                });
//                            };
//                            reader.readAsArrayBuffer(inputFile);
//                        }
//                    });
//                }
//
//                $scope.showUploadData = function (type) {
//
//                    switch (type) {
//                        case "reversal":
//                            Swal.fire({
//                                focusConfirm: false,
//                                title: 'Bulk Reversal',
////                    input: 'select',
////                    inputOptions: options,
////                    html: `<input class="swal2-file" id="uploadFile" placeholder="Please select valid Excel File" accept=".xlsx" type="file"/><br /> `,
//                                input: 'file'
//                                ,
//                                inputAttributes: {
//                                    'accept': '.xlsx',
//                                    'aria-label': 'Upload Excel'
//                                },
//                                type: 'warning',
//                                showCancelButton: true,
//                                cancelButtonColor: 'grey',
//                                confirmButtonText: 'Search!',
//                                inputPlaceholder: 'Upload Excel',
//                                allowOutsideClick: false,
//                                showLoaderOnConfirm: true,
//                                onOpen: function (ele) {
//                                    $(ele).find('.momo_status').insertAfter($(ele).find('.swal2-file'));
//                                },
//                                preConfirm: function preConfirm(file) {
//
//                                    if (!file) {
//                                        Swal.showValidationMessage('Please select excel file');
//                                    } else {
//                                        var filename = $(".swal2-file").val().toLowerCase();
//                                        var ext = filename.substring(filename.lastIndexOf('.') + 1);
//                                        if (ext === "xlsx") {
//                                            return uploadFileX($(".swal2-file")[0].files[0], 'reference')
//                                                    .then(function (res) {
//                                                        $scope.selectAll = false;
//                                                        $scope.selectedData = [];
//                                                        $scope.selection = [];
//                                                        return $http({
//                                                            url: "api/Reversal",
//                                                            method: 'POST',
//                                                            data: $httpParamSerializerJQLike({
//                                                                "trans_data": JSON.stringify(res),
//                                                                "service": "bulkTransactions"
//                                                            }),
//                                                            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                                                        }).then(function success(response) {
////                                                console.log(response.data);
//                                                            if (response.data.code !== '00') {
//                                                                throw new Error(response.data.message);
//                                                            }
//                                                            return response;
//                                                        }).catch(function (error) {
//                                                            Swal.showValidationMessage(error || 'Request Failed');
//                                                        });
//                                                    })
//                                                    .catch(function (err) {
//                                                        console.log(err);
//                                                        Swal.showValidationMessage(err);
//                                                    });
//                                        } else {
//                                            Swal.showValidationMessage("Invalid Excel File");
//                                        }
//                                    }
//                                }
//                            }).then(function (result) {
//                                if (!result.dismiss) {
////                        console.log('result: ' + JSON.stringify(result));
//                                    result = result.value.data;
////                        console.log('code:' + result.code);
////                        console.log('message:' + result.message);
//                                    if (result.code === '00') {
//                                        Swal.fire({
//                                            position: 'top-end',
//                                            type: 'success',
//                                            title: result.message,
//                                            showConfirmButton: false,
//                                            timer: 1500
//                                        });
//                                        var modelName = "ErequestTrxs";
//                                        var respField = "respcode";
//                                        var respMsg = "SUCCESSFUL";
//                                        var amountColumn = "trans_amount";
//                                        $scope.pageNumber = 1;
////                            $rootScope[modelName] = JSON.parse(result.data);
//                                        $rootScope.$apply($rootScope[modelName] = JSON.parse(result.data));
//                                        $scope.$apply($scope.getStatistics(modelName, respField, respMsg, amountColumn));
//                                        $scope.$apply($scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage));
//                                        $scope.$apply($scope.end = parseInt($scope.begin + $scope.rowsPerPage));
//                                        $rootScope.$apply($rootScope['filtered' + modelName] = $rootScope[modelName].slice($scope.begin, $scope.end));
//                                    } else {
//                                        Swal.showValidationMessage(result.message);
//                                    }
//                                }
//                            }).catch(function (error) {
//                                console.log(error);
//                                Swal.showValidationMessage(error);
//                            });
//                            break;
//                        case "mobilemoney":
//                            Swal.fire({
//                                focusConfirm: false,
//                                title: 'Bulk Momo Update',
////                    input: 'select',
////                    inputOptions: options,
////                    html: `<input class="swal2-file" id="uploadFile" placeholder="Please select valid Excel File" accept=".xlsx" type="file"/><br /> `,
//                                input: 'file',
//                                html: `<span class="input-group-btn momo_status" style="margin-bottom: 20px !important">
//                                <select class="form-control" id="momo_status">
//                                    <option selected value="" disabled>Select Status</option>
//                                    <option value="successful">SUCCESSFUL</option>
//                                    <option value="failed">FAILED</option>
//                                    <option value="locked">ACCOUNT LOCKED</option>
//                                    <option value="limit_exceeded">LIMIT EXCEEDED</option>
//                                    <option value="not_registered">NOT REGISTERED</option>
//                                    <option value="reprocess">REPROCESS</option>
//                                 </select>
//                        </span>`,
//                                inputAttributes: {
//                                    'accept': '.xlsx',
//                                    'aria-label': 'Upload Excel'
//                                },
//                                type: 'warning',
//                                showCancelButton: true,
//                                cancelButtonColor: 'grey',
//                                confirmButtonText: 'Update!',
//                                inputPlaceholder: 'Upload Excel',
//                                allowOutsideClick: false,
//                                showLoaderOnConfirm: true,
//                                onOpen: function (ele) {
//                                    $(ele).find('.momo_status').insertAfter($(ele).find('.swal2-file'));
//                                },
//                                preConfirm: function preConfirm(file) {
//
//                                    var momo_status = document.getElementById('momo_status').value;
//                                    if (!file) {
//                                        Swal.showValidationMessage('Please select excel file');
//                                    } else if (momo_status === '') {
//                                        Swal.showValidationMessage('Please select status for file');
//                                    } else {
//                                        var filename = $(".swal2-file").val().toLowerCase();
//                                        var ext = filename.substring(filename.lastIndexOf('.') + 1);
//                                        if (ext === "xlsx") {
//                                            return uploadFileX($(".swal2-file")[0].files[0], 'reference,clientid')
//                                                    .then(function (res) {
//                                                        return $http({
//                                                            url: "api/Mobilemoney",
//                                                            method: 'POST',
//                                                            data: $httpParamSerializerJQLike({
//                                                                "uniqueTransId": JSON.stringify(res),
//                                                                "service": "momoupdate",
//                                                                "status": momo_status
//                                                            }),
//                                                            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                                                        }).then(function success(response) {
////                                                console.log(response.data);
//                                                            if (response.data.code !== '00') {
//                                                                throw new Error(response.data.message);
//                                                            }
//                                                            return response;
//                                                        }).catch(function (error) {
//                                                            Swal.showValidationMessage(error || 'Request Failed');
//                                                        });
//                                                    })
//                                                    .catch(function (err) {
//                                                        console.log(err);
//                                                        Swal.showValidationMessage(err);
//                                                    });
//                                        } else {
//                                            Swal.showValidationMessage("Invalid Excel File");
//                                        }
//                                    }
//                                }
//                            }).then(function (result) {
//                                if (!result.dismiss) {
////                        console.log('result: ' + JSON.stringify(result));
//                                    result = result.value.data;
////                        console.log('code:' + result.code);
////                        console.log('message:' + result.message);
//                                    if (result.code === '00') {
//                                        Swal.fire({
//                                            position: 'top-end',
//                                            type: 'success',
//                                            title: result.message,
//                                            showConfirmButton: false,
//                                            timer: 1500
//                                        });
//                                        var modelName = "MomoTrxs";
//                                        var respField = "respcode";
//                                        var respMsg = "SUCCESSFUL";
//                                        var amountColumn = "amount";
//                                        $scope.pageNumber = 1;
////                            $rootScope[modelName] = JSON.parse(result.data);
//                                        $rootScope.$apply($rootScope[modelName] = JSON.parse(result.data));
//                                        $scope.$apply($scope.getStatistics(modelName, respField, respMsg, amountColumn));
//                                        $scope.$apply($scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage));
//                                        $scope.$apply($scope.end = parseInt($scope.begin + $scope.rowsPerPage));
//                                        $rootScope.$apply($rootScope['filtered' + modelName] = $rootScope[modelName].slice($scope.begin, $scope.end));
//                                    } else {
//                                        Swal.showValidationMessage(result.message);
//                                    }
//                                }
//                            }).catch(function (error) {
//                                console.log(error);
//                                Swal.showValidationMessage(error);
//                            });
//                            break;
//                        case "bulkreprocess":
//                            Swal.fire({
//                                focusConfirm: false,
//                                title: 'Bulk Process',
//                                input: 'file',
////                            html: `<span class="input-group-btn momo_status" style="margin-bottom: 20px !important">
////                                        <select class="form-control" id="momo_status">
////                                            <option selected value="" disabled>Select Status</option>
////                                            <option value="successful">SUCCESSFUL</option>
////                                            <option value="failed">FAILED</option>
////                                            <option value="locked">ACCOUNT LOCKED</option>
////                                            <option value="limit_exceeded">LIMIT EXCEEDED</option>
////                                            <option value="not_registered">NOT REGISTERED</option>
////                                            <option value="reprocess">REPROCESS</option>
////                                         </select>
////                                    </span>`,
//                                inputAttributes: {
//                                    'accept': '.xlsx',
//                                    'aria-label': 'Upload Excel'
//                                },
//                                type: 'warning',
//                                showCancelButton: true,
//                                cancelButtonColor: 'grey',
//                                confirmButtonText: 'Upload!',
//                                inputPlaceholder: 'Upload Excel',
//                                allowOutsideClick: false,
//                                showLoaderOnConfirm: true,
//                                onOpen: function (ele) {
////                                $(ele).find('.momo_status').insertAfter($(ele).find('.swal2-file'));
//                                },
//                                preConfirm: function preConfirm(file) {
//
////                                var momo_status = document.getElementById('momo_status').value;
//
//                                    if (!file) {
//                                        Swal.showValidationMessage('Please select excel file');
//                                    }
////                                else if (momo_status === '') {
////                                    Swal.showValidationMessage('Please select status for file');
////                                } 
//                                    else {
//                                        var filename = $(".swal2-file").val().toLowerCase();
//                                        var ext = filename.substring(filename.lastIndexOf('.') + 1);
//                                        if (ext === "xlsx") {
//                                            return uploadFileX($(".swal2-file")[0].files[0], 'reference,account,amount,transaction_type,destination_type,extra_info')
//                                                    .then(function (res) {
////                                                    return $http({
////                                                        url: "api/Reprocess",
////                                                        method: 'POST',
////                                                        data: $httpParamSerializerJQLike({
////                                                            "trans_data": JSON.stringify(res),
////                                                            "service": "uploadFile"
//////                                                            "status": momo_status
////                                                        }),
////                                                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
////                                                    }).then(function success(response) {
//////                                                console.log(response.data);
////                                                        if (response.data.code !== '00') {
////                                                            throw new Error(response.data.message);
////                                                        }
////                                                        return response;
////                                                    }).catch(function (error) {
////                                                        Swal.showValidationMessage(error || 'Request Failed');
////                                                    });
//
//                                                        var response = {};
//                                                        response['code'] = "00";
//                                                        response['message'] = "Upload FIle Complete";
//                                                        response['data'] = JSON.stringify(res);
////                                                    console.log('trans: ' + JSON.stringify(response));
//                                                        var resp = {};
//                                                        resp['data'] = response;
//                                                        return resp;
//                                                    }).catch(function (err) {
//                                                console.log(err);
//                                                Swal.showValidationMessage(err);
//                                            });
//                                        } else {
//                                            Swal.showValidationMessage("Invalid Excel File");
//                                        }
//                                    }
//                                }
//                            }).then(function (result) {
//                                if (!result.dismiss) {
////                        console.log('result: ' + JSON.stringify(result));
//                                    result = result.value.data;
////                        console.log('code:' + result.code);
////                        console.log('message:' + result.message);
//                                    if (result.code === '00') {
//                                        Swal.fire({
//                                            position: 'top-end',
//                                            type: 'success',
//                                            title: result.message,
//                                            showConfirmButton: false,
//                                            timer: 1500
//                                        });
//                                        var modelName = "BulkProcessTrxs";
//                                        var respField = "respcode";
//                                        var respMsg = "00";
//                                        var amountColumn = "amount";
//                                        $scope.pageNumber = 1;
////                            $rootScope[modelName] = JSON.parse(result.data);
//                                        $rootScope.$apply($rootScope[modelName] = JSON.parse(result.data));
//                                        $scope.$apply($scope.getStatistics(modelName, respField, respMsg, amountColumn));
//                                        $scope.$apply($scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage));
//                                        $scope.$apply($scope.end = parseInt($scope.begin + $scope.rowsPerPage));
//                                        $rootScope.$apply($rootScope['filtered' + modelName] = $rootScope[modelName].slice($scope.begin, $scope.end));
//                                    } else {
//                                        Swal.showValidationMessage(result.message);
//                                    }
//                                }
//                            }).catch(function (error) {
//                                console.log(error);
//                                Swal.showValidationMessage(error);
//                            });
//                            break;
//                        case "tmc":
//                            Swal.fire({
//                                focusConfirm: false,
//                                title: 'Bulk TMC Update',
////                    input: 'select',
////                    inputOptions: options,
////                    html: `<input class="swal2-file" id="uploadFile" placeholder="Please select valid Excel File" accept=".xlsx" type="file"/><br /> `,
//                                input: 'file',
////                            html: `<span class="input-group-btn momo_status" style="margin-bottom: 20px !important">
////                                <select class="form-control" id="momo_status">
////                                    <option selected value="" disabled>Select Status</option>
////                                    <option value="successful">SUCCESSFUL</option>
////                                    <option value="failed">FAILED</option>
////                                    <option value="locked">ACCOUNT LOCKED</option>
////                                    <option value="limit_exceeded">LIMIT EXCEEDED</option>
////                                    <option value="not_registered">NOT REGISTERED</option>
////                                    <option value="reprocess">REPROCESS</option>
////                                 </select>
////                        </span>`,
//                                inputAttributes: {
//                                    'accept': '.xlsx',
//                                    'aria-label': 'Upload Excel'
//                                },
//                                type: 'warning',
//                                showCancelButton: true,
//                                cancelButtonColor: 'grey',
//                                confirmButtonText: 'Update!',
//                                inputPlaceholder: 'Upload Excel',
//                                allowOutsideClick: false,
//                                showLoaderOnConfirm: true,
////                            onOpen: function (ele) {
////                                $(ele).find('.momo_status').insertAfter($(ele).find('.swal2-file'));
////                            },
//                                preConfirm: function preConfirm(file) {
//
////                                var momo_status = document.getElementById('momo_status').value;
//
//                                    if (!file) {
//                                        Swal.showValidationMessage('Please select excel file');
////                                } else if (momo_status === '') {
////                                    Swal.showValidationMessage('Please select status for file');
//                                    } else {
//                                        var filename = $(".swal2-file").val().toLowerCase();
//                                        var ext = filename.substring(filename.lastIndexOf('.') + 1);
//                                        if (ext === "xlsx") {
//                                            return uploadFileX($(".swal2-file")[0].files[0], 'reference')
//                                                    .then(function (res) {
//                                                        return $http({
//                                                            url: "api/Tmc",
//                                                            method: 'POST',
//                                                            data: $httpParamSerializerJQLike({
//                                                                "uniqueTransId": JSON.stringify(res),
//                                                                "service": "tmcupdate",
////                                                            "status": momo_status
//                                                            }),
//                                                            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                                                        }).then(function success(response) {
////                                                console.log(response.data);
//                                                            if (response.data.code !== '00') {
//                                                                throw new Error(response.data.message);
//                                                            }
//                                                            return response;
//                                                        }).catch(function (error) {
//                                                            Swal.showValidationMessage(error || 'Request Failed');
//                                                        });
//                                                    })
//                                                    .catch(function (err) {
//                                                        console.log(err);
//                                                        Swal.showValidationMessage(err);
//                                                    });
//                                        } else {
//                                            Swal.showValidationMessage("Invalid Excel File");
//                                        }
//                                    }
//                                }
//                            }).then(function (result) {
//                                if (!result.dismiss) {
////                        console.log('result: ' + JSON.stringify(result));
//                                    result = result.value.data;
////                        console.log('code:' + result.code);
////                        console.log('message:' + result.message);
//                                    if (result.code === '00') {
//                                        Swal.fire({
//                                            position: 'top-end',
//                                            type: 'success',
//                                            title: result.message,
//                                            showConfirmButton: false,
//                                            timer: 1500
//                                        });
//                                        var modelName = "TmcTrxs";
//                                        var respField = "response_code";
//                                        var respMsg = "00";
//                                        var amountColumn = "amount";
//                                        $scope.pageNumber = 1;
////                            $rootScope[modelName] = JSON.parse(result.data);
//                                        $rootScope.$apply($rootScope[modelName] = JSON.parse(result.data));
//                                        $scope.$apply($scope.getStatistics(modelName, respField, respMsg, amountColumn));
//                                        $scope.$apply($scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage));
//                                        $scope.$apply($scope.end = parseInt($scope.begin + $scope.rowsPerPage));
//                                        $rootScope.$apply($rootScope['filtered' + modelName] = $rootScope[modelName].slice($scope.begin, $scope.end));
//                                    } else {
//                                        Swal.showValidationMessage(result.message);
//                                    }
//                                }
//                            }).catch(function (error) {
//                                console.log(error);
//                                Swal.showValidationMessage(error);
//                            });
//                            break;
//                        case "vasreprocess":
//                            //alert("Vas Reprocess")
//                            Swal.fire({
//                                focusConfirm: false,
//                                title: 'Vasgate Reprocessing',
////                    input: 'select',
////                    inputOptions: options,
////                    html: `<input class="swal2-file" id="uploadFile" placeholder="Please select valid Excel File" accept=".xlsx" type="file"/><br /> `,
//                                input: 'file',
////                            html: `<span class="input-group-btn momo_status" style="margin-bottom: 20px !important">
////                                <select class="form-control" id="momo_status">
////                                    <option selected value="" disabled>Select Status</option>
////                                    <option value="successful">SUCCESSFUL</option>
////                                    <option value="failed">FAILED</option>
////                                    <option value="locked">ACCOUNT LOCKED</option>
////                                    <option value="limit_exceeded">LIMIT EXCEEDED</option>
////                                    <option value="not_registered">NOT REGISTERED</option>
////                                    <option value="reprocess">REPROCESS</option>
////                                 </select>
////                        </span>`,
//                                inputAttributes: {
//                                    'accept': '.xlsx',
//                                    'aria-label': 'Upload Excel'
//                                },
//                                type: 'warning',
//                                showCancelButton: true,
//                                cancelButtonColor: 'grey',
//                                confirmButtonText: 'Update!',
//                                inputPlaceholder: 'Upload Excel',
//                                allowOutsideClick: false,
//                                showLoaderOnConfirm: true,
////                            onOpen: function (ele) {
////                                $(ele).find('.momo_status').insertAfter($(ele).find('.swal2-file'));
////                            },
//                                preConfirm: function preConfirm(file) {
//
////                                var momo_status = document.getElementById('momo_status').value;
//
//                                    if (!file) {
//                                        Swal.showValidationMessage('Please select excel file');
////                                } else if (momo_status === '') {
////                                    Swal.showValidationMessage('Please select status for file');
//                                    } else {
//                                        var filename = $(".swal2-file").val().toLowerCase();
//                                        var ext = filename.substring(filename.lastIndexOf('.') + 1);
//                                        if (ext === "xlsx") {
//                                            return uploadFileX($(".swal2-file")[0].files[0], 'reference')
//                                                    .then(function (res) {
//                                                        return $http({
//                                                            url: "api/VasgateReprocess",
//                                                            method: 'POST',
//                                                            data: $httpParamSerializerJQLike({
//                                                                "uniqueTransId": JSON.stringify(res),
//                                                                "service": "vasgatereprocess"
////                                                            "status": momo_status
//                                                            }),
//                                                            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                                                        }).then(function success(response) {
////                                                console.log(response.data);
//                                                            if (response.data.code !== '00') {
//                                                                throw new Error(response.data.message);
//                                                            }
//                                                            return response;
//                                                        }).catch(function (error) {
//                                                            Swal.showValidationMessage(error || 'Request Failed');
//                                                        });
//                                                    })
//                                                    .catch(function (err) {
//                                                        console.log(err);
//                                                        Swal.showValidationMessage(err);
//                                                    });
//                                        } else {
//                                            Swal.showValidationMessage("Invalid Excel File");
//                                        }
//                                    }
//                                }
//                            }).then(function (result) {
//                                if (!result.dismiss) {
////                        console.log('result: ' + JSON.stringify(result));
//                                    result = result.value.data;
////                        console.log('code:' + result.code);
////                        console.log('message:' + result.message);
//                                    if (result.code === '00') {
//                                        Swal.fire({
//                                            position: 'top-end',
//                                            type: 'success',
//                                            title: result.message,
//                                            showConfirmButton: false,
//                                            timer: 1500
//                                        });
//                                        var modelName = "TmcTrxs";
//                                        var respField = "response_code";
//                                        var respMsg = "00";
//                                        var amountColumn = "amount";
//                                        $scope.pageNumber = 1;
////                            $rootScope[modelName] = JSON.parse(result.data);
//
//                                        $rootScope.$apply($rootScope[modelName] = JSON.parse(result.data));
//                                        $scope.$apply($scope.getStatistics(modelName, respField, respMsg, amountColumn));
//                                        $scope.$apply($scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage));
//                                        $scope.$apply($scope.end = parseInt($scope.begin + $scope.rowsPerPage));
//                                        $rootScope.$apply($rootScope['filtered' + modelName] = $rootScope[modelName].slice($scope.begin, $scope.end));
//                                    } else {
//                                        Swal.showValidationMessage(result.message);
//                                    }
//                                }
//                            }).catch(function (error) {
//                                console.log(error);
//                                Swal.showValidationMessage(error);
//                            });
//                            $rootScope.DisplayFirst = false;
//                            $rootScope.DisplaySecond = true;
//                            $rootScope.DisplayThird = false;
//                            break;
//                        case "settlement":
//
//                            var options = `<span class="input-group-btn momo_status" style="margin-bottom: 20px !important">
//                                <select class="form-control" id="bank_code">
//                                    <option selected value="" disabled>--- Select Bank ---</option>`;
//                            angular.forEach($rootScope.banksList, function (value, index) {
//                                options += '<option value ="' + value.issuer_code + '">' + value.issuer_name + '</option>';
//                            });
//                            options += `
//                                 </select>
//                        </span>`;
//                            var bank_code = '';
//                            Swal.fire({
//                                focusConfirm: false,
//                                title: 'Settlement File Upload',
////                    input: 'select',
////                    inputOptions: options,
////                    html: `<input class="swal2-file" id="uploadFile" placeholder="Please select valid Excel File" accept=".xlsx" type="file"/><br /> `,
//                                input: 'file',
//                                html: options,
//                                inputAttributes: {
//                                    'accept': '.zip',
//                                    'aria-label': 'Upload Zip File'
//                                },
//                                type: 'warning',
//                                showCancelButton: true,
//                                cancelButtonColor: 'grey',
//                                confirmButtonText: 'Upload!',
//                                inputPlaceholder: 'Upload Zip File',
//                                allowOutsideClick: false,
//                                showLoaderOnConfirm: true,
//                                onOpen: function (ele) {
//                                    $(ele).find('.momo_status').insertAfter($(ele).find('.swal2-file'));
//                                },
//                                preConfirm: function preConfirm(file) {
//
//                                    bank_code = document.getElementById('bank_code').value;
//                                    if (!file) {
//                                        Swal.showValidationMessage('Please select zip file');
//                                    } else if (bank_code === '') {
//                                        Swal.showValidationMessage('Please select bank for file');
//                                    } else {
//                                        var filename = $(".swal2-file").val().toLowerCase();
//                                        var ext = filename.substring(filename.lastIndexOf('.') + 1);
//                                        if (ext === "zip") {
//                                            var formdata = new FormData();
//                                            $scope.uploadResult = "";
//                                            $scope.showProgressBar = false;
//                                            $scope.showProgressBarError = false;
//                                            formdata.append("file", file);
//                                            formdata.append("bank", bank_code);
//                                            formdata.append("service", "uploadFile");
//                                            return $http({
//                                                url: "api/ConsoleFiles",
//                                                method: 'POST',
//                                                data: formdata,
//                                                headers: {'Content-Type': undefined}
//                                            }).then(function success(response) {
////                                                console.log(response.data);
//                                                if (response.data.code !== '00') {
//                                                    throw new Error(response.data.message);
//                                                }
//                                                return response;
//                                            }).catch(function (error) {
//                                                Swal.showValidationMessage(error || 'Request Failed');
//                                            });
//                                        } else {
//                                            Swal.showValidationMessage("Invalid Zip File");
//                                        }
//                                    }
//                                }
//                            }).then(function (result) {
//                                if (!result.dismiss) {
////                        console.log('result: ' + JSON.stringify(result));
//                                    result = result.value.data;
////                        console.log('code:' + result.code);
////                        console.log('message:' + result.message);
//                                    if (result.code === '00') {
//                                        Swal.fire({
//                                            position: 'top-end',
//                                            type: 'success',
//                                            title: result.message,
//                                            showConfirmButton: false,
//                                            timer: 1500
//                                        });
////                                    $scope.bank = bank_code;
////                                    document.forms['myForm']['name'].value = "New value";
////                                    $scope.refreshReport2('ConsoleFiles', 'ConsoleFiles', 'cf', 1, 0);
//                                    } else {
//                                        Swal.showValidationMessage(result.message);
//                                    }
//                                }
//                            }).catch(function (error) {
////                            console.log(error);
//                                Swal.showValidationMessage(error);
//                            });
//                            break;
//                            
//                            case "mobilemoneyreprocess":
//                            //alert("Mobile money Reprocess");
//                            Swal.fire({
//                                focusConfirm: false,
//                                title: 'Mobile Money Reprocessing Data',
////                    input: 'select',
////                    inputOptions: options,
////                    html: `<input class="swal2-file" id="uploadFile" placeholder="Please select valid Excel File" accept=".xlsx" type="file"/><br /> `,
//                                input: 'file',
////                            html: `<span class="input-group-btn momo_status" style="margin-bottom: 20px !important">
////                                <select class="form-control" id="momo_status">
////                                    <option selected value="" disabled>Select Status</option>
////                                    <option value="successful">SUCCESSFUL</option>
////                                    <option value="failed">FAILED</option>
////                                    <option value="locked">ACCOUNT LOCKED</option>
////                                    <option value="limit_exceeded">LIMIT EXCEEDED</option>
////                                    <option value="not_registered">NOT REGISTERED</option>
////                                    <option value="reprocess">REPROCESS</option>
////                                 </select>
////                        </span>`,
//                                inputAttributes: {
//                                    'accept': '.xlsx',
//                                    'aria-label': 'Upload Excel'
//                                },
//                                type: 'warning',
//                                showCancelButton: true,
//                                cancelButtonColor: 'grey',
//                                confirmButtonText: 'Update!',
//                                inputPlaceholder: 'Upload Excel',
//                                allowOutsideClick: false,
//                                showLoaderOnConfirm: true,
////                            onOpen: function (ele) {
////                                $(ele).find('.momo_status').insertAfter($(ele).find('.swal2-file'));
////                            },
//                                preConfirm: function preConfirm(file) {
//
////                                var momo_status = document.getElementById('momo_status').value;
//
//                                    if (!file) {
//                                        Swal.showValidationMessage('Please select excel file');
////                                } else if (momo_status === '') {
////                                    Swal.showValidationMessage('Please select status for file');
//                                    } else {
//                                        var filename = $(".swal2-file").val().toLowerCase();
//                                        var ext = filename.substring(filename.lastIndexOf('.') + 1);
//                                        if (ext === "xlsx") {
//                                            return uploadFileX($(".swal2-file")[0].files[0], 'reference')
//                                                    .then(function (res) {
//                                                        return $http({
//                                                            url: "api/Mobilemoneyreprocessing",
//                                                            method: 'POST',
//                                                            data: $httpParamSerializerJQLike({
//                                                                "uniqueTransId": JSON.stringify(res),
//                                                                "service": "Mobilemoneyreprocessing"
////                                                            "status": momo_status
//                                                            }),
//                                                            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                                                        }).then(function success(response) {
////                                                console.log(response.data);
//                                                            if (response.data.code !== '00') {
//                                                                throw new Error(response.data.message);
//                                                            }
//                                                            return response;
//                                                        }).catch(function (error) {
//                                                            Swal.showValidationMessage(error || 'Request Failed');
//                                                        });
//                                                    })
//                                                    .catch(function (err) {
//                                                        console.log(err);
//                                                        Swal.showValidationMessage(err);
//                                                    });
//                                        } else {
//                                            Swal.showValidationMessage("Invalid Excel File");
//                                        }
//                                    }
//                                }
//                            }).then(function (result) {
//                                if (!result.dismiss) {
////                        console.log('result: ' + JSON.stringify(result));
//                                    result = result.value.data;
////                        console.log('code:' + result.code);
////                        console.log('message:' + result.message);
//                                    if (result.code === '00') {
//                                        Swal.fire({
//                                            position: 'top-end',
//                                            type: 'success',
//                                            title: result.message,
//                                            showConfirmButton: false,
//                                            timer: 1500
//                                        });
//                                        var modelName = "TmcTrxs";
//                                        var respField = "response_code";
//                                        var respMsg = "00";
//                                        var amountColumn = "amount";
//                                        $scope.pageNumber = 1;
////                            $rootScope[modelName] = JSON.parse(result.data);
//
//                                        $rootScope.$apply($rootScope[modelName] = JSON.parse(result.data));
//                                        $scope.$apply($scope.getStatistics(modelName, respField, respMsg, amountColumn));
//                                        $scope.$apply($scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage));
//                                        $scope.$apply($scope.end = parseInt($scope.begin + $scope.rowsPerPage));
//                                        $rootScope.$apply($rootScope['filtered' + modelName] = $rootScope[modelName].slice($scope.begin, $scope.end));
//                                    } else {
//                                        Swal.showValidationMessage(result.message);
//                                    }
//                                }
//                            }).catch(function (error) {
//                                console.log(error);
//                                Swal.showValidationMessage(error);
//                            });
////                            $rootScope.DisplayFirst = false;
////                            $rootScope.DisplaySecond = true;
////                            $rootScope.DisplayThird = false;
//                            break;
//                            
//                        default:
//                            break;
//                    }
//                };
//                $scope.toggleKycUploadBox = function () {
//                    $scope.showKycUploadBox = !$scope.showKycUploadBox;
//                };
//                $scope.getOvatrxs = function (page, paging) {
//
//                    $scope.showLoader = true;
//                    $rootScope.showLoading();
//                    $scope.showFilterBox = false;
//                    $scope.pageNumber = page;
//                    $('.reload').addClass('fa-spin text-info');
//                    if ($scope.pageNumber < 1)
//                        $scope.pageNumber = 1;
//                    if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                        $scope.pageNumber = $scope.fgRecordsLastPage;
//                    console.log('DATA: ' + $scope.pageNumber + ' : ' + $scope.fgRecordsLastPage);
//                    if (paging) {
//
////                console.log('DATA: ' + page + ' : ' + $scope.fgRecordsLastPage);
//                        $scope.begin = (page - 1) * parseInt($scope.rowsPerPage);
//                        $scope.end = parseInt($scope.begin) + parseInt($scope.rowsPerPage);
////                console.log('begin: ' + $scope.begin + ' : ' + $scope.end);
//                        $scope.filteredOvaRecords = $scope.verifyResult.slice($scope.begin, $scope.end);
//                        $scope.getOvaTotalCount();
//                        $scope.showLoader = false;
//                        $rootScope.hideLoading();
//                        $('.reload').removeClass('fa-spin text-info');
//                    }
//                };
//                $scope.getOvaTotalCount = function () {
//                    $scope.fgRecordsTotalCount = $scope.verifyResult.length;
//                    $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//                    if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//                        $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//                    }
//                };
//                $scope.getRecordsDetailsTotalCount = function () {
//                    $scope.fgRecordsTotalCount = $scope.uploadResult.ovaDataRecordInserted.length > 0 ? $scope.uploadResult.ovaDataRecordInserted.length : $scope.uploadResult.ovaDataRecordExist.length;
//                    $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//                    if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//                        $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//                    }
//                };
//                function _objectWithoutProperties(source, excluded) {
//                    if (source === null)
//                        return {};
//                    var target = _objectWithoutPropertiesLoose(source, excluded);
//                    var key, i;
//                    if (Object.getOwnPropertySymbols) {
//                        var sourceSymbolKeys = Object.getOwnPropertySymbols(source);
//                        for (i = 0; i < sourceSymbolKeys.length; i++) {
//                            key = sourceSymbolKeys[i];
//                            if (excluded.indexOf(key) >= 0)
//                                continue;
//                            if (!Object.prototype.propertyIsEnumerable.call(source, key))
//                                continue;
//                            target[key] = source[key];
//                        }
//                    }
//                    return target;
//                }
//
//                function _objectWithoutPropertiesLoose(source, excluded) {
//                    if (source === null)
//                        return {};
//                    var target = {};
//                    var sourceKeys = Object.keys(source);
//                    var key, i;
//                    for (i = 0; i < sourceKeys.length; i++) {
//                        key = sourceKeys[i];
//                        if (excluded.indexOf(key) >= 0)
//                            continue;
//                        target[key] = source[key];
//                    }
//                    return target;
//                }
//
//                function replacer(key, value) {
//                    var amount = ['amount', 'trans_amount', 'fee'];
//                    if (amount.includes(key) && typeof value === "number") {
//                        return  value.toFixed(2);
//                    }
//                    return value;
//                }
//
//                $scope.getStatistics = function (data, resp_field, resp_msg, amount_field, fee_field) {
//
//
//                    $scope.fgRecordsTotalCount = $scope[data].length;
//                    $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//                    if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//                        $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//                    }
//
//                    if (resp_field && resp_msg && amount_field) {
//                        $scope.fgRecordsTotalAmount = $scope.sumAmount($scope[data], resp_field, resp_msg, amount_field);
//                        if (fee_field) {
//                            $scope.fgRecordsTotalFee = $scope.sumAmount($scope[data], resp_field, resp_msg, fee_field);
//                        }
//                    }
//
//                };
//                $scope.exportToExcel = function (dataToExport, type) {
//
//                    $scope.getCurrentLocation();
////        $scope.data = dataToExport.map(({$$hashKey, ...item }) => item);
//                    if (!type) {
//                        $scope.data = dataToExport.map(function (_ref) {
//                            var $$hashKey = _ref.$$hashKey,
//                                    item = _objectWithoutProperties(_ref, ["$$hashKey"]);
//                            return item;
//                        });
//                        /* generate a worksheet */
//                        var ws = XLSX.utils.json_to_sheet($scope.data);
//                        /* add to workbook */
//                        var wb = XLSX.utils.book_new();
//                        XLSX.utils.book_append_sheet(wb, ws, $scope.currentPageLocation);
//                        /* write workbook and force a download */
//                        XLSX.writeFile(wb, $scope.currentPageLocation + ".xlsx");
//                    } else {
//                        location.href = dataToExport;
//                    }
//                };
//                function jsonBufferToObject(data, headersGetter, status) {
//                    var type = headersGetter("Content-Type");
//                    if (!type.startsWith("application/json")) {
//                        return data;
//                    }
//                    ;
//                    var decoder = new TextDecoder("utf-8");
//                    var domString = decoder.decode(data);
//                    var json = JSON.parse(domString);
//                    return json;
//                }
//
//
//                $scope.exportToPdf = function (dataToExport) {
//                    $rootScope.showLoading();
//                    var data = $scope[dataToExport].map(function (_ref) {
//                        var $$hashKey = _ref.$$hashKey,
//                                item = _objectWithoutProperties(_ref, ["$$hashKey"]);
//                        return item;
//                    });
//                    $http({
//                        url: 'api/ExportPdf',
//                        method: 'POST',
//                        data: $httpParamSerializerJQLike({
//                            "data": JSON.stringify(data)
//                        }),
//                        responseType: 'arraybuffer',
//                        transformResponse: jsonBufferToObject,
//                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                    }).then(function success(response) {
//                        $scope.showLoader = false;
//                        $('.reload').removeClass('fa-spin text-info');
//                        $rootScope.hideLoading();
//                    }, function error(response) {
//                        $scope.showLoader = false;
//                        $('.reload').removeClass('fa-spin text-info');
////                        alert('Connection or Server Error. Try again');
//                        $rootScope.hideLoading();
//                        //$rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                    });
//                };
//                $scope.uploadData = function (dataToExport, route, service) {
//                    //alert($scope[dataToExport]);
//                    var json = JSON.stringify($scope[dataToExport]);
////                console.log(json);
//                    if ($scope[dataToExport]) {
//                        //$rootScope.showLoading();
//                        var data = $scope[dataToExport].map(function (_ref) {
//                            var $$hashKey = _ref.$$hashKey,
//                                    item = _objectWithoutProperties(_ref, ["$$hashKey"]);
//                            return item;
//                        });
//                        $http({
//                            url: 'api/' + route,
//                            method: 'POST',
//                            data: $httpParamSerializerJQLike({
////                            "data": JSON.stringify(data),
//                                "data": JSON.stringify($scope[dataToExport]),
//                                "service": service
//                            }),
//                            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                        }).then(function success(response) {
//                            $scope.showLoader = false;
//                            $('.reload').removeClass('fa-spin text-info');
//                            //console.log("response data"+JSON.stringify(response.data));
//                            var resp = response.data;
//                            console.log("response data two" + JSON.stringify(resp.data));
//                            $rootScope[dataToExport] = "";
//                            //$rootScope[dataToExport] = resp; 
//
//                            if (resp.code === '00') {
//                                Swal.fire({
//                                    position: 'top-end',
//                                    type: 'success',
//                                    title: resp.message,
//                                    showConfirmButton: false,
//                                    timer: 1500
//                                });
//                                var modelName = "TmcTrxs";
//                                var respField = "response_code";
//                                var respMsg = "00";
//                                var amountColumn = "amount";
//                                var feeColumn = "";
//                                $scope.pageNumber = 1;
//                                //start
////                                  $rootScope["filtered"] = JSON.parse(resp.data);
////                                    $scope.getStatistics(dataToExport, route, service);
////                                    $scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage);
////                                    $scope.end = parseInt($scope.begin + $scope.rowsPerPage);
////                                    $rootScope['filtered' + dataToExport] = $scope[dataToExport].slice($scope.begin, $scope.end);
////                                    
////                                    $rootScope.$apply($rootScope[modelName] = JSON.parse(resp.data));
////                                    $scope.$apply($scope.getStatistics(modelName, respField, respMsg, amountColumn));
////                                    $scope.$apply($scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage));
////                                    $scope.$apply($scope.end = parseInt($scope.begin + $scope.rowsPerPage));
////                                    $rootScope.$apply($rootScope['filtered' + modelName] = $rootScope[modelName].slice($scope.begin, $scope.end));
////                                    
//                                $rootScope[modelName] = JSON.parse(resp.data);
//                                $scope.getStatistics(modelName, respField, respMsg, amountColumn, feeColumn);
//                                $scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage);
//                                $scope.end = parseInt($scope.begin + $scope.rowsPerPage);
//                                $rootScope['filtered' + modelName] = $scope[modelName].slice($scope.begin, $scope.end);
//                                //end
//
//                                $rootScope.DisplayFirst = false;
//                                $rootScope.DisplaySecond = false;
//                                $rootScope.DisplayThird = true;
//                            } else {
//                                Swal.showValidationMessage(resp.message);
//                            }
//                            //return response;
//                            $rootScope.hideLoading();
//                        }, function error(response) {
//                            $scope.showLoader = false;
//                            $('.reload').removeClass('fa-spin text-info');
////                        alert('Connection or Server Error. Try again');
//                            $rootScope.hideLoading();
//                            //$rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                        });
//                    } else {
//
//                    }
//                };
//                $scope.sumAmount = function (data, filter, filterValue, amount) {
//                    var total = 0.00;
//                    $scope.tempData = data;
//                    if ($scope.tempData.length > 0) {
//                        var amountType = amount;
//                        var filterType = filter;
////        console.log('sim: ' + $scope.tempData[1][amountType]);
////        console.log('Type: ' + typeof $scope.tempData[1].[amount]);
//                        for (var i = 0; i < $scope.tempData.length; i++) {  //loop through the array
//                            if (filterType === '') {
//                                total += parseFloat($scope.tempData[i][amountType]); //Do the math!
//                            } else {
//                                if ($scope.tempData[i][filterType] === filterValue)
//                                    total += parseFloat($scope.tempData[i][amountType]);
//                            }
//                        }
//                    }
////                console.log('total: ' + total);
//                    return $scope.getAmt(total.toFixed(2));
//                };
//                $scope.resultsPageCount = function (data) {
//                    return Math.ceil(data.length / $scope.rowsPerPage);
//                };
//                $scope.uploadFile = function (file, type, url) {
////                console.log('got here' + type + ':' + url);
//                    $scope.errorMsg = null;
//                    uploadUsing$http(file, type, url);
//                };
//                $scope.uploadKycFile = function (file, type, url) {
//                    console.log('got here' + type + ':' + url);
//                    $scope.errorMsg = null;
//                    $scope.UploadKycData(file, type, url);
//                };
//                $scope.chunkSize = 100000;
//                $scope.confirm = function () {
//                    return confirm('Are you sure? Your local changes will be lost.');
//                };
////            $scope.rowsPerPage = 1000;
//                $scope.currentPage = 0;
//                $scope.items = [];
//                $scope.range = function () {
//                    var rangeSize = 5;
//                    var ret = [];
//                    var start;
//                    start = $scope.currentPage;
//                    if (start > $scope.pageCount() - rangeSize) {
//                        start = $scope.pageCount() - rangeSize + 1;
//                    }
//
//                    for (var i = start; i < start + rangeSize; i++) {
//                        ret.push(i);
//                    }
//                    return ret;
//                };
//                $scope.prevPage = function () {
//                    if ($scope.currentPage > 0) {
//                        $scope.currentPage--;
//                    }
//                };
//                $scope.prevPageDisabled = function () {
//                    return $scope.currentPage === 0 ? "disabled" : "";
//                };
//                $scope.pageCount = function (trans) {
//                    return Math.ceil($scope.fgTrxs.length / $scope.rowsPerPage) - 1;
//                };
//                $scope.nextPage = function () {
//                    if ($scope.currentPage < $scope.pageCount()) {
//                        $scope.currentPage++;
//                    }
//                };
//                $scope.nextPageDisabled = function () {
//                    return $scope.currentPage === $scope.pageCount() ? "disabled" : "";
//                };
//                $scope.setPage = function (n) {
//                    $scope.currentPage = n;
//                };
//                $('[data-toggle="tooltip"]').tooltip();
////            $scope.$on("$routeChangeSuccess", function (event, next, current) {
////                $http.get('./Cleaner').then(function (response) {});
////            });
//                $scope.initDates = function () {
//                    var startOfDay = moment().format('YYYY-MM-DD 00:00');
//                    var currentDateTime = moment().format('YYYY-MM-DD 23:59');
//                    $scope.startDate = startOfDay;
//                    $scope.endDate = currentDateTime;
//                };
////            profileService.updateUserProfile();
//                $scope.initDates();
//                $scope.merchant = "ALL";
//                $scope.bank = "ALL";
//                $scope.product = "ALL";
//                $scope.status = "ALL";
////            $scope.destination2 = $scope.BankNodes;
//                $scope.statusProxy = "./etz/proxy/2018/statusProxy";
//                $scope.statusupdateProxy = "./etz/proxy/2018/statusupdateProxy";
//                $scope.crdDetProxy = "./etz/proxy/2018/cardDetailsView";
//                $scope.crdUpdateProxy = "./etz/proxy/2018/cardUpdate";
//                $scope.adminProxy = "api/Admin";
//                $scope.vasUrl = "./vasgate";
//                $scope.showProgressBar = false;
//                $scope.showProgressBarError = false;
//                $scope.showFilterBox = true;
//                $scope.showCardUpdateBox = false;
//                var addRoleClicked = false;
//                $scope.rowsPerPage = "100";
//                $scope.pageNumber = 1;
//                $scope.fgRecordsLastPage = 1;
//                $scope.fgRecordsTotalCount = 0;
//                $scope.fgRecordsTotalAmount = 0;
//                $scope.fgRecordsTotalFailedAmount = 0;
//                $scope.fgRecordsTotalFee = 0;
//                $scope.balance = "n/a";
//                $scope.lastBalanceDate = "n/a";
//                $scope.reference = "";
//                $scope.pageNumber = 1;
//                $scope.fgRecordsLastPage = "";
//                $scope.fgRecordsTotalCount = 0;
//                $scope.pageReady = false;
//                $scope.showUserTypes = false;
//                $scope.usernameAvailable = false;
//                $scope.autoRefreshState = false;
//                $scope.refreshState = false;
//                $rootScope.card_service = "update";
//                $scope.action = '';
//                $rootScope.showEnhancement = true;
//                $scope.process_state = "PROCESS";
//                $scope.otp_process_state = "SEND OTP";
//                $scope.authorize_process_state = "AUTHORIZE";
//                $scope.deny_process_state = "DENY";
//                $scope.notification_process_state = "SEND NOTIFICATION";
//                $rootScope.values = [];
//                $scope.selectedData = [];
//                $scope.selection = [];
//                $scope.selectAll = false;
//                $scope.provider = "ALL";
////            $scope.sender = "ALL";
////            $scope.userProfiles = "";
//                $scope.getCurrentLocation = function () {
//                    $scope.currentPageLocation = $location.path().substring(1);
//                };
//                $scope.getSingleCardBalance = function (type) {
//
//                    $http.get('api/CardBalance?action=' + type).then(
//                            function (response) {
//                                $scope.balance = response.data;
//                                $scope.lastBalanceDate = moment().format('DD-MM-YY H:mm');
//                            }
//                    );
//                };
//                $scope.updateApp = function () {
//                    profileService.updateUserProfile();
//                    $scope.getFGBalance();
////        $scope.getGCBTotal();
//
//                };
//                $scope.autoRefreshReport = function (action) {
//                    $('.refresh-btn').toggleClass('fa-sync fa-recycle');
//                    $('.refresh-btn').toggleClass('fa-spin');
//                    if (!$scope.autoRefreshState) {
//                        $scope.autoRefreshState = true;
////                    console.log('autorefresh start');
//                        $scope.refreshReportInterval = setInterval(function () {
//                            $scope.startDate = (moment().subtract(5, 'minutes')).format('YYYY-MM-DD hh:mm');
//                            $scope.endDate = moment().format('YYYY-MM-DD hh:mm');
//                            if (typeof $scope[action] === "function") {
//                                $scope[action](1);
//                            }
//                        }, autoRefreshRate);
//                    } else {
//                        clearInterval($scope.refreshReportInterval);
////                    console.log('cancel autofresh');
//                        $scope.autoRefreshState = false;
//                    }
//                };
//                $scope.refreshReport = function (action) {
//                    if (!$scope.refreshState) {
//                        $scope.refreshState = true;
//                        $scope.endDate = moment().format('YYYY-MM-DD hh:mm');
//                        if (typeof $scope[action] === "function") {
//                            $scope[action](1);
//                        }
//                        setTimeout(function () {
//                            $scope.$apply($scope.refreshState = false);
//                        }, 2000);
//                    }
//                };
//                $scope.checkHotlist = function (status, card) {
////        console.log("status: " + status + " " + "cardnum: " + card);
////        if ($scope.formType === "HOTLIST") {
//                    if (status === '0') {
//                        var element = document.getElementById(card);
//                        element.classList.add("btn-success");
//                        return "HOTLIST";
//                    } else {
//                        var element = document.getElementById(card);
//                        element.classList.add("btn-warning");
//                        return "DEHOTLIST";
//                    }
////        }
////        return "UPDATE";
//
//                };
//                $scope.hideSpecificOption = function () {
//                    $scope.specificReason = false;
//                    $scope.reason = '';
//                    $scope.hideSpecificEnhancement();
//                };
//                $scope.showSpecificOption = function () {
//                    $scope.hideSpecificEnhancement();
//                    $scope.specificReason = true;
//                    $scope.reason = '';
//                };
//                $scope.hideSpecificEnhancement = function () {
//                    $scope.specificEnhancement = false;
//                };
//                $scope.showSpecificEnhancement = function () {
//                    $scope.showSpecificOption();
//                    $scope.specificEnhancement = true;
//                    $scope.specificReason = false;
//                    $scope.reason = '';
//                };
//                $scope.changeCMSOption = function () {
//                    $scope.specificOption = $('#specificOption').val();
//                };
//                $scope.showOtherReason = function () {
//
//                };
//                $scope.checkHotlist1 = function (status, card) {
//                    console.log("status: " + status + " " + "cardnum: " + card);
//                    if ($scope.formType === "HOTLIST") {
//                        if (status === '0') {
//                            var element = document.getElementById(card);
//                            element.classList.add("btn-success");
//                            return "HOTLIST";
//                        } else {
//                            var element = document.getElementById(card);
//                            element.classList.add("btn-warning");
//                            return "DEHOTLIST";
//                        }
//                    }
//                    return "UPDATE";
//                };
//                $scope.clearRootscope = function () {
//                    for (var prop in $rootScope) {
//                        if (prop.startsWith("filtered")) {
//                            delete $rootScope[prop];
//                        }
//                    }
//                };
//                $scope.clearRootscope();
////METHODS
//                $scope.toggleSelect = function (modelName, index) {
//                    $scope[modelName][index].checked = !$scope[modelName][index].checked;
//                    if (!$scope[modelName][index].checked) {
//                        $scope.selectAll = false;
//                    }
//                };
//                $scope.toggleSelectAll = function (modelName, key) {
//                    var checked = $scope.selectAll;
//                    for (var i = 0; i < $scope[modelName].length; i++) {
//                        $scope.selectedData[i] = checked;
//                        $scope[modelName][i].checked = checked;
//                        $scope.selection.push($scope[modelName][i].transid);
//                    }
////               console.log('test: '+ $scope.selection);
//                };
//                $scope.getRecords = function (modelName, path, formName, page, paging, respField, respMsg, amountColumn, feeColumn) {
////                $('.loader').css('display', 'block');
//                    $scope.$evalAsync(function () {
//                        $rootScope.$evalAsync(function () {
//                            $scope.showLoader = true;
//                            $rootScope.showLoading();
//                            $scope.showFilterBox = false;
//                            $scope.pageNumber = page;
//                            $('.reload').addClass('fa-spin text-info');
//                            var url = path;
//                            if ($scope.pageNumber < 1)
//                                $scope.pageNumber = 1;
//                            if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage) {
//                                $scope.pageNumber = $scope.fgRecordsLastPage;
//                            }
//                            console.log('DATA: ' + $scope.pageNumber + ' : ' + $scope.fgRecordsLastPage + ":" + paging + ":" + page);
//                            if (paging && paging !== 0) {
//
////                console.log('DATA: ' + page + ' : ' + $scope.fgRecordsLastPage);
//                                if (page !== 1) {
//                                    $scope.begin = (page - 1) * parseInt($scope.rowsPerPage);
//                                    $scope.end = parseInt($scope.begin) + parseInt($scope.rowsPerPage);
//                                    console.log('begin: ' + $scope.begin + ' : ' + $scope.end);
//                                    $scope['filtered' + modelName] = $scope[modelName].slice($scope.begin, $scope.end);
////                    $scope.showLoader = false;
////                    $('.reload').removeClass('fa-spin text-info');
//
//                                }
//                                $rootScope.hideLoading();
//                            } else {
//                                var formData = $('#' + formName).serializeJSON();
//                                $http({
//                                    url: 'api/' + url,
//                                    method: 'POST',
//                                    data: $httpParamSerializerJQLike(formData),
//                                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                                }).then(function success(response) {
//                                    $scope.showLoader = false;
//                                    $('.reload').removeClass('fa-spin text-info');
//                                    var resp = response.data;
//                                    $rootScope[modelName] = "";
//                                    if (resp.code === "00") {
////                            console.log('data: ' + JSON.stringify(resp));
//                                        $rootScope[modelName] = JSON.parse(resp.data);
//                                        $scope.getStatistics(modelName, respField, respMsg, amountColumn, feeColumn);
//                                        $scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage);
//                                        $scope.end = parseInt($scope.begin + $scope.rowsPerPage);
//                                        $rootScope['filtered' + modelName] = $scope[modelName].slice($scope.begin, $scope.end);
//                                        // console.log('tesr: ' + 'filtered' + modelName + " :: " + JSON.stringify($scope['filtered' + modelName]));
//
//                                    } else {
//
//                                        $rootScope['filtered' + modelName] = "";
//                                        $scope.getStatistics(modelName, respField, respMsg, amountColumn);
//                                    }
////                        console.log('field:  $scope[filtered' + modelName + ']' + JSON.stringify($scope['filtered' + modelName]));
//                                    $rootScope.hideLoading();
//                                }, function error(response) {
//                                    $scope.showLoader = false;
//                                    $('.reload').removeClass('fa-spin text-info');
////                        alert('Connection or Server Error. Try again');
//                                    $rootScope.hideLoading();
//                                    //$rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                                });
//                            }
//                        });
//                    });
//                };
////            $scope.getRecords = function (modelName, path, formName, page, paging, respField, respMsg, amountColumn, feeColumn) {
//////                $('.loader').css('display', 'block');
////                $scope.$evalAsync(function () {
////                    $rootScope.$evalAsync(function () {
////                        $scope.showLoader = true;
////                        $rootScope.showLoading();
////                        $scope.showFilterBox = false;
////                        $scope.pageNumber = page;
////                        $('.reload').addClass('fa-spin text-info');
////                        var url = path;
////
////                        if ($scope.pageNumber < 1)
////                            $scope.pageNumber = 1;
////                        if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
////                            $scope.pageNumber = $scope.fgRecordsLastPage;
//////            console.log('DATA: ' + $scope.pageNumber + ' : ' + $scope.fgRecordsLastPage);
////                        if (paging && paging !== 0) {
////
//////                console.log('DATA: ' + page + ' : ' + $scope.fgRecordsLastPage);
////                            $scope.begin = (page - 1) * parseInt($scope.rowsPerPage);
////                            $scope.end = parseInt($scope.begin) + parseInt($scope.rowsPerPage);
//////                console.log('begin: ' + $scope.begin + ' : ' + $scope.end);
////                            $scope['filtered' + modelName] = $scope[modelName].slice($scope.begin, $scope.end);
////
//////                    $scope.showLoader = false;
//////                    $('.reload').removeClass('fa-spin text-info');
////                            $rootScope.hideLoading();
////
////                        } else {
////                            var formData = $('#' + formName).serializeJSON();
////                            $http({
////                                url: 'api/' + url,
////                                method: 'POST',
////                                data: $httpParamSerializerJQLike(formData),
////                                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
////                            }).then(function success(response) {
////                                $scope.showLoader = false;
////                                $('.reload').removeClass('fa-spin text-info');
////                                var resp = response.data;
////                                $rootScope[modelName] = "";
////
////                                if (resp.code === "00") {
//////                            console.log('data: ' + JSON.stringify(resp));
////                                    $rootScope[modelName] = JSON.parse(resp.data);
////                                    $scope.getStatistics(modelName, respField, respMsg, amountColumn, feeColumn);
////                                    $scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage);
////                                    $scope.end = parseInt($scope.begin + $scope.rowsPerPage);
////                                    $rootScope['filtered' + modelName] = $scope[modelName].slice($scope.begin, $scope.end);
////                                    // console.log('tesr: ' + 'filtered' + modelName + " :: " + JSON.stringify($scope['filtered' + modelName]));
////
////                                } else {
////
////                                    $rootScope['filtered' + modelName] = "";
////                                    $scope.getStatistics(modelName, respField, respMsg, amountColumn);
////                                }
//////                        console.log('field:  $scope[filtered' + modelName + ']' + JSON.stringify($scope['filtered' + modelName]));
////                                $rootScope.hideLoading();
////                            }, function error(response) {
////                                $scope.showLoader = false;
////                                $('.reload').removeClass('fa-spin text-info');
//////                        alert('Connection or Server Error. Try again');
////                                $rootScope.hideLoading();
////                                //$rootScope.toggleErrorNotification("Connection or Server Error. Try again");
////                            });
////                        }
////                    });
////                });
////            };
//
//                $scope.refreshReport2 = function (modelName, path, formName, page, paging, respField, respMsg, amountColumn, feeColumn) {
////                $('.loader').css('display', 'block');
//                    $scope.showLoader = true;
//                    $rootScope.showLoading();
//                    $scope.showFilterBox = false;
//                    $scope.pageNumber = page;
//                    $scope.endDate = moment().format('YYYY-MM-DD HH:mm');
//                    $('.reload').addClass('fa-spin text-info');
//                    $('.refresh-btn').toggleClass('fa-sync fa-recycle');
//                    $('.refresh-btn').toggleClass('fa-spin');
//                    var url = path;
//                    if ($scope.pageNumber < 1)
//                        $scope.pageNumber = 1;
//                    if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                        $scope.pageNumber = $scope.fgRecordsLastPage;
////            console.log('DATA: ' + $scope.pageNumber + ' : ' + $scope.fgRecordsLastPage);
//
//                    var formData = $('#' + formName).serializeJSON();
//                    $http({
//                        url: 'api/' + url,
//                        method: 'POST',
//                        data: $httpParamSerializerJQLike(formData),
//                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                    }).then(function success(response) {
//                        $scope.showLoader = false;
//                        $('.reload').removeClass('fa-spin text-info');
//                        var resp = response.data;
//                        if (resp.code === "00") {
////                            console.log('data: ' + JSON.stringify(resp));
//                            $rootScope[modelName] = JSON.parse(resp.data);
//                            $scope.getStatistics(modelName, respField, respMsg, amountColumn, feeColumn);
//                            $scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage);
//                            $scope.end = parseInt($scope.begin + $scope.rowsPerPage);
//                            $rootScope['filtered' + modelName] = $scope[modelName].slice($scope.begin, $scope.end);
//                            // console.log('tesr: ' + 'filtered' + modelName + " :: " + JSON.stringify($scope['filtered' + modelName]));
//
//                        } else {
//
//                            $scope[modelName] = "";
//                            $scope['filtered' + modelName] = "";
//                            $scope.getStatistics(modelName, respField, respMsg, amountColumn);
//                        }
////                        console.log('field:  $scope[filtered' + modelName + ']' + JSON.stringify($scope['filtered' + modelName]));
//                        $rootScope.hideLoading();
//                        $('.refresh-btn').toggleClass('fa-sync fa-recycle');
//                        $('.refresh-btn').toggleClass('fa-spin');
//                    }, function error(response) {
//                        $scope.showLoader = false;
//                        $('.reload').removeClass('fa-spin text-info');
////                        alert('Connection or Server Error. Try again');
//                        $rootScope.hideLoading();
//                        //$rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                    });
//                };
//                $scope.getMobileInvestigationDetails = function (message_id, unique_transid, type, app_id) {
//                    $scope.showLoader = true;
//                    $rootScope.showLoading();
//                    $http({
//                        url: 'api/MobileInvest',
//                        method: 'POST',
//                        data: $httpParamSerializerJQLike({
//                            "trans_id": message_id,
//                            "unique_transid": unique_transid,
//                            "appId": app_id,
//                            "type": type,
//                            "service": "mobileinvestdetails"
//                        }),
//                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                    }).then(function success(response) {
////                alert(response);
////                console.log(response);
//                        $scope.showLoader = false;
//                        $('.reload').removeClass('fa-spin text-info');
//                        $scope.mobileinvestdetailsResp = response.data;
//                        if ($scope.mobileinvestdetailsResp.code === "00") {
//                            $scope.mobileinvestigationsdetails = JSON.parse($scope.mobileinvestdetailsResp.data)[0];
//                            $scope.detailsid = $scope.mobileinvestigationsdetails.id;
//                            $scope.detailsappid = $scope.mobileinvestigationsdetails.appid;
//                            $scope.detailsmessage_id = $scope.mobileinvestigationsdetails.message_id;
//                            var finalHtml = `<div class="card-block font08">
//                                            <form >
//                                                <table class="table borderless">
//                                                    <tr >
//                                                        <td><label for="mobile_no">ID:</label></td>
//                                                        <td><span> ` + $scope.mobileinvestigationsdetails.id + `</span></td>
//                                                    </tr>
//                                                    <tr>
//                                                        <td><label for="messageid">Message ID:</label></td>
//                                                        <td><span> ` + $scope.mobileinvestigationsdetails.message_id + `</span></td>
//                                                    </tr>
//                                                    <tr>
//                                                        <td><label for="AppId">App ID:</label></td>
//                                                        <td><span> ` + $scope.mobileinvestigationsdetails.appid + `</span></td>
//                                                    </tr>
//
//                                                    <tr>
//                                                        <td><label for="responsemessage">Response Message</label></td>
//                                                        <td><span style="white-space: pre-line;"> ` + $scope.mobileinvestigationsdetails.response_message + `</span></td>
//                                                    </tr>
//                                                    <tr>
//                                                        <td><label for="senderid">Sender Id</label></td>
//                                                        <td><span> ` + $scope.mobileinvestigationsdetails.sender_id + `</span></td>
//                                                    </tr>
//
//                                                    <tr>
//                                                        <td><label for="Date">Date</label></td>
//                                                        <td><span> ` + $scope.mobileinvestigationsdetails.created + `</span></td>
//                                                    </tr>
//                                                    ` + ($scope.mobileinvestigationsdetails.amount !== 'null' ? `
//                                                    <tr ng-hide="mobileinvestigationsdetails.amount === 'null'">
//                                                        <td><label for="Amount">Amount</label></td>
//                                                        <td><span> ` + $scope.mobileinvestigationsdetails.amount + `</span></td>
//                                                    </tr>` : ``)
//                                    + ($scope.mobileinvestigationsdetails.vt_resp !== 'null' ? `
//                                                    <tr ng-hide="mobileinvestigationsdetails[0].vt_resp === 'null'">
//                                                        <td><label for="VtResp">Topup Response</label></td>
//                                                        <td><span> ` + $scope.mobileinvestigationsdetails.vt_resp + `</span></td>
//                                                    </tr>` : ``)
//                                    + ($scope.mobileinvestigationsdetails.vt_no !== 'null' ? `
//                                                    <tr ng-hide="mobileinvestigationsdetails.vt_no === 'null'">
//                                                        <td><label for="VtNum">Destination </label></td>
//                                                        <td><span> ` + $scope.mobileinvestigationsdetails.vt_no + `</span></td>
//                                                    </tr>` : ``)
//                                    + ($scope.mobileinvestigationsdetails.momo_no !== 'null' ? `
//                                                    <tr ng-hide="mobileinvestigationsdetails.momo_no === 'null'">
//                                                        <td><label for="MomoNum">Destination </label></td>
//                                                        <td><span> ` + $scope.mobileinvestigationsdetails.momo_no + `</span></td>
//                                                    </tr>` : ``)
//                                    + ($scope.mobileinvestigationsdetails.momo_resp !== 'null' ? `
//                                                    <tr ng-hide="mobileinvestigationsdetails.momo_resp === 'null'">
//                                                        <td><label for="MomoResp">Momo Response</label></td>
//                                                        <td><span> ` + $scope.mobileinvestigationsdetails.momo_resp + `</span></td>
//                                                    </tr>` : ``)
//                                    + ($scope.mobileinvestigationsdetails.momo_ref !== 'null' ? `
//                                                    <tr ng-hide="mobileinvestigationsdetails.momo_ref === 'null'">
//                                                        <td><label for="MomoResp">Momo Reference</label></td>
//                                                        <td><span> ` + $scope.mobileinvestigationsdetails.momo_ref + `</span></td>
//                                                    </tr>` : ``)
//                                    + `
//                                                </table>
//                                            </form>
//                                        </div>`;
//                            var compiledHtml = $compile(angular.element(finalHtml))($scope);
//                            $scope.$evalAsync(function () {
//                                Swal.fire({
//                                    title: unique_transid,
//                                    html: compiledHtml
////                                html: `<label for="status">ID: &nbsp;</label><span style="white-space: pre-line;" >` + $scope.mobileinvestigationsdetails.id + `</span><br/>\n\
////                                   <label for="status">Message ID: &nbsp;</label><span style="white-space: pre-line;" >` + $scope.mobileinvestigationsdetails.message_id + `</span><br/>\n\
////                                   <label for="status">App ID: &nbsp;</label><span style="white-space: pre-line;" >` + $scope.mobileinvestigationsdetails.appid + `</span><br/>\n\
////                                   <label for="status">Response Message: &nbsp;</label><span style="white-space: pre-line;" >` + $scope.mobileinvestigationsdetails.response_message + `</span><br/>\n\
////                                   <label for="status">Sender Id: &nbsp;</label><span style="white-space: pre-line;" >` + $scope.mobileinvestigationsdetails.comment + `</span><br/>\n\
////                                   <label for="status">Date: &nbsp;</label><span style="white-space: pre-line;" >` + $scope.mobileinvestigationsdetails.created + `</span><br/>\n\
////                                   <label for="status">Amount: &nbsp;</label><span style="white-space: pre-line;" >` + $scope.mobileinvestigationsdetails.amount + `</span><br/>\n\
////                                   <label for="status">Destination: &nbsp;</label><span style="white-space: pre-line;" >` + $scope.mobileinvestigationsdetails.momo_no + `</span><br/>\n\
////                                   <label for="status">Momo Response: &nbsp;</label><span style="white-space: pre-line;" >` + $scope.mobileinvestigationsdetails.momo_resp + `</span><br/>\n\
////                                   <label for="status">Momo Reference: &nbsp;</label><span style="white-space: pre-line;" >` + $scope.mobileinvestigationsdetails.momo_ref + `</span><br/>`
//                                });
//                            });
////                    alert($scope.mobileinvestigationsdetails);
//                        } else {
//
//                        }
//                        $rootScope.hideLoading();
//                    }, function error(response) {
//                        $scope.showLoader = false;
//                        $('.reload').removeClass('fa-spin text-info');
////                    alert('Connection or Server Error. Try again');
//                        $rootScope.hideLoading();
//                    });
//                };
//                $scope.$on('onRepeatLast', function (scope, element, attrs) {
////                console.log('last repeat');
//                    $rootScope.hideLoading();
//                    $rootScope.updateRecordsTable();
//                });
//                $scope.initializeSettings = function (path, action, modelName, type) {
//
//                    if (type === 'balance') {
//                        $http.get('api/' + path + '?action=balance&merchant=' + $scope.merchant).then(function (response) {
//                            $scope.balance = response.data;
////                        console.log('data: ' + response.data);
//                            $scope.lastBalanceDate = moment().format('DD-MM-YY H:mm');
//                        });
//                    } else if ((action === 'merchants' || action === 'products' || action === 'servicecodes' || action === 'getBankTopics') && type) {
//                        $rootScope[modelName] = "";
//                        $http.get('api/' + path + '?action=' + action + (type !== '' ? ('&' + type + '=' + $scope[type]) : ('&merchant=' + $scope.merchant))).then(function (response) {
//                            $rootScope[modelName] = response.data;
//                        });
//                    } else if ((action === 'doPbcProfiles' || action === 'rmPbcProfiles' || action === 'pcPbcProfiles' || action === 'fmPbcProfiles') && type) {
//                        $rootScope[modelName] = "";
//                        $http.get('api/' + path + '?action=' + action + ('&idNo=' + $scope[type])).then(function (response) {
//                            $rootScope[modelName] = response.data;
//                        });
//                    } else {
//                        $rootScope[modelName] = "";
//                        $http.get('api/' + path + '?action=' + action).then(function (response) {
//                            $rootScope[modelName] = response.data;
////                       console.log('banches: '+ JSON.stringify($scope.branchList));
//                        });
//                    }
//
//                    $rootScope.DisplayFirst = true;
//                    $rootScope.DisplaySecond = false;
//                    $rootScope.DisplayThird = false;
//                };
//                $scope.verifyGipTrans = function (reference, url) {
//                    $scope.ref = reference;
//                    $scope.showLoader = true;
//                    $rootScope.showLoading();
//                    $('.reload').addClass('fa-spin text-info');
//                    $scope.showFilterBox = false;
//                    $http({
//                        url: "api/" + url,
//                        method: 'POST',
//                        data: $httpParamSerializerJQLike({
//                            "uniqueTransId": reference,
//                            "service": "gipcheck"
//                        }),
//                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                    }).then(function success(response) {
//                        $scope.showLoader = false;
//                        $rootScope.hideLoading();
//                        $('.reload').removeClass('fa-spin text-info');
//                        $scope.verifyTrxsResp = response.data;
//                        if ($scope.verifyTrxsResp.code === "00") {
//                            $scope.verifyTrxs = JSON.parse($scope.verifyTrxsResp.data);
//                            Swal.fire({
//                                title: reference,
//                                html: '<label for="status">Status: &nbsp;</label><span style="white-space: pre-line;" >' + $scope.verifyTrxs.ResponseStatus + '</span><br/><span style="white-space: pre-line;" >' + $scope.verifyTrxs.comment + '</span>'
//                            });
//                        }
//                    }, function error(response) {
//                        $scope.showLoader = false;
//                        $rootScope.hideLoading();
//                        $('.reload').removeClass('fa-spin text-info');
//                        $rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                    });
//                };
//                $.contextMenu({
//                    selector: '.file-record',
////                autoHide: true,
//                    build: function (triggerElement, e) {
////                    console.log('det: ' + $(triggerElement).attr('id'));
//                        var fileIndex = $(triggerElement).attr('id');
////                    console.log('user: ' + JSON.stringify(userProfiles));
//
////                    console.log('status:' + status);
//
//                        var menuData = {
//                            "downloadConsoleFile": {"name": "Download", "icon": "download"}
//                        };
//                        return {
//                            callback: function (key, options) {
////                            window.console && console.log(m);
//                                switch (key) {
//                                    case "downloadConsoleFile":
//                                        $scope.downloadConsoleFile(fileIndex);
//                                        break;
//                                    default:
//                                        break;
//                                }
//                            },
//                            items: menuData
//                        };
//                    }
//                });
//                $scope.getValueCodeMsgMapping = function (code) {
//                    if (code === "0" || code === "00") {
//                        return "SUCCESSFUL";
//                    } else if (code === "66") {
//                        return "PENDING";
//                    } else if (!code) {
//                        return "NO RESPONSE";
//                    } else if (code === "N/A" || code === "null") {
//                        return "ERROR. Check with vendor";
//                    } else if (code === "05") {
//                        return "PENDING. Check with vendor";
//                    } else if (code === "06") {
//                        return "FAILED";
//                    }
//                    return "UNKNOWN ERROR: " + code + ". Check with vendor";
//                };
//                $scope.getValueCodeMsgMapping2 = function (code, msg, callback) {
//                    if ((callback === 'SUCCESSFUL')) {
//                        if (code === "0" || code === "00") {
//                            return "SUCCESSFUL";
//                        } else if (code === "66") {
//                            return "PENDING " + ((msg === null || msg === "null" || msg.length > 0) ? "" : msg);
//                        } else if (!code) {
//                            return "NO RESPONSE";
//                        } else if (code === "N/A" || code === "null") {
//                            return ((msg === null || msg === "null" || msg.length > 0) ? "" : msg);
//                        } else if (code === "05") {
//                            return "PENDING. Check with vendor";
//                        } else if (code === "06") {
//                            return "FAILED " + ((msg === null || msg === "null") ? "" : msg);
//                        }
//                        return "UNKNOWN ERROR: " + code + ". Check with vendor";
//                    } else {
//                        return "";
//                    }
//                };
//                $scope.getFlagMessage = function (flag) {
//                    if (!flag) {
//                        return "";
//                    }
//                    if (flag === "0")
//                        return "COMPLETED";
//                    else if (flag === "null")
//                        return " ";
//                    else if (flag === "")
//                        return " ";
//                    else if (flag === "7")
//                        return "REVERSING ";
//                    else if (flag === "8")
//                        return "REVERSED";
//                    else
//                        return "PENDING";
//                };
//                $scope.trimJP = function (text) {
//                    if (text) {
//                        if (text.length > 36) {
//                            return (text.substring(0, 35)) + " ...";
//                        }
//                    }
//                    return text;
//                };
//                $scope.trim = function (text) {
//                    if (text.length > 60) {
//                        return (text.substring(0, 62)) + " ...";
//                    }
//                    return text;
//                };
//                $scope.trimShort = function (text) {
//                    if (text) {
//                        if (text.length > 40) {
//                            return (text.substring(0, 38)) + " ...";
//                        }
//                    }
//                    return text;
//                };
//                $scope.trimVasBillId = function (text) {
//                    if (text.length > 30) {
//                        return (text.substring(0, 30)) + " ...";
//                    }
//                    return text;
//                };
//                $scope.toggleMobileAppStatus = function (client_id, phone, state, appId, index) {
//                    $scope.showLoader = true;
//                    $('.reload').addClass('fa-spin text-info');
////        var Request = new Object();
//                    $scope.showLoader = true;
//                    $scope.showFilterBox = false;
//                    Swal.fire({
//                        title: 'Are you sure?',
//                        text: "This will " + state.toLowerCase() + " " + phone,
//                        type: 'warning',
//                        showCancelButton: true,
//                        confirmButtonColor: '#3085d6',
//                        cancelButtonColor: '#d33',
//                        confirmButtonText: 'Yes, ' + state.toLowerCase() + ' it!',
//                        preConfirm: function preConfirm(login) {
////                        console.log('action: ' + login);
////                        console.log('calling');
//                            return $http({
//                                url: "api/MobileAppMgmt",
//                                method: 'POST',
//                                data: $httpParamSerializerJQLike({
//                                    "clientId": client_id,
//                                    "mobile_no": phone,
//                                    "appId": appId,
//                                    "state": state,
//                                    "service": "toggle"
//                                }),
//                                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                            }).then(function success(response) {
////                            console.log(response.data);
//                                if (response.data.code !== '00') {
//                                    throw new Error(response.data.message);
//                                }
//                                return response;
//                            }).catch(function (error) {
//                                Swal.showValidationMessage(error || 'Request Failed');
//                            });
//                        }
//                    }).then((result) => {
////                    console.log('json: ' + JSON.stringify(result));
//
//                        if (result.value) {
//                            if (result.value.data.code === '00') {
//                                $scope.toggleMobileAppActivationResp = result.value.data;
////                            console.log('Data: ' + JSON.stringify($scope.toggleMobileAppActivationResp));
//                                $scope.responseOnActivation = JSON.parse($scope.toggleMobileAppActivationResp.data);
////                            console.log('d: ' + $scope.responseOnActivation.response);
//                                if ($scope.responseOnActivation.response !== 'FAILED') {
//
////                                console.log('GIPresp: ' + $scope.responseOnActivation.response + " -- " + state.toLowerCase());
//
//                                    $('#toggle' + client_id).html($scope.responseOnActivation.status);
//                                    $scope.$evalAsync(function () {
//                                        $scope.filteredMobileAppRecs[index].active = ($scope.responseOnActivation.status.toLowerCase() === 'active' ? 'true' : 'false');
////                                    console.log('CCC:::' + JSON.stringify($scope.filteredMobileAppRecs[index]));
//                                    });
//                                    Swal.fire(state.toLowerCase() + 'd!', phone + ' has been ' + state.toLowerCase() + 'd', 'success');
//                                } else {
////                                var err = $scope.toggleMobileAppActivationResp.message || 'An Error Occured';
//                                    Swal.showValidationMessage($scope.responseOnActivation.status);
//                                }
//                            } else {
//                                Swal.showValidationMessage(result.value.data.message);
//                            }
//                            //Swal.fire({title: respTitle, text: respText, type: respType, timer: 1500});
//                        } else {
//
//                            Swal.showValidationMessage("An Error Occured");
//                        }
//                    });
//                };
//                $scope.reverse = function (id, transid, type) {
//                    var buttonId = '#' + id;
//                    $rootScope.showLoading();
//                    $(buttonId).prop("disabled", true);
//                    if (type === "ini") {
//                        $(buttonId).html("<i class='fa fa-spinner fa-spin btn-loader'></i> Saving...");
//                    } else if (type === "auth") {
//                        $(buttonId).html("<i class='fa fa-spinner fa-spin btn-loader'></i> Reversing...");
//                    }
//
//                    $http({
//                        url: 'api/Reversal',
//                        method: 'POST',
//                        data: $httpParamSerializerJQLike({
//                            "id": id,
//                            "transid": transid,
//                            "service": type === 'auth' ? 'authorizeReversal' : 'initiateReversal'
//                        }),
//                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                    }).then(function success(response) {
//                        $scope.showLoader = false;
//                        $rootScope.hideLoading();
//                        if (type === "ini") {
//                            if (response.data.message === "success") {
//                                $(buttonId).removeClass("btn-primary");
//                                $(buttonId).addClass('btn-success');
//                                $(buttonId).html('SUCCESS, AWAITING AUTHORIZATION');
//                                $(buttonId).prop('disabled', true);
//                            } else {
////                            alert(response.data);
//                                $rootScope.toggleErrorNotification(response.data.message);
//                                $(buttonId).prop('disabled', false);
//                                $(buttonId).html('TRY AGAIN');
//                            }
//                        } else if (type === "auth") {
//
//                            if (response.data.message === "PROCESSED") {
//                                $(buttonId).removeClass("btn-primary");
//                                $(buttonId).addClass('btn-success');
//                                $(buttonId).html('PROCESSED');
//                                $(buttonId).prop('disabled', true);
//                                $(buttonId + "_1").removeClass("hidden");
//                            } else {
////                            alert(response.data);
//                                $rootScope.toggleErrorNotification(response.data.message);
//                                $(buttonId).prop('disabled', false);
//                                $(buttonId).html('TRY AGAIN');
//                            }
//                        }
//                    }, function error(response) {
//                        $scope.showLoader = false;
//                        $rootScope.hideLoading();
//                    });
//                };
//                $scope.reverseSelected = function (type, choice) {
//                    var buttonId = '#reverse-btn';
//                    $rootScope.showLoading();
//                    $(buttonId).prop("disabled", true);
//                    if (type === "ini") {
//                        $(buttonId).html("<i class='fa fa-spinner fa-spin btn-loader'></i> Saving...");
//                    } else if (type === "auth") {
//                        $(buttonId).html("<i class='fa fa-spinner fa-spin btn-loader'></i> Reversing...");
//                    }
//
//                    $http({
//                        url: 'api/Reversal',
//                        method: 'POST',
//                        data: $httpParamSerializerJQLike({
//                            "transid": JSON.stringify($scope.selection),
//                            "choice": choice,
//                            "service": type === 'auth' ? 'authorizeReversal' : 'initiateReversal'
//                        }),
//                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                    }).then(function success(response) {
//                        $scope.showLoader = false;
//                        $rootScope.hideLoading();
//                        $scope.selectAll = false;
//                        $scope.selectedData = [];
//                        $scope.selection = [];
//                        if (type === "ini") {
//                            $(buttonId).html('INITIATE');
//                            $(buttonId).prop('disabled', false);
////                        if (response.data.message === "success") {
////                            $(buttonId).removeClass("btn-primary");
////                            $(buttonId).addClass('btn-success');
////                            $(buttonId).html('SUCCESS, AWAITING AUTHORIZATION');
////                            $(buttonId).prop('disabled', true);
////                        } else {
//////                            alert(response.data);
////                            $rootScope.toggleErrorNotification(response.data.message);
////                            $(buttonId).prop('disabled', false);
////                            $(buttonId).html('TRY AGAIN');
////                        }
//                            var modelName = "ErequestTrxs";
//                            var resp = response.data;
//                            $rootScope[modelName] = "";
//                            if (resp.code === "00") {
////                            console.log('data: ' + JSON.stringify(resp));
//                                $rootScope[modelName] = JSON.parse(resp.data);
//                                $scope.getStatistics(modelName);
//                                $scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage);
//                                $scope.end = parseInt($scope.begin + $scope.rowsPerPage);
//                                $rootScope['filtered' + modelName] = $scope[modelName].slice($scope.begin, $scope.end);
//                                // console.log('tesr: ' + 'filtered' + modelName + " :: " + JSON.stringify($scope['filtered' + modelName]));
//
//                            } else {
//
//                                $rootScope['filtered' + modelName] = "";
//                                $scope.getStatistics(modelName);
//                            }
//                        } else if (type === "auth") {
//
//                            if (response.data.message === "PROCESSED") {
//                                $(buttonId).removeClass("btn-primary");
//                                $(buttonId).addClass('btn-success');
//                                $(buttonId).html('PROCESSED');
//                                $(buttonId).prop('disabled', true);
//                                $(buttonId + "_1").removeClass("hidden");
//                            } else {
////                            alert(response.data);
//                                $rootScope.toggleErrorNotification(response.data.message);
//                                $(buttonId).prop('disabled', false);
//                                $(buttonId).html('TRY AGAIN');
//                            }
//                        }
//                    }, function error(response) {
//                        $scope.showLoader = false;
//                        $rootScope.hideLoading();
//                    });
//                };
//                $scope.getCount = function (count) {
//
//                    if (!isNaN(count)) {
//
//                        return accounting.formatNumber(count);
//                    }
//                    return 0;
//                };
//                $scope.getAmt = function (createAmt) {
//
//                    if (createAmt === "N/A") {
//                        return "N/A";
//                    }
//                    if (!isNaN(createAmt))
//                    {
//                        return accounting.formatMoney(parseFloat(createAmt), "");
//                    }
//                };
//                $scope.getReversalTrxns = function (page, paging) {
////                profileService.updateUserProfile();
////        console.log('chech');
////        alert($('#scbType').val());
//                    if ($('#start-date').val() && $('#end-date').val()) {
//                        $scope.showLoader = true;
//                        $rootScope.showLoading();
//                        $scope.showFilterBox = false;
//                        $scope.pageNumber = page;
//                        $('.reload').addClass('fa-spin text-info');
//                        var url = $scope.jpProxy;
//                        if ($scope.pageNumber < 1)
//                            $scope.pageNumber = 1;
//                        if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                            $scope.pageNumber = $scope.fgRecordsLastPage;
//                        if (paging) {
//                            $scope.begin = (page - 1) * parseInt($scope.rowsPerPage);
//                            $scope.end = parseInt($scope.begin) + parseInt($scope.rowsPerPage);
//                            $scope.filteredJPTrxs = $scope.JPTrxs.slice($scope.begin, $scope.end);
//                            $scope.showLoader = false;
//                            $rootScope.hideLoading();
//                            $('.reload').removeClass('fa-spin text-info');
//                        }
//                        $http({
//                            url: url,
//                            method: 'POST',
//                            data: $httpParamSerializerJQLike({
//                                "startDate": $scope.startDate,
//                                "endDate": $scope.endDate,
//                                "trans_data": $scope.trans_data,
//                                "appname": $scope.appname,
//                                "service": "transactions",
//                                "pageNumber": $scope.pageNumber,
//                                "rowsPerPage": $scope.rowsPerPage
//                            }),
//                            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                        }).then(function success(response) {
//                            $scope.showLoader = false;
//                            $rootScope.hideLoading();
//                            $('.reload').removeClass('fa-spin text-info');
//                            $scope.JPResp = response.data;
//                            if ($scope.JPResp.code === "00") {
//                                $scope.JPTrxs = JSON.parse($scope.JPResp.data);
//                                $scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage);
//                                $scope.end = parseInt($scope.begin + $scope.rowsPerPage);
//                                $scope.filteredJPTrxs = $scope.JPTrxs.slice($scope.begin, $scope.end);
//                                $scope.getStatistics('JPTrxs');
//                                $scope.getJPTransactionsTotalFee();
//                            } else {
//
//                            }
//                        }, function error(response) {
//                            $scope.showLoader = false;
//                            $rootScope.hideLoading();
//                            $('.reload').removeClass('fa-spin text-info');
//                            //$rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                        });
//                    } else {
//                        $rootScope.toggleInfoNotification("Select values for start and end date");
//                    }
//                };
//                $scope.setOrderProperty = function (propertyName) {
//                    if ($scope.orderProperty === propertyName) {
//                        $scope.orderProperty = '-' + propertyName;
//                    } else if ($scope.orderProperty === '-' + propertyName) {
//                        $scope.orderProperty = propertyName;
//                    } else {
//                        $scope.orderProperty = propertyName;
//                    }
//                };
//                $scope.initializePortalSettings = function () {
//                    $scope.getPortalSettings(1);
//                };
//                $scope.initializeProviders = function () {
//                    $http.get('api/MerchantsInfo?action=providers').then(
//                            function (response) {
//                                $scope.providerList = response.data;
//                            }
//                    );
//                };
//                $scope.initializeMobileAppIdList = function () {
//                    $http.get('api/MerchantsInfo?action=appIdList').then(
//                            function (response) {
//                                $scope.appIdList = response.data;
//                            }
//                    );
//                };
//                $scope.initializeSenderIds = function () {
//                    $http.get('api/MerchantsInfo?action=senderIds').then(
//                            function (response) {
//                                $scope.providerList = response.data;
//                            }
//                    );
//                };
//                $scope.initializeBanks = function () {
//                    $http.get('api/MerchantsInfo?action=banks').then(
//                            function (response) {
//                                $rootScope.banksList = response.data;
//                            }
//                    );
//                };
//                $scope.initializeChannels = function () {
//                    $http.get('api/MerchantsInfo?action=channels').then(
//                            function (response) {
//                                $scope.channelsList = response.data;
//                            }
//                    );
//                };
//                $scope.initializeVasGateMerchants = function () {
//                    $http.get('api/MerchantsInfo?action=vasgate').then(
//                            function (response) {
//                                $scope.vasgateMerchantList = response.data;
//                            }
//                    );
//                };
//                $scope.initializeTMCSettings = function () {
//                    $scope.record = "new";
//                    $http.get('api/MerchantsInfo?action=bankNodes').then(
//                            function (response) {
//                                $scope.TMCBankNodeList = response.data;
////                            $scope.BankNodesSet();
//                            }
//                    );
//                };
//                $scope.initializeVasGateSettings = function () {
//                    $scope.initializeBanks();
//                    $scope.initializeChannels();
//                    $scope.initializeVasGateMerchants();
//                };
//                $scope.initializeWCMerchants = function () {
////                console.log('log');
//                    $http.get('api/MerchantsInfo?action=wcMerchants').then(
//                            function (response) {
//                                $scope.wcMerchantList = response.data;
//                            }
//                    );
//                };
//                $scope.initializeGcbAgentTrans = function () {
//                    $scope.getGCBTotal();
//                    $scope.getAgentTransaction(1);
//                };
//                $scope.initializeFundgateMerchantBalance = function () {
//                    $scope.getFundgateBalanceReport(1);
//                };
//                $scope.initializeCPayMerchants = function () {
//                    var i = $scope.profile.user_code;
//                    $scope.getCPayMerchants();
//                };
//                $scope.initializeMerchantSettings = function () {
//                    $scope.getMPayMerchants();
//                    $scope.getMPayProducts(1);
//                };
//                $scope.initializeMerchantPaySettings = function () {
//                    console.log('testing');
//                    if ($scope.testEtz() || $scope.testMerchantPayAll()) {
//                        $scope.getMPayMerchants();
//                    } else {
//                        $scope.getMPayProducts();
//                    }
//                };
//                $scope.initializeUserManagementSettings = function () {
////                $scope.initializeBanks();
////                $scope.initializeUserTypes();
//                };
//                $scope.initializeUserTypes = function () {
//                    $http.get('api/MerchantsInfo?action=typeIdList').then(
//                            function (response) {
//                                $rootScope.userTypeList = response.data;
////                            console.log('types: ' + JSON.stringify($scope.typeIdList));
////                    $scope.showUserTypes = true;
//                            }
//                    );
//                };
//                $scope.initializeBucketBalance = function () {
//                    $http.get('api/WalletBalance?action=fetchBuckets').then(
//                            function (response) {
//                                $scope.bucketBalanceTypes = response.data;
//                                $scope.fetchBucketBalance();
//                            }
//                    );
//                };
//                $scope.fetchBucketBalance = function () {
//                    angular.forEach($scope.bucketBalanceTypes, function (value, index) {
//                        $http.get('api/WalletBalance?action=fetchBucketsBalance&type=' + value.alias).then(
//                                function (response) {
//                                    $scope[value.alias] = $scope.getAmt(response.data.replace(",", ""));
//                                }
//                        );
//                    });
//                };
//                $scope.calcBalanceLevel = function (balance, min) {
//                    var calcBal = $scope.calBalancePercentage(balance, min);
//                    var strokeColor = "";
//                    switch (true) {
//                        case (calcBal < 25):
//                            strokeColor = "red";
//                            break;
//                        case (calcBal < 50):
//                            strokeColor = "orange";
//                            break;
//                        case (calcBal < 75):
//                            strokeColor = "blue";
//                            break;
//                        case (calcBal > 75):
//                            strokeColor = "green";
//                            break;
//                        default:
//                            strokeColor = "brown";
//                            break;
//                    }
//                    return strokeColor;
//                };
//                $scope.calBalanceStroke = function (balance, min) {
//                    var calcBal = $scope.calBalancePercentage(balance, min);
//                    $scope['percent' + balance] = calcBal > 100 ? 100 : calcBal;
//                    return calcBal + ", 100";
//                };
//                $scope.calBalancePercentage = function (balance, min) {
//                    var calcBal = 0;
//                    if (typeof $scope[balance] === 'number' || typeof $scope[balance] === 'string') {
//                        var availableBal = (typeof $scope[balance] === 'number' ? $scope[balance].toString() : $scope[balance]);
//                        availableBal = availableBal.replace(",", "");
//                        calcBal = ((availableBal / min) * 100).toFixed(2);
//                    }
//                    return calcBal;
//                };
//                $rootScope.getUserTypeOptions = function (typeId, scopeVariable) {
////        console.log(typeId + ' ' + scopeVariable);
//                    $http.get('api/MerchantsInfo?action=typeIdListOptions&option=' + typeId).then(
//                            function (response) {
////                            var model = $parse(scopeVariable);
////                            model.assign($rootScope, response.data);
//                                $rootScope[scopeVariable] = response.data;
////                    console.log('datafield10_ ' + JSON.stringify($scope["datafield10_"]));
//                            }
//                    );
//                };
//                $scope.initReportList = function () {
//
//                    $scope.initProviderList();
//                    $scope.initSenderIdList();
//                };
//                $scope.initSenderIdList = function () {
//                    $http.get('api/MerchantsInfo?action=senderIdList').then(
//                            function (response) {
////                            var model = $parse(scopeVariable);
////                            model.assign($scope, response.data);
//
////                    console.log('datafield10_ ' + JSON.stringify($scope["datafield10_"]));
//
//                                $scope.senderIdList = response.data;
//                                if ($scope.senderIdList.length > 0) {
//                                    $scope.sender = $scope.senderIdList[0].id;
////                                console.log('ter: ' + $scope.senderIdList[0].id);
//                                }
//                            }
//                    );
//                };
//                $scope.initProviderList = function () {
//                    $http.get('api/MerchantsInfo?action=providerList').then(
//                            function (response) {
////                            var model = $parse(scopeVariable);
////                            model.assign($scope, response.data);
//
////                    console.log('datafield10_ ' + JSON.stringify($scope["datafield10_"]));
//
//                                $scope.smsProviderList = response.data;
//                                if ($scope.smsProviderList.length > 0) {
//                                    $scope.provider = $scope.smsProviderList[0].id;
////                                console.log('ter: ' + $scope.senderIdList[0].id);
//                                }
//                            }
//                    );
//                };
//                $scope.getCPayMerchants = function () {
//                    $http.get('api/MerchantsInfo?action=cpayMerchants').then(
//                            function (response) {
//                                $scope.cpayMerchantList = response.data;
//                            }
//                    );
//                };
//                $scope.getScbMerchants = function () {
//                    $http.get('api/MerchantsInfo?action=scbMerchants').then(
//                            function (response) {
//                                $scope.scbMerchantList = response.data;
//                            }
//                    );
//                };
//                $scope.getEtzMerchants = function () {
//                    $http.get('api/MerchantsInfo?action=etzMerchants').then(
//                            function (response) {
//                                $scope.etzMerchantList = response.data;
//                            }
//                    );
//                };
//                $scope.getEtzMerchantProducts = function () {
//                    $http.get('api/MerchantsInfo?action=etzMerchantProducts').then(
//                            function (response) {
//                                $scope.etzMerchantProductList = response.data;
//                            });
//                };
//                $scope.getMPayProducts = function (init) {
//                    $http.get('api/MerchantsInfo?action=mpayProducts&merchant=' + $scope.merchant + (init !== undefined ? "&init=" + init : "")).then(
//                            function (response) {
//                                $scope.merchantPayProductList = response.data;
//                            });
//                };
//                $scope.getMerchantPayProductList = function () {
//                    if ($scope.merchant !== 'ALL') {
//                        $scope.getMPayProducts();
//                        $scope.product = "ALL";
//                    } else {
//                        $scope.merchantPayProductList = "";
//                        $scope.product = "ALL";
//                    }
//                };
//                $scope.getMerchantPayMerchantList = function () {
//                    if ($scope.bank !== 'ALL') {
//                        $scope.getMPayMerchants();
//                        $scope.merchant = "ALL";
//                        $scope.product = "ALL";
//                    } else {
//                        $scope.merchantPayMerchantList = "";
//                        $scope.merchantPayProductList = "";
//                        $scope.merchant = "ALL";
//                        $scope.product = "ALL";
//                    }
//                };
//                $scope.getMPayMerchants = function () {
//                    var j = $scope.getRoleParams($scope.profile.user_code, "[2000000000000049]");
////                console.log('j: ' + $scope.bank);
//                    $http.get('api/MerchantsInfo?action=' + ($rootScope.testEtz() ? $scope.bank : j.split(":")[1]).toLowerCase() + 'Merchants').then(
//                            function (response) {
//                                $scope.merchantList = response.data;
//                            });
//                };
////    $scope.initializeKYCReport = function () {
////        $scope.currentLocation = $location.path().substring(1);
////        if ($scope.currentLocation === "KYCReport") {
////            $scope.getKYCReport(1);
////        }
////    };
//
//                $scope.goBack = function () {
//                    $scope.showagents = false;
//                };
//                $scope.verifyMobileNumber = function () {
//                    $scope.portedNetwork = "";
//                    $scope.ported = "";
//                    $scope.originalNetwork = "";
//                    $('#verifyPhone-btn').prop("disabled", true);
//                    $('#verifyPhone-btn').html("<i class='fa fa-spinner fa-spin btn-loader'></i> please wait");
//                    profileService.updateUserProfile();
//                    var url = $scope.verifyProxy;
//                    $http({
//                        url: url,
//                        method: 'POST',
//                        data: $httpParamSerializerJQLike({
//                            "mobile_no": $scope.mobile_no,
//                            "service": "numberLookup"
//                        }),
//                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                    }).then(function success(response) {
////            $scope.binUpdateResultView = true;
//                        $('#verifyPhone-btn').prop("disabled", false);
//                        $('#verifyPhone-btn').html("Check");
//                        $scope.verifyNumberResp = response.data;
//                        if ($scope.verifyNumberResp.code === "00") {
//                            $scope.verifyNumberResp = response.data;
////                console.log($scope.verifyNumberResp.data);
//                            $scope.numberDetails = $scope.verifyNumberResp.data;
//                            $scope.portedNetwork = $scope.numberDetails.split(':')[1];
//                            $scope.ported = $scope.numberDetails.split(':')[1];
//                            $scope.originalNetwork = $scope.numberDetails.split(':')[2];
//                        } else {
//                            $scope.portedNetwork = "N/A";
//                            $scope.ported = "N/A";
//                            $scope.originalNetwork = "N/A";
//                        }
//                    }, function error(response) {
//                        $scope.showLoader = false;
//                        $rootScope.hideLoading();
////            $scope.binUpdateResultView = false;
//                        $('#verifyPhone-btn').prop("disabled", false);
//                        $('#verifyPhone-btn').html("Check");
//                        $('.reload').removeClass('fa-spin text-info');
//                        //$rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                    });
//                };
//                $scope.getUsers = function (page, paging) {
//                    $scope.showLoader = true;
//                    $rootScope.showLoading();
////      $scope.showFilterBox = false;
//                    $scope.pageNumber = page;
//                    $('.reload').addClass('fa-spin text-info');
//                    var url = $scope.adminProxy;
//                    if ($scope.pageNumber < 1)
//                        $scope.pageNumber = 1;
//                    if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                        $scope.pageNumber = $scope.fgRecordsLastPage;
//                    if (paging) {
//
//                        $scope.begin = (page - 1) * parseInt($scope.rowsPerPage);
//                        $scope.end = parseInt($scope.begin) + parseInt($scope.rowsPerPage);
//                        $scope.filtereduserProfiles = $scope.userProfiles.slice($scope.begin, $scope.end);
//                        $scope.showLoader = false;
//                        $rootScope.hideLoading();
//                        $('.reload').removeClass('fa-spin text-info');
//                    } else {
//                        $http({
//                            url: url,
//                            method: 'POST',
//                            data: $httpParamSerializerJQLike({
//                                "username": $scope.username,
//                                "service": "users",
//                                "pageNumber": $scope.pageNumber,
//                                "rowsPerPage": $scope.rowsPerPage
//                            }),
//                            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                        }).then(function success(response) {
//                            $scope.showLoader = false;
//                            $rootScope.hideLoading();
//                            $scope.showCardUpdateBox = false;
//                            $('.reload').removeClass('fa-spin text-info');
//                            $scope.usersResp = response.data;
//                            $scope.userProfiles = "";
//                            console.log('log:: ');
//                            console.log('$scope.usersResp: ' + $scope.usersResp);
//                            if ($scope.usersResp.code === "00") {
//                                $scope.userProfiles = JSON.parse($scope.usersResp.data);
//                                $scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage);
//                                $scope.end = parseInt($scope.begin + $scope.rowsPerPage);
//                                $scope.filtereduserProfiles = $scope.userProfiles.slice($scope.begin, $scope.end);
//                                userProfiles = $scope.filtereduserProfiles;
//                                console.log('$scope.filtereduserProfiles: ' + $scope.filtereduserProfiles);
//                            }
//                            $scope.getStatistics('userProfiles');
//                        }, function error(response) {
//                            $scope.showLoader = false;
//                            $rootScope.hideLoading();
//                            $('.reload').removeClass('fa-spin text-info');
//                            //$rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                        });
//                    }
//                };
//                $scope.getUserData = function (key, username) {
////                profileService.updateUserProfile();
//                    $scope.showLoader = true;
////                console.log('here');
////                $rootScope.showLoading();
////      $scope.showFilterBox = false;
//
//                    $('.reload').addClass('fa-spin text-info');
//                    var url = $scope.adminProxy;
//                    $http({
//                        url: url,
//                        method: 'POST',
//                        data: $httpParamSerializerJQLike({
//                            "username": username,
//                            "type": "singleuser",
//                            "service": "users",
//                            "pageNumber": $scope.pageNumber,
//                            "rowsPerPage": $scope.rowsPerPage
//                        }),
//                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                    }).then(function success(response) {
//                        $scope.showLoader = false;
//                        $rootScope.hideLoading();
//                        $scope.showCardUpdateBox = false;
//                        $('.reload').removeClass('fa-spin text-info');
//                        $scope.usersResp = response.data;
//                        $scope.userProfiles = "";
//                        if ($scope.usersResp.code === "00") {
//                            $scope.searchUserProfile = JSON.parse($scope.usersResp.data);
//                            switch (key) {
//                                case "editUser":
//                                    $scope.userUpdateForm($scope.userIndex);
//                                    break;
//                                case "editRoles":
//                                    $scope.roleUpdateForm($scope.userIndex);
//                                    break;
//                                case "activateUser":
//                                    $scope.changeUserStatus($scope.userIndex, "activate");
//                                    break;
//                                case "deactivateUser":
//                                    $scope.changeUserStatus($scope.userIndex, "deactivate");
//                                    break;
//                                case "resetPassword":
//                                    $scope.resetUserPassword($scope.userIndex);
//                                    break;
//                                case "auditTrail":
//                                    $scope.OpenInNewTabWinBrowser("auditTrail?userId=" + $scope.filtereduserProfiles[$scope.userIndex].username);
//                                    break;
//                                default:
//                                    break;
//                            }
//                        }
//                    }, function error(response) {
//                        $scope.showLoader = false;
//                        $rootScope.hideLoading();
//                        $('.reload').removeClass('fa-spin text-info');
//                        //$rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                    });
//                };
//                $scope.hotlistCardView = function (index, hotlist) {
//                    $scope.formName = "Card Information";
//                    $scope.formType = "HOTLIST";
//                    $scope.formButton = hotlist;
//                    $scope.showCardUpdateBoxView();
//                    $scope.card_num_to_update = $scope.crdTrxs[index].card2;
//                    $scope.card_num3 = $scope.crdTrxs[index].card_num;
//                    $scope.email = $scope.crdTrxs[index].Email;
//                    $scope.firstName = $scope.crdTrxs[index].Firstname;
//                    $scope.lastName = $scope.crdTrxs[index].Lastname;
//                    $scope.from_source2 = $scope.crdTrxs[index].Phone;
//                    $scope.fax = $scope.crdTrxs[index].fax;
////        alert($scope.crdTrxs[index].fax.split(' ')[1]);
//                    $scope.IdType = $scope.crdTrxs[index].fax.split(' ')[1];
////        $scope.user_hotlist = $scope.crdTrxs[index].User_Hotlist;
//                    if ($scope.fax !== "null" || $scope.fax !== "") {
//                        $scope.CheckId();
//                    }
//                };
//                $scope.checkformType = function (formtype, hotlist) {
//                    if (formtype === "HOTLIST") {
//                        return hotlist;
//                    } else
//                        return "UPDATE";
//                };
//                $scope.checkForm = function () {
//                    if ($scope.formButton === "UPDATE") {
//                        return true;
//                    }
//                    return false;
//                };
//                $scope.searchCard = function () {
//                    if ($scope.searchTrxnKey.length > 5) {
//                        $scope.showLoader = true;
//                        $rootScope.showLoading();
//                        $('.reload').addClass('fa-spin text-info');
//                        $http({
//                            url: $scope.crdDetProxy,
//                            method: 'POST',
//                            data: $httpParamSerializerJQLike({
//                                "searchKey": $scope.searchTrxnKey,
//                                "service": "search"
//                            }),
//                            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                        }).then(function success(response) {
//                            $scope.showLoader = false;
//                            $rootScope.hideLoading();
//                            $scope.showCardUpdateBox = false;
//                            $('.reload').removeClass('fa-spin text-info');
//                            $scope.crdTrxResp = response.data;
//                            if ($scope.crdTrxResp.code === "00") {
//                                $scope.crdTrxs = JSON.parse($scope.crdTrxResp.data);
//                                $scope.firstName = $scope.crdTrxs[1].Firstname;
//                            } else {
//                                $scope.crdTrxs = "";
//                                if ($scope.crdTrxResp.message) {
//                                    $scope.errorData = "Error Message: " + $scope.crdTrxResp.message;
//                                }
//                            }
//                        }, function error(response) {
//                            $scope.showLoader = false;
//                            $rootScope.hideLoading();
//                            $('.reload').removeClass('fa-spin text-info');
//                            //$rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                        });
//                    } else {
//                        alert("Length of Transaction ID or Mobile Nos must be greater than 5");
//                    }
//                };
//                $scope.updateForm = function (index) {
//                    $scope.formName = "Card Information";
//                    $scope.formType = "UPDATE";
//                    $scope.formButton = "UPDATE";
//                    $scope.showCardUpdateBoxView();
//                    $scope.card_num_to_update = $scope.crdTrxs[index].card2;
//                    $scope.card_num3 = $scope.crdTrxs[index].card_num;
//                    $scope.email = $scope.crdTrxs[index].Email;
//                    $scope.firstName = $scope.crdTrxs[index].Firstname;
//                    $scope.lastName = $scope.crdTrxs[index].Lastname;
//                    $scope.street = $scope.crdTrxs[index].Street;
//                    $scope.city = $scope.crdTrxs[index].city;
//                    $scope.bound_work = $scope.crdTrxs[index].bound_work;
//                    $scope.from_source2 = $scope.crdTrxs[index].Phone;
//                    $scope.fax = $scope.crdTrxs[index].fax;
////        alert($scope.fax);
//                    if ($scope.fax.length > 10) {
//                        $scope.IdType = $scope.crdTrxs[index].fax.split(' ')[1];
//                        $scope.IdNumber = $scope.crdTrxs[index].fax.split(' ')[1];
//                        $scope.CheckId();
//                    }
//
//                    $scope.checkAction($scope.crdTrxs[index].action);
//                };
//                $scope.userUpdateForm = function (index) {
//                    $scope.formName = "Update User Info";
//                    $scope.formType = "UPDATE";
//                    $scope.formButton = "Modify user!";
//                    $scope.email = $scope.searchUserProfile[0].email;
//                    $scope.firstName = $scope.searchUserProfile[0].firstname;
//                    $scope.lastName = $scope.searchUserProfile[0].lastname;
//                    $scope.type_id = $scope.searchUserProfile[0].type_id;
//                    $scope.username = $scope.searchUserProfile[0].username;
//                    $scope.userId = $scope.searchUserProfile[0].user_id;
//                    $scope.mobile = $scope.searchUserProfile[0].mobile;
//                    $scope.company = $scope.searchUserProfile[0].company;
//                    $scope.usercode = $scope.searchUserProfile[0].user_code;
//                    $scope.branchcode = $scope.searchUserProfile[0].branch_code;
//                    $scope.require2FA = $scope.searchUserProfile[0].require2FA;
//                    var modifyHtml = `<div class="card-block font08">
//                                    <br/>
//                                        <form method="post" id="userUpdateForm">
//                                            <table class="table borderless" id="item_table">        
//                                                <tr>
//                                                    <td><label for="firstName" style="font-size: 0.763em; margin-bottom: 0em !important;">First Name:<i class="text-danger" style="display: inline-block;"> *</i></label></td>
//                                                    <td><input class="form-control form-control-sm text-primary" name="firstName"  ng-model="firstName" id="firstName" value="` + $scope.firstName + `" required/></td>
//                                                </tr>
//                                                <tr>
//                                                    <td><label for="lastName" style="font-size: 0.763em; margin-bottom: 0em !important;">Last Name:<i class="text-danger" style="display: inline-block;"> *</i></label></td>
//                                                    <td><input class="form-control form-control-sm text-primary" name="lastName" ng-model="lastName" id="lastName" value="` + $scope.lastName + `" required /></td>
//                                                </tr>
//                                                <tr>
//                                                    <td><label for="email" style="font-size: 0.763em; margin-bottom: 0em !important;">Email:</label></td>
//                                                    <td><input class="form-control form-control-sm text-primary" name="email" type="email" ng-model="email" id="email" value="` + $scope.email + `"  /></td>
//                                                </tr> 
//                                                <tr>
//                                                    <td><label for="mobile" style="font-size: 0.763em; margin-bottom: 0em !important;">Mobile No:<i class="text-danger" style="display: inline-block;"> *</i></label></td>
//                                                    <td><input class="form-control form-control-sm text-primary" name="mobile" ng-model="mobile" id="mobile" value="` + $scope.mobile + `" /></td>
//                                                </tr>
//                                                ` + ($scope.profile.admin === 'ETZ' ? `
//                                                <tr>
//                                                    <td><label for="bankSelect" style="font-size: 0.763em;">Bank:</label></td>
//                                                    <td>
//                                                        <select class="form-control form-control-sm item_name" id="bankSelect" name ="bankCode" ng-model="bankCode" ng-change="getBranchList()">
//                                                            
//                                                         </select>
//                                                     </td>
//                                                </tr>
//                                                <tr>
//                                                    <td><label for="branchSelect" style="font-size: 0.763em;">Branch:</label></td>
//                                                    <td>
//                                                        <select class="form-control form-control-sm item_name" id="branchSelect" name ="branchCode">
//                                                            
//                                                         </select>
//                                                     </td>
//                                                </tr>
//                                                <tr>
//                                                    <td><label for="company" style="font-size: 0.763em; margin-bottom: 0em !important;">Company:<i class="text-danger" style="display: inline-block;"> *</i></label></td>
//                                                    <td><input class="form-control form-control-sm text-primary" name="company" ng-model="company" id="company" value="` + $scope.company + `" /></td>
//                                                </tr>` : `
//                                                <tr>
//                                                    <td><label for="branchSelect" style="font-size: 0.763em;">Branch:</label></td>
//                                                    <td>
//                                                        <select class="form-control form-control-sm item_name" id="branchSelect" name ="branchCode">
//                                                            
//                                                         </select>
//                                                     </td>
//                                                </tr>`) + `
//                                                <tr>
//                                                    <td><label for="require2fa" style="font-size: 0.763em; margin-bottom: 0em !important;">Enable 2FA:</label></td>
//                                                    <td style="text-align: left;"><label class="switch"><input type="checkbox" id="require2FA" name="require2FA" ng-model="require2FA" ng-change="change2FA(require2FA)"  value="` + $scope.require2FA + `" ` + ($scope.require2FA === "true" ? "checked" : "") + `> <span class="slider"></span></label></td>
//                                                </tr>
//                                            </table>
//                                            <input type="hidden" name="service" value="modifyUser"/>
//                                            <input type="hidden" name="userId" value="` + $scope.userId + `"/>
//                                        </form>
//                                    </div>`;
//                    modifyHtml = $compile(angular.element(modifyHtml))($scope);
//                    setTimeout(function () {
////                    console.log('here: ');
////
////                    console.log('resolving' + modifyHtml);
//                        Swal.fire({
////                            title: $scope.formName,
//                            html: modifyHtml,
//                            allowEscapeKey: false,
//                            allowEnterKey: false,
//                            showCancelButton: true,
//                            confirmButtonColor: '#3085d6',
//                            cancelButtonColor: '#d33',
//                            confirmButtonText: $scope.formButton,
//                            showLoaderOnConfirm: true,
//                            onOpen() {
//
//                                $scope.populateBankSelect($scope.usercode);
//                                $scope.populateBranchSelect($scope.branchcode);
//                            },
//                            preConfirm: (login) => {
//                                $scope.require2FA = (Swal.getPopup().querySelector('#require2FA').checked);
//                                var loginForm = $('#userUpdateForm').serializeJSON();
//                                loginForm['require2FA'] = $scope.require2FA;
//                                console.log('JSON: ' + JSON.stringify(loginForm));
//                                return $http({
//                                    url: $scope.adminProxy,
//                                    method: 'POST',
//                                    data: $httpParamSerializerJQLike({
//                                        "modifyUserData": JSON.stringify(loginForm),
//                                        "service": "modifyUser"
//                                    }),
//                                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                                }).then(function success(response) {
//                                    console.log('response.data' + response.data.data);
//                                    var respData = JSON.parse(response.data.data);
//                                    if (respData.message !== 'User Modified Successfully') {
//                                        throw new Error(respData.message);
//                                    }
//                                    return response;
//                                }).catch(function (error) {
//                                    Swal.showValidationMessage(error);
//                                });
//                            },
//                            allowOutsideClick: false
//                        }).then(function (result) {
//                            if (result.value) {
//                                var respData = JSON.parse(result.value.data.data);
//                                console.log('result: ' + respData.message);
//                                if (respData.message === 'User Modified Successfully') {
//                                    Swal.fire({title: 'Success', text: 'User Info Modified Successfully!', type: 'success', allowOutsideClick: false});
//                                } else {
//                                    Swal.showValidationMessage("User Modification Failed: " + respData.message);
//                                }
//                            }
//                        });
//                    }, 500);
//                };
//                $scope.change2FA = function (value) {
//                    console.log("changed: " + value);
//                };
//                $scope.portalUpdateForm = function (index) {
//                    $scope.formName = "Update Setting";
//                    $scope.formType = "UPDATE";
//                    $scope.formButton = "Modify Setting!";
//                    var name = $scope.filteredPortalSettings[index].name;
//                    var description = $scope.filteredPortalSettings[index].description;
//                    var current_value = $scope.filteredPortalSettings[index].current_value;
//                    var id = $scope.filteredPortalSettings[index].id;
//                    var type_1 = $scope.filteredPortalSettings[index].type_1;
//                    var type_2 = $scope.filteredPortalSettings[index].type_2;
//                    var composedTime = `<div class="form-group">
//                                        <div class="input-group">
//                                            <span class="input-group-btn">
//                                                <input type="text" class="form-control" name="current_value" value="` + current_value + `">
//                                            </span>
//                                            &nbsp;&nbsp;
//                                            <span class="input-group-btn">
//                                                <select class="form-control" name="type_2">
//                                                   <option ` + (type_2 === 'minute' ? 'selected' : '') + ` value="minute">MINUTES</option>
//                                                   <option ` + (type_2 === 'hour' ? 'selected' : '') + ` value="hour">HOURS</option>
//                                                   <option ` + (type_2 === 'day' ? 'selected' : '') + ` value="day">DAYS</option>
//                                                   <option ` + (type_2 === 'month' ? 'selected' : '') + ` value="month">MONTHS</option>
//                                                   <option ` + (type_2 === 'year' ? 'selected' : '') + ` value="year">YEARS</option>
//                                                </select>
//                                            </span>
//                                        </div>	
//                                    </div>`;
//                    //FETCH USER ACCESS
//
//                    var modifyHtml = `<div class="card-block font08">
//                                    <br/>
//                                        <form method="post" id="portalUpdateForm">
//                                            <table class="table borderless" id="item_table">        
//                                                <tr>
//                                                    <td><label for="name" style="font-size: 0.763em; margin-bottom: 0em !important;">Name:<i class="text-danger" style="display: inline-block;"> *</i></label></td>
//                                                    <td><input class="form-control form-control-sm text-primary" name="name"  ng-model="name" id="name" value="` + name + `" required/></td>
//                                                </tr>
//                                                <tr>
//                                                    <td><label for="description" style="font-size: 0.763em; margin-bottom: 0em !important;">Description:<i class="text-danger" style="display: inline-block;"> *</i></label></td>
//                                                    <td><textarea class="form-control form-control-sm text-primary" name="description" ng-model="description" id="description" max-rows="3" style="resize: none;" required >` + description + `</textarea></td>
//                                                </tr>
//                                                <tr>
//                                                    <td><label for="current_value" style="font-size: 0.763em; margin-bottom: 0em !important;">Value :<i class="text-danger" style="display: inline-block;"> *</i></label></td>
//                                                    <td>
//                                                    ` + (type_1 === 'time' ? composedTime : '<input type="text" class="form-control" name="current_value" value="' + current_value + '">') + `	
//                                                    </td>                                               
//                                                </tr>
//                                            </table>
//                                            <input type="hidden" name="service" value="modifyPortalSetting"/>
//                                            <input type="hidden" name="id" value="` + id + `"/>
//                                        </form>
//                                    </div>`;
//                    Swal.fire({
////                            title: $scope.formName,
//                        html: modifyHtml,
//                        showCancelButton: true,
//                        allowEscapeKey: false,
//                        allowEnterKey: false,
//                        confirmButtonColor: '#3085d6',
//                        cancelButtonColor: '#d33',
//                        confirmButtonText: $scope.formButton,
//                        showLoaderOnConfirm: true,
//                        preConfirm: (login) => {
////                        $('#userUpdateForm').find('input').each(function () {
////                            if (!$(this).prop('required')) {
////                                console.log("NR");
////                            } else {
////                                console.log("IR");
////                            }
////                        });
//                            var loginForm = $('#portalUpdateForm').serializeJSON({checkboxUncheckedValue: "false"});
//                            console.log('JSON: ' + JSON.stringify(loginForm));
//                            return $http({
//                                url: $scope.adminProxy,
//                                method: 'POST',
//                                data: $httpParamSerializerJQLike({
//                                    "modifyPortalSettings": JSON.stringify(loginForm),
//                                    "service": "portalSettingsUpdate"
//                                }),
//                                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                            }).then(function success(response) {
//                                console.log('response.data' + response.data.data);
//                                var respData = JSON.parse(response.data.data);
//                                if (respData.message !== 'PortalSetting Modified Successfully') {
//                                    throw new Error(respData.message);
//                                }
//                                return response;
//                            }).catch(function (error) {
//                                Swal.showValidationMessage(error);
//                            });
//                        },
//                        allowOutsideClick: false
//                    }).then(function (result) {
//                        if (result.value) {
//                            var respData = JSON.parse(result.value.data.data);
//                            console.log('result: ' + respData.message);
//                            if (respData.message === 'PortalSetting Modified Successfully') {
//                                Swal.fire({title: 'Success', text: 'PortalSetting Info Modified Successfully!', type: 'success', allowOutsideClick: false});
//                            } else {
//                                Swal.showValidationMessage(respData.message);
//                            }
////                        $scope.getPortalSettings(1,1);
//                        }
//                    });
//                };
//                $scope.changePortalSettingStatus = function (index, action) {
//                    $scope.hideFilterBox();
//                    var name = portalSettings[index].name;
//                    var id = portalSettings[index].key_name;
//                    Swal.fire({
//                        title: "Are you sure ?",
////                    text: "This will " + action + " " + $scope.username,
//                        html: "This will <b>" + action + " " + name + "</b>",
//                        type: 'warning',
//                        showCancelButton: true,
//                        confirmButtonColor: '#3085d6',
//                        cancelButtonColor: '#d33',
//                        confirmButtonText: 'Yes, ' + action + ' Setting!',
//                        showLoaderOnConfirm: true,
//                        preConfirm: function preConfirm(login) {
//                            console.log('action: ' + action);
//                            return $http({
//                                url: $scope.adminProxy,
//                                method: 'POST',
//                                data: $httpParamSerializerJQLike({
//                                    "id": id,
//                                    "status": action,
//                                    "service": "changePortalSettingsStatus"
//                                }),
//                                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                            }).then(function success(response) {
//                                console.log(response.data);
//                                return response;
//                            }).catch(function (error) {
//                                Swal.showValidationMessage("Request failed ");
//                            });
//                        },
//                        allowOutsideClick: function allowOutsideClick() {
//                            return !Swal.isLoading();
//                        }
//                    }).then(function (result) {
//                        console.log('USer: ' + JSON.stringify(result));
//                        //do asyc
//                        if (result.value) {
//                            var resp = result.value.data.data;
//                            portalSettings[index].status = (resp === 'SUCCESS' ? (action === 'enable' ? 1 : 0) : portalSettings[index].status);
//                            var respText = (resp === 'SUCCESS' ? name + ' has been ' + action + 'd.' : (resp === 'FAILED' ? 'Failed to ' + action + ' ' + name + '.' : resp));
//                            var respType = (resp === 'SUCCESS' ? 'success' : 'error');
//                            var respTitle = (resp === 'SUCCESS' ? action + 'd!' : 'Failed');
////                        console.log('after: ' + JSON.stringify(userProfiles[index]));
//                            if (resp === 'SUCCESS') {
//                                $('#portalSettingStatus' + index).html((action === 'enable' ? '1' : '0'));
//                            }
//                            Swal.fire({title: respTitle, text: respText, type: respType, timer: 1500});
//                        }
//                    });
//                };
//                $scope.createFinalHtml = function (roleResp) {
//                    return $q(function (resolve, reject) {
//                        var finalHtml = "";
//                        angular.forEach(roleResp.roleInfo, function (value, index) {
////                        console.log('typeId: ' + value.id + " " + value.userRole + " " + JSON.stringify(value.roleOptions) + " " + index);
//                            finalHtml += $scope.populateRoleElement(value.id, value.userRole, value.roleOptions, index);
//                        });
////                    console.log('finalHtml: ' + JSON.stringify($scope.typeIdList));
//                        var el = angular.element(finalHtml);
////                    $rootScope.userTypeList = $scope.typeIdList;
////                    console.log('type: ' + JSON.stringify($rootScope.userTypeList));
//                        var result = $compile(el)($rootScope);
////                    $timeout(function () {
////                        console.log(el.html());
////                        alert(el.html());
////                    });
//                        setTimeout(function () {
//                            resolve(result);
//                        }, 500);
//                    });
//                };
//                $scope.roleUpdateForm = function (index) {
//                    $scope.formName = "Update User Roles";
//                    $scope.formType = "UPDATE";
//                    $scope.formButton = "Modify Roles";
//                    $scope.userId = $scope.searchUserProfile[0].user_id;
//                    $scope.username = $scope.searchUserProfile[0].username;
//                    //FETCH USER ACCESS
//                    $timeout(function () {
//                        Swal.fire({
//                            title: "Loading User Roles ...",
////                    text: "This will " + action + " " + $scope.username,
//                            html: "Fetching <b>" + $scope.username + "'s</b> data. Please wait",
//                            type: 'info',
//                            showCancelButton: true,
//                            showLoaderOnConfirm: true,
//                            onOpen() {
//                                Swal.clickConfirm();
//                            },
//                            preConfirm: function preConfirm(login) {
////                        console.log('action: ' + action);
//                                return $http({
//                                    url: $scope.adminProxy,
//                                    method: 'POST',
//                                    data: $httpParamSerializerJQLike({
//                                        "userId": $scope.userId,
//                                        "service": "fetchUserRoles"
//                                    }),
//                                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                                }).then(function success(response) {
//                                    console.log('response');
//                                    return response;
//                                }).catch(function (error) {
//                                    Swal.showValidationMessage("Request failed");
//                                });
//                            },
//                            allowOutsideClick: false
//                        }).then(function (result) {
////                        console.log('user: ' + 'JSON.stringify(result)');
//                            //do asyc
//                            var roleResp = JSON.parse(result.value.data.data);
////                        console.log('before: ' + roleResp);
////                        $rootScope.userTypeList = JSON.parse(result.value.data.data).typeIdList;
//
//                            //POPULATE FIELDS
//
//                            $scope.createFinalHtml(roleResp).then(function (compiledHtml) {
////                            console.log('creating view');
//                                var modifyHtml = `<div class="card-block font08 ">
//                                    <div class="text-right">
//                                        <button class="badge badge-success update-btn py-2 px-2 cursor-pointer addRoleElement" >ADD ROLE <i class="fa fa-user-plus"></i></button>
//                                    </div>
//                                    <br/>
//                                        <form method="post" id="userUpdateForm">
//                                            <table class="table borderless" id="item_table">
//                                                <div id="typeIdData">
//                                                    
//                                                </div>
//                                            </table>
//                                            <input type="hidden" name="service" value="modifyUserRoles"/>
//                                            <input type="hidden" name="userId" value="` + $scope.userId + `"/>
//                                        </form>
//                                    </div>`;
////                        $scope.compiledHtml = $compile(angular.element(finalHtml))($scope);
////                        setTimeout(function () {
//                                console.log('resolving');
//                                Swal.fire({
////                            title: $scope.formName,
//                                    onOpen() {
//                                        $('#typeIdData').prepend(compiledHtml);
//                                    },
//                                    html: modifyHtml,
//                                    showCancelButton: true,
//                                    confirmButtonColor: '#3085d6',
//                                    cancelButtonColor: '#d33',
//                                    confirmButtonText: $scope.formButton,
//                                    showLoaderOnConfirm: true,
//                                    preConfirm: (login) => {
////                        $('#userUpdateForm').find('input').each(function () {
////                            if (!$(this).prop('required')) {
////                                console.log("NR");
////                            } else {
////                                console.log("IR");
////                            }
////                        });
//                                        var loginForm = $('#userUpdateForm').serializeJSON({checkboxUncheckedValue: "false"});
////                                    console.log('APP: ' + JSON.stringify(loginForm));
////                                    console.log('(loginForm).item_quantity::: ' + (loginForm).item_quantity + " -- " + ((loginForm).item_quantity === null) + " --- " + ((loginForm).item_quantity === ''));
//
//                                        if ((loginForm).item_name) {
//                                            var userOptions = [];
//                                            for (var i = 0; i < (loginForm).item_name.length; i++) {
//                                                userOptions.push({
//                                                    "userOption0": (loginForm).item_name[i],
//                                                    "userOption1": ((loginForm).item_quantity ? (loginForm).item_quantity[i] : '') || ''
//                                                });
//                                            }
//                                            delete loginForm['item_name'];
//                                            delete loginForm['item_quantity'];
//                                            loginForm['userOptions'] = userOptions;
//                                        }
//
////                                    console.log('JSON: ' + JSON.stringify(loginForm));
//                                        return $http({
//                                            url: $scope.adminProxy,
//                                            method: 'POST',
//                                            data: $httpParamSerializerJQLike({
//                                                "modifyUserRoles": JSON.stringify(loginForm),
//                                                "service": "modifyUserRoles"
//                                            }),
//                                            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                                        }).then(function success(response) {
////                                        console.log('response.data' + response.data.data);
//                                            var respData = JSON.parse(response.data.data);
//                                            if (respData.message !== 'User Modified Successfully') {
//                                                throw new Error(respData.message);
//                                            }
//                                            return response;
//                                        }).catch(function (error) {
//                                            Swal.showValidationMessage(error);
//                                        });
//                                    },
//                                    allowOutsideClick: false
//                                }).then(function (result) {
//                                    if (result.value) {
//                                        var respData = JSON.parse(result.value.data.data);
////                                    console.log('result: ' + respData.message);
//                                        if (respData.message === 'User Modified Successfully') {
//                                            Swal.fire({title: 'Success', text: 'User Roles Modified Successfully!', type: 'success', allowOutsideClick: false});
//                                        } else {
//                                            Swal.showValidationMessage("User Creation Failed: " + respData.message);
//                                        }
//                                    }
//                                });
////                        }, 1000);
//                                if (!roleElementBound) {
//                                    $(document).on('click', '.addRoleElement', function (event) {
//
//                                        event.preventDefault();
//                                        $scope.addRoleElement();
//                                    });
//                                    roleElementBound = true;
//                                }
//                            });
//                        });
//                    });
//                };
////            $scope.$evalAsync(function () {
//                $.contextMenu({
//                    selector: '.user-record',
//                    autoHide: true,
//                    build: function (triggerElement, e) {
////                        console.log('det: ' + $(triggerElement).attr('id'));
//                        var status = "";
////                        $scope.$evalAsync(function () {
//
//                        $scope.userIndex = $(triggerElement).attr('id');
////                    console.log('user: ' + JSON.stringify($scope.filtereduserProfiles));
////                    console.log("index" + $scope.userIndex);
//                        status = $scope.filtereduserProfiles[$scope.userIndex].status_id ? $scope.filtereduserProfiles[$scope.userIndex].status_id : '0';
////                        });
//                        var statusIcon = ((status === '1') ? "user-lock" : "user-activate");
//                        var statusText = ((status === '1') ? "Deactivate User" : "Activate User");
//                        var statusKey = ((status === '1') ? "deactivateUser" : "activateUser");
//                        var menuData = {
//                            "editUser": {"name": "Edit User", "icon": "user-edit"},
//                            "editRoles": {"name": "Edit User Roles", "icon": "user-tag"},
//                            [statusKey]: {"name": statusText, "icon": statusIcon},
//                            "resetPassword": {"name": "Password Reset", "icon": "user-resetpassword"},
//                            "auditTrail": {"name": "View Audit Trail", "icon": "audit-trail"}
//                        };
//                        return {
//                            callback: function (key, options) {
////                            window.console && console.log(m);
//                                $scope.getUserData(key, $scope.filtereduserProfiles[$scope.userIndex].username);
//                            },
//                            items: menuData
//                        };
//                    }
////                });
//                });
//                $.contextMenu({
//                    selector: '.portal-record',
//                    build: function (triggerElement, e) {
////                        console.log('det: ' + $(triggerElement).attr('id'));
//                        var settingIndex = $(triggerElement).attr('id');
////                    console.log('user: ' + JSON.stringify(userProfiles));
//                        var status = $scope.filteredPortalSettings[settingIndex].status;
////                    console.log('status:' + status);
//                        var statusIcon = ((status === '1') ? "lock" : "unlock");
//                        var statusText = ((status === '1') ? "Disable Setting" : "Enable Setting");
//                        var statusKey = ((status === '1') ? "disableSetting" : "enableSetting");
//                        var menuData = {
//                            "editSetting": {"name": "Edit Setting", "icon": "edit"},
//                            [statusKey]: {"name": statusText, "icon": statusIcon}
//                        };
//                        return {
//                            callback: function (key, options) {
////                            window.console && console.log(m);
//                                switch (key) {
//                                    case "editSetting":
//                                        $scope.portalUpdateForm(settingIndex);
//                                        break;
//                                    case "disableSetting":
//                                        $scope.changePortalSettingStatus(settingIndex, "disable");
//                                        break;
//                                    case "enableSetting":
//                                        $scope.changePortalSettingStatus(settingIndex, "enable");
//                                        break;
//                                    default:
//                                        break;
//                                }
//                            },
//                            items: menuData
//                        };
//                    }
//                });
//                $.contextMenu({
//                    selector: '.mobileappuser-record',
//                    autoHide: true,
//                    build: function (triggerElement, e) {
////                        console.log('det: ' + $(triggerElement).attr('id'));
//                        var status = "";
////                        $scope.$evalAsync(function () {
//
//                        $scope.userIndex = $(triggerElement).attr('id');
////                    console.log('user: ' + JSON.stringify($scope.filtereduserProfiles));
////                    console.log("index" + $scope.userIndex);
//                        status = $scope.filteredMobileAppUsers[$scope.userIndex].status ? $scope.filteredMobileAppUsers[$scope.userIndex].status : 'INACTIVE';
////                        });
////                    var statusIcon = "";
////                    if (status === 'ACTIVE') {
////                        statusIcon = "user-lock";
////                        statusText = "Deactivate User";
////                        statusKey = "deactivateUser";
////                    } else if (status === 'INACTIVE') {
////                        statusIcon = "user-activate";
////                        statusText = "Activate User";
////                        statusKey = "activateUser";
////                    } else if (status === 'BLOCKED') {
////                        statusIcon = "user-activate";
////                        statusText = "Unblock User";
////                        statusKey = "unblockUser";
////                    }
//
////                    var statusIcon = ((status === 'ACTIVE') ? "user-lock" : (status === 'BLOCKED' ? "user-activate" : "user-ban"));
////                    var statusText = ((status === 'ACTIVE') ? "Deactivate User" : (status === 'BLOCKED' ? "Activate User" : "Block User"));
////                    var statusKey = ((status === 'ACTIVE') ? "deactivateUser" : (status === 'BLOCKED' ? "activateUser" : "blockUser"));
//                        var statusIcon = ((status === 'ACTIVE') ? "user-lock" : "user-activate");
//                        var statusText = ((status === 'ACTIVE') ? "Deactivate User" : "Activate User");
//                        var statusKey = ((status === 'ACTIVE') ? "deactivateUser" : "activateUser");
//                        var statusIcon2 = ((status === 'BLOCKED') ? "user-activate" : "user-ban");
//                        var statusText2 = ((status === 'BLOCKED') ? "Unblock User" : "Block User");
//                        var statusKey2 = ((status === 'BLOCKED') ? "unblockUser" : "blockUser");
//                        var menuData = {};
//                        if (status === 'BLOCKED') {
//                            menuData = {
//                                [statusKey2]: {"name": statusText2, "icon": statusIcon2},
//                                "resetPassword": {"name": "Password Reset", "icon": "user-resetpassword"},
//                                "resetProfile": {"name": "Reset Profile", "icon": "user-resetpassword"},
//                                "clearProfile": {"name": "Clear Profile", "icon": "user-resetpassword"}
//                            };
//                        } else {
//                            menuData = {
//                                [statusKey]: {"name": statusText, "icon": statusIcon},
//                                "blockUser": {"name": "Block User", "icon": "user-ban"},
//                                "resetPassword": {"name": "Password Reset", "icon": "user-resetpassword"},
//                                "resetProfile": {"name": "Reset Profile", "icon": "user-resetpassword"},
//                                "clearProfile": {"name": "Clear Profile", "icon": "user-resetpassword"}
//                            };
//                        }
//
//                        return {
//                            callback: function (key, options) {
////                            window.console && console.log(m);
//                                $scope.changeMobileAppStatus($scope.userIndex, key);
//                            },
//                            items: menuData
//                        };
//                    }
////                });
//                });
//                $scope.changeMobileAppStatus = function (index, action) {
//                    $scope.hideFilterBox();
//                    $scope.mobileNo = $scope.filteredMobileAppUsers[index].mobileNo;
//                    $scope.action = action;
//                    $scope.customerId = $scope.filteredMobileAppUsers[index].customerId;
//                    var bankCode = $scope.filteredMobileAppUsers[index].bankCode;
//                    var newState = '';
//                    if ($scope.action === 'activateUser' || $scope.action === 'unblockUser') {
//                        newState = 'ACTIVE';
//                    } else if ($scope.action === 'deactivateUser') {
//                        newState = 'INACTIVE';
//                    } else if ($scope.action === 'blockUser') {
//                        newState = 'BLOCKED';
//                    }
//                    Swal.fire({
//                        title: "Are you sure ?",
////                    text: "This will " + action + " " + $scope.username,
//                        html: "This will <b>" + action + " " + $scope.mobileNo + "</b>",
//                        type: 'warning',
//                        showCancelButton: true,
//                        confirmButtonColor: '#3085d6',
//                        cancelButtonColor: '#d33',
//                        confirmButtonText: 'Yes, ' + action + '!',
//                        showLoaderOnConfirm: true,
//                        preConfirm: function preConfirm(login) {
//                            console.log('action: ' + action);
//                            return $http({
//                                url: "api/MobileAppMgmt",
//                                method: 'POST',
//                                data: $httpParamSerializerJQLike({
//                                    "mobile_no": $scope.mobileNo,
//                                    "customer_id": $scope.customerId,
//                                    "bank_code": bankCode,
//                                    "action": $scope.action,
//                                    "service": "modifyMobileApp"
//                                }),
//                                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                            }).then(function success(response) {
////                            console.log(response.data);
//                                return response;
//                            }).catch(function (error) {
//                                Swal.showValidationMessage("Request failed ");
//                            });
//                        },
//                        allowOutsideClick: function allowOutsideClick() {
//                            return !Swal.isLoading();
//                        }
//                    }).then(function (result) {
////                    console.log('USer: ' + JSON.stringify(result));
//                        //do asyc
//                        if (result.value) {
//                            var resp = result.value.data;
////                        console.log('before: ' + JSON.stringify($scope.searchUserProfile[0]));
////                        console.log('action: ' + $scope.action);
//                            var respText = resp.message;
//                            var respType = (resp.code === '00' ? 'success' : 'error');
////                        var respTitle = (resp.code === '00' ? action + 'd!' : 'Failed');
//                            var respTitle = 'MobileApp User Modification';
////                        console.log('after: ' + JSON.stringify($scope.searchUserProfile[0]));
//                            if (resp.code === '00') {
//                                if (!(resp.message.indexOf("initiate") > -1)) {
//                                    $('#toggle' + index).html(newState);
//                                    $scope.filteredMobileAppUsers[index].status = (resp.code === '00' ? newState : $scope.filteredMobileAppUsers[index].status);
//                                    $scope.filteredMobileAppUsers[$scope.userIndex].status = newState;
//                                }
//                            }
//                            Swal.fire({title: respTitle, text: respText, type: respType, timer: 1500});
//                        }
//                    });
//                };
//                $scope.resetAppPassword = function (index) {
//                    $scope.hideFilterBox();
//                    $scope.username = $scope.searchUserProfile[0].username;
////                $scope.action = action;
//                    $scope.userId = $scope.searchUserProfile[0].user_id;
//                    Swal.fire({
//                        title: "Are you sure ?",
////                    text: "This will " + action + " " + $scope.username,
//                        html: "This will <b>reset " + $scope.username + "'s password</b>",
//                        type: 'warning',
//                        showCancelButton: true,
//                        confirmButtonColor: '#3085d6',
//                        cancelButtonColor: '#d33',
//                        confirmButtonText: 'Yes, Reset Password!',
//                        showLoaderOnConfirm: true,
//                        preConfirm: function preConfirm(login) {
////                        console.log('action: ' + action);
//                            return $http({
//                                url: $scope.adminProxy,
//                                method: 'POST',
//                                data: $httpParamSerializerJQLike({
//                                    "userId": $scope.userId,
//                                    "service": "resetPassword"
//                                }),
//                                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                            }).then(function success(response) {
//                                console.log(response.data);
//                                return response;
//                            }).catch(function (error) {
//                                Swal.showValidationMessage("Request failed ");
//                            });
//                        },
//                        allowOutsideClick: function allowOutsideClick() {
//                            return !Swal.isLoading();
//                        }
//                    }).then(function (result) {
//                        console.log('USer: ' + JSON.stringify(result));
//                        //do asyc
//                        if (result.value) {
//                            var resp = result.value.data;
//                            console.log('before: ' + JSON.stringify(resp));
////                        console.log('action: ' + $scope.action);
////                        $scope.searchUserProfile[0].status_id = (resp === 'SUCCESS' ? ($scope.action === 'activate' ? 1 : 2) : $scope.searchUserProfile[0].status_id);
////                    $scope.searchUserProfile[0].username    
//                            var respText = (resp.code === '00' ? resp.data : (resp === 'FAILED' ? 'Failed to reset ' + $scope.searchUserProfile[0].username + '\'s password.' : resp));
//                            var respType = (resp.code === '00' ? 'success' : 'error');
//                            var respTitle = (resp.code === '00' ? 'Password Reset Success' : 'Password Reset Failed');
//                            Swal.fire({title: respTitle, text: respText, type: respType, timer: 1500});
//                        }
//                    });
//                };
//                $scope.checkCms = function (type) {
//                    if (type === 'card-enquiry') {
//                        var j = $scope.getRoleParams($scope.profile.user_code, "[50]");
//                        return j.split('~').includes('2');
//                    }
//                };
//                $.contextMenu({
//                    selector: '.card-record',
//                    autoHide: true,
//                    build: function (triggerElement, e) {
////                        console.log('det: ' + $(triggerElement).attr('id'));
//                        $scope.userIndex = $(triggerElement).attr('id');
////                    console.log('user: ' + JSON.stringify($scope.filtereduserProfiles));
////                    console.log("index" + $scope.userIndex);
////                    accountUser = $scope.filteredCmsRecs;
//                        var status = $scope.filteredCmsRecs[$scope.userIndex].User_Hotlist ? $scope.filteredCmsRecs[$scope.userIndex].User_Hotlist : '0';
//                        var statusIcon = ((status !== '1') ? "user-lock" : "user-activate");
//                        var statusText = ((status !== '1') ? "Hotlist Account" : "Dehotlist Account");
//                        var statusKey = ((status !== '1') ? "hotlist" : "dehotlist");
//                        var menuData = {
//                            "update": {"name": "Update Account", "icon": "user-edit"},
//                            "addaccount": {"name": "Add Account", "icon": "user-add"},
//                            [statusKey]: {"name": statusText, "icon": statusIcon}
//                        };
//                        return {
//                            callback: function (key, options) {
////                            window.console && console.log(m);
//                                $scope.$apply($scope.showCardUserForm(key, $scope.userIndex, 'filteredCmsRecs'));
//                            },
//                            items: menuData
//                        };
//                    }
//                });
//                $.contextMenu({
//                    selector: '.file-record',
//                    build: function (triggerElement, e) {
////                    console.log('det: ' + $(triggerElement).attr('id'));
//                        var fileIndex = $(triggerElement).attr('id');
////                    console.log('user: ' + JSON.stringify(userProfiles));
//
////                    console.log('status:' + status);
//
//                        var menuData = {
//                            "downloadConsoleFile": {"name": "Download", "icon": "edit"}
//                        };
//                        return {
//                            callback: function (key, options) {
////                            window.console && console.log(m);
//                                switch (key) {
//                                    case "downloadConsoleFile":
//
//                                        $scope.downloadConsoleFile(fileIndex);
//                                        break;
//                                    default:
//                                        break;
//                                }
//                            },
//                            items: menuData
//                        };
//                    }
//                });
//                $scope.downloadConsoleFile = function (fileIndex) {
////                console.log('wer');
//                    $rootScope.$evalAsync(function () {
////                    console.log('ds');
//                        var file = $rootScope.filteredConsoleFiles[fileIndex].filePath;
//                        var formData = {};
//                        formData['fileId'] = file;
//                        formData['service'] = "downloadFile";
//                        $http({
//                            url: 'api/ConsoleFiles',
//                            method: 'POST',
//                            data: $httpParamSerializerJQLike(formData),
//                            responseType: 'arraybuffer',
//                            transformResponse: jsonBufferToObject,
//                            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                        }).then(function success(response) {
//                            $scope.showLoader = false;
//                            $('.reload').removeClass('fa-spin text-info');
//                            $rootScope.hideLoading();
//                        }, function error(response) {
//                            $scope.showLoader = false;
//                            $('.reload').removeClass('fa-spin text-info');
////                        alert('Connection or Server Error. Try again');
//                            $rootScope.hideLoading();
//                            //$rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                        });
//                    });
//                };
//                $rootScope.getTypeIdOptions = function (option) {
////                console.log('came here');
//                    $rootScope.getUserTypeOptions($rootScope.values[option], 'data' + option);
//                    if ($('#data' + option).length < 1) {
//                        $rootScope.addTypeIdOptions(option);
//                    }
//                };
//                $scope.createSelectBox = function (selected) {
//                    return $q(function (resolve, reject) {
//                        $http.get('api/MerchantsInfo?action=banks').then(
//                                function (response) {
//                                    $rootScope.banksList = response.data;
//                                    var options = `<tr>
//                                <td><label for="banks" style="font-size: 0.763em;">Bank</label></td>
//                                <td>
//                                    <select class="form-control form-control-sm" name="bankCode" ng-model="bankCode" id="banks">`;
//                                    angular.forEach($rootScope.banksList, function (value, index) {
////                    console.log('bank: ' + value.issuer_code + ' : ' + value.issuer_name);
//                                        if (selected) {
//                                            options += '<option ' + (selected === value.issuer_code ? ' selected ' : '') + ' value ="' + value.issuer_code + '">' + value.issuer_name + ' </option>';
//                                        } else {
//                                            if (value.issuer_code !== '000') {
//                                                options += '<option selected value ="000">ETZ ADMIN BANK</option>';
//                                                options += '<option value ="' + value.issuer_code + '">' + value.issuer_name + '</option>';
//                                            }
//                                        }
//                                    });
//                                    options += `</select>    
//                                </td>
//                               </tr>`;
//                                    resolve(options);
//                                }
//                        );
//                    });
//                };
//                $scope.populateBankSelect = function (usercode) {
//                    if (!usercode) {
//                        usercode = '000';
//                    }
//
//                    $rootScope.$evalAsync(function () {
//                        var ele = document.getElementById('bankSelect');
//                        if (ele) {
//                            var html = "";
//                            for (var i = 0; i < $rootScope.banksList.length; i++) {
//                                // POPULATE SELECT ELEMENT WITH JSON.
////                        console.log($rootScope.banksList[i]['issuer_code']);
//
//                                html +=
//                                        '<option value="' + $rootScope.banksList[i]['issuer_code'] + '" ' + ($rootScope.banksList[i]['issuer_code'] === usercode ? 'selected' : '') + ' >' + $rootScope.banksList[i]['issuer_name'] + '</option>';
//                            }
////                    console.log('html: ' + html);
//                            ele.innerHTML = html;
//                        }
//
//                    });
//                };
//                $scope.populateBranchSelect = function (usercode) {
//                    if (!usercode) {
//                        usercode = 'ALL';
//                    }
//
//                    $scope.$evalAsync(function () {
//                        var ele = document.getElementById('branchSelect');
//                        if (ele) {
//                            var html = "";
//                            html +=
//                                    '<option value="ALL" ' + ('ALL' === usercode ? 'selected' : '') + '>ALL</option>';
//                            for (var i = 0; i < $rootScope.branchList.length; i++) {
//                                // POPULATE SELECT ELEMENT WITH JSON.
////                        console.log($rootScope.banksList[i]['issuer_code']);
//
//                                html += '<option value="' + $rootScope.branchList[i]['issuer_code'] + '" '
//                                        + ($rootScope.branchList[i]['issuer_code'] === usercode ? 'selected' : '') + ' >'
//                                        + $rootScope.branchList[i]['issuer_name'] + '</option>';
//                            }
////                    console.log('html: ' + html);
//                            ele.innerHTML = html;
//                        }
//
//                    });
//                };
//                $scope.getBranchList = function (bankcode) {
//                    if (!bankcode) {
//                        bankcode = $('#bankSelect').val();
//                    }
//                    console.log('jgj: ' + bankcode);
//                    $http.get('api/Admin?action=branches&bankCode=' + bankcode).then(function (response) {
//                        $rootScope.branchList = response.data;
////                       console.log('banches: '+ JSON.stringify($scope.branchList));
//                    });
//                };
//                $scope.showCreateUserForm = function () {
//                    $scope.formName = "Create New User";
//                    $scope.formType = "CreateUser";
//                    $scope.formButton = "Create User";
//                    $scope.email = "";
//                    $scope.firstName = "";
//                    $scope.lastName = "";
//                    $scope.type_id = "";
//                    $scope.require2FA = false;
//                    var createHtml = `<div class="card-block font08 addUserForm">
//                                    <div class="text-right">
//                                        <button class="badge badge-success update-btn py-2 px-2 cursor-pointer addRoleElement">ADD ROLE <i class="fa fa-user-plus"></i></button>
//                                    </div>
//                                    <br/>
//                                        <form method="post" id="userUpdateForm">
//                                            <table class="table borderless" id="item_table">        
//                                                <tr>
//                                                    <td><label for="firstName" style="font-size: 0.763em;">First Name:<i class="text-danger"> *</i></label></td>
//                                                    <td><input class="form-control form-control-sm text-primary" name="firstName"  ng-model="firstName" id="firstName" required/></td>
//                                                </tr>
//                                                <tr>
//                                                    <td><label for="lastName" style="font-size: 0.763em;">Last Name:<i class="text-danger"> *</i></label></td>
//                                                    <td><input class="form-control form-control-sm text-primary" name="lastName" ng-model="lastName" id="lastName" required /></td>
//                                                </tr>
//                                                <tr>
//                                                    <td><label for="username" style="font-size: 0.763em;">Username:<i class="text-danger"> *</i></label></td>
//                                                    <td class="inner-addon right-addon username-addon">
//                                                        <i class="fa fa-check-circle available" style="color:green; display:none;"></i>
//                                                        <i class="fa fa-times-circle unavailable" style="color:red; display:none;" ></i>
//                                                        <input type="text" class="form-control" id ="newUsername" name="newUsername" ng-model="newUsername" />
//                                                    </td>
//                                                </tr>
//                                                <tr>
//                                                    <td><label for="email" style="font-size: 0.763em;">Email:</label></td>
//                                                    <td><input class="form-control form-control-sm text-primary" name="email" type="email" ng-model="email" id="email" /></td>
//                                                </tr> 
//                                                <tr>
//                                                    <td><label for="mobile" style="font-size: 0.763em;">Mobile:</label></td>
//                                                    <td><input class="form-control form-control-sm text-primary" name="mobile" ng-model="mobile" id="mobile" /></td>
//                                                </tr>
//                                                ` + ($scope.profile.admin === 'ETZ' ? `
//                                                <tr>
//                                                    <td><label for="bankSelect" style="font-size: 0.763em;">Bank:</label></td>
//                                                    <td>
//                                                        <select class="form-control form-control-sm item_name" id="bankSelect" name ="bankCode" ng-model="bankCode" ng-change="getBranchList()">
//                                                            
//                                                         </select>
//                                                     </td>
//                                                </tr>
//                                                 <tr>
//                                                    <td><label for="branchSelect" style="font-size: 0.763em;">Branch:</label></td>
//                                                    <td>
//                                                        <select class="form-control form-control-sm item_name" id="branchSelect" name ="branchCode">
//                                                            
//                                                         </select>
//                                                     </td>
//                                                </tr>
//                                                <tr>
//                                                    <td><label for="company" style="font-size: 0.763em;">Company:</label></td>
//                                                    <td><input class="form-control form-control-sm text-primary" name="company" ng-model="company" id="company" /></td>
//                                                </tr>
//                                                ` : `
//                                                <tr>
//                                                    <td><label for="branchSelect" style="font-size: 0.763em;">Branch:</label></td>
//                                                    <td>
//                                                        <select class="form-control form-control-sm item_name" id="branchSelect" name ="branchCode">
//                                                            
//                                                         </select>
//                                                     </td>
//                                                </tr>`) + `
//                                                <tr>
//                                                    <td><label for="require2fa" style="font-size: 0.763em; margin-bottom: 0em !important;">Enable 2FA:</label></td>
//                                                    <td style="text-align: left;"><label class="switch"><input type="checkbox" ng-model="require2FA" id="require2FA" name="require2FA"><span class="slider"></span></label></td>
//                                                </tr>
//                                                <div id="typeIdData"></div>
//                                            </table>
//                                            <input type="hidden" name="service" value="createUser"/>
//                                        </form>
//                                    </div>`;
//                    createHtml = $compile(angular.element(createHtml))($scope);
//                    $scope.$evalAsync(function () {
//                        Swal.fire({
////                    title: $scope.formName,
//                            html: createHtml,
//                            showCancelButton: true,
//                            confirmButtonColor: '#3085d6',
//                            cancelButtonColor: '#d33',
//                            confirmButtonText: $scope.formButton,
//                            showLoaderOnConfirm: true,
//                            onOpen() {
//                                $scope.populateBankSelect();
//                                $scope.populateBranchSelect();
//                            },
//                            preConfirm: (login) => {
//                                $scope.require2FA = (Swal.getPopup().querySelector('#require2FA').checked);
//                                var loginForm = $('#userUpdateForm').serializeJSON();
//                                loginForm['require2FA'] = $scope.require2FA;
////                        console.log('hg: ' + JSON.stringify(loginForm));
////                        console.log((loginForm).item_name.length);
//                                if ((loginForm).item_name) {
//                                    var userOptions = [];
//                                    for (var i = 0; i < (loginForm).item_name.length; i++) {
//                                        if (!(loginForm).item_quantity) {
//                                            userOptions.push({
//                                                "userOption0": (loginForm).item_name[i]
//                                            });
//                                        } else {
//                                            userOptions.push({
//                                                "userOption0": (loginForm).item_name[i],
//                                                "userOption1": (loginForm).item_quantity[i]
//                                            });
//                                        }
//                                    }
//                                    delete loginForm['item_name'];
//                                    delete loginForm['item_quantity'];
//                                    loginForm['userOptions'] = userOptions;
//                                }
//
//
//                                console.log('JSON: ' + JSON.stringify(loginForm));
//                                return $http({
//                                    url: $scope.adminProxy,
//                                    method: 'POST',
//                                    data: $httpParamSerializerJQLike({
//                                        "createUserData": JSON.stringify(loginForm),
//                                        "service": "createUser"
//                                    }),
//                                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                                }).then(function success(response) {
////                            console.log('response.data' + response.data.data);
//                                    var respData = JSON.parse(response.data.data);
//                                    if (respData.message !== 'User Created Successfully') {
//                                        throw new Error(respData.message);
//                                    }
//                                    return response;
//                                }).catch(function (error) {
//                                    Swal.showValidationMessage(error);
//                                });
//                            },
//                            allowOutsideClick: false
//                        }).then((result) => {
//                            if (result.value) {
//
//                                var respData = JSON.parse(result.value.data.data);
//                                console.log('result: ' + respData.message);
//                                if (respData.message === 'User Created Successfully') {
//                                    Swal.fire({title: 'User Created Successfully!', text: 'Username: ' + respData.username + ' password: ' + respData.password, type: 'success', allowOutsideClick: false});
//                                } else {
//                                    Swal.showValidationMessage("User Creation Failed: " + respData.message);
//                                }
//
//                            }
//                        });
//                    });
//                    if (!roleElementBound) {
//                        $(document).on('click', '.addRoleElement', function (event) {
//
//                            event.preventDefault();
//                            $scope.addRoleElement();
//                        });
//                        roleElementBound = true;
//                    }
//                };
//                $scope.getPortalSettings = function (page, paging) {
//                    $scope.updateApp();
//                    $scope.showLoader = true;
//                    $rootScope.showLoading();
//                    $scope.showFilterBox = false;
//                    $scope.pageNumber = page;
//                    $('.reload').addClass('fa-spin text-info');
//                    var url = $scope.adminProxy;
//                    if ($scope.pageNumber < 1)
//                        $scope.pageNumber = 1;
//                    if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                        $scope.pageNumber = $scope.fgRecordsLastPage;
//                    if (paging) {
//
//                        $scope.begin = (page - 1) * parseInt($scope.rowsPerPage);
//                        $scope.end = parseInt($scope.begin) + parseInt($scope.rowsPerPage);
//                        $scope.filteredPortalSettings = $scope.PortalSettings.slice($scope.begin, $scope.end);
//                        $scope.showLoader = false;
//                        $rootScope.hideLoading();
//                        $('.reload').removeClass('fa-spin text-info');
//                    } else {
//                        $http({
//                            url: url,
//                            method: 'POST',
//                            data: $httpParamSerializerJQLike({
//                                "setting_name": $scope.transtype,
//                                "service": "portalSettings"
//                            }),
//                            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                        }).then(function success(response) {
//                            $scope.showLoader = false;
//                            $rootScope.hideLoading();
//                            $('.reload').removeClass('fa-spin text-info');
//                            $scope.PortalSettingsResp = response.data;
//                            if ($scope.PortalSettingsResp.code === "00") {
//                                $scope.PortalSettings = JSON.parse($scope.PortalSettingsResp.data);
//                                $scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage);
//                                $scope.end = parseInt($scope.begin + $scope.rowsPerPage);
//                                $scope.filteredPortalSettings = $scope.PortalSettings.slice($scope.begin, $scope.end);
//                                portalSettings = $scope.filteredPortalSettings;
//                            }
//                            $scope.getStatistics('PortalSettings');
//                        }, function error(response) {
//                            $scope.showLoader = false;
//                            $rootScope.hideLoading();
//                            $('.reload').removeClass('fa-spin text-info');
//                            //$rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                        });
//                    }
//                };
//                $scope.showSetting = function (setting, index) {
//                    $scope.username = userProfiles[index].username;
//                    $scope.action = action;
//                    $scope.userId = userProfiles[index].user_id;
//                    Swal.fire({
////                    title: "Are you sure ?",
////                    text: "This will " + action + " " + $scope.username,
//                        html: "This will <b>" + action + " " + $scope.username + "</b>",
//                        showCancelButton: true,
//                        confirmButtonColor: '#3085d6',
//                        cancelButtonColor: '#d33',
//                        confirmButtonText: 'Yes, ' + action + ' user!',
//                        showLoaderOnConfirm: true,
//                        preConfirm: function preConfirm(login) {
//                            console.log('action: ' + action);
//                            return $http({
//                                url: $scope.adminProxy,
//                                method: 'POST',
//                                data: $httpParamSerializerJQLike({
//                                    "setting_name": $scope.userId,
//                                    "status": $scope.action,
//                                    "service": "updateSettings"
//                                }),
//                                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                            }).then(function success(response) {
//                                console.log(response.data);
//                                return response;
//                            }).catch(function (error) {
//                                Swal.showValidationMessage("Request failed ");
//                            });
//                        },
//                        allowOutsideClick: function allowOutsideClick() {
//                            return !Swal.isLoading();
//                        }
//                    }).then(function (result) {
//                        console.log('USer: ' + JSON.stringify(result));
//                        //do asyc
//                        var resp = result.value.data.data;
//                        console.log('before: ' + JSON.stringify(userProfiles[index]));
//                        console.log('action: ' + $scope.action);
//                        userProfiles[index].status_id = (resp === 'SUCCESS' ? ($scope.action === 'activate' ? 1 : 2) : userProfiles[$scope.userId].status_id);
//                        var respText = (resp === 'SUCCESS' ? $scope.username + ' has been ' + action + 'd.' : (resp === 'FAILED' ? 'Failed to ' + action + ' ' + $scope.username + '.' : resp));
//                        var respType = (resp === 'SUCCESS' ? 'success' : 'error');
//                        var respTitle = (resp === 'SUCCESS' ? action + 'd!' : 'Failed');
//                        console.log('after: ' + JSON.stringify(userProfiles[index]));
//                        if (resp === 'SUCCESS') {
//                            $('#user' + index).html(($scope.action === 'activate' ? 'active' : 'inactive'));
//                        }
//                        Swal.fire({title: respTitle, text: respText, type: respType, timer: 1500});
//                    });
//                };
//                $scope.authorizeForm = function (index) {
//                    $scope.formName = "Card Information";
//                    $scope.formType = "AUTHORIZE";
//                    $scope.formButton = "AUTHORIZE";
//                    $('#deny').prop("disabled", false);
//                    $('#authorize').prop("disabled", false);
//                    $scope.showCardUpdateBoxView();
//                    $scope.card_num_to_update = $scope.cmsReq[index].card2;
//                    $scope.card_num3 = $scope.cmsReq[index].card_num;
//                    $scope.email = $scope.cmsReq[index].Email;
//                    $scope.firstName = $scope.cmsReq[index].Firstname;
//                    $scope.lastName = $scope.cmsReq[index].Lastname;
//                    $scope.street = $scope.cmsReq[index].Street;
//                    $scope.city = $scope.cmsReq[index].city;
////        $scope.bound_work = $scope.cmsReq[index].bound_work;
//                    $scope.from_source2 = $scope.cmsReq[index].Phone;
//                    $scope.fax = $scope.cmsReq[index].fax;
//                    if ($scope.fax) {
////             console.log('use me');
//                        $scope.IdType = $scope.cmsReq[index].fax.split(' ')[1];
//                        $scope.IdNumber = $scope.cmsReq[index].fax.split(' ')[1];
//                        $scope.CheckId();
//                    }
//
//                    $scope.reason = $scope.cmsReq[index].reason;
//                    $scope.action = $scope.cmsReq[index].action;
//                    $scope.intiator = $scope.cmsReq[index].initiated_by;
//                    $scope.initiated_date = $scope.cmsReq[index].date_initiated;
//                    $scope.id = $scope.cmsReq[index].id;
//                    $scope.checkAction($scope.cmsReq[index].action);
//                };
//                $scope.authoriseForm = function (index) {
//
////        $('.example2').on('click', function () {
//
//                    $scope.formName = "Card Information";
//                    $scope.formType = "AUTHORIZE";
//                    $scope.formButton = "AUTHORIZE";
//                    $('#deny').prop("disabled", false);
//                    $('#authorize').prop("disabled", false);
//                    $scope.showCardUpdateBoxView();
//                    $scope.card_num_to_update = $scope.cmsReq[index].card2;
//                    $scope.card_num3 = $scope.cmsReq[index].card_num;
//                    $scope.email = $scope.cmsReq[index].Email;
//                    $scope.firstName = $scope.cmsReq[index].Firstname;
//                    $scope.lastName = $scope.cmsReq[index].Lastname;
//                    $scope.street = $scope.cmsReq[index].Street;
//                    $scope.city = $scope.cmsReq[index].city;
////        $scope.bound_work = $scope.cmsReq[index].bound_work;
//                    $scope.from_source2 = $scope.cmsReq[index].Phone;
//                    $scope.fax = $scope.cmsReq[index].fax;
//                    if ($scope.fax) {
////             console.log('use me');
//                        $scope.IdType = $scope.cmsReq[index].fax.split(' ')[1];
//                        $scope.IdNumber = $scope.cmsReq[index].fax.split(' ')[1];
//                        $scope.CheckId();
//                    }
//
//                    $scope.reason = $scope.cmsReq[index].reason;
//                    $scope.action = $scope.cmsReq[index].action;
//                    $scope.intiator = $scope.cmsReq[index].initiated_by;
//                    $scope.initiated_date = $scope.cmsReq[index].date_initiated;
//                    $scope.id = $scope.cmsReq[index].id;
//                    $scope.checkAction($scope.cmsReq[index].action);
//                    $.confirm({
//                        title: 'Confirm!',
//                        closeIcon: true,
//                        closeIconClass: 'fa fa-close',
//                        columnClass: 'medium',
//                        draggable: true,
//                        content: function () {
//                            var self = this;
//                            return $.ajax({
//                                url: 'bower.json',
//                                dataType: 'json',
//                                method: 'post',
//                                data: data
//                            }).done(function (response) {
//                                self.setContent('' +
//                                        '<form ng-submit="updateCard();">' +
//                                        '<div class="row" data-ng-init="CheckId()">' +
//                                        '   <div class="col-9">' +
//                                        '      <table class="table borderless">    ' +
//                                        '         <tr>' +
//                                        '            <td><label>Card Number:</label></td>' +
//                                        '          <td><label class="">&nbsp;&nbsp;&nbsp;' + $scope.card_num3 + '</label></td>' +
//                                        '     </tr>' +
//                                        '    <tr>' +
//                                        '       <td><label for="firstName">First Name:</label></td>' +
//                                        '      <td><label class="">&nbsp;&nbsp;&nbsp;' + $scope.firstName + '</label></td>' +
//                                        ' </tr>' +
//                                        '<tr>' +
//                                        '   <td><label for="lastName">Last Name:</label></td>' +
//                                        '      <td><label class="">&nbsp;&nbsp;&nbsp;' + $scope.lastName + '</label></td>' +
//                                        '</tr>' +
//                                        '<tr>' +
//                                        '   <td><label for="from_source">Mobile No:</label></td>' +
//                                        '      <td><label class="">&nbsp;&nbsp;&nbsp;' + $scope.from_source2 + '</label></td>' +
//                                        '</tr> ' +
//                                        '<tr>' +
//                                        '  <td><label for="reason">Reason:</label></td>' +
//                                        '  <td><textarea class="form-control form-control-sm text-primary" name="reason" ng-model="reason" id="reason" max-rows="3" style="resize:none;" disabled> ' + $scope.reason + '</textarea></td>' +
//                                        '</tr>' +
//                                        '<tr>' +
//                                        '<td><label for="fax">ID:</label></td>' +
//                                        '<td>' +
//                                        '<div class="input-group">' +
//                                        '<input type="text" class="form-control form-control-sm text-primary" value="' + $scope.fax + '" disabled>' +
//                                        '<span class="input-group-btn">' +
//                                        ' <button class="btn btn-success btn-sm">' +
//                                        '   Verify' +
//                                        '</button>' +
//                                        '</span>' +
//                                        '</div>' +
//                                        '</td>' +
//                                        '</tr>' +
//                                        '</table>' +
//                                        ' </div>' +
//                                        ' <div class="col-3">' +
//                                        '<img id="verifyIDimage" src="http://ssl.gstatic.com/accounts/ui/avatar_2x.png" class="avatar img-circle img-thumbnail" alt="avatar">' +
//                                        ' </div>' +
//                                        '</div>' +
////                    '<div class="row">' +
////                    '<div class="col col-6">' +
////                    '<button id="authorize" type="submit" class="btn btn-success btn-block filter-btn" ng-click="setDecision(\'AUTHORIZE\')">AUTHORIZE</button>' +
////                    '</div>' +
////                    '<div class="col col-6">' +
////                    '<button id="deny" type="submit" class="btn btn-danger btn-block filter-btn" ng-click="setDecision(\'DENY\')">DENY</button>' +
////                    '</div>' +
//                                        '</div>' +
//                                        '</form>');
////                    self.setContentAppend('<br>Version: ' + response.version);
////                    self.setTitle(response.name);
//                            }).fail(function () {
//                                self.setContent('' +
//                                        '<form ng-submit="updateCard();">' +
//                                        '<div class="row" data-ng-init="CheckId()">' +
//                                        '   <div class="col-9">' +
//                                        '      <table class="table borderless">    ' +
//                                        '         <tr>' +
//                                        '            <td><label>Card Number:</label></td>' +
//                                        '          <td><label class="">&nbsp;&nbsp;&nbsp;' + $scope.card_num3 + '</label></td>' +
//                                        '     </tr>' +
//                                        '    <tr>' +
//                                        '       <td><label for="firstName">First Name:</label></td>' +
//                                        '      <td><label class="">&nbsp;&nbsp;&nbsp;' + $scope.firstName + '</label></td>' +
//                                        ' </tr>' +
//                                        '<tr>' +
//                                        '   <td><label for="lastName">Last Name:</label></td>' +
//                                        '      <td><label class="">&nbsp;&nbsp;&nbsp;' + $scope.lastName + '</label></td>' +
//                                        '</tr>' +
//                                        '<tr>' +
//                                        '   <td><label for="from_source">Mobile No:</label></td>' +
//                                        '      <td><label class="">&nbsp;&nbsp;&nbsp;' + $scope.from_source2 + '</label></td>' +
//                                        '</tr> ' +
//                                        '<tr>' +
//                                        '  <td><label for="reason">Reason:</label></td>' +
//                                        '  <td><textarea class="form-control form-control-sm text-primary" name="reason" ng-model="reason" id="reason" max-rows="3" style="resize:none;" disabled> ' + $scope.reason + '</textarea></td>' +
//                                        '</tr>' +
//                                        '<tr>' +
//                                        '<td><label for="fax">ID:</label></td>' +
//                                        '<td>' +
//                                        '<div class="input-group">' +
//                                        '<input type="text" class="form-control form-control-sm text-primary" value="' + $scope.fax + '" disabled>' +
//                                        '<span class="input-group-btn">' +
//                                        ' <button class="btn btn-success btn-sm">' +
//                                        '   Verify' +
//                                        '</button>' +
//                                        '</span>' +
//                                        '</div>' +
//                                        '</td>' +
//                                        '</tr>' +
//                                        '</table>' +
//                                        ' </div>' +
//                                        ' <div class="col-3">' +
//                                        '<img id="verifyIDimage" src="http://ssl.gstatic.com/accounts/ui/avatar_2x.png" class="avatar img-circle img-thumbnail" alt="avatar">' +
//                                        ' </div>' +
//                                        '</div>' +
////                    '<div class="row">' +
////                    '<div class="col col-6">' +
////                    '<button id="authorize" type="submit" class="btn btn-success btn-block filter-btn" ng-click="setDecision(\'AUTHORIZE\')">AUTHORIZE</button>' +
////                    '</div>' +
////                    '<div class="col col-6">' +
////                    '<button id="deny" type="submit" class="btn btn-danger btn-block filter-btn" ng-click="setDecision(\'DENY\')">DENY</button>' +
////                    '</div>' +
//                                        '</div>' +
//                                        '</form>');
//                            });
//                        },
//                        buttons: {
//                            confirm: function () {
//                                $.alert('Confirmed!');
//                            },
//                            cancel: function () {
//                                $.alert('Canceled!');
//                            },
//                            somethingElse: {
//                                text: 'Something else',
//                                btnClass: 'btn-blue',
//                                keys: [
//                                    'enter',
//                                    'shift'
//                                ],
//                                action: function () {
//                                    this.$content; // reference to the content
//                                    $.alert('Something else?');
//                                }
//                            }
//                        }
//                    });
////        });
//
//                };
//                $scope.CheckId = function () {
////        $('.reload').addClass('fa-spin text-info');
////        alert($('#fax').val());
////                $('#checkId').prop("disabled", true);
//                    $scope.searching_id = true;
//                    $scope.id_verified = '';
//                    $http({
//                        url: 'api/Cms',
//                        method: 'POST',
//                        data: $httpParamSerializerJQLike({
//                            "id_type": $('#id_type').val() || $scope.id_type,
//                            "id_number": $('#id_number').val() || $scope.id_number,
//                            "service": "verifyID"
//                        }),
//                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                    }).then(function success(response) {
//                        $scope.searching_id = false;
//                        $('#checkId').html("Check");
//                        $('.reload').removeClass('fa-spin text-info');
//                        $scope.verifyIDResp = response.data;
//                        if ($scope.verifyIDResp.code === "00" && $scope.verifyIDResp.data) {
//                            $scope.verified = JSON.parse($scope.verifyIDResp.data);
////                console.log($scope.verified.message);
////                console.log($scope.verified.message.substring(0, $scope.verified.message.indexOf("_")));
//                            $scope.fullName = $scope.verified.message.split('_')[0];
////                console.log('fullname: ' + $scope.fullName + $scope.fullName.indexOf("No Person Found"));
//                            if ($scope.fullName.indexOf("No Person Found") === -1 && $scope.fullName.indexOf("Incorrect Id Type") === -1 && $scope.fullName.indexOf("HTTP error code") === -1) {
//                                $('#firstname').val($scope.fullName.substring($scope.fullName.lastIndexOf(" "), 0));
//                                $('#lastname').val($scope.fullName.substring($scope.fullName.lastIndexOf(" ") + 1));
//                                $scope.id_verified = 'true';
//                                $("#verifyIDimage").attr("src", "data:image/gif;base64," + $scope.verified.message.split('_')[3]);
////                    console.log('image: ' + $scope.verified.message.split('_')[3]);
//                                $('#updateData').prop("disabled", false);
//                            } else {
//                                $('#updateResp').addClass("bg-danger py-2");
//                                $('#updateResp').removeClass("bg-success");
//                                $('#updateResp').html('Verification Failed: ' + $scope.fullName);
//                                $scope.id_verified = 'false';
//                                if ($scope.fullName.indexOf("Incorrect Id Type") !== -1) {
//                                    $scope.IdName = $scope.IdType || '';
//                                    $scope.IdType = "Others";
//                                }
//
//                                $scope.cardUpdateResultView = true;
////                    console.log('view: ' + $scope.cardUpdateResultView);
//                                setTimeout(function () {
////                        $('#updateResp').css('display', 'none');
//                                    $scope.cardUpdateResultView = false;
//                                    $scope.$apply($scope.cardUpdateResultView = false);
//                                }, 1500);
//                            }
//                        } else {
//                            $scope.id_verified = 'error';
//                        }
//
//                    }, function error(response) {
////            $scope.showLoader = false; $rootScope.hideLoading();
////            $('.reload').removeClass('fa-spin text-info');
//                        $('#checkId').html("Check");
//                        $('#checkId').prop('disabled', false);
//                        $scope.searching_id = false;
//                        //$rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                    });
//                };
//                $scope.verifyVasgate = function () {
//                    $scope.vasRsp = '';
//                    $('#verify-btn').prop("disabled", true);
//                    $('#verify-btn').html("<i class='fa fa-spinner fa-spin btn-loader'></i> please wait");
//                    $scope.updateApp();
//                    $http({
//                        url: $scope.verifyProxy,
//                        method: 'POST',
//                        data: $httpParamSerializerJQLike({
//                            "merchant": $scope.merchant,
//                            "account": $scope.account,
////                "action": 'query',
//                            "service": 'vasgate'
//                        }),
//                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                    }).then(function success(response) {
//                        $('#verify-btn').prop("disabled", false);
//                        $('#verify-btn').html(" Verify");
//                        $scope.vasRsp = response.data;
//                    }, function error(response) {
//                        $('#verify-btn').prop("disabled", false);
//                        $('#verify-btn').html(" Verify");
//                    });
//                };
//                $scope.confirmationDialogConfig = {};
//                $scope.confirmationDialog = function (action) {
//
////        switch ($scope.profile.role_id) {
////
////            case "3":
//                    $scope.confirmationDialogConfig = {
//                        title: "CONFIRM ACTION",
//                        message: "Are you sure you want proceed?",
//                        buttons: [{
//                                label: "PROCEED",
//                                action: action
//                            }]
//                    };
//                    $scope.showDialog(true);
//                };
//                $scope.executeDialogAction = function (action) {
//                    if (typeof $scope[action] === "function") {
//                        $scope[action]();
//                    }
//                };
//                $scope.dismissModal = function () {
//                    $scope.showDialog(false);
//                };
//                $scope.showDialog = function (flag) {
//                    jQuery("#confirmation-dialog .modal").modal(flag ? 'show' : 'hide');
//                };
//                $scope.addRoleElement = function () {
//                    var html = '';
////                $rootScope.values = [];
////                $rootScope.values['field' + $rootScope.currentIndex + '0_'] = '17';
//                    html += '<tr id="field' + $rootScope.currentIndex + '0_">';
//                    html += '<td style="padding-right: 10px"><button type="button" name="remove" class="btn btn-danger btn-sm remove" ng-click="removeRoleElement();"><i class="fa fa-trash"></i></button></td>';
//                    html += '<td style="padding-right: 10px">';
//                    html += '<select class="form-control form-control-sm item_name" name="item_name[]" ng-model="values[\'field' + $rootScope.currentIndex + '0_\']" id="type_id" ng-Change="getTypeIdOptions(\'field' + $rootScope.currentIndex + '0_\')" ng-required="true" >';
//                    html += '<option value="" selected readonly>Select User Type</option>';
//                    html += '<option ng-repeat="option in userTypeList track by option.id" title="" value="{{option.id}}">{{option.name}}</option>';
//                    html += '</select>';
//                    html += '</td>';
//                    html += '</tr>';
//                    $('#typeIdData').prepend($compile(angular.element(html))($rootScope));
//                    $rootScope.currentIndex++;
//                    $("#typeIdData").animate({scrollTop: 0}, "slow");
//                };
//                $rootScope.addTypeIdOptions = function (id) {
////        if (id === '43') {
//                    var html = '';
//                    html += '<td >';
//                    html += '<div id="data' + id + '">';
//                    html += '<select class="form-control form-control-sm item_quantity" name="item_quantity[]" ng-model="values[\'field' + $rootScope.currentIndex + '1_\'+$index]" id="type_id"  >';
////                console.log('gg:' + $scope['data' + id]);
////                console.log('kk:' + $rootScope['data' + id]);
//
////                if ($scope['data' + id] && $scope['data' + id].length > 0) {
//                    html += '<option value="" selected readonly ng-if="(data' + id + ' && data' + id + '.length>0)">Select User Type</option>';
//                    html += '<option value="" selected readonly ng-if="!(data' + id + ' && data' + id + '.length>0)">No Options Available</option>';
//                    html += '<option ng-repeat="option in data' + id + ' track by $index" value="{{option.alias}}">{{option.name}}</option>';
////                } else {
////                    html += '<option value="" selected readonly>No Options Available</option>';
////                }
//
//                    html += '</select>';
//                    html += '</div>';
//                    html += '</td>';
//                    $('#' + id).append($compile(angular.element(html))($rootScope));
//                };
//                $scope.populateRoleElement = function (role, roleParam, roleOptions, index) {
//                    var html = '';
////                $rootScope.values['field' + $rootScope.currentIndex + '0_'] = role;
//                    html += '<tr id="field' + $rootScope.currentIndex + '0_">';
//                    html += '<td style="padding-right: 10px"><button type="button" name="remove" class="btn btn-danger btn-sm remove" ng-click="removeRoleElement();"><i class="fa fa-trash"></i></button></td>';
//                    html += '<td style="padding-right: 10px">';
//                    html += '<select class="form-control form-control-sm item_name" name="item_name[]" ng-model="values[\'field' + $rootScope.currentIndex + '0_\']" ng-Change="getTypeIdOptions(\'field' + $rootScope.currentIndex + '0_\')" required ng-init="values[\'field' + $rootScope.currentIndex + '0_\'] = \'' + role + '\'" >';
////                html += '<option value="" selected disabled>Select User Type</option>';
//                    html += '<option ng-repeat="option in userTypeList track by option.id" value="{{option.id}}">{{option.name}}</option>';
//                    html += '</select>';
////                html += '<select ng-model="values[\'field' + $rootScope.currentIndex + '0_\']" ng-options="option.id as option.name for option in userTypeList track by option.id" ng-init="values[\'field' + $rootScope.currentIndex + '0_\'] = \'' + role + '\'"></select>';
//                    html += '</td>';
////                console.log('options: ' + roleOptions.length);
//                    var model = $parse('datafield' + $rootScope.currentIndex + '0_');
//                    model.assign($rootScope, roleOptions);
////                console.log('data::: ' + JSON.stringify(roleOptions));
////                console.log('data' + $rootScope.currentIndex + ' : '+ $scope['data' + $rootScope.currentIndex]);
////                    
////                if (roleOptions.length > 0) {
////                console.log('DAAT: ' + roleOptions.length + ' : ' + roleParam);
//                    roleParam = (roleOptions.length > 0 ? roleParam : "");
//                    $rootScope.values['field' + $rootScope.currentIndex + '1_' + index] = roleParam;
////                    var model = $parse('data' + $rootScope.currentIndex);
////                    model.assign($scope, roleOptions);
////                if (roleOptions.length > 0) {
//                    html += '<td>';
////                html += '<td ng-show="' + (roleOptions.length > 0) + '">';
//                    html += '<div id="datafield' + $rootScope.currentIndex + '0_">';
////                    html += '<select class="form-control form-control-sm item_quantity" name="item_quantity[]" ng-model="values[\'field' + $rootScope.currentIndex + '1_\' + index + ]" id="type_id">';
//                    html += `<select class="form-control form-control-sm item_quantity" name="item_quantity[]" ng-model="values['field` + $rootScope.currentIndex + `1_` + index + `']" ng-init="values['field` + $rootScope.currentIndex + `1_` + index + `'] = '` + roleParam + `'">`;
//                    if (roleOptions.length > 0) {
//                        html += '<option value="" readonly>Select User Type</option>';
//                        html += '<option ng-repeat="option in datafield' + $rootScope.currentIndex + '0_ track by $index" value="{{option.alias}}">{{option.name}}</option>';
//                    } else {
//                        html += '<option value="" readonly>No Options Available</option>';
//                    }
//                    html += '</select>';
//                    html += '</div>';
//                    html += '</td>';
////                }
//
//                    html += '</tr>';
////                $('#typeIdData').prepend($compile(angular.element(html))($scope));
//                    $rootScope.currentIndex++;
//                    return  html;
//                };
//                $scope.populateTypeIdOptions = function (id) {
////        if (id === '43') {
//                    var html = '';
//                    html += '<td >';
//                    html += '<div id="data' + id + '">';
//                    html += '<select class="form-control form-control-sm item_quantity" name="item_quantity[]" ng-model="values[\'field' + $rootScope.currentIndex + '1_\'+$index]" id="type_id"  >';
////                html += '<option value="" selected disabled>Select User Type</option>';
//                    html += '<option ng-repeat="option in data' + id + '" value="{{option.alias}}">{{option.name}}</option>';
//                    html += '</select>';
//                    html += '</div>';
//                    html += '</td>';
//                    $('#' + id).append($compile(angular.element(html))($scope));
////        }
//
//                };
//                $scope.populateTypeIdOptions2 = function (id) {
////        if (id === '43') {
//                    var html = '';
//                    html += '<td >';
//                    html += '<div id="data-' + id + '">';
//                    html += '<select class="form-control form-control-sm item_quantity" name="item_quantity[]" ng-model="values[\'field' + $rootScope.currentIndex + '1_\'+$index]" id="type_id"  >';
////                html += '<option value="" selected disabled>Select User Type</option>';
//                    html += '<option ng-repeat="option in data-' + id + '" value="{{option.alias}}">{{option.name}}</option>';
//                    html += '</select>';
//                    html += '</div>';
//                    html += '</td>';
//                    $('#' + id).append($compile(angular.element(html))($scope));
////        }
//
//                };
////            $scope.populateRoleElement = function (role) {
////                var html = '';
////                $rootScope.values['field' + $rootScope.currentIndex + '0_'] = role;
//////                console.log('lge');
////                html += '<tr id="field' + $rootScope.currentIndex + '0_">';
////                html += '<td style="padding-right: 10px"><button type="button" name="remove" class="btn btn-danger btn-sm remove" ng-click="removeRoleElement();"><i class="fa fa-trash"></i></button></td>';
////                html += '<td style="padding-right: 25px">';
////                html += '<select class="form-control form-control-sm item_name" name="item_name[]" ng-model="values[\'field' + $rootScope.currentIndex + '0_\']" id="type_id" ng-Change="getTypeIdOptions(\'field' + $rootScope.currentIndex + '0_\')" required >';
//////                html += '<option value="" selected disabled>Select User Type</option>';
////                html += '<option ng-repeat="option in typeIdList" title="yert" value="{{option.id}}">{{option.name}}</option>';
////                html += '</select>';
////                html += '</td>';
////                if (1 > 2) {
////                    html += '<td >';
////                    html += '<div id="data' + $rootScope.currentIndex + '">';
////                    html += '<select class="form-control form-control-sm item_quantity" name="item_quantity[]" ng-model="values[\'field' + $rootScope.currentIndex + '1_\'+$index]" id="type_id"  >';
//////                html += '<option value="" selected disabled>Select User Type</option>';
////                    html += '<option ng-repeat="option in data' + $rootScope.currentIndex + '" value="{{option.alias}}">{{option.name}}</option>';
////                    html += '</select>';
////                    html += '</div>';
////                    html += '</td>';
////                }
////
////                html += '</tr>';
//////                $('#typeIdData').prepend($compile(angular.element(html))($scope));
////
////                return  html;
////            };
////          
//
//                $(document).on('click', '.remove', function () {
//                    $(this).closest('tr').remove();
//                });
//                $('#userUpdateForm').on('submit', function (event) {
//                    event.preventDefault();
//                    var form_data = $(this).serialize();
////        console.log('data: ' + form_data);
//                    var loginForm = $(this).serializeJSON({checkboxUncheckedValue: "false"});
////        console.log('hg: ' + JSON.stringify(loginForm));
////        console.log((loginForm).item_name.length);
//
//                    if ((loginForm).item_name) {
//                        var userOptions = [];
//                        for (var i = 0; i < (loginForm).item_name.length; i++) {
//                            userOptions.push({
//                                "userOption0": (loginForm).item_name[i],
//                                "userOption1": (loginForm).item_quantity[i] || '',
//                                "userOption2": (loginForm).item_unit[i] || ''
//                            });
//                        }
//                        delete loginForm['item_name'];
//                        delete loginForm['item_unit'];
//                        delete loginForm['item_quantity'];
//                        loginForm['userOptions'] = userOptions;
////        console.log('newjson: ' + JSON.stringify(userOptions));
//
//
//                        console.log('JSON: ' + JSON.stringify(loginForm));
//                        if ($scope.usernameAvailable) {
////create user
//
//                            $http({
//                                url: $scope.adminProxy,
//                                method: 'POST',
//                                data: $httpParamSerializerJQLike({
//                                    "createUserData": JSON.stringify(loginForm),
//                                    "service": "createUser"
//                                }),
//                                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                            }).then(function success(response) {
//                                $scope.showLoader = false;
//                                $rootScope.hideLoading();
//                                $('.reload').removeClass('fa-spin text-info');
//                                $scope.mobileAppResp = response.data;
//                                if ($scope.mobileAppResp.code === "00") {
////                console.log('' + $scope.mobileAppResp.data);
////                console.log('data: ' + $scope.mobileAppResp.data.indexOf('Unavailable'));
//                                    if ($scope.mobileAppResp.data.indexOf('Unavailable') !== -1) {
//                                        $('.unavailable').css('display', 'block');
//                                        $scope.usernameAvailable = false;
//                                    }
//                                    if ($scope.mobileAppResp.data.indexOf('Available') !== -1) {
//                                        $('.available').css('display', 'block');
//                                        $scope.usernameAvailable = true;
//                                    }
//                                } else {
//
//                                }
//                            }, function error(response) {
//                                $scope.showLoader = false;
//                                $rootScope.hideLoading();
//                                $('.reload').removeClass('fa-spin text-info');
//                                //$rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                            });
//                        } else {
//                            console.log('username is unavailable');
//                        }
//                    } else {
//                        alert('Please Select User Type');
//                    }
//
//
//                });
//                var checkusernametimer = "";
//                ["input", "keyup", "mousedown", "mouseup", "select", "contextmenu", "drop", "paste"].forEach(function (event) {
//                    $(document).on(event, '#newUsername', function (e) {
////                    console.log('VALUE: ' + $(this).val());
//                        $scope.checkUsernameTimeout($(this).val());
//                    });
//                });
//                $scope.cancelCheckUsernameTimeout = function () {
//                    clearTimeout(checkusernametimer);
//                };
//                $scope.checkUsernameTimeout = function (username) {
//                    $scope.usernameAvailable = false;
//                    console.log('dfg');
//                    $scope.cancelCheckUsernameTimeout();
//                    $('.unavailable').css('display', 'none');
//                    $('.available').css('display', 'none');
//                    if (username.trim() !== "") {
//                        checkusernametimer = setTimeout(function () {
//                            $scope.$apply($scope.checkUsername(username));
//                        }, 1000);
//                    }
//                };
//                $scope.checkUsername = function (username) {
//                    $http({
//                        url: $scope.adminProxy,
//                        method: 'POST',
//                        data: $httpParamSerializerJQLike({
//                            "username": username,
//                            "service": "checkUsername"
//                        }),
//                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                    }).then(function success(response) {
//                        $scope.showLoader = false;
//                        $rootScope.hideLoading();
//                        $('.reload').removeClass('fa-spin text-info');
//                        $scope.mobileAppResp = response.data;
//                        if ($scope.mobileAppResp.code === "00") {
////                console.log('' + $scope.mobileAppResp.data);
////                console.log('data: ' + $scope.mobileAppResp.data.indexOf('Unavailable'));
//                            if ($scope.mobileAppResp.data.indexOf('Unavailable') !== -1) {
//                                $('.unavailable').css('display', 'block');
//                                $scope.usernameAvailable = false;
//                            }
//                            if ($scope.mobileAppResp.data.indexOf('Available') !== -1) {
//                                $('.available').css('display', 'block');
//                                $scope.usernameAvailable = true;
//                            }
//                        } else {
//
//                        }
//                    }, function error(response) {
//                        $scope.showLoader = false;
//                        $rootScope.hideLoading();
//                        $('.reload').removeClass('fa-spin text-info');
//                        //$rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                    });
//                };
//                $scope.roleValues = {};
//                $scope.showCardUserForm = function (action, index, scope) {
//
//                    accountUser = $scope[scope];
//                    if (action) {
////                    $rootScope.$apply(function () {
//                        $rootScope.card_service = action;
//                        $scope.checkActionType(action);
////                    });
//                        console.log('action ::: ' + $rootScope.card_service);
//                    }
//                    console.log('user: ' + accountUser[index].card2);
//                    $('#account_number').text(accountUser[index].card2);
//                    $scope.account_number = accountUser[index].card2;
//                    $('#firstname').val(accountUser[index].Firstname);
//                    $('#lastname').val(accountUser[index].Lastname);
//                    $('#email').val(accountUser[index].Email);
//                    $('#mobile_no').val(accountUser[index].Phone);
//                    $('#street').val(accountUser[index].Street);
//                    $('#city').val(accountUser[index].city);
////                $('#action').val(accountUser[index].action);
//                    $('#id_type').val((accountUser[index].fax).split(" ")[0]);
//                    $('#id_number').val((accountUser[index].fax).split(" ")[1]);
//                    $('#branch').val(accountUser[index].sub_code);
//                    $scope.hotlist = (accountUser[index].User_Hotlist) === 0;
//                    //                $('#reason').val("accountUser[index].reason");
//                    $scope.reason = accountUser[index].reason;
//                    console.log('reason: ' + accountUser[index].reason);
//                    $(".card-user-form").css("display", "block");
////                $('#action').val(action);
//                    //                $scope.showCardForm = true;
//                    animateCSS('.card-user-form', 'backInDown');
//                    $("#verifyIDimage").attr("src", "assets/img/avatar_2x.png");
//                    if (accountUser[index].fax.length > 10) {
//                        $('#id_type').val(accountUser[index].fax.split(' ')[0]);
//                        $('#id_number').val(accountUser[index].fax.split(' ')[1]);
//                        $scope.CheckId();
//                    }
//                };
//                $scope.showAccountUserForm = function (profile) {
//
//
////                if (action) {
//////                    $rootScope.$apply(function () {
////                    $rootScope.card_service = action;
////
////                    $scope.checkActionType(action);
////
//////                    });
////                    console.log('action ::: ' + $rootScope.card_service);
////                }
////                console.log('user: ' + profile.card_num);
//                    $('#account_number').text(profile.card_num);
//                    $scope.account_number = profile.card_num;
//                    $scope.account_id = profile.Client_ID;
//                    $('#firstname').val(profile.Firstname);
//                    $('#appname').val(profile.AppName);
//                    $('#appid').val(profile.AppId);
//                    $('#mobile_number').val(profile.Phone);
//                    $('#mobile_nos').val(profile.Phone);
////                $scope.mobile_no = profile.Phone;
//                    $('#branch').val(profile.sub_code);
//                    $scope.hotlist = (profile.User_Hotlist) === 0;
//                    //                $('#reason').val("profile.reason");
//                    $scope.reason = profile.reason;
//                    $scope.bankCode = profile.issuer_code;
////                console.log('bankcode: ' + $scope.bankCode);
//
////                console.log('reason: ' + profile.reason);
//                    $(".card-user-form").css("display", "block");
////                $('#action').val(action);
//                    //                $scope.showCardForm = true;
//                    animateCSS('.card-user-form', 'backInDown');
//                };
//                $scope.showProcessAccountUserForm = function (profile) {
//                    $scope.card_service = profile.action;
//                    $scope.mobile_app_profile = profile;
//                    $scope.authorize_process_state = 'AUTHORIZE';
//                    $scope.deny_process_state = 'DENY';
//                    $scope.sms_process_state = 'VERIFY';
//                    $('.account_name').val("please wait...");
//                    $http.get('api/MobileAppMgmt?action=getAccountName&account_id=' + profile.account_id + "&mobile_no=" + profile.mobile_number + "&appId=" + profile.app_id).then(function (response) {
//                        var resp = response.data;
//                        if (resp.code === '00' && resp.data !== '') {
//                            $('.account_name').val(resp.data);
//                        } else {
//                            $('.account_name').val('N/A');
//                        }
//                    });
//                    //                       console.log('banches: '+ JSON.stringify($scope.branchList));
//                    $('.account_number').val(profile.account);
//                    $('.account_id').val(profile.account_id);
//                    $('.appname').val(profile.app_name);
//                    $('.appid').val(profile.app_id);
//                    $('.mobile_number').val(profile.mobile_number);
//                    $('.action').val(profile.action);
//                    $('.account_change').val(profile.account_change);
////                console.log('sd: ' + profile.account_change);
////                $scope.mobile_no = profile.Phone;
//                    $('.branch').val(profile.sub_code);
//                    $('.reason').val(profile.reason);
//                    $(".card-user-form").css("display", "block");
//                    $('.process_state').prop('disabled', false);
//                    animateCSS('.card-user-form', 'backInDown');
//                };
//                $scope.showProcessAccountRequestForm = function (request) {
//                    $scope.card_service = request.action;
//                    $scope.mobile_app_profile = request;
//                    $scope.authorize_process_state = 'PROCESS';
//                    $scope.deny_process_state = 'DECLINE';
//                    $scope.sms_process_state = 'VERIFY';
//                    $('.account').val(request.accountNumber);
//                    $('.reference').val(request.reference);
//                    $('.bank_code').val(request.bankCode);
//                    $('.bank').val(request.bank);
//                    $('.action').val(request.requestType);
//                    $('.mobile_number').val(request.msisdn);
////                $('.action').val(request.action);
////                $('.account_change').val(profile.account_change);
////                console.log('sd: ' + profile.account_change);
////                $scope.mobile_no = profile.Phone;
////                $('.branch').val(profile.sub_code);
//                    $('.reason').val(request.requestInfo);
//                    $(".card-user-form").css("display", "block");
//                    $('.process_state').prop('disabled', false);
//                    animateCSS('.card-user-form', 'backInDown');
//                };
//                $scope.downloadBankApplicationImage = function (a) {
//                    $rootScope.showLoading();
//                    $rootScope.$evalAsync(function () {
////                    console.log('ds');
//                        var formData = {};
//                        formData['bank_code'] = a.bankCode;
//                        formData['reference'] = a.reference;
//                        formData['service'] = "downloadBankApplicationImage";
//                        $http({
//                            url: 'api/MobileAppMgmt',
//                            method: 'POST',
//                            data: $httpParamSerializerJQLike(formData),
//                            responseType: 'arraybuffer',
//                            transformResponse: jsonBufferToObject,
//                            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                        }).then(function success(response) {
//                            $scope.showLoader = false;
//                            $('.reload').removeClass('fa-spin text-info');
//                            $rootScope.hideLoading();
//                        }, function error(response) {
//                            $scope.showLoader = false;
//                            $('.reload').removeClass('fa-spin text-info');
////                        alert('Connection or Server Error. Try again');
//                            $rootScope.hideLoading();
//                            //$rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                        });
//                    });
//                };
//                $scope.dataURItoBlob = function (dataURI) {
//                    dataURI = "data:image/jpeg;base64," + dataURI;
//                    var byteString = atob(dataURI.split(',')[1]);
//                    var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];
//                    var ab = new ArrayBuffer(byteString.length);
//                    var ia = new Uint8Array(ab);
//                    for (var i = 0; i < byteString.length; i++) {
//                        ia[i] = byteString.charCodeAt(i);
//                    }
//
//                    var blob = new Blob([ab], {type: mimeString});
//                    return blob;
//                };
//                $scope.showProcessBankApplicationForm = function (request) {
//                    $scope.card_service = request.action;
//                    $scope.mobile_app_profile = request;
//                    $scope.authorize_process_state = 'PROCESS';
//                    $scope.deny_process_state = 'DECLINE';
//                    $scope.sms_process_state = 'VERIFY';
//                    $('.firstName').val(request.firstName);
//                    $('.lastName').val(request.lastName);
//                    $('.account_id').val(request.applicationId);
//                    $('.bankCode').val(request.bankCode);
//                    $('.bank').val(request.bank);
//                    $('.dob').val(request.dob);
//                    $('.mobile_number').val(request.phoneNumber);
//                    $('.gender').val(request.gender);
//                    $('.idNumber').val(request.idNumber);
//                    $('.idType').val(request.idType);
//                    $('.reference').val(request.reference);
////                $('.account_change').val(profile.account_change);
////                console.log('sd: ' + profile.account_change);
////                $scope.mobile_no = profile.Phone;
////                $('.branch').val(profile.sub_code);
//                    $('.homeAddress').val(request.homeAddress);
//                    $('.ghPostAddress').val(request.ghPostAddress);
//                    $('.email').val(request.email);
//                    $('.location').val(request.closestBranch);
//                    $(".card-user-form").css("display", "block");
//                    $('.process_state').prop('disabled', false);
//                    animateCSS('.card-user-form', 'backInDown');
//                };
//                $scope.showProcessMobileAppForm = function (request) {
//                    $scope.card_service = request.action;
//                    $scope.mobile_app_profile = request;
//                    $scope.authorize_process_state = 'AUTHORIZE';
//                    $scope.deny_process_state = 'DENY';
//                    $('.customer_id').val(request.customerId);
//                    $('.account').val(request.customerId);
//                    $('.bankCode').val(request.bankCode);
//                    $('.bank').val(request.bank);
//                    $('.action').val(request.action);
//                    $('.service_req').val((request.action === 'addaccount' ? 'processAccountRequest' : 'modifyMobileApp'));
//                    $('.mobile_number').val(request.mobileNo);
//                    $('.reference').val(request.reference);
//                    $(".card-user-form").css("display", "block");
//                    $('.process_state').prop('disabled', false);
//                    animateCSS('.card-user-form', 'backInDown');
//                };
//                $scope.hideCardUserForm = function () {
//                    animateCSS('.card-user-form', 'backOutUp').then(function () {
//                        //                    $scope.showCardForm = false;
//                        $(".card-user-form").css("display", "none");
//                    });
//                };
//                $scope.processCms = function () {
//
//                    var formData = $('#cms_form').serializeJSON();
//                    formData['card_number'] = $('#card_number').val();
//                    formData['id_type'] = $('#id_type').val();
//                    formData['id_number'] = $('#id_number').val();
//                    $('.process_state').prop('disabled', true);
//                    $scope.process_state = "Processing...";
//                    $('.process_state').toggleClass("running");
//                    $http({
//                        url: 'api/Cms',
//                        method: 'POST',
//                        data: $httpParamSerializerJQLike(formData),
//                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                    }).then(function success(response) {
//                        setTimeout(function () {
//                            $('.process_state').prop('disabled', false);
//                            $scope.$evalAsync(function () {
//                                $scope.process_state = "Process";
//                            });
//                            $('.process_state').toggleClass("running");
//                            var resp = response.data;
//                            if (resp.code === "00") {
//                                $rootScope.toggleSuccessNotification(resp.message);
//                            } else {
//                                $rootScope.toggleErrorNotification(resp.message);
//                            }
//                        }, 200);
//                    }, function error(response) {
////                        alert('Connection or Server Error. Try again');
//                        //                    $rootScope.hideLoading();
//                        setTimeout(function () {
//                            $('.process_state').prop('disabled', false);
//                            $scope.$apply($scope.process_state = "Process");
//                            $('.process_state').toggleClass("running");
//                            $rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                        }, 200);
//                    });
//                };
//                $scope.sendNMOtp = function () {
//                    var formData = $('#mam_form').serializeJSON();
//                    formData['action'] = 'NMOTP';
//                    formData['service'] = 'NMOTP';
//                    $('.otp_process_state').prop('disabled', true);
//                    $scope.otp_process_state = "Sending...";
//                    $('.otp_process_state').toggleClass("running");
//                    $http({
//                        url: 'api/MobileAppMgmt',
//                        method: 'POST',
//                        data: $httpParamSerializerJQLike(formData),
//                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                    }).then(function success(response) {
//                        setTimeout(function () {
//                            $('.otp_process_state').prop('disabled', false);
//                            $scope.$evalAsync(function () {
//                                $scope.otp_process_state = "SEND OTP";
//                            });
//                            $('.otp_process_state').toggleClass("running");
//                            var resp = response.data;
//                            if (resp.code === "00") {
//                                $rootScope.toggleSuccessNotification(resp.message);
//                            } else {
//                                $rootScope.toggleErrorNotification(resp.message);
//                            }
//                        }, 200);
//                    }, function error(response) {
////                        alert('Connection or Server Error. Try again');
//                        //                    $rootScope.hideLoading();
//                        setTimeout(function () {
//                            $('.otp_process_state').prop('disabled', false);
//                            $scope.$apply($scope.otp_process_state = "Process");
//                            $('.otp_process_state').toggleClass("running");
//                            $rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                        }, 200);
//                    });
//                };
//                $scope.processMobileAppMgmt = function () {
//
//                    var formData = $('#mam_form').serializeJSON();
//                    if (formData['action'] && formData['action'] !== '') {
////                    console.log('ee: ' + formData['action']);
//                        $('.process_state').prop('disabled', true);
//                        $scope.process_state = "Processing...";
//                        $('.process_state').toggleClass("running");
//                        $http({
//                            url: 'api/MobileAppMgmt',
//                            method: 'POST',
//                            data: $httpParamSerializerJQLike(formData),
//                            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                        }).then(function success(response) {
//                            setTimeout(function () {
//                                $('.process_state').prop('disabled', false);
//                                $scope.$evalAsync(function () {
//                                    $scope.process_state = "Process";
//                                });
//                                $('.process_state').toggleClass("running");
//                                var resp = response.data;
//                                if (resp.code === "00") {
//                                    $rootScope.toggleSuccessNotification(resp.message);
//                                } else {
//                                    $rootScope.toggleErrorNotification(resp.message);
//                                }
//                            }, 200);
//                        }, function error(response) {
////                        alert('Connection or Server Error. Try again');
//                            //                    $rootScope.hideLoading();
//                            setTimeout(function () {
//                                $('.process_state').prop('disabled', false);
//                                $scope.$apply($scope.process_state = "Process");
//                                $('.process_state').toggleClass("running");
//                                $rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                            }, 200);
//                        });
//                    } else {
//                        $rootScope.toggleErrorNotification("Please select an action");
//                    }
//                };
//                $scope.processMobileAppMgmtAuth = function (auth) {
//                    var index = $scope.mobile_app_profile.account_id;
//                    var formData = $('#mam_form').serializeJSON();
//                    if (formData['action'] && formData['action'] !== '') {
//                        formData['choice'] = auth;
////                    console.log('ee: ' + formData['action']);
//                        $scope[auth + '_process_state'] = "Processing...";
//                        $('.' + auth + '_process_state').toggleClass("running");
//                        $http({
//                            url: 'api/MobileAppMgmt',
//                            method: 'POST',
//                            data: $httpParamSerializerJQLike(formData),
//                            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                        }).then(function success(response) {
//                            setTimeout(function () {
//
//                                $('.' + auth + '_process_state').toggleClass("running");
//                                var resp = response.data;
////                            console.log('index: ' + index);
//                                if (resp.code === "00") {
//                                    $scope.$evalAsync(function () {
//                                        $scope[auth + '_process_state'] = (auth === 'authorize' ? 'AUTHORIZED' : 'DENIED');
//                                        $('#toggle' + index).html(auth === 'authorize' ? 'APPROVED' : 'DENIED');
//                                        $('#vst' + index).css("display", "none");
//                                    });
//                                    $rootScope.toggleSuccessNotification(resp.message);
//                                } else {
//
//                                    $('.process_state').prop('disabled', false);
//                                    $scope.$evalAsync(function () {
//                                        $scope[auth + '_process_state'] = auth.toUpperCase();
//                                    });
//                                    $rootScope.toggleErrorNotification(resp.message);
//                                }
//                            }, 200);
//                        }, function error(response) {
////                        alert('Connection or Server Error. Try again');
//                            //                    $rootScope.hideLoading();
//                            setTimeout(function () {
//                                $('.process_state').prop('disabled', false);
//                                $scope.$apply($scope.process_state = "Process");
//                                $('.process_state').toggleClass("running");
//                                $rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                            }, 200);
//                        });
//                    } else {
//                        $rootScope.toggleErrorNotification("Please select an action");
//                    }
//                };
//                $scope.processBankAccountRequest = function (auth) {
//                    var index = $scope.mobile_app_profile.reference;
//                    var formData = $('#mam_form').serializeJSON();
//                    formData['choice'] = auth;
////                    console.log('ee: ' + formData['action']);
//                    $scope[auth + '_process_state'] = "Processing...";
//                    $('.' + auth + '_process_state').toggleClass("running");
//                    $http({
//                        url: 'api/MobileAppMgmt',
//                        method: 'POST',
//                        data: $httpParamSerializerJQLike(formData),
//                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                    }).then(function success(response) {
//                        setTimeout(function () {
//
//                            $('.' + auth + '_process_state').toggleClass("running");
//                            var resp = response.data;
////                            console.log('index: ' + index);
//                            if (resp.code === "00") {
//                                $scope.$evalAsync(function () {
//                                    $scope[auth + '_process_state'] = (auth === 'authorize' ? 'PROCESSED' : 'DECLINED');
//                                    $('#toggle' + index).html(auth === 'authorize' ? 'PROCESSED' : 'DECLINED');
//                                    $('#vst' + index).css("display", "none");
//                                    $scope.filteredBankAccountRequests[index].status = (auth === 'authorize' ? 'PROCESSED' : 'DECLINED');
//                                });
//                                $rootScope.toggleSuccessNotification(resp.message);
//                            } else {
//
//                                $('.process_state').prop('disabled', false);
//                                $scope.$evalAsync(function () {
//                                    $scope[auth + '_process_state'] = (auth === 'authorize' ? 'PROCESS' : 'DECLINE');
//                                });
//                                $rootScope.toggleErrorNotification(resp.message);
//                            }
//                        }, 200);
//                    }, function error(response) {
////                        alert('Connection or Server Error. Try again');
//                        //                    $rootScope.hideLoading();
//                        setTimeout(function () {
//                            $('.process_state').prop('disabled', false);
//                            $scope.$apply($scope.process_state = "Process");
//                            $('.process_state').toggleClass("running");
//                            $rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                        }, 200);
//                    });
//                };
//                $scope.processBankApplicationRequest = function (auth) {
//                    var index = $scope.mobile_app_profile.reference;
//                    var formData = $('#mam_form').serializeJSON();
//                    formData['choice'] = auth;
////                    console.log('ee: ' + formData['action']);
//                    $scope[auth + '_process_state'] = "Processing...";
//                    $('.' + auth + '_process_state').toggleClass("running");
//                    $http({
//                        url: 'api/MobileAppMgmt',
//                        method: 'POST',
//                        data: $httpParamSerializerJQLike(formData),
//                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                    }).then(function success(response) {
//                        setTimeout(function () {
//
//                            $('.' + auth + '_process_state').toggleClass("running");
//                            var resp = response.data;
////                            console.log('index: ' + index);
//                            if (resp.code === "00") {
//                                $scope.$evalAsync(function () {
//                                    $scope[auth + '_process_state'] = (auth === 'authorize' ? 'PROCESSED' : 'DECLINED');
//                                    $('#toggle' + index).html(auth === 'authorize' ? 'PROCESSED' : 'DECLINED');
//                                    $('#vst' + index).css("display", "none");
//                                });
//                                $rootScope.toggleSuccessNotification(resp.message);
//                            } else {
//
//                                $('.process_state').prop('disabled', false);
//                                $scope.$evalAsync(function () {
//                                    $scope[auth + '_process_state'] = (auth === 'authorize' ? 'PROCESS' : 'DECLINE');
//                                });
//                                $rootScope.toggleErrorNotification(resp.message);
//                            }
//                        }, 200);
//                    }, function error(response) {
////                        alert('Connection or Server Error. Try again');
//                        //                    $rootScope.hideLoading();
//                        setTimeout(function () {
//                            $('.process_state').prop('disabled', false);
//                            $scope.$apply($scope.process_state = "Process");
//                            $('.process_state').toggleClass("running");
//                            $rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                        }, 200);
//                    });
//                };
//                $scope.processMobileAppUserLog = function (auth) {
//                    var index = $scope.mobile_app_profile.customerId;
//                    var formData = $('#mam_form').serializeJSON();
//                    formData['choice'] = auth;
////                    console.log('ee: ' + formData['action']);
//                    $scope[auth + '_process_state'] = "Processing...";
//                    $('.' + auth + '_process_state').toggleClass("running");
//                    $http({
//                        url: 'api/MobileAppMgmt',
//                        method: 'POST',
//                        data: $httpParamSerializerJQLike(formData),
//                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                    }).then(function success(response) {
//                        setTimeout(function () {
//
//                            $('.' + auth + '_process_state').toggleClass("running");
//                            var resp = response.data;
////                            console.log('index: ' + index);
//                            if (resp.code === "00") {
//                                $scope.$evalAsync(function () {
//                                    $scope[auth + '_process_state'] = (auth === 'authorize' ? 'AUTHORIZED' : 'DENIED');
//                                    $('#toggle' + index).html(auth === 'authorize' ? 'APPROVED' : 'DENIED');
//                                    $('#vst' + index).css("display", "none");
//                                    $scope.filteredMobileAppUserLog[index].status = (auth === 'authorize' ? 'APPROVED' : 'DENIED');
//                                });
//                                $rootScope.toggleSuccessNotification(resp.message);
//                            } else {
//
//                                $('.process_state').prop('disabled', false);
//                                $scope.$evalAsync(function () {
//                                    $scope[auth + '_process_state'] = auth.toUpperCase();
//                                });
//                                $rootScope.toggleErrorNotification(resp.message);
//                            }
//                        }, 200);
//                    }, function error(response) {
////                        alert('Connection or Server Error. Try again');
//                        //                    $rootScope.hideLoading();
//                        setTimeout(function () {
//                            $('.process_state').prop('disabled', false);
//                            $scope.$apply($scope.process_state = "Process");
//                            $('.process_state').toggleClass("running");
//                            $rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                        }, 200);
//                    });
//                };
//                $scope.processMobileAppNotification = function (auth) {
//                    var formData = $('#mam_form').serializeJSON();
////                    console.log('ee: ' + formData['action']);
//                    $scope[auth + '_process_state'] = "Sending...";
//                    $('.' + auth + '_process_state').toggleClass("running");
//                    $http({
//                        url: 'api/MobileAppMgmt',
//                        method: 'POST',
//                        data: $httpParamSerializerJQLike(formData),
//                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                    }).then(function success(response) {
//                        setTimeout(function () {
//
//                            $('.' + auth + '_process_state').toggleClass("running");
//                            var resp = response.data;
////                            console.log('index: ' + index);
//                            if (resp.code === "00") {
//                                $rootScope.toggleSuccessNotification(resp.message);
//                            } else {
//                                $rootScope.toggleErrorNotification(resp.message);
//                            }
//                            $scope.$evalAsync(function () {
//                                $scope[auth + '_process_state'] = "SEND NOTIFICATION";
//                            });
//                            $('.process_state').prop('disabled', false);
////                        $('.process_state').toggleClass("running");
//                        }, 200);
//                    }, function error(response) {
////                        alert('Connection or Server Error. Try again');
//                        //                    $rootScope.hideLoading();
//                        setTimeout(function () {
//                            $('.process_state').prop('disabled', false);
//                            $scope.$apply($scope.process_state = "Process");
//                            $('.process_state').toggleClass("running");
//                            $rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                        }, 200);
//                    });
//                };
//                $scope.enableAuth = function () {
//                    $('.process_state').prop('disabled', false);
//                };
//                $scope.disableAuth = function () {
//                    $('.process_state').prop('disabled', true);
//                };
//                $scope.processCmsReq = function (action) {
//
//                    var formData = $('#cms_form').serializeJSON();
////                formData['card_number'] = $('#card_number').val();
////                formData['id'] = $('#card_number').val();
//                    formData['choice'] = action;
//                    formData['action'] = $('#action').val();
////                console.log('action: ' + $('#action').val());F
//
//                    $('.' + action + '_process_state').prop('disabled', true);
//                    $scope[action] = "Processing...";
//                    $('.' + action + '_process_state').toggleClass("running");
//                    $http({
//                        url: 'api/Cms',
//                        method: 'POST',
//                        data: $httpParamSerializerJQLike(formData),
//                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                    }).then(function success(response) {
//                        setTimeout(function () {
//
//                            $scope[action] = "Process";
//                            $('.' + action + '_process_state').toggleClass("running");
//                            var resp = response.data;
//                            if (resp.code === "00") {
//                                $('.process_state').prop('disabled', true);
//                                $rootScope.toggleSuccessNotification(resp.message);
//                            } else {
//                                $('.' + action + '_process_state').prop('disabled', false);
//                                $rootScope.toggleErrorNotification(resp.message);
//                            }
//                        }, 200);
//                    }, function error(response) {
////                        alert('Connection or Server Error. Try again');
//                        //                    $rootScope.hideLoading();
//                        setTimeout(function () {
//                            $('.' + action + '_process_state').prop('disabled', false);
//                            $scope[action] = "Process";
//                            $('.' + action + '_process_state').toggleClass("running");
//                            $rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                        }, 200);
//                    });
//                };
//                $scope.checkActionType = function (action) {
////                console.log('type: ' + action);
////                $scope.$apply();
//                    $("#branchList").hide("fast");
//                    $("#enhancementType").hide("fast");
//                    $("#numberModification").hide("fast");
//                    $(".numberModification").hide("fast");
//                    if (action === 'enhancement') {
//                        $("#enhancementType").show("fast");
//                    } else if (action === 'addaccount') {
//                        $("#branchList").show("fast");
////                    $scope.initializeSettings('Cms', 'branches', 'branchList', 'all');
//
//                    } else if (action === 'number_modification') {
//                        $("#numberModification").show("fast");
//                        $(".numberModification").show("fast");
//                    }
//                    $scope.action = action;
//                };
//                $rootScope.currentIndex = 1;
//                $scope.processBulkRequest = function (index) {
//
//                    var bulkRequest = $scope.filteredBulkProcessTrxs[index];
//                    Swal.fire({
//                        title: "Process Bulk Request: " + bulkRequest.batch_id + " ?",
////                    text: "This will " + action + " " + $scope.username,
//                        html: "Action to take on request: <b>" + bulkRequest.batch_id + " made by " + bulkRequest.initiated_by + " with total GHS " + $scope.getAmt(bulkRequest.amount) + "</b>",
//                        type: 'warning',
//                        showCancelButton: true,
//                        confirmButtonColor: '#3085d6',
//                        cancelButtonColor: '#d33',
//                        cancelButtonText: 'No, Deny Request',
//                        confirmButtonText: 'Authorize',
//                        allowOutsideClick: true
//                    }).then(function (result) {
//                        console.log('USer: ' + JSON.stringify(result));
//                        //do asyc
//                        var action;
//                        if (result.dismiss === Swal.DismissReason.cancel) {
//                            console.log('cancelled');
//                            action = "DENY";
//                        } else if (result.value) {
//                            action = "AUTHORIZE";
////                        var resp = result.value.data.data;
//////                        console.log('before: ' + JSON.stringify($scope.searchUserProfile[0]));
//////                        console.log('action: ' + $scope.action);
////                        $scope.searchUserProfile[0].status_id = (resp === 'SUCCESS' ? ($scope.action === 'activate' ? 1 : 2) : $scope.searchUserProfile[0].status_id);
////                        var respText = (resp === 'SUCCESS' ? $scope.searchUserProfile[0].username + ' has been ' + action + 'd.' : (resp === 'FAILED' ? 'Failed to ' + action + ' ' + $scope.searchUserProfile[0].username + '.' : resp));
////                        var respType = (resp === 'SUCCESS' ? 'success' : 'error');
////                        var respTitle = (resp === 'SUCCESS' ? action + 'd!' : 'Failed');
//////                        console.log('after: ' + JSON.stringify($scope.searchUserProfile[0]));
////                        if (resp === 'SUCCESS') {
////                            $('#user' + index).html(($scope.action === 'activate' ? 'active' : 'inactive'));
////                            $scope.filtereduserProfiles[$scope.userIndex].status_id = ($scope.action === 'activate') ? '1' : '2';
////                        }
////                        Swal.fire({title: respTitle, text: respText, type: respType, timer: 1500});
//                        }
//                        if (action !== '') {
//                            var formData = {};
//                            formData['unique_transid'] = bulkRequest.batch_id;
//                            formData['action'] = action;
//                            formData['service'] = 'processBulkRequest';
//                            $http({
//                                url: 'api/BulkProcess',
//                                method: 'POST',
//                                data: $httpParamSerializerJQLike(formData),
//                                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                            }).then(function success(response) {
//                                var resp = response.data;
//                                if (resp.code === "00") {
//                                    $rootScope.toggleSuccessNotification(resp.message);
//                                    $scope.filteredBulkProcessTrxs[index].status = (action === 'AUTHORIZE' ? 'AUTHORIZED' : 'DENIED');
//                                } else {
//                                    $rootScope.toggleErrorNotification(resp.message);
//                                }
//                            }, function error(response) {
////                        
//                            });
//                        }
//
//                    });
//                };
//                $scope.trimJP = function (text) {
//                    if (text) {
//                        if (text.length > 36) {
//                            return (text.substring(0, 35)) + " ...";
//                        }
//                    }
//                    return text;
//                };
//                $scope.trim = function (text) {
//                    if (text.length > 60) {
//                        return (text.substring(0, 62)) + " ...";
//                    }
//                    return text;
//                };
//                $scope.trimShort = function (text) {
//                    if (text) {
//                        if (text.length > 40) {
//                            return (text.substring(0, 38)) + " ...";
//                        }
//                    }
//                    return text;
//                };
//                $scope.trimVasBillId = function (text) {
//                    if (text.length > 30) {
//                        return (text.substring(0, 30)) + " ...";
//                    }
//                    return text;
//                };
//                $scope.getResponseCode = function (str) {
//                    return str.split("::")[0];
//                };
//                $scope.getResponseMsg = function (str) {
//                    return $scope.trim(str.split("::")[1]);
//                };
//                $scope.trim2 = function (text) {
//                    if (text.length > 78) {
//                        return (text.substring(0, 77)) + " ...";
//                    }
//                    return text;
//                };
//                $scope.getBankName = function (bankCode) {
//                    return bankCode;
//                };
//                $scope.formatStr = function (descr) {
//                    var newStr = descr.replace(/,/g, ' ');
//                    return newStr;
//                };
//                $scope.hideFilterBox = function () {
//                    $scope.showTrxFilterBox = false;
//                    $scope.showFilterBox = false;
//                    //                $('.filter-box').toggleClass('slideOutRight slideInRight');
//                    $(".filter-box").css("display", "none");
//                };
//                $scope.hideAgentBox = function () {
//                    $scope.showAgent = false;
//                };
//                $scope.hideDetailsBox = function () {
//                    $scope.showTrxFilterBox = false;
//                    $scope.showDetailsBox = false;
//                };
//                $scope.showFilterBoxView = function () {
//                    $scope.showTrxFilterBox = true;
//                    $scope.showFilterBox = true;
//                    $(".filter-box").css("display", "block");
//                };
//                $scope.showAgentBox = function () {
//                    $scope.showAgent = true;
//                };
//                $scope.toggleFilterBoxView = function () {
////                console.log('sdfsdf');
//                    if ($scope.showFilterBox) {
//                        $scope.hideFilterBox();
//                    } else {
//                        $scope.showFilterBoxView();
//                    }
//                };
//                $scope.toggleCPayTransView = function () {
//                    $scope.showCPayTransView = !$scope.showCPayTransView;
//                };
//                $scope.showCardUpdateBoxView = function () {
//                    $scope.showCardUpdateBox = true;
//                    $scope.cardUpdateResultView = false;
//                };
//                $scope.hideCardUpdateBoxView = function () {
//                    $scope.showCardUpdateBox = false;
//                };
//                $scope.showCardHotlistBoxView = function () {
//                    $scope.showCardUpdateBox = true;
//                    $scope.cardUpdateResultView = false;
//                };
//                $scope.hideCardHotlistBoxView = function () {
//                    $scope.showCardUpdateBox = false;
//                };
//                $scope.toggleAgentBoxView = function () {
//                    $scope.showAgent = !$scope.showAgent;
//                };
//                $scope.getChannel = function (channel) {
////                console.log(channel);
//                    switch (channel) {
//                        case "ju":
//                            channel = "justpay";
//                            break;
//                        case "09":
//                            channel = "fundgate";
//                            break;
//                        case  "02":
//                            channel = "mobile";
//                            break;
//                        case "05":
//                            channel = "payoutlet";
//                            break;
//                        case "01":
//                            channel = "webconnect";
//                            break;
//                        default:
//                            channel = "N/A";
//                            break;
//                    }
//                    return channel;
//                };
//                $scope.genRowColor = function (str) {
//                    //                if ($scope.status === "ALL") {
//
//                    var rowClass = $scope.getResponseCode(str);
//                    if (rowClass !== "0" && rowClass !== "00") {
//                        if (rowClass === '05') {
//                            return "blue";
//                        }
//                        return "red";
//                    }
//                    //                }
//                };
//                $scope.fgRowColor = function (str) {
//                    //                if ($scope.status === "ALL") {
//
//                    var rowClass = $scope.getResponseCode(str);
//                    if (rowClass !== "0") {
//                        if (rowClass === '31') {
//                            return "blue";
//                        }
//                        return "red";
//                    }
//                    //                }
//                };
//                $scope.fgRowColor2 = function (str) {
//                    if (str !== "0") {
//                        if (str === '31') {
//                            return "blue";
//                        }
//                        return "red";
//                    }
//                };
//                $scope.fgRowColor3 = function (str, str2) {
//
//
//                    if (str2 !== '00') {
//                        if ((str === '58') || str === '66') {
//                            return "blue";
//                        }
//                        if (str !== "0") {
//                            if (str === '31') {
//                                return "blue";
//                            }
//                            return "red";
//                        }
//                        return "red";
//                    }
//
//
//                };
//                $scope.momoRowColor = function (str) {
//                    if ($scope.status === "ALL") {
//                        if (str.toUpperCase() !== "SUCCESSFUL") {
//                            if (str.toUpperCase() === "PENDING") {
//                                return "blue";
//                            }
//                            return "red";
//                        }
//
//                    }
//                };
//                $scope.webconnectRowColor = function (str, str2) {
//                    if ($scope.status === "ALL") {
//                        str = str === null || str === undefined ? "" : str.toUpperCase();
//                        str2 = str2 === null || str2 === undefined ? "" : str2.toUpperCase();
//                        if (str !== "SUCCESSFUL" || str2 !== "SUCCESSFUL") {
//                            if (str === "PENDING") {
//                                return "blue";
//                            }
//                            if (str2 !== "PENDING") {
//                                return "blue";
//                            }
//
//                            return "red";
//                        }
//
//                    }
//                };
//                $scope.flagColor = function (str) {
//                    if ($scope.status === "ALL") {
//                        if (str.toUpperCase() === "PENDING") {
//                            return "yellow";
//                        }
//                    }
//                    if ($scope.status === "SUCCESSFUL") {
//                        if (str.toUpperCase() === "PENDING") {
//                            return "yellow";
//                        }
//                    }
//                    if ($scope.status === "FAILED") {
//                        if (str.toUpperCase() === "PENDING") {
//                            return "yellow";
//                        }
//                    }
//                };
//                $scope.barRowColor = function (str) {
//                    if ($scope.status === "ALL") {
//                        if (!(str === "000" || str.toUpperCase().indexOf("SUCCESSFUL") > -1)) {
//                            return "red";
//                        }
//                    }
//                };
//                $scope.vtuRowColor = function (str) {
//                    if ($scope.status === "ALL") {
//                        if (str !== "0") {
//                            return "red";
//                        }
//                    }
//                };
//                $scope.jpRowColor = function (str, str2) {
////                if ($scope.status === "ALL") {
//                    if (str2 === "SUCCESSFUL" || str2 === "SUCCESS") {
//                        if (str === "0" || str === "00") {
//                        } else {
//                            return "red";
//                        }
//                    } else if (str2 === "PENDING" || str === "58") {
//                        return "blue";
//                    } else {
//                        return "red";
//                    }
////                }
//                };
//                $scope.sasRowColor = function (str) {
//                    if ($scope.status === "ALL") {
//                        if (str === "0" || str === "00") {
//                        } else {
//                            return "red";
//                        }
//                    }
//                };
//                $scope.tmcRowColor = function (str) {
//                    if (str === "00" || str === "0") {
//
//                    } else {
//                        switch ($scope.status) {
//                            case "ALL":
//                                if (str === "51" || str === "55" || str === "57") {
//                                    return "yellow";
//                                } else if (str === "58") {
//                                    return "blue";
//                                } else
//                                    return "red";
//                                break;
//                            case "REVERSAL":
//                                if (str === "51" || str === "55" || str === "57") {
//                                    return "yellow";
//                                } else if (str === "58") {
//                                    return "blue";
//                                } else
//                                    return "red";
//                                break;
//                            case "FAIL":
//                                if (str === "51" || str === "55" || str === "57") {
//                                    return "yellow";
//                                } else if (str === "58") {
//                                    return "blue";
//                                } else
//                                    return "red";
//                                break;
//                            case "FAILED":
//                                if (str === "51" || str === "55" || str === "57") {
//                                    return "yellow";
//                                } else if (str === "58") {
//                                    return "blue";
//                                } else
//                                    return "red";
//                                break;
//                            case "AMBIGUOUS":
//                                if (str === "51" || str === "55" || str === "57") {
//                                    return "yellow";
//                                } else if (str === "58") {
//                                    return "blue";
//                                } else
//                                    return "red";
//                                break;
//                            case "SUCCESSFUL":
//                                break;
//                            default:
//                                break;
//                        }
//                    }
//
//                };
//            }]);