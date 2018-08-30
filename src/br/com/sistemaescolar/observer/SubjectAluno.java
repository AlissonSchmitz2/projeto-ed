package br.com.sistemaescolar.observer;

import br.com.sistemaescolar.model.Aluno;

public interface SubjectAluno {
	public void addObserver(ObserverAluno o);
    public void removeObserver(ObserverAluno o);
    public void notifyObservers(Aluno aluno);
}
