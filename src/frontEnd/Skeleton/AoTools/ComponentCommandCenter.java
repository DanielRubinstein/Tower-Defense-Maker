package frontEnd.Skeleton.AoTools;

import backEnd.Attribute.AttributeOwner;
import backEnd.Attribute.AttributeOwnerReader;
import frontEnd.View;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ComponentCommandCenter extends CommandCenter {
	private static final String DEFAULT_CSS = "/resources/css/Flatter.css";
	private VBox myRoot;
	private Stage myStage;
	private Scene myScene;
	
	public ComponentCommandCenter(View view, AttributeOwnerReader attr){
		AttributeOwner attrOwn = (AttributeOwner)attr;
		AttributeCommandCenter aCC = new AttributeCommandCenter(view, attrOwn, "In-Game Component");
		myRoot = aCC.get();
		
	}
	private void generate(double x, double y) {
		myStage = new Stage();
		try{
			myScene = new Scene(myRoot);
		} catch (IllegalArgumentException e){
			myScene.setRoot(myRoot);
		}
		myScene.getStylesheets().add(DEFAULT_CSS);
		myStage.setScene(myScene);
		myStage.setTitle("Command Center");
		myStage.setX(x);
		myStage.setY(y);
		myStage.show();
	}
	
	public void launch(double x, double y) {
		generate(x,y);
	}
}
