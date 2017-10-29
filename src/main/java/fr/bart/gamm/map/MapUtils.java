package fr.bart.gamm.map;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import fr.bart.gamm.model.Magasin;
import fr.bart.gamm.util.Couple;
import fr.bart.gamm.util.HttpUtils;
import fr.bart.gamm.util.StringUtil;
import fr.bart.gamm.util.TypeEloignement;

public class MapUtils {

	public static Map<Integer, Integer> getDistanceFromAdresse(List<Magasin> magasinList, String adresse, TypeEloignement typeEloignement) {
		
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		
		return map;
	}
	
	public static boolean AdresseExiste(String adresse) {
		boolean result = false;
		if(adresse != null && !"".equals(adresse)) {
			String addressNormalize = StringUtil.Normalize(adresse).replaceAll("\\s+","+");;
			String httpResult = HttpUtils.executePost("https://maps.googleapis.com/maps/api/geocode/json", "address=" + addressNormalize + "&key=AIzaSyC3yymmxg6KgmbSsetDC8feIDZvF-aoXhM");
			JSONObject jsonObj = new JSONObject(httpResult);
			if(jsonObj.get("status") != null && jsonObj.get("status").equals("OK")) {
				result = true;
			}
		}
		return result;
	}
	
	public static boolean AdresseExiste(Magasin magasin) {
		
		boolean result = false;
		if(magasin != null) {
			result = AdresseExiste(magasin.getAdresse());
		}			
		return result;
		
	}
	
	public static Couple<Float, Float> getLatLong(String numero, String rue, String codePostal, String ville) {
		
		if(numero == null) numero = "";
		if(rue == null) rue = "";
		if(codePostal == null) codePostal = "";
		if(ville == null) ville = "";		
		
		String normalizeNumero = StringUtil.Normalize(numero);
		String normalizeRue = StringUtil.Normalize(rue);
		String normalizeCodePostal = StringUtil.Normalize(codePostal);
		String normalizeVille = StringUtil.Normalize(ville);
		String address = normalizeNumero + " " + normalizeRue + " " + normalizeCodePostal + " " + normalizeVille;
		return getLatLong(address);
		
	}
	
	public static Couple<Float, Float> getLatLong(String adresse) {
		if(adresse == null) {
			adresse = "";
		}
		
		String normalizeAddress = StringUtil.Normalize(adresse).replaceAll("\\s+","+");				
		String url = "https://maps.googleapis.com/maps/api/geocode/json";
		String parameters = "address=" + normalizeAddress + "&key=AIzaSyC3yymmxg6KgmbSsetDC8feIDZvF-aoXhM";
		
		String httpResult = HttpUtils.executePost(url, parameters);
		JSONObject jsonObj = new JSONObject(httpResult);
		
		if(jsonObj.has("results") && jsonObj.getJSONArray("results").length() > 0  && jsonObj.getJSONArray("results").getJSONObject(0).has("geometry") && jsonObj.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").has("location")) {
			return new Couple<Float, Float>(jsonObj.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getFloat("lat"), jsonObj.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getFloat("lng"));
		} else {
			return null;
		}
		
	}
	
	public static Couple<Integer, Integer> distanceEntreAdresseByGoogleApi(String adresseDepart, String adresseDestination) {
		
		Couple<Integer, Integer> result = new Couple<Integer, Integer>();
		if(adresseDepart != null && adresseDestination != null) {
			
			String adresseDepartNormalize = StringUtil.Normalize(adresseDepart).replaceAll("\\s+","+");;
			String adresseArriveNormalize = StringUtil.Normalize(adresseDestination).replaceAll("\\s+","+");;
			
			String httpResult = HttpUtils.executePost("https://maps.googleapis.com/maps/api/distancematrix/json", "units=metric&origins=" + adresseDepartNormalize + "&destinations=" + adresseArriveNormalize + "&key=AIzaSyC3yymmxg6KgmbSsetDC8feIDZvF-aoXhM");
			JSONObject jsonObj = new JSONObject(httpResult);
			if(jsonObj.has("rows") && jsonObj.getJSONArray("rows").length() > 0 && 
					jsonObj.getJSONArray("rows").getJSONObject(0).has("elements") && 
					jsonObj.getJSONArray("rows").getJSONObject(0).getJSONArray("elements").length() > 0 &&
					jsonObj.getJSONArray("rows").getJSONObject(0).getJSONArray("elements").getJSONObject(0).has("distance") &&
					jsonObj.getJSONArray("rows").getJSONObject(0).getJSONArray("elements").getJSONObject(0).has("duration")) {
				result.setElement1(jsonObj.getJSONArray("rows").getJSONObject(0).getJSONArray("elements").getJSONObject(0).getJSONObject("distance").getInt("value"));
				result.setElement2(jsonObj.getJSONArray("rows").getJSONObject(0).getJSONArray("elements").getJSONObject(0).getJSONObject("duration").getInt("value"));
			}
		}		
		return result;
		
	}
	
	public static Double distanceEntreAdresse(Couple<Float, Float> p1, Couple<Float, Float> p2) {
		//pythagore ta mere MOTHER FUCKER !!!
		if(p1 != null && p1.getElement1() != null && p1.getElement2() != null && p2 != null && p2.getElement1() != null && p2.getElement2() != null) {
			float a = Math.abs(p1.getElement1() - p2.getElement1());
			float b = Math.abs(p1.getElement2() - p2.getElement2());
			return Math.sqrt(a+b);
		} else {
			return null;
		}		
	}
	
}
