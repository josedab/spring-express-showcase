'use strict';

angular.module('socialprofileApp')
    .controller('SocialprofileDetailController', function ($scope, $rootScope, $stateParams, entity, Socialprofile) {
        $scope.socialprofile = entity;
        $scope.load = function (id) {
            Socialprofile.get({id: id}, function(result) {
                $scope.socialprofile = result;
            });
        };
        $rootScope.$on('socialprofileApp:socialprofileUpdate', function(event, result) {
            $scope.socialprofile = result;
        });
    });
