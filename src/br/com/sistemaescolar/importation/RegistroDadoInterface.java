package br.com.sistemaescolar.importation;

import br.com.sistemaescolar.model.Configuracoes;

public interface RegistroDadoInterface {
	public boolean validar(Configuracoes configuracoes) throws DadosInvalidosException;
}
