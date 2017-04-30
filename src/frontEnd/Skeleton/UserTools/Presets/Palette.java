package frontEnd.Skeleton.UserTools.Presets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import ModificationFromUser.AttributeOwner.Modification_Add_PaletteToGrid;
import ModificationFromUser.AttributeOwner.Modification_Remove_FromPalette;
import backEnd.Attribute.AttributeOwner;
import backEnd.Bank.BankControllerImpl;
import backEnd.Bank.BankControllerReader;
import backEnd.GameData.State.SerializableObservable;
import backEnd.GameData.State.SerializableObserver;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import resources.constants.NumericResourceBundle;

/**
 * http://stackoverflow.com/questions/27182323/working-on-creating-image-gallery
 * -in-javafx-not-able-to-display-image-properly
 * 
 * @author Miguel Anderson
 *
 */
public class Palette implements SkeletonObject, SerializableObserver{
	private NumericResourceBundle numericResourceBundle = new NumericResourceBundle();
	
	private View myView;
	private TilePane tile;
	private Collection<? extends AttributeOwner> myPresets;
	private Map<ImageView, AttributeOwner> myPresetMapFrontEnd;
	private String myType;
	private BankControllerReader observedBankController;
	private ModeReader observedMode;
	
	public Palette(View view, Collection<? extends AttributeOwner> presets) {
		myView = view;
		initializeMaps(presets);
		initializePane();
		for (AttributeOwner preset : presets) {
			addPresetToPalette(preset);
		}
		extractStringType();
		createNewPresetButton();
		
	}

	private void extractStringType() {
		for(AttributeOwner attributeOwner : myPresetMapFrontEnd.values()){
			myType = attributeOwner.getClass().getName();
			break;
		}
	}

	private void initializeMaps(Collection<? extends AttributeOwner> presets) {
		observedBankController = myView.getBankControllerReader();
		observedBankController.addObserver(this);
		//observedMode = myView.getModeReader();
		//observedMode.addObserver(this);
		myPresets = presets;
		myPresetMapFrontEnd = new HashMap<ImageView, AttributeOwner>();
	}

	private void addPresetToPalette(AttributeOwner preset) {
		AttributeOwnerVisual attrOwner = new AttributeOwnerVisualImpl(preset);
		ImageView imageView = attrOwner.getImageView();
		setPresetInteractions(preset, imageView);
		myPresetMapFrontEnd.put(imageView, preset);
		addPresetImageViewToPalette(imageView);
	}

	private void setPresetInteractions(AttributeOwner preset, ImageView imageView) {
		setClickEvent(imageView, (iV) -> {
			GenericCommandCenter presetComCenter = new GenericCommandCenter(myView, preset);
			
			presetComCenter.launch("Preset", iV.getBoundsInParent().getMinX(), iV.getBoundsInParent().getMinY());
		},setRemoveEvent(imageView),preset);
		//setRemoveEvent(imageView,preset);
		makeHoverOverName(preset, imageView);
		makePresetDraggable(preset, imageView);
	}

	private void setClickEvent(Node imageView, Consumer<Node> consumer, BiConsumer<AttributeOwner, MouseEvent> c, AttributeOwner aO) {
		imageView.setOnMouseClicked(mouseEvent -> {
			if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
				consumer.accept(imageView);
			} else if (mouseEvent.getButton().equals(MouseButton.SECONDARY)){
				c.accept(aO, mouseEvent);
			}
		});	
	}
	
	private BiConsumer<AttributeOwner, MouseEvent> setRemoveEvent(Node imageView){
		return(a, m) -> {
			ContextMenu removeMenu = new ContextMenu();
			MenuItem removeItem = new MenuItem("Remove from palette");
			removeItem.setOnAction(e -> {
				myView.sendUserModification(new Modification_Remove_FromPalette(a));
			});
			removeMenu.getItems().add(removeItem);
			removeMenu.setAutoHide(true);
			removeMenu.show(tile, m.getScreenX(), m.getScreenY());
		};
		
	}

	private void addPresetImageViewToPalette(ImageView imageView) {
		if(imageView.getFitHeight() == 0 || imageView.getFitWidth() == 0){
			// no size attribute for this attribute owner
			imageView.setFitHeight(numericResourceBundle.getPresetSizeInPalette());
			imageView.setFitWidth(numericResourceBundle.getPresetSizeInPalette());
		}
				
		tile.getChildren().add(imageView);
	}
	
	private void removePresetFromPalette(ImageView imageView) {
		tile.getChildren().remove(imageView);
		myPresetMapFrontEnd.remove(imageView);
	}

	private void makeHoverOverName(AttributeOwner preset, ImageView imageView) {
		Tooltip t = new Tooltip(observedBankController.getPresetName(preset));
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

	private void makePresetDraggable(AttributeOwner preset, ImageView imageView) {
		imageView.setOnDragDetected(e -> {
			Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);
			ClipboardContent content = new ClipboardContent();
			content.putString(observedBankController.getPresetName(preset));
			db.setContent(content);
			db.setDragView(imageView.getImage());
		});
	}

	private void createNewPresetButton() {
		PresetCreationButton presetCreationButton = new PresetCreationButton(myView, myType, 
				(imagePath) -> createImageView(imagePath), (node, consumer) -> setClickEvent(node, consumer,null,null) );
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
		updatePalette();
	}

	private void updatePalette() {
		for (AttributeOwner preset : myPresets) {
			if (!myPresetMapFrontEnd.containsValue(preset)) {
				addPresetToPalette(preset);
			}
		}
		List<ImageView> toRemove = new ArrayList<ImageView>();
		for (ImageView iv : myPresetMapFrontEnd.keySet()) {
			if (!myPresets.contains(myPresetMapFrontEnd.get(iv))) {
				toRemove.add(iv);
			}
		}
		for (ImageView iv : toRemove){
			removePresetFromPalette(iv);
		}
	}
}