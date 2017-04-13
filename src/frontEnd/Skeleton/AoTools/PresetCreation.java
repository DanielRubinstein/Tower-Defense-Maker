package frontEnd.Skeleton.AoTools;

import backEnd.Attribute.AttributeOwnerReader;
import frontEnd.View;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PresetCreation{
	public static final String DEFAULT_CSS = "/resources/css/Flatter.css";
	private Stage myStage;
	private Scene myScene;
	private VBox myRoot;
	
	public PresetCreation(View myView, AttributeOwnerReader obj){
		AttributeCommandCenter aCC = new AttributeCommandCenter(myView, obj);
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
