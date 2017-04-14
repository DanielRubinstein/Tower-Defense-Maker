package frontEnd.Skeleton.UserTools;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.function.Consumer;

import ModificationFromUser.Modification_AddPresetAttributeOwnerToGrid;
import backEnd.Attribute.AttributeData;
import backEnd.Attribute.AttributeOwner;
import backEnd.Bank.BankController;
import backEnd.GameData.State.AccessPermissionsImpl;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.TileImpl;
import backEnd.Mode.UserModeType;
import frontEnd.View;
import frontEnd.Skeleton.AoTools.ComponentCommandCenter;
import frontEnd.Skeleton.AoTools.PresetCreation;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
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
	private Map<String, T> myPresetMapBackEnd;
	private static final String IMAGEFILE_ATTRIBUTE_NAME = "ImageFile";
	private static final String SETTINGS_IMAGE = "resources/images/Tools/plus.jpg";
	private Map<ImageView, T> myPresetMapFrontEnd;
	private String myType;
	private BankController observedBankController;
	private ImageView addPreset;

	public Palette(View view, Map<String, T> presetMap, String string){
		myView = view;
		initializeMaps(presetMap);
		myType =string;
		initializePane();
		for (T preset : myPresetMapBackEnd.values()) {
			addPresetToPalette(preset);
		}
		addNewPresetButton();
		
	}

	private void initializeMaps(Map<String, T> presetMap) {
		observedBankController = myView.getBankController();
		observedBankController.addObserver(this);
		myPresetMapBackEnd = presetMap;
		myPresetMapFrontEnd = new HashMap<ImageView, T>();
	}

	private void addNewPresetButton() {
		addPreset = createNewPresetButton();
		addPreset.disableProperty().bind(myView.getBooleanAuthorModeProperty().not());
		addPreset.disableProperty().addListener((a,b,c)->{
			if(c){
				tile.getChildren().remove(addPreset);
			} else{
				tile.getChildren().add(addPreset);
			}
		});
		tile.getChildren().add(addPreset);
	}

	private void addPresetToPalette(T preset) {
		String myImagePath = (String) preset.getAttribute(IMAGEFILE_ATTRIBUTE_NAME).getValue();
		ImageView imageView = createImageView(myImagePath);
		setPresetInteractions(preset, imageView);
		
		myPresetMapFrontEnd.put(imageView, preset);
		addPresetImageViewToPalette(imageView);
	}

	private void setPresetInteractions(T preset, ImageView imageView) {
		setDoubleClickEvent(imageView, (iV) ->{
			ComponentCommandCenter comCenter = new ComponentCommandCenter(myView, preset);
			comCenter.launch(iV.getX(), iV.getY());
		});
		makeHoverOverName(preset, imageView);
		makePresetDraggable(preset, imageView);
	}

	private void setDoubleClickEvent(ImageView imageView, Consumer<ImageView> consumer) {
		imageView.setOnMouseClicked(mouseEvent -> {
			if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
				consumer.accept(imageView);
			}
		});
	}

	private void addPresetImageViewToPalette(ImageView imageView) {
		tile.getChildren().remove(addPreset);
		tile.getChildren().add(imageView);
		tile.getChildren().add(addPreset);
	}

	private void makeHoverOverName(T preset, ImageView imageView) {
		Tooltip t = new Tooltip(observedBankController.getAOName(preset));
		imageView.hoverProperty().addListener((o, oldV, newV) -> {
			if(newV){
				Bounds scenePos= imageView.localToScreen(imageView.getBoundsInLocal());
				t.show(imageView, scenePos.getMaxX(), scenePos.getMinY());
			}else{
				t.hide();
			}
		});
	}

	private void makePresetDraggable(T preset, ImageView imageView) {
		imageView.setOnDragDetected(e -> {
			Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);
			ClipboardContent content = new ClipboardContent();
			content.putString("Drop here");
			db.setContent(content);
			db.setDragView(imageView.getImage());
		});
		Node canvas = myView.getCanvas();
		canvas.setOnDragOver(e -> e.acceptTransferModes(TransferMode.ANY));
		canvas.setOnDragDropped(e -> {
			Point2D pos = new Point2D(e.getSceneX(),e.getSceneY());
			myView.sendUserModification(new Modification_AddPresetAttributeOwnerToGrid(preset,pos));
		});
	}

	private ImageView createNewPresetButton() {
		ImageView addImage = createImageView(SETTINGS_IMAGE);
		setDoubleClickEvent(addImage,  (iV) ->{
			try {	
				AttributeOwner newAO = null;
				String imagePathForNewPreset = "";
				switch(myType){
				case "Tiles":
					//newAO = new TileImpl();
					// TODO make blank tile
					newAO = new TileImpl(Arrays.asList(), Arrays.asList(UserModeType.AUTHOR), new Point2D(0,0));
					imagePathForNewPreset =  "resources/images/Tiles/grass.jpg";
					break;
				case "Components":
					newAO = new Component(new AttributeData(),new AccessPermissionsImpl());
					imagePathForNewPreset =  "resources/images/Components/zombie.png";
					break;
				}

				PresetCreation presetCreation = new PresetCreation(myView, newAO);
				presetCreation.launch(0d, 0d);
			} catch (FileNotFoundException e) {
				myView.reportError(e);
			}
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

	private ImageView createImageView(String myImagePath) {
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(myImagePath));
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(TILE_SIZE);
		imageView.setFitHeight(TILE_SIZE);
		return imageView;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (o == observedBankController){
			if(myType.equals("Tiles")){
				myPresetMapBackEnd = (Map<String, T>) observedBankController.getTileMap();
				updatePalette();
			} else if(myType.equals("Components")){
				myPresetMapBackEnd = (Map<String, T>) observedBankController.getComponentMap();
				updatePalette();
			}
		}
	}

	private void updatePalette() {
		for (T preset : myPresetMapBackEnd.values()) {
			if (!myPresetMapFrontEnd.containsValue(preset)){
				addPresetToPalette(preset);
			}
		}
	}
}