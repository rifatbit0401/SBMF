package com.rifat.javacode.utility;

import java.util.ArrayList;
import java.util.List;

public class StringUtility {

	public String toPhraseString(String camelCaseStr) {

		String phraseStr = "";
		String word = "";
		for (int i = 0; i < camelCaseStr.length(); i++) {
			char ch = camelCaseStr.charAt(i);
			if (Character.isUpperCase(ch)) {
				phraseStr += word + " ";
				word = "" + Character.toLowerCase(ch);
				continue;
			}
			word += camelCaseStr.charAt(i);
		}
		phraseStr += word;
		return phraseStr;
	}
}
