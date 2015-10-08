 'use strict';

angular.module('socialprofileApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-socialprofileApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-socialprofileApp-params')});
                }
                return response;
            },
        };
    });