package com.rifat.javacode.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.rifat.javacode.execution.CodeGenerator;

public class CodeGeneratorTest {

	CodeGenerator codeGenerator;

	@Before
	public void setUp() throws Exception {
		codeGenerator = new CodeGenerator();
	}

	@Test
	public void testGenerateMainClassCode() {
		String expected="public class Main{public }";
		String actual=codeGenerator.getMainClassCode("add", "Test", "1,2");
		assertEquals(expected,actual);
	}

}
