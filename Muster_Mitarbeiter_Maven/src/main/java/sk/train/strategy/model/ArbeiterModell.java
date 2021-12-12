package sk.train.strategy.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ArbeiterModell implements Gehaltsmodell {
	
	//Datenattribute
	private BigDecimal stdlohn;
	private BigDecimal stdzahl;	
	
	
	//Konstruktoren
	public ArbeiterModell(BigDecimal stdlohn, BigDecimal stdzahl) {
		super();
		this.stdlohn = stdlohn;
		this.stdzahl = stdzahl;
	}

	//getter/setter
	public BigDecimal getStdlohn() {
		return stdlohn;
	}
	
	public void setStdlohn(BigDecimal stdlohn) {
		this.stdlohn = stdlohn;
	}
	
	public BigDecimal getStdzahl() {
		return stdzahl;
	}
	
	public void setStdzahl(BigDecimal stdzahl) {
		this.stdzahl = stdzahl;
	}

	@Override
	public BigDecimal getGehalt() {
		return stdlohn.multiply(stdzahl);
	}

	@Override
	public String toString() {
		return "ArbeiterModell [stdlohn=" + stdlohn + ", stdzahl=" + stdzahl + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stdlohn == null) ? 0 : stdlohn.hashCode());
		result = prime * result + ((stdzahl == null) ? 0 : stdzahl.hashCode());
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
		ArbeiterModell other = (ArbeiterModell) obj;
		if (stdlohn == null) {
			if (other.stdlohn != null)
				return false;
		} else if (!stdlohn.equals(other.stdlohn))
			return false;
		if (stdzahl == null) {
			if (other.stdzahl != null)
				return false;
		} else if (!stdzahl.equals(other.stdzahl))
			return false;
		return true;
	}
}
