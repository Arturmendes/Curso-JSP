package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanProduto;
import dao.DaoProduto;




@WebServlet("/ProdutoServlet")
public class ProdutoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DaoProduto daoProduto = new DaoProduto();
       
    public ProdutoServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("Entrei no doGet");
		
		
		String id = request.getParameter("id");
		//String nome = request.getParameter("nome");
		//String qtd = request.getParameter("quantidade");
		//String valor = request.getParameter("valor");
		
		String acao = request.getParameter("acao") != null ? request.getParameter("acao") : "listarTodos";
		
		System.out.println("Ação no doGet: " + acao);
		
		if(acao.equalsIgnoreCase("carregar") || acao.equalsIgnoreCase("listarTodos")) {		
			
			request.setAttribute("produtos", daoProduto.listar());
			
		}else if(acao.equalsIgnoreCase("editar")) {
			
			BeanProduto produtoConsultado = daoProduto.consultarProduto(Long.parseLong(id));
			
			request.setAttribute("produto", produtoConsultado);		
			request.setAttribute("produtos", daoProduto.listar());
			
		}else if(acao.equalsIgnoreCase("delete")) {
			
			daoProduto.delete(Long.parseLong(id));
			
			request.setAttribute("msg", "*****Produto excluido*****");
			request.setAttribute("produtos", daoProduto.listar());
		}
		
		
		RequestDispatcher view = request.getRequestDispatcher("cadastro_produto.jsp");
		view.forward(request, response);	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//System.out.println("Entrei no doPost");
		
		String id = request.getParameter("id");
		String nome = request.getParameter("nome");
		String qtd = request.getParameter("quantidade");
		String valor = request.getParameter("valor");
		String acao= request.getParameter("acao");
		
		BeanProduto produto = new BeanProduto();
		produto.setNome(nome);
		if(qtd.isEmpty() || qtd == null) {
			produto.setQuantidade(0);
		}else {
			produto.setQuantidade(Double.parseDouble(qtd));
		}
		
		if(valor.isEmpty() || valor == null) {
			produto.setValor(0);
		}else {
			
			String valorParse = valor.replaceAll("\\.", "");			
			produto.setValor(Double.parseDouble(valorParse.replaceAll("\\,", ".")));
		}
		
		
		/*
		System.out.println("Id Produto: " + id);
		System.out.println("Nome Produto: " + nome);
		System.out.println("Quantidade Produto: " + qtd);
		System.out.println("Valor Produto: " + valor);
		System.out.println("Ação Produto: " + acao);
		*/
		
		if(acao == null || acao.isEmpty()) {
			if(nome ==  null || nome.isEmpty()) {
				request.setAttribute("msg", "Nome deve ser informado");
				request.setAttribute("produto", produto);
				request.setAttribute("produtos", daoProduto.listar());
			}else if(qtd == null || qtd.isEmpty()) {
				request.setAttribute("msg", "Quantidade deve ser informada");
				request.setAttribute("produto", produto);
				request.setAttribute("produtos", daoProduto.listar());				
			}else if(valor == null || valor.isEmpty()) {
				request.setAttribute("msg", "Valor deve ser informado");
				request.setAttribute("produto", produto);
				request.setAttribute("produtos", daoProduto.listar());				
			}else if(id == null || id.isEmpty()) {//se id null ou vazio cadastra o produto
				if(daoProduto.validarProduto(nome)) {//se n�o existir um produto com mesmo nome, entao cadastra
					System.out.println("Produto validado, pode cadastrar");
					daoProduto.cadastrarProduto(produto);							
					request.setAttribute("produtos", daoProduto.listar());	
					request.setAttribute("msg", "*****Produto cadastrado*****");
				}else {
					request.setAttribute("msg", "*****Produto duplicado*****");
					request.setAttribute("produtos", daoProduto.listar());	
				}
			}else {//se id tiver preenchido edita o produto
				if(daoProduto.validarProdutoUpdate(nome , Long.parseLong(id))) {
					produto.setId(Long.parseLong(id));
					daoProduto.editarProduto(produto);
					request.setAttribute("produtos", daoProduto.listar());	
					request.setAttribute("msg", "*****Produto editado*****");
				}else {					
					produto.setId(Long.parseLong(id));
					request.setAttribute("produto", produto);
					request.setAttribute("produtos", daoProduto.listar());
					request.setAttribute("msg", "*****Produto duplicado*****");
				}
			}			
		}else {
			if(acao.equalsIgnoreCase("reset")) {				
				request.setAttribute("produtos", daoProduto.listar());	
			}
		}
		
		RequestDispatcher view = request.getRequestDispatcher("cadastro_produto.jsp");
		view.forward(request, response);
	}

}
