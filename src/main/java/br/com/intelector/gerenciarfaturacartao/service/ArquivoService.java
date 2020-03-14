package br.com.intelector.gerenciarfaturacartao.service;

import br.com.intelector.gerenciarfaturacartao.configuration.ApplicationProperties;
import br.com.intelector.gerenciarfaturacartao.dto.LancamentoDTO;
import br.com.intelector.gerenciarfaturacartao.exception.ArquivoException;
import br.com.intelector.gerenciarfaturacartao.repository.ArquivoRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Log4j2
@Service
public class ArquivoService {

    @Autowired
    private ApplicationProperties properties;

    @Autowired
    private ArquivoRepository repository;

    public boolean validaArquivoDuplicado(String nome) throws ArquivoException {
        if(repository.findByNome(nome).isPresent())
            throw new ArquivoException("Arquivo já importado anteriormente!");
        return false;
    }

    public void processarArquivo(){
        String nome = null;
        try {
            nome = properties.getNomeArquivo();
            stepValidarArquivoDuplicado(nome);
            stepCarregarLancamentoFromArquivo(nome);
        } catch (FileNotFoundException e) {
            log.error("Arquivo não encontrado {}", nome, e);
        } catch (ArquivoException e) {
            log.error("Erro no arquivo {}", nome, e);
        }
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

}
