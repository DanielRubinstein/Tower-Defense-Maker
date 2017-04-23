package frontEnd.Skeleton.UserTools;

import java.util.Collection;
import backEnd.GameData.State.PlayerStatusReader;
import frontEnd.ViewReader;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class StatusView {
	private ViewReader myView;
	private VBox namesBox;
	private VBox valuesBox;
	private HBox globalBox;
	private ScrollPane myScrollPane;

	public StatusView(ViewReader view){
		myView = view;
		namesBox=new VBox();
		valuesBox=new VBox();
		globalBox=new HBox();
		myScrollPane=new ScrollPane();
		
		PlayerStatusReader playerStatus = myView.getPlayerStatus();
		Collection<String> statusItems = playerStatus.getPropertyNames();
		
        for (String str : statusItems){
        	namesBox.getChildren().add(new Label(str));
        	ReadOnlyDoubleProperty myProperty=playerStatus.getProperty(str);
        	Label value = new Label(myProperty.getValue().toString());
    		myProperty.addListener((o,oldV, newV)->{
    			value.setText(newV.toString());
    		});
        	valuesBox.getChildren().add(value);
        }
        valuesBox.setMinWidth(80);
        valuesBox.setFillWidth(true);
        globalBox.setSpacing(10);
        globalBox.setStyle("-fx-padding: 10;"+
                "-fx-background-color: 	#696969;"+
                "-fx-background-radius: 5;"+
                "-fx-background-insets: 3,3,3;"+
                "-fx-padding: 4 4 4 4;"+
        		"-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
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
