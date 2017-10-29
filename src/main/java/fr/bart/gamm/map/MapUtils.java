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
			String addressNormalize = StringUtil.Normalize(adresse).replaceAll("\\s+","+");
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
		if(p1 != null && p1.getElement1() != null && p1.getElement2() != null && p2 != null && p2.getElement1() != null && p2.getElement2() != null) {
			return distanceInKmBetweenEarthCoordinate(p1.getElement1(), p2.getElement1(), p1.getElement2(), p2.getElement2(),0,0);
		} else {
			return null;
		}		
	}
	
	private static double degreesToRadians(double degrees) {
		return degrees * Math.PI / 180;
	}
	
	public static double distanceInKmBetweenEarthCoordinate(double lat1, double lat2, double lon1, double lon2, double el1, double el2) {

	    final int R = 6371; // Radius of the earth

	    double latDistance = Math.toRadians(lat2 - lat1);
	    double lonDistance = Math.toRadians(lon2 - lon1);
	    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c * 1000; // convert to meters

	    double height = el1 - el2;

	    distance = Math.pow(distance, 2) + Math.pow(height, 2);

	    return Math.sqrt(distance);
	}

	private static double distanceInKmBetweenEarthCoordinates(double lat1, double lon1, double lat2, double lon2) {
		int earthRadiusKm = 6371;
		double dLat = degreesToRadians(lat2-lat1);
		double dLon = degreesToRadians(lon2-lon1);

		lat1 = degreesToRadians(lat1);
		lat2 = degreesToRadians(lat2);

		double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
		Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2); 
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		return earthRadiusKm * c;
	}
	
}
