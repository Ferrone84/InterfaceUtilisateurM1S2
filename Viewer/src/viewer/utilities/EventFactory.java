package viewer.utilities;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import viewer.Viewer;

public class EventFactory {
	private EventFactory() {}

	public static EventHandler CloseDir(Viewer t) {
		return (EventHandler<ActionEvent>) (ActionEvent event) -> {t.CloseDir();};
	}
	public static EventHandler CloseView(Viewer t) {
		return (EventHandler<ActionEvent>) (ActionEvent event) -> {t.CloseView();};
	}
	public static EventHandler Crop(Viewer t) {
		return (EventHandler<ActionEvent>) (ActionEvent event) -> {t.Crop();};
	}
	public static EventHandler Diapo(Viewer t) {
		return (EventHandler<ActionEvent>) (ActionEvent event) -> {t.Diapo();};
	}
	public static EventHandler Lang(Viewer t, String lang) {
		return (EventHandler<ActionEvent>) (ActionEvent event) -> {t.Lang(lang);};
	}
	public static EventHandler Mirror(Viewer t, boolean direction) {
		return (EventHandler<ActionEvent>) (ActionEvent event) -> {t.Mirror(direction);};
	}
	public static EventHandler OpenDir(Viewer t) {
		return (EventHandler<ActionEvent>) (ActionEvent event) -> {t.OpenDir();};
	}
	public static EventHandler OpenPic(Viewer t) {
		return (EventHandler<ActionEvent>) (ActionEvent event) -> {t.OpenPic();};
	}
	public static EventHandler Quit(Viewer t) {
		return (EventHandler<ActionEvent>) (ActionEvent event) -> {t.Quit();};
	}
	public static EventHandler Rename(Viewer t) {
		return (EventHandler<ActionEvent>) (ActionEvent event) -> {t.Rename();};
	}
	public static EventHandler Search(Viewer t) {
		return (EventHandler<ActionEvent>) (ActionEvent event) -> {t.Search();};
	}
	public static EventHandler Tags(Viewer t) {
		return (EventHandler<ActionEvent>) (ActionEvent event) -> {t.Tags();};
	}
	public static EventHandler Turn(Viewer t, int quarters) {
		return (EventHandler<ActionEvent>) (ActionEvent event) -> {t.Turn(quarters);};
	}
}