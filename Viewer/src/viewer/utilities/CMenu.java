package viewer.utilities;

import java.util.List;
import javafx.event.EventHandler;
import javafx.util.Pair;

public class CMenu extends Pair<String, List<CMenuItem>> {
	public CMenu() {this("");}
	public CMenu(String key) {this(key, null);}
	public CMenu(String key, List<CMenuItem> value) {
		super(key, value);
	}
	public void Add(CMenuItem item) {
		this.getValue().add(item);
	}
	public void Add(String key, EventHandler handler) {
		this.getValue().add(new CMenuItem(key, handler));
	}
}