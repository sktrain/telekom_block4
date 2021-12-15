package sk.train.strategy.model;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Properties;

public class GehaltsmodellFactory {
	
//	public static Gehaltsmodell getGehaltsmodell(String type) {
//		if (type.equals("A"))
//			return new ArbeiterModell(new BigDecimal((int)(Math.random()*100)),	new BigDecimal(120));
//		else return new FixGehaltModell(new BigDecimal((int)(Math.random()*10000))); }
	
	static Properties props;
	//static Initializer
	static {
		props = new Properties();
		try {
			// props.load(new FileInputStream("../Factory.properties"));
			// alternativ �ber den ClassPath laden!
			props.load(GehaltsmodellFactory.class.getResourceAsStream("/factory.properties"));
			props.list(System.out);
		} catch (IOException e) {
			System.err.println("laden der Properties fehlgeschlagen");
		}
	}
	
	public static Gehaltsmodell getGehaltsmodell(String type) {
		
		if (type.equals("A"))
			return new ArbeiterModell(new BigDecimal(props.get("astdlohn").toString()),
					                  new BigDecimal(props.get("astdzahl").toString()));
		else return new FixGehaltModell(new BigDecimal(props.get("fgehalt").toString())); }
	 

}
