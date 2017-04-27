package frontEnd.Skeleton.ScreenGrid;

import backEnd.Attribute.AttributeOwnerReader;
import backEnd.GameData.State.SerializableObservable;
import backEnd.GameData.State.SerializableObserver;
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
	private AttributeOwnerReader myAttr;
	
	public AttributeOwnerVisualImpl(AttributeOwnerReader attr){
		myAttr = attr;
		myAttr.addObserver(this);
		myImage = new ImageView();
		setImage(myAttr.getMyAttributes().<String>get(IMAGE_ATTRIBUTE).getValue());
		setImageHover();
		try{
			Double size = myAttr.getMyAttributes().<Double>get("Size").getValue();
			setSize(size);
		} catch (NullPointerException e){
			// means we are dealing with something that does not have size
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
		// TODO is this supposed to be position hover?
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
			try{
				Double newSize = myAttr.getMyAttributes().<Double>get("Size").getValue();
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
