package frontEnd.Skeleton.SpawnTimelineVisualization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import frontEnd.View;
import frontEnd.Skeleton.UserTools.SkeletonObject;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Alert.AlertType;

public class SpawnTabPane implements SkeletonObject{
	private TabPane myRoot;
	private Collection<Integer> takenIDs;
	private View myView;
	
	
	public SpawnTabPane(View view){
		myView = view;
		myRoot = new TabPane();
		createAddTab();
	}
	
	private void createAddTab() {
		Tab tabAdd = new Tab("+");
		tabAdd.setClosable(false);
		tabAdd.setOnSelectionChanged((e) -> {
			if(tabAdd.selectedProperty().get()){
				createNewTimelineTab();
			}
		});
		myRoot.getTabs().add(tabAdd);
	}

	private void createNewTimelineTab() {
		Integer tabID = nextTimelineIndex();
		Tab newSpawnTab = new Tab("Spawn Timeline " + tabID);
		SpawnTimelineView spawnTimelineView = new SpawnTimelineView(myView, myRoot.widthProperty());
		
		newSpawnTab.setContent(spawnTimelineView.getRoot());
		newSpawnTab.setOnCloseRequest(e -> {
			if(checkWithUser(newSpawnTab)){
				takenIDs.remove(tabID);
				//delete tab in backend
			} else {
				e.consume();
			}
		});
		
		myRoot.getTabs().add(myRoot.getTabs().size() -1, newSpawnTab);
		myRoot.getSelectionModel().select(newSpawnTab);
	}

	/**
	 * http://stackoverflow.com/questions/31540500/alert-box-for-when-user-attempts-to-close-application-using-setoncloserequest-in
	 * 
	 * @param tab
	 * @return
	 */
	private boolean checkWithUser(Tab tab) {
		 Alert alert = new Alert(AlertType.CONFIRMATION);
         alert.setTitle("Close Confirmation");
         alert.setHeaderText("Delete Spawn Timeline");
         alert.setContentText("Are you sure you want to delete this spawn timeline? This cannot be undone");
         Optional<ButtonType> result = alert.showAndWait();
         if (result.get() == ButtonType.OK){
        	 alert.close();
        	 return true;
         }

         if(result.get()==ButtonType.CANCEL){
             alert.close();
             return false;
         }
		return false;
	}

	private Integer nextTimelineIndex() {
		Integer count = 1;
		if(takenIDs != null){
			while(takenIDs.contains(count)){
				count++;
			}
		} else {
			takenIDs = new ArrayList<Integer>();
		}
		takenIDs.add(count);
		return count;
	}

	@Override
	public Node getRoot() {
		return myRoot;
	}
	
	

}
