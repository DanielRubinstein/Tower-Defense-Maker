##README 

* This utility allows one to connect to Facebook. This improved UX by integrating the game with the most popular social platform. Users can share that they're playing the game along with any relevant updates. Their profile picture can be integrated with the game as an avatar, etc.
* This utility relies heavily on the restfb API, which should be included in your library. See http://restfb.com/documentation/ for more information about the API calls and how to extend this utility to implement more features. 


#How to use
* In order to use this utility, it is first necessary that your app is registered on Facebook. This gives you an AppId, AppSecretKey and will allow your users to access your app's page. See https://developers.facebook.com/ for more details on how to set up, etc.
* This utility, through FacebookConnector, handles all the complicated details about authenticating a user login and getting their required access token, etc.
* One you have your AppId, etc. replace the default Strigs at the top of FacebookConnectorImpl and FacebookInteractorImpl with yours. 
* Note, however, that only people who are listed as "developers" of your app will have unrestricted access to this utility. For instance, this app will not be able to post on a user's facebook feed if they're not a developer UNTIL you get your app approved by Facebook.