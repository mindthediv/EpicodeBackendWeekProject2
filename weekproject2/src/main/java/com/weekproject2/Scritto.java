package com.weekproject2;

public class Scritto {
    long ISBN;
    String titolo;
    int annoUscita;
    int numeroPagine;
    String type = "Scritto";

    public Scritto(long ISBN, String titolo, int annoUscita, int numeroPagine) {
    this.ISBN = ISBN;
    this.titolo = titolo;
    this.annoUscita = annoUscita;
    this.numeroPagine = numeroPagine;
    }
    public Scritto(){};

    @Override
    public String toString() {
        return this.titolo+"!"+this.numeroPagine+"&"+this.annoUscita;
    }

    public int getAnnoUscita() {
        return annoUscita;
    }
    public void setAnnoUscita(int annoUscita) {
        this.annoUscita = annoUscita;
    }
    public long getISBN() {
        return ISBN;
    }
    public void setISBN(long iSBN) {
        ISBN = iSBN;
    }
    public int getNumeroPagine() {
        return numeroPagine;
    }
    public void setNumeroPagine(int numeroPagine) {
        this.numeroPagine = numeroPagine;
    }
    public String getTitolo() {
        return titolo;
    }
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
    
    
}
