package backEnd;

import backEnd.GameData.State.ComponentImpl;

public interface BankControllerReader {

	String getComponentName(ComponentImpl component);
	
	ComponentImpl getComponent(String componentName);

}