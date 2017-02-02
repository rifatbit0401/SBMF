package com.rifat.sbmf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rifat.javacode.constants.Constants;
import com.rifat.javacode.execution.CodeExecutor;
import com.rifat.javacode.executor.model.Method;
import com.rifat.javacode.input.ArgumentGenerator;
import com.rifat.javacode.model.SelfExecutableMethod;
import com.rifat.javacode.parse.ParserUtility;
import com.rifat.javacode.parse.SelfExecutableMethodParser;
import com.rifat.javacode.parse.SourceCodeParser;
import com.rifat.javacode.utility.FileUtility;
import com.rifat.sbmf.configurations.SBMFConfiguration;
import com.rifat.sbmf.model.MethodCluster;

import japa.parser.ParseException;

public class Controller {

	private SelfExecutableMethodParser selfExecutableMethodParser;
	private FileUtility fileUtility;
	private SimilarityChecker similarityChecker;
	private TermSelector termSelector;
	
	public Controller() {
		selfExecutableMethodParser = new SelfExecutableMethodParser();
		fileUtility = new FileUtility();
		similarityChecker = new SimilarityChecker();
		termSelector = new TermSelector();
	}

	public void start(String codebaseDir) throws ParseException, IOException, InterruptedException {
		List<File> files = fileUtility.getAllFiles(codebaseDir, Constants.JAVA_EXTENSION);

		List<SelfExecutableMethod> selfExecutableMethodCollection = new ArrayList<SelfExecutableMethod>();
		for (File file : files) {
			System.out.println("parsing " + file.getPath());
			List<SelfExecutableMethod> selfExecutableMethods = selfExecutableMethodParser
					.parseSingleSourceFile(file.getPath());
			for (SelfExecutableMethod selfExecutableMethod : selfExecutableMethods) {
				System.out.println("===>" + selfExecutableMethod.getMethod().getName());
			}
			selfExecutableMethodCollection.addAll(selfExecutableMethods);
		}

		System.out.println("Clustering methods");
		List<MethodCluster> clusters = similarityChecker.clusterMethods(selfExecutableMethodCollection);
		System.out.println("Printing Clusters.............");
		for (MethodCluster methodCluster : clusters) {
			System.out.println("Cluster #" + clusters.indexOf(methodCluster));
			for (SelfExecutableMethod method : methodCluster.getMethods()) {
				System.out.println("==>" + method.getMethod().getName() + " " + method.getMethod().getType());
			}
		}
		
		System.out.println("Selecting proper terms");
		for (MethodCluster methodCluster : clusters) {
			List<String>terms = termSelector.getProperTerms(methodCluster);
			System.out.println("Cluster #" + clusters.indexOf(methodCluster));
			System.out.println(terms.toString());
		}
		

	}

	

}
