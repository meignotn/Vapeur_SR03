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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import Ressources.SFactory;
import Ressources.User;

/**
 * Servlet implementation class Auth
 */
@WebServlet("/user/wallet")
public class WalletServlet extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 2L;

	public WalletServlet() {
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
			out.println(objectMapper.writeValueAsString(user.getWallet()));
			session.close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		Session session = SFactory.getSession();
		int quantity = 0;
		if(request.getParameter("quantity") != null){
			quantity = Integer.parseInt(request.getParameter("quantity"));
		}
		if (user != null) {
			session.refresh(user);
			user.addWallet(quantity, session);
			session.close();
		}
	}

}
