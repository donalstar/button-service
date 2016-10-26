var services = angular.module('service', []);

services.factory('facebookService', function($q) {
    return {
        getMyLastName: function() {
      
            var deferred = $q.defer();
            FB.api('/me', {
                fields: 'first_name, last_name'
            }, function(response) {
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

