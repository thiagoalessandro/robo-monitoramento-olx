package br.com.intelector.gerenciarcontapagar.repository;

import br.com.intelector.gerenciarcontapagar.model.Lembrete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Repository
public interface LembreteRepository extends JpaRepository<Lembrete, Long> {

    Optional<Lembrete> findByDataCompraAndValor(Date dataCompra, BigDecimal valor);

}
