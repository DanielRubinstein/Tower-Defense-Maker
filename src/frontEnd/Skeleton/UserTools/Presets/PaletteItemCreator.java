package frontEnd.Skeleton.UserTools.Presets;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.restfb.types.Group;

import ModificationFromUser.AttributeOwner.Modification_Remove_FromPalette;
import backEnd.Attribute.AttributeOwner;
import backEnd.Attribute.AttributeOwnerReader;
import backEnd.Bank.BankControllerReader;
import frontEnd.View;
import frontEnd.Skeleton.AoTools.GenericCommandCenter;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

public class PaletteItemCreator {
	private View myView;
	private BankControllerReader observedBankController;
	
	public PaletteItemCreator(View view, BankControllerReader bankControllerReader) {
		myView = view;
		observedBankController = bankControllerReader;
	}

	public void create(AttributeOwnerReader preset, ImageView imageView) {
		setClickEvent(imageView, (iV) -> {
			GenericCommandCenter presetComCenter = new GenericCommandCenter(myView, preset);
			presetComCenter.launch("Preset", iV.getBoundsInParent().getMinX(), iV.getBoundsInParent().getMinY());
		},setRemoveEvent(imageView),preset);
		makeHoverOverName(preset, imageView);
		makePresetDraggable(preset, imageView);
	}

	private void setClickEvent(Node imageView, Consumer<Node> consumer, BiConsumer<AttributeOwnerReader, MouseEvent> c, AttributeOwnerReader aO) {
		imageView.setOnMouseClicked(mouseEvent -> {
			if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
				consumer.accept(imageView);
			} else if (mouseEvent.getButton().equals(MouseButton.SECONDARY)){
				c.accept(aO, mouseEvent);
			}
		});	
	}
	
	private BiConsumer<AttributeOwnerReader, MouseEvent> setRemoveEvent(Node imageView){
		return(a, m) -> {
			ContextMenu removeMenu = new ContextMenu();
			MenuItem removeItem = new MenuItem("Remove from palette");
			removeItem.setOnAction(e -> {
				myView.sendUserModification(new Modification_Remove_FromPalette(a));
			});
			removeMenu.getItems().add(removeItem);
			removeMenu.setAutoHide(true);
			removeMenu.show(imageView, m.getScreenX(), m.getScreenY());
		};
		
	}
	
	private void makeHoverOverName(AttributeOwnerReader preset, ImageView imageView) {
		Tooltip t = new Tooltip(observedBankController.getPresetName(preset));
		imageView.hoverProperty().addListener((o, oldV, newV) -> {
			if (newV) {
				Bounds scenePos = imageView.localToScreen(imageView.getBoundsInLocal());
				t.show(imageView, scenePos.getMaxX(), scenePos.getMinY());
				// TODO someone help
			} else {
				t.hide();
			}
		});
	}

	private void makePresetDraggable(AttributeOwnerReader preset, ImageView imageView) {
		imageView.setOnDragDetected(e -> {
			Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);
			ClipboardContent content = new ClipboardContent();
			content.putString(observedBankController.getPresetName(preset));
			db.setContent(content);
			db.setDragView(imageView.getImage());
		});
	}
}
