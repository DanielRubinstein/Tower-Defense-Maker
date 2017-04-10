package backEnd.Attribute;

import java.util.Map;

import backEnd.GameEngine.Attribute;

public interface AttributeOwner {
	
	public void addAttribute(String name, Attribute<?> value);
	
	public Attribute<?> getAttribute(String name);
	
	public Map<String, Attribute<?>> getAttributeMap();
	
	public boolean hasAttribute(String name);

}
