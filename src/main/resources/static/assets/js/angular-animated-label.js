'use strict';
/*
 * Author: Wender Lima
 * Github: https://github.com/wender/angular-animated-label
 * */
angular.module('animatedLabel', [])
        .directive('animatedLabel', function () {
            return {
                restrict: 'A',
                link: function ($scope, el, attrs) {
                    var parent = angular.element(el).parent();
                    var css = attrs.animatedLabel || 'animated-label';
                    parent.addClass(css);
                    el.on('focus', function () {
                        parent.addClass(css + '-focus');
                    });
                    el.on('blur', function () {
                        parent.removeClass(css + '-focus');
                        if (el.val()) {
                            parent.addClass(css + '-has-value');
                        } else {
                            parent.removeClass(css + '-has-value');
                        }
                    });
                }
            };
        });