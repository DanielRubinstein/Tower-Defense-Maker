package backEnd.GameEngine;

import java.util.Map;
import java.util.Observable;

public class DeathBehavior implements Behavior {
	private AttributeData myAttributes;

	
	@Override
	public void update(Observable newData, Object arg) {
		myAttributes = (AttributeData) newData;
	}

	@Override
	public void execute(Map<String, Attribute<?>> myAttributes) {
		// TODO Auto-generated method stub
		
	}

}
