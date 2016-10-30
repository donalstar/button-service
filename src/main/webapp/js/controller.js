application.controller('controller', ['$scope', '$rootScope', '$location', '$window',
    'userService', 'postService',

    function ($scope, $rootScope, $location, $window, userService, postService) {

        $scope.fb_authenticate = function() {
            $window.location.replace("/social/facebook/signin");
        };

        $scope.code = $location.search().code;

        $scope.authenticated = $location.search().authx;

        $scope.getTasks = function () {
            $rootScope.processing = true;

            console.log("get tasks");

        };

        $scope.getPosts = function () {
            $rootScope.processing = true;

            console.log("get posts");

            $scope.page_id = 0;

            if ($scope.authenticated == 'true') {

                 postService.get($scope.page_id)
                     .success(function (data) {
                        console.log("Got posts " + data.posts);

                        $scope.posts = data.posts;
                        $scope.post_page_id = data.pageId;
                        $scope.post_page_count = data.pageCount;

                     })
                     .error(function (error) {
                         $rootScope.processing = false;

                         console.log(":Error  " + error);
                  });
            }

        };

                $scope.getPosts1 = function () {
                    $rootScope.processing = true;

                    console.log("get posts1");

                    $scope.page_id = 1;

                    if ($scope.authenticated == 'true') {

                         postService.get($scope.page_id)
                             .success(function (data) {
                                console.log("Got posts (1)" + data);

                             })
                             .error(function (error) {
                                 $rootScope.processing = false;

                                 console.log(":Error  " + error);
                          });
                    }

                };


        $scope.getUser = function () {
            $rootScope.processing = true;

            if ($scope.authenticated == 'true') {

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

