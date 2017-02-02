package com.rifat.cmd.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.rifat.cmd.JavaCmdService;

public class JavaCmdServiceTest {

	private JavaCmdService javaCmdService;

	@Before
	public void setUp() throws Exception {
		javaCmdService = new JavaCmdService();
	}

	@Test
	public void testIsCompilable() throws IOException, InterruptedException {
		String sourceFilePath = "C:/Users/Rifat/Desktop/sbmf/Test.java";
		File sourceJavaFile = new File(sourceFilePath);
		boolean actual = javaCmdService.isCompilable(sourceJavaFile);
		assertEquals(true, actual);
	}

}
