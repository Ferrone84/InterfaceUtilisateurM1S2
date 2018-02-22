package viewer.utilities;

import java.util.Map;

public class Lang {
	private static String language = null;
	private static Map<String, String> dictionary = null;

	private Lang() {}

	public static void Load(String lang) {
		// Parse here
	}
	public static String GetWord(String id) {
		if (Lang.dictionary == null) {return id;}
		String word = Lang.dictionary.get(id);
		return ((word == null) ? id : word);
	}
}