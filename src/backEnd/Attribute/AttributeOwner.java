package backEnd.Attribute;

import java.util.Collection;

public interface AttributeOwner {
	
	public void addAttribute(String name, Attribute<?> value);
	
	public Attribute<?> getAttribute(String name);
	
	public Collection<Attribute<?>> getAttributeList();
	
	public void setAttributeList(Collection<Attribute<?>> newAttri);
	
	public boolean hasAttribute(String name);

}
