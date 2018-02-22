package viewer;

import java.util.List;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.control.Control;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

import viewer.utilities.*;

public class Viewer extends Application {
	private static Viewer instance;
	public static Viewer GetInstance() {
		return Viewer.instance;
	}
	

	private Stage frame;
	private View view;
	public Viewer() {
		Viewer.instance = this;
	}

	@Override
	public void start(Stage frame) {
		this.view = new View();
		this.frame = frame;
		this.frame.setTitle("Viewer");

		this.InitializeView();
		
		this.frame.setScene(this.view);
		this.frame.show();
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	private void InitializeView() {
		// Setup toolbar menu
		MenuBar menubar = new MenuBar();
		menubar.prefWidthProperty().bind(this.frame.widthProperty());
		menubar.setMaxWidth(Control.USE_COMPUTED_SIZE);
		this.view.Add(menubar);

		List<CMenu> options = new ArrayList<>();
		CMenu menu;
		menu = new CMenu("file", new ArrayList<>());
		menu.add(new CMenuItem("diapo", EventFactory.Diapo(this)));
		menu.add(new CMenuItem("open_pic", EventFactory.OpenPic(this)));
		menu.add(new CMenuItem("open_dir", EventFactory.OpenDir(this)));
		menu.add(new CMenuItem("search", EventFactory.Search(this)));
		menu.add(new CMenuItem("", null));
		menu.add(new CMenuItem("close_view", EventFactory.CloseView(this)));
		menu.add(new CMenuItem("close_dir", EventFactory.CloseDir(this)));
		menu.add(new CMenuItem("", null));
		menu.add(new CMenuItem("quit", EventFactory.Quit(this)));
		menubar.getMenus().add(UIFactory.MakeMenu(menu));

		menu = new CMenu("edit", new ArrayList<>());
		menu.add(new CMenuItem("rename", EventFactory.Rename(this)));
		menu.add(new CMenuItem("tags", EventFactory.Tags(this)));
		menu.add(new CMenuItem("", null));
		menu.add(new CMenuItem("turn_90_a", EventFactory.Turn(this, 3)));
		menu.add(new CMenuItem("turn_90_h", EventFactory.Turn(this, 1)));
		menu.add(new CMenuItem("turn_180", EventFactory.Turn(this, 2)));
		menu.add(new CMenuItem("mirror_h", EventFactory.Mirror(this, false)));
		menu.add(new CMenuItem("mirror_v", EventFactory.Mirror(this, true)));
		menu.add(new CMenuItem("", null));
		menu.add(new CMenuItem("crop", EventFactory.Crop(this)));
		menubar.getMenus().add(UIFactory.MakeMenu(menu));

		menu = new CMenu("lang", new ArrayList<>());
		menu.add(new CMenuItem("en-uk", EventFactory.Lang(this, "en-uk")));
		menu.add(new CMenuItem("fr-fr", EventFactory.Lang(this, "fr-fr")));
		menu.add(new CMenuItem("ge", EventFactory.Lang(this, "ge")));
		menu.add(new CMenuItem("", null));
		menu.add(new CMenuItem("ru", EventFactory.Lang(this, "ru")));
		menubar.getMenus().add(UIFactory.MakeMenu(menu));
	}
	
	public void CloseDir() {

	}
	public void CloseView() {

	}
	public void Crop() {

	}
	public void Diapo() {

	}
	public void Lang(String lang) {

	}
	public void Mirror(boolean direction) {

	}
	public void OpenDir() {

	}
	public void OpenPic() {

	}
	public void Quit() {

	}
	public void Rename() {

	}
	public void Search() {

	}
	public void Tags() {

	}
	public void Turn(int quarters) {

	}
}
