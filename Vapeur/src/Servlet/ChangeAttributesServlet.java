package Servlet;

import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;

import java.io.IOException;
import java.io.PrintWriter;

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
import Ressources.SFactory;
import Ressources.User;
import Ressources.Library;

/**
 * Servlet implementation class Auth
 */
@WebServlet("/user/change_attributes")
public class ChangeAttributesServlet extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 2L;

	public ChangeAttributesServlet() {
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
			out.println(objectMapper.writeValueAsString(user));
			session.close();
		}
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		if(user!=null){
			Session session = SFactory.getSession();
			session.refresh(user);
			if (request.getParameter("mail") != null) {
				user.setMail(escapeHtml4(request.getParameter("mail")));
			}
			if (request.getParameter("name") != null) {
				user.setName(escapeHtml4(request.getParameter("name")));
			}
			if (request.getParameter("nickname") != null) {
				user.setNickname(escapeHtml4(request.getParameter("nickname")));
			}
			if (request.getParameter("password") != null) {
				user.setPassword(escapeHtml4(request.getParameter("password")));
			}
			session.beginTransaction();
			session.save(user);
			session.getTransaction().commit();
			session.close();
		}

	}

}
