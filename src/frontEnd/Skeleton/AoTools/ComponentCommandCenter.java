package frontEnd.Skeleton.AoTools;

import backEnd.GameData.State.Component;
import frontEnd.View;
import javafx.scene.Node;

public class ComponentCommandCenter extends CommandCenter{
	private Component myComponent;
	
	public ComponentCommandCenter(View view, Component component){
		super(view);
		myComponent = component;
	}

	@Override
	public void launch(double x, double y) {
		tabPane.getTabs().add(createAttributeOwnerTab(myComponent));
		generate(x,y);
	}

}
