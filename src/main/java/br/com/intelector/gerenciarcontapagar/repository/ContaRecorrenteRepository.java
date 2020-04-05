package br.com.intelector.gerenciarcontapagar.repository;

import br.com.intelector.gerenciarcontapagar.model.ContaRecorrente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContaRecorrenteRepository extends JpaRepository<ContaRecorrente, Long> {

    Optional<ContaRecorrente> findByDescricao(String descricao);

}
