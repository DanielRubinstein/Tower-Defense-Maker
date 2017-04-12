package ModificationFromUser;

import backEnd.ModelImpl;

public class Modification_Save implements ModificationFromUser {

	
	private String myFilePath;
	private String myGameName;
	
	public Modification_Save(String filePath, String gameName){
		myFilePath = filePath;
		myGameName = gameName;
		
	}
	
	
	@Override
	public void invoke(ModelImpl myModel) throws Exception {
		myModel.getDataController();

	}

}
