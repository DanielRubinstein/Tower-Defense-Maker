package frontEnd.Skeleton.AoTools.AttributeVisualization;

import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeOwner;
import backEnd.GameData.State.Component;
import frontEnd.View;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class AttributeViewerCreator implements AttributeVisualization{
	private View myView;
	private AttributeOwner myOwner;
	private Attribute<?> myAttr;
	public static final String SAVED_IMAGES_DIRECTORY = "./src/resources/images";

	public AttributeViewerCreator(View view, AttributeOwner obj, Attribute<?> attr) {
		myView = view;
		myOwner = obj;
		myAttr = attr;
	}
	
	@Override
	public String getMethodNameFormat() {
		return "get%s";
	}
	
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

	public Node getPOSITION() {
		String stringFormatter = "(%.0f, %.0f)";
		Point2D pos = (Point2D) myAttr.getValue();
		return new Label(String.format(stringFormatter, pos.getX(), pos.getY()));
	}

	public Node getCOMPONENT() {
		Component baby = (Component) myAttr.getValue();
		
		HBox pair = new HBox();
		
		String babyImagePath =  baby.<String>getAttribute("ImageFile").getValue();
		ImageView imv = createImageView(babyImagePath);
		Label name = new Label();
		name.setText(myView.getBankController().getAOName(baby));
		pair.getChildren().add(name);
		pair.getChildren().add(imv);
		return pair;
	}
	
	private Node createBasicViewer() {
		return new Label(myAttr.getValue().toString());
	}

	public Node getINTEGER() {
		return createBasicViewer();
	}

	public Node getEDITABLESTRING() {
		return createBasicViewer();
	}

	public Node getDOUBLE() {
		return createBasicViewer();
	}

	public Node getBOOLEAN() {
		return createBasicViewer();
	}

	public Node getSTRINGLIST() {
		return createBasicViewer();
	}

}
