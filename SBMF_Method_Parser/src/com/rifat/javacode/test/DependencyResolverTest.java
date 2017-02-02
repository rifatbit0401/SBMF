package com.rifat.javacode.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.rifat.javacode.constants.Constants;
import com.rifat.javacode.model.ClassInfo;
import com.rifat.javacode.parse.DependencyResolver;
import com.rifat.javacode.parse.SourceCodeParser;

import japa.parser.ParseException;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;

public class DependencyResolverTest {

	private DependencyResolver dependencyResolver;
	private SourceCodeParser sourceCodeParser;

	@Before
	public void setUp() throws Exception {
		dependencyResolver = new DependencyResolver();
		sourceCodeParser = new SourceCodeParser();
	}

	@Test
	public void testPreProcessClassInfo() throws ParseException, IOException, InterruptedException{
		String sourceFilePath = "I:/SF50/10_water-simulator/src/main/java/simulator/util/ConsumerAttributes.java";
		ClassInfo classInfo = sourceCodeParser.parse(new File(sourceFilePath));
		classInfo = dependencyResolver.preProcessClassInfo(classInfo);
		assertEquals("ConsumerAttributes", classInfo.className);
	}
	
	@Test
	public void testResolveMethodCall1() throws Exception {
		String sourceFilePath = "C:/Users/Rifat/Desktop/sbmf/Test.java";
		ClassInfo classInfo = sourceCodeParser.parse(new File(sourceFilePath));
		classInfo.className = Constants.CLASS_NAME;
		List<MethodDeclaration> invokedMethods = dependencyResolver
				.resolveMethodCall(classInfo.methodDeclarations.get(0), classInfo);

		assertEquals(0, invokedMethods.size());
	}

	@Test
	public void testResolveMethodCall2() throws Exception {
		String sourceFilePath = "C:/Users/Rifat/Desktop/sbmf/Test1.java";
		ClassInfo classInfo = sourceCodeParser.parse(new File(sourceFilePath));
		classInfo.className = Constants.CLASS_NAME;
		List<MethodDeclaration> invokedMethods = dependencyResolver
				.resolveMethodCall(classInfo.methodDeclarations.get(1), classInfo);

		assertEquals(1, invokedMethods.size());
	}

	@Test
	public void testResolveMethodCall3() throws Exception {
		String sourceFilePath = "I:/SF50/10_water-simulator/src/main/java/simulator/util/ConsumerType.java";
		ClassInfo classInfo = sourceCodeParser.parse(new File(sourceFilePath));
		//classInfo.className = Constants.CLASS_NAME;
		List<MethodDeclaration> invokedMethods = dependencyResolver
				.resolveMethodCall(classInfo.methodDeclarations.get(1), classInfo);

		assertEquals(0, invokedMethods.size());
	}
	
	@Test
	public void testResolveFieldDependency1() throws Exception {
		String sourceFilePath = "C:/Users/Rifat/Desktop/sbmf/Test1.java";
		ClassInfo classInfo = sourceCodeParser.parse(new File(sourceFilePath));
		classInfo.className = Constants.CLASS_NAME;
		List<FieldDeclaration> dependentFields = dependencyResolver
				.resolveFieldDependency(classInfo.methodDeclarations.get(1), classInfo);

		assertEquals(1, dependentFields.size());
	}

	@Test
	public void testResolveLibraryDependency1() throws Exception {
		String sourceFilePath = "C:/Users/Rifat/Desktop/sbmf/Test1.java";
		ClassInfo classInfo = sourceCodeParser.parse(new File(sourceFilePath));
		classInfo.className = Constants.CLASS_NAME;
		List<ImportDeclaration> dependentLibraries = dependencyResolver
				.resolveLibraryDependency(classInfo.methodDeclarations.get(1), classInfo);

		assertEquals(0, dependentLibraries.size());
	}
}
