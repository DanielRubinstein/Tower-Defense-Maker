package ModificationFromUser;

import backEnd.Model;

public class Modifciation_EditPlayerStatus implements ModificationFromUser {
	private String myPlayerStatusKey;
	private Double myNewValue;
	
	public Modifciation_EditPlayerStatus(String playerStatusKey, Double newValue) {
		myPlayerStatusKey = playerStatusKey;
		myNewValue = newValue;
	}
	
	@Override
	public void invoke(Model myModel) throws Exception {
		myModel.getModifiablePlayerStatus().setStatusItemValue(myPlayerStatusKey, myNewValue);
	}

}
