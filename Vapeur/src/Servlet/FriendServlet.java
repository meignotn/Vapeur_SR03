package Servlet;

import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import Ressources.SFactory;
import Ressources.User;

/**
 * Servlet implementation class Auth
 */
@WebServlet("/user/friends")
public class FriendServlet extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 2L;

	public FriendServlet() {
		super();
	}

	@Override
	public void init() throws ServletException {
		super.init();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		PrintWriter out = response.getWriter();
		Session session = SFactory.getSession();
		if (user != null) {
			session.refresh(user);
			ObjectMapper objectMapper = new ObjectMapper();
	    	objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
			out.println(objectMapper.writeValueAsString(user.getFriends()));
			session.close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		String id = escapeHtml4(request.getParameter("id"));
		Session session = SFactory.getSession();
		if (user != null) {
			session.refresh(user);
			User friend = session.get(User.class, Integer.parseInt(id));
				if (user.getFriend_request().contains(friend)) {
					user.acceptFriendRequest(friend, session);
				}
		}
		session.close();
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		String id = request.getParameter("id");
		Session session = SFactory.getSession();
		if (user != null) {
			session.refresh(user);
			User friend = session.get(User.class, Integer.parseInt(id));
			if (user.getFriends().contains(friend)) {
				user.removeFriend(friend, session);
			}
		}
		session.close();
	}

}
