var services = angular.module('service', []);

//var host_name = 'http://localhost:8080';

services.factory('userService', ['$http', function ($http) {
    return {
        get: function () {

            return $http.get(host_name + '/api/user');
        }
    }
}]);


services.factory('postService', ['$http', function ($http) {
    return {
        get: function (page_id) {

            return $http.get(host_name + '/api/post/' + page_id);
        }

    }
}]);

