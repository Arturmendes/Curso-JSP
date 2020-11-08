package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeanUsuario;
import connection.SingleConnection;

public class DaoUsuario {
	
	private Connection connection;
	
	
	
	public DaoUsuario() {
		connection = SingleConnection.getConnection();
	}
	
	public void cadastrarUsuario(BeanUsuario usuario) {
		System.out.println("Cadastro de Usu�rio");
		String sql = "insert into usuario(login, senha, nome, telefone, cep, rua, bairro, numero, cidade, estado, fotobase64, contenttype, curriculobase64, contenttypecurriculo, fotobase64miniatura) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, usuario.getLogin());
			insert.setString(2, usuario.getSenha());
			insert.setString(3, usuario.getNome());
			insert.setString(4, usuario.getTelefone());
			insert.setString(5, usuario.getCep());
			insert.setString(6, usuario.getRua());
			insert.setString(7, usuario.getBairro());
			insert.setString(8, usuario.getNumero());
			insert.setString(9, usuario.getCidade());
			insert.setString(10, usuario.getEstado());
			insert.setString(11, usuario.getFotoBase64());
			insert.setString(12, usuario.getContentType());
			insert.setString(13, usuario.getCurriculoBase64());
			insert.setString(14, usuario.getContentTypeCurriculo());
			insert.setString(15, usuario.getFotoBase64Miniatura());
			
			insert.execute();
			connection.commit();			
		} catch (Exception e) {
			System.out.println("Usuário não cadastrado");
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			e.printStackTrace();
		}
		
	}
	
	public List<BeanUsuario> listar(){
		List<BeanUsuario> lista = new ArrayList<BeanUsuario>();
		
		String sql = "select * from usuario where login <> 'admin'";
		
		try {
			PreparedStatement stm = connection.prepareStatement(sql);
			ResultSet result = stm.executeQuery();
			while(result.next()) {
				BeanUsuario usuario = new BeanUsuario();
				usuario.setId(result.getLong("id"));
				usuario.setLogin(result.getString("login"));
				usuario.setSenha(result.getString("senha"));
				usuario.setNome(result.getString("nome"));
				usuario.setTelefone(result.getString("telefone"));
				usuario.setCep(result.getString("cep"));
				usuario.setRua(result.getString("rua"));
				usuario.setBairro(result.getString("bairro"));
				usuario.setNumero(result.getString("numero"));
				usuario.setCidade(result.getString("cidade"));
				usuario.setEstado(result.getString("estado"));
				//usuario.setContentType(result.getString("contenttype"));
				//usuario.setFotoBase64(result.getString("fotobase64"));
				usuario.setContentTypeCurriculo(result.getString("contenttypecurriculo"));
				usuario.setCurriculoBase64(result.getString("curriculobase64"));
				usuario.setFotoBase64Miniatura(result.getString("fotobase64miniatura"));
				
				lista.add(usuario);				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return lista;
	}
	
	public void deleteUsuario(Long id) {
		String sql = "delete from usuario where id = " + id + " and login <> 'admin'";
		
		 
		try {
			PreparedStatement delete = connection.prepareStatement(sql);
			delete.execute();
			connection.commit();
		} catch (SQLException e) {
			System.out.println("Usuário " + id + ", não excluido.");
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	public void editarUsuario(BeanUsuario usuario) {
		String sql = "update usuario set login = ?, senha = ?, nome = ?, telefone = ?, cep = ?, rua = ?, bairro = ?, numero = ?, cidade = ?, estado = ?, fotobase64 = ?, contenttype = ?, curriculobase64 =?, contenttypecurriculo = ?, fotobase64miniatura = ? where id = " + usuario.getId();
		
		
		try {
			PreparedStatement update = connection.prepareStatement(sql);
			update.setString(1, usuario.getLogin());
			update.setString(2, usuario.getSenha());
			update.setString(3, usuario.getNome());
			update.setString(4, usuario.getTelefone());
			update.setString(5, usuario.getCep());
			update.setString(6, usuario.getRua());
			update.setString(7, usuario.getBairro());
			update.setString(8, usuario.getNumero());
			update.setString(9, usuario.getCidade());
			update.setString(10, usuario.getEstado());
			update.setString(11, usuario.getFotoBase64());
			update.setString(12, usuario.getContentType());
			update.setString(13, usuario.getCurriculoBase64());
			update.setString(14, usuario.getContentTypeCurriculo());
			update.setString(15, usuario.getFotoBase64Miniatura());
			update.executeUpdate();
			connection.commit();
			
		} catch (SQLException e) {
			System.out.println("Usuário não atualizado");
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		}
		
		
	}
	
	public BeanUsuario consultarUsuario(String id) {
		
		
		String sql = "select * from usuario where id = " + id;
		
		try {
			PreparedStatement consulta = connection.prepareStatement(sql);
			ResultSet result = consulta.executeQuery();
			if(result.next()) {
				BeanUsuario usuario = new BeanUsuario();
				usuario.setId(result.getLong("id"));
				usuario.setLogin(result.getString("login"));
				usuario.setSenha(result.getString("senha"));
				usuario.setNome(result.getString("nome"));
				usuario.setTelefone(result.getString("telefone"));
				usuario.setCep(result.getString("cep"));
				usuario.setRua(result.getString("rua"));
				usuario.setBairro(result.getString("bairro"));
				usuario.setNumero(result.getString("numero"));
				usuario.setCidade(result.getString("cidade"));
				usuario.setEstado(result.getString("estado"));
				usuario.setContentType(result.getString("contenttype"));
				usuario.setFotoBase64(result.getString("fotobase64"));
				usuario.setContentTypeCurriculo(result.getString("contenttypecurriculo"));
				usuario.setCurriculoBase64(result.getString("curriculobase64"));
				usuario.setFotoBase64Miniatura(result.getString("fotobase64miniatura"));
				
				
				return usuario;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
		
				
	}
	
	public boolean validarUsuario(String login) {
		
		
		String sql = "select count(1) as qtd from usuario where login = '" + login + "'";
		
		try {
			PreparedStatement consulta = connection.prepareStatement(sql);
			ResultSet result = consulta.executeQuery();
			if(result.next()) {
				return result.getInt("qtd") == 0;//return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
		
				
	}
	
public boolean validarUsuarioUpdate(String login, Long id) {
		
		
		String sql = "select count(1) as qtd from usuario where login = '" + login + "' and id <> " + id;
		
		try {
			PreparedStatement consulta = connection.prepareStatement(sql);
			ResultSet result = consulta.executeQuery();
			if(result.next()) {
				return result.getInt("qtd") == 0;//return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
		
				
	}


}
