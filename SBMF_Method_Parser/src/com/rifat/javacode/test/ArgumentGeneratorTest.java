package com.rifat.javacode.test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.rifat.javacode.constants.Constants;
import com.rifat.javacode.execution.CodeExecutor;
import com.rifat.javacode.input.ArgumentGenerator;
import com.rifat.javacode.model.SelfExecutableMethod;
import com.rifat.javacode.parse.SelfExecutableMethodParser;

public class ArgumentGeneratorTest {

	ArgumentGenerator argumentGenerator;
	private CodeExecutor codeExecutor;
	private SelfExecutableMethodParser selfExecutableMethodParser;

	@Before
	public void setUp() throws Exception {
		argumentGenerator = new ArgumentGenerator();
		codeExecutor = new CodeExecutor();
		selfExecutableMethodParser = new SelfExecutableMethodParser();

	}

	@Test
	public void testGetArgumentString() throws Exception {
		String path = "C://Users//Rifat//Desktop//rif//test";// "I:/SF50/10_water-simulator/src/main/java/simulator/util";
		List<SelfExecutableMethod> selfExecutableMethods = selfExecutableMethodParser.parseSelfExecutableMethod(path);
		String argumentStr = argumentGenerator.getArgument(selfExecutableMethods.get(0).getMethod());
		assertEquals("10,10", argumentStr);
		codeExecutor.execute(selfExecutableMethods.get(0), Constants.SOURCE_CODE_STORAGE_DIR + "/test", argumentStr);
	}
	
	
	

}
