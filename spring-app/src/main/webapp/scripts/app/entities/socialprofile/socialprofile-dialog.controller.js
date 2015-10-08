'use strict';

angular.module('socialprofileApp').controller('SocialprofileDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Socialprofile',
        function($scope, $stateParams, $modalInstance, entity, Socialprofile) {

        $scope.socialprofile = entity;
        $scope.load = function(id) {
            Socialprofile.get({id : id}, function(result) {
                $scope.socialprofile = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('socialprofileApp:socialprofileUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.socialprofile.id != null) {
                Socialprofile.update($scope.socialprofile, onSaveFinished);
            } else {
                Socialprofile.save($scope.socialprofile, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
