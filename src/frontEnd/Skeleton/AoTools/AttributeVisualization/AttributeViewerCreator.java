package frontEnd.Skeleton.AoTools.AttributeVisualization;

import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeOwner;
import backEnd.GameData.State.ComponentImpl;
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
		String stringFormatter = "(%.0f, %.0f)";
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
			ComponentImpl baby = (ComponentImpl) myAttr.getValue();
			
			HBox pair = new HBox();
			
			String babyImagePath =  baby.<String>getAttribute("ImageFile").getValue();
			ImageView imv = createImageView(babyImagePath);
			Label name = new Label();
			name.setText(myView.getBankController().getAOName(baby));
			pair.getChildren().add(name);
			pair.getChildren().add(imv);
			return pair;
		} catch (NullPointerException e){
			return new Label("None Selected");
		}
	}

}
