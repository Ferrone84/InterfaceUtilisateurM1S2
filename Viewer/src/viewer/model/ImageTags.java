package viewer.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageTags {
	private final static Map<String, ImageTags> instances = new HashMap<>();
	public static ImageTags Get(String filename) {
		return ImageTags.instances.get(filename);
	}
	public static ImageTags GetOrCreate(String filename) {
		ImageTags imagetags = ImageTags.Get(filename);
		return ((imagetags == null) ? new ImageTags(filename) : imagetags);
	}
	public static void Trim() {
		List<String> to_remove = new LinkedList<>();
		for (Entry<String, ImageTags> entry : ImageTags.instances.entrySet()) {
			if (entry.getValue().IsEmpty()) {
				to_remove.add(entry.getKey());
			}
		}
		for (String filename : to_remove) {
			ImageTags.Delete(filename);
		}
	}
	
	public static void Trim(String filename) {
		ImageTags imagetags = ImageTags.Get(filename);
		if ((imagetags != null) && (imagetags.IsEmpty())) {
			ImageTags.Delete(filename);
		}
	}
	public static void Delete(String filename) {
		ImageTags.instances.remove(filename);
	}
	
	public static void LoadFromFile(String filename) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line;
		String group_filename;
		String group_tags;
		ImageTags imagetags;


		String regex_tag = "[a-z]+(?:[-_][a-z]+)*";
		String regex_tags = regex_tag + "(?:," + regex_tag + ")*";
		Pattern linepattern = Pattern.compile("^(.+): (" + regex_tags + ")$");
		Pattern tasgpattern = Pattern.compile("^" + regex_tags + "$");
		Matcher matcher;
		while ((line = br.readLine()) != null) {
			matcher = linepattern.matcher(line);
			if (matcher.matches()) {
				group_filename = matcher.group(1);
				group_tags = matcher.group(2);
				String[] tags_list = group_tags.split(",");
				imagetags = new ImageTags(group_filename);
				imagetags.SetTags(new ArrayList<String>(tags_list.length + 1));
				for (String tag : tags_list) {imagetags.Add(tag);}
			}
		}
	}
	public static void SaveToFile(String filename) {
		ImageTags.Trim();
		try {
			PrintWriter writer = new PrintWriter(filename, "UTF-8");
			for (Entry<String, ImageTags> entry : ImageTags.instances.entrySet()) {
				writer.print(entry.getKey());
				writer.print(": ");
				writer.println(entry.getValue().ToString());
			}
			writer.close();
		}
		catch (IOException e) {
			
		}
	}

	public static List<String> GetMatching(List<String> searchterms) {
		List<String> results = new LinkedList();
		for (Entry<String, ImageTags> entry : ImageTags.instances.entrySet()) {
			if (entry.getValue().Matches(searchterms)) {results.add(entry.getKey());}
		}
		return results;
	}
	
	private List<String> tags;
	public ImageTags(String filename) {
		this.tags = new ArrayList<>();
		ImageTags.instances.put(filename, this);
	}
	public List<String> GetTags() {
		return this.tags;
	}
	public void SetTags(List<String> value) {
		this.tags = value;
	}
	public void Add(String value) {
		if (value == null) {return;}
		if (value.equals("")) {return;}
		if (this.tags.contains(value)) {return;}
		this.tags.add(value);
	}
	public void Remove(String value) {
		if (value == null) {return;}
		if (value.equals("")) {return;}
		if (!this.tags.contains(value)) {return;}
		this.tags.remove(value);
	}

	private boolean IsEmpty() {
		return this.tags.isEmpty();
	}

	public boolean Contains(String other) {
		for (String tag : this.tags) {
			if (tag.equals(other)) {return true;}
		}
		return false;
	}
	
	public boolean MatchesExactly(String tag) {
		return this.Contains(tag);
	}
	public boolean Matches(String search) {
		for (String tag : this.tags) {
			if (tag.contains(search)) {return true;}
		}
		return false;
	}
	public boolean Matches(List<String> searchterms) {
		if (this.tags.isEmpty()) {return false;}
		for (String term : searchterms) {
			if (!this.Matches(term)) {return false;}
		}
		return true;
	}
	
	public String toString() {return this.ToString();}
	public String ToString() {
		return String.join(",", this.tags);
	}
}
