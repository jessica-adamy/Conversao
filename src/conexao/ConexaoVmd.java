package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoVmd {
public static void main(String args[]){
	Connection conexao = null;
	try{
		String url = "jdbc:jtds:sqlserver://localhost/VMD";
		String usuario = "sa";
		String senha = "vls021130";
		Class.forName("net.sourceforge.jtds.jdbc.Driver");
		conexao = DriverManager.getConnection(url, usuario, senha);
		System.out.println("conectou");
	} catch(SQLException e) {
		System.out.println("Ocorreu um erro: " +e.getMessage());
	
		
	}catch (ClassNotFoundException e) {
		System.out.println("Erro de drive: " + e.getMessage());
	}
	
	finally{
		try{
			conexao.close();
		}catch(SQLException e){
			System.out.println("Erro ao fechar conexao: " +e.getMessage());
		}
	}
}
}
