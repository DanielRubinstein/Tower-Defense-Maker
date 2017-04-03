package frontEnd.Skeleton.View;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class View {
	
	public View(){
		BorderPane root = new BorderPane();
		
		SideBar sideBar = new SideBar();
		BottomBar bottomBar = new BottomBar();
		Canvas canvas = new Canvas();
		
		root.setRight(sideBar);
		root.setBottom(bottomBar);
		root.setCenter(canvas.getRoot());
		
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
	}

}
