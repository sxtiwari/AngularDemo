angular.module('sso').config([
   '$routeProvider',
    function(a){
        a
        .when('/',{
            templateUrl: 'index.html',
            controller: 'indexController'
        })
        .otherwise({
                redirectTo: '/'
        });
    }
]);