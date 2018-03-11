package viewer.view;

import static javafx.beans.binding.Bindings.min;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import viewer.model.ImageFile;

public class ImageFileFrame extends StackPane {
	private ImageView imageview;
	private ImageFile image;
	private Tooltip tooltip;
	private Label name;
	public ImageFileFrame(ImageFile image) {
		super();
		this.Setup();
		this.SetImageFile(image);
	}
	
	public ImageFile GetImageFile() {
		return this.image;
	}
	public void SetImageFile(ImageFile imagefile) {
		this.image = imagefile;
		
		this.imageview.fitWidthProperty().bind(min(this.widthProperty().subtract(32), this.image.getWidth()));
		this.imageview.fitHeightProperty().bind(min(this.heightProperty().subtract(32), this.image.getHeight()));
		
		this.name.setText(this.image.GetName());
		this.imageview.setImage(this.image);
		this.tooltip.setText(this.image.GetFullName());
	}
	
	public void Setup() {
		this.name = new Label();
		this.imageview = new ImageView();
		this.getChildren().add(this.name);
		this.getChildren().add(this.imageview);
		
		this.name.setPrefWidth(Region.USE_COMPUTED_SIZE);
		this.name.setMinWidth(Region.USE_COMPUTED_SIZE);
		this.name.setMaxWidth(Region.USE_COMPUTED_SIZE);
		StackPane.setAlignment(this.name, Pos.BOTTOM_CENTER);
		
		this.imageview.setPreserveRatio(true);
		StackPane.setAlignment(this.imageview, Pos.CENTER);
		this.tooltip = new Tooltip();
		Tooltip.install(this, this.tooltip);
	}
}
