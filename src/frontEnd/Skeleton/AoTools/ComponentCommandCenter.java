package frontEnd.Skeleton.AoTools;

import backEnd.GameData.State.Component;

public class ComponentCommandCenter extends CommandCenter{
	private Component myComponent;
	
	public ComponentCommandCenter(Component component){
		myComponent = component;
	}

	@Override
	public void launch(double x, double y) {
		tabPane.getTabs().add(createAttributeOwnerTab(myComponent));
		generate(x,y);
	}

}
