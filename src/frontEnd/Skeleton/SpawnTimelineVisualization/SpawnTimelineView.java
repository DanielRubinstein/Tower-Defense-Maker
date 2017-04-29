package frontEnd.Skeleton.SpawnTimelineVisualization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ModificationFromUser.Spawning.Modification_AddSpawner;
import backEnd.GameData.State.SerializableObservable;
import backEnd.GameData.State.SerializableObserver;
import backEnd.GameData.State.TileImpl;
import backEnd.GameEngine.Engine.Spawning.SpawnDataImpl;
import backEnd.GameEngine.Engine.Spawning.SpawnDataReader;
import backEnd.GameEngine.Engine.Spawning.SpawnQueues;
import frontEnd.View;
import frontEnd.CustomJavafxNodes.SingleFieldPrompt;
import frontEnd.Skeleton.UserTools.SkeletonObject;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

public class SpawnTimelineView implements SkeletonObject, SerializableObserver {
	private View myView;
	private GridPane myRoot;
	private String mySpawnQueueName;
	private SpawnQueues mySpawnQueues;
	private List<Pair<List<? extends SpawnDataReader>, ScrollPane>> spawnQueueToDropZone;
	private Map<SpawnDataReader, List<? extends SpawnDataReader>> spawnDataToQueue;
	private Map<SpawnDataReader, VisualSpawnEntry> spawnDataToVisual;
	private Map<SpawnDataReader, ScrollPane> spawnDataToDropZone;

	public SpawnTimelineView(View view, ReadOnlyDoubleProperty readOnlyDoubleProperty, String key, SpawnQueues value) {
		myView = view;
		mySpawnQueueName = key;
		initializeGrid(readOnlyDoubleProperty);
		initializingSpawnQueue(value);
	}

	private void initializingSpawnQueue(SpawnQueues value) {
		mySpawnQueues = value;
		mySpawnQueues.addObserver(this);
		spawnDataToQueue = new HashMap<>();
		spawnDataToVisual = new HashMap<>();
		spawnDataToDropZone = new HashMap<>();
		spawnQueueToDropZone = new ArrayList<Pair<List<? extends SpawnDataReader>,ScrollPane>>();
		
		List<? extends SpawnDataReader> single = mySpawnQueues.getSingleSpawnQueue();
		List<? extends SpawnDataReader> frequency = mySpawnQueues.getFrequencySpawnQueue();
		
		ScrollPane dropZoneSingle = singleDropZone("Single Instance Spawns", 0, false);
		ScrollPane dropZoneFrequency = singleDropZone("Recurring Spawns", 1, true);
		
		spawnQueueToDropZone.add(new Pair<List<? extends SpawnDataReader>, ScrollPane>(single, dropZoneSingle));
		spawnQueueToDropZone.add(new Pair<List<? extends SpawnDataReader>, ScrollPane>(frequency, dropZoneFrequency));
		
		loadTimelines(single, dropZoneSingle);
		loadTimelines(frequency, dropZoneFrequency);
	}

	private void loadTimelines(List<? extends SpawnDataReader> queue, ScrollPane dropZone) {
		for(SpawnDataReader spawnDataReader : queue){
			spawnDataToQueue.put(spawnDataReader, queue);
			spawnDataToDropZone.put(spawnDataReader, dropZone);
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


	private ScrollPane singleDropZone(String name, int order, boolean repeating) {
		ScrollPane dropZone = createDropZone(name, order);
		setDragCondition(dropZone, repeating);
		return dropZone;
	}

	private void setDragCondition(ScrollPane dropZone, boolean repeating) {
		dropZone.setOnDragDropped(e -> {
			String presetName = e.getDragboard().getString();
			if(myView.getBankController().getPreset(presetName) instanceof TileImpl){
				// FIXME avoid instance of
				// TODO show error to user
				return;
			}
			SingleFieldPrompt hey = new SingleFieldPrompt(
					Arrays.asList("Add Spawn", "Please input a time for your new spawn item"), "Spawn Time Value",
					"1.0");
			Double value = hey.getUserInputDouble();
			if(value == null){
				return;
			}
			myView.sendUserModification(
					new Modification_AddSpawner(mySpawnQueueName, presetName, value, repeating));
		});
	}

	private ScrollPane createDropZone(String string, int i) {
		Label title = new Label(string);
		myRoot.add(title, i, 0);
		ScrollPane scroll = new ScrollPane();
		scroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scroll.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scroll.setPrefHeight(300);
		VBox wrapper = new VBox();
		//wrapper.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(50);
		myRoot.getColumnConstraints().add(column1);
		wrapper.prefWidthProperty().bind(myRoot.widthProperty().divide(2.5));
		wrapper.setFillWidth(true);
		wrapper.setPrefHeight(300);
		scroll.setContent(wrapper);
		myRoot.add(scroll, i, 1);
		scroll.setOnDragOver(e -> e.acceptTransferModes(TransferMode.ANY));
		return scroll;
	}

	@Override
	public Node getRoot() {
		return myRoot;
	}

	@Override
	public void update(SerializableObservable so, Object relevantSpawnData) {
		for(Pair<List<? extends SpawnDataReader>, ScrollPane> entry : spawnQueueToDropZone){
			List<? extends SpawnDataReader> queue = entry.getKey();
			if(queue.contains(relevantSpawnData)){
				addSpawn(relevantSpawnData, queue, entry.getValue());
				return;
			}
		}
		removeSpawn(relevantSpawnData);
	}

	private void removeSpawn(Object obj) {
		SpawnDataReader toRemove_Spawn = getToRemove_Spawn(obj);
		((HBox) spawnDataToVisual.get(toRemove_Spawn).getRoot()).getChildren().clear();
		spawnDataToDropZone.remove(toRemove_Spawn);
		spawnDataToVisual.remove(toRemove_Spawn);
		spawnDataToQueue.remove(toRemove_Spawn);
	}

	private void addSpawn(Object obj, List<? extends SpawnDataReader> queue, ScrollPane dropZone) {
		SpawnDataReader toAdd_Spawn = queue.get(queue.indexOf(obj));
		spawnDataToQueue.put(toAdd_Spawn, queue);
		Node toAdd = createVisual(toAdd_Spawn);
		((VBox) dropZone.getContent()).getChildren().add(toAdd);
		spawnDataToDropZone.put(toAdd_Spawn, dropZone);
	}

	private SpawnDataReader getToRemove_Spawn(Object obj) {
		for(SpawnDataReader spawnData : spawnDataToQueue.keySet()){
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
