package ModificationFromUser;

import java.util.List;
import java.util.Observer;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import backEnd.ModelImpl;
import backEnd.Attribute.AttributeOwner;
import backEnd.GameData.GameData;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.Tile;
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
	public void invoke(ModelImpl myModel) throws Exception {
		List<Observer> oldObservers = newAttrOwn.getAndClearObservers();
		String serializedAO = xStream.toXML(newAttrOwn);
		newAttrOwn.setObserverList(oldObservers);
		if (newAttrOwn instanceof Tile) {
			switch (myModel.getMode().getUserMode()) {
			case "AUTHOR":
				Tile newTile = (Tile) xStream.fromXML(serializedAO);
				newTile.setAttributeValue("Position", location);
				myModel.getState().getTileGrid().setTileByScreenPosition(newTile,location);
				break;

			case "PLAYER":
				throw new ModeException(myModel.getMode(), DESCRIPTION_TILE);
			}
		} else if (newAttrOwn instanceof Component) {
			Component newComp = (Component) xStream.fromXML(serializedAO);
			newComp.setAttributeValue("Position", location);
			myModel.getState().getComponentGraph().addComponentToGrid(newComp, location);
		} else {
			// can't be reached
			// FIXME AHHHHH
			throw new Exception(DESCRIPTION_ERROR);
		}

	}
}
