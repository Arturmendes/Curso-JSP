package connection;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *Respons�vel por fazer a conex�o com o banco de dados 
 * @author Artur Mendes
 */

public class SingleConnection {

	private static String banco = "jdbc:postgresql://127.0.0.1:5432/curso-jsp?autoReconnect=true";
	private static String user = "artur";
	private static String password = "2468";
	private static Connection connection = null;
	
	static {
		conectar();
	}
	
	private SingleConnection() {
		conectar();
	}

	private static void conectar() {
		System.out.println(connection);
		try {			
			if(connection == null) {
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(banco, user, password);
				connection.setAutoCommit(false);
				System.out.println("Conexão ok.");
			}else {
				getConnection();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro ao conectar no BD.");
			throw new RuntimeException("Erro ao conectar com o banco de dados.");
		}
	}

	public static Connection getConnection() {
		return connection;
	}
	
	
	
}
