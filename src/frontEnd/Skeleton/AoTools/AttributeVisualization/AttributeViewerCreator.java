package frontEnd.Skeleton.AoTools.AttributeVisualization;

import backEnd.Attribute.AttributeReader;
import backEnd.GameData.State.Component;
import frontEnd.View;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import resources.constants.StringResourceBundle;

public class AttributeViewerCreator implements AttributeVisualization{
	private View myView;
	private AttributeReader<?> myAttr;
	private static final StringResourceBundle STRING_RESOURCE_BUNDLE = new StringResourceBundle();
	public static final String SAVED_IMAGES_DIRECTORY = STRING_RESOURCE_BUNDLE.getFromFilePaths("Images_Path");

	public AttributeViewerCreator(View view, AttributeReader<?> attr) {
		myView = view;
		myAttr = attr;
	}
	
	@Override
	public String getMethodNameFormat() {
		return STRING_RESOURCE_BUNDLE.getFromStringConstants("AttributeMethod");
	}
	
	@Override
	public Node getIMAGE() {
		String imagePath = (String) myAttr.getValue();
		return createImageView(imagePath);
	}
	
	private ImageView createImageView(String imagePath) {
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(imagePath));
		ImageView imv = new ImageView();
		imv.setImage(image);
		imv.setPreserveRatio(true);
		imv.setFitHeight(100);
		return imv;
	}

	@Override
	public Node getPOSITION() {
		String stringFormatter = STRING_RESOURCE_BUNDLE.getFromStringConstants("Position");
		Point2D pos = (Point2D) myAttr.getValue();
		return new Label(String.format(stringFormatter, pos.getX(), pos.getY()));
	}
	
	private Node createBasicViewer() {
		return new Label(myAttr.getValue().toString());
	}

	@Override
	public Node getINTEGER() {
		return createBasicViewer();
	}

	@Override
	public Node getEDITABLESTRING() {
		return createBasicViewer();
	}

	@Override
	public Node getDOUBLE() {
		return createBasicViewer();
	}

	@Override
	public Node getBOOLEAN() {
		return createBasicViewer();
	}

	@Override
	public Node getSTRINGLIST() {
		return createBasicViewer();
	}
	
	@Override
	public Node getCOMPONENT() {
		try{
			String baby_str = (String) myAttr.getValue();
			Component baby = myView.getBankControllerReader().getComponent(baby_str);
			
			HBox pair = new HBox();
			
			String babyImagePath =  baby.<String>getAttribute(STRING_RESOURCE_BUNDLE.getFromAttributeNames("ImageFile")).getValue();
			ImageView imv = createImageView(babyImagePath);
			Label name = new Label();
			name.setText(baby_str);
			pair.getChildren().add(name);
			pair.getChildren().add(imv);
			pair.setAlignment(Pos.CENTER_LEFT);
			return pair;
		} catch (NullPointerException e){
			return new Label(STRING_RESOURCE_BUNDLE.getFromStringConstants("NoneSelected"));
		}
	}

}
