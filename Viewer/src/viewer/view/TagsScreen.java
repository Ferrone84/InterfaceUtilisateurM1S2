package viewer.view;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import viewer.Viewer;
import viewer.model.ImageFile;
import viewer.model.ImageTags;
import viewer.utilities.Lang;
import viewer.utilities.RichEvent;
import viewer.utilities.RichHandler;
import viewer.utilities.UIFactory;

public class TagsScreen extends Screen {
	private AnchorPane anchorpane;
	private ImageTags imagetags;
	private Label newtaglabel;
	private TextField newtag;
	private Button addtag;
	private Button ok;
	private FlowPane tagslist;
	
	public TagsScreen() {
		super(480,270);
		this.Initialize();
	}
	private void Initialize() {
		this.imagetags = null;
		AnchorPane anchorpane = new AnchorPane();
		Label label = new Label();
		TextField textfield = new TextField();
		Button addbutton = new Button("+");
		ScrollPane scrollpane = new ScrollPane();
		AnchorPane scrollpanecontent = new AnchorPane();
		FlowPane flowpane = new FlowPane();
		Button okbutton = new Button("OK");
		
		anchorpane.prefWidthProperty().bind(this.GetRoot().widthProperty());
		anchorpane.prefHeightProperty().bind(this.GetRoot().heightProperty());
		UIFactory.BothPref(anchorpane);
		
		textfield.setMinWidth(Region.USE_COMPUTED_SIZE);
		textfield.setMaxWidth(Region.USE_COMPUTED_SIZE);
		
		EventHandler handler = new RichHandler(
			Viewer.GetInstance().TagCreationEvent,
			textfield
		);
		textfield.setOnAction(handler);
		addbutton.setOnAction(handler);
		okbutton.setOnAction(this.OnClose);
		okbutton.setOnKeyPressed(UIFactory.ButtonEnterFix);
		
		AnchorPane.setTopAnchor(label, 12.0);
		AnchorPane.setLeftAnchor(label, 16.0);
		
		AnchorPane.setTopAnchor(textfield, 32.0);
		AnchorPane.setLeftAnchor(textfield, 16.0);
		AnchorPane.setRightAnchor(textfield, 48.0);
		AnchorPane.setTopAnchor(addbutton, 32.0);
		AnchorPane.setRightAnchor(addbutton, 16.0);
		
		AnchorPane.setTopAnchor(scrollpane, 64.0);
		AnchorPane.setLeftAnchor(scrollpane, 16.0);
		AnchorPane.setRightAnchor(scrollpane, 16.0);
		AnchorPane.setBottomAnchor(scrollpane, 64.0);
		UIFactory.BothPref(scrollpane);
		
		
		
		flowpane.setLayoutX(8);
		flowpane.setLayoutY(4);
		flowpane.prefWidthProperty().bind(scrollpane.widthProperty().subtract(16));
		UIFactory.WidthPref(flowpane);
		scrollpanecontent.getChildren().add(flowpane);
		scrollpane.setContent(scrollpanecontent);
		
		AnchorPane.setBottomAnchor(okbutton, 12.0);
		AnchorPane.setRightAnchor(okbutton, 16.0);
		
		this.anchorpane = anchorpane;
		this.newtaglabel = label;
		this.newtag = textfield;
		this.addtag = addbutton;
		this.tagslist = flowpane;
		this.ok = okbutton;
		
		this.Add(anchorpane);
		anchorpane.getChildren().add(label);
		anchorpane.getChildren().add(textfield);
		anchorpane.getChildren().add(addbutton);
		anchorpane.getChildren().add(scrollpane);
		anchorpane.getChildren().add(okbutton);
		
		Viewer.GetInstance().LangEvent.Subscribe(this.OnLang);
		Viewer.GetInstance().OpenedPicEvent.Subscribe(this.OnOpenedPic);
		Viewer.GetInstance().TagCreationEvent.Subscribe(this.OnTagCreation);
		Viewer.GetInstance().TagRemovalEvent.Subscribe(this.OnTagRemoval);
		this.setOnKeyPressed(this.OnKeyPressed);
	}
	
	public final EventHandler<Event> OnOpenedPic = (EventHandler<Event>) (Event event) -> {this.OnOpenedPic(event);};
	public void OnOpenedPic(Event event) {
		this.imagetags = ((RichEvent<ImageFile>)event).GetData().GetTags();
		this.ResetTags();
	}
	private void ResetTags() {
		this.tagslist.getChildren().clear();
		if (this.imagetags == null) {return;}
		for (String tag : this.imagetags.GetTags()) {
			this.AddTag(tag);
		}
		this.SortTags();
	}
	private void AddTag(String tag) {
		TagFrame tagframe = new TagFrame(tag);
		tagframe.SetEventHandler(Viewer.GetInstance().TagRemovalEvent);
		this.tagslist.getChildren().add(tagframe);
	}
	private void SortTags() {
		this.tagslist.getChildren().setAll(this.tagslist.getChildren().sorted());
	}
	public final EventHandler<Event> OnTagCreation = (EventHandler<Event>) (Event event) -> {this.OnTagCreation(event);};
	public void OnTagCreation(Event event) {
		if (event.isConsumed()) {return;}
		TextField textfield = ((RichEvent<TextField>)event).GetData();
		this.AddTag(textfield.getText());
		this.SortTags();
		textfield.setText("");
	}
	public final EventHandler<Event> OnTagRemoval = (EventHandler<Event>) (Event event) -> {this.OnTagRemoval(event);};
	public void OnTagRemoval(Event event) {
		TagFrame tagframe = ((RichEvent<TagFrame>)event).GetData();
		this.tagslist.getChildren().remove(tagframe);
	}
	
	public final EventHandler<Event> OnLang = (EventHandler<Event>) (Event event) -> {this.OnLang(event);};
	public void OnLang(Event event) { // RecompileMenu
		this.SetTitle(Lang.GetWord("tags"));
		this.newtaglabel.setText(Lang.GetWord("tag") + " :");
		this.newtag.setPromptText(Lang.GetWord("tag"));
		
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
