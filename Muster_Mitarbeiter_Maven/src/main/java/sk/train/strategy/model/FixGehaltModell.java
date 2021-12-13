package sk.train.strategy.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FixGehaltModell implements Gehaltsmodell{
    
	//Datenattribute
	private BigDecimal gehalt;
	
	//Konstruktoren
	public FixGehaltModell(BigDecimal gehalt) {
		super();
		this.gehalt = gehalt;
	}


	//Getter/Setter
	public BigDecimal getGehalt() {
		return gehalt;
	}

	public void setGehalt(BigDecimal gehalt) {
		this.gehalt = gehalt;
	}

	@Override
	public String toString() {
		return "FixGehaltModell [gehalt=" + gehalt + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gehalt == null) ? 0 : gehalt.hashCode());
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
		FixGehaltModell other = (FixGehaltModell) obj;
		if (gehalt == null) {
			if (other.gehalt != null)
				return false;
		} else if (!gehalt.equals(other.gehalt))
			return false;
		return true;
	}
	
	
	
}
