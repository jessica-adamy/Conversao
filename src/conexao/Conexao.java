package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {	
	public Connection getConnection(String servidor, String banco) {
		Connection conexao = null;
		try{
			String url = "jdbc:jtds:sqlserver://"+servidor+"/"+banco;
			String usuario = "sa";
			String senha = "vls021130";
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			conexao = DriverManager.getConnection(url, usuario, senha);
			System.out.println("conectou "+banco);
		} catch(SQLException e) {
			System.out.println("Ocorreu um erro: " +e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("Erro de drive: " + e.getMessage());
		}
		return conexao;
	}
}
