package frontEnd.CustomJavafxNodes.DragDrop;

import javafx.beans.property.BooleanProperty;

public interface ListDragDropChanger<T> {

	/**
	 * Sets the Boolean ChangedProperty to true
	 */
	public void setChanged();

}