package models;

public class Caminhao extends Automovel {
	private double capacidadeCarga;
	
	public Caminhao(String marca, String modelo, int ano, double capacidadeCarga) {
		super(marca, modelo, ano);
		this.capacidadeCarga = capacidadeCarga;
	}

	@Override
	public String getTipo() {
		return "Caminhao";
	}

	public void setCapacidadeCarga(double capacidadeCarga) {
		this.capacidadeCarga = capacidadeCarga;
	}

	public double getCapacidadeCarga() {
		return capacidadeCarga;
	}

}
