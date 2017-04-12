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

	/**
	 * Sets the width of the side and height of the bottom
	 * @param width of the side pane
	 * @param height of the bottom pane
	 */
	public void setBottomAndSideDimensions(double width, double height){
		System.out.println("in user tools have width and height "+width  + "   "+height);
		this.bottomRoot.setDimensions(width, height);
		this.sideBar.setWidth(width);
		
	}



}
