package com.weekproject2;


public class Rivista extends Scritto {
    long ISBN;
    String titolo;
    int annoUscita;
    int numeroPagine;
    String type;
    
    public static enum period {SETTIMANALE,MENSILE,SEMESTRALE};
    public period period;

    public Rivista( long ISBN, String titolo,int annoUscita, int numeroPagine,  period period){
        super(ISBN, titolo, annoUscita, numeroPagine);
        this.type = "Rivista";
        this.period = period;
    }
    public Rivista( long ISBN, String titolo,int annoUscita, int numeroPagine){
        super(ISBN, titolo, annoUscita, numeroPagine);
        this.type = "Rivista";
    }

    public void setPeriod(period period) {
        this.period = period;
    }
    public period getPeriod() {
        return period;
    }

    @Override
    public String toString() {
        return super.toString().replace("Scritto", this.type) + " period: "+this.period ;
    }
}
