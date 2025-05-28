package views;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;

import models.Automovel;
import models.AutomovelDAO;
import models.Caminhao;
import models.Carro;

public class MainView {

	private JFrame frame;
	private JPanel contentPane;
	private DefaultTableModel model;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainView window = new MainView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Listagem de Automóveis");
		contentPane = new JPanel();
		contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		contentPane.setLayout(new BorderLayout(10, 10));
		frame.setContentPane(contentPane);
		
		model = new DefaultTableModel();
		model.setColumnIdentifiers(new String[] {
		    "ID", "Tipo", "Marca", "Modelo", "Ano", "Portas", "Carga (ton)"
		});
		
		table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
        contentPane.add(scrollPane, BorderLayout.CENTER);
        
        JPanel botoesPanel = new JPanel();
        JButton adicionarBtn = new JButton("Adicionar");
        JButton editarBtn = new JButton("Editar");
        JButton excluirBtn = new JButton("Excluir");
        JButton atualizarBtn = new JButton("Atualizar");
		
        botoesPanel.add(adicionarBtn);
        botoesPanel.add(editarBtn);
        botoesPanel.add(excluirBtn);
        botoesPanel.add(atualizarBtn);
        
        contentPane.add(botoesPanel, BorderLayout.SOUTH);
        
        adicionarBtn.addActionListener(e -> {
            CadastroAutomovel tela = new CadastroAutomovel("");
            tela.setVisible(true);
            tela.addWindowListener(new WindowAdapter() {
            	@Override
                public void windowClosed(WindowEvent e) {
                    carregarAutomoveis();
                }
			});
        });
        
        editarBtn.addActionListener(e -> editarAutomovelSelecionado());
        
        excluirBtn.addActionListener(e -> excluirAutomovelSelecionado());
        
        atualizarBtn.addActionListener(e -> carregarAutomoveis());
     
        carregarAutomoveis();
	}
	
	private void carregarAutomoveis() {
        model.setRowCount(0);
        AutomovelDAO dao = new AutomovelDAO();
        List<Automovel> lista = dao.listarTodos();

        for (Automovel auto : lista) {
            if (auto instanceof Carro) {
                Carro c = (Carro) auto;
                model.addRow(new Object[] {
                        c.getId(), "Carro", c.getMarca(), c.getModelo(), c.getAno(),
                        c.getPortas(), null
                });
            } else if (auto instanceof Caminhao) {
                Caminhao c = (Caminhao) auto;
                model.addRow(new Object[] {
                        c.getId(), "Caminhão", c.getMarca(), c.getModelo(), c.getAno(),
                        null, c.getCapacidadeCarga()
                });
            }
        }
    }
	
	private void excluirAutomovelSelecionado() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Selecione um automóvel para excluir.");
            return;
        }

        int id = (int) model.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(
                frame, "Deseja realmente excluir o automóvel ID " + id + "?",
                "Confirmação", JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            AutomovelDAO dao = new AutomovelDAO();
            Automovel auto = dao.buscarPorId(id);
            dao.excluir(auto);
            carregarAutomoveis();
        }
    }

	private void editarAutomovelSelecionado() {
	    int selectedRow = table.getSelectedRow();
	    if (selectedRow == -1) {
	        JOptionPane.showMessageDialog(frame, "Selecione um automóvel para editar.");
	        return;
	    }

	    int id = (int) model.getValueAt(selectedRow, 0);
	    AutomovelDAO dao = new AutomovelDAO();
	    Automovel auto = dao.buscarPorId(id);

	    if (auto != null) {
	        CadastroAutomovel tela = new CadastroAutomovel(auto);
	        tela.setVisible(true);
	        
	        tela.addWindowListener(new WindowAdapter() {
            	@Override
                public void windowClosed(WindowEvent e) {
                    carregarAutomoveis();
                }
			});
	    } else {
	        JOptionPane.showMessageDialog(frame, "Erro ao buscar automóvel.");
	    }
	}
	
}
