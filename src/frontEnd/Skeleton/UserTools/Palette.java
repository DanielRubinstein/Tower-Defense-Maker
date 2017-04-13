package frontEnd.Skeleton.UserTools;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import ModificationFromUser.Modification_AddAttributeOwner;
import ModificationFromUser.Modification_AddNewPresetAttributeOwner;
import backEnd.Attribute.AttributeOwner;
import backEnd.Attribute.AttributeOwnerReader;
import backEnd.GameData.State.Component;
import frontEnd.View;
import frontEnd.CustomJavafxNodes.DoubleFieldPrompt;
import frontEnd.Skeleton.AoTools.PresetCreation;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
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
	private static final String SETTINGS_IMAGE = "images/plus.jpg";
	private Map<ImageView, T> myMap;
	private String myType;

	public Palette(View view, Collection<T> objects, String string) {
		myView = view;
		myPresets = objects;
		myType =string;
		initializePane();
		myMap = new HashMap<ImageView, T>();
		System.out.println("in palette " + myPresets);
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
		
		ImageView addPresetButton = addNewPresetButton();
		addPresetButton.disableProperty().bind(myView.getBooleanAuthorModeProperty().not());
		Tooltip t = new Tooltip("Only possible in Author mode");
		addPresetButton.hoverProperty().addListener((a,b,c)->{
			if(addPresetButton.isDisabled()){
				Bounds scenePos= addPresetButton.localToScreen(addPresetButton.getBoundsInLocal());
				t.show(addPresetButton, scenePos.getMaxX(), scenePos.getMinY()-scenePos.getHeight());
			}else{
				t.hide();
			}
		});
		tile.getChildren().add(addNewPresetButton());
		
	}

	private ImageView addNewPresetButton() {
		ImageView addImage = createImageView(SETTINGS_IMAGE, (iV) ->{
			// TODO this is where a new preset is created in the frontend
			String newAttributeOwnerName = null;
			AttributeOwner newAO = new Component();
			String imagePathForNewPreset = "images/zombie.jpg";
			//newAO.addAttribute(IMAGEFILE_ATTRIBUTE_NAME, imagePathForNewPreset);

			PresetCreation presetCreation = new PresetCreation(myView, newAO);
			
			ImageView newImage = createImageView(imagePathForNewPreset, (iV2) ->{
				System.out.println("testing eme");
				Point2D point = askForNewPosition();
				myView.sendUserModification(new Modification_AddAttributeOwner(newAO, point));
			});
			System.out.println(newImage +  "    " + ((T) newAO));
			myMap.put(newImage, (T) newAO);
			presetCreation.add(newImage);
			presetCreation.launch(0d, 0d);
		});
		/*
		Button b = new Button();
		b.setGraphic(addImage);
		b.setPadding(Insets.EMPTY);
		b.getStyleClass().clear();
		b.setOnAction((e) -> addImage.getOnMouseClicked());
		*/
		System.out.println(addImage.getImage());
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
		System.out.println("creaitng imageview " +myImagePath);
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
