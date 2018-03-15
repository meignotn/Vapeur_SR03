package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import Ressources.Game;
import Ressources.SFactory;
import Ressources.User;

/**
 * Servlet implementation class Buy
 */
@WebServlet("/Selection")
public class Selection extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Selection() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		PrintWriter out = response.getWriter();
		Session session = SFactory.getSession();
		if(user != null){
			session.refresh(user);
			List<Game> selection = (List<Game>)request.getSession().getAttribute("selection");
			if (selection!=null){
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
				out.println(objectMapper.writeValueAsString(selection));
			}
		}
		session.close();
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		int gameID = Integer.parseInt(request.getParameter("game"));
		Session session = SFactory.getSession();
		Game game = session.get(Game.class, gameID);
		session.close();
		if(user != null && game!=null){
			List<Game> selection = (List<Game>)request.getSession().getAttribute("selection");
			if (selection==null)
				selection = new ArrayList<Game>();
			selection.add(game);
			request.getSession().setAttribute("selection", selection);
		}
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		Integer gameID=null;
		if (request.getParameter("game")!=null)
			gameID = Integer.parseInt(request.getParameter("game"));
		
		if(user!=null){
			List<Game> selection = (List<Game>)request.getSession().getAttribute("selection");
			if( gameID!=null && selection != null){
				for(Game g:selection){
					if(g.getId()==gameID){
						selection.remove(g);
						break;
					}
				}
				request.getSession().setAttribute("selection", selection);
			}else{
				request.getSession().removeAttribute("selection");
			}
			
		}
	}

}
