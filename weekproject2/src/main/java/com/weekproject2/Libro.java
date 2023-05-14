package com.weekproject2;

public class Libro extends Scritto {
    long ISBN;
    String titolo;
    int annoUscita;
    int numeroPagine;
    String type;
    
    String autore;
    String genere;

    public Libro( long ISBN, String titolo, int annoUscita, int numeroPagine, String autore, String genere){
        super(ISBN, titolo, annoUscita, numeroPagine);
        this.type = "Libro";
        this.autore = autore;
        this.genere = genere;
    }
    public Libro( long ISBN, String titolo, int annoUscita, int numeroPagine){
        super(ISBN, titolo, annoUscita, numeroPagine);
        this.type = "Libro";
    }
    public Libro() {};

    public void setAutore(String autore) {
        this.autore = autore;
    }
    public String getAutore() {
        return autore;
    }
    public void setGenere(String genere) {
        this.genere = genere;
    }
    public String getGenere() {
        return genere;
    }

    @Override
    public String toString() {
        
        return super.toString() + "@" +this.autore+"#"+this.genere;
    }

    
}
