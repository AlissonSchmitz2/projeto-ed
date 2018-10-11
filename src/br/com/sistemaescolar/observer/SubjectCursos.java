package br.com.sistemaescolar.observer;

import br.com.sistemaescolar.model.Curso;

public interface SubjectCursos {
	public void addObserver(ObserverCursos o);
    public void removeObserver(ObserverCursos o);
    public void notifyObservers(Curso curso);
}
