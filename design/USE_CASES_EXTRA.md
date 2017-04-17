1. In author mode, user creates a new component from scratch and saves it as a preset

* When the user presses the "plus" button on the bottom and if component is selected, this launches a command center where the user can edit attributes of that component. 
* After setting the desired values, the user selects the "Save as Preset Option", which sends an invokable through the View
* This creates this component in the bank in the back end
* This bottom bar now contains an image of this component and all attributes are saved

2. In author mode, user adds preset component to the screen.

* One a preset has been made, the user sees it in the bottom root.
* The user can click and drag this preset onto the canvas and drop it wherever
* When this happens (and if the tile is a valid tile that can hold components), another invokable method is called and invoked through the view
* This adds this component to the ComponentGraph in the back end, which notifies the Canvas of this change
* The Canvas then retrieves the component it did not already have, and creates a FrontEndAttributeOwner object to represent this component
* This then gets immediately placed on screen.

3. User clicks on a tile in author mode


* When this happens, the lambda expression to launch a new instance of tile command center is called
* This launches a new window containing information about all the attributes of the tile
* In player mode, the user can only see this information
* in author mode, the user can edit these attributes. This editing is again done through invokables
* There are also various tabs if the tile has any components on it

4. In player mode, user drags a component to use on screen.
 

* This is what will happen when the game is fully implemented
* This is similar to how an author can add components
* However, now there may be restrictions to which components the user can drag and drop
* The drag and drop process calls an invokable which adds a component to the componentGraph in the back end.
* This component is immediately on screen and can interact with the rest of the game.

5. In player mode, user clicks on a tile

* The Player menu for the Tile appears, which allows the user to purchase a Component such as a Tower to place on the Tile. 

6. In author mode, user drags a preset tile and drops it on the canvas.



* Once tile presets have been saved, the user can find these in the "Tile" tab of the bottom bar
* The user can drag and drop these onto the canvas
* This effectively replaces the previous tile with the new tile
* What this really means for the user is that the tile has been replaced by another one with different attributes
* This calls an invokable through the View, which interacts with TileGrid

7. In author mode, user clicks on a component and changes its location.


* When a component is clicked in author mode, a command center pops up where the attributes can be changed
* IF the user clicks and enters a new position for the component, this calls an invokable through the view which interacts with ComponentGraph
* The corresponding component's position attribute is changed, and when this happens the canvas is notified (via observable pattern) and this change is reflected in the UI


8. In author mode, user clicks on a tile and changes its image.


* In this scenario, the user clicks on a button to select a new image.
* From the directory, the user can select which image he wants to replace the one of the tile (can only select valid image types).
* This allows the user to improve the aesthetics of the game and group certain tiles together (like all traversable tiles are green)
* This calls an invokable to edit the tile's image attribute along with the String of the new path, and the front end gets updated immediately.
* However, it is not the canvas that gets notified. Rather, the FrontEndAttributeOwner is "tied" to a corresponding back end class (Either tile or component) and when the back end class is changed, the FrontEndAttributeOwner finds this change and changes accordingly, and the canvas is updated automatically.


9. In 


10. A projectile is fired (revised from v1.0)

* AttackEngine sees that a turret is a Component of type Turret, and so runs its execute() on the turret. The turret generates a Component of type Projectile and selects a Component near it with the Enemy label to fire at. The projectile then moves to the target component and performs its specified behavior upon collision.

11. The user clicks "Save" in the UI

* The current GameData (containing State, Rules, and UserData) is bundled together and written to XML through XMLWriter (XStream). Then, this file is saved to the specified location, where it can be read in to reload the game.

12. A Component's Attribute is modified through the UI

* An invokable object is created in the frontend, which is passed as a lambda to the Environment. The environment runs this lambda to perform the action specified in the invokable upon the Component's attributes. This triggers an Observer to modify the Component in State to reflect the change.

13. In Player Mode, the user attempts to add a new Component to the TileGrid

* The Mode check is performed, which returns Player instead of the required Author. The sequence of method calls to add the Component then terminates, as this action is not permitted in Player mode.

14. The user attempts to set a path through the Grid that is not contiguous
* The path checking algorithm sees that there is no way to path from a start tile to an end tile, and displays an error popup detailing the issue and asking the user to define a correct path. 

15. Enemy dies leaving a coin. 

* Coin is clicked and its setOnAction() causes it to disappear and add money to the player's current total.

16. Player clicks on turret. 

* A display for the turret pops up and allows player to change turrets target preferences. Changes attribute targetPreference via setAttribute().

17. Player purchases and places a bomb object (or road spikes, etc.). 

* After small time the item engine creates explode behavior that calls execute() which damages all the surrounding enemies.

18. Player creates new custom component. 

* This leads to creation of componentBuilder which then uses getComponent() to create a component with the correct attributes.

19. Player deletes tower. 
* Tower is selected by player and tower attributes appear. Player presses delete which removes the instance from the game using remove().