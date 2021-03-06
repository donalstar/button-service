<!DOCTYPE html>


<html ng-app="appname">


<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="shortcut icon" type="image/x-icon" href="/img/favicon.png">

    <title>Button Service!</title>

    <link rel="stylesheet" type="text/css" href="/css/style.css" />

    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

    <script data-require="angular.js@1.2.x" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.4/angular.min.js"
            data-server="1.2.4"></script>

    <!-- Custom Fonts -->
    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- FB API -->
    <script src="//connect.facebook.net/en_US/all.js"></script>

    <script src="/js/app.js"></script>
    <script src="/js/services.js"></script>
    <script src="/js/controller.js"></script>
</head>


<body>

        <div ng-controller="controller">

            <div class="intro-header">

                <div class="container">

                <div class="row">

                         <div class="col-md-1">
                             <img class="logo" alt="Button Logo" src="/img/ic_button-mark-blue@2x.png"/>
                         </div>

                         <div class="col-md-9">

                             <div class="intro-message">

                                 <h1>button service!</h1>

                             </div>
                         </div>
                     </div>
                </div>


                <div class="container" ng-hide="authenticated">

                    <div class="row">
                        <div class="col-md-12">

                            <h3>authenticate yourself</h3>

                            <div class="row">


                                <div class="modal-body">
                                    <button type="button" ng-click="fb_authenticate()" class="btn btn-primary btn-lg"><i
                                            class="fa fa-facebook-square fa-fw"></i> <span
                                            class="network-name">Facebook</span>
                                    </button>
                                </div>

                            </div>

                            <hr class="intro-divider">

                        </div>
                    </div>
                </div>

            </div>

        </div>


    <!-- jQuery -->
    <script src="js/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>




</body>
</html>