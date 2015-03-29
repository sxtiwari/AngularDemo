/**
 * Created by Saurabh Tiwari on 3/29/2015.
 */

var indexController = function(scopeVar, clientId){
    scopeVar.title = 'Google Single Sign On Application';
    scopeVar.clientId = clientId;
    scopeVar.googleSignInCallback = function(authResult){
        if (authResult['status']['signed_in']) {
            // Update the app to reflect a signed in user
            // Hide the sign-in button now that the user is authorized, for example:
            document.getElementById('signinButton').setAttribute('style', 'display: none');
            debug;
            console.log("You Are Signed In");
        } else {
            // Update the app to reflect a signed out user
            // Possible error values:
            //   "user_signed_out" - User is signed-out
            //   "access_denied" - User denied access to your app
            //   "immediate_failed" - Could not automatically log in the user
            debug;
            console.log('Sign-in state: ' + authResult['error']);
        }
    }
};
indexController.$inject = ['$scope', 'GOOGLE_CLIENT_ID'];
angular.module('sso').controller('indexController', indexController);
