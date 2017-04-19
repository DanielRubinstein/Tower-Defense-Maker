package frontEnd.Skeleton.AoTools;

import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeOwner;
import frontEnd.View;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AttributeViewerCreator {
	private View myView;
	private AttributeOwner myOwner;
	private Attribute<?> myAttr;
	public static final String SAVED_IMAGES_DIRECTORY = "./src/resources/images";

	public AttributeViewerCreator(View view, AttributeOwner obj, Attribute<?> attr) {
		myView = view;
		myOwner = obj;
		myAttr = attr;
	}

	public Node extractViewer(String type) {
		Node n = null;
		switch (type) {
		case "IMAGE":
			n = createImageViewer();
			break;
		case "POSITION":
			n = createPositionViewer();
			break;
		case "COMPONENT":
			n = createComponentViewer();
			break;
		default:
			n = new Label(myAttr.getValue().toString());
			break;
		}
		return n;
	}

	private Node createImageViewer() {
		String imagePath = (String) myAttr.getValue();
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(imagePath));
		ImageView imv = new ImageView();
		imv.setImage(image);
		imv.setPreserveRatio(true);
		imv.setFitHeight(100);
		return imv;
	}

	private Node createPositionViewer() {
		String stringFormatter = "(%.0f, %.0f)";
		Point2D pos = (Point2D) myAttr.getValue();
		return new Label(String.format(stringFormatter, pos.getX(), pos.getY()));
	}

	private Node createComponentViewer() {
		return new Label("inDev");
	}

}
