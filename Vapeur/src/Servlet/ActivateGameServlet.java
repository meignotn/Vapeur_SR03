package Servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import Ressources.Game;
import Ressources.SFactory;
import Ressources.User;
import Ressources.Library;

/**
 * Servlet implementation class Auth
 */
@WebServlet("/user/activate_game")
public class ActivateGameServlet extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 2L;

	public ActivateGameServlet() {
		super();
	}

	@Override
	public void init() throws ServletException {
		super.init();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		Session session = SFactory.getSession();
		int libID = -1;
		Library library = null;
		if (user != null) {
			session.refresh(user);
			if (request.getParameter("library") != null)
				libID = Integer.parseInt(request.getParameter("library"));
			if (libID != -1) {
				library = session.get(Library.class, libID);
			}
			if (library != null) {
				if (user.getLibrary().contains(library)) {
					library.activate(session);
				}
			}
		}
		session.close();
	}

}
