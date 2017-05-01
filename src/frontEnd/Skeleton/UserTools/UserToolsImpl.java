package frontEnd.Skeleton.UserTools;


import frontEnd.View;
import javafx.scene.Node;

public class UserToolsImpl implements UserTools {
	private SkeletonObject sideBar;
	private SkeletonObject bottomRoot;
	
	public UserToolsImpl(View view){
		this.sideBar = new SideBarImpl(view);
		this.bottomRoot = new BottomRootImpl(view);
		
	}
	
	/* (non-Javadoc)
	 * @see frontEnd.Skeleton.UserTools.UserTools#getSidePane()
	 */
	@Override
	public Node getSidePane(){
		return this.sideBar.getRoot();
	}
	
	/* (non-Javadoc)
	 * @see frontEnd.Skeleton.UserTools.UserTools#getBottomPane()
	 */
	@Override
	public Node getBottomPane(){
		return this.bottomRoot.getRoot();
	}


}
