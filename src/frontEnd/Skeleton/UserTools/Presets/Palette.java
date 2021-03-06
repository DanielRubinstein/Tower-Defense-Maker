package frontEnd.Skeleton.UserTools.Presets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import backEnd.Attribute.AttributeOwnerReader;
import backEnd.Bank.BankControllerReader;
import backEnd.GameData.State.SerializableObservable;
import backEnd.GameData.State.SerializableObserver;
import frontEnd.View;
import frontEnd.Skeleton.SkeletonObject;
import frontEnd.Skeleton.ScreenGrid.AttributeOwnerVisual;
import frontEnd.Skeleton.ScreenGrid.AttributeOwnerVisualImpl;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import resources.constants.numeric.NumericResourceBundle;

/**
 * This class shows the images of the presets available for the user to drag and
 * drop onto the grid. Clicking on the images here will also pop up the options
 * for that preset which allow for customization.
 * 
 * Heavy influence:
 * http://stackoverflow.com/questions/27182323/working-on-creating-image-gallery
 * -in-javafx-not-able-to-display-image-properly
 * 
 * @author Miguel Anderson
 *
 */
public class Palette implements SkeletonObject, SerializableObserver {
	private NumericResourceBundle numericResourceBundle = new NumericResourceBundle();

	private View myView;
	private TilePane tile;
	private Collection<? extends AttributeOwnerReader> myPresets;
	private Map<ImageView, AttributeOwnerReader> myPresetMapFrontEnd;
	private String myType;
	private BankControllerReader observedBankController;
	private PaletteItemCreator myPaletteItemCreator;

	public Palette(View view, Collection<? extends AttributeOwnerReader> presets, String type) {
		myView = view;
		initializeMaps(presets);
		initializePane();
		myPaletteItemCreator = new PaletteItemCreator(myView, observedBankController);
		for (AttributeOwnerReader preset : presets) {
			addPresetToPalette(preset);
		}
		myType = type;
		//extractStringType();
		createNewPresetButton();

	}

	private void extractStringType() {
		for (AttributeOwnerReader attributeOwner : myPresetMapFrontEnd.values()) {
			myType = attributeOwner.getClass().getName();
			break;
		}
	}

	private void initializeMaps(Collection<? extends AttributeOwnerReader> presets) {
		observedBankController = myView.getBankControllerReader();
		observedBankController.addObserver(this);
		myPresets = presets;
		myPresetMapFrontEnd = new HashMap<ImageView, AttributeOwnerReader>();
	}

	private void addPresetToPalette(AttributeOwnerReader preset) {
		AttributeOwnerVisual attrOwner = new AttributeOwnerVisualImpl(preset);
		ImageView imageView = attrOwner.getImageView();
		myPaletteItemCreator.create(preset, imageView);
		myPresetMapFrontEnd.put(imageView, preset);
		addPresetImageViewToPalette(imageView);
	}

	private void addPresetImageViewToPalette(ImageView imageView) {
		if (imageView.getFitHeight() == 0 || imageView.getFitWidth() == 0) {
			// no size attribute for this attribute owner
			Double palettePresetSize = numericResourceBundle.getFromSizing("PalettePresetSize");
			imageView.setFitHeight(palettePresetSize);
			imageView.setFitWidth(palettePresetSize);
		}

		tile.getChildren().add(imageView);
	}

	private void removePresetFromPalette(ImageView imageView) {
		tile.getChildren().remove(imageView);
		myPresetMapFrontEnd.remove(imageView);
	}

	private void createNewPresetButton() {
		PresetCreationButton presetCreationButton = new PresetCreationButton(myView, myType);
		//System.out.println("button made " + myView.getBooleanAuthorModeProperty().get());
		if(myView.getBooleanAuthorModeProperty().get()){
			tile.getChildren().add(0, presetCreationButton.getRoot());
		}
		myView.getBooleanAuthorModeProperty().addListener((o, oldV, newV) -> {
			Boolean inPlayerMode = !newV;
			if (inPlayerMode && tile.getChildren().contains(presetCreationButton.getRoot())) {
				tile.getChildren().remove(presetCreationButton.getRoot());
			} else if (!inPlayerMode && !tile.getChildren().contains(presetCreationButton.getRoot())) {
				tile.getChildren().add(0, presetCreationButton.getRoot());
			}
		});
		
		/*
		presetCreationButton.disableInPlayerMode((inPlayerMode) -> {
			if (inPlayerMode && tile.getChildren().contains(presetCreationButton)) {
				tile.getChildren().remove(presetCreationButton.getRoot());
			} else if (!inPlayerMode && !tile.getChildren().contains(presetCreationButton)) {
				tile.getChildren().add(0, presetCreationButton.getRoot());
			}
		});
		*/
	}

	private void initializePane() {
		tile = new TilePane();
		tile.setPadding(new Insets(numericResourceBundle.getFromSizing("StandardSpacing")));
		tile.setHgap(numericResourceBundle.getFromSizing("StandardSpacing"));
		tile.setVgap(numericResourceBundle.getFromSizing("StandardSpacing"));
	}

	@Override
	public Node getRoot() {
		return tile;
	}

	@Override
	public void update(SerializableObservable so, Object obj) {
		updatePalette();
	}

	private void updatePalette() {
		for (AttributeOwnerReader preset : myPresets) {
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
		for (ImageView iv : toRemove) {
			removePresetFromPalette(iv);
		}
	}
}