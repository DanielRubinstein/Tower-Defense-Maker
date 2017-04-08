package frontEnd.Skeleton.UserTools;

import java.util.function.Consumer;

import backEnd.GameData.GameData;
import frontEnd.ViewEditor;
import javafx.scene.Node;

public class UserTools {
	private SideBar sideBar;
	private BottomRoot bottomRoot;
	
	public UserTools(ViewEditor view){
		sideBar = new SideBarImpl(view);
		bottomRoot = new BottomRootImpl(view);
		
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
