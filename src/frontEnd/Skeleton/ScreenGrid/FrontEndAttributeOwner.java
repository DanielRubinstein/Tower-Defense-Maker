package frontEnd.Skeleton.ScreenGrid;

import java.util.Observable;

import javafx.scene.image.ImageView;
/**
 * Interface that defines the front end counterpart of AttributeOwners.
 * This is what is display on screen but whose behavior, attributes, etc. all depend on its back end counterpart. 
 * @author Tim
 *
 */
public interface FrontEndAttributeOwner {

	ImageView getImageView();

	void update(Observable o, Object arg);

	void refreshXY();

}