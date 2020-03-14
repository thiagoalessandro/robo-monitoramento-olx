package br.com.intelector.gerenciarfaturacartao.exception;

public class ArquivoException extends Exception {
    public ArquivoException(Exception e) {
        super(e);
    }
    public ArquivoException(String messagem) {
        super(messagem);
    }
}
