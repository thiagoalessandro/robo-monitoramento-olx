package br.com.intelector.gerenciarfaturacartao.repository;

import br.com.intelector.gerenciarfaturacartao.model.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

    Optional<Lancamento> findByDataCompraAndDescricao(Date dataCompra, String descricao);

}
