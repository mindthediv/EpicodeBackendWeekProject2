package com.weekproject2;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.io.FileUtils;
import com.weekproject2.Rivista.period;

public final class CatalogoBibliografico {
    public static Logger log = LoggerFactory.getLogger(CatalogoBibliografico.class);
    public static Map< Long, Scritto > archivioScritti = new HashMap<>();
    public static Map< Long, Libro > archivioLibri = new HashMap<>();
    public static Map< Long, Rivista > archivioRiviste = new HashMap<>();

    public static Scritto aggiungiScritto(long ISBN, String titolo, int annoUscita, int numeroPagine){
        Scritto scritto = new Scritto(ISBN,titolo, annoUscita, numeroPagine);
        archivioScritti.put(ISBN, scritto);
        System.out.println("Hai aggiunto ("+scritto.toString()+") all'archivio");
        return scritto; 
    }
    public static Libro aggiungiLibro(long ISBN, String titolo, int annoUscita, int numeroPagine, String autore, String genere){
        Libro libro = new Libro(ISBN,titolo,annoUscita,numeroPagine, autore, genere);
        archivioScritti.put(ISBN, libro);
        archivioLibri.put(ISBN, libro);
        System.out.println("Hai aggiunto ("+libro.toString()+") all'archivio");
        return libro;  
    }
    public static Rivista aggiungiRivista(long ISBN, String titolo, int annoUscita, int numeroPagine, period period){
        Rivista rivista = new Rivista(ISBN,titolo,annoUscita,numeroPagine,period);
        archivioScritti.put(ISBN, rivista);
        archivioRiviste.put(ISBN, rivista);
        System.out.println("Hai aggiunto ("+rivista.toString()+") all'archivio");
        return rivista;  
    }

    // public static  void aggiungiAdArchivio(long ISBN, String titolo, int annoUscita, int numeroPagine, String type) {
    //     switch (type) {
    //         case "Scritto":
    //             aggiungiScritto(ISBN, titolo, annoUscita, numeroPagine);
    //             break;
    //         case "Libro":
    //             aggiungLibro(ISBN, titolo, annoUscita, numeroPagine);
    //             break;
    //         case "Rivista":
    //             aggiungRivista(ISBN, titolo, annoUscita, numeroPagine);
    //             break;
    //         default:
    //             break;
    //     }
    // }

    public static void rimuoviScritto(long ISBN) {
        System.out.println("Hai rimosso ("+archivioScritti.get(ISBN).toString()+") dall'archivio");
        archivioScritti.remove(ISBN);
        if(archivioLibri.keySet().contains(ISBN)){
            archivioLibri.remove(ISBN);
        } else if(archivioRiviste.keySet().contains(ISBN)){
            archivioRiviste.remove(ISBN);
        }
    }

    public static Scritto cercaIsbn(long ISBN){
        Scritto scritto = archivioScritti.get(ISBN);
        System.out.println("Risultato ricerca: "+scritto.toString());
        return scritto;
    }
    public static Collection<Scritto> cercaAnno(int annoUscita){
        Collection<Scritto> dellAnno = archivioScritti.values()
        .stream()
        .filter(scritto -> scritto.getAnnoUscita() == annoUscita)
        .toList();
        System.out.println("Risultato ricerca: "+ dellAnno.toString());
        return dellAnno;
    }
    public static Collection<Libro> cercaAutore(String autore){
        List<Libro> dellAutore = archivioLibri.values()
        .stream()
        .filter(libro -> libro.getAutore() == autore)
        .toList();        
        System.out.println("Risultato ricerca: "+ dellAutore.toString());
        return dellAutore;
    }

    public static void salvaArchivio() throws IOException {
		File archivio = new File("EpicStorage/archivio.txt");
		FileUtils.write(archivio,scriviSuDisco(),"UTF-8");
		System.out.println("Registro salvato");
	}

    public static CharSequence scriviSuDisco(){
        CharSequence data = archivioScritti.toString();
        return data;        
    }

    // public HashMap< Long, Scritto> caricaArchivio() throws IOException {
	// 	File archivio = new File("EpicStorage/archivio.txt");
	// 	String data = FileUtils.readFileToString(archivio,"UTF-8");
	// 	String[] scritti = data.split(",");
    //     Map< Long, Scritto> archivioCaricato = new HashMap< Long, Scritto> ();
    //     archivioCaricato.put(leggiIsbn(scritti), leggiScritto());
	// 	return archivioCaricato;
	// }

    static long isbn;
    public static long leggiIsbn(String[] scritti){
        for (int i=0; i < scritti.length; i++){
            char[] charData = scritti[i].toCharArray();
            for(int k = 0; k < charData.length; k++){
                
                while(charData[i]!='='){
                    String stringData = charData[i] + "";
                    isbn += Long.parseLong(stringData);
                }
                log.info(""+isbn); 
            }
        }
        return isbn; 
    }

    public static void main(String[] args) throws IOException {
        Libro scritto1 = aggiungiLibro((long) 123, "Even", 2023, 2, "Elon Musk", "Scienze" );
        Libro scritto2 = aggiungiLibro((long) 456, "Odd", 2019, 1, "Bill Gates", "Scienze");
        Rivista scritto3 = aggiungiRivista(789, "Math", 2023, 123, period.SEMESTRALE);
        cercaIsbn(456);
        cercaAnno(2023);
        cercaAutore("Bill Gates");
        rimuoviScritto(123);
        salvaArchivio();

        //test carica dati (memo: nf on ssd !!!)
        String data = "{789=[Rivista nPagine(123)] - 'Math'  Anno: 2023 period: SEMESTRALE, 456=[Libro nPagine(1)] - 'Odd'  Anno: 2019 Autore: Bill Gates Genere: Scienze}";
        String[] scritti = data.split(",");
        leggiIsbn(scritti);
    }
    
}
