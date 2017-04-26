package frontEnd.Skeleton.ScreenGrid;



import backEnd.Attribute.AttributeOwnerReader;
import backEnd.GameData.State.SerializableObservable;
import backEnd.GameData.State.SerializableObserver;
import backEnd.GameData.State.TileImpl;
import javafx.geometry.Point2D;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AttributeOwnerVisualImpl implements SerializableObserver, AttributeOwnerVisual{

	private ImageView myImage;
	private String myImagePath;
	private Double mySize;
	private Point2D myPosition;
	private static final String IMAGE_ATTRIBUTE = "ImageFile";
	private static final String POSITION_ATTRIBUTE = "Position";
	private static final Double PRESET_SIZE = 75d;
	private AttributeOwnerReader myAttr;
	
	public AttributeOwnerVisualImpl(AttributeOwnerReader attr){
		myAttr = attr;
		myAttr.addObserver(this);
		setImage(myAttr.getMyAttributes().<String>get(IMAGE_ATTRIBUTE).getValue());
		setImageHover();
		if(myAttr instanceof TileImpl){
			mySize = 0d; // FIXME
		} else {
			setSize(myAttr.getMyAttributes().<Double>get("Size").getValue());
		}
		setPosition(myAttr.getMyAttributes().<Point2D>get(POSITION_ATTRIBUTE).getValue());
		
		
	}
	
	private void setSize(Double value) {
		mySize = value;
		myImage.setPreserveRatio(true);
		myImage.setFitHeight(mySize);
		myImage.setFitWidth(mySize);
	}
	
	private void setImageHover() {
		Tooltip hover = new Tooltip();
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
		if(myImage == null){
			myImage = new ImageView(image);
		} else {
			myImage.setImage(image);
		}
		
		
		if(!(myAttr instanceof TileImpl)){ // FIXME
			if(mySize==null){
				setSize(1d);
			} else {
				setSize(mySize);
			}
		} else {
			setSize(PRESET_SIZE);
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
	 * @see frontEnd.CustomJavafxNodes.FrontEndAttributeOwner#update(java.util.SerializableObservable, java.lang.Object)
	 */
	@Override
	public void update(SerializableObservable o, Object arg) {
		if(o == myAttr){
			String newImagePath = myAttr.getMyAttributes().<String>get(IMAGE_ATTRIBUTE).getValue();
			Point2D newPosition = myAttr.getMyAttributes().<Point2D>get(POSITION_ATTRIBUTE).getValue();
			if(!(myAttr instanceof TileImpl)){
				Double newSize = myAttr.getMyAttributes().<Double>get("Size").getValue();
				if(!newSize.equals(mySize)){
					setSize(newSize);
					setPosition(myPosition);
				}
			}
			
			
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
