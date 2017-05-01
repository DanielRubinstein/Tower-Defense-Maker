package frontEnd.Skeleton.ScreenGrid;

import java.util.function.Consumer;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

public interface GridInteractor<T> {

	void setGridInteraction(Pane p);

	/**
	 * Sets the interaction for Tiles. Specifically, this determines what happens when an Object is clicked on.
	 * If the Object is double clicked, its CommandCenter is launched. If it is clicked while the Control Key is
	 * pushed down, it is "selected". Multiple tiles can be selected, and if one presses an arrow key, this changes
	 * the MoveDirection for all of them. If one adds a Preset Object to one of this, this changes all of them.
	 * @param n Node representing the object visually on screen.
	 * @param t Object that will be interacted with.
	 */
	void setInteraction(Node n, T t);

	void clearSelection();

	/**
	 * Performs the Consumer on every object. 
	 * @param method
	 */
	void forEachSelected(Consumer<? super T> method);

}