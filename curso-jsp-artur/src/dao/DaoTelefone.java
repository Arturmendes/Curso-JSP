package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeanProduto;
import beans.BeanTelefone;
import connection.SingleConnection;

public class DaoTelefone {
	
	private Connection connection;
	
	public DaoTelefone() {
		connection = SingleConnection.getConnection();
	}
	
	public boolean validarTelefone(String numero) {
		
		
		String sql = "select count(1) as qtd from telefone where numero = '" + numero + "'";
		
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

	public List<BeanTelefone> listar(Long idUser) {
		List<BeanTelefone> lista = new ArrayList<BeanTelefone>();
		
		String sql = "select * from telefone where idusuario = " + idUser;
		
		try {
			PreparedStatement stm = connection.prepareStatement(sql);
			ResultSet result = stm.executeQuery();
			while(result.next()) {
				BeanTelefone telefone = new BeanTelefone();
				telefone.setId(result.getLong("id"));
				telefone.setTipo(result.getString("tipo"));
				telefone.setNumero(result.getString("numero"));
				telefone.setIdUsuario(result.getLong("idusuario"));
				
				
				lista.add(telefone);				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return lista;		
		
	}

	public void cadastrarTelefone(BeanTelefone telefone) {		
		String sql = "insert into telefone(tipo, numero, idusuario) values (?, ?, ?)";
		
		try {
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, telefone.getTipo());
			insert.setString(2, telefone.getNumero());
			insert.setLong(3, telefone.getIdUsuario());
			
			
			insert.execute();
			connection.commit();			
		} catch (Exception e) {
			System.out.println("Telefone não cadastrado");
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			e.printStackTrace();
		}
		
	}
	
	public BeanTelefone consultarTelefone(Long id) {		
		
		String sql = "select * from telefone where id = " + id;
		
		try {
			PreparedStatement consulta = connection.prepareStatement(sql);
			ResultSet result = consulta.executeQuery();
			if(result.next()) {
				BeanTelefone telefone = new BeanTelefone();
				telefone.setId(result.getLong("id"));
				telefone.setTipo(result.getString("tipo"));
				telefone.setNumero(result.getString("numero"));
				telefone.setIdUsuario(result.getLong("idusuario"));
				
				return telefone;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
		
				
	}

	public boolean validarTefoneUpdate(String numero, Long id) {
		String sql = "select count(1) as qtd from telefone where numero = '" + numero + "' and id <> " + id;
		
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

	public void editarTelefone(BeanTelefone telefone) {
		String sql = "update telefone set tipo = ?, numero = ? where id = " + telefone.getId();
		
		
		try {
			PreparedStatement update = connection.prepareStatement(sql);
			update.setString(1, telefone.getTipo());
			update.setString(2, telefone.getNumero());
			
			
			update.executeUpdate();
			connection.commit();
			
		} catch (SQLException e) {
			System.out.println("Telefone não atualizado");
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		}
		
	}

	public void delete(Long id) {
		String sql = "delete from telefone where id = " + id;
		
		 
		try {
			PreparedStatement delete = connection.prepareStatement(sql);
			delete.execute();
			connection.commit();
		} catch (SQLException e) {
			System.out.println("Telefone " + id + ", não excluido.");
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	

}
