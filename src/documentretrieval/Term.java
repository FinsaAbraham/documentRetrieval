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
public class Term {
    private String term;
    private ArrayList <PostingList> List;

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public ArrayList<PostingList> getList() {
        return List;
    }

    public void setList(ArrayList<PostingList> List) {
        this.List = List;
    }

    public Term(String term, ArrayList<PostingList> List) {
        this.term = term;
        this.List = List;
    }
}
