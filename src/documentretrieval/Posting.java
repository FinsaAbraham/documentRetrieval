/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package documentretrieval;

/**
 *
 * @author admin
 */
public class Posting {
    private Document Document;

    public Document getDocument() {
        return Document;
    }

    public void setDocument(Document Document) {
        this.Document = Document;
    }

    public Posting(Document Document) {
        this.Document = Document;
    }
}
