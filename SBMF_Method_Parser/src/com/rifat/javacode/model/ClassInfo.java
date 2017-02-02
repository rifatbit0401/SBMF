package com.rifat.javacode.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.rifat.javacode.constants.Constants;
import com.rifat.javacode.parse.SourceCodeParser;
import com.rifat.javacode.utility.FileUtility;

import japa.parser.ParseException;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;

public class ClassInfo {

	public String className;
	public List<MethodDeclaration> methodDeclarations;
	public List<FieldDeclaration> fieldDeclarations;
	public List<ImportDeclaration> importDeclarations;
	public List<ConstructorDeclaration> constructorDeclarations;

	public ClassInfo() {
		this.methodDeclarations = new ArrayList<MethodDeclaration>();
		this.fieldDeclarations = new ArrayList<FieldDeclaration>();
		this.importDeclarations = new ArrayList<ImportDeclaration>();
		this.constructorDeclarations = new ArrayList<ConstructorDeclaration>();
	}

	public ClassInfo(String sourceCode) throws IOException, ParseException {
		FileUtility fileUtility = new FileUtility();
		String filePath = Constants.TEMP_DIR + "/" + Constants.TEMP_FILE + "." + Constants.JAVA_EXTENSION;
		fileUtility.createFile(sourceCode, filePath);
		SourceCodeParser codeParser = new SourceCodeParser();
		ClassInfo classInfo = codeParser.parse(new File(filePath));
		this.className = classInfo.className;
		this.constructorDeclarations = classInfo.constructorDeclarations;
		this.fieldDeclarations = classInfo.fieldDeclarations;
		this.importDeclarations = classInfo.importDeclarations;
		this.methodDeclarations = classInfo.methodDeclarations;
	}

	public String toJavaCode() {
		String javacode = "";
		javacode += toStr(importDeclarations);
		javacode += "public class " + className + "{";
		javacode += toStr(fieldDeclarations);
		javacode += toStr(constructorDeclarations);
		javacode += toStr(getUniqueMethodDeclarations());
		javacode += "}";
		return javacode;
	}

	private <E> String toStr(List<E> list) {
		String str = "";
		for (E item : list) {
			str += item.toString() + "\n";
		}
		return str;
	}
	
	private List<MethodDeclaration> getUniqueMethodDeclarations(){
		List<MethodDeclaration>uniqueMethods = new ArrayList<MethodDeclaration>();
		for (MethodDeclaration methodDeclaration : methodDeclarations) {
			boolean found=false;
			for (MethodDeclaration md : uniqueMethods) {
				if(md.toString().equals(methodDeclaration))
				{
					found = true;
					break;
				}
			}
			
			if(!found){
				uniqueMethods.add(methodDeclaration);
			}
		}
		return uniqueMethods;
	}
	
}
