package com.rifat.cmd;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CmdRunner {

	public List<String> runCommand(List<String> commands, String cmdStartDir) throws IOException, InterruptedException {

		ProcessBuilder processBuilder = new ProcessBuilder(commands);
		processBuilder.redirectErrorStream(true);
		processBuilder.directory(new File(cmdStartDir));
		Process process = processBuilder.start();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		List<String> output = new ArrayList<String>();
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			output.add(line);
		}
		process.waitFor();
		bufferedReader.close();
		return output;
	}

}
