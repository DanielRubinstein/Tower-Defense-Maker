package frontEnd.Skeleton.ScreenGrid;

import java.util.MissingResourceException;

import backEnd.Attribute.AttributeOwnerReader;
import backEnd.GameData.State.SerializableObservable;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import resources.constants.StringResourceBundle;

/**
 * This class is the frontEnd counterpart of AttributeOwner, basically anything that has attributes (like 
 * Components or Tiles). This is what gets displayed for that AttributeOwner.
 * @author Tim, Miguel
 *
 */
public class AttributeOwnerVisualImpl implements AttributeOwnerVisual{

	private ImageView myImage;
	private String myImagePath;
	private Double mySize;
	private Point2D myPosition;
	private StringResourceBundle stringResourceBundle = new StringResourceBundle();
	private final String IMAGE_ATTRIBUTE = stringResourceBundle.getFromAttributeNames("ImageFile");
	private final String POSITION_ATTRIBUTE = stringResourceBundle.getFromAttributeNames("Position");
	private final String SIZE_ATTRIBUTE = stringResourceBundle.getFromAttributeNames("Size");
	private final String POISON_TIME_ATTRIBUTE = stringResourceBundle.getFromAttributeNames("PoisonTime");
	private final String SLOW_TIME_ATTRIBUTE = stringResourceBundle.getFromAttributeNames("SlowTime");
	
	
	private AttributeOwnerReader myAttributeOwnerReader;
	
	public AttributeOwnerVisualImpl(AttributeOwnerReader attr){
		myAttributeOwnerReader = attr;
		myAttributeOwnerReader.addObserver(this);
		initializeImage();
		
	}

	private void initializeImage() {
		myImage = new ImageView();
		setImage(myAttributeOwnerReader.<String>getAttributeReader(IMAGE_ATTRIBUTE).getValue());
		try{
			Double size = myAttributeOwnerReader.<Double>getAttributeReader(SIZE_ATTRIBUTE).getValue();
			setSize(size);
		} catch (NullPointerException e){
			// means we are dealing with something that does not have size
		} catch (MissingResourceException e){
			//means we are dealing with something that does not have size
		}

		setPosition(myAttributeOwnerReader.<Point2D>getAttributeReader(POSITION_ATTRIBUTE).getValue());
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
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(myImagePath));
		myImage.setImage(image);
	}

	@Override
	public ImageView getImageView() {
		return myImage;
	}
	
	@Override
	public void update(SerializableObservable o, Object arg) {
		if(o == myAttributeOwnerReader){
			String newImagePath = myAttributeOwnerReader.<String>getAttributeReader(IMAGE_ATTRIBUTE).getValue();
			Point2D newPosition = myAttributeOwnerReader.<Point2D>getAttributeReader(POSITION_ATTRIBUTE).getValue();
			try{
				Double newSize = myAttributeOwnerReader.<Double>getAttributeReader(SIZE_ATTRIBUTE).getValue();
				if(!newImagePath.equals(myImagePath)){
					setImage(newImagePath);
					setSize(newSize);
					setPosition(myPosition);
				} else if(!newSize.equals(mySize)){
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
		setEffects();
	}

	private void setEffects() {
		if(!setSlowDownEffect() && !setPoisonEffect()){
			myImage.setStyle("");
		}
		
	}
	private boolean setDoubleEffect(String AttributeName,String styleName){
		try{
			double effectTime = myAttributeOwnerReader.<Double>getAttributeReader(AttributeName).getValue();
			if(effectTime>0){
				//System.out.println("EFFECT " + styleName + "   "  +AttributeName);
				myImage.setStyle(stringResourceBundle.getFromCustomCSS(styleName));
				//System.out.println(myImage.getStyle());
				return true;
			}
		}catch(NullPointerException e){
			//no slowtime attribute
		}
		return false;
	}
	

	private boolean setSlowDownEffect() {
		return setDoubleEffect(SLOW_TIME_ATTRIBUTE,"SlowEffect");
	}

	private boolean setPoisonEffect() {
		return setDoubleEffect(POISON_TIME_ATTRIBUTE,"PoisonEffect");
	}

	@Override
	public void refreshXY() {
		setPosition(myAttributeOwnerReader.<Point2D>getAttributeReader(POSITION_ATTRIBUTE).getValue());
	}

	
}
