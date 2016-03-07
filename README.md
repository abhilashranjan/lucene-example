# lucene-example
Hello world usging apache lucene.
This is a maven based project contains Lucene basics.
Apache Lucen is a full text-search library for java which helps
you add search capability to your application/website.

# Few Terminology need to remember.
## Index

Lucene uses index called an inverted index, because it inverts
a page-centric data structure in class room (Student Name->roll number) to a keyword-centric
data structure (word->pages).
At time of index we can store the field depend upn the usages find that index will be easier.
<code>
Field.Store.YES
</code>

If we are storing index using StringField in document then while quering It shot be exact match.
String field dos not work on tokinezier so it require exact match.

In case of TextField it works on toknizing the element so it doesnot require the exact macth,
but be carefull it doesnot reconize english gramattical sentence like A, An The etc..


## Analyzer Class: Parsing the Documents
Most likely, the data that you want to index by Lucene is plain text English. The job of Analyzer is to "parse" each field of your data into indexable "tokens" or keywords. Several types of analyzers are provided out of the box. Table 1 shows some of the more interesting ones.

Table 1 Lucene analyzers.
|Analyzer 	|Description
------------------------------------------------------------------------
|StandardAnalyzer 	|A sophisticated general-purpose analyzer.|-
|WhitespaceAnalyzer |	A very simple analyzer that just separates tokens using white space.|-
|StopAnalyzer 	|Removes common English words that are not usually useful for indexing.|-
|SnowballAnalyzer 	|An interesting experimental analyzer that works on word roots (a search on rain should also return entries with raining, rained, and so on). |-


## Document

An index consists of one or more Documents.
Indexing involves adding Documents to an IndexWriter,
and searching involves retrieving Documents from an index via an IndexSearcher. 

More precisely, to add a field to a document, you create a new instance of the Field class, which can be either a StringField or a TextField (the difference between the two will be explained shortly). A field object takes the following three parameters:

    Field name: This is the name of the field. In the above example, they are "id" and "description".
    Field value: This is the value of the field. In the above example, they are "Hotel-1345" and "A beautiful hotel". A value can be a String like our example or a Reader if the object to be indexed is a file.
    Storage flag: The third parameter specifies whether the actual value of the field needs to be stored in the lucene index or it can be discarded after it is indexed. Storing the value is useful if you need the value later, like you want to display it in the search result list or you use the value to look up a tuple from a database table, for example. If the value must be stored, use Field.Store.YES. You can also use Field.Store.COMPRESS for large documents or binary value fields. If you don't need to store the value, use Field.Store.NO.

StringField vs TextField: In the above example, the "id" field contains the ID of the hotel, which is a single atomic value. In contrast, the "description" field contains an English text, which should be parsed (or "tokenized") into a set of words for indexing. Use StringField for a field with an atomic value that should not be tokenized. Use TextField for a field that needs to be tokenized into a set of words. 


