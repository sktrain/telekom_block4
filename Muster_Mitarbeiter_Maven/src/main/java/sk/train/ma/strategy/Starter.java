package sk.train.ma.strategy;

import java.util.Comparator;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import sk.train.ma.strategy.gui.TableModelMAListe;
import sk.train.ma.strategy.verwaltung.MitarbeiterVerwaltung;
import sk.train.ma.strategy.verwaltung.MitarbeiterVerwaltungIf;



public class Starter {

	public static void main(String[] args) {
		
		//Mitarbeiterverwaltung instantiieren
		MitarbeiterVerwaltungIf mv = new MitarbeiterVerwaltung();
		
		//TableModel bereitstellen
		TableModelMAListe model = new TableModelMAListe(mv.getMlist(Comparator.naturalOrder()));
		

		JFrame f = new JFrame();
		JTable mytable = new JTable(model);
		JScrollPane p = new JScrollPane(mytable);
		f.add(p);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.fireTableCellUpdated(0, 1);



	}

}
