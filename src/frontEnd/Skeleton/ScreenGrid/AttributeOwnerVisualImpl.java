package frontEnd.Skeleton.ScreenGrid;

import java.util.MissingResourceException;

import backEnd.Attribute.AttributeOwnerReader;
import backEnd.GameData.State.SerializableObservable;
import backEnd.GameData.State.SerializableObserver;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import resources.constants.StringResourceBundle;

/**
 * This class is the frontEnd counterpart of AttributeOwner, basically anything that has attributes (like 
 * Components or Tiles). This is what gets displayed for that AttributeOwner.
 * @author Tim
 *
 */
public class AttributeOwnerVisualImpl implements SerializableObserver, AttributeOwnerVisual{

	private ImageView myImage;
	private String myImagePath;
	private Double mySize;
	private Point2D myPosition;
	private StringResourceBundle stringResourceBundle = new StringResourceBundle();
	private final String IMAGE_ATTRIBUTE = stringResourceBundle.getFromAttributeNames("ImageFile");
	private final String POSITION_ATTRIBUTE = stringResourceBundle.getFromAttributeNames("Position");
	private final String SIZE_ATTRIBUTE = stringResourceBundle.getFromAttributeNames("Size");
	private AttributeOwnerReader myAttr;
	
	public AttributeOwnerVisualImpl(AttributeOwnerReader attr){
		myAttr = attr;
		myAttr.addObserver(this);
		myImage = new ImageView();
		setImage(myAttr.<String>getAttributeReader(IMAGE_ATTRIBUTE).getValue());
		try{
			Double size = myAttr.<Double>getAttributeReader(SIZE_ATTRIBUTE).getValue();
			setSize(size);
		} catch (NullPointerException e){
			// means we are dealing with something that does not have size
		}catch (MissingResourceException e){
			//means we are dealing with something that does not have size
		}
		setPosition(myAttr.<Point2D>getAttributeReader(POSITION_ATTRIBUTE).getValue());
	}
	
	private void setSize(Double value) {
		mySize = value;
		myImage.setPreserveRatio(true);
		myImage.setFitHeight(mySize);
		myImage.setFitWidth(mySize);
	}

	private void setPosition(Point2D newPosition){
		if (newPosition != null){
			myPosition = newPosition;
			myImage.setX(newPosition.getX()-myImage.getFitWidth()/2);
			myImage.setY(newPosition.getY()-myImage.getFitHeight()/2);
		}
	}
	
	private void setImage(String newImagePath){
		myImagePath = newImagePath;
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(newImagePath));
		myImage.setImage(image);
	}

	@Override
	public ImageView getImageView() {
		return myImage;
	}
	
	@Override
	public void update(SerializableObservable o, Object arg) {
		if(o == myAttr){
			String newImagePath = myAttr.<String>getAttributeReader(IMAGE_ATTRIBUTE).getValue();
			Point2D newPosition = myAttr.<Point2D>getAttributeReader(POSITION_ATTRIBUTE).getValue();
			try{
				Double newSize = myAttr.<Double>getAttributeReader(SIZE_ATTRIBUTE).getValue();
				if(!newImagePath.equals(myImagePath)){
					setImage(newImagePath);
					setSize(newSize);
					setPosition(myPosition);
				}
				if(!newSize.equals(mySize)){
					setSize(newSize);
					setPosition(myPosition);
				}
			} catch (NullPointerException e){
				// means we are dealing with something that does not have size
				if(!newImagePath.equals(myImagePath)){
					setImage(newImagePath);
				}
			} catch (MissingResourceException e){
				// means we are dealing with something that does not have size
			}
			if(!newPosition.equals(myPosition)){
				setPosition(newPosition);
			}
		}
	}

	@Override
	public void refreshXY() {
		setPosition(myAttr.<Point2D>getAttributeReader(POSITION_ATTRIBUTE).getValue());
	}

	
}
