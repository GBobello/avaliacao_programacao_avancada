package models;

public class Carro extends Automovel {
	private int portas;
	
	public Carro(String marca, String modelo, int ano, int portas) {
		super(marca, modelo, ano);
		this.portas = portas;
	}
	
	@Override
	public String getTipo() {
		return "Carro";
	}

	public int getPortas() {
		return portas;
	}

	public void setPortas(int portas) {
		this.portas = portas;
	}
}
