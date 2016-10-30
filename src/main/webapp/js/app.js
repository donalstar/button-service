var application = angular.module('appname', [
    'service'
]);


var host_name = 'http://localhost:8080';

application.config(['$httpProvider', '$locationProvider', function ($httpProvider, $locationProvider) {

    $httpProvider.defaults.headers.common = {};
    $httpProvider.defaults.headers.post = {};
    $httpProvider.defaults.headers.put = {};
    $httpProvider.defaults.headers.patch = {};

    $locationProvider.html5Mode(true);

}]);






application.config(['$httpProvider', function ($httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    delete $httpProvider.defaults.headers.common["X-Requested-With"];
    $httpProvider.defaults.headers.common["Access-Control-Allow-Origin"] = "*";

}]);
