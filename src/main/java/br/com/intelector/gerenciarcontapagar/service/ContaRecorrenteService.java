package br.com.intelector.gerenciarcontapagar.service;

import br.com.intelector.gerenciarcontapagar.model.ContaRecorrente;
import br.com.intelector.gerenciarcontapagar.repository.ContaRecorrenteRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class ContaRecorrenteService {

    @Autowired
    private ContaRecorrenteRepository repository;

    public Optional<ContaRecorrente> findByDescricao(String descricao) {
        return repository.findByDescricaoIgnoreCase(descricao);
    }

    public Optional<ContaRecorrente> procurarContaRecorrente(String descricao) {
        return findByDescricao(descricao);
    }
}
