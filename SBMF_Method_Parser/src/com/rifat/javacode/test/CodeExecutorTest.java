package com.rifat.javacode.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.rifat.javacode.constants.Constants;
import com.rifat.javacode.execution.CodeExecutor;
import com.rifat.javacode.input.ArgumentGenerator;
import com.rifat.javacode.model.SelfExecutableMethod;
import com.rifat.javacode.parse.SelfExecutableMethodParser;

import japa.parser.ParseException;

public class CodeExecutorTest {

	private CodeExecutor codeExecutor;
	private SelfExecutableMethodParser selfExecutableMethodParser;
	
	@Before
	public void setUp() throws Exception {
		codeExecutor = new CodeExecutor();
		selfExecutableMethodParser = new SelfExecutableMethodParser();
	}

	@Test
	public void testExecute() throws Exception {
		String path = "C://Users//Rifat//Desktop//rif//test";// "I:/SF50/10_water-simulator/src/main/java/simulator/util";
		List<SelfExecutableMethod> selfExecutableMethods = selfExecutableMethodParser.parseSelfExecutableMethod(path);
		String actual = codeExecutor.execute(selfExecutableMethods.get(0), Constants.SOURCE_CODE_STORAGE_DIR + "/new",
				"10,20");
		assertEquals("30", actual);
	}

}
