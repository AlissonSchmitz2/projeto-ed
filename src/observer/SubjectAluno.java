package observer;

import model.Aluno;

public interface SubjectAluno {
	public void addObserver(ObserverAluno o);
    public void removeObserver(ObserverAluno o);
    public void notifyObservers(Aluno aluno);
}
