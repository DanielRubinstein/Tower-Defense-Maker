package backEnd.Environment;

public class Environment implements EnvironmentInterface
{
	ComponentBank componentBank;
	TileBank tileBank;
	ComponentBehaviorBank behaviorBank;
	TileAttributeBank attributeBank;
	
	@Override
	public ComponentBank getComponentBank()
	{
		return componentBank;
	}

	@Override
	public void setComponentBank(ComponentBank bank)
	{
		componentBank = bank;
	}

	@Override
	public TileBank getTileBank()
	{
		return tileBank;
	}

	@Override
	public void setTileBank(TileBank bank)
	{
		tileBank = bank;
	}

	@Override
	public ComponentBehaviorBank getBehaviorBank()
	{
		return behaviorBank;
	}

	@Override
	public void setBehaviorBank(ComponentBehaviorBank bank)
	{
		behaviorBank = bank;
	}

	@Override
	public TileAttributeBank getAttributeBank()
	{
		return attributeBank;
	}

	@Override
	public void setTileAttributeBank(TileAttributeBank bank)
	{
		attributeBank = bank;
	}

}