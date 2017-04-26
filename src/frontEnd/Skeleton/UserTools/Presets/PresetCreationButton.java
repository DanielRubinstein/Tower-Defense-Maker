package frontEnd.Skeleton.UserTools.Presets;

import java.io.FileNotFoundException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import backEnd.Attribute.AttributeOwner;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.TileImpl;
import frontEnd.View;
import frontEnd.Skeleton.AoTools.GenericCommandCenter;
import frontEnd.Skeleton.UserTools.SkeletonObject;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

public class PresetCreationButton implements SkeletonObject {
	private static final String PLUS_IMAGE = "resources/images/Tools/plus.jpg";
	private static final Double PRESET_SIZE = 75d;
	private View myView;
	private ImageView myRoot;
	
	public PresetCreationButton(View view, String myType, Function<String, ImageView> imageCreator, BiConsumer<Node, Consumer<Node>> clickEventSetter){
		myRoot = imageCreator.apply(PLUS_IMAGE);
		myRoot.setFitWidth(PRESET_SIZE);
		myRoot.setFitHeight(PRESET_SIZE);
		myView = view;
		clickEventSetter.accept(myRoot, (iV) -> {
			try {
				AttributeOwner newAO = null;
				switch (myType) {
				case "Tiles":
					newAO = new TileImpl();
					break;
				case "Components":
					newAO = new Component();
					break;
				}
				GenericCommandCenter presetCreation = new GenericCommandCenter(myView, newAO);
				presetCreation.launch("Design a new preset", 0d, 0d);
			} catch (FileNotFoundException e) {
				myView.reportError(e);
			}
		});
		
	}
	
	public void disableInPlayerMode(Consumer<Boolean> disableConsumer) {
		myRoot.disableProperty().bind(myView.getBooleanAuthorModeProperty().not());
		myRoot.disableProperty().addListener((a, b, c) -> {
			disableConsumer.accept(c);
		});
	}
	

	@Override
	public Node getRoot() {
		return myRoot;
	}
	
}