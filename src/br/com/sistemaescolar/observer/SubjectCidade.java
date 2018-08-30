package br.com.sistemaescolar.observer;

import br.com.sistemaescolar.model.Cidade;

public interface SubjectCidade {
	public void addObserver(ObserverCidade o);
    public void removeObserver(ObserverCidade o);
    public void notifyObservers(Cidade cidade);
}
