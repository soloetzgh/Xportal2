///* global Swal, moment, pdfMake, XLSX, accounting, Symbol */
//
//
//var toastr;
//$(document).ready(function () {
//
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
//        "showMethod": "fadeIn",
//        "hideMethod": "fadeOut"
//    };
//});
//var $routeProviderReference;
//var IN_DEVELOPMENT = true;
//var environment = window.location.hostname;
//var routes = "";
//var userProfileData = "";
//var userProfiles = "";
//var portalSettings = "";
//var roleElementBound = false;
//var autoRefreshRate = 5000;
//var settings = "";
//var smsprocessorUrl = "../XPortalProxy/api/SmsProcessor";
//var app = angular.module('XPortalApp', ['ngRoute', 'ngAnimate', 'oc.lazyLoad', 'angular.filter', 'userInfo', 'menuList', 'menus', 'ngIdle', 'swangular']);
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
//        return $http.get('../XPortalProxy/api/PortalSettings?action=menuList').then(
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
//        return $http.get('../XPortalProxy/api/PortalSettings?action=config').then(
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
//    $http.get('../XPortalProxy/api/UserProfile').then(
//            function (success) {
//
//                // Define a 'userInfo' module.
//                angular.module('userInfo', []).constant('userInfo', success.data);
//                userProfileData = success.data;
////                Fetch MenuItems
//                $rootScope.fetchMenuList().then(function (menuList) {
////                    console.log(JSON.stringify(menuList));
//                    routes = menuList;
//                    angular.module('menuList', []).constant('menuList', menuList);
//                    $rootScope.fetchMenu(menuList).then(function (menus) {
//                        $rootScope.fetchPortalSettings().then(function (settingsData) {
////                        console.log(JSON.stringify(menus));
//                            settings = settingsData;
//                            console.log('settingos: ' + settings);
//                            angular.module('menus', []).constant('menus', menus);
//                            angular.element(document).ready(function () {
//                                angular.bootstrap(document, ['XPortalApp']);
//                            });
//                        });
//                    });
//                });
//            });
//})();
//app.config(['$httpProvider', '$routeProvider', 'IdleProvider', 'KeepaliveProvider', function ($httpProvider, $routeProvider, IdleProvider, KeepaliveProvider) {
//
//        //calculate time
//        var session_timeout = settings.session_timeout;
//        IdleProvider.idle(session_timeout - 10);
//        IdleProvider.timeout(10);
//        //KeepaliveProvider.interval(10);
//
//        $httpProvider.interceptors.push('ajaxNonceInterceptor');
//        $httpProvider.interceptors.push('responseInterceptor');
//        $httpProvider.interceptors.push('csrfInterceptor');
//        $routeProvider.caseInsensitiveMatch = true;
//        angular.forEach(routes, function (route) {
//
//            if (route.controller !== null && route.controller !== undefined) {
////                console.log('route: ' + route.name + '\n access: ' + route.controller);
//                $routeProvider.when('/' + route.routePath, {
//                    title: route.name,
//                    templateUrl: '../XPortalProxy/views/' + route.templateUrl,
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
//                                    auth: profileService.authenticate(route.accessLevel, route.openTo)
//                                });
//                            }]
////                        'auth': function (profileService) {
////                            return profileService.authenticate(route.accessLevel, route.openTo);
////                        }
//                    }
//                });
//            }
//        });
//        console.log('done');
////        $routeProvider.when('/',{
////            redirectTo: '/auth'
////        });
//        $routeProvider.otherwise({redirectTo: '/auth'});
////        $route.reload();
//
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
//                                '../XPortalProxy/assets/css/rwd-table.min.css',
//                                '../XPortalProxy/assets/css/all.min.css',
//                                '../XPortalProxy/assets/css/bootstrap-toggle.min.css',
////                                '../XPortalProxy/assets/css/uikit.almost-flat.min.css',
//                                '../XPortalProxy/assets/css/jquery.datetimepicker.min.css',
//                                '../XPortalProxy/assets/css/jquery.contextMenu.min.css',
//                                '../XPortalProxy/assets/css/daterangepicker.min.css',
//                                '../XPortalProxy/assets/js/rwd-table.js',
//                                '../XPortalProxy/assets/js/app.js',
//                                '../XPortalProxy/assets/js/chart.min.js',
//                                '../XPortalProxy/assets/js/dynamicgraph.js',
//                                '../XPortalProxy/assets/js/xlsx.full.min.js',
//                                '../XPortalProxy/assets/js/ng-file-upload-shim.js',
//                                '../XPortalProxy/assets/js/ng-file-upload.js',
//                                '../XPortalProxy/assets/js/jquery.datetimepicker.full.min.js',
//                                '../XPortalProxy/assets/js/daterangepicker.min.js',
//                                '../XPortalProxy/assets/js/moment.min.js',
//                                '../XPortalProxy/assets/js/jquery.stickyheader.js',
//                                '../XPortalProxy/assets/js/jquery.ba-throttle-debounce.min.js',
//                                '../XPortalProxy/assets/js/money.min.js',
//                                '../XPortalProxy/assets/js/angular-long-press.min.js',
//                                '../XPortalProxy/assets/js/jquery.contextMenu.min.js',
//                                '../XPortalProxy/assets/js/jquery.ui.position.js',
//                                '../XPortalProxy/assets/js/bootstrap-toggle.min.js'
//                            ],
//                            serie: true
//                        }]
//                }
//                );
//            }])
//        .run(function ($route, $rootScope, profileService, $location, menuList, menus, $http, $httpParamSerializerJQLike, swangular) {
//            $rootScope.menus = menus;
//            $rootScope.menuList = menuList;
//            $rootScope.timerate = autoRefreshRate;
//            $rootScope.showLoading = function () {
//                $('.loader').css('display', 'block');
//            };
//            $rootScope.hideLoading = function () {
//                $('.loader').css('display', 'none');
//                $rootScope.updateRecordsTable();
//            };
//            $rootScope.toggleFab = function () {
////                $('.floating-menus').toggle();
//                $('.float-btn-group').toggleClass('open');
//            };
//            $.LoadingOverlaySetup({
//                background: "rgba(0, 0, 0, 1.0)",
//                image: "loader.png",
//                imageAnimation: "1.5s fadein"
////                imageColor: "#ffcc00"
//            });
//            $rootScope.goHome = function () {
//                window.location = '/XPortal/#!' + profileService.getUserProfile().firstRoute;
////                window.location = "/XPortal/#!/dashboard";
//            };
//            $rootScope.changepass = function () {
//                window.location = "/XPortal/#!/firstChangePassword";
//            };
//            $rootScope.menuFilter = function (item) {
//                if (item.showOnMenu === '1') {
//                    return item;
//                }
//            };
//            $rootScope.checkMenuList = function (type) {
//                var i = (userProfileData.type_id !== undefined && userProfileData.type_id !== null) ? userProfileData.type_id : "";
//                var count = $rootScope.menuList.reduce(function (acc, cur) {
//                    return cur.menu === type && (i.indexOf(cur.accessLevel) > -1 || (i.indexOf('[1]') > -1 && cur.openTo.indexOf('[1]') > -1)) ? ++acc : acc;
//                }, 0);
//                return count;
//            };
//            $rootScope.defaultState = false;
//            $rootScope.authenticated = false;
//            var profile = profileService.getUserProfile();
//            if (profile !== "") {
//                if (!($('#navbarmenu').hasClass('animated bounceIn'))) {
//                    $('#navbarmenu').addClass('animated bounceIn');
//                }
//            }
//
//            $rootScope.$on("$routeChangeStart", function (event, next, current) {
////                console.log('2-' + next.originalPath);
//                var route = next.originalPath !== undefined ? next.originalPath.replace("/", "") : "";
//                swangular.close();
////                var route = next.toString().split("#!")[1].replace("/", "");
//                console.log('route: ' + route);
////                console.log('route2: ' + JSON.stringify(next));
//
//                if (profileService.isAuthenticated()) {
//                    if (profileService.isFirstLogon()) {
//                        console.log('df');
//                        $rootScope.changepass();
//                        setTimeout(function () {
//                            $rootScope.toggleErrorNotification("Password Change Required");
//                        }, 1000);
//                    } else if (route === "") {
//                        $rootScope.goHome();
//                        console.log('a');
//                        setTimeout(function () {
//                            $rootScope.toggleErrorNotification("You are already Logged In");
//                        }, 1000);
//                    } else if (route === "auth") {
//                        console.log('b');
//                        $rootScope.goHome();
//                        setTimeout(function () {
//                            $rootScope.toggleErrorNotification("You are already Logged In");
//                        }, 1000);
//                    }
////                    else if (route !== "") {
////
////                    }
//                    else {
//
//                        if (!$rootScope.profile) {
//                            profileService.setUserProfile(JSON.stringify(profile));
//                        }
//
//                        $rootScope.authenticated = true;
//                        $('#navbarmenu').css('display', 'block');
//                        setTimeout(function () {
//                            $.LoadingOverlay("hide");
//                        }, 2000);
//                    }
//
//                } else {
//                    if ($('#navbarmenu').hasClass('animated bounceIn')) {
//                        $('#navbarmenu').removeClass('animated bounceIn');
//                    }
//                    $rootScope.authenticated = false;
//                    if (route !== 'auth') {
//                        window.location = './#!/auth';
//                    }
//                }
//
//
//
//
//
//
//
//
////                if (route === "" && profileService.isAuthenticated()) {
////                    if (profileService.isFirstLogon()) {
////                        console.log('df');
////                        $rootScope.changepass();
////                        setTimeout(function () {
////                            $rootScope.toggleErrorNotification("Password Change Required");
////                        }, 1000);
////                    } else {
////                        $rootScope.goHome();
////                        setTimeout(function () {
////                            $rootScope.toggleErrorNotification("You are already Logged In");
////                        }, 1000);
////                    }
////                } else {
//////                    console.log('first: ' + profileService.isFirstLogon());
////                    if (profileService.isFirstLogon()) {
////                        $rootScope.changepass();
////                        setTimeout(function () {
////                            $rootScope.toggleErrorNotification("Password Change Required");
////                        }, 1000);
////                    } else {
////                        console.log('1');
////                        if (route === 'auth') {
////                            console.log('2');
////                            if (!profileService.isAuthenticated()) {
////                                if ($('#navbarmenu').hasClass('animated bounceIn')) {
////                                    $('#navbarmenu').removeClass('animated bounceIn');
////                                }
////                                $rootScope.authenticated = false;
////                                console.log('3');
////                            } else {
////                                var url = next.originalPath !== undefined ? next.originalPath.replace("/", "") : "";
//////                        console.log('current: ' + url);
////                                if (url !== 'auth') {
////                                    event.preventDefault();
////                                } else {
////                                    $rootScope.goHome();
////                                    setTimeout(function () {
////                                        $rootScope.toggleErrorNotification("You are already Logged In");
////                                    }, 1000);
////                                }
////                            }
////
////                        }
//////                    profileService.updateUserProfile();
////                        if (!profileService.isAuthenticated()) {
////                            console.log('logging out');
////                            if ($('#navbarmenu').hasClass('animated bounceIn')) {
////                                $('#navbarmenu').removeClass('animated bounceIn');
////                            }
////                            $rootScope.authenticated = false;
////                            $location.path("/auth");
////                        } else if (!(profileService.isAuthenticated()) || (!(profileService.isAuthenticated()) && route !== 'auth')) {
////                            if ($('#navbarmenu').hasClass('animated bounceIn')) {
////                                $('#navbarmenu').removeClass('animated bounceIn');
////                            }
////                            $rootScope.authenticated = false;
//////                    console.log('redirecting');
////                            $location.path("/auth");
////                        } else {
////                            if (!$rootScope.profile) {
////                                profileService.setUserProfile(JSON.stringify(profile));
////                            }
////                            $rootScope.authenticated = true;
////                            $('#navbarmenu').css('display', 'block');
////                            setTimeout(function () {
////                                $.LoadingOverlay("hide");
////                            }, 2000);
////                        }
////                    }
////                }
//            });
//            $rootScope.$on('$routeChangeError', function (event, current, previous, rejection) {
//                console.log('reject: ' + rejection);
//                event.preventDefault();
//                if (rejection === 'Unauthorized') {
//                    $location.path('/unauthorized');
//                }
////                if (rejection === 'AuthenticationError') {
////                    $location.path('/auth');
////                }
//                if (rejection === 'Authenticated') {
////                    $location.path('/auth');
//                    console.log('authenticated already');
//                }
//            });
//            $rootScope.$on('$routeChangeSuccess', function (event, current, previous) {
//                $rootScope.title = current.$$route.title;
//
////                if (current.$$route.initMethod) {
////                    if (typeof $rootScope[current.$$route.initMethod] === "function") {
////                        $rootScope[current.$$route.initMethod]();
////                    }
////                }
//            });
//            $rootScope.userReady2 = function () {
//                if (profileService.isAuthenticated()) {
//                    if (profileService.isFirstLogon()) {
//                        $location.path('/firstChangePassword');
//                        return false;
//                    } else {
//                        return true;
//                    }
//                }
//            };
//            $rootScope.updateRecordsTable = function () {
//                setTimeout(function () {
//                    $('.table-responsive').responsiveTable('update');
//                }, 800);
//            };
//            $rootScope.toggleErrorNotification = function (message) {
//                toastr["error"](message);
//            };
//            $rootScope.toggleSuccessNotification = function (message) {
//                console.log('login');
//                toastr["success"](message);
//            };
//            $rootScope.toggleInfoNotification = function (message) {
//                toastr["info"](message);
//            };
//            $rootScope.logout = function () {
////                location.href = "./Logout";
//                console.log('logging out');
//                $http.get('../XPortalProxy/api/Logout').then(function () {
//                    window.localStorage.clear();
//                    profileService.logout();
////                    window.location = './#!/auth';
////                    $location.path("/auth");
////                    window.location.reload();
//                });
//            };
//            $rootScope.getRoleParams = function (rolesList, role) {
//                var roleParam = "";
//                if (rolesList) {
//                    var firstParam = rolesList.indexOf(role);
//                    var secondParam = rolesList.indexOf(",", firstParam);
//                    roleParam = rolesList.substring(firstParam, secondParam === -1 ? rolesList.length : secondParam).split("|")[1];
//                }
//                return roleParam;
//            };
//            $rootScope.testBillPayment = function () {
//                $rootScope.userReady2();
//                var i = ($rootScope.profile.type_id !== undefined && $rootScope.profile.type_id !== null) ? $rootScope.profile.type_id : "";
//                if (i.indexOf("[26]") !== -1) {
//                    return false;
//                }
//                if ($rootScope.testBank() || $rootScope.testVasMerchant()) {
//                    return true;
//                }
//                if ($rootScope.testEtz() || i.indexOf("[79]") !== -1 || i.indexOf("[32]") !== -1) {
//                    return true;
//                }
//                return false;
//            };
//            $rootScope.testTools = function () {
//                if ($rootScope.testMomoStatus() || $rootScope.testMobileInvestigation() || $rootScope.testFGTrx() || $rootScope.testMobileAppActivation()) { // || $rootScope.testBarclays()) {
//                    return true;
//                }
//                return false;
//            };
//            $rootScope.testJP = function () {
//                $rootScope.userReady2();
//                var i = ($rootScope.profile.type_id !== undefined && $rootScope.profile.type_id !== null) ? $rootScope.profile.type_id : "";
//                if ($rootScope.testEtz() || i.indexOf("[79]") !== -1 || i.indexOf("[2000000000000054]") !== -1) {
//                    return true;
//                }
//                return false;
//            };
//            $rootScope.testEtz = function () {
////                $rootScope.userReady2();
//                var i = ($rootScope.profile.type_id !== undefined && $rootScope.profile.type_id !== null) ? $rootScope.profile.type_id : "";
//                if (i.indexOf("[1]") !== -1) {
//                    return true;
//                }
//                return false;
//            };
//            $rootScope.testAdmin = function () {
//                $rootScope.userReady2();
//                if ($rootScope.testAdm()) { //|| testUserMgt()) {
//                    return true;
//                }
//
//                return false;
//            };
//            $rootScope.testTech = function () {
//                $rootScope.userReady2();
//                var i = ($rootScope.profile.type_id !== undefined && $rootScope.profile.type_id !== null) ? $rootScope.profile.type_id : "";
//                if (i.indexOf("[1]") !== -1 || i.indexOf("[2]") !== -1 || i.indexOf("[8]") !== -1 || i.indexOf("[22]") !== -1 || i.indexOf("[2000000000000050]") !== -1) {
//                    return true;
//                }
//                return false;
//            };
//            $rootScope.testAdm = function () {
//                $rootScope.userReady2();
//                var i = ($rootScope.profile.type_id !== undefined && $rootScope.profile.type_id !== null) ? $rootScope.profile.type_id : "";
//                var j = $rootScope.profile.role_id;
//                if (i.indexOf("[1]") !== -1 || i.indexOf("[2]") !== -1 || j === "1") {
//                    return true;
//                }
//                return false;
//            };
//            $rootScope.testUserMgt = function () {
//                var i = $rootScope.profile.Admin;
//                if (i.indexOf("[1]") !== -1 || i.indexOf("[2]") !== -1) {
//                    return true;
//                }
//                return false;
//            };
//            $rootScope.testHideFee = function () {
//
//                var i = ($rootScope.profile.type_id !== undefined && $rootScope.profile.type_id !== null) ? $rootScope.profile.type_id : "";
//                var j = $rootScope.profile.user_code;
//                if (j !== 'ETZ:6WCI') {
//                    return true;
//                }
//                return false;
//            };
//            $rootScope.testEtzMerchantAll = function () {
//                var j = $rootScope.profile.user_code;
//                if (j !== 'ETZ:ALL') {
//                    return true;
//                }
//                return false;
//            };
//            $rootScope.testMerchantPayAll = function () {
//                var j = $rootScope.profile.user_code;
////                console.log('rec: ' + j);
////                console.log('rec2: ' + profileService.getUserProfile());
//                if ((j.indexOf('ETZ:ALL') > -1) || (j.indexOf('GCB:ALL') > -1) || (j.indexOf('SCB:ALL') > -1)) {
//                    return true;
//                }
//                return false;
//            };
//        })
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
//
//                if ($rootScope.profile !== "") {
//                    console.log('saving: ' + type);
//                    window.localStorage.setItem("userData", $rootScope.profile);
//                    if (type === 1) {
//                        $rootScope.authenticated = true;
//                    }
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
////                    var profileExists = (typeof userProfileData === "string" ? userProfileData.length > 0 : Object.keys(userProfileData).length > 0);
////                    console.log('profileExists: ' + profileExists);
////                    $http.get('../XPortalProxy/api/UserProfile').then(
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
//                    console.log('profileExists: ' + profileExists);
//                    $http.get('../XPortalProxy/api/RefreshToken').then(
//                            function (success) {
////                                console.log('updating profile');
//                                if (success.data) {
//                                    addUserProfile(success.data);
////                                    console.log('checking firstlogon: ' + $rootScope.profile.first_logon);
//                                    if (getFirstLogon()) {
//                                        $location.path('/firstChangePassword');
//                                    }
//                                } else {
//                                    if ($('#navbarmenu').hasClass('animated bounceIn')) {
//                                        $('#navbarmenu').removeClass('animated bounceIn');
//                                    }
//                                    if ($rootScope.authenticated) {
//                                        $rootScope.authenticated = false;
//                                        setTimeout(function () {
//                                            $rootScope.toggleErrorNotification("Your Session Timed out");
//                                        }, 500);
//                                    }
//                                    location.href = "/auth";
//                                }
//                            });
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
//                    console.log('userProfile: ' + JSON.stringify(userProfile));
//                    return (userProfile !== "" && userProfile !== "{}" && userProfile.message !== "Session Expired");
//                },
//                isFirstLogon: function () {
//                    console.log('userProfile: ');
//                    return userProfile.first_logon === "0" || userProfile.temp_password === "true";
//                },
//                authenticate: function (role, openTo) {
////                    console.log('role: ' + role);
//                    console.log('1');
//                    if (role !== "[2]" && role !== "[3]" && role !== "[4]" && role !== "[5]" && role !== "[6]") {
////                        console.log('data: '+ JSON.stringify(userProfile));
////                        console.log('type: '+ typeof userProfile);
//                        var userState = typeof userProfile === 'string' ? JSON.parse(userProfile) : userProfile;
////                        console.log('checking auth: ' + userState.type_id);
//                        if (userState.type_id) {
//                            console.log('checking role: ' + role);
//                            if (userState.type_id.indexOf(role) > -1 || (userState.type_id.indexOf('[1]') > -1 && openTo.indexOf('[1]') > -1)) {
//                                return true;
//                            } else {
//                                return $q.reject("Unauthorized");
//                            }
//                        } else {
////                        return $q.reject("AuthenticationError");
//                            $location.path("/auth");
//                        }
//                    } else {
//                        return true;
//                    }
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
////                    console.log('resp code: ' + response.headers()['content-type']);
//
//                    if (response.headers()['content-type'].indexOf("application/json") > -1) {
//                        // Validate response, if not ok reject
////                        console.log('response msg');
//
//                        if (typeof response === 'object') {
////                            console.log('object response');
////                            console.log('r -> ' + response.data.message);
//                            if (response.data.message === 'Session Expired') {
//                                $rootScope.toggleErrorNotification(response.data.message);
//                                $rootScope.logout();
//                                return $q.reject(response);
//                            } else if (response.data.message === 'OTP Required') {
//                                $rootScope.toggleErrorNotification(response.data.message);
//                                $rootScope.logout();
//                                return $q.reject(response);
//                            }
//
//                        }
//                    }
//                    return response;
//                },
//                responseError: function (response) {
//                    // do something on error
//                    $rootScope.toggleErrorNotification("Connection or Server Error. Try again");
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
//                    }
//                    return config;
//                },
//                response: function (config) {
////                    console.log('re + ' + config.headers()['xsrf-token']);
//                    if (config.headers()['xsrf-token']) {
//                        $rootScope.csrf = config.headers()['xsrf-token'];
//                        if ($('#csrf').length > 0) {
//                            $('#csrf').val($rootScope.csrf);
//                        }
//                    }
//
//                    return config;
//                }
//            };
//        })
//        .service('storeData', function ($http) {
//            this.searchResults = [];
//            this.promiseResolvingFnInService = function (response) {
//                this.searchResults = response.data; //assuming array is inside data prop of API response.
//            };
//            this.putData = function (method, url, headers, data) {
//
//                // call the back end
//                return $http({
//                    method: method,
//                    url: url,
//                    headers: headers,
//                    data: data
//                });
//            };
//        })
//        .controller('supportCtrl', function ($scope, userInfo, $rootScope, profileService, $http, $httpParamSerializerJQLike) {
//
//            $scope.profile = userInfo;
//            $scope.showPassword1 = "password";
//            $scope.showPassword2 = "password";
//            $scope.showPassword3 = "password";
//            $(".load-icon").removeClass("fa-check");
//            $(".load-icon").addClass("fa-cog fa-spin");
//            $scope.pwdLoader = false;
//            $scope.pwdUrl = "../XPortalProxy/api/ChangePassword";
//            $scope.pwdUrl2 = "../XPortalProxy/api/ChangePasswordNew";
//            $scope.executeDialogAction = function (action) {
//                if (typeof $scope[action] === "function") {
//                    $scope[action]();
//                }
//            };
//            $scope.toggleShowPassword = function (type) {
//                if ($('.' + type).hasClass('fa-eye')) {
//                    $('.' + type).removeClass('fa-eye');
//                    $('.' + type).addClass('fa-eye-slash');
//                } else {
//                    $('.' + type).addClass('fa-eye');
//                    $('.' + type).removeClass('fa-eye-slash');
//                }
//                $scope[type] = $scope[type] === 'text' ? 'password' : 'text';
//            };
//            $scope.toggleSuccess = function () {
//                $('.circle-loader').removeClass('load-error').toggleClass('load-complete');
//                $('.checkmark').removeClass('error').addClass('draw').toggle();
//            };
//            $scope.toggleError = function () {
//                $('.circle-loader').removeClass('load-complete').toggleClass('load-error');
//                $('.checkmark').removeClass('draw').addClass('error').toggle();
//            };
//            $scope.hideOverlay = function (type) {
//                if (type === 'error') {
//                    $scope.toggleError();
//                }
//                $('.authLoading').css('display', 'none');
//                $('.authLogin').css('display', 'block');
//            };
//            $scope.showOverlay = function () {
//                $('.overlay').addClass('blur');
//                $('.authLoading').css('display', 'block');
//                $('.authLogin').css('display', 'none');
//            };
////            $scope.initChangePassword = function () {
////                console.log('email: '+ $scope.profile.email);
////                $scope.auth.email = $scope.profile.email;
////            };
//            $scope.changePassword = function () {
//                $scope.passwordChangeMessage = "";
//                $scope.validForm = true;
//                if (!$scope.auth.oldpassword) {
//                    $('.oldpassword').addClass('alert-validate');
//                    setTimeout(function () {
//                        $('.oldpassword').removeClass('alert-validate');
//                    }, 2000);
//                    $scope.validForm = false;
//                }
//                if (!$scope.auth.newpassword) {
//                    $('.newpassword').addClass('alert-validate');
//                    setTimeout(function () {
//                        $('.newpassword').removeClass('alert-validate');
//                    }, 2000);
//                    $scope.validForm = false;
//                }
//                if (!$scope.auth.confpassword) {
//                    $('.confpassword').addClass('alert-validate');
//                    setTimeout(function () {
//                        $('.confpassword').removeClass('alert-validate');
//                    }, 2000);
//                    $scope.validForm = false;
//                }
//                var regex = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/;
//                if (!regex.test($scope.auth.email)) {
//                    $('.email').addClass('alert-validate');
//                    setTimeout(function () {
//                        $('.email').removeClass('alert-validate');
//                    }, 2000);
//                    $scope.validForm = false;
//                }
//                if ($scope.validForm) {
//                    $scope.showOverlay();
//                    $http({
//                        url: $scope.pwdUrl,
//                        method: 'POST',
//                        data: $httpParamSerializerJQLike({
//                            "oldPwd": $scope.auth.oldpassword,
//                            "newPwd": $scope.auth.newpassword,
//                            "confPwd": $scope.auth.confpassword,
//                            "email": $scope.auth.email
//                        }),
//                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                    }).then(function success(response) {
//                        $scope.authResp = response.data;
//                        $scope.type = "";
//                        $scope.auth.oldpassword = "";
//                        $scope.auth.newpassword = "";
//                        $scope.auth.confpassword = "";
//                        if ($scope.authResp.trim() !== "success") {
//                            $scope.toggleError();
//                            $scope.type = "error";
//                            $scope.passwordChangeMessage = $scope.authResp.trim();
//                        } else {
//                            //save user
//                            $scope.passwordChangeMessage = "Password Changed Successfully";
//                            $scope.toggleSuccess();
//                            $scope.type = "success";
//                        }
//                        setTimeout(function () {
//
//                            $scope.$apply($scope.hideOverlay($scope.type));
//                        }, 1500);
//                    }, function error(response) {
////                        $scope.showLoader = false; $rootScope.hideLoading();
////                        $('.reload').removeClass('fa-spin text-info');
//                        $scope.toggleError();
//                        $scope.type = "error";
//                        $scope.hideOverlay($scope.type);
//                        //$rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                    });
//                }
//            };
//            $scope.changePasswordNew = function () {
//                profileService.updateUserProfile();
//                $scope.pwdLoader = true;
//                $http({
//                    url: $scope.pwdUrl2,
//                    method: 'POST',
//                    data: $httpParamSerializerJQLike({
//                        "oldPwd": $scope.oldPwd,
//                        "newPwd": $scope.newPwd,
//                        "confPwd": $scope.confPwd,
//                        "email": $scope.email
//
//                    }),
//                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                }).then(function success(response) {
//                    var rsp = response.data;
//                    if (rsp.trim() === "success") {
//                        $(".load-icon").addClass("fa-check");
//                        $(".load-icon").removeClass("fa-cog fa-spin");
//                        $(".rsp").html("<span class='text-success'>Password change successful<span> <br><a href='#!auth' class='text-primary font-weight-bold'>Continue</a> ");
//                    } else {
//                        $(".load-icon").removeClass("fa-check");
//                        $(".load-icon").addClass("fa-cog fa-spin");
//                        $(".rsp").html("<span class='text-danger'>" + rsp + "</span>");
//                    }
//                }, function error(response) {
//                    $(".load-icon").removeClass("fa-check");
//                    $(".load-icon").addClass("fa-cog fa-spin");
//                    $(".rsp").html("<span class='text-danger'>An error occured. Please try again</span>");
//                });
//            };
//            $scope.fetchUserEmail = function () {
//                $scope.email = $scope.profile.email;
//            };
//            $scope.hideFilterBox = function () {
//                $scope.pwdLoader = false;
//                console.log('dsf');
//            };
//        })
//        .controller('noCtrl', function () {})
//        .controller('authCtrl', function ($scope, userInfo, $rootScope, profileService, $http, $httpParamSerializerJQLike, $location, swangular) {
//            $scope.showPassword = "password";
//            $scope.showPassword1 = "password";
//            $scope.showPassword2 = "password";
//            $scope.showPassword3 = "password";
//            $scope.pwdUrl = "../XPortalProxy/api/ChangePassword";
//            $scope.pwdUrl2 = "../XPortalProxy/api/ChangePasswordNew";
//            $scope.executeDialogAction = function (action) {
//                if (typeof $scope[action] === "function") {
//                    $scope[action]();
//                }
//            };
//            $scope.toggleShowPassword = function (type) {
//                if ($('.' + type).hasClass('fa-eye')) {
//                    $('.' + type).removeClass('fa-eye');
//                    $('.' + type).addClass('fa-eye-slash');
//                } else {
//                    $('.' + type).addClass('fa-eye');
//                    $('.' + type).removeClass('fa-eye-slash');
//                }
//                $scope[type] = $scope[type] === 'text' ? 'password' : 'text';
//            };
//            $scope.toggleAuthView = function (formType) {
//                $scope.showResetForm = !$scope.showResetForm;
//                if (formType === 'login') {
//                    $('.loginForm').css('display', 'block');
//                    $('.resetForm').css('display', 'none');
//                } else {
//                    $('.loginForm').css('display', 'none');
//                    $('.resetForm').css('display', 'block');
//                }
//            };
//            $scope.toggleSuccess = function () {
//                $('.circle-loader').removeClass('load-error').toggleClass('load-complete');
//                $('.checkmark').removeClass('error').addClass('draw').toggle();
//            };
//            $scope.toggleError = function () {
//                $('.circle-loader').removeClass('load-complete').toggleClass('load-error');
//                $('.checkmark').removeClass('draw').addClass('error').toggle();
//            };
//            $scope.hideOverlay = function (type) {
//                if (type === 'error') {
//                    $scope.toggleError();
//                    $('.authLoading').css('display', 'none');
//                    $('.authLogin').css('display', 'block');
//                } else {
////                    $scope.toggleSuccess();
////                    $('.authLoading').css('display', 'none');
////                    $('.authLogin').css('display', 'block');
//                }
//
//            };
//            $scope.showOverlay = function () {
//                $('.overlay').addClass('blur');
//                $('.authLoading').css('display', 'block');
//                $('.authLogin').css('display', 'none');
//            };
//            $scope.showOtp = function () {
////                location.href = "./Logout";
//                console.log('showing OTP');
//                swangular.swal({
//                    title: "Enter OTP",
////                    text: "This will " + action + " " + $scope.username,
////                    html: "This will <b>" + action + " " + $scope.username + "</b>",
//                    type: 'info',
//                    text: "Check your Email for OTP",
//                    input: 'text',
//                    showCancelButton: true,
//                    confirmButtonColor: '#3085d6',
//                    cancelButtonColor: '#d33',
//                    confirmButtonText: 'Login',
//                    showLoaderOnConfirm: true,
//                    preConfirm: function preConfirm(login) {
//                        console.log('action: ' + login);
//                        console.log('calling');
//                        return $http({
//                            url: "../XPortalProxy/api/ValidateOtp",
//                            method: 'POST',
//                            data: $httpParamSerializerJQLike({
//                                "otp": login
//                            }),
//                            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                        }).then(function success(response) {
//                            console.log(response.data);
//                            return response;
//                        }).catch(function (error) {
//                            swangular.showValidationMessage("Request failed ");
//                        });
//                    },
//                    allowOutsideClick: function allowOutsideClick() {
//                        return !Swal.isLoading();
//                    }
//                }).then(function (result) {
//                    if (result.value) {
//                        var resp = result.value.data.code;
//                        if (resp === '00') {
//
//                            $scope.toggleMsg();
//                        } else {
//                            swangular.showValidationMessage(result.value.data);
//                        }
//                        //Swal.fire({title: respTitle, text: respText, type: respType, timer: 1500});
//                    } else {
//
//                        swangular.showValidationMessage("An Error Occured");
//                    }
//                });
//            };
//            $scope.toggleMsg = function () {
//                $scope.toggleSuccess();
//                if ($scope.type === "success") {
//                    console.log('data: ' + JSON.parse($scope.authResp.userData).first_logon);
//                    profileService.setUserProfile($scope.authResp.userData, 0);
//                    if (JSON.parse($scope.authResp.userData).first_logon === '0') {
//                        setTimeout(function () {
//                            $scope.$apply($location.path("/firstChangePassword"));
//                            console.log('auth');
//                        }, 1300);
//                    } else {
//                        setTimeout(function () {
//                            $.LoadingOverlay("show");
//                        }, 1300);
//                        setTimeout(function () {
//                            $scope.$apply($scope.hideOverlay($scope.type));
//                            if ($scope.type === "success") {
//                                $rootScope.authenticated = true;
//                                $('#navbarmenu').css('display', 'block');
////                                $rootScope.profile = $scope.authResp.userData;
//                                console.log('setting profile: ' + JSON.parse($scope.authResp.userData).first_logon);
//                                profileService.setUserProfile($scope.authResp.userData);
//                                console.log('router : ' + '/XPortal/#!' + JSON.parse($scope.authResp.userData).firstRoute);
//                                window.location = '/XPortal/#!' + JSON.parse($scope.authResp.userData).firstRoute;
////                                        $location.path($scope.authResp.nextRoute);
////                                        if()
////                                        window.location = './#!/requestLog';
//                                setTimeout(function () {
//                                    $scope.$apply($rootScope.toggleSuccessNotification('Welcome ' + $rootScope.profile.username));
//                                }, 1200);
//                            }
//                        }, 1500);
//                    }
//                } else {
//                    setTimeout(function () {
//                        $scope.$apply($scope.hideOverlay($scope.type));
//                    }, 1700);
//                }
//            };
//            $scope.Login = function () {
//                $scope.loginMessage = "";
//                $scope.extraInfo = "";
//                if (!$scope.auth.username) {
//                    $('.username').addClass('alert-validate');
//                    setTimeout(function () {
//                        $('.username').removeClass('alert-validate');
//                    }, 1500);
//                }
//                if (!$scope.auth.password) {
//                    $('.password').addClass('alert-validate');
//                    setTimeout(function () {
//                        $('.password').removeClass('alert-validate');
//                    }, 1500);
//                }
//                if ($scope.auth.password && $scope.auth.username) {
//                    $scope.showOverlay();
//                    $http({
//                        url: "../XPortalProxy/api/Authenticator",
//                        method: 'POST',
//                        data: $httpParamSerializerJQLike({
//                            "username": $scope.auth.username,
//                            "password": $scope.auth.password
//                        }),
//                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                    }).then(function success(response) {
//                        console.log('resp: ' + response.data.message);
//                        $scope.authResp = response.data;
//                        $scope.type = "";
//                        $scope.auth.username = "";
//                        $scope.auth.password = "";
//                        $scope.loginMessage = $scope.authResp.message;
//                        $scope.extraInfo = $scope.authResp.extra_info;
//                        if ($scope.authResp.message !== "Successful Login") {
//                            $scope.toggleError();
//                            $scope.type = "error";
//                            //hideOverlay
//                            setTimeout(function () {
//
//                                $scope.$apply($scope.hideOverlay($scope.type));
//                            }, 1500);
//                        } else {
//                            //save user
//
//                            $scope.type = "success";
//                            if ($scope.authResp.userData && JSON.parse($scope.authResp.userData).requires2FA) {
//                                console.log('USER REQUIRES 2FA');
//                                $scope.showOtp();
//                            } else {
//                                $scope.toggleMsg();
//                            }
//                        }
//
//                    }, function error(response) {
////                        $scope.showLoader = false; $rootScope.hideLoading();
////                        $('.reload').removeClass('fa-spin text-info');
//                        $scope.toggleError();
//                        $scope.type = "error";
//                        $scope.hideOverlay($scope.type);
//                        //$rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                    });
//                }
//            };
//            $scope.Reset = function () {
//                if (!$scope.auth.username) {
//                    $('.username').addClass('alert-validate');
//                    setTimeout(function () {
//                        $('.username').removeClass('alert-validate');
//                    }, 1500);
//                } else {
//                    $scope.showOverlay();
//                    $http({
//                        url: "../XPortalProxy/api/PasswordReset",
//                        method: 'POST',
//                        data: $httpParamSerializerJQLike({
//                            "username": $scope.auth.username,
//                            "password": $scope.auth.password
//                        }),
//                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                    }).then(function success(response) {
//                        console.log('resp: ' + response.data.message);
//                        $scope.authResp = response.data;
//                        $scope.type = "";
//                        $scope.auth.username = "";
//                        $scope.auth.password = "";
//                        if (!($scope.authResp.message.indexOf("sent to") > -1)) {
//                            $scope.toggleError();
//                            $scope.type = "error";
//                            setTimeout(function () {
//                                $scope.$apply($scope.hideOverlay($scope.type));
//                            }, 2500);
//                        } else {
//                            $scope.toggleSuccess();
//                            $scope.type = "success";
//                            setTimeout(function () {
//                                $('.authLoading').css('display', 'none');
//                                $('.authLogin').css('display', 'block');
//                            }, 2500);
//                        }
//                        $scope.loginMessage = $scope.authResp.message;
//                        //hideOverlay
//
//
//                    }, function error(response) {
////                       $scope.toggleError();
//                        $scope.type = "error";
//                        $scope.hideOverlay($scope.type);
//                        //$rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                    });
//                }
//            };
//            $scope.changePassword = function () {
//                $scope.passwordChangeMessage = "";
//                $scope.validForm = true;
//                if (!$scope.auth.oldpassword) {
//                    $('.oldpassword').addClass('alert-validate');
//                    setTimeout(function () {
//                        $('.oldpassword').removeClass('alert-validate');
//                    }, 2000);
//                    $scope.validForm = false;
//                }
//                if (!$scope.auth.newpassword) {
//                    $('.newpassword').addClass('alert-validate');
//                    setTimeout(function () {
//                        $('.newpassword').removeClass('alert-validate');
//                    }, 2000);
//                    $scope.validForm = false;
//                }
//                if (!$scope.auth.confpassword) {
//                    $('.confpassword').addClass('alert-validate');
//                    setTimeout(function () {
//                        $('.confpassword').removeClass('alert-validate');
//                    }, 2000);
//                    $scope.validForm = false;
//                }
//                var regex = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/;
//                if (!regex.test($scope.auth.email)) {
//                    $('.email').addClass('alert-validate');
//                    setTimeout(function () {
//                        $('.email').removeClass('alert-validate');
//                    }, 2000);
//                    $scope.validForm = false;
//                }
//                if ($scope.validForm) {
//                    $scope.showOverlay();
//                    $http({
//                        url: $scope.pwdUrl,
//                        method: 'POST',
//                        data: $httpParamSerializerJQLike({
//                            "oldPwd": $scope.auth.oldpassword,
//                            "newPwd": $scope.auth.newpassword,
//                            "confPwd": $scope.auth.confpassword,
//                            "email": $scope.auth.email
//                        }),
//                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                    }).then(function success(response) {
//                        $scope.authResp = response.data;
//                        $scope.type = "";
//                        $scope.auth.oldpassword = "";
//                        $scope.auth.newpassword = "";
//                        $scope.auth.confpassword = "";
//                        if ($scope.authResp.trim() !== "success") {
//                            $scope.toggleError();
//                            $scope.type = "error";
//                            $scope.passwordChangeMessage = $scope.authResp.trim();
//                        } else {
//                            //save user
//                            $scope.passwordChangeMessage = "Password Changed Successfully";
//                            $scope.toggleSuccess();
//                            $scope.type = "success";
//                        }
//                        setTimeout(function () {
//                            $scope.$apply($scope.hideOverlay($scope.type));
//                        }, 1000);
//                        if ($scope.authResp.trim() === "success") {
//                            setTimeout(function () {
////                            location.href = "./Logout";
//                                $rootScope.logout();
//                                window.localStorage.clear();
//                            }, 1300);
//                        }
//                    }, function error(response) {
////                        $scope.showLoader = false; $rootScope.hideLoading();
////                        $('.reload').removeClass('fa-spin text-info');
//                        $scope.toggleError();
//                        $scope.type = "error";
//                        $scope.hideOverlay($scope.type);
//                        //$rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                    });
//                }
//            };
//        })
//        .controller('adminCtrl', function ($scope, Idle, Keepalive, userInfo, $rootScope, profileService, $http, $httpParamSerializerJQLike) {
//
//            $scope.role = "0";
//            $scope.profile = userInfo;
//            $scope.initInfo = function () {
//                $http.get('./Administrator?action=roles').then(
//                        function (response) {
//                            $scope.roles = response.data;
//                        }
//                );
//            };
//            $scope.updateApp = function () {
//                profileService.updateUserProfile();
//            };
//            $(".load-icon").removeClass("fa-check");
//            $(".load-icon").addClass("fa-cog fa-spin");
//            $scope.pwdLoader = false;
//            $scope.admnUrl = "./Administrator";
//            $scope.showFilterBox = true;
//            $scope.createUser = function () {
//                $scope.updateApp();
//                $scope.pwdLoader = true;
//                $http({
//                    url: $scope.admnUrl,
//                    method: 'POST',
//                    data: $httpParamSerializerJQLike({
//                        "username": $scope.username,
//                        "role": $scope.role,
//                        "service": "create"
//                    }),
//                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                }).then(function success(response) {
//                    var rsp = response.data;
//                    if (rsp.trim() === "success") {
//                        $(".load-icon").addClass("fa-check");
//                        $(".load-icon").removeClass("fa-cog fa-spin");
//                        $scope.clearForm();
//                        $(".rsp").html("<span class='text-success'>Update successful<span> <br> ");
//                    } else {
//                        $(".load-icon").removeClass("fa-check");
//                        $(".load-icon").addClass("fa-cog fa-spin");
//                        $(".rsp").html("<span class='text-danger'>" + rsp + "</span>");
//                    }
//                }, function error(response) {
//                    $(".load-icon").removeClass("fa-check");
//                    $(".load-icon").addClass("fa-cog fa-spin");
//                    $(".rsp").html("<span class='text-danger'>An error occured. Please try again</span>");
//                });
//            };
//            $scope.appLogin = function () {
//                $scope.updateApp();
//                $scope.pwdLoader = true;
//                $http({
//                    url: $scope.admnUrl,
//                    method: 'POST',
//                    data: $httpParamSerializerJQLike({
//                        "username": $scope.username,
//                        "password": $scope.password,
//                        "service": "login"
//                    }),
//                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                }).then(function success(response) {
//                    var rsp = response.data;
//                    if (rsp.trim() === "success") {
//                        $(".load-icon").addClass("fa-check");
//                        $(".load-icon").removeClass("fa-cog fa-spin");
//                        $scope.clearForm();
//                        $(".rsp").html("<span class='text-success'>Account created successfully<span> <br><a href='#!/Administrator' class='text-primary font-weight-bold'>Continue</a> ");
//                    } else {
//                        $(".load-icon").removeClass("fa-check");
//                        $(".load-icon").addClass("fa-cog fa-spin");
//                        $(".rsp").html("<span class='text-danger'>" + rsp + "</span>");
//                    }
//                }, function error(response) {
//                    $(".load-icon").removeClass("fa-check");
//                    $(".load-icon").addClass("fa-cog fa-spin");
//                    $(".rsp").html("<span class='text-danger'>An error occured. Please try again</span>");
//                });
//            };
//            $scope.clearForm = function () {
//                $scope.name = "";
//                $scope.username = "";
//                $scope.password = "";
//            };
//            $scope.hidePwdLoader = function () {
//                $scope.pwdLoader = false;
//            };
//            $scope.hideFilterBox = function () {
//                $scope.showFilterBox = false;
//            };
//            $scope.showFilterBoxView = function () {
//                $scope.showFilterBox = true;
//            };
//            $scope.initInfo();
//        })
//        .controller('fgTransactionsCtrl', function ($scope, Idle, Keepalive, userInfo, profileService, $rootScope, $parse, $http, $httpParamSerializerJQLike, $location, $timeout, $compile, Upload, $q, swangular) {
////    $scope.profile = "";
////    $scope.TMCBankNodeList ="";
//            Idle.watch();
//            if ($('#csrf').length < 1) {
//                $("<input>").attr({
//                    name: "csrf",
//                    id: "csrf",
//                    type: "hidden",
//                    value: $rootScope.csrf
//                }).appendTo("form");
//                console.log('adding');
//            }
//
//            var showAlert = function (title, timer, isReload) {
//                Swal.fire({
//                    title: title,
//                    html: '<br/>You will be logged out automatically in <strong class="swal-timer-count">' + timer / 1000 + '</strong> seconds...',
//                    type: 'warning',
//                    timer: 10000,
//                    allowEscapeKey: true,
//                    showConfirmButton: false
//                }, function () {
//                    Swal.close();
//                    if (false)
//                        location.reload(true);
//                });
//                var e = $(".swal2-content").find(".swal-timer-count");
//                var n = +e.text();
//                setInterval(function () {
//                    n > 1 && e.text(--n);
//                }, 1000);
//            };
//            function closeModals() {
//                if ($scope.warning) {
//                    $scope.warning.close();
//                    $scope.warning = null;
//                }
//
//                if ($scope.timedout) {
//                    $scope.timedout.close();
//                    $scope.timedout = null;
//                }
//            }
//
//            $scope.$on('IdleStart', function () {
//                closeModals();
//                console.log('idle detected');
//                showAlert("Inactivity Detected");
//            });
//            $scope.$on('IdleEnd', function () {
//                closeModals();
//                console.log('welcome back');
//                profileService.reloadSessionProfile();
//                Swal.close();
//            });
//            $scope.$on('IdleTimeout', function () {
//                closeModals();
////                Swal.fire({title: "", text: respText, type: respType});
//                console.log('logging out');
//                $rootScope.logout();
//            });
//            $scope.$on('IdleWarn', function () {
//                closeModals();
////                Swal.fire({title: "", text: respText, type: respType});
//                console.log('warnings');
//            });
//            var dynamicGraph = new DynamicGraph();
//            $scope.menuList = "";
//            $scope.onInit = function () {
//                $scope.profile = profileService.getUserProfile();
//            };
//            $scope.onInit();
////            $scope.profile = $rootScope.profile;
//
//            $(document).ready(function () {
//
//
//                $(document).click(function (event) {
//                    $(event.target).closest(".navbar").length || $(".navbar-collapse.show").length && $(".navbar-collapse.show").collapse("hide");
//                    if (!event.target.closest(".float-btn-group"))
//                        $('.float-btn-group').removeClass("open");
//                });
////                $(".dropdown-item").click(function (event) {
////                    console.log('tes');
////                    $("#navbarDropdown").dropdown("toggle");
////                });
////                $('[data-toggle="tooltip"]').tooltip();
////                $('[data-toggle="popover"]').popover();
//                $('#inpt_search').mouseover(function () {
//                    //gets the current placeholder
//                    this.holder = $(this).attr('placeholder');
//                    $(this).attr('placeholder', 'Enter Reference');
//                });
//                $('#inpt_search').mouseout(function () {
//                    $(this).attr('placeholder', ''); //sets it back to the initial value
//                });
//                $("#inpt_search").on('focus', function () {
//                    $(this).parent('label').addClass('active');
//                });
//                $("#inpt_search").on('blur', function () {
//                    if ($(this).val().length === 0)
//                        $(this).parent('label').removeClass('active');
//                });
//                function generatePDF() {
//                    var documentDefinition = {
//                        content:
//                                [
//                                    {
//                                        text: 'Etranzact Ghana', style: 'mainheader'
//                                    },
//                                    {
//                                        table:
//                                                {
//                                                    headerRows: 1,
//                                                    widths: ['*', '*', '*', '*'],
//                                                    body: [
//                                                        [
//                                                            {text: 'Header 1', style: 'tableHeader'},
//                                                            {text: 'Header 2', style: 'tableHeader'},
//                                                            {text: 'Header 3', style: 'tableHeader'}
//                                                        ],
//                                                        [
//                                                            {text: 'Hello'},
//                                                            {text: 'I'},
//                                                            {text: 'am'}
//                                                        ],
//                                                        [
//                                                            {text: 'a'},
//                                                            {text: 'table'},
//                                                            {text: '.'}
//                                                        ]
//                                                    ]
//                                                }
//                                    }
//                                ],
//                        styles:
//                                {mainheader: {
//                                        fontSize: 20,
//                                        bold: true,
//                                        margin: [0, 10, 0, 10],
//                                        alignment: 'left'
//                                    },
//                                    header:
//                                            {
//                                                fontSize: 18,
//                                                bold: true,
//                                                margin: [0, 10, 0, 10],
//                                                alignment: 'center'
//                                            },
//                                    tableHeader:
//                                            {
//                                                fillColor: '#4CAF50',
//                                                color: 'white'
//                                            }
//                                }
//                    };
//                    pdfMake.createPdf(documentDefinition).download('testdoc.pdf');
//                }
//
//                function KeyPress(e) {
//                    var evtobj = window.event ? event : e;
////            if (evtobj.keyCode === 90 && evtobj.ctrlKey && evtobj.shiftKey) {
//////                $scope.goBack();
////                alert("HEY");
////            }
//                    if (evtobj.keyCode === 76 && evtobj.ctrlKey && evtobj.shiftKey) {
//                        $rootScope.logout();
//                    }
//                    if (evtobj.keyCode === 90 && evtobj.ctrlKey && evtobj.shiftKey) {
////                alert("hello");
//                        $http.get('https://api.freebinchecker.com/bin/464898').then(
//                                function (response) {
//                                    console.log(response.data);
//                                });
//                    }
//
//                }
//
//
//
//                document.onkeydown = KeyPress;
//            });
//            $scope.go = function () {
////                var data = "";
////                let qs = "";
////                let data = "";
////                qs = '?start=' + $('#start-date').val() + '&end=' + $('#end-date').val();
////                data = dynamicGraph.fetchGraphData(MOMO_LIVE_BASE_URL + $path + qs);
////                data = dynamicGraph.fetchGraphData("http://172.16.30.17:8496/amard/graph/MobileMoneyReport?start=2020-02-26%2000:00&end=2020-02-26%2011:56");
//
//                $http.get('http://172.16.30.17:8496/amard/graph/MobileMoneyReport?start=2020-02-26%2000:00&end=2020-02-26%2003:00').then(
//                        function (success) {
//                            var data = success.data;
//                            console.log('DATA 1 : ' + JSON.stringify(data));
//                            if (data) {
//                                for (let graphObj of data) {
//                                    let chartObj = '';
//                                    if (graphObj.chartJsData.chartType !== 'line' && graphObj.chartJsData.chartType !== 'bar') {
//                                        chartObj = dynamicGraph.drawGraphPie('tester', graphObj);
//                                    } else {
//                                        chartObj = dynamicGraph.drawGraph('tester', graphObj);
//                                    }
//                                }
//                            }
//                        });
//            };
//            $scope.updateGraph = function () {
////                if ($path === 'USSDReportByNetwork' && $scope.getDateRange() > 1) {
////                    alert('Date range exceeded. USSD Report Date range cannot exceed 24hrs');
////                } else if ($scope.getDateRange() < 8) {
//                let qs = '?start=' + $('#start-date').val() + '&end=' + $('#end-date').val();
////                let url = MOMO_LIVE_BASE_URL + $path + qs;
////                    dynamicGraph.updateGraph(url);
//                dynamicGraph.updateGraph("http://172.16.30.17:8496/amard/graph/MobileMoneyReport?start=2020-02-26%2000:00&end=2020-02-26%2011:56");
////                } else {
////                    alert('Date range exceeded. Maximum date range is 7 days');
////                }
//            };
//            //DASHBOARD
//            // statistics
//            $scope.dynamicStats = [
//                {
//                    type: 'totalRequestStats'
//                },
//                {
//                    type: 'totalResponseStats'
//                },
//                {
//                    type: 'providerSuccessfulRequestStats'
//                },
//                {
//                    type: 'providerPendingRequestStats'
//                },
//                {
//                    type: 'providerFailedRequestStats'
//                },
//                {
//                    type: 'providerSuccessfulResponseStats'
//                },
//                {
//                    type: 'providerPendingResponseStats'
//                },
//                {
//                    type: 'providerFailedResponseStats'
//                }
////                {
////                    id: '4',
////                    title: 'Sms Rate',
////                    count: '34',
////                    type: 'smstotalcount'
////                },
//                ,
//                {
//                    type: 'providerStats',
//                    provider: 'infobip'
//                }, {
//                    type: 'providerStats',
//                    provider: 'nalo'
//                },
//                {
//                    type: 'providerStats',
//                    provider: 'zentech'
//                }
//            ];
//            $scope.initDashboardData = function () {
////                console.log('hsud');
////                $scope.createMultiGraph();
//                angular.forEach($scope.dynamicStats, function (stats) {
////                    console.log('stts::'+stats.id);
//
//                    $http({
//                        url: smsprocessorUrl,
//                        method: 'POST',
//                        data: $httpParamSerializerJQLike({
//                            "startDate": $scope.startDate,
//                            "endDate": $scope.endDate,
//                            "service": stats.type,
//                            "provider": stats.provider || ''
////                        "offset": $scope.pageNumber - 1,
////                        "limit": $scope.rowsPerPage
//                        }),
//                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                    }).then(function success(response) {
//                        $scope.showLoader = false;
//                        $rootScope.hideLoading();
//                        $('.reload').removeClass('fa-spin text-info');
//                        $scope.smsMsgsResp = response.data;
//                        if ($scope.smsMsgsResp.code === "00") {
//                            if (stats.type === 'totalRequestStats' || stats.type === 'totalResponseStats') {
//                                $scope[stats.type] = JSON.parse($scope.smsMsgsResp.data).totalCount;
//                            } else if (stats.type === 'providerStats') {
//                                var data = JSON.parse($scope.smsMsgsResp.data);
//                                console.log('DATA 2 : ' + JSON.stringify(data));
//                                if (data) {
//                                    for (let graphObj of data) {
//                                        let chartObj = '';
//                                        if (graphObj.chartJsData.chartType !== 'line' && graphObj.chartJsData.chartType !== 'bar') {
//                                            chartObj = dynamicGraph.drawGraphPie(stats.provider, graphObj);
//                                        } else {
//                                            chartObj = dynamicGraph.drawGraph(stats.provider, graphObj);
//                                        }
//                                    }
//                                }
//
//                            } else {
//                                $scope[stats.type] = JSON.parse($scope.smsMsgsResp.data).smsRequestObjs;
//                            }
//                            $(window).resize();
////                            $scope.SmsMsgs = JSON.parse($scope.smsMsgsResp.data);
////                        $scope.getMpaySettlementTotalCount();
////                        $scope.getMpaySettlementTotalAmount();
////                        $scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage);
////                        $scope.end = parseInt($scope.begin + $scope.rowsPerPage);
////                        $scope.filteredMPaySettlementTrxs = $scope.mpaySettlementTrxs.slice($scope.begin, $scope.end);
//                        } else if ($scope.smsMsgsResp.code === "01") {
//                            $scope.SmsMsgs = "";
//                        } else {
//                            $scope.SmsMsgs = "";
//                        }
//                    }, function error(response) {
//                        $scope.showLoader = false;
//                        $rootScope.hideLoading();
//                        $('.reload').removeClass('fa-spin text-info');
//                        //$rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                    });
//                });
////                $scope.go();
//            };
//            $scope.trimText = function (text) {
//
//                if (text) {
//                    if (text.length > 36) {
//                        return (text.substring(0, 35)) + " ...";
//                    }
//                }
//                return text;
//            };
//            $scope.getResponseLog = function (page, paging) {
//                $scope.type = "responseLog";
//                $scope.getLatestMessages(page, paging);
//            };
//            $scope.getRequestLog = function (page, paging) {
//                $scope.type = "requestLog";
//                $scope.getLatestMessages(page, paging);
//            };
//            $scope.getProviderStats = function (page, paging) {
//                $scope.type = "providerStatsLog";
//                console.log('trest');
//                $scope.getLatestMessages(page, paging);
//            };
//            //GET LATEST MESSAGES
//            $scope.getLatestMessages = function (page, paging) {
//                $scope.hideFilterBox();
//                $rootScope.showLoading();
//                $scope.pageNumber = page;
//                $('.reload').addClass('fa-spin text-info');
//                console.log('Page :' + $scope.pageNumber + ' fgRecs :' + $scope.fgRecordsLastPage);
//                if (paging) {
//                    //url = './GipTransactions?action=paging';
//                }
//                if ($scope.pageNumber < 1)
//                    $scope.pageNumber = 1;
//                if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                    $scope.fgRecordsLastPage = $scope.pageNumber;
//                console.log('Page :' + $scope.pageNumber + ' fgRecs :' + $scope.fgRecordsLastPage);
//                $http({
//                    url: smsprocessorUrl,
//                    method: 'POST',
//                    data: $httpParamSerializerJQLike({
//                        "startDate": $scope.startDate,
//                        "endDate": $scope.endDate,
//                        "provider": $scope.provider,
//                        "service": $scope.type,
//                        "status": $scope.status,
//                        "searchKey": $scope.searchKey,
//                        "searchType": $scope.searchType,
//                        "senderId": $scope.sender,
//                        "offset": ($scope.pageNumber - 1) * $scope.rowsPerPage,
//                        "limit": $scope.rowsPerPage
//                    }),
//                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                }).then(function success(response) {
//                    $scope.showLoader = false;
//                    $rootScope.hideLoading();
//                    $('.reload').removeClass('fa-spin text-info');
//                    $scope.smsMsgsResp = response.data;
//                    if ($scope.smsMsgsResp.code === "00") {
//                        if ($scope.type === 'requestLog') {
//                            $scope.SmsMsgs = JSON.parse($scope.smsMsgsResp.data).smsRequestObjs;
//                        } else if ($scope.type === 'responseLog') {
//                            $scope.SmsMsgs = JSON.parse($scope.smsMsgsResp.data).smsResponseObjs;
//                        } else if ($scope.type === 'providerStatsLog') {
//                            $scope.SmsMsgs = JSON.parse($scope.smsMsgsResp.data).smsProvStatObjs;
//                        } else {
//
//                        }
//                        console.log('result: ' + $scope.SmsMsgs.length);
////                        $scope.getMpaySettlementTotalCount();
////                        $scope.getMpaySettlementTotalAmount();
////                        $scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage);
////                        $scope.end = parseInt($scope.begin + $scope.rowsPerPage);
////                        $scope.filteredMPaySettlementTrxs = $scope.mpaySettlementTrxs.slice($scope.begin, $scope.end);
//
//                        $scope.getStatistics('SmsMsgs');
//                    } else if ($scope.smsMsgsResp.code === "01") {
//                        $scope.SmsMsgs = "";
//                    } else {
//                        $scope.SmsMsgs = "";
//                    }
//                }, function error(response) {
//                    $scope.showLoader = false;
//                    $rootScope.hideLoading();
//                    $('.reload').removeClass('fa-spin text-info');
//                    //$rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                });
//                $rootScope.updateRecordsTable();
//            };
//            $scope.changeUserStatus = function (index, action) {
//                $scope.hideFilterBox();
//                $scope.username = $scope.searchUserProfile[0].username;
//                $scope.action = action;
//                $scope.userId = $scope.searchUserProfile[0].user_id;
//                Swal.fire({
//                    title: "Are you sure ?",
////                    text: "This will " + action + " " + $scope.username,
//                    html: "This will <b>" + action + " " + $scope.username + "</b>",
//                    type: 'warning',
//                    showCancelButton: true,
//                    confirmButtonColor: '#3085d6',
//                    cancelButtonColor: '#d33',
//                    confirmButtonText: 'Yes, ' + action + ' user!',
//                    showLoaderOnConfirm: true,
//                    preConfirm: function preConfirm(login) {
//                        console.log('action: ' + action);
//                        return $http({
//                            url: $scope.adminProxy,
//                            method: 'POST',
//                            data: $httpParamSerializerJQLike({
//                                "userId": $scope.userId,
//                                "status": $scope.action,
//                                "service": "toggleStatus"
//                            }),
//                            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                        }).then(function success(response) {
//                            console.log(response.data);
//                            return response;
//                        }).catch(function (error) {
//                            Swal.showValidationMessage("Request failed ");
//                        });
////                        return fetch("//api.github.com/users/".concat(login)).then(function (response) {
////                            if (!response.ok) {
////                                throw new Error(response.statusText);
////                            }
////
////                            return response.json();
////                        }).catch(function (error) {
////                            Swal.showValidationMessage("Request failed ");
////                        });
//                    },
//                    allowOutsideClick: function allowOutsideClick() {
//                        return !Swal.isLoading();
//                    }
//                }).then(function (result) {
//                    console.log('USer: ' + JSON.stringify(result));
//                    //do asyc
//                    if (result.value) {
//                        var resp = result.value.data.data;
//                        console.log('before: ' + JSON.stringify($scope.searchUserProfile[0]));
//                        console.log('action: ' + $scope.action);
//                        $scope.searchUserProfile[0].status_id = (resp === 'SUCCESS' ? ($scope.action === 'activate' ? 1 : 2) : $scope.searchUserProfile[0].status_id);
//                        var respText = (resp === 'SUCCESS' ? $scope.searchUserProfile[0].username + ' has been ' + action + 'd.' : (resp === 'FAILED' ? 'Failed to ' + action + ' ' + $scope.searchUserProfile[0].username + '.' : resp));
//                        var respType = (resp === 'SUCCESS' ? 'success' : 'error');
//                        var respTitle = (resp === 'SUCCESS' ? action + 'd!' : 'Failed');
//                        console.log('after: ' + JSON.stringify($scope.searchUserProfile[0]));
//                        if (resp === 'SUCCESS') {
//                            $('#user' + index).html(($scope.action === 'activate' ? 'active' : 'inactive'));
//                        }
//                        Swal.fire({title: respTitle, text: respText, type: respType, timer: 1500});
//                    }
//                });
//            };
//            $scope.resetUserPassword = function (index) {
//
//            };
////            $scope.initMenu();
//            $scope.usingFlash = FileAPI && FileAPI.upload !== null;
//            //Upload.setDefaults({ngfKeep: true, ngfPattern:'image/*'});
//            $scope.changeAngularVersion = function () {
//                window.location.hash = $scope.angularVersion;
//                window.location.reload(true);
//            };
//            $scope.angularVersion = window.location.hash.length > 1 ? (window.location.hash.indexOf('/') === 1 ?
//                    window.location.hash.substring(2) : window.location.hash.substring(1)) : '1.2.24';
//            $scope.invalidFiles = [];
//            // make invalidFiles array for not multiple to be able to be used in ng-repeat in the ui
//            $scope.$watch('invalidFiles', function (invalidFiles) {
//                if (invalidFiles !== null && !angular.isArray(invalidFiles)) {
//                    $timeout(function () {
//                        $scope.invalidFiles = [invalidFiles];
//                    });
//                }
//            });
//            function uploadUsing$http(file, type, url) {
//                $scope.UploadOvaData(file, type, url);
//            }
//
//            $scope.uploadPic = function (file) {
//                $scope.formUpload = true;
//                $scope.validateExcelFile(file, 'upload', 1);
//            };
//            $scope.checkKycFile = function (file) {
//                $scope.validateKYCFile(file, 'upload', 1);
//            };
//            $scope.verifyOvaData = function (file, type) {
////        alert('verifyova');
//                $scope.hideFilterBox();
//                $scope.validateExcelFile(file, 'verify', type);
//            };
//            $scope.recordsExists = function () {
//                $scope.hideFilterBox();
//                $scope.showLoader = true;
//                $rootScope.showLoading();
//                if ($scope.pageNumber < 1)
//                    $scope.pageNumber = 1;
//                if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                    $scope.pageNumber = $scope.fgRecordsLastPage;
//                $scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage);
//                $scope.end = parseInt($scope.begin + $scope.rowsPerPage);
//                $scope.filteredOvaRecords = ($scope.uploadResult.ovaDataRecordExist).slice($scope.begin, $scope.end);
//                $scope.getRecordsDetailsTotalCount();
//                console.log('DATA: ' + $scope.filteredOvaRecords);
//                $scope.showLoader = false;
//                $rootScope.hideLoading();
//            };
//            $scope.recordsInserted = function () {
////        if ($scope.uploadResult.ovaDataRecordInserted.length > 0) {
//                $scope.hideFilterBox();
//                $scope.showLoader = true;
//                $rootScope.showLoading();
//                if ($scope.pageNumber < 1)
//                    $scope.pageNumber = 1;
//                if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                    $scope.pageNumber = $scope.fgRecordsLastPage;
//                $scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage);
//                $scope.end = parseInt($scope.begin + $scope.rowsPerPage);
//                $scope.filteredOvaRecords = ($scope.uploadResult.ovaDataRecordInserted).slice($scope.begin, $scope.end);
//                $scope.getRecordsDetailsTotalCount();
//                $scope.showLoader = false;
//                $rootScope.hideLoading();
////        }
//            };
//            $scope.UploadOvaData = function (file, type, urlType) {
////        console.log('Got');
//                var formdata = new FormData();
//                $scope.uploadResult = "";
//                $scope.showProgressBar = false;
//                $scope.showProgressBarError = false;
//                formdata.append("file", file);
//                $http.get($scope.ovaUploadProxy).then(
//                        function (response) {
//                            $scope.ovaUploadSettings = response.data;
//                            if ($scope.ovaUploadSettings.response === 'success') {
//                                if (file.size <= $scope.ovaUploadSettings.ovaUploadMaxSize * 1024000) {
//                                    if (type === 'verify') {
////                                alert('VERIFY');
//                                        if ($scope.pageNumber < 1)
//                                            $scope.pageNumber = 1;
//                                        if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                                            $scope.pageNumber = $scope.fgRecordsLastPage;
////                                console.log('DATA: ' + $scope.pageNumber + ' : ' + $scope.fgRecordsLastPage);
//                                        $scope.showLoader = true;
//                                        $rootScope.showLoading();
//                                        file.upload = Upload.http({
//                                            url: $scope.ovaUploadSettings['ovaVerifyUrl' + urlType],
//                                            method: 'POST',
//                                            headers: {
//                                                'Content-Type': undefined
//                                            },
//                                            data: formdata
//                                        });
//                                        file.upload.then(function (response) {
//                                            $scope.showLoader = false;
//                                            $rootScope.hideLoading();
//                                            if (urlType === 1) {
//                                                $('#verify-btn').prop("disabled", false);
//                                                $('#verify-btn').html("VERIFY ER");
//                                            } else {
//                                                $('#verify-btn2').prop("disabled", false);
//                                                $('#verify-btn2').html("VERIFY NER");
//                                            }
//                                            file.result = response.data;
//                                            $scope.verifyResult = response.data;
//                                            $scope.showLoader = false;
//                                            $rootScope.hideLoading();
//                                            $scope.getOvaTotalCount();
//                                            $scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage);
//                                            $scope.end = parseInt($scope.begin + $scope.rowsPerPage);
//                                            $scope.filteredOvaRecords = $scope.verifyResult.slice($scope.begin, $scope.end);
//                                            console.log('DATA: ' + $scope.filteredOvaRecords);
//                                        }, function (response) {
//                                            if (urlType === 1) {
//                                                $('#verify-btn').prop("disabled", false);
//                                                $('#verify-btn').html("VERIFY ER");
//                                            } else {
//                                                $('#verify-btn2').prop("disabled", false);
//                                                $('#verify-btn2').html("VERIFY NER");
//                                            }
//                                            $scope.showLoader = false;
//                                            $rootScope.hideLoading();
//                                            alert('An Error Occured: ' + response.status);
//                                        });
//                                        file.upload.progress(function (evt) {
//                                            file.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
//                                        });
//                                    } else {
////                                alert('Uploading');
//                                        $('#upload-btn').html("<i class='fa fa-spinner fa-spin btn-loader'></i> Uploading...");
////                                alert('Sending: ' + $scope.ovaUploadSettings['ovaUploadUrl' + urlType]);
//                                        file.upload = Upload.http({
//                                            url: $scope.ovaUploadSettings['ovaUploadUrl' + urlType],
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
//                                    }
//                                } else {
//                                    alert("File is too large. Limit is " + $scope.ovaUploadSettings.ovaUploadMaxSize + "MB");
//                                }
//                            } else {
//                                alert('Could not Load OvaUpload Settings');
//                            }
//                            return true;
//                        }
//                );
//            };
//            $scope.UploadKycData = function (file, type, urlType) {
////        console.log('Got');
//                var formdata = new FormData();
//                $scope.uploadResult = "";
//                $scope.showProgressBar = false;
//                $scope.showProgressBarError = false;
//                formdata.append("file", file);
//                $http.get($scope.kycUploadProxy).then(
//                        function (response) {
//                            $scope.kycUploadSettings = response.data;
//                            if ($scope.kycUploadSettings.response === 'success') {
//                                if (file.size <= $scope.kycUploadSettings.UploadMaxSize * 1024000) {
//
////                                alert('Uploading');
//                                    $('#upload-btn').html("<i class='fa fa-spinner fa-spin btn-loader'></i> Uploading...");
////                                alert('Sending: ' + $scope.ovaUploadSettings['ovaUploadUrl' + urlType]);
//                                    file.upload = Upload.http({
//                                        url: $scope.kycUploadSettings['ovaUploadUrl' + urlType],
//                                        method: 'POST',
//                                        headers: {
//                                            'Content-Type': undefined
//                                        },
//                                        data: formdata
//                                    });
//                                    file.upload.then(function (response) {
//                                        file.result = response.data;
//                                        console.log('RESPONSE: ' + JSON.stringify(response.data));
//                                        $scope.uploadResult = response.data;
//                                        console.log("RESP: " + $scope.uploadResult.status);
//                                        $scope.showLoader = false;
//                                        $rootScope.hideLoading();
//                                        $('#upload-btn').prop("disabled", false);
//                                        $('#upload-btn').html("Upload");
//                                        if ($scope.uploadResult.status) {
//                                            $scope.showProgressBar = true;
//                                        } else {
//                                            $scope.showProgressBarError = true;
//                                        }
//
//                                    }, function (response) {
//                                        $('#upload-btn').prop("disabled", false);
//                                        $('#upload-btn').html("Upload");
//                                        alert('An Error Occured: ' + response.status);
//                                    });
//                                    file.upload.progress(function (evt) {
//                                        file.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
//                                    });
//                                } else {
//                                    alert("File is too large. Limit is " + $scope.kycUploadSettings.UploadMaxSize + "MB");
//                                }
//                            } else {
//                                alert('Could not Load KycUpload Settings');
//                            }
//                            return true;
//                        }
//                );
//            };
//            $scope.validateExcelFile = function (file, type, recordType) {
//                if (type === 'verify') {
//                    if (recordType === 1) {
//                        $('#verify-btn').prop("disabled", true);
//                        $('#verify-btn').html("<i class='fa fa-spinner fa-spin btn-loader'></i> Validating");
//                    } else {
//                        $('#verify-btn2').prop("disabled", true);
//                        $('#verify-btn2').html("<i class='fa fa-spinner fa-spin btn-loader'></i> Validating");
//                    }
//                } else {
//                    $('#upload-btn').prop("disabled", true);
//                    $('#upload-btn').html("<i class='fa fa-spinner fa-spin btn-loader'></i> Validating");
//                }
//
//                var xlsxflag = false; /*Flag for checking whether excel is .xls format or .xlsx format*/
//                if ($("#excelfile").val().toLowerCase().indexOf(".xlsx") > 0) {
////                console.log('2');
//                    xlsxflag = true;
//                }
//                /*Checks whether the browser supports HTML5*/
//                if (typeof (FileReader) !== "undefined") {
//
//                    var reader = new FileReader();
//                    reader.onload = function (e) {
//                        var data = e.target.result;
//                        /*Converts the excel data in to object*/
//                        if (xlsxflag) {
//                            var binary = "";
//                            var bytes = new Uint8Array(data);
//                            var length = bytes.byteLength;
//                            for (var i = 0; i < length; i++) {
//                                binary += String.fromCharCode(bytes[i]);
//                            }
//                            var workbook = XLSX.read(binary, {type: 'binary'});
//                        }
//                        /*Gets all the sheetnames of excel in to a variable*/
//                        var sheet_name_list = workbook.SheetNames;
//                        var cnt = 0; /*This is used for restricting the script to consider only first sheet of excel*/
//                        sheet_name_list.forEach(function (y) { /*Iterate through all sheets*/
//                            /*Convert the cell value to Json*/
//                            if (xlsxflag) {
//                                var exceljson = XLSX.utils.sheet_to_json(workbook.Sheets[y]);
////                        var jsonObj = JSON.stringify(exceljson);
////
////                        console.log(jsonObj);
//
//                            }
//
//                            var columnSet = [];
//                            if (exceljson.length > 0 && cnt === 0) {
////                            console.log('Data: t');
//                                var rowHash = exceljson[1];
//                                for (var key in rowHash) {
//
//                                    if (rowHash.hasOwnProperty(key)) {
//                                        if ($.inArray(key, columnSet) === -1) {/*Adding each unique column names to a variable array*/
//                                            columnSet.push(key);
//                                        }
//                                    }
//                                }
//                                console.log('2: ' + columnSet);
//                                var header = 'Id,External Transaction Id,Date,Status,Type';
//                                if ($.trim(columnSet).includes(header)) {
//
//                                    console.log('Valid excel file');
//                                    if (file !== null) {
//                                        $scope.uploadFile(file, type, recordType);
//                                    } else {
//                                        alert('could not check File');
//                                    }
//                                } else {
//
//                                    alert("invalid excel file");
//                                }
//                                cnt++;
//                            }
//                        });
//                    };
//                    if (xlsxflag) {/*If excel file is .xlsx extension than creates a Array Buffer from excel*/
//                        reader.readAsArrayBuffer($("#excelfile")[1].files[1]);
//                    }
//
//                } else {
//                    $('#upload-btn').prop("disabled", false);
//                    $('#upload-btn').html("Upload");
//                    alert("Sorry! Your browser does not support HTML5!");
//                }
//            };
//            $scope.validateKYCFile = function (file, type, recordType) {
//                if (type === 'verify') {
//                    if (recordType === 1) {
//                        $('#verify-btn').prop("disabled", true);
//                        $('#verify-btn').html("<i class='fa fa-spinner fa-spin btn-loader'></i> Validating");
//                    } else {
//                        $('#verify-btn2').prop("disabled", true);
//                        $('#verify-btn2').html("<i class='fa fa-spinner fa-spin btn-loader'></i> Validating");
//                    }
//                } else {
//                    $('#upload-btn').prop("disabled", true);
//                    $('#upload-btn').html("<i class='fa fa-spinner fa-spin btn-loader'></i> Validating");
//                }
//                var xlsxflag = false; /*Flag for checking whether excel is .xls format or .xlsx format*/
//                if ($("#excelfile").val().toLowerCase().indexOf(".xlsx") !== 1) {
////                console.log('2');
//                    xlsxflag = true;
//                }
//                /*Checks whether the browser supports HTML5*/
//                if (typeof (FileReader) !== "undefined") {
//
//                    var reader = new FileReader();
//                    reader.onload = function (e) {
//                        var data = e.target.result;
//                        /*Converts the excel data in to object*/
//                        if (xlsxflag) {
//                            var binary = "";
//                            var bytes = new Uint8Array(data);
//                            var length = bytes.byteLength;
//                            for (var i = 0; i < length; i++) {
//                                binary += String.fromCharCode(bytes[i]);
//                            }
//                            var workbook = XLSX.read(binary, {type: 'binary'});
//                        }
//                        /*Gets all the sheetnames of excel in to a variable*/
//                        var sheet_name_list = workbook.SheetNames;
//                        var cnt = 0; /*This is used for restricting the script to consider only first sheet of excel*/
//                        sheet_name_list.forEach(function (y) { /*Iterate through all sheets*/
//                            /*Convert the cell value to Json*/
//                            if (xlsxflag) {
//                                var exceljson = XLSX.utils.sheet_to_json(workbook.Sheets[y]);
//                                var jsonObj = JSON.stringify(exceljson);
//                                console.log(jsonObj);
//                            }
//
//                            var columnSet = [];
//                            if (exceljson.length > 0 && cnt === 0) {
////                            console.log('Data: t');
//                                var rowHash = exceljson[1];
//                                for (var key in rowHash) {
//
//                                    if (rowHash.hasOwnProperty(key)) {
//                                        if ($.inArray(key, columnSet) === -1) {/*Adding each unique column names to a variable array*/
//                                            columnSet.push(key);
//                                        }
//                                    }
//                                }
//                                console.log('2: ' + columnSet);
//                                var header = 'CardHolderName,CardNumber,IdNumber,IdType,MobileNumber';
//                                $scope.validKycFile = false;
//                                header.split(/\s*,\s*/).forEach(function (myString) {
//                                    $scope.validKycFile = exceljson[1].hasOwnProperty(myString);
//                                });
//                                if ($scope.validKycFile) {
//
//                                    console.log('Valid excel file');
//                                    if (file !== null) {
//                                        $scope.uploadKycFile(file, type, recordType);
//                                    } else {
//                                        alert('could not check File');
//                                    }
//                                } else {
//
//                                    alert("invalid excel file");
//                                }
//                                cnt++;
//                            }
//                        });
//                    };
//                    if (xlsxflag) {/*If excel file is .xlsx extension than creates a Array Buffer from excel*/
//                        reader.readAsArrayBuffer($("#excelfile")[1].files[1]);
//                    }
//
//                } else {
//                    $('#upload-btn').prop("disabled", false);
//                    $('#upload-btn').html("Upload");
//                    alert("Sorry! Your browser does not support HTML5!");
//                }
////        } else {
////            $('#upload-btn').prop("disabled", false);
////            $('#upload-btn').html("Upload");
////            alert("Please upload a valid Excel file!!!");
////        }
//            };
//            $scope.toggleKycUploadBox = function () {
//                $scope.showKycUploadBox = !$scope.showKycUploadBox;
//            };
//            $scope.getOvatrxs = function (page, paging) {
//
//                $scope.showLoader = true;
//                $rootScope.showLoading();
//                $scope.showFilterBox = false;
//                $scope.pageNumber = page;
//                $('.reload').addClass('fa-spin text-info');
//                if ($scope.pageNumber < 1)
//                    $scope.pageNumber = 1;
//                if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                    $scope.pageNumber = $scope.fgRecordsLastPage;
//                console.log('DATA: ' + $scope.pageNumber + ' : ' + $scope.fgRecordsLastPage);
//                if (paging) {
//
////                console.log('DATA: ' + page + ' : ' + $scope.fgRecordsLastPage);
//                    $scope.begin = (page - 1) * parseInt($scope.rowsPerPage);
//                    $scope.end = parseInt($scope.begin) + parseInt($scope.rowsPerPage);
////                console.log('begin: ' + $scope.begin + ' : ' + $scope.end);
//                    $scope.filteredOvaRecords = $scope.verifyResult.slice($scope.begin, $scope.end);
//                    $scope.getOvaTotalCount();
//                    $scope.showLoader = false;
//                    $rootScope.hideLoading();
//                    $('.reload').removeClass('fa-spin text-info');
//                }
//            };
//            $scope.getOvaTotalCount = function () {
//                $scope.fgRecordsTotalCount = $scope.verifyResult.length;
//                $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//                if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//                    $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//                }
//            };
//            $scope.getRecordsDetailsTotalCount = function () {
//                $scope.fgRecordsTotalCount = $scope.uploadResult.ovaDataRecordInserted.length > 0 ? $scope.uploadResult.ovaDataRecordInserted.length : $scope.uploadResult.ovaDataRecordExist.length;
//                $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//                if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//                    $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//                }
//            };
//            function _objectWithoutProperties(source, excluded) {
//                if (source === null)
//                    return {};
//                var target = _objectWithoutPropertiesLoose(source, excluded);
//                var key, i;
//                if (Object.getOwnPropertySymbols) {
//                    var sourceSymbolKeys = Object.getOwnPropertySymbols(source);
//                    for (i = 0; i < sourceSymbolKeys.length; i++) {
//                        key = sourceSymbolKeys[i];
//                        if (excluded.indexOf(key) >= 0)
//                            continue;
//                        if (!Object.prototype.propertyIsEnumerable.call(source, key))
//                            continue;
//                        target[key] = source[key];
//                    }
//                }
//                return target;
//            }
//
//            function _objectWithoutPropertiesLoose(source, excluded) {
//                if (source === null)
//                    return {};
//                var target = {};
//                var sourceKeys = Object.keys(source);
//                var key, i;
//                for (i = 0; i < sourceKeys.length; i++) {
//                    key = sourceKeys[i];
//                    if (excluded.indexOf(key) >= 0)
//                        continue;
//                    target[key] = source[key];
//                }
//                return target;
//            }
//
//
//            $scope.getStatistics = function (data, resp_field, resp_msg, amount_field) {
//                $scope.fgRecordsTotalCount = $scope[data].length;
//                $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//                if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//                    $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//                }
//                if (resp_field && resp_msg && amount_field)
//                    $scope.fgRecordsTotalAmount = $scope.sumAmount($scope[data], resp_field, resp_msg, amount_field);
//            };
//            $scope.exportToExcel = function (dataToExport, type) {
//                $scope.getCurrentLocation();
////        $scope.data = dataToExport.map(({$$hashKey, ...item }) => item);
//                if (!type) {
//                    $scope.data = dataToExport.map(function (_ref) {
//                        var $$hashKey = _ref.$$hashKey,
//                                item = _objectWithoutProperties(_ref, ["$$hashKey"]);
//                        return item;
//                    });
//                    /* generate a worksheet */
//                    var ws = XLSX.utils.json_to_sheet($scope.data);
//                    /* add to workbook */
//                    var wb = XLSX.utils.book_new();
//                    XLSX.utils.book_append_sheet(wb, ws, $scope.currentPageLocation);
//                    /* write workbook and force a download */
//                    XLSX.writeFile(wb, $scope.currentPageLocation + ".xlsx");
//                } else {
//                    location.href = dataToExport;
//                }
//            };
//            $scope.sumAmount = function (data, filter, filterValue, amount) {
//                var total = 0.00;
//                $scope.tempData = data;
//                if ($scope.tempData.length > 0) {
//                    var amountType = amount;
//                    var filterType = filter;
////        console.log('sim: ' + $scope.tempData[1][amountType]);
////        console.log('Type: ' + typeof $scope.tempData[1].[amount]);
//                    for (var i = 0; i < $scope.tempData.length; i++) {  //loop through the array
//                        if (filterType === '') {
//                            total += $scope.tempData[i][amountType]; //Do the math!
//                        } else {
//                            if ($scope.tempData[i][filterType] === filterValue)
//                                total += $scope.tempData[i][amountType];
//                        }
//                    }
//                }
//                return total.toFixed(2);
//            };
//            $scope.resultsPageCount = function (data) {
//                return Math.ceil(data.length / $scope.rowsPerPage);
//            };
//            $scope.uploadFile = function (file, type, url) {
//                console.log('got here' + type + ':' + url);
//                $scope.errorMsg = null;
//                uploadUsing$http(file, type, url);
//            };
//            $scope.uploadKycFile = function (file, type, url) {
//                console.log('got here' + type + ':' + url);
//                $scope.errorMsg = null;
//                $scope.UploadKycData(file, type, url);
//            };
//            $scope.chunkSize = 100000;
//            $scope.confirm = function () {
//                return confirm('Are you sure? Your local changes will be lost.');
//            };
//            $scope.rowsPerPage = 100;
//            $scope.currentPage = 0;
//            $scope.items = [];
//            $scope.range = function () {
//                var rangeSize = 5;
//                var ret = [];
//                var start;
//                start = $scope.currentPage;
//                if (start > $scope.pageCount() - rangeSize) {
//                    start = $scope.pageCount() - rangeSize + 1;
//                }
//
//                for (var i = start; i < start + rangeSize; i++) {
//                    ret.push(i);
//                }
//                return ret;
//            };
//            $scope.prevPage = function () {
//                if ($scope.currentPage > 0) {
//                    $scope.currentPage--;
//                }
//            };
//            $scope.prevPageDisabled = function () {
//                return $scope.currentPage === 0 ? "disabled" : "";
//            };
//            $scope.pageCount = function (trans) {
//                return Math.ceil($scope.fgTrxs.length / $scope.rowsPerPage) - 1;
//            };
//            $scope.nextPage = function () {
//                if ($scope.currentPage < $scope.pageCount()) {
//                    $scope.currentPage++;
//                }
//            };
//            $scope.nextPageDisabled = function () {
//                return $scope.currentPage === $scope.pageCount() ? "disabled" : "";
//            };
//            $scope.setPage = function (n) {
//                $scope.currentPage = n;
//            };
//            $('[data-toggle="tooltip"]').tooltip();
////            $scope.$on("$routeChangeSuccess", function (event, next, current) {
////                $http.get('./Cleaner').then(function (response) {});
////            });
//            $scope.initDates = function () {
//                var startOfDay = moment().format('YYYY-MM-DD 00:00');
//                var currentDateTime = moment().format('YYYY-MM-DD 23:59');
//                $('#start-date').datetimepicker({
//                    format: 'Y-m-d H:i',
//                    value: startOfDay
//                });
//                $scope.startDate = startOfDay;
//                $scope.endDate = currentDateTime;
//                $('#start-date-btn').on('click', function () {
//                    $('#start-date').datetimepicker('show');
//                });
//                $('#end-date').datetimepicker({
//                    format: 'Y-m-d H:i',
//                    value: moment().format('Y-m-d H:i')
//                });
//                $('#end-date-btn').on('click', function () {
//                    $('#end-date').datetimepicker('show');
//                });
//            };
////            profileService.updateUserProfile();
//            $scope.initDates();
//            $scope.merchant = "ALL";
//            $scope.product = "ALL";
//            $scope.status = "ALL";
////            $scope.destination2 = $scope.BankNodes;
//            $scope.statusProxy = "./etz/proxy/2018/statusProxy";
//            $scope.statusupdateProxy = "./etz/proxy/2018/statusupdateProxy";
//            $scope.crdDetProxy = "./etz/proxy/2018/cardDetailsView";
//            $scope.crdUpdateProxy = "./etz/proxy/2018/cardUpdate";
//            $scope.adminProxy = "../XPortalProxy/api/Admin";
//            $scope.vasUrl = "./vasgate";
//            $scope.showProgressBar = false;
//            $scope.showProgressBarError = false;
//            $scope.showFilterBox = true;
//            $scope.showCardUpdateBox = false;
//            var addRoleClicked = false;
//            $scope.rowsPerPage = "1000";
//            $scope.pageNumber = 1;
//            $scope.fgRecordsLastPage = 1;
//            $scope.fgRecordsTotalCount = 0;
//            $scope.fgRecordsTotalAmount = 0;
//            $scope.fgRecordsTotalFailedAmount = 0;
//            $scope.fgRecordsTotalFee = 0;
//            $scope.balance = "n/a";
//            $scope.lastBalanceDate = "n/a";
//            $scope.reference = "";
//            $scope.pageNumber = 1;
//            $scope.fgRecordsLastPage = "";
//            $scope.fgRecordsTotalCount = 0;
//            $scope.pageReady = false;
//            $scope.showUserTypes = false;
//            $scope.usernameAvailable = false;
//            $scope.autoRefreshState = false;
//            $scope.refreshState = false;
//            $scope.values = [];
//            $scope.typeIdList = [];
//            $scope.provider = "ALL";
////            $scope.sender = "ALL";
////            $scope.userProfiles = "";
//            $scope.initInfo = function () {
//                $http.get('../XPortalProxy/api/MerchantsInfo?action=fundgate').then(
//                        function (response) {
//                            $scope.merchantList = response.data;
//                        }
//                );
//                $http.get('../XPortalProxy/api/MerchantsInfo?action=bankNodes').then(
//                        function (response) {
//                            $scope.TMCBankNodeList = response.data;
//                            $scope.BankNodesSet();
//                        }
//                );
//                $http.get('../XPortalProxy/api/MerchantsInfo?action=BMerchantList').then(
//                        function (response) {
//                            $scope.BMerchantList = response.data;
//                            $scope.BankNodesSet();
//                        }
//                );
//            };
//            $scope.getCurrentLocation = function () {
//                $scope.currentPageLocation = $location.path().substring(1);
//            };
//            $scope.getFGBalance = function () {
//                $http.get('../XPortalProxy/api/MerchantsInfo?action=balance&terminalId=' + $scope.merchant).then(
//                        function (response) {
//                            $scope.balance = response.data;
//                            $scope.lastBalanceDate = moment().format('DD-MM-YY H:mm');
//                        }
//                );
//            };
//            $scope.getSurfBalance = function () {
//                $http.get('./surfBalance').then(
//                        function (response) {
//                            $scope.surfBalance = response.data;
//                            $scope.surflastBalanceDate = moment().format('DD-MM-YY H:mm');
//                        }
//                );
//            };
//            $scope.updateApp = function () {
//                profileService.updateUserProfile();
//                $scope.getFGBalance();
////        $scope.getGCBTotal();
//
//            };
//            $scope.autoRefreshReport = function (action) {
//                $('.refresh-btn').toggleClass('fa-sync fa-recycle');
//                $('.refresh-btn').toggleClass('fa-spin');
//                if (!$scope.autoRefreshState) {
//                    $scope.autoRefreshState = true;
////                    console.log('autorefresh start');
//                    $scope.refreshReportInterval = setInterval(function () {
//                        $scope.startDate = (moment().subtract(5, 'minutes')).format('YYYY-MM-DD hh:mm');
//                        $scope.endDate = moment().format('YYYY-MM-DD hh:mm');
//                        if (typeof $scope[action] === "function") {
//                            $scope[action](1);
//                        }
//                    }, autoRefreshRate);
//                } else {
//                    clearInterval($scope.refreshReportInterval);
////                    console.log('cancel autofresh');
//                    $scope.autoRefreshState = false;
//                }
//            };
//            $scope.refreshReport = function (action) {
//                if (!$scope.refreshState) {
//                    $scope.refreshState = true;
//                    $scope.endDate = moment().format('YYYY-MM-DD hh:mm');
//                    if (typeof $scope[action] === "function") {
//                        $scope[action](1);
//                    }
//                    setTimeout(function () {
//                        $scope.$apply($scope.refreshState = false);
//                    }, 2000);
//                }
//            };
//            $scope.checkHotlist = function (status, card) {
////        console.log("status: " + status + " " + "cardnum: " + card);
////        if ($scope.formType === "HOTLIST") {
//                if (status === '0') {
//                    var element = document.getElementById(card);
//                    element.classList.add("btn-success");
//                    return "HOTLIST";
//                } else {
//                    var element = document.getElementById(card);
//                    element.classList.add("btn-warning");
//                    return "DEHOTLIST";
//                }
////        }
////        return "UPDATE";
//
//            };
//            $scope.hideSpecificOption = function () {
//                $scope.specificReason = false;
//                $scope.reason = '';
//                $scope.hideSpecificEnhancement();
//            };
//            $scope.showSpecificOption = function () {
//                $scope.hideSpecificEnhancement();
//                $scope.specificReason = true;
//                $scope.reason = '';
//            };
//            $scope.hideSpecificEnhancement = function () {
//                $scope.specificEnhancement = false;
//            };
//            $scope.showSpecificEnhancement = function () {
//                $scope.showSpecificOption();
//                $scope.specificEnhancement = true;
//                $scope.specificReason = false;
//                $scope.reason = '';
//            };
//            $scope.changeCMSOption = function () {
//                $scope.specificOption = $('#specificOption').val();
//            };
//            $scope.showOtherReason = function () {
//
//            };
//            $scope.checkHotlist1 = function (status, card) {
//                console.log("status: " + status + " " + "cardnum: " + card);
//                if ($scope.formType === "HOTLIST") {
//                    if (status === '0') {
//                        var element = document.getElementById(card);
//                        element.classList.add("btn-success");
//                        return "HOTLIST";
//                    } else {
//                        var element = document.getElementById(card);
//                        element.classList.add("btn-warning");
//                        return "DEHOTLIST";
//                    }
//                }
//                return "UPDATE";
//            };
//            $scope.getJustPayTrxns = function (page, paging) {
//                profileService.updateUserProfile();
////        console.log('chech');
////        alert($('#scbType').val());
//                if ($('#start-date').val() && $('#end-date').val()) {
//                    $scope.showLoader = true;
//                    $rootScope.showLoading();
//                    $scope.showFilterBox = false;
//                    $scope.pageNumber = page;
//                    $('.reload').addClass('fa-spin text-info');
//                    var url = $scope.jpProxy;
//                    if ($scope.pageNumber < 1)
//                        $scope.pageNumber = 1;
//                    if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                        $scope.pageNumber = $scope.fgRecordsLastPage;
//                    if (paging) {
//                        $scope.begin = (page - 1) * parseInt($scope.rowsPerPage);
//                        $scope.end = parseInt($scope.begin) + parseInt($scope.rowsPerPage);
//                        $scope.filteredJPTrxs = $scope.JPTrxs.slice($scope.begin, $scope.end);
//                        $scope.showLoader = false;
//                        $rootScope.hideLoading();
//                        $('.reload').removeClass('fa-spin text-info');
//                    }
//                    $http({
//                        url: url,
//                        method: 'POST',
//                        data: $httpParamSerializerJQLike({
//                            "startDate": $scope.startDate,
//                            "endDate": $scope.endDate,
//                            "uniqueTransId": $scope.uniqueTransId,
//                            "trans_code": $scope.trans_code,
//                            "transType": $scope.transType,
//                            "source": $scope.bank_code,
//                            "destination": $scope.jpSubscriberId,
//                            "trans_status": $scope.trans_status,
////                    "scbType": $('#scbType').val(),
//                            "product": $('#product').val(),
//                            "merchant": $scope.merchant || 'ALL',
//                            "service": "transactions",
//                            "pageNumber": $scope.pageNumber,
//                            "rowsPerPage": $scope.rowsPerPage
//                        }),
//                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                    }).then(function success(response) {
//                        $scope.showLoader = false;
//                        $rootScope.hideLoading();
//                        $('.reload').removeClass('fa-spin text-info');
//                        $scope.JPResp = response.data;
//                        if ($scope.JPResp.code === "00") {
//                            $scope.JPTrxs = JSON.parse($scope.JPResp.data);
//                            $scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage);
//                            $scope.end = parseInt($scope.begin + $scope.rowsPerPage);
//                            $scope.filteredJPTrxs = $scope.JPTrxs.slice($scope.begin, $scope.end);
//                            $scope.getStatistics('JPTrxs');
//                            $scope.getJPTransactionsTotalFee();
//                        } else {
//
//                        }
//                    }, function error(response) {
//                        $scope.showLoader = false;
//                        $rootScope.hideLoading();
//                        $('.reload').removeClass('fa-spin text-info');
//                        //$rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                    });
//                } else {
//                    $rootScope.toggleInfoNotification("Select values for start and end date");
//                }
//            };
//            $scope.searchJPTrxn = function () {
//                profileService.updateUserProfile();
//                if ($scope.searchTrxnKey.length > 15) {
//                    $scope.showLoader = true;
//                    $rootScope.showLoading();
//                    $('.reload').addClass('fa-spin text-info');
//                    $http({
//                        url: $scope.jpProxy,
//                        method: 'POST',
//                        data: $httpParamSerializerJQLike({
//                            "searchKey": $scope.searchTrxnKey,
//                            "service": "search"
//                        }),
//                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                    }).then(function success(response) {
//                        $scope.showLoader = false;
//                        $rootScope.hideLoading();
//                        $('.reload').removeClass('fa-spin text-info');
//                        $scope.JPResp = response.data;
//                        if ($scope.JPResp.code === "00") {
//                            $scope.JPTrxs = JSON.parse($scope.JPResp.data);
//                            $scope.getJPTransactionsCount();
//                        } else if ($scope.JPResp.code === "01") {
//                            $scope.JPTrxs = "";
//                        } else {
//                            $scope.JPTrxs = "";
//                        }
//                    }, function error(response) {
//                        $scope.showLoader = false;
//                        $rootScope.hideLoading();
//                        $('.reload').removeClass('fa-spin text-info');
//                        //$rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                    });
//                } else {
//                    alert("Length of Transaction ID must be greater than 15");
//                }
//            };
//            $scope.getJPTransactionsCount = function () {
//                $http.get('./JustPayTransactions?action=count').then(
//                        function (response) {
//                            $scope.fgRecordsTotalCount = JSON.parse(response.data);
//                            $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//                            if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//                                $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//                            }
//                        }
//                );
//            };
//            $scope.getJPTransactionsTotalAmount = function () {
//                $http.get('./JustPayTransactions?action=totalAmount').then(
//                        function (response) {
//                            $scope.fgRecordsTotalAmount = response.data;
//                        });
//            };
//            $scope.getJPTransactionsTotalFee = function () {
//                $http.get('./JustPayTransactions?action=totalFee').then(
//                        function (response) {
//                            $scope.fgRecordsTotalFee = response.data;
//                        });
//            };
//            $scope.getWebconTrnxs = function (page, paging) {
//                profileService.updateUserProfile();
//                if ($('#start-date').val() && $('#end-date').val()) {
//                    $scope.showLoader = true;
//                    $rootScope.showLoading();
//                    $scope.showFilterBox = false;
//                    $scope.pageNumber = page;
//                    $('.reload').addClass('fa-spin text-info');
//                    var url = $scope.webconProxy;
//                    if ($scope.pageNumber < 1)
//                        $scope.pageNumber = 1;
//                    if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                        $scope.pageNumber = $scope.fgRecordsLastPage;
////            console.log('DATA: ' + $scope.pageNumber + ' : ' + $scope.fgRecordsLastPage);
//                    if (paging) {
//
////                console.log('DATA: ' + page + ' : ' + $scope.fgRecordsLastPage);
//                        $scope.begin = (page - 1) * parseInt($scope.rowsPerPage);
//                        $scope.end = parseInt($scope.begin) + parseInt($scope.rowsPerPage);
////                console.log('begin: ' + $scope.begin + ' : ' + $scope.end);
//                        $scope.filteredwebconTrxs = $scope.webconTrxs.slice($scope.begin, $scope.end);
//                        $scope.getWebconTotalCount();
//                        $scope.getWebconTotalAmount();
//                        $scope.showLoader = false;
//                        $rootScope.hideLoading();
//                        $('.reload').removeClass('fa-spin text-info');
//                    } else {
//                        $http({
//                            url: url,
//                            method: 'POST',
//                            data: $httpParamSerializerJQLike({
//                                "startDate": $scope.startDate,
//                                "endDate": $scope.endDate,
//                                "payType": $scope.payType,
//                                "wcMerchant": $('#wcMerchant').val().replace("string:", "") || 'ALL',
//                                "status": $scope.status,
//                                "service": "transactions",
//                                "pageNumber": $scope.pageNumber,
//                                "rowsPerPage": $scope.rowsPerPage
//                            }),
//                            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                        }).then(function success(response) {
//                            $scope.showLoader = false;
//                            $rootScope.hideLoading();
//                            $('.reload').removeClass('fa-spin text-info');
//                            $scope.webconTrxResp = response.data;
//                            if ($scope.webconTrxResp.code === "00") {
//                                $scope.webconTrxs = JSON.parse($scope.webconTrxResp.data);
//                                $scope.getWebconTotalCount();
//                                $scope.getWebconTotalAmount();
//                                $scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage);
//                                $scope.end = parseInt($scope.begin + $scope.rowsPerPage);
//                                $scope.filteredwebconTrxs = $scope.webconTrxs.slice($scope.begin, $scope.end);
//                            }
//                        }, function error(response) {
//                            $scope.showLoader = false;
//                            $rootScope.hideLoading();
//                            $('.reload').removeClass('fa-spin text-info');
//                            //$rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                        });
//                        $rootScope.updateRecordsTable();
//                    }
//                } else {
//                    $rootScope.toggleInfoNotification("Select values for start and end date");
//                }
//
//
//            };
//            $scope.searchWebconTrxn = function () {
//                if ($scope.searchTrxnKey.length > 6) {
//                    $scope.showLoader = true;
//                    $rootScope.showLoading();
//                    $('.reload').addClass('fa-spin text-info');
//                    $http({
//                        url: $scope.webconProxy,
//                        method: 'POST',
//                        data: $httpParamSerializerJQLike({
//                            "searchKey": $scope.searchTrxnKey,
//                            "service": "search"
//                        }),
//                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                    }).then(function success(response) {
//                        $scope.showLoader = false;
//                        $rootScope.hideLoading();
//                        $('.reload').removeClass('fa-spin text-info');
//                        $scope.webconTrxResp = response.data;
//                        if ($scope.webconTrxResp.code === "00") {
//                            $scope.webconTrxs = JSON.parse($scope.webconTrxResp.data);
//                            $scope.getWebconTotalCount();
//                            $scope.getWebconTotalAmount();
//                            $scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage);
//                            $scope.end = parseInt($scope.begin + $scope.rowsPerPage);
//                            $scope.filteredwebconTrxs = $scope.webconTrxs.slice($scope.begin, $scope.end);
//                        } else if ($scope.webconTrxResp.code === "01") {
//                            $scope.webconTrxs = "";
//                        } else {
//                            $scope.webconTrxs = "";
//                        }
//                    }, function error(response) {
//                        $scope.showLoader = false;
//                        $rootScope.hideLoading();
//                        $('.reload').removeClass('fa-spin text-info');
//                        //$rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                    });
////                    $rootScope.updateRecordsTable();
//                } else {
//                    alert("Length of Transaction ID must be greater than 6");
//                }
//            };
//            $scope.getWebconTotalCount = function () {
//                $scope.fgRecordsTotalCount = $scope.webconTrxs.length;
//                $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//                if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//                    $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//                }
//            };
//            $scope.getWebconTotalAmount = function () {
//                $scope.fgRecordsTotalAmount = $scope.sumAmount($scope.webconTrxs, 'response', 'SUCCESSFUL', 'trans_amount');
//            };
//            $scope.setOrderProperty = function (propertyName) {
//                if ($scope.orderProperty === propertyName) {
//                    $scope.orderProperty = '-' + propertyName;
//                } else if ($scope.orderProperty === '-' + propertyName) {
//                    $scope.orderProperty = propertyName;
//                } else {
//                    $scope.orderProperty = propertyName;
//                }
//            };
//            $scope.initializePortalSettings = function () {
//                $scope.getPortalSettings(1);
//            };
//            $scope.initializeProviders = function () {
//                $http.get('../XPortalProxy/api/MerchantsInfo?action=providers').then(
//                        function (response) {
//                            $scope.providerList = response.data;
//                        }
//                );
//            };
//            $scope.initializeSenderIds = function () {
//                $http.get('../XPortalProxy/api/MerchantsInfo?action=senderIds').then(
//                        function (response) {
//                            $scope.providerList = response.data;
//                        }
//                );
//            };
//            $scope.initializeBanks = function () {
//                $http.get('../XPortalProxy/api/MerchantsInfo?action=banks').then(
//                        function (response) {
//                            $scope.banksList = response.data;
//                        }
//                );
//            };
//            $scope.initializeChannels = function () {
//                $http.get('../XPortalProxy/api/MerchantsInfo?action=channels').then(
//                        function (response) {
//                            $scope.channelsList = response.data;
//                        }
//                );
//            };
//            $scope.initializeVasGateMerchants = function () {
//                $http.get('../XPortalProxy/api/MerchantsInfo?action=vasgate').then(
//                        function (response) {
//                            $scope.vasgateMerchantList = response.data;
//                        }
//                );
//            };
//            $scope.initializeTMCSettings = function () {
//                $scope.record = "new";
//                $http.get('../XPortalProxy/api/MerchantsInfo?action=bankNodes').then(
//                        function (response) {
//                            $scope.TMCBankNodeList = response.data;
////                            $scope.BankNodesSet();
//                        }
//                );
//            };
//            $scope.initializeVasGateSettings = function () {
//                $scope.initializeBanks();
//                $scope.initializeChannels();
//                $scope.initializeVasGateMerchants();
//            };
//            $scope.initializeWCMerchants = function () {
////                console.log('log');
//                $http.get('../XPortalProxy/api/MerchantsInfo?action=wcMerchants').then(
//                        function (response) {
//                            $scope.wcMerchantList = response.data;
//                        }
//                );
//            };
//            $scope.initializeGcbAgentTrans = function () {
//                $scope.getGCBTotal();
//                $scope.getAgentTransaction(1);
//            };
//            $scope.initializeFundgateMerchantBalance = function () {
//                $scope.getFundgateBalanceReport(1);
//            };
//            $scope.initializeCPayMerchants = function () {
//                var i = $scope.profile.user_code;
//                $scope.getCPayMerchants();
//            };
//            $scope.initializeMerchantSettings = function () {
//                $scope.getMPayMerchants();
//                $scope.getMPayProducts(1);
//            };
//            $scope.initializeMerchantPaySettings = function () {
//                console.log('testing');
//                if ($scope.testEtz() || $scope.testMerchantPayAll()) {
//                    $scope.getMPayMerchants();
//                } else {
//                    $scope.getMPayProducts();
//                }
//            };
//            $scope.initializeUserManagementSettings = function () {
//                $scope.initializeBanks();
//                $scope.initializeUserTypes();
//            };
//            $scope.initializeUserTypes = function () {
//                $http.get('../XPortalProxy/api/MerchantsInfo?action=typeIdList').then(
//                        function (response) {
//                            $scope.typeIdList = response.data;
////                    $scope.showUserTypes = true;
//                        }
//                );
//            };
//            $scope.initializeBucketBalance = function () {
//                $http.get('../XPortalProxy/api/MerchantsInfo?action=fetchBuckets').then(
//                        function (response) {
//                            $scope.bucketBalanceTypes = response.data;
//                            $scope.fetchBucketBalance();
//                        }
//                );
//            };
//            $scope.fetchBucketBalance = function () {
//                angular.forEach($scope.bucketBalanceTypes, function (value, index) {
//                    $http.get('../XPortalProxy/api/MerchantsInfo?action=fetchBucketsBalance&type=' + value.alias).then(
//                            function (response) {
//                                $scope[value.alias] = $scope.getAmt(response.data.replace(",", ""));
//                            }
//                    );
//                });
//            };
//            $scope.calcBalanceLevel = function (balance, min) {
//                var calcBal = $scope.calBalancePercentage(balance, min);
//                var strokeColor = "";
//                switch (true) {
//                    case (calcBal < 25):
//                        strokeColor = "red";
//                        break;
//                    case (calcBal < 50):
//                        strokeColor = "orange";
//                        break;
//                    case (calcBal < 75):
//                        strokeColor = "blue";
//                        break;
//                    case (calcBal > 75):
//                        strokeColor = "green";
//                        break;
//                    default:
//                        strokeColor = "brown";
//                        break;
//                }
//                return strokeColor;
//            };
//            $scope.calBalanceStroke = function (balance, min) {
//                var calcBal = $scope.calBalancePercentage(balance, min);
//                $scope['percent' + balance] = calcBal > 100 ? 100 : calcBal;
//                return calcBal + ", 100";
//            };
//            $scope.calBalancePercentage = function (balance, min) {
//                var calcBal = 0;
//                if (typeof $scope[balance] === 'number' || typeof $scope[balance] === 'string') {
//                    var availableBal = (typeof $scope[balance] === 'number' ? $scope[balance].toString() : $scope[balance]);
//                    availableBal = availableBal.replace(",", "");
//                    calcBal = ((availableBal / min) * 100).toFixed(2);
//                }
//                return calcBal;
//            };
//            $scope.getUserTypeOptions = function (typeId, scopeVariable) {
////        console.log(typeId + ' ' + scopeVariable);
//                $http.get('../XPortalProxy/api/MerchantsInfo?action=typeIdListOptions&option=' + typeId).then(
//                        function (response) {
//                            var model = $parse(scopeVariable);
//                            model.assign($scope, response.data);
////                    console.log('datafield10_ ' + JSON.stringify($scope["datafield10_"]));
//                        }
//                );
//            };
//            $scope.initReportList = function () {
//
//                $scope.initProviderList();
//                $scope.initSenderIdList();
//            };
//            $scope.initSenderIdList = function () {
//                $http.get('../XPortalProxy/api/MerchantsInfo?action=senderIdList').then(
//                        function (response) {
////                            var model = $parse(scopeVariable);
////                            model.assign($scope, response.data);
//
////                    console.log('datafield10_ ' + JSON.stringify($scope["datafield10_"]));
//
//                            $scope.senderIdList = response.data;
//                            if ($scope.senderIdList.length > 0) {
//                                $scope.sender = $scope.senderIdList[0].id;
////                                console.log('ter: ' + $scope.senderIdList[0].id);
//                            }
//                        }
//                );
//            };
//            $scope.initProviderList = function () {
//                $http.get('../XPortalProxy/api/MerchantsInfo?action=providerList').then(
//                        function (response) {
////                            var model = $parse(scopeVariable);
////                            model.assign($scope, response.data);
//
////                    console.log('datafield10_ ' + JSON.stringify($scope["datafield10_"]));
//
//                            $scope.smsProviderList = response.data;
//                            if ($scope.smsProviderList.length > 0) {
//                                $scope.provider = $scope.smsProviderList[0].id;
////                                console.log('ter: ' + $scope.senderIdList[0].id);
//                            }
//                        }
//                );
//            };
//            $scope.getCPayMerchants = function () {
//                $http.get('../XPortalProxy/api/MerchantsInfo?action=cpayMerchants').then(
//                        function (response) {
//                            $scope.cpayMerchantList = response.data;
//                        }
//                );
//            };
//            $scope.getScbMerchants = function () {
//                $http.get('../XPortalProxy/api/MerchantsInfo?action=scbMerchants').then(
//                        function (response) {
//                            $scope.scbMerchantList = response.data;
//                        }
//                );
//            };
//            $scope.getEtzMerchants = function () {
//                $http.get('../XPortalProxy/api/MerchantsInfo?action=etzMerchants').then(
//                        function (response) {
//                            $scope.etzMerchantList = response.data;
//                        }
//                );
//            };
//            $scope.getEtzMerchantProducts = function () {
//                $http.get('../XPortalProxy/api/MerchantsInfo?action=etzMerchantProducts').then(
//                        function (response) {
//                            $scope.etzMerchantProductList = response.data;
//                        });
//            };
//            $scope.getMPayProducts = function (init) {
//                $http.get('../XPortalProxy/api/MerchantsInfo?action=mpayProducts&merchant=' + $scope.merchant + (init !== undefined ? "&init=" + init : "")).then(
//                        function (response) {
//                            $scope.merchantPayProductList = response.data;
//                        });
//            };
//            $scope.getMerchantPayProductList = function () {
//                if ($scope.merchant !== 'ALL') {
//                    $scope.getMPayProducts();
//                    $scope.product = "ALL";
//                } else {
//                    $scope.merchantPayProductList = "";
//                    $scope.product = "ALL";
//                }
//            };
//            $scope.getMerchantPayMerchantList = function () {
//                if ($scope.bank !== 'ALL') {
//                    $scope.getMPayMerchants();
//                    $scope.merchant = "ALL";
//                    $scope.product = "ALL";
//                } else {
//                    $scope.merchantPayMerchantList = "";
//                    $scope.merchantPayProductList = "";
//                    $scope.merchant = "ALL";
//                    $scope.product = "ALL";
//                }
//            };
//            $scope.getMPayMerchants = function () {
//                var j = $scope.getRoleParams($scope.profile.user_code, "[2000000000000049]");
//                $http.get('../XPortalProxy/api/MerchantsInfo?action=' + ($scope.testEtz() ? $scope.bank : j.split(":")[1]).toLowerCase() + 'Merchants').then(
//                        function (response) {
//                            $scope.merchantPayMerchantList = response.data;
//                        });
//            };
////    $scope.initializeKYCReport = function () {
////        $scope.currentLocation = $location.path().substring(1);
////        if ($scope.currentLocation === "KYCReport") {
////            $scope.getKYCReport(1);
////        }
////    };
//
//            $scope.goBack = function () {
//                $scope.showagents = false;
//            };
//            $scope.verifyMobileNumber = function () {
//                $scope.portedNetwork = "";
//                $scope.ported = "";
//                $scope.originalNetwork = "";
//                $('#verifyPhone-btn').prop("disabled", true);
//                $('#verifyPhone-btn').html("<i class='fa fa-spinner fa-spin btn-loader'></i> please wait");
//                profileService.updateUserProfile();
//                var url = $scope.verifyProxy;
//                $http({
//                    url: url,
//                    method: 'POST',
//                    data: $httpParamSerializerJQLike({
//                        "mobile_no": $scope.mobile_no,
//                        "service": "numberLookup"
//                    }),
//                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                }).then(function success(response) {
////            $scope.binUpdateResultView = true;
//                    $('#verifyPhone-btn').prop("disabled", false);
//                    $('#verifyPhone-btn').html("Check");
//                    $scope.verifyNumberResp = response.data;
//                    if ($scope.verifyNumberResp.code === "00") {
//                        $scope.verifyNumberResp = response.data;
////                console.log($scope.verifyNumberResp.data);
//                        $scope.numberDetails = $scope.verifyNumberResp.data;
//                        $scope.portedNetwork = $scope.numberDetails.split(':')[1];
//                        $scope.ported = $scope.numberDetails.split(':')[1];
//                        $scope.originalNetwork = $scope.numberDetails.split(':')[2];
//                    } else {
//                        $scope.portedNetwork = "N/A";
//                        $scope.ported = "N/A";
//                        $scope.originalNetwork = "N/A";
//                    }
//                }, function error(response) {
//                    $scope.showLoader = false;
//                    $rootScope.hideLoading();
////            $scope.binUpdateResultView = false;
//                    $('#verifyPhone-btn').prop("disabled", false);
//                    $('#verifyPhone-btn').html("Check");
//                    $('.reload').removeClass('fa-spin text-info');
//                    //$rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                });
//            };
//            $scope.getUsers = function (page, paging) {
//                profileService.updateUserProfile();
//                $scope.showLoader = true;
//                $rootScope.showLoading();
////      $scope.showFilterBox = false;
//                $scope.pageNumber = page;
//                $('.reload').addClass('fa-spin text-info');
//                var url = $scope.adminProxy;
//                if ($scope.pageNumber < 1)
//                    $scope.pageNumber = 1;
//                if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                    $scope.pageNumber = $scope.fgRecordsLastPage;
//                if (paging) {
//
//                    $scope.begin = (page - 1) * parseInt($scope.rowsPerPage);
//                    $scope.end = parseInt($scope.begin) + parseInt($scope.rowsPerPage);
//                    $scope.filtereduserProfiles = $scope.userProfiles.slice($scope.begin, $scope.end);
//                    $scope.showLoader = false;
//                    $rootScope.hideLoading();
//                    $('.reload').removeClass('fa-spin text-info');
//                } else {
//                    $http({
//                        url: url,
//                        method: 'POST',
//                        data: $httpParamSerializerJQLike({
//                            "username": $scope.username,
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
//                            $scope.userProfiles = JSON.parse($scope.usersResp.data);
//                            $scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage);
//                            $scope.end = parseInt($scope.begin + $scope.rowsPerPage);
//                            $scope.filtereduserProfiles = $scope.userProfiles.slice($scope.begin, $scope.end);
//                            userProfiles = $scope.filtereduserProfiles;
//                        }
//                        $scope.getStatistics('userProfiles');
//                    }, function error(response) {
//                        $scope.showLoader = false;
//                        $rootScope.hideLoading();
//                        $('.reload').removeClass('fa-spin text-info');
//                        //$rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                    });
//                }
//            };
//            $scope.getUserData = function (key, username) {
//                profileService.updateUserProfile();
//                $scope.showLoader = true;
////                $rootScope.showLoading();
////      $scope.showFilterBox = false;
//
//                $('.reload').addClass('fa-spin text-info');
//                var url = $scope.adminProxy;
//                $http({
//                    url: url,
//                    method: 'POST',
//                    data: $httpParamSerializerJQLike({
//                        "username": username,
//                        "service": "users",
//                        "pageNumber": $scope.pageNumber,
//                        "rowsPerPage": $scope.rowsPerPage
//                    }),
//                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                }).then(function success(response) {
//                    $scope.showLoader = false;
//                    $rootScope.hideLoading();
//                    $scope.showCardUpdateBox = false;
//                    $('.reload').removeClass('fa-spin text-info');
//                    $scope.usersResp = response.data;
//                    $scope.userProfiles = "";
//                    if ($scope.usersResp.code === "00") {
//                        $scope.searchUserProfile = JSON.parse($scope.usersResp.data);
//                        switch (key) {
//                            case "editUser":
//                                $scope.userUpdateForm($scope.userIndex);
//                                break;
//                            case "editRoles":
//                                $scope.roleUpdateForm($scope.userIndex);
//                                break;
//                            case "activateUser":
//                                $scope.changeUserStatus($scope.userIndex, "activate");
//                                break;
//                            case "deactivateUser":
//                                $scope.changeUserStatus($scope.userIndex, "deactivate");
//                                break;
//                            case "resetPassword":
//                                $scope.resetUserPassword($scope.userIndex);
//                                break;
//                            default:
//                                break;
//                        }
//                    }
//                }, function error(response) {
//                    $scope.showLoader = false;
//                    $rootScope.hideLoading();
//                    $('.reload').removeClass('fa-spin text-info');
//                    //$rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                });
//
//            };
//
//            $scope.hotlistCardView = function (index, hotlist) {
//                $scope.formName = "Card Information";
//                $scope.formType = "HOTLIST";
//                $scope.formButton = hotlist;
//                $scope.showCardUpdateBoxView();
//                $scope.card_num_to_update = $scope.crdTrxs[index].card2;
//                $scope.card_num3 = $scope.crdTrxs[index].card_num;
//                $scope.email = $scope.crdTrxs[index].Email;
//                $scope.firstName = $scope.crdTrxs[index].Firstname;
//                $scope.lastName = $scope.crdTrxs[index].Lastname;
//                $scope.from_source2 = $scope.crdTrxs[index].Phone;
//                $scope.fax = $scope.crdTrxs[index].fax;
////        alert($scope.crdTrxs[index].fax.split(' ')[1]);
//                $scope.IdType = $scope.crdTrxs[index].fax.split(' ')[1];
////        $scope.user_hotlist = $scope.crdTrxs[index].User_Hotlist;
//                if ($scope.fax !== "null" || $scope.fax !== "") {
//                    $scope.CheckId();
//                }
//            };
//            $scope.checkformType = function (formtype, hotlist) {
//                if (formtype === "HOTLIST") {
//                    return hotlist;
//                } else
//                    return "UPDATE";
//            };
//            $scope.checkForm = function () {
//                if ($scope.formButton === "UPDATE") {
//                    return true;
//                }
//                return false;
//            };
//            $scope.searchCard = function () {
//                if ($scope.searchTrxnKey.length > 5) {
//                    $scope.showLoader = true;
//                    $rootScope.showLoading();
//                    $('.reload').addClass('fa-spin text-info');
//                    $http({
//                        url: $scope.crdDetProxy,
//                        method: 'POST',
//                        data: $httpParamSerializerJQLike({
//                            "searchKey": $scope.searchTrxnKey,
//                            "service": "search"
//                        }),
//                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                    }).then(function success(response) {
//                        $scope.showLoader = false;
//                        $rootScope.hideLoading();
//                        $scope.showCardUpdateBox = false;
//                        $('.reload').removeClass('fa-spin text-info');
//                        $scope.crdTrxResp = response.data;
//                        if ($scope.crdTrxResp.code === "00") {
//                            $scope.crdTrxs = JSON.parse($scope.crdTrxResp.data);
//                            $scope.firstName = $scope.crdTrxs[1].Firstname;
//                        } else {
//                            $scope.crdTrxs = "";
//                            if ($scope.crdTrxResp.message) {
//                                $scope.errorData = "Error Message: " + $scope.crdTrxResp.message;
//                            }
//                        }
//                    }, function error(response) {
//                        $scope.showLoader = false;
//                        $rootScope.hideLoading();
//                        $('.reload').removeClass('fa-spin text-info');
//                        //$rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                    });
//                } else {
//                    alert("Length of Transaction ID or Mobile Nos must be greater than 5");
//                }
//            };
//            $scope.updateForm = function (index) {
//                $scope.formName = "Card Information";
//                $scope.formType = "UPDATE";
//                $scope.formButton = "UPDATE";
//                $scope.showCardUpdateBoxView();
//                $scope.card_num_to_update = $scope.crdTrxs[index].card2;
//                $scope.card_num3 = $scope.crdTrxs[index].card_num;
//                $scope.email = $scope.crdTrxs[index].Email;
//                $scope.firstName = $scope.crdTrxs[index].Firstname;
//                $scope.lastName = $scope.crdTrxs[index].Lastname;
//                $scope.street = $scope.crdTrxs[index].Street;
//                $scope.city = $scope.crdTrxs[index].city;
//                $scope.bound_work = $scope.crdTrxs[index].bound_work;
//                $scope.from_source2 = $scope.crdTrxs[index].Phone;
//                $scope.fax = $scope.crdTrxs[index].fax;
////        alert($scope.fax);
//                if ($scope.fax.length > 10) {
//                    $scope.IdType = $scope.crdTrxs[index].fax.split(' ')[1];
//                    $scope.IdNumber = $scope.crdTrxs[index].fax.split(' ')[1];
//                    $scope.CheckId();
//                }
//
//                $scope.checkAction($scope.crdTrxs[index].action);
//            };
//            $scope.userUpdateForm = function (index) {
//                $scope.formName = "Update User Info";
//                $scope.formType = "UPDATE";
//                $scope.formButton = "Modify user!";
//                $scope.email = $scope.searchUserProfile[0].email;
//                $scope.firstName = $scope.searchUserProfile[0].firstname;
//                $scope.lastName = $scope.searchUserProfile[0].lastname;
//                $scope.type_id = $scope.searchUserProfile[0].type_id;
//                $scope.username = $scope.searchUserProfile[0].username;
//                $scope.userId = $scope.searchUserProfile[0].user_id;
//                $scope.mobile = $scope.searchUserProfile[0].mobile;
//                $scope.usercode = $scope.searchUserProfile[0].user_code;
//                $scope.require2FA = $scope.searchUserProfile[0].require2FA;
//                var modifyHtml = `<div class="card-block font08">
//                                    <br/>
//                                        <form method="post" id="userUpdateForm">
//                                            <table class="table borderless" id="item_table">        
//                                                <tr>
//                                                    <td><label for="firstName" style="font-size: small; margin-bottom: 0em !important;">First Name:<i class="text-danger" style="display: inline-block;"> *</i></label></td>
//                                                    <td><input class="form-control form-control-sm text-primary" name="firstName"  ng-model="firstName" id="firstName" value="` + $scope.firstName + `" required/></td>
//                                                </tr>
//                                                <tr>
//                                                    <td><label for="lastName" style="font-size: small; margin-bottom: 0em !important;">Last Name:<i class="text-danger" style="display: inline-block;"> *</i></label></td>
//                                                    <td><input class="form-control form-control-sm text-primary" name="lastName" ng-model="lastName" id="lastName" value="` + $scope.lastName + `" required /></td>
//                                                </tr>
//                                                <tr>
//                                                    <td><label for="email" style="font-size: small; margin-bottom: 0em !important;">Email:</label></td>
//                                                    <td><input class="form-control form-control-sm text-primary" name="email" type="email" ng-model="email" id="email" value="` + $scope.email + `"  /></td>
//                                                </tr> 
//                                                <tr>
//                                                    <td><label for="mobile" style="font-size: small; margin-bottom: 0em !important;">Mobile No:<i class="text-danger" style="display: inline-block;"> *</i></label></td>
//                                                    <td><input class="form-control form-control-sm text-primary" name="mobile" ng-model="mobile" id="mobile" value="` + $scope.mobile + `" /></td>
//                                                </tr>
//                                                <tr>
//                                                    <td><label for="require2fa" style="font-size: small; margin-bottom: 0em !important;">Enable 2FA:</label></td>
//                                                    <td style="text-align: left;"><label class="switch"><input type="checkbox" id="require2FA" name="require2FA" ng-model="require2FA" ng-change="change2FA(require2FA)"  value="` + $scope.require2FA + `" ` + ($scope.require2FA === "true" ? "checked" : "") + `> <span class="slider"></span></label></td>
//                                                </tr>
//                                            </table>
//                                            <input type="hidden" name="service" value="modifyUser"/>
//                                            <input type="hidden" name="userId" value="` + $scope.userId + `"/>
//                                        </form>
//                                    </div>`;
//                setTimeout(function () {
//                    console.log('resolving');
//                    swangular.open({
////                            title: $scope.formName,
//                        html: modifyHtml,
//                        showCancelButton: true,
//                        confirmButtonColor: '#3085d6',
//                        cancelButtonColor: '#d33',
//                        confirmButtonText: $scope.formButton,
//                        showLoaderOnConfirm: true,
//                        preConfirm: (login) => {
//                            $scope.require2FA = (Swal.getPopup().querySelector('#require2FA').checked);
//                            var loginForm = $('#userUpdateForm').serializeJSON();
//                            loginForm['require2FA'] = $scope.require2FA;
//                            console.log('JSON: ' + JSON.stringify(loginForm));
//                            return $http({
//                                url: $scope.adminProxy,
//                                method: 'POST',
//                                data: $httpParamSerializerJQLike({
//                                    "modifyUserData": JSON.stringify(loginForm),
//                                    "service": "modifyUser"
//                                }),
//                                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                            }).then(function success(response) {
//                                console.log('response.data' + response.data.data);
//                                var respData = JSON.parse(response.data.data);
//                                if (respData.message !== 'User Modified Successfully') {
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
//                            if (respData.message === 'User Modified Successfully') {
//                                Swal.fire({title: 'Success', text: 'User Info Modified Successfully!', type: 'success', allowOutsideClick: false});
//                            } else {
//                                Swal.showValidationMessage("User Modification Failed: " + respData.message);
//                            }
//                        }
//                    });
//                }, 500);
//            };
//            $scope.change2FA = function (value) {
//                console.log("changed: " + value);
//            };
//            $scope.portalUpdateForm = function (index) {
//                $scope.formName = "Update Setting";
//                $scope.formType = "UPDATE";
//                $scope.formButton = "Modify Setting!";
//                var name = portalSettings[index].name;
//                var description = portalSettings[index].description;
//                var current_value = portalSettings[index].current_value;
//                var id = portalSettings[index].id;
//                var type_1 = portalSettings[index].type_1;
//                var type_2 = portalSettings[index].type_2;
//                var composedTime = `<div class="form-group">
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
//                //FETCH USER ACCESS
//
//                var modifyHtml = `<div class="card-block font08">
//                                    <br/>
//                                        <form method="post" id="portalUpdateForm">
//                                            <table class="table borderless" id="item_table">        
//                                                <tr>
//                                                    <td><label for="name" style="font-size: small; margin-bottom: 0em !important;">Name:<i class="text-danger" style="display: inline-block;"> *</i></label></td>
//                                                    <td><input class="form-control form-control-sm text-primary" name="name"  ng-model="name" id="name" value="` + name + `" required/></td>
//                                                </tr>
//                                                <tr>
//                                                    <td><label for="description" style="font-size: small; margin-bottom: 0em !important;">Description:<i class="text-danger" style="display: inline-block;"> *</i></label></td>
//                                                    <td><textarea class="form-control form-control-sm text-primary" name="description" ng-model="description" id="description" max-rows="3" style="resize: none;" required >` + description + `</textarea></td>
//                                                </tr>
//                                                <tr>
//                                                    <td><label for="current_value" style="font-size: small; margin-bottom: 0em !important;">Value :<i class="text-danger" style="display: inline-block;"> *</i></label></td>
//                                                    <td>
//                                                    ` + (type_1 === 'time' ? composedTime : '<input type="text" class="form-control" name="current_value" value="' + current_value + '">') + `	
//                                                    </td>                                               
//                                                </tr>
//                                            </table>
//                                            <input type="hidden" name="service" value="modifyPortalSetting"/>
//                                            <input type="hidden" name="id" value="` + id + `"/>
//                                        </form>
//                                    </div>`;
//                Swal.fire({
////                            title: $scope.formName,
//                    html: modifyHtml,
//                    showCancelButton: true,
//                    confirmButtonColor: '#3085d6',
//                    cancelButtonColor: '#d33',
//                    confirmButtonText: $scope.formButton,
//                    showLoaderOnConfirm: true,
//                    preConfirm: (login) => {
////                        $('#userUpdateForm').find('input').each(function () {
////                            if (!$(this).prop('required')) {
////                                console.log("NR");
////                            } else {
////                                console.log("IR");
////                            }
////                        });
//                        var loginForm = $('#portalUpdateForm').serializeJSON({checkboxUncheckedValue: "false"});
//                        console.log('JSON: ' + JSON.stringify(loginForm));
//                        return $http({
//                            url: $scope.adminProxy,
//                            method: 'POST',
//                            data: $httpParamSerializerJQLike({
//                                "modifyPortalSettings": JSON.stringify(loginForm),
//                                "service": "portalSettingsUpdate"
//                            }),
//                            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                        }).then(function success(response) {
//                            console.log('response.data' + response.data.data);
//                            var respData = JSON.parse(response.data.data);
//                            if (respData.message !== 'PortalSetting Modified Successfully') {
//                                throw new Error(respData.message);
//                            }
//                            return response;
//                        }).catch(function (error) {
//                            Swal.showValidationMessage(error);
//                        });
//                    },
//                    allowOutsideClick: false
//                }).then(function (result) {
//                    if (result.value) {
//                        var respData = JSON.parse(result.value.data.data);
//                        console.log('result: ' + respData.message);
//                        if (respData.message === 'PortalSetting Modified Successfully') {
//                            Swal.fire({title: 'Success', text: 'PortalSetting Info Modified Successfully!', type: 'success', allowOutsideClick: false});
//                        } else {
//                            Swal.showValidationMessage(respData.message);
//                        }
////                        $scope.getPortalSettings(1,1);
//                    }
//                });
//            };
//            $scope.changePortalSettingStatus = function (index, action) {
//                $scope.hideFilterBox();
//                var name = portalSettings[index].name;
//                var id = portalSettings[index].key_name;
//                Swal.fire({
//                    title: "Are you sure ?",
////                    text: "This will " + action + " " + $scope.username,
//                    html: "This will <b>" + action + " " + name + "</b>",
//                    type: 'warning',
//                    showCancelButton: true,
//                    confirmButtonColor: '#3085d6',
//                    cancelButtonColor: '#d33',
//                    confirmButtonText: 'Yes, ' + action + ' Setting!',
//                    showLoaderOnConfirm: true,
//                    preConfirm: function preConfirm(login) {
//                        console.log('action: ' + action);
//                        return $http({
//                            url: $scope.adminProxy,
//                            method: 'POST',
//                            data: $httpParamSerializerJQLike({
//                                "id": id,
//                                "status": action,
//                                "service": "changePortalSettingsStatus"
//                            }),
//                            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                        }).then(function success(response) {
//                            console.log(response.data);
//                            return response;
//                        }).catch(function (error) {
//                            Swal.showValidationMessage("Request failed ");
//                        });
//                    },
//                    allowOutsideClick: function allowOutsideClick() {
//                        return !Swal.isLoading();
//                    }
//                }).then(function (result) {
//                    console.log('USer: ' + JSON.stringify(result));
//                    //do asyc
//                    if (result.value) {
//                        var resp = result.value.data.data;
//                        portalSettings[index].status = (resp === 'SUCCESS' ? (action === 'enable' ? 1 : 0) : portalSettings[index].status);
//                        var respText = (resp === 'SUCCESS' ? name + ' has been ' + action + 'd.' : (resp === 'FAILED' ? 'Failed to ' + action + ' ' + name + '.' : resp));
//                        var respType = (resp === 'SUCCESS' ? 'success' : 'error');
//                        var respTitle = (resp === 'SUCCESS' ? action + 'd!' : 'Failed');
////                        console.log('after: ' + JSON.stringify(userProfiles[index]));
//                        if (resp === 'SUCCESS') {
//                            $('#portalSettingStatus' + index).html((action === 'enable' ? '1' : '0'));
//                        }
//                        Swal.fire({title: respTitle, text: respText, type: respType, timer: 1500});
//                    }
//                });
//            };
//            $scope.createFinalHtml = function (roleResp) {
//                return $q(function (resolve, reject) {
//                    var finalHtml = "";
//                    console.log('newt: ' + $scope.newListl);
//                    console.log('newt2: ' + $rootScope.newList);
//                    angular.forEach(roleResp, function (value, index) {
////                        console.log('typeId: ' + value.id);
//                        finalHtml += $scope.populateRoleElement(value.id, value.userRole, value.roleOptions, index);
//                    });
////                    console.log('finalHtml: ' + finalHtml);
//                    $scope.compiledHtml = finalHtml;// $compile(angular.element(finalHtml))($scope);
//                    setTimeout(function () {
//                        resolve($scope.compiledHtml);
//                    }, 500);
//                });
//            };
//            $scope.roleUpdateForm = function (index) {
//                $scope.formName = "Update User Roles";
//                $scope.formType = "UPDATE";
//                $scope.formButton = "Modify Roles";
//                $scope.userId = $scope.searchUserProfile[0].user_id;
//                $scope.username = $scope.searchUserProfile[0].username;
//                //FETCH USER ACCESS
//
//                swangular.open({
//                    title: "Loading User Roles ...",
////                    text: "This will " + action + " " + $scope.username,
//                    html: "Fetching <b>" + $scope.username + "'s</b> data. Please wait",
//                    type: 'info',
//                    showCancelButton: true,
//                    showLoaderOnConfirm: true,
//                    onOpen() {
//                        swangular.clickConfirm();
//                    },
//                    preConfirm: function preConfirm(login) {
////                        console.log('action: ' + action);
//                        return $http({
//                            url: $scope.adminProxy,
//                            method: 'POST',
//                            data: $httpParamSerializerJQLike({
//                                "userId": $scope.userId,
//                                "service": "fetchUserRoles"
//                            }),
//                            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                        }).then(function success(response) {
//                            console.log('response');
//                            return response;
//                        }).catch(function (error) {
//                            Swal.showValidationMessage("Request failed");
//                        });
//                    },
//                    allowOutsideClick: false
//                }).then(function (result) {
//                    console.log('user: ' + 'JSON.stringify(result)');
//                    //do asyc
//                    $timeout(function () {
//                        $rootScope.newList = ((JSON.parse(result.value.data.data).typeIdList));
//                    });
////                    $rootScope.newList = ((JSON.parse(result.value.data.data).typeIdList));
//                    var roleResp = ((JSON.parse(result.value.data.data).roleInfo));
//
//                    console.log('before: ' + roleResp);
//                    console.log('before2: ' + $rootScope.newList);
//                    var finalHtml = "";
//                    angular.forEach(roleResp, function (value, index) {
////                        console.log('typeId: ' + value.id);
//                        finalHtml += $scope.populateRoleElement(value.id, value.userRole, value.roleOptions, index);
//                    });
////                    console.log('finalHtml: ' + finalHtml);
//                    $scope.compiledHtml = finalHtml;
//
//                    //POPULATE FIELDS
//
////                    $scope.createFinalHtml(roleResp).then(function (compiledHtml) {
//                    console.log('creating view');
//                    var modifyHtml = `<div class="card-block font08 ">
//                                    <div class="text-right">
//                                        <button class="badge badge-success update-btn py-2 px-2 cursor-pointer addRoleElement" >ADD ROLE <i class="fa fa-user-plus"></i></button>
//                                    </div>
//                                    <br/>
//                                        <form method="post" id="userUpdateForm">
//                                            <table class="table borderless" id="item_table">
//                                                <div id="typeIdData">
//                                                    ` + finalHtml + `
//                                                </div>
//                                            </table>
//                                            <input type="hidden" name="service" value="modifyUser"/>
//                                            <input type="hidden" name="userId" value="` + $scope.userId + `"/>
//                                        </form>
//                                    </div>`;
////                        $scope.compiledHtml = $compile(angular.element(finalHtml))($scope);
////                        setTimeout(function () {
//                    console.log('resolving');
////                        $('#typeIdData').prepend(compiledHtml);
//                    swangular.open({
////                            title: $scope.formName,
////                            onOpen() {
////                                $('#typeIdData').prepend(compiledHtml);
////                                $scope.$apply();
////                            },
//                        controller: 'fgTransactionsCtrl',
//                        html: modifyHtml,
//                        showCancelButton: true,
//                        confirmButtonColor: '#3085d6',
//                        cancelButtonColor: '#d33',
//                        confirmButtonText: $scope.formButton,
//                        showLoaderOnConfirm: true,
//                        preConfirm: (login) => {
//                            var loginForm = $('#userUpdateForm').serializeJSON({checkboxUncheckedValue: "false"});
//                            if ((loginForm).item_name) {
//                                var userOptions = [];
//                                for (var i = 0; i < (loginForm).item_name.length; i++) {
//                                    userOptions.push({
//                                        "userOption0": (loginForm).item_name[i],
//                                        "userOption1": (loginForm).item_quantity[i] || ''
//                                    });
//                                }
//                                delete loginForm['item_name'];
//                                delete loginForm['item_quantity'];
//                                loginForm['userOptions'] = userOptions;
//                            }
//
//                            console.log('JSON: ' + JSON.stringify(loginForm));
//                            return $http({
//                                url: $scope.adminProxy,
//                                method: 'POST',
//                                data: $httpParamSerializerJQLike({
//                                    "modifyUserRoles": JSON.stringify(loginForm),
//                                    "service": "modifyUserRoles"
//                                }),
//                                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                            }).then(function success(response) {
//                                console.log('response.data' + response.data.data);
//                                var respData = JSON.parse(response.data.data);
//                                if (respData.message !== 'User Modified Successfully') {
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
//                            if (respData.message === 'User Modified Successfully') {
//                                Swal.fire({title: 'Success', text: 'User Roles Modified Successfully!', type: 'success', allowOutsideClick: false});
//                            } else {
//                                Swal.showValidationMessage("User Creation Failed: " + respData.message);
//                            }
//                        }
//                    });
////                        }, 1000);
//                    if (!roleElementBound) {
//                        $(document).on('click', '.addRoleElement', function (event) {
//
//                            event.preventDefault();
//                            $scope.addRoleElement();
//                        });
//                        roleElementBound = true;
//                    }
////                    });
//                });
//            };
//            $.contextMenu({
//                selector: '.user-record',
//                autoHide: true,
//                build: function (triggerElement, e) {
////                        console.log('det: ' + $(triggerElement).attr('id'));
//                    $scope.userIndex = $(triggerElement).attr('id');
////                    console.log('user: ' + JSON.stringify(userProfiles));
//                    var status = userProfiles[$scope.userIndex].status_id;
//                    var statusIcon = ((status === '1') ? "user-lock" : "user-activate");
//                    var statusText = ((status === '1') ? "Deactivate User" : "Activate User");
//                    var statusKey = ((status === '1') ? "deactivateUser" : "activateUser");
//                    var menuData = {
//                        "editUser": {"name": "Edit User", "icon": "user-edit"},
//                        "editRoles": {"name": "Edit User Roles", "icon": "user-tag"},
//                        [statusKey]: {"name": statusText, "icon": statusIcon},
//                        "resetPassword": {"name": "Password Reset", "icon": "user-resetpassword"}
//                    };
//                    return {
//                        callback: function (key, options) {
////                            window.console && console.log(m);
//                            $scope.getUserData(key, userProfiles[$scope.userIndex].username);
//                        },
//                        items: menuData
//                    };
//                }
//            });
//            $.contextMenu({
//                selector: '.portal-record',
//                build: function (triggerElement, e) {
////                        console.log('det: ' + $(triggerElement).attr('id'));
//                    var settingIndex = $(triggerElement).attr('id');
////                    console.log('user: ' + JSON.stringify(userProfiles));
//                    var status = portalSettings[settingIndex].status;
//                    console.log('status:' + status);
//                    var statusIcon = ((status === '1') ? "lock" : "unlock");
//                    var statusText = ((status === '1') ? "Disable Setting" : "Enable Setting");
//                    var statusKey = ((status === '1') ? "disableSetting" : "enableSetting");
//                    var menuData = {
//                        "editSetting": {"name": "Edit Setting", "icon": "edit"},
//                        [statusKey]: {"name": statusText, "icon": statusIcon}
//                    };
//                    return {
//                        callback: function (key, options) {
////                            window.console && console.log(m);
//                            switch (key) {
//                                case "editSetting":
//                                    $scope.portalUpdateForm(settingIndex);
//                                    break;
//                                case "disableSetting":
//                                    $scope.changePortalSettingStatus(settingIndex, "disable");
//                                    break;
//                                case "enableSetting":
//                                    $scope.changePortalSettingStatus(settingIndex, "enable");
//                                    break;
//                                default:
//                                    break;
//                            }
//                        },
//                        items: menuData
//                    };
//                }
//            });
//            $scope.getTypeIdOptions = function (option) {
//
//                $scope.getUserTypeOptions($scope.values[option], 'data' + option);
//                if ($('#data' + option).length < 1) {
//                    $scope.addTypeIdOptions(option);
//                }
//            };
//            $scope.createSelectBox = function (selected) {
//                return $q(function (resolve, reject) {
//                    $http.get('../XPortalProxy/api/MerchantsInfo?action=banks').then(
//                            function (response) {
//                                $scope.banksList = response.data;
//                                var options = `<tr>
//                                <td><label for="banks" style="font-size: small;">Bank</label></td>
//                                <td>
//                                    <select class="form-control form-control-sm" name="bankCode" ng-model="bankCode" id="banks">`;
//                                angular.forEach($scope.banksList, function (value, index) {
////                    console.log('bank: ' + value.issuer_code + ' : ' + value.issuer_name);
//                                    if (selected) {
//                                        options += '<option ' + (selected === value.issuer_code ? ' selected ' : '') + ' value ="' + value.issuer_code + '">' + value.issuer_name + ' </option>';
//                                    } else {
//                                        if (value.issuer_code !== '000') {
//                                            options += '<option selected value ="000">ETZ ADMIN BANK</option>';
//                                            options += '<option value ="' + value.issuer_code + '">' + value.issuer_name + '</option>';
//                                        }
//                                    }
//                                });
//                                options += `</select>    
//                                </td>
//                               </tr>`;
//                                resolve(options);
//                            }
//                    );
//                });
//            };
//            $scope.showCreateUserForm = function () {
//                $scope.formName = "Create New User";
//                $scope.formType = "CreateUser";
//                $scope.formButton = "Create User";
//                $scope.email = "";
//                $scope.firstName = "";
//                $scope.lastName = "";
//                $scope.type_id = "";
//                $scope.require2FA = false;
//                Swal.fire({
////                    title: $scope.formName,
//                    html: `<div class="card-block font08 addUserForm">
//                                    <div class="text-right">
//                                        <button class="badge badge-success update-btn py-2 px-2 cursor-pointer addRoleElement">ADD ROLE <i class="fa fa-user-plus"></i></button>
//                                    </div>
//                                    <br/>
//                                        <form method="post" id="userUpdateForm">
//                                            <table class="table borderless" id="item_table">        
//                                                <tr>
//                                                    <td><label for="firstName" style="font-size: small;">First Name:<i class="text-danger"> *</i></label></td>
//                                                    <td><input class="form-control form-control-sm text-primary" name="firstName"  ng-model="firstName" id="firstName" required/></td>
//                                                </tr>
//                                                <tr>
//                                                    <td><label for="lastName" style="font-size: small;">Last Name:<i class="text-danger"> *</i></label></td>
//                                                    <td><input class="form-control form-control-sm text-primary" name="lastName" ng-model="lastName" id="lastName" required /></td>
//                                                </tr>
//                                                <tr>
//                                                    <td><label for="username" style="font-size: small;">Username:<i class="text-danger"> *</i></label></td>
//                                                    <td class="inner-addon right-addon">
//                                                        <i class="fa fa-check-circle available" style="color:green; display:none;"></i>
//                                                        <i class="fa fa-times-circle unavailable" style="color:red; display:none;" ></i>
//                                                        <input type="text" class="form-control" id ="newUsername" name="newUsername" ng-model="newUsername"  ng-keyup="checkUsernameTimeout(newUsername)"/>
//                                                    </td>
//                                                </tr>
//                                                <tr>
//                                                    <td><label for="email" style="font-size: small;">Email:</label></td>
//                                                    <td><input class="form-control form-control-sm text-primary" name="email" type="email" ng-model="email" id="email" /></td>
//                                                </tr> 
//                                                <tr>
//                                                    <td><label for="mobile" style="font-size: small;">Mobile:</label></td>
//                                                    <td><input class="form-control form-control-sm text-primary" name="mobile" ng-model="mobile" id="mobile" /></td>
//                                                </tr>
//                                                <tr>
//                                                    <td><label for="require2fa" style="font-size: small; margin-bottom: 0em !important;">Enable 2FA:</label></td>
//                                                    <td style="text-align: left;"><label class="switch"><input type="checkbox" ng-model="require2FA" id="require2FA" name="require2FA"><span class="slider"></span></label></td>
//                                                </tr>
//                                                <div id="typeIdData"></div>
//                                            </table>
//                                            <input type="hidden" name="service" value="createUser"/>
//                                        </form>
//                                    </div>`,
//                    showCancelButton: true,
//                    confirmButtonColor: '#3085d6',
//                    cancelButtonColor: '#d33',
//                    confirmButtonText: $scope.formButton,
//                    showLoaderOnConfirm: true,
//                    preConfirm: (login) => {
//                        $scope.require2FA = (Swal.getPopup().querySelector('#require2FA').checked);
//                        var loginForm = $('#userUpdateForm').serializeJSON();
//                        loginForm['require2FA'] = $scope.require2FA;
//                        console.log('hg: ' + JSON.stringify(loginForm));
//                        console.log((loginForm).item_name.length);
//                        if ((loginForm).item_name) {
//                            var userOptions = [];
//                            for (var i = 0; i < (loginForm).item_name.length; i++) {
//                                if (!(loginForm).item_quantity) {
//                                    userOptions.push({
//                                        "userOption0": (loginForm).item_name[i]
//                                    });
//                                } else {
//                                    userOptions.push({
//                                        "userOption0": (loginForm).item_name[i],
//                                        "userOption1": (loginForm).item_quantity[i]
//                                    });
//                                }
//                            }
//                            delete loginForm['item_name'];
//                            delete loginForm['item_quantity'];
//                            loginForm['userOptions'] = userOptions;
//                        }
//
//
//                        console.log('JSON: ' + JSON.stringify(loginForm));
//                        return $http({
//                            url: $scope.adminProxy,
//                            method: 'POST',
//                            data: $httpParamSerializerJQLike({
//                                "createUserData": JSON.stringify(loginForm),
//                                "service": "createUser"
//                            }),
//                            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                        }).then(function success(response) {
//                            console.log('response.data' + response.data.data);
//                            var respData = JSON.parse(response.data.data);
//                            if (respData.message !== 'User Created Successfully') {
//                                throw new Error(respData.message);
//                            }
//                            return response;
//                        }).catch(function (error) {
//                            Swal.showValidationMessage(error);
//                        });
//                    },
//                    allowOutsideClick: false
//                }).then((result) => {
//                    if (result.value) {
//
//                        var respData = JSON.parse(result.value.data.data);
//                        console.log('result: ' + respData.message);
//                        if (respData.message === 'User Created Successfully') {
//                            Swal.fire({title: 'User Created Successfully!', text: 'Username: ' + respData.username + ' password: ' + respData.password, type: 'success', allowOutsideClick: false});
//                        } else {
//                            Swal.showValidationMessage("User Creation Failed: " + respData.message);
//                        }
//
//                    }
//                });
//                if (!roleElementBound) {
//                    $(document).on('click', '.addRoleElement', function (event) {
//
//                        event.preventDefault();
//                        $scope.addRoleElement();
//                    });
//                    roleElementBound = true;
//                }
//            };
//            $scope.getPortalSettings = function (page, paging) {
//                $scope.updateApp();
//                $scope.showLoader = true;
//                $rootScope.showLoading();
//                $scope.showFilterBox = false;
//                $scope.pageNumber = page;
//                $('.reload').addClass('fa-spin text-info');
//                var url = $scope.adminProxy;
//                if ($scope.pageNumber < 1)
//                    $scope.pageNumber = 1;
//                if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                    $scope.pageNumber = $scope.fgRecordsLastPage;
//                if (paging) {
//
//                    $scope.begin = (page - 1) * parseInt($scope.rowsPerPage);
//                    $scope.end = parseInt($scope.begin) + parseInt($scope.rowsPerPage);
//                    $scope.filteredPortalSettings = $scope.PortalSettings.slice($scope.begin, $scope.end);
//                    $scope.showLoader = false;
//                    $rootScope.hideLoading();
//                    $('.reload').removeClass('fa-spin text-info');
//                } else {
//                    $http({
//                        url: url,
//                        method: 'POST',
//                        data: $httpParamSerializerJQLike({
//                            "setting_name": $scope.transtype,
//                            "service": "portalSettings"
//                        }),
//                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                    }).then(function success(response) {
//                        $scope.showLoader = false;
//                        $rootScope.hideLoading();
//                        $('.reload').removeClass('fa-spin text-info');
//                        $scope.PortalSettingsResp = response.data;
//                        if ($scope.PortalSettingsResp.code === "00") {
//                            $scope.PortalSettings = JSON.parse($scope.PortalSettingsResp.data);
//                            $scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage);
//                            $scope.end = parseInt($scope.begin + $scope.rowsPerPage);
//                            $scope.filteredPortalSettings = $scope.PortalSettings.slice($scope.begin, $scope.end);
//                            portalSettings = $scope.filteredPortalSettings;
//                        }
//                        $scope.getStatistics('PortalSettings');
//                    }, function error(response) {
//                        $scope.showLoader = false;
//                        $rootScope.hideLoading();
//                        $('.reload').removeClass('fa-spin text-info');
//                        //$rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                    });
//                }
//            };
//            $scope.showSetting = function (setting, index) {
//                $scope.username = userProfiles[index].username;
//                $scope.action = action;
//                $scope.userId = userProfiles[index].user_id;
//                Swal.fire({
////                    title: "Are you sure ?",
////                    text: "This will " + action + " " + $scope.username,
//                    html: "This will <b>" + action + " " + $scope.username + "</b>",
//                    showCancelButton: true,
//                    confirmButtonColor: '#3085d6',
//                    cancelButtonColor: '#d33',
//                    confirmButtonText: 'Yes, ' + action + ' user!',
//                    showLoaderOnConfirm: true,
//                    preConfirm: function preConfirm(login) {
//                        console.log('action: ' + action);
//                        return $http({
//                            url: $scope.adminProxy,
//                            method: 'POST',
//                            data: $httpParamSerializerJQLike({
//                                "setting_name": $scope.userId,
//                                "status": $scope.action,
//                                "service": "updateSettings"
//                            }),
//                            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                        }).then(function success(response) {
//                            console.log(response.data);
//                            return response;
//                        }).catch(function (error) {
//                            Swal.showValidationMessage("Request failed ");
//                        });
//                    },
//                    allowOutsideClick: function allowOutsideClick() {
//                        return !Swal.isLoading();
//                    }
//                }).then(function (result) {
//                    console.log('USer: ' + JSON.stringify(result));
//                    //do asyc
//                    var resp = result.value.data.data;
//                    console.log('before: ' + JSON.stringify(userProfiles[index]));
//                    console.log('action: ' + $scope.action);
//                    userProfiles[index].status_id = (resp === 'SUCCESS' ? ($scope.action === 'activate' ? 1 : 2) : userProfiles[$scope.userId].status_id);
//                    var respText = (resp === 'SUCCESS' ? $scope.username + ' has been ' + action + 'd.' : (resp === 'FAILED' ? 'Failed to ' + action + ' ' + $scope.username + '.' : resp));
//                    var respType = (resp === 'SUCCESS' ? 'success' : 'error');
//                    var respTitle = (resp === 'SUCCESS' ? action + 'd!' : 'Failed');
//                    console.log('after: ' + JSON.stringify(userProfiles[index]));
//                    if (resp === 'SUCCESS') {
//                        $('#user' + index).html(($scope.action === 'activate' ? 'active' : 'inactive'));
//                    }
//                    Swal.fire({title: respTitle, text: respText, type: respType, timer: 1500});
//                });
//            };
//            $scope.authorizeForm = function (index) {
//                $scope.formName = "Card Information";
//                $scope.formType = "AUTHORIZE";
//                $scope.formButton = "AUTHORIZE";
//                $('#deny').prop("disabled", false);
//                $('#authorize').prop("disabled", false);
//                $scope.showCardUpdateBoxView();
//                $scope.card_num_to_update = $scope.cmsReq[index].card2;
//                $scope.card_num3 = $scope.cmsReq[index].card_num;
//                $scope.email = $scope.cmsReq[index].Email;
//                $scope.firstName = $scope.cmsReq[index].Firstname;
//                $scope.lastName = $scope.cmsReq[index].Lastname;
//                $scope.street = $scope.cmsReq[index].Street;
//                $scope.city = $scope.cmsReq[index].city;
////        $scope.bound_work = $scope.cmsReq[index].bound_work;
//                $scope.from_source2 = $scope.cmsReq[index].Phone;
//                $scope.fax = $scope.cmsReq[index].fax;
//                if ($scope.fax) {
////             console.log('use me');
//                    $scope.IdType = $scope.cmsReq[index].fax.split(' ')[1];
//                    $scope.IdNumber = $scope.cmsReq[index].fax.split(' ')[1];
//                    $scope.CheckId();
//                }
//
//                $scope.reason = $scope.cmsReq[index].reason;
//                $scope.action = $scope.cmsReq[index].action;
//                $scope.intiator = $scope.cmsReq[index].initiated_by;
//                $scope.initiated_date = $scope.cmsReq[index].date_initiated;
//                $scope.id = $scope.cmsReq[index].id;
//                $scope.checkAction($scope.cmsReq[index].action);
//            };
//            $scope.authoriseForm = function (index) {
//
////        $('.example2').on('click', function () {
//
//                $scope.formName = "Card Information";
//                $scope.formType = "AUTHORIZE";
//                $scope.formButton = "AUTHORIZE";
//                $('#deny').prop("disabled", false);
//                $('#authorize').prop("disabled", false);
//                $scope.showCardUpdateBoxView();
//                $scope.card_num_to_update = $scope.cmsReq[index].card2;
//                $scope.card_num3 = $scope.cmsReq[index].card_num;
//                $scope.email = $scope.cmsReq[index].Email;
//                $scope.firstName = $scope.cmsReq[index].Firstname;
//                $scope.lastName = $scope.cmsReq[index].Lastname;
//                $scope.street = $scope.cmsReq[index].Street;
//                $scope.city = $scope.cmsReq[index].city;
////        $scope.bound_work = $scope.cmsReq[index].bound_work;
//                $scope.from_source2 = $scope.cmsReq[index].Phone;
//                $scope.fax = $scope.cmsReq[index].fax;
//                if ($scope.fax) {
////             console.log('use me');
//                    $scope.IdType = $scope.cmsReq[index].fax.split(' ')[1];
//                    $scope.IdNumber = $scope.cmsReq[index].fax.split(' ')[1];
//                    $scope.CheckId();
//                }
//
//                $scope.reason = $scope.cmsReq[index].reason;
//                $scope.action = $scope.cmsReq[index].action;
//                $scope.intiator = $scope.cmsReq[index].initiated_by;
//                $scope.initiated_date = $scope.cmsReq[index].date_initiated;
//                $scope.id = $scope.cmsReq[index].id;
//                $scope.checkAction($scope.cmsReq[index].action);
//                $.confirm({
//                    title: 'Confirm!',
//                    closeIcon: true,
//                    closeIconClass: 'fa fa-close',
//                    columnClass: 'medium',
//                    draggable: true,
//                    content: function () {
//                        var self = this;
//                        return $.ajax({
//                            url: 'bower.json',
//                            dataType: 'json',
//                            method: 'post',
//                            data: data
//                        }).done(function (response) {
//                            self.setContent('' +
//                                    '<form ng-submit="updateCard();">' +
//                                    '<div class="row" data-ng-init="CheckId()">' +
//                                    '   <div class="col-9">' +
//                                    '      <table class="table borderless">    ' +
//                                    '         <tr>' +
//                                    '            <td><label>Card Number:</label></td>' +
//                                    '          <td><label class="">&nbsp;&nbsp;&nbsp;' + $scope.card_num3 + '</label></td>' +
//                                    '     </tr>' +
//                                    '    <tr>' +
//                                    '       <td><label for="firstName">First Name:</label></td>' +
//                                    '      <td><label class="">&nbsp;&nbsp;&nbsp;' + $scope.firstName + '</label></td>' +
//                                    ' </tr>' +
//                                    '<tr>' +
//                                    '   <td><label for="lastName">Last Name:</label></td>' +
//                                    '      <td><label class="">&nbsp;&nbsp;&nbsp;' + $scope.lastName + '</label></td>' +
//                                    '</tr>' +
//                                    '<tr>' +
//                                    '   <td><label for="from_source">Mobile No:</label></td>' +
//                                    '      <td><label class="">&nbsp;&nbsp;&nbsp;' + $scope.from_source2 + '</label></td>' +
//                                    '</tr> ' +
//                                    '<tr>' +
//                                    '  <td><label for="reason">Reason:</label></td>' +
//                                    '  <td><textarea class="form-control form-control-sm text-primary" name="reason" ng-model="reason" id="reason" max-rows="3" style="resize:none;" disabled> ' + $scope.reason + '</textarea></td>' +
//                                    '</tr>' +
//                                    '<tr>' +
//                                    '<td><label for="fax">ID:</label></td>' +
//                                    '<td>' +
//                                    '<div class="input-group">' +
//                                    '<input type="text" class="form-control form-control-sm text-primary" value="' + $scope.fax + '" disabled>' +
//                                    '<span class="input-group-btn">' +
//                                    ' <button class="btn btn-success btn-sm">' +
//                                    '   Verify' +
//                                    '</button>' +
//                                    '</span>' +
//                                    '</div>' +
//                                    '</td>' +
//                                    '</tr>' +
//                                    '</table>' +
//                                    ' </div>' +
//                                    ' <div class="col-3">' +
//                                    '<img id="verifyIDimage" src="http://ssl.gstatic.com/accounts/ui/avatar_2x.png" class="avatar img-circle img-thumbnail" alt="avatar">' +
//                                    ' </div>' +
//                                    '</div>' +
////                    '<div class="row">' +
////                    '<div class="col col-6">' +
////                    '<button id="authorize" type="submit" class="btn btn-success btn-block filter-btn" ng-click="setDecision(\'AUTHORIZE\')">AUTHORIZE</button>' +
////                    '</div>' +
////                    '<div class="col col-6">' +
////                    '<button id="deny" type="submit" class="btn btn-danger btn-block filter-btn" ng-click="setDecision(\'DENY\')">DENY</button>' +
////                    '</div>' +
//                                    '</div>' +
//                                    '</form>');
////                    self.setContentAppend('<br>Version: ' + response.version);
////                    self.setTitle(response.name);
//                        }).fail(function () {
//                            self.setContent('' +
//                                    '<form ng-submit="updateCard();">' +
//                                    '<div class="row" data-ng-init="CheckId()">' +
//                                    '   <div class="col-9">' +
//                                    '      <table class="table borderless">    ' +
//                                    '         <tr>' +
//                                    '            <td><label>Card Number:</label></td>' +
//                                    '          <td><label class="">&nbsp;&nbsp;&nbsp;' + $scope.card_num3 + '</label></td>' +
//                                    '     </tr>' +
//                                    '    <tr>' +
//                                    '       <td><label for="firstName">First Name:</label></td>' +
//                                    '      <td><label class="">&nbsp;&nbsp;&nbsp;' + $scope.firstName + '</label></td>' +
//                                    ' </tr>' +
//                                    '<tr>' +
//                                    '   <td><label for="lastName">Last Name:</label></td>' +
//                                    '      <td><label class="">&nbsp;&nbsp;&nbsp;' + $scope.lastName + '</label></td>' +
//                                    '</tr>' +
//                                    '<tr>' +
//                                    '   <td><label for="from_source">Mobile No:</label></td>' +
//                                    '      <td><label class="">&nbsp;&nbsp;&nbsp;' + $scope.from_source2 + '</label></td>' +
//                                    '</tr> ' +
//                                    '<tr>' +
//                                    '  <td><label for="reason">Reason:</label></td>' +
//                                    '  <td><textarea class="form-control form-control-sm text-primary" name="reason" ng-model="reason" id="reason" max-rows="3" style="resize:none;" disabled> ' + $scope.reason + '</textarea></td>' +
//                                    '</tr>' +
//                                    '<tr>' +
//                                    '<td><label for="fax">ID:</label></td>' +
//                                    '<td>' +
//                                    '<div class="input-group">' +
//                                    '<input type="text" class="form-control form-control-sm text-primary" value="' + $scope.fax + '" disabled>' +
//                                    '<span class="input-group-btn">' +
//                                    ' <button class="btn btn-success btn-sm">' +
//                                    '   Verify' +
//                                    '</button>' +
//                                    '</span>' +
//                                    '</div>' +
//                                    '</td>' +
//                                    '</tr>' +
//                                    '</table>' +
//                                    ' </div>' +
//                                    ' <div class="col-3">' +
//                                    '<img id="verifyIDimage" src="http://ssl.gstatic.com/accounts/ui/avatar_2x.png" class="avatar img-circle img-thumbnail" alt="avatar">' +
//                                    ' </div>' +
//                                    '</div>' +
////                    '<div class="row">' +
////                    '<div class="col col-6">' +
////                    '<button id="authorize" type="submit" class="btn btn-success btn-block filter-btn" ng-click="setDecision(\'AUTHORIZE\')">AUTHORIZE</button>' +
////                    '</div>' +
////                    '<div class="col col-6">' +
////                    '<button id="deny" type="submit" class="btn btn-danger btn-block filter-btn" ng-click="setDecision(\'DENY\')">DENY</button>' +
////                    '</div>' +
//                                    '</div>' +
//                                    '</form>');
//                        });
//                    },
//                    buttons: {
//                        confirm: function () {
//                            $.alert('Confirmed!');
//                        },
//                        cancel: function () {
//                            $.alert('Canceled!');
//                        },
//                        somethingElse: {
//                            text: 'Something else',
//                            btnClass: 'btn-blue',
//                            keys: [
//                                'enter',
//                                'shift'
//                            ],
//                            action: function () {
//                                this.$content; // reference to the content
//                                $.alert('Something else?');
//                            }
//                        }
//                    }
//                });
////        });
//
//            };
//            $scope.CheckId = function () {
////        $('.reload').addClass('fa-spin text-info');
////        alert($('#fax').val());
//                $('#checkId').prop("disabled", true);
//                $('#checkId').html("<i class='fa fa-spinner fa-spin btn-loader'></i> Checking...");
////        $scope.IdType = $scope.fax.substring($scope.fax.lastIndexOf(" "), 0);
////        alert(IdType);
////        $scope.IdNumber = $scope.fax.substring($scope.fax.lastIndexOf(" ") + 1);
////        alert(IdNumber);
//                $http({
//                    url: $scope.crdDetProxy,
//                    method: 'POST',
//                    data: $httpParamSerializerJQLike({
//                        "idType": $('#IdType').val() || $scope.IdType,
//                        "idNumber": $('#IdNumber').val() || $scope.IdNumber,
////              "idType": $('#IdType').val(),
//                        "service": "verifyID"
//                    }),
//                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                }).then(function success(response) {
//
//                    $('#checkId').html("Check");
//                    $('.reload').removeClass('fa-spin text-info');
//                    $scope.verifyIDResp = response.data;
//                    if ($scope.verifyIDResp.code === "00") {
//                        $scope.verified = JSON.parse($scope.verifyIDResp.data);
////                console.log($scope.verified.message);
////                console.log($scope.verified.message.substring(0, $scope.verified.message.indexOf("_")));
//                        $scope.fullName = $scope.verified.message.split('_')[1];
////                console.log('fullname: ' + $scope.fullName + $scope.fullName.indexOf("No Person Found"));
//                        if ($scope.fullName.indexOf("No Person Found") === -1 && $scope.fullName.indexOf("Incorrect Id Type") === -1 && $scope.fullName.indexOf("HTTP error code") === -1) {
//                            $scope.firstName = $scope.fullName.substring($scope.fullName.lastIndexOf(" "), 0);
//                            $scope.lastName = $scope.fullName.substring($scope.fullName.lastIndexOf(" ") + 1);
//                            $("#verifyIDimage").attr("src", "data:image/gif;base64," + $scope.verified.message.split('_')[3]);
////                    console.log('image: ' + $scope.verified.message.split('_')[3]);
//                            $('#updateData').prop("disabled", false);
//                        } else {
//                            $('#updateResp').addClass("bg-danger py-2");
//                            $('#updateResp').removeClass("bg-success");
//                            $('#updateResp').html('Verification Failed: ' + $scope.fullName);
//                            if ($scope.fullName.indexOf("Incorrect Id Type") !== -1) {
//                                $scope.IdName = $scope.IdType || '';
//                                $scope.IdType = "Others";
//                            }
//
//                            $scope.cardUpdateResultView = true;
////                    console.log('view: ' + $scope.cardUpdateResultView);
//                            setTimeout(function () {
////                        $('#updateResp').css('display', 'none');
//                                $scope.cardUpdateResultView = false;
//                                $scope.$apply($scope.cardUpdateResultView = false);
//                            }, 1500);
//                        }
//                    }
//
//                }, function error(response) {
////            $scope.showLoader = false; $rootScope.hideLoading();
////            $('.reload').removeClass('fa-spin text-info');
//                    $('#checkId').html("Check");
//                    $('#checkId').prop('disabled', false);
//                    //$rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                });
//            };
//            $scope.verifyVasgate = function () {
//                $scope.vasRsp = '';
//                $('#verify-btn').prop("disabled", true);
//                $('#verify-btn').html("<i class='fa fa-spinner fa-spin btn-loader'></i> please wait");
//                $scope.updateApp();
//                $http({
//                    url: $scope.verifyProxy,
//                    method: 'POST',
//                    data: $httpParamSerializerJQLike({
//                        "merchant": $scope.merchant,
//                        "account": $scope.account,
////                "action": 'query',
//                        "service": 'vasgate'
//                    }),
//                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                }).then(function success(response) {
//                    $('#verify-btn').prop("disabled", false);
//                    $('#verify-btn').html(" Verify");
//                    $scope.vasRsp = response.data;
//                }, function error(response) {
//                    $('#verify-btn').prop("disabled", false);
//                    $('#verify-btn').html(" Verify");
//                });
//            };
//            $scope.confirmationDialogConfig = {};
//            $scope.confirmationDialog = function (action) {
//
////        switch ($scope.profile.role_id) {
////
////            case "3":
//                $scope.confirmationDialogConfig = {
//                    title: "CONFIRM ACTION",
//                    message: "Are you sure you want proceed?",
//                    buttons: [{
//                            label: "PROCEED",
//                            action: action
//                        }]
//                };
//                $scope.showDialog(true);
//            };
//            $scope.executeDialogAction = function (action) {
//                if (typeof $scope[action] === "function") {
//                    $scope[action]();
//                }
//            };
//            $scope.dismissModal = function () {
//                $scope.showDialog(false);
//            };
//            $scope.showDialog = function (flag) {
//                jQuery("#confirmation-dialog .modal").modal(flag ? 'show' : 'hide');
//            };
//            $scope.addRoleElement = function () {
//                var html = '';
////                $scope.values = [];
////                $scope.values['field' + $scope.currentIndex + '0_'] = '17';
//                html += '<tr id="field' + $scope.currentIndex + '0_">';
//                html += '<td style="padding-right: 10px"><button type="button" name="remove" class="btn btn-danger btn-sm remove" ng-click="removeRoleElement();"><i class="fa fa-trash"></i></button></td>';
//                html += '<td style="padding-right: 10px">';
//                html += '<select class="form-control form-control-sm item_name" name="item_name[]" ng-model="values[\'field' + $scope.currentIndex + '0_\']" id="type_id" ng-Change="getTypeIdOptions(\'field' + $scope.currentIndex + '0_\')" required >';
//                html += '<option value="" selected disabled>Select User Type</option>';
//                html += '<option ng-repeat="option in typeIdList" title="" value="{{option.id}}">{{option.name}}</option>';
//                html += '</select>';
//                html += '</td>';
//                html += '</tr>';
//                $('#typeIdData').prepend($compile(angular.element(html))($scope));
//                $scope.currentIndex++;
//                $("#typeIdData").animate({scrollTop: 0}, "slow");
//            };
//            $scope.addTypeIdOptions = function (id) {
////        if (id === '43') {
//                var html = '';
//                html += '<td >';
//                html += '<div id="data' + id + '">';
//                html += '<select class="form-control form-control-sm item_quantity" name="item_quantity[]" ng-model="values[\'field' + $scope.currentIndex + '1_\'+$index]" id="type_id"  >';
//                html += '<option value="" selected disabled>Select User Type</option>';
//                html += '<option ng-repeat="option in data' + id + '" value="{{option.alias}}">{{option.name}}</option>';
//                html += '</select>';
//                html += '</div>';
//                html += '</td>';
//                $('#' + id).append($compile(angular.element(html))($scope));
//            };
//            $rootScope.newList = [{"id": "1500000000000039", "name": "ICGC"}]; //{"id": "94", "name": "INFOBIB"}, {"id": "2000000000000054", "name": "JUSTPAY"}, {"id": "2000000000000055", "name": "JUSTPAY_"}, {"id": "65", "name": "KNUST"}, {"id": "2000000000000043", "name": "KU LEUVEN"}, {"id": "95", "name": "KWESSE TV"}, {"id": "2000000000000049", "name": "MerchantPay"}, {"id": "47", "name": "MerchantTrnx"}, {"id": "20", "name": "MIDLAND"}, {"id": "91", "name": "MOBILE MONEY"}, {"id": "2000000000000045", "name": "MOH-MCP"}, {"id": "42", "name": "MTN"}, {"id": "99", "name": "MTN"}, {"id": "39", "name": "MTN1"}, {"id": "2000000000000050", "name": "MTNCMS"}, {"id": "2000000000000042", "name": "MTNREPORT"}, {"id": "33", "name": "NITA"}, {"id": "9", "name": "OTHERS"}, {"id": "10", "name": "OTHERS1"}, {"id": "2000000000000064", "name": "OVA BALANCE"}, {"id": "77", "name": "PAGA"}, {"id": "2500000000000042", "name": "PAYFLUID"}, {"id": "80", "name": "PING-EXPRESS"}, {"id": "19", "name": "PROFPAY"}, {"id": "3", "name": "REPORT OFFICER"}, {"id": "6", "name": "REPORT VIEWER"}, {"id": "98", "name": "SAS"}, {"id": "2000000000000047", "name": "SCB"}, {"id": "90", "name": "SEED FUND"}, {"id": "18", "name": "SIKA CASH"}, {"id": "13", "name": "SMS-GHANA"}, {"id": "28", "name": "STANCHART"}, {"id": "2", "name": "SUPPORT OFFICER"}, {"id": "57", "name": "SW-GLOBAL"}, {"id": "2000000000000060", "name": "TMC"}, {"id": "14", "name": "TRANSFER-TO"}, {"id": "2500000000000045", "name": "TREE"}, {"id": "76", "name": "UBA-PUBSEC"}, {"id": "1500000000000038", "name": "UBA-SUPPORT"}, {"id": "68", "name": "UCC-CCE"}, {"id": "66", "name": "UG-COLLECTIONS"}, {"id": "2000000000000046", "name": "UGFP"}, {"id": "15", "name": "UMOBILE"}, {"id": "11", "name": "UNION"}, {"id": "97", "name": "UNIVERSAL MERCHANT BANK"}, {"id": "36", "name": "UT-BANK"}, {"id": "2000000000000056", "name": "VASGATE"}, {"id": "2000000000000062", "name": "VASGATETOPUP"}, {"id": "2000000000000053", "name": "VASMERCHANT"}, {"id": "2000000000000044", "name": "VODAFONE"}, {"id": "35", "name": "VODAFONE"}, {"id": "71", "name": "WEBCONNECT"}, {"id": "38", "name": "WEBUSER"}, {"id": "25", "name": "WWBG"}];
//            $scope.cars = [
//                {"id": "1500000000000039", "name": "ICGC"}, {"id": "94", "name": "INFOBIB"}, {"id": "2000000000000054", "name": "JUSTPAY"}, {"id": "2000000000000055", "name": "JUSTPAY_"}, {"id": "65", "name": "KNUST"}, {"id": "2000000000000043", "name": "KU LEUVEN"}, {"id": "95", "name": "KWESSE TV"}, {"id": "2000000000000049", "name": "MerchantPay"}, {"id": "47", "name": "MerchantTrnx"}, {"id": "20", "name": "MIDLAND"}, {"id": "91", "name": "MOBILE MONEY"}, {"id": "2000000000000045", "name": "MOH-MCP"}, {"id": "42", "name": "MTN"}, {"id": "99", "name": "MTN"}, {"id": "39", "name": "MTN1"}, {"id": "2000000000000050", "name": "MTNCMS"}, {"id": "2000000000000042", "name": "MTNREPORT"}, {"id": "33", "name": "NITA"}, {"id": "9", "name": "OTHERS"}, {"id": "10", "name": "OTHERS1"}, {"id": "2000000000000064", "name": "OVA BALANCE"}, {"id": "77", "name": "PAGA"}, {"id": "2500000000000042", "name": "PAYFLUID"}, {"id": "80", "name": "PING-EXPRESS"}, {"id": "19", "name": "PROFPAY"}, {"id": "3", "name": "REPORT OFFICER"}, {"id": "6", "name": "REPORT VIEWER"}, {"id": "98", "name": "SAS"}, {"id": "2000000000000047", "name": "SCB"}, {"id": "90", "name": "SEED FUND"}, {"id": "18", "name": "SIKA CASH"}, {"id": "13", "name": "SMS-GHANA"}, {"id": "28", "name": "STANCHART"}, {"id": "2", "name": "SUPPORT OFFICER"}, {"id": "57", "name": "SW-GLOBAL"}, {"id": "2000000000000060", "name": "TMC"}, {"id": "14", "name": "TRANSFER-TO"}, {"id": "2500000000000045", "name": "TREE"}, {"id": "76", "name": "UBA-PUBSEC"}, {"id": "1500000000000038", "name": "UBA-SUPPORT"}, {"id": "68", "name": "UCC-CCE"}, {"id": "66", "name": "UG-COLLECTIONS"}, {"id": "2000000000000046", "name": "UGFP"}, {"id": "15", "name": "UMOBILE"}, {"id": "11", "name": "UNION"}, {"id": "97", "name": "UNIVERSAL MERCHANT BANK"}, {"id": "36", "name": "UT-BANK"}, {"id": "2000000000000056", "name": "VASGATE"}, {"id": "2000000000000062", "name": "VASGATETOPUP"}, {"id": "2000000000000053", "name": "VASMERCHANT"}, {"id": "2000000000000044", "name": "VODAFONE"}, {"id": "35", "name": "VODAFONE"}, {"id": "71", "name": "WEBCONNECT"}, {"id": "38", "name": "WEBUSER"}, {"id": "25", "name": "WWBG"}];
//            $scope.populateRoleElement = function (role, roleParam, roleOptions, index) {
//                var html = '';
//                console.log('index: ' + index);
//
//                $scope.values['field' + $scope.currentIndex + '0_'] = role;
//                console.log('type: ' + JSON.stringify($scope.typeIdList));
//                console.log('type88: ' + JSON.stringify($scope.cars));
//                html += '<tr id="field' + $scope.currentIndex + '0_">';
//                html += '<td style="padding-right: 10px"><button type="button" name="remove" class="btn btn-danger btn-sm remove" ng-click="removeRoleElement();"><i class="fa fa-trash"></i></button></td>';
//                html += '<td style="padding-right: 10px">';
////                html += '<select class="form-control form-control-sm item_name" name="item_name[]" ng-model="values[\'field' + $scope.currentIndex + '0_\']" ng-Change="getTypeIdOptions(\'field' + $scope.currentIndex + '0_\')" required >';
//////                html += '<option value="" selected disabled>Select User Type</option>';
////                html += '<option ng-repeat="option in typeIdList" title="yesu" value="{{option.id}}">{{option.name}}</option>';
////                html += '</select>';
//                html += '<select ng-model="values[\'field' + $scope.currentIndex + '0_\']"  ng-options="x.name for x in  newList"></select>';
////                html += '<select ng-model="values[\'field' + $scope.currentIndex + '0_\']" ng-options="x.name for x in typeIdList"></select>';
//                html += '</td>';
////                console.log('options: ' + roleOptions.length);
//                var model = $parse('datafield' + $scope.currentIndex + '0_');
//                model.assign($scope, roleOptions);
//                console.log('data::: ' + JSON.stringify(roleOptions));
//                console.log('data' + $scope.currentIndex + ' : ' + $scope['datafield' + $scope.currentIndex + '0_']);
////                    
////                if (roleOptions.length > 0) {
//                console.log('DAAT: ' + roleOptions.length + ' : ' + roleParam);
//                $scope.values['field' + $scope.currentIndex + '1_' + index] = (roleOptions.length > 0 ? roleParam : "");
////                    var model = $parse('data' + $scope.currentIndex);
////                    model.assign($scope, roleOptions);
//                if (roleOptions.length > 0) {
//                    html += '<td>';
////                html += '<td ng-show="' + (roleOptions.length > 0) + '">';
//                    html += '<div id="datafield' + $scope.currentIndex + '0_">';
////                    html += '<select class="form-control form-control-sm item_quantity" name="item_quantity[]" ng-model="values[\'field' + $scope.currentIndex + '1_\' + index + ]" id="type_id">';
//                    html += `<select class="form-control form-control-sm item_quantity" name="item_quantity[]" ng-model="values['field` + $scope.currentIndex + `1_` + index + `']" >`;
//                    if (!roleOptions.length > 0) {
//                        html += '<option value="" selected >Select User Type</option>';
//                    }
//                    html += '<option ng-repeat="option in datafield' + $scope.currentIndex + '0_" value="{{option.alias}}">{{option.name}}</option>';
//                    html += '</select>';
//                    html += '</div>';
//                    html += '</td>';
//                }
//
//
//                html += '</tr>';
////                $('#typeIdData').prepend($compile(angular.element(html))($scope));
//                $scope.currentIndex++;
//
//                return  html;
//            };
//            $scope.populateTypeIdOptions = function (id) {
////        if (id === '43') {
//                var html = '';
//                html += '<td >';
//                html += '<div id="data' + id + '">';
//                html += '<select class="form-control form-control-sm item_quantity" name="item_quantity[]" ng-model="values[\'field' + $scope.currentIndex + '1_\'+$index]" id="type_id"  >';
////                html += '<option value="" selected disabled>Select User Type</option>';
//                html += '<option ng-repeat="option in data' + id + '" value="{{option.alias}}">{{option.name}}</option>';
//                html += '</select>';
//                html += '</div>';
//                html += '</td>';
//                $('#' + id).append($compile(angular.element(html))($scope));
////        }
//
//            };
////            $scope.populateRoleElement = function (role) {
////                var html = '';
////                $scope.values['field' + $scope.currentIndex + '0_'] = role;
//////                console.log('lge');
////                html += '<tr id="field' + $scope.currentIndex + '0_">';
////                html += '<td style="padding-right: 10px"><button type="button" name="remove" class="btn btn-danger btn-sm remove" ng-click="removeRoleElement();"><i class="fa fa-trash"></i></button></td>';
////                html += '<td style="padding-right: 25px">';
////                html += '<select class="form-control form-control-sm item_name" name="item_name[]" ng-model="values[\'field' + $scope.currentIndex + '0_\']" id="type_id" ng-Change="getTypeIdOptions(\'field' + $scope.currentIndex + '0_\')" required >';
//////                html += '<option value="" selected disabled>Select User Type</option>';
////                html += '<option ng-repeat="option in typeIdList" title="yert" value="{{option.id}}">{{option.name}}</option>';
////                html += '</select>';
////                html += '</td>';
////                if (1 > 2) {
////                    html += '<td >';
////                    html += '<div id="data' + $scope.currentIndex + '">';
////                    html += '<select class="form-control form-control-sm item_quantity" name="item_quantity[]" ng-model="values[\'field' + $scope.currentIndex + '1_\'+$index]" id="type_id"  >';
//////                html += '<option value="" selected disabled>Select User Type</option>';
////                    html += '<option ng-repeat="option in data' + $scope.currentIndex + '" value="{{option.alias}}">{{option.name}}</option>';
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
//            $(document).on('click', '.remove', function () {
//                $(this).closest('tr').remove();
//            });
//            $('#userUpdateForm').on('submit', function (event) {
//                event.preventDefault();
//                var form_data = $(this).serialize();
////        console.log('data: ' + form_data);
//                var loginForm = $(this).serializeJSON({checkboxUncheckedValue: "false"});
////        console.log('hg: ' + JSON.stringify(loginForm));
////        console.log((loginForm).item_name.length);
//
//                if ((loginForm).item_name) {
//                    var userOptions = [];
//                    for (var i = 0; i < (loginForm).item_name.length; i++) {
//                        userOptions.push({
//                            "userOption0": (loginForm).item_name[i],
//                            "userOption1": (loginForm).item_quantity[i] || '',
//                            "userOption2": (loginForm).item_unit[i] || ''
//                        });
//                    }
//                    delete loginForm['item_name'];
//                    delete loginForm['item_unit'];
//                    delete loginForm['item_quantity'];
//                    loginForm['userOptions'] = userOptions;
////        console.log('newjson: ' + JSON.stringify(userOptions));
//
//
//                    console.log('JSON: ' + JSON.stringify(loginForm));
//                    if ($scope.usernameAvailable) {
////create user
//
//                        $http({
//                            url: $scope.adminProxy,
//                            method: 'POST',
//                            data: $httpParamSerializerJQLike({
//                                "createUserData": JSON.stringify(loginForm),
//                                "service": "createUser"
//                            }),
//                            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                        }).then(function success(response) {
//                            $scope.showLoader = false;
//                            $rootScope.hideLoading();
//                            $('.reload').removeClass('fa-spin text-info');
//                            $scope.mobileAppResp = response.data;
//                            if ($scope.mobileAppResp.code === "00") {
////                console.log('' + $scope.mobileAppResp.data);
////                console.log('data: ' + $scope.mobileAppResp.data.indexOf('Unavailable'));
//                                if ($scope.mobileAppResp.data.indexOf('Unavailable') !== -1) {
//                                    $('.unavailable').css('display', 'block');
//                                    $scope.usernameAvailable = false;
//                                }
//                                if ($scope.mobileAppResp.data.indexOf('Available') !== -1) {
//                                    $('.available').css('display', 'block');
//                                    $scope.usernameAvailable = true;
//                                }
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
//                        console.log('username is unavailable');
//                    }
//                } else {
//                    alert('Please Select User Type');
//                }
//
//
//            });
//            var checkusernametimer = "";
//            $scope.cancelCheckUsernameTimeout = function () {
//                clearTimeout(checkusernametimer);
//            };
//            $scope.checkUsernameTimeout = function (username) {
//                $scope.usernameAvailable = false;
//                $scope.cancelCheckUsernameTimeout();
//                $('.unavailable').css('display', 'none');
//                $('.available').css('display', 'none');
//                if (username.trim() !== "") {
//                    checkusernametimer = setTimeout(function () {
//                        $scope.$apply($scope.checkUsername(username));
//                    }, 1000);
//                }
//            };
//            $scope.checkUsername = function (username) {
//                $http({
//                    url: $scope.adminProxy,
//                    method: 'POST',
//                    data: $httpParamSerializerJQLike({
//                        "username": username,
//                        "service": "checkUsername"
//                    }),
//                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                }).then(function success(response) {
//                    $scope.showLoader = false;
//                    $rootScope.hideLoading();
//                    $('.reload').removeClass('fa-spin text-info');
//                    $scope.mobileAppResp = response.data;
//                    if ($scope.mobileAppResp.code === "00") {
////                console.log('' + $scope.mobileAppResp.data);
////                console.log('data: ' + $scope.mobileAppResp.data.indexOf('Unavailable'));
//                        if ($scope.mobileAppResp.data.indexOf('Unavailable') !== -1) {
//                            $('.unavailable').css('display', 'block');
//                            $scope.usernameAvailable = false;
//                        }
//                        if ($scope.mobileAppResp.data.indexOf('Available') !== -1) {
//                            $('.available').css('display', 'block');
//                            $scope.usernameAvailable = true;
//                        }
//                    } else {
//
//                    }
//                }, function error(response) {
//                    $scope.showLoader = false;
//                    $rootScope.hideLoading();
//                    $('.reload').removeClass('fa-spin text-info');
//                    //$rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                });
//            };
//            $scope.roleValues = {};
//            $scope.currentIndex = 1;
//            $scope.getMPaySettlement = function (page, paging) {
//                profileService.updateUserProfile();
//                if ($('#start-date').val() && $('#end-date').val()) {
//                    $scope.showLoader = true;
//                    $rootScope.showLoading();
//                    $scope.showFilterBox = false;
//                    $scope.pageNumber = page;
//                    $('.reload').addClass('fa-spin text-info');
//                    var url = $scope.mpaySettlementProxy;
//                    if ($scope.pageNumber < 1)
//                        $scope.pageNumber = 1;
//                    if ($scope.pageNumber !== 1 && $scope.pageNumber > $scope.fgRecordsLastPage)
//                        $scope.pageNumber = $scope.fgRecordsLastPage;
//                    if (paging) {
//                        $scope.begin = (page - 1) * parseInt($scope.rowsPerPage);
//                        $scope.end = parseInt($scope.begin) + parseInt($scope.rowsPerPage);
//                        $scope.filteredMPaySettlementTrxs = $scope.mpaySettlementTrxs.slice($scope.begin, $scope.end);
//                        $scope.getMpaySettlementTotalCount();
//                        $scope.getMpaySettlementTotalAmount();
//                        $scope.showLoader = false;
//                        $rootScope.hideLoading();
//                        $('.reload').removeClass('fa-spin text-info');
//                    } else {
//                        $http({
//                            url: url,
//                            method: 'POST',
//                            data: $httpParamSerializerJQLike({
//                                "startDate": $scope.startDate,
//                                "endDate": $scope.endDate,
//                                "merchant": $scope.merchant,
//                                "status": $scope.status,
//                                "service": "transactions",
//                                "pageNumber": $scope.pageNumber,
//                                "rowsPerPage": $scope.rowsPerPage
//                            }),
//                            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                        }).then(function success(response) {
//                            $scope.showLoader = false;
//                            $rootScope.hideLoading();
//                            $('.reload').removeClass('fa-spin text-info');
//                            $scope.mpaySettlementTrxsResp = response.data;
//                            if ($scope.mpaySettlementTrxsResp.code === "00") {
//                                $scope.mpaySettlementTrxs = JSON.parse($scope.mpaySettlementTrxsResp.data);
//                                $scope.getMpaySettlementTotalCount();
//                                $scope.getMpaySettlementTotalAmount();
//                                $scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage);
//                                $scope.end = parseInt($scope.begin + $scope.rowsPerPage);
//                                $scope.filteredMPaySettlementTrxs = $scope.mpaySettlementTrxs.slice($scope.begin, $scope.end);
//                            }
//                        }, function error(response) {
//                            $scope.showLoader = false;
//                            $rootScope.hideLoading();
//                            $('.reload').removeClass('fa-spin text-info');
//                            //$rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                        });
//                        $rootScope.updateRecordsTable();
//                    }
//                } else {
//                    $rootScope.toggleInfoNotification("Select values for start and end date");
//                }
//            };
//            $scope.searchMPaySettlement = function () {
//                if ($scope.searchTrxnKey.length > 6) {
//                    $scope.showLoader = true;
//                    $rootScope.showLoading();
//                    $('.reload').addClass('fa-spin text-info');
//                    $http({
//                        url: $scope.mpaySettlementProxy,
//                        method: 'POST',
//                        data: $httpParamSerializerJQLike({
//                            "searchKey": $scope.searchTrxnKey,
//                            "service": "search"
//                        }),
//                        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
//                    }).then(function success(response) {
//                        $scope.showLoader = false;
//                        $rootScope.hideLoading();
//                        $('.reload').removeClass('fa-spin text-info');
//                        $scope.mpaySettlementTrxsResp = response.data;
//                        if ($scope.mpaySettlementTrxsResp.code === "00") {
//                            $scope.mpaySettlementTrxs = JSON.parse($scope.mpaySettlementTrxsResp.data);
//                            $scope.getMpaySettlementTotalCount();
//                            $scope.getMpaySettlementTotalAmount();
//                            $scope.begin = parseInt(($scope.pageNumber - 1) * $scope.rowsPerPage);
//                            $scope.end = parseInt($scope.begin + $scope.rowsPerPage);
//                            $scope.filteredMPaySettlementTrxs = $scope.mpaySettlementTrxs.slice($scope.begin, $scope.end);
//                        } else if ($scope.mpaySettlementTrxsResp.code === "01") {
//                            $scope.mpaySettlementTrxs = "";
//                        } else {
//                            $scope.mpaySettlementTrxs = "";
//                        }
//                    }, function error(response) {
//                        $scope.showLoader = false;
//                        $rootScope.hideLoading();
//                        $('.reload').removeClass('fa-spin text-info');
//                        //$rootScope.toggleErrorNotification("Connection or Server Error. Try again");
//                    });
////                    $rootScope.updateRecordsTable();
//                } else {
//                    alert("Length of Transaction ID must be greater than 6");
//                }
//            };
//            $scope.getMpaySettlementTotalCount = function () {
//                $scope.fgRecordsTotalCount = $scope.mpaySettlementTrxs.length;
//                $scope.fgRecordsLastPage = $scope.fgRecordsTotalCount / $scope.rowsPerPage;
//                if ($scope.fgRecordsLastPage > parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage)) {
//                    $scope.fgRecordsLastPage = parseInt($scope.fgRecordsTotalCount / $scope.rowsPerPage) + 1;
//                }
//            };
//            $scope.getMpaySettlementTotalAmount = function () {
//                $scope.fgRecordsTotalAmount = $scope.sumAmount($scope.mpaySettlementTrxs, 'status', '0', 'settle_amount');
//            };
//            $scope.trimJP = function (text) {
//                if (text) {
//                    if (text.length > 36) {
//                        return (text.substring(0, 35)) + " ...";
//                    }
//                }
//                return text;
//            };
//            $scope.trim = function (text) {
//                if (text.length > 60) {
//                    return (text.substring(0, 62)) + " ...";
//                }
//                return text;
//            };
//            $scope.trimShort = function (text) {
//                if (text) {
//                    if (text.length > 40) {
//                        return (text.substring(0, 38)) + " ...";
//                    }
//                }
//                return text;
//            };
//            $scope.trimVasBillId = function (text) {
//                if (text.length > 30) {
//                    return (text.substring(0, 30)) + " ...";
//                }
//                return text;
//            };
//            $scope.getResponseCode = function (str) {
//                return str.split("::")[1];
//            };
//            $scope.getResponseMsg = function (str) {
//                return $scope.trim(str.split("::")[1]);
//            };
//            $scope.trim2 = function (text) {
//                if (text.length > 78) {
//                    return (text.substring(0, 77)) + " ...";
//                }
//                return text;
//            };
//            $scope.getBankName = function (bankCode) {
//                return bankCode;
//            };
//            $scope.formatStr = function (descr) {
//                var newStr = descr.replace(/,/g, ' ');
//                return newStr;
//            };
//            $scope.hideFilterBox = function () {
//                $scope.showTrxFilterBox = false;
//                $scope.showFilterBox = false;
////                $('.filter-box').toggleClass('slideOutRight slideInRight');
//                $(".filter-box").css("display", "none");
//            };
//            $scope.hideAgentBox = function () {
//                $scope.showAgent = false;
//            };
//            $scope.hideDetailsBox = function () {
//                $scope.showTrxFilterBox = false;
//                $scope.showDetailsBox = false;
//            };
//            $scope.showFilterBoxView = function () {
//                $scope.showTrxFilterBox = true;
//                $scope.showFilterBox = true;
//                $(".filter-box").css("display", "block");
//            };
//            $scope.showAgentBox = function () {
//                $scope.showAgent = true;
//            };
//            $scope.toggleFilterBoxView = function () {
//                console.log('sdfsdf');
//                if ($scope.showFilterBox) {
//                    $scope.hideFilterBox();
//                } else {
//                    $scope.showFilterBoxView();
//                }
//            };
//            $scope.toggleCPayTransView = function () {
//                $scope.showCPayTransView = !$scope.showCPayTransView;
//            };
//            $scope.showCardUpdateBoxView = function () {
//                $scope.showCardUpdateBox = true;
//                $scope.cardUpdateResultView = false;
//            };
//            $scope.hideCardUpdateBoxView = function () {
//                $scope.showCardUpdateBox = false;
//            };
//            $scope.showCardHotlistBoxView = function () {
//                $scope.showCardUpdateBox = true;
//                $scope.cardUpdateResultView = false;
//            };
//            $scope.hideCardHotlistBoxView = function () {
//                $scope.showCardUpdateBox = false;
//            };
//            $scope.toggleAgentBoxView = function () {
//                $scope.showAgent = !$scope.showAgent;
//            };
//            $scope.getChannel = function (channel) {
//                console.log(channel);
//                switch (channel) {
//                    case "ju":
//                        channel = "justpay";
//                        break;
//                    case "09":
//                        channel = "fundgate";
//                        break;
//                    case  "02":
//                        channel = "mobile";
//                        break;
//                    case "05":
//                        channel = "payoutlet";
//                        break;
//                    case "01":
//                        channel = "webconnect";
//                        break;
//                    default:
//                        channel = "N/A";
//                        break;
//                }
//                return channel;
//            };
//            $scope.fgRowColor = function (str) {
//                if ($scope.status === "ALL") {
//                    var rowClass = $scope.getResponseCode(str);
//                    if (rowClass !== "0") {
//                        if (rowClass === '31') {
//                            return "blue";
//                        }
//                        return "red";
//                    }
//                }
//            };
//            $scope.momoRowColor = function (str) {
//                if ($scope.status === "ALL") {
//                    if (str.toUpperCase() !== "SUCCESSFUL") {
//                        if (str.toUpperCase() === "PENDING") {
//                            return "blue";
//                        }
//                        return "red";
//                    }
//
//                }
//            };
//            $scope.webconnectRowColor = function (str, str2) {
//                if ($scope.status === "ALL") {
//                    str = str === null || str === undefined ? "" : str.toUpperCase();
//                    str2 = str2 === null || str2 === undefined ? "" : str2.toUpperCase();
//                    if (str !== "SUCCESSFUL" || str2 !== "SUCCESSFUL") {
//                        if (str === "PENDING") {
//                            return "blue";
//                        }
//                        if (str2 !== "PENDING") {
//                            return "blue";
//                        }
//
//                        return "red";
//                    }
//
//                }
//            };
//            $scope.flagColor = function (str) {
//                if ($scope.status === "ALL") {
//                    if (str.toUpperCase() === "PENDING") {
//                        return "yellow";
//                    }
//                }
//                if ($scope.status === "SUCCESSFUL") {
//                    if (str.toUpperCase() === "PENDING") {
//                        return "yellow";
//                    }
//                }
//                if ($scope.status === "FAILED") {
//                    if (str.toUpperCase() === "PENDING") {
//                        return "yellow";
//                    }
//                }
//            };
//            $scope.barRowColor = function (str) {
//                if ($scope.status === "ALL") {
//                    if (!(str === "000" || str.toUpperCase().indexOf("SUCCESSFUL") > -1)) {
//                        return "red";
//                    }
//                }
//            };
//            $scope.vtuRowColor = function (str) {
//                if ($scope.status === "ALL") {
//                    if (str !== "0") {
//                        return "red";
//                    }
//                }
//            };
//            $scope.jpRowColor = function (str) {
//                if ($scope.status === "ALL") {
//                    if (str === "0" || str === "00") {
//                    } else {
//                        return "red";
//                    }
//                }
//            };
//            $scope.sasRowColor = function (str) {
//                if ($scope.status === "ALL") {
//                    if (str === "0" || str === "00") {
//                    } else {
//                        return "red";
//                    }
//                }
//            };
//            $scope.tmcRowColor = function (str) {
//                if (str === "00") {
//
//                } else {
//                    switch ($scope.status) {
//                        case "ALL":
//                            if (str === "51" || str === "55" || str === "57") {
//                                return "yellow";
//                            } else if (str === "58") {
//                                return "blue";
//                            } else
//                                return "red";
//                            break;
//                        case "REVERSAL":
//                            if (str === "51" || str === "55" || str === "57") {
//                                return "yellow";
//                            } else if (str === "58") {
//                                return "blue";
//                            } else
//                                return "red";
//                            break;
//                        case "FAIL":
//                            if (str === "51" || str === "55" || str === "57") {
//                                return "yellow";
//                            } else if (str === "58") {
//                                return "blue";
//                            } else
//                                return "red";
//                            break;
//                        case "FAILED":
//                            if (str === "51" || str === "55" || str === "57") {
//                                return "yellow";
//                            } else if (str === "58") {
//                                return "blue";
//                            } else
//                                return "red";
//                            break;
//                        case "AMBIGUOUS":
//                            if (str === "51" || str === "55" || str === "57") {
//                                return "yellow";
//                            } else if (str === "58") {
//                                return "blue";
//                            } else
//                                return "red";
//                            break;
//                        case "SUCCESSFUL":
//                            break;
//                        default:
//                            break;
//                    }
//                }
//
//            };
//            $scope.kwesetvRowColor = function (str) {
//                switch ($scope.trans_status) {
//                    case "ALL":
//                        if (str !== "00") {
//                            return "red";
//                        }
//                        break;
//                    case "FAILED":
//                        if (str !== "00") {
//                            return "red";
//                        }
//                        break;
//                    case "SUCCESS":
//                        break;
//                    default:
//                        break;
//                }
//            };
//        });