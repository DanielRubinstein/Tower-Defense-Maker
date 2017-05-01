package frontEnd.Skeleton.UserTools;

import java.util.Arrays;
import java.util.Collection;

import ModificationFromUser.Modifciation_EditPlayerStatus;
import backEnd.GameData.State.PlayerStatusReader;
import frontEnd.View;
import frontEnd.CustomJavafxNodes.SingleFieldPrompt;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import resources.constants.StringResourceBundle;

public class StatusView {
	private View myView;
	private VBox namesBox;
	private VBox valuesBox;
	private HBox globalBox;
	private ScrollPane myScrollPane;
	private StringResourceBundle strResources = new StringResourceBundle();
	
	public StatusView(View view){
		myView = view;
		namesBox=new VBox();
		valuesBox=new VBox();
		globalBox=new HBox();
		myScrollPane=new ScrollPane();
		initializeContent();
	}
	private void initializeContent(){
		PlayerStatusReader playerStatus = myView.getPlayerStatus();
		Collection<String> statusItems = playerStatus.getPropertyNames();
		
        for (String str : statusItems){
        	setInteraction(str, playerStatus);
        }
        organizeDisplay();
	}
	
	private void setInteraction(String str, PlayerStatusReader playerStatus){
    	namesBox.getChildren().add(new Label(str));
    	ReadOnlyDoubleProperty myProperty=playerStatus.getProperty(str);
    	Label value = new Label(myProperty.getValue().toString());
    	clickInteraction(value, str);
		myProperty.addListener((o,oldV, newV)->{
			value.setText(newV.toString());
		});
    	valuesBox.getChildren().add(value);
	}
	
	private void clickInteraction(Label value, String str){
    	value.setOnMouseClicked(e -> {
    		SingleFieldPrompt newPrompt = new SingleFieldPrompt(
					Arrays.asList(strResources.getFromStringConstants("EditingWindow"), 
							String.format(strResources.getFromStringConstants("StringInputFormat"),str)), 
					strResources.getFromStringConstants("NewValue"),
					value.getText());
    		Double newValue = null;
    		try{
    			newValue = newPrompt.getUserInputDouble();
    		} catch (NumberFormatException err){
    			myView.reportError(err);
    		}
			if(newValue == null){
				return;
			}
			myView.sendUserModification(new Modifciation_EditPlayerStatus(str, newValue));
			value.setText(Double.toString(newValue));
    	});
	}
	
	private void organizeDisplay(){
        valuesBox.setMinWidth(80);
        valuesBox.setFillWidth(true);
        globalBox.setSpacing(10);
        globalBox.setStyle(strResources.getFromCustomCSS("StatusView"));
        globalBox.getChildren().add(0, namesBox);
        globalBox.getChildren().add(1, valuesBox);
        myScrollPane.setMinHeight(150);
        myScrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
        myScrollPane.setContent(globalBox);
	}

	public Node getRoot(){
		return myScrollPane;
	}

}
