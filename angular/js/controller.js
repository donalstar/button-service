application.controller('controller', ['$scope', '$rootScope', '$location', '$window', 'facebookService', 'taskService',
    'userService', 'postService',

    function ($scope, $rootScope, $location, $window, facebookService, taskService, userService, postService) {


        $scope.fb_client_id = "605705216297822";
        $scope.fb_redirect_uri = "http://localhost:8000/index2.html";

        $window.fbAsyncInit = function () {
            FB.init({
                appId: $scope.fb_client_id,
                status: true,
                cookie: true,
                xfbml: true,
                version: 'v2.5'
            });
        };

        $scope.code = $location.search().code;

        // $scope.login = function () {
        //     console.log("login");
        //
        //     $scope.auth = true;
        //     $rootScope.authenticated = true;
        //
        //     FB.login(function(){
        //         // Note: The call will only work if you accept the permission request
        //         // FB.api('/me/feed', 'post', {message: 'Hello, world!'});
        //
        //         console.log("FB LOGIN GOOD");
        //
        //         $rootScope.authenticated = true;
        //         $scope.auth = true;
        //
        //         FB.getLoginStatus(function(response) {
        //             if (response.status === 'connected') {
        //                 console.log(response.authResponse.accessToken);
        //
        //                 $scope.code = response.authResponse.accessToken;
        //             }
        //         });
        //     }, {scope: 'publish_actions'});
        // };

        $scope.login = function () {
            $rootScope.processing = true;

            facebookService.login()
                .then(function (response) {
                        $scope.code = response.authResponse.accessToken;

                        $scope.getName();
                    }
                );
        };

        $scope.getName = function () {

            facebookService.getName()
                .then(function (response) {
                        $scope.first_name = response.first_name;
                        $scope.last_name = response.last_name;

                        $rootScope.authenticated = true;
                        $rootScope.processing = false;
                    }
                );
        };

        $scope.getTasks = function () {
            $rootScope.processing = true;

            console.log("get tasks");

            taskService.get("1")
                .success(function (data) {
                    $rootScope.processing = false;


                    if (data.code == 0) {

                        $rootScope.processSuccess = true;

                        console.log("Tasks get successful!");

                        $rootScope.tasks = data.tasks;
                    }
                })
                .error(function (error) {
                    $rootScope.processing = false;

                    console.log(":Error  " + error);
                });
        };

        $scope.getPosts = function () {
            $rootScope.processing = true;

            console.log("get posts");

            postService.get($scope.code)
                .success(function (data) {
                    $rootScope.processing = false;
                    $rootScope.processPostsSuccess = true;

                    $rootScope.posts = data;
                })
                .error(function (error) {
                    $rootScope.processing = false;

                    console.log(":Error  " + error);
                });
        };


        $scope.getUser = function () {
            $rootScope.processing = true;

            console.log("get user - use code " + $scope.code);

            userService.get($scope.code)
                .success(function (data) {
                    $rootScope.processing = false;
                    $rootScope.processUserSuccess = true;

                    $rootScope.user = data;
                })
                .error(function (error) {
                    $rootScope.processing = false;

                    console.log(":Error  " + error);
                });
        };

    }])
;

