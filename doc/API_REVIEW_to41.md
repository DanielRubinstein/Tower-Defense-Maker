### Part 1
1. What about your API/design is intended to be flexible?

* As we are working on the front end and Game Player, our API/design is intended to be flexible in terms of adding another component or feature either 1) in the front end (like adding a new way for the user to interact with our program: e.g. a restart button) 2) in the back end and being able to display this in the front end. 


2. How is your API/design encapsulating your implementation decisions?

* So far, I think we’ve done a good job of encapsulating our implementation decisions. Instead of having a few master classes to organize the front end, we split it up into several components that work together. For instance, the bottom pane of our view (we use a borderpane) comes from a class called BottomRoot, which represents this entire region. BottomRoot contains an OptionsCorner, which contains various buttons and indicators for the user) and a BottomBar, which contains other information and ways for the user to interact. BottomRoot knows nothing about how these two parts are implemented, so we can add a new feature in OptionsCorner (which we did by adding a fast forward button) without anything changing in other classes. 
This is something we need to be very careful about, as is it very easy for a front end developer to try to do it all in one go and focus on functionality and improving the GUI without really separating the various parts. At least this was the case forme when I first started working on the front end (like in BrickBreaker) but now I have a much better understanding of how to encapsulate our components.

3. How is your part linked to other parts of the project?
* We are closely linked to the backEnd Environment, as this is what holds the state and this is where we get the necessary information to display on the front end.

* On the opposite side, our user interactions (like changing from Author to Player mode) interact with the Environment to change the player’s mode or changing the State. 
4. What exceptions (error cases) might occur in your part and how will you handle them (or not, by throwing)?

* At the moment, we do not do a lot of error checking via throwing exceptions. This is something we will implement as our program adds functionality and the user is actually able to interact with what’s on screen. Right now, we handle potential user interaction “errors” but not allowing them. For instance, if a user tried to edit rules while in player mode, which isn’t possible, we avoid this by simply disabling the button when the user is in player mode. When the cursor hovers over the button, then, a message pop ups explaining them they can’t do this in player mode.
* There are other potential errors in terms of how the user interacts with our program as we add functionality. For instance, if a user tries to place a component where it is illegal, an exception should be thrown and caught, and then handled via some indication to the user in the front end. This will be the source of most of our errors and exception handling.
5. Why do you think your API/design is good (also define what your measure of good is)?
* I think our design is good for some of the reasons mentioned above. I value flexibility and encapsulation very highly. Specifically, being able to add a feature without editing several classes is essential. Because our view is split up into so many components, this is handled well and I am confident we can implement any tools or information in the future that we will eventually need.
* However, we can do a better job using interfaces as opposed to passing classes, and this is something I am working on right now.


### Part 2

1. What feature/design problem are you most excited to work on?
* I’m most excited to work on implementing a social center, where users can send private messages to each other, perhaps connect via Facebook, etc. There is still some gray area as to what this will look like, but I envision the log in option being on the splash screen, and an avatar/username/messagebox can be displayed either on the sideBar or BottomRoot.
2. What feature/design problem are you most worried about working on?
* I am most worried about the animation of the game and the user interaction. This will undoubtedly require a lot of thinking and a lot of work, and I’m a bit worried about the performance as there will be a lot of movement and updating. I am confident we can animate the game well, but if we do end up having performance issues I’m not entirely sure how we will overcome this.

3. What major feature do you plan to implement this weekend?
* This weekend, we plan on implementing the display of the game. To start, it will be an empty grid where the user can define the size and change how it looks. Then, we plan on getting some basic animation going. Once this is done, the basis for the rest of our program will be set.
4. Discuss the use cases/issues created for your pieces: are they descriptive, appropriate, and reasonably sized?
* Our use cases are very appropriate and descriptive enough for us to understand exactly what will be going on. For instance, we have a detailed use case where the user’s player information (like health) changes during the game and in immediately portrayed in the front end. This will be done via Binding (most likely) and our use case clearly lays out how this should happen. We have similarly descriptive use cases for a scenario when the user pauses the case, etc.

5. Do you have use cases for errors that might occur?

* We don’t have a lot of use cases for errors that might occur. One that we do have, however, is when the user tries to place two towers on top of each other. The validity of a move like this is checked in the back end, and if this isn’t allowed, we give some negative feedback to the user.
* Outside of this, we don’t have many use cases where the user or some component in the backend may raise an error. However, as we begin implementing more functionality, we are confident we will have a better idea of the errors that may occur and include robust error-handling.
