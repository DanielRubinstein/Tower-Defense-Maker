package frontEnd.Skeleton.UserTools.Presets;

import java.io.FileNotFoundException;
import java.util.function.Consumer;

import backEnd.Attribute.AttributeOwner;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.ComponentImpl;
import backEnd.GameData.State.TileImpl;
import frontEnd.View;
import frontEnd.Skeleton.SkeletonObject;
import frontEnd.Skeleton.AoTools.GenericCommandCenter;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import resources.constants.numeric.NumericResourceBundle;
import util.reflection.Reflection;

/**
 * This class creates the plus button that creates a new preset to place on the grid or the palette
 * 
 * @author Miguel Anderson
 *
 */

public class PresetCreationButton implements SkeletonObject {
	private NumericResourceBundle numericResourceBundle = new NumericResourceBundle();
	private static final String PLUS_IMAGE = "resources/images/Tools/plus.jpg";
	private View myView;
	private ImageView myRoot;
	
	public PresetCreationButton(View view, String myType){
		myRoot = createImageView(PLUS_IMAGE);
		Double palettePresetSize = numericResourceBundle.getFromSizing("PalettePresetSize");
		myRoot.setFitHeight(palettePresetSize);
		myRoot.setFitWidth(palettePresetSize);
		myView = view;
		myRoot.setOnMouseClicked(e -> {
			AttributeOwner newAO = null;
			try{
				newAO = (AttributeOwner) Reflection.createInstance(myType);
			} catch (Exception err){
				try{
					switch (myType) {
					case "Components":
						newAO = new ComponentImpl();
						break;
					case "Tiles":
						newAO = new TileImpl();
						break;
					default:
						break;
					}
				} catch (FileNotFoundException err2){
					return;
				}
			
			}
			GenericCommandCenter presetCreation = new GenericCommandCenter(myView, newAO);
			presetCreation.launch("Design a new preset", 0d, 0d);
		});		
	}
	/*
	public void disableInPlayerMode(Consumer<Boolean> disableConsumer) {
		myRoot.disableProperty().bind(myView.getBooleanAuthorModeProperty().not());
		System.out.println("button disable bound");
		myRoot.disableProperty().addListener((a, b, c) -> {
			disableConsumer.accept(c);
		});
	}
	*/
	
	private ImageView createImageView(String myImagePath) {
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(myImagePath));
		ImageView imageView = new ImageView(image);
		return imageView;
	}

	@Override
	public Node getRoot() {
		return myRoot;
	}
	
}
