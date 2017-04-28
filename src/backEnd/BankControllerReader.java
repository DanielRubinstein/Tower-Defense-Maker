package backEnd;

import backEnd.GameData.State.ComponentImpl;

public interface BankControllerReader {

<<<<<<< HEAD
	String getComponentName(Component component);
=======
	String getComponentName(ComponentImpl component);
	
	ComponentImpl getComponent(String componentName);
>>>>>>> 24bb9c3fa1bf8e7df3482775321bbe43dd9b7326

}