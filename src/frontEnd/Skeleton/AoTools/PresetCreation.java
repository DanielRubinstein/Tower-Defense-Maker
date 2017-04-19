package frontEnd.Skeleton.AoTools;

import backEnd.Attribute.AttributeOwner;
import backEnd.Attribute.AttributeOwnerReader;
import frontEnd.View;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PresetCreation extends CommandCenter{
	private View myView;
	private VBox myRoot;
	private AttributeOwner obje;
	
	public PresetCreation(View view, AttributeOwnerReader obj){
		myView = view;
		obje = (AttributeOwner) obj;
		
	}
	
	
	public void add(Node n){
		myRoot.getChildren().add(n);
	}
	
	public void launch(double x, double y) {
		Stage stage = new Stage();
		AttributeCommandCenter aCC = new AttributeCommandCenter(myView, stage, obje, "Preset Creation");
		myRoot = aCC.get();
		generate(x,y, stage, (Parent) myRoot);
	}

}
