package br.com.intelector.robomonitoramentoolx.repository;

import br.com.intelector.robomonitoramentoolx.domain.DominioSituacaoRegistro;
import br.com.intelector.robomonitoramentoolx.model.Monitoramento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonitoramentoRepository extends JpaRepository<Monitoramento, Long> {

    List<Monitoramento> findBySituacao(DominioSituacaoRegistro situacao);

}
