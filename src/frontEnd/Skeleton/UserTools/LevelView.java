package frontEnd.Skeleton.UserTools;

import frontEnd.View;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LevelView {
	
	private View myView;
	private GridPane myRoot;
	private Stage myStage;
	
	public LevelView(View view, Stage parentStage){
		myView = view;
		myRoot = new GridPane();
		//myRoot.prefWidthProperty().bind(readOnlyDoubleProperty);
		myRoot.setPadding(new Insets(20, 20, 20, 20));
		myRoot.setVgap(20);
		myRoot.setHgap(20);
		createStructureBoxes();
	}
	
	public void launch(){
		myStage = new Stage();
		Scene myScene = new Scene(myRoot);
		myStage.setScene(myScene);
		myStage.show();
	}
	
	public void createStructureBoxes(){
		Label title = new Label("Game Structure");
		myRoot.add(title, 1, 0);
		
		
		
	}
	public void createSingleBox(){
		
	}
}
