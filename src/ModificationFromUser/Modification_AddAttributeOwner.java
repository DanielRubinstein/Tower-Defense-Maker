package ModificationFromUser;

import backEnd.ModelImpl;
import backEnd.Attribute.AttributeOwner;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.Tile;
import backEnd.Mode.ModeException;
import javafx.geometry.Point2D;

public class Modification_AddAttributeOwner implements ModificationFromUser {
	private AttributeOwner newAttrOwn;
	private Point2D location;
	public static final String DESCRIPTION_TILE = "Replace Tile";
	public static final String DESCRIPTION_COMPONENT = "Add Component";	

	public Modification_AddAttributeOwner(AttributeOwner attrOwn, Point2D location) {
		this.newAttrOwn = attrOwn;
		this.location = location;

	}

	@Override
	public void invoke(ModelImpl myModel) throws Exception {
		if (newAttrOwn instanceof Tile){
			switch (myModel.getMode().getUserMode()) {
			case AUTHOR:
				myModel.getState().getTileGrid().setTile((Tile) newAttrOwn, location);
			case PLAYER:
				 throw new ModeException(myModel.getMode(), DESCRIPTION_TILE);
			}	
		} else if (newAttrOwn instanceof Component){
			myModel.getState().getComponentGraph().addComponentToGrid((Component) newAttrOwn, location);
			/**
			 * FIXME should parameter be newComp.clone() ?
			 */
		} else {
			// can't be reached
			// FIXME AHHHHH
			throw new Exception();
		}
		
		
		
	}
}
