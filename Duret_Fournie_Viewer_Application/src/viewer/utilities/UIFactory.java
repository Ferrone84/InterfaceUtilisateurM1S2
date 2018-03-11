package viewer.utilities;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;

public class UIFactory {
	public static final EventHandler<KeyEvent> ButtonEnterFix = (EventHandler<KeyEvent>) (KeyEvent event) -> {
		if (event.getCode().equals(KeyCode.ENTER)) {
			((Button)event.getSource()).fire();
			event.consume();
		}
	};

	private UIFactory() {}
	
	public static void BothPref(Region region) {
		UIFactory.WidthPref(region);
		UIFactory.HeightPref(region);
	}
	public static void WidthPref(Region region) {
		region.setMinWidth(Region.USE_PREF_SIZE);
		region.setMaxWidth(Region.USE_PREF_SIZE);
	}
	public static void HeightPref(Region region) {
		region.setMinHeight(Region.USE_PREF_SIZE);
		region.setMaxHeight(Region.USE_PREF_SIZE);
	}

	public static Menu MakeMenu(CMenu cmenu) {
		return UIFactory.MakeMenu(cmenu, new Menu());
	}
	public static Menu MakeMenu(CMenu cmenu, Menu menu) {
		MenuItem item;
		menu.setId(cmenu.getKey());
		for(CMenuItem citem : cmenu.getValue()) {
			if (citem.getValue() == null) {
				menu.getItems().add(new SeparatorMenuItem());
				continue;
			}
			item = new MenuItem();
			item.setId(citem.getKey());
			item.setOnAction(citem.getValue());
            ImageView iv = new ImageView(citem.GetImage());
            iv.setFitHeight(16);
            iv.setFitWidth(16);
            iv.setPreserveRatio(true);
            item.setGraphic(iv);
			menu.getItems().add(item);
		}
		return menu;
	}
	public static void Recompile(Menu menu) {
		menu.setText(Lang.GetWord(menu.getId()));
		for (MenuItem item : menu.getItems()) {
			item.setText(Lang.GetWord(item.getId()));
		}
	}
	public static void Recompile(ContextMenu menu) {
		for (MenuItem item : menu.getItems()) {
			item.setText(Lang.GetWord(item.getId()));
		}
	}
	public static void Recompile(Label label) {
		label.setText(Lang.GetWord(label.getId()));
	}
}