'use strict';

angular.module('socialprofileApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
