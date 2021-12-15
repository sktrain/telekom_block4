/* Mitarbeiter-Loesung erweitert um verschiedene Sortiervarianten:
 * Lambdas werden kurz eingefuehrt.
 */

package sk.train.ma.strategy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import sk.train.ma.strategy.util.NachnamenComparator;
import sk.train.ma.strategy.verwaltung.MitarbeiterVerwaltung;
import sk.train.ma.strategy.verwaltung.MitarbeiterVerwaltungIf;
import sk.train.strategy.model.FixGehaltModell;
import sk.train.strategy.model.Geschlecht;
import sk.train.strategy.model.Mitarbeiter;

public class MitarbeiterVerwaltungTest {

	public static void main(String[] args) {

		MitarbeiterVerwaltung mv = new MitarbeiterVerwaltung();
		
		//System.out.println(Objects.isNull(mv));
			
		//Mitarbeiter entfernen bzw. hinzufuegen
		
		System.out.println(mv.getMap().size());
		Mitarbeiter neu = new Mitarbeiter(200, "Max", "Meier", 
				                          LocalDate.of(1990, 1, 1), 
				                          LocalDate.of(2010, 1, 1),
				                          Geschlecht.M,
				                          new FixGehaltModell(new BigDecimal(5000)));
		
		String persnr  = mv.addMitarbeiter(neu);   
		Mitarbeiter hinzugefuegt = mv.getMitarbeiter(persnr); 
		
		System.out.println(hinzugefuegt);
		System.out.println(mv.getMap().size());
		
		mv.delMitarbeiter(neu);
		
		System.out.println(mv.getMap().size());
		
		print(mv);  //strange: hier ist der "PO200" im Falle der TreeMap noch enthalten !!
		
//		hinzugefuegt = mv.getMitarbeiter("PO200");
		
//		printNaturalOrder(mv);
//		
//		printSortedByName(mv);
//
//		printSortedByGebDatum(mv);
//
//		printSortedByGebDatumAndGehalt(mv);

	}
	

	private static void print(MitarbeiterVerwaltungIf mv) {
		//Ausgabe sortiert nach dem Key, da TreeMaps die Values nicht geordnet speichern
		System.out.println(mv);   //vorausgesetzt entsprechende toString-Methode
		

	}

	private static void printNaturalOrder(MitarbeiterVerwaltungIf mv) {
		// Jetzt nach Geh�ltern sortiert (natural order)
		System.out.println("\n***********Hier nach Gehalt sortiert**************\n");
		mv.getMlist(Comparator.naturalOrder()).forEach(System.out::println);
	}

	private static void printSortedByName(MitarbeiterVerwaltungIf mv) {
		// jetzt nach Nachnamen sortiert
		System.out.println("\n***********Hier nach Nachnamen sortiert**************\n");
		mv.getMlist(new NachnamenComparator()).forEach(System.out::println);
	}

	private static void printSortedByGebDatum(MitarbeiterVerwaltungIf mv) {
		// jetzt mal nur per Lambda nach den Geburtsdaten und Ausgabe mit
		// foreach-Lambda-Version
		System.out.println("\n***********Hier nach Geburtsdatum sortiert**************\n");
		Comparator<Mitarbeiter> cgebdatum = (Mitarbeiter m1, Mitarbeiter m2) -> {
			return m1.getGebdatum().compareTo(m2.getGebdatum());
		};
		mv.getMlist(cgebdatum).forEach(System.out::println);
	}
	
	private static void printSortedByGebDatumAndGehalt(MitarbeiterVerwaltungIf mv) {
		// und jetzt mal zuerst nach den Geburtsdaten und dann nachsortiert anhand der Geh�lter
		System.out.println("\n******Hier nach Geburtsdatum und Gehalt sortiert************\n");
		Comparator<Mitarbeiter> cgebdatum = (Mitarbeiter m1, Mitarbeiter m2) -> {
			return m1.getGebdatum().compareTo(m2.getGebdatum());
		};
		Comparator<Mitarbeiter> cgehalt = (Mitarbeiter m1, Mitarbeiter m2) -> {
			return m1.getGmodell().getGehalt().compareTo(m2.getGmodell().getGehalt());
		};
		mv.getMlist(cgebdatum.thenComparing(cgehalt)).forEach(System.out::println);;
		
	}

}