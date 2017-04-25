package frontEnd.Skeleton.ScreenGrid;

import java.util.Observable;
import java.util.Observer;


import backEnd.Attribute.AttributeOwnerReader;
import javafx.geometry.Point2D;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FrontEndAttributeOwnerImpl implements Observer, FrontEndAttributeOwner{

	private ImageView myImage;
	private String myImagePath;
	private Point2D myPosition;
	private static final String IMAGE_ATTRIBUTE = "ImageFile";
	private static final String POSITION_ATTRIBUTE = "Position";
	private AttributeOwnerReader myAttr;
	
	public FrontEndAttributeOwnerImpl(AttributeOwnerReader attr){
		myAttr = attr;
		myAttr.addAsListener(this);
		setImage(myAttr.getMyAttributes().<String>get(IMAGE_ATTRIBUTE).getValue());
		setImageHover();
		setPosition(myAttr.getMyAttributes().<Point2D>get(POSITION_ATTRIBUTE).getValue());
	}
	
	private void setImageHover() {
		Tooltip hover = new Tooltip();
		
	}

	private void setPosition(Point2D newPosition){
		if (newPosition != null){
			myPosition = newPosition;
			myImage.setX(newPosition.getX()-myImage.getFitWidth());
			myImage.setY(newPosition.getY()-myImage.getFitHeight());
		}
	}
	
	private void setImage(String newImagePath){
		myImagePath = newImagePath;
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(newImagePath));
		if(myImage == null){
			myImage = new ImageView(image);
		} else {
			myImage.setImage(image);
		}
	}

	/* (non-Javadoc)
	 * @see frontEnd.CustomJavafxNodes.FrontEndAttributeOwner#getImageView()
	 */
	@Override
	public ImageView getImageView() {
		return myImage;
	}
	
	/* (non-Javadoc)
	 * @see frontEnd.CustomJavafxNodes.FrontEndAttributeOwner#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		if(o == myAttr){
			String newImagePath = myAttr.getMyAttributes().<String>get(IMAGE_ATTRIBUTE).getValue();
			Point2D newPosition = myAttr.getMyAttributes().<Point2D>get(POSITION_ATTRIBUTE).getValue();
			
			if(!newImagePath.equals(myImagePath)){
				setImage(newImagePath);
			}
			if(!newPosition.equals(myPosition)){
				setPosition(newPosition);
			}
		}
	}

	@Override
	public void refreshXY() {
		setPosition(myAttr.getMyAttributes().<Point2D>get(POSITION_ATTRIBUTE).getValue());
	}

	
}
