/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package documentretrieval;

import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class InvertedIndex {
    private ArrayList <Term> termList;

    public InvertedIndex(ArrayList<Term> termList) {
        this.termList = termList;
    }

    public ArrayList<Term> getTermList() {
        return termList;
    }

    public void setTermList(ArrayList<Term> termList) {
        this.termList = termList;
    }
}
