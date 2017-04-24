package frontEnd.Skeleton.UserTools;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.function.Consumer;

import ModificationFromUser.AttributeOwner.Modification_Add_PaletteToGrid;
import ModificationFromUser.Spawning.Modification_AddSpawner;
import backEnd.BankController;
import backEnd.Attribute.AttributeOwner;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.TileImpl;
import backEnd.Mode.ModeReader;
import frontEnd.View;
import frontEnd.Skeleton.AoTools.GenericCommandCenter;
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
import resources.Constants;

/**
 * http://stackoverflow.com/questions/27182323/working-on-creating-image-gallery
 * -in-javafx-not-able-to-display-image-properly
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
	private Map<ImageView, T> myPresetMapFrontEnd;
	private String myType;
	private BankController observedBankController;
	private ModeReader observedMode;
	public Palette(View view, Map<String, T> presetMap, String string) {
		myView = view;
		initializeMaps(presetMap);
		myType = string;
		initializePane();
		for (T preset : myPresetMapBackEnd.values()) {
			addPresetToPalette(preset);
		}
		createNewPresetButton();

	}

	private void initializeMaps(Map<String, T> presetMap) {
		observedBankController = myView.getBankController();
		observedBankController.addObserver(this);
		observedMode = myView.getModeReader();
		observedMode.addObserver(this);
		myPresetMapBackEnd = presetMap;
		myPresetMapFrontEnd = new HashMap<ImageView, T>();
	}

	

	private void addPresetToPalette(T preset) {
		String myImagePath = preset.<String>getAttribute(IMAGEFILE_ATTRIBUTE_NAME).getValue();
		ImageView imageView = createImageView(myImagePath);
		setPresetInteractions(preset, imageView);
		myPresetMapFrontEnd.put(imageView, preset);
		addPresetImageViewToPalette(imageView);
	}

	private void setPresetInteractions(T preset, ImageView imageView) {
		setClickEvent(imageView, (iV) -> {
			GenericCommandCenter presetComCenter = new GenericCommandCenter(myView, preset);
			presetComCenter.launch("Preset", iV.getLayoutX(), iV.getLayoutY());
		});
		makeHoverOverName(preset, imageView);
		makePresetDraggable(preset, imageView);
	}

	private void setClickEvent(Node imageView, Consumer<Node> consumer) {
		imageView.setOnMouseClicked(mouseEvent -> {
			if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
				consumer.accept(imageView);
			}
		});
	}

	private void addPresetImageViewToPalette(ImageView imageView) {
		if(myView.getBooleanAuthorModeProperty().get()){
			try{
				tile.getChildren().add(tile.getChildren().size() - 1, imageView);
			} catch (IndexOutOfBoundsException e){
				tile.getChildren().add(imageView);
			}	
		} else {
			tile.getChildren().add(imageView);
			// this should not happen (potentially MODE EXCEPTION SHIT)
		}
	}

	private void makeHoverOverName(T preset, ImageView imageView) {
		Tooltip t = new Tooltip(observedBankController.getAOName(preset));
		imageView.hoverProperty().addListener((o, oldV, newV) -> {
			if (newV) {
				Bounds scenePos = imageView.localToScreen(imageView.getBoundsInLocal());
				t.show(imageView, scenePos.getMaxX(), scenePos.getMinY());
			} else {
				t.hide();
			}
		});
	}

	private void makePresetDraggable(T preset, ImageView imageView) {
		
		imageView.setOnDragDetected(e -> {
			Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);
			ClipboardContent content = new ClipboardContent();
			content.putString(observedBankController.getAOName(preset));
			db.setContent(content);
			db.setDragView(imageView.getImage());
		});
		Node screenGrid = myView.getScreenGrid();
		screenGrid.setOnDragOver(e -> e.acceptTransferModes(TransferMode.ANY));
		screenGrid.setOnDragDropped(e -> {
			String presetName = e.getDragboard().getString();
			AttributeOwner presetAO = observedBankController.getPreset(presetName);
			Double offsetX;
			Double offsetY;
			// TODO holy shit, how do we get rid of these magic numbers
			if(myView.getBooleanAuthorModeProperty().get()){
				offsetX = 40d;
				offsetY = -10d; 
			} else {
				offsetX = 12.5d;
				offsetY = 12.5d; 
			}
			Point2D pos = new Point2D(e.getSceneX() - Constants.SCREEN_GRID_PADDING + offsetX ,e.getSceneY() - Constants.SCREEN_GRID_PADDING + offsetY);
			myView.sendUserModification(new Modification_Add_PaletteToGrid(presetAO, pos));
		});
	}

	private void createNewPresetButton() {
		PresetCreationButton presetCreationButton = new PresetCreationButton(myView, myType, (imagePath) -> createImageView(imagePath), (node, consumer) -> setClickEvent(node, consumer) );
		tile.getChildren().add(presetCreationButton.getRoot());
		presetCreationButton.disableInPlayerMode((inPlayerMode) -> {
			if(inPlayerMode){
				tile.getChildren().remove(presetCreationButton.getRoot());
			} else {
				tile.getChildren().add(presetCreationButton.getRoot());
			}
		});
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
		switch (myType) {
		case "Tiles":
			myPresetMapBackEnd = (Map<String, T>) observedBankController.getAccessibleTileMap();
			updatePalette();
			break;
		case "Components":
			myPresetMapBackEnd = (Map<String, T>) observedBankController.getAccessibleComponentMap();
			updatePalette();
			break;
		}
	}

	private void updatePalette() {
		for (T preset : myPresetMapBackEnd.values()) {
			if (!myPresetMapFrontEnd.containsValue(preset)) {
				addPresetToPalette(preset);
			}
		}
	}
}