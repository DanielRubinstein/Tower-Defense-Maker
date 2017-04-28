package frontEnd.Skeleton.ScreenGrid;

import backEnd.GameData.State.SerializableObservable;
import javafx.scene.image.ImageView;
/**
 * Interface that defines the front end counterpart of AttributeOwners.
 * This is what is display on screen but whose behavior, attributes, etc. all depend on its back end counterpart. 
 * @author Tim
 *
 */
public interface AttributeOwnerVisual {

	ImageView getImageView();

	void update(SerializableObservable o, Object arg);

	void refreshXY();

}