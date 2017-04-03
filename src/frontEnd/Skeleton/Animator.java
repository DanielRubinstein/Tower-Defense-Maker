package frontEnd.Skeleton;

/**
 * 
 * @author Tim
 * This interface defined a method to setState, which will be called by other modules (i.e. Environment) to set the State
 * of the front end. This will be used primarily for animation purposes after a game loop has been run.
 */
public interface Animator {
	/**
	* Sets the state that will hold the information of the current game
	* For example, structure positions to be drawn
	*/
	public void setState();
}
