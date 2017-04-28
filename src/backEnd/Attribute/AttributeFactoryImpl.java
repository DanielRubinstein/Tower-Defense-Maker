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
import util.reflection.Reflection;

/**
 * This class has a method that returns an attribute of the given name and type
 * @author Riley Nisbet
 * @author Miguel Anderson (Reflection work)
 *
 */

public class AttributeFactoryImpl implements AttributeFactory{
	
	private final static String XML_FILE_NAME = "src/resources/AttributePresets.xml";
	private final static String ALL_ATTRIBUTES_TYPES = "resources/allAttributeTypes";
	private static ResourceBundle myAttrNameResources;
	private Document doc;
	private String currentGameAttributeName;
	private Node currentAttributeNode;
	
	public AttributeFactoryImpl() throws FileNotFoundException{
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
		currentGameAttributeName = gameAttributeName;
		currentAttributeNode = doc.getElementsByTagName(currentGameAttributeName).item(0);
		
		String attributeType = myAttrNameResources.getString(currentGameAttributeName);
		String methodFormat = "get%sAttribute";
		
		return (Attribute<?>) Reflection.callMethod(this, String.format(methodFormat, attributeType));
	}

	/* (non-Javadoc)
	 * @see backEnd.Attribute.AttributeFactory#getCOMPONENTAttribute()
	 */
	@Override
	public Attribute<?> getCOMPONENTAttribute() {
		AttributeImpl<String> comp_newAttr = new AttributeImpl<String>(null, currentGameAttributeName);
		comp_newAttr.setValue(null);
		return comp_newAttr;
	}

	/* (non-Javadoc)
	 * @see backEnd.Attribute.AttributeFactory#getIMAGEAttribute()
	 */
	@Override
	public Attribute<?> getIMAGEAttribute() {
		AttributeImpl<String> img_newAttr = new AttributeImpl<String>(null, currentGameAttributeName);
		img_newAttr.setValue("");
		return img_newAttr;
	}

	/* (non-Javadoc)
	 * @see backEnd.Attribute.AttributeFactory#getPOSITIONAttribute()
	 */
	@Override
	public Attribute<?> getPOSITIONAttribute() {
		AttributeImpl<Point2D> pos_newAttr = new AttributeImpl<Point2D>(null, currentGameAttributeName);
		pos_newAttr.setValue(new Point2D(0,0));
		return pos_newAttr;
	}

	/* (non-Javadoc)
	 * @see backEnd.Attribute.AttributeFactory#getBOOLEANAttribute()
	 */
	@Override
	public Attribute<?> getBOOLEANAttribute() {
		AttributeImpl<Boolean> bool_newAttr = new AttributeImpl<Boolean>(null, currentGameAttributeName);
		bool_newAttr.setValue(false);
		return bool_newAttr;
	}

	/* (non-Javadoc)
	 * @see backEnd.Attribute.AttributeFactory#getINTEGERAttribute()
	 */
	@Override
	public Attribute<?> getINTEGERAttribute() {
		String int_min_str = currentAttributeNode.getAttributes().getNamedItem("min").getNodeValue();
		int int_min = Integer.parseInt(int_min_str);
		String int_max_str = currentAttributeNode.getAttributes().getNamedItem("max").getNodeValue();
		int int_max = Integer.parseInt(int_max_str);
		String int_default_str = currentAttributeNode.getAttributes().getNamedItem("start").getNodeValue();
		int int_default = Integer.parseInt(int_default_str);
		String int_incr_str = currentAttributeNode.getAttributes().getNamedItem("increment").getNodeValue();
		int int_incr = Integer.parseInt(int_incr_str);
		List<Integer> intParameters = Arrays.asList(int_min, int_max, int_default, int_incr);
		AttributeImpl<Integer> int_newAttr = new AttributeImpl<Integer>(intParameters, currentGameAttributeName);
		int_newAttr.setValue(int_default);
		return int_newAttr;
	}

	/* (non-Javadoc)
	 * @see backEnd.Attribute.AttributeFactory#getEDITABLESTRINGAttribute()
	 */
	@Override
	public Attribute<?> getEDITABLESTRINGAttribute() {
		AttributeImpl<String> es_newAttr = new AttributeImpl<String>(null, currentGameAttributeName);
		es_newAttr.setValue("RIGHT");
		return es_newAttr;
	}

	/* (non-Javadoc)
	 * @see backEnd.Attribute.AttributeFactory#getDOUBLEAttribute()
	 */
	@Override
	public Attribute<?> getDOUBLEAttribute() {
		String dbl_min_str = currentAttributeNode.getAttributes().getNamedItem("min").getNodeValue();
		double dbl_min = Double.parseDouble(dbl_min_str);
		String dbl_max_str = currentAttributeNode.getAttributes().getNamedItem("max").getNodeValue();
		double dbl_max = Double.parseDouble(dbl_max_str);
		String dbl_default_str = currentAttributeNode.getAttributes().getNamedItem("start").getNodeValue();
		double dbl_default = Double.parseDouble(dbl_default_str);
		String dbl_incr_str = currentAttributeNode.getAttributes().getNamedItem("increment").getNodeValue();
		double dbl_incr = Double.parseDouble(dbl_incr_str);
		List<Double> doubleParameters = Arrays.asList(dbl_min, dbl_max, dbl_default, dbl_incr);
		Attribute<Double> dbl_newAttr = new AttributeImpl<Double>(doubleParameters, currentGameAttributeName);
		dbl_newAttr.setValue(dbl_default);
		return dbl_newAttr;
	}

	/* (non-Javadoc)
	 * @see backEnd.Attribute.AttributeFactory#getSTRINGLISTAttribute()
	 */
	@Override
	public Attribute<?> getSTRINGLISTAttribute() {
		String allStringOptions = currentAttributeNode.getAttributes().getNamedItem("options").getNodeValue();
		List<String> stringOptions = Arrays.asList(allStringOptions.split("\\s*,\\s*"));
		Attribute<String> sl_newAttr = new AttributeImpl<String>(stringOptions, currentGameAttributeName);;
		sl_newAttr.setValue("");
		return sl_newAttr;
	}
}
