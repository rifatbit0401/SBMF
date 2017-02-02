package com.rifat.javacode.utility;

import java.util.List;

public class ListOperator {

	public String toString(List<String> list, char itemSeperator) {

		String str = "";

		for (String item : list) {
			if (list.indexOf(item) != 0)
				str += itemSeperator;
			str += item;
		}
		return str;
	}

}
