/* Bereinigte Klasse mit Gehaltsmodell
 * 
 */

package sk.train.strategy.model;

import java.io.Serializable;
//import java.math.BigDecimal;
import java.time.LocalDate;

public class Mitarbeiter implements Comparable<Mitarbeiter>, Serializable {

	// statische Elemente
	//private static int zaehler = 100000;

	private static final long serialVersionUID = 1L;
	
	// Datenattribute
	private String persnr;
	private String vorname;
	private String nachname;
	private LocalDate gebdatum;
	private LocalDate einstdatum;
	private Geschlecht geschlecht;
	private Gehaltsmodell gmodell;

	// Konstruktoren
	public Mitarbeiter(int persnr, String vorname, String nachname, LocalDate gebdatum, LocalDate einstdatum, Geschlecht geschlecht,
			Gehaltsmodell gmodell) {
		super();
		//this.persnr = "PO" + zaehler;
		//++zaehler;
		this.persnr = "PO"+ persnr;
		this.vorname = vorname;
		this.nachname = nachname;
		this.gebdatum = gebdatum;
		this.einstdatum = einstdatum;
		this.geschlecht = geschlecht;
		this.gmodell = gmodell;

	}

	// Getter/Setter

	public String getVorname() {
		return vorname;
	}

	public Geschlecht getGeschlecht() {
		return geschlecht;
	}

	public void setGeschlecht(Geschlecht geschlecht) {
		this.geschlecht = geschlecht;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getPersnr() {
		return persnr;
	}

	public LocalDate getGebdatum() {
		return gebdatum;
	}

	public LocalDate getEinstdatum() {
		return einstdatum;
	}

	public Gehaltsmodell getGmodell() {
		return gmodell;
	}

	public void setGmodell(Gehaltsmodell gehalt) {
		this.gmodell = gehalt;
	}

	// �berschriebene Standardmethoden

	@Override
	public String toString() {
		return "Mitarbeiter [persnr=" + persnr + ", vorname=" + vorname + ", nachname=" + nachname + ", gebdatum="
				+ gebdatum + ", einstdatum=" + einstdatum + ", geschlecht=" + geschlecht + ", gehalt=" + gmodell + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((einstdatum == null) ? 0 : einstdatum.hashCode());
		result = prime * result + ((gebdatum == null) ? 0 : gebdatum.hashCode());
		result = prime * result + ((gmodell == null) ? 0 : gmodell.hashCode());
		result = prime * result + ((geschlecht == null) ? 0 : geschlecht.hashCode());
		result = prime * result + ((nachname == null) ? 0 : nachname.hashCode());
		result = prime * result + ((persnr == null) ? 0 : persnr.hashCode());
		result = prime * result + ((vorname == null) ? 0 : vorname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mitarbeiter other = (Mitarbeiter) obj;
		if (einstdatum == null) {
			if (other.einstdatum != null)
				return false;
		} else if (!einstdatum.equals(other.einstdatum))
			return false;
		if (gebdatum == null) {
			if (other.gebdatum != null)
				return false;
		} else if (!gebdatum.equals(other.gebdatum))
			return false;
		if (gmodell == null) {
			if (other.gmodell != null)
				return false;
		} else if (!gmodell.equals(other.gmodell))
			return false;
		if (geschlecht != other.geschlecht)
			return false;
		if (nachname == null) {
			if (other.nachname != null)
				return false;
		} else if (!nachname.equals(other.nachname))
			return false;
		if (persnr == null) {
			if (other.persnr != null)
				return false;
		} else if (!persnr.equals(other.persnr))
			return false;
		if (vorname == null) {
			if (other.vorname != null)
				return false;
		} else if (!vorname.equals(other.vorname))
			return false;
		return true;
	}

	// Interface-Methoden umsetzen

	@Override
	public int compareTo(Mitarbeiter m) {
		return this.gmodell.getGehalt().compareTo(m.gmodell.getGehalt());
	}

}
