package com.rifat.javacode.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.rifat.javacode.model.SelfExecutableMethod;
import com.rifat.javacode.parse.SelfExecutableMethodParser;

public class SelfExecutableMethodParserTest {

	private SelfExecutableMethodParser selfExecutableMethodParser;

	@Before
	public void setUp() throws Exception {
		selfExecutableMethodParser = new SelfExecutableMethodParser();
	}

	@Test
	public void testParseSelfExecutableMethod() throws Exception {
		// String path="F:/Ananda DU/android
		// projects/org.eclipse.egit.core.test/src";
		String path = "I:/SF50/10_water-simulator/src/main/java/simulator/util";//"I:\\SF50\\10_water-simulator\\src\\main\\java\\simulator\\util";
		List<SelfExecutableMethod> selfExecutableMethods = selfExecutableMethodParser.parseSelfExecutableMethod(path);
		assertEquals(10, selfExecutableMethods.size());
	}

}
