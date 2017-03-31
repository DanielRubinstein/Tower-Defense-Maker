package frontEnd;

import backEnd.Environment.Mode;
/**
 * This interface is part of our API and allows other modules (i.e. the environment) to interact with our module.
 * This defines methods that will be part of the front end that anyone can access.
 * @author Tim
 *
 */
public interface FrontEndInterface {
	/**
	* Returns the current mode the user is in
	* Possible Modes: God, Player
	*/
	public Mode getCurrentMode();

	/**
	* Sets the state that will hold the information of the current game
	* For example, structure positions to be drawn
	*/
	public void setState();

}
