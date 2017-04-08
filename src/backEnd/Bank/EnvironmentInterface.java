package backEnd.Bank;

public interface EnvironmentInterface {

	
	/**
	 * @return Bank of all available components
	 */
	public ComponentBank getComponentBank();
	
	public void setComponentBank(ComponentBank bank);
	
	public TileBank getTileBank();
	
	public void setTileBank(TileBank bank);
	
	public ComponentBehaviorBank getBehaviorBank();
	
	public void setBehaviorBank(ComponentBehaviorBank bank);
	
	public TileAttributeBank getAttributeBank();
	
	public void setTileAttributeBank(TileAttributeBank bank);
}
