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
import javafx.scene.input.TransferMode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ModificationFromUser.Spawning.Modification_AddSpawner;

public class SpawnTimelineView implements SkeletonObject{
	private View myView;
	private Rectangle dropZone1;
	private Rectangle dropZone2;
	private GridPane myRoot;

	public SpawnTimelineView(View view, ReadOnlyDoubleProperty readOnlyDoubleProperty) {
		myView = view;
		myRoot = new GridPane();
		myRoot.prefWidthProperty().bind(readOnlyDoubleProperty);
		myRoot.setPadding(new Insets(20, 20, 20, 20));
		myRoot.setVgap(20);
		myRoot.setHgap(20);
		dropZone1 = createDropZone("Single Instance Spawns", 0);
		dropZone1.setOnDragDropped(e -> {
			String presetName = e.getDragboard().getString();
			Component presetComponent = (Component) myView.getBankController().getPreset(presetName);
			SingleFieldPrompt hey = new SingleFieldPrompt(Arrays.asList("1","2"), "3", "4");
			long value = Long.parseLong(hey.create());
			myView.sendUserModification(new Modification_AddSpawner("", presetComponent, value, false));
		});
		dropZone2 = createDropZone("Recurring Spawns", 1);
		dropZone2.setOnDragDropped(e -> {
			String presetName = e.getDragboard().getString();
			Component presetComponent = (Component) myView.getBankController().getPreset(presetName);
			SingleFieldPrompt hey = new SingleFieldPrompt(Arrays.asList("1","2"), "3", "4");
			long value = Long.parseLong(hey.create());
			myView.sendUserModification(new Modification_AddSpawner("", presetComponent, value, true));
		});
	}

	private Rectangle createDropZone(String string, int i) {
		Label title = new Label(string);
		myRoot.add(title, i, 0);
		Rectangle dropZone = new Rectangle();
		ColumnConstraints column1 = new ColumnConstraints();
	    column1.setPercentWidth(50);
	    myRoot.getColumnConstraints().add(column1);
	    dropZone.widthProperty().bind(myRoot.widthProperty().divide(3));
		dropZone.setHeight(300);
		dropZone.setFill(Color.LIGHTGRAY);
		dropZone.setArcHeight(25);
		dropZone.setArcWidth(25);
		myRoot.add(dropZone, i, 1);
		dropZone.setOnDragOver(e -> e.acceptTransferModes(TransferMode.ANY));
		return dropZone;
	}

	@Override
	public Node getRoot() {
		return myRoot;
	}

}
