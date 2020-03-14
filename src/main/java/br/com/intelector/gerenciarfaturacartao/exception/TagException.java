package br.com.intelector.gerenciarfaturacartao.exception;

public class TagException extends Exception {
    public TagException(Exception e) {
        super(e);
    }

    public TagException(String mensagem) {
        super(mensagem);
    }
}
