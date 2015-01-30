'use strict';

angular
    .module(ApplicationConfiguration.applicationModuleName, ApplicationConfiguration.applicationModuleVendorDependencies);

angular
    .module(ApplicationConfiguration.applicationModuleName)
    .config(['$locationProvider',
        function($locationProvider) {
            $locationProvider.hashPrefix('!');
        }
    ]).config(['$mdThemingProvider', function($mdThemingProvider){
        $mdThemingProvider.theme("THEME_NAME");
    }]);

angular
    .element(document)
    .ready(function() {
        angular.bootstrap(document, [ApplicationConfiguration.applicationModuleName]);
    });

