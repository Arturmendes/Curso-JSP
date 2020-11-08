package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.SingleConnection;

public class DaoLogin {
	
	private Connection connection;
	
	
	public DaoLogin() {
		connection = SingleConnection.getConnection();
	}
	
	public boolean validarLogin(String login, String senha) {
		
		String sql = "select * from usuario where login = '" + login + "' and senha = '" + senha + "'";
		
		 
		try {
			PreparedStatement stm = connection.prepareStatement(sql);
			ResultSet result = stm.executeQuery();
			if(result.next()) {
				return true; //Possui usuário
			}else { // não validou o usuário
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;		
	}

}
