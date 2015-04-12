/// <reference path="./reference.ts" />

var app = angular.module('vidme', ['controllers', 'ngRoute']);

app.config(function ($routeProvider:ng.route.IRouteProvider) {
    $routeProvider.when('/login', {
        controller: 'SecurityCtrl',
        templateUrl: 'assets/partialviews/login.html'
    }).when('/:status', {
        controller: 'TodoCtrl',
        templateUrl: 'todomvc-index.html'
    }).otherwise({
        redirectTo: '/'
    });
});