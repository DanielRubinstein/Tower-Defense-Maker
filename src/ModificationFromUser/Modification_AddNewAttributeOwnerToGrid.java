package ModificationFromUser;

import java.lang.reflect.Method;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import backEnd.ModelImpl;
import backEnd.Attribute.AttributeOwner;
import backEnd.GameData.GameData;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.Tile;
import backEnd.GameData.State.TileImpl;
import backEnd.Mode.ModeException;
import frontEnd.Skeleton.AoTools.AttributeCommandCenter;
import javafx.geometry.Point2D;

/**
 * Used for adding a new attribute owner to the grid. New attribute owner is passed as parameter in constructor
 * 
 * @author Derek
 *
 */
public class Modification_AddNewAttributeOwnerToGrid implements ModificationFromUser {
	private AttributeOwner newAttrOwn;
	private Point2D location;
	private ModelImpl myModel;
	public static final String DESCRIPTION_TILE = "Replace Tile";
	public static final String DESCRIPTION_COMPONENT = "Add Component";	
	public static final String DESCRIPTION_ERROR = "Not a recognized Attribute Owner";	
	

	public Modification_AddNewAttributeOwnerToGrid(AttributeOwner toAdd) {
		this.newAttrOwn = toAdd;
		this.location = (Point2D)toAdd.getAttribute("Position").getValue();

	}

	@Override
	public void invoke(ModelImpl model) throws Exception {
		myModel = model;
		try {
			Method add = Modification_AddNewAttributeOwnerToGrid.class.getDeclaredMethod("addAttributeOwnerToGrid", newAttrOwn.getClass());
			add.setAccessible(true);
			add.invoke(this, newAttrOwn);
		} catch (NoSuchMethodException e) {
			System.out.println("in Modification_AddNewAttributeOwnerToGrid, No method found, ugh");
			// do nothing
			// this means the thing being put in attribute command center is a tile
		} catch (Exception e) {
			// something went wrong
			System.out.println("Something went wrong in Modification_AddNewAttributeOwnerToGrid");
			// TODO add exception?
		}
	}
	
	private void addAttributeOwnerToGrid(TileImpl tile){
		switch (myModel.getMode().getUserMode()) {
		case AUTHOR:
			myModel.getState().getTileGrid().setTileByGridPosition(tile, (int) location.getX(), (int) location.getY());
			break;
		case PLAYER:
			 throw new ModeException(myModel.getMode(), DESCRIPTION_TILE);
		}	
	}
	
	private void addAttributeOwnerToGrid(Component component){
		myModel.getState().getComponentGraph().addComponentToGrid(component, location);
		switch (myModel.getMode().getUserMode()) {
		case AUTHOR:
			// for future, do something
			break;
		case PLAYER:
			// for future, do something
			break;
		}	
	}
	
}
