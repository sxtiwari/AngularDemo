angular.module('sso').config([
   '$routeProvider',
    function(a){
        a
        .when('/home',{
            templateUrl: 'index.html',
            controller: 'afnHomeController'
        })
        .otherwise({
                redirectTo: '/home'
        });
    }
]);