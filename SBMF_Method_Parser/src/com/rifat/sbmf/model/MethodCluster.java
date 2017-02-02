package com.rifat.sbmf.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rifat.javacode.model.SelfExecutableMethod;
import com.rifat.javacode.utility.StringUtility;

import japa.parser.ast.body.MethodDeclaration;

public class MethodCluster {

	private List<SelfExecutableMethod> methods;
	private List<String> arguments;

	public MethodCluster(SelfExecutableMethod method, List<String> arguments) {
		methods = new ArrayList<SelfExecutableMethod>();
		methods.add(method);
		this.arguments = arguments;
	}

	public void addMethod(SelfExecutableMethod selfExecutableMethod) {
		methods.add(selfExecutableMethod);
	}

	public List<SelfExecutableMethod> getMethods() {
		return methods;
	}

	public List<String> getArguments() {
		return arguments;
	}

}
