package ModificationFromUser.AttributeOwner;

import java.util.List;
import java.util.Observer;
import java.lang.reflect.Method;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import ModificationFromUser.ModificationFromUser;
import backEnd.ModelImpl;
import backEnd.Attribute.AttributeOwner;
import backEnd.Attribute.AttributeOwnerSerializer;
import backEnd.GameData.GameData;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.Tile;
import backEnd.GameData.State.TileImpl;
import backEnd.Mode.ModeException;
import javafx.geometry.Point2D;

/**
 * Used for adding a preset attribute owner to the grid. Preset attribute owner
 * is passed as parameter in constructor
 * 
 * @author Derek
 *
 */
public class Modification_Add_PaletteToGrid implements ModificationFromUser {
	private AttributeOwner newAttrOwn;
	private Point2D location;
	private ModelImpl myModel;
	public static final String DESCRIPTION_TILE = "Replace Tile";
	public static final String DESCRIPTION_COMPONENT = "Add Component";
	public static final String DESCRIPTION_ERROR = "Not a recognized Attribute Owner";

	public Modification_Add_PaletteToGrid(AttributeOwner preset, Point2D location) {
		this.newAttrOwn = preset;
		this.location = location;

	}

	@Override
	public void invoke(ModelImpl model) throws Exception {
		myModel = model;
		
		AttributeOwnerSerializer attributeOwnerSerializer = new AttributeOwnerSerializer();
		AttributeOwner cleanAO = attributeOwnerSerializer.createCopy(newAttrOwn);
		cleanAO.setAttributeValue("Position", location);
		try {
			Method add = Modification_Add_PaletteToGrid.class.getDeclaredMethod("add", cleanAO.getClass());
			add.setAccessible(true);
			add.invoke(this, cleanAO);
		} catch (NoSuchMethodException e) {
			System.out.println("in Modification_Add_PaletteToGrid, No method found, ugh");
			// do nothing
			// this means the thing being put in attribute command center is a tile
		} catch (ModeException e){
			throw e;
		} catch (Exception e) {
			// something went wrong
			System.out.println("Something went wrong in Modification_Add_PaletteToGrid");
			e.printStackTrace();
		}
	}
	
	private void add(TileImpl tile){
		switch (myModel.getMode().getUserMode()) {
		case "AUTHOR":
			myModel.getState().getTileGrid().setTileByScreenPosition(tile,location);
			break;

		case "PLAYER":
			throw new ModeException(myModel.getMode(), DESCRIPTION_TILE);
		}
	}
	
	private void add(Component component){
		myModel.getState().getComponentGraph().addComponentToGrid(component, location);
		switch (myModel.getMode().getUserMode()) {
		case "AUTHOR":
			// do nothing
			break;
		case "PLAYER":
			// for future, do something
			// deduct price
			break;
		}	
	}
}