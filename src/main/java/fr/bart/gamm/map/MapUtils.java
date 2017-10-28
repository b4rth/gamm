package fr.bart.gamm.map;

import java.text.Normalizer;
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
	
	public static Couple<Float, Float> getLatLong(String numero, String rue, String codePostal, String ville) {
		
		if(numero == null) numero = "";
		if(rue == null) rue = "";
		if(codePostal == null) codePostal = "";
		if(ville == null) ville = "";
		
		
		String normalizeNumero = Normalizer.normalize(numero, Normalizer.Form.NFD).trim().replaceAll("\\s+","+");
		String normalizeRue = Normalizer.normalize(rue, Normalizer.Form.NFD).trim().replaceAll("\\s+","+");
		String normalizeCodePostal = Normalizer.normalize(codePostal, Normalizer.Form.NFD).trim().replaceAll("\\s+","+");
		String normalizeVille = Normalizer.normalize(ville, Normalizer.Form.NFD).trim().replaceAll("\\s+","+");;
		
		String urlAddress = normalizeNumero + "+" + normalizeRue + "+" + normalizeCodePostal + "+" + normalizeVille;
		String url = "https://maps.googleapis.com/maps/api/geocode/json";
		String parameters = "address=" + urlAddress + "&key=AIzaSyC3yymmxg6KgmbSsetDC8feIDZvF-aoXhM";
		
		String httpResult = HttpUtils.executePost(url, parameters);
		JSONObject jsonObj = new JSONObject(httpResult);
		
		if(jsonObj.has("results") && jsonObj.getJSONArray("results").length() > 0  && jsonObj.getJSONArray("results").getJSONObject(0).has("geometry") && jsonObj.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").has("location")) {
			return new Couple<Float, Float>(jsonObj.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getFloat("lat"), jsonObj.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getFloat("lng"));
		} else {
			return null;
		}
		
	}
	
	public static Couple<Integer, Integer> distanceEntreAdresse(String adresseDepart, String adresseDestination) {
		
		Couple<Integer, Integer> result = new Couple<Integer, Integer>();
		if(adresseDepart != null && adresseDestination != null) {
			
			String httpResult = HttpUtils.executePost("https://maps.googleapis.com/maps/api/distancematrix/json", "units=metric&origins=" + adresseDepart + "&destinations=" + adresseDestination + "&key=AIzaSyC3yymmxg6KgmbSsetDC8feIDZvF-aoXhM");
			JSONObject jsonObj = new JSONObject(httpResult);
			if(jsonObj.get("rows") != null) {
				
			}
		}
		
		return result;
		
	}
	
}
