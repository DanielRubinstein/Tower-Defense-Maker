package frontEnd.CustomJavafxNodes.DragDrop;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

/**
 * This class represents the actual cells of data used by ListDragDrop.
 * @author Tim
 *
 * @param <T>
 */
public class CellDragDrop<T> extends ListCell<T>{

	private T myData;
	private int myIndex;
	private Map<String,T> stringItemMap;
	private ListView<T> myContents;
	private ListDragDropChanger<T> myParentList;

	public CellDragDrop(ListView<T> contents, ListDragDropChanger<T> parentList){
		myParentList = parentList;
		stringItemMap = new HashMap<>();
		makeContents(contents);
		setDragInteractions();
	}
	private void makeContents(ListView<T> contents){
		myContents = contents;
		myContents.getItems().forEach(e -> stringItemMap.put(e.toString(), e));
		myIndex = myContents.getItems().indexOf(myData);
		this.setContentDisplay(ContentDisplay.CENTER);
		myData = this.getItem();
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
				myParentList.setChanged();
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
