package com.rifat.javacode.execution;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.rifat.cmd.CmdRunner;
import com.rifat.cmd.Command;
import com.rifat.javacode.constants.Constants;
import com.rifat.javacode.model.ClassInfo;
import com.rifat.javacode.model.SelfExecutableMethod;
import com.rifat.javacode.utility.FileUtility;
import com.rifat.javacode.utility.ListOperator;

import japa.parser.ParseException;

public class CodeExecutor {

	private String rootDirOfProject;
	private Command command;
	private CmdRunner cmdRunner;
	private CodeGenerator codeGenerator;
	private ListOperator listOperator;

	public CodeExecutor() {
		command = new Command();
		cmdRunner = new CmdRunner();
		codeGenerator = new CodeGenerator();
		listOperator = new ListOperator();
	}

	public String execute(SelfExecutableMethod method, String rootdirOfProject, String argumentString)
			throws IOException, ParseException, InterruptedException {
		this.rootDirOfProject = rootdirOfProject;
		ClassInfo methodClassInfo = method.toClassInfo(Constants.CLASS_NAME);
		String mainClassCode = codeGenerator.getMainClassCode(method.getMethod().getName(), methodClassInfo.className,
				argumentString);
		ClassInfo mainClassInfo = new ClassInfo(mainClassCode);
		createProject(mainClassInfo, methodClassInfo);
		compileProject(mainClassInfo);
		return runProject(mainClassInfo);
	}

	private void createProject(ClassInfo mainClass, ClassInfo methodClass) throws IOException {
		String mainClassCodeFullPath = getFullPath(mainClass);
		String methodClassCodeFullPath = getFullPath(methodClass);
		writeSourceCodeToFile(mainClassCodeFullPath, mainClass.toJavaCode());
		writeSourceCodeToFile(methodClassCodeFullPath, methodClass.toJavaCode());
	}

	public String runProject(ClassInfo mainClassInfo) throws IOException, InterruptedException {
		List<String> runCommands = new ArrayList<String>();
		runCommands.addAll(command.getOpenCommandPromptCommand());
		runCommands.add(command.getRunCodeCommand(null, mainClassInfo.className));
		return listOperator.toString(cmdRunner.runCommand(runCommands, rootDirOfProject), '\n');
	}

	private void compileProject(ClassInfo mainClass) throws IOException, InterruptedException {
		List<String> compileCommands = new ArrayList<String>();
		compileCommands.addAll(command.getOpenCommandPromptCommand());
		compileCommands.add(command.getCompileJavaCodeCommand(rootDirOfProject, null, mainClass.className));
		cmdRunner.runCommand(compileCommands, rootDirOfProject);
	}

	private void writeSourceCodeToFile(String sourceFileFullPath, String sourceCode) throws IOException {
		File file = new File(sourceFileFullPath);
		if (!file.getParentFile().exists())
			file.getParentFile().mkdirs();
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(sourceFileFullPath)));
		bufferedWriter.write(sourceCode);
		bufferedWriter.close();
	}

	private String getFullPath(ClassInfo classInfo) {
		return rootDirOfProject + "\\" + classInfo.className + "." + Constants.JAVA_EXTENSION;
	}
}
