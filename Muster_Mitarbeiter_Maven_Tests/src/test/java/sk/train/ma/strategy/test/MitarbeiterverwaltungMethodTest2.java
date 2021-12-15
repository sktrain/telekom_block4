package sk.train.ma.strategy.test;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sk.train.ma.strategy.util.KarrersException;
import sk.train.ma.strategy.verwaltung.MitarbeiterVerwaltung;
import sk.train.strategy.model.FixGehaltModell;
import sk.train.strategy.model.Geschlecht;
import sk.train.strategy.model.Mitarbeiter;

class MitarbeiterverwaltungMethodTest2 {

    private MitarbeiterVerwaltung mv;
    
	@BeforeEach
	void setUp() throws Exception {
		mv = new MitarbeiterVerwaltung();
	}

	
	@Test
	final void testGetGehaltssumme() {
		BigDecimal sum = mv.getGehaltssumme();
		assertNotNull(sum, "Gehaltssumme sollte nicht null sein");
		assertTrue(sum.compareTo(BigDecimal.ZERO)>= 0, "Gehaltssumme sollte nicht negativ sein");
		//Gehaltssumme mal selbst berechnen
		List<Mitarbeiter> mlist =  mv.getMlist(Comparator.naturalOrder());
		BigDecimal result = BigDecimal.ZERO;
		for (Mitarbeiter m : mlist) {
			result = result.add(m.getGmodell().getGehalt());
		}
		assertEquals(result, sum, "Gehaltssumme wird nicht korrekt berechnet");
	}
	
	@Test //Whitebox-Test unter Nutzung der getMap-Methode
	final void testGetGehaltssummeMap() {
		TreeMap<String, Mitarbeiter> map = mv.getMap();
		BigDecimal result = map.values().stream()
		            .map(mitarbeiter -> mitarbeiter.getGmodell().getGehalt())
		            .reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal sum = mv.getGehaltssumme();
		assertEquals(result, sum, "Gehaltssumme wird nicht korrekt berechnet");
	}
	
	@Test
	final void testGetMlistWithComparator() {
		List<Mitarbeiter> mlist =  mv.getMlist(Comparator.naturalOrder());
		assertNotNull(mlist, "Liste sollte nicht null sein");
		Comparator<Mitarbeiter> cgebdatum = (Mitarbeiter m1, Mitarbeiter m2) -> {
			return m1.getGebdatum().compareTo(m2.getGebdatum());
		};
		Comparator<Mitarbeiter> cgehalt = (Mitarbeiter m1, Mitarbeiter m2) -> {
			return m1.getGmodell().getGehalt().compareTo(m2.getGmodell().getGehalt());
		};
		mlist.sort(cgebdatum.thenComparing(cgehalt));
		//siehe Doku zu equals bei Listen und wir brauchen natürlich eindeutige Sortierung
		assertEquals(mlist, mv.getMlist(cgebdatum.thenComparing(cgehalt)), "es wird nicht korrekt sortiert");		
	}

	@Test //hier sollten wir das Design nochmal überlegen
	final void testGetMlistWithNull() {
		//aus der Doku:
		//If the specified comparator is null then all elements in this list must implement the Comparable interface
		//and the elements' natural ordering should be used. 
		assertDoesNotThrow(() ->  mv.getMlist(null), "autsch, es wird Exception geworfen");
		
	
	}
	

	@Test
	final void testAddAndGetMitarbeiter() {
		int size = mv.getMlist(Comparator.naturalOrder()).size();
		Mitarbeiter neu = new Mitarbeiter(200, "Max", "Meier", 
                LocalDate.of(1990, 1, 1), 
                LocalDate.of(2010, 1, 1),
                Geschlecht.M,
                new FixGehaltModell(new BigDecimal(5000)));
		assertDoesNotThrow(() -> mv.addMitarbeiter(neu), "Mitarbeiter darf noch nicht exitieren");
		assertEquals(size + 1, mv.getMlist(Comparator.naturalOrder()).size(), "kein Mitarbeiter hinzugefuegt");
		assertEquals(neu, mv.getMitarbeiter(neu.getPersnr()), "es sollte schon der gleiche Mitarbeiter sein");
	}
	

	@Test
	final void testDelMitarbeiterMitarbeiter() {
		List<Mitarbeiter> mlist = mv.getMlist(Comparator.naturalOrder());
		int size = mlist.size();
		Mitarbeiter first = mlist.get(0);
		mv.delMitarbeiter(first);
		assertEquals(size-1, mv.getMlist(Comparator.naturalOrder()).size(), "Liste sollte schrumpfen");
		// nur mal zur Probe
		assertEquals(mv.getMap().size(), mv.getMlist(Comparator.naturalOrder()).size(), "Map und Liste sollten gleiche Size haben");
	}

	@Test
	final void testDelMitarbeiterString() {
		List<Mitarbeiter> mlist = mv.getMlist(Comparator.naturalOrder());
		int size = mlist.size();
		Mitarbeiter first = mlist.get(0);
		mv.delMitarbeiter(first.getPersnr());
		assertEquals(size-1, mv.getMlist(Comparator.naturalOrder()).size(), "Liste sollte schrumpfen");
	}

	@Test  //throw new KarrersException("ungueltige Personalnummer");
	final void testGetMitarbeiterException() {
		Exception e = assertThrows(KarrersException.class, () -> mv.getMitarbeiter("teststring"), "autsch, keine Exception");
		assertEquals("ungueltige Personalnummer", e.getMessage());
	}
	
	@Test  //throw new KarrersException("Mitarbeiter existiert nicht");
	final void testDelMitarbeiterException() {
		Exception e = assertThrows(KarrersException.class, () -> mv.delMitarbeiter("teststring"), "autsch, keine Exception");
		assertEquals("Mitarbeiter existiert nicht", e.getMessage());
		Mitarbeiter neu = new Mitarbeiter(5000, "Max", "Meier",   //PO5000 gibt es nicht
                LocalDate.of(1990, 1, 1), 
                LocalDate.of(2010, 1, 1),
                Geschlecht.M,
                new FixGehaltModell(new BigDecimal(5000)));
		e = assertThrows(KarrersException.class, () -> mv.delMitarbeiter(neu), "autsch, keine Exception");
		assertEquals("Mitarbeiter existiert nicht", e.getMessage());
		
		
	}

//	@Test
//	final void testWriteToFile() {
//		fail("Not yet implemented"); // TODO
//	}

}
