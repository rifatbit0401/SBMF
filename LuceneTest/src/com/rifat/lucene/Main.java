package com.rifat.lucene;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

public class Main {

	private static final String INDEX_DIR = "C:/Users/Rifat/Desktop/sbmf/lucene test";

	public static void main(String[] args) throws IOException, ParseException {

		createIndex("C:/Users/Rifat/Desktop/sbmf/my_sbmf");
		System.out.println("Indexing done");

		Searcher searcher = new Searcher(INDEX_DIR);
		TopDocs hits = searcher.search("main");

		for (ScoreDoc scoreDoc : hits.scoreDocs) {
			System.out.println(searcher.getDocument(scoreDoc).getField(LuceneConstants.FILE_NAME).stringValue());
		}

	}

	public static void createIndex(String path) throws IOException {
		Indexer indexer = new Indexer(INDEX_DIR);
		indexer.createIndex(path);
		indexer.close();
	}
}
