package sk.train.ma.strategy.verwaltung;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

import sk.train.ma.strategy.store.MitarbeiterStoreService;
import sk.train.ma.strategy.util.KarrersException;
import sk.train.strategy.model.Mitarbeiter;


public class MitarbeiterVerwaltung implements MitarbeiterVerwaltungIf {

	private TreeMap<String, Mitarbeiter> map = MitarbeiterStoreService.getStoreService();

	public MitarbeiterVerwaltung() { 
		
	}

	@Override
	public BigDecimal getGehaltssumme() {

		BigDecimal result = BigDecimal.ZERO;
		for (Mitarbeiter m : map.values()) {
			result = result.add(m.getGmodell().getGehalt());
		}
		return result;
	}

	// nur zum Test
	public TreeMap<String, Mitarbeiter> getMap() {
		return map;
	}

	@Override
	public List<Mitarbeiter> getMlist(Comparator<Mitarbeiter> comp) {
		ArrayList<Mitarbeiter> alm = new ArrayList<>();
		alm.addAll(map.values());
		alm.sort(comp);
		return alm;
	}

	@Override
	public String addMitarbeiter(Mitarbeiter m) {
		String persnr = m.getPersnr();
		if (map.containsKey(persnr)) {
			throw new KarrersException("Personalnummer existiert schon");
		} else {
			map.put(persnr, m);
		}
		return persnr;
	}

	@Override
	public void delMitarbeiter(Mitarbeiter m) {
		if (map.remove(m.getPersnr()) == null) {
			throw new KarrersException("Mitarbeiter existiert nicht");
		}
	}

	@Override
	public void delMitarbeiter(String persnr) {
		if (map.remove(persnr) == null) {
			throw new KarrersException("Mitarbeiter existiert nicht");
		}
	}

	@Override
	public Mitarbeiter getMitarbeiter(String persnr) {
		Mitarbeiter m = map.get(persnr);
		if (m == null) {
			throw new KarrersException("ungueltige Personalnummer");
		}
		return m;
	}

	/********* I/O-Methoden ******************/
	
	//Textuelle Ausgabe in Datei
	@Override
	public void writeToFile(String fname) {
		try (Writer out = Files.newBufferedWriter(Paths.get(fname), StandardCharsets.ISO_8859_1)) {
			for (Mitarbeiter m : map.values()) {
				out.write(m.toString());
				out.write(System.lineSeparator());
			}
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	

	/********* Standardmethoden **************/

	@Override
	public String toString() {
		// return "MitarbeiterVerwaltung [marray=" + Arrays.toString(marray) + ",
		// mlist=" + mlist + "]";
		// Standard-Ausgabe der Map macht keine Zeilenumbr�che
		// deshalb per Schleife zeilenweise (analog direkter Ausgabeschleife)
		StringBuilder sb = new StringBuilder();
		for (Mitarbeiter m : map.values()) {
			sb.append(m.toString()).append('\n');
		}
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((map == null) ? 0 : map.hashCode());
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
		MitarbeiterVerwaltung other = (MitarbeiterVerwaltung) obj;
		if (map == null) {
			if (other.map != null)
				return false;
		} else if (!map.equals(other.map))
			return false;
		return true;
	}

}
