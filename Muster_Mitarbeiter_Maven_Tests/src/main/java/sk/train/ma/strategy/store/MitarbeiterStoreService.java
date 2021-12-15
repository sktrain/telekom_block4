package sk.train.ma.strategy.store;

import java.time.LocalDate;
import java.util.TreeMap;

import sk.train.strategy.model.Gehaltsmodell;
import sk.train.strategy.model.GehaltsmodellFactory;
import sk.train.strategy.model.Geschlecht;
import sk.train.strategy.model.Mitarbeiter;

public class MitarbeiterStoreService {

	private static TreeMap<String, Mitarbeiter> map;

	public static TreeMap<String, Mitarbeiter> getStoreService() {

		map = new TreeMap<>();

		Mitarbeiter m;
		Gehaltsmodell gehaltsmodell;
		for (int i = 0; i < 100; ++i) {
			if (i % 2 == 0) {
				// Nutzung der GehaltsmodellFactory
				gehaltsmodell = GehaltsmodellFactory.getGehaltsmodell("F");
				m = new Mitarbeiter(i, "Erika", "Musterfrau" + i, LocalDate.of(1976, 1 + (int) (Math.random() * 12), 1),
						LocalDate.of(2000, 1, 1), Geschlecht.W, gehaltsmodell);
				map.put(m.getPersnr(), m);
			} else {
				// Nutzung der GehaltsmodellFactory
				gehaltsmodell = GehaltsmodellFactory.getGehaltsmodell("A");
				m = new Mitarbeiter(i, "Max", "Mustermann" + i, LocalDate.of(1976, 1 + (int) (Math.random() * 12), 1),
						LocalDate.of(2000, 1, 1), Geschlecht.M, gehaltsmodell);
				map.put(m.getPersnr(), m);
			}

		}
		return map;
	}

}
