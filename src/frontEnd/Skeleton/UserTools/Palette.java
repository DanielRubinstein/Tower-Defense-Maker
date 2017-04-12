package frontEnd.Skeleton.UserTools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import ModificationFromUser.Modification_AddAttributeOwner;
import ModificationFromUser.Modification_AddNewPresetAttributeOwner;
import backEnd.Attribute.AttributeOwner;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.Tile;
import frontEnd.View;
import frontEnd.CustomJavafxNodes.DoubleFieldPrompt;
import frontEnd.CustomJavafxNodes.SingleFieldPrompt;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;

/**
 * http://stackoverflow.com/questions/27182323/working-on-creating-image-gallery-in-javafx-not-able-to-display-image-properly
 * 
 * @author Miguel Anderson
 *
 * @param <T>
 */
public class Palette<T extends AttributeOwner> implements SkeletonObject {
	private static final int TILE_SIZE = 75;
	private View myView;
	private TilePane tile;
	private Collection<T> myPresets;
	private static final String IMAGEFILE_ATTRIBUTE_NAME = "ImageFile";
	private static final String SETTINGS_IMAGE = "images/Settings.jpg";
	private Map<ImageView, T> myMap;
	private String myType;

	public Palette(View view, Collection<T> objects, String string) {
		myView = view;
		myPresets = objects;
		myType =string;
		initializePane();
		
		try{
			for (T preset : myPresets) {
				
				String myImagePath = (String) preset.getAttribute(IMAGEFILE_ATTRIBUTE_NAME).getValue();
				ImageView imageView = createImageView(myImagePath, (iV) ->{
					myView.sendUserModification(new Modification_AddAttributeOwner(myMap.get(iV), askForNewPosition()));
				});
				myMap.put(imageView, preset);
				tile.getChildren().add(imageView);
			}
		} catch (NullPointerException e){
			System.out.println("Again no presets here");
		}
		
		tile.getChildren().add(addNewPresetButton());
		
	}

	private Node addNewPresetButton() {
		ImageView addImage = createImageView(SETTINGS_IMAGE, (iV) ->{
			String newAttributeOwnerName = null;
			AttributeOwner newAO = null;
			String imagePathForNewPreset = null;
			//newAO.addAttribute(IMAGEFILE_ATTRIBUTE_NAME, imagePathForNewPreset);
			// TODO this is where a new preset is created in the frontend
			// you're gonna have to use instanceof
			if (myType.equals("Tiles")){
				
			} else if (myType.equals("Components")){
				
			}
			myView.sendUserModification(new Modification_AddNewPresetAttributeOwner(newAttributeOwnerName, newAO));
			
			ImageView newImage = createImageView(imagePathForNewPreset, (iV2) ->{
				myView.sendUserModification(new Modification_AddAttributeOwner(newAO, askForNewPosition()));
			});
			myMap.put(newImage, (T) newAO);
		});
		return addImage;
	}

	private void initializePane() {
		tile = new TilePane();
		tile.setPadding(new Insets(15, 15, 15, 15));
		tile.setHgap(15);
	}

	@Override
	public Node getRoot() {
		return tile;
	}

	private ImageView createImageView(String myImagePath, Consumer<ImageView> consumer) {
		// DEFAULT_THUMBNAIL_WIDTH is a constant you need to define
		// The last two arguments are: preserveRatio, and use smooth (slower)
		// resizing
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(myImagePath));
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(TILE_SIZE);
		imageView.setFitHeight(TILE_SIZE);
		imageView.setOnMouseClicked(mouseEvent -> {
			if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
				consumer.accept(imageView);
			}
		});

		return imageView;
	}

	private Point2D askForNewPosition() {
		List<String> dialogTitles = Arrays.asList("Creation Utility", "Please input a location");
		List<String> promptLabel = Arrays.asList("X Position:", "Y Position:");
		List<String> promptText = Arrays.asList("0.0", "0.0");
		DoubleFieldPrompt myDialog = new DoubleFieldPrompt(dialogTitles, promptLabel, promptText);
		List<String> results = myDialog.create();
		return new Point2D(Double.parseDouble(results.get(0)), Double.parseDouble(results.get(1)));
	}
}
