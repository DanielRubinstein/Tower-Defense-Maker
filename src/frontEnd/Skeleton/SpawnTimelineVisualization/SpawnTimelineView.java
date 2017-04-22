package frontEnd.Skeleton.SpawnTimelineVisualization;

import java.util.Arrays;
import java.util.List;

import backEnd.Attribute.AttributeOwner;
import backEnd.GameData.State.Component;
import frontEnd.View;
import frontEnd.CustomJavafxNodes.SingleFieldPrompt;
import frontEnd.Skeleton.UserTools.SkeletonObject;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ModificationFromUser.Spawning.Modification_AddSpawner;

public class SpawnTimelineView implements SkeletonObject{
	private View myView;
	private ScrollPane dropZone1;
	private ScrollPane dropZone2;
	private GridPane myRoot;
	private ScrollPane test;

	public SpawnTimelineView(View view, ReadOnlyDoubleProperty readOnlyDoubleProperty) {
		myView = view;
		myRoot = new GridPane();
		myRoot.prefWidthProperty().bind(readOnlyDoubleProperty);
		myRoot.setPadding(new Insets(20, 20, 20, 20));
		myRoot.setVgap(20);
		myRoot.setHgap(20);
		createDropZones();
	}
	
	private void createDropZones(){
		dropZone1 = createDropZone("Single Instance Spawns", 0);
		dropZone1.setOnDragDropped(e -> {
			String presetName = e.getDragboard().getString();
			Component presetComponent = (Component) myView.getBankController().getPreset(presetName);
			SingleFieldPrompt hey = new SingleFieldPrompt(Arrays.asList("Add Spawn",""), "Time interval", "4");
			long value = Long.parseLong(hey.create());
			myView.sendUserModification(new Modification_AddSpawner("", presetComponent, value, false));
			addToDropZone(dropZone1,presetComponent,value);
		});
		//timelines in a map in game data
		dropZone2 = createDropZone("Recurring Spawns", 1);
		dropZone2.setOnDragDropped(e -> {
			String presetName = e.getDragboard().getString();
			Component presetComponent = (Component) myView.getBankController().getPreset(presetName);
			SingleFieldPrompt hey = new SingleFieldPrompt(Arrays.asList("1","2"), "3", "4");
			long value = Long.parseLong(hey.create());
			myView.sendUserModification(new Modification_AddSpawner("", presetComponent, value, true));
			addToDropZone(dropZone2,presetComponent,value);
		});
	}
	private void addToDropZone(ScrollPane dropZone, Component spawn,long value){
		HBox spawnBox = new HBox();
		String spawnImagePath = (String) spawn.getAttribute("ImageFile").getValue();
		ImageView spawnImage =  createImageView(spawnImagePath);
		
		Label name = new Label();
		name.setText(myView.getBankController().getAOName(spawn));
		Label valueText = new Label(Long.toString(value));
		spawnBox.getChildren().add(name);
		spawnBox.getChildren().add(spawnImage);
		spawnBox.getChildren().add(valueText);
		
		VBox child = (VBox) dropZone.getContent();
		child.getChildren().add(spawnBox);
		dropZone.setContent(child);
	}
	private ImageView createImageView(String imagePath) {
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(imagePath));
		ImageView imv = new ImageView();
		imv.setImage(image);
		imv.setPreserveRatio(true);
		imv.setFitHeight(100);
		return imv;
	}

	private ScrollPane createDropZone(String string, int i) {
		Label title = new Label(string);
		myRoot.add(title, i, 0);
		ScrollPane scroll = new ScrollPane();
		scroll.setHbarPolicy(ScrollBarPolicy.NEVER);
		scroll.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scroll.setPrefHeight(300);
		
		VBox wrapper = new VBox();
		ColumnConstraints column1 = new ColumnConstraints();
	    column1.setPercentWidth(50);
	    myRoot.getColumnConstraints().add(column1);
	    wrapper.maxWidthProperty().bind(myRoot.widthProperty().divide(3));
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

}
