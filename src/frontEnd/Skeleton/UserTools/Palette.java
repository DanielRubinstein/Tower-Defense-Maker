package frontEnd.Skeleton.UserTools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import ModificationFromUser.Modification_AddAttributeOwner;
import backEnd.Attribute.AttributeOwner;
import frontEnd.View;
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
		//http://code.makery.ch/blog/javafx-dialogs-official/
		// Create the custom dialog.
		Dialog<Point2D> dialog = new Dialog<>();
		dialog.setTitle("Attribute Owner Creation");
		dialog.setHeaderText("Where would like to place this?");
	
		/*
		// Set the icon (must be included in the project).
		dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));
		*/
	
		// Set the button types.
		ButtonType createButtonType = new ButtonType("sCreate", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);
	
		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
	
		TextField textX = new TextField();
		textX.setPromptText("0.0");
		TextField textY = new TextField();
		textY.setPromptText("0.0");
	
		grid.add(new Label("X Position:"), 0, 0);
		grid.add(textX, 1, 0);
		grid.add(new Label("Y Position:"), 0, 1);
		grid.add(textY, 1, 1);
	
		// Enable/Disable login button depending on whether a username was entered.
		Node createButton = dialog.getDialogPane().lookupButton(createButtonType);
		createButton.setDisable(true);
	
		// Do some validation (using the Java 8 lambda syntax).
		textX.textProperty().addListener((observable, oldValue, newValue) -> {
		    createButton.setDisable(newValue.trim().isEmpty() && textY.getText().trim().isEmpty());
		});
		textY.textProperty().addListener((observable, oldValue, newValue) -> {
		    createButton.setDisable(newValue.trim().isEmpty() && textX.getText().trim().isEmpty());
		});
	
		dialog.getDialogPane().setContent(grid);
	
		// Convert the result to a username-password-pair when the login button is clicked.
		dialog.setResultConverter(dialogButton -> {
			try{
			    if (dialogButton == createButtonType) {
			    	Point2D loc = new Point2D(Double.parseDouble(textX.getText()), Double.parseDouble(textY.getText()));
			        return loc;
			    }
			    return null;
			} catch (NumberFormatException e) {
				// TODO error thing
				return null;
			}
		});
	
		Optional<Point2D> result = dialog.showAndWait();
	
		if (result.isPresent()){
			return result.get();
		}
		return null;
	}
}
