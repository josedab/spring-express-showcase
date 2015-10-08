'use strict';

angular.module('socialprofileApp')
    .factory('Socialprofile', function ($resource, DateUtils) {
        return $resource('api/socialprofiles/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
