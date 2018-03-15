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
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import Ressources.Game;
import Ressources.Review;
import Ressources.SFactory;
import Ressources.User;

/**
 * Servlet implementation class Auth
 */
@WebServlet("/user/friend_request")
public class FriendRequestServlet extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 2L;

	public FriendRequestServlet() {
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
			if (user.getFriend_request() != null) {
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
				out.println(objectMapper.writeValueAsString(user.getFriend_request()));
			}
		}
		session.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		String id = escapeHtml4(request.getParameter("id"));
		Session session = SFactory.getSession();
		if (user != null) {
			session.refresh(user);
			User friend = session.get(User.class, Integer.parseInt(id));
			System.out.println(friend.getNickname());
			user.addFriend(friend, session);
		}
		session.close();

	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		String id = escapeHtml4(request.getParameter("id"));
		PrintWriter out = response.getWriter();
		Session session = SFactory.getSession();
		if (user != null) {
			session.refresh(user);
			User friend = session.get(User.class, Integer.parseInt(id));
			if (user.getFriend_request().contains(friend)) {
				user.rejectFriendRequest(friend, session);
			}

		}
		session.close();
	}

}
