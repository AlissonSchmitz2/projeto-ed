package observer;

import model.Cidade;

public interface SubjectCidade {
	public void addObserver(ObserverCidade o);
    public void removeObserver(ObserverCidade o);
    public void notifyObservers(Cidade cidade);
}
