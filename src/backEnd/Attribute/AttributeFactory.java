package backEnd.Attribute;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import backEnd.GameData.State.Component;
import javafx.geometry.Point2D;

/**
 * This class has a method that returns an attribute of the given name and type
 * @author Riley Nisbet
 *
 */

public class AttributeFactory {
	
	private final static String XML_FILE_NAME = "src/resources/AttributePresets.xml";
	private final static String ALL_ATTRIBUTES_TYPES = "resources/allAttributeTypes";
	private static ResourceBundle myAttrNameResources;
	private Document doc;
	
	public AttributeFactory() throws FileNotFoundException{
		myAttrNameResources = ResourceBundle.getBundle(ALL_ATTRIBUTES_TYPES);
		try{
			File fXmlFile = new File(XML_FILE_NAME);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
		} catch (Exception e){
			throw new FileNotFoundException();
		}
	}
	
	public Attribute<?> getAttribute(String gameAttributeName){
		String attributeType = myAttrNameResources.getString(gameAttributeName);
		Node thisAttrNode = doc.getElementsByTagName(gameAttributeName).item(0);
		switch (attributeType) {
			case "STRINGLIST":
				String allStringOptions = thisAttrNode.getAttributes().getNamedItem("options").getNodeValue();
				List<String> stringOptions = Arrays.asList(allStringOptions.split("\\s*,\\s*"));
				Attribute<String> sl_newAttr = new AttributeImpl<String>(stringOptions, gameAttributeName);;
				sl_newAttr.setValue("");
				return sl_newAttr;
				
			case "DOUBLE":
				String dbl_min_str = thisAttrNode.getAttributes().getNamedItem("min").getNodeValue();
				double dbl_min = Double.parseDouble(dbl_min_str);
				String dbl_max_str = thisAttrNode.getAttributes().getNamedItem("max").getNodeValue();
				double dbl_max = Double.parseDouble(dbl_max_str);
				String dbl_default_str = thisAttrNode.getAttributes().getNamedItem("start").getNodeValue();
				double dbl_default = Double.parseDouble(dbl_default_str);
				String dbl_incr_str = thisAttrNode.getAttributes().getNamedItem("increment").getNodeValue();
				double dbl_incr = Double.parseDouble(dbl_incr_str);
				List<Double> doubleParameters = Arrays.asList(dbl_min, dbl_max, dbl_default, dbl_incr);
				Attribute<Double> dbl_newAttr = new AttributeImpl<Double>(doubleParameters, gameAttributeName);
				dbl_newAttr.setValue(dbl_default);
				return dbl_newAttr;
				
			case "EDITABLESTRING":
				AttributeImpl<String> es_newAttr = new AttributeImpl<String>(null, gameAttributeName);
				es_newAttr.setValue("RIGHT");
				return es_newAttr;
				
			case "INTEGER":
				String int_min_str = thisAttrNode.getAttributes().getNamedItem("min").getNodeValue();
				int int_min = Integer.parseInt(int_min_str);
				String int_max_str = thisAttrNode.getAttributes().getNamedItem("max").getNodeValue();
				int int_max = Integer.parseInt(int_max_str);
				String int_default_str = thisAttrNode.getAttributes().getNamedItem("start").getNodeValue();
				int int_default = Integer.parseInt(int_default_str);
				String int_incr_str = thisAttrNode.getAttributes().getNamedItem("increment").getNodeValue();
				int int_incr = Integer.parseInt(int_incr_str);
				List<Integer> intParameters = Arrays.asList(int_min, int_max, int_default, int_incr);
				AttributeImpl<Integer> int_newAttr = new AttributeImpl<Integer>(intParameters, gameAttributeName);
				int_newAttr.setValue(int_default);
				return int_newAttr;
				
			case "BOOLEAN":
				AttributeImpl<Boolean> bool_newAttr = new AttributeImpl<Boolean>(null, gameAttributeName);
				bool_newAttr.setValue(false);
				return bool_newAttr;
				
			case "POSITION":
				AttributeImpl<Point2D> pos_newAttr = new AttributeImpl<Point2D>(null, gameAttributeName);
				pos_newAttr.setValue(new Point2D(0,0));
				return pos_newAttr;
						
			case "IMAGE":
				AttributeImpl<String> img_newAttr = new AttributeImpl<String>(null, gameAttributeName);
				img_newAttr.setValue("");
				return img_newAttr;
			case "COMPONENT":
				AttributeImpl<Component> comp_newAttr = new AttributeImpl<Component>(null, gameAttributeName);
				comp_newAttr.setValue(null);
				return comp_newAttr;
			default: throw new IllegalArgumentException();
		}
	}
}