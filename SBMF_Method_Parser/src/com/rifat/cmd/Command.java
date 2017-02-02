package com.rifat.cmd;

import java.util.ArrayList;
import java.util.List;

import com.rifat.javacode.constants.Constants;

public class Command {

	public String getCompileJavaCodeCommand(String rootPath, String packageName, String fileName) {

		if (!fileName.trim().endsWith(Configuration.JAVA_EXTENSION))
			fileName += Configuration.JAVA_EXTENSION;
		String compileCommand = Configuration.COMPILE_COMMAND + " ";
		if (packageName != null)
			compileCommand += packageName + "\\";
		compileCommand += fileName;
		return compileCommand;
	}

	public String getRunCodeCommand(String packageName, String mainClassName) {
		String runCommand = Configuration.RUN_COMMAND + " ";
		if (packageName != null)
			runCommand += " " + packageName + ".";
		runCommand += mainClassName;
		return runCommand;
	}

	public List<String> getOpenCommandPromptCommand() {
		List<String> commands = new ArrayList<String>();
		commands.add(Configuration.OPEN_CMD_COMMAND_1);
		commands.add(Configuration.OPEN_CMD_COMMAND_2);
		return commands;
	}

}
