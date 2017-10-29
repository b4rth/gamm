package fr.bart.gamm.util;

import java.text.Normalizer;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {

	public static String Normalize(String string) {
		if(string != null) {
			return StringUtils.stripAccents(Normalizer.normalize(string, Normalizer.Form.NFD).trim());
		} else {
			return "";
		}
	}
	
}
