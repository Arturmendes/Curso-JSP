package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanTelefone;
import beans.BeanUsuario;
import dao.DaoTelefone;
import dao.DaoUsuario;

@WebServlet("/TelefonesUsuariosServlet")
public class TelefonesUsuariosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DaoUsuario daoUsuario = new DaoUsuario();
	private DaoTelefone daoTelefone = new DaoTelefone();
       
    public TelefonesUsuariosServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getParameter("acao");
		String idUsuario = request.getParameter("idUser");
		String idFone = request.getParameter("idFone");
		
		//System.out.println("Id usuário: " + idUsuario);
		
		if(acao.equalsIgnoreCase("carregar")) {
			BeanUsuario usuario = daoUsuario.consultarUsuario(idUsuario);
			//System.out.println(usuario);
			
			request.getSession().setAttribute("usuarioSessao", usuario);
			request.setAttribute("usuario", usuario);
			request.setAttribute("telefones", daoTelefone.listar(usuario.getId()));				
		}else if(acao.equalsIgnoreCase("delete")) {
			daoTelefone.delete(Long.parseLong(idFone));
			BeanUsuario usuario = (BeanUsuario) request.getSession().getAttribute("usuarioSessao");
			
			request.getSession().setAttribute("usuarioSessao", usuario);
			request.setAttribute("usuario", usuario);
			request.setAttribute("telefones", daoTelefone.listar(usuario.getId()));
		}else if(acao.equalsIgnoreCase("editar")) {
			BeanTelefone telefoneConsultado = daoTelefone.consultarTelefone(Long.parseLong(idFone));
			
			BeanUsuario usuario = (BeanUsuario) request.getSession().getAttribute("usuarioSessao");
			
			request.getSession().setAttribute("usuarioSessao", usuario);
			request.setAttribute("usuario", usuario);
			request.setAttribute("telefone", telefoneConsultado);
			
			
		}
		
		RequestDispatcher view = request.getRequestDispatcher("cadastro_telefones_usuario.jsp");
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BeanUsuario usuario = (BeanUsuario) request.getSession().getAttribute("usuarioSessao");
		
		
		String idTelefone = request.getParameter("idTelefone");		
		String tipo = request.getParameter("tipo");
		String numero = request.getParameter("numeroTelefone");
		String idUsuario = request.getParameter("idUser");
		String acao = request.getParameter("acao");
		
		BeanTelefone telefone = new BeanTelefone();
		telefone.setTipo(tipo);
		telefone.setNumero(numero);
		telefone.setIdUsuario(Long.parseLong(idUsuario));
		
		if(acao == null || acao.isEmpty() ) {
			if(numero == null || numero.isEmpty()) {
				request.setAttribute("msg", "Número deve ser informado2");				
				request.setAttribute("usuario", usuario);
				request.setAttribute("telefone", telefone);
				request.setAttribute("telefones", daoTelefone.listar(Long.parseLong(idUsuario)));
			}else if(idTelefone == null || idTelefone.isEmpty()) {
				daoTelefone.cadastrarTelefone(telefone);
				request.setAttribute("msg", "Telefone cadastrado");	
				request.setAttribute("usuario", usuario);
				request.setAttribute("telefone", telefone);
				request.setAttribute("telefones", daoTelefone.listar(Long.parseLong(idUsuario)));
			}else {				
				if(daoTelefone.validarTefoneUpdate(numero, Long.parseLong(idTelefone))) {
					telefone.setId(Long.parseLong(idTelefone));
					daoTelefone.editarTelefone(telefone);
					
					request.setAttribute("usuario", usuario);					
					request.setAttribute("telefones", daoTelefone.listar(usuario.getId()));
											
					
					request.setAttribute("msg", "Telefone " + telefone.getNumero() + " editado!!!");
					
				}else {			
					telefone.setId(Long.parseLong(idTelefone));
					request.setAttribute("msg", "Numero ja existe...");
					request.setAttribute("usuario", usuario);
					request.setAttribute("telefone", telefone);
					request.setAttribute("telefones", daoTelefone.listar(usuario.getId()));								
				}				
			}
			
			request.getSession().setAttribute("usuarioSessao", usuario);
			RequestDispatcher view = request.getRequestDispatcher("cadastro_telefones_usuario.jsp");
			view.forward(request, response);
		}else {
			if(acao.equalsIgnoreCase("reset")) {
				RequestDispatcher view = request.getRequestDispatcher("cadastro_usuario.jsp");
				request.getSession().setAttribute("usuarioSessao", null);
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);	
			}
		}
		/*
		if(numero != null || !numero.isEmpty()) {
			daoTelefone.cadastrarTelefone(telefone);
			request.getSession().setAttribute("usuario", usuario);
			request.setAttribute("telefones", daoTelefone.listar(telefone.getIdUsuario()));
			//request.setAttribute("usuario", usuario);
			request.setAttribute("msg","Número " + telefone.getNumero() + " cadastrado.");
			
			System.out.println(telefone);
		}else {
			request.setAttribute("msg","Informe o número.");
		}*/
		
		
		
		
		
		
	}

}
