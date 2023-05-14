package com.weekproject2;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.weekproject2.Rivista.period;

import org.apache.commons.io.FileUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class Archivi {
    static Logger log = LoggerFactory.getLogger(Archivi.class);
    // ---------------- Dati ------------------------------------------
    public static long isbnCounter = 1;
    public static long getIsbnCounter() {
        return isbnCounter;
    }
    public static void setIsbnCounter(long isbnCounter) {
        CatalogoBibliografico.isbnCounter = isbnCounter;
    }  
    // Tutti gli scritti
    static Map< Long, Scritto > archivioScritti = new HashMap<>();
    public static Map<Long, Scritto> getArchivioScritti() {
        return archivioScritti;
    }
    public static void setArchivioScritti(Map<Long, Scritto> archivioScritti) {
        Archivi.archivioScritti = archivioScritti;
    }
    // Tutti i Libri
    static Map< Long, Libro > archivioLibri = new HashMap<>();
    public static Map<Long, Libro> getArchivioLibri() {
        return archivioLibri;
    }
    public static void setArchivioLibri(Map<Long, Libro> archivioLibri) {
        Archivi.archivioLibri = archivioLibri;
    }
    // Tutte le Riviste
    static Map< Long, Rivista > archivioRiviste = new HashMap<>();
    public static Map<Long, Rivista> getArchivioRiviste() {
        return archivioRiviste;
    }
    public static void setArchivioRiviste(Map<Long, Rivista> archivioRiviste) {
        Archivi.archivioRiviste = archivioRiviste;
    }
    // File su disco 
    public static File libriFile = new File("EpicStorage/archivioLibri.txt");
    public static File rivisteFile = new File("EpicStorage/archivioRiviste.txt");
    // ----------------------------------------------------------------------------
    // ---------------- Metodi di reload ------------------------------------------
    static public void caricaArchivio() throws IOException {
		caricaLibri();
        caricaRiviste();
        System.out.println("");
	}
    // Ricostruiscono gli archivi passando loro lo String[] dei file 
     private static void rivisteDriver(String[] arrayFile) {
        Arrays.stream(arrayFile).forEach(rivista -> {
            String[] splitIsbn = rivista.split("=");
            long isbn = Long.parseLong(splitIsbn[0].trim());
            String[] splitTitolo = splitIsbn[1].split("!");
            String[] splitPagine = splitTitolo[1].split("&");
            String[] splitAnno = splitPagine[1].split("§");
            Rivista newRivista = new Rivista(isbn, splitTitolo[0], Integer.parseInt(String.valueOf(splitAnno[0])), Integer.parseInt(String.valueOf(splitPagine[0])), period.valueOf(splitAnno[1]));
            archivioRiviste.put(isbn, newRivista);
            archivioScritti.put(isbn, newRivista);
        });
    }
     private static void libriDriver(String[] arrayFile){
        Arrays.stream(arrayFile).forEach(rivista -> { 
            String[] splitIsbn = rivista.split("=");
            long isbn = Long.parseLong(splitIsbn[0].trim());
            String[] splitTitolo = splitIsbn[1].split("!");
            String[] splitPagine = splitTitolo[1].split("&");
            String[] splitAnno = splitPagine[1].split("@");    
            String[] splitAutore = splitAnno[1].split("#");
            Libro newLibro = new Libro(isbn, splitTitolo[0], Integer.parseInt(splitAnno[0]), Integer.parseInt(splitPagine[0]), splitAutore[0], splitAutore[1]);
            archivioLibri.put(isbn, newLibro);
            archivioScritti.put(isbn, newLibro);
        });
    }
    // Avviano i driver, targettando uno i libri, l'altro le riviste, fornendo loro lo String[] da iterare
     private static void caricaLibri() throws IOException{
        String libriSuDisco = FileUtils.readFileToString(libriFile, "UTF-8")
        .replace("{","")
        .replace("}", "");
        if(libriSuDisco != ""){
        String[] arrayFile = libriSuDisco.split(",");
        libriDriver(arrayFile);
        System.out.println("Libri importati da "+ libriFile);
        } else {
            System.out.println("Non sono presenti libri");
        }
    }
     private static void caricaRiviste() throws IOException{
        String rivisteSuDisco = FileUtils.readFileToString(rivisteFile, "UTF-8")
        .replace("{","")
        .replace("}", "");
        if(rivisteSuDisco != ""){        
        String[] arrayFile = rivisteSuDisco.split(",");
        rivisteDriver(arrayFile);
        System.out.println("Riviste importate da "+rivisteFile);
        } else {
            System.out.println("Non sono presenti riviste");
        }
    }
    // ----------------------------------------------------------------------------
    // ---------------- Metodi di aggiunta e rimozione ----------------------------
    // Aggiungi Libro
    static public  Libro aggiungiLibro(long ISBN, String titolo, int annoUscita, int numeroPagine, String autore, String genere){
        Libro libro = new Libro(ISBN,titolo,annoUscita,numeroPagine, autore, genere);
        archivioScritti.put(ISBN, libro);
        archivioLibri.put(ISBN, libro);
        System.out.println("Hai aggiunto ("+libro.toString()+") all'archivio");
        System.out.println("");
        return libro;  
    }
    // Aggiungi Rivista
    static public  Rivista aggiungiRivista(long ISBN, String titolo, int annoUscita, int numeroPagine, period period){
        Rivista rivista = new Rivista(ISBN,titolo,annoUscita,numeroPagine,period);
        archivioScritti.put(ISBN, rivista);
        archivioRiviste.put(ISBN, rivista);
        System.out.println("Hai aggiunto ("+rivista.toString()+") all'archivio");
        System.out.println("");
        return rivista;  
    }
    // Rimuovi Scritto
    static public  void rimuoviScritto(long ISBN) {
        System.out.println("Hai rimosso ("+archivioScritti.get(ISBN).toString()+") dall'archivio");
        System.out.println("");
        archivioScritti.remove(ISBN);
        if(archivioLibri.keySet().contains(ISBN)){
            archivioLibri.remove(ISBN);
        } else if(archivioRiviste.keySet().contains(ISBN)){
            archivioRiviste.remove(ISBN);
        }
    }
    // ---------------------------------------------------------------------------
    // ---------------- Metodi di ricerca ----------------------------------------
    // ISBN
    static public Scritto cercaIsbn(long ISBN){
        Scritto scritto = archivioScritti.get(ISBN);
        System.out.println("Risultato ricerca: "+scritto.toString());
             System.out.println("");
        return scritto;
    }
    // Anno pubblicazione
    static public  Collection<Scritto> cercaAnno(int annoUscita){
        Collection<Scritto> dellAnno = archivioScritti.values()
        .stream()
        .filter(scritto -> scritto.getAnnoUscita() == annoUscita)
        .toList();
        System.out.println("Risultato ricerca: "+ dellAnno.toString());
             System.out.println("");
        return dellAnno;
    }
    // Autore
    static public List<Libro> cercaAutore(String autore){
        List<Libro> dellAutore = archivioLibri.values()
        .stream()
        .filter(l -> l.getAutore().contains(autore))     
        .toList();
         System.out.println("Risultato ricerca: "+dellAutore.toString());
         System.out.println("");
        return dellAutore;
    }
    // Metodo di salvataggio su disco (.txt)
    static public  void salvaArchivio() throws IOException {
		FileUtils.write(libriFile,archivioLibri.toString(),"UTF-8");
		FileUtils.write(rivisteFile,archivioRiviste.toString(),"UTF-8");
        System.out.println("");
		System.out.println("Archivio salvato");
        System.out.println("");
	}

  
}

