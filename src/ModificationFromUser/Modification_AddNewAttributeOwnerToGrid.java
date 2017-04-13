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
 * Used for adding a new attribute owner to the grid. New attribute owner is passed as parameter in constructor
 * 
 * @author Derek
 *
 */
public class Modification_AddNewAttributeOwnerToGrid implements ModificationFromUser {
	private AttributeOwner newAttrOwn;
	private Point2D location;
	public static final String DESCRIPTION_TILE = "Replace Tile";
	public static final String DESCRIPTION_COMPONENT = "Add Component";	
	public static final String DESCRIPTION_ERROR = "Not a recognized Attribute Owner";	
	

	public Modification_AddNewAttributeOwnerToGrid(AttributeOwner toAdd) {
		this.newAttrOwn = toAdd;
		this.location = (Point2D)toAdd.getAttribute("Position").getValue();

	}

	@Override
	public void invoke(ModelImpl myModel) throws Exception {
		if (newAttrOwn instanceof Tile){
			switch (myModel.getMode().getUserMode()) {
			case AUTHOR:
				myModel.getState().getTileGrid().setTile((Tile) newAttrOwn, location);
				break;
			case PLAYER:
				 throw new ModeException(myModel.getMode(), DESCRIPTION_TILE);
			}	
		} else if (newAttrOwn instanceof Component){
			myModel.getState().getComponentGraph().addComponentToGrid((Component) newAttrOwn, location);

		} else {
			// can't be reached
			// FIXME AHHHHH
			throw new Exception(DESCRIPTION_ERROR);
		}
		
		
		
	}
}
