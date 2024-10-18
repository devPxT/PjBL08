interface Subject {
    void adicionarObserver(Observer observer);

    void removerObserver();

    void notificarObserver(String mensagem);
}