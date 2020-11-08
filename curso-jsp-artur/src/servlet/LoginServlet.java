package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import dao.DaoLogin;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;     
	
	private DaoLogin daoLogin = new DaoLogin();
   
    public LoginServlet() {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		
		if(login != null && !login.isEmpty()) {
			System.out.println("Login informado");
			if(senha != null && !senha.isEmpty()) {
				System.out.println("Senha informada");
				if(daoLogin.validarLogin(login, senha)) {//acesso ok
					RequestDispatcher dispatcher = request.getRequestDispatcher("acesso_liberado.jsp");
					dispatcher.forward(request, response);
				}else {
					RequestDispatcher dispatcher = request.getRequestDispatcher("acesso_negado.jsp");
					dispatcher.forward(request, response);
				}				
			}else {
				request.setAttribute("msg", "Senha deve ser informada!");
				request.setAttribute("login", login);
				RequestDispatcher view = request.getRequestDispatcher("index.jsp");
				view.forward(request, response);				
			}			
		}else {			
			request.setAttribute("msg", "Usuário deve ser informado!");
			request.setAttribute("senha", senha);
			RequestDispatcher view = request.getRequestDispatcher("index.jsp");
			view.forward(request, response);
		}
		
		
		
		System.out.println(request.getParameter("login"));
		System.out.println(request.getParameter("senha"));
	}
}
