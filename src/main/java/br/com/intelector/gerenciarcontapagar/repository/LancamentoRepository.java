package br.com.intelector.gerenciarcontapagar.repository;

import br.com.intelector.gerenciarcontapagar.domain.DominioSituacaoLancamento;
import br.com.intelector.gerenciarcontapagar.domain.DominioCartao;
import br.com.intelector.gerenciarcontapagar.model.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

    List<Lancamento> findBySituacaoLancamentoAndCartao(DominioSituacaoLancamento dominioSituacaoLancamento, DominioCartao cartao);

    Optional<Lancamento> findByHashAndSituacaoLancamento(String hash, DominioSituacaoLancamento dominioSituacaoLancamento);

    boolean existsByHash(String hash);
}
