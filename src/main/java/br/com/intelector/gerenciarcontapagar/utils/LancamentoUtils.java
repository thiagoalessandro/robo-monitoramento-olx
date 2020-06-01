package br.com.intelector.gerenciarcontapagar.utils;

import br.com.intelector.gerenciarcontapagar.dto.LancamentoDTO;
import br.com.intelector.gerenciarcontapagar.exception.ArquivoException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringJoiner;

public class LancamentoUtils {

    public static String gerarHashLancamento(LancamentoDTO lancamentoDTO) throws ArquivoException, ParseException {
        StringBuilder strBuilder = new StringBuilder();

        String[] parcela = getParcela(lancamentoDTO.getMovimentacao());
        Date dataCompra = getDataCompra(lancamentoDTO.getData(), Integer.parseInt(parcela[1]));
        String descricao = getDescricao(lancamentoDTO.getMovimentacao());

        strBuilder.append(lancamentoDTO.getCartao().replace("_", ""));
        strBuilder.append(DateFormatUtils.format(dataCompra, "ddMMyyyy"));
        strBuilder.append(StringUtils.deleteWhitespace(descricao.toUpperCase()));
        strBuilder.append(lancamentoDTO.getValor().replace(".", ""));

        return strBuilder.toString();
    }

    public static String[] getParcela(String movimentacao) {
        String parcela;
        if (movimentacao.contains("/")) {
            parcela = movimentacao.substring(movimentacao.length()-5);
            return parcela.split("/");
        }
        return new String[]{"0", "0"};
    }

    public static String getDescricao(String movimentacao) {

        if (!movimentacao.contains("/"))
            return movimentacao.trim();

        return movimentacao.trim().substring(0, movimentacao.length()-5).trim();
    }

    public static Date getDataCompra(String diaMes, int parcelaAtual) throws ArquivoException, ParseException {
        if(diaMes.contains("/")) {
            Calendar calendar = new GregorianCalendar();
            String[] dataTemp = diaMes.split("/");
            int mesAtual = Calendar.getInstance().get(Calendar.MONTH) + 1;
            String mesCompra = dataTemp[1];

            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dataTemp[0]));
            calendar.set(Calendar.MONTH, Integer.parseInt(mesCompra) - 1);
            if (parcelaAtual > mesAtual)
                calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR) - 1);
            else
                calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
            return calendar.getTime();
        }else {
            return DateUtils.parseDate(diaMes, "yyyy-MM-dd");
        }
    }

    public static String converteMesToNum(String mes) throws ArquivoException {
        String[] mesNum = new String[]{"JAN", "FEV", "MAR", "ABR", "MAI", "JUN", "JUL", "AGO", "SET", "OUT", "NOV", "DEZ"};
        for (int i = 0; i < mesNum.length; i++){
            if(mes.equalsIgnoreCase(mesNum[i]))
                return StringUtils.leftPad(String.valueOf(i+1), 2, "0");
        }
        throw new ArquivoException("Erro ao buscar mês");
    }
}
