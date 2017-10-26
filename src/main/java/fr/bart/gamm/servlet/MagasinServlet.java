package fr.bart.gamm.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import antlr.StringUtils;
import fr.bart.gamm.dao.MagasinDao;
import fr.bart.gamm.model.Magasin;
import fr.bart.gamm.util.Action;

@WebServlet( urlPatterns = { "/magasin" } )
public class MagasinServlet extends HttpServlet {
	
	public static final String MAGASINS = "magasins";
	
	public static final String VUE = "/WEB-INF/jsp/magasin.jsp";
	
	private MagasinDao magasinDao = new MagasinDao();

	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		
		Action action = null;
		if(request.getParameter("action") != null && request.getParameter("action") instanceof String) {
			action = Action.getByLabel((String)request.getParameter("action"));
		}
		
		if(action != null) {
			switch(action) {
				case CREATE :
					String numero = request.getParameter("numero");
					String rue = request.getParameter("rue");
					String codePostal = request.getParameter("codePostal");
					String ville = request.getParameter("ville");
					
					Integer codePostalInt = null;
					Integer numeroInt = null;
					if(codePostal != null && !"".equals(codePostal)) {
						try {
							codePostalInt = Integer.parseInt(codePostal);
						} catch(Exception e) {
							e.printStackTrace();
						}			
					}
					if(numero != null && !"".equals(numero)) {
						try {
							numeroInt = Integer.parseInt(numero);
						} catch(Exception e) {
							e.printStackTrace();
						}			
					}
					
					Magasin magasin = new Magasin(numeroInt, rue, codePostalInt, ville, null);
					magasinDao.create(magasin);
					
					response.sendRedirect("magasin");
					break;
				case DELETE :
					supprimerMagasin(request);
					retournerMagasins(request);
					this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
					break;					
			}
		} else {
			retournerMagasins(request);
			this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
		}
		
		
        


    }

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {


        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
    
    private void supprimerMagasin(HttpServletRequest request) {
    	if(request.getParameter("id") != null && request.getParameter("id") instanceof String) {
    		String stringId = (String) request.getParameter("id");
    		if(stringId != null && !"".equals(stringId)) {
    			int id = Integer.parseInt(stringId);
    			Magasin magasin = magasinDao.read(id);
    			if(magasin != null) {
    				magasinDao.delete(magasin);    				
    			}
    		}
    	}
    }
    
    private void retournerMagasins(HttpServletRequest request) {
		List<Magasin> magasinList = magasinDao.findAll();		
		if(magasinList != null) {
			request.setAttribute(MAGASINS, magasinList);
		}
    }
   
	
}
