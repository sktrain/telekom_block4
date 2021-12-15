package sk.train.ma.strategy.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import sk.train.ma.strategy.verwaltung.MitarbeiterVerwaltung;

class Mitarbeiterverwaltung_KonstruktorTest {



	//Whitebox-Test unter Nutzung der getMap-Methode
	@Test
	final void testMitarbeiterVerwaltungKonstruktor() {
		MitarbeiterVerwaltung mv = new MitarbeiterVerwaltung();
		assertNotNull(mv, "Konstruktor inkorrekt");
		assertNotNull(mv.getMap(), "Interne Map wurde nicht bereit gestellt");
		assertTrue(mv.getMap().size()>0 , "Map ist leer");
	}

}
