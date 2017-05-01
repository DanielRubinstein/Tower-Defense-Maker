package frontEnd.CustomJavafxNodes.DragDrop;

import java.util.List;
import java.util.stream.Collectors;

import frontEnd.Skeleton.UserTools.SkeletonObject;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;

/**
 * This class allows for the functionality of dragging and dropping items in a list.
 * When an item is dropped on another item, their positions in the List swap. 
 * @author Tim, Miguel
 *
 * @param <T>
 */
public class ListDragDropImpl<T> implements SkeletonObject, ListDragDropChanger<T>, ListDragDrop<T> {

	private ListView<T> myContents;
	private ScrollPane myRoot;
	private BooleanProperty changedListProperty;
	
	public ListDragDropImpl(ObservableList<T> toAdd){
		changedListProperty = new SimpleBooleanProperty();
		myContents = new ListView<T>(toAdd);
		myContents.setItems(toAdd);
		myContents.setCellFactory(e -> new CellDragDrop<T>(myContents,this));
		myRoot = new ScrollPane();
		myRoot.setContent(myContents);
		myRoot.setVbarPolicy(ScrollBarPolicy.ALWAYS);
	}
	/* (non-Javadoc)
	 * @see frontEnd.CustomJavafxNodes.DragDrop.ListDragDrop#changedListProperty()
	 */
	@Override
	public BooleanProperty changedListProperty(){
		return changedListProperty;
	}
	/* (non-Javadoc)
	 * @see frontEnd.CustomJavafxNodes.DragDrop.ListDragDrop#getList()
	 */
	@Override
	public List<T> getList() {
		return myContents.getItems().stream().collect(Collectors.toList());
	}
	/* (non-Javadoc)
	 * @see frontEnd.CustomJavafxNodes.DragDrop.ListDragDrop#acceptChange()
	 */
	@Override
	public void acceptChange(){
		changedListProperty.set(false);
	}

	/* (non-Javadoc)
	 * @see frontEnd.CustomJavafxNodes.DragDrop.ListDragDrop#getRoot()
	 */
	@Override
	public Node getRoot() {
		return myRoot;
	}

	@Override
	public void setChanged() {
		changedListProperty.set(true);
		
	}


}
