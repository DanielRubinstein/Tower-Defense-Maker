package backEnd.Attribute;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.javafx.geom.Point2D;

import javafx.scene.image.Image;

public class AttributeFactory {
	
	private final static String RESOURCES_PATH = "resources/";
	private final static String XML_FILE_NAME = "AttributePresets.xml";
	private final static String ALL_ATTRIBUTES_TYPES = "allAttributeTypes";
	private final static ResourceBundle myAttrNameResources = ResourceBundle.getBundle(RESOURCES_PATH + ALL_ATTRIBUTES_TYPES);
	private Document doc;
	
	public AttributeFactory(){
		try{
			File fXmlFile = new File("src/resources/AttributePresets.xml");
			System.out.println(fXmlFile.exists());
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			System.out.println(dBuilder + "  ");
			System.out.println("ASDFS" + dBuilder.parse(fXmlFile) + "  ");
			doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
		} catch (Exception e){
			
		}
	}
	
	public Attribute<?> getAttribute(String gameAttributeName){
		System.out.println("attribute name: " + gameAttributeName);
		String attributeType = myAttrNameResources.getString(gameAttributeName);
		System.out.println("attribute type " + attributeType);
		System.out.println("test " + doc);
		Node thisAttrNode = doc.getElementsByTagName(gameAttributeName).item(0);
		switch (attributeType) {
			case "STRINGLIST":
				String allStringOptions = thisAttrNode.getAttributes().getNamedItem("type").getNodeValue();
				List<String> stringOptions = Arrays.asList(allStringOptions.split("\\s*,\\s*"));
				return new AttributeImpl<String>(stringOptions, gameAttributeName);
				
			case "DOUBLE":
				String dbl_min_str = thisAttrNode.getAttributes().getNamedItem("min").getNodeValue();
				double dbl_min = Double.parseDouble(dbl_min_str);
				String dbl_max_str = thisAttrNode.getAttributes().getNamedItem("max").getNodeValue();
				double dbl_max = Double.parseDouble(dbl_max_str);
				String dbl_incr_str = thisAttrNode.getAttributes().getNamedItem("increment").getNodeValue();
				double dbl_incr = Double.parseDouble(dbl_incr_str);
				List<Double> doubleParameters = Arrays.asList(dbl_min, dbl_max, dbl_incr);
				return new AttributeImpl<Double>(doubleParameters, gameAttributeName);
				
			case "EDITABLESTRING":
				return new AttributeImpl<String>(null, gameAttributeName);
				
			case "INTEGER":
				String int_min_str = thisAttrNode.getAttributes().getNamedItem("min").getNodeValue();
				int int_min = Integer.parseInt(int_min_str);
				String int_max_str = thisAttrNode.getAttributes().getNamedItem("max").getNodeValue();
				int int_max = Integer.parseInt(int_max_str);
				String int_incr_str = thisAttrNode.getAttributes().getNamedItem("increment").getNodeValue();
				int int_incr = Integer.parseInt(int_incr_str);
				List<Integer> intParameters = Arrays.asList(int_min, int_max, int_incr);
				return new AttributeImpl<Integer>(intParameters, gameAttributeName);
				
			case "BOOLEAN":
				return new AttributeImpl<Boolean>(null, gameAttributeName);
				
			case "POSITION":
				return new AttributeImpl<Point2D>(null, gameAttributeName);
						
			case "IMAGE":
				return new AttributeImpl<Image>(null,gameAttributeName);
			default: throw new IllegalArgumentException();
		}
	}
}