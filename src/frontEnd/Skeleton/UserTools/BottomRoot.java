package frontEnd.Skeleton.UserTools;
/**
 * Interface that represents the entire bottom pane of the Skeleton and includes any components that are represented here.
 * @author Tim
 *
 */
public interface BottomRoot extends SkeletonObject{

	/**
	 * Sets the height to this parameter. Width is automatically the width of the application, and the parameter width is
	 * the width of the side pane, to be used if certain objects need to be resized. 
	 * @param height
	 */
	public void setDimensions(double width, double height);
}
