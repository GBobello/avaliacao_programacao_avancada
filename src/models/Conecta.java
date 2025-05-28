package models;

import java.sql.*;

import javax.swing.JOptionPane;

public class Conecta {
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/veiculos";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static Connection conexao;
	
    public static Connection getConexao() {
    	try {
    		Class.forName(DRIVER);
    		conexao = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    		if (conexao != null) {
//    			JOptionPane.showMessageDialog(null, "Conexão com o banco de dados bem sucedida!");
    		}
    	} catch(ClassNotFoundException erro) {
    		JOptionPane.showMessageDialog(null, "Driver não encontrado!\n" + erro.toString());
    	} catch(SQLException erro) {
    		JOptionPane.showMessageDialog(null, "Problemas de conexão com a fonte de dados\n" + erro.toString());
    	}
    	
    	return conexao;
    }
}
