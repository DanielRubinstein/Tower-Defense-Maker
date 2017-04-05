package frontEnd.Skeleton;

import java.util.function.Consumer;

import backEnd.GameData;
import frontEnd.Skeleton.UserTools.UserTools;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Skeleton {
	
	private BorderPane myRoot;
	private Scene myScene;
	private UserTools userTools;
	public static final double MENU_HEIGHT = 500d;
	public static final double MENU_WIDTH = 500d;
	
	public Skeleton(){
		myRoot = new BorderPane();
		
		Canvas canvas = new Canvas();
		canvas.setSize(300, 400);
		myRoot.setCenter(canvas.getRoot());
		
		userTools = new UserTools();
		userTools.setPaneThickness(100);
		myRoot.setRight(userTools.getSidePane());
		myRoot.setBottom(userTools.getBottomPane());
		
		
	}
	private void align(Double size1, Double size2){
		myRoot.setMinWidth(size1);
		myRoot.setMinHeight(size2);
   	 	myScene = new Scene(myRoot, size1, size2);
	}

	public void display(Stage stage) {
		align(MENU_WIDTH,MENU_HEIGHT);
		stage.setScene(myScene);
		//createBars(stage);
		stage.show();
		
	}



}
