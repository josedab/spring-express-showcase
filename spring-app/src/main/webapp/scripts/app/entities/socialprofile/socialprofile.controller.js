'use strict';

angular.module('socialprofileApp')
    .controller('SocialprofileController', function ($scope, Socialprofile, ParseLinks) {
        $scope.socialprofiles = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Socialprofile.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.socialprofiles = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Socialprofile.get({id: id}, function(result) {
                $scope.socialprofile = result;
                $('#deleteSocialprofileConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Socialprofile.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteSocialprofileConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.socialprofile = {twitter: null, facebook: null, email: null, instagram: null, pinterest: null, googleplus: null, id: null};
        };
    });
