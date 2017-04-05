package frontEnd.Skeleton.UserTools;

import java.util.function.Consumer;

import backEnd.GameData;
import javafx.scene.Node;

public class UserTools {
	private SideBar sideBar;
	private BottomRoot bottomRoot;
	
	public UserTools(){
		sideBar = new SideBar();
		bottomRoot = new BottomRoot();
		
	}
	
	public Node getSidePane(){
		return this.sideBar.getRoot();
	}
	
	public Node getBottomPane(){
		return this.bottomRoot.getRoot();
	}
	
	public void setPaneThickness(double in){
		bottomRoot.setHeight(in);
		sideBar.setWidth(in);
		
	}



}
