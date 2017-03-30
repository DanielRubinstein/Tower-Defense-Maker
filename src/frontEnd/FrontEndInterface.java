package frontEnd;

import backEnd.Environment.Mode;
/**
 * This interface is part of our API and allows other modules (i.e. the environment) to interact with our module.
 * In this way, another module is able to 
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
