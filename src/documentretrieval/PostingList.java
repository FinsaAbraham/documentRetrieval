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
public class PostingList {
    private ArrayList<Posting> postings;

    public ArrayList<Posting> getPostings() {
        return postings;
    }

    public void setPostings(ArrayList<Posting> postings) {
        this.postings = postings;
    }

    public PostingList(ArrayList<Posting> postings) {
        this.postings = postings;
    }
}
