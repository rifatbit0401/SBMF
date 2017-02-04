package com.rifat.javacode.input;

import java.security.Policy.Parameters;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.type.Type;

public class ArgumentGenerator {

	private Random random;

	public ArgumentGenerator() {
		random = new Random();
	}

	public List<String> getArguments(MethodDeclaration methodDeclaration, int number) {

		List<String> arguments = new ArrayList<String>();
		for (int i = 0; i < number; i++) {
			arguments.add(getArgument(methodDeclaration));
		}
		return arguments;
	}

	public String getArgument(MethodDeclaration methodDeclaration) {
		String argStr = "";
		boolean isFirst = true;

		for (Parameter parameter : methodDeclaration.getParameters()) {

			if (isFirst == false)
				argStr += ",";
			isFirst = false;

			String type = parameter.getType().toString();
			if (type.equals(Keywords.INTEGER)) {
				argStr += getDataForInt();
			} else if (type.equals(Keywords.FLOAT)) {
				argStr += getDataForFloat();
			} else if (type.equals(Keywords.DOUBLE)) {
				argStr += getDataForDouble();
			} else if (type.equals(Keywords.STRING)) {
				argStr += getDataForString();
			} else if (type.equals(Keywords.CHAR)) {
				argStr += "'" + getDataForChar() + "'";
			} else {
				argStr += "null";
			}

		}

		return argStr;
	}

	private int getDataForInt() {
		return random.nextInt(Integer.MAX_VALUE);
	}

	private float getDataForFloat() {
		return Float.parseFloat(Integer.toString(random.nextInt(Integer.MAX_VALUE)));
	}

	private double getDataForDouble() {
		return Double.parseDouble(Integer.toString(random.nextInt(Integer.MAX_VALUE)));
	}

	private String getDataForString() {
		return "str1";
	}

	private char getDataForChar() {
		return 'C';
	}

	public class Keywords {
		public static final String INTEGER = "int";
		public static final String FLOAT = "float";
		public static final String DOUBLE = "double";
		public static final String STRING = "String";
		public static final String LIST = "List";
		public static final String CHAR = "char";
	}
}
