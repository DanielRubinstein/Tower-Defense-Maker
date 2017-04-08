package backEnd.Data;

public class DataController {
	private XMLReader myXMLReader;
	private XMLWriter myXMLWriter;
	
	public DataController (){
		myXMLReader = new XMLReaderImpl();
		myXMLWriter = new XMLWriterImpl();
	}
	
	

}
