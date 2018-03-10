package viewer.view;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import viewer.utilities.RichHandler;
import viewer.utilities.UIFactory;

public class TagFrame extends AnchorPane implements Comparable<TagFrame> {
	private final Label label;
	private final Button button;
	public TagFrame(String tag) {
		this.setPadding(new Insets(4, 8, 4, 8));
		this.label = new Label(tag);
		this.button = new Button("×");
		
		this.getChildren().add(this.label);
		this.label.setGraphic(this.button);
		this.label.setContentDisplay(ContentDisplay.RIGHT);
		this.button.setOnKeyPressed(UIFactory.ButtonEnterFix);
	}
	public void SetEventHandler(EventHandler<Event> handler) {
		this.button.setOnAction(new RichHandler(handler, this));
	}
	public String GetTag() {
		return this.label.getText();
	}
	
	public int compareTo(TagFrame other) {
		return this.GetTag().compareTo(other.GetTag());
	}
}
