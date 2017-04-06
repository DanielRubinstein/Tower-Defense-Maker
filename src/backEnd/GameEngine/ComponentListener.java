package backEnd.GameEngine;

import java.util.Map;

public interface ComponentListener {
	public void updateAttributes(Map<String,Attribute<?>> myAttributes);
}
