package br.com.intelector.gerenciarfaturacartao.service;

import br.com.intelector.gerenciarfaturacartao.model.Assinatura;
import br.com.intelector.gerenciarfaturacartao.repository.AssinaturaRepository;
import br.com.intelector.gerenciarfaturacartao.repository.ConfiguracaoRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class AssinaturaService {

    @Autowired
    private AssinaturaRepository repository;

    public Optional<Assinatura> findByDescricao(String descricao) {
        return repository.findByDescricao(descricao);
    }
}
