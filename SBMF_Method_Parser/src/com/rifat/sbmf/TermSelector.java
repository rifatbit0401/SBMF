package com.rifat.sbmf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rifat.javacode.model.SelfExecutableMethod;
import com.rifat.javacode.utility.StringUtility;
import com.rifat.sbmf.model.MethodCluster;

public class TermSelector {

	private StringUtility stringUtility;

	public TermSelector() {
		stringUtility = new StringUtility();
	}

	public List<String> getProperTerms(MethodCluster methodCluster) {
		Map<String, Integer> termFerquencyMap = new HashMap<String, Integer>();

		for (SelfExecutableMethod method : methodCluster.getMethods()) {

			String methodName = method.getMethod().getName();
			String[] keywords = stringUtility.toPhraseString(methodName).split(" ");
			for (String keyword : keywords) {
				if (!termFerquencyMap.containsKey(keyword))
					termFerquencyMap.put(keyword, 0);
				termFerquencyMap.put(keyword, termFerquencyMap.get(keyword) + 1);

			}
		}

		List<String> topTerms = new ArrayList<String>();

		for (String term : termFerquencyMap.keySet()) {
			if (doesMaximumMethodContain(methodCluster, termFerquencyMap, term)) {
				topTerms.add(term);
			}

		}

		return topTerms;
	}

	private boolean doesMaximumMethodContain(MethodCluster methodCluster, Map<String, Integer> termFerquencyMap,
			String term) {
		return termFerquencyMap.get(term) >= methodCluster.getMethods().size() / 2;
	}
}
