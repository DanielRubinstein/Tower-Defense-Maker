package backEnd.GameData.State;

import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ComponentBuilder {
	private Component myComponent;
	private XStream xStream;
	private final String COMPONENT_IMPL="ComponentImpl";
	public ComponentBuilder(Component component) {
		myComponent = component;
		xStream = new XStream(new DomDriver());
		xStream.alias(COMPONENT_IMPL, ComponentImpl.class);
	}
	
	public Component getComponent() {
		List<SerializableObserver> oldObservers = myComponent.getAndClearObservers();
		String serializedAO = xStream.toXML(myComponent);
		myComponent.setObserverList(oldObservers);
		return (Component) xStream.fromXML(serializedAO);
	}
	
}
