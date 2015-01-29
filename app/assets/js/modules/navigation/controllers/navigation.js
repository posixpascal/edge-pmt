angular.module("navigation").controller("NavCtrl", ["$scope", function($scope){
	$scope.modules = [
		{
			title: "Übersicht",
			endpoint: "home"
		},
		{
			title: "Projekte",
			endpoint: "projects"
		}
	];
}]);