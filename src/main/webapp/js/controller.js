application.controller('controller', ['$scope', '$rootScope', '$location', '$window',
    'userService', 'postService',

    function ($scope, $rootScope, $location, $window, userService, postService) {

        $rootScope.processSuccess = false;
        $rootScope.processing = false;


        $scope.fb_authenticate = function() {
            $window.location.replace("/social/facebook/signin");
        };

        $scope.code = $location.search().code;

        $scope.authenticated = $location.search().authx;

         $scope.getAllPosts = function () {
             $scope.getPosts(null, null);
         };

        $scope.getPostsForDate = function () {
            $scope.getPosts($scope.search_date, null);
        };

        $scope.getPostsForHashtag = function () {
            $scope.getPosts(null, $scope.hashtag);
        };

        $scope.getPosts = function (date, hashtag) {

                 $rootScope.processing = true;

                 if ($scope.authenticated == 'true') {

                      postService.get(date, hashtag)
                          .success(function (data) {
                             $rootScope.processing = false;
                             $rootScope.processSuccess = true;

                             console.log("Got posts " + data.posts);

                             // ugh!!

                             if (typeof data.posts != 'undefined') {
                                if (data.posts.constructor === Array) {
                                    $scope.posts = data.posts;
                                }
                                else {
                                   console.log("just one " + data.posts)

                                   result = [ data.posts ];
                                   $scope.posts = result;
                                }
                             }
                             else {
                                $scope.posts = [];
                             }
                          })
                          .error(function (error) {
                              $rootScope.processing = false;

                              console.log(":Error  " + error);
                       });
                 }

             };


        // Get User
        $scope.getUser = function () {

            if ($scope.authenticated == 'true') {

                 userService.get()
                     .success(function (data) {
                         $rootScope.user = data;

                     })
                     .error(function (error) {
                         console.log(":Error  " + error);
                  });
            }
        };

        $scope.getUser();

    }])
;

