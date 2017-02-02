package com.rifat.javacode.parse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.rifat.javacode.model.ClassInfo;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;

public class SourceCodeParser {

	private final String ENCODING = "ISO8859_1";
	
	
	public ClassInfo parse(File sourceFile) throws ParseException, IOException {
		CompilationUnit compilationUnit = JavaParser.parse(sourceFile, ENCODING);
		return new CustomClassVisitor().getClassInfo(compilationUnit, null);
	}
}
