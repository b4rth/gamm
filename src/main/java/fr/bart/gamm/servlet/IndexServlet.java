package fr.bart.gamm.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.bart.gamm.controller.MagasinController;
import fr.bart.gamm.model.Magasin;

@WebServlet( urlPatterns = { "/index" } )
public class IndexServlet extends HttpServlet {
	
	public static final String VUE = "/WEB-INF/jsp/index.jsp";
	
	private MagasinController magasinController = new MagasinController();

	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Affichage de la page d'inscription */
		List<Magasin> magasinList = magasinController.recupererMagasins();
		
		if(magasinList != null && magasinList.size() > 1) {
			request.setAttribute("magasin1", magasinList.get(0));
			request.setAttribute("magasin2", magasinList.get(1));
		}
		
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {


        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
	
}
