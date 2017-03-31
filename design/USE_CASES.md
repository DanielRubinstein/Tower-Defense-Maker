Use Cases:

Prompt: *write at least 40 use cases for your project that describe specific features you expect to complete. Focus on those features you plan to complete during the first implementation sprint, the next two weeks, (i.e., the project's core functionality, what must be written first to support the project's later features). Note, they can be similar to those given in the assignment specification, but should include more details based on your project's or genre's features and goals.*

###FrontEnd Use Cases

Use Case 1: User loads a previously saved game 
* While in-game,
	* There will be a Menubar at the top of the screen. The user will select the first menubar item (�File�). One option will be �Load�. 
* On startup,
	* There will be a splash screen with an option �Load Saved Game�
* Either
	* On selection, a FileChooser will appear that opens to a �SavedGames� directory. If a file that is not a valid XML is selected, an exception will be thrown. When the exception is caught, a dialog box will appear with the description of the error; in this case �Please select a valid saved game file�.
	* If a valid game XML is selected, the file is given to the Controller via the FrontEndControllerInterface, so that it can make the appropriate class in Data to load the given file.

Use Case 2: User loses health at some point in the game, and this change is reflected in the HUD
* When an interaction occurs that causes the user to lose health, the interaction in question deals the appropriate damage to the State, which is held in the Controller.
* The visual display of the user�s health (along with the other elements in the HUD) are dynamically linked to its respective data in the State, using either observables or listeners
* The visual health display is notified of the change in the State, and this change is immediately updated in the GUI
* The user is thus immediately able to see their change in health

Use Case 3: User pauses the game
* The user is currently in the �Player Mode : Playing� state as indicated by the Mode Indicator.
* The users presses the pause button.
* The user calls the .Pause method in the FrontEndControllerInterface that alerts the Controller (and then the Engine) to pause the game loop.
* Then the Mode Indicator updates to say �Player Mode: Paused�

Use Case 4: While creating a game, user tries to place two towers on top of each other
* User selects/picks-up tower (yet has not placed it)
* The user drags the tower over the Grid
* The user will receive visual feedback when they drag the new tower over tiles
	* Positive feedback - the tower can be placed here
		* (e.g. the tile lights up green)
		* If placed, then add the tower to grid
	* Negative feedback - the tower cannot be placed here
		* (e.g. the tie lights up red)
		* If placed, then throw exception and, when caught, produce dialog box saying �cannot place tower on that tile�
		
Use Case 5: User switches from God mode to Player mode
* The user presses the settings button in the GUI
* A command box pops up promting the user with various options
* Within this new box, the User toggles the Player mode button
* This changes the Mode of the game, and while the user is still in the settings option, the GUI behind the command box gets changed accordingly, including changing the elements on the HUD and toolbar


Use Case 6: User saves the current game while playing
* User initiates save
	* Via click
		* There will be a Menubar at the top of the screen. The user will select the first menubar item (�File�). One option will be �Save�. 
	* Via keyboard shortcut
		* using a keyboard shortcut (likely Command + �S� on Mac, Cntrl + �S� on windows), the user can select to the save the game, which the user can load at some point in the future.
* The back-end processes
	* When this occurs, the action handler makes the appropriate call in the Controller, which tells the Data Interface to save the current game�s state and rules in a data folder, which can be accessed at a later time. 

Use Case 7: User selects to build a new game from scratch
* While in-game,
	* There will be a Menubar at the top of the screen. The user will select the first menubar item (�File�). One option will be �New�. 
* On startup,
	* There will be a splash screen with an option �Create New Game�
* Either
	* On selection, a series of dialog boxes will appear over a blank View. These dialog boxes force the user to pick rules, structures and all other relevant questions to create a full game. Once all selections have been made the regular in-game View will be presented with the settings loaded and ready for God or Player mode interaction.

Use Case 8: User unpauses (plays) the game
* The user is currently in the �Player Mode : Paused� state as indicated by the Mode Indicator.
* The users presses the play button.
* The user calls the .Play method in the FrontEndControllerInterface that alerts the Controller (and then the Engine) to begin the game loop.
* Then the Mode Indicator updates to say �Player Mode: Playing�

Use Case 9:  User looks at the rules
* The user presses the settings button in the GUI
* This opens up a dialogue box containing the various options, like Load, Save, etc. including two options for Rules: View and Edit. Edit will be grayed out if the user is not in God mode
* The User clicks on the View button, and the rules of the current game are displayed (likely in another dialogue box) on screen in a user-friendly format
* The User can then read the rules and exit out when finished and resume playing the game

Use Case 10: User runs out of health and loses the game.
* Via the same mechanism in which the front end receivers user health, money, etc., the front will receive an alert that the game is over (whether the user ran out of health or some other reason)
* When this occurs, a loseGame() method is called, which launches the Settings dialogue box once again but with text �Game Over�
* The user then has the options in the settings to load a new game, create a new one, etc.

###Environment Use Cases

11. Placing a structure on the Grid in Player Mode
* .addStruct(Structure, Location) method is called, which adds the structure to the StructGrid.

12. Placing a structure on the Grid in Authoring Mode
* .addStruct(Structure, Location) method is called, which adds the structure to the StructGrid.

13. Removing a structure from the Grid in Player Mode
* .removeStruct(Structure) method is called, which removed the structure from the StructGrid. 

14. Removing a structure from the Grid in Authoring Mode
* .removeStruct(Structure) method is called, which removed the structure from the StructGrid. 

15. Change tile type in Authoring Mode
* User selects a location in the Grid, which brings up a window with the option to see/edit information about the tile at that location and the Structures at that location. The user will be able to choose from a list of Tile attributes for the Tile to change how the Tile reacts to Structures. 

16. Change Damage Behavior for a Structure
* User selects a location in the Grid with a Structure already present, which brings up a window with the option to edit the structure. User changes the existing damage behavior to a new one and the .setBehavior(DamageBehavior) method is called which replaces the existing DamageBehavior with the selected one.

17. User switches from Author to Player Mode
* User goes to the menu and selects Player Mode. The difference between these modes appearance-wise is the removal of a “level-advancement menu” button and a “game rules menu” button. The difference between these modes interaction-wise is the removal of the ability to change the rules for Structures and to change the Tile attributes.

18. User switches from Player to Author Mode
* User goes to the menu and selects Player Mode. The difference between these modes appearance-wise is the addition of a “level-advancement menu” button and a “game rules menu” button. The difference between these modes interaction-wise is the addition of the ability to change the rules for Structures and to change the Tile attributes.

19. Skeleton requests information to Save
* User hits save button and the instance of the state class within data is saved to XML using XStream along with its instance of game status and rules.

20. Skeleton gives information to Load State
* User hits load button in skeleton and chooses a different game to load. The contents of state are changed to match the information in the loaded XML file and thus the components that have instances of state are automatically updated.

21. Environment offers user to choose from list of GameRules
* When the User selects some “game rules menu”, a window pops up which has the different categories of rules (i.e. what’s a win condition, how do objects interact with each other?) and the user can choose one of these predefined rules from each category.

22. User chooses GameRule 
* When the user chooses a GameRule, that GameRule is passed to engine for it to constantly reference throughout the game.

23. Structure runs out of health and spawns next iteration of Structure
* Once a structure runs out of health, the Death ComponentEngine will run through all the Structures, check if they are “dead” and invoke their behavior if that’s true. For this Structure, the death behavior is to spawn a similar “child”. The Structure will call upon the StructureResponseController which would call Environment.removeStruct(this) and Environment.addStruct(ChildStructure, this.getLocation()).

24. User clicks structure in Player Mode
* User clicks the Structure, the View class calls Mode.onStructClick() which returns a list of potential behaviors that the Structure can have (for the Player Mode). The menu that comes up will display game-specific options for that Structure (i.e. remove, upgrade) which are stored in the InteractionBehavior interface for the Structure

25. User clicks structure in Author Mode
* User clicks the structure, the View class calls Mode.onStructClick() which returns a list of potential behaviors that the Structure can have (for the Author Mode). The menu that comes up will display all of these potential behaviors and offer a field to edit the value for that given field (i.e. health = 100, deathBehavior = “spawn child”)

# Engine Use Cases

#Use Cases
Use case #26: 
* Collision is detected between a projectile and an enemy.
* Collision handler calls `updateCollisions()` sends message to the collision engine. 
* Collision engine iterates through list of collision behaviors and calls `execute()` from behavior.
* Behavior then handles the collision appropriately and the attached listener sends update to the GUI.
Use case #27:
* Enemy runs out of health.
* Death behavior `execute()` is called.
* Behavior then updates the appropriate fields (ex. score, money).
* GUI is notified to remove the image through the listener.
Use case #28:
* Enemy runs into to turret (In PvZ type game).
* Collision handler calls `updateCollisions()`
* Enemy's `collisionBehavior` calls `execute()`
* Enemy runs turret collision behavior.
* Enemy stops and begins to damage turret.
* Turret's `collisionBehavior` calls `execute()`
* GUI is notified that enemy is attacking.
Use case #29:
* Enemy is on path and progresses.
* Move engine iterates through all components to invoke the move behavior `execute()` methods.
* Internal position attribute of component is updated by `execute`.
* Listeners attached to position in GUI update automatically.
Use case #30:
* Enemy moves in turret range.
* Range detector `execute()` behavior is run.
* Turret runs shoot behavior's `execute()` on enemy in range
* Projectile spawns and is added to the GUI through the shoot behavior
Use case #31:
* User adds an Attribute to a tower
* A new class implementing `Attribute` is constructed in `GameProcessController` and added to the List of attributes in the `Component`
* SetValue is called on the `Attribute` to fill in its value (HP, etc.)
Use case #32:
* User adds a `Behavior` to a tower
* A new class implementing `Behavior's execute()` in the desired fashion is constructed in `GameProcessController` and added to the `List` of behaviors in the `Component`
* The attributes that this `Behavior` will act on are added to this behavior's List of attributes
Use case #33:
The user sells a tower
* The GameProcessController tells the `sellEngine` to call the 'Sell' `Behavior` contained in the chosen tower 
* The Sell behavior calls `setGold()` to give the player the refund amount
* The Sell behavior removes the tower from the `State` by setting itself to null
Use case #34:
The level is cleared
* Controller directs `winConditionEngine` to perform its check, and it sees that its winCondition is met (balloon queue is empty, for example)
* winConditionEngine returns `True` to the Controller's `isLevelComplete()`, which then calls the level progression chain, clearing the `State` and replacing it with the next `State` in the Level queue
Use case #35:
A balloon reaches a base tile
* The collisionEngine detects that two Components occupy the same tile
* The collisionEngine calls collison behavior's execute() on each of the two Components
* The balloon's collision behavior's execute says to destroy the balloon
* The base's collision behavior's execute says to lose one HP
* User case #36
Object dies and creates a new object next to it
Engine's gameLoop() runs and runs the DeathEngine(). Death Engine check's the Component's health attribute, and if it is equal to 0, it detects that the object has died. It then checks the Component's onDeath attribute, which is this case is equal to SPAWN_SPRITE_NEXT. DeathEngine has a switch case that has a different action for each possible onDeath attribute.  
Use case #37
Object is upgraded
UpgradeEngine() is run in the gameLoop(). If Component's upgradeAttribute==true, then the Component's UpgradeBehavior modifies the object's relevant attributes.
Use case #38
Tower fires a projectile
FireEngine detects that a component needs to fire at another component by looking at the component's FireAt attribute which tells us which types of components that component fires at and then looping through the surrounding cells of the grid to see if there are any objects that that object should fire at.
If so, FireEngine sets a bullet on the grid next to the firing object in a way that points towards the object fired at. We then treat the bullet hitting the object like a Collision (using CollisionEngine).
Use case #39
update the score
Everytime the DeathEngine causes a component to die, we check the Objects onDeathPoints Attribute and add that to the score (contained in engine).
Use case #40
drag an object in the middle of a game and drop it in a new location
Front-end updates the state object in the main Controller. The GameProcessController observes that State (using observer/ observables), so when it changes, the Engine will automatically have access to State (and therefore the latest grid).

