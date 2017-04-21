package ModificationFromUser;

import java.util.List;
import java.util.Observer;
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
import javafx.geometry.Point2D;

/**
 * Used for adding a preset attribute owner to the grid. Preset attribute owner
 * is passed as parameter in constructor
 * 
 * @author Derek
 *
 */
public class Modification_AddPresetAttributeOwnerToGrid implements ModificationFromUser {
	private AttributeOwner newAttrOwn;
	private Point2D location;
	private XStream xStream;
	private ModelImpl myModel;
	public static final String DESCRIPTION_TILE = "Replace Tile";
	public static final String DESCRIPTION_COMPONENT = "Add Component";
	public static final String DESCRIPTION_ERROR = "Not a recognized Attribute Owner";

	public Modification_AddPresetAttributeOwnerToGrid(AttributeOwner preset, Point2D location) {
		this.newAttrOwn = preset;
		this.location = location;
		xStream = new XStream(new DomDriver());
		xStream.alias("Component", Component.class);
		xStream.alias("Tile", Tile.class);

	}

	@Override
	public void invoke(ModelImpl model) throws Exception {
		myModel = model;
		
		List<Observer> oldObservers = newAttrOwn.getAndClearObservers();
		String serializedAO = xStream.toXML(newAttrOwn);
		newAttrOwn.setObserverList(oldObservers);
		AttributeOwner cleanAO = (AttributeOwner) xStream.fromXML(serializedAO);
		cleanAO.setAttributeValue("Position", location);
		System.out.println(cleanAO.getClass());
		try {
			Method add = Modification_AddPresetAttributeOwnerToGrid.class.getDeclaredMethod("add", cleanAO.getClass());
			add.setAccessible(true);
			add.invoke(this, cleanAO);
		} catch (NoSuchMethodException e) {
			System.out.println("in Modification_AddNewPresetAttributeOwnerToGrid, No method found, ugh");
			// do nothing
			// this means the thing being put in attribute command center is a tile
		} catch (Exception e) {
			// something went wrong
			System.out.println("Something went wrong in Modification_AddNewPresetAttributeOwnerToGrid");
			// TODO add exception?
		}
	}
	
	private void add(TileImpl tile){
		switch (myModel.getMode().getUserMode()) {
		case AUTHOR:
			myModel.getState().getTileGrid().setTileByScreenPosition(tile,location);
			break;

		case PLAYER:
			throw new ModeException(myModel.getMode(), DESCRIPTION_TILE);
		}
	}
	
	private void add(Component component){
		myModel.getState().getComponentGraph().addComponentToGrid(component, location);
	}
}
