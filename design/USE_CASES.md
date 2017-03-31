Use Cases:

Prompt: *write at least 40 use cases for your project that describe specific features you expect to complete. Focus on those features you plan to complete during the first implementation sprint, the next two weeks, (i.e., the project's core functionality, what must be written first to support the project's later features). Note, they can be similar to those given in the assignment specification, but should include more details based on your project's or genre's features and goals.*

#FrontEnd Use Cases

Use Case 1: User loads a previously saved game 
* While in-game,
	* There will be a Menubar at the top of the screen. The user will select the first menubar item (“File”). One option will be “Load”. 
* On startup,
	* There will be a splash screen with an option “Load Saved Game”
* Either
	* On selection, a FileChooser will appear that opens to a “SavedGames” directory. If a file that is not a valid XML is selected, an exception will be thrown. When the exception is caught, a dialog box will appear with the description of the error; in this case “Please select a valid saved game file”.
	* If a valid game XML is selected, the file is given to the Controller via the FrontEndControllerInterface, so that it can make the appropriate class in Data to load the given file.

Use Case 2: User loses health at some point in the game, and this change is reflected in the HUD
* When an interaction occurs that causes the user to lose health, the interaction in question deals the appropriate damage to the State, which is held in the Controller.
* The visual display of the user’s health (along with the other elements in the HUD) are dynamically linked to its respective data in the State, using either observables or listeners
* The visual health display is notified of the change in the State, and this change is immediately updated in the GUI
* The user is thus immediately able to see their change in health

Use Case 3: User pauses the game
* The user is currently in the “Player Mode : Playing” state as indicated by the Mode Indicator.
* The users presses the pause button.
* The user calls the .Pause method in the FrontEndControllerInterface that alerts the Controller (and then the Engine) to pause the game loop.
* Then the Mode Indicator updates to say “Player Mode: Paused”

Use Case 4: While creating a game, user tries to place two towers on top of each other
* User selects/picks-up tower (yet has not placed it)
* The user drags the tower over the Grid
* The user will receive visual feedback when they drag the new tower over tiles
	* Positive feedback - the tower can be placed here
		* (e.g. the tile lights up green)
		* If placed, then add the tower to grid
	* Negative feedback - the tower cannot be placed here
		* (e.g. the tie lights up red)
		* If placed, then throw exception and, when caught, produce dialog box saying “cannot place tower on that tile”
		
Use Case 5: User switches from God mode to Player mode
* The user presses the settings button in the GUI
* A command box pops up promting the user with various options
* Within this new box, the User toggles the Player mode button
* This changes the Mode of the game, and while the user is still in the settings option, the GUI behind the command box gets changed accordingly, including changing the elements on the HUD and toolbar


Use Case 6: User saves the current game while playing
* User initiates save
	* Via click
		* There will be a Menubar at the top of the screen. The user will select the first menubar item (“File”). One option will be “Save”. 
	* Via keyboard shortcut
		* using a keyboard shortcut (likely Command + ‘S’ on Mac, Cntrl + ‘S’ on windows), the user can select to the save the game, which the user can load at some point in the future.
* The back-end processes
	* When this occurs, the action handler makes the appropriate call in the Controller, which tells the Data Interface to save the current game’s state and rules in a data folder, which can be accessed at a later time. 

Use Case 7: User selects to build a new game from scratch
* While in-game,
	* There will be a Menubar at the top of the screen. The user will select the first menubar item (“File”). One option will be “New”. 
* On startup,
	* There will be a splash screen with an option “Create New Game”
* Either
	* On selection, a series of dialog boxes will appear over a blank View. These dialog boxes force the user to pick rules, structures and all other relevant questions to create a full game. Once all selections have been made the regular in-game View will be presented with the settings loaded and ready for God or Player mode interaction.

Use Case 8: User unpauses (plays) the game
* The user is currently in the “Player Mode : Paused” state as indicated by the Mode Indicator.
* The users presses the play button.
* The user calls the .Play method in the FrontEndControllerInterface that alerts the Controller (and then the Engine) to begin the game loop.
* Then the Mode Indicator updates to say “Player Mode: Playing”

Use Case 9:  User looks at the rules
* The user presses the settings button in the GUI
* This opens up a dialogue box containing the various options, like Load, Save, etc. including two options for Rules: View and Edit. Edit will be grayed out if the user is not in God mode
* The User clicks on the View button, and the rules of the current game are displayed (likely in another dialogue box) on screen in a user-friendly format
* The User can then read the rules and exit out when finished and resume playing the game

Use Case 10: User runs out of health and loses the game.
* Via the same mechanism in which the front end receivers user health, money, etc., the front will receive an alert that the game is over (whether the user ran out of health or some other reason)
* When this occurs, a loseGame() method is called, which launches the Settings dialogue box once again but with text “Game Over”
* The user then has the options in the settings to load a new game, create a new one, etc.

