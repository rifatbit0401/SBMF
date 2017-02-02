package com.rifat.javacode.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.rifat.javacode.model.ClassInfo;
import com.rifat.javacode.parse.CustomClassVisitor;
import com.rifat.javacode.parse.SourceCodeParser;

import japa.parser.ParseException;

public class SourceCodeParserTest {

	private SourceCodeParser sourceCodeParser;

	@Before
	public void setUp() throws Exception {
		sourceCodeParser = new SourceCodeParser();
	}

	@Test
	public void testParseCode() throws ParseException, IOException {
		String sourceFilePath = "C:/Users/Rifat/Desktop/sbmf/sbmf/Main.java";
		ClassInfo classInfo = sourceCodeParser.parse(new File(sourceFilePath));
		assertEquals("initialize", classInfo.methodDeclarations.get(0).getName().toString());
		String expectedCode = "public class "+classInfo.className+"{private String num1, num2;\n" + "private void initialize() {\n"
				+ "    this.num1 = \"123\";\n" + "    this.num2 = \"456\";\n" + "}\n"
				+ "public void main(String[] args) {\n" + "    String output;\n" + "    Test test = new Test();\n"
				+ "    initialize();\n" + "    output = test.concate(num1, num2);\n"
				+ "    System.out.println(output);\n}\n}";

		assertEquals(expectedCode, classInfo.toJavaCode());
	}
	

}
