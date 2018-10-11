package br.com.sistemaescolar.observer;

import br.com.sistemaescolar.model.Grade;

public interface SubjectGrade {
	public void addObserver(ObserverGrade o);
    public void removeObserver(ObserverGrade o);
    public void notifyObservers(Grade grade);
}
