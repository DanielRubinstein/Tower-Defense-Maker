package frontEnd.Skeleton.UserTools;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.function.Consumer;
import ModificationFromUser.Modification_AddPresetAttributeOwnerToGrid;
import ModificationFromUser.Modification_EditAttribute;
import backEnd.Attribute.AttributeData;
import backEnd.Attribute.AttributeImpl;
import backEnd.Attribute.AttributeOwner;
import backEnd.Bank.BankController;
import backEnd.GameData.State.AccessPermissionsImpl;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.TileImpl;
import backEnd.Mode.UserModeType;
import frontEnd.View;
import frontEnd.CustomJavafxNodes.DoubleFieldPrompt;
import frontEnd.Skeleton.AoTools.PresetCreation;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
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
public class Palette<T extends AttributeOwner> implements SkeletonObject, Observer {
	private static final int TILE_SIZE = 75;
	private View myView;
	private TilePane tile;
	private Map<String, T> myPresetMap;
	private static final String IMAGEFILE_ATTRIBUTE_NAME = "ImageFile";
	private static final String SETTINGS_IMAGE = "images/plus.jpg";
	private Map<ImageView, T> myMap;
	private String myType;
	private BankController observedBankController;

	public Palette(View view, Map<String, T> presetMap, String string){
		myView = view;
		observedBankController = myView.getBankController();
		observedBankController.addObserver(this);
		myPresetMap = presetMap;
		myType =string;
		initializePane();
		myMap = new HashMap<ImageView, T>();
		try{
			for (T preset : myPresetMap.values()) {
				addPresetToPalette(preset);
			}
		} catch (NullPointerException e){
			System.out.println("Again no presets here");
		}
		
		addNewPresetButton();
		
	}

	private void addNewPresetButton() {
		ImageView addPresetButton = createNewPresetButton();
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
		tile.getChildren().add(addPresetButton);
	}

	private void addPresetToPalette(T preset) {
		String myImagePath = (String) preset.getAttribute(IMAGEFILE_ATTRIBUTE_NAME).getValue();
		ImageView imageView = createImageView(myImagePath, (iV) ->{
			myView.sendUserModification(new Modification_AddPresetAttributeOwnerToGrid(myMap.get(iV), askForNewPosition()));
		});
		imageView.setOnDragDetected(e -> {
			//TODO: make a component from imageView and put it into the Tile that drop ends on
		});
		myMap.put(imageView, preset);
		tile.getChildren().add(imageView);
	}

	private ImageView createNewPresetButton() {
		ImageView addImage = createImageView(SETTINGS_IMAGE, (iV) ->{

			String newAttributeOwnerName = null;
				try {	
					AttributeOwner newAO = null;
					String imagePathForNewPreset = "";
					switch(myType){
					case "Tiles":
						//newAO = new TileImpl();
						// TODO make blank tile
						newAO = new TileImpl(Arrays.asList(), Arrays.asList(UserModeType.AUTHOR), new Point2D(0,0));
						imagePathForNewPreset =  "images/grass.jpg";
						break;
					case "Components":
						newAO = new Component(new AttributeData(),new AccessPermissionsImpl());
						imagePathForNewPreset =  "images/zombie.jpg";
						break;
					}

					PresetCreation presetCreation = new PresetCreation(myView, newAO);
					presetCreation.launch(0d, 0d);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		});
		/*
		Button b = new Button();
		b.setGraphic(addImage);
		b.setPadding(Insets.EMPTY);
		b.getStyleClass().clear();
		b.setOnAction((e) -> addImage.getOnMouseClicked());
		*/
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
		DoubleFieldPrompt myDialog = new DoubleFieldPrompt(dialogTitles, promptText, promptLabel);
		List<String> results = myDialog.create();
		return new Point2D(Double.parseDouble(results.get(0)), Double.parseDouble(results.get(1)));
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (o == observedBankController){
			if(myType.equals("Tiles")){
				myPresetMap = (Map<String, T>) observedBankController.getTileMap();
				updatePalette();
			} else if(myType.equals("Components")){
				myPresetMap = (Map<String, T>) observedBankController.getComponentMap();
				updatePalette();
			}
			
		}
	}

	private void updatePalette() {
		for (T preset : myPresetMap.values()) {
			if (!myMap.containsValue(preset)){
				addPresetToPalette(preset);
			}
		}
		
	
	}
}
