package frontEnd.Skeleton.SpawnTimelineVisualization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ModificationFromUser.Spawning.Modification_EditSpawnData;
import ModificationFromUser.Spawning.Modification_RemoveSpawner;
import backEnd.Attribute.AttributeOwnerReader;
import backEnd.GameEngine.Engine.Spawning.SpawnDataReader;
import frontEnd.View;
import frontEnd.CustomJavafxNodes.MultiFieldPrompt;
import frontEnd.Skeleton.SkeletonObject;
import frontEnd.Skeleton.ScreenGrid.AttributeOwnerVisual;
import frontEnd.Skeleton.ScreenGrid.AttributeOwnerVisualImpl;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class VisualSpawnEntry implements SkeletonObject {
	private View myView;
	private String mySpawnQueueName;
	private HBox mySpawnBox;
	private List<Label> myLabels;
	
	public VisualSpawnEntry(View view, String spawnQueueName, SpawnDataReader spawnData){
		myView = view;
		mySpawnQueueName = spawnQueueName;
		addToDropZone(spawnData , false);
	}
	
	private void addToDropZone(SpawnDataReader spawnData,  boolean repeating) {
		mySpawnBox = new HBox();
		
		String presetName = spawnData.getPresetName();
		
		List<Number> spawnDataNumbers = getSpawnDataNumbers(spawnData);
		
		AttributeOwnerReader presetComponent = myView.getBankControllerReader().getPreset(presetName);
		AttributeOwnerVisual presetVisual = new AttributeOwnerVisualImpl(presetComponent);
		ImageView spawnImage = presetVisual.getImageView();
		spawnImage.setFitHeight(30);
		
		Label name = new Label();
		name.setText(presetName);
		
		myLabels = new ArrayList<>();
		
		for(Number number : spawnDataNumbers){
			Label valueText = new Label(number.toString());
			valueText.setOnMouseClicked(e -> {
				MultiFieldPrompt multiFieldPrompt = new MultiFieldPrompt(3, Arrays.asList("Add Spawn", "Please input values for your new spawn item"),  Arrays.asList("0.0", "0.0", "0"), Arrays.asList("Frequency", "Delay", "Iterations"));
				List<String> userInput = multiFieldPrompt.create();
				if(userInput == null){
					return;
				}
				myView.sendUserModification(
						new Modification_EditSpawnData(spawnData, Double.parseDouble(userInput.get(0)), Double.parseDouble(userInput.get(1)),Integer.parseInt(userInput.get(2))));
				
				updateValueLabels(userInput);
			});
			myLabels.add(valueText);
			mySpawnBox.getChildren().add(valueText);
		}
		
		Button remove = new Button("Delete");
		remove.setOnAction(e -> {
			myView.sendUserModification(new Modification_RemoveSpawner(mySpawnQueueName, spawnData));
		});
		
		Separator separator2 = new Separator();
		separator2.setOrientation(Orientation.VERTICAL);
		
		
		mySpawnBox.getChildren().add(separator2);
		mySpawnBox.getChildren().add(name);
		mySpawnBox.getChildren().add(spawnImage);
		mySpawnBox.getChildren().add(remove);
		mySpawnBox.setAlignment(Pos.CENTER);
		mySpawnBox.setSpacing(10);
	}

	private void updateValueLabels(List<String> userInput) {
		for(int i = 0 ; i < myLabels.size() ; i++){
			myLabels.get(i).setText(userInput.get(i));
		}
	}


	private List<Number> getSpawnDataNumbers(SpawnDataReader spawnData) {
		List<Number> spawnDataNumbers = new ArrayList<>();
		spawnDataNumbers.add(spawnData.getFrequency());
		spawnDataNumbers.add(spawnData.getDelay());
		spawnDataNumbers.add(spawnData.getSpawns());
		return spawnDataNumbers;
	}

	@Override
	public Node getRoot() {
		return mySpawnBox;
	}
}
