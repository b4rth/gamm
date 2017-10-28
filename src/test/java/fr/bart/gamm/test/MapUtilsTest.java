package fr.bart.gamm.test;

import org.junit.Test;

import fr.bart.gamm.map.MapUtils;
import fr.bart.gamm.model.Magasin;
import fr.bart.gamm.util.Couple;
import junit.framework.Assert;

public class MapUtilsTest {
	
	@Test
	public void adresseExisteTest() {
		
		Magasin magasin = new Magasin(8, "rue de la barre", 49000, "Angers", null);
		boolean exist = MapUtils.AdresseExiste(magasin);
		Assert.assertEquals(true, exist);
		
		Magasin magasin2 = new Magasin(null, "gkfezoipgàjàzdakgp", null, "fezijoé_çgrhçéààhj", null);
		boolean exist2 = MapUtils.AdresseExiste(magasin2);
		Assert.assertEquals(false, exist2);
		
	}
	
	@Test
	public void distanceEntreAdresse() {
		MapUtils.distanceEntreAdresse("8+rue+de+la+barre+angers", "la+pironniere+ballée");
	}

	
	@Test
	public void getLatLongTest() {
		Couple<Float, Float> angersCoor = MapUtils.getLatLong("8", "rue de la barre", "49000", "Angers");
		if(angersCoor != null) {
			System.out.println(angersCoor.getElement1());
			System.out.println(angersCoor.getElement2());
		}
	}
}
