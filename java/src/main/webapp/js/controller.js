application.controller('controller', ['$scope', '$rootScope', '$location', '$window',
    'userService',

    function ($scope, $rootScope, $location, $window, userService) {

        $scope.fb_authenticate = function() {
            $window.location.replace("/social/facebook/signin");
        };

        $scope.code = $location.search().code;

        $scope.authx = $location.search().authx;

        $scope.getTasks = function () {
            $rootScope.processing = true;

            console.log("get tasks");

        };

        $scope.getPosts = function () {
            $rootScope.processing = true;

            console.log("get posts");


        };


        $scope.getUser = function () {
            $rootScope.processing = true;

            if (typeof $scope.authx != 'undefined') {

                 userService.get()
                     .success(function (data) {
                         $rootScope.processing = false;
                         $rootScope.processUserSuccess = true;

                         $rootScope.user = data;

                     })
                     .error(function (error) {
                         $rootScope.processing = false;

                         console.log(":Error  " + error);
                  });
            }


        };

        $scope.getUser();

    }])
;

