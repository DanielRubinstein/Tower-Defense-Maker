package frontEnd.Skeleton.UserTools;

import java.util.Collection;

import ModificationFromUser.Rules.Modification_EditRuleValue;
import ModificationFromUser.Rules.Modification_ToggleRule;
import backEnd.GameData.Rules.RuleReader;
import frontEnd.View;
import frontEnd.CustomJavafxNodes.NumberChanger;
import javafx.beans.property.BooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import resources.constants.StringResourceBundle;
import resources.constants.numeric.NumericResourceBundle;
import resources.constants.numeric.ScreenConstants;

public class RulesView implements PopUp{
	private StringResourceBundle stringResourceBundle = new StringResourceBundle();
	private NumericResourceBundle numResourceBundle = new NumericResourceBundle();
	private GridPane myRoot;
	private View myView;
	private BooleanProperty canEdit;
	public RulesView(View view, Stage parentStage){
		myRoot = new GridPane();
		myView = view;
		canEdit = view.getBooleanAuthorModeProperty();
		setupView();
	}
	
	@Override
	public void displayOnStage(Stage stage){
		Stage myStage = stage;
		myStage.initOwner(myView.getMainWindow());
		myStage.initModality(Modality.APPLICATION_MODAL);
		myRoot.setPadding(new Insets(numResourceBundle.getFromSizing("StandardSpacing")));
		Scene scene = new Scene(myRoot);
		scene.getStylesheets().add(stringResourceBundle.getFromStringConstants("DEFAULT_CSS"));
		myStage.setScene(scene);
		myStage.show();
	}
	
	private void setupView(){
		setupTitle(stringResourceBundle.getFromStringConstants("Rules"),0);
		setupTitle(stringResourceBundle.getFromStringConstants("Enabled"),1);
		setupTitle(stringResourceBundle.getFromStringConstants("Value"),2);
		Collection<RuleReader> myRules = myView.getRules();
		int rowIndex = 2;
		for(RuleReader rule : myRules){
			Label ruleText = new Label(rule.getDisplayString());
			
			CheckBox ruleEnabled = new CheckBox(stringResourceBundle.getFromStringConstants("Enabled"));
			ruleEnabled.setSelected(rule.isEnabled());
			ruleEnabled.setOnAction(e -> {
				myView.sendUserModification(new Modification_ToggleRule(rule.getKeyName()));
			});
			ruleEnabled.disableProperty().bind(canEdit.not());
			
			Node ruleChanger = createRuleChanger(rule);
			ruleChanger.disableProperty().bind(canEdit.not());
			myRoot.add(ruleText, 0, rowIndex);
			myRoot.add(ruleEnabled, 1, rowIndex);
			myRoot.add(ruleChanger, 2, rowIndex);
			rowIndex++;
		}
		
	}
	private Node createRuleChanger(RuleReader rule){
		HBox changer = new HBox();
		NumberChanger ruleChanger = new NumberChanger(rule.getMinVal(), rule.getMaxVal(), 
				rule.getVal(), (rule.getMaxVal()-rule.getMinVal())/100);
		ruleChanger.addListener((SerializableObservable, oldValue, newValue) -> {
			myView.sendUserModification(new Modification_EditRuleValue(rule.getKeyName(),newValue.doubleValue()));
		});
		HBox indicatorBox = ruleChanger.addIntegerIndicator();
		changer.getChildren().addAll(ruleChanger.getRoot(),indicatorBox);
		
		return changer;
	}
	private void setupTitle(String title, int col){
		Label exampleTitle = new Label(title);
		exampleTitle.setUnderline(true);
		myRoot.add(exampleTitle, col, 1);
	}	
}
