application.controller('controller', ['$scope', '$location', '$window', 'facebookService',

    function ($scope, $location, $window, facebookService) {

        $scope.fb_client_id = "401909753266103";
        $scope.fb_redirect_uri = "http://localhost:8000";

        $window.fbAsyncInit = function () {
            FB.init({
                appId: $scope.fb_client_id,
                status: true,
                cookie: true,
                xfbml: true,
                version: 'v2.5'
            });
        };

        $scope.getMyLastName = function () {

            facebookService.getMyLastName()
                .then(function (response) {
                        $scope.first_name = response.first_name;
                        $scope.last_name = response.last_name;
                    }
                );
        };

        $scope.code = $location.search().code;
    }])
;

