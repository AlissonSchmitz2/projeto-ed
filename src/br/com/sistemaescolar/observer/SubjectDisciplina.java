package br.com.sistemaescolar.observer;

import br.com.sistemaescolar.model.Disciplina;

public interface SubjectDisciplina {
	public void addObserver(ObserverDisciplina o);
    public void removeObserver(ObserverDisciplina o);
    public void notifyObservers(Disciplina disciplina);
}
