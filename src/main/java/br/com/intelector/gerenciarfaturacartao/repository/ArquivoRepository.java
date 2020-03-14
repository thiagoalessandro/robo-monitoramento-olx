package br.com.intelector.gerenciarfaturacartao.repository;

import br.com.intelector.gerenciarfaturacartao.model.Arquivo;
import br.com.intelector.gerenciarfaturacartao.model.Configuracao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArquivoRepository extends JpaRepository<Arquivo, Long> {

    Optional<Arquivo> findByNome(String nome);

}
