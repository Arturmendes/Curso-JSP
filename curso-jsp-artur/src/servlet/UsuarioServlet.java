package servlet;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.codec.binary.Base64;

import beans.BeanUsuario;
import dao.DaoUsuario;


@WebServlet("/CadastrarUsuario")
@MultipartConfig
public class UsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DaoUsuario daoUsuario = new DaoUsuario();
       
    public UsuarioServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Entrei no doGet UsuarioServlet");
		System.out.println(request.getParameter("acao"));
		System.out.println(request.getParameter("id"));
		System.out.println(request.getParameter("login"));
		
		
		String id = request.getParameter("id");
		String acao = request.getParameter("acao") != null ? request.getParameter("acao"): "listarTodos";
		
		
		if(acao.equalsIgnoreCase("carregar") || acao.equalsIgnoreCase("listarTodos")) {
			RequestDispatcher view = request.getRequestDispatcher("cadastro_usuario.jsp");		
			request.setAttribute("usuarios", daoUsuario.listar());			
			view.forward(request, response);			
		}else if(acao.equalsIgnoreCase("delete")) {
			if(!request.getParameter("login").equalsIgnoreCase("admin")) {
				daoUsuario.deleteUsuario(Long.parseLong(id));
				RequestDispatcher view = request.getRequestDispatcher("cadastro_usuario.jsp");		
				request.setAttribute("usuarios", daoUsuario.listar());
				
				view.forward(request, response);				
			}
			
			request.setAttribute("msg", "admin não pode ser apagado");
			request.setAttribute("usuarios", daoUsuario.listar());
			
			request.getRequestDispatcher("cadastro_usuario.jsp").forward(request, response);
						
		}else if(acao.equalsIgnoreCase("editar")) {
			
			BeanUsuario usuarioConsultado = daoUsuario.consultarUsuario(id);
			
			RequestDispatcher view = request.getRequestDispatcher("cadastro_usuario.jsp");		
			request.setAttribute("usuario", usuarioConsultado);
			
			view.forward(request, response);
			
		}else if(acao.equalsIgnoreCase("downloadFoto")) {			
			BeanUsuario usuarioConsultado = daoUsuario.consultarUsuario(id);
			if(usuarioConsultado != null) {
				response.setHeader("Content-Disposition", "attachment;filename=arquivo." + usuarioConsultado.getContentType().split("\\/")[1]);
				
				/*converte a base64 da imagem do banco para byte[]*/
				byte[] imageFotoBytes = new Base64().decodeBase64(usuarioConsultado.getFotoBase64());
				
				/*Coloca os bytes em um objeto de entrada para processar*/
				InputStream is = new ByteArrayInputStream(imageFotoBytes);
				
				/*Inicio da resposta para o navegador*/
				
				int read = 0;
				
				byte[] bytes = new byte[1024];
				
				OutputStream os = response.getOutputStream();
				
				int count = 0;
				while((read = is.read(bytes)) != -1) {
					os.write(bytes, 0, read);
					count++;					
				}
				
				System.out.println("Count = " + count);
				
				os.flush();
				os.close();
				
				
				
				
			}						
			
		}else if(acao.equalsIgnoreCase("downloadCurriculo")) {			
			BeanUsuario usuarioConsultado = daoUsuario.consultarUsuario(id);
			if(usuarioConsultado != null) {
				response.setHeader("Content-Disposition", "attachment;filename=arquivo." + usuarioConsultado.getContentTypeCurriculo().split("\\/")[1]);
				
				/*converte a base64 da imagem do banco para byte[]*/
				byte[] imageCurriculoBytes = new Base64().decodeBase64(usuarioConsultado.getCurriculoBase64());
				
				/*Coloca os bytes em um objeto de entrada para processar*/
				InputStream is = new ByteArrayInputStream(imageCurriculoBytes);
				
				/*Inicio da resposta para o navegador*/
				
				int read = 0;
				
				byte[] bytes = new byte[1024];
				
				OutputStream os = response.getOutputStream();
				
				int count = 0;
				while((read = is.read(bytes)) != -1) {
					os.write(bytes, 0, read);
					count++;					
				}
				
				System.out.println("Count = " + count);
				
				os.flush();
				os.close();			
				
			}						
			
		}
		System.out.println("Sai do doGet UsuarioServlet");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Entrei no doPost UsuarioServlet");
		String id= request.getParameter("id");
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		String nome = request.getParameter("nome");
		String telefone = request.getParameter("telefone");
		String cep = request.getParameter("cep");
		String rua = request.getParameter("rua");
		String numero = request.getParameter("numero");
		String cidade = request.getParameter("cidade");
		String bairro = request.getParameter("bairro");
		String estado = request.getParameter("estado");		
		String acao = request.getParameter("acao");
		
		BeanUsuario usuario = new BeanUsuario();
		//usuario.setId(!id.isEmpty() || id != null ? Long.parseLong(id) : 0);		
		usuario.setLogin(login);
		usuario.setNome(nome);
		usuario.setTelefone(telefone);
		usuario.setSenha(senha);
		usuario.setCep(cep);
		usuario.setRua(rua);
		usuario.setNumero(numero);
		usuario.setCidade(cidade);
		usuario.setBairro(bairro);
		usuario.setEstado(estado);
		
		
		
		
		/*In�cio file upload de imagens e pdf*/
		
		if(ServletFileUpload.isMultipartContent(request)) {
			Part imagemFoto = request.getPart("foto");
			if(imagemFoto != null && imagemFoto.getInputStream().available() > 0) {
				
				byte[] bytesImagem = converteStremParabyte(imagemFoto.getInputStream());
				String fotoBase64 = new Base64().encodeBase64String(bytesImagem);
				String contentType = imagemFoto.getContentType();
				usuario.setFotoBase64(fotoBase64);
				usuario.setContentType(contentType);
				
				/*Inicio miniatura imagem*/
				
					/*Transforma em um bufferedImage*/
					byte[] imageByteDecode = new Base64().decodeBase64(fotoBase64);
					
					BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageByteDecode));
					
					/*Pega o tipo da imagem*/
					
					int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
					
					/*Cria imagem em miniatura*/
					
					BufferedImage resizeImage = new BufferedImage(100, 100, type);
					
					Graphics2D g = resizeImage.createGraphics();
					
					g.drawImage(bufferedImage, 0, 0, 100, 100, null);
					g.dispose();
					
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					
					ImageIO.write(resizeImage, "png", baos);
					
					
					String miniaturaBase64 = "data:image/png;base64," + DatatypeConverter.printBase64Binary(baos.toByteArray());
					
					usuario.setFotoBase64Miniatura(miniaturaBase64);
				
				
				
				
				
				
				/*Fim miniatura imagem*/
				
				
			}else {
				usuario.setFotoBase64(request.getParameter("fotoTemp"));
				usuario.setContentType(request.getParameter("fotoContentTemp"));
				
			}
			
			Part curriculoPdf = request.getPart("curriculo");
			if(curriculoPdf != null && curriculoPdf.getInputStream().available() > 0) {
				String curriculoBase64 = new Base64().encodeBase64String(converteStremParabyte(curriculoPdf.getInputStream()));
				String contentTypeCurriculo = curriculoPdf.getContentType();
				usuario.setCurriculoBase64(curriculoBase64);
				usuario.setContentTypeCurriculo(contentTypeCurriculo);
			}else {
				usuario.setCurriculoBase64(request.getParameter("curriculoTemp"));
				usuario.setContentTypeCurriculo(request.getParameter("curriculoContentTemp"));
			}
		}
		
		
		
		/*Fim file upload de imagens e pdf*/
		
		System.out.println(usuario);
		
		
		
		
		if(acao == null || acao.isEmpty()) {
			if(nome == null || nome.isEmpty()) {
				request.setAttribute("msg", "Nome deve ser informado");
				request.setAttribute("usuario", usuario);
				request.setAttribute("usuarios", daoUsuario.listar());
			}else if(telefone.isEmpty() || telefone == null) {
				request.setAttribute("msg", "Telefone deve ser informado");
				request.setAttribute("usuario", usuario);
				request.setAttribute("usuarios", daoUsuario.listar());				
			}else if(login.isEmpty() || login == null) {
				request.setAttribute("msg", "Login deve ser informado");
				request.setAttribute("usuario", usuario);
				request.setAttribute("usuarios", daoUsuario.listar());				
			}else if(senha.isEmpty() || senha == null) {
				request.setAttribute("msg", "Senha deve ser informado");
				request.setAttribute("usuario", usuario);
				request.setAttribute("usuarios", daoUsuario.listar());		
			}else if(id.isEmpty() || id == null) {
				if(daoUsuario.validarUsuario(login)) {
					daoUsuario.cadastrarUsuario(usuario);
					request.setAttribute("usuarios", daoUsuario.listar());
					request.setAttribute("msg", "Cadastro realizado login = " + usuario.getLogin());
				}else {
					request.setAttribute("msg", "Login duplicado");
					request.setAttribute("usuario", usuario);
					request.setAttribute("usuarios", daoUsuario.listar());								
				}
			}else {
				if(daoUsuario.validarUsuarioUpdate(login, Long.parseLong(id))) {
					usuario.setId(Long.parseLong(id));	
					daoUsuario.editarUsuario(usuario);						
					request.setAttribute("usuarios", daoUsuario.listar());
					request.setAttribute("msg", "Usu�rio " + usuario.getNome() + " editado!!!");
					
				}else {
					usuario.setId(Long.parseLong(id));
					request.setAttribute("msg", "Login ja existe...");
					request.setAttribute("usuario", usuario);
					request.setAttribute("usuarios", daoUsuario.listar());
				}
			}
		}else {
			if(acao.equalsIgnoreCase("reset") && acao != null) {
				request.getSession().setAttribute("usuario", null);
				request.setAttribute("usuarios", daoUsuario.listar());
			}
		}
		
		RequestDispatcher view = request.getRequestDispatcher("cadastro_usuario.jsp");
		view.forward(request, response);
		
		System.out.println("Sai do doPost UsuarioServlet");
	}
	
	/*Converte a entrada de fluxo de dados da imagem para byte[]*/
	private byte[] converteStremParabyte(InputStream imagem) {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int reads;
		try {
			reads = imagem.read();
			System.out.println("Reads = " + reads);
			while(reads !=  -1) {
				baos.write(reads);
				reads = imagem.read();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		return baos.toByteArray();
		
	}
}
