package com.rifat.sbmf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rifat.javacode.constants.Constants;
import com.rifat.javacode.execution.CodeExecutor;
import com.rifat.javacode.input.ArgumentGenerator;
import com.rifat.javacode.model.SelfExecutableMethod;
import com.rifat.javacode.parse.ParserUtility;
import com.rifat.javacode.parse.SelfExecutableMethodParser;
import com.rifat.javacode.utility.FileUtility;
import com.rifat.sbmf.configurations.SBMFConfiguration;
import com.rifat.sbmf.model.MethodCluster;

import japa.parser.ParseException;

public class SimilarityChecker {

	private CodeExecutor codeExecutor;
	private ArgumentGenerator argumentGenerator;
	private ParserUtility parserUtility;

	public SimilarityChecker() {
		codeExecutor = new CodeExecutor();
		argumentGenerator = new ArgumentGenerator();
		parserUtility = new ParserUtility();
	}

	public List<MethodCluster> clusterMethods(List<SelfExecutableMethod> selfExecutableMethods)
			throws IOException, ParseException, InterruptedException {

		List<MethodCluster> methodClusters = new ArrayList<MethodCluster>();
		Map<SelfExecutableMethod, Boolean> map = new HashMap<SelfExecutableMethod, Boolean>();

		for (SelfExecutableMethod method1 : selfExecutableMethods) {
			if (map.containsKey(method1))
				continue;
			MethodCluster methodCluster = new MethodCluster(method1,
					argumentGenerator.getArguments(method1.getMethod(), SBMFConfiguration.NUMBER_TEST_CASE));
			map.put(method1, true);

			Map<SelfExecutableMethod, Boolean> ignored = new HashMap<SelfExecutableMethod, Boolean>();

			for (String argument : methodCluster.getArguments()) {

				String output1 = codeExecutor.execute(method1, Constants.SOURCE_CODE_STORAGE_DIR, argument);

				for (SelfExecutableMethod method2 : selfExecutableMethods) {

					if (method1.equals(method2))
						continue;

					if (!doesSignatureMatches(method1, method2)) {
						ignored.put(method2, true);
						continue;
					}
					if (ignored.containsKey(method2))
						continue;
					String output2 = codeExecutor.execute(method2, Constants.SOURCE_CODE_STORAGE_DIR, argument);

					if (!output1.equals(output2)) {
						ignored.put(method2, true);
					}
				}

			}

			for (SelfExecutableMethod method : selfExecutableMethods) {
				if (method1.equals(method) || ignored.containsKey(method) || map.containsKey(method))
					continue;
				methodCluster.addMethod(method);
				map.put(method, true);
			}
			methodClusters.add(methodCluster);
		}

		return methodClusters;
	}

	private boolean doesSignatureMatches(SelfExecutableMethod method1, SelfExecutableMethod method2) {
		boolean returnTypeMatched = method1.getMethod().getType().toString()
				.equals(method2.getMethod().getType().toString());
		boolean parametersMatched = parserUtility.getParameterString(method1.getMethod())
				.equals(parserUtility.getParameterString(method2.getMethod()));
		return returnTypeMatched && parametersMatched;
	}
}
