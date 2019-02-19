/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class Term {

    private String term;
    private PostingList termList;

    public Term() {
        termList = new PostingList();
    }

    public Term(String term, PostingList termList) {
        this.term = term;
        this.termList = termList;
    }

    public String getTerm() {
        return term;
    }

    public PostingList getTermList() {
        return termList;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public void setTermList(PostingList termList) {
        this.termList = termList;
    }

}
