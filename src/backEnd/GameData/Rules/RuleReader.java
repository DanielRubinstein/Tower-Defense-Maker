package backEnd.GameData.Rules;

/**
 * @author Derek
 *
 */
public interface RuleReader {

	public double getVal();

	public boolean isEnabled();

	public double getMaxVal();

	public double getMinVal();

	public String getKeyName();

	public String getDisplayString();
	
	@Deprecated
	public void printRule();

}
