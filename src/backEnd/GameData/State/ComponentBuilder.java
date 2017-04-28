package backEnd.GameData.State;

import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ComponentBuilder {
	private ComponentImpl myComponent;
	private XStream xStream;
	
	public ComponentBuilder(ComponentImpl component) {
		myComponent = component;
		xStream = new XStream(new DomDriver());
		xStream.alias("ComponentImpl", ComponentImpl.class);
	}
	
	public ComponentImpl getComponent() {
		List<SerializableObserver> oldObservers = myComponent.getAndClearObservers();
		String serializedAO = xStream.toXML(myComponent);
		myComponent.setObserverList(oldObservers);
		return (ComponentImpl) xStream.fromXML(serializedAO);
	}
	
}
