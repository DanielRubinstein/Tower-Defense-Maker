package backEnd;

import backEnd.GameData.State.Component;
import backEnd.GameData.State.ComponentImpl;

public interface BankControllerReader {

	String getComponentName(ComponentImpl component);
	
	Component getComponent(String componentName);

}