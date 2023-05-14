package com.weekproject2;


public class Rivista extends Scritto {
    long ISBN;
    String titolo;
    int annoUscita;
    int numeroPagine;
    String type = "Rivista";
    
    public static enum period {SETTIMANALE,MENSILE,SEMESTRALE};
    public period period;

    public Rivista( long ISBN, String titolo,int annoUscita, int numeroPagine,  period period){
        super(ISBN, titolo, annoUscita, numeroPagine);
        this.period = period;
    }
    public Rivista( long ISBN, String titolo,int annoUscita, int numeroPagine){
        super(ISBN, titolo, annoUscita, numeroPagine);
        
    }
    public Rivista(){};

    public void setPeriod(period period) {
        this.period = period;
    }
    public period getPeriod() {
        return period;
    }

    @Override
    public String toString() {
        return super.toString() + "§"+this.period ;
    }
}
