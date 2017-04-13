package frontEnd.CustomJavafxNodes;

import java.util.Observable;
import java.util.Observer;


import backEnd.Attribute.AttributeOwnerReader;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FrontEndAttributeOwnerImpl implements Observer, FrontEndAttributeOwner{

	private ImageView myImage;
	private String myImagePath;
	private double x;
	private double y;
	private static final String IMAGE_ATTRIBUTE = "ImageFile";
	private static final String POSITION_ATTRIBUTE = "Position";
	
	public FrontEndAttributeOwnerImpl(AttributeOwnerReader attr){
		attr.addAsListener(this);
		setUpImageView(attr);
	}
	
	private void setUpImageView(AttributeOwnerReader attr){
		myImagePath = (String) attr.getMyAttributes().get(IMAGE_ATTRIBUTE).getValue();
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(myImagePath));
		myImage = new ImageView(image);
	}

	/* (non-Javadoc)
	 * @see frontEnd.CustomJavafxNodes.FrontEndAttributeOwner#getImageView()
	 */
	@Override
	public ImageView getImageView() {
		return myImage;
	}
	/* (non-Javadoc)
	 * @see frontEnd.CustomJavafxNodes.FrontEndAttributeOwner#setXY(double, double)
	 */
	@Override
	public void setXY(double xx, double yy){
		//System.out.println(String.format("setting frontEndAttributeOwner @ (%.0f, %.0f)", xx, yy));
		myImage.setX(xx);
		myImage.setY(yy);
	}
	public void test(String path){
		myImagePath = path;
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(myImagePath));
		myImage.setImage(image);
		//System.out.println(String.format("there is frontEndAttributeOwner @ (%.0f, %.0f)", x, y));
	}
	
	/* (non-Javadoc)
	 * @see frontEnd.CustomJavafxNodes.FrontEndAttributeOwner#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		AttributeOwnerReader newAttr = (AttributeOwnerReader) o;
	
		String newImagePath = (String) newAttr.getMyAttributes().get(IMAGE_ATTRIBUTE).getValue();
		//Point2D newLoc = (Point2D) newAttr.getMyAttributes().get(POSITION_ATTRIBUTE).getValue();
		if(!newImagePath.equals(myImagePath) ){//|| (x!=newLoc.getX() && y!=newLoc.getY())){
			myImagePath = newImagePath;
			//x = newLoc.getX();
			//y = newLoc.getY();
			Image image = new Image(getClass().getClassLoader().getResourceAsStream(myImagePath));
			myImage.setImage(image);
			myImage.setX(x);
			myImage.setY(y);
		}
	}

	

	
}
