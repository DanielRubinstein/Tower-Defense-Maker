## README 

* This utility allows one to connect to Facebook. This improves UX by integrating the game with the most popular social platform. Users can share that they're playing the game along with any relevant updates. Their profile picture can be integrated with the game as an avatar, etc.
* This utility relies heavily on the restfb API, which should be included in your library. See http://restfb.com/documentation/ for more information about the API calls and how to extend this utility to implement more features. 


# How to use
* In order to use this utility, it is first necessary that your app is registered on Facebook. This gives you an AppId, AppSecretKey and will allow your users to access your app's page. See https://developers.facebook.com/ for more details on how to set up, etc.
* The AppId and AppSecretKey need to be passed in when initializing FacebookConnectorImpl.
* Add the jar file to your lib folder in your program.
* Add the file "facebook.resources" to your resources package.
* This utility, through FacebookConnector, handles all the complicated details about authenticating a user login and getting their required access token, etc.
* Note, however, that only people who are listed as "developers" of your app will have unrestricted access to this utility. For instance, this app will not be able to post on a user's facebook feed if they're not a developer UNTIL you get your app approved by Facebook. However, you are able to retrieve their profile picture to use as their avatar and if you are the developer of the app, you can post on your own wall.