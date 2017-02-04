package com.rifat.javacode.utility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.rifat.javacode.constants.Constants;
import com.rifat.javacode.model.SelfExecutableMethod;

public class FileUtility {

	public File createFile(String data, String filePath) throws IOException {
		File file = new File(filePath);
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(data);
		fileWriter.close();
		return file;
	}

	/**
	 * delete all files in the specified directory
	 */
	public void clearDirectory(String directoryPath) {
		File file = new File(directoryPath);
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				f.delete();
			}
		}
	}

	public void deleteFile(String filePath) {
		File file = new File(filePath);
		file.delete();
	}

	public List<File> getAllFiles(String dir, String extension) {
		List<File> files = new ArrayList<>();
		Browse(new File(dir), files, extension);
		return files;
	}

	private void Browse(File dir, List<File> foundFiles, String extension) {

		File[] files = dir.listFiles();
		List<File> dirs = new ArrayList<File>();
		for (File file : files) {
			if (file.isDirectory()) {
				dirs.add(file);
				continue;
			}

			if (!file.getName().endsWith(extension))
				continue;
			foundFiles.add(file);
		}

		for (File directory : dirs) {
			Browse(directory, foundFiles, extension);
		}
	}

}
