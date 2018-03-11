package viewer.view;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Screen extends Scene {
	private static final int DEFAULT_WIDTH = 768;
	private static final int DEFAULT_HEIGHT = 432;
	private static final int DEFAULT_MIN_WIDTH = 256;
	private static final int DEFAULT_MIN_HEIGHT = 144;

	private String title;
	private int min_width;
	private int min_height;
	// public final ChangeListener<Number> OnWidthChange = (ChangeListener<Number>) (ObservableValue<? extends Number> observable, Number old_value, Number new_value) -> {
	// 	this.OnWidthChange((Double)old_value, (Double)new_value);
	// };
	// public final ChangeListener<Number> OnHeightChange = (ChangeListener<Number>) (ObservableValue<? extends Number> observable, Number old_value, Number new_value) -> {
	// 	this.OnHeightChange((Double)old_value, (Double)new_value);
	// };

	public Screen() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	public Screen(int width, int height) {
		super(new Pane(), width, height);
		this.title = "";
		this.SetMinWidth(DEFAULT_MIN_WIDTH);
		this.SetMinHeight(DEFAULT_MIN_HEIGHT);
		// this.widthProperty().addListener(this.OnWidthChange);
		// this.heightProperty().addListener(this.OnHeightChange);
	}
	
	public int GetMinWidth() {
		return this.min_width;
	}
	public void SetMinWidth(int value) {
		this.min_width = value;
	}
	public int GetMinHeight() {
		return this.min_height;
	}
	public void SetMinHeight(int value) {
		this.min_height = value;
	}
	
	public void Add(Node node) {
		if ((node != null)) {
			this.GetRoot().getChildren().add(node);
		}
	}
	public void Remove(Node node) {
		if ((node != null)) {
			this.GetRoot().getChildren().remove(node);
		}
	}

	public Pane GetRoot() {
		return (Pane)this.getRoot();
	}



	// public void OnWidthChange(Double old_value, Double new_value) {
	// 	System.out.println("Width changed");
	// }
	// public void OnHeightChange(Double old_value, Double new_value) {
	// 	System.out.println("Height changed");
	// }

	public void Setup(Object object) {

	}
	
	public String GetTitle() {
		return this.title;
	}
	public void SetTitle(String value) {
		this.title = value;
		Stage frame = ((Stage)this.getWindow());
		if (frame != null) {
			frame.setTitle(this.GetTitle());
		}
	}

}
