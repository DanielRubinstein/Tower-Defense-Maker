package frontEnd.Skeleton.SpawnTimelineVisualization;

import java.util.Arrays;

import ModificationFromUser.Spawning.Modification_EditSpawnDataTime;
import ModificationFromUser.Spawning.Modification_RemoveSpawner;
import backEnd.Attribute.AttributeOwner;
import backEnd.GameEngine.Engine.Spawning.SpawnDataReader;
import frontEnd.View;
import frontEnd.CustomJavafxNodes.SingleFieldPrompt;
import frontEnd.Skeleton.ScreenGrid.AttributeOwnerVisual;
import frontEnd.Skeleton.ScreenGrid.AttributeOwnerVisualImpl;
import frontEnd.Skeleton.UserTools.SkeletonObject;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class VisualSpawnEntry implements SkeletonObject {
	private View myView;
	private String mySpawnQueueName;
	private HBox mySpawnBox;
	
	public VisualSpawnEntry(View view, String spawnQueueName, SpawnDataReader spawnData){
		myView = view;
		mySpawnQueueName = spawnQueueName;
		addToDropZone(spawnData , false);
	}
	
	
	
	private void addToDropZone(SpawnDataReader spawnData,  boolean repeating) {
		mySpawnBox = new HBox();
		
		String presetName = spawnData.getPresetName();
		Double time = spawnData.getTime();
		
		AttributeOwner presetComponent = myView.getBankController().getPreset(presetName);
		AttributeOwnerVisual presetVisual = new AttributeOwnerVisualImpl(presetComponent);
		ImageView spawnImage = presetVisual.getImageView();
		spawnImage.setFitHeight(30);
		
		Label name = new Label();
		name.setText(presetName);
		
		Label valueText = new Label(Double.toString(time));
		valueText.setOnMouseClicked(e -> {
			SingleFieldPrompt newPrompt = new SingleFieldPrompt(
					Arrays.asList("Add Spawn", "Please input a time for your new spawn item"), "Spawn Time Value",
					"1");
			double newValue = Double.parseDouble(newPrompt.create());
			myView.sendUserModification(new Modification_EditSpawnDataTime(spawnData,newValue));
			valueText.setText(Double.toString(newValue));
		});
		
		Button remove = new Button("Delete");
		remove.setOnAction(e -> {
			myView.sendUserModification(new Modification_RemoveSpawner(mySpawnQueueName, spawnData));
		});
		
		Separator separator2 = new Separator();
		separator2.setOrientation(Orientation.VERTICAL);
		
		mySpawnBox.getChildren().add(valueText);
		mySpawnBox.getChildren().add(separator2);
		mySpawnBox.getChildren().add(name);
		mySpawnBox.getChildren().add(spawnImage);
		mySpawnBox.getChildren().add(remove);
		mySpawnBox.setAlignment(Pos.CENTER);
		mySpawnBox.setSpacing(10);
	}


	@Override
	public Node getRoot() {
		return mySpawnBox;
	}
}
