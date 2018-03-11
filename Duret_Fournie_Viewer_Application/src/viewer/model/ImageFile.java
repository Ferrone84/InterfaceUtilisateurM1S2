package viewer.model;

import java.io.File;
import java.io.IOException;
import javafx.scene.image.Image;

public class ImageFile extends Image implements Comparable<ImageFile> {
	public static int Compare(ImageFile a, ImageFile b) {
		if ((a == null) || (b == null)) {
			return ((a == b) ? 0 : (a != null) ? 1 : -1);
		}
		if ((a.GetFile() == null) || (b.GetFile() == null)) {
			return ((a.GetFile() == b.GetFile()) ? 0 : (a.GetFile() != null) ? 1 : -1);
		}
		if ((a.GetFile().getAbsolutePath() == null) || (b.GetFile().getAbsolutePath() == null)) {
			return ((a.GetFile().getAbsolutePath() == b.GetFile().getAbsolutePath()) ? 0 : (a.GetFile().getAbsolutePath() != null) ? 1 : -1);
		}
		return a.GetFile().getAbsolutePath().compareTo(b.GetFile().getAbsolutePath());
	}
	private final File file;
	public ImageFile(File file) {
		super("file:" + file.getAbsolutePath());
		this.file = file;
	}
	public File GetFile() {
		return this.file;
	}
	public String GetName() {
		return this.file.getName();
	}
	public String GetFullName() {
		return this.file.getAbsolutePath();
	}
	
	public ImageTags GetTags() {
		try {
			return ImageTags.GetOrCreate(this.file.getCanonicalPath());
		}
		catch (IOException e) {return null;}
	}
	
	public int compareTo(ImageFile other) {
		return ImageFile.Compare(this, other);
	}
	public boolean equals(ImageFile other) {
		return (ImageFile.Compare(this, other) == 0);
	}
}
