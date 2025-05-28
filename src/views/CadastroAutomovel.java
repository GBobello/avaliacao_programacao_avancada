package views;

import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import models.Automovel;
import models.AutomovelDAO;
import models.Caminhao;
import models.Carro;

public class CadastroAutomovel extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox<String> tipoCombo;
    private JTextField marcaField, modeloField, anoField, portasField, cargaField;
    private JButton salvarBtn;
    private JLabel label;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastroAutomovel frame = new CadastroAutomovel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public CadastroAutomovel() {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().setLayout(new GridLayout(7, 2));
		setContentPane(contentPane);

		contentPane.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblTipo = new JLabel("Tipo:");
		lblTipo.setBounds(10, 10, 80, 20);
		contentPane.add(lblTipo);

		tipoCombo = new JComboBox<>(new String[] { "Carro", "Caminhao" });
		tipoCombo.setBounds(100, 10, 150, 22);
		contentPane.add(tipoCombo);

		JLabel lblMarca = new JLabel("Marca:");
		lblMarca.setBounds(10, 45, 80, 20);
		contentPane.add(lblMarca);

		marcaField = new JTextField();
		marcaField.setBounds(100, 45, 250, 22);
		contentPane.add(marcaField);

		JLabel lblModelo = new JLabel("Modelo:");
		lblModelo.setBounds(10, 80, 80, 20);
		contentPane.add(lblModelo);

		modeloField = new JTextField();
		modeloField.setBounds(100, 80, 250, 22);
		contentPane.add(modeloField);

		JLabel lblAno = new JLabel("Ano:");
		lblAno.setBounds(10, 115, 80, 20);
		contentPane.add(lblAno);

		anoField = new JTextField();
		anoField.setBounds(100, 115, 80, 22);
		contentPane.add(anoField);

		JLabel lblPortas = new JLabel("Portas:");
		lblPortas.setBounds(10, 150, 80, 20);
		contentPane.add(lblPortas);

		portasField = new JTextField();
		portasField.setBounds(100, 150, 80, 22);
		contentPane.add(portasField);

		JLabel lblCarga = new JLabel("Carga (ton):");
		lblCarga.setBounds(10, 185, 80, 20);
		contentPane.add(lblCarga);

		cargaField = new JTextField();
		cargaField.setBounds(100, 185, 80, 22);
		contentPane.add(cargaField);

		salvarBtn = new JButton("Salvar");
		salvarBtn.setBounds(140, 230, 100, 30);
		contentPane.add(salvarBtn);		
		
		label = new JLabel("");
		contentPane.add(label);
	}
	
	public CadastroAutomovel(String nada) {
		this();
		tipoCombo.addActionListener(e -> alternarCampos());
		salvarBtn.addActionListener(e -> salvarAutomovel());
		alternarCampos();
	}
	
	
	public CadastroAutomovel(Automovel auto) {		
		this();
		
		tipoCombo.setSelectedItem(auto.getTipo());
	    marcaField.setText(auto.getMarca());
	    modeloField.setText(auto.getModelo());
	    anoField.setText(String.valueOf(auto.getAno()));

	    if (auto instanceof Carro) {
	        portasField.setText(String.valueOf(((Carro) auto).getPortas()));
	    } else if (auto instanceof Caminhao) {
	        cargaField.setText(String.valueOf(((Caminhao) auto).getCapacidadeCarga()));
	    }

	    salvarBtn.setText("Atualizar");
	    salvarBtn.addActionListener(e -> {
	        auto.setMarca(marcaField.getText());
	        auto.setModelo(modeloField.getText());
	        auto.setAno(Integer.parseInt(anoField.getText()));

	        if (auto instanceof Carro) {
	            ((Carro) auto).setPortas(Integer.parseInt(portasField.getText()));
	        } else if (auto instanceof Caminhao) {
	            ((Caminhao) auto).setCapacidadeCarga(Double.parseDouble(cargaField.getText()));
	        }

	        new AutomovelDAO().atualizar(auto);
	        JOptionPane.showMessageDialog(this, "Automóvel atualizado com sucesso!");
	        dispose();
	    });
	}
	
	private void alternarCampos() {
		String tipo = (String) tipoCombo.getSelectedItem();
		boolean isCarro = tipo.equalsIgnoreCase("Carro");

		portasField.setEnabled(isCarro);
		cargaField.setEnabled(!isCarro);
	}
	
	private void salvarAutomovel() {
		String tipo = (String) tipoCombo.getSelectedItem();
		String marca = marcaField.getText();
		String modelo = modeloField.getText();

		try {
			int ano = Integer.parseInt(anoField.getText());
			AutomovelDAO dao = new AutomovelDAO();

			if (tipo.equalsIgnoreCase("Carro")) {
				int portas = Integer.parseInt(portasField.getText());
				Carro carro = new Carro(marca, modelo, ano, portas);
				dao.inserir(carro);
			} else {
				double carga = Double.parseDouble(cargaField.getText());
				Caminhao caminhao = new Caminhao(marca, modelo, ano, carga);
				dao.inserir(caminhao);
			}

			JOptionPane.showMessageDialog(this, "Automóvel cadastrado com sucesso!");
			limparCampos();
			dispose();

		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Erro: verifique os campos numéricos.");
		}
	}
	
	private void limparCampos() {
		marcaField.setText("");
		modeloField.setText("");
		anoField.setText("");
		portasField.setText("");
		cargaField.setText("");
	}

}
