package frontEnd.Skeleton.UserTools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import ModificationFromUser.Modification_AddAttributeOwner;
import backEnd.Attribute.AttributeOwner;
import frontEnd.View;
import frontEnd.CustomJavafxNodes.DoubleFieldPrompt;
import frontEnd.CustomJavafxNodes.SingleFieldPrompt;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
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
	private static final String ATTRIBUTE_IMAGE_PATH_NAME = "ImageFile";
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
	            Image image = new Image(new FileInputStream(imageFile), 150, 0, true, true);
	            imageView = new ImageView(image);
	            imageView.setFitWidth(150);
	            imageView.setOnMouseClicked(mouseEvent -> {
                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2){
                    	Point2D loc = askForNewPosition();
                    	myView.sendUserModification(new Modification_AddAttributeOwner(myMap.get(imageView), loc));
                    }
	            });
	        } catch (FileNotFoundException ex) {
	        	// TODO put something here
	            ex.printStackTrace();
	        }
	        return imageView;
	    }
	 
	private Point2D askForNewPosition() {
		List<String> dialogTitles = Arrays.asList("Creation Utility", "Please input a location");
		List<String> promptLabel = Arrays.asList("X Position:", "Y Position:");
		List<String> promptText = Arrays.asList("0.0", "0.0");
		DoubleFieldPrompt myDialog = new DoubleFieldPrompt(dialogTitles, promptLabel, promptText);
		List<String> results = myDialog.create();
		return new Point2D(Double.parseDouble(results.get(0)), Double.parseDouble(results.get(1)));
	}
}
