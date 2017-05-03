package frontEnd.Skeleton.AoTools.AttributeVisualization;

import javafx.scene.Node;

/**
 * This interface serves as a way to visualize all attribute types of the
 * project. If any attribute types are added a new method is required here.
 * 
 * @author Miguel Anderson
 *
 */

public interface AttributeVisualization {

	/**
	 * The string of the format of the rest of the methods in this interface
	 * 
	 * @return
	 */
	public String getMethodNameFormat();

	/**
	 * The viewer or editor for image attributes, will show the image to the
	 * user.
	 * 
	 * @return
	 */
	public Node getIMAGE();

	/**
	 * The viewer or editor for Double attributes, will show the numeric value
	 * to the user.
	 * 
	 * @return
	 */
	public Node getDOUBLE();

	/**
	 * The viewer or editor for component attributes, will show the name of the
	 * preset and the image to the user.
	 * 
	 * @return
	 */
	public Node getCOMPONENT();

	/**
	 * The viewer or editor for position attributes, will show the x and y value
	 * to the user.
	 * 
	 * @return
	 */
	public Node getPOSITION();

	/**
	 * The viewer or editor for Integer attributes, will show the numeric value
	 * to the user.
	 * 
	 * @return
	 */
	public Node getINTEGER();

	/**
	 * The viewer or editor for Boolean attributes, will show the value to the
	 * user.
	 * 
	 * @return
	 */
	public Node getBOOLEAN();

	/**
	 * The viewer or editor for Stringlist attributes, will show the string
	 * value to the user. The options for this attribute is a list of strings.
	 * They can be dyanimic or static (decided by an XML)
	 * 
	 * @return
	 */
	public Node getSTRINGLIST();

	/**
	 * The viewer or editor for Editable string attributes, will show the value
	 * to the user. This can be a name, or things decided by the user
	 * 
	 * @return
	 */
	public Node getEDITABLESTRING();

}
