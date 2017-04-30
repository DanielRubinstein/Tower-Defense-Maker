package frontEnd.CustomJavafxNodes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import frontEnd.Skeleton.UserTools.SkeletonObject;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class ListDragDrop<T> implements SkeletonObject, ObservableValue<T> {

	private ListView<T> myContents;
	private ScrollPane myRoot;
	private BooleanProperty changedListProperty;
	
	public ListDragDrop(ObservableList<T> toAdd){
		changedListProperty = new SimpleBooleanProperty();
		myContents = new ListView<T>(toAdd);
		myContents.setItems(toAdd);
		myContents.setCellFactory(e -> new TCell(myContents));
		myRoot = new ScrollPane();
		myRoot.setContent(myContents);
		myRoot.setVbarPolicy(ScrollBarPolicy.ALWAYS);
	}
	public BooleanProperty changedListProperty(){
		return changedListProperty;
	}
	public List<T> getList() {
		return myContents.getItems().stream().collect(Collectors.toList());
	}
	public void acceptChange(){
		changedListProperty.set(false);
	}
	

	@Override
	public Node getRoot() {
		return myRoot;
	}
	
	private class TCell extends ListCell<T> {
		
		private T myData;
		private int myIndex;
		private Map<String,T> stringItemMap;

		public TCell(ListView<T> allItems){
			
			stringItemMap = new HashMap<>();
			myContents.getItems().forEach(e -> stringItemMap.put(e.toString(), e));
			myIndex = myContents.getItems().indexOf(myData);
			this.setContentDisplay(ContentDisplay.CENTER);
			myData = this.getItem();
			setDragInteractions();
		}
		private void setDragInteractions() {
			this.setOnDragDetected(e -> {
				if(myData==null) return;
				Dragboard dragboard = startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putString(myData.toString());
                dragboard.setContent(content);
			});
			this.setOnDragOver(e -> {
				if(e.getDragboard().hasString() && myData!=null){
					e.acceptTransferModes(TransferMode.ANY);
				}

			});
			this.setOnDragDropped(e -> {
				Dragboard board = e.getDragboard();
				if(board.hasString()){
					String draggedString = board.getString();
					T draggedItem = stringItemMap.get(draggedString);
					int draggedIndex = myContents.getItems().indexOf(draggedItem);
					
					T temp = draggedItem;
					myContents.getItems().set(draggedIndex, myData);
					myContents.getItems().set(myIndex, temp);
					changedListProperty.set(true);
				}
				e.consume();
			});
			
		}
		@Override 
		protected void updateItem(T item, boolean empty){
			super.updateItem(item, empty);
		     if (empty || item == null) {
		         setText(null);
		         setGraphic(null);
		     } else {
		    	 this.setItem(item);
		    	 myData = item;
		    	 myIndex = this.getIndex();
		         this.setText(item.toString());
		     }
		}
	}

	@Override
	public void addListener(InvalidationListener listener) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void removeListener(InvalidationListener listener) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void addListener(ChangeListener<? super T> listener) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void removeListener(ChangeListener<? super T> listener) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public T getValue() {
		// TODO Auto-generated method stub
		return null;
	}



}
