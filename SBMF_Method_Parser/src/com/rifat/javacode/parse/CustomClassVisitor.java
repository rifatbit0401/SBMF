package com.rifat.javacode.parse;

import com.rifat.javacode.model.ClassInfo;

import japa.parser.ast.CompilationUnit;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.expr.ClassExpr;
import japa.parser.ast.visitor.VoidVisitorAdapter;

 public class CustomClassVisitor extends VoidVisitorAdapter {

	private ClassInfo classInfo;

	public CustomClassVisitor() {
		super();
		classInfo = new ClassInfo();
	}

	public ClassInfo getClassInfo(CompilationUnit compilationUnit, Object obj) {
		visit(compilationUnit, obj);
		return classInfo;
	}

	@Override
	public void visit(ClassOrInterfaceDeclaration n, Object arg) {
		classInfo.className = n.getName();
		super.visit(n, arg);
	}

	@Override
	public void visit(ConstructorDeclaration n, Object arg) {
		classInfo.constructorDeclarations.add(n);
		super.visit(n, arg);
	}

	@Override
	public void visit(FieldDeclaration n, Object arg) {
		classInfo.fieldDeclarations.add(n);
		super.visit(n, arg);
	}

	@Override
	public void visit(ImportDeclaration n, Object arg) {
		classInfo.importDeclarations.add(n);
		super.visit(n, arg);
	}

	@Override
	public void visit(MethodDeclaration n, Object arg) {
		classInfo.methodDeclarations.add(n);
		super.visit(n, arg);
	}

}
