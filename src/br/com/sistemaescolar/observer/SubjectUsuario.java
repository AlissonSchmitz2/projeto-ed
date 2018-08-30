package br.com.sistemaescolar.observer;

import br.com.sistemaescolar.model.Usuario;

public interface SubjectUsuario {
	public void addObserver(ObserverUsuario o);
    public void removeObserver(ObserverUsuario o);
    public void notifyObservers(Usuario usuario);
}
