package com.rifat.cmd;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JavaCmdService {

	private CmdRunner cmdRunner;
	private Command command;

	public JavaCmdService() {
		cmdRunner = new CmdRunner();
		command = new Command();
	}

	public boolean isCompilable(File javaSourceFile) throws IOException, InterruptedException {
		String compileCommand = command.getCompileJavaCodeCommand(javaSourceFile.getAbsolutePath(), null,
				javaSourceFile.getName());
		List<String> commands = new ArrayList<String>();
		commands.addAll(command.getOpenCommandPromptCommand());
		commands.add(compileCommand);
		List<String> output = cmdRunner.runCommand(commands, javaSourceFile.getParent());
		if (output.isEmpty())
			return true;
		else {		
			return false;
		}
	}

}
