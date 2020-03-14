package br.com.intelector.gerenciarfaturacartao.dto;

import com.opencsv.bean.CsvBindByName;

public class LancamentoDTO {

    @CsvBindByName(column = "data")
    private String data;

    @CsvBindByName(column = "movimentacao")
    private String movimentacao;

    @CsvBindByName(column = "valor")
    private String valor;

}
