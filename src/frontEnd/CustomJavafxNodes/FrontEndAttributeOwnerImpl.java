package frontEnd.CustomJavafxNodes;

import java.util.Observable;
import java.util.Observer;


import backEnd.Attribute.AttributeOwnerReader;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FrontEndAttributeOwnerImpl implements Observer{

	private ImageView myImage;
	private String myImagePath;
	private double x;
	private double y;
	private static final String IMAGE_ATTRIBUTE = "ImageFile";
	private static final String POSITION_ATTRIBUTE = "Locattion";
	
	public FrontEndAttributeOwnerImpl(AttributeOwnerReader attr){
		setUpImageView(attr);
	}
	
	private void setUpImageView(AttributeOwnerReader attr){
		myImagePath = (String) attr.getMyAttributes().get(IMAGE_ATTRIBUTE).getValue();
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(myImagePath));
		myImage = new ImageView(image);
	}

	public ImageView getImageView() {
		return myImage;
	}
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
	
	@Override
	public void update(Observable o, Object arg) {
		AttributeOwnerReader newAttr = (AttributeOwnerReader) o;
		
		String newImagePath = (String) newAttr.getMyAttributes().get(IMAGE_ATTRIBUTE).getValue();
		Point2D newLoc = (Point2D) newAttr.getMyAttributes().get(POSITION_ATTRIBUTE).getValue();
		
		if(!newImagePath.equals(myImagePath) || (x!=newLoc.getX() && y!=newLoc.getY())){
			//update
			myImagePath = newImagePath;
			x = newLoc.getX();
			y = newLoc.getY();
			Image image = new Image(getClass().getClassLoader().getResourceAsStream(myImagePath));
			myImage.setImage(image);
			myImage.setX(x);
			myImage.setY(y);
		}
	}


	
}
