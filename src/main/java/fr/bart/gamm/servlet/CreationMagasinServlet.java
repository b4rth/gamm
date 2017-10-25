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

@WebServlet( urlPatterns = { "/creationMagasin" } )
public class CreationMagasinServlet extends HttpServlet {
	
	public static final String VUE = "/WEB-INF/jsp/creationMagasin.jsp";
	
	private MagasinController magasinController = new MagasinController();

	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {


        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
	
}