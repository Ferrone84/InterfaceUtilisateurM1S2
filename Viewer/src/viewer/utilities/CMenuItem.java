package viewer.utilities;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Pair;

public class CMenuItem extends Pair<String, EventHandler<ActionEvent>> {
	public CMenuItem() {this("");}
	public CMenuItem(String key) {this(key, null);}
	public CMenuItem(String key, EventHandler value) {
		super(key, value);
	}
}