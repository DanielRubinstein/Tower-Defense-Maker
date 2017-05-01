package frontEnd.CustomJavafxNodes.DragDrop;

import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.scene.Node;

public interface ListDragDrop<T> {

	/**
	 * Property that determines whether or not the underlying List has changed in some way. To accept this change
	 * and set this property back to false, be sure to call 
	 * @return BooleanProperty that indicates whether the List has changed.
	 */
	BooleanProperty changedListProperty();

	/**
	 * Gets the underlying list of data.
	 * @return
	 */
	List<T> getList();

	/**
	 * Accept the change of this class. By doing so, the changedProperty will be reset to false.
	 */
	void acceptChange();

	Node getRoot();

}