package frontEnd.Skeleton;

import frontEnd.Skeleton.UserTools.BottomRoot;
import frontEnd.Skeleton.UserTools.SideBar;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Skeleton {
	
	private BorderPane myRoot;
	private Scene myScene;
	private SideBar mySideBar;
	private BottomRoot myBottom;
	public static final double MENU_HEIGHT = 500d;
	public static final double MENU_WIDTH = 500d;
	
	public Skeleton(){
		myRoot = new BorderPane();
		Canvas canvas = new Canvas();
		initializeSide();
		initializeBottom();
		myRoot.setCenter(canvas.getRoot());
	}
	private void initializeSide(){
		mySideBar = new SideBar();
		mySideBar.setSideBarWeidth(MENU_WIDTH*0.2);
		myRoot.setRight(mySideBar.getRoot());
	}
	private void initializeBottom(){
		myBottom = new BottomRoot(MENU_WIDTH*0.2,MENU_HEIGHT*0.2);
		myRoot.setBottom(myBottom.getRoot());
	}
	
	private void align(Double size1, Double size2){
		myRoot.setMinWidth(size1);
		myRoot.setMinHeight(size2);
   	 	myScene = new Scene(myRoot, size1, size2);
	}

	public void display(Stage stage) {
		align(MENU_WIDTH,MENU_HEIGHT);
		stage.setScene(myScene);
		stage.show();
		
	}


}
