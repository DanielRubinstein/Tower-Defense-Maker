package frontEnd;

import backEnd.Mode.ModeEditor;

public interface ViewEditor extends ViewReader{
	
	public ModeEditor getMode();

	public Object save();

	public Object viewRules();

	public Object editRules();
	
	

}
