package br.com.intelector.gerenciarcontapagar.service;

import br.com.intelector.gerenciarcontapagar.domain.DominioResponsavel;
import br.com.intelector.gerenciarcontapagar.model.Lembrete;
import br.com.intelector.gerenciarcontapagar.repository.LembreteRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Log4j2
@Service
public class LembreteService {

    @Autowired
    private LembreteRepository repository;

    public Optional<Lembrete> findByDataCompraAndValor(Date dataCompra, BigDecimal valor) {
        return repository.findByDataCompraAndValor(dataCompra, valor);
    }

    public Optional<Lembrete> procurarLembrete(Date dataCompra, BigDecimal valor) {
       return findByDataCompraAndValor(dataCompra, valor);
    }
}
