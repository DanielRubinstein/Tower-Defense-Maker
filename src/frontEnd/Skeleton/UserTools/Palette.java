package frontEnd.Skeleton.UserTools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Map;

import ModificationFromUser.Modification_AddComponent;
import backEnd.Attribute.AttributeOwner;
import backEnd.GameData.State.Component;
import frontEnd.View;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.TilePane;

/**
 * http://stackoverflow.com/questions/27182323/working-on-creating-image-gallery-in-javafx-not-able-to-display-image-properly
 * 
 * @author Miguel Anderson
 *
 * @param <T>
 */
public class Palette<T extends AttributeOwner> implements SkeletonObject {
	private View myView;
	private TilePane tile;
	private Collection<T> myPresets;
	private static final String ATTRIBUTE_IMAGE_PATH_NAME = "no fucking clue";
	private Map<ImageView, T> myMap; 
	
	public Palette(View view, Collection<T> objects){
		myView = view;
		myPresets = objects;
		
		for(T preset : myPresets){
			File file = new File((String) preset.getAttribute(ATTRIBUTE_IMAGE_PATH_NAME).getValue());
			ImageView imageView = createImageView(file);
			myMap.put(imageView, preset);
			tile.getChildren().add(imageView);
		}
		
		initializePane();
		
		
	}

	private void initializePane() {
		tile = new TilePane();
		tile.setPadding(new Insets(15, 15, 15, 15));
        tile.setHgap(15);
	}

	@Override
	public Node getRoot() {
		return tile;
	}
	
	 private ImageView createImageView(final File imageFile) {
	        // DEFAULT_THUMBNAIL_WIDTH is a constant you need to define
	        // The last two arguments are: preserveRatio, and use smooth (slower)
	        // resizing

	        ImageView imageView = null;
	        try {
	            final Image image = new Image(new FileInputStream(imageFile), 150, 0, true,
	                    true);
	            imageView = new ImageView(image);
	            imageView.setFitWidth(150);
	            imageView.setOnMouseClicked(mouseEvent -> {
                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2){
                    	
                    	// ask for x and y of new thing
                    	Double x;
                    	Double y;
                    	
                    	// use modification shit to make the new thing
                    	myView.sendUserModification(new Modification_AddComponent((Component) myMap.get(imageView), new Point2D(x, y)));
                    	
                    }
	            });
	        } catch (FileNotFoundException ex) {
	        	// TODO put something here
	            ex.printStackTrace();
	        }
	        return imageView;
	    }
	



}
