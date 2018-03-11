package viewer.utilities;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.util.Pair;

public class CMenuItem extends Pair<String, EventHandler<ActionEvent>> {
	private Image image;
	public CMenuItem() {this("");}
	public CMenuItem(String key) {this(key, null);}
	public CMenuItem(String key, EventHandler value) {
		super(key, value);
		this.image = new Image("file:resources/img/" + key + ".png");
	}
	public Image GetImage() {
		return this.image;
	}
}