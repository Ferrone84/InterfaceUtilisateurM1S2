package viewer.utilities;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class UIFactory {
	private UIFactory() {}

	public static Button NewButton(String text, int x, int y) {
		Button button = new Button(text);
		/*btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Hello World!");
			}
		});*/
		return button;
	}
	public static Label NewLabel() {
		return new Label();
	}
	public static Pane NewPane() {
		return new Pane();
	}

	public static Menu MakeMenu(CMenu cmenu) {
		MenuItem item;
		Menu menu = new Menu();
		menu.setText(Lang.GetWord(cmenu.getKey()));
		for(CMenuItem citem : cmenu.getValue()) {
			if (citem.getValue() == null) {
				menu.getItems().add(new SeparatorMenuItem());
				continue;
			}
			item = new MenuItem();
			item.setText(Lang.GetWord(citem.getKey()));
			item.setOnAction(citem.getValue());
			item.setGraphic(new ImageView("file:resources/img/" + citem.getKey() + ".png"));
			menu.getItems().add(item);
		}
		return menu;
	}
}

// TODO : set la taille des images !! mdddddddr