package frontEnd.Skeleton.SpawnTimelineVisualization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import ModificationFromUser.Spawning.Modification_AddSpawnQueue;
import ModificationFromUser.Spawning.Modification_RemoveSpawnQueue;
import backEnd.GameEngine.Engine.Spawning.SpawnQueues;
import frontEnd.View;
import frontEnd.Skeleton.SkeletonObject;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Alert.AlertType;

public class SpawnTabPane implements SkeletonObject {
	private TabPane myRoot;
	private Collection<Integer> takenIDs;
	private View myView;
	private Map<String, SpawnQueues> mySpawnQueues;

	public SpawnTabPane(View view) {
		myView = view;
		myRoot = new TabPane();
		mySpawnQueues = myView.getSpawnQueues();
		if (!mySpawnQueues.isEmpty()) {
			addPresetQueues();
		}
		createAddTab();
	}

	private void addPresetQueues() {
		for (Map.Entry<String, SpawnQueues> entry : mySpawnQueues.entrySet()) {
			extractTimelineIndex(entry.getKey());
			createNewTimelineTab(entry.getKey(), entry.getValue());
		}
	}

	private void extractTimelineIndex(String key) {
		Integer index = Integer.parseInt(key.substring(key.indexOf(' ')+1));
		if(takenIDs == null){
			takenIDs = new ArrayList<Integer>();
		}
		takenIDs.add(index);
	}

	private void createNewTimelineTab(String key, SpawnQueues value) {
		SpawnTimelineView spawnTimelineView = new SpawnTimelineView(myView, myRoot.widthProperty(), key, value);
		Tab newSpawnTab = new Tab(key);
		newSpawnTab.setContent(spawnTimelineView.getRoot());
		newSpawnTab.setOnCloseRequest(e -> {
			if (checkWithUser(newSpawnTab)) {
				takenIDs.remove(Integer.parseInt(key.substring(key.indexOf(' ')+1)));
				myView.sendUserModification(new Modification_RemoveSpawnQueue(key));
			} else {
				e.consume();
			}
		});
		
		int index = myRoot.getTabs().size();
		if (index == 0) index++;

		myRoot.getTabs().add(index - 1, newSpawnTab);
		myRoot.getSelectionModel().select(newSpawnTab);
	}

	private void createAddTab() {
		Tab tabAdd = new Tab("+");
		tabAdd.setClosable(false);
		tabAdd.setOnSelectionChanged((e) -> {
			if (tabAdd.selectedProperty().get()) {
				createNewTimelineTab();
			}
		});
		myRoot.getTabs().add(tabAdd);
	}

	private void createNewTimelineTab() {
		Integer tabID = nextTimelineIndex();
		System.out.println(tabID);
		String tabName = "Timeline " + tabID;
		myView.sendUserModification(new Modification_AddSpawnQueue(tabName));
		createNewTimelineTab(tabName, mySpawnQueues.get(tabName));
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
		if (result.get() == ButtonType.OK || result.get() == ButtonType.CANCEL) {
			alert.close();
		}
		return result.get() == ButtonType.OK;
	}

	private Integer nextTimelineIndex() {
		Integer count = 1;
		if (takenIDs != null) {
			while (takenIDs.contains(count)) {
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