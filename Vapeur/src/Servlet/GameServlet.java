package Servlet;

import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.fasterxml.jackson.core.filter.FilteringGeneratorDelegate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import Ressources.Game;
import Ressources.SFactory;
import Ressources.User;

/**
 * Servlet implementation class Buy
 */
@WebServlet("/game")
public class GameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public  GameServlet() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		Session session = SFactory.getSession();
		int gameID=-1;
		String gameTitle=null;
		int gameType =-1;
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		System.out.println(request.getParameter("game"));
		if(request.getParameter("game") != null)
			gameID = Integer.parseInt(request.getParameter("game"));
		if(request.getParameter("gameTitle") != null)
			gameTitle = escapeHtml4(request.getParameter("gameTitle"));
		if(request.getParameter("gameType") != null)
			gameType = Integer.parseInt(request.getParameter("gameType"));
		if(gameID != -1){
			out.print(objectMapper.writeValueAsString(session.get(Game.class, gameID)));
		}else if(gameTitle != null && gameType != -1){
			out.print(objectMapper.writeValueAsString(Game.search(gameTitle, gameType,session)));
		}else if(gameTitle !=null){
				out.print(objectMapper.writeValueAsString(Game.search(gameTitle,session)));
		}else
			out.print(objectMapper.writeValueAsString(Game.findAllGame(session)));
		session.close();
	}
}