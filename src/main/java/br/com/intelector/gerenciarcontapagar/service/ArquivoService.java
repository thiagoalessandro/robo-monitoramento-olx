package br.com.intelector.gerenciarcontapagar.service;

import br.com.intelector.gerenciarcontapagar.configuration.ApplicationProperties;
import br.com.intelector.gerenciarcontapagar.domain.DominioCartao;
import br.com.intelector.gerenciarcontapagar.dto.LancamentoDTO;
import br.com.intelector.gerenciarcontapagar.exception.ArquivoException;
import br.com.intelector.gerenciarcontapagar.model.Arquivo;
import br.com.intelector.gerenciarcontapagar.repository.ArquivoRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Log4j2
@Service
public class ArquivoService {

    @Autowired
    private ApplicationProperties properties;

    @Autowired
    private ArquivoRepository repository;

    @Autowired
    private LancamentoService lancamentoService;

    public void validaArquivoDuplicado(String nome) throws ArquivoException {
        if (repository.findByNome(nome).isPresent())
            throw new ArquivoException("Arquivo já importado anteriormente!");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void processarArquivo() throws ArquivoException, FileNotFoundException, ParseException {
        String nomeArquivo;
        DominioCartao cartao;
        List<LancamentoDTO> lancamentosAtuais;
        Date dataFechamento;

        nomeArquivo = properties.getNomeArquivo();
        dataFechamento = capturarDataFechamento(nomeArquivo);
        cartao = capturarCartao(nomeArquivo);
        stepValidarArquivoDuplicado(nomeArquivo);
        stepRegistrarArquivo(nomeArquivo, cartao, dataFechamento);
        lancamentosAtuais = stepCarregarLancamentoFromArquivo(nomeArquivo);
        stepProcessarLancamentosPagos(lancamentosAtuais, cartao);
        stepProcessarLancamentosPendentes(lancamentosAtuais);
        stepProcessarLancamentosNovos(lancamentosAtuais, cartao);
    }

    private void stepRegistrarArquivo(String nome, DominioCartao cartao, Date dataFechamento) {
        Arquivo arquivo = new Arquivo();
        arquivo.setNome(nome);
        arquivo.setDataFechamento(dataFechamento);
        arquivo.setCartao(cartao);
        arquivo.setCdUsuAtu("BOT");
        repository.save(arquivo);
    }

    private void stepProcessarLancamentosNovos(List<LancamentoDTO> lancamentosAtuais, DominioCartao dominioCartao) throws ArquivoException, ParseException {
        lancamentoService.processarLancamentosNovos(lancamentosAtuais, dominioCartao);
    }

    private void stepProcessarLancamentosPendentes(List<LancamentoDTO> lancamentosAtuais) throws ArquivoException, ParseException {
        lancamentoService.processarLancamentosPendentes(lancamentosAtuais);
    }

    private void stepProcessarLancamentosPagos(List<LancamentoDTO> lancamentosAtuais, DominioCartao dominioCartao) {
        lancamentoService.processarLancamentosPagos(lancamentosAtuais, dominioCartao);
    }

    public void stepValidarArquivoDuplicado(String nome) throws ArquivoException {
        validaArquivoDuplicado(nome);
    }

    public Reader reader(String nome) throws FileNotFoundException {
        File file = new File(properties.getAppDiretorioArquivo().concat(nome));
        return new BufferedReader(new InputStreamReader(new FileInputStream(file)));
    }

    public List<LancamentoDTO> stepCarregarLancamentoFromArquivo(String nome) throws FileNotFoundException {
        Reader reader = reader(nome);
        CsvToBean<LancamentoDTO> csvToBean = new CsvToBeanBuilder(reader)
                .withType(LancamentoDTO.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
        return csvToBean.parse();
    }

    private Date capturarDataFechamento(String nomeArquivo) throws ArquivoException {
        String[] nomeArquivoTemp;
        String dataArquivo;
        try {
            nomeArquivoTemp = nomeArquivo.split("_");
            dataArquivo = nomeArquivoTemp[1].replace(".csv", "");
            return DateUtils.parseDate(dataArquivo, "ddMMyyyy");
        } catch (Exception e) {
            throw new ArquivoException("Erro ao buscar data de fechamento, arquivo com nome inválido");
        }
    }

    private DominioCartao capturarCartao(String nomeArquivo) throws ArquivoException {
        String[] nomeArquivoTemp;
        try {
            nomeArquivoTemp = nomeArquivo.split("_");
            return DominioCartao.convertStringToEnum(nomeArquivoTemp[0]);
        } catch (Exception e) {
            throw new ArquivoException("Erro ao buscar cartão, arquivo com nome inválido");
        }
    }
}
