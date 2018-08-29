package observer;

import model.Usuario;

public interface SubjectUsuario {
	public void addObserver(ObserverUsuario o);
    public void removeObserver(ObserverUsuario o);
    public void notifyObservers(Usuario usuario);
}
