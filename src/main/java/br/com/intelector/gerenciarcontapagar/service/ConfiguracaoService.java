package br.com.intelector.gerenciarcontapagar.service;

import br.com.intelector.gerenciarcontapagar.repository.ConfiguracaoRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class ConfiguracaoService {

    @Autowired
    private ConfiguracaoRepository configuracaoRepository;

    public String getValue(String nome) {
        try {
            return configuracaoRepository.findByNome(nome).get().getValor();
        } catch (Exception e) {
            log.error(e);
        }
        return null;
    }
}
