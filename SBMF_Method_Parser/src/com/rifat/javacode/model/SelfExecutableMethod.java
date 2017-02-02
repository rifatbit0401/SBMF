package com.rifat.javacode.model;

import java.util.List;

import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;

public class SelfExecutableMethod {

	private MethodDeclaration method;
	private List<MethodDeclaration> dependentMethods;
	private List<FieldDeclaration> dependentFields;
	private List<ImportDeclaration> dependentLibraries;

	public SelfExecutableMethod(MethodDeclaration method, List<MethodDeclaration> dependentMethods,
			List<FieldDeclaration> dependentFields, List<ImportDeclaration> dependentLibraries) {
		this.method = method;
		this.dependentMethods = dependentMethods;
		this.dependentFields = dependentFields;
		this.dependentLibraries = dependentLibraries;
	}

	public MethodDeclaration getMethod() {
		return method;
	}

	public List<MethodDeclaration> getDependentMethods() {
		return dependentMethods;
	}

	public List<FieldDeclaration> getDependentFields() {
		return dependentFields;
	}

	public List<ImportDeclaration> getDependentLibraries() {
		return dependentLibraries;
	}

	public ClassInfo toClassInfo(String className) {
		ClassInfo classInfo = new ClassInfo();
		classInfo.className = className;
		/*classInfo.fieldDeclarations = this.dependentFields;
		classInfo.importDeclarations = this.dependentLibraries;
		classInfo.methodDeclarations = this.dependentMethods;
		
		classInfo.methodDeclarations.add(method);
		*/
		classInfo.fieldDeclarations.addAll(this.dependentFields);
		classInfo.importDeclarations.addAll(this.dependentLibraries);
		classInfo.methodDeclarations.addAll(this.dependentMethods);
		classInfo.methodDeclarations.add(method);
		return classInfo;
	}
	

}
