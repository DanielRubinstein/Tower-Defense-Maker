package backEnd.Attribute;

public interface AttributeFactory extends AttributeFactoryReader{

	Attribute<?> getCOMPONENTAttribute();

	Attribute<?> getIMAGEAttribute();

	Attribute<?> getPOSITIONAttribute();

	Attribute<?> getBOOLEANAttribute();

	Attribute<?> getINTEGERAttribute();

	Attribute<?> getEDITABLESTRINGAttribute();

	Attribute<?> getDOUBLEAttribute();

	Attribute<?> getSTRINGLISTAttribute();

}