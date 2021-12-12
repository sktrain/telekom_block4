package sk.train.ma.strategy.util;

import java.util.Comparator;

import sk.train.strategy.model.Mitarbeiter;

public class NachnamenComparator implements Comparator<Mitarbeiter> {

	@Override
	public int compare(Mitarbeiter arg0, Mitarbeiter arg1) {
		return arg0.getNachname().compareTo(arg1.getNachname());
	}

}
