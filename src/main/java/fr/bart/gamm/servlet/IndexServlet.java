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
import fr.bart.gamm.util.Couple;

@WebServlet( urlPatterns = { "/index" } )
public class IndexServlet extends HttpServlet {
	
	public static final String VUE = "/WEB-INF/jsp/index.jsp";
	
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
						listeDesMagasinsProches(adresse, 5);
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


        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
	
    public void redirectToIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	List<Magasin> magasinList = magasinController.findAll();		
		if(magasinList != null) {
			request.setAttribute("magasins", magasinList);
		}		
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );		
    }
    
    private Map<Couple<Integer, Integer>, Magasin> listeDesMagasinsProches(String adresse, int nbMagasin) {
    	Map<Double, Magasin> mapVolOiseau = new HashMap<Double, Magasin>();
    	Map<Couple<Integer, Integer>, Magasin> mapResult = new HashMap<Couple<Integer, Integer>, Magasin>();
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
    	int i = 0;
    	for(Map.Entry<Double, Magasin> e : mapVolOiseauSorted.entrySet()) {
    		if(i < nbMagasin) {
    			mapResult.put(MapUtils.distanceEntreAdresseByGoogleApi(adresse, e.getValue().getAdresse()), e.getValue()) ;
    		} else {
    			break;
    		}
    	}    	
	
    	return mapResult;
    }
}
