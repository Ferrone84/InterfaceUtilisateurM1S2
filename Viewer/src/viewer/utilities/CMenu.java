package viewer.utilities;

import java.util.List;
import javafx.util.Pair;

public class CMenu extends Pair<String, List<CMenuItem>> {
	public CMenu() {this("");}
	public CMenu(String key) {this(key, null);}
	public CMenu(String key, List<CMenuItem> value) {
		super(key, value);
	}
	public void add(CMenuItem item) {
		this.getValue().add(item);
	}
}