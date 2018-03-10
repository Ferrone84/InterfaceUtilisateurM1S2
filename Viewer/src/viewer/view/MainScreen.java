package viewer.view;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import javafx.beans.binding.Bindings;
import static javafx.beans.binding.Bindings.min;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.NumberBinding;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import viewer.Viewer;
import viewer.model.ImageFile;

import viewer.utilities.*;

public class MainScreen extends Screen {
	private List<CMenu> menu_struct;
	private CMenu rightcontextmenu_struct;
	private MenuBar menubar;
	private ImageFile imagefile;
	
	private BorderPane middlecontainer;
	
	private StackPane leftside;
	private Label nofilelabel;
	private ScrollPane imagelistcontainer;
	private ImageFileList imagelist;

	private StackPane rightside;
	private ImageView right_view;
	private DoubleBinding right_pref_hsize;
	private DoubleBinding right_pref_vsize;
	private ContextMenu rightcontextmenu;

	public MainScreen() {
		super(768, 432);
		this.SetTitle("Viewer");
		this.menu_struct = new ArrayList<>();
		this.imagefile = null;
		this.Initialize();
	}

	public MenuBar GetMenuBar() {
		return this.menubar;
	}
	public void SetMenuBar(MenuBar value) {
		this.Remove(this.menubar);
		this.menubar = value;
		this.Add(this.menubar);
	}
	
	private void Initialize() {
		this.MakeMenuStruct();
		this.MakeMenuBar();
		this.MakeBottomOptions();
		this.MakeImagePreview();
		
		Viewer viewer = Viewer.GetInstance();
		viewer.Turn90Event.Subscribe(this.OnRotate(1));
		viewer.Turn180Event.Subscribe(this.OnRotate(2));
		viewer.Turn270Event.Subscribe(this.OnRotate(3));
		viewer.MirrorHEvent.Subscribe(this.OnMirrorH());
		viewer.MirrorVEvent.Subscribe(this.OnMirrorV());
		
		viewer.LangEvent.Subscribe(this.OnLang);
		viewer.OpenedPicEvent.Subscribe(this.OnOpenedPic);
		viewer.OpenedDirEvent.Subscribe(this.OnOpenedDir);
	}

	private void MakeMenuStruct() {
		Viewer viewer = Viewer.GetInstance();
		CMenu menu;
		this.menu_struct = new ArrayList<>();
		this.menu_struct.add(menu = new CMenu("file", new ArrayList<>()));
		menu.Add("diapo", viewer.DiapoEvent);
		menu.Add("open_pic", viewer.OpenPicEvent);
		menu.Add("open_dir", viewer.OpenDirEvent);
		menu.Add("search", viewer.SearchEvent);
		menu.Add("", null);
		menu.Add("close_view", viewer.CloseViewEvent);
		menu.Add("close_dir", viewer.CloseDirEvent);
		menu.Add("", null);
		menu.Add("quit", viewer.QuitEvent);

		this.menu_struct.add(menu = new CMenu("edit", new ArrayList<>()));
		menu.Add("rename", viewer.RenameEvent);
		menu.Add("tags", viewer.TagsEvent);
		menu.Add("", null);
		menu.Add("turn_90_a", viewer.Turn270Event);
		menu.Add("turn_90_h", viewer.Turn90Event);
		menu.Add("turn_180", viewer.Turn180Event);
		menu.Add("mirror_h", viewer.MirrorHEvent);
		menu.Add("mirror_v", viewer.MirrorVEvent);
		menu.Add("", null);
		menu.Add("crop", viewer.CropEvent);
		this.rightcontextmenu_struct = menu;
		
		this.menu_struct.add(menu = new CMenu("lang", new ArrayList<>()));
		menu.Add("en-uk", new RichHandler(viewer.LangEvent, "en-uk"));
		menu.Add("fr-fr", new RichHandler(viewer.LangEvent, "fr-fr"));
		menu.Add("de", new RichHandler(viewer.LangEvent, "de"));
		menu.Add("", null);
		menu.Add("ru", new RichHandler(viewer.LangEvent, "ru"));
	}
	private void MakeMenuBar() {
		MenuBar m = new MenuBar();
		m.prefWidthProperty().bind(this.widthProperty());
		UIFactory.WidthPref(m);
		this.SetMenuBar(m);
		for (CMenu cmenu : this.menu_struct) {
			m.getMenus().add(UIFactory.MakeMenu(cmenu));
		}
	}
	private void MakeBottomOptions() {}
	private void MakeImagePreview() {
		BorderPane borderpane = new BorderPane();
		borderpane.prefWidthProperty().bind(
			this.GetRoot().widthProperty()
		);
		borderpane.prefHeightProperty().bind(
			this.GetRoot().heightProperty()
				.subtract(menubar.heightProperty())
		);
		UIFactory.BothPref(borderpane);
		borderpane.layoutYProperty().bind(menubar.heightProperty());
		this.middlecontainer = borderpane;
		this.MakeLeftSide();
		this.MakeRightSide();
		borderpane.setLeft(this.leftside);
		borderpane.setRight(this.rightside);
		this.Add(borderpane);
	}
	private void MakeLeftSide() {
		StackPane pane = new StackPane();
		Label label = new Label();
		ImageFileList imagefilelist = new ImageFileList();
		
		
		BooleanBinding visibility = this.widthProperty().greaterThan(384);
		NumberBinding dynamicsize = Bindings.when(visibility).then(192).otherwise(0);
		pane.prefWidthProperty().bind(dynamicsize);
		pane.prefHeightProperty().bind(this.middlecontainer.heightProperty());
		UIFactory.BothPref(pane);
		pane.visibleProperty().bind(visibility);
		pane.managedProperty().bind(visibility);
		pane.setStyle("-fx-background-color: #FFFFFF");
		label.setStyle("-fx-background-color: #FFFFFF");
		label.setId("nofile");
		//SplitPane.setResizableWithParent(pane, Boolean.FALSE);
		
		StackPane.setAlignment(label, Pos.CENTER);
		label.visibleProperty().bind(imagefilelist.visibleProperty().not());
		
		imagefilelist.SetParent(pane);
		imagefilelist.setVisible(false);
		
		pane.getChildren().add(label);
		pane.getChildren().add(imagefilelist);
		
		this.leftside = pane;
		this.nofilelabel = label;
		this.imagelist = imagefilelist;
	}
	private void MakeRightSide() {
		StackPane pane = new StackPane();
		this.rightside = pane;
		BooleanBinding visibility = this.widthProperty().greaterThan(384);
		NumberBinding dynamicsize = Bindings.when(visibility).then(192).otherwise(0);
		pane.prefWidthProperty().bind(this.middlecontainer.widthProperty().subtract(dynamicsize));
		pane.prefHeightProperty().bind(this.middlecontainer.heightProperty());
		UIFactory.BothPref(pane);
		
		int horizontal_margin = 32;
		int vertical_margin = 32;
		this.right_pref_hsize = pane.widthProperty().subtract(horizontal_margin);
		this.right_pref_vsize = pane.heightProperty().subtract(vertical_margin);
		
		this.right_view = new ImageView();
		pane.getChildren().add(this.right_view);
		this.right_view.setPreserveRatio(true);
		this.right_view.visibleProperty().bind(
			pane.prefWidthProperty().greaterThan(horizontal_margin)
			.and(pane.prefHeightProperty().greaterThan(vertical_margin))
		);
		this.ResetImageView();
		StackPane.setAlignment(this.right_view, Pos.CENTER);
		
		this.rightcontextmenu = new ContextMenu();
		this.rightcontextmenu.getItems().addAll(UIFactory.MakeMenu(this.rightcontextmenu_struct).getItems());
		this.rightcontextmenu.setAutoHide(true);
		
		this.menubar.getMenus().get(1).disableProperty().bind(this.right_view.imageProperty().isNull());
	}
	
	/* =========================================================================
	|* Les event de l'improbable
	|* Hack monstrueux pour gérer les rotations et les renversements.
	========================================================================= */
	private EventHandler<Event> OnRotate(int quarters) {
		return (EventHandler<Event>) (Event event) -> {
			int rotation = (int)this.right_view.getRotate();
			rotation = rotation + (4 + quarters * (int)this.right_view.getScaleX() * (int)this.right_view.getScaleY()) * 90;
			rotation = rotation % 360;
			this.right_view.setRotate(rotation);
			if ((rotation % 180) == 0) {
				this.right_view.fitWidthProperty().bind(this.right_pref_hsize);
				this.right_view.fitHeightProperty().bind(this.right_pref_vsize);
			} else {
				this.right_view.fitWidthProperty().bind(this.right_pref_vsize);
				this.right_view.fitHeightProperty().bind(this.right_pref_hsize);
			}
			if ((quarters % 2) == 1) {
				double scx = this.right_view.getScaleX();
				double scy = this.right_view.getScaleY();
				this.right_view.setScaleX(scy);
				this.right_view.setScaleY(scx);
			}
		};
	}
	private EventHandler<Event> OnMirrorH() {
		return (EventHandler<Event>) (Event event) -> {
			if ((this.right_view.getRotate() % 180) == 0) {
				this.right_view.setScaleX(this.right_view.getScaleX() * -1);
			} else {
				this.right_view.setScaleY(this.right_view.getScaleY() * -1);
			}
		};
	}
	private EventHandler<Event> OnMirrorV() {
		return (EventHandler<Event>) (Event event) -> {
			if ((this.right_view.getRotate() % 180) == 0) {
				this.right_view.setScaleY(this.right_view.getScaleY() * -1);
			} else {
				this.right_view.setScaleX(this.right_view.getScaleX() * -1);
			}
		};
	}

	public final EventHandler<Event> OnLang = (EventHandler<Event>) (Event event) -> {this.OnLang(event);};
	public void OnLang(Event event) { // RecompileMenu
		for (Menu menu : this.GetMenuBar().getMenus()) {
			UIFactory.Recompile(menu);
		}
		UIFactory.Recompile(rightcontextmenu);
		UIFactory.Recompile(this.nofilelabel);//.setText(Lang.GetWord("nofile"));
		
		this.rightcontextmenu.getItems().setAll(UIFactory.MakeMenu(this.rightcontextmenu_struct).getItems());
	}

	public final EventHandler<Event> OnOpenedPic = (EventHandler<Event>) (Event event) -> {this.OnOpenedPic(event);};
	public void OnOpenedPic(Event event) { // LoadPicture
		this.SetImage(((RichEvent<ImageFile>)event).GetData());
	}
	public void SetImage(ImageFile imagefile) {
		this.imagefile = imagefile;
		Boolean imageisnull = (this.imagefile != null);
		if (imageisnull) {
			this.rightside.setOnContextMenuRequested(this.OnRightSideContext);
		}
		this.ResetImageView();
	}
	private void ResetImageView() {
		this.right_view.setScaleX(1.0);
		this.right_view.setScaleY(1.0);
		this.right_view.setImage(this.imagefile);
		if (this.imagefile == null) {
			this.right_view.fitWidthProperty().bind(this.right_pref_hsize);
			this.right_view.fitHeightProperty().bind(this.right_pref_vsize);
		}
		else {
			this.right_view.fitWidthProperty().bind(min(this.right_pref_hsize, (Double)this.imagefile.getWidth()));
			this.right_view.fitHeightProperty().bind(min(this.right_pref_vsize, (Double)this.imagefile.getHeight()));
		}
	}
	public final EventHandler<ContextMenuEvent> OnRightSideContext = (EventHandler<ContextMenuEvent>) (ContextMenuEvent event) -> {this.OnRightSideContext(event);};
	private void OnRightSideContext(ContextMenuEvent event) {
		if (this.imagefile != null) {
			this.rightcontextmenu.show(this.getWindow(), event.getScreenX(), event.getScreenY());
		}
	}
	
	public final EventHandler<Event> OnOpenedDir = (EventHandler<Event>) (Event event) -> {this.OnOpenedDir(event);};
	public void OnOpenedDir(Event event) {
		if (((RichEvent<File>)event).GetData() == null) {return;}
		this.imagelist.Setup(Viewer.GetInstance().GetImageFiles());
	}
	
	public void PrintDebug() {
		System.out.println("Middle : " + this.middlecontainer.getWidth() + " / " + this.middlecontainer.getHeight());
		System.out.println("Middle : " + this.leftside.getWidth() + " / " + this.leftside.getHeight());
		System.out.println("Middle : " + this.rightside.getWidth() + " / " + this.rightside.getHeight());
		System.out.println("LeftPreview : ");
		this.PrintElement("-> LeftSide : ", this.leftside);
		this.PrintElement("-> ImageListContainer : ", this.imagelistcontainer);
		this.PrintElement("-> ImageList : ", this.imagelist);
		for (ImageFileFrame iff : this.imagelist.getItems()) {
			this.PrintElement(" > ImageFileFrame : ", iff);
		}
		
	}
	public void PrintElement(String name, Region node) {
		if (node == null) {return;}
		System.out.print(name);
		System.out.print(node.getWidth());
		System.out.print(" / ");
		System.out.print(node.getHeight());
		System.out.print(" (");
		System.out.print(node.getLayoutX());
		System.out.print(", ");
		System.out.print(node.getLayoutY());
		System.out.println(")");
	}

	// @Override
	// public void OnWidthChange(Double old_value, Double new_value) {
	// 	System.out.println("Width changed");
	// }
	// @Override
	// public void OnHeightChange(Double old_value, Double new_value) {
	// 	System.out.println("Height changed");
	// }
	
}
