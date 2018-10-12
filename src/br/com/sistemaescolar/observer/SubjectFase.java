package br.com.sistemaescolar.observer;

import br.com.sistemaescolar.model.Fase;

public interface SubjectFase {
	public void addObserver(ObserverFase o);
    public void removeObserver(ObserverFase o);
    public void notifyObservers(Fase fase);
}
