package backEnd.GameData.State;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeData;
import backEnd.Attribute.AttributeFactoryImpl;
import backEnd.Attribute.AttributeFactoryReader;
import backEnd.Attribute.AttributeReader;
import backEnd.GameEngine.Engine.Coordinates;
import resources.constants.StringResourceBundle;

public class ComponentImpl implements SerializableObservable, Component, ComponentReader {
	/**
	 * Any object on the grid is a component.
	 * This class implements components via the Component interface.
	 * @author Daniel
	 */
	private static final StringResourceBundle strResources = new StringResourceBundle();
	private AttributeData myAttributes;
	private AccessPermissions myAccessPermissions;
	private List<SerializableObserver> observers;
	private long ID;
	private Coordinates previousMovement;

	
	public ComponentImpl() throws FileNotFoundException{
		this(new AttributeData(),new AccessPermissionsImpl());
	}
	

	@Override
	public long printID(){
		return ID;
	}
	
	public ComponentImpl(AttributeData attributes, AccessPermissions accessPermissions) throws FileNotFoundException {
		observers = new ArrayList<SerializableObserver>();
		previousMovement=new Coordinates();
		ID = System.nanoTime();
		myAttributes = attributes;
		AttributeFactoryReader attributeFactory = new AttributeFactoryImpl();
		for (String key : strResources.getKeysFromDefaultComponentAttributes()) {
			Attribute<?> myAttribute = attributeFactory.getAttribute(key);
			addAttribute(key, myAttribute);
		}
		this.myAccessPermissions = accessPermissions;
	}

	@Override
	public AccessPermissions getAccessPermissions() {
		return myAccessPermissions;
	}

	@Override
	public <T> Attribute<T> getAttribute(String attributeType) {
		return myAttributes.get(attributeType);
	}
	
	@Override
	public <T> AttributeReader<T> getAttributeReader(String attributeType) {
		return myAttributes.get(attributeType);
	}

	@Override
	public AttributeData getMyAttributes() {
		return myAttributes;
	}

	@Override
	public void addAttributeData(AttributeData attributes) {
		myAttributes = attributes;
	}

	@Override
	public void addAttribute(String attrType, Attribute<?> newAttr) {
		myAttributes.addAttribute(attrType, newAttr);
	}

	@Override
	public <T> void setAttributeValue(String attrName, T newVal) {
		Attribute<T> attrToSet = myAttributes.<T>get(attrName);
		attrToSet.setValue(newVal);
		notifyObservers();
	}

	@Override
	public void addObserver(SerializableObserver obs) {
		observers.add(obs);
	}

	@Override
	public boolean containsAttribute(String key){
		return myAttributes.containsAttribute(key);
	}
	
	private void notifyObservers() {
		for (SerializableObserver obs : observers) {
			obs.update(this, null);
		}
	}

	@Override
	public List<SerializableObserver> getAndClearObservers() {
		List<SerializableObserver> currObservers = observers;
		observers = new ArrayList<SerializableObserver>();
		return currObservers;
	}
	
	@Override
	public void setPreviousMovement(Coordinates myPreviousMovement){
		previousMovement=myPreviousMovement;
	}

	@Override
	public Coordinates getPreviousMovement(){
		return previousMovement;
	}
	
	@Override
	public void setObserverList(List<SerializableObserver> observers) {
		this.observers = observers;
	}


	@Override
	public List<SerializableObserver> getObservers() {
		return observers;
	}

	@Override
	public void clearObservers() {
		observers.clear();
	}

	@Override
	public void setObservers(List<SerializableObserver> observersave) {
		observers = observersave;
	}
	
	@Override
	public int compareTo(Object o) {
		return Integer.compare(this.hashCode(), o.hashCode());
	}

	@Override
	public void removeAttribute(String attrName) {
		myAttributes.remove(attrName);		
	}

	@Override
	public boolean contains(String attName) {
		return myAttributes.containsAttribute(attName);
	}


	@Override
	public AccessPermissionsReader getAccessPermissionsReader() {
		return this.myAccessPermissions;
	}	
}