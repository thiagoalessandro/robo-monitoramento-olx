package br.com.intelector.gerenciarcontapagar.dto;

import br.com.intelector.gerenciarcontapagar.exception.ArquivoException;
import br.com.intelector.gerenciarcontapagar.utils.LancamentoUtils;
import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import lombok.ToString;

import java.text.ParseException;
import java.util.Date;

@Data
@ToString
public class LancamentoDTO {

    @CsvBindByName(column = "data")
    private String data;

    @CsvBindByName(column = "movimentacao")
    private String movimentacao;

    @CsvBindByName(column = "valor")
    private String valor;

    @CsvBindByName(column = "cartao")
    private String cartao;

    public String getHash() throws ArquivoException, ParseException {
        return LancamentoUtils.gerarHashLancamento(this);
    }

    public String[] getParcela(){
        return LancamentoUtils.getParcela(this.movimentacao);
    }

    public Date getDataCompra() throws ArquivoException, ParseException {
        return LancamentoUtils.getDataCompra(this.getData(), Integer.parseInt(getParcela()[1]));
    }

    public String getDescricao(){
        return LancamentoUtils.getDescricao(this.getMovimentacao());
    }

}
