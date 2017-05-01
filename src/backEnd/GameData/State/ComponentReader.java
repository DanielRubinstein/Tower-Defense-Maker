package backEnd.GameData.State;

import backEnd.Attribute.AttributeData;

public interface ComponentReader {

	/* (non-Javadoc)
	 * @see backEnd.GameData.State.Component#printID()
	 */
	long printID();

	/* (non-Javadoc)
	 * @see backEnd.GameData.State.Component#getAccessPermissions()
	 */
	AccessPermissions getAccessPermissions();

	/* (non-Javadoc)
	 * @see backEnd.GameData.State.Component#getBehavior(java.lang.String)
	 */

	AttributeData getMyAttributes();
	
	/* (non-Javadoc)
	 * @see backEnd.GameData.State.Component#addObserver(backEnd.GameData.State.SerializableObserver)
	 */
	void addObserver(SerializableObserver obs);

	/* (non-Javadoc)
	 * @see backEnd.GameData.State.Component#containsAttribute(java.lang.String)
	 */
	boolean containsAttribute(String key);

}