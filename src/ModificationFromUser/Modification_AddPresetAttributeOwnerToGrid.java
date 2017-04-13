package ModificationFromUser;

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
 * Used for adding a preset attribute owner to the grid. Preset attribute owner is passed as parameter in constructor
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
		String serializedAO = xStream.toXML(newAttrOwn);
		if (newAttrOwn instanceof Tile){
			switch (myModel.getMode().getUserMode()) {
			case AUTHOR:

				myModel.getState().getTileGrid().setTile((Tile) xStream.fromXML(serializedAO), location);
				break;

			case PLAYER:
				 throw new ModeException(myModel.getMode(), DESCRIPTION_TILE);
			}	
		} else if (newAttrOwn instanceof Component){

			System.out.println(myModel.getState()+ "   "  +newAttrOwn.getAttribute("Position").getValue());
			newAttrOwn.setAttributeValue("Position", location);
			System.out.println(myModel.getState().getComponentGraph() + "  "  +newAttrOwn.getAttribute("Position").getValue());
			System.out.println((Component) xStream.fromXML(serializedAO));


			System.out.println(myModel.getState() + "     "   +location + "   " +newAttrOwn.getAttribute("Position").getValue());
			//myModel.getState().getComponentGraph().addComponentToGrid((Component) xStream.fromXML(serializedAO), location);
			
			myModel.getState().getComponentGraph().addComponentToGrid((Component) newAttrOwn, location);
			

			//newAttrOwn.addAttribute(attrName, newAttr);
		} else {
			// can't be reached
			// FIXME AHHHHH
			throw new Exception(DESCRIPTION_ERROR);
		}
		
		
		
	}
}
