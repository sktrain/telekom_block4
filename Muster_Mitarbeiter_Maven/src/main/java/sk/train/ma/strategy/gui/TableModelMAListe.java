package sk.train.ma.strategy.gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import sk.train.strategy.model.Mitarbeiter;

public class TableModelMAListe extends AbstractTableModel{
	
	private List<Mitarbeiter> mlist;
	
	public TableModelMAListe(List<Mitarbeiter> mlist) {
		super();
		this.mlist = mlist;
	}	
	
	@Override
	public int getColumnCount() {
		return 7;
	}

	@Override
	public int getRowCount() {
		return mlist.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Mitarbeiter m = mlist.get(rowIndex);
		if (columnIndex == 0) {
			return m.getPersnr();
		}
		if (columnIndex == 1) {
			return m.getVorname();
		}
		if (columnIndex == 2) {
			return m.getNachname();
		}
		if (columnIndex == 3) {
			return m.getGebdatum();
		}
		if (columnIndex == 4) {
			return m.getEinstdatum();
		}
		if (columnIndex == 5) {
			return m.getGeschlecht();
		}
		if (columnIndex == 6) {
			return m.getGmodell().getGehalt();
		}
        return null;
	}
	
	@Override
	public String getColumnName(int column) {
		String[] columns = {"Persnr", "Vorname", "Nachname", "Geb-Datum", 
                "Einst.-Datum", "Geschlecht", "Gehalt"};
		return columns[column];
	}

	//Nachnamen werden setzbar
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 2)
		return true;
		return false;
	}

	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (columnIndex == 2) {
			Mitarbeiter m = mlist.get(rowIndex);
			m.setNachname(aValue.toString());
			System.out.println(m);
		}
			
	}

	//Benachrichtigung des Views
	@Override
	public void fireTableCellUpdated(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		mlist.get(2).setVorname("Hugo");
		super.fireTableCellUpdated(2, 1);
	}
	


}
