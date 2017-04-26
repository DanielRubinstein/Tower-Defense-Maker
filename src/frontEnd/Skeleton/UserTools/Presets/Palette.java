package frontEnd.Skeleton.UserTools.Presets;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import ModificationFromUser.AttributeOwner.Modification_Add_PaletteToGrid;
import ModificationFromUser.Spawning.Modification_AddSpawner;
import backEnd.BankController;
import backEnd.Attribute.AttributeOwner;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.SerializableObservable;
import backEnd.GameData.State.SerializableObserver;
import backEnd.GameData.State.TileImpl;
import backEnd.Mode.ModeReader;
import frontEnd.View;
import frontEnd.Skeleton.AoTools.GenericCommandCenter;
import frontEnd.Skeleton.ScreenGrid.AttributeOwnerVisual;
import frontEnd.Skeleton.ScreenGrid.AttributeOwnerVisualImpl;
import frontEnd.Skeleton.UserTools.SkeletonObject;
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
public class Palette<T extends AttributeOwner> implements SkeletonObject, SerializableObserver {
	private View myView;
	private TilePane tile;
	private Map<String, T> myPresetMapBackEnd;
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
		AttributeOwnerVisual attrOwner = new AttributeOwnerVisualImpl(preset);
		ImageView imageView = attrOwner.getImageView();
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
		switch (myType) {
		case "Tiles":
			imageView.setFitHeight(75d);
			imageView.setFitWidth(75d);
			break;
		}
				
		tile.getChildren().add(imageView);
	}
	
	private void removePresetFromPalette(ImageView imageView) {
		tile.getChildren().remove(imageView);
		myPresetMapFrontEnd.remove(imageView);
	}

	private void makeHoverOverName(T preset, ImageView imageView) {
		Tooltip t = new Tooltip(observedBankController.getAOName(preset));
		imageView.hoverProperty().addListener((o, oldV, newV) -> {
			if (newV) {
				Bounds scenePos = imageView.localToScreen(imageView.getBoundsInLocal());
				t.show(imageView, scenePos.getMaxX(), scenePos.getMinY());
				// TODO someone help
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
			Point2D pos = new Point2D(e.getX(), e.getY());
			myView.sendUserModification(new Modification_Add_PaletteToGrid(presetAO, pos));
		});
	}

	private void createNewPresetButton() {
		PresetCreationButton presetCreationButton = new PresetCreationButton(myView, myType, (imagePath) -> createImageView(imagePath), (node, consumer) -> setClickEvent(node, consumer) );
		tile.getChildren().add(0, presetCreationButton.getRoot());
		presetCreationButton.disableInPlayerMode((inPlayerMode) -> {
			if(inPlayerMode){
				tile.getChildren().remove(presetCreationButton.getRoot());
			} else {
				tile.getChildren().add(0, presetCreationButton.getRoot());
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
		
		return imageView;
	}

	@Override
	public void update(SerializableObservable so, Object obj) {
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
		List<ImageView> toRemove = new ArrayList<ImageView>();
		for (ImageView iv : myPresetMapFrontEnd.keySet()) {
			if (!myPresetMapBackEnd.containsValue(myPresetMapFrontEnd.get(iv))) {
				toRemove.add(iv);
			}
		}
		for (ImageView iv : toRemove){
			removePresetFromPalette(iv);
		}
	}
}