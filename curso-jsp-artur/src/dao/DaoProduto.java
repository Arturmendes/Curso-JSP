package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeanProduto;
import connection.SingleConnection;

public class DaoProduto {
	
	private Connection connection;
	
	public DaoProduto() {
		connection = SingleConnection.getConnection();
	}
	
	public boolean validarProduto(String nome) {
		
		
		String sql = "select count(1) as qtd from produto where nome = '" + nome + "'";
		
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

	public List<BeanProduto> listar() {
		List<BeanProduto> lista = new ArrayList<BeanProduto>();
		
		String sql = "select * from produto";
		
		try {
			PreparedStatement stm = connection.prepareStatement(sql);
			ResultSet result = stm.executeQuery();
			while(result.next()) {
				BeanProduto produto = new BeanProduto();
				produto.setId(result.getLong("id"));
				produto.setNome(result.getString("nome"));
				produto.setQuantidade(result.getDouble("quantidade"));
				produto.setValor(result.getDouble("valor"));
				
				
				lista.add(produto);				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return lista;		
		
	}

	public void cadastrarProduto(BeanProduto produto) {
		System.out.println("Cadastro de produto");
		String sql = "insert into produto(nome, quantidade, valor) values (?, ?, ?)";
		
		try {
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, produto.getNome());
			insert.setDouble(2, produto.getQuantidade());
			insert.setDouble(3, produto.getValor());
			
			
			insert.execute();
			connection.commit();			
		} catch (Exception e) {
			System.out.println("Produto não cadastrado");
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			e.printStackTrace();
		}
		
	}
	
	public BeanProduto consultarProduto(Long id) {
		
		
		String sql = "select * from produto where id = " + id;
		
		try {
			PreparedStatement consulta = connection.prepareStatement(sql);
			ResultSet result = consulta.executeQuery();
			if(result.next()) {
				BeanProduto produto = new BeanProduto();
				produto.setId(result.getLong("id"));				
				produto.setNome(result.getString("nome"));
				produto.setQuantidade(result.getDouble("quantidade"));
				produto.setValor(result.getDouble("valor"));
				
				return produto;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
		
				
	}

	public boolean validarProdutoUpdate(String nome, Long id) {
		String sql = "select count(1) as qtd from produto where nome = '" + nome + "' and id <> " + id;
		
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

	public void editarProduto(BeanProduto produto) {
		String sql = "update produto set nome = ?, quantidade = ?, valor = ? where id = " + produto.getId();
		
		
		try {
			PreparedStatement update = connection.prepareStatement(sql);
			update.setString(1, produto.getNome());
			update.setDouble(2, produto.getQuantidade());
			update.setDouble(3, produto.getValor());
			
			update.executeUpdate();
			connection.commit();
			
		} catch (SQLException e) {
			System.out.println("Produto não atualizado");
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		}
		
	}

	public void delete(Long id) {
		String sql = "delete from produto where id = " + id;
		
		 
		try {
			PreparedStatement delete = connection.prepareStatement(sql);
			delete.execute();
			connection.commit();
		} catch (SQLException e) {
			System.out.println("Produto " + id + ", não excluido.");
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	

}
