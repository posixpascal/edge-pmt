'use strict';

angular.module('projects').config(['$stateProvider', function($stateProvider){
		$stateProvider.state('projects', {
			templateUrl: 'assets/templates/projects/projects.html',
			controller: 'ProjectsCtrl',
			url: '/projects'
		});
	}]);