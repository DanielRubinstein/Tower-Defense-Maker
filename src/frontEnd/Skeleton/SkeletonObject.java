package frontEnd.Skeleton;

import javafx.scene.Node;
/**
 * An interface to define anything that is part of the skeleton and being portrayed on screen.
 * @author Tim
 *
 */
public interface SkeletonObject {

	/**
	 * Offers the node that can be added to other nodes and displayed on the screen
	 * @return
	 */
	public Node getRoot();
}
