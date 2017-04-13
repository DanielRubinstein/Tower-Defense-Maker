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

public class Modification_AddAttributeOwner implements ModificationFromUser {
	private AttributeOwner newAttrOwn;
	private Point2D location;
	private XStream xStream;
	public static final String DESCRIPTION_TILE = "Replace Tile";
	public static final String DESCRIPTION_COMPONENT = "Add Component";	
	public static final String DESCRIPTION_ERROR = "Not a recognized Attribute Owner";	
	

	public Modification_AddAttributeOwner(AttributeOwner attrOwn, Point2D location) {
		this.newAttrOwn = attrOwn;
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
			case PLAYER:
				 throw new ModeException(myModel.getMode(), DESCRIPTION_TILE);
			}	
		} else if (newAttrOwn instanceof Component){
			System.out.println(myModel.getState());
			System.out.println(myModel.getState().getComponentGraph());
			System.out.println((Component) xStream.fromXML(serializedAO));
			System.out.println(myModel.getState());
			myModel.getState().getComponentGraph().addComponentToGrid((Component) xStream.fromXML(serializedAO), location);
			//newAttrOwn.addAttribute(attrName, newAttr);
		} else {
			// can't be reached
			// FIXME AHHHHH
			throw new Exception(DESCRIPTION_ERROR);
		}
		
		
		
	}
}
