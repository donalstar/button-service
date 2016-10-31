var services = angular.module('service', []);

services.factory('userService', ['$http', function ($http) {
    return {
        get: function () {

            return $http.get(host_name + '/api/user');
        }
    }
}]);


services.factory('postService', ['$http', function ($http) {
    return {
        get: function (search_date, hashtag) {

            console.log("#tag =  " + hashtag);
            url = host_name + '/api/post/';

            if (search_date != null) {
                url += "?date=" + search_date;
            }
            else if (hashtag != null) {
                url += "?hashtag=" + hashtag;
            }

            console.log("Svc url " + url);

            return $http.get(url);
        }

    }
}]);

