package frontEnd.Skeleton.SpawnTimelineVisualization;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ModificationFromUser.Spawning.Modification_AddSpawner;
import backEnd.GameData.State.SerializableObservable;
import backEnd.GameData.State.SerializableObserver;
import backEnd.GameData.State.TileImpl;
import backEnd.GameEngine.Engine.Spawning.SpawnDataReader;
import backEnd.GameEngine.Engine.Spawning.SpawnQueues;
import frontEnd.View;
import frontEnd.CustomJavafxNodes.MultiFieldPrompt;
import frontEnd.Skeleton.UserTools.SkeletonObject;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SpawnTimelineView implements SkeletonObject, SerializableObserver {
	private View myView;
	private GridPane myRoot;
	private String mySpawnQueueName;
	private SpawnQueues mySpawnQueues;
	private List<? extends SpawnDataReader> queue;
	private ScrollPane dropZone;
	private Map<SpawnDataReader, VisualSpawnEntry> spawnDataToVisual;
	
	public SpawnTimelineView(View view, ReadOnlyDoubleProperty readOnlyDoubleProperty, String key, SpawnQueues value) {
		myView = view;
		mySpawnQueueName = key;
		initializeGrid(readOnlyDoubleProperty);
		initializingSpawnQueue(value);
	}

	private void initializingSpawnQueue(SpawnQueues value) {
		mySpawnQueues = value;
		mySpawnQueues.addObserver(this);
		spawnDataToVisual = new HashMap<>();
		queue = mySpawnQueues.getSingleSpawnQueue();
		dropZone = singleDropZone("Spawns (Freq, Delay, Iterations)");
		loadTimelines(queue, dropZone);
	}

	private void loadTimelines(List<? extends SpawnDataReader> queue, ScrollPane dropZone) {
		for(SpawnDataReader spawnDataReader : queue){
			addSpawn(spawnDataReader, queue, dropZone);
		}	
	}

	private void initializeGrid(ReadOnlyDoubleProperty readOnlyDoubleProperty) {
		myRoot = new GridPane();
		myRoot.prefWidthProperty().bind(readOnlyDoubleProperty);
		myRoot.setPadding(new Insets(20, 20, 20, 20));
		myRoot.setVgap(20);
		myRoot.setHgap(20);
	}


	private ScrollPane singleDropZone(String name) {
		ScrollPane dropZone = createDropZone(name);
		setDragCondition(dropZone);
		return dropZone;
	}

	private void setDragCondition(ScrollPane dropZone) {
		dropZone.setOnDragDropped(e -> {
			String presetName = e.getDragboard().getString();
			if(myView.getBankControllerReader().getPreset(presetName) instanceof TileImpl){
				// FIXME avoid instance of
				// TODO show error to user
				return;
			}
			MultiFieldPrompt multiFieldPrompt = new MultiFieldPrompt(3, Arrays.asList("Add Spawn", "Please input values for your new spawn item"),  Arrays.asList("0.0", "0.0", "0"), Arrays.asList("Frequency", "Delay", "Spawns"));
			
			List<String> userInput = multiFieldPrompt.create();
			myView.sendUserModification(
					new Modification_AddSpawner(mySpawnQueueName, presetName, Double.parseDouble(userInput.get(0)), Double.parseDouble(userInput.get(1)),Integer.parseInt(userInput.get(2))));
		});
	}

	private ScrollPane createDropZone(String string) {
		Label title = new Label(string);
		myRoot.add(title, 0, 0);
		ScrollPane scroll = new ScrollPane();
		scroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scroll.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scroll.setPrefHeight(300);
		VBox wrapper = new VBox();
		//wrapper.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
		wrapper.prefWidthProperty().bind(myRoot.widthProperty().divide(1.25));
		wrapper.setFillWidth(true);
		wrapper.setPrefHeight(300);
		scroll.setContent(wrapper);
		myRoot.add(scroll, 0, 1);
		scroll.setOnDragOver(e -> e.acceptTransferModes(TransferMode.ANY));
		return scroll;
	}

	@Override
	public Node getRoot() {
		return myRoot;
	}

	@Override
	public void update(SerializableObservable so, Object relevantSpawnData) {
		if(so == mySpawnQueues){
			if(!spawnDataToVisual.keySet().contains(relevantSpawnData)){
				addSpawn(relevantSpawnData, queue, dropZone);
				return;
			}
			removeSpawn(relevantSpawnData);
		}
		
	}

	private void removeSpawn(Object obj) {
		SpawnDataReader toRemove_Spawn = getToRemove_Spawn(obj);
		((HBox) spawnDataToVisual.get(toRemove_Spawn).getRoot()).getChildren().clear();
		spawnDataToVisual.remove(toRemove_Spawn);
	}

	private void addSpawn(Object obj, List<? extends SpawnDataReader> queue, ScrollPane dropZone) {
		SpawnDataReader toAdd_Spawn = queue.get(queue.indexOf(obj));
		Node toAdd = createVisual(toAdd_Spawn);
		((VBox) dropZone.getContent()).getChildren().add(toAdd);
	}

	private SpawnDataReader getToRemove_Spawn(Object obj) {
		for(SpawnDataReader spawnData : spawnDataToVisual.keySet()){
			if (spawnData == obj){
				return spawnData;
			}
		}
		return null;
	}

	private Node createVisual(SpawnDataReader spawnData) {
		VisualSpawnEntry visualSpawnEntry = new VisualSpawnEntry(myView, mySpawnQueueName , spawnData);
		spawnDataToVisual.put(spawnData, visualSpawnEntry);
		return visualSpawnEntry.getRoot();
	}

}
