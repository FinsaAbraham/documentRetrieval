/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;

/**
 *
 * @author admin
 */
public class InvertedIndex {

    private ArrayList<Document> listOfDocument = new ArrayList<Document>();
    private ArrayList<Term> dictionary = new ArrayList<Term>();

    public InvertedIndex() {
    }

    public void addNewDocument(Document document) {
        getListOfDocument().add(document);
    }

    public ArrayList<Posting> getUnsortedPostingList() {
        ArrayList<Posting> list = new ArrayList<Posting>();
        for (int i = 0; i < getListOfDocument().size(); i++) {
            String[] termResult = getListOfDocument().get(i).getListofTerm();
            for (int j = 0; j < termResult.length; j++) {
                Posting tempPosting = new Posting(termResult[j],
                        getListOfDocument().get(i));
                list.add(tempPosting);
            }
        }
        return list;
    }

    public ArrayList<Posting> getUnsortedPostingListWithTermNumber() {
        ArrayList<Posting> list = new ArrayList<Posting>();
        for (int i = 0; i < getListOfDocument().size(); i++) {
            ArrayList<Posting> postingDocument = getListOfDocument().get(i).getListofPosting();
            for (int j = 0; j < postingDocument.size(); j++) {
                Posting tempPosting = postingDocument.get(j);
                list.add(tempPosting);
            }
        }
        return list;
    }

    public ArrayList<Posting> getSortedPostingList() {
        ArrayList<Posting> list = new ArrayList<Posting>();
        list = this.getUnsortedPostingList();
        Collections.sort(list);
        return list;
    }

    public ArrayList<Posting> getSortedPostingListWithTermNumber() {
        ArrayList<Posting> list = new ArrayList<Posting>();
        list = this.getUnsortedPostingListWithTermNumber();
        Collections.sort(list);
        return list;
    }

    /**
     * Method untuk mencari dokumen
     *
     * @param query
     * @return
     */
    public ArrayList<Posting> search(String query) {
        String tempQuery[] = query.split(" ");
        ArrayList<Posting> result = new ArrayList<Posting>();
        for (int i = 0; i < tempQuery.length; i++) {
            String string = tempQuery[i];
            if (i == 0) {
                result = searchOneWord(string);
            } else {
                ArrayList<Posting> result1 = searchOneWord(string);
                result = intersection(result, result1);
            }
        }
        return result;
    }

    /**
     * Method untuk menggabungkan 2 buah posting 
     *
     * @param p1
     * @param p2
     * @return
     */
    public ArrayList<Posting> intersection(ArrayList<Posting> p1,
            ArrayList<Posting> p2) {
        if (p1 == null || p2 == null) {
            return new ArrayList<>();
        }

        ArrayList<Posting> postings = new ArrayList<>();
        int p1Index = 0;
        int p2Index = 0;

        Posting post1 = p1.get(p1Index);
        Posting post2 = p2.get(p2Index);

        while (true) {
            if (post1.getDocument().getId() == post2.getDocument().getId()) {
                try {
                    postings.add(post1);
                    p1Index++;
                    p2Index++;
                    post1 = p1.get(p1Index);
                    post2 = p2.get(p2Index);
                } catch (Exception e) {
                    break;
                }

            } else if (post1.getDocument().getId() < post2.getDocument().getId()) {
                try {
                    p1Index++;
                    post1 = p1.get(p1Index);
                } catch (Exception e) {
                    break;
                }

            } else {
                try {
                    p2Index++;
                    post2 = p2.get(p2Index);
                } catch (Exception e) {
                    break;
                }
            }
        }
        return postings;
    }

    public ArrayList<Posting> searchOneWord(String word) {
        Term tempTerm = new Term(word);
        if (getDictionary().isEmpty()) {
            return null;
        } else {
            int positionTerm = Collections.binarySearch(dictionary, tempTerm);
            if (positionTerm < 0) {
                return null;
            } else {
                return dictionary.get(positionTerm).getPostingList();
            }
        }
    }

    public void makeDictionary() {
        ArrayList<Posting> list = getSortedPostingList();
        for (int i = 0; i < list.size(); i++) {
            if (getDictionary().isEmpty()) {
                Term term = new Term(list.get(i).getTerm());
                term.getPostingList().add(list.get(i));
                getDictionary().add(term);
            } else {
                Term tempTerm = new Term(list.get(i).getTerm());
                int position = Collections.binarySearch(getDictionary(), tempTerm);
                if (position < 0) {
                    tempTerm.getPostingList().add(list.get(i));
                    getDictionary().add(tempTerm);
                } else {
                    getDictionary().get(position).
                            getPostingList().add(list.get(i));
                    Collections.sort(getDictionary().get(position)
                            .getPostingList());
                }
                Collections.sort(getDictionary());
            }

        }

    }

    public void makeDictionaryWithTermNumber() {
        ArrayList<Posting> list = getSortedPostingListWithTermNumber();
        for (int i = 0; i < list.size(); i++) {
            if (getDictionary().isEmpty()) {
                Term term = new Term(list.get(i).getTerm());
                term.getPostingList().add(list.get(i));
                getDictionary().add(term);
            } else {
                Term tempTerm = new Term(list.get(i).getTerm());
                int position = Collections.binarySearch(getDictionary(), tempTerm);
                if (position < 0) {
                    tempTerm.getPostingList().add(list.get(i));
                    getDictionary().add(tempTerm);
                } else {
                    getDictionary().get(position).
                            getPostingList().add(list.get(i));
                    Collections.sort(getDictionary().get(position)
                            .getPostingList());
                }
                Collections.sort(getDictionary());
            }
        }

    }

    /**
     * @return the listOfDocument
     */
    public ArrayList<Document> getListOfDocument() {
        return listOfDocument;
    }

    /**
     * @param listOfDocument the listOfDocument to set
     */
    public void setListOfDocument(ArrayList<Document> listOfDocument) {
        this.listOfDocument = listOfDocument;
    }

    /**
     * @return the dictionary
     */
    public ArrayList<Term> getDictionary() {
        return dictionary;
    }

    /**
     * @param dictionary the dictionary to set
     */
    public void setDictionary(ArrayList<Term> dictionary) {
        this.dictionary = dictionary;
    }

    /**
     * Method mencari frequensi sebuah term dalam sebuah index
     *
     * @param term
     * @return
     */
    public int getDocumentFrequency(String term) {
        Term tempTerm = new Term(term);
        int index = Collections.binarySearch(dictionary, tempTerm);
        if (index > 0) {
            ArrayList<Posting> tempPosting = dictionary.get(index)
                    .getPostingList();
            return tempPosting.size();
        } else {
            return -1;
        }
    }

    /**
     * Method untuk mencari inverse term dari sebuah index
     *
     * @param term
     * @return
     */
    public double getInverseDocumentFrequency(String term) {
        Term tempTerm = new Term(term);
        int index = Collections.binarySearch(dictionary, tempTerm);
        if (index > 0) {
            int N = listOfDocument.size();
            int ni = getDocumentFrequency(term);
            double Nni = (double) N / ni;
            return Math.log10(Nni);
        } else {
            return 0.0;
        }
    }

    /**
     * Method untuk mencari term frequency
     *
     * @param term
     * @param idDocument
     * @return
     */
    public int getTermFrequency(String term, int idDocument) {
        Document document = new Document();
        document.setId(idDocument);
        int pos = Collections.binarySearch(listOfDocument, document);
        if (pos >= 0) {
            ArrayList<Posting> tempPosting = listOfDocument.get(pos).getListofPosting();
            Posting posting = new Posting();
            posting.setTerm(term);
            int postingIndex = Collections.binarySearch(tempPosting, posting);
            if (postingIndex >= 0) {
                return tempPosting.get(postingIndex).getNumberOfTerm();
            }
            return 0;
        }

        return 0;
    }

    /**
     * Method untuk menghitung TF-IDF dari sebuah dokumen
     *
     * @param idDocument
     */
    public ArrayList<Posting> makeTFIDF(int idDocument) {
        ArrayList<Posting> result = new ArrayList<Posting>();
        Document temp = new Document(idDocument);
        int cari = Collections.binarySearch(listOfDocument, temp);
        if (cari >= 0) {
            temp = listOfDocument.get(cari);
            result = temp.getListofPosting();
            for (int i = 0; i < result.size(); i++) {
                String tempTerm = result.get(i).getTerm();
                double idf = getInverseDocumentFrequency(tempTerm);
                int tf = result.get(i).getNumberOfTerm();
                double bobot = tf * idf;
                result.get(i).setWeight(bobot);
            }
            Collections.sort(result);
        } else {
        }
        return result;
    }

    /**
     * Method perkalian inner product dari PostingList Atribut yang dikalikan
     * adalah atribut weight TFIDF dari posting Ini dikenal dengan istilah
     * penghitungan jarak Euclidean
     *
     * @param p1
     * @param p2
     * @return
     */
    public double getInnerProduct(ArrayList<Posting> p1,
            ArrayList<Posting> p2) {
        Collections.sort(p2);
        Collections.sort(p1);
        double result = 0.0;
        for (int i = 0; i < p1.size(); i++) {
            Posting temp = p1.get(i);
            boolean found = false;
            for (int j = 0; j < p2.size() && found == false; j++) {
                Posting temp1 = p2.get(j);
                if (temp1.getTerm().equalsIgnoreCase(temp.getTerm())) {
                    found = true;
                    result = result + temp1.getWeight() * temp.getWeight();
                }
            }
        }
        return result;
    }

    /**
     * Method untuk membentuk posting list dari sebuah query
     *
     * @param query
     * @return
     */
    public ArrayList<Posting> getQueryPosting(String query) {
        Document temp = new Document(-1, query);
        ArrayList<Posting> result = temp.getListofPosting();
        for (int i = 0; i < result.size(); i++) {
            String tempTerm = result.get(i).getTerm();
            double idf = getInverseDocumentFrequency(tempTerm);
            int tf = result.get(i).getNumberOfTerm();
            double bobot = tf * idf;
            result.get(i).setWeight(bobot);
        }
        Collections.sort(result);
        return result;
    }

    /**
     * Method untuk menghitung panjang dari sebuah posting 
     * Asumsi posting memiliki komponen bobot/weight
     *
     * @param posting
     * @return
     */
    public double getLengthOfPosting(ArrayList<Posting> posting) {
        double result = 0.0;
        for (int i = 0; i < posting.size(); i++) {
            Posting post = posting.get(i);
            double weight = post.getWeight();
            weight = weight * weight;
            result = result + weight;
        }
        return Math.sqrt(result);
    }

    /**
     * Method untuk menghitung cosine similarity
     *
     * @param posting
     * @param posting1
     * @return
     */
    public double getCosineSimilarity(ArrayList<Posting> posting,
            ArrayList<Posting> posting1) {
        double hasilDotProduct = getInnerProduct(posting, posting1);
        double panjang_posting = getLengthOfPosting(posting);
        double panjang_posting1 = getLengthOfPosting(posting1);
        double result
                = hasilDotProduct / Math.sqrt(panjang_posting * panjang_posting1);
        return result;
    }

    /**
     * Method untuk mencari berdasar nilai TFIDF
     *
     * @param query
     * @return
     */
    public ArrayList<SearchingResult> searchTFIDF(String query) {
        ArrayList<SearchingResult> result = new ArrayList<SearchingResult>();
        ArrayList<Posting> queryPostingList = getQueryPosting(query);
        for (int i = 0; i < listOfDocument.size(); i++) {
            Document doc = listOfDocument.get(i);
            int idDoc = doc.getId();
            ArrayList<Posting> tempDocWeight = makeTFIDF(idDoc);
            double hasilDotProduct = getInnerProduct(tempDocWeight, queryPostingList);
            if (hasilDotProduct > 0) {
                SearchingResult resultDoc = new SearchingResult(hasilDotProduct, doc);
                result.add(resultDoc);
            }
        }
        Collections.sort(result);
        return result;
    }

    /**
     * Method untuk mencari dokumen berdasarkan cosine similarity
     *
     * @param query
     * @return
     */
    public ArrayList<SearchingResult> searchCosineSimilarity(String query) {
        ArrayList<SearchingResult> result = new ArrayList<SearchingResult>();
        ArrayList<Posting> queryPostingList = getQueryPosting(query);
        for (int i = 0; i < listOfDocument.size(); i++) {
            Document doc = listOfDocument.get(i);
            int idDoc = doc.getId();
            ArrayList<Posting> tempDocWeight = makeTFIDF(idDoc);
            double cosineSimilarity = getCosineSimilarity(tempDocWeight, queryPostingList);
            if (cosineSimilarity > 0) {
                SearchingResult resultDoc = new SearchingResult(cosineSimilarity, doc);
                result.add(resultDoc);
            }
        }
        Collections.sort(result);
        return result;
    }

    /**
     * Method untuk membuat list dokumen dari sebuah directory .
     *
     * @param directory
     */
    public void readDirectory(File directory) {
        File[] fileNames = directory.listFiles();
        int i = 1;
        for (File file : fileNames) {
            // if directory call the same method again
            if (file.isDirectory()) {
                readDirectory(file);
            } else {
                try {
                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                        String strLine;
                        String AllContent = "";
                        // Read lines from the file, returns null when end of stream 
                        // is reached                    
                        while ((strLine = br.readLine()) != null) {
                            AllContent += strLine + " \n";
                        }
                        Document doc = new Document(i, AllContent, file.getName().replace(".txt", ""));
                        listOfDocument.add(doc);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            i++;
        }
        makeDictionaryWithTermNumber();
    }

    /**
     * Method untuk membaca file dokumen berdasar file txt.
     *
     * @param file
     */
    public void readFileTXT(File file) {
        try (FileReader reader = new FileReader(file);
                BufferedReader br = new BufferedReader(reader)) {

            // read line by line
            String line;
            String AllContent = "";
            while ((line = br.readLine()) != null) {
                AllContent += line + " \n";
            }
            Document doc = new Document(listOfDocument.size() + 1, AllContent, file.getName().replace(".txt", ""));
            listOfDocument.add(doc);
            makeDictionaryWithTermNumber();

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }
}
