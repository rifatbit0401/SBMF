package com.rifat.javacode.parse;

import java.util.List;

import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;

public class ParserUtility {

	public String getSignature(MethodDeclaration methodDeclaration) {
		String signature = "";
		signature += methodDeclaration.getType().toString() + " " + methodDeclaration.getName() + "(";
		signature = getParameterString(methodDeclaration);
		signature += ")";
		return signature;
	}

	/**
	 * Return only the type of the parameters
	 * for example: int,int
	 */
	public String getParameterString(MethodDeclaration methodDeclaration) {
		String parameterStr="";
		boolean isFirst = true;
		List<Parameter> parameters = methodDeclaration.getParameters();
		for (Parameter parameter : parameters) {
			if (parameters.indexOf(parameter) != 0)
				parameterStr += ",";
			parameterStr += parameter.getType().toString();
		}
		return parameterStr;
	}
	
	
}
