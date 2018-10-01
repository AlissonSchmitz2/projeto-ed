package br.com.sistemaescolar.observer;

import br.com.sistemaescolar.model.Professor;

public interface SubjectProfessor {
	public void addObserver(ObserverProfessor o);
    public void removeObserver(ObserverProfessor o);
    public void notifyObservers(Professor professor);
}
