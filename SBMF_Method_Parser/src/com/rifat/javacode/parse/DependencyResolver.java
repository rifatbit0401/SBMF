package com.rifat.javacode.parse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.rifat.cmd.JavaCmdService;
import com.rifat.javacode.constants.Constants;
import com.rifat.javacode.model.ClassInfo;
import com.rifat.javacode.utility.FileUtility;

import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;

public class DependencyResolver {

	private JavaCmdService javaCmdService;
	private FileUtility fileUtility;

	public DependencyResolver() {
		javaCmdService = new JavaCmdService();
		fileUtility = new FileUtility();
	}
	
	public ClassInfo preProcessClassInfo(ClassInfo classInfo) throws IOException, InterruptedException{
		ClassInfo refinedClassInfo = new ClassInfo();
		refinedClassInfo.className=classInfo.className;
		refinedClassInfo.methodDeclarations=classInfo.methodDeclarations;
		
		for (ImportDeclaration importDeclaration : classInfo.importDeclarations) {
			ClassInfo temp = new ClassInfo();
			temp.className=refinedClassInfo.className;
			temp.importDeclarations.addAll(refinedClassInfo.importDeclarations);
			temp.importDeclarations.add(importDeclaration);
			
			if(javaCmdService.isCompilable(getSourceFile(temp)))
			{
				refinedClassInfo.importDeclarations.add(importDeclaration);
			}
		}
		
		for (FieldDeclaration fieldDeclaration : classInfo.fieldDeclarations) {
			ClassInfo temp = new ClassInfo();
			temp.className=refinedClassInfo.className;
			temp.importDeclarations=refinedClassInfo.importDeclarations;
			temp.fieldDeclarations.addAll(refinedClassInfo.fieldDeclarations);
			temp.fieldDeclarations.add(fieldDeclaration);
			
			if(javaCmdService.isCompilable(getSourceFile(temp)))
			{
				refinedClassInfo.fieldDeclarations.add(fieldDeclaration);
			}
		}
		
		return refinedClassInfo;
	}

	public List<MethodDeclaration> resolveMethodCall(MethodDeclaration methodDeclaration, ClassInfo classInfo)
			throws Exception {
		ClassInfo methodClass = new ClassInfo();
		methodClass.className = classInfo.className;
		methodClass.importDeclarations = classInfo.importDeclarations;
		methodClass.constructorDeclarations = classInfo.constructorDeclarations;
		methodClass.fieldDeclarations = classInfo.fieldDeclarations;
		methodClass.methodDeclarations.add(methodDeclaration);

		List<MethodDeclaration> invokedMethods = new ArrayList<MethodDeclaration>();
		File sourceFile = getSourceFile(methodClass);

		if (javaCmdService.isCompilable(sourceFile))
			return invokedMethods;

		for (MethodDeclaration md : classInfo.methodDeclarations) {
			
			if(md.equals(methodDeclaration))
				continue;
			
			methodClass.methodDeclarations.add(md);
			sourceFile = getSourceFile(methodClass);
			invokedMethods.add(md);
			if (javaCmdService.isCompilable(sourceFile))
				return invokedMethods;
		}
		throw new Exception("can't resolve method dependencies");
	}

	public List<FieldDeclaration> resolveFieldDependency(MethodDeclaration methodDeclaration, ClassInfo classInfo)
			throws Exception {

		List<FieldDeclaration> fields = new ArrayList<FieldDeclaration>();
		ClassInfo methodClass = new ClassInfo();
		methodClass.className = classInfo.className;
		methodClass.constructorDeclarations = classInfo.constructorDeclarations;
		methodClass.importDeclarations = classInfo.importDeclarations;
		methodClass.methodDeclarations.add(methodDeclaration);
		methodClass.methodDeclarations.addAll(resolveMethodCall(methodDeclaration, classInfo));

		if (javaCmdService.isCompilable(getSourceFile(methodClass))) {
			return fields;
		}
		for (FieldDeclaration fd : classInfo.fieldDeclarations) {
			methodClass.fieldDeclarations.add(fd);
			fields.add(fd);
			if (javaCmdService.isCompilable(getSourceFile(methodClass)))
				return fields;
		}

		throw new Exception("can't resolve field dependency");
	}

	public List<ImportDeclaration> resolveLibraryDependency(MethodDeclaration methodDeclaration, ClassInfo classInfo)
			throws Exception {
		List<ImportDeclaration> libraries = new ArrayList<ImportDeclaration>();
		ClassInfo methodClass = new ClassInfo();
		methodClass.className = classInfo.className;
		methodClass.constructorDeclarations = classInfo.constructorDeclarations;
		methodClass.methodDeclarations.add(methodDeclaration);
		methodClass.methodDeclarations.addAll(resolveMethodCall(methodDeclaration, classInfo));
		methodClass.fieldDeclarations = resolveFieldDependency(methodDeclaration, classInfo);

		if (javaCmdService.isCompilable(getSourceFile(methodClass))) {
			return libraries;
		}
		for (ImportDeclaration importDeclaration : classInfo.importDeclarations) {
			methodClass.importDeclarations.add(importDeclaration);
			libraries.add(importDeclaration);
			if (javaCmdService.isCompilable(getSourceFile(methodClass)))
				return libraries;
		}

		throw new Exception("can't resolve library dependency");
	}

	private File getSourceFile(ClassInfo methodClass) throws IOException {
		return fileUtility.createFile(methodClass.toJavaCode(),
				Constants.SOURCE_CODE_STORAGE_DIR + "/" + methodClass.className + "." + Constants.JAVA_EXTENSION);
	}
}
