package com.rifat.javacode.parse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.rifat.javacode.model.ClassInfo;
import com.rifat.javacode.model.SelfExecutableMethod;

import japa.parser.ParseException;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;

public class SelfExecutableMethodParser {

	private SourceCodeParser sourceCodeParser;
	private DependencyResolver dependencyResolver;

	public SelfExecutableMethodParser() {
		sourceCodeParser = new SourceCodeParser();
		dependencyResolver = new DependencyResolver();
	}

	public List<SelfExecutableMethod> parseSelfExecutableMethod(String sourcePath) throws Exception {

		List<SelfExecutableMethod> selfExecutableMethods = new ArrayList<SelfExecutableMethod>();
		Browse(new File(sourcePath), selfExecutableMethods);
		return selfExecutableMethods;
	}

	private void Browse(File dir, List<SelfExecutableMethod> selfExecutableMethods) throws Exception {

		File[] files = dir.listFiles();
		List<File> dirs = new ArrayList<File>();
		for (File file : files) {
			if (file.isDirectory()) {
				dirs.add(file);
				continue;
			}

			if (!file.getName().endsWith(".java"))
				continue;

			selfExecutableMethods.addAll(parseSingleSourceFile(file.getPath()));
		}

		for (File directory : dirs) {
			Browse(directory, selfExecutableMethods);
		}
	}

	public List<SelfExecutableMethod> parseSingleSourceFile(String sourceFile)
			throws ParseException, IOException, InterruptedException {

		List<SelfExecutableMethod> selfExecutableMethods = new ArrayList<SelfExecutableMethod>();

		ClassInfo classInfo = sourceCodeParser.parse(new File(sourceFile));
		classInfo = dependencyResolver.preProcessClassInfo(classInfo);

		for (MethodDeclaration methodDeclaration : classInfo.methodDeclarations) {
			List<MethodDeclaration> dependentMethods;
			try {
				dependentMethods = dependencyResolver.resolveMethodCall(methodDeclaration, classInfo);

				List<FieldDeclaration> dependentFields = dependencyResolver.resolveFieldDependency(methodDeclaration,
						classInfo);
				List<ImportDeclaration> dependentLibraries = dependencyResolver
						.resolveLibraryDependency(methodDeclaration, classInfo);
				SelfExecutableMethod selfExecutableMethod = new SelfExecutableMethod(methodDeclaration,
						dependentMethods, dependentFields, dependentLibraries);
				selfExecutableMethods.add(selfExecutableMethod);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.err.println(e.getMessage());
				System.err.println(methodDeclaration.getName());
			}
		}

		return selfExecutableMethods;
	}

}
