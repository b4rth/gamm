package fr.bart.gamm.map;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import fr.bart.gamm.model.Magasin;
import fr.bart.gamm.util.Couple;
import fr.bart.gamm.util.HttpUtils;
import fr.bart.gamm.util.TypeEloignement;

public class MapUtils {

	public static Map<Integer, Integer> getDistanceFromAdresse(List<Magasin> magasinList, String adresse, TypeEloignement typeEloignement) {
		
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		
		return map;
	}
	
	public static boolean AdresseExiste(Magasin magasin) {
		
		boolean result = false;
		if(magasin != null) {
			String adresse = magasin.getAdresseForURL();
			String httpResult = HttpUtils.executePost("https://maps.googleapis.com/maps/api/geocode/json", "address=" + adresse + "&key=AIzaSyC3yymmxg6KgmbSsetDC8feIDZvF-aoXhM");
			JSONObject jsonObj = new JSONObject(httpResult);
			if(jsonObj.get("status") != null && jsonObj.get("status").equals("OK")) {
				return true;
			}
		}			
		return result;
		
	}
	
	public static Couple<Integer, Integer> distanceEntreAdresse(String adresseDepart, String adresseDestination) {
		
		Couple<Integer, Integer> result = new Couple<Integer, Integer>();
		if(adresseDepart != null && adresseDestination != null) {
			
			String httpResult = HttpUtils.executePost("https://maps.googleapis.com/maps/api/distancematrix/json", "units=imperial&origins=" + adresseDepart + "&destinations=" + adresseDestination + "&key=AIzaSyC3yymmxg6KgmbSsetDC8feIDZvF-aoXhM");
			JSONObject jsonObj = new JSONObject(httpResult);
			if(jsonObj.get("rows") != null) {
				
			}
		}
		
		return result;
		
	}
	
}
