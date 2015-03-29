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
            console.log("You Are Signed In");
        } else {
            // Update the app to reflect a signed out user
            // Possible error values:
            //   "user_signed_out" - User is signed-out
            //   "access_denied" - User denied access to your app
            //   "immediate_failed" - Could not automatically log in the user
            console.log('Sign-in state: ' + authResult['error']);
        }
    }

    scopeVar.renderSignInButton = function() {
        gapi.signin.render('signinButton',
            {
                'callback': scopeVar.googleSignInCallback, // Function handling the callback.
                'clientid': clientId, // CLIENT_ID from developer console which has been explained earlier.
                'requestvisibleactions': 'http://schemas.google.com/AddActivity', // Visible actions, scope and cookie policy wont be described now,
                // as their explanation is available in Google+ API Documentation.
                'scope': 'https://www.googleapis.com/auth/plus.login https://www.googleapis.com/auth/userinfo.email',
                'cookiepolicy': 'single_host_origin'
            }
        );
    }

    // Start function in this example only renders the sign in button.
    scopeVar.start = function() {
        scopeVar.renderSignInButton();
    };

    // Call start function on load.
    scopeVar.start();
};
indexController.$inject = ['$scope', 'GOOGLE_CLIENT_ID'];
angular.module('sso').controller('indexController', indexController);
