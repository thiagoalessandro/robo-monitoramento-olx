package br.com.intelector.gerenciarfaturacartao.repository;

import br.com.intelector.gerenciarfaturacartao.model.Assinatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssinaturaRepository extends JpaRepository<Assinatura, Long> {

    Optional<Assinatura> findByDescricao(String descricao);

}
