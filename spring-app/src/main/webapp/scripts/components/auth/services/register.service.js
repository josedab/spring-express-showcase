'use strict';

angular.module('socialprofileApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


