var services = angular.module('service', []);

services.factory('userService', ['$http', function ($http) {
    return {
        get: function () {

            return $http.get('http://localhost:8080/api/user');
        }

    }
}]);
