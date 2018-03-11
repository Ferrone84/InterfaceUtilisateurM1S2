package viewer.view;

import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import viewer.Viewer;
import viewer.model.ImageFile;
import viewer.utilities.Lang;
import viewer.utilities.RichHandler;
import viewer.utilities.UIFactory;

public class SearchScreen extends Screen {
	private List<ImageFile> imagefiles;
	private Label searchlabel;
	private TextField searchbox; 
	private Button searchbutton;
	private ImageFileList imagefilelist; 
	public SearchScreen() {
		super(480, 512);
		this.imagefiles = null;
		this.Initialize();
	}
	public void Setup(List<ImageFile> imagefiles) {
		this.imagefilelist.Setup(imagefiles);
	}
	private void Initialize() {
		AnchorPane anchorpane = new AnchorPane();
		Label label = new Label();
		TextField textfield = new TextField();
		Button searchbutton = new Button();
		ImageFileList imagefilelist = new ImageFileList();
		Button okbutton = new Button("OK");
		
		
		anchorpane.prefWidthProperty().bind(this.GetRoot().widthProperty());
		anchorpane.prefHeightProperty().bind(this.GetRoot().heightProperty());
		UIFactory.BothPref(anchorpane);
		
		textfield.setMinWidth(Region.USE_COMPUTED_SIZE);
		textfield.setMaxWidth(Region.USE_COMPUTED_SIZE);
		
		okbutton.setOnAction(this.OnClose);
		okbutton.setOnKeyPressed(UIFactory.ButtonEnterFix);
		
		AnchorPane.setTopAnchor(label, 12.0);
		AnchorPane.setLeftAnchor(label, 16.0);
		
		AnchorPane.setTopAnchor(textfield, 32.0);
		AnchorPane.setLeftAnchor(textfield, 16.0);
		AnchorPane.setRightAnchor(textfield, 104.0);
		AnchorPane.setTopAnchor(searchbutton, 32.0);
		AnchorPane.setRightAnchor(searchbutton, 16.0);
		
		searchbutton.setPrefWidth(80.0);
		UIFactory.WidthPref(searchbutton);
		
		AnchorPane.setTopAnchor(imagefilelist, 64.0);
		AnchorPane.setLeftAnchor(imagefilelist, 16.0);
		AnchorPane.setRightAnchor(imagefilelist, 16.0);
		AnchorPane.setBottomAnchor(imagefilelist, 64.0);
		UIFactory.BothPref(imagefilelist);
		
		AnchorPane.setBottomAnchor(okbutton, 12.0);
		AnchorPane.setRightAnchor(okbutton, 16.0);
		
		EventHandler handler = new RichHandler(
			Viewer.GetInstance().SearchByTagsEvent,
			textfield
		);
		textfield.setOnAction(handler);
		searchbutton.setOnAction(handler);
		
		imagefilelist.prefWidthProperty().bind(this.widthProperty().subtract(16));
		imagefilelist.prefWidthProperty().bind(this.heightProperty().subtract(48));
		
		this.searchlabel = label;
		this.searchbox = textfield;
		this.searchbutton = searchbutton;
		this.imagefilelist = imagefilelist;
		
		this.Add(anchorpane);
		anchorpane.getChildren().add(label);
		anchorpane.getChildren().add(textfield);
		anchorpane.getChildren().add(searchbutton);
		anchorpane.getChildren().add(imagefilelist);
		anchorpane.getChildren().add(okbutton);
		
		Viewer.GetInstance().LangEvent.Subscribe(this.OnLang);
		this.setOnKeyPressed(this.OnKeyPressed);
	}
	
	
	public final EventHandler<Event> OnLang = (EventHandler<Event>) (Event event) -> {this.OnLang(event);};
	public void OnLang(Event event) { // RecompileMenu
		this.SetTitle(Lang.GetWord("search"));
		this.searchlabel.setText(Lang.GetWord("tags") + " :");
		this.searchbox.setPromptText(Lang.GetWord("tags"));
		this.searchbutton.setText(Lang.GetWord("search"));
	}
	public final EventHandler<KeyEvent> OnKeyPressed = (EventHandler<KeyEvent>) (KeyEvent event) -> {this.OnKeyPressed(event);};
	private void OnKeyPressed(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ESCAPE)) {this.OnClose(event);}
	}
	public final EventHandler<ActionEvent> OnClose = (EventHandler<ActionEvent>) (ActionEvent event) -> {this.OnClose(event);};
	
	public void OnClose(Event event) {
		((Stage)this.getWindow()).close();
	}
}
