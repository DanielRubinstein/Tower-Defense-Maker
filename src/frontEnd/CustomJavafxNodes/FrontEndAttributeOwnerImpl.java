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
	private Point2D myPosition;
	private static final String IMAGE_ATTRIBUTE = "ImageFile";
	private static final String POSITION_ATTRIBUTE = "Position";
	private AttributeOwnerReader myAttr;
	private Point2D newLoc;
	
	public FrontEndAttributeOwnerImpl(AttributeOwnerReader attr){
		attr.addAsListener(this);
		myAttr = attr;
		setUpImageView(attr);
	}
	
	private void setUpImageView(AttributeOwnerReader attr){
		Object myImagePathObj=attr.getMyAttributes().get(IMAGE_ATTRIBUTE).getValue();
		myImagePath = (String) myImagePathObj;
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
		myImage.setX(xx);
		myImage.setY(yy);
	}
	
	/* (non-Javadoc)
	 * @see frontEnd.CustomJavafxNodes.FrontEndAttributeOwner#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		AttributeOwnerReader newAttr = (AttributeOwnerReader) o;
		Object newImagePathObj=newAttr.getMyAttributes().get(IMAGE_ATTRIBUTE).getValue();
		String newImagePath = (String) newImagePathObj;
		Object newLocObj=newAttr.getMyAttributes().get(POSITION_ATTRIBUTE).getValue();
		newLoc = (Point2D) newLocObj;
		if(!newImagePath.equals(myImagePath) ){ //FIXME : I removed || !newLoc.equals(myPosition) from this line because caused error.
			refreshXY();
			myImagePath = newImagePath;
			Image image = new Image(getClass().getClassLoader().getResourceAsStream(myImagePath));
			myImage.setImage(image);
		}
	}

	@Override
	public void refreshXY() {
		Object locObj=myAttr.getMyAttributes().get(POSITION_ATTRIBUTE).getValue();
		Point2D loc= (Point2D) locObj;
		if (newLoc==null){
			newLoc=loc;
		}
		if (loc==null){ //components with null locations exist on the screen when game initialized- SAD!
			return;
		}
		System.out.println("refreshXY executed. Old position: "+myPosition+" newPosition"+loc);
//		myPosition = loc;
//		myImage.setX(loc.getX());
//		myImage.setY(loc.getY());
		myPosition = newLoc;
		myImage.setX(newLoc.getX());
		myImage.setY(newLoc.getY());
		System.out.println("it should have moved");
		
	}

	

	
}
