package br.com.intelector.gerenciarcontapagar.exception;

public class DominioException extends Exception {
    public DominioException(Exception e) {
        super(e);
    }

    public DominioException(String mensagem) {
        super(mensagem);
    }
}
