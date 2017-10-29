package fr.bart.gamm.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.bart.gamm.dao.MagasinDao;
import fr.bart.gamm.map.MapUtils;
import fr.bart.gamm.model.Magasin;
import fr.bart.gamm.util.Action;
import fr.bart.gamm.util.Error;
import fr.bart.gamm.util.Couple;

@WebServlet( urlPatterns = { "/index" } )
public class IndexServlet extends HttpServlet {
	
	public static final String VUE_INDEX = "/WEB-INF/jsp/index.jsp";
	public static final String VUE_DISTANCE = "/WEB-INF/jsp/distance.jsp";
	
	private MagasinDao magasinController = new MagasinDao();

	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Affichage de la page d'inscription */
		
		Action action = null;
		if(request.getParameter("action") != null && request.getParameter("action") instanceof String) {
			action = Action.getByLabel((String)request.getParameter("action"));
		}
		
		if(action != null) {
			switch (action) {
				case SEARCH_NEAR_STORES:
					if(request.getParameter("address") != null && request.getParameter("address") instanceof String) {
						String adresse = (String)request.getParameter("address");
						if(MapUtils.AdresseExiste(adresse)) {
							Map<Magasin, Couple<Integer, Integer>> magasins = listeDesMagasinsProches(adresse, 4);
							request.setAttribute("magasinsAvecDistance", magasins);
							request.setAttribute("adresseRenseignee", adresse);
							this.getServletContext().getRequestDispatcher( VUE_DISTANCE ).forward( request, response );								
						} else { //adresse renseignee n'existe pas
							request.setAttribute("error", Error.ADDRESS_DOES_NOT_EXIST);
							redirectToIndex(request, response);
						}
					} else {
						redirectToIndex(request, response);
					}
					break;
				default:
					redirectToIndex(request, response);
					break;
			}
		} else {
			redirectToIndex(request, response);
		}
		
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {


        this.getServletContext().getRequestDispatcher( VUE_INDEX ).forward( request, response );
    }
	
    public void redirectToIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	List<Magasin> magasinList = magasinController.findAll();		
		if(magasinList != null) {
			request.setAttribute("magasins", magasinList);
		}		
		this.getServletContext().getRequestDispatcher( VUE_INDEX ).forward( request, response );		
    }
    
    private Map<Magasin, Couple<Integer, Integer>> listeDesMagasinsProches(String adresse, int nbMagasin) {
    	Map<Double, Magasin> mapVolOiseau = new HashMap<Double, Magasin>();
    	Map<Magasin, Couple<Integer, Integer>> mapResult = new HashMap<Magasin, Couple<Integer, Integer>>();
    	List<Magasin> magasinList = magasinController.findAll();
    	
    	Couple<Float, Float> latLongAdresse = MapUtils.getLatLong(adresse);
    	if(latLongAdresse != null && latLongAdresse.getElement1() != null && latLongAdresse.getElement2() != null && magasinList != null) {    		
    		for(Magasin magasin : magasinList) {
    			if(magasin.getLatitude() != null && magasin.getLongitude() != null) {
    				mapVolOiseau.put(MapUtils.distanceEntreAdresse(latLongAdresse, new Couple<Float, Float>(magasin.getLatitude(), magasin.getLongitude())), magasin);
    			}
    		}    		
    	}
    	    	
    	Map<Double, Magasin> mapVolOiseauSorted = new TreeMap<Double, Magasin>(mapVolOiseau);
    	
    	for(Map.Entry<Double, Magasin> e : mapVolOiseauSorted.entrySet()) {
    		System.out.println("Distance avec " + e.getValue().getAdresse() + " : " + e.getKey());
    	}
    	
    	int i = 0;
    	for(Map.Entry<Double, Magasin> e : mapVolOiseauSorted.entrySet()) {
    		if(i < nbMagasin) {
    			mapResult.put(e.getValue(), MapUtils.distanceEntreAdresseByGoogleApi(adresse, e.getValue().getAdresse())) ;
    		} else {
    			break;
    		}
    		i++;
    	}    	
	
    	return mapResult;
    }
}
