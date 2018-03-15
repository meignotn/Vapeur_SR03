package Servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import Ressources.Game;
import Ressources.SFactory;
import Ressources.User;

/**
 * Servlet implementation class Buy
 */
@WebServlet("/PurchaseSelection")
public class PurchaseSelection extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PurchaseSelection() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		Session session = SFactory.getSession();
		if(user != null ){
			List<Game> selection = (List<Game>)request.getSession().getAttribute("selection");
			if (selection==null)
				return;
			session.refresh(user);
			int sum = 0;
			for(Game g : selection)
				sum+=g.getPrice();
			if(user.getWallet() >= sum){
				for(Game g : selection)
					user.buyGame(g, session);
				request.getSession().removeAttribute("selection");
			}
			
			session.close();
		}
	}

}
