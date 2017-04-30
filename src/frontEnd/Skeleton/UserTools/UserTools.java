package frontEnd.Skeleton.UserTools;


import frontEnd.View;
import javafx.scene.Node;

public class UserTools {
	private SideBar sideBar;
	private BottomRoot bottomRoot;
	
	public UserTools(View view){
		this.sideBar = new SideBarImpl(view);
		this.bottomRoot = new BottomRootImpl(view);
		
	}
	
	public Node getSidePane(){
		return this.sideBar.getRoot();
	}
	
	public Node getBottomPane(){
		return this.bottomRoot.getRoot();
	}


}
