package ModificationFromUser.AttributeOwner.ReflectionMethods;

import java.util.List;
import java.util.Optional;

import backEnd.Model;
import backEnd.Attribute.AttributeOwnerReader;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.Tile;
import backEnd.GameData.State.TileCorners;
import backEnd.GameData.State.TileImpl;
import backEnd.GameEngine.Engine.Coordinates;
import backEnd.Mode.ModeException;
import frontEnd.CustomJavafxNodes.ErrorDialog;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import resources.constants.StringResourceBundle;

/**
 * This is the reflection class used by Modification_Add_PaletteToGrid and
 * Modification_Add_StraightToGrid to add components and tiles to the screen
 * grid.
 * 
 * @author Miguel Anderson
 *
 */

public class Modification_Add_ToGrid_Methods {
	private Point2D location;
	private Model myModel;
	public static final String DESCRIPTION_TILE = "Replace Tile";
	public static final String DESCRIPTION_COMPONENT = "Add Component";
	public static final String DESCRIPTION_ERROR = "Not a recognized Attribute Owner";
	private static final StringResourceBundle strResources = new StringResourceBundle();
	private Coordinates myCoordinates = new Coordinates();

	public Modification_Add_ToGrid_Methods(Model model, Point2D location) {
		this.myModel = model;
		this.location = location;
	}

	public void add(TileImpl tile) throws ModeException {
		switch (myModel.getMode().getUserMode()) {
		case "AUTHOR":
			myModel.getState().getTileGrid().setTileByScreenPosition(tile, location);
			break;
		case "PLAYER":
			throw new ModeException(myModel.getMode(), DESCRIPTION_TILE);
		}
	}

	public void add(Component component) {

		switch (myModel.getMode().getUserMode()) {
		case "AUTHOR":
			addComponentToGrid(component);
			break;
		case "PLAYER":
			int price = (int) component.getAttribute(strResources.getFromAttributeNames("Price")).getValue();
			int money = (int) myModel.getPlayerStatusReader().getProperty("Money").get();
			if(price > money){
				new ErrorDialog().create(strResources.getFromErrorMessages("Not_Enough_Money_Header"), 
						String.format(strResources.getFromErrorMessages("Not_Enough_Money_Header"), price));
			}
			else{
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle(strResources.getFromStringConstants("StoreBuyConfirmation"));
				String priceFormat = strResources.getFromStringConstants("StorePurchaseFormat");
				alert.setHeaderText(String.format(priceFormat, price));
				alert.setContentText(strResources.getFromStringConstants("StoreQuestion"));
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK || result.get() == ButtonType.CANCEL) {
					alert.close();
				}
				if (result.get() == ButtonType.OK) {
					if (checkBuildCapacity(component)) {
						addComponentToGrid(component);
						myModel.getModifiablePlayerStatus().decrementStatusItem("Money", price);
					}
				}
			}
			break;
		}
	}

	private boolean checkBuildCapacity(Component component) {
		Tile tile = myModel.getState().getTileGrid().getTileByScreenPosition(location);
		double tileWidth = myModel.getState().getTileGrid().getTileWidth();
		double tileHeight = myModel.getState().getTileGrid().getTileHeight();
		List<Component> compList = myModel.getState().getComponentGraph()
				.getComponentsByTileCorners(new TileCorners(location, tileWidth, tileHeight));
		if (compList.size() < tile.<Integer>getAttribute(strResources.getFromAttributeNames("BuildCapacity"))
				.getValue()) {
			component.setAttributeValue("SaveToTemplate", false);
			return true;
		}
		else{
			new ModeException(myModel.getMode(), String.format(strResources.getFromErrorMessages("Tile_Not_Buildable"), 
					tile.<Integer>getAttribute(strResources.getFromAttributeNames("BuildCapacity")).getValue()));
		}
		return false;
	}

	private void addComponentToGrid(Component component) {
		component.setPreviousMovement(myCoordinates.getNullMovement()); // components
																		// should
																		// not
																		// have
																		// any
																		// pre-existing
																		// movement
		myModel.getState().getComponentGraph().addComponentToGrid(component, location);
	}
}