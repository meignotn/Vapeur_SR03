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

import Ressources.Game;
import Ressources.Review;
import Ressources.SFactory;
import Ressources.User;

/**
 * Servlet implementation class Auth
 */
@WebServlet("/user/reviews")
public class UserReviewServlet extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 2L;

	public UserReviewServlet() {
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
		session.beginTransaction();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		int gameID = -1;
		if (request.getParameter("game") != null)
			gameID = Integer.parseInt(request.getParameter("game"));
		if (gameID != -1) {
			Game g = (Game) session.get(Game.class, gameID);
			out.println(objectMapper.writeValueAsString(g.getReviews()));
		} else if (user != null) {
			session.refresh(user);
			out.println(objectMapper.writeValueAsString(user.getReviews()));
		}
		session.getTransaction().commit();

		session.close();

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		int mark = -1;
		int game = -1;
		String review = escapeHtml4(request.getParameter("review"));
		if (request.getParameter("game") != null)
			game = Integer.parseInt(request.getParameter("game"));
		if (request.getParameter("mark") != null)
			mark = Integer.parseInt(request.getParameter("mark"));
		Session session = SFactory.getSession();
		if (user != null) {
			session.refresh(user);
			Review rev = new Review(user, session.get(Game.class, game), review, mark);
			session.save(rev);
		}
		session.close();
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		Session session = SFactory.getSession();
		int revID = -1;
		Review review = null;
		if (user != null) {
			session.refresh(user);
			if (escapeHtml4(request.getParameter("reviewID")) != null)
				revID = Integer.parseInt(escapeHtml4(request.getParameter("reviewID")));
			if (revID != -1) {
				review = session.get(Review.class, revID);
			}
			if (review != null) {
				if (user.getReviews().contains(review)) {
					if (escapeHtml4(request.getParameter("review")) != null) {
						String new_review = escapeHtml4(request.getParameter("review"));
						review.changeReview(new_review, session);
					}if (escapeHtml4(request.getParameter("mark")) != null) {
						int mark = Integer.parseInt(escapeHtml4(request.getParameter("mark")));
						review.changeMark(mark, session);
					}
				}
			}
		}
		session.close();
	}
}
