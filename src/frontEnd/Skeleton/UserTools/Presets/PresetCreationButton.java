package frontEnd.Skeleton.UserTools.Presets;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import backEnd.Attribute.AttributeOwner;
import frontEnd.View;
import frontEnd.Skeleton.AoTools.GenericCommandCenter;
import frontEnd.Skeleton.UserTools.SkeletonObject;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import resources.constants.NumericResourceBundle;
import util.reflection.Reflection;

public class PresetCreationButton implements SkeletonObject {
	private NumericResourceBundle numericResourceBundle = new NumericResourceBundle();
	private static final String PLUS_IMAGE = "resources/images/Tools/plus.jpg";
	private View myView;
	private ImageView myRoot;
	
	public PresetCreationButton(View view, String myType, Function<String, ImageView> imageCreator, BiConsumer<Node, Consumer<Node>> clickEventSetter){
		myRoot = imageCreator.apply(PLUS_IMAGE);
		myRoot.setFitWidth(numericResourceBundle.getPresetSizeInPalette());
		myRoot.setFitHeight(numericResourceBundle.getPresetSizeInPalette());
		myView = view;
		clickEventSetter.accept(myRoot, (iV) -> {
			AttributeOwner newAO = (AttributeOwner) Reflection.createInstance(myType);
			GenericCommandCenter presetCreation = new GenericCommandCenter(myView, newAO);
			presetCreation.launch("Design a new preset", 0d, 0d);			
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
