package viewer.view;

import java.util.List;
import javafx.beans.binding.DoubleBinding;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import viewer.Viewer;
import viewer.model.ImageFile;
import viewer.utilities.UIFactory;

public class ImageFileList extends ListView<ImageFileFrame> {
	private DoubleBinding inner_width;
	private int inner_height;
	public ImageFileList() {
		super();
		this.inner_width = this.widthProperty().subtract(32);
		this.inner_height = 96;
	}
	public ImageFileList(Pane parent) {
		this();
		this.SetParent(parent);
	}
	public void SetParent(Pane parent) {
		this.prefWidthProperty().bind(parent.widthProperty().subtract(2));
		this.setPrefHeight(Region.USE_COMPUTED_SIZE);
		UIFactory.WidthPref(this);
		this.setMinHeight(Region.USE_COMPUTED_SIZE);
		this.setMaxHeight(Region.USE_COMPUTED_SIZE);
	}
	public void Setup(List<ImageFile> list) {
		this.setOnKeyPressed(this.OnKeyPressed);
		this.getItems().clear();
		Boolean visible = !((list == null) || (list.isEmpty()));
		this.setVisible(visible);
		if (!visible) {return;}
		ImageFileFrame iff;
		EventHandler<Event> handler = Viewer.GetInstance().PreviewClickedEvent;
		for (ImageFile i : list) {
			iff = new ImageFileFrame(i);
			iff.prefWidthProperty().bind(this.inner_width);
			iff.setPrefHeight(this.inner_height);
			UIFactory.BothPref(iff);
			iff.setOnMouseClicked(handler);
			this.getItems().add(iff);
		}
	}
	public final EventHandler<KeyEvent> OnKeyPressed = (EventHandler<KeyEvent>) (KeyEvent event) -> {this.OnKeyPressed(event);};
	private void OnKeyPressed(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.SPACE)) {
			ImageFileFrame iff = this.getSelectionModel().getSelectedItem();
			if (iff != null) {
				Viewer.GetInstance().PreviewClickedEvent.Call(new Event(iff, null, EventType.ROOT));
			}
		}
	}
}
