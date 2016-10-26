var application = angular.module('appname', [
    'service'
]);




application.config(['$httpProvider', '$locationProvider', function ($httpProvider, $locationProvider) {

    $httpProvider.defaults.headers.common = {};
    $httpProvider.defaults.headers.post = {};
    $httpProvider.defaults.headers.put = {};
    $httpProvider.defaults.headers.patch = {};

    $locationProvider.html5Mode(true);

}]);





