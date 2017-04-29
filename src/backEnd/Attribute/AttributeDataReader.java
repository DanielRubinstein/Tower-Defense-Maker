package backEnd.Attribute;

import java.util.Collection;

public interface AttributeDataReader {
	
	<T> AttributeReader<T> getAttributeReader(String key);
	
	Collection<AttributeReader<?>> getAllAttributeReaders();

}
