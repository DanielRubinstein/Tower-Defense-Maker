package frontEnd.Skeleton.AoTools;

import backEnd.Attribute.AttributeOwner;
import backEnd.Attribute.AttributeOwnerReader;
import frontEnd.View;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import resources.constants.StringResourceBundle;

public class GenericCommandCenter implements CommandCenter{
	private StringResourceBundle stringResourceBundle = new StringResourceBundle();
	private View myView;
	private VBox myRoot;
	private AttributeOwner obje;
	
	public GenericCommandCenter(View view, AttributeOwnerReader obj){
		myView = view;
		obje = (AttributeOwner) obj;
	}
	
	public void generate(double x, double y, Stage myStage, Parent myRoot) {
		Scene myScene = new Scene(myRoot);
		myScene.getStylesheets().add(stringResourceBundle.getDefaultCSS());
		myStage.setScene(myScene);
		myStage.setTitle("Command Center");
		myStage.setX(x);
		myStage.setY(y);
		myStage.show();
	}
	
	public void launch(String title, double x, double y) {
		Stage stage = new Stage();
		AttributeCommandCenter aCC = new AttributeCommandCenter(myView, stage, obje, title);
		myRoot = aCC.get();
		generate(x,y, stage, (Parent) myRoot);
	}

}
