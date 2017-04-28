package backEnd.GameData.State;

import backEnd.Attribute.AttributeData;
import backEnd.GameEngine.Behaviors.Behavior;

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
	Behavior getBehavior(String behaviorType);

	/* (non-Javadoc)
	 * @see backEnd.GameData.State.Component#getMyAttributes()
	 */
	AttributeData getMyAttributes();

	/* (non-Javadoc)
	 * @see backEnd.GameData.State.Component#getMyType()
	 */
	String getMyType();

	/* (non-Javadoc)
	 * @see backEnd.GameData.State.Component#addObserver(backEnd.GameData.State.SerializableObserver)
	 */
	void addObserver(SerializableObserver obs);

	/* (non-Javadoc)
	 * @see backEnd.GameData.State.Component#containsAttribute(java.lang.String)
	 */
	boolean containsAttribute(String key);

}