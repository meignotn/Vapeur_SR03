package Servlet;

import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import Ressources.User;
import Ressources.Game;
import Ressources.Hasher;
import Ressources.Library;
import Ressources.Review;
import Ressources.Type;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			String mail = escapeHtml4(request.getParameter("mail"));
			String password = escapeHtml4(request.getParameter("password"));
			String nickname = escapeHtml4(request.getParameter("nickname"));
			String name = escapeHtml4(request.getParameter("name"));
			Hasher hasher = new Hasher("MD5");
			String hashed_password = Hasher.hashing(hasher, password);
			SessionFactory factory = null;
			User user = null;
			try{
		         factory = new Configuration().configure()
		        		 .buildSessionFactory();
			}catch (Throwable ex) { 
		         System.err.println("Failed to create sessionFactory object." + ex);
		         throw new ExceptionInInitializerError(ex); 
			}
			Session session = factory.openSession();
		    Transaction tx = null;
		    Integer Userid = null;
		    try{
		    	tx = session.beginTransaction();
		    	user = new User(nickname,name,mail,hashed_password);
		        session.save(user);

		         tx.commit();
		      }catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      }finally {
		         session.close(); 
		      }
			PrintWriter out = response.getWriter();
			if (user != null) {
				request.getSession().setAttribute("user", user);
           
				out.println("<h1>Compte créé</h1>");
			}else {
				out.println("<h1>Compté NON créé</h1>");
				//request.getRequestDispatcher("/login.jsp").forward(request, response);
			}
		}
        catch (Exception e)
		{
			System.out.println(e);
		}
		
	}

}
