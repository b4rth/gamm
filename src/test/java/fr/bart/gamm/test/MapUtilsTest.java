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
	public void getLatLongTest() {
		Couple<Float, Float> angersCoor = MapUtils.getLatLong("8", "rue de la barre", "49000", "Angers");
		if(angersCoor != null) {
			System.out.println(angersCoor.getElement1());
			System.out.println(angersCoor.getElement2());
		}
		
		Couple<Float, Float> angersCoor2 = MapUtils.getLatLong("8 rue de la barre 49000 Angers");
		if(angersCoor2 != null) {
			System.out.println(angersCoor2.getElement1());
			System.out.println(angersCoor2.getElement2());
		}		
	}
	
	@Test
	public void distanceEntreAdresseByGoogleApiTest() {
		Couple<Integer, Integer> distance = MapUtils.distanceEntreAdresseByGoogleApi("8 rue de la barre angers", "la pironnière ballée");
		System.out.println("Distance en m : " + distance.getElement1());
		System.out.println("Distance en temps (s) : " + distance.getElement2());
	}
}
