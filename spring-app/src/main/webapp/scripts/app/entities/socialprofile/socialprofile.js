'use strict';

angular.module('socialprofileApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('socialprofile', {
                parent: 'entity',
                url: '/socialprofiles',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Socialprofiles'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/socialprofile/socialprofiles.html',
                        controller: 'SocialprofileController'
                    }
                },
                resolve: {
                }
            })
            .state('socialprofile.detail', {
                parent: 'entity',
                url: '/socialprofile/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Socialprofile'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/socialprofile/socialprofile-detail.html',
                        controller: 'SocialprofileDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Socialprofile', function($stateParams, Socialprofile) {
                        return Socialprofile.get({id : $stateParams.id});
                    }]
                }
            })
            .state('socialprofile.new', {
                parent: 'socialprofile',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/socialprofile/socialprofile-dialog.html',
                        controller: 'SocialprofileDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {twitter: null, facebook: null, email: null, instagram: null, pinterest: null, googleplus: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('socialprofile', null, { reload: true });
                    }, function() {
                        $state.go('socialprofile');
                    })
                }]
            })
            .state('socialprofile.edit', {
                parent: 'socialprofile',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/socialprofile/socialprofile-dialog.html',
                        controller: 'SocialprofileDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Socialprofile', function(Socialprofile) {
                                return Socialprofile.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('socialprofile', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
