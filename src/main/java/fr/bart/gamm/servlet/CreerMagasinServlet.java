package fr.bart.gamm.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.bart.gamm.dao.MagasinDao;
import fr.bart.gamm.model.Magasin;

@WebServlet( urlPatterns = { "/creerMagasin" } )
public class CreerMagasinServlet extends HttpServlet {
	
	public static final String VUE = "/WEB-INF/jsp/magasin.jsp";
	
	private MagasinDao magasinDao = new MagasinDao();

	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
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
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {


        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
	
}
