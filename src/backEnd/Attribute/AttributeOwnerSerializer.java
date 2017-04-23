package backEnd.Attribute;

import java.util.List;
import java.util.Observer;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import backEnd.GameData.State.Component;
import backEnd.GameData.State.Tile;

public class AttributeOwnerSerializer {
	private XStream xStream;
	
	public AttributeOwnerSerializer(){
		xStream = new XStream(new DomDriver());
		xStream.alias("Component", Component.class);
		xStream.alias("Tile", Tile.class);
	}
	
	public AttributeOwner createCopy(AttributeOwner oldAO){
		List<Observer> oldObservers = oldAO.getAndClearObservers();
		String serializedAO = xStream.toXML(oldAO);
		oldAO.setObserverList(oldObservers);
		return (AttributeOwner) xStream.fromXML(serializedAO);
	}

}