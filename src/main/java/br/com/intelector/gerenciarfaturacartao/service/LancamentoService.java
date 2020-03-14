package br.com.intelector.gerenciarfaturacartao.service;

import br.com.intelector.gerenciarfaturacartao.model.Lancamento;
import br.com.intelector.gerenciarfaturacartao.repository.LancamentoRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Log4j2
@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository repository;

    public Optional<Lancamento> findByDataCompraAndDescricao(Date dataCompra, String descricao) {
        return repository.findByDataCompraAndDescricao(dataCompra, descricao);
    }
}
