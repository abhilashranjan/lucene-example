package helloworld;

import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;


/**
 * This class is a basic hello world scenaerio for the lucene. This example contains the 4 book name and their isbn.
 * We create the query for the isbn and matching title keyword.
 */
public class MYFirstLuceneExample {
    public static void main(String[] args) throws IOException, ParseException {
        // 0. Specify the analyzer for tokenizing text.
        //    The same analyzer should be used for indexing and searching
        StandardAnalyzer analyzer = new StandardAnalyzer();

        // 1. create the index directory we are using RAMDirectory.
        Directory index = new RAMDirectory();

        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        IndexWriter indexWriter = new IndexWriter(index, config);
        addDoc(indexWriter, "Lucene in Action", new Long(1l));
        addDoc(indexWriter, "Lucene for Dummies", new Long(2l));
        addDoc(indexWriter, "Spark in Action", new Long(5l));
        addDoc(indexWriter, "Hadoop in Action", new Long(3l));
        indexWriter.close();

        // 2. query This can accept only word no alphabet.
        String querystrTitle = "lucene";
        String querystrISBN = "5";


        // 3. search
        int hitsPerPage = 10;
        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);

        //4Creating query for the title search.
        Query q = new QueryParser("title", analyzer).parse(querystrTitle);
        TopDocs docs = searcher.search(q, hitsPerPage);
        ScoreDoc[] scoreDocsTitle = docs.scoreDocs;


        //5. Finding the exact match
        Query qisbn = new QueryParser("isbn", analyzer).parse(querystrISBN);
        BooleanQuery bq = new BooleanQuery();
        bq.add(qisbn, BooleanClause.Occur.MUST);
        TopDocs docsIsbn = searcher.search(bq, hitsPerPage);
        ScoreDoc[] scoreDocsISBN = docsIsbn.scoreDocs;

        // 6. display results
        System.out.println("-------------TITLE--------------");
        printResult(searcher, scoreDocsTitle);
        System.out.println("-------------ISBN--------------");
        printResult(searcher, scoreDocsISBN);

        // reader can only be closed when there
        // is no need to access the documents any more.
        reader.close();
    }

    /**
     * This method is printing the result.
     *
     * @param searcher
     * @param hits
     * @throws IOException
     */
    private static void printResult(IndexSearcher searcher, ScoreDoc[] hits) throws IOException {
        System.out.println("Found " + hits.length + " hits.");
        for (int i = 0; i < hits.length; ++i) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            System.out.println((i + 1) + ". " + d.get("isbn") + "\t" + d.get("title"));
        }
    }

    /**
     * This method is adding indexing based on the title and isbn.
     *
     * @param w
     * @param title
     * @param isbn
     * @throws IOException
     */
    private static void addDoc(IndexWriter w, String title, Long isbn) throws IOException {
        Document doc = new Document();
        doc.add(new TextField("title", title, Field.Store.YES));

        // use a string field for isbn because we don't want it tokenized
        doc.add(new StringField("isbn", String.valueOf(isbn), Field.Store.YES));
        w.addDocument(doc);
    }
}