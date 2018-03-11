package viewer.utilities;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lang {
	private static String language = null;
	private static Map<String, String> dictionary = null;

	private Lang() {}

	public static void Load(String lang) {
		Map<String, String> loaded = new HashMap<>();
		BufferedReader reader;
		String line;
		String key;
		String value;
		Pattern pattern = Pattern.compile("^[ \t]*(?:([A-Za-z0-9_-]*)(?:[ \t]*[=:][ \t]*|[ \t]+))?((?:\"[^\"]\"|[^#]*[^# \t])?)[ \t]*(#.*)?$");
		Matcher matcher;
		try {
			InputStreamReader r = new InputStreamReader(new FileInputStream("resources/lang/"+lang+".lang"), "UTF-8"); // ENCODING FIX
			reader = new BufferedReader(r);//new FileReader("resources/lang/"+lang+".lang"), "e");
			while ((line = reader.readLine()) != null) {
				if ((matcher = pattern.matcher(line)).matches()) {
					key = matcher.group(1);
					value = matcher.group(2);
					if ((key == null) || (value == null)) {} // No value on line (or incorrect pattern)
					else {loaded.put(key, value);}
				}
			}
			reader.close();

			Lang.language = lang;
			if (Lang.dictionary != null) {Lang.dictionary.clear();}
			Lang.dictionary = loaded;
		} catch (IOException e) {loaded.clear();}
	}
	public static String GetWord(String id) {
		if (Lang.dictionary == null) {return id;}
		String word = Lang.dictionary.get(id);
		return ((word == null) ? id : word);
	}
}