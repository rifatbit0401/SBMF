package com.rifat.javacode.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.rifat.javacode.utility.StringUtility;

public class StringUtilityTest {

	private StringUtility stringUtility;
	@Before
	public void setUp() throws Exception {
		stringUtility = new StringUtility();
	}

	@Test
	public void testToPhraseString() {
		assertEquals("test string concate", stringUtility.toPhraseString("testStringConcate"));
		assertEquals("test string1 concate", stringUtility.toPhraseString("testString1Concate"));
		assertEquals("test str123ing concate", stringUtility.toPhraseString("testStr123ingConcate"));
		
	}

}
