package frontEnd.Skeleton.View;

import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class View {
	
	private BorderPane myRoot;
	private Scene myScene;
	public static final double MENU_HEIGHT = 500d;
	public static final double MENU_WIDTH = 500d;
	
	public View(){
		myRoot = new BorderPane();
		SideBar sideBar = new SideBar();
		BottomRoot bottomRoot = new BottomRoot();
		Canvas canvas = new Canvas();
		bottomRoot.setBottomBarHeight(MENU_HEIGHT*0.2);
		myRoot.setRight(sideBar.getRoot());
		myRoot.setBottom(bottomRoot.getRoot());
		myRoot.setCenter(canvas.getRoot());
		

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
	private void createSeparator(){

		Separator separator = new Separator();
		separator.setOrientation(Orientation.VERTICAL);
		//separator.setMaxHeight(bottomBar.getHeight());
		separator.setHalignment(HPos.RIGHT);

		
		
	}

}
