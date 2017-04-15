package frontEnd.Skeleton.AoTools;

import backEnd.Attribute.AttributeOwner;
import backEnd.Attribute.AttributeOwnerReader;
import backEnd.GameData.State.Tile;
import frontEnd.View;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ComponentCommandCenter extends CommandCenter {
	private AttributeOwner attrOwn;
	private View myView;
	private VBox myRoot;
	
	public ComponentCommandCenter(View view, AttributeOwnerReader attr){
		myView = view;
		attrOwn = (AttributeOwner)attr;
	}
	
	public void launch(double x, double y) {
		Stage stage = new Stage();
		AttributeCommandCenter aCC = new AttributeCommandCenter(myView, stage, attrOwn, "In-Game Component");
		if(! (attrOwn instanceof Tile)){
			aCC.addSubmitButton(attrOwn);
		}
		myRoot = aCC.get();
		
		generate(x,y, stage, (Parent) myRoot);
	}
}
