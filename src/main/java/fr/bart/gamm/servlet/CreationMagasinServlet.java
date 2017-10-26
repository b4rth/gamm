package fr.bart.gamm.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet( urlPatterns = { "/creationMagasin" } )
public class CreationMagasinServlet extends HttpServlet {
	
	public static final String VUE = "/WEB-INF/jsp/creationMagasin.jsp";
	

	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {


        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
	
}
