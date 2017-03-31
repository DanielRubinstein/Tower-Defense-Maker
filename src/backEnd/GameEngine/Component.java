package backEnd.GameEngine;

public interface Component {

	public Behavior getBehavior(String behaviorType);
	
	public Attribute getAttribute(String attributeType);
}
