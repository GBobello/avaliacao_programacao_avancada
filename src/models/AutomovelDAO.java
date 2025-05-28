package models;

import java.sql.*;
import java.util.*;

import javax.swing.JOptionPane;

public class AutomovelDAO implements IAutomovelDAO {
	private Connection conexao;
	
	public AutomovelDAO() {
		this.conexao = Conecta.getConexao();
	}

	@Override
	public void inserir(Automovel auto) {
		String sql = "INSERT INTO automovel (tipo, marca, modelo, ano, portas, carga) VALUES (?, ?, ?, ?, ?, ?)";
		try(PreparedStatement stmt = conexao.prepareStatement(sql)){
			stmt.setString(1, auto.getTipo());
			stmt.setString(2, auto.getMarca());
			stmt.setString(3, auto.getModelo());
			stmt.setInt(4, auto.getAno());
			if (auto instanceof Carro) {
				stmt.setInt(5, ((Carro) auto).getPortas());
				stmt.setNull(6, Types.DOUBLE);
			} else if (auto instanceof Caminhao) {
				stmt.setNull(5, Types.INTEGER);
				stmt.setDouble(6, ((Caminhao) auto).getCapacidadeCarga());
			}
			stmt.executeUpdate();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro ao inserir automóvel!\n" + e.toString());
		}
		
	}

	@Override
	public void atualizar(Automovel auto) {
	    String sql = "UPDATE automovel SET tipo = ?, marca = ?, modelo = ?, ano = ?, portas = ?, carga = ? WHERE id = ?";
	    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
	    	stmt.setString(1, auto.getTipo());
	        stmt.setString(2, auto.getMarca());
	        stmt.setString(3, auto.getModelo());
	        stmt.setInt(4, auto.getAno());

	        if (auto instanceof Carro) {
	            stmt.setInt(5, ((Carro) auto).getPortas());
	            stmt.setNull(6, Types.DOUBLE);
	        } else if (auto instanceof Caminhao) {
	            stmt.setNull(5, Types.INTEGER);
	            stmt.setDouble(6, ((Caminhao) auto).getCapacidadeCarga());
	        }

	        stmt.setInt(7, auto.getId());

	        int linhasAfetadas = stmt.executeUpdate();
	        if (linhasAfetadas <= 0) {
	        	JOptionPane.showMessageDialog(null, "Nenhum registro foi atualizado.");
	        }

	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(null, "Erro ao atualizar automóvel!\n" + e.toString());
	    }
		
	}

	@Override
	public void excluir(Automovel auto) {
	    String sql = "DELETE FROM automovel WHERE id = ?";
	    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
	        stmt.setInt(1, auto.getId());
	        int linhasAfetadas = stmt.executeUpdate();
	        if (linhasAfetadas > 0) {
	            JOptionPane.showMessageDialog(null, "Automóvel excluído com sucesso!");
	        } else {
	            JOptionPane.showMessageDialog(null, "Nenhum registro foi excluído.");
	        }

	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(null, "Erro ao excluir automóvel!\n" + e.toString());
	    }
		
	}

	@Override
	public List<Automovel> listarTodos() {
        List<Automovel> lista = new ArrayList<>();
        String sql = "SELECT * FROM automovel";
        try (Statement stmt = conexao.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
            	int id = rs.getInt("id");
                String tipo = rs.getString("tipo");
                if ("Carro".equals(tipo)) {
                	Carro carro = new Carro(
                            rs.getString("marca"),
                            rs.getString("modelo"),
                            rs.getInt("ano"),
                            rs.getInt("portas")
                        );
                	carro.setId(id);
                    lista.add(carro);
                } else if ("Caminhao".equals(tipo)) {
                	Caminhao caminhao = new Caminhao(
                            rs.getString("marca"),
                            rs.getString("modelo"),
                            rs.getInt("ano"),
                            rs.getDouble("carga")
                        );
                	caminhao.setId(id);
                    lista.add(caminhao);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
	}

	@Override
	public Automovel buscarPorId(int id) {
	    String sql = "SELECT * FROM automovel WHERE id = ?";
	    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
	        stmt.setInt(1, id);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            String tipo = rs.getString("tipo");
	            String marca = rs.getString("marca");
	            String modelo = rs.getString("modelo");
	            int ano = rs.getInt("ano");

	            if ("Carro".equalsIgnoreCase(tipo)) {
	                int portas = rs.getInt("portas");
	                Carro carro = new Carro(marca, modelo, ano, portas);
	                carro.setId(id);
	                return carro;
	            } else if ("Caminhao".equalsIgnoreCase(tipo)) {
	                double carga = rs.getDouble("carga");
	                Caminhao caminhao = new Caminhao(marca, modelo, ano, carga);
	                caminhao.setId(id);
	                return caminhao;
	            }
	        }
	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(null, "Erro ao buscar automóvel!\n" + e.toString());
	    }

	    return null;
	}
	
	
}
