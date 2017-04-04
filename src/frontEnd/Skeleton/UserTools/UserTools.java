package frontEnd.Skeleton.UserTools;

import javafx.scene.layout.BorderPane;

public class UserTools {
	private SideBar sideBar;
	private BottomRoot bottomRoot;
	
	public UserTools(){
		sideBar = new SideBar();
		bottomRoot = new BottomRoot();
		
	}
	
	public void addToBorderPane(BorderPane root){
		root.setRight(sideBar.getRoot());
		root.setBottom(bottomRoot.getRoot());
	}
	
	public void setBottomPaneHeight(double in){
		bottomRoot.setBottomBarHeight(in);
	}

}
