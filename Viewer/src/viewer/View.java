package viewer;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class View extends Scene {

	public View() {
		super(new Pane(), 768, 432);
	}

	public void Add(Node node) {
		((Pane)this.getRoot()).getChildren().add(node);
	}
    
    public Pane GetRoot() {
        return (Pane)this.getRoot();
    }
}
