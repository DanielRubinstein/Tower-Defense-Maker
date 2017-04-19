package frontEnd.Skeleton.AoTools;

import java.lang.reflect.Method;

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
		String stringFormatForViewerMethod = "create_%s_Viewer";
		try{
			Method createViewer = AttributeViewerCreator.class.getDeclaredMethod(String.format(stringFormatForViewerMethod, type));
			createViewer.setAccessible(true);
			n = (Node) createViewer.invoke(this);
		} catch (NoSuchMethodException e){
			System.out.println("attribute type not yet coded");
		} catch (Exception e){
			// fuck
		}
		return n;
	}
	
	private Node create_IMAGE_Viewer() {
		String imagePath = (String) myAttr.getValue();
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(imagePath));
		ImageView imv = new ImageView();
		imv.setImage(image);
		imv.setPreserveRatio(true);
		imv.setFitHeight(100);
		return imv;
	}
	
	private Node create_POSITION_Viewer() {
		String stringFormatter = "(%.0f, %.0f)";
		Point2D pos = (Point2D) myAttr.getValue();
		return new Label(String.format(stringFormatter, pos.getX(), pos.getY()));
	}

	private Node create_COMPONENT_Viewer() {
		return new Label("inDev");
	}
	
	private Node createBasicViewer() {
		return new Label(myAttr.getValue().toString());
	}

	private Node create_INTEGER_Viewer() {
		return createBasicViewer();
	}

	private Node create_EDITABLESTRING_Viewer() {
		return createBasicViewer();
	}

	private Node create_STRINGLIST_Viewer() {
		return createBasicViewer();
	}

	private Node create_DOUBLE_Viewer() {
		return createBasicViewer();
	}

	private Node create_BOOLEAN_Viewer() {
		return createBasicViewer();
	}


}
