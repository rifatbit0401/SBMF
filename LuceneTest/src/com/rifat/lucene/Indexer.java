package com.rifat.lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Indexer {

	private IndexWriter indexWriter;

	@SuppressWarnings("deprecation")
	public Indexer(String indexDirectoryPath) throws IOException {
		Directory directory = FSDirectory.open(new File(indexDirectoryPath));

		indexWriter = new IndexWriter(directory, new StandardAnalyzer(Version.LUCENE_36), true,
				IndexWriter.MaxFieldLength.UNLIMITED);

	}

	public void close() throws CorruptIndexException, IOException {
		indexWriter.close();
	}

	private Document getDocument(File file) throws FileNotFoundException, IOException {

		Document document = new Document();

		Field contentField = new Field(LuceneConstants.CONTENTS, readFile(file), Field.Store.YES, Field.Index.ANALYZED);
		Field fileNameField = new Field(LuceneConstants.FILE_NAME, file.getName(), Field.Store.YES,
				Field.Index.ANALYZED);
		Field filePathField = new Field(LuceneConstants.FILE_PATH, file.getPath(), Field.Store.YES,
				Field.Index.ANALYZED);
		document.add(filePathField);
		document.add(fileNameField);
		document.add(contentField);
		return document;
	}

	private String readFile(File file) throws FileNotFoundException, IOException {

		BufferedReader reader = new BufferedReader(new FileReader(file));
		String content = "";
		String line;
		while ((line = reader.readLine()) != null) {
			content += line + "\n";
		}
		return content;
	}
	
	private void indexFile(File file) throws CorruptIndexException, FileNotFoundException, IOException{
		indexWriter.addDocument(getDocument(file));
	}
	
	public int createIndex(String dataDirPath) throws CorruptIndexException, FileNotFoundException, IOException{
		
		File[]files = new  File(dataDirPath).listFiles();
		
		for (File file : files) {
			indexFile(file);
		}
		return indexWriter.numDocs();
	}
	

}
