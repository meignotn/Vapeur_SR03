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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import Ressources.Game;
import Ressources.Hasher;
import Ressources.User;

/**
 * Servlet implementation class Auth
 */
@WebServlet("/Auth")
public class Auth extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 2L;
       
    public Auth() {
        super();
    }
	@Override
	public void init() throws ServletException {
		super.init();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		if(request.getSession().getAttribute("user")!=null)
			out.print(objectMapper.writeValueAsString(request.getSession().getAttribute("user")));

	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mail = escapeHtml4(request.getParameter("mail"));
		String password = escapeHtml4(request.getParameter("password"));
		Hasher hasher = new Hasher("MD5");
		String hashed_password = Hasher.hashing(hasher, password);
		User user = User.login(mail, hashed_password);
		if (user != null)
			request.getSession().setAttribute("user", user);
    }
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			request.getSession().removeAttribute("user");
    }

}
