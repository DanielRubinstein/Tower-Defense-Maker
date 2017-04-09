package frontEnd.Skeleton;

import backEnd.Mode.ModeImpl;
/**
 * 
 * @author Tim
 * This interface will be implemented by any class that depends on the Mode of the user (e.g. Player or God Mode).
 */
public interface UserInteractor {
	/**
	* Returns the current mode the user is in
	* Possible Modes: God, Player
	*/
	public ModeImpl getCurrentMode();

}
