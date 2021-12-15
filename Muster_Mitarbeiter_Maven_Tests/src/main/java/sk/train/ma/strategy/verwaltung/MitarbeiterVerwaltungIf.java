package sk.train.ma.strategy.verwaltung;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import sk.train.strategy.model.Mitarbeiter;

public interface MitarbeiterVerwaltungIf {

	BigDecimal getGehaltssumme();

	// nur zum Test
	//TreeMap<String, Mitarbeiter> getMap();

	List<Mitarbeiter> getMlist(Comparator<Mitarbeiter> comp);

	String addMitarbeiter(Mitarbeiter m);

	void delMitarbeiter(Mitarbeiter m);

	void delMitarbeiter(String persnr);

	Mitarbeiter getMitarbeiter(String persnr);

	/********* I/O-Methoden ******************/

	//Textuelle Ausgabe in Datei
	void writeToFile(String fname);

}