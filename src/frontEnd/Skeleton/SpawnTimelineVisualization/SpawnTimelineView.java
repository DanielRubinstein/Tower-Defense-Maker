package frontEnd.Skeleton.SpawnTimelineVisualization;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import backEnd.Attribute.AttributeOwner;
import backEnd.GameData.State.Component;
import backEnd.GameEngine.Engine.Spawning.SpawnData;
import backEnd.GameEngine.Engine.Spawning.SpawnQueue;
import frontEnd.View;
import frontEnd.CustomJavafxNodes.SingleFieldPrompt;
import frontEnd.Skeleton.UserTools.SkeletonObject;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import ModificationFromUser.Spawning.Modification_AddSpawner;
import ModificationFromUser.Spawning.Modification_EditSpawnDataTime;
import ModificationFromUser.Spawning.Modification_RemoveSpawner;

public class SpawnTimelineView implements SkeletonObject, Observer {
	private View myView;
	private ScrollPane dropZone1;
	private ScrollPane dropZone2;
	private GridPane myRoot;
	private String mySpawnQueueName;
	private SpawnQueue mySpawnQueue;
	private Map<SpawnData, VisualSpawnEntry> myFrequencySpawns;
	private Map<SpawnData, VisualSpawnEntry> mySingleSpawns;

	public SpawnTimelineView(View view, ReadOnlyDoubleProperty readOnlyDoubleProperty, String key, SpawnQueue value) {
		myView = view;
		mySpawnQueueName = key;
		mySpawnQueue = value;
		mySpawnQueue.addObserver(this);
		myRoot = new GridPane();
		myRoot.prefWidthProperty().bind(readOnlyDoubleProperty);
		myRoot.setPadding(new Insets(20, 20, 20, 20));
		myRoot.setVgap(20);
		myRoot.setHgap(20);
		createDropZones();
	}

	private void createDropZones() {
		dropZone1 = singleDropZone("Single Instance Spawns", 0, false);
		// timelines in a map in game data
		dropZone2 = singleDropZone("Recurring Spawns", 1, true);
	}

	private ScrollPane singleDropZone(String name, int order, boolean repeating) {
		ScrollPane dropZone = createDropZone(name, order);
		dropZone.setOnDragDropped(e -> {
			String presetName = e.getDragboard().getString();
			Component presetComponent = (Component) myView.getBankController().getPreset(presetName);
			SingleFieldPrompt hey = new SingleFieldPrompt(
					Arrays.asList("Add Spawn", "Please input a time for your new spawn item"), "Spawn Time Value",
					"1.0");
			double value = Double.parseDouble(hey.create());
			myView.sendUserModification(

					new Modification_AddSpawner(mySpawnQueueName, presetName, value, repeating));
			addToDropZone(dropZone, presetComponent, value,repeating); // This will be

																// removed.
																// Instead the
																// method will
																// be called via
																// the update
																// process from
																// the backend
																// (must be the
																// result of an
																// observation
																// trigger)
		});
		return dropZone;
	}

	private void addToDropZone(ScrollPane dropZone, Component spawn, double value, boolean repeating) {

		HBox spawnBox = new HBox();
		String spawnImagePath = spawn.<String>getAttribute("ImageFile").getValue();
		ImageView spawnImage = createImageView(spawnImagePath);
		String spawnName = myView.getBankController().getAOName(spawn);
		Label name = new Label();
		name.setText(spawnName);
		Label valueText = new Label(Double.toString(value));
		// TODO Editable value here valueText.setOnClick()
		valueText.setOnMouseClicked(e -> {
			SingleFieldPrompt newPrompt = new SingleFieldPrompt(
					Arrays.asList("Add Spawn", "Please input a time for your new spawn item"), "Spawn Time Value",
					"1");
			double newValue = Double.parseDouble(newPrompt.create());
			myView.sendUserModification(new Modification_EditSpawnDataTime(mySpawnQueueName,spawnName,value,newValue,repeating));
			valueText.setText(Double.toString(newValue));
		});
		Button remove = new Button("Delete");
		remove.setOnAction(e -> {
			myView.sendUserModification(new Modification_RemoveSpawner(mySpawnQueueName,spawnName,value,repeating));
			spawnBox.getChildren().clear();
		});
		spawnBox.getChildren().add(name);
		spawnBox.getChildren().add(spawnImage);
		spawnBox.getChildren().add(valueText);
		spawnBox.getChildren().add(remove);
		VBox child = (VBox) dropZone.getContent();
		child.getChildren().add(spawnBox);
		dropZone.setContent(child);
	}

	private ImageView createImageView(String imagePath) {
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(imagePath));
		ImageView imv = new ImageView();
		imv.setImage(image);
		imv.setPreserveRatio(true);
		imv.setFitHeight(30);
		return imv;
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
	public void update(Observable arg0, Object arg1) {
		System.out.println("fuck");
		if(arg0 == mySpawnQueue){
			System.out.println(arg1);
			if(arg1 == mySpawnQueue.getFrequencyQueue()){
				System.out.println("yo yo yo");
			}
		}
	}

}
