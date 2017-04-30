package frontEnd.CustomJavafxNodes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import frontEnd.Skeleton.UserTools.SkeletonObject;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.SplitPane;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ListDragDrop<T> implements SkeletonObject {

	private ListView<T> myContents;
	private ScrollPane myRoot;
	
	public ListDragDrop(ObservableList<T> toAdd){
		myContents = new ListView<T>(toAdd);
		myContents.setItems(toAdd);
		myContents.setCellFactory(e -> new TCell(myContents));


		VBox myBox = new VBox();
		toAdd.forEach(e -> {
			SplitPane wrapper = new SplitPane();
			Text wrap = new Text(e.toString());
			//wrapper.getItems().add(e);
			myBox.getChildren().add(wrap);
			wrap.setOnDragDetected(f -> {
				Dragboard dragboard = wrap.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putString(wrap.getText());
                dragboard.setContent(content);
			});
			wrap.setOnDragOver(f -> {
				f.acceptTransferModes(TransferMode.ANY);
			});
			
			wrap.setOnDragDropped(f -> {
				f.consume();
			});
		});
		
		
		myRoot = new ScrollPane();
		myRoot.setContent(myContents);
		myRoot.setVbarPolicy(ScrollBarPolicy.ALWAYS);
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
				}
				e.consume();
	
			});
			
		}
		@Override 
		protected void updateItem(T item, boolean empty){
			super.updateItem(item, empty);
			System.out.println( " my update item " + item + "  " + empty);
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

}
