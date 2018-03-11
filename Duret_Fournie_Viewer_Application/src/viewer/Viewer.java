package viewer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import viewer.view.View;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import viewer.model.ImageFile;
import viewer.model.ImageTags;

import viewer.utilities.*;
import viewer.view.ImageFileFrame;
import viewer.view.Screen;
import viewer.view.SearchScreen;
import viewer.view.TagFrame;

public class Viewer extends Application {
	private static Viewer instance;
	public static Viewer GetInstance() {
		return Viewer.instance;
	}
	public final Delegate CloseDirEvent = new Delegate();
	public final Delegate CloseViewEvent = new Delegate();
	public final Delegate CropEvent = new Delegate();
	public final Delegate DiapoEvent = new Delegate();
	public final Delegate LangEvent = new Delegate();
	public final Delegate MirrorHEvent = new Delegate();
	public final Delegate MirrorVEvent = new Delegate();
	public final Delegate OpenDirEvent = new Delegate();
	public final Delegate OpenedDirEvent = new Delegate();
	public final Delegate OpenPicEvent = new Delegate();
	public final Delegate OpenedPicEvent = new Delegate();
	public final Delegate PreviewClickedEvent = new Delegate();
	public final Delegate QuitEvent = new Delegate();
	public final Delegate RenameEvent = new Delegate();
	public final Delegate SearchEvent = new Delegate();
	public final Delegate SearchByTagsEvent = new Delegate();
	public final Delegate TagsEvent = new Delegate();
	public final Delegate TagCreationEvent = new Delegate();
	public final Delegate TagRemovalEvent = new Delegate();
	public final Delegate Turn90Event = new Delegate();
	public final Delegate Turn180Event = new Delegate();
	public final Delegate Turn270Event = new Delegate();

	private final EventHandler<Event> OnCloseDir = (EventHandler<Event>) (Event event) -> {this.OnCloseDir(event);};
	private final EventHandler<Event> OnCloseView = (EventHandler<Event>) (Event event) -> {this.OnCloseView(event);};
	private final EventHandler<Event> OnLang = (EventHandler<Event>) (Event event) -> {this.OnLang(event);};
	private final EventHandler<Event> OnQuit = (EventHandler<Event>) (Event event) -> {this.OnQuit(event);};
	private final EventHandler<Event> OnOpenDir = (EventHandler<Event>) (Event event) -> {this.OnOpenDir(event);};
	private final EventHandler<Event> OnOpenedDir = (EventHandler<Event>) (Event event) -> {this.OnOpenedDir(event);};
	private final EventHandler<Event> OnOpenPic = (EventHandler<Event>) (Event event) -> {this.OnOpenPic(event);};
	private final EventHandler<Event> OnOpenedPic = (EventHandler<Event>) (Event event) -> {this.OnOpenedPic(event);};
	private final EventHandler<Event> OnSearch = (EventHandler<Event>) (Event event) -> {this.OnSearch(event);};
	private final EventHandler<Event> OnSearchByTags = (EventHandler<Event>) (Event event) -> {this.OnSearchByTags(event);};
	private final EventHandler<Event> OnTags = (EventHandler<Event>) (Event event) -> {this.OnTags(event);};
	private final EventHandler<Event> OnTagCreation = (EventHandler<Event>) (Event event) -> {this.OnTagCreation(event);};
	private final EventHandler<Event> OnTagRemoval = (EventHandler<Event>) (Event event) -> {this.OnTagRemoval(event);};
	
	private View view;
	private List<ImageFile> imagefiles;
	private ImageFile imagefile;
	public Viewer() {
		Viewer.instance = this;
	}

	@Override
	public void start(Stage frame) {
		this.imagefiles = new ArrayList<>();
		this.DefineBaseEvents();
		this.view = new View(frame);

		this.InitializeView();
		this.view.Show();
		try {
			ImageTags.LoadFromFile("./resources/tags.yml");
		}
		catch (IOException e) {
			this.view.SummonError("Could not load image tags : " + e.getMessage());
		}
	}
	@Override
	public void stop() {
		ImageTags.SaveToFile("./resources/tags.yml");
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	public void DefineBaseEvents() {
		this.CloseDirEvent.Subscribe(this.OnCloseDir);
		this.CloseViewEvent.Subscribe(this.OnCloseView);
		this.LangEvent.Subscribe(this.OnLang);
		this.QuitEvent.Subscribe(this.OnQuit);
		this.OpenDirEvent.Subscribe(this.OnOpenDir);
		this.OpenedDirEvent.Subscribe(this.OnOpenedDir);
		this.OpenPicEvent.Subscribe(this.OnOpenPic);
		this.OpenedPicEvent.Subscribe(this.OnOpenedPic);
		this.PreviewClickedEvent.Subscribe(OnPreviewClicked);
		this.SearchEvent.Subscribe(this.OnSearch);
		this.SearchByTagsEvent.Subscribe(this.OnSearchByTags);
		this.TagsEvent.Subscribe(this.OnTags);
		this.TagCreationEvent.Subscribe(this.OnTagCreation);
		this.TagRemovalEvent.Subscribe(this.OnTagRemoval);
	}
	public void InitializeView() {
		this.LangEvent.Call(new RichEvent<String>("fr-fr"));
		this.view.SwitchScreen("main");
	}
	
	public List<ImageFile> GetImageFiles() {
		return this.imagefiles;
	}
	public void SetImageFiles(List<ImageFile> imagefiles) {
		this.imagefiles = imagefiles;
	}

	public void OnCloseDir(Event event) {
		this.imagefiles = null;
	}
	public void OnCloseView(Event event) {
		this.imagefile = null;
	}
	private void OnLang(Event event) {
		Lang.Load(((RichEvent<String>)event).GetData());
	};
	private void OnQuit(Event event) {
		Platform.exit();
	};
	private void OnOpenDir(Event event) {
		DirectoryChooser directory_chooser = new DirectoryChooser();
		File directory = directory_chooser.showDialog(this.view.GetFrame());
		OpenedDirEvent.Call(new RichEvent(directory));
	};
	private void OnOpenedDir(Event event) {
		File directory = ((RichEvent<File>)event).GetData();
		if (directory == null) {return;}
		String[] extensions = {"png", "jpg", "jpeg", "bmp", "gif"};
		this.imagefiles.clear();
		for (File file : directory.listFiles()) {
			if (file.isDirectory()) {}
			else {
				String lowercase = file.getName().toLowerCase();
				for (String extension : extensions) {
					if (lowercase.endsWith('.'+extension)) {
						this.imagefiles.add(new ImageFile(file));
						break;
					}
				}
			}
		}
		Collections.sort(this.imagefiles);
	};
	private void OnOpenPic(Event event) {
		FileChooser file_chooser = new FileChooser();
		file_chooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
		File chosen_file = file_chooser.showOpenDialog(this.view.GetFrame());
		if (chosen_file == null) {}
		else {OpenedPicEvent.Call(new RichEvent(new ImageFile(chosen_file)));}
	};
	public final EventHandler<Event> OnPreviewClicked = (EventHandler<Event>) (Event event) -> {this.OnPreviewClicked(event);};
	public void OnPreviewClicked(Event event) {
		OpenedPicEvent.Call(new RichEvent(
			((ImageFileFrame)event.getSource()).GetImageFile()
		));
	}
	private void OnOpenedPic(Event event) {
		this.imagefile = ((RichEvent<ImageFile>)event).GetData();
	}
	public void Lang(String lang) {
		Lang.Load(lang);
	}
	public void OnSearch(Event event) {
		Screen screen = this.view.GetScreen("search");
		if (screen != null) {
			this.view.Summon("search", screen);
		}
	}
	public void OnSearchByTags(Event event) {
		String search = ((RichEvent<TextField>)event).GetData().getText();
		Pattern pattern = Pattern.compile("(?:(?:^|[ ]+)([a-z]+(?:[-_][a-z]+)*))*$");
		Matcher matcher = pattern.matcher(search);
		if (matcher.matches()) {
			pattern = Pattern.compile("[a-z]+(?:[-_][a-z]+)*");
			matcher = pattern.matcher(search);
			List<String> searchterms = new LinkedList();
			while (matcher.find()) {searchterms.add(matcher.group(0));}
			List<ImageFile> imagefiles = new LinkedList();
			for (String matchingfile : ImageTags.GetMatching(searchterms)) {
				imagefiles.add(new ImageFile(new File(matchingfile)));
			}
			((SearchScreen)this.view.GetScreen("search")).Setup(imagefiles);
		}
		else {
			this.view.SummonError("Must be space-separated tags.\nTags are one or more groups of lowercase latin letters, separated by one '-' or one '_'");
		}
	}
	public void OnTags(Event event) {
		if (this.imagefile == null) {return;}
		Screen screen = this.view.GetScreen("tags");
		if (screen != null) {
			screen.Setup(ImageTags.GetOrCreate(this.imagefile.GetName()));
			this.view.Summon("tags", screen);
		}
	}
	public void Turn(int quarters) {

	}

	private void OnTagCreation(Event event) {
		String newtag = ((RichEvent<TextField>)event).GetData().getText();
		Pattern pattern = Pattern.compile("^[a-z]+([-_][a-z]+)*$");
		Matcher matcher = pattern.matcher(newtag);
		if (matcher.matches()) {
			ImageTags imagetags = this.imagefile.GetTags();
			if (imagetags != null) {
				if (imagetags.Contains(newtag)) {
					event.consume();
					this.view.SummonError("Tag already exists.");
				}
				else {imagetags.Add(newtag);}
			}
			else {
				this.view.SummonError("An error occured while manipulating tags.");
			}
		}
		else {
			event.consume();
			this.view.SummonError("Must be one or more groups of lowercase latin letters, separated by one '-' or one '_'");
		}
	}

	private void OnTagRemoval(Event event) {
		this.imagefile.GetTags().Remove(
			((RichEvent<TagFrame>)event).GetData().GetTag()
		);
	}
}
