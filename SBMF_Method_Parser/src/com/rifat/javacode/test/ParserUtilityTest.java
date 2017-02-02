package com.rifat.javacode.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.rifat.javacode.model.SelfExecutableMethod;
import com.rifat.javacode.parse.ParserUtility;
import com.rifat.javacode.parse.SelfExecutableMethodParser;

public class ParserUtilityTest {

	private ParserUtility parserUtility;
	private SelfExecutableMethodParser selfExecutableMethodParser;

	@Before
	public void setUp() throws Exception {
		parserUtility = new ParserUtility();
		selfExecutableMethodParser = new SelfExecutableMethodParser();

	}

	@Test
	public void testGetSignature() throws Exception {
		String path = "C://Users//Rifat//Desktop//rif//test";
		List<SelfExecutableMethod> selfExecutableMethods = selfExecutableMethodParser.parseSelfExecutableMethod(path);
		assertEquals("int add(int,int)", parserUtility.getSignature(selfExecutableMethods.get(0).getMethod()));
	}

}
