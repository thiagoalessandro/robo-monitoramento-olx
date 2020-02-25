package br.com.intelector.robomonitoramentoolx.repository;

import br.com.intelector.robomonitoramentoolx.model.Configuracao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfiguracaoRepository extends JpaRepository<Configuracao, Long> {

    Optional<Configuracao> findByNome(String nome);

}
