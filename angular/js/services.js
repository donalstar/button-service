var services = angular.module('service', []);

services.factory('facebookService', function ($q) {
    return {

        login: function () {
            var deferred = $q.defer();

            FB.login(function () {

                FB.getLoginStatus(function(response) {
                    if (response.status === 'connected') {
                        console.log(response.authResponse.accessToken);

                        deferred.resolve(response);
                    }
                });
                
                
             //   deferred.resolve();
            }, {scope: 'publish_actions'});
            
            return deferred.promise;
        },

        getName: function () {

            var deferred = $q.defer();
            FB.api('/me', {
                fields: 'first_name, last_name'
            }, function (response) {
                if (!response || response.error) {
                    deferred.reject('Error occured');
                } else {
                    deferred.resolve(response);
                }
            });
            return deferred.promise;
        }

    }
});


services.factory('taskService', ['$http', function ($http) {
    return {
        get: function (username) {
            return $http.get('http://localhost:8080/task?user_id=' + username);
        }


    }
}]);


services.factory('userService', ['$http', function ($http) {
    return {
        get: function (code) {

            console.log("SCOPE CODE " + code);

            return $http.get('http://localhost:8080/user?code=' + code);
        }


    }
}]);

services.factory('postService', ['$http', function ($http) {
    return {
        get: function (code) {

            console.log("SCOPE CODE " + code);

            return $http.get('http://localhost:8080/post?code=' + code);
        }


    }
}]);