package Servlet;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import Ressources.Hasher;
import Ressources.Library;
import Ressources.SFactory;
import Ressources.User;

/**
 * Servlet implementation class Auth
 */
@WebServlet("/user/library")
public class LibraryServlet extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 2L;
       
    public LibraryServlet() {
        super();
    }
	@Override
	public void init() throws ServletException {
		super.init();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		PrintWriter out = response.getWriter();
		Session session = SFactory.getSession();
		if (user!=null){
			session.refresh(user);
			ObjectMapper objectMapper = new ObjectMapper();
	    	objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
			out.println(objectMapper.writeValueAsString(user.getLibrary()));
			session.close();
		}
    }

}
