package com.rifat.javacode.execution;

import com.rifat.javacode.model.SelfExecutableMethod;

import japa.parser.ast.body.MethodDeclaration;

public class CodeGenerator {

	private final static String MAIN_CLASS_CODE_TEMPLATE = "public class Main{\n public static void main(String[] args){\n System.out.println(new <CLASS_NAME>().<METHOD_NAME>(<ARGUMENT_STRING>));}}";

	public String getMainClassCode(String methodName, String methodClassName, String argString) {

		String mainClassCode = MAIN_CLASS_CODE_TEMPLATE;
		mainClassCode = mainClassCode.replaceAll(Keys.CLASS_NAME, methodClassName);
		mainClassCode = mainClassCode.replaceAll(Keys.METHOD_NAME, methodName);
		mainClassCode = mainClassCode.replaceAll(Keys.ARGUMENT_STRING, argString);
		return mainClassCode;
	}

	class Keys {
		static final String CLASS_NAME = "<CLASS_NAME>";
		static final String METHOD_NAME = "<METHOD_NAME>";
		static final String ARGUMENT_STRING = "<ARGUMENT_STRING>";
	}
}
