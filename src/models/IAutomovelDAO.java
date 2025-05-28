package models;

import java.util.List;

public interface IAutomovelDAO {
	void inserir(Automovel auto);
	void atualizar(Automovel auto);
	void excluir(Automovel auto);
	Automovel buscarPorId(int id);
	List<Automovel> listarTodos();
}
