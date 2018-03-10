package viewer.view;

import java.util.HashMap;
import java.util.Map;
import javafx.stage.Modality;

import javafx.stage.Stage;

public class View {
	private Stage mainframe;
	private Screen screen;
	private String screen_name;
	private final Map<String, Stage> frames;
	private final Map<String, Screen> screens;
	public View(Stage frame) {
		this.frames = new HashMap<>();
		this.screens = new HashMap<>();
		this.screens.put("main", new MainScreen());
		this.screens.put("tags", new TagsScreen());
		this.screen = null;
		this.mainframe = null;
		this.SetFrame(frame);
	}

	public Stage GetFrame() {
		return this.mainframe;
	}
	public void SetFrame(Stage value) {
		this.mainframe = value;
		this.SwitchScreen(this.GetScreen());
	}

	public Screen GetScreen() {
		return this.screen;
	}
	public Screen GetScreen(String id) {
		return this.screens.get(id);
	}
	public void SwitchScreen(String id) {
		this.SwitchScreen(this.GetScreen(id));
	}
	public void SwitchScreen(Screen screen) {
		if (screen == null) {return;}
		if (null != this.GetFrame()) {
			this.GetFrame().setScene(this.screen = screen);
			this.GetFrame().setMinWidth(screen.GetMinWidth());
			this.GetFrame().setMinHeight(screen.GetMinHeight());
			this.GetFrame().setTitle(screen.GetTitle());
		}
	}

	public void Show() {
		if (this.GetFrame() != null) {
			this.GetFrame().show();
		}
	}
	public void Hide() {
		if (this.GetFrame() != null) {
			this.GetFrame().hide();
		}
	}

	
	public void Summon(String screen) {
		this.Summon(screen, this.GetScreen(screen));
	}
	public void Summon(String frame, String screen) {
		this.Summon(frame, this.GetScreen(screen));
	}
	public void Summon(String frame, Screen screen) {
		if (screen == null) {return;}
		final Stage dialog = new Stage();
		//this.frames.put(frame, dialog);
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(this.mainframe);
		dialog.setTitle(screen.GetTitle());
		dialog.setScene(screen);
		dialog.showAndWait();
	}
	public void SummonError(String error) {
		System.out.println(error);
	}
}
